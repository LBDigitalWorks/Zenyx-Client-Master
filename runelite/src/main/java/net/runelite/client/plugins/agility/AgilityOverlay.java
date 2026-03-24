package net.runelite.client.plugins.agility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.AgilityShortcut;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class AgilityOverlay extends Overlay
{
	private static final int MAX_DISTANCE = 2350;
	private static final Color SHORTCUT_HIGH_LEVEL_COLOR = Color.ORANGE;

	private final Client client;
	private final AgilityPlugin plugin;
	private final AgilityConfig config;

	@Inject
	private AgilityOverlay(Client client, AgilityPlugin plugin, AgilityConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (client.getLocalPlayer() == null)
		{
			return null;
		}

		LocalPoint playerLocation = client.getLocalPlayer().getLocalLocation();
		Point mousePosition = client.getMouseCanvasPosition();
		int agilityLevel = plugin.getAgilityLevel();

		Map<TileObject, Tile> obstacles;
		try
		{
			obstacles = Map.copyOf(plugin.getObstacles());
		}
		catch (Exception e)
		{
			return null;
		}

		for (Map.Entry<TileObject, Tile> entry : obstacles.entrySet())
		{
			TileObject object = entry.getKey();
			Tile tile = entry.getValue();

			try
			{
				if (tile.getPlane() != client.getPlane())
				{
					continue;
				}

				LocalPoint location = object.getLocalLocation();
				if (location == null)
				{
					continue;
				}

				if (playerLocation.distanceTo(location) > MAX_DISTANCE)
				{
					continue;
				}

				int objectId = object.getId();

				if (Obstacles.SHORTCUT_OBSTACLE_IDS.containsKey(objectId))
				{
					if (!config.highlightShortcuts())
					{
						continue;
					}

					Collection<AgilityShortcut> shortcuts = Obstacles.SHORTCUT_OBSTACLE_IDS.get(objectId);
					AgilityShortcut matchingShortcut = null;
					for (AgilityShortcut shortcut : shortcuts)
					{
						if (shortcut.matches(client, object))
						{
							matchingShortcut = shortcut;
							break;
						}
					}

					if (matchingShortcut == null)
					{
						continue;
					}

					Color color = agilityLevel >= matchingShortcut.getLevel()
						? config.getOverlayColor()
						: SHORTCUT_HIGH_LEVEL_COLOR;

					renderObstacle(graphics, mousePosition, object, color, matchingShortcut.getTooltip());
				}
				else if (Obstacles.TRAP_OBSTACLE_IDS.contains(objectId))
				{
					if (!config.showTrapOverlay())
					{
						continue;
					}

					renderTilePoly(graphics, object, config.getTrapColor());
				}
				else
				{
					if (!config.showClickboxes())
					{
						continue;
					}

					renderObstacle(graphics, mousePosition, object, config.getOverlayColor(), null);
				}
			}
			catch (Exception e)
			{
				// Skip this obstacle on render error
			}
		}

		// Highlight marks of grace
		if (config.highlightMarks())
		{
			try
			{
				for (Tile markTile : plugin.getMarks().toArray(new Tile[0]))
				{
					if (markTile.getPlane() != client.getPlane())
					{
						continue;
					}

					LocalPoint markLocation = markTile.getLocalLocation();
					if (markLocation == null || playerLocation.distanceTo(markLocation) > MAX_DISTANCE)
					{
						continue;
					}

					Polygon poly = Perspective.getCanvasTilePoly(client, markLocation);
					if (poly != null)
					{
						OverlayUtil.renderPolygon(graphics, poly, config.getMarkColor());
					}
				}
			}
			catch (Exception e)
			{
				// Skip marks on error
			}
		}

		// Highlight stick
		Tile stickTile = plugin.getStickTile();
		if (config.highlightStick() && stickTile != null)
		{
			try
			{
				if (stickTile.getPlane() == client.getPlane())
				{
					LocalPoint stickLocation = stickTile.getLocalLocation();
					if (stickLocation != null && playerLocation.distanceTo(stickLocation) <= MAX_DISTANCE)
					{
						Polygon poly = Perspective.getCanvasTilePoly(client, stickLocation);
						if (poly != null)
						{
							OverlayUtil.renderPolygon(graphics, poly, config.stickHighlightColor());
						}
					}
				}
			}
			catch (Exception e)
			{
				// Skip stick on error
			}
		}

		return null;
	}

	private void renderObstacle(Graphics2D graphics, Point mousePosition, TileObject object, Color color, String tooltip)
	{
		// Try clickbox first, fall back to tile polygon
		Shape clickbox = null;
		try
		{
			clickbox = object.getClickbox();
		}
		catch (Exception e)
		{
			// Clickbox calculation failed
		}

		if (clickbox != null)
		{
			Color fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
			if (mousePosition != null && clickbox.contains(mousePosition.getX(), mousePosition.getY()))
			{
				graphics.setColor(color);
				graphics.draw(clickbox);
				graphics.setColor(fillColor);
				graphics.fill(clickbox);
			}
			else
			{
				graphics.setColor(color);
				graphics.draw(clickbox);
			}
		}
		else
		{
			// Fallback: render tile polygon
			renderTilePoly(graphics, object, color);
		}

		if (tooltip != null)
		{
			Point textLocation = object.getCanvasTextLocation(graphics, tooltip, 0);
			if (textLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, textLocation, tooltip, color);
			}
		}
	}

	private void renderTilePoly(Graphics2D graphics, TileObject object, Color color)
	{
		Polygon poly = null;
		try
		{
			poly = object.getCanvasTilePoly();
		}
		catch (Exception e)
		{
			// Tile poly calculation failed, try from local point
		}

		if (poly == null)
		{
			LocalPoint lp = object.getLocalLocation();
			if (lp != null)
			{
				poly = Perspective.getCanvasTilePoly(client, lp);
			}
		}

		if (poly != null)
		{
			OverlayUtil.renderPolygon(graphics, poly, color);
		}
	}
}
