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

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GroundObject;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Interact Highlight",
	description = "Highlight the object or NPC you are interacting with or hovering over",
	enabledByDefault = false,
	tags = {"highlight", "npc", "object", "outline", "interact"}
)
public class InteractHighlightPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InteractHighlightOverlay interactHighlightOverlay;

	@Getter
	private TileObject interactedObject;

	@Getter
	private NPC interactedNpc;

	@Getter
	private boolean isAttack;

	@Getter
	private int clickTick;

	@Override
	protected void startUp()
	{
		overlayManager.add(interactHighlightOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(interactHighlightOverlay);
		interactedObject = null;
		interactedNpc = null;
	}

	@Provides
	InteractHighlightConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InteractHighlightConfig.class);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		MenuAction action = event.getMenuAction();

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
				int x = event.getParam0();
				int y = event.getParam1();
				int id = event.getId();
				TileObject obj = findTileObject(x, y, id);
				if (obj != null)
				{
					interactedObject = obj;
					interactedNpc = null;
					isAttack = false;
					clickTick = client.getTickCount();
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
				// Get NPC from cached NPCs array using identifier as index
				int index = event.getId();
				NPC npc = findNpcByIndex(index);
				if (npc != null)
				{
					interactedNpc = npc;
					interactedObject = null;
					String option = event.getMenuOption();
					isAttack = option != null && option.equalsIgnoreCase("Attack");
					clickTick = client.getTickCount();
					log.debug("Interacted with NPC: {} (attack={})", npc.getName(), isAttack);
				}
				break;
			}
			case WALK:
			case CANCEL:
			{
				interactedObject = null;
				interactedNpc = null;
				break;
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null)
		{
			return;
		}

		// Clear interaction when player reaches destination or stops moving
		if (interactedObject != null || interactedNpc != null)
		{
			Actor interacting = localPlayer.getInteracting();

			// If player is interacting with the NPC we clicked, keep highlighting
			if (interactedNpc != null && interacting == interactedNpc)
			{
				return;
			}

			// If player is not moving and not interacting, clear after a delay
			if (localPlayer.getPoseAnimation() == localPlayer.getIdlePoseAnimation())
			{
				int ticksSinceClick = client.getTickCount() - clickTick;
				if (ticksSinceClick > 2)
				{
					interactedObject = null;
					interactedNpc = null;
				}
			}
		}
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
