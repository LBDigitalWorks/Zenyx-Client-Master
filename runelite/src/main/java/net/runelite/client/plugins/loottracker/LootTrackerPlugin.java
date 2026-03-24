/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.loottracker;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Provides;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.Player;
import net.runelite.api.SpriteID;
import net.runelite.api.SpritePixels;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.Text;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "Loot Tracker",
	description = "Tracks loot received from NPC and PvP kills",
	tags = {"drops", "kills", "loot", "pvm", "pvp", "tracker"}
)
public class LootTrackerPlugin extends Plugin
{
	private static final String CONFIG_KEY_RECORDS = "records";
	private static final Type SAVE_TYPE = new TypeToken<List<LootTrackerSaveRecord>>() {}.getType();

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ItemManager itemManager;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private LootTrackerConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private Gson gson;

	private LootTrackerPanel panel;
	private NavigationButton navButton;

	/** Aggregated loot records keyed by source name (NPC or player name). */
	private final Map<String, LootTrackerRecord> records = new LinkedHashMap<>();

	@Override
	protected void startUp()
	{
		panel = new LootTrackerPanel(itemManager, this, config);
		loadRecords();
		rebuildPanel();

		// Attempt to load the inventory tab sprite from the game cache on the client thread.
		// invokeLater retries until it returns true. If the sprite cannot be loaded,
		// a simple generated fallback icon is used so the nav button always appears.
		clientThread.invokeLater(() ->
		{
			try
			{
				final SpritePixels[] sprites = client.getSprites(client.getIndexSprites(), SpriteID.TAB_INVENTORY, 0);
				if (sprites == null || sprites.length == 0 || sprites[0] == null)
				{
					return false; // not ready yet — retry next tick
				}

				final BufferedImage icon = ImageUtil.resizeImage(sprites[0].toBufferedImage(), 16, 16);
				SwingUtilities.invokeLater(() -> addNavButton(icon));
				return true;
			}
			catch (Exception e)
			{
				log.warn("Loot Tracker: failed to load sprite icon, using fallback", e);
				SwingUtilities.invokeLater(() -> addNavButton(createFallbackIcon()));
				return true;
			}
		});
	}

	@Override
	protected void shutDown()
	{
		if (navButton != null)
		{
			clientToolbar.removeNavigation(navButton);
		}
		records.clear();
		panel = null;
		navButton = null;
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived event)
	{
		if (!config.showNpcLoot())
		{
			return;
		}

		final String name = sanitizeName(event.getNpcName(), "Unknown");
		final Collection<ItemStack> items = event.getItems();

		addLoot(name, items);
	}

	@Subscribe
	public void onPlayerLootReceived(PlayerLootReceived event)
	{
		if (!config.showPvpLoot())
		{
			return;
		}

		final Player player = event.getPlayer();
		final String name = sanitizeName(player.getName(), "Unknown Player");
		final Collection<ItemStack> items = event.getItems();

		addLoot(name + " (PvP)", items);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!LootTrackerConfig.GROUP.equals(event.getGroup()))
		{
			return;
		}

		if ("priceType".equals(event.getKey()))
		{
			recalculateRecordPrices();
			saveRecords();
		}

		if ("records".equals(event.getKey()))
		{
			return;
		}

		rebuildPanel();
	}

	/**
	 * Clears all tracked loot and resets the panel. Called from the panel's Clear button.
	 */
	void clearLoot()
	{
		records.clear();
		saveRecords();
		SwingUtilities.invokeLater(panel::clearAll);
	}

	private void addNavButton(BufferedImage icon)
	{
		navButton = NavigationButton.builder()
			.tooltip("Loot Tracker")
			.icon(icon)
			.priority(5)
			.panel(panel)
			.build();
		clientToolbar.addNavigation(navButton);
	}

	/**
	 * Creates a simple 16x16 fallback icon (green square with "L" letter)
	 * used when the game sprite cannot be loaded.
	 */
	private BufferedImage createFallbackIcon()
	{
		final BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(0x00, 0xAA, 0x44));
		g.fillRect(0, 0, 16, 16);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 11));
		final FontMetrics fm = g.getFontMetrics();
		final int x = (16 - fm.stringWidth("L")) / 2;
		final int y = (16 - fm.getHeight()) / 2 + fm.getAscent();
		g.drawString("L", x, y);
		g.dispose();
		return img;
	}

	private void addLoot(String sourceName, Collection<ItemStack> itemStacks)
	{
		final List<LootTrackerItem> trackerItems = buildItems(itemStacks);
		if (trackerItems.isEmpty())
		{
			return;
		}

		LootTrackerRecord record = records.remove(sourceName);
		if (record == null)
		{
			record = new LootTrackerRecord(sourceName);
		}
		record.addKill(trackerItems);
		records.put(sourceName, record);
		moveRecordToFront(sourceName);

		saveRecords();

		final LootTrackerRecord snapshot = record;
		SwingUtilities.invokeLater(() -> panel.updateRecord(snapshot));
	}

	private List<LootTrackerItem> buildItems(Collection<ItemStack> itemStacks)
	{
		final List<LootTrackerItem> result = new ArrayList<>();
		for (ItemStack stack : itemStacks)
		{
			final int itemId = stack.getId();
			final int quantity = stack.getQuantity();

			if (itemId <= 0)
			{
				continue;
			}

			final int canonId = itemManager.canonicalize(itemId);
			final ItemComposition canonComp = itemManager.getItemComposition(canonId);
			if (canonComp == null)
			{
				continue;
			}

			final String name = sanitizeItemName(canonComp.getName());
			if (name == null)
			{
				// Item has no valid name in the cache — skip it (null-named items with rogue sprites)
				continue;
			}

			final long price;
			if (config.priceType() == LootTrackerPriceType.HIGH_ALCHEMY)
			{
				price = canonComp.getHaPrice();
			}
			else
			{
				price = itemManager.getItemPrice(canonId);
			}

			result.add(new LootTrackerItem(canonId, name, quantity, price));
		}
		return result;
	}

	private static String sanitizeItemName(String name)
	{
		return sanitizeName(name, null);
	}

	private static String sanitizeName(String name, String fallback)
	{
		if (name == null)
		{
			return fallback;
		}

		name = Text.removeTags(name).replace('\u00A0', ' ').trim();
		if (name.isEmpty() || "null".equalsIgnoreCase(name))
		{
			return fallback;
		}

		return name;
	}

	private void rebuildPanel()
	{
		final List<LootTrackerRecord> snapshots = records.values().stream()
			.collect(Collectors.toList());

		SwingUtilities.invokeLater(() -> panel.setRecords(snapshots));
	}

	private void recalculateRecordPrices()
	{
		for (LootTrackerRecord record : records.values())
		{
			record.reprice(this::getPriceForItem);
		}
	}

	private long getPriceForItem(int itemId)
	{
		final ItemComposition comp = itemManager.getItemComposition(itemId);
		if (config.priceType() == LootTrackerPriceType.HIGH_ALCHEMY)
		{
			return comp.getHaPrice();
		}

		return itemManager.getItemPrice(itemId);
	}

	private void loadRecords()
	{
		records.clear();

		final String json = configManager.getConfiguration(LootTrackerConfig.GROUP, CONFIG_KEY_RECORDS);
		if (json == null || json.isEmpty())
		{
			return;
		}

		try
		{
			final List<LootTrackerSaveRecord> saved = gson.fromJson(json, SAVE_TYPE);
			if (saved == null)
			{
				return;
			}

			for (LootTrackerSaveRecord entry : saved)
			{
				if (entry == null || entry.getTitle() == null || entry.getTitle().isEmpty())
				{
					continue;
				}

				final LootTrackerRecord record = new LootTrackerRecord(entry.getTitle());
				record.restore(entry.getKills(), entry.getItems());
				records.put(entry.getTitle(), record);
			}
		}
		catch (Exception ex)
		{
			log.warn("Unable to load loot tracker records", ex);
		}
	}

	private void saveRecords()
	{
		final List<LootTrackerSaveRecord> serializable = records.values().stream()
			.map(record -> new LootTrackerSaveRecord(
				record.getTitle(),
				record.getKills(),
				record.getItems().stream()
					.filter(Objects::nonNull)
					.collect(Collectors.toList())
			))
			.collect(Collectors.toList());

		configManager.setConfiguration(
			LootTrackerConfig.GROUP,
			CONFIG_KEY_RECORDS,
			gson.toJson(serializable)
		);
	}

	private void moveRecordToFront(String sourceName)
	{
		final LootTrackerRecord current = records.remove(sourceName);
		if (current == null)
		{
			return;
		}

		final Map<String, LootTrackerRecord> reordered = new LinkedHashMap<>();
		reordered.put(sourceName, current);
		reordered.putAll(records);
		records.clear();
		records.putAll(reordered);
	}

	@Provides
	LootTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LootTrackerConfig.class);
	}
}
