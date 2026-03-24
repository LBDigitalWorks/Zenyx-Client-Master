/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package net.runelite.client.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.NpcID;
import net.runelite.api.Player;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemQuantityChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.util.Text;

@Singleton
@Slf4j
public class LootManager
{
	private static final int GENERIC_LOOT_WAIT_TICKS = 6;

	private static final Map<Integer, Integer> NPC_DEATH_ANIMATIONS = ImmutableMap.of(
		NpcID.CAVE_KRAKEN, AnimationID.CAVE_KRAKEN_DEATH
	);

	private final EventBus eventBus;
	private final Client client;
	private final NpcUtil npcUtil;
	private final ListMultimap<Integer, ItemStack> itemSpawns = ArrayListMultimap.create();
	private final Set<WorldPoint> killPoints = new HashSet<>();
	/**
	 * NPCs that despawned this tick paired with their pre-computed drop areas.
	 * Drop areas must be captured eagerly in onNpcDespawned while the NPC's
	 * definition (desc) is still non-null; by the time onGameTick runs, desc
	 * has already been cleared by the game engine.
	 */
	private final List<PendingNpcLoot> pendingLootNpcs = new ArrayList<>();
	private WorldPoint playerLocationLastTick;
	private WorldPoint krakenPlayerLocation;

	private NPC delayedLootNpc;
	private int delayedLootTick;
	private List<WorldArea> delayedLootAreas;

	private static final class PendingNpcLoot
	{
		private final NPC npc;
		private final int npcId;
		private final String npcName;
		private final List<WorldArea> dropAreas;
		private int ticksRemaining;

		private PendingNpcLoot(NPC npc, int npcId, String npcName, List<WorldArea> dropAreas, int ticksRemaining)
		{
			this.npc = npc;
			this.npcId = npcId;
			this.npcName = npcName;
			this.dropAreas = dropAreas;
			this.ticksRemaining = ticksRemaining;
		}
	}

	private static final class NpcLootMatchResult
	{
		private final List<ItemStack> items;
		private final String source;

		private NpcLootMatchResult(List<ItemStack> items, String source)
		{
			this.items = items;
			this.source = source;
		}
	}

	@Inject
	private LootManager(EventBus eventBus, Client client, NpcUtil npcUtil)
	{
		this.eventBus = eventBus;
		this.client = client;
		this.npcUtil = npcUtil;
		eventBus.register(this);
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned)
	{
		final NPC npc = npcDespawned.getNpc();

		// Delayed-loot NPCs (e.g. Nightmare) drop items a few ticks after death;
		// the delayed handler deals with them, so skip normal processing here.
		if (npc == delayedLootNpc)
		{
			clearDelayedLootNpc();
			return;
		}

		// Only queue NPCs that are actually dying. This prevents visual/animation
		// NPCs (which have no Attack action and null names) from being mistakenly
		// attributed with items that spawn nearby from an actual kill.
		if (!npcUtil.isDying(npc))
		{
			return;
		}

		// Capture drop areas now while the NPC's desc is still non-null.
		// The game engine nulls desc immediately after posting NpcDespawned,
		// so any call to npc.getId() / getWorldArea() after this point would NPE.
		final List<WorldArea> dropAreas = getDropLocations(npc);
		final int npcId = getNpcIdSafe(npc);
		final String npcName = getNpcName(npc);
		log.debug("NPC despawn candidate id={} name='{}' areas={}", npcId, npcName, dropAreas);

		// Queue for processing in onGameTick. Item-spawn packets (opcode 215) and
		// NPC-update packets (opcode 65) arrive in server-defined order; deferring
		// to onGameTick ensures all ItemSpawned events for this tick have fired
		// before we search itemSpawns, regardless of packet order.
		pendingLootNpcs.add(new PendingNpcLoot(npc, npcId, npcName, dropAreas, GENERIC_LOOT_WAIT_TICKS));
	}

	@Subscribe
	public void onPlayerDespawned(PlayerDespawned playerDespawned)
	{
		final Player player = playerDespawned.getPlayer();
		// Only care about dead Players
		if (player.getHealthRatio() != 0)
		{
			return;
		}

		final WorldPoint worldPoint = player.getWorldLocation();
		final LocalPoint location = LocalPoint.fromWorld(client, worldPoint);
		if (location == null || killPoints.contains(worldPoint))
		{
			return;
		}

		final int x = location.getSceneX();
		final int y = location.getSceneY();
		final int packed = x << 8 | y;
		final Collection<ItemStack> items = itemSpawns.get(packed);

		if (items.isEmpty())
		{
			return;
		}

		killPoints.add(worldPoint);
		eventBus.post(new PlayerLootReceived(player, items));
	}

	@Subscribe
	public void onItemSpawned(ItemSpawned itemSpawned)
	{
		final TileItem item = itemSpawned.getItem();
		final Tile tile = itemSpawned.getTile();
		final LocalPoint location = tile.getLocalLocation();
		final int packed = location.getSceneX() << 8 | location.getSceneY();
		itemSpawns.put(packed, new ItemStack(item.getId(), item.getQuantity(), location));
		log.debug("Item spawn {} ({}) location {}", item.getId(), item.getQuantity(), location);
	}

	@Subscribe
	public void onItemDespawned(ItemDespawned itemDespawned)
	{
		final TileItem item = itemDespawned.getItem();
		final LocalPoint location = itemDespawned.getTile().getLocalLocation();
		log.debug("Item despawn {} ({}) location {}", item.getId(), item.getQuantity(), location);
	}

	@Subscribe
	public void onItemQuantityChanged(ItemQuantityChanged itemQuantityChanged)
	{
		final TileItem item = itemQuantityChanged.getItem();
		final Tile tile = itemQuantityChanged.getTile();
		final LocalPoint location = tile.getLocalLocation();
		final int packed = location.getSceneX() << 8 | location.getSceneY();
		final int diff = itemQuantityChanged.getNewQuantity() - itemQuantityChanged.getOldQuantity();

		if (diff <= 0)
		{
			return;
		}

		itemSpawns.put(packed, new ItemStack(item.getId(), diff, location));
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged e)
	{
		if (!(e.getActor() instanceof NPC))
		{
			return;
		}

		final NPC npc = (NPC) e.getActor();
		int id = npc.getId();

		// We only care about certain NPCs
		final Integer deathAnim = NPC_DEATH_ANIMATIONS.get(id);

		// Current animation is death animation?
		if (deathAnim != null && deathAnim == npc.getAnimation())
		{
			if (id == NpcID.CAVE_KRAKEN)
			{
				// Big Kraken drops loot wherever player is standing when animation starts.
				krakenPlayerLocation = client.getLocalPlayer().getWorldLocation();
			}
			else
			{
				// These NPCs drop loot on death animation, which is right now.
				processNpcLoot(npc);
			}
		}
	}

	@Subscribe
	public void onNpcChanged(NpcChanged npcChanged)
	{
		final NPC npc = npcChanged.getNpc();
		if (npc.getId() == NpcID.THE_NIGHTMARE_9433 || npc.getId() == NpcID.PHOSANIS_NIGHTMARE_9424)
		{
			delayedLootNpc = npc;
			delayedLootTick = 10;
			// it is too early to call getAdjacentSquareLootTile() because the player might move before the
			// loot location is calculated by the server.
		}
		else if (npc.getId() == NpcID.HOLE_IN_THE_WALL)
		{
			delayedLootNpc = npc;
			delayedLootTick = 1;
			delayedLootAreas = getDropLocations(npc);
		}
		else if (npc.getId() == NpcID.DUKE_SUCELLUS_12192 || npc.getId() == NpcID.DUKE_SUCELLUS_12196)
		{
			delayedLootNpc = npc;
			delayedLootTick = 5;
			delayedLootAreas = getDropLocations(npc);
		}
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		// Process NPCs that despawned this tick. All ItemSpawned events for this
		// tick have already fired (packets processed before GameTick is posted),
		// so itemSpawns is fully populated when we check it here.
		for (int i = pendingLootNpcs.size() - 1; i >= 0; i--)
		{
			final PendingNpcLoot entry = pendingLootNpcs.get(i);
			final NPC npc = entry.npc;
			final List<WorldArea> areas = entry.dropAreas;
			final NpcLootMatchResult match = getItemStacksForNpc(areas);
			if (!match.items.isEmpty())
			{
				log.debug("NPC loot matched id={} name='{}' source={} items={}", entry.npcId, entry.npcName, match.source, match.items);
				eventBus.post(new NpcLootReceived(npc, entry.npcName, match.items));
				pendingLootNpcs.remove(i);
				continue;
			}

			entry.ticksRemaining--;
			if (entry.ticksRemaining <= 0)
			{
				log.debug("NPC loot expired id={} name='{}' with no matched drops", entry.npcId, entry.npcName);
				pendingLootNpcs.remove(i);
			}
		}

		if (delayedLootNpc != null && --delayedLootTick == 0)
		{
			processDelayedLoot();
			clearDelayedLootNpc();
		}

		playerLocationLastTick = client.getLocalPlayer().getWorldLocation();

		itemSpawns.clear();
		killPoints.clear();
	}

	private void processDelayedLoot()
	{
		if (delayedLootAreas == null)
		{
			// This is only for nightmare
			delayedLootAreas = List.of(getAdjacentSquareLootTile(delayedLootNpc).toWorldArea());
		}

		final NpcLootMatchResult match = getItemStacksForNpc(delayedLootAreas);
		if (!match.items.isEmpty())
		{
			log.debug("Got delayed loot stack from {} via {}: {}", getNpcName(delayedLootNpc), match.source, match.items);
			eventBus.post(new NpcLootReceived(delayedLootNpc, getNpcName(delayedLootNpc), match.items));
		}
		else
		{
			log.debug("Delayed loot expired with no loot");
		}
	}

	private void processNpcLoot(NPC npc)
	{
		final NpcLootMatchResult match = getItemStacksForNpc(getDropLocations(npc));
		if (!match.items.isEmpty())
		{
			final int npcId = getNpcIdSafe(npc);
			final String npcName = getNpcName(npc);
			log.debug("NPC death-animation loot matched id={} name='{}' source={} items={}", npcId, npcName, match.source, match.items);
			eventBus.post(new NpcLootReceived(npc, npcName, match.items));
		}
	}

	private static String getNpcName(NPC npc)
	{
		if (npc == null)
		{
			return "Unknown";
		}

		String name = null;
		try
		{
			name = npc.getName();
		}
		catch (Exception ex)
		{
			// Some clients null NPC desc immediately on despawn, causing getName/getId to NPE.
		}
		if (name == null || name.isEmpty() || "null".equalsIgnoreCase(name))
		{
			try
			{
				final NPCComposition composition = npc.getTransformedComposition();
				if (composition != null)
				{
					name = composition.getName();
				}
			}
			catch (Exception ex)
			{
				// Ignore and fall through to Unknown.
			}
		}

		if (name == null || name.isEmpty() || "null".equalsIgnoreCase(name))
		{
			return "Unknown";
		}

		name = Text.removeTags(name).replace('\u00A0', ' ').trim();
		return name.isEmpty() ? "Unknown" : name;
	}

	private static int getNpcIdSafe(NPC npc)
	{
		if (npc == null)
		{
			return -1;
		}

		try
		{
			return npc.getId();
		}
		catch (Exception ex)
		{
			return -1;
		}
	}

	private List<ItemStack> getItemStacksFromAreas(final List<WorldArea> areas)
	{
		final List<ItemStack> allItems = new ArrayList<>();
		for (final WorldArea dropLocation : areas)
		{
			final WorldPoint worldPoint = dropLocation.toWorldPoint();
			final LocalPoint location = LocalPoint.fromWorld(client, worldPoint);
			if (location == null)
			{
				continue;
			}

			final int x = location.getSceneX();
			final int y = location.getSceneY();

			for (int i = 0; i < dropLocation.getWidth(); ++i)
			{
				for (int j = 0; j < dropLocation.getHeight(); ++j)
				{
					WorldPoint dropPoint = new WorldPoint(worldPoint.getX() + i, worldPoint.getY() + j, worldPoint.getPlane());
					if (!killPoints.add(dropPoint))
					{
						continue;
					}

					final int packed = (x + i) << 8 | (y + j);
					final Collection<ItemStack> items = itemSpawns.get(packed);
					allItems.addAll(items);
				}
			}
		}

		return allItems;
	}

	private NpcLootMatchResult getItemStacksForNpc(final List<WorldArea> primaryAreas)
	{
		final List<ItemStack> fromNpcArea = getItemStacksFromAreas(primaryAreas);
		if (!fromNpcArea.isEmpty())
		{
			return new NpcLootMatchResult(fromNpcArea, "npc-area");
		}

		// Some servers place drops on adjacent tiles around the NPC area.
		final List<WorldArea> expandedNpcAreas = new ArrayList<>(primaryAreas.size());
		for (WorldArea area : primaryAreas)
		{
			expandedNpcAreas.add(new WorldArea(
				area.getX() - 1,
				area.getY() - 1,
				area.getWidth() + 2,
				area.getHeight() + 2,
				area.getPlane()
			));
		}
		final List<ItemStack> fromExpandedNpcArea = getItemStacksFromAreas(expandedNpcAreas);
		if (!fromExpandedNpcArea.isEmpty())
		{
			return new NpcLootMatchResult(fromExpandedNpcArea, "expanded-npc-area");
		}

		// Some servers place NPC drops on the player tile rather than the NPC tile.
		final Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null)
		{
			return new NpcLootMatchResult(fromExpandedNpcArea, "none");
		}

		final List<WorldArea> fallbackAreas = new ArrayList<>(2);
		final WorldPoint currentPlayerLocation = localPlayer.getWorldLocation();
		if (currentPlayerLocation != null)
		{
			fallbackAreas.add(currentPlayerLocation.toWorldArea());
		}
		if (playerLocationLastTick != null && !playerLocationLastTick.equals(currentPlayerLocation))
		{
			fallbackAreas.add(playerLocationLastTick.toWorldArea());
		}
		if (fallbackAreas.isEmpty())
		{
			return new NpcLootMatchResult(fromExpandedNpcArea, "none");
		}

		final List<ItemStack> fromPlayerFallback = getItemStacksFromAreas(fallbackAreas);
		if (!fromPlayerFallback.isEmpty())
		{
			return new NpcLootMatchResult(fromPlayerFallback, "player-fallback");
		}

		return new NpcLootMatchResult(fromPlayerFallback, "none");
	}

	private List<WorldArea> getDropLocations(NPC npc)
	{
		switch (npc.getId())
		{
			case NpcID.KRAKEN:
			case NpcID.KRAKEN_6640:
			case NpcID.KRAKEN_6656:
				return Collections.singletonList(playerLocationLastTick.toWorldArea());
			case NpcID.CAVE_KRAKEN:
				return Collections.singletonList(krakenPlayerLocation.toWorldArea());
			case NpcID.ZULRAH:      // Green
			case NpcID.ZULRAH_2043: // Red
			case NpcID.ZULRAH_2044: // Blue
				for (Map.Entry<Integer, ItemStack> entry : itemSpawns.entries())
				{
					if (entry.getValue().getId() == ItemID.ZULRAHS_SCALES)
					{
						int packed = entry.getKey();
						int unpackedX = packed >> 8;
						int unpackedY = packed & 0xFF;
						final WorldPoint lootPoint = WorldPoint.fromScene(client, unpackedX, unpackedY, npc.getWorldLocation().getPlane());
						return Collections.singletonList(lootPoint.toWorldArea());
					}
				}
				break;
			case NpcID.VORKATH:
			case NpcID.VORKATH_8058:
			case NpcID.VORKATH_8059:
			case NpcID.VORKATH_8060:
			case NpcID.VORKATH_8061:
			{
				final WorldPoint bossLocation = npc.getWorldLocation();
				int x = bossLocation.getX() + 3;
				int y = bossLocation.getY() + 3;
				if (playerLocationLastTick.getX() < x)
				{
					x -= 4;
				}
				else if (playerLocationLastTick.getX() > x)
				{
					x += 4;
				}
				if (playerLocationLastTick.getY() < y)
				{
					y -= 4;
				}
				else if (playerLocationLastTick.getY() > y)
				{
					y += 4;
				}
				return Collections.singletonList(new WorldArea(x, y, 1, 1, bossLocation.getPlane()));
			}
			case NpcID.NEX:
			case NpcID.NEX_11279:
			case NpcID.NEX_11280:
			case NpcID.NEX_11281:
			case NpcID.NEX_11282:
			{
				// Nex loot is under the player, or under nex
				LocalPoint localPoint = LocalPoint.fromWorld(client, playerLocationLastTick);
				if (localPoint != null)
				{
					int x = localPoint.getSceneX();
					int y = localPoint.getSceneY();
					final int packed = x << 8 | y;
					if (itemSpawns.containsKey(packed))
					{
						return Collections.singletonList(playerLocationLastTick.toWorldArea());
					}
				}
				break;
			}
			case NpcID.VETION_6612:
			case NpcID.CALLISTO:
			case NpcID.CALLISTO_6609:
			case NpcID.VENENATIS:
			case NpcID.VENENATIS_6610:
			case NpcID.CALVARION_11994:
			case NpcID.ARTIO:
			case NpcID.SPINDEL:
				// Bones are dropped under the center of the boss and loot is dropped under the player
				return ImmutableList.of(npc.getWorldArea(), playerLocationLastTick.toWorldArea());
			case NpcID.DUKE_SUCELLUS_12192:
			case NpcID.DUKE_SUCELLUS_12196:
			{
				final WorldPoint bossLocation = npc.getWorldLocation();
				final int x = bossLocation.getX() + npc.getComposition().getSize() / 2;
				final int y = bossLocation.getY() - 1;

				return List.of(new WorldPoint(x, y, bossLocation.getPlane()).toWorldArea());
			}
			case NpcID.VARDORVIS:
			case NpcID.VARDORVIS_12224:
			{
				final WorldArea bossArea = npc.getWorldArea();
				return List.of(new WorldArea(bossArea.getX() - 2, bossArea.getY() - 2, bossArea.getWidth() + 4, bossArea.getHeight() + 4, bossArea.getPlane()));
			}
			case NpcID.THE_LEVIATHAN:
			case NpcID.THE_LEVIATHAN_12215:
			{
				final WorldArea bossArea = npc.getWorldArea();
				final int expand = 8;
				final WorldArea expandedArea = new WorldArea(bossArea.getX() - expand, bossArea.getY() - expand, bossArea.getWidth() + expand * 2, bossArea.getHeight() + expand * 2, bossArea.getPlane());
				return List.of(expandedArea);
			}
			case NpcID.HOLE_IN_THE_WALL:
			{
				final WorldArea bossArea = npc.getWorldArea();
				return List.of(new WorldArea(bossArea.getX() - 1, bossArea.getY() - 1, 3, 3, bossArea.getPlane()));
			}
		}

		return Collections.singletonList(npc.getWorldArea());
	}

	private WorldPoint getAdjacentSquareLootTile(NPC npc)
	{
		final NPCComposition composition = npc.getComposition();
		final WorldPoint worldLocation = npc.getWorldLocation();
		int x = worldLocation.getX();
		int y = worldLocation.getY();

		if (playerLocationLastTick.getX() < x)
		{
			x -= 1;
		}
		else
		{
			x += Math.min(playerLocationLastTick.getX() - x, composition.getSize());
		}

		if (playerLocationLastTick.getY() < y)
		{
			y -= 1;
		}
		else
		{
			y += Math.min(playerLocationLastTick.getY() - y, composition.getSize());
		}

		return new WorldPoint(x, y, worldLocation.getPlane());
	}

	/**
	 * Get the list of items present at the provided WorldPoint that spawned this tick.
	 *
	 * @param worldPoint the location in question
	 * @return the list of item stacks
	 */
	public Collection<ItemStack> getItemSpawns(WorldPoint worldPoint)
	{
		LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);
		if (localPoint == null)
		{
			return Collections.emptyList();
		}

		final int sceneX = localPoint.getSceneX();
		final int sceneY = localPoint.getSceneY();
		final int packed = sceneX << 8 | sceneY;
		final List<ItemStack> itemStacks = itemSpawns.get(packed);
		return Collections.unmodifiableList(itemStacks);
	}

	private void clearDelayedLootNpc()
	{
		delayedLootNpc = null;
		delayedLootTick = 0;
		delayedLootAreas = null;
	}
}
