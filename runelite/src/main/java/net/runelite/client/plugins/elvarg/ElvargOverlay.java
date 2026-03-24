/*
 * Copyright (c) 2024, Elvarg Rebirth
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
package net.runelite.client.plugins.elvarg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ElvargOverlay extends Overlay
{
	private static final Color STATUS_ONLINE = new Color(0, 200, 83);
	private static final Color STATUS_OFFLINE = new Color(200, 50, 50);

	private final Client client;
	private final ElvargConfig config;
	private final ElvargPlugin plugin;
	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	private ElvargOverlay(Client client, ElvargConfig config, ElvargPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		setPosition(OverlayPosition.TOP_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showOverlay())
		{
			return null;
		}

		panelComponent.getChildren().clear();

		// Add title
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Elvarg Rebirth")
			.color(config.overlayColor())
			.build());

		// Server status
		if (config.showServerStatus())
		{
			boolean isOnline = plugin.isLoggedIn();
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Status:")
				.right(isOnline ? "Online" : "Offline")
				.rightColor(isOnline ? STATUS_ONLINE : STATUS_OFFLINE)
				.build());
		}

		// Session timer
		if (config.showSessionTimer())
		{
			Instant sessionStart = plugin.getSessionStartTime();
			String sessionTime = "00:00:00";
			if (sessionStart != null)
			{
				Duration duration = Duration.between(sessionStart, Instant.now());
				sessionTime = formatDuration(duration);
			}
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Session:")
				.right(sessionTime)
				.rightColor(config.overlayColor())
				.build());
		}

		// Player count
		if (config.showPlayerCount())
		{
			int playerCount = 0;
			for (Player player : client.getPlayers())
			{
				if (player != null)
				{
					playerCount++;
				}
			}
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Players nearby:")
				.right(String.valueOf(playerCount))
				.rightColor(config.overlayColor())
				.build());
		}

		return panelComponent.render(graphics);
	}

	private static String formatDuration(Duration duration)
	{
		long seconds = duration.getSeconds();
		long hours = seconds / 3600;
		long minutes = (seconds % 3600) / 60;
		long secs = seconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, secs);
	}
}
