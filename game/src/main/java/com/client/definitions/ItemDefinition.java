package com.client.definitions;

import com.client.*;
import com.client.collection.EvictingDualNodeHashTable;
import com.client.collection.node.DualNode;
import com.client.collection.table.IterableNodeHashTable;
import com.client.definitions.custom.ItemDefinition_Sub1;
import com.client.definitions.custom.ItemDefinition_Sub2;
import com.client.definitions.custom.ItemDefinition_Sub3;
import com.client.draw.Rasterizer3D;
import com.client.entity.model.ModelData;
import com.client.entity.model.Model;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import com.client.utilities.FileOperations;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSItemComposition;
import net.runelite.rs.api.RSIterableNodeHashTable;

import static net.runelite.api.Constants.CLIENT_DEFAULT_ZOOM;

public final class ItemDefinition extends DualNode implements RSItemComposition {

	public static EvictingDualNodeHashTable sprites = new EvictingDualNodeHashTable(100);
	public static EvictingDualNodeHashTable models = new EvictingDualNodeHashTable(50);
	public static boolean isMembers = true;

	public int value;
	public short[] originalModelColors;
	public int id;
	public String[][] subOps;
	public short[] modifiedModelColors;
	public boolean membersObject;
	public boolean animateInventory;
	public int certTemplateID;
	public int secondaryFemaleModel;
	public int primaryMaleModel;
	public String[] groundActions;
	public int spriteTranslateX;
	public String name;
	public String description;
	public int modelId;
	public int primaryMaleHeadPiece;
	public boolean stackable;
	public int certID;
	public int spriteScale;
	public int secondaryMaleModel;
	public String[] itemActions;
	public int spriteYRotation;
	public int[] stackIDs;
	public int spriteTranslateY;//
	public int primaryFemaleHeadPiece;
	public int spriteZRotation;
	public int primaryFemaleModel;
	public int[] stackAmounts;
	public int team;
	public int spriteXRotation;
	public String[] equipActions;
	public boolean searchable;
	IterableNodeHashTable params;
	public int glowColor = -1;
	private short[] retextureTo;
	private short[] retextureFrom;
	private int femaleYOffset;
	private int tertiaryFemaleEquipmentModel;
	private int secondaryMaleHeadPiece;
	private int groundScaleX;
	private int secondaryFemaleHeadPiece;
	public int contrast;
	private int tertiaryMaleEquipmentModel;
	private int groundScaleZ;
	private int groundScaleY;
	public int ambient;
	private int maleYOffset;
	public byte modelYOffset;
	public byte modelXOffset;
	private int shiftClickIndex = -2;
	private int category;
	public int unnotedId;
	public int notedId;

	public int placeholderId;

	public int placeholderTemplateId;

	private ItemDefinition() {
		id = -1;
	}

	public void createCustomSprite(String img) {
		customSpriteLocation = getCustomSprite(img);
	}

	public void createSmallCustomSprite(String img) {
		customSmallSpriteLocation = getCustomSprite(img);
	}


	private byte[] getCustomSprite(String img) {
		String location = (Sprite.location + Configuration.CUSTOM_ITEM_SPRITES_DIRECTORY + img).toLowerCase();
		byte[] spriteData = FileOperations.readFile(location);
		return spriteData;
	}

	public byte[] customSpriteLocation;
	public byte[] customSmallSpriteLocation;


	public static void clear() {
		models.clear();
		sprites.clear();
	}


	public int weight;
	public int wearPos1;
	public int wearPos2;
	public int wearPos3;

	public static ItemDefinition copy(ItemDefinition itemDef, int newId, int copyingItemId, String newName, String... actions) {
		ItemDefinition copyItemDef = lookup(copyingItemId);
		itemDef.id = newId;
		itemDef.name = newName;
		itemDef.originalModelColors = copyItemDef.originalModelColors;
		itemDef.modifiedModelColors = copyItemDef.modifiedModelColors;
		itemDef.modelId = copyItemDef.modelId;
		itemDef.primaryMaleModel = copyItemDef.primaryMaleModel;
		itemDef.primaryFemaleModel = copyItemDef.primaryFemaleModel;
		itemDef.spriteScale = copyItemDef.spriteScale;
		itemDef.spriteYRotation = copyItemDef.spriteYRotation;
		itemDef.spriteZRotation = copyItemDef.spriteZRotation;
		itemDef.spriteTranslateX = copyItemDef.spriteTranslateX;
		itemDef.spriteTranslateY = copyItemDef.spriteTranslateY;
		itemDef.itemActions = copyItemDef.itemActions;
		itemDef.itemActions = new String[5];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.itemActions, 0, actions.length);
		}
		return itemDef;
	}

	public static ItemDefinition copy2(ItemDefinition itemDef, int newId, int copyingItemId, int newModelid, int newEquipModel, String newName, String... actions) {
		ItemDefinition copyItemDef = lookup(copyingItemId);
		itemDef.setDefaults();
		itemDef.id = newId;
		itemDef.name = newName;
		itemDef.originalModelColors = copyItemDef.originalModelColors;
		itemDef.modifiedModelColors = copyItemDef.modifiedModelColors;
		itemDef.modelId = newModelid;
		itemDef.primaryMaleModel = newEquipModel;
		itemDef.primaryFemaleModel = newEquipModel;
		itemDef.spriteScale = copyItemDef.spriteScale;
		itemDef.spriteYRotation = copyItemDef.spriteYRotation;
		itemDef.spriteZRotation = copyItemDef.spriteZRotation;
		itemDef.spriteTranslateX = copyItemDef.spriteTranslateX;
		itemDef.spriteTranslateY = copyItemDef.spriteTranslateY;
		itemDef.itemActions = copyItemDef.itemActions;
		itemDef.itemActions = new String[5];
		if (actions != null) {
			System.arraycopy(actions, 0, itemDef.itemActions, 0, actions.length);
		}
		return itemDef;
	}


	private static void customItems(int itemId, ItemDefinition itemDef) {

		switch (itemId) {
			case 4183:// Making a shooting star locator and teleporter.
				itemDef.name = "Like a star";
				itemDef.description = "Once a star, always a star.";
				itemDef.itemActions = new String[]{"Teleport", null, null, null, null};
				itemDef.glowColor = 1;
				break;

			case 21726:
			case 21728:
				itemDef.stackable = true;
				break;
			case 12863: // Dwarf Cannon Set
			case 12865: // Green Dragonhide Set
			case 12867: // Blue Dragonhide Set
			case 12869: // Red Dragonhide Set
			case 12871: // Black Dragonhide Set
			case 12873: // Guthan's Armour Set
			case 12875: // Verac'z Armour Set
			case 12877: // Dharok's Armour Set
			case 12879: // Torag's Armour Set
			case 12881: // Ahrim's Armour Set
			case 12883: // Karil's Armour Set
			case 12960: // Bronze Set (lg)
			case 12962: // Bronze Set (sk)
			case 12964: // Bronze Trimmed Set (lg)
			case 12966: // Bronze Trimmed Set (sk)
			case 12968: // Bronze Gold-Trimmed Set (lg)
			case 12970: // Bronze Gold-Trimmed Set (sk)
			case 12972: // Iron Set (lg)
			case 12974: // Iron Set (sk)
			case 12976: // Iron Trimmed Set (lg)
			case 12978: // Iron Trimmed Set (sk)
			case 12980: // Iron Gold-Trimmed Set (lg)
			case 12982: // Iron Gold-Trimmed Set (sk)
			case 12984: // Steel Set (lg)
			case 12986: // Steel Set (sk)
			case 12988: // Black Set (lg)
			case 12990: // Black Set (sk)
			case 12992: // Black Trimmed Set (lg)
			case 12994: // Black Trimmed Set (sk)
			case 12996: // Black Gold-Trimmed Set (lg)
			case 12998: // Black Gold-Trimmed Set (sk)
			case 13000: // Mithril Set (lg)
			case 13002: // Mithril Set (sk)
			case 13004: // Mithril Trimmed Set (lg)
			case 13006: // Mithril Trimmed Set (sk)
			case 13008: // Mithril Gold-Trimmed Set (lg)
			case 13010: // Mithril Gold-Trimmed Set (sk)
			case 13012: // Adamant Set (lg)
			case 13014: // Adamant Set (sk)
			case 13016: // Adamant Trimmed Set (lg)
			case 13018: // Adamant Trimmed Set (sk)
			case 13020: // Adamant Gold-Trimmed Set (lg)
			case 13022: // Adamant Gold-Trimmed Set (sk)
			case 13024: // Rune Set (lg)
			case 13026: // Rune Set (sk)
			case 13028: // Rune Trimmed Set (lg)
			case 13030: // Rune Trimmed Set (sk)
			case 13032: // Rune Gold-Trimmed Set (lg)
			case 13034: // Rune Gold-Trimmed Set (sk)
			case 13036: // Gilded Armour Set (lg)
			case 13038: // Gilded Armour Set (sk)
			case 13040: // Saradomin Armour Set (lg)
			case 13042: // Saradomin Armour Set (sk)
			case 13044: // Zamorak Armour Set (lg)
			case 13046: // Zamorak Armour Set (sk)
			case 13048: // Guthix Armour Set (lg)
			case 13050: // Guthix Armour Set (sk)
			case 13052: // Armadyl Armour Set (lg)
			case 13054: // Armadyl Armour Set (sk)
			case 13056: // Bandos Rune Armour Set (lg)
			case 13058: // Bandos Rune Armour Set (sk)
			case 13060: // Ancient Rune Armour Set (lg)
			case 13062: // Ancient Rune Armour Set (sk)
			case 13064: // Combat Potion Set
			case 13066: // Super Potion Set
			case 13149: // Holy Book Page Set
			case 13151: // Unholy Book Page Set
			case 13153: // Book of Balance Page Set
			case 13155: // Book of War Page Set
			case 13157: // Book of Law Page Set
			case 13159: // Book of Darkness Page Set
			case 13161: // Zamorak Dragonhide Set
			case 13163: // Saradomin Dragonhide Set
			case 13165: // Guthix Dragonhide Set
			case 13167: // Bandos Dragonhide Set
			case 13169: // Armadyl Dragonhide Set
			case 13171: // Ancient Dragonhide Set
			case 13173: // Partyhat Set
			case 13175: // Halloween Mask Set
			case 20376: // Steel Trimmed Armour Set (lg)
			case 20379: // Steel Trimmed Armour Set (sk)
			case 20382: // Steel Gold-Trimmed Armour Set (lg)
			case 20385: // Steel Gold-Trimmed Armour Set (sk)
			case 21049: // Ancestral Robes Set
			case 21279: // Obsidian Armour Set
			case 21882: // Dragon Armour Set (lg)
			case 21885: // Dragon Armour Set (sk)
			case 22438: // Justiciar Armour Set
			case 23110: // Mystic Set (Light)
			case 23113: // Mystic Set (Blue)
			case 23116: // Mystic Set (Dark)
			case 23119: // Mystic Set (Dusk)
			case 23124: // Gilded Dragonhide Set
			case 23667: // Dragonstone Armour Set
			case 24333: // Dagon'hai Armour Set
			case 24469: // Twisted Relic Hunter (t1) Armour Set
			case 24472: // Twisted Relic Hunter (t2) Armour Set
			case 24475: // Twisted Relic Hunter (t3) Armour Set
			case 24488: // Inquisitor's Armour Set
			case 24554: // Pyromancer Set
			case 25380: // Trailblazer Relic Hunter (t1) Armour Set
			case 25383: // Trailblazer Relic Hunter (t2) Armour Set
			case 25386: // Trailblazer Relic Hunter (t3) Armour Set
			case 26554: // Shattered Relic Hunter (t1) Armour Set
			case 26557: // Shattered Relic Hunter (t1) Armour Set
			case 26560: // Shattered Relic Hunter (t1) Armour Set
			case 27355: // Masori Armour Set
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 26241: // VIRTUS MASK
			case 26243: // VIRTUS TOP
			case 26245: // VIRTUS BOT
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;

			case 25646: // 50 bundle
				itemDef.name = "$50 Bundle Deal";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 25647: // 100 bundle
				itemDef.name = "$100 Bundle Deal";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 25648: // 250 bundle
				itemDef.name = "$250 Bundle Deal";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 25649: // 500 bundle
				itemDef.name = "$500 Bundle Deal";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 25651: // 1000 bundle
				itemDef.name = "$1000 Bundle Deal";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;

			case 24364: // Dragonkin Battlegear
				itemDef.name = "Dragonkin Battlegear Set";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 25104: // Crystal Armour
				itemDef.name = "Crystal Armour Set";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;

			case 11158: // Torva Armour
				itemDef.name = "Torva Armour Set";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 8150: // Virtus Armour
				itemDef.name = "Virtus Robe Set";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 8151: // Pernix Armour
				itemDef.name = "Pernix Armour Set";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;

			case 3875: // nature run red
				itemDef.name = "Rune of Alchemy";
				itemDef.itemActions = new String[]{null, "Wear", "Toggles", "Charge", "Drop"};
				itemDef.equipActions[0] = "Remove";
				itemDef.equipActions[1] = "Toggles";
				break;

			case 600: // prestige book
				itemDef.name = "Prestige Book";
				itemDef.itemActions = new String[]{"Prestige", null, "Perks", "Shop", "Drop"};
				break;
			case 13092: //this makes crystal halberds wieldable, weird af.
			case 13093:
			case 13094:
			case 13095:
			case 13096:
			case 13097:
			case 13098:
			case 13099:
			case 13100:
			case 13101:
				itemDef.itemActions = new String[] { null, "Wield", null, null, null};
				break;
			case 23933:
				itemDef.name = "Vote crystal";
				break;
			case 9698:
				itemDef.name = "Unfired burning rune";
				//itemDef.description= "I should burn this.";
				itemDef.createCustomSprite("Unfired_burning_rune.png");
				break;
			case 9699:
				itemDef.name = "Burning rune";
				//itemDef.description= "Hot to the touch.";
				itemDef.createCustomSprite("Burning_rune.png");
				break;
			case 23778:
				itemDef.name = "Uncut toxic gem";
				//itemDef.description= "I should use a chisel on this.";
				break;
			case 22374:
				itemDef.name = "Hespori key";
				//itemDef.description= "Can be used on the Hespori chest.";
				break;
			case 3459:
				itemDef.name = "Key of Mystery";
				itemDef.description = "Can be used on the The All-in-One Loot Chest.";
				break;
			case 22754:
				itemDef.name = "Blood of Bloodfang's";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelId = 49938;
				break;

			case 27604:
				itemDef.name = "Vote Reward Chest";
				itemDef.description = "Thank you for voting! Here is a reward!";
				itemDef.itemActions = new String[]{"Open", null, "Open-All", null, "Drop"};
				break;

			case 3460:
				itemDef.name = "@dre@Nex key";
				break;

			case 23783:
				itemDef.name = "Toxic gem";
				//itemDef.description= "I should be careful with this.";
				break;

			case 9017:
				itemDef.name = "Hespori essence";
				//itemDef.description= "Maybe I should burn this.";
				itemDef.itemActions = new String[] {  null, null, null, null, "Drop" };
				break;

			case 484:
				itemDef.name = "AFK Coin";
				itemDef.modelId = 63683;
				itemDef.description = "AFK coins can be used to buy supplies from the afk shop!";
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 500;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 19473:
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 10556:
			case 10557:
			case 10558:
			case 10559:
				itemDef.itemActions = new String[] { null, "Wear", "Feature", null, "Drop" };
				break;
			case 21898:
				itemDef.itemActions = new String[] { null, "Wear", "Teleports", "Features", null };
				break;
			case 23804:
				itemDef.name = "Imbue Dust";
				break;
			case 22517:
				itemDef.name = "Crystal Teleport Shard";
				itemDef.itemActions = new String[]{null, "Teleport", null, null, "Drop"};
				break;

			case 23951:
				itemDef.name = "Crystalline Key";
				break;
			case 691:
			case 22609:
				itemDef.name = "10,000 FoE Point Certificate";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 692:
			case 22607:
				itemDef.name = "25,000 FoE Point Certificate";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 693:
			case 22608:
				itemDef.name = "50,000 FoE Point Certificate";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 696:
			case 22606:
				itemDef.name = "250,000 FoE Point Certificate";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 23877:
				itemDef.name = "Crystal Shard";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = true;
				break;

			case 10142:
				itemDef.name = "Tar Ammo";
				itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
				itemDef.stackable = false;
				break;

			case 962://christmas cracker
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;

			case 24621:
				itemDef.name = "Blighted vengeance sack";
				itemDef.itemActions = new String[]{"Cast", null, null, null, "Drop"};
				itemDef.stackable = true;
				break;

			case 23943:
				itemDef.itemActions = new String[] { null, "Wear", "Uncharge", "Check", "Drop" };
				break;
			case 2996:
				itemDef.name = "PKP Ticket";
				break;
			case 23776:
				itemDef.name = "Hunllef's Key";
				break;

			case 19668:
				itemDef.name = "Scroll of imbuing the imbued";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;

			case 13148:
				itemDef.name = "Reset Lamp";
				break;
			case 6792:
				itemDef.name = "Seren's Key";
				break;
			case 4185:
				itemDef.name = "Wilderness Key";
				break;
			case 21880:
				itemDef.name = "Wrath Rune";
				itemDef.value = 1930;
				break;
			case 12885:
			case 13277:
			case 19701:
			case 13245:
			case 12007:
			case 22106:
			case 12936:
			case 24495:
				itemDef.itemActions = new String[] { null, null, "Open", null, "Drop" };
				break;
			case 2572:
			case 2573:
			case 12785:
			case 12786:
				itemDef.itemActions = new String[] { null, "Wear", null, "Rub", "Drop" };
				break;

			case 6759:
				itemDef.itemActions = new String[]{"Open", null, null, null, null};
				itemDef.name = "Youtube Starter Chest"; //Name
				itemDef.description = "A chest for youtubers starting off."; //Description
				break;

			case 21262:
				itemDef.name = "Vote Genie Pet";
				itemDef.itemActions = new String[] { null, null, null, null, "Release" };
				break;
			case 21817:
			case 26718:
			case 26719:
			case 26720:
			case 26714:
			case 26715:
			case 26716:
				itemDef.itemActions = new String[] { null, "Wear", "Dismantle", null, null, };
				break;
			case 21347:
				itemDef.itemActions = new String[] { null, null, null, "Chisel-Options", null, };
				break;
			case 21259:
				itemDef.name = "Name Change Scroll";
				itemDef.itemActions = new String[] { null, null, "Read", null, null, };
				break;
			case 6807:
				itemDef.name = "@cya@Scroll Of Immunity";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 6803:

				itemDef.name = "@cya@Scroll Of Darkness";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 6801:

				itemDef.name = "@cya@Scroll Of Riches";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 6804:

				itemDef.name = "@cya@Scroll Of Resource";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 6806:

				itemDef.name = "@cya@Crystal Scroll";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 25481:

				itemDef.name = "@cya@Scroll Of Upgrade";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;
			case 25478:
				itemDef.name = "@cya@Scroll Of Savings";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;

			case 30949:
				itemDef.name = "@cya@Scroll Of Safety";
				itemDef.itemActions = new String[]{null, null, "Read", null, null,};
				break;

			case 12411:
				itemDef.name = "Crystal Teleport Scroll";
				itemDef.itemActions = new String[]{"Teleport", null, null, null, "Drop",};
				break;

			case 22547:
			case 22552:
			case 22542:
				itemDef.itemActions = new String[] { null, null, null, null, null, };
				break;
			case 22555:
			case 22550:
			case 22545:
				itemDef.itemActions = new String[] { null, "Wield", "Check", "Uncharge", null, };
				break;
			case 732:
				itemDef.name = "@blu@Imbuedeifer";
				itemDef.value = 1930;
				break;
			case 21881:
				itemDef.name = "Wrath Rune";
				itemDef.value = 1930;
				break;
			case 13226:
				itemDef.name = "Herb Sack";
				itemDef.itemActions = new String[]{"Fill", null, "Empty", "Check", "Drop",};
				itemDef.description = "Thats a nice looking sack.";
				break;
			case 25865:
				itemDef.itemActions = new String[]{null, "Wield", "Check", null, "Drop"};
				break;

			case 26747:
				itemDef.name = "Elvarg starter helm";
				itemDef.description = "A great starter helm for your adventures on BloodLust.";
				break;
			case 26753:
				itemDef.name = "Elvarg starter body";
				itemDef.description = "A great starter body for your adventures on BloodLust.";
				break;
			case 26759:
				itemDef.name = "Elvarg starter legs";
				itemDef.description = "A great pair of starter legs for your adventures on BloodLust.";
				break;

			case 3456:
				itemDef.name = "Common Raids Key";
				//itemDef.description= "Can be used on the storage unit.";
				break;
			case 3464:
				itemDef.name = "Rare Raids Key";
				//itemDef.description= "Can be used on the storage unit.";
				break;
			case 28416:
				itemDef.name = "The Whisperer Key";
				break;
			case 28418:
				itemDef.name = "The Scorched Key";
				break;
			case 28419:
				itemDef.name = "The Leviathan Key";
				break;
			case 28420:
				itemDef.name = "Duke Sucellus Key";
				break;
			case 28373:
				itemDef.name = "Vardorvis Key";
				break;
			case 3465:
				itemDef.name = "Rare Ba-Ba Key";
				break;
			case 3467:
				itemDef.name = "Rare Zebak Key";
				itemDef.description = "Can be used on the storage unit.";
				break;
			case 25424:
				itemDef.name = "Elvarg Key";
				itemDef.description = "Can be used on the storage unit.";
				break;
			case 6829:
				itemDef.name = "Beta Box";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 24361:
				itemDef.name = "Bonus Starter Scroll";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;

			case 6831:
				itemDef.name = "YT Video Giveaway Box (t2)";
				//itemDef.description= "Spawns items to giveaway for your youtube video.";
				itemDef.itemActions = new String[] { "Giveaway", null, null, null, "Drop" };

				break;
			case 6832:
				itemDef.name = "Referral Mystery Box";
				itemDef.description = "Referral Mystery Box.";
				itemDef.itemActions = new String[]{"Open", null, null, "Quick-Open", "Drop"};
				break;
			case 13639:
				itemDef.name = "Hespori Mystery Box";
				itemDef.description = "Hespori Mystery Box.";
				itemDef.itemActions = new String[]{"Open", null, null, "Quick-Open", "Drop"};
				break;

			case 6833:
				itemDef.name = "Youtube Reward Box";
				itemDef.description = "Spawns items to giveaway for your youtube stream.";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 27753:
			case 27765:
			case 27729:
			case 27777:
			case 27741:
			case 27717:
			case 23842:

				itemDef.name = "Crystal helm (c)";
				break;

			case 27745:
			case 27757:
			case 27721:
			case 27769:
			case 27733:
			case 27709:
			case 23845:

				itemDef.name = "Crystal body (c)";
				break;

			case 27749:
			case 27761:
			case 27725:
			case 27773:
			case 27737:
			case 27713:
			case 23848:

				itemDef.name = "Crystal legs (c)";
				break;
			case 6121:
				itemDef.name = "Break Vials Instruction";
				//itemDef.description= "How does one break a vial, its impossible?";
				break;
			case 2528:
				itemDef.name = "Experience Lamp";
				//itemDef.description= "Should I rub it......";
				break;
			case 5509:
				itemDef.name = "Small Pouch";
				itemDef.createCustomSprite("Small_pouch.png");
				itemDef.itemActions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 5510:
				itemDef.name = "Medium Pouch";
				itemDef.createCustomSprite("Medium_pouch.png");
				itemDef.itemActions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 5512:
				itemDef.name = "Large Pouch";
				itemDef.createCustomSprite("Large_pouch.png");
				itemDef.itemActions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 10724: //full skeleton
			case 10725:
			case 10726:
			case 10727:
			case 10728:
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 30302:
			case 30303:
			case 30304:
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 5514:
				itemDef.name = "Giant Pouch";
				itemDef.createCustomSprite("Giant_pouch.png");
				break;
			case 22610: //vesta spear
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 22613: //vesta longsword
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 22504: //stat warhammer
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 4224:
			case 4225:
			case 4226:
			case 4227:
			case 4228:
			case 4229:
			case 4230:
			case 4231:
			case 4232:
			case 4233:
			case 4234:
			case 4235://crystal sheild
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
				itemDef.itemActions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 2841:
				itemDef.name = "Bonus Exp Scroll";
				itemDef.itemActions = new String[] { "Activate", null, null, null, "Drop" };
				//itemDef.description= "You will get double experience using this scroll.";
				break;
			case 29855:
				itemDef.name = "Sussy's Cape";
				itemDef.description = "For sussy.";
				itemDef.itemActions = new String[]{"Spawn", "Wear", "Staff Zone", "Bank", "Drop"};
				break;
			case 29715:
			case 29718:
				itemDef.itemActions = new String[]{"Spawn", "Wear", "Staff Zone", "Bank", "Drop"};
				break;
			case 29716:
			case 29717:
			case 29719:
				itemDef.itemActions = new String[]{null, "Wear", "Staff Zone", null, "Drop"};
				break;
			case 13319:
				itemDef.name = "Elvarg Veteran's Cape";
				itemDef.description = "A cape only for BloodLust Veterans";
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;

			case 23517:
				itemDef.name = "Giant egg sac";
				itemDef.itemActions = new String[]{"Cut-Open", null, null, null, "Drop"};
				break;
			case 21791:
			case 21793:
			case 21795:
				itemDef.itemActions = new String[] { null, "Wear", null, null, "Drop" };
				break;
			case 24664:
			case 24666:
			case 24668:
				itemDef.itemActions = new String[]{null, "Wear", "Dismantle", null, "Drop"};
				break;
			case 25734:
				itemDef.itemActions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
				break;
			case 19841:
				itemDef.name = "Master Casket";
				break;
			case 23837:
				itemDef.name = "Bloody shards";
				break;
			case 24691:
				itemDef.name = "Elvarg logs";
				break;
			case 13356:
				itemDef.name = "Elvarg ore";
				break;
			case 13354:
				itemDef.name = "Elvarg bar";
				break;
			case 9597:
				itemDef.name = "Elvarg mark";
				break;
			case 23835:
				itemDef.name = "Elvarg herb";
				break;
			case 20856:
				itemDef.name = "Elvarg fish";
				break;
			case 26941:
				itemDef.name = "Elvarg essence";
				break;
			case 1497:
				itemDef.name = "Elvarg necklace";
				break;
			case 5745:
				itemDef.name = "Elvarg brew";
				break;
			case 744:
				itemDef.name = "Elvarg stone";
				break;
			case 21308:
				itemDef.name = "Elvarg string";
				break;
			case 20908:
				itemDef.name = "Elvarg plant";
				break;
			case 9952:
				itemDef.name = "Elvarg imp";
				break;
			case 25547://star fragment
				itemDef.itemActions = new String[]{"Crush", null, null, null, "Drop"};
				break;
			case 21034:
				itemDef.itemActions = new String[] { "Read", null, null, null, "Drop" };
				break;
			case 553:
				itemDef.name = "Skull of Death";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;

			case 6830:
				itemDef.name = "Raids 2 Mystery Box";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 10025:

				itemDef.name = "@dre@Raids 1 Mystery Box";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 21079:
				itemDef.itemActions = new String[] { "Read", null, null, null, "Drop" };
				break;
			case 22093:
				itemDef.name = "Vote Streak Key";
				//itemDef.description= "Thanks for voting!";
				break;
			case 10027:
				itemDef.name = "Skilling Box";
				itemDef.description = "Box full of skilling supplys";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 22885:
				itemDef.name = "Kronos seed";
				//itemDef.description= "Provides whole server with bonus xp for 1 skill for 5 hours!";
				break;
			case 6457:
				itemDef.name = "The Whisperer seed";
				break;
			case 27039:
				itemDef.name = "Kaida seed";
				break;
			case 27041:
				itemDef.name = "Ancient seed";
				itemDef.description = "Provides whole server with x2 nex keys!";
				break;
			case 23824:
				itemDef.name = "Slaughter charge";
				//itemDef.description= "Can be used on bracelet of slaughter to charge it.";
				break;
			case 22883:
				itemDef.name = "Iasor seed";
				//itemDef.description= "Increased drop rate (+10%) for whole server for 5 hours!";
				break;
			case 22881:
				itemDef.name = "Attas seed";
				//itemDef.description= "Provides the whole server with bonus xp for 5 hours!";
				break;
			case 20906:
				itemDef.name = "Golpar seed";
				//itemDef.description= "Provides whole server with double c keys, resource boxes, coin bags, and clues!";
				break;
			case 6112:
				itemDef.name = "Kelda seed";
				//itemDef.description= "Provides whole server with x2 Larren's keys for 1 hour!";
				break;
			case 20903:
				itemDef.name = "Noxifer seed";
				//itemDef.description= "Provides whole server with x2 Slayer points for 1 hour!";
				break;
			case 20909:
				itemDef.name = "Buchu seed";
				//itemDef.description= "Provides whole server with x2 Boss points for 1 hour!";
				break;
			case 22869:
				itemDef.name = "Celastrus seed";
				//itemDef.description= "Provides whole server with x2 Brimstone keys for 1 hour!";
				break;
			case 4205:
				itemDef.name = "Consecration seed";
				//itemDef.description= "Provides the whole server with +5 PC points for 1 hour.";
				itemDef.stackable = true;
				break;
			case 11864:
			case 11865:
			case 19639:
			case 19641:
			case 19643:
			case 19645:
			case 19647:
			case 19649:
			case 24444:
			case 24370:
			case 23075:
			case 23073:
			case 21888:
			case 21890:
			case 21264:
			case 21266:
				itemDef.equipActions[2] = "Log";
				itemDef.equipActions[1] = "Check";
				break;
			case 13136:
				itemDef.equipActions[2] = "Elidinis";
				itemDef.equipActions[1] = "Kalphite Hive";
				break;
			case 2550:
				itemDef.equipActions[2] = "Check";
				break;
			case 2400:
				itemDef.name = "Halloween Chest Key";
				break;
			case 13256: //sara light
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 13307: //blood money
				itemDef.itemActions = new String[]{null, "Convert", null, null, "Drop"};
				break;
			case 28371: //blood money
				itemDef.itemActions = new String[]{null, "Convert", null, null, "Drop"};
				break;

			case 1712:
			case 1710:
			case 1708:
			case 1706:
			case 19707:
				itemDef.equipActions[1] = "Edgeville";
				itemDef.equipActions[2] = "Karamja";
				itemDef.equipActions[3] = "Draynor";
				itemDef.equipActions[4] = "Al-Kharid";
				break;
			case 21816:
				itemDef.itemActions = new String[] { null, "Wear", "Uncharge", null, "Drop" };
				itemDef.equipActions[1] = "Check";
				itemDef.equipActions[2] = "Toggle-absorption";
				break;
			case 2552:
			case 2554:
			case 2556:
			case 2558:
			case 2560:
			case 2562:
			case 2564:
			case 2566: // Ring of duelling
				itemDef.equipActions[2] = "Shantay Pass";
				itemDef.equipActions[1] = "Clan wars";
				break;
			case 11739:
				itemDef.name = "Vote Mystery Box";
				//itemDef.description= "Probably contains cosmetics, or maybe not...";
				itemDef.itemActions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 6828:
				itemDef.name = "Super Mystery Box";
				//itemDef.description= "Mystery box that contains goodies.";
				itemDef.itemActions = new String[] { "Open", null, "View-Loots", "Quick-Open", "Drop" };
				itemDef.createCustomSprite("Mystery_Box.png");
				itemDef.createSmallCustomSprite("Mystery_Box_Small.png");
				itemDef.stackable = false;
				break;
			case 27255:

				itemDef.name = "Menaphite ornament kit";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 26300:
				itemDef.name = "Festive cosmetic kit";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 10487:
				itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
				break;
			case 25650:
				itemDef.name = "Primal Chest";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 881:
				itemDef.name = "Twisted bolts";
				break;
			case 22330:
			case 24130:
				itemDef.name = "Starter Pack";
				itemDef.itemActions = new String[]{"Open", null, null, null, null};
				break;
			case 27251:
			case 27253:
			case 2949:
				itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
				break;
			case 23525:
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 30010:
				itemDef.setDefaults();
				itemDef.name = "Skull Raider";
				itemDef.modelId = 65063;
				itemDef.spriteScale = 1000;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				break;
			case 30011:
				itemDef.setDefaults();
				itemDef.name = "Clue Gremlin";
				itemDef.modelId = 65065;
				itemDef.spriteScale = 1500;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				break;
			case 30012:
				itemDef.setDefaults();
				itemDef.name = "Toucan";
				//itemDef.description= "50% chance to pick up resource packs.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Toucan.png");
				break;
			case 678:
				itemDef.name = "Shadow Pet";
				itemDef.spriteScale = 2226;
				itemDef.spriteXRotation = 100;
				itemDef.spriteYRotation = 302;
				itemDef.modelYOffset = 61;
				itemDef.groundActions = new String[]{null, null, "Pick Up", null, null};
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 30013:
				itemDef.setDefaults();
				itemDef.name = "Penguin King";
				//itemDef.description= "50% chance to auto-pick up coin bags.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Penguin_King.png");
				break;
			case 30014:
				itemDef.setDefaults();
				itemDef.name = "K'klik";
				//itemDef.description= "An extra 5% in drop rate boost.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("K'klik.png");
				break;
			case 30015:
				itemDef.setDefaults();
				itemDef.name = "Shadow warrior";
				//itemDef.description= "50% chance for an additional +10% strength bonus in pvm.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_warrior.png");
				break;
			case 30016:
				itemDef.setDefaults();
				itemDef.name = "Shadow archer";
				//itemDef.description= "50% chance for an additional +10% range str bonus in PvM.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_archer.png");
				break;
			case 30017:
				itemDef.setDefaults();
				itemDef.name = "Shadow wizard";
				//itemDef.description= "50% chance for an additional +10% mage str bonus in PvM.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_wizard.png");
				break;
			case 30018:
				itemDef.setDefaults();
				itemDef.name = "Healer Death Spawn";
				//itemDef.description= "5% chance hit restores HP.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Healer_Death_Spawn.png");
				break;
			case 30019:
				itemDef.setDefaults();
				itemDef.name = "Holy Death Spawn";
				//itemDef.description= "5% chance 1/2 of your hit is restored into prayer.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Holy_Death_Spawn.png");
				break;
			case 30020:
				itemDef.setDefaults();
				itemDef.name = "Corrupt beast";
				//itemDef.description= "50% chance for an additional +10% strength bonus for melee, mage, and range in pvm.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Corrupt_beast.png");
				break;
			case 30021:
				itemDef.setDefaults();
				itemDef.name = "Roc";
				//itemDef.description= "An extra 10% in drop rate boost.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Roc.png");
				break;
			case 30022:
				itemDef.setDefaults();
				itemDef.name = "Kratos";
				//itemDef.description= "The most powerful pet, see ::foepets for full list of perks.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Yama.png");
				break;
			case 8333:
				itemDef.name = "Mini Scorch";
				itemDef.description = "Boss pet of Scorchon.";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.createCustomSprite("scorched.png");
				break;

			case 8350:
				itemDef.name = "Princess Elvarg";
				itemDef.description = "Boss pet of Slayer Boss.";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelId = 54579;
				itemDef.spriteScale = 1500;
				itemDef.spriteYRotation = 315;
				itemDef.spriteZRotation = 870;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;

			case 8348:
				itemDef.name = "Lil Dono";
				itemDef.description = "Boss pet of Dono Boss.";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.createCustomSprite("donoboss.png");
				break;
			case 1500:
				itemDef.name = "Lil Varg";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.modelId = 65037;
				itemDef.spriteScale = 2547;
				itemDef.spriteYRotation = 97;
				itemDef.spriteZRotation = 1820;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 8349:
				itemDef.name = "Small Golem";
				itemDef.modelId = 56450;
				itemDef.spriteScale = 5000;
				itemDef.description = "Boss pet of Bloodlust Golem.";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				//itemDef.createCustomSprite("golem.png");
				break;

			case 10997:
				itemDef.name = "@cya@Molanisk";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 8866:
				itemDef.name = "Storage chest key (UIM)";
				//itemDef.description= "Used to open the UIM storage chest 1 time.";
				itemDef.stackable = true;
				break;
			case 8868:
				itemDef.name = "Perm. storage chest key (UIM)";
				//itemDef.description= "Permanently unlocks UIM storage chest.";
				break;
			case 771:
				itemDef.name = "@cya@Ancient branch";
				//itemDef.description= "Burning items in the FoE with this branch provides a 1 time +10% FoE value increase.";
				break;
			case 6199:
				itemDef.name = "Mystery Box";
				//itemDef.description= "Mystery box that contains goodies.";
				itemDef.itemActions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
				break;

			case 28088:
				itemDef.name = "Corrupt Crate";
				itemDef.itemActions = new String[]{"Open", null, null, null, "Drop"};
				break;

			case 12789:
				itemDef.name = "Youtube Mystery Box";
				//itemDef.description= "Mystery box that contains goodies.";
				itemDef.itemActions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 13346:
				itemDef.name = "Ultra Mystery Box";
				itemDef.itemActions = new String[] { "Open", null, null, "Quick-Open", "Drop" };
				break;
			case 8152:
				itemDef.name = "Elvarg Chest";
				itemDef.modelId = 63037;
				itemDef.spriteScale = 3000;
				itemDef.spriteYRotation = 609;
				itemDef.spriteZRotation = 111;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.itemActions = new String[]{"Open", null, null, "Quick-Open", "Drop"};
				break;
			case 24731:
				itemDef.modelId = 65061;
				itemDef.name = "Ring of Elvarg";
				itemDef.spriteScale = 750;
				itemDef.spriteYRotation = 342;
				itemDef.spriteZRotation = 250;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -3;
				itemDef.spriteTranslateY = -12;
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				itemDef.stackable = false;
				break;

			case 13666:
				itemDef.name = "Ferox Enclave Teleport Tablet";
				itemDef.itemActions = new String[]{"Break", null, null, null, "Drop"};
				break;

			case 24963:
				itemDef.name = "The Whisperer Teleport Tablet";
				itemDef.itemActions = new String[]{"Break", null, null, null, "Drop"};
				break;

			case 21541:
				itemDef.name = "Vardorvis Teleport Tablet";
				itemDef.itemActions = new String[]{"Break", null, null, null, "Drop"};
				break;

			case 6949:
				itemDef.name = "ToA Teleport Scroll";
				itemDef.itemActions = new String[]{"Read", null, null, null, "Drop"};
				break;
			case 8167:
				itemDef.name = "FoE Mystery Chest (locked)";
				itemDef.itemActions = new String[] { "Unlock", null, null, "Quick-Open", "Drop" };
				break;
			case 13438:
				itemDef.name = "Slayer Mystery Chest";
				itemDef.itemActions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 25488:
				itemDef.name = "Warrior ring (iu)";
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;
			case 25487:
				itemDef.name = "Archers ring (iu)";
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;
			case 25486:
				itemDef.name = "Seer's ring (iu)";
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;
			case 25485:
				itemDef.name = "Berserker ring (iu)";
				itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
				break;

			case 26598:
				itemDef.name = "Special bait";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 22844:
				itemDef.name = "Special fishing rod";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20857:
				itemDef.name = "Special raw fish";
				itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20858:
				itemDef.name = "Special fish";
				itemDef.itemActions = new String[]{"Eat", null, null, null, "Drop"};
				break;

			case 23856:
				itemDef.name = "Barronite bow";
				break;
			case 8869:
				itemDef.name = "Lost key of the cryptic";
				break;
			case 23898:
				itemDef.name = "Barronite staff";
				break;
			case 23841:
				itemDef.name = "Barronite helm";
				break;
			case 23844:
				itemDef.name = "Barronite body";
				break;
			case 23847:
				itemDef.name = "Barronite legs";
				break;


			case 27655:
				itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
				break;
			case 27665:
				itemDef.itemActions = new String[]{null, "Wield", null, "Swap", "Drop"};
				break;
			case 27660:
				itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
				break;
			case 27679:
				itemDef.itemActions = new String[]{null, "Wield", null, "Swap", "Drop"};
				break;
			case 2399:
				itemDef.name = "FoE Mystery Key";
				//itemDef.description= "Used to unlock the FoE Mystery Chest.";
				break;
			case 28140:

				itemDef.name = "Log Basket";
				itemDef.itemActions = new String[]{"Fill", "Wear", "Check", "Empty", "Drop"};
				break;
			case 10832:
				itemDef.name = "Small coin bag";
				itemDef.itemActions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description= "I can see some coins inside.";
				break;
			case 10833:
				itemDef.name = "Medium coin bag";
				itemDef.itemActions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description= "I can see some coins inside.";
				break;
			case 10834:
				itemDef.name = "Large coin bag";
				itemDef.itemActions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description= "I can see some coins inside.";
				break;
			case 26500:

				itemDef.name = "Battle Pass";
				itemDef.itemActions = new String[]{"Redeem", null, null, null, null};
				itemDef.description = "Redeem this pass to unlock the premium rewards.";
				break;
			case 5733:

				itemDef.name = "Magic Dev Potato";
				itemDef.itemActions = new String[]{"Admin Interface", null, null, null, null};
				break;

			case 26502:

				itemDef.name = "Battle Pass";
				itemDef.itemActions = new String[]{"Redeem", null, null, null, null};
				itemDef.description = "Redeem this pass to unlock the premium rewards.";
				break;
			case 22316:
				itemDef.name = "Sword of Elvarg";
				//itemDef.description= "The Sword of Elvarg.";
				break;
			case 27916:

				itemDef.name = "Morrigan's javelin (o)";
				itemDef.description = "Morrigan's javelin earned from outlast";
				break;
			case 27912:

				itemDef.name = "Morrigan's throwing axe (o)";
				itemDef.description = "Morrigan's throwing axe earned from outlast";
				break;
			case 27836:

				itemDef.name = "Morrigan's Coif (o)";
				itemDef.description = "Morrigan's coif earned from outlast.";
				break;
			case 27837:

				itemDef.name = "Morrigan's leather body (o)";
				itemDef.description = "Morrigan's leather body earned from outlast.";
				break;
			case 27838:

				itemDef.name = "Morrigan's leather chaps (o)";
				itemDef.description = "Morrigan's leather chaps earned from outlast.";
				break;
			case 27831:

				itemDef.name = "Vesta's chainbody (o)";
				itemDef.description = "Vesta's chainbody earned from outlast.";
				break;
			case 27832:

				itemDef.name = "Vesta's plateskirt (o)";
				itemDef.description = "Vesta's plateskirt earned from outlast.";
				break;
			case 27900:

				itemDef.name = "Vesta's Spear (o)";
				itemDef.description = "Vesta's Spear earned from outlast.";
				break;
			case 27904:

				itemDef.name = "Vesta's longsword (o)";
				itemDef.description = "Vesta's longsword earned from outlast.";
				break;
			case 27908:

				itemDef.name = "Statius's warhammer (o)";
				itemDef.description = "Statius's warhammer earned from outlast.";
				break;
			case 27833:

				itemDef.name = "Statius's full helm (o)";
				itemDef.description = "Statius's full helm earned from outlast.";
				break;
			case 27834:

				itemDef.name = "Statius's platebody (o)";
				itemDef.description = "Statius's platebody earned from outlast.";
				break;
			case 27835:

				itemDef.name = "Statius's platelegs (o)";
				itemDef.description = "Statius's platelegs earned from outlast.";
				break;
			case 27902:

				itemDef.name = "Zuriel's staff (o)";
				itemDef.description = "Zuriel's staff earned from outlast.";
				break;
			case 27839:

				itemDef.name = "Zuriel's hood (o)";
				itemDef.description = "Zuriel's hood earned from outlast.";
				break;
			case 27840:

				itemDef.name = "Zuriel's robe top (o)";
				itemDef.description = "Zuriel's robe top earned from outlast.";
				break;
			case 27841:

				itemDef.name = "Zuriel's robe bottom (o)";
				itemDef.description = "Zuriel's robe bottom earned from outlast.";
				break;
			case 19942:
				itemDef.name = "Lil Mimic";
				//itemDef.description= "It's a lil mimic.";
				break;
			case 3880:

				itemDef.name = "AFK Rune";
				itemDef.description = "A Rune that can be used to buy runecrafting outfits";
				break;
			case 30110:
				itemDef.setDefaults();
				itemDef.name = "Dark Skull Raider";
				itemDef.modelId = 65064;
				itemDef.spriteScale = 1000;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				break;
			case 30111:
				itemDef.setDefaults();
				itemDef.name = "Dark Clue Gremlin";
				itemDef.spriteScale = 1500;
				itemDef.modelId = 65066;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;

				break;
			case 30112:
				itemDef.setDefaults();
				itemDef.name = "Dark toucan";
				itemDef.spriteScale = 1500;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Toucan.png");
				break;
			case 30113:
				itemDef.setDefaults();
				itemDef.name = "Dark penguin King";
				//itemDef.description= "Picks up all coin bags and 25% chance to double.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Penguin_King.png");
				break;
			case 30114:
				itemDef.setDefaults();
				itemDef.name = "Dark k'klik";
				//itemDef.description= "An extra 10% in drop rate boost.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_K'klik.png");
				break;
			case 30115:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow warrior";
				//itemDef.description= "Gives constant +10% strength bonus in pvm.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_warrior.png");
				break;
			case 30116:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow archer";
				//itemDef.description= "Gives constant +10% range str bonus in PvM.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_archer.png");
				break;
			case 30117:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow wizard";
				//itemDef.description= "Gives constant +10% mage str bonus in PvM.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_wizard.png");
				break;
			case 30118:
				itemDef.setDefaults();
				itemDef.name = "Dark healer death spawn";
				//itemDef.description= "10% chance hit restores HP.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Healer_Death_Spawn.png");
				break;
			case 30119:
				itemDef.setDefaults();
				itemDef.name = "Dark holy death spawn";
				//itemDef.description= "10% chance 1/2 of your hit is restored into prayer.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Holy_Death_Spawn.png");
				break;
			case 30120:
				itemDef.setDefaults();
				itemDef.name = "Dark corrupt beast";
				//itemDef.description= "Extra 10% in drop rate and constant +10% strength bonus for all styles in pvm.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Corrupt_beast.png");
				break;
			case 30121:
				itemDef.setDefaults();
				itemDef.name = "Dark roc";
				//itemDef.description= "An extra 20% in drop rate boost.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Roc.png");
				break;
			case 30122:
				itemDef.setDefaults();
				itemDef.name = "Dark kratos";
				//itemDef.description= "The most powerful pet, see ::foepets for full list of perks.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_yama.png");
				break;
			case 30123:
				itemDef.setDefaults();
				itemDef.name = "Dark seren";
				//itemDef.description= "85% chance for Wildy Event Boss to hit a 0 and 25% chance to double key.";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_seren.png");

				break;
			case 23939:
				itemDef.name = "Seren";
				//itemDef.description= "50% chance for wildy event bosses to hit a 0 on you.";
				itemDef.createCustomSprite("seren.png");
				break;
			case 21046:
				itemDef.name = "@cya@Chest rate bonus (+15%)";
				//itemDef.description= "A single use +15% chance from chests, or to receive a rare raids key.";
				itemDef.stackable = true;
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				break;
			case 11666:
				itemDef.name = "Full Void Token";
				//itemDef.description= "Use this token to receive a full elite void set with all combat pieces.";
				itemDef.itemActions = new String[] { "Activate", null, null, null, "Drop" };
				break;
			case 1004:
				itemDef.name = "5M Coins";
				itemDef.description = "Lovely coins.";
				itemDef.stackable = false;
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				break;
			case 7629:
				itemDef.name = "2x Slayer point scroll";
				itemDef.itemActions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 24460:
				itemDef.name = "Faster clues (30 mins)";
				//itemDef.description= "Clue rates are halved for npcs and skilling.";
				itemDef.itemActions = new String[] { "Boost", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 7968:
				itemDef.name = "+25% Skilling pet rate (30 mins)";
				itemDef.itemActions = new String[] { "Boost", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 8899:
				itemDef.name = "50m Coins";
				//itemDef.description= "Lovely coins.";
				itemDef.stackable = false;
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 4035:
				itemDef.itemActions = new String[] { "Teleport", null, null, null, null };
				break;
			case 6996:
				itemDef.itemActions = new String[]{"Teleport", null, null, null, null};
				break;
			case 405:

				itemDef.name = "PvM Casket";
				itemDef.itemActions = new String[]{"Open", null, "Open-All", null, null};
				break;
			case 10835:
				itemDef.name = "Buldging coin bag";
				itemDef.itemActions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description= "I can see some coins inside.";
				break;
			case 15098:
				itemDef.name = "Dice (up to 100)";
				//itemDef.description= "A 100-sided dice.";
				itemDef.modelId = 31223;
				itemDef.spriteScale = 1104;
				itemDef.spriteZRotation = 215;
				itemDef.spriteYRotation = 94;
				itemDef.spriteTranslateY = -5;
				itemDef.spriteTranslateX = -18;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Public-roll";
				itemDef.itemActions[2] = null;
				itemDef.name = "Dice (up to 100)";
				itemDef.ambient = 15;
				itemDef.contrast = 25;
				itemDef.createCustomSprite("Dice_Bag.png");
				break;

			case 281:
				itemDef.name = "$500 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 65028;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 11773:
			case 11771:
			case 11770:
			case 11772:
				itemDef.ambient += 45;
				break;
			case 12792:
				itemDef.name = "Graceful Recolor Box";
				itemDef.itemActions = new String[] { null, "Use", null, null, "Drop" };
				break;
			case 6769:
				itemDef.name = "$5 Elvarg flame";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63631;
				itemDef.spriteScale = 550;
				break;
			case 2403:
				itemDef.name = "$10 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 10 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63632;
				itemDef.spriteScale = 550;
				break;
			case 2396:
				itemDef.name = "$25 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 25 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63633;
				itemDef.spriteScale = 550;
				break;
			case 786:
				itemDef.name = "$50 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 50 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63634;
				itemDef.spriteScale = 550;
				break;
			case 761:
				itemDef.name = "$100 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 100 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63635;
				itemDef.spriteScale = 550;
				break;
			case 607:
				itemDef.name = "$250 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 250 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63636;
				itemDef.spriteScale = 550;
				break;
			case 608:
				itemDef.name = "$500 Elvarg flame";
				//itemDef.description= "Claim this scroll to be rewarded with 500 donator points.";
				itemDef.itemActions = new String[] { "Claim", null, null, null, "Drop" };
				itemDef.modelId = 63637;
				itemDef.spriteScale = 550;
				break;
			case 24030:
				itemDef.name = "$5 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63638;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 24032:
				itemDef.name = "$10 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63639;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 24031:
				itemDef.name = "$25 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63640;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 24033:
				itemDef.name = "$50 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63641;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 22087:
				itemDef.name = "$100 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63642;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 282:
				itemDef.name = "$250 Total donated gem";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.description = "A peice of dragon history that could unlock something!.";
				itemDef.modelId = 63643;
				itemDef.spriteScale = 875;
				itemDef.spriteZRotation = 0;
				itemDef.spriteYRotation = 500;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteTranslateX = 0;
				break;
			case 486:
				itemDef.name = "$50 Dono Deal Token";
				itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.modelId = 65023;
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 488:
				itemDef.name = "$100 Dono Deal Token";
				itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.modelId = 65024;
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 490:
				itemDef.name = "$250 Dono Deal Token";
				itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.modelId = 65025;
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 492:
				itemDef.name = "$500 Dono Deal Token";
				itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.modelId = 65026;
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 494:
				itemDef.name = "$1000 Dono Deal Token";
				itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.itemActions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.modelId = 65027;
				itemDef.spriteScale = 600;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				break;
			case 1464:
				itemDef.name = "Vote ticket";
				//itemDef.description= "Exchange this for a Vote Point.";
				break;


			case 33049:
				itemDef.setDefaults();
				itemDef.originalModelColors = new short[] {(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.modifiedModelColors = new short[] { 677, 801, (short) 43540,(short)  43543, (short) 43546,(short)  (short) 43549, (short) 43550, (short) 43552, (short) 43554, (short) 43558,
						(short) 43560, (short) 43575 };
				itemDef.name = "Agility master cape";
				itemDef.modelId = 63070;
				itemDef.primaryMaleModel = 63071;
				itemDef.primaryFemaleModel = 63071;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				break;
			case 33033:
				itemDef.setDefaults();
				itemDef.name = "Attack master cape";
				itemDef.originalModelColors = new short[] {(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.modifiedModelColors = new short[] { 7104, 9151, 911, 914, 917, 920, 921, 923, 925, 929, 931, 946 };
				itemDef.modelId = 63072;
				itemDef.primaryMaleModel = 63073;
				itemDef.primaryFemaleModel = 63073;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33055:
				itemDef.setDefaults();
				itemDef.name = "Construction master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{6061, 5945, 6327, 6330, 6333, 6336, 6337, 6339, 6341, 6345, 6347,
						6362};
				itemDef.modelId = 63074;
				itemDef.primaryMaleModel = 63075;
				itemDef.primaryFemaleModel = 63075;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33040:
				itemDef.setDefaults();
				itemDef.name = "Cooking master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{920, 920, (short) 51856, (short) 51859, (short) 51862, (short) 51865, (short) 51866, (short) 51868, (short) 51870, (short) 51874,
						(short) 51876, (short) 51891};
				itemDef.modelId = 63076;
				itemDef.primaryMaleModel = 63077;
				itemDef.primaryFemaleModel = 63077;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33045:
				itemDef.setDefaults();
				itemDef.name = "Crafting master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{9142, 9152, 4511, 4514, 4517, 4520, 4521, 4523, 4525, 4529, 4531,
						4546};
				itemDef.modelId = 63078;
				itemDef.primaryMaleModel = 63079;
				itemDef.primaryFemaleModel = 63079;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33034:
				itemDef.setDefaults();
				itemDef.name = "Defence master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{10460, 10473, (short) 41410, (short) 41413, (short) 41416, (short) (short) 41419, (short) (short) 41420, (short) 41422, (short) (short) 41424,
						(short) (short) 41428, (short) 41430, (short) 41445};
				itemDef.modelId = 63080;
				itemDef.primaryMaleModel = 63081;
				itemDef.primaryFemaleModel = 63081;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33052:
				itemDef.setDefaults();
				itemDef.name = "Farming master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{14775, 14792, 22026, 22029, 22032, 22035, 22036, 22038, 22040,
						22044, 22046, 22061};
				itemDef.modelId = 63082;
				itemDef.primaryMaleModel = 63083;
				itemDef.primaryFemaleModel = 63083;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33044:
				itemDef.setDefaults();
				itemDef.name = "Firemaking master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{8125, 9152, 4015, 4018, 4021, 4024, 4025, 4027, 4029, 4033, 4035,
						4050};
				itemDef.modelId = 63084;
				itemDef.primaryMaleModel = 63085;
				itemDef.primaryFemaleModel = 63085;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33043:
				itemDef.setDefaults();
				itemDef.name = "Fishing master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{9144, 9152, (short) 38202, (short) 38205, (short) 38208, (short) 38211, (short) 38212, (short) 38214, (short) 38216,
						(short) 38220, (short) 38222, (short) 38237};
				itemDef.modelId = 63086;
				itemDef.primaryMaleModel = 63087;
				itemDef.primaryFemaleModel = 63087;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33042:
				itemDef.setDefaults();
				itemDef.name = "Fletching master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{6067, 9152, (short) 33670, (short) 33673, (short) 33676, (short) 33679, (short) 33680, (short) 33682, (short) 33684,
						(short) 33688, (short) 33690, (short) 33705};
				itemDef.modelId = 63088;
				itemDef.primaryMaleModel = 63089;
				itemDef.primaryFemaleModel = 63089;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33048:
				itemDef.setDefaults();
				itemDef.name = "Herblore master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{9145, 9156, 22414, 22417, 22420, 22423, 22424, 22426, 22428,
						22432, 22434, 22449};
				itemDef.modelId = 63090;
				itemDef.primaryMaleModel = 63091;
				itemDef.primaryFemaleModel = 63091;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33036:
				itemDef.setDefaults();
				itemDef.name = "Hitpoints master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) (short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{818, 951, 8291, 8294, 8297, 8300, 8301, 8303, 8305, 8309, 8311,
						8319};
				itemDef.modelId = 63092;
				itemDef.primaryMaleModel = 63093;
				itemDef.primaryFemaleModel = 63093;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				//itemDef.femaleOffset = 4;
				break;
			case 33054:
				itemDef.setDefaults();
				itemDef.name = "Hunter master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{5262, 6020, 8472, 8475, 8478, 8481, 8482, 8484, 8486, 8490, 8492,
						8507};
				itemDef.modelId = 63094;
				itemDef.primaryMaleModel = 63095;
				itemDef.primaryFemaleModel = 63095;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33039:
				itemDef.setDefaults();
				itemDef.name = "Magic master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{(short) 43569, (short) 43685, 6336, 6339, 6342, 6345, 6346, 6348, 6350, 6354,
						6356, 6371};
				itemDef.modelId = 63096;
				itemDef.primaryMaleModel = 63097;
				itemDef.primaryFemaleModel = 63097;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33047:
				itemDef.setDefaults();
				itemDef.name = "Mining master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{(short) 36296, (short) 36279, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
						10404, 10406, 10421};
				itemDef.modelId = 63098;
				itemDef.primaryMaleModel = 63099;
				itemDef.primaryFemaleModel = 63099;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33038:
				itemDef.setDefaults();
				itemDef.name = "Prayer master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{9163, 9168, 117, 120, 123, 126, 127, 127, 127, 127, 127, 127};
				itemDef.modelId = 63100;
				itemDef.primaryMaleModel = 63101;
				itemDef.primaryFemaleModel = 63101;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33037:
				itemDef.setDefaults();
				itemDef.name = "Range master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{3755, 3998, 15122, 15125, 15128, 15131, 15132, 15134, 15136,
						15140, 15142, 15157};
				itemDef.modelId = 63102;
				itemDef.primaryMaleModel = 63103;
				itemDef.primaryFemaleModel = 63103;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33053:
				itemDef.setDefaults();
				itemDef.name = "Runecrafting master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{9152, 8128, 10318, 10321, 10324, 10327, 10328, 10330, 10332,
						10336, 10338, 10353};
				itemDef.modelId = 63104;
				itemDef.primaryMaleModel = 63105;
				itemDef.primaryFemaleModel = 63105;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33051:
				itemDef.setDefaults();
				itemDef.name = "Slayer master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811};
				itemDef.modifiedModelColors = new short[]{912, 920};
				itemDef.modelId = 63106;
				itemDef.primaryMaleModel = 63107;
				itemDef.primaryFemaleModel = 63107;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33046:
				itemDef.setDefaults();
				itemDef.name = "Smithing master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{8115, 9148, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
						10404, 10406, 10421};
				itemDef.modelId = 63108;
				itemDef.primaryMaleModel = 63109;
				itemDef.primaryFemaleModel = 63109;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33035:
				itemDef.setDefaults();
				itemDef.name = "Strength master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{935, 931, 27538, 27541, 27544, 27547, 27548, 27550, 27552, 27556,
						27558, 27573};
				itemDef.modelId = 63110;
				itemDef.primaryMaleModel = 63111;
				itemDef.primaryFemaleModel = 63111;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33050:
				itemDef.setDefaults();
				itemDef.name = "Thieving master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{11, 0, (short) 58779, (short) 58782, (short) 58785, (short) 58788, (short) 58789, (short) 57891, (short) 58793, (short) 58797,
						(short) 58799, (short) 58814};
				itemDef.modelId = 63112;
				itemDef.primaryMaleModel = 63113;
				itemDef.primaryFemaleModel = 63113;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33041:
				itemDef.setDefaults();
				itemDef.name = "Woodcutting master cape";
				itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.originalModelColors = new short[]{(short) 57022, (short) 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2};
				itemDef.modifiedModelColors = new short[]{25109, 24088, 6693, 6696, 6699, 6702, 6703, 6705, 6707, 6711,
						6713, 6728};
				itemDef.modelId = 63114;
				itemDef.primaryMaleModel = 63115;
				itemDef.primaryFemaleModel = 63115;
				itemDef.spriteScale = 2300;
				itemDef.spriteYRotation = 400;
				itemDef.spriteZRotation = 1020;
				itemDef.spriteTranslateX = 3;
				itemDef.spriteTranslateY = 30;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = null;
				//itemDef.maleOffset = -2;
				break;
		}
	}

	public static EvictingDualNodeHashTable cached = new EvictingDualNodeHashTable(64);


	public static ItemDefinition lookup(int id) {
		ItemDefinition itemDef = (ItemDefinition) ItemDefinition.cached.get(id);
		if (newCustomItems(id) != null) {
			return newCustomItems(id);
		}
		if (itemDef == null) {
			byte[] data = Js5List.configs.takeFile(Js5ConfigType.ITEM, id);
			itemDef = new ItemDefinition();
			itemDef.setDefaults();
			itemDef.id = id;
			if (data != null) {
				itemDef.decodeValues(new Buffer(data));
			}
			itemDef.post();
			ItemDefinition_Sub1.itemDef((int) id, itemDef);
			ItemDefinition_Sub2.itemDef((int) id, itemDef);
			ItemDefinition_Sub3.itemDef((int) id, itemDef);
			if (itemDef.certTemplateID != -1) {
				itemDef.updateNote(lookup(itemDef.certTemplateID), lookup(itemDef.certID));
			}
			if (itemDef.notedId != -1) {
				itemDef.method2789(lookup(itemDef.notedId), lookup(itemDef.unnotedId));
			}
			if (itemDef.placeholderTemplateId != -1) {
				itemDef.method2790(lookup(itemDef.placeholderTemplateId), lookup(itemDef.placeholderId));
			}


			customItems(id,itemDef);

			cached.put(itemDef, id);
		}
		return itemDef;
	}

	private void post() {
		if (stackable) {
			weight = 0;
		}
	}

	private static ItemDefinition newCustomItems(int itemId) {
		ItemDefinition itemDef = new ItemDefinition();
		itemDef.setDefaults();
		switch (itemId) {
			case 31236:
				return copy(itemDef, 31236, 11738, "Resource box(small)", "Open");
			case 31237:
				return copy(itemDef, 31237, 11738, "Resource box(medium)", "Open");
			case 31238:
				return copy(itemDef, 31238, 11738, "Resource box(large)", "Open");
			case 31239:
				return copy(itemDef, 31239, 4561, "Halloween Candy", "Eat", null, null);
			case 22375:
				return copy(itemDef, 22375, 22374, "Mossy key");
			case 33920:
				return copy(itemDef, 33920, 8013, "Home teleport", "Break");


			case 29499:
				itemDef.id = 29499;
				itemDef.modelId = 63483;
				itemDef.name = "Scorched Scythe";
				itemDef.description = "A scythe from lava.";
				itemDef.spriteScale = 3000;
				itemDef.spriteYRotation = 1750;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				itemDef.primaryMaleModel = 63482;
				itemDef.primaryFemaleModel = 63288;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;

			case 29180:

				itemDef.id = 29180;
				itemDef.modelId = 63129;
				itemDef.name = "Elder ursine chainmace";
				itemDef.description = "A Mace From Dragon Keepers.";
				itemDef.spriteScale = 1488;
				itemDef.spriteYRotation = 1495;
				itemDef.spriteZRotation = 256;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 11;
				itemDef.spriteTranslateY = -8;
				itemDef.primaryMaleModel = 63490;
				itemDef.primaryFemaleModel = 63491;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 8029:
				itemDef.id = 8029;
				itemDef.modelId = 63696;
				itemDef.name = "Kaida Twisted Bow";
				itemDef.description = "Kaida T'Bow.";
				itemDef.spriteScale = 2250;
				itemDef.spriteYRotation = 1500;
				itemDef.spriteZRotation = 1035;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteXRotation = 14;
				itemDef.primaryMaleModel = 63697;
				itemDef.primaryFemaleModel = 63139;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 8813:

				itemDef.id = 8813;
				itemDef.name = "Scorched Torva Platebody";
				itemDef.description = "A body from lava.";
				itemDef.modelId = 63514;
				itemDef.spriteScale = 1480;
				itemDef.spriteYRotation = 518;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -1;
				itemDef.spriteTranslateY = -1;
				itemDef.primaryMaleModel = 63512;
				itemDef.primaryFemaleModel = 63513;
				;
				itemDef.primaryMaleHeadPiece = -1;
				itemDef.primaryFemaleHeadPiece = -1;
				itemDef.value = 600000;
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.certID = -1;
				itemDef.certTemplateID = -1;
				itemDef.stackable = false;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 8812:

				itemDef.id = 8812;
				itemDef.modelId = 63063;
				itemDef.name = "Scorched Torva Platelegs";
				itemDef.description = "Platelegs made from lava.";
				itemDef.spriteScale = 1780;
				itemDef.spriteYRotation = 500;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -1;
				itemDef.spriteTranslateY = 8;
				itemDef.primaryMaleModel = 63064;
				itemDef.primaryFemaleModel = 63052;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 8814:

				itemDef.id = 8814;
				itemDef.modelId = 63690;
				itemDef.name = "Scorched Torva Full Helm";
				itemDef.description = "A Helm from lava.";
				itemDef.spriteScale = 750;
				itemDef.spriteYRotation = 0;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 1;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = -2;
				itemDef.primaryMaleModel = 63691;
				itemDef.primaryFemaleModel = 63053;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;

			case 4371:
				itemDef.id = 4371;
				itemDef.modelId = 63561;
				itemDef.name = "Legendary Cape";
				itemDef.description = "A Cape for the finest.";
				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;
				itemDef.primaryMaleModel = 63561;
				itemDef.primaryFemaleModel = 63561;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[4] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 29431:

				itemDef.id = 29231;
				itemDef.modelId = 63350;
				itemDef.name = "Burning skeleteon Gloves";
				itemDef.description = "spooky skeleton.";

				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;

				itemDef.primaryMaleModel = 63350;
				itemDef.primaryFemaleModel = 63350;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 29432:

				itemDef.id = 29232;
				itemDef.modelId = 63351;
				itemDef.name = "Burning skeleteon mask";
				itemDef.description = "spooky skeleton.";

				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;

				itemDef.primaryMaleModel = 63351;
				itemDef.primaryFemaleModel = 63351;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 29433:

				itemDef.id = 29233;
				itemDef.modelId = 63352;
				itemDef.name = "Burning skeleteon leggings";
				itemDef.description = "spooky skeleton.";

				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;

				itemDef.primaryMaleModel = 63352;
				itemDef.primaryFemaleModel = 63352;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 29434:

				itemDef.id = 29234;
				itemDef.modelId = 63354;
				itemDef.name = "Burning skeleteon Skirt";
				itemDef.description = "spooky skeleton.";

				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;

				itemDef.primaryMaleModel = 63354;
				itemDef.secondaryMaleModel = 63353;
				itemDef.primaryFemaleModel = 63354;
				itemDef.secondaryFemaleModel = 63353;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				itemDef.unnotedId = -1;
				itemDef.notedId = -1;
				itemDef.placeholderId = -1;
				itemDef.placeholderTemplateId = -1;
				return itemDef;
			case 33056:
				itemDef.setDefaults();
				itemDef.id = 33056;
				itemDef.modelId = 63698;
				itemDef.name = "Completionist cape";
				//itemDef.description= "A cape worn by those who've overachieved.";

				itemDef.spriteScale = 1385;
				itemDef.spriteYRotation = 279;
				itemDef.spriteZRotation = 948;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 24;

				itemDef.primaryMaleModel = 63701;
				itemDef.primaryFemaleModel = 63703;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Teleports";
				itemDef.itemActions[3] = "Features";
				itemDef.itemActions[4] = "Drop";
				return itemDef;
			case 33057:
				itemDef.setDefaults();
				itemDef.id = 33057;
				itemDef.modelId = 63699;
				itemDef.name = "Completionist hood";
				//itemDef.description= "A hood worn by those who've over achieved.";

				itemDef.spriteScale = 760;
				itemDef.spriteYRotation = 11;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;

				itemDef.primaryMaleModel = 63700;
				itemDef.primaryFemaleModel = 63702;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				return itemDef;


				//START OF ELVARG REBIRTH
			case 50000:
				itemDef.name = "Avernic Gauntlets";
				itemDef.id = 50000;
				itemDef.modelId = 64996;
				itemDef.primaryMaleModel = 64995;
				itemDef.primaryFemaleModel = 64994;
				itemDef.spriteScale = 724;
				itemDef.spriteYRotation = 609;
				itemDef.spriteZRotation = 111;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = -1;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				return itemDef;
			case 50001:
				itemDef.name = "Avernic Treads";
				itemDef.id = 50001;
				itemDef.modelId = 64999;
				itemDef.primaryMaleModel = 64998;
				itemDef.primaryFemaleModel = 64997;
				itemDef.spriteScale = 724;
				itemDef.spriteYRotation = 171;
				itemDef.spriteZRotation = 250;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = -7;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[2] = "Drop";
				return itemDef;
			case 50002:
				itemDef.id = 50002;
				itemDef.name = "Oath Breaker Helm";
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wear";
				itemDef.itemActions[4] = "Drop";
				itemDef.modelId = 65004;
				itemDef.spriteScale = 750;
				itemDef.spriteYRotation = 0;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 1;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = -2;
				itemDef.primaryMaleModel = 65005;
				itemDef.primaryFemaleModel = 65006;
				return itemDef;
			case 50003:
				itemDef.id = 50003;
				itemDef.name = "Oath breaker platebody";
				itemDef.modelId = 65007;
				itemDef.spriteScale = 1480;
				itemDef.spriteYRotation = 518;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -1;
				itemDef.spriteTranslateY = -1;
				itemDef.primaryMaleModel = 65008;
				itemDef.primaryFemaleModel = 65009;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				return itemDef;

			case 50004:
				itemDef.id = 50004;
				itemDef.modelId = 65010;
				itemDef.name = "Oath breaker platelegs";
				itemDef.spriteScale = 1780;
				itemDef.spriteYRotation = 500;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -1;
				itemDef.spriteTranslateY = 8;
				itemDef.primaryMaleModel = 65011;
				itemDef.primaryFemaleModel = 65012;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				return itemDef;

			case 50005:
				itemDef.id = 50005;
				itemDef.modelId = 65013;
				itemDef.name = "Oath breaker flail";
				itemDef.spriteScale = 3000;
				itemDef.spriteYRotation = 1750;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 0;
				itemDef.spriteTranslateX = -3;
				itemDef.spriteTranslateY = 10;
				itemDef.primaryMaleModel = 65014;
				itemDef.primaryFemaleModel = 65015;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				return itemDef;
			case 50006:
				itemDef.id = 50006;
				itemDef.modelId = 65016;
				itemDef.name = "Founders hood";
				itemDef.spriteScale = 750;
				itemDef.spriteYRotation = 0;
				itemDef.spriteZRotation = 0;
				itemDef.spriteXRotation = 1;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = -2;
				itemDef.primaryMaleModel = 65017;
				itemDef.primaryFemaleModel = 65018;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				return itemDef;
			case 50007:
				itemDef.id = 50007;
				itemDef.modelId = 65019;
				itemDef.name = "Founders cape";
				itemDef.spriteScale = 2000;
				itemDef.spriteYRotation = 517;
				itemDef.spriteZRotation = 0;
				itemDef.spriteTranslateX = 0;
				itemDef.spriteTranslateY = 0;
				itemDef.spriteXRotation = 14;
				itemDef.primaryMaleModel = 65020;
				itemDef.primaryFemaleModel = 65021;
				itemDef.itemActions = new String[5];
				itemDef.itemActions[1] = "Wield";
				itemDef.itemActions[4] = "Drop";
				return itemDef;
		}
		return null;
	}


	void method2790(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		spriteScale = 1 * var1.spriteScale;
		spriteYRotation = var1.spriteYRotation * 1;
		spriteZRotation = var1.spriteZRotation * 1;
		spriteXRotation = var1.spriteXRotation * 1;
		spriteTranslateX = 1 * var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY * 1;
		originalModelColors = var1.originalModelColors;
		modifiedModelColors = var1.modifiedModelColors;
		retextureFrom = var1.retextureFrom;
		retextureTo = var1.retextureTo;
		stackable = var1.stackable;
		name = var2.name;
		value = 0;
	}

	void method2789(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		spriteScale = var1.spriteScale * 1;
		spriteYRotation = 1 * var1.spriteYRotation;
		spriteZRotation = 1 * var1.spriteZRotation;
		spriteXRotation = 1 * var1.spriteXRotation;
		spriteTranslateX = 1 * var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY * 1;
		originalModelColors = var2.originalModelColors;
		modifiedModelColors = var2.modifiedModelColors;
		retextureFrom = var2.retextureFrom;
		retextureTo = var2.retextureTo;
		name = var2.name;
		membersObject = var2.membersObject;
		stackable = var2.stackable;
		primaryMaleModel = 1 * var2.primaryMaleModel;
		secondaryMaleModel = 1 * var2.secondaryMaleModel;
		tertiaryMaleEquipmentModel = 1 * var2.tertiaryMaleEquipmentModel;
		primaryFemaleModel = var2.primaryFemaleModel * 1;
		secondaryFemaleModel = var2.secondaryFemaleModel * 1;
		tertiaryFemaleEquipmentModel = 1 * var2.tertiaryFemaleEquipmentModel;
		primaryMaleHeadPiece = 1 * var2.primaryMaleHeadPiece;
		secondaryMaleHeadPiece = var2.secondaryMaleHeadPiece * 1;
		primaryFemaleHeadPiece = var2.primaryFemaleHeadPiece * 1;
		secondaryFemaleHeadPiece = var2.secondaryFemaleHeadPiece * 1;
		team = var2.team * 1;
		groundActions = var2.groundActions;
		itemActions = new String[5];
		equipActions = new String[5];
		if (null != var2.itemActions) {
			for (int var4 = 0; var4 < 4; ++var4) {
				itemActions[var4] = var2.itemActions[var4];
			}
		}

		itemActions[4] = "Discard";
		value = 0;
	}

	void toPlaceholder(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		spriteScale = 1 * var1.spriteScale;
		spriteYRotation = var1.spriteYRotation * 1;
		spriteZRotation = var1.spriteZRotation * 1;
		spriteXRotation = var1.spriteXRotation * 1;
		spriteTranslateX = 1 * var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY * 1;
		originalModelColors = var1.originalModelColors;
		modifiedModelColors = var1.modifiedModelColors;
		retextureFrom = var1.retextureFrom;
		retextureTo = var1.retextureTo;
		stackable = var1.stackable;
		name = var2.name;
		value = 0;
	}

	public static Sprite getSprite(int itemId, int stackSize, int outlineColor, boolean noted, int border,int shadow) {

		return Sprite.EMPTY_SPRITE;
	}

	public static Sprite getSmallSprite(int itemId) {
		return getSmallSprite(itemId, 1);
	}

	public static Sprite getSmallSprite(int itemId, int stackSize) {
		return Sprite.EMPTY_SPRITE;
	}


	public static Sprite getSprite(int itemId, int stackSize, int outlineColor) {
		int zoom = Client.instance.get3dZoom();
		Client.instance.set3dZoom(CLIENT_DEFAULT_ZOOM);
		try {
			if (outlineColor == 0) {
				Sprite sprite = (Sprite) sprites.get(itemId);
				if (sprite != null && sprite.maxHeight != stackSize) {

					sprite.unlink();
					sprite = null;
				}
				if (sprite != null)
					return sprite;
			}
			ItemDefinition itemDef = lookup(itemId);
			if (itemDef.stackIDs == null)
				stackSize = -1;
			if (stackSize > 1) {
				int stack_item_id = -1;
				for (int j1 = 0; j1 < 10; j1++)
					if (stackSize >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
						stack_item_id = itemDef.stackIDs[j1];

				if (stack_item_id != -1)
					itemDef = lookup(stack_item_id);
			}
			Model model = itemDef.getModel(1);
			if (model == null)
				return null;
			Sprite sprite = null;
			if (itemDef.certTemplateID != -1) {
				sprite = getSprite(itemDef.certID, 10, -1);
				if (sprite == null) {
					return null;
				}
			} else if (itemDef.notedId != -1) {
				sprite = getSprite(itemDef.unnotedId, stackSize, -1);
				if (sprite == null) {
					return null;
				}
			} else if (itemDef.placeholderTemplateId != -1) {
				sprite = getSprite(itemDef.placeholderId, stackSize, -1);
				if (sprite == null) {
					return null;
				}
			}

			int[] pixels = Rasterizer2D.Rasterizer2D_pixels;
			int width = Rasterizer2D.Rasterizer2D_width;
			int height = Rasterizer2D.Rasterizer2D_height;
			float[] depth = Rasterizer2D.Rasterizer2D_brightness;
			int[] arrayClip = new int[4];
			Rasterizer2D.getClipArray(arrayClip);
			Sprite enabledSprite = new Sprite(32, 32);
			Rasterizer3D.initDrawingArea(enabledSprite.myPixels, 32, 32, (float[]) null);
			Rasterizer2D.clear();
			Rasterizer3D.resetRasterClipping();
			Rasterizer3D.setCustomClipBounds(16, 16);
			Rasterizer3D.clips.rasterGouraudLowRes = false;
			model.renderonGpu = false;
			if (itemDef.placeholderTemplateId != -1) {
				int old_w = sprite.maxWidth;
				int old_h = sprite.maxHeight;
				sprite.maxWidth = 32;
				sprite.maxHeight = 32;
				sprite.drawSprite(0, 0);
				sprite.maxWidth = old_w;
				sprite.maxHeight = old_h;
			}

			int k3 = itemDef.spriteScale;
			if (outlineColor == -1)
				k3 = (int) ((double) k3 * 1.5D);
			if (outlineColor > 0)
				k3 = (int) ((double) k3 * 1.04D);

			int l3 = Rasterizer3D.Rasterizer3D_sine[itemDef.spriteYRotation] * k3 >> 16;
			int i4 = Rasterizer3D.Rasterizer3D_cosine[itemDef.spriteYRotation] * k3 >> 16;

			model.calculateBoundsCylinder();
			model.renderModel(itemDef.spriteZRotation, itemDef.spriteXRotation, itemDef.spriteYRotation, itemDef.spriteTranslateX, l3 + model.model_height / 2 + itemDef.spriteTranslateY, i4 + itemDef.spriteTranslateY);

			if (itemDef.certID != -1) {
				enabledSprite.drawAdvancedSprite(0, 0);
			}

			enabledSprite.outline(1);
			if (outlineColor > 0) {
				enabledSprite.outline(16777215);
			}
			if (outlineColor == 0) {
				enabledSprite.shadow(3153952);
			}

			Rasterizer3D.initDrawingArea(enabledSprite.myPixels, 32, 32, (float[]) null);
			if (itemDef.notedId != -1) {
				int l5 = sprite.maxWidth;
				int j6 = sprite.maxHeight;
				sprite.maxWidth = 32;
				sprite.maxHeight = 32;
				sprite.drawSprite(0, 0);
				sprite.maxWidth = l5;
				sprite.maxHeight = j6;
			}
			if (itemDef.certTemplateID != -1) {
				int old_w = sprite.maxWidth;
				int old_h = sprite.maxHeight;
				sprite.maxWidth = 32;
				sprite.maxHeight = 32;
				sprite.drawSprite(0, 0);
				sprite.maxWidth = old_w;
				sprite.maxHeight = old_h;
			}

			if (outlineColor == 0 && !itemDef.animateInventory) {
				sprites.put(enabledSprite, itemId);
			}


			Rasterizer3D.initDrawingArea(pixels, width, height, depth);
			Rasterizer2D.setClipArray(arrayClip);
			Rasterizer3D.resetRasterClipping();
			Rasterizer3D.clips.rasterGouraudLowRes = true;
			model.renderonGpu = true;
			return enabledSprite;
		} finally {
			Client.instance.set3dZoom(zoom);
		}
	}

	public static Sprite getSprite(int var0, int var1, int var2, int var3, int var4, boolean var5) {
		if (var1 == -1) {
			var4 = 0;
		} else if (var4 == 2 && var1 != 1) {
			var4 = 1;
		}

		long var6 = ((long) var2 << 38) + (long) var0 + ((long) var1 << 16) + ((long) var4 << 40) + ((long) var3 << 42);
		Sprite var8;
		if (!var5) {
			var8 = (Sprite) cached.get(var6);
			if (var8 != null) {
				return var8;
			}
		}

		ItemDefinition var9 = ItemDefinition.lookup(var0);
		if (var1 > 1 && var9.stackIDs != null) {
			int var10 = -1;

			for (int var11 = 0; var11 < 10; ++var11) {
				if (var1 >= var9.stackAmounts[var11] && var9.stackAmounts[var11] != 0) {
					var10 = var9.stackIDs[var11];
				}
			}

			if (var10 != -1) {
				var9 = ItemDefinition.lookup(var10);
			}
		}

		Model var20 = var9.getModel(1);
		if (var20 == null) {
			return null;
		} else {
			Sprite var21 = null;
			if (var9.certTemplateID != -1) {
				var21 = getSprite(var9.certID, 10, 1, 0, 0, true);
				if (var21 == null) {
					return null;
				}
			} else if (var9.certID != -1) {
				var21 = getSprite(var9.unnotedId, var1, var2, var3, 0, false);
				if (var21 == null) {
					return null;
				}
			} else if (var9.placeholderTemplateId != -1) {
				var21 = getSprite(var9.placeholderId, var1, 0, 0, 0, false);
				if (var21 == null) {
					return null;
				}
			}

			int[] var12 = Rasterizer2D.Rasterizer2D_pixels;
			int var13 = Rasterizer2D.Rasterizer2D_width;
			int var14 = Rasterizer2D.Rasterizer2D_height;
			float[] var15 = Rasterizer2D.Rasterizer2D_brightness;
			int[] var16 = new int[4];
			Rasterizer2D.getClipArray(var16);
			var8 = new Sprite(36, 32);
			Rasterizer3D.initDrawingArea(var8.myPixels, 36, 32, (float[]) null);
			Rasterizer2D.clear();
			Rasterizer3D.resetRasterClipping();
			Rasterizer3D.setCustomClipBounds(16, 16);
			Rasterizer3D.clips.rasterGouraudLowRes = false;
			var20.renderonGpu = false;
			if (var9.placeholderTemplateId != -1) {
				var21.drawAdvancedSprite(0, 0);
			}

			int var17 = var9.spriteScale;
			if (var5) {
				var17 = (int) (1.5D * (double) var17);
			} else if (var2 == 2) {
				var17 = (int) ((double) var17 * 1.04D);
			}

			int var18 = var17 * Rasterizer3D.Rasterizer3D_sine[var9.spriteYRotation] >> 16;
			int var19 = var17 * Rasterizer3D.Rasterizer3D_cosine[var9.spriteYRotation] >> 16;
			var20.calculateBoundsCylinder();
			var20.renderModel(var9.spriteZRotation, var9.spriteXRotation, var9.spriteYRotation, var9.spriteTranslateX, var20.model_height / 2 + var18 + var9.spriteTranslateY, var19 + var9.spriteTranslateY);
			if (var9.certID != -1) {
				var21.drawAdvancedSprite(0, 0);
			}

			if (var2 >= 1) {
				var8.outline(1);
			}

			if (var2 >= 2) {
				var8.outline(16777215);
			}

			if (var3 != 0) {
				var8.shadow(var3);
			}

			Rasterizer3D.initDrawingArea(var8.myPixels, 36, 32, (float[]) null);
			if (var9.certTemplateID != -1) {
				var21.drawAdvancedSprite(0, 0);
			}

			if (var4 == 1 || var4 == 2 && var9.stackable) {
				Client.instance.newSmallFont.drawBasicString(method633(var1), 0, 9, 16776960, 1);
			}

			if (!var5) {
				sprites.put(var8, var6);
			}

			Rasterizer3D.initDrawingArea(var12, var13, var14, var15);
			Rasterizer2D.setClipArray(var16);
			Rasterizer3D.resetRasterClipping();
			Rasterizer3D.clips.rasterGouraudLowRes = true;
			var20.renderonGpu = true;
			return var8;
		}
	}

	static String method633(int var0) {
		if (var0 < 100000) {
			return "<col=ffff00>" + var0 + "</col>";
		} else {
			return var0 < 10000000 ? "<col=ffffff>" + var0 / 1000 + "K" + "</col>" : "<col=00ff80>" + var0 / 1000000 + "M" + "</col>";
		}
	}

	public boolean isDialogueModelCached(int gender) {
		int model_1 = primaryMaleHeadPiece;
		int model_2 = secondaryMaleHeadPiece;
		if (gender == 1) {
			model_1 = primaryFemaleHeadPiece;
			model_2 = secondaryFemaleHeadPiece;
		}
		if (model_1 == -1)
			return true;
		boolean cached = Js5List.models.tryLoadFile(model_1);
		if (model_2 != -1 && !Js5List.models.tryLoadFile(model_2))
			cached = false;
		return cached;
	}

	public boolean isEquippedModelCached(int gender) {
		int primaryModel = primaryMaleModel;
		int secondaryModel = secondaryMaleModel;
		int emblem = tertiaryMaleEquipmentModel;
		if (gender == 1) {
			primaryModel = primaryFemaleModel;
			secondaryModel = secondaryFemaleModel;
			emblem = tertiaryFemaleEquipmentModel;
		}
		if (primaryModel == -1)
			return true;
		boolean cached = Js5List.models.tryLoadFile(primaryModel);
		if (secondaryModel != -1 && !Js5List.models.tryLoadFile(secondaryModel))
			cached = false;
		if (emblem != -1 && !Js5List.models.tryLoadFile(emblem))
			cached = false;
		return cached;
	}

	public ModelData getEquippedModel(int gender) {
		int primaryModel = primaryMaleModel;
		int secondaryModel = secondaryMaleModel;
		int emblem = tertiaryMaleEquipmentModel;

		if (gender == 1) {
			primaryModel = primaryFemaleModel;
			secondaryModel = secondaryFemaleModel;
			emblem = tertiaryFemaleEquipmentModel;
		}

		if (primaryModel == -1)
			return null;
		ModelData primaryModel_ = ModelData.getModel(primaryModel);
		if (secondaryModel != -1)
			if (emblem != -1) {
				ModelData secondaryModel_ = ModelData.getModel(secondaryModel);
				ModelData emblemModel = ModelData.getModel(emblem);
				ModelData[] models = {primaryModel_, secondaryModel_, emblemModel};
				primaryModel_ = new ModelData(models,3);
			} else {
				ModelData model_2 = ModelData.getModel(secondaryModel);
				ModelData[] models = {primaryModel_, model_2};
				primaryModel_ = new ModelData(models,2);
			}
		if (gender == 0 && maleYOffset != 0|| modelYOffset != 0 || modelXOffset != 0)
			primaryModel_.translate(0, maleYOffset + modelYOffset, 0);
		if (gender == 1 && femaleYOffset != 0|| modelYOffset != 0 || modelXOffset != 0)
			primaryModel_.translate(0, femaleYOffset + modelYOffset, 0);


		if (originalModelColors != null) {
			for (int index = 0; index < originalModelColors.length; index++) {
				primaryModel_.recolor((short) originalModelColors[index], (short) modifiedModelColors[index]);
			}
		}
		if (retextureFrom != null) {
			for (int index = 0; index < retextureFrom.length; index++) {
				primaryModel_.retexture(retextureFrom[index], retextureTo[index]);
			}
		}

		return primaryModel_;
	}

	/**
	 * Gets the head piece model for dialogue/chat heads.
	 * Uses the head piece model fields instead of the body equipment models.
	 */
	public ModelData getHeadPieceModel(int gender) {
		int primaryModel = primaryMaleHeadPiece;
		int secondaryModel = secondaryMaleHeadPiece;

		if (gender == 1) {
			primaryModel = primaryFemaleHeadPiece;
			secondaryModel = secondaryFemaleHeadPiece;
		}

		if (primaryModel == -1)
			return null;

		ModelData primaryModel_ = ModelData.getModel(primaryModel);
		if (secondaryModel != -1) {
			ModelData secondaryModel_ = ModelData.getModel(secondaryModel);
			ModelData[] models = {primaryModel_, secondaryModel_};
			primaryModel_ = new ModelData(models, 2);
		}

		if (originalModelColors != null) {
			for (int index = 0; index < originalModelColors.length; index++) {
				primaryModel_.recolor((short) originalModelColors[index], (short) modifiedModelColors[index]);
			}
		}
		if (retextureFrom != null) {
			for (int index = 0; index < retextureFrom.length; index++) {
				primaryModel_.retexture(retextureFrom[index], retextureTo[index]);
			}
		}

		return primaryModel_;
	}

	public void setDefaults() {
		customSpriteLocation = null;
		customSmallSpriteLocation = null;
		equipActions = new String[]{"Remove", null, "Operate", null, null};
		modelId = 0;
		name = null;
		originalModelColors = null;
		modifiedModelColors = null;
		retextureTo = null;
		retextureFrom = null;

		spriteScale = 2000;
		spriteYRotation = 0;
		spriteZRotation = 0;
		spriteXRotation = 0;
		spriteTranslateX = 0;
		spriteTranslateY = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = new String[]{null, null, "Take", null, null};
		itemActions = new String[]{null, null, null, null, "Drop"};
		primaryMaleModel = -1;
		secondaryMaleModel = -1;
		maleYOffset = 0;
		primaryFemaleModel = -1;
		secondaryFemaleModel = -1;
		femaleYOffset = 0;
		modelXOffset = 0;
		modelYOffset = 0;
		tertiaryMaleEquipmentModel = -1;
		tertiaryFemaleEquipmentModel = -1;
		primaryMaleHeadPiece = -1;
		secondaryMaleHeadPiece = -1;
		primaryFemaleHeadPiece = -1;
		secondaryFemaleHeadPiece = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		groundScaleX = 128;
		groundScaleY = 128;
		groundScaleZ = 128;
		ambient = 0;
		contrast = 0;
		team = 0;
		notedId = -1;
		unnotedId = -1;
		placeholderId = -1;
		placeholderTemplateId = -1;
		glowColor = -1;
		searchable = false;
	}

	private void copy(ItemDefinition copy) {
		spriteZRotation = copy.spriteZRotation;
		spriteYRotation = copy.spriteYRotation;
		spriteXRotation = copy.spriteXRotation;
		groundScaleX = copy.groundScaleX;
		groundScaleY = copy.groundScaleY;
		groundScaleZ = copy.groundScaleZ;
		spriteScale = copy.spriteScale;
		spriteTranslateX = copy.spriteTranslateX;
		spriteTranslateY = copy.spriteTranslateY;
		modelId = copy.modelId;
		stackable = copy.stackable;

	}

	private void updateNote(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId;
		spriteScale = var1.spriteScale;
		spriteYRotation = var1.spriteYRotation;
		spriteZRotation = var1.spriteZRotation;

		spriteXRotation = var1.spriteXRotation;
		spriteTranslateX = var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY;
		originalModelColors = var1.originalModelColors;
		modifiedModelColors = var1.modifiedModelColors;
		retextureFrom = var1.retextureFrom;
		retextureTo = var1.retextureTo;
		name = var2.name;
		this.description = "Swap this note at any bank for the equivalent item";
		membersObject = var2.membersObject;
		value = var2.value;
		stackable = true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getNote() {
		return certTemplateID;
	}

	@Override
	public int getLinkedNoteId() {
		return certID;
	}

	@Override
	public int getPlaceholderId() {
		return placeholderId;
	}

	@Override
	public int getPlaceholderTemplateId() {
		return placeholderTemplateId;
	}

	@Override
	public int getPrice() {
		return value;
	}

	@Override
	public boolean isMembers() {
		return membersObject;
	}

	@Override
	public boolean isTradeable() {
		return certTemplateID == -1;
	}

	@Override
	public void setTradeable(boolean yes) {

	}

	@Override
	public int getIsStackable() {
		return stackable ? 1 : 0;
	}

	@Override
	public int getMaleModel() {
		return primaryMaleModel;
	}

	@Override
	public String[] getInventoryActions() {
		return itemActions;
	}

	@Override
	public String[] getGroundActions() {
		return groundActions;
	}

	@Override
	public int getShiftClickActionIndex() {
		return shiftClickIndex;
	}

	@Override
	public void setShiftClickActionIndex(int shiftClickActionIndex) {

	}

	public ModelData getModelWidget(int stack_size) {
		if (stackIDs != null && stack_size > 1) {
			int stack_item_id = -1;
			for (int index = 0; index < 10; index++) {
				if (stack_size >= stackAmounts[index] && stackAmounts[index] != 0)
					stack_item_id = stackIDs[index];
			}
			if (stack_item_id != -1)
				return lookup(stack_item_id).getModelWidget(1);

		}
		ModelData widget_model = ModelData.getModel(modelId);
		if (widget_model == null)
			return null;

		int var4;
		if (this.originalModelColors != null) {
			for (var4 = 0; var4 < this.originalModelColors.length; ++var4) {
				widget_model.recolor((short) this.originalModelColors[var4], (short) this.modifiedModelColors[var4]);
			}
		}

		if (this.retextureFrom != null) {
			for (var4 = 0; var4 < this.retextureFrom.length; ++var4) {
				widget_model.retexture(this.retextureFrom[var4], this.retextureTo[var4]);
			}
		}


		return widget_model;
	}

	public Model getModel(int stack_size) {
		if (this.stackIDs != null && stack_size > 1) {
			int var2 = -1;

			for (int var3 = 0; var3 < 10; ++var3) {
				if (stack_size >= this.stackAmounts[var3] && this.stackAmounts[var3] != 0) {
					var2 = this.stackIDs[var3];
				}
			}

			if (var2 != -1) {
				return lookup(var2).getModel(1);
			}
		}

		Model var5 = (Model) models.get((long) this.id);
		if (var5 != null) {
			return var5;
		} else {
			ModelData var6 = ModelData.getModel(this.modelId);
			if (var6 == null) {
				return null;
			} else {
				if (this.groundScaleX != 128 || this.groundScaleY != 128 || this.groundScaleZ != 128) {
					var6.resize(this.groundScaleX, this.groundScaleY, this.groundScaleZ);
				}


				int var4;
				if (this.originalModelColors != null) {
					for (var4 = 0; var4 < this.originalModelColors.length; ++var4) {
						var6.recolor((short) this.originalModelColors[var4], (short) this.modifiedModelColors[var4]);
					}
				}

				if (this.retextureFrom != null) {
					for (var4 = 0; var4 < this.retextureFrom.length; ++var4) {
						var6.retexture(this.retextureFrom[var4], this.retextureTo[var4]);
					}
				}


				var5 = var6.toModel(this.ambient + 64, this.contrast + 768, -50, -10, -50);
				var5.isSingleTile = true;

				models.put(var5, (long) this.id);
				return var5;
			}
		}
	}


	@Override
	public int getInventoryModel() {
		return modelId;
	}

	@Override
	public short[] getColorToReplaceWith() {
		return new short[0];
	}

	@Override
	public short[] getTextureToReplaceWith() {
		return new short[0];
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

	private void decodeValues(Buffer stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				return;
			if (opcode == 1) {
				modelId = stream.readUShort();
			} else if (opcode == 2) {
				name = stream.readStringCp1252NullTerminated();
			} else if (opcode == 3) {
				description = stream.readStringCp1252NullTerminated();
			} else if (opcode == 4) {
				spriteScale = stream.readUShort();
			} else if (opcode == 5) {
				spriteYRotation = stream.readUShort();
			} else if (opcode == 6) {
				spriteZRotation = stream.readUShort();
			} else if (opcode == 7) {
				spriteTranslateX = stream.readUShort();
				if (spriteTranslateX > 32767) {
					spriteTranslateX -= 65536;
				}
			} else if (opcode == 8) {
				spriteTranslateY = stream.readUShort();
				if (spriteTranslateY > 32767) {
					spriteTranslateY -= 65536;
				}
			} else if (opcode == 9) {
				stream.readStringCp1252NullTerminated();
			} else if (opcode == 11) {
				stackable = true;
			} else if (opcode == 12) {
				value = stream.readInt();
			} else if (opcode == 13) {
				wearPos1 = stream.readByte();
			} else if (opcode == 14) {
				wearPos2 = stream.readByte();
			} else if (opcode == 16) {
				membersObject = true;
			} else if (opcode == 23) {
				primaryMaleModel = stream.readUShort();
				maleYOffset = stream.readUnsignedByte();
			} else if (opcode == 24) {
				secondaryMaleModel = stream.readUShort();
			} else if (opcode == 25) {
				primaryFemaleModel = stream.readUShort();
				femaleYOffset = stream.readUnsignedByte();
			} else if (opcode == 26) {
				secondaryFemaleModel = stream.readUShort();
			} else if (opcode == 27) {
				wearPos3 = stream.readByte();
			} else if (opcode >= 30 && opcode < 35) {
				groundActions[opcode - 30] = stream.readStringCp1252NullTerminated();
				if (groundActions[opcode - 30].equalsIgnoreCase("Hidden")) {
					groundActions[opcode - 30] = null;
				}
			} else if (opcode >= 35 && opcode < 40) {
				if (itemActions == null) {
					itemActions = new String[5];
				}
				itemActions[opcode - 35] = stream.readStringCp1252NullTerminated();
			} else if (opcode == 40) {
				int var5 = stream.readUnsignedByte();
				originalModelColors = new short[var5];
				modifiedModelColors = new short[var5];

				for (int var4 = 0; var4 < var5; ++var4) {
					originalModelColors[var4] = (short) stream.readUShort();
					modifiedModelColors[var4] = (short) stream.readUShort();
				}

			} else if (opcode == 41) {
				int var5 = stream.readUnsignedByte();
				retextureFrom = new short[var5];
				retextureTo = new short[var5];

				for (int var4 = 0; var4 < var5; ++var4) {
					retextureFrom[var4] = (short) stream.readUShort();
					retextureTo[var4] = (short) stream.readUShort();
				}

			} else if (opcode == 42) {
				shiftClickIndex = stream.readByte();
			} else if (opcode == 43) {
			int var3 = stream.readUnsignedByte();
			if (this.subOps == null) {
				this.subOps = new String[5][];
			}

			boolean var7 = var3 >= 0 && var3 < 5;
			if (var7 && this.subOps[var3] == null) {
				this.subOps[var3] = new String[20];
			}

			while (true) {
				int var5 = stream.readUnsignedByte() - 1;
				if (var5 == -1) {
					break;
				}

				String var6 = stream.readStringCp1252NullTerminated();
				if (var7 && var5 >= 0 && var5 < 20) {
					this.subOps[var3][var5] = var6;
				}
			}
			} else if (opcode == 65) {
				searchable = true;
			} else if (opcode == 75) {
				weight = stream.readShort();
			} else if (opcode == 78) {
				tertiaryMaleEquipmentModel = stream.readUShort();
			} else if (opcode == 79) {
				tertiaryFemaleEquipmentModel = stream.readUShort();
			} else if (opcode == 90) {
				primaryMaleHeadPiece = stream.readUShort();
			} else if (opcode == 91) {
				primaryFemaleHeadPiece = stream.readUShort();
			} else if (opcode == 92) {
				secondaryMaleHeadPiece = stream.readUShort();
			} else if (opcode == 93) {
				secondaryFemaleHeadPiece = stream.readUShort();
			} else if (opcode == 94) {
				category = stream.readUShort();
			} else if (opcode == 95) {
				spriteXRotation = stream.readUShort();
			} else if (opcode == 97) {
				certID = stream.readUShort();
			} else if (opcode == 98) {
				certTemplateID = stream.readUShort();
			} else if (opcode >= 100 && opcode < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}

				stackIDs[opcode - 100] = stream.readUShort();
				stackAmounts[opcode - 100] = stream.readUShort();
			} else if (opcode == 110) {
				groundScaleX = stream.readUShort();
			} else if (opcode == 111) {
				groundScaleY = stream.readUShort();
			} else if (opcode == 112) {
				groundScaleZ = stream.readUShort();
			} else if (opcode == 113) {
				ambient = stream.readByte();
			} else if (opcode == 114) {
				contrast = stream.readByte();
			} else if (opcode == 115) {
				team = stream.readUnsignedByte();
			} else if (opcode == 139) {
				unnotedId = stream.readUShort();
			} else if (opcode == 140) {
				notedId = stream.readUShort();
			} else if (opcode == 148) {
				placeholderId = stream.readUShort();
			} else if (opcode == 149) {
				placeholderTemplateId = stream.readUShort();
			} else if (opcode == 249) {
				this.params = Buffer.readStringIntParameters(stream, this.params);
			} else {
				System.err.printf("Error unrecognised {Items} opcode: %d%n%n", opcode);
			}
		}
	}

	@Override
	public int getHaPrice() {
		return (int) (value * 0.6);
	}

	@Override
	public boolean isStackable() {
		return stackable;
	}

	@Override
	public void resetShiftClickActionIndex() {

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
}



