package net.runelite.client.plugins.agility;

import java.util.function.ToDoubleFunction;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.VarbitID;

@Getter
enum Courses
{
	GNOME(110.5, 9781, new WorldPoint(2484, 3437, 0), new WorldPoint(2487, 3437, 0)),
	DRAYNOR(120.0, 12338, new WorldPoint(3103, 3261, 0)),
	AL_KHARID(216.0, 13105, new WorldPoint(3299, 3194, 0)),
	PYRAMID(722.0, 13356, new WorldPoint(3364, 2830, 0)),
	VARROCK(270.0, 12853, new WorldPoint(3236, 3417, 0)),
	PENGUIN(540.0, 10559, new WorldPoint(2652, 4039, 1)),
	BARBARIAN(153.2, 10039, new WorldPoint(2543, 3553, 0)),
	CANIFIS(240.0, 13878, new WorldPoint(3510, 3485, 0)),
	APE_ATOLL(580.0, 11050, new WorldPoint(2770, 2747, 0)),
	FALADOR(586, 12084, new WorldPoint(3029, 3332, 0), new WorldPoint(3029, 3333, 0), new WorldPoint(3029, 3334, 0), new WorldPoint(3029, 3335, 0)),
	WILDERNESS(571.4, 11837, new WorldPoint(2993, 3933, 0), new WorldPoint(2994, 3933, 0), new WorldPoint(2995, 3933, 0)),
	WEREWOLF(730.0, 14234, new WorldPoint(3528, 9873, 0)),
	SEERS(570.0, 10806, new WorldPoint(2704, 3464, 0)),
	POLLNIVNEACH((client) -> client.getVarbitValue(VarbitID.DESERT_DIARY_HARD_COMPLETE) == 1 ? 1016.0 : 890.0, 13358, new WorldPoint(3363, 2998, 0)),
	RELLEKA((client) -> client.getVarbitValue(VarbitID.FREMENNIK_DIARY_HARD_COMPLETE) == 1 ? 920.0 : 780.0, 10553, new WorldPoint(2653, 3676, 0)),
	ARDOUGNE(889.0, 10547, new WorldPoint(2668, 3297, 0));

	private final ToDoubleFunction<Client> totalXp;
	private final int regionId;
	private final WorldPoint[] lastObstacleArea;

	Courses(double totalXp, int regionId, WorldPoint... lastObstacles)
	{
		this.totalXp = (client) -> totalXp;
		this.regionId = regionId;
		this.lastObstacleArea = lastObstacles;
	}

	Courses(ToDoubleFunction<Client> totalXp, int regionId, WorldPoint... lastObstacles)
	{
		this.totalXp = totalXp;
		this.regionId = regionId;
		this.lastObstacleArea = lastObstacles;
	}

	Courses(double totalXp)
	{
		this.totalXp = (client) -> totalXp;
		this.regionId = -1;
		this.lastObstacleArea = new WorldPoint[0];
	}

	static Courses getCourse(int regionId)
	{
		for (Courses course : values())
		{
			if (course.regionId == regionId)
			{
				return course;
			}
		}
		return null;
	}

	double getTotalXp(Client client)
	{
		return totalXp.applyAsDouble(client);
	}
}
