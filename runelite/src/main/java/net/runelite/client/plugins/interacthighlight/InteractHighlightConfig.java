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

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

import java.awt.Color;

@ConfigGroup("interacthighlight")
public interface InteractHighlightConfig extends Config
{
	@ConfigSection(
		name = "NPCs",
		description = "NPC highlight settings",
		position = 0
	)
	String npcSection = "npcs";

	@ConfigSection(
		name = "Objects",
		description = "Object highlight settings",
		position = 1
	)
	String objectSection = "objects";

	@ConfigItem(
		keyName = "npcShowHover",
		name = "Hover",
		description = "Show outline on NPCs when hovered",
		section = npcSection,
		position = 0
	)
	default boolean npcShowHover()
	{
		return true;
	}

	@ConfigItem(
		keyName = "npcShowInteract",
		name = "Interact",
		description = "Show outline on NPCs when interacted with",
		section = npcSection,
		position = 1
	)
	default boolean npcShowInteract()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "npcHoverHighlightColor",
		name = "Hover color",
		description = "Color of the hover outline for NPCs",
		section = npcSection,
		position = 2
	)
	default Color npcHoverHighlightColor()
	{
		return new Color(0x90FFFF00, true);
	}

	@Alpha
	@ConfigItem(
		keyName = "npcInteractHighlightColor",
		name = "Interact color",
		description = "Color of the interact outline for NPCs",
		section = npcSection,
		position = 3
	)
	default Color npcInteractHighlightColor()
	{
		return new Color(0x9000FF00, true);
	}

	@Alpha
	@ConfigItem(
		keyName = "npcAttackHoverHighlightColor",
		name = "Attack hover color",
		description = "Color of the hover outline for attackable NPCs",
		section = npcSection,
		position = 4
	)
	default Color npcAttackHoverHighlightColor()
	{
		return new Color(0x90FF0000, true);
	}

	@Alpha
	@ConfigItem(
		keyName = "npcAttackInteractHighlightColor",
		name = "Attack interact color",
		description = "Color of the interact outline for attacking NPCs",
		section = npcSection,
		position = 5
	)
	default Color npcAttackInteractHighlightColor()
	{
		return new Color(0x90FF6400, true);
	}

	@ConfigItem(
		keyName = "objectShowHover",
		name = "Hover",
		description = "Show outline on objects when hovered",
		section = objectSection,
		position = 0
	)
	default boolean objectShowHover()
	{
		return true;
	}

	@ConfigItem(
		keyName = "objectShowInteract",
		name = "Interact",
		description = "Show outline on objects when interacted with",
		section = objectSection,
		position = 1
	)
	default boolean objectShowInteract()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "objectHoverHighlightColor",
		name = "Hover color",
		description = "Color of the hover outline for objects",
		section = objectSection,
		position = 2
	)
	default Color objectHoverHighlightColor()
	{
		return new Color(0x90FFFF00, true);
	}

	@Alpha
	@ConfigItem(
		keyName = "objectInteractHighlightColor",
		name = "Interact color",
		description = "Color of the interact outline for objects",
		section = objectSection,
		position = 3
	)
	default Color objectInteractHighlightColor()
	{
		return new Color(0x9000FF00, true);
	}

	@Range(
		min = 1,
		max = 10
	)
	@ConfigItem(
		keyName = "borderWidth",
		name = "Border width",
		description = "Width of the outline border",
		position = 10
	)
	default int borderWidth()
	{
		return 4;
	}

	@Range(
		min = 0,
		max = 4
	)
	@ConfigItem(
		keyName = "outlineFeather",
		name = "Outline feather",
		description = "Amount of feathering on the outline",
		position = 11
	)
	default int outlineFeather()
	{
		return 0;
	}
}
