package net.runelite.client.plugins.agility;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.GroundObject;
import net.runelite.api.ItemID;
import net.runelite.api.Scene;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.StatChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Agility",
	description = "Show helpful information about agility courses and obstacles",
	tags = {"grace", "marks", "overlay", "shortcuts", "skilling", "traps"}
)
@Slf4j
public class AgilityPlugin extends Plugin
{
	private static final int STICK_ITEM_ID = ItemID.STICK;
	private static final int MARK_OF_GRACE_ID = ItemID.MARK_OF_GRACE;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AgilityOverlay agilityOverlay;

	@Inject
	private LapCountOverlay lapCountOverlay;

	@Inject
	private Notifier notifier;

	@Inject
	private AgilityConfig config;

	@Getter
	private final Map<TileObject, Tile> obstacles = new HashMap<>();

	@Getter
	private final List<Tile> marks = new ArrayList<>();

	@Getter
	private AgilitySession session;

	@Getter
	private int agilityLevel;

	@Getter
	private Tile stickTile;

	private int lastAgilityXp;

	@Provides
	AgilityConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AgilityConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(agilityOverlay);
		overlayManager.add(lapCountOverlay);

		try
		{
			agilityLevel = client.getBoostedSkillLevel(Skill.AGILITY);
		}
		catch (Exception e)
		{
			agilityLevel = 1;
		}

		log.info("Agility plugin started. Obstacle IDs loaded: {}, Shortcut IDs loaded: {}",
			Obstacles.OBSTACLE_IDS.size(), Obstacles.SHORTCUT_OBSTACLE_IDS.size());

		scanScene();
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(agilityOverlay);
		overlayManager.remove(lapCountOverlay);
		obstacles.clear();
		marks.clear();
		session = null;
		stickTile = null;
		agilityLevel = 0;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			obstacles.clear();
			marks.clear();
			stickTile = null;
		}
		else if (event.getGameState() == GameState.LOGGED_IN)
		{
			try
			{
				agilityLevel = client.getBoostedSkillLevel(Skill.AGILITY);
			}
			catch (Exception e)
			{
				agilityLevel = 1;
			}
			scanScene();
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		if (event.getSkill() != Skill.AGILITY)
		{
			return;
		}

		agilityLevel = event.getBoostedLevel();

		int currentXp = event.getXp();
		if (lastAgilityXp == 0)
		{
			lastAgilityXp = currentXp;
			return;
		}

		if (currentXp <= lastAgilityXp)
		{
			lastAgilityXp = currentXp;
			return;
		}

		int xpGained = currentXp - lastAgilityXp;
		lastAgilityXp = currentXp;

		trackSession(xpGained);
	}

	private void trackSession(int xpGained)
	{
		int[] regions = client.getMapRegions();
		if (regions == null)
		{
			return;
		}

		// Try to match a known course by region ID
		for (int regionId : regions)
		{
			Courses course = Courses.getCourse(regionId);
			if (course == null)
			{
				continue;
			}

			if (session != null && session.getCourse() == course)
			{
				session.incrementLapCount();
			}
			else
			{
				session = new AgilitySession(course);
				session.incrementLapCount();
			}
			return;
		}

		// Fallback: region does not match any known OSRS course (e.g. custom RSPS course)
		// Still track the lap so the overlay is shown
		if (session == null)
		{
			session = new AgilitySession(null);
		}
		session.incrementLapCount();
	}

	@Subscribe
	public void onItemSpawned(ItemSpawned event)
	{
		TileItem item = event.getItem();
		Tile tile = event.getTile();

		if (item.getId() == MARK_OF_GRACE_ID)
		{
			marks.add(tile);
		}
		else if (item.getId() == STICK_ITEM_ID)
		{
			stickTile = tile;
		}
	}

	@Subscribe
	public void onItemDespawned(ItemDespawned event)
	{
		TileItem item = event.getItem();
		Tile tile = event.getTile();

		if (item.getId() == MARK_OF_GRACE_ID)
		{
			marks.remove(tile);
		}
		else if (item.getId() == STICK_ITEM_ID)
		{
			stickTile = null;
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getGameObject());
	}

	@Subscribe
	public void onGameObjectChanged(GameObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getGameObject());
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getGameObject(), null);
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getGroundObject());
	}

	@Subscribe
	public void onGroundObjectDespawned(GroundObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getGroundObject(), null);
	}

	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getWallObject());
	}

	@Subscribe
	public void onWallObjectDespawned(WallObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getWallObject(), null);
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getDecorativeObject());
	}

	@Subscribe
	public void onDecorativeObjectChanged(DecorativeObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getDecorativeObject());
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getDecorativeObject(), null);
	}

	private void scanScene()
	{
		obstacles.clear();

		try
		{
			Scene scene = client.getScene();
			if (scene == null)
			{
				return;
			}

			Tile[][][] tiles = scene.getTiles();
			if (tiles == null)
			{
				return;
			}

			int matchCount = 0;
			for (int z = 0; z < tiles.length; z++)
			{
				for (int x = 0; x < tiles[z].length; x++)
				{
					for (int y = 0; y < tiles[z][x].length; y++)
					{
						Tile tile = tiles[z][x][y];
						if (tile == null)
						{
							continue;
						}

						for (GameObject gameObject : tile.getGameObjects())
						{
							if (gameObject == null)
							{
								continue;
							}
							if (checkObstacle(gameObject.getId(), tile))
							{
								obstacles.put(gameObject, tile);
								matchCount++;
							}
						}

						WallObject wallObject = tile.getWallObject();
						if (wallObject != null && checkObstacle(wallObject.getId(), tile))
						{
							obstacles.put(wallObject, tile);
							matchCount++;
						}

						DecorativeObject decorativeObject = tile.getDecorativeObject();
						if (decorativeObject != null && checkObstacle(decorativeObject.getId(), tile))
						{
							obstacles.put(decorativeObject, tile);
							matchCount++;
						}

						GroundObject groundObject = tile.getGroundObject();
						if (groundObject != null && checkObstacle(groundObject.getId(), tile))
						{
							obstacles.put(groundObject, tile);
							matchCount++;
						}
					}
				}
			}

			log.info("Scene scan complete. Found {} obstacles.", matchCount);
		}
		catch (Exception e)
		{
			log.warn("Error scanning scene for obstacles", e);
		}
	}

	private boolean checkObstacle(int id, Tile tile)
	{
		return Obstacles.OBSTACLE_IDS.contains(id)
			|| Obstacles.SHORTCUT_OBSTACLE_IDS.containsKey(id)
			|| isTrapObstacle(id, tile);
	}

	private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject)
	{
		if (oldObject != null)
		{
			obstacles.remove(oldObject);
		}

		if (newObject == null)
		{
			return;
		}

		int id = newObject.getId();

		if (checkObstacle(id, tile))
		{
			obstacles.put(newObject, tile);
		}
	}

	private boolean isTrapObstacle(int id, Tile tile)
	{
		if (!Obstacles.TRAP_OBSTACLE_IDS.contains(id))
		{
			return false;
		}

		int[] regions = client.getMapRegions();
		if (regions == null)
		{
			return false;
		}

		for (int region : regions)
		{
			if (Obstacles.TRAP_OBSTACLE_REGIONS.contains(region))
			{
				return true;
			}
		}

		return false;
	}
}
