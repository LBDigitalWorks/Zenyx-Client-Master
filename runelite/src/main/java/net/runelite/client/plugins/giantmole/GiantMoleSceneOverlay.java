/*
 * Copyright (c) 2026
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
package net.runelite.client.plugins.giantmole;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class GiantMoleSceneOverlay extends Overlay
{

	private final Client client;
	private final GiantMolePlugin plugin;

	@Inject
	public GiantMoleSceneOverlay(Client client, GiantMolePlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		NPC npc = plugin.getActiveMole();
		if (npc == null || !GiantMoleOverlayUtil.isFlashVisible() || GiantMoleOverlayUtil.ARROW_IMAGE == null)
		{
			return null;
		}

		LocalPoint localPoint = npc.getLocalLocation();
		if (localPoint == null)
		{
			return null;
		}

		// Project the NPC's head position to 2D screen space.
		// Using only the head height (not an elevated 3D offset) ensures the projected
		// 2D point stays locked to the mole's head regardless of camera yaw/pitch.
		// The arrow is then offset upward in 2D so its tip hovers just above the head.
		int headZOffset = npc.getLogicalHeight();
		net.runelite.api.Point headPoint = Perspective.localToCanvas(client, localPoint, client.getPlane(), headZOffset);
		if (headPoint == null || !isInViewport(headPoint))
		{
			return null;
		}

		java.awt.image.BufferedImage arrowImage = GiantMoleOverlayUtil.ARROW_IMAGE;
		int imageX = headPoint.getX() - arrowImage.getWidth() / 2;
		int imageY = headPoint.getY() - arrowImage.getHeight() - 10;
		GiantMoleOverlayUtil.drawArrowImage(graphics, new net.runelite.api.Point(imageX, imageY));
		return null;
	}

	private boolean isInViewport(net.runelite.api.Point point)
	{
		int x = point.getX();
		int y = point.getY();
		int viewportX = client.getViewportXOffset();
		int viewportY = client.getViewportYOffset();
		int viewportWidth = client.getViewportWidth();
		int viewportHeight = client.getViewportHeight();
		return x >= viewportX && x <= viewportX + viewportWidth
			&& y >= viewportY && y <= viewportY + viewportHeight;
	}
}
