package com.client.definitions;

import com.client.Buffer;
import com.client.Client;
import com.client.Configuration;
import com.client.collection.EvictingDualNodeHashTable;
import com.client.collection.node.DualNode;
import com.client.collection.table.IterableNodeHashTable;
import com.client.definitions.anim.SequenceDefinition;
import com.client.entity.model.ModelData;
import com.client.entity.model.Model;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import com.client.model.Npcs;
import net.runelite.api.HeadIcon;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSIterableNodeHashTable;
import net.runelite.rs.api.RSNPCComposition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;


public final class NpcDefinition extends DualNode implements RSNPCComposition {

	public static EvictingDualNodeHashTable npcsCached = new EvictingDualNodeHashTable(64);

	private static int defaultHeadIconArchive = -1;
	private int height = -1;
	int[] field2032 = new int[]{1, 1, 1, 1, 1, 1};
	public int footprintSize;
	boolean field2156;
	int field2157;
	public static void init(int headIconArchive) {
		defaultHeadIconArchive = headIconArchive;
	}


	public static NpcDefinition get(int id) {
		NpcDefinition cachedNpc = (NpcDefinition)NpcDefinition.npcsCached.get(id);
		if (cachedNpc == null) {
			byte[] data = Js5List.configs.takeFile(Js5ConfigType.NPC, id);
			cachedNpc = new NpcDefinition();
			cachedNpc.npcId = id;
			if (data != null) {
				cachedNpc.decode(new Buffer(data));
			}
			if (cachedNpc.footprintSize == -1) {
				cachedNpc.footprintSize = (int)(0.4F * (float)(cachedNpc.size * 128));
			}
			switch(id){
				case 14817:
					//cachedNpc.models = new int[] { 7400 };
					cachedNpc = copy(cachedNpc, 14817, 1829, "Vote boss", new int[]{65034}, 1000, null, "Attack", null, null, null);
					cachedNpc.size = 9;
					cachedNpc.npcWidth = 350;
					cachedNpc.npcHeight = 350;
					return cachedNpc;
				case 11959:
					//cachedNpc.models = new int[] { 7400 };
					return copy(cachedNpc, 11959, 8633, "Legendary boss", new int[]{57142}, 1000, null, "Attack", null, null, null);
				case 14818:
					 copy(cachedNpc, 14818, 6633, "Vote boss jr", new int[]{65034}, 0, "Talk-to", null, "Pick-Up", null, null);
					cachedNpc.size = 1;
					cachedNpc.npcWidth = 50;
					cachedNpc.npcHeight = 50;
					cachedNpc.standingAnimation = 2561;
					cachedNpc.walkingAnimation = 2562;
					return cachedNpc;
				case 11960:
					return copy(cachedNpc, 11960, 9120, "Donation Shop 2", 0, "Trade", null, null, null, null);
				case 11961:
					//cachedNpc.models = new int[] { 7400 };
					return copy(cachedNpc, 11961, 11278, "Kaida Nex", new int[]{54083}, 200, null, "Attack", null, null, null);
				case 11962:
					//bat
					return copy(cachedNpc, 11962, 7692, "Scorched Mejrah", new int[]{56704}, 230, null, "Attack", null, null, null);
				case 11963:
					//blob
					return copy(cachedNpc, 11963, 7693, "Scorched Ak", new int[]{56705}, 460, null, "Attack", null, null, null);
				case 11964:
					//mager
					return copy(cachedNpc, 11964, 7699, "Scorched Zek", new int[]{56706}, 960, null, "Attack", null, null, null);
				case 11965:
					//mele
					return copy(cachedNpc, 11965, 7697, "Scorched Imkot", new int[]{56707}, 725, null, "Attack", null, null, null);
				case 11966:
					//range
					return copy(cachedNpc, 11966, 7702, "Scorched Xil", new int[]{56708}, 1345, null, "Attack", null, null, null);
				case 11967:
					return copy(cachedNpc, 11967, 904, "Big Brother Ryuu", new int[]{49543}, 540, null, "Attack", null, null, null);
				case 11968:
					return copy(cachedNpc, 11968, 7881, "Corrupted revenant imp", new int[]{63567}, 99, null, "Attack", null, null, null);
				case 11969:
					return copy(cachedNpc, 11969, 7939, "Corrupted revenant knight", new int[]{63568}, 500, null, "Attack", null, null, null);
				case 11970:
					return copy(cachedNpc, 11970, 7938, "Corrupted revenant dark beast", new int[]{63569}, 482, null, "Attack", null, null, null);
				case 11971:
					return copy(cachedNpc, 11971, 7940, "Corrupted revenant dragon", new int[]{63570}, 478, null, "Attack", null, null, null);
				case 11972:
					return copy(cachedNpc, 11972, 7932, "Corrupted revenant pryefiend", new int[]{63571}, 135, null, "Attack", null, null, null);
			/*case 11973:
				return copy(cachedNpc, 11973, 7936, "Corrupted revenant demon",new int[] {63572}, 328, null, "Attack", null, null, null);*/
				case 11975:
					return copy(cachedNpc, 2, 7937, "Corrupted revenant ork", new int[]{63573}, 220, null, "Attack", null, null, null);
				case 11976:
					return copy(cachedNpc, 11976, 7933, "Corrupted revenant hobgoblin", new int[]{63574}, 120, null, "Attack", null, null, null);
				case 11978:
					return copy(cachedNpc, 11978, 7934, "Corrupted revenant cyclops", new int[]{65037}, 289, null, "Attack", null, null, null);
				case 11979:
					return copy(cachedNpc, 11979, 7931, "Corrupted revenant goblin", new int[]{63576}, 106, null, "Attack", null, null, null);
				case 11980:
					return copy(cachedNpc, 11980, 11246, "Corrupted revenant maledictus", new int[]{63577}, 953, null, "Attack", null, null, null);
				case 11981:
					return copy(cachedNpc, 11981, 7935, "Corrupted revenant hellhound", new int[]{63578}, 257, null, "Attack", null, null, null);
				case 11982:
					return copy(cachedNpc, 11982, 2215, "Ravaged General Graardor", new int[]{65038, 65039}, 924, null, "Attack", null, null, null);
				case 11983:
					return copy(cachedNpc, 11983, 3162, "Ravaged Kree'arra", new int[]{65040, 65041, 65042}, 880, null, "Attack", null, null, null);
				case 11984:
					return copy(cachedNpc, 11984, 3129, "Ravaged K'ril Tsutsaroth", new int[]{65043, 65044, 65045, 65046, 65047}, 950, null, "Attack", null, null, null);
				case 11985:
					return copy(cachedNpc, 11985, 2205, "Ravaged Commander Zilyana", new int[]{65049, 65050, 65051, 65052, 65053}, 896, null, "Attack", null, null, null);
				case 11986:
					return copy(cachedNpc, 11986, 8565, "Ravaged Lizardman Shaman", new int[]{65048}, 275, null, "Attack", null, null, null);
				case 11988:
					return copy(cachedNpc, 11988, 7031, "AFK Horror", 1, null, "Attack", null, null, null);
				case 11990:
					return copy(cachedNpc, 11990, 7413, "Blood Zone Combat Dummy", new int[]{50517}, 500, null, "Attack", null, null, null);
				case 11991:
					return copy(cachedNpc, 11991, 10936, "Lil Night", new int[]{42551}, 0, "Pick-Up", null, null, null, null);
				case 11992:
					return copy(cachedNpc, 11992, 3129, "Dorgan", new int[]{51100, 51101, 51102, 51103, 51104}, 950, null, "Attack", null, null, null);
				case 11993:
					return copy(cachedNpc, 11993, 494, "Jack-o-Kraken", new int[]{56220}, 1254, null, "Attack", null, null, null);
				case 11994:
					return copy(cachedNpc, 11994, 494, "Jack-o-Kraken", new int[]{56220}, 1254, null, "Attack", null, null, null);
				case 11995:
					return copy(cachedNpc, 11995, 7795, "Minature Jack-o-bat", new int[]{56222}, 421, null, "Attack", null, null, null);
				case 11996:
					return copy(cachedNpc, 11996, 7795, "Giant Jack-o-bat", new int[]{56223}, 333, null, "Attack", null, null, null);
				case 11997:
					return copy(cachedNpc, 11997, 8096, "Donator Dragon", new int[]{65062}, 2000, null, "Attack", null, null, null);
				/*case 11998:
					return copy(cachedNpc, 11998, 3134, "Evil Sanata's Imp", new int[]{52858}, 2000, null, "Attack", null, null, null);*/
				case 11999:
					return copy(cachedNpc, 11999, 3134, "Evil Santa's Imp", new int[]{52859}, 2000, null, "Attack", null, null, null);

			}
			if (id == Npcs.BOB_BARTER_HERBS) {
				cachedNpc.actions = new String[]{"Talk-to", "Prices", "Decant", "Clean", null};
			}
			if (id == Npcs.ZAHUR)
				cachedNpc.actions[0] = "Trade";
			if (id == Npcs.JOSSIK) {
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Talk-to";
				cachedNpc.actions[2] = "Trade";
			}
			if (id == 9460 || id == 1150 || id == 2912 || id == 2911 || id == 2910 || id == 6481
					|| id == 3500 || id == 9459 || id == 9457 || id == 9458) {
				// Setting combat to zero to npcs that can't be attacked
				cachedNpc.combatLevel = 0;
			}
			if (id == Npcs.PERDU) {
				cachedNpc.actions = new String[]{"Talk-to", null, "Reclaim-lost", null, null};
			}
			if (id == 8184) {
				cachedNpc.name = "Theatre Of Blood Wizard";
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Teleport";
			}
			if (id == 7599) {
				//cachedNpc.models = new int[]{53080, 53081, 53082, 53083, 53084, 117085};
				//cachedNpc.models = new int[]{63120};
				cachedNpc.name = "Elvarg Rebirth Guide";
				cachedNpc.size = 1;
			}
			if (id == 4305) {
				cachedNpc.name = "Drunken cannoneer";
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Pickpocket";
			}
			if (id == 12029) {
				cachedNpc.name = "Lady Luck";
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Trade";
			}
			if (id == 12248) {
				cachedNpc.name = "Scorched-Custos";
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, null, "Talk-To", null, null};
				cachedNpc.size = 2;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
			}
			if (id == 10654) {
				cachedNpc.name = "Kaida Golem";
				cachedNpc.models = new int[]{56450};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 6;
				cachedNpc.combatLevel = 1539;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 195) {
				cachedNpc.name = "Small Golem";
				cachedNpc.models = new int[]{56450};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, null, "Pick-Up", null, null};
				cachedNpc.size = 2;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
				cachedNpc.standingAnimation = 8941;
				cachedNpc.walkingAnimation = 8939;
			}
			if (id == 10695) {
				cachedNpc.name = "Boulder Brute";
				cachedNpc.models = new int[]{51173};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 4;
				cachedNpc.combatLevel = 1539;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 10693) {
				cachedNpc.name = "Prism Paladin";
				cachedNpc.models = new int[]{51173};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 4;
				cachedNpc.combatLevel = 1539;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 10691) {
				cachedNpc.name = "Granite Guardian";
				cachedNpc.models = new int[]{51173};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 4;
				cachedNpc.combatLevel = 1539;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 10689) {
				cachedNpc.name = "Obsidian Monolith";
				cachedNpc.models = new int[]{51173};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 4;
				cachedNpc.combatLevel = 1539;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 11456) {
				cachedNpc.name = "Golem Reavers";
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 2;
				cachedNpc.combatLevel = 150;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
			}
			if (id == 3247) {
				cachedNpc.name = "Wizard";
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Teleport";
			}
			if (id == 6517) {
				cachedNpc.name = "Daily-reward wizard";
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Talk-to";
				cachedNpc.actions[2] = "View rewards";
			}
			if (id == 3428 || id == 3429) {
				cachedNpc.name = "Elf warrior";
			}
			if (id == 5044) { // sanfew (decant)
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Decant-potions";
			}
			if (id == 8026) {
				cachedNpc.combatLevel = 392;
			}
			if (id == 7913) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Ironman shop keeper";
				cachedNpc.description = "A shop specifically for iron men.";
			}
			if (id == 8906) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Santa's little elf";
				cachedNpc.description = "A helper sent from santa himself.";
				cachedNpc.actions = new String[]{"Talk-To", null, "Christmas Shop", "Return-Items", null};
			}
			if (id == 954) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Crystal Seed Trader";
				cachedNpc.description = "Use a seed on me to get a Crystal Bow.";

			}
			if (id == 6970) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Theif";
				cachedNpc.actions = new String[]{null, null, "Pickpocket", null, null};
			}
			if (id == 8761) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Assignment", "Trade", "Rewards"};

			}
			if (id == 9400) {
				cachedNpc.name = "Ted O'bombr";
			}
			if (id == 8026 || id == 8027 || id == 8028) {
				cachedNpc.size = 9;
			}
			if (id == 7954) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Achievement Master";
				cachedNpc.actions = new String[]{"Trade", null, "Open Achievements", null, null,};

			}
			if (id == 12599) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Assignment", "Trade", "Rewards"};

			}
			if (id == 3400) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Giveaway Manager";
				cachedNpc.actions = new String[]{"Open-manager", null, null, null, null};

			}
			if (id == 2690) {
				cachedNpc.name = "Skill Depot";
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 1013) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Gambler Shop";
				cachedNpc.description = "A shop specifically for gamblers.";
			}
			if (id == 308) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "PKP Manager";
			}
			if (id == 13) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Referral Tutor";
				cachedNpc.description = "He manages referrals.";
			}
			if (id == 5293) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Elven Keeper";
			}
			if (id == 3218 || id == 3217) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 2897) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Trading Post Manager";
				cachedNpc.actions = new String[]{"Open", null, "Collect", null, null};
			}
			if (id == 1306) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Make-over", null, null, null, null};
			}
			if (id == 3257) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 1011) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Item Gambler";
				cachedNpc.actions = new String[]{"Info", null, "Gamble", null, null};
			}
			if (id == 3248) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = Configuration.CLIENT_TITLE + " Wizard";
				cachedNpc.actions = new String[]{"Teleport", null, "Previous Location", null, null};
			}
			if (id == 1520) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Small Net", null, "Harpoon", null, null};
			}
			if (id == 8920) {

				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 8921) {
				cachedNpc.name = "Crystal Whirlwind";
			}
			if (id == 9120) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Donator Shop";
				cachedNpc.actions = new String[]{"Trade", null, "Rewards", null, null};
			}
			if (id == 2662) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Tournament Manager";
				cachedNpc.actions = new String[]{"Open-Shop", null, null, null, null};
			}
			if (id == 603) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Captain Kraken";
				cachedNpc.actions = new String[]{"Talk-to", null, null, null, null};
			}
			if (id == 7041) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Ticket Exchange";
				cachedNpc.actions = new String[]{"Exchange", null, null, null, null};
			}
			if (id == 3894) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Sigmund The Merchant";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}

			if (id == 7413) {
				cachedNpc.name = "Max Dummy";
				cachedNpc.actions[0] = null;
			}
			if (id == 9011) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Vote Shop";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 1933) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Mills";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 8819) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Boss point shop";
				cachedNpc.actions = new String[]{null, null, "Trade", null, null};
			}
			if (id == 8688) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Fat Tony";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 7769) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Shop Keeper";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 6987) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Man";
				cachedNpc.actions = new String[]{"Talk", null, "Pickpocket", null, null};
			}
			if (id == 5730) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Master Farmer";
				cachedNpc.actions = new String[]{"Pickpocket", null, "Trade", null, null};
			}
			if (id == 1501) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Hunter Store";
				cachedNpc.actions = new String[]{null, null, null, null, "Trade"};
			}
			if (id == 2913) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Fishing Store";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 5809) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Crafting and Tanner";
				cachedNpc.actions = new String[]{"Tan", null, "Trade", null, null};
			}
			if (id == 555) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Sell Me Store";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 9168) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Flex";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 8208) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Pet Collector";
				cachedNpc.actions = new String[]{"Talk-to", null, null, null, null};
			}
			if (id == 8202) {
				cachedNpc.actions = new String[]{"Talk-to", "Pick-Up", null, null, null};
			}
			if (id == 4921) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Supplies";
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 5314) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Mystical Wizard";
				cachedNpc.actions = new String[]{"Teleport", "Previous Location", null, null, null};
				cachedNpc.description = "This wizard has the power to teleport you to many locations.";
			}
			if (id == 8781) {
				cachedNpc.name = "@red@Queen Latsyrc";
				cachedNpc.combatLevel = 982;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 1577) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Melee Shop";
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 1576) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Range Shop";
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 1578) {
				cachedNpc.combatLevel = 0;
				cachedNpc.name = "Mage Shop";
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}
			if (id == 8026) {
				cachedNpc.name = "Vorkath";
				// cachedNpc.combatLevel = 732;
				cachedNpc.models = new int[]{35023};
				cachedNpc.standingAnimation = 7946;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Poke", null, null, null, null};
				cachedNpc.npcWidth = 100;
				cachedNpc.npcHeight = 100;
			}
			if (id == 7852 || id == 7853 || id == 7884) {//Dawn
				cachedNpc.standingAnimation = 7775;
				cachedNpc.walkingAnimation = 7775;
			}
			if (id == 5518) {
				cachedNpc.standingAnimation = 185;
			}
			if (id == 8019) {
				cachedNpc.standingAnimation = 185;
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Talk-to";
				cachedNpc.actions[2] = "Trade";
			}
			if (id == 308) {
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Talk-to";
				cachedNpc.actions[2] = "Trade";
				cachedNpc.actions[3] = "Disable Interface";
				cachedNpc.actions[4] = "Skull";
			}
			if (id == 6088) {
				cachedNpc.standingAnimation = 185;
				cachedNpc.actions = new String[5];
				cachedNpc.actions[0] = "Talk-to";
				cachedNpc.actions[2] = "Travel";
			}
			if (id == 1434 || id == 876 || id == 1612) {//gnome fix
				cachedNpc.standingAnimation = 185;
			}
			if (id == 7674 || id == 8009 || id == 388 || id == 8010) {

				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 8492 || id == 8493 || id == 8494 || id == 8495) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 8737 || id == 8738 || id == 8009 || id == 7674) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 326 || id == 327) {
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 85;
				cachedNpc.npcHeight = 85;
				cachedNpc.name = "Vote Pet";
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id >= 7354 && id <= 7367) {
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 5559 || id == 5560) {
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 2149 || id == 2150 || id == 2151 || id == 2148) {
				cachedNpc.name = "Trading Clerk";
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Bank", null, "Trading Post", null, null};
			}
			if (id == 6473) { //terror dog
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
			}
			if (id == 3510) { //outlast shop
				cachedNpc.name = "Trader";
				cachedNpc.combatLevel = 0;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Open-Shop", null, null, null, null};
			}

			if (id == 10685) { //barronite shop
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Trade", null, null, null, null};
			}

			if (id == 904) { //Ryuu Boss
				cachedNpc.name = "Big Brother Ryuu";
				cachedNpc.models = new int[]{63166};
				cachedNpc.combatLevel = 420;
				cachedNpc.size = 1;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Pick-Up", null, null, null, null};
			}

			if (id == 905) { //Ryuu Boss
				cachedNpc.name = "Big Brother Ryuu";
				cachedNpc.models = new int[]{63166};
				cachedNpc.combatLevel = 420;
				cachedNpc.size = 3;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 200; //WIDTH
				cachedNpc.npcHeight = 200; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 14817) {
				cachedNpc.name = "Vote Boss";
				//cachedNpc.combatLevel = 1000;
				cachedNpc.size = 9;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.standingAnimation = 2561;
				cachedNpc.walkingAnimation = 2562;
			}

			if (id == 9047) { //Slayer Boss 49543
				cachedNpc.name = "Queen Elvarg";
				cachedNpc.combatLevel = 980;
				cachedNpc.size = 8;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 300; //WIDTH
				cachedNpc.npcHeight = 300; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 197) { //Slayer Boss
				cachedNpc.name = "Princess Elvarg";
				cachedNpc.models = new int[]{38611};
				cachedNpc.size = 2;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, null, "Pick-Up", null, null};
				cachedNpc.standingAnimation = 27;
				cachedNpc.walkingAnimation = 79;
			}

			if (id == 1468) {
				cachedNpc.name = "Infected Monkey";
				cachedNpc.combatLevel = 720;
				cachedNpc.size = 1;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 200; //WIDTH
				cachedNpc.npcHeight = 200; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 7031) {
				cachedNpc.name = "Dono Horror";
				cachedNpc.combatLevel = 692;
				cachedNpc.size = 4;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
			}
			if (id == 488) { //rain cloud
				cachedNpc.combatLevel = 0;
				cachedNpc.size = 1;
				cachedNpc.onMinimap = true;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
			}
			if (id == 14183) { //voice of yama
				cachedNpc.name = "Kratos";
				cachedNpc.size = 2;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 90; //WIDTH
				cachedNpc.npcHeight = 90; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};

			}
			if (id == 1377) {
				cachedNpc.size = 3;
				cachedNpc.npcWidth = 300; //WIDTH
				cachedNpc.npcHeight = 300; // HEIGH
				cachedNpc.actions[0] = null;


			}
			if (id == 2105) {
				cachedNpc.size = 4;
				cachedNpc.npcWidth = 600; //WIDTH
				cachedNpc.npcHeight = 600; // HEIGH
			}
			if (id == 2107) {
				cachedNpc.size = 4;
				cachedNpc.npcWidth = 600; //WIDTH
				cachedNpc.npcHeight = 600; // HEIGH
			}
			if (id == 2850) {
				cachedNpc.name = "GIM Tracker";
				cachedNpc.actions = new String[]{"Open", null, null, null, null};

			}
			if (id == 6119) { //weird monster
				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 30; //WIDTH
				cachedNpc.npcHeight = 30; // HEIGH
			}
			if (id == 763) { //roc

				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 30; //WIDTH
				cachedNpc.npcHeight = 30; // HEIGH
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};


			}
			if (id == 9035 || id == 9036 || id == 9037 || id == 9038) {
				cachedNpc.name = "Bloody Hunllef";
			}
			if (id == 762) { //foe small bird
				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", "Metamorphosis", null};
			}
			if (id == 4987 || id == 6292 || id == 6354) { //chronzon
				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 45; //WIDTH
				cachedNpc.npcHeight = 45; // HEIGH
			}
			if (id == 8709) {
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.name = "Corrupt Beast";
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 60; //WIDTH
				cachedNpc.npcHeight = 60; // HEIGH
				cachedNpc.size = 1;
			}
			if (id == 7025) { //guard dog
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
			}

			if (id == 6716) {//prayer
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
				cachedNpc.combatLevel = 0;


			}
			if (id == 6723) {//healer
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
				cachedNpc.combatLevel = 0;

			}
			if (id == 1088) {
				cachedNpc.name = "Seren";
				cachedNpc.models = new int[]{38605};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8372;
				cachedNpc.walkingAnimation = 8372;
				cachedNpc.models = new int[]{38605};

			}
			if (id == 1089) {
				cachedNpc.name = "Lil mimic";
				cachedNpc.models = new int[]{37142};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 25; //WIDTH
				cachedNpc.npcHeight = 25; // HEIGH
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8307;
				cachedNpc.walkingAnimation = 8306;
				cachedNpc.models = new int[]{37142};

			}
			if (id == 2120) {
				cachedNpc.name = "Shadow Ranger";
				cachedNpc.models = new int[]{29267};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.models = new int[]{29267};

			}
			if (id == 2121) {
				cachedNpc.name = "Shadow Wizard";
				cachedNpc.models = new int[]{29268};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.models = new int[]{29268};
			}
			if (id == 2122) {
				cachedNpc.name = "Shadow Warrior";
				cachedNpc.models = new int[]{29266};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.models = new int[]{29266};
			}

			if (id == 7216 || id == 6473) {//green monkey and green dog
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
			}
			if (id == 6723 || id == 6716 || id == 8709) {
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
			}
			if (id == 3291) {
				cachedNpc.name = "Skull Raider";
				cachedNpc.models = new int[]{65063};
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
			}
			if (id == 5738) {//imp
				cachedNpc.name = "Clue Gremlin";
				cachedNpc.models = new int[]{65065};
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};

			}
			if (id == 5240) {//toucan
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};

			}
			if (id == 834) {
				cachedNpc.name = "King penguin";
				cachedNpc.actions = new String[5];
				cachedNpc.combatLevel = 0;
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};

			}
			if (id == 1873) {//klik
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.standingAnimation = 3345;
				cachedNpc.walkingAnimation = 3346;

			}
			//dark pets
			if (id == 2300) {
				cachedNpc.models = new int[1];
				cachedNpc.name = "Dark Skull Raider";
				cachedNpc.models = new int[]{65064};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 3948;
				cachedNpc.walkingAnimation = 3947;
			}
			if (id == 2301) {
				cachedNpc.name = "Dark Clue Gremlin";
				cachedNpc.models = new int[]{65066};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 171;
				cachedNpc.walkingAnimation = 168;
			}
			if (id == 2302) {
				cachedNpc.name = "Dark toucan";
				cachedNpc.models = new int[]{46800, 46801};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 6772;
				cachedNpc.walkingAnimation = 6774;
			}
			if (id == 2303) {
				cachedNpc.name = "Dark king penguin";
				cachedNpc.models = new int[]{46200};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 5668;
				cachedNpc.walkingAnimation = 5666;
			}
			if (id == 2304) {
				cachedNpc.name = "Dark k'klik";
				cachedNpc.models = new int[]{46300, 46301, 46302};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 3346;
				cachedNpc.walkingAnimation = -1;
			}
			if (id == 2305) {
				cachedNpc.name = "Dark shadow warrior";
				cachedNpc.models = new int[]{46100};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
			}
			if (id == 2306) {
				cachedNpc.name = "Dark shadow archer";
				cachedNpc.models = new int[]{56800};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
			}
			if (id == 2307) {
				cachedNpc.name = "Dark shadow wizard";
				cachedNpc.models = new int[]{45900};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8526;
				cachedNpc.walkingAnimation = 8527;
				cachedNpc.npcWidth = 85; //WIDTH
				cachedNpc.npcHeight = 85; // HEIGH
			}
			if (id == 2308) {
				cachedNpc.name = "Dark healer death spawn";
				cachedNpc.models = new int[]{46500, 46501, 46502, 46503, 46504, 46505, 46506,};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 1539;
				cachedNpc.walkingAnimation = 1539;
			}
			if (id == 2309) {
				cachedNpc.name = "Dark holy death spawn";
				cachedNpc.models = new int[]{46406, 46405, 46404, 46403, 46402, 46401, 46400};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 1539;
				cachedNpc.walkingAnimation = 1539;
			}
			if (id == 2310) {
				cachedNpc.name = "Dark seren";
				cachedNpc.models = new int[]{46900};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.standingAnimation = 8372;
				cachedNpc.walkingAnimation = 8372;
				cachedNpc.npcWidth = 65; //WIDTH
				cachedNpc.npcHeight = 65; // HEIGH
			}
			if (id == 2311) {
				cachedNpc.name = "Dark corrupt beast";
				cachedNpc.models = new int[]{45710};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 60; //WIDTH
				cachedNpc.npcHeight = 60; // HEIGH
				cachedNpc.size = 1;
				cachedNpc.standingAnimation = 5616;
				cachedNpc.walkingAnimation = 5615;
			}
			if (id == 2312) {
				cachedNpc.name = "Dark roc";
				cachedNpc.models = new int[]{45600, 45601};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.standingAnimation = 5021;
				cachedNpc.walkingAnimation = 5022;
				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 30; //WIDTH
				cachedNpc.npcHeight = 30; // HEIGH
			}
			if (id == 2313) {
				cachedNpc.name = "Dark kratos";
				cachedNpc.models = new int[]{45500, 45501, 45502};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Talk-to", null, "Pick-Up", null, null};
				cachedNpc.originalColors = null;
				cachedNpc.newColors = null;
				cachedNpc.standingAnimation = 7017;
				cachedNpc.walkingAnimation = 7016;
				cachedNpc.size = 2;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 90; //WIDTH
				cachedNpc.npcHeight = 90; // HEIGH
			}
			if (id == 8027) {
				cachedNpc.name = "Vorkath";
				cachedNpc.combatLevel = 732;
				cachedNpc.models = new int[]{35023};
				cachedNpc.standingAnimation = 7950;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, null, null, null, null};
				cachedNpc.npcWidth = 100;
				cachedNpc.npcHeight = 100;
			}
			if (id == 8028) {
				cachedNpc.name = "Vorkath";
				cachedNpc.combatLevel = 732;
				cachedNpc.models = new int[]{35023};
				cachedNpc.standingAnimation = 7948;
				cachedNpc.onMinimap = true;
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.npcWidth = 100;
				cachedNpc.npcHeight = 100;
			}
			if (id == 7144) {
				cachedNpc.anInt75 = 0;
			}
			if (id == 963) {
				cachedNpc.anInt75 = 6;
			}
			if (id == 7145) {
				cachedNpc.anInt75 = 1;
			}
			if (id == 7146) {
				cachedNpc.anInt75 = 2;
			}
			if (id == 3598) {
				cachedNpc.name = "Varg";
				cachedNpc.models = new int[]{65035};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 5;
				cachedNpc.combatLevel = 3479;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
				cachedNpc.standingAnimation = 8267;
				cachedNpc.walkingAnimation = 8267;
			}
			if (id == 3596) {
				cachedNpc.name = "Varg";
				cachedNpc.models = new int[]{65036};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{null, "Attack", null, null, null};
				cachedNpc.size = 5;
				cachedNpc.combatLevel = 3479;
				cachedNpc.npcWidth = 150; //WIDTH
				cachedNpc.npcHeight = 150; // HEIGH
				cachedNpc.standingAnimation = 8267;
				cachedNpc.walkingAnimation = 8267;
			}
			if (id == 912) {
				cachedNpc.name = "Lil varg";
				cachedNpc.models = new int[]{65035};
				cachedNpc.actions = new String[5];
				cachedNpc.actions = new String[]{"Pick-up", null, null, null, null};
				cachedNpc.size = 1;
				cachedNpc.combatLevel = 0;
				cachedNpc.npcWidth = 50; //WIDTH
				cachedNpc.npcHeight = 50; // HEIGH
				cachedNpc.standingAnimation = 8266;
				cachedNpc.walkingAnimation = 8266;
			}
			if (cachedNpc.name != null && cachedNpc.name.toLowerCase().contains("chinchompa") && !cachedNpc.name.toLowerCase().contains("baby")) {
				cachedNpc.actions = new String[5];
			}
			npcsCached.put(cachedNpc, id);
		}
        return cachedNpc;
    }
	public static NpcDefinition copy(NpcDefinition itemDef, int newId, int copyingItemId, String newName, int[] models, int combatLevel, String... actions) {
		NpcDefinition copyItemDef = get(copyingItemId);
		itemDef.npcId = newId;
		itemDef.name = newName;
		itemDef.description = copyItemDef.description;
		itemDef.models = models;
		itemDef.size = copyItemDef.size;
		itemDef.standingAnimation = copyItemDef.standingAnimation;
		itemDef.walkingAnimation = copyItemDef.walkingAnimation;
		itemDef.rotate180AnimIndex = copyItemDef.rotate180AnimIndex;
		itemDef.rotate90CCWAnimIndex = copyItemDef.rotate90CCWAnimIndex;
		itemDef.rotate90CWAnimIndex = copyItemDef.rotate90CWAnimIndex;
		itemDef.originalColors = copyItemDef.originalColors;
		itemDef.newColors = copyItemDef.newColors;
		itemDef.originalTextures = copyItemDef.originalTextures;
		itemDef.newTextures = copyItemDef.newTextures;
		itemDef.chatheadModels = copyItemDef.chatheadModels;
		itemDef.onMinimap = copyItemDef.onMinimap;
		itemDef.combatLevel = combatLevel;
		itemDef.npcHeight = copyItemDef.npcHeight;
		itemDef.npcWidth = copyItemDef.npcWidth;
		itemDef.aBoolean93 = copyItemDef.aBoolean93;
		itemDef.ambient = copyItemDef.ambient;
		itemDef.contrast = copyItemDef.contrast;
		itemDef.headIconSpriteIndex = copyItemDef.headIconSpriteIndex;
		itemDef.headIconArchiveIds = copyItemDef.headIconArchiveIds;
		itemDef.rotationSpeed = copyItemDef.rotationSpeed;
		itemDef.varbitId = copyItemDef.varbitId;
		itemDef.varpIndex = copyItemDef.varpIndex;
		itemDef.configs = copyItemDef.configs;
		itemDef.clickable = copyItemDef.clickable;
		itemDef.isClickable = copyItemDef.isClickable;
		itemDef.isPet = copyItemDef.isPet;
		itemDef.field1914 = copyItemDef.field1914;
		itemDef.field1919 = copyItemDef.field1919;
		itemDef.field1918 = copyItemDef.field1918;
		itemDef.field1938 = copyItemDef.field1938;
		itemDef.field1920 = copyItemDef.field1920;
		itemDef.field1933 = copyItemDef.field1933;
		itemDef.field1922 = copyItemDef.field1922;
		itemDef.field1923 = copyItemDef.field1923;
		itemDef.actions = new String[10];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.actions, 0, actions.length);
		}
		return itemDef;
	}

	public static NpcDefinition copy(NpcDefinition itemDef, int newId, int copyingItemId, String newName, int combatLevel, String... actions) {
		NpcDefinition copyItemDef = get(copyingItemId);
		itemDef.npcId = newId;
		itemDef.name = newName;
		itemDef.description = copyItemDef.description;
		itemDef.models = copyItemDef.models;
		itemDef.size = copyItemDef.size;
		itemDef.standingAnimation = copyItemDef.standingAnimation;
		itemDef.walkingAnimation = copyItemDef.walkingAnimation;
		itemDef.rotate180AnimIndex = copyItemDef.rotate180AnimIndex;
		itemDef.rotate90CCWAnimIndex = copyItemDef.rotate90CCWAnimIndex;
		itemDef.rotate90CWAnimIndex = copyItemDef.rotate90CWAnimIndex;
		itemDef.originalColors = copyItemDef.originalColors;
		itemDef.newColors = copyItemDef.newColors;
		itemDef.originalTextures = copyItemDef.originalTextures;
		itemDef.newTextures = copyItemDef.newTextures;
		itemDef.chatheadModels = copyItemDef.chatheadModels;
		itemDef.onMinimap = copyItemDef.onMinimap;
		itemDef.combatLevel = combatLevel;
		itemDef.npcHeight = copyItemDef.npcHeight;
		itemDef.npcWidth = copyItemDef.npcWidth;
		itemDef.aBoolean93 = copyItemDef.aBoolean93;
		itemDef.ambient = copyItemDef.ambient;
		itemDef.contrast = copyItemDef.contrast;
		itemDef.headIconSpriteIndex = copyItemDef.headIconSpriteIndex;
		itemDef.headIconArchiveIds = copyItemDef.headIconArchiveIds;
		itemDef.rotationSpeed = copyItemDef.rotationSpeed;
		itemDef.varbitId = copyItemDef.varbitId;
		itemDef.varpIndex = copyItemDef.varpIndex;
		itemDef.configs = copyItemDef.configs;
		itemDef.clickable = copyItemDef.clickable;
		itemDef.isClickable = copyItemDef.isClickable;
		itemDef.isPet = copyItemDef.isPet;
		itemDef.field1914 = copyItemDef.field1914;
		itemDef.field1919 = copyItemDef.field1919;
		itemDef.field1918 = copyItemDef.field1918;
		itemDef.field1938 = copyItemDef.field1938;
		itemDef.field1920 = copyItemDef.field1920;
		itemDef.field1933 = copyItemDef.field1933;
		itemDef.field1922 = copyItemDef.field1922;
		itemDef.field1923 = copyItemDef.field1923;
		itemDef.actions = copyItemDef.actions;
		itemDef.actions = new String[10];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.actions, 0, actions.length);
		}
		return itemDef;
	}

	public static NpcDefinition copy(NpcDefinition itemDef, int newId, int copyingItemId, String newName, int combatLevel, int npcHeight, int npcWidth, String... actions) {
		NpcDefinition copyItemDef = get(copyingItemId);
		itemDef.npcId = newId;
		itemDef.name = newName;
		itemDef.description = copyItemDef.description;
		itemDef.models = copyItemDef.models;
		itemDef.size = copyItemDef.size;
		itemDef.standingAnimation = copyItemDef.standingAnimation;
		itemDef.walkingAnimation = copyItemDef.walkingAnimation;
		itemDef.rotate180AnimIndex = copyItemDef.rotate180AnimIndex;
		itemDef.rotate90CCWAnimIndex = copyItemDef.rotate90CCWAnimIndex;
		itemDef.rotate90CWAnimIndex = copyItemDef.rotate90CWAnimIndex;
		itemDef.originalColors = copyItemDef.originalColors;
		itemDef.newColors = copyItemDef.newColors;
		itemDef.originalTextures = copyItemDef.originalTextures;
		itemDef.newTextures = copyItemDef.newTextures;
		itemDef.chatheadModels = copyItemDef.chatheadModels;
		itemDef.onMinimap = copyItemDef.onMinimap;
		itemDef.combatLevel = combatLevel;
		itemDef.npcHeight = npcHeight;
		itemDef.npcWidth = npcWidth;
		itemDef.aBoolean93 = copyItemDef.aBoolean93;
		itemDef.ambient = copyItemDef.ambient;
		itemDef.contrast = copyItemDef.contrast;
		itemDef.headIconSpriteIndex = copyItemDef.headIconSpriteIndex;
		itemDef.headIconArchiveIds = copyItemDef.headIconArchiveIds;
		itemDef.rotationSpeed = copyItemDef.rotationSpeed;
		itemDef.varbitId = copyItemDef.varbitId;
		itemDef.varpIndex = copyItemDef.varpIndex;
		itemDef.configs = copyItemDef.configs;
		itemDef.clickable = copyItemDef.clickable;
		itemDef.isClickable = copyItemDef.isClickable;
		itemDef.isPet = copyItemDef.isPet;
		itemDef.field1914 = copyItemDef.field1914;
		itemDef.field1919 = copyItemDef.field1919;
		itemDef.field1918 = copyItemDef.field1918;
		itemDef.field1938 = copyItemDef.field1938;
		itemDef.field1920 = copyItemDef.field1920;
		itemDef.field1933 = copyItemDef.field1933;
		itemDef.field1922 = copyItemDef.field1922;
		itemDef.field1923 = copyItemDef.field1923;
		itemDef.actions = copyItemDef.actions;
		itemDef.actions = new String[10];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.actions, 0, actions.length);
		}
		return itemDef;
	}

	public static NpcDefinition copy2(NpcDefinition itemDef, int newId, int copyingItemId, String newName, int combatLevel, int[] models, int[] newcolors, String... actions) {
		NpcDefinition copyItemDef = get(copyingItemId);
		itemDef.npcId = newId;
		itemDef.name = newName;
		itemDef.description = copyItemDef.description;
		itemDef.models = models;
		itemDef.size = copyItemDef.size;
		itemDef.standingAnimation = copyItemDef.standingAnimation;
		itemDef.walkingAnimation = copyItemDef.walkingAnimation;
		itemDef.rotate180AnimIndex = copyItemDef.rotate180AnimIndex;
		itemDef.rotate90CCWAnimIndex = copyItemDef.rotate90CCWAnimIndex;
		itemDef.rotate90CWAnimIndex = copyItemDef.rotate90CWAnimIndex;
		itemDef.originalColors = copyItemDef.originalColors;
		itemDef.newColors = newcolors;
		itemDef.originalTextures = copyItemDef.originalTextures;
		itemDef.newTextures = copyItemDef.newTextures;
		itemDef.chatheadModels = copyItemDef.chatheadModels;
		itemDef.onMinimap = copyItemDef.onMinimap;
		itemDef.combatLevel = combatLevel;
		itemDef.npcHeight = copyItemDef.npcHeight;
		itemDef.npcWidth = copyItemDef.npcWidth;
		itemDef.aBoolean93 = copyItemDef.aBoolean93;
		itemDef.ambient = copyItemDef.ambient;
		itemDef.contrast = copyItemDef.contrast;
		itemDef.headIconSpriteIndex = copyItemDef.headIconSpriteIndex;
		itemDef.headIconArchiveIds = copyItemDef.headIconArchiveIds;
		itemDef.rotationSpeed = copyItemDef.rotationSpeed;
		itemDef.varbitId = copyItemDef.varbitId;
		itemDef.varpIndex = copyItemDef.varpIndex;
		itemDef.configs = copyItemDef.configs;
		itemDef.clickable = copyItemDef.clickable;
		itemDef.isClickable = copyItemDef.isClickable;
		itemDef.isPet = copyItemDef.isPet;
		itemDef.field1914 = copyItemDef.field1914;
		itemDef.field1919 = copyItemDef.field1919;
		itemDef.field1918 = copyItemDef.field1918;
		itemDef.field1938 = copyItemDef.field1938;
		itemDef.field1920 = copyItemDef.field1920;
		itemDef.field1933 = copyItemDef.field1933;
		itemDef.field1922 = copyItemDef.field1922;
		itemDef.field1923 = copyItemDef.field1923;
		itemDef.actions = copyItemDef.actions;
		itemDef.actions = new String[10];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.actions, 0, actions.length);
		}
		return itemDef;
	}
	public int[] headIconArchiveIds;
	public short[] headIconSpriteIndex;
	public int field1919 = -1;
	public int field1918 = -1;
	public int field1938 = -1;
	public int field1920 = -1;
	public int field1933 = -1;
	public int field1914 = -1;
	public int field1922 = -1;
	public int field1923 = -1;

    public static int totalAmount;





    public int category;


	public int defaultHeadIconArchive1 = -1;
	public int rotateLeftAnimation = -1;
	public int rotateRightAnimation = -1;

	void decode(Buffer var1) {
		while(true) {
			int var2 = var1.readUnsignedByte();
			if (var2 == 0) {
				return;
			}

			this.decodeNext(var1, var2);
		}
	}

	void decodeNext(Buffer buffer, int var2) {
		int index;
		int var4;
		if (var2 == 1) {
			index = buffer.readUnsignedByte();
			models = new int[index];

			for(var4 = 0; var4 < index; ++var4) {
				models[var4] = buffer.readUShort();
			}
		} else if (var2 == 2) {
			name = buffer.readStringCp1252NullTerminated();
		} else if (var2 == 12) {
			size = buffer.readUnsignedByte();
		} else if (var2 == 13) {
			standingAnimation = buffer.readUShort();
		} else if (var2 == 14) {
			walkingAnimation = buffer.readUShort();
		} else if (var2 == 15) {
			readyanim_r = buffer.readUShort();
		} else if (var2 == 16) {
			readyanim_l = buffer.readUShort();
		} else if (var2 == 17) {
			walkingAnimation = buffer.readUShort();
			rotate180AnimIndex = buffer.readUShort();
			rotate90CWAnimIndex = buffer.readUShort();
			rotate90CCWAnimIndex = buffer.readUShort();
		} else if (var2 == 18) {
			category = buffer.readUShort();
		} else if (var2 >= 30 && var2 < 35) {
			actions[var2 - 30] = buffer.readStringCp1252NullTerminated();
			if (actions[var2 - 30].equalsIgnoreCase("Hidden")) {
				actions[var2 - 30] = null;
			}
		} else if (var2 == 40) {
			index = buffer.readUnsignedByte();
			originalColors = new int[index];
			newColors = new int[index];

			for(var4 = 0; var4 < index; ++var4) {
				originalColors[var4] = (short)buffer.readUShort();
				newColors[var4] = (short)buffer.readUShort();
			}
		} else if (var2 == 41) {
			index = buffer.readUnsignedByte();
			originalTextures = new short[index];
			newTextures = new short[index];

			for(var4 = 0; var4 < index; ++var4) {
				originalTextures[var4] = (short)buffer.readUShort();
				newTextures[var4] = (short)buffer.readUShort();
			}
		} else if (var2 == 60) {
			index = buffer.readUnsignedByte();
			chatheadModels = new int[index];

			for(var4 = 0; var4 < index; ++var4) {
				chatheadModels[var4] = buffer.readUShort();
			}
		} else if(var2 == 74) {
			this.field2032[0] = buffer.readUShort();
		} else if(var2 == 75) {
			this.field2032[1] = buffer.readUShort();
		} else if(var2 == 76) {
			this.field2032[2] = buffer.readUShort();
		} else if(var2 == 77) {
			this.field2032[3] = buffer.readUShort();
		} else if(var2 == 78) {
			this.field2032[4] = buffer.readUShort();
		} else if(var2 == 79) {
			this.field2032[5] = buffer.readUShort();
		}else if (var2 == 93) {
			onMinimap = false;
		} else if (var2 == 95) {
			combatLevel = buffer.readUShort();
		} else if (var2 == 97) {
			npcHeight = buffer.readUShort();
		} else if (var2 == 98) {
			npcWidth = buffer.readUShort();
		} else if (var2 == 99) {
			aBoolean93 = true;
		} else if (var2 == 100) {
			ambient = buffer.readSignedByte();
		} else if (var2 == 101) {
			contrast = buffer.readSignedByte();
		} else {
			int var5;
			if (var2 == 102) {
				index = buffer.readUnsignedByte();
				var4 = 0;

				for(var5 = index; var5 != 0; var5 >>= 1) {
					++var4;
				}

				headIconArchiveIds = new int[var4];
				headIconSpriteIndex = new short[var4];

				for(int var6 = 0; var6 < var4; ++var6) {
					if ((index & 1 << var6) == 0) {
						headIconArchiveIds[var6] = -1;
						headIconSpriteIndex[var6] = -1;
					} else {
						headIconArchiveIds[var6] = buffer.readNullableLargeSmart();
						headIconSpriteIndex[var6] = (short)buffer.readShortSmartSub();
					}
				}
			} else if (var2 == 103) {
				rotationSpeed = buffer.readUShort();
			} else if (var2 != 106 && var2 != 118) {
				if (var2 == 107) {
					clickable = false;
				} else if (var2 == 109) {
					isClickable = false;
				} else if (var2 == 111) {
					this.isPet = true;
				} else if (var2 == 114) {
					field1914 = buffer.readUShort();
				} else if (var2 == 115) {
					field1914 = buffer.readUShort();
					field1919 = buffer.readUShort();
					field1918 = buffer.readUShort();
					field1938 = buffer.readUShort();
				} else if (var2 == 116) {
					field1920 = buffer.readUShort();
				} else if (var2 == 117) {
					field1920 = buffer.readUShort();
					field1933 = buffer.readUShort();
					field1922 = buffer.readUShort();
					field1923 = buffer.readUShort();
				} else if (var2 == 122) {
					lowPriorityFollowerOps = true;
				} else if (var2 == 123) {
					isPet = true;
				} else if(var2 == 124) {
					height = buffer.readUShort();
				} else if(var2 == 126){
					this.footprintSize = buffer.readUShort();
				} else if(var2 == 145){
					this.field2156 = true;
				} else if(var2 == 146){
					this.field2157 = buffer.readUShort();
				}else if (var2 == 249) {
					params = Buffer.readStringIntParameters(buffer, params);
				}
			} else {
				varbitId = buffer.readUShort();
				if (varbitId == 65535) {
					varbitId = -1;
				}

				varpIndex = buffer.readUShort();
				if (varpIndex == 65535) {
					varpIndex = -1;
				}

				index = -1;
				if (var2 == 118) {
					index = buffer.readUShort();
					if (index == 65535) {
						index = -1;
					}
				}

				var4 = buffer.readUnsignedByte();
				configs = new int[var4 + 2];

				for(var5 = 0; var5 <= var4; ++var5) {
					configs[var5] = buffer.readUShort();
					if (configs[var5] == 65535) {
						configs[var5] = -1;
					}
				}

				configs[var4 + 1] = index;
			}
		}

	}

	public boolean isClickable = true;
	public boolean lowPriorityFollowerOps;
	public boolean isPet;
	IterableNodeHashTable params;

	public ModelData getDialogueModel() {
		if (this.configs != null) {
			NpcDefinition var2 = this.morph();
			return var2 == null ? null : var2.getDialogueModel();
		} else {
			return this.getModelData(this.chatheadModels);
		}
	}

	ModelData getModelData(int[] models) {

		if (models == null) {
			return null;
		} else {
			boolean cached = false;

			for (int i : models) {
				if (i != -1 && !Js5List.models.tryLoadFile(i, 0)) {
					cached = true;
				}
			}

			if (cached) {
				return null;
			} else {
				ModelData[] modelParts = new ModelData[models.length];

				for(int var6 = 0; var6 < models.length; ++var6) {
					modelParts[var6] = ModelData.getModel(models[var6]);
				}

				ModelData model;
				if (modelParts.length == 1) {
					model = modelParts[0];
					if (model == null) {
						model = new ModelData(modelParts, modelParts.length);
					}
				} else {
					model = new ModelData(modelParts, modelParts.length);
				}

				if (originalColors != null) {
					for (int k = 0; k < originalColors.length; k++)
						model.recolor((short) originalColors[k], (short) newColors[k]);
				}

				if (newTextures != null) {
					for (int k1 = 0; k1 < newTextures.length; k1++) {
						model.recolor(newTextures[k1], originalTextures[k1]);
					}
				}

				return model;
			}
		}
	}


	public NpcDefinition morph() {
        int j = -1;
        if (varbitId != -1) {
            VariableBits varBit = VariableBits.lookup(varbitId);
            int k = varBit.baseVar;
            int l = varBit.startBit;
            int i1 = varBit.endBit;
            int j1 = Client.anIntArray1232[i1 - l];
            j = clientInstance.variousSettings[k] >> l & j1;
        } else if (varpIndex != -1)
            j = clientInstance.variousSettings[varpIndex];
        int var3;
        if (j >= 0 && j < configs.length)
            var3 = configs[j];
        else
            var3 = configs[configs.length - 1];
        return var3 == -1 ? null : get(var3);
    }

	public final Model getAnimatedModel(SequenceDefinition var1, int var2, SequenceDefinition var3, int var4) {
		if (this.configs != null) {
			NpcDefinition var10 = this.morph();
			return var10 == null ? null : var10.getAnimatedModel(var1, var2, var3, var4);
		} else {
			long var6 = (long)this.npcId;


			Model var8 = (Model)modelCache.get(var6);
			if (var8 == null) {
				ModelData var9 = this.getModelData(this.models);
				if (var9 == null) {
					return null;
				}

				var8 = var9.toModel(this.ambient + 64, this.contrast * 5 + 850, -30, -50, -30);
				modelCache.put(var8, var6);
			}

			Model var11;
			try {
				if (var1 != null && var3 != null) {
					var11 = var1.applyTransformations(var8, var2, var3, var4);
				} else if (var1 != null) {
					var11 = var1.transformActorModel(var8, var2);
				} else if (var3 != null) {
					var11 = var3.transformActorModel(var8, var4);
				} else {
					var11 = var8.toSharedSequenceModel(true);
				}
			} catch (RuntimeException ex) {
				var11 = var8.toSharedSequenceModel(true);
			}
			if (var11 == null) {
				var11 = var8.toSharedSequenceModel(true);
			}

			if (this.npcHeight != 128 || this.npcWidth != 128) {
				var11.rs$scale(this.npcHeight, this.npcWidth, this.npcHeight);
			}

			return var11;
		}
	}

    private NpcDefinition() {
        rotate90CCWAnimIndex = -1;
        varbitId = walkingAnimation;
        rotate180AnimIndex = walkingAnimation;
        varpIndex = walkingAnimation;
        combatLevel = -1;
        anInt64 = 1834;
        walkingAnimation = -1;
        size = 1;
        anInt75 = -1;
        standingAnimation = -1;
        npcId = -1;
        rotationSpeed = 32;
        rotate90CWAnimIndex = -1;
        clickable = true;
        npcWidth = 128;
        onMinimap = true;
        npcHeight = 128;
        aBoolean93 = false;
		this.footprintSize = -1;
		this.field2032 = new int[]{1, 1, 1, 1, 1, 1};
		this.headIconArchiveIds = null;
		this.headIconSpriteIndex = null;
		this.field2156 = false;
		this.field2157 = 39188;
    }


    @Override
    public String toString() {
        return "NpcDefinition{" +
                "npcId=" + npcId +
                ", combatLevel=" + combatLevel +
                ", name='" + name + '\'' +
                ", actions=" + Arrays.toString(actions) +
                ", walkAnim=" + walkingAnimation +
                ", size=" + size +
                ", standAnim=" + standingAnimation +
                ", childrenIDs=" + Arrays.toString(configs) +
                ", models=" + Arrays.toString(models) +
                '}';
    }

    public static void nullLoader() {
        modelCache.clear();
        npcsCached.clear();
    }

    public static void dumpList() {
        try {
            File file = new File("E:\\downloads\\Xeros Package\\temp\\npc_list.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < totalAmount; i++) {
                    NpcDefinition definition = get(i);
                    if (definition != null) {
                        writer.write("npc = " + i + "\t" + definition.name + "\t" + definition.combatLevel + "\t"
                                + definition.standingAnimation + "\t" + definition.walkingAnimation + "\t");
                        writer.newLine();
                    }
                }
            }

            System.out.println("Finished dumping npc definitions.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public int[] getHeadIconArchiveIds() {
		return this.headIconArchiveIds;
	}

	public boolean headIconArchiveAvailable() {
		return this.headIconArchiveIds != null && this.headIconSpriteIndex != null;
	}

	public short[] headIconIndex() {
		return this.headIconSpriteIndex;
	}

    public static void dumpSizes() {
        try {
            File file = new File(System.getProperty("user.home") + "/Desktop/npcSizes 143.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < totalAmount; i++) {
                    NpcDefinition definition = get(i);
                    if (definition != null) {
                        writer.write(i + "	" + definition.size);
                        writer.newLine();
                    }
                }
            }

            System.out.println("Finished dumping npc definitions.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int rotate90CCWAnimIndex;

    public int varbitId;
    public int rotate180AnimIndex;
    public int varpIndex;

    public int combatLevel;
    public final int anInt64;
    public String name;
    public String actions[] = new String[5];
    public int walkingAnimation;
	public int readyanim_r = -1;
	public int readyanim_l = -1;
    public int size;
    public int[] newColors;
    public static int[] streamIndices;
    public int[] chatheadModels;
    public int anInt75;
    public int[] originalColors;
    public short[] originalTextures, newTextures;
    public int standingAnimation;
    public int npcId;
    public int rotationSpeed;

    public static Client clientInstance;
    public int rotate90CWAnimIndex;
    public boolean clickable;
    public int ambient;
    public int npcWidth;
    public boolean onMinimap;
    public int configs[];
    public String description;
    public int npcHeight;
    public int contrast;
    public boolean aBoolean93;
    public int[] models;
    public static EvictingDualNodeHashTable modelCache = new EvictingDualNodeHashTable(70);


    @Override
    public HeadIcon getOverheadIcon() {
        return null;
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
    public String getName() {
        return name;
    }

    @Override
    public int[] getModels() {
        return models;
    }

    @Override
    public String[] getActions() {
        return actions;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public boolean isFollower() {
        return false;
    }

    @Override
    public boolean isInteractible() {
        return false;
    }

    @Override
    public boolean isMinimapVisible() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public int getId() {
        return npcId;
    }

    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public int[] getConfigs() {
        return new int[0];
    }

    @Override
    public RSNPCComposition transform() {
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getRsOverheadIcon() {
        return 0;
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
}
