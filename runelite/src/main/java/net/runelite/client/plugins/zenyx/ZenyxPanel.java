/*
 * Copyright (c) 2024, Zenyx
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
package net.runelite.client.plugins.zenyx;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.Instant;
import javax.annotation.Nullable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

public class ZenyxPanel extends PluginPanel
{
	private static final ImageIcon ARROW_RIGHT_ICON;
	private static final ImageIcon DISCORD_ICON;
	private static final ImageIcon WEBSITE_ICON;
	private static final ImageIcon VOTE_ICON;
	private static final ImageIcon STORE_ICON;
	private static final ImageIcon WIKI_ICON;

	private static final Color STATUS_ONLINE = new Color(0, 200, 83);
	private static final Color STATUS_OFFLINE = new Color(200, 50, 50);

	private JPanel linksContainer;
	private JLabel playerCountLabel;
	private JLabel sessionTimeLabel;
	private JLabel serverStatusLabel;

	@Inject
	@Nullable
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private EventBus eventBus;

	@Inject
	private ZenyxConfig config;

	@Inject
	private ZenyxPlugin plugin;

	static
	{
		ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/util/arrow_right.png"));
		DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/net/runelite/client/plugins/info/discord_icon.png"));
		WEBSITE_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/net/runelite/client/plugins/info/github_icon.png"));
		VOTE_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/net/runelite/client/plugins/info/patreon_icon.png"));
		STORE_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/net/runelite/client/plugins/info/patreon_icon.png"));
		WIKI_ICON = new ImageIcon(ImageUtil.loadImageResource(ZenyxPanel.class, "/net/runelite/client/plugins/info/wiki_icon.png"));
	}

	void init()
	{
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));

		// Header panel with server name
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headerPanel.setLayout(new GridLayout(0, 1));

		JLabel titleLabel = new JLabel("Zenyx");
		titleLabel.setFont(FontManager.getRunescapeBoldFont());
		titleLabel.setForeground(new Color(138, 43, 226)); // Purple color to match logo
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel subtitleLabel = new JLabel("Private Server");
		subtitleLabel.setFont(FontManager.getRunescapeSmallFont());
		subtitleLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Version info
		JLabel versionLabel = new JLabel(htmlLabel("Version: ", "1.0.0"));
		versionLabel.setFont(FontManager.getRunescapeSmallFont());
		versionLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Server status label
		serverStatusLabel = new JLabel();
		serverStatusLabel.setFont(FontManager.getRunescapeSmallFont());
		serverStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateServerStatus();

		// Session time label
		sessionTimeLabel = new JLabel(htmlLabel("Session: ", "00:00:00"));
		sessionTimeLabel.setFont(FontManager.getRunescapeSmallFont());
		sessionTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Player count label
		playerCountLabel = new JLabel(htmlLabel("Players nearby: ", "0"));
		playerCountLabel.setFont(FontManager.getRunescapeSmallFont());
		playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		headerPanel.add(titleLabel);
		headerPanel.add(subtitleLabel);
		headerPanel.add(versionLabel);
		headerPanel.add(serverStatusLabel);
		headerPanel.add(sessionTimeLabel);
		headerPanel.add(playerCountLabel);

		// Links container
		linksContainer = new JPanel();
		linksContainer.setBorder(new EmptyBorder(10, 0, 0, 0));
		linksContainer.setLayout(new GridLayout(0, 1, 0, 10));
		linksContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);

		buildLinks();

		add(headerPanel, BorderLayout.NORTH);
		add(linksContainer, BorderLayout.CENTER);

		eventBus.register(this);

		// Start updating player count
		updatePlayerCount();
	}

	void deinit()
	{
		eventBus.unregister(this);
	}

	void updateLinks()
	{
		linksContainer.removeAll();
		buildLinks();
		linksContainer.revalidate();
		linksContainer.repaint();
	}

	private void buildLinks()
	{
		linksContainer.add(buildLinkPanel(DISCORD_ICON, "Join our", "Discord Server", config.discordUrl()));
		linksContainer.add(buildLinkPanel(WEBSITE_ICON, "Visit our", "Website", config.websiteUrl()));
		linksContainer.add(buildLinkPanel(VOTE_ICON, "Vote for us", "and earn rewards", config.voteUrl()));
		linksContainer.add(buildLinkPanel(STORE_ICON, "Support us at", "our Store", config.storeUrl()));
		linksContainer.add(buildLinkPanel(WIKI_ICON, "Browse our", "Wiki & Guides", config.wikiUrl()));
	}

	private void updatePlayerCount()
	{
		if (client == null)
		{
			return;
		}

		clientThread.invokeLater(() ->
		{
			int count = 0;
			for (Player player : client.getPlayers())
			{
				if (player != null)
				{
					count++;
				}
			}
			final int playerCount = count;
			playerCountLabel.setText(htmlLabel("Players nearby: ", String.valueOf(playerCount)));
		});
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(ZenyxPlugin.CONFIG_GROUP_KEY))
		{
			updateLinks();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		updatePlayerCount();
		updateSessionTime();
		updateServerStatus();
	}

	private void updateSessionTime()
	{
		Instant sessionStart = plugin.getSessionStartTime();
		String sessionTime = "00:00:00";
		if (sessionStart != null)
		{
			Duration duration = Duration.between(sessionStart, Instant.now());
			sessionTime = formatDuration(duration);
		}
		sessionTimeLabel.setText(htmlLabel("Session: ", sessionTime));
	}

	private void updateServerStatus()
	{
		boolean isOnline = plugin.isLoggedIn();
		String statusHtml = "<html><body style='color:#a5a5a5'>Status: <span style='color:" +
			(isOnline ? "#00c853" : "#c83232") + "'>" +
			(isOnline ? "Online" : "Offline") + "</span></body></html>";
		serverStatusLabel.setText(statusHtml);
	}

	private static String formatDuration(Duration duration)
	{
		long seconds = duration.getSeconds();
		long hours = seconds / 3600;
		long minutes = (seconds % 3600) / 60;
		long secs = seconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, secs);
	}

	private static JPanel buildLinkPanel(ImageIcon icon, String topText, String bottomText, String url)
	{
		return buildLinkPanel(icon, topText, bottomText, () -> LinkBrowser.browse(url));
	}

	private static JPanel buildLinkPanel(ImageIcon icon, String topText, String bottomText, Runnable callback)
	{
		JPanel container = new JPanel();
		container.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		final Color hoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR;
		final Color pressedColor = ColorScheme.DARKER_GRAY_COLOR.brighter();

		JLabel iconLabel = new JLabel(icon);
		container.add(iconLabel, BorderLayout.WEST);

		JPanel textContainer = new JPanel();
		textContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		textContainer.setLayout(new GridLayout(2, 1));
		textContainer.setBorder(new EmptyBorder(5, 10, 5, 10));

		container.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				container.setBackground(pressedColor);
				textContainer.setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				callback.run();
				container.setBackground(hoverColor);
				textContainer.setBackground(hoverColor);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				container.setBackground(hoverColor);
				textContainer.setBackground(hoverColor);
				container.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				container.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				textContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				container.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		JLabel topLine = new JLabel(topText);
		topLine.setForeground(Color.WHITE);
		topLine.setFont(FontManager.getRunescapeSmallFont());

		JLabel bottomLine = new JLabel(bottomText);
		bottomLine.setForeground(Color.WHITE);
		bottomLine.setFont(FontManager.getRunescapeSmallFont());

		textContainer.add(topLine);
		textContainer.add(bottomLine);

		container.add(textContainer, BorderLayout.CENTER);

		JLabel arrowLabel = new JLabel(ARROW_RIGHT_ICON);
		container.add(arrowLabel, BorderLayout.EAST);

		return container;
	}

	private static String htmlLabel(String key, String value)
	{
		return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
	}
}

