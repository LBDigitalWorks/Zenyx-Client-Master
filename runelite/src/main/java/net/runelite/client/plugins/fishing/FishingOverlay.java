/*
 * Copyright (c) 2017, Seth <Sethtroll3@gmail.com>
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
package net.runelite.client.plugins.fishing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.game.FishingSpot;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class FishingOverlay extends Overlay
{
	private final FishingPlugin plugin;
	private final FishingConfig config;
	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	private FishingOverlay(FishingPlugin plugin, FishingConfig config)
	{
		setPosition(OverlayPosition.TOP_LEFT);
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		// Always show debug info if enabled
		if (config.showDebug())
		{
			panelComponent.getChildren().clear();

			panelComponent.getChildren().add(TitleComponent.builder()
				.text("Fishing Debug")
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Spots detected:")
				.right(Integer.toString(plugin.getFishingSpots().size()))
				.build());

			// List first few detected spots
			int count = 0;
			for (var npc : plugin.getFishingSpots())
			{
				if (count >= 3) break;
				String name = npc.getName() != null ? npc.getName() : "Unknown";
				panelComponent.getChildren().add(LineComponent.builder()
					.left("ID " + npc.getId() + ":")
					.right(name)
					.build());
				count++;
			}

			return panelComponent.render(graphics);
		}

		if (!config.showFishingStats())
		{
			return null;
		}

		FishingSession session = plugin.getSession();
		if (session.getLastFishCaughtTime() == null)
		{
			return null;
		}

		panelComponent.getChildren().clear();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Fishing")
			.build());

		FishingSpot spot = session.getLastFishingSpot();
		if (spot != null)
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Spot:")
				.right(spot.getName())
				.build());
		}

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Caught:")
			.right(Integer.toString(session.getFishCaught()))
			.build());

		return panelComponent.render(graphics);
	}
}
