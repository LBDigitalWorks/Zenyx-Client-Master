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
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(ElvargPlugin.CONFIG_GROUP_KEY)
public interface ElvargConfig extends Config
{
	@ConfigSection(
		name = "Links",
		description = "Server link URLs",
		position = 0
	)
	String linksSection = "links";

	@ConfigSection(
		name = "Overlay",
		description = "Overlay settings",
		position = 1
	)
	String overlaySection = "overlay";

	@ConfigItem(
		keyName = "discordUrl",
		name = "Discord URL",
		description = "Discord server invite URL",
		position = 1,
		section = linksSection
	)
	default String discordUrl()
	{
		return "https://discord.gg/4EW5M8rpdm";
	}

	@ConfigItem(
		keyName = "websiteUrl",
		name = "Website URL",
		description = "Server website URL",
		position = 2,
		section = linksSection
	)
	default String websiteUrl()
	{
		return "https://elvarg.io";
	}

	@ConfigItem(
		keyName = "voteUrl",
		name = "Vote URL",
		description = "Vote page URL",
		position = 3,
		section = linksSection
	)
	default String voteUrl()
	{
		return "https://elvarg.io/vote";
	}

	@ConfigItem(
		keyName = "storeUrl",
		name = "Store URL",
		description = "Donation/store page URL",
		position = 4,
		section = linksSection
	)
	default String storeUrl()
	{
		return "https://elvarg.io/store";
	}

	@ConfigItem(
		keyName = "wikiUrl",
		name = "Wiki URL",
		description = "Wiki/guides page URL",
		position = 5,
		section = linksSection
	)
	default String wikiUrl()
	{
		return "https://elvarg.io/wiki";
	}

	@ConfigItem(
		keyName = "showOverlay",
		name = "Show Overlay",
		description = "Show the in-game overlay",
		position = 1,
		section = overlaySection
	)
	default boolean showOverlay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showSessionTimer",
		name = "Show Session Timer",
		description = "Display session play time in overlay",
		position = 2,
		section = overlaySection
	)
	default boolean showSessionTimer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPlayerCount",
		name = "Show Player Count",
		description = "Display nearby player count in overlay",
		position = 3,
		section = overlaySection
	)
	default boolean showPlayerCount()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showServerStatus",
		name = "Show Server Status",
		description = "Display server connection status in overlay",
		position = 4,
		section = overlaySection
	)
	default boolean showServerStatus()
	{
		return true;
	}

	@ConfigItem(
		keyName = "overlayColor",
		name = "Overlay Color",
		description = "Color of the overlay text",
		position = 5,
		section = overlaySection
	)
	default Color overlayColor()
	{
		return new Color(138, 43, 226); // Purple to match branding
	}
}
