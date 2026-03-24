package net.runelite.client.plugins.agility;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class LapCountOverlay extends OverlayPanel
{
	private final AgilityPlugin plugin;
	private final AgilityConfig config;

	@Inject
	private LapCountOverlay(AgilityPlugin plugin, AgilityConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		AgilitySession session = plugin.getSession();
		if (session == null || !config.showLapCount())
		{
			return null;
		}

		// Hide lap counter after configured timeout
		Instant lastLapCompleted = session.getLastLapCompleted();
		if (lastLapCompleted != null && config.lapTimeout() > 0)
		{
			Duration timeSinceLastLap = Duration.between(lastLapCompleted, Instant.now());
			if (timeSinceLastLap.toMinutes() >= config.lapTimeout())
			{
				return null;
			}
		}

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Agility")
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Total laps:")
			.right(Integer.toString(session.getTotalLaps()))
			.build());

		if (config.lapsToLevel() && session.getLapsTillGoal() > 0)
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Laps until goal:")
				.right(Integer.toString(session.getLapsTillGoal()))
				.build());
		}

		if (config.lapsPerHour() && session.getLapsPerHour() > 0)
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Laps/hr:")
				.right(Integer.toString(session.getLapsPerHour()))
				.build());
		}

		return super.render(graphics);
	}
}