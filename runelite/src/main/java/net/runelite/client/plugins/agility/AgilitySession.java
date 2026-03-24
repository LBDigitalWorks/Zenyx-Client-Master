package net.runelite.client.plugins.agility;

import com.google.common.collect.EvictingQueue;
import java.time.Duration;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

class AgilitySession
{
	@Getter
	private final Courses course;

	@Getter
	private Instant lastLapCompleted;

	@Getter
	@Setter
	private int totalLaps;

	@Getter
	@Setter
	private int lapsTillGoal;

	@Getter
	@Setter
	private int lapsPerHour;

	private final EvictingQueue<Duration> lastLapTimes = EvictingQueue.create(30);

	AgilitySession(Courses course)
	{
		this.course = course;
	}

	void incrementLapCount()
	{
		Instant now = Instant.now();

		if (lastLapCompleted != null)
		{
			Duration lapTime = Duration.between(lastLapCompleted, now);
			lastLapTimes.add(lapTime);
		}

		lastLapCompleted = now;
		++totalLaps;

		calculateLapsPerHour();
	}

	private void calculateLapsPerHour()
	{
		if (lastLapTimes.isEmpty())
		{
			lapsPerHour = 0;
			return;
		}

		long totalMillis = 0;
		for (Duration d : lastLapTimes)
		{
			totalMillis += d.toMillis();
		}

		long avgLapMillis = totalMillis / lastLapTimes.size();
		if (avgLapMillis > 0)
		{
			lapsPerHour = (int) (Duration.ofHours(1).toMillis() / avgLapMillis);
		}
	}
}