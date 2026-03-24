package net.runelite.client.plugins.giantmole;

import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "Giant Mole Tracker",
		description = "Shows an arrow over the Giant Mole and on the minimap",
		tags = {"npc", "boss", "overlay", "minimap", "giant mole"}
)
public class GiantMolePlugin extends Plugin
{
	private static final String GIANT_MOLE_NAME = "Giant Mole";

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private GiantMoleSceneOverlay sceneOverlay;

	@Inject
	private GiantMoleMinimapOverlay minimapOverlay;

	@Getter
	private NPC activeMole;

	@Override
	protected void startUp()
	{
		overlayManager.add(sceneOverlay);
		overlayManager.add(minimapOverlay);

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			scanForMole();
			updateHintArrow();
		}
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(minimapOverlay);
		activeMole = null;
		client.clearHintArrow();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			scanForMole();
			updateHintArrow();
		}
		else if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			activeMole = null;
			client.clearHintArrow();
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		NPC npc = event.getNpc();

		if (npc != null && isGiantMole(npc))
		{
			activeMole = npc;
			updateHintArrow();
		}
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		NPC npc = event.getNpc();

		if (npc == activeMole)
		{
			activeMole = null;
			client.clearHintArrow();
		}
	}

	private void scanForMole()
	{
		activeMole = null;

		for (NPC npc : client.getNpcs())
		{
			if (npc != null && isGiantMole(npc))
			{
				activeMole = npc;
				return;
			}
		}
	}

	private void updateHintArrow()
	{
		if (activeMole != null)
		{
			client.setHintArrow(activeMole);
		}
		else
		{
			client.clearHintArrow();
		}
	}

	private static boolean isGiantMole(NPC npc)
	{
		String name = npc.getName();
		return name != null && name.equalsIgnoreCase(GIANT_MOLE_NAME);
	}
}