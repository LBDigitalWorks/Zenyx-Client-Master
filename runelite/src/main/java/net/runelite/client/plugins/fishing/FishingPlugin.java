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

import com.google.inject.Inject;
import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.FishingSpot;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Fishing",
	description = "Show fishing stats and mark fishing spots",
	tags = {"overlay", "skilling"}
)
public class FishingPlugin extends Plugin
{
	private static final String FISHING_CATCH_MESSAGE = "You catch ";
	private static final String FISHING_FAIL_MESSAGE = "You fail to catch";

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private FishingConfig config;

	@Inject
	private FishingSpotOverlay spotOverlay;

	@Inject
	private FishingSpotMinimapOverlay minimapOverlay;

	@Inject
	private FishingOverlay fishingOverlay;

	@Getter(AccessLevel.PACKAGE)
	private final FishingSession session = new FishingSession();

	@Getter(AccessLevel.PACKAGE)
	private final List<NPC> fishingSpots = new ArrayList<>();

	@Getter(AccessLevel.PACKAGE)
	private FishingSpot currentSpot;

	@Provides
	FishingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FishingConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(spotOverlay);
		overlayManager.add(minimapOverlay);
		overlayManager.add(fishingOverlay);

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			List<NPC> npcs = client.getNpcs();
			if (npcs != null)
			{
				for (NPC npc : npcs)
				{
					if (npc != null && FishingSpot.findSpot(npc.getId()) != null)
					{
						fishingSpots.add(npc);
					}
				}
				inverseSortSpotDistanceFromPlayer();
			}
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(spotOverlay);
		overlayManager.remove(minimapOverlay);
		overlayManager.remove(fishingOverlay);
		fishingSpots.clear();
		currentSpot = null;
		session.reset();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			fishingSpots.clear();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		inverseSortSpotDistanceFromPlayer();

		if (session.getLastFishCaughtTime() != null)
		{
			Duration timeout = Duration.ofMinutes(config.statTimeout());
			Duration timeSinceLastCatch = Duration.between(session.getLastFishCaughtTime(), Instant.now());
			if (timeSinceLastCatch.compareTo(timeout) >= 0)
			{
				session.reset();
				currentSpot = null;
			}
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.SPAM && event.getType() != ChatMessageType.GAMEMESSAGE)
		{
			return;
		}

		String message = event.getMessage();
		if (message.startsWith(FISHING_CATCH_MESSAGE) || message.startsWith(FISHING_FAIL_MESSAGE))
		{
			session.incrementFishCaught();
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		final NPC npc = event.getNpc();

		if (FishingSpot.findSpot(npc.getId()) == null)
		{
			return;
		}

		fishingSpots.add(npc);
		inverseSortSpotDistanceFromPlayer();
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		fishingSpots.remove(event.getNpc());
	}

	void setCurrentSpot(FishingSpot spot)
	{
		currentSpot = spot;
		session.setLastFishingSpot(spot);
	}

	private void inverseSortSpotDistanceFromPlayer()
	{
		if (fishingSpots.isEmpty())
		{
			return;
		}

		final LocalPoint cameraPoint = new LocalPoint(client.getCameraX(), client.getCameraY());
		fishingSpots.sort(
			Comparator.comparingInt(
				(NPC npc) -> -npc.getLocalLocation().distanceTo(cameraPoint))
				.thenComparing(NPC::getLocalLocation, Comparator.comparingInt(LocalPoint::getX)
					.thenComparingInt(LocalPoint::getY))
				.thenComparingInt(NPC::getId)
		);
	}
}
