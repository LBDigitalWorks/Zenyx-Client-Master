package net.runelite.client.plugins.agility;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;

@ConfigGroup("agility")
public interface AgilityConfig extends Config
{
	@ConfigItem(
		keyName = "showClickboxes",
		name = "Show clickboxes",
		description = "Show agility course and other obstacle clickboxes.",
		position = 0
	)
	default boolean showClickboxes()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showLapCount",
		name = "Show lap count",
		description = "Enable/disable the lap counter.",
		position = 1
	)
	default boolean showLapCount()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lapTimeout",
		name = "Hide lap count",
		description = "Time until the lap counter hides/resets.",
		position = 2
	)
	@Units(Units.MINUTES)
	default int lapTimeout()
	{
		return 5;
	}

	@ConfigItem(
		keyName = "lapsToLevel",
		name = "Show laps until goal",
		description = "Show number of laps remaining until next goal is reached.",
		position = 3
	)
	default boolean lapsToLevel()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lapsPerHour",
		name = "Show laps per hour",
		description = "Shows how many laps you can expect to complete per hour.",
		position = 4
	)
	default boolean lapsPerHour()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "overlayColor",
		name = "Overlay color",
		description = "Color of agility overlay.",
		position = 5
	)
	default Color getOverlayColor()
	{
		return Color.GREEN;
	}

	@ConfigItem(
		keyName = "highlightMarks",
		name = "Highlight marks of grace",
		description = "Enable/disable the highlighting of retrievable marks of grace.",
		position = 6
	)
	default boolean highlightMarks()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "markHighlight",
		name = "Mark highlight color",
		description = "Color of highlighted marks of grace.",
		position = 7
	)
	default Color getMarkColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		keyName = "highlightShortcuts",
		name = "Highlight agility shortcuts",
		description = "Enable/disable the highlighting of agility shortcuts.",
		position = 8
	)
	default boolean highlightShortcuts()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trapOverlay",
		name = "Show trap overlay",
		description = "Enable/disable the highlighting of traps on agility courses.",
		position = 9
	)
	default boolean showTrapOverlay()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "trapHighlight",
		name = "Trap overlay color",
		description = "Color of agility trap overlay.",
		position = 10
	)
	default Color getTrapColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		keyName = "agilityArenaNotifier",
		name = "Agility Arena notifier",
		description = "Notify on ticket location change in Agility Arena.",
		position = 11
	)
	default boolean notifyAgilityArena()
	{
		return true;
	}

	@ConfigItem(
		keyName = "agilityArenaTimer",
		name = "Agility Arena timer",
		description = "Configures whether Agility Arena timer is displayed.",
		position = 12
	)
	default boolean showAgilityArenaTimer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightStick",
		name = "Highlight stick",
		description = "Highlight the retrievable stick in the Werewolf Agility Course.",
		position = 13
	)
	default boolean highlightStick()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "stickHighlightColor",
		name = "Stick highlight color",
		description = "Color of highlighted stick.",
		position = 14
	)
	default Color stickHighlightColor()
	{
		return Color.RED;
	}
}