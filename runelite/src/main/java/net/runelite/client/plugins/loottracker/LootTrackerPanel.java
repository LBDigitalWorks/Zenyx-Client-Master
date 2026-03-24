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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.QuantityFormatter;

/**
 * Main side panel for the Loot Tracker plugin.
 * Shows overall stats, a collapse-all toggle, and a list of collapsible boxes per kill source.
 */
class LootTrackerPanel extends PluginPanel
{
	private final ItemManager itemManager;
	private final LootTrackerPlugin plugin;
	private final LootTrackerConfig config;

	private final List<LootTrackerBox> boxes = new ArrayList<>();

	private final JPanel boxPanel = new JPanel();
	private final PluginErrorPanel errorPanel = new PluginErrorPanel();

	// Overall stats panel (Zenyte-style)
	private final JPanel overallPanel = new JPanel(new BorderLayout());
	private final JLabel overallKillsLabel = new JLabel();
	private final JLabel overallGpLabel = new JLabel();

	// Whether all boxes are currently collapsed
	private boolean allCollapsed = false;
	private final JButton collapseAllButton;

	LootTrackerPanel(ItemManager itemManager, LootTrackerPlugin plugin, LootTrackerConfig config)
	{
		this.itemManager = itemManager;
		this.plugin = plugin;
		this.config = config;

		setLayout(new BorderLayout(0, 0));
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		// --- Top header ---
		JPanel headerPanel = new JPanel(new BorderLayout(5, 0));
		headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel titleLabel = new JLabel("Loot Tracker");
		titleLabel.setFont(FontManager.getRunescapeBoldFont());
		titleLabel.setForeground(Color.WHITE);

		JPanel headerButtons = new JPanel(new BorderLayout(4, 0));
		headerButtons.setOpaque(false);

		collapseAllButton = new JButton("\u25BC");
		collapseAllButton.setFont(FontManager.getRunescapeSmallFont());
		collapseAllButton.setForeground(Color.WHITE);
		collapseAllButton.setBackground(ColorScheme.MEDIUM_GRAY_COLOR);
		collapseAllButton.setBorder(new EmptyBorder(4, 6, 4, 6));
		collapseAllButton.setFocusPainted(false);
		collapseAllButton.setToolTipText("Collapse all");
		collapseAllButton.addActionListener(e -> toggleCollapseAll());

		JButton clearButton = new JButton("Clear");
		clearButton.setFont(FontManager.getRunescapeSmallFont());
		clearButton.setForeground(Color.WHITE);
		clearButton.setBackground(ColorScheme.MEDIUM_GRAY_COLOR);
		clearButton.setBorder(new EmptyBorder(4, 8, 4, 8));
		clearButton.setFocusPainted(false);
		clearButton.addActionListener(e -> plugin.clearLoot());

		headerButtons.add(collapseAllButton, BorderLayout.WEST);
		headerButtons.add(clearButton, BorderLayout.EAST);

		headerPanel.add(titleLabel, BorderLayout.WEST);
		headerPanel.add(headerButtons, BorderLayout.EAST);

		// --- Overall stats panel ---
		overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		overallPanel.setBorder(new EmptyBorder(6, 10, 6, 10));
		overallPanel.setVisible(false);

		overallKillsLabel.setFont(FontManager.getRunescapeSmallFont());
		overallKillsLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

		overallGpLabel.setFont(FontManager.getRunescapeSmallFont());
		overallGpLabel.setForeground(ColorScheme.GRAND_EXCHANGE_PRICE);

		overallPanel.add(overallKillsLabel, BorderLayout.WEST);
		overallPanel.add(overallGpLabel, BorderLayout.EAST);

		// Right-click "Reset All" on the overall stats panel
		overallPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
				{
					plugin.clearLoot();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				overallPanel.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});

		// --- North area: header + overall stats ---
		JPanel northPanel = new JPanel(new BorderLayout(0, 1));
		northPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
		northPanel.add(headerPanel, BorderLayout.NORTH);
		northPanel.add(overallPanel, BorderLayout.SOUTH);

		// --- Box container ---
		boxPanel.setLayout(new GridBagLayout());
		boxPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
		boxPanel.setVisible(false);

		// --- Empty state ---
		errorPanel.setContent("No loot tracked", "Kill monsters to track your loot drops.");

		JPanel contentWrapper = new JPanel(new BorderLayout());
		contentWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
		contentWrapper.add(boxPanel, BorderLayout.NORTH);
		contentWrapper.add(errorPanel, BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);
		add(contentWrapper, BorderLayout.CENTER);
	}

	/**
	 * Adds or updates the loot box for the given record. Must be called from EDT.
	 */
	void updateRecord(LootTrackerRecord record)
	{
		assert SwingUtilities.isEventDispatchThread();

		LootTrackerBox box = findBox(record.getTitle());
		if (box == null)
		{
			box = new LootTrackerBox(itemManager, record.getTitle(), config);
			if (allCollapsed)
			{
				box.setCollapsed(true);
			}
			boxes.add(0, box);
		}
		else
		{
			boxes.remove(box);
			boxes.add(0, box);
		}

		box.updateRecord(record);
		rebuildBoxPanel();
		updateOverall();

		errorPanel.setVisible(false);
		boxPanel.setVisible(true);
		overallPanel.setVisible(true);

		revalidate();
		repaint();
	}

	void setRecords(List<LootTrackerRecord> records)
	{
		assert SwingUtilities.isEventDispatchThread();

		boxes.clear();
		if (records != null)
		{
			for (LootTrackerRecord record : records)
			{
				if (record == null)
				{
					continue;
				}

				final LootTrackerBox box = new LootTrackerBox(itemManager, record.getTitle(), config);
				if (allCollapsed)
				{
					box.setCollapsed(true);
				}
				box.updateRecord(record);
				boxes.add(box);
			}
		}

		rebuildBoxPanel();
		updateOverall();

		final boolean hasData = !boxes.isEmpty();
		boxPanel.setVisible(hasData);
		overallPanel.setVisible(hasData);
		errorPanel.setVisible(!hasData);

		revalidate();
		repaint();
	}

	/**
	 * Removes all tracked loot and resets the panel to empty state.
	 */
	void clearAll()
	{
		assert SwingUtilities.isEventDispatchThread();

		boxes.clear();
		allCollapsed = false;
		collapseAllButton.setText("\u25BC");
		collapseAllButton.setToolTipText("Collapse all");

		boxPanel.removeAll();
		boxPanel.setVisible(false);
		overallPanel.setVisible(false);
		errorPanel.setVisible(true);

		revalidate();
		repaint();
	}

	/**
	 * Recomputes and displays the overall kill count and total GP value across all boxes.
	 */
	private void updateOverall()
	{
		int totalKills = 0;
		long totalGp = 0;

		for (LootTrackerBox box : boxes)
		{
			totalKills += box.getKills();
			totalGp += box.getTotalValue();
		}

		overallKillsLabel.setText(totalKills + (totalKills == 1 ? " kill" : " kills"));

		if (config.showPrices() && totalGp > 0)
		{
			overallGpLabel.setText(QuantityFormatter.quantityToStackSize(totalGp) + " gp");
			overallGpLabel.setVisible(true);
		}
		else
		{
			overallGpLabel.setVisible(false);
		}
	}

	/**
	 * Toggles the collapsed state of all boxes simultaneously.
	 */
	private void toggleCollapseAll()
	{
		allCollapsed = !allCollapsed;

		for (LootTrackerBox box : boxes)
		{
			box.setCollapsed(allCollapsed);
		}

		if (allCollapsed)
		{
			collapseAllButton.setText("\u25B2");
			collapseAllButton.setToolTipText("Expand all");
		}
		else
		{
			collapseAllButton.setText("\u25BC");
			collapseAllButton.setToolTipText("Collapse all");
		}

		revalidate();
		repaint();
	}

	private LootTrackerBox findBox(String id)
	{
		for (LootTrackerBox box : boxes)
		{
			if (box.getId().equals(id))
			{
				return box;
			}
		}
		return null;
	}

	private void rebuildBoxPanel()
	{
		boxPanel.removeAll();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		for (LootTrackerBox box : boxes)
		{
			boxPanel.add(box, c);
			c.gridy++;
		}

		boxPanel.revalidate();
		boxPanel.repaint();
	}
}
