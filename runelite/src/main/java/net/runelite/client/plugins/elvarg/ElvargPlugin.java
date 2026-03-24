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

import com.google.inject.Inject;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.time.Instant;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

@PluginDescriptor(
	name = "Elvarg",
	description = "Elvarg Rebirth server information and links",
	tags = {"server", "info", "elvarg", "discord", "rebirth"}
)
public class ElvargPlugin extends Plugin
{
	static final String CONFIG_GROUP_KEY = "elvarg";

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ElvargOverlay overlay;

	private ElvargPanel panel;
	private NavigationButton navButton;

	@Getter
	private Instant sessionStartTime;

	@Provides
	ElvargConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ElvargConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		panel = injector.getInstance(ElvargPanel.class);
		panel.init();

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "elvarg_icon.png");

		navButton = NavigationButton.builder()
			.tooltip("Elvarg Rebirth")
			.icon(icon)
			.priority(5)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
		overlayManager.add(overlay);

		// Initialize session time if already logged in
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			sessionStartTime = Instant.now();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		overlayManager.remove(overlay);
		panel.deinit();
		panel = null;
		navButton = null;
		sessionStartTime = null;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			// Start session timer when logging in
			if (sessionStartTime == null)
			{
				sessionStartTime = Instant.now();
			}
		}
		else if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			// Reset session timer when returning to login screen
			sessionStartTime = null;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(CONFIG_GROUP_KEY))
		{
			panel.updateLinks();
		}
	}

	public boolean isLoggedIn()
	{
		return client.getGameState() == GameState.LOGGED_IN;
	}
}
