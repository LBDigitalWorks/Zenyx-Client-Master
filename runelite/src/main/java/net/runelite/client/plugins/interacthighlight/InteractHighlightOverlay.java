/*
 * Copyright (c) 2018, Kamiel
 * Copyright (c) 2021, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.interacthighlight;

import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GroundObject;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class InteractHighlightOverlay extends Overlay
{
	private static final int INTERACT_FADE_TICKS = 10;

	private final Client client;
	private final InteractHighlightPlugin plugin;
	private final InteractHighlightConfig config;
	private final ModelOutlineRenderer modelOutlineRenderer;

	@Inject
	public InteractHighlightOverlay(Client client, InteractHighlightPlugin plugin,
									InteractHighlightConfig config, ModelOutlineRenderer modelOutlineRenderer)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.modelOutlineRenderer = modelOutlineRenderer;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		renderMouseover();
		renderTarget();
		return null;
	}

	private void renderMouseover()
	{
		MenuEntry[] menuEntries = client.getMenuEntries();
		if (menuEntries.length == 0)
		{
			return;
		}

		// Get the top menu entry (the one that would be left-clicked)
		MenuEntry topEntry = menuEntries[menuEntries.length - 1];
		MenuAction action = topEntry.getType();

		if (action == null)
		{
			return;
		}

		switch (action)
		{
			case GAME_OBJECT_FIRST_OPTION:
			case GAME_OBJECT_SECOND_OPTION:
			case GAME_OBJECT_THIRD_OPTION:
			case GAME_OBJECT_FOURTH_OPTION:
			case GAME_OBJECT_FIFTH_OPTION:
			case ITEM_USE_ON_GAME_OBJECT:
			case SPELL_CAST_ON_GAME_OBJECT:
			{
				if (!config.objectShowHover())
				{
					return;
				}

				int x = topEntry.getParam0();
				int y = topEntry.getParam1();
				int id = topEntry.getIdentifier();
				TileObject obj = findTileObject(x, y, id);
				if (obj != null && obj != plugin.getInteractedObject())
				{
					modelOutlineRenderer.drawOutline(obj, config.borderWidth(),
						config.objectHoverHighlightColor(), config.outlineFeather());
				}
				break;
			}
			case NPC_FIRST_OPTION:
			case NPC_SECOND_OPTION:
			case NPC_THIRD_OPTION:
			case NPC_FOURTH_OPTION:
			case NPC_FIFTH_OPTION:
			case ITEM_USE_ON_NPC:
			case SPELL_CAST_ON_NPC:
			{
				if (!config.npcShowHover())
				{
					return;
				}

				// Get NPC from cached NPCs array using identifier as index
				int index = topEntry.getIdentifier();
				NPC npc = findNpcByIndex(index);
				if (npc != null && npc != plugin.getInteractedNpc())
				{
					String option = topEntry.getOption();
					boolean isAttack = option != null && option.equalsIgnoreCase("Attack");
					Color color = isAttack ? config.npcAttackHoverHighlightColor() : config.npcHoverHighlightColor();
					modelOutlineRenderer.drawOutline(npc, config.borderWidth(), color, config.outlineFeather());
				}
				break;
			}
		}
	}

	private void renderTarget()
	{
		TileObject interactedObject = plugin.getInteractedObject();
		NPC interactedNpc = plugin.getInteractedNpc();

		if (interactedObject != null && config.objectShowInteract())
		{
			Color color = getClickColor(config.objectInteractHighlightColor());
			modelOutlineRenderer.drawOutline(interactedObject, config.borderWidth(), color, config.outlineFeather());
		}

		if (interactedNpc != null && config.npcShowInteract())
		{
			Color baseColor = plugin.isAttack() ? config.npcAttackInteractHighlightColor() : config.npcInteractHighlightColor();
			Color color = getClickColor(baseColor);
			modelOutlineRenderer.drawOutline(interactedNpc, config.borderWidth(), color, config.outlineFeather());
		}
	}

	private Color getClickColor(Color baseColor)
	{
		int ticksSinceClick = client.getTickCount() - plugin.getClickTick();

		if (ticksSinceClick < INTERACT_FADE_TICKS)
		{
			// Pulse effect - brighter at first, then fade to base color
			float progress = (float) ticksSinceClick / INTERACT_FADE_TICKS;
			int brightAlpha = Math.min(255, baseColor.getAlpha() + 50);
			int alpha = (int) (brightAlpha - (brightAlpha - baseColor.getAlpha()) * progress);
			return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
		}

		return baseColor;
	}

	private TileObject findTileObject(int x, int y, int id)
	{
		Scene scene = client.getScene();
		Tile[][][] tiles = scene.getTiles();
		int plane = client.getPlane();

		if (x < 0 || y < 0 || x >= tiles[plane].length || y >= tiles[plane][x].length)
		{
			return null;
		}

		Tile tile = tiles[plane][x][y];
		if (tile == null)
		{
			return null;
		}

		// Check GameObjects
		GameObject[] gameObjects = tile.getGameObjects();
		if (gameObjects != null)
		{
			for (GameObject gameObject : gameObjects)
			{
				if (gameObject != null && gameObject.getId() == id)
				{
					return gameObject;
				}
			}
		}

		// Check WallObject
		WallObject wallObject = tile.getWallObject();
		if (wallObject != null && wallObject.getId() == id)
		{
			return wallObject;
		}

		// Check DecorativeObject
		DecorativeObject decorativeObject = tile.getDecorativeObject();
		if (decorativeObject != null && decorativeObject.getId() == id)
		{
			return decorativeObject;
		}

		// Check GroundObject
		GroundObject groundObject = tile.getGroundObject();
		if (groundObject != null && groundObject.getId() == id)
		{
			return groundObject;
		}

		return null;
	}

	private NPC findNpcByIndex(int index)
	{
		// Use getCachedNPCs() which is an array indexed by NPC index
		NPC[] cachedNpcs = client.getCachedNPCs();
		if (cachedNpcs != null && index >= 0 && index < cachedNpcs.length)
		{
			return cachedNpcs[index];
		}
		return null;
	}
}
