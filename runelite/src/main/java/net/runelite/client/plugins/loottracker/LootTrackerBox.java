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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.QuantityFormatter;

/**
 * A collapsible panel showing aggregated loot from a single source (NPC or player).
 */
class LootTrackerBox extends JPanel
{
	private static final int ITEMS_PER_ROW = 5;
	private static final int ITEM_SIZE = 36;
	private static final Dimension ITEM_PREFERRED_SIZE = new Dimension(ITEM_SIZE, ITEM_SIZE);

	private final ItemManager itemManager;
	private final String id;
	private final LootTrackerConfig config;

	private final JPanel headerPanel = new JPanel(new BorderLayout());
	private final JLabel titleLabel = new JLabel();
	private final JLabel subTitleLabel = new JLabel();
	private final JLabel priceLabel = new JLabel();
	private final JPanel itemContainer = new JPanel();

	private int kills = 0;
	private final List<LootTrackerItem> items = new ArrayList<>();

	@Getter
	private boolean collapsed = false;

	LootTrackerBox(ItemManager itemManager, String id, LootTrackerConfig config)
	{
		this.itemManager = itemManager;
		this.id = id;
		this.config = config;

		setLayout(new BorderLayout(0, 1));
		setBorder(new EmptyBorder(5, 0, 0, 0));
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		// --- Header ---
		headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		headerPanel.setBorder(new EmptyBorder(7, 7, 7, 7));

		titleLabel.setText(id);
		titleLabel.setFont(FontManager.getRunescapeSmallFont());
		titleLabel.setForeground(Color.WHITE);

		subTitleLabel.setFont(FontManager.getRunescapeSmallFont());
		subTitleLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

		JPanel leftPanel = new JPanel(new BorderLayout(4, 0));
		leftPanel.setOpaque(false);
		leftPanel.add(titleLabel, BorderLayout.WEST);
		leftPanel.add(subTitleLabel, BorderLayout.CENTER);

		priceLabel.setFont(FontManager.getRunescapeSmallFont());
		priceLabel.setForeground(ColorScheme.GRAND_EXCHANGE_PRICE);
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		headerPanel.add(leftPanel, BorderLayout.WEST);
		headerPanel.add(priceLabel, BorderLayout.EAST);

		headerPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				setCollapsed(!collapsed);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				headerPanel.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});

		// --- Item container ---
		itemContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);
		itemContainer.setBorder(new EmptyBorder(4, 4, 4, 4));

		add(headerPanel, BorderLayout.NORTH);
		add(itemContainer, BorderLayout.CENTER);
	}

	String getId()
	{
		return id;
	}

	/**
	 * Sets the collapsed state of this box, showing or hiding the item container accordingly.
	 */
	void setCollapsed(boolean collapsed)
	{
		this.collapsed = collapsed;
		itemContainer.setVisible(!collapsed);
		revalidate();
	}

	/**
	 * Returns the total value of all items in this box.
	 */
	long getTotalValue()
	{
		return items.stream().mapToLong(i -> i.getPrice() * i.getQuantity()).sum();
	}

	/**
	 * Returns the kill count for this box.
	 */
	int getKills()
	{
		return kills;
	}

	/**
	 * Updates this box with the latest data from a {@link LootTrackerRecord}.
	 */
	void updateRecord(LootTrackerRecord record)
	{
		kills = record.getKills();
		items.clear();
		items.addAll(record.getItems());
		items.sort(
			Comparator
				.comparingLong((LootTrackerItem i) -> i.getPrice() * i.getQuantity())
				.reversed()
				.thenComparing(LootTrackerItem::getName, String.CASE_INSENSITIVE_ORDER)
		);
		rebuild();
	}

	private void rebuild()
	{
		// Kill count subtitle
		subTitleLabel.setText(kills > 1 ? " x" + kills : "");

		// Total price label
		if (config.showPrices())
		{
			long total = items.stream().mapToLong(i -> i.getPrice() * i.getQuantity()).sum();
			if (total > 0)
			{
				priceLabel.setText(QuantityFormatter.quantityToStackSize(total) + " gp");
				priceLabel.setVisible(true);
			}
			else
			{
				priceLabel.setVisible(false);
			}
		}
		else
		{
			priceLabel.setVisible(false);
		}

		// Rebuild item grid
		itemContainer.removeAll();

		if (items.isEmpty())
		{
			revalidate();
			repaint();
			return;
		}

		int numItems = items.size();
		int rows = (numItems + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
		itemContainer.setLayout(new GridLayout(rows, ITEMS_PER_ROW, 1, 1));

		for (LootTrackerItem item : items)
		{
			JPanel slot = buildItemSlot(item);
			itemContainer.add(slot);
		}

		// Pad remaining cells in last row
		int remainder = numItems % ITEMS_PER_ROW;
		if (remainder != 0)
		{
			for (int i = 0; i < ITEMS_PER_ROW - remainder; i++)
			{
				JPanel filler = new JPanel();
				filler.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				filler.setPreferredSize(ITEM_PREFERRED_SIZE);
				itemContainer.add(filler);
			}
		}

		revalidate();
		repaint();
	}

	private JPanel buildItemSlot(LootTrackerItem item)
	{
		JPanel slot = new JPanel(new BorderLayout());
		slot.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		slot.setPreferredSize(ITEM_PREFERRED_SIZE);

		JLabel imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);

		AsyncBufferedImage img = itemManager.getImage(item.getId(), item.getQuantity(), item.getQuantity() > 1);
		if (img != null)
		{
			img.addTo(imageLabel);
		}

		String tooltip = "<html>" + item.getName() + " x " + QuantityFormatter.formatNumber(item.getQuantity());
		if (config.showPrices() && item.getPrice() > 0)
		{
			tooltip += "<br/>Value: " + QuantityFormatter.formatNumber(item.getPrice() * item.getQuantity()) + " gp";
		}
		tooltip += "</html>";
		imageLabel.setToolTipText(tooltip);

		slot.add(imageLabel);
		return slot;
	}
}
