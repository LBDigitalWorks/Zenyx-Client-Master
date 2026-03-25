package com.client.definitions;

import com.client.*;
import com.client.collection.EvictingDualNodeHashTable;
import com.client.collection.node.DualNode;
import com.client.collection.table.IterableNodeHashTable;
import com.client.definitions.anim.SequenceDefinition;
import com.client.entity.model.ModelData;
import com.client.entity.model.Model;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSBuffer;
import net.runelite.rs.api.RSIterableNodeHashTable;
import net.runelite.rs.api.RSObjectComposition;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.util.Arrays;

public final class ObjectDefinition extends DualNode implements RSObjectComposition {

	public boolean usePre220Sounds = false;
	private int field2130 = 0;
	public boolean field2222;
	public int opcode91;
	public int opcode92;
	public int opcode92_2;
	public int field2395;
	public int field2396;
	public static final class343 field2344;
	public class343 field2394;
	public class343 field2365;

	public class343 field2342;
	public class402 field2334;
	public static final class343 field2345;
	public static final class343 field2346;
	public static EvictingDualNodeHashTable objectsCached;
	public static EvictingDualNodeHashTable ObjectDefinition_cachedModelData;
	static EvictingDualNodeHashTable ObjectDefinition_cachedEntities;
	public static EvictingDualNodeHashTable ObjectDefinition_cachedModels;

	public static ModelData[] modelDataArray;

	static {
		field2344 = class343.field3891;
		field2345 = class343.field3891;
		field2346 = class343.field3891;
		objectsCached = new EvictingDualNodeHashTable(4096);
		ObjectDefinition_cachedModelData = new EvictingDualNodeHashTable(500);
		ObjectDefinition_cachedEntities = new EvictingDualNodeHashTable(256);
		ObjectDefinition_cachedModels = new EvictingDualNodeHashTable(256);
		modelDataArray = new ModelData[4];
	}

	public static ObjectDefinition lookup(int objectID) {
		ObjectDefinition definition = (ObjectDefinition) objectsCached.get(objectID);
		if (definition == null) {
			byte[] data = Js5List.configs.takeFile(Js5ConfigType.OBJECT, objectID);
			definition = new ObjectDefinition();
			definition.setDefaults();
			definition.id = objectID;
			if (data != null) {
				definition.decode(new Buffer(data));
			}
			definition.postDecode();

			if (objectID >= 26281 && objectID <= 26290) {
				definition.actions = new String[] { "Choose", null, null, null, null };
			}

			switch (objectID) {
				case 36201: // Raids 1 lobby entrance
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 33014:
					definition.name = "Item Upgrades Table";
					//definition.modelIds = new int[]{ 54578 };
					definition.actions = new String[]{"Upgrade", null, null, null, null};
					break;
				case 590:
					definition.name = "Item Upgrades Table";
					definition.modelIds = new int[]{ 54578 };
					definition.actions = new String[]{"Forge", null, null, null, null};
					break;
				case 31923:
					definition.name = "Altar of the Occult";
					definition.actions = new String[]{"Switch Spell Books", null, null, null, null};
					break;
				case 46068:
					definition.name = "Enter Tombs of Amascut";
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 46089:
					definition.name = "Blocked Tomb";
					definition.actions = new String[]{null, null, null, null, null};
					break;
				case 36062:
					definition.description = "Teleports you anywhere around Zenyx.";
					definition.actions = new String[]{"Activate", "Previous Location", null, null, null};
					break;
				case 48218:
					definition.name = "Fight Vardorvis";
					definition.description = "Activates the fight with Vardorvis.";
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;
				case 4389:
					definition.name = "Fight The Whisperer";
					definition.description = "Activates the fight with The Whisperer.";
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;
				case 4152:
					definition.name = "Skilling Portal";
					definition.description = "Teleports you to various skilling areas.";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 37616:
					definition.name = "Main Donator Teleport Portal";
					definition.description = "Teleports you to various location in the donator zone.";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 1206:
					definition.name = "Hespori Vines";
					definition.description = "The vines of Hespori.";
					definition.actions = new String[] { "Pick", null, null, null, null };
					break;
				case 33222:
					definition.name = "Burning Ore";
					definition.description = "I should try heating this up.";
					definition.actions = new String[] { "Mine", null, null, null, null };
					break;
				case 30150:
					definition.name = "Magical Dead Tree";
					definition.description = "A tree that needs leaves.";
					definition.actions = new String[]{"Give All Leaves", null, null, null, null};
					break;
				case 23676:
					definition.name = "C Bandos Alter";
					definition.modelIds = new int[]{ 50017 };
					definition.description = "A tree that needs leaves.";
					definition.actions = new String[]{"Pray", "Teleport", null, null, null};
					definition.isSolid = true;
					definition.setSizeX(3);
					definition.setSizeY(3);
					break;
				case 10290:
					definition.name = "C Armadyl Alter";
					definition.modelIds = new int[]{ 49044 };
					definition.description = "A tree that needs leaves.";
					definition.actions = new String[]{"Pray", "Teleport", null, null, null};
					definition.isSolid = true;
					definition.setSizeX(3);
					definition.setSizeY(3);
					break;

				case 8880:
					definition.name = "Tool Box";
					definition.description = "Useful tools for resources in the area.";
					definition.actions = new String[]{"Take-tools", null, null, null, null};
					break;
				case 29771:
					definition.name = "Tools";
					definition.description = "Useful tools for resources in the area.";
					definition.actions = new String[]{null, null, null, null, null};
					break;
				case 33223:
					definition.name = "Enchanted stone";
					definition.description = "A fragile ancient stone.";
					definition.actions = new String[]{"Mine", null, null, null, null};
					break;

				case 33311:
					definition.name = "Fire";
					definition.description = "Looks very hot.";
					definition.actions = new String[]{"Burn-essence", "Burn-runes", null, null, null};
					break;
				case 12768:
					definition.name = "@gre@Nature Chest";
					definition.description = "Requires a Hespori key to open.";
					break;
				case 37743: // nightmare good flower
					definition.animation = 8617;
					break;
				case 37740: // nightmare bad flower
					definition.animation = 8623;
					break;
				case 37738: // nightmare spore spawn
					definition.animation = 8630;
					break;
				case 35914:
					definition.name = "Ahrim The Blighted";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 9362:
					definition.name = "Dharok The Wretched";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 14766:
					definition.name = "Verac The Defiled";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 9360:
					definition.name = "Torag The Corrupted";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 28723:
					definition.name = "Karil The Tainted";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 31716:
					definition.name = "Guthan The Infested";
					definition.actions = new String[] { "Awaken", null, null, null, null };
					break;
				case 31622:
					definition.name = "Outlast Entrance";
					definition.actions = new String[] { "Enter", "Check Players", "Check Active", null, null };
					break;
				case 31624:
					definition.name = "@pur@Platinum Altar";
					break;
				case 29064:
					definition.name = "Zenyx Leaderboards";
					definition.actions = new String[]{"View", null, null, null, null};
					break;
				case 33320:
					return copy(definition, 33320, 33321, "Flame of Zenyx", new String[]{"Burn", "Burn Rates", null, null, null});
				case 33318:
					definition.name = "Pool of Darkened Blood";
					definition.modelIds = new int[]{63489};
					definition.actions = new String[]{"Sacrifice", null, null, null, null};
					break;
				case 46164:
					definition.name = "Path of Ba-Ba";
					//definition.modelIds = new int[]{63489};
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 33515:
					definition.name = "Araxxor Sign";
					definition.modelIds = new int[]{63509};
					definition.actions = new String[]{"Forge", null, null, null, null};
					break;
				case 33560:
					definition.name = "Blood Forge";
					definition.modelIds = new int[]{63488};
					definition.actions = new String[]{"Forge", null, null, null, null};
					break;
				case 33558:
					definition.name = "Araxxor Entrence";
					definition.modelIds = new int[]{59650};
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 32508:
					definition.name = "Hunllef's Chest";
					definition.actions = new String[] { "Unlock", null, null, null, null };
					break;
				case 46220:
					definition.name = "Scarcophagus";
					definition.actions = new String[]{"Open", null, null, null, null};
					break;

				case 23675:
					definition.modelIds = new int[]{63116};
					definition.name = "Zenyx Loot Chest";
					definition.actions = new String[]{"Unlock", null, null, null, null};
					definition.setSizeX(3);
					definition.setSizeY(1);
					break;
				case 24712:
					definition.modelIds = new int[]{63185};
					definition.name = "Flames of Zenyx";
					definition.actions = new String[]{"Sacrifice", "Sacrifice Rates", null, null, null};
					break;
				case 11492:
					definition.modelIds = new int[]{63160};
					definition.name = "Ryuu Sarcophagus";
					definition.actions = new String[]{null, null, null, null, null};
					definition.isSolid = true;
					definition.sizeX = 3;
					definition.sizeY = 9;
					break;
				case 29734:
					definition.name = "Ryuu Crypt";
					break;
				case 23674:
					definition.modelIds = new int[]{35337};
					definition.name = "Treasure Room";
					definition.actions = new String[]{null, "Enter", null, null, null};
					definition.setSizeX(3);
					definition.setSizeY(3);
					break;
				case 6097:
					definition.name = "Well of Zenyx";
					definition.modelIds = new int[] { 65022 };
					definition.actions = new String[]{"Donate", null, null, null, null};
					break;
				case 23673:
					definition.name = "Statue of Queen Zenyx";
					definition.modelIds = new int[] { 63119 };
					definition.actions = new String[]{null, null, null, null, null};
					definition.setSizeX(3);
					definition.setSizeY(3);
					definition.isSolid = true;
					//definition.animation = 3030;
					break;
				case 28449:
					definition.name = "Santa gnome";
					definition.modelIds = new int[] { 57981, 57982, 57983, 57984 };
					definition.isSolid = true;
					break;
				case 29165:
					definition.name = "Coin Stack";
					definition.actions = new String[] { null, "Steal From", null, null, null };
					break;
				case 13681:
					definition.name = "Animal Cage";
					definition.actions = new String[] { null, null, null, null, null };
					break;
				case 30720:
					definition.name = "@red@Corrupt Chest";
					definition.actions = new String[] { "Open", null, null, null, null };
					break;
				case 34662:
					definition.actions = new String[] { "Open", "Teleport", null, null, null };
					break;
				case 12202:
					definition.actions = new String[] { "Dig", null, null, null, null };
					break;
				case 30107:
					definition.name = "Raids Reward Chest";
					definition.actions = new String[] { "Open", null, null, null, null };
					break;
				case 36197:
					definition.name = "Home Teleport";

					break;
				case 10562:
					definition.actions = new String[] { "Open", null, null, null, null };
					break;
				case 8207:
					definition.actions = new String[] { "Care-To", null, null, null, null };
					definition.name = "Herb Patch";
					break;
				case 8720:
					definition.name = "Vote shop";
					break;
				case 8210:
					definition.actions = new String[] { "Rake", null, null, null, null };
					definition.name = "Herb Patch";
					break;
				case 29150:
					definition.actions = new String[] { "Venerate", null, null, null, null };
					break;
				case 6764:
					definition.name = null;
					definition.actions = new String[] { null, null, null, null, null };
					break;
				case 8139:
				case 8140:
				case 8141:
				case 8142:
					definition.actions = new String[] { "Inspect", null, null, null, null };
					definition.name = "Herbs";
					break;
				case 2341:
					definition.actions = new String[] { null, null, null, null, null };
					break;
				case 14217:
					definition.actions = new String[5];
					break;
				case 3840:
					definition.actions = new String[5];
					definition.actions[0] = "Fill";
					definition.actions[1] = "Empty-From";
					definition.name = "Compost Bin";
					break;
				case 172:
					definition.name = "Ckey chest";
					break;
				case 31925:
					definition.name = "Max Island";
					definition.actions = new String[] { "Tele to", null, null, null, null };
					break;
				case 2996:
					definition.name = "Vote Chest";
					definition.actions = new String[] { "Unlock", null, null, null, null };
					break;

				case 12309:
					definition.actions = new String[5];
					definition.actions[0] = "Bank";
					definition.actions[1] = "Buy gloves";
					definition.actions[2] = null;
					definition.name = "Chest";
					break;
				case 32572:
					definition.actions = new String[5];
					definition.actions[0] = "Bank";
					definition.name = "Group chest";
					break;
				case 1750:
					definition.modelIds = new int[] { 8131, };
					definition.name = "Willow";
					definition.sizeX = 2;
					definition.sizeY = 2;
					definition.ambient = 25;
					definition.actions = new String[] { "Chop down", null, null, null, null };
					definition.mapscene = 3;
					break;

				case 26782:
					definition.actions = new String[] { "Recharge", null, null, null, null };
					break;

				case 1751:
					definition.modelIds = new int[] { 8037, 8040, };
					definition.name = "Oak";
					definition.sizeX = 3;
					definition.sizeY = 3;
					definition.ambient = 25;
					definition.actions = new String[] { "Chop down", null, null, null, null };
					definition.mapscene = 1;
					break;

				case 7814:
					definition.actions = new String[] { "Teleport", null, null, null, null };
					break;

				case 8356:
					definition.actions = new String[] { "Teleport", "Mt. Quidamortem", null, null, null };
					break;

				case 28900:
					definition.actions = new String[] { "Teleport", "Recharge Crystals", null, null, null };
					break;
				case 26740:
					definition.name = "Player Outlast";
					definition.actions = new String[] { "Join", "Setup", null, null, null };
					break;

				case 28837:
					definition.actions = new String[] { "Set Destination", null, null, null, null };
					break;

				case 7811:
					definition.name = "District Supplies";
					definition.actions = new String[] { "Blood Money", "Free", "Quick-Sets", null, null };
					break;
				case 10061:
				case 10060:
					definition.name = "Trading Post";
					definition.actions = new String[] { "Bank", "Open", "Collect", null, null };
					break;
				case 13287:
					definition.name = "Storage chest (UIM)";
					definition.description = "A chest to store items for UIM.";
					break;
				case 1752:
					definition.modelIds = new int[] { 4146, };
					definition.name = "Hollow tree";
					definition.ambient = 25;
					definition.actions = new String[] { "Chop down", null, null, null, null };
					definition.recolorTo = new int[] { 13592, 10512, };
					definition.recolorFrom = new int[] { 7056, 6674, };
					definition.mapscene = 0;
					break;
				case 4873:
					definition.name = "Wilderness Lever";
					definition.sizeX = 3;
					definition.sizeY = 3;
					definition.ambient = 25;
					definition.actions = new String[] { "Enter Deep Wildy", null, null, null, null };
					definition.mapscene = 3;
					break;
				case 36559:
				case 42837:
					definition.modelIds = new int[]{63117}; //TODO: Readd model to cache.
                    //definition.modelIds = new int[]{23319};
					definition.name = "Bank booth";
					definition.animation = 3030;
					definition.boolean1 = false;
					definition.ambient = 25;
					definition.contrast = 25;
					definition.actions = new String[]{null, "Bank", "Collect", null, null};
					break;
				case 29735:
					definition.name = "Basic Slayer Dungeon";
					break;
				case 2544:
					definition.name = "Dagannoth Manhole";
					break;
				case 29345:
					definition.name = "Training Teleports Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 41414:
					definition.name = "Lower Donator Teleport Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 33354:
					definition.name = "Upper Donator Teleport Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 10738:
					definition.name = "Zenyx Teleport Device";
					definition.modelIds = new int[]{63118,};
					definition.actions = new String[]{"Teleport", null, null, null, null};
					definition.animation = 3030;
					definition.modelHeight = 90;
					break;
				case 29346:
					definition.name = "Wilderness Teleports Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 29347:
					definition.name = "Boss Teleports Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 29349:
					definition.name = "Mini-Game Teleports Portal";
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
				case 3650:
					definition.name = "Zenyx Golem Entrence";
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 7127:
					definition.name = "Leaderboards";
					definition.actions = new String[]{"Open", "Wins", "Kills", "KDR", null};
					break;
				case 4155:
					definition.name = "Zul Andra Portal";
					break;
				case 2123:
					definition.name = "Mt. Quidamortem Slayer Dungeon";
					break;
				case 4150:
					definition.name = "Warriors Guild Mini-game Portal";
					break;
				case 11803:
					definition.name = "Donator Slayer Dungeon";
					break;
				case 4151:
					definition.name = "Barrows Mini-game Portal";
					break;
				case 1753:
					definition.modelIds = new int[] { 8157, };
					definition.name = "Yew";
					definition.sizeX = 3;
					definition.sizeY = 3;
					definition.ambient = 25;
					definition.actions = new String[] { "Chop down", null, null, null, null };
					definition.mapscene = 3;
					break;

				case 6943:
					definition.modelIds = new int[] { 1270, };
					definition.name = "Bank booth";
					definition.boolean1 = false;
					definition.ambient = 25;
					definition.contrast = 25;
					definition.actions = new String[] { null, "Bank", "Collect", null, null };
					break;

				case 25016:
				case 25017:
				case 25018:
				case 25029:
					definition.actions = new String[] { "Push-Through", null, null, null, null };
					break;

				case 18826:
				case 18819:
				case 18818:
					definition.sizeX = 3;
					definition.sizeY = 3;
					definition.modelSizeY = 200; // Width
					definition.modelSizeX = 200; // Thickness
					definition.modelHeight = 100; // Height
					break;

				case 27777:
					definition.name = "Gangplank";
					definition.actions = new String[] { "Travel to CrabClaw Isle", null, null, null, null };
					definition.sizeX = 1;
					definition.sizeY = 1;
					definition.modelSizeY = 80; // Width
					definition.modelSizeX = 80; // Thickness
					definition.modelHeight = 250; // Height
					break;
				case 13641:
					definition.name = "Teleportation Device";
					definition.actions = new String[] { "Quick-Teleport", null, null, null, null };
					definition.sizeX = 1;
					definition.sizeY = 1;
					definition.modelSizeY = 80; // Width
					definition.modelSizeX = 80; // Thickness
					definition.modelHeight = 250; // Height
					break;

				case 29333:
					definition.name = "Trading post";
					definition.actions = new String[] { "Open", null, "Collect", null, null };
					definition.modelIds = new int[] { 63360 };
					definition.ambient = 25;
					definition.nonFlatShading = false;
					definition.description = "Buy and sell items with players here!";
					break;

				case 11700:
					definition.modelIds = new int[] { 4086 };
					definition.name = "Venom";
					definition.sizeX = 3;
					definition.sizeY = 3;
					definition.interactType = 0;
					definition.clipType = 1;
					definition.animation = 1261;
					definition.recolorFrom = new int[] { 31636 };
					definition.recolorTo = new int[] { 10543 };
					definition.modelSizeX = 160;
					definition.modelHeight = 160;
					definition.modelSizeY = 160;
					definition.actions = new String[5];
					// definition.description = new String(
					// "It's a cloud of venomous smoke that is extremely toxic.");
					break;

				case 11601: // 11601
					definition.retextureFrom = new short[] { 2 };
					definition.retextureTo = new short[] { 46 };
					break;
			}

			objectsCached.put(definition, objectID);
		}
		return definition;
	}

	public void postDecode() {
		if (int1 == -1) {
			int1 = 0;
			if (modelIds != null && (models == null || models[0] == 10)) {
				int1 = 1;
			}

			for (int index = 0; index < 5; index++) {
				if (this.actions[index] != null) {
					this.int1 = 1;
				}
			}
		}

		if (int3 == -1) {
			int3 = interactType != 0 ? 1 : 0;
		}
	}


	public static ObjectDefinition copy(ObjectDefinition objectDefinition, int newId, int copyingobjId, String newName, String... actions) {
		ObjectDefinition copyobjectDefinition = lookup(copyingobjId);
		objectDefinition.id = newId;
		objectDefinition.name = newName;
		objectDefinition.description = copyobjectDefinition.description;
		objectDefinition.recolorTo = copyobjectDefinition.recolorTo;
		objectDefinition.recolorFrom = copyobjectDefinition.recolorFrom;
		objectDefinition.modelIds = copyobjectDefinition.modelIds;
		objectDefinition.actions = new String[5];
		if (actions != null) {
			for (int index = 0; index < actions.length; index++) {
				objectDefinition.actions[index] = actions[index];
			}
		}
		return objectDefinition;
	}

	public static void dumpList() {
		try {
			FileWriter fw = new FileWriter("E:\\downloads\\Xeros Package\\temp\\" + "object_data.json");
			fw.write("[\n");
			for (int i = 0; i < totalObjects; i++) {
				ObjectDefinition def = ObjectDefinition.lookup(i);
				String output = "[\"" + StringUtils.join(def.actions, "\", \"") + "\"],";

				String finalOutput = "	{\n" + "		\"id\": " + def.id + ",\n		" + "\"name\": \"" + def.name
						+ "\",\n		\"models\": " + Arrays.toString(def.modelIds) + ",\n		\"actions\": "
						+ output.replaceAll(", \"\"]", ", \"Examine\"]").replaceAll("\"\"", "null")
								.replace("[\"null\"]", "[null, null, null, null, \"Examine\"]")
								.replaceAll(", \"Remove\"", ", \"Remove\", \"Examine\"")
						+ "	\n		\"width\": " + def.modelSizeY + "\n	},";
				fw.write(finalOutput.replaceAll("\"name\": \"null\",", "\"name\": null,"));
				fw.write(System.getProperty("line.separator"));
			}
			fw.write("]");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setDefaults() {
		name = null;
		recolorFrom = null;
		recolorTo = null;
		retextureTo = null;
		retextureFrom = null;
		sizeX = 1;
		sizeY = 1;
		boolean1 = true;
		int1 = -1;
		clipType = -1;
		nonFlatShading = false;
		modelClipped = false;
		boolean3 = true;
		animation = -1;
		int2 = 16;
		interactType = 2;
		ambient = 0;
		contrast = 0;
		actions = new String[5];
		minimapFunction = -1;
		mapscene = -1;
		isRotated = false;
		clipped = true;
		modelSizeX = 128;
		modelHeight = 128;
		modelSizeY = 128;
		surroundings = 0;
		offsetX = 0;
		offsetHeight = 0;
		offsetY = 0;
		boolean2 = false;
		isSolid = false;
		int3 = -1;
		transformVarp = -1;
		transformVarbit = -1;
		transforms = null;

		this.field2394 = field2344;
		this.field2395 = 300;
		this.field2396 = 300;
		this.field2365 = field2345;
		this.field2342 = field2346;
		this.field2334 = class402.field4861;
	}

	public static void nullLoader() {
		ObjectDefinition_cachedModelData.clear();
		ObjectDefinition_cachedEntities.clear();
		ObjectDefinition_cachedModelData.clear();
		ObjectDefinition_cachedModels.clear();
	}

	public static int totalObjects;

	public final boolean modelTypeCached(int var1) {
		if (this.models != null) {
			for (int var4 = 0; var4 < this.models.length; ++var4) {
				if (this.models[var4] == var1) {
					return Js5List.models.tryLoadFile(this.modelIds[var4] & 65535, 0);
				}
			}

			return true;
		} else if (this.modelIds == null) {
			return true;
		} else if (var1 != 10) {
			return true;
		} else {
			boolean var2 = true;

			for (int var3 = 0; var3 < this.modelIds.length; ++var3) {
				var2 &= Js5List.models.tryLoadFile(this.modelIds[var3] & 65535, 0);
			}

			return var2;
		}
	}

	public final Model getModelDynamic(int var1, int var2, int[][] var3, int var4, int var5, int var6, SequenceDefinition var7, int var8) {
		long var9;
		if (this.models == null) {
			var9 = (long)(var2 + (this.id << 10));
		} else {
			var9 = (long)(var2 + (var1 << 3) + (this.id << 10));
		}

		Model var11 = (Model) ObjectDefinition_cachedModels.get(var9);
		if (var11 == null) {
			ModelData var12 = this.getModelData(var1, var2);
			if (var12 == null) {
				return null;
			}

			var11 = var12.toModel(this.ambient + 64, this.contrast + 768, -50, -10, -50);
			ObjectDefinition_cachedModels.put(var11, var9);
		}

		if (var7 == null && this.clipType * 65536 == -1) {
			return var11;
		} else {
			if (var7 != null) {
				var11 = var7.transformObjectModel(var11, var8, var2);
			} else {
				var11 = var11.toSharedSequenceModel(true);
			}

			if (this.clipType * 65536 >= 0) {
				var11 = var11.contourGround(var3, var4, var5, var6, false, this.clipType * 65536);
			}

			return var11;
		}
	}

	public Renderable getEntity(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
		long var7;
		if (this.models == null) {
			var7 = (long) (var2 + (this.id << 10));
		} else {
			var7 = (long) (var2 + (var1 << 3) + (this.id << 10));
		}

		Object var9 = (Renderable) ObjectDefinition_cachedEntities.get(var7);
		if (var9 == null) {
			ModelData var10 = this.getModelData(var1, var2);
			if (var10 == null) {
				return null;
			}

			if (!this.nonFlatShading) {
				var9 = var10.toModel(this.ambient + 64, this.contrast + 768, -50, -10, -50);
			} else {
				var10.ambient = (short)(this.ambient + 64);
				var10.contrast = (short)(this.contrast + 768);
				var10.calculateVertexNormals();
				var9 = var10;
			}

			ObjectDefinition_cachedEntities.put((DualNode)var9, var7);
		}

		if (this.nonFlatShading) {
			var9 = ((ModelData)var9).copyModelData();
		}

		if (this.clipType * 65536 >= 0) {
			if (var9 instanceof Model) {
				var9 = ((Model)var9).contourGround(var3, var4, var5, var6, true, this.clipType * 65536);
			} else if (var9 instanceof ModelData) {
				var9 = ((ModelData)var9).method4239(var3, var4, var5, var6, true, this.clipType * 65536);
			}
		}

		return (Renderable) var9;
	}

	public final Model getModel(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
		long var7;
		if (this.models == null) {
			var7 = (long)(var2 + (this.id << 10));
		} else {
			var7 = (long)(var2 + (var1 << 3) + (this.id << 10));
		}

		Model var9 = (Model) ObjectDefinition_cachedModels.get(var7);
		if (var9 == null) {
			ModelData var10 = this.getModelData(var1, var2);
			if (var10 == null) {
				return null;
			}

			var9 = var10.toModel(this.ambient + 64, this.contrast + 768, -50, -10, -50);
			ObjectDefinition_cachedModels.put(var9, var7);
		}

		if (this.clipType * 65536 >= 0) {
			var9 = var9.contourGround(var3, var4, var5, var6, true, this.clipType * 65536);
		}

		return var9;
	}

	ModelData getModelData(int var1, int var2) {
			ModelData var3 = null;
			boolean var4;
			int var5;
			int var7;
			if (this.models == null) {
				if (var1 != 10) {
					return null;
				}

				if (this.modelIds == null) {
					return null;
				}

				var4 = this.isRotated;
				if (var1 == 2 && var2 > 3) {
					var4 = !var4;
				}

				var5 = this.modelIds.length;

				for (int var6 = 0; var6 < var5; ++var6) {
					var7 = this.modelIds[var6];
					if (var4) {
						var7 += 65536;
					}

					var3 = (ModelData) ObjectDefinition_cachedModelData.get((long) var7);
					if (var3 == null) {
						var3 = ModelData.ModelData_get(Js5List.models, var7 & 65535, 0);
						if (var3 == null) {
							return null;
						}

						if (var4) {
							var3.method4306();
						}

						ObjectDefinition_cachedModelData.put(var3, (long) var7);
					}

					if (var5 > 1) {
						modelDataArray[var6] = var3;
					}
				}

				if (var5 > 1) {
					var3 = new ModelData(modelDataArray, var5);
				}
			} else {
					int var9 = -1;

					for (var5 = 0; var5 < this.models.length; ++var5) {
						if (this.models[var5] == var1) {
							var9 = var5;
							break;
						}
					}

					if (var9 == -1) {
						return null;
					}


					var5 = this.modelIds[var9];
					boolean var10 = this.isRotated ^ var2 > 3;
					if (var10) {
						var5 += 65536;
					}

					var3 = (ModelData) ObjectDefinition_cachedModelData.get((long) var5);
					if (var3 == null) {
						var3 = ModelData.get(var5 & 65535, 0);
						if (var3 == null) {
							return null;
						}

						if (var10) {
							var3.method4306();
						}

						ObjectDefinition_cachedModelData.put(var3, (long) var5);
					}
			}

			if (this.modelSizeX == 128 && this.modelHeight == 128 && this.modelSizeY == 128) {
				var4 = false;
			} else {
				var4 = true;
			}

			boolean var11;
			if (this.offsetX == 0 && this.offsetHeight == 0 && this.offsetY == 0) {
				var11 = false;
			} else {
				var11 = true;
			}

			ModelData var8 = new ModelData(var3, var2 == 0 && !var4 && !var11, this.recolorFrom == null, null == this.retextureFrom, true);
			if (var1 == 4 && var2 > 3) {
				var8.method4244(256);
				var8.changeOffset(45, 0, -45);
			}

			var2 &= 3;
			if (var2 == 1) {
				var8.method4281();
			} else if (var2 == 2) {
				var8.method4242();
			} else if (var2 == 3) {
				var8.method4243();
			}

			if (this.recolorFrom != null) {
				for (var7 = 0; var7 < this.recolorFrom.length; ++var7) {
					var8.recolor((short) this.recolorFrom[var7], (short) this.recolorTo[var7]);
				}
			}

			if (this.retextureFrom != null) {
				for (var7 = 0; var7 < this.retextureFrom.length; ++var7) {
					var8.retexture(this.retextureFrom[var7], this.retextureTo[var7]);
				}
			}

			if (var4) {
				var8.resize(this.modelSizeX, this.modelHeight, this.modelSizeY);
			}

			if (var11) {
				var8.changeOffset(this.offsetX, this.offsetHeight, this.offsetY);
			}
		return var8;
	}


	public final boolean needsModelFiles() {
		if (this.modelIds == null) {
			return true;
		} else {
			boolean var1 = true;

			for (int var2 = 0; var2 < this.modelIds.length; ++var2) {
				boolean loaded = Js5List.models.tryLoadFile(this.modelIds[var2] & 65535, 0);
                if (!loaded) {
                    System.out.println("Object " + this.id + " (" + this.name + ") missing model: " + (this.modelIds[var2] & 65535));
                }
                var1 &= loaded;
            }

			return var1;
		}
	}


	public ObjectDefinition method580() {
		int i = -1;
		if (transformVarbit != -1) {
			VariableBits varBit = VariableBits.lookup(transformVarbit);
			int j = varBit.baseVar;
			int k = varBit.startBit;
			int l = varBit.endBit;
			int i1 = Client.anIntArray1232[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (transformVarp != -1)
			i = clientInstance.variousSettings[transformVarp];
		int var3;
		if (i >= 0 && i < transforms.length)
			var3 = transforms[i];
		else
			var3 = transforms[transforms.length - 1];
		return var3 == -1 ? null : lookup(var3);
	}

	void decode(Buffer buffer) {
		try {
			while(true) {
				int var2 = buffer.readUnsignedByte();
				if (var2 == 0) {
					return;
				}

				this.decodeNext(buffer, var2);
			}
		} catch (Exception e) {
			System.out.println("Error decoding object ID: " + this.id + ", buffer length: " + buffer.payload.length + ", position: " + buffer.currentPosition);
		}
	}

	void processOp(Buffer buffer, int opcode) {
		int var3;
		int var4;
		if (opcode == 1) {
			var3 = buffer.readUnsignedByte();
			if (var3 > 0) {
				if (this.modelIds != null && !lowMem) {
					buffer.currentPosition += var3 * 3;
				} else {
					this.models = new int[var3];
					this.modelIds = new int[var3];

					for(var4 = 0; var4 < var3; ++var4) {
						this.modelIds[var4] = buffer.readUShort();
						this.models[var4] = buffer.readUnsignedByte();
					}
				}
			}
		} else if (opcode == 2) {
			this.name = buffer.readStringCp1252NullTerminated();
		} else if (opcode == 5) {
			var3 = buffer.readUnsignedByte();
			if (var3 > 0) {
				if (this.modelIds != null && !lowMem) {
					buffer.currentPosition += var3 * 3;
				} else {
					this.models = null;
					this.modelIds = new int[var3];

					for(var4 = 0; var4 < var3; ++var4) {
						this.modelIds[var4] = buffer.readUShort();
					}
				}
			}
		} else if (opcode == 14) {
			this.sizeX = buffer.readUnsignedByte();
		} else if (opcode == 15) {
			this.sizeY = buffer.readUnsignedByte();
		} else if (opcode == 17) {
			this.interactType = 0;
			this.boolean1 = false;
		} else if (opcode == 18) {
			this.boolean1 = false;
		} else if (opcode == 19) {
			int1 = buffer.readUnsignedByte();
		} else if (opcode == 21) {
			this.clipType = 0;
		} else if (opcode == 22) {
			this.nonFlatShading = true;
		} else if (opcode == 23) {
			this.modelClipped = true;
		} else if (opcode == 24) {
			this.animation = buffer.readUShort();
			if (this.animation == 65535) {
				this.animation = -1;
			}
		} else if (opcode == 27) {
			this.interactType = 1;
		} else if (opcode == 28) {
			this.int2 = buffer.readUnsignedByte();
		} else if (opcode == 29) {
			this.ambient = buffer.readSignedByte();
		} else if (opcode == 39) {
			this.contrast = buffer.readSignedByte() * 25;
		} else if (opcode >= 30 && opcode < 35) {
			this.actions[opcode - 30] = buffer.readStringCp1252NullTerminated();
			if (this.actions[opcode - 30].equalsIgnoreCase("Hidden")) {
				this.actions[opcode - 30] = null;
			}
		} else if (opcode == 40) {
			var3 = buffer.readUnsignedByte();
			this.recolorFrom = new int[var3];
			this.recolorTo = new int[var3];

			for(var4 = 0; var4 < var3; ++var4) {
				this.recolorFrom[var4] = (short)buffer.readUShort();
				this.recolorTo[var4] = (short)buffer.readUShort();
			}
		} else if (opcode == 41) {
			var3 = buffer.readUnsignedByte();
			this.retextureFrom = new short[var3];
			this.retextureTo = new short[var3];

			for(var4 = 0; var4 < var3; ++var4) {
				this.retextureFrom[var4] = (short)buffer.readUShort();
				this.retextureTo[var4] = (short)buffer.readUShort();
			}
		} else if (opcode == 61) {
			buffer.readUShort();
		} else if (opcode == 62) {
			this.isRotated = true;
		} else if (opcode == 64) {
			this.clipped = false;
		} else if (opcode == 65) {
			this.modelSizeX = buffer.readUShort();
		} else if (opcode == 66) {
			this.modelHeight = buffer.readUShort();
		} else if (opcode == 67) {
			this.modelSizeY = buffer.readUShort();
		} else if (opcode == 68) {
			this.mapscene = buffer.readUShort();
		} else if (opcode == 69) {
			surroundings = buffer.readUnsignedByte();
		} else if (opcode == 70) {
			this.offsetX = buffer.readShort();
		} else if (opcode == 71) {
			this.offsetHeight = buffer.readShort();
		} else if (opcode == 72) {
			this.offsetY = buffer.readShort();
		} else if (opcode == 73) {
			this.boolean2 = true;
		} else if (opcode == 74) {
			this.isSolid = true;
		} else if (opcode == 75) {
			this.int3 = buffer.readUnsignedByte();
		} else if (opcode != 77 && opcode != 92) {
			if (opcode == 78) {
				this.soundId = buffer.readUShort();
				this.soundRange = buffer.readUnsignedByte();
				this.soundRetain = usePre220Sounds ? 0: buffer.readUnsignedByte();

			} else if (opcode == 79) {
				this.soundMin = buffer.readUShort();
				this.soundMax = buffer.readUShort();
				this.soundRange = buffer.readUnsignedByte();
				this.soundRetain = usePre220Sounds ? 0: buffer.readUnsignedByte();

				var3 = buffer.readUnsignedByte();
				this.soundEffectIds = new int[var3];

				for(var4 = 0; var4 < var3; ++var4) {
					this.soundEffectIds[var4] = buffer.readUShort();
				}
			} else if (opcode == 81) {
				this.clipType = buffer.readUnsignedByte() * 256;
			} else if (opcode == 82) {
				this.minimapFunction = buffer.readUShort();
			} else if (opcode == 89) {
				this.boolean3 = false;
			}else if(opcode == 90){
				field2222 = true;
			} else if (opcode == 91) {
				field2394 = class358.method7617(buffer.readUnsignedByte());
			} else if (opcode == 93) {
				this.field2365 = class358.method7617(buffer.readUnsignedByte());
				this.field2395 = buffer.readUnsignedShort();
				this.field2342 = class358.method7617(buffer.readUnsignedByte());
				this.field2396 = buffer.readUnsignedShort();
			} else if (opcode == 95)  {
				class402[] opcoded = new class402[]{class402.field4863, class402.field4861, class402.field4868};
				this.field2334 = (class402)Client.findEnumerated(opcoded, buffer.readUnsignedByte());
			} else if (opcode == 249) {
				this.params = Buffer.readStringIntParameters(buffer, this.params);
			}
		} else {
			this.transformVarbit = buffer.readUShort();
			if (this.transformVarbit == 65535) {
				this.transformVarbit = -1;
			}

			this.transformVarp = buffer.readUShort();
			if (this.transformVarp == 65535) {
				this.transformVarp = -1;
			}

			var3 = -1;
			if (opcode == 92) {
				var3 = buffer.readUShort();
				if (var3 == 65535) {
					var3 = -1;
				}
			}

			var4 = buffer.readUnsignedByte();
			this.transforms = new int[var4 + 2];

			for(int var5 = 0; var5 <= var4; ++var5) {
				this.transforms[var5] = buffer.readUShort();
				if (this.transforms[var5] == 65535) {
					this.transforms[var5] = -1;
				}
			}

			this.transforms[var4 + 1] = var3;
		}

	}

	public int soundRange = 0;


	private ObjectDefinition() {
		id = -1;
	}

	private short[] retextureFrom;
	private short[] retextureTo;
	public boolean boolean2;
	@SuppressWarnings("unused")
	private int contrast;
	@SuppressWarnings("unused")
	private int ambient;
	private int offsetX;
	public String name;
	private int modelSizeY;

	public int sizeX;
	private int offsetHeight;
	public int minimapFunction;
	private int[] recolorTo;
	private int modelSizeX;
	public int transformVarbit;
	private boolean isRotated;
	public static boolean lowMem;

	public int id;

	public boolean boolean1;
	public int mapscene;
	public int transforms[];
	public int int3;
	public int sizeY;
	public int clipType;
	public boolean modelClipped;
	public static Client clientInstance;
	private boolean isSolid;
	public int interactType;
	public int surroundings;
	private boolean nonFlatShading;

	private int modelHeight;
	public int[] modelIds;
	public int soundId = -1;
	public int soundDistance = 0;
	public int soundMin = 0;
	public int soundRetain = 0;
	public int soundMax = 0;
	public int[] soundEffectIds;


	public IterableNodeHashTable params = null;

	public boolean boolean3;
	public int transformVarp;
	public int int2;
	private int[] models;
	public String description;
	public int int1;
	public boolean clipped;

	public int animation;

	private int offsetY;
	private int[] recolorFrom;
	public static EvictingDualNodeHashTable baseModels = new EvictingDualNodeHashTable(500);
	public String actions[] = new String[5];

	@Override
	public int getAccessBitMask() {
		return 0;
	}

	@Override
	public int getVarbitId() {
		return transformVarbit;
	}

	@Override
	public int getVarPlayerId() {
		return transformVarp;
	}

	@Override
	public int getIntValue(int paramID) {
		return 0;
	}

	@Override
	public void setValue(int paramID, int value) {

	}

	@Override
	public String getStringValue(int paramID) {
		return null;
	}

	@Override
	public void setValue(int paramID, String value) {

	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public String[] getActions() {
		return new String[0];
	}

	@Override
	public int getMapSceneId() {
		return 0;
	}

	@Override
	public int getMapIconId() {
		return 0;
	}

	@Override
	public int[] getImpostorIds() {
		return transforms;
	}

	@Override
	public RSObjectComposition getImpostor() {
		return method580();
	}

	@Override
	public RSIterableNodeHashTable getParams() {
		return null;
	}

	@Override
	public void setParams(IterableHashTable params) {

	}

	@Override
	public void setParams(RSIterableNodeHashTable params) {

	}

	@Override
	public void decodeNext(RSBuffer buffer, int opcode) {
		this.processOp((Buffer) buffer,opcode);
	}

	@Override
	public int[] getModelIds() {
		return new int[0];
	}

	@Override
	public void setModelIds(int[] modelIds) {

	}

	@Override
	public int[] getModels() {
		return new int[0];
	}

	@Override
	public void setModels(int[] models) {

	}

	@Override
	public boolean getObjectDefinitionIsLowDetail() {
		return false;
	}

	@Override
	public int getSizeX() {
		return this.sizeX;
	}

	@Override
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	@Override
	public int getSizeY() {
		return sizeY;
	}

	@Override
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	@Override
	public int getInteractType() {
		return 0;
	}

	@Override
	public void setInteractType(int interactType) {

	}

	@Override
	public boolean getBoolean1() {
		return false;
	}

	@Override
	public void setBoolean1(boolean boolean1) {

	}

	@Override
	public int getInt1() {
		return 0;
	}

	@Override
	public void setInt1(int int1) {

	}

	@Override
	public int getInt2() {
		return 0;
	}

	@Override
	public void setInt2(int int2) {

	}

	@Override
	public int getClipType() {
		return 0;
	}

	@Override
	public void setClipType(int clipType) {

	}

	@Override
	public boolean getNonFlatShading() {
		return nonFlatShading;
	}

	@Override
	public void setNonFlatShading(boolean nonFlatShading) {
		this.nonFlatShading = nonFlatShading;
	}

	@Override
	public void setModelClipped(boolean modelClipped) {

	}

	@Override
	public boolean getModelClipped() {
		return false;
	}

	@Override
	public int getAnimationId() {
		return 0;
	}

	@Override
	public void setAnimationId(int animationId) {

	}

	@Override
	public int getAmbient() {
		return 0;
	}

	@Override
	public void setAmbient(int ambient) {

	}

	@Override
	public int getContrast() {
		return 0;
	}

	@Override
	public void setContrast(int contrast) {

	}

	@Override
	public short[] getRecolorFrom() {
		return new short[0];
	}

	@Override
	public void setRecolorFrom(short[] recolorFrom) {

	}

	@Override
	public short[] getRecolorTo() {
		return new short[0];
	}

	@Override
	public void setRecolorTo(short[] recolorTo) {

	}

	@Override
	public short[] getRetextureFrom() {
		return new short[0];
	}

	@Override
	public void setRetextureFrom(short[] retextureFrom) {

	}

	@Override
	public short[] getRetextureTo() {
		return new short[0];
	}

	@Override
	public void setRetextureTo(short[] retextureTo) {

	}

	@Override
	public void setIsRotated(boolean rotated) {

	}

	@Override
	public boolean getIsRotated() {
		return false;
	}

	@Override
	public void setClipped(boolean clipped) {

	}

	@Override
	public boolean getClipped() {
		return false;
	}

	@Override
	public void setMapSceneId(int mapSceneId) {

	}

	@Override
	public void setModelSizeX(int modelSizeX) {

	}

	@Override
	public int getModelSizeX() {
		return 0;
	}

	@Override
	public void setModelHeight(int modelHeight) {

	}

	@Override
	public void setModelSizeY(int modelSizeY) {

	}

	@Override
	public void setOffsetX(int modelSizeY) {

	}

	@Override
	public void setOffsetHeight(int offsetHeight) {

	}

	@Override
	public void setOffsetY(int offsetY) {

	}

	@Override
	public void setInt3(int int3) {

	}

	@Override
	public void setInt5(int int5) {

	}

	@Override
	public void setInt6(int int6) {

	}

	@Override
	public void setInt7(int int7) {

	}

	@Override
	public void setBoolean2(boolean boolean2) {

	}

	@Override
	public void setIsSolid(boolean isSolid) {

	}

	@Override
	public void setAmbientSoundId(int ambientSoundId) {

	}

	@Override
	public void setSoundEffectIds(int[] soundEffectIds) {

	}

	@Override
	public int[] getSoundEffectIds() {
		return new int[0];
	}

	@Override
	public void setMapIconId(int mapIconId) {

	}

	@Override
	public void setBoolean3(boolean boolean3) {

	}

	@Override
	public void setTransformVarbit(int transformVarbit) {

	}

	@Override
	public int getTransformVarbit() {
		return 0;
	}

	@Override
	public void setTransformVarp(int transformVarp) {

	}

	@Override
	public int getTransformVarp() {
		return 0;
	}

	@Override
	public void setTransforms(int[] transforms) {

	}

	@Override
	public int[] getTransforms() {
		return transforms;
	}

	public boolean hasSound() {
		if (this.transforms == null) {
			return this.soundId != -1 || this.soundEffectIds != null;
		} else {
			for (int transform : this.transforms) {
				if (transform != -1) {
					ObjectDefinition var2 = lookup(transform);
					if (var2.soundId != -1 || var2.soundEffectIds != null) {
						return true;
					}
				}
			}
			return false;
		}
	}

}


