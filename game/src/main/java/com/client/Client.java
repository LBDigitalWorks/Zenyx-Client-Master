package com.client;

import com.client.audio.ObjectSound;
import com.client.audio.StaticSound;
import com.client.camera.Camera;
import com.client.collection.EvictingDualNodeHashTable;
import com.client.collection.node.NodeDeque;
import com.client.definitions.anim.SequenceDefinition;
import com.client.definitions.healthbar.HealthBarDefinition;
import com.client.definitions.healthbar.HealthBarUpdate;
import com.client.definitions.healthbar.HealthBar;
import com.client.draw.Rasterizer3D;
import com.client.draw.ImageCache;
import com.client.draw.font.custom.FontArchiver;
import com.client.draw.font.osrs.FontInfo;
import com.client.draw.font.osrs.FontLoader;
import com.client.draw.font.osrs.RSFontOSRS;
import com.client.draw.login.LoginBackground;
import com.client.draw.login.LoginScreen;
import com.client.draw.login.LoginState;
import com.client.draw.rasterizer.Clips;
import com.client.draw.widget.components.Background;
import com.client.draw.widget.components.Divider;
import com.client.draw.widget.components.Rasterizer;
import com.client.engine.impl.MouseWheel;
import com.client.engine.keys.KeyEventProcessorImpl;
import com.client.entity.model.Model;
import com.client.entity.model.ViewportMouse;
import com.client.graphics.GraphicsDefaults;
import com.client.graphics.SpriteData;
import com.client.graphics.loaders.*;
import com.client.mixins.menu.RuneLiteMenuEntry;
import com.client.scene.*;
import com.client.js5.Js5ArchiveIndex;
import com.client.js5.Js5List;
import com.client.js5.Js5System;
import com.client.js5.disk.ArchiveDisk;
import com.client.js5.disk.ArchiveDiskActionHandler;
import com.client.collection.node.Node;
import com.client.js5.net.JagexNetThread;
import com.client.js5.util.ArchiveLoader;
import com.client.js5.util.Js5ConfigType;
import com.client.scene.Projectile;
import com.client.scene.object.GroundDecoration;
import com.client.scene.object.InteractiveObject;
import com.client.scene.object.Wall;
import com.client.scene.object.WallDecoration;
import com.client.settings.AccountData;
import com.client.settings.SettingManager;
import com.client.util.ColorUtils;
import com.client.util.headicon.class421;
import com.client.util.headicon.class423;
import com.google.common.primitives.Ints;
import com.util.AssetUtils;
import com.client.hover.HoverMenuManager;
import java.awt.*;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.swing.JFrame;

import ch.qos.logback.classic.Level;
import com.client.broadcast.Broadcast;
import com.client.broadcast.BroadcastManager;
import com.client.definitions.*;
import com.client.definitions.server.ItemDef;
import com.client.engine.impl.KeyHandler;
import com.client.engine.impl.MouseHandler;
import com.client.features.settings.Preferences;
import com.client.graphics.interfaces.Configs;
import com.client.graphics.interfaces.builder.impl.GroupIronmanBank;
import com.client.graphics.interfaces.daily.DailyRewards;
import com.client.graphics.interfaces.eventcalendar.EventCalendar;
import com.client.graphics.interfaces.impl.Autocast;
import com.client.graphics.interfaces.impl.Bank;
import com.client.graphics.interfaces.impl.DonatorRewards;
import com.client.graphics.interfaces.impl.Interfaces;
import com.client.graphics.interfaces.impl.MonsterDropViewer;
import com.client.graphics.interfaces.impl.Nightmare;
import com.client.graphics.interfaces.impl.QuestTab;

import static com.client.scene.SceneGraph.low_detail;
import static com.client.scene.SceneGraph.pitchRelaxEnabled;
import static com.client.engine.impl.MouseHandler.clickMode3;
import static net.runelite.api.MenuAction.UNKNOWN;

import com.client.features.EntityTarget;
import com.client.features.ExperienceDrop;
import com.client.features.gametimers.GameTimer;
import com.client.features.gametimers.GameTimerHandler;
import com.client.features.settings.InformationFile;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.DropdownMenu;
import com.client.graphics.interfaces.impl.GrandExchange;
import com.client.graphics.interfaces.impl.Keybinding;
import com.client.graphics.interfaces.impl.SpawnContainer;
import com.client.graphics.interfaces.settings.Setting;
import com.client.graphics.interfaces.settings.SettingsInterface;
import com.client.graphics.interfaces.impl.SettingsTabWidget;
import com.client.graphics.interfaces.impl.Slider;
import com.client.graphics.textures.TextureProvider;
import com.client.model.Items;
import com.client.script.ClientScripts;
import com.client.sign.Signlink;
import com.client.sound.SoundType;
import com.client.ui.DevConsole;
import com.client.utilities.*;
import com.client.utilities.settings.Settings;
import com.client.utilities.settings.SettingsManager;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import dorkbox.notify.Notify;
import dorkbox.notify.Pos;
import dorkbox.util.ActionHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.events.*;
import net.runelite.api.widgets.WidgetID;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.runelite.api.*;
import net.runelite.api.clan.ClanRank;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.vars.AccountType;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.rs.api.*;
import com.client.engine.GameEngine;

@Slf4j
public class Client extends GameEngine implements RSClient {

	public LoginScreen loginScreen = new LoginScreen(this);
	private final PacketHandler packetHandler = new PacketHandler(this);
	final GameRenderer gameRenderer = new GameRenderer(this);
	final InterfaceManager interfaceManager = new InterfaceManager(this);
	final MenuHandler menuHandler = new MenuHandler(this);

	public static KeyEventProcessorImpl keyManager;
	static MouseWheel mouseWheel;

	public static Enum findEnumerated(Enum[] var0, int var1) {
		Enum[] var2 = var0;

		for (int var3 = 0; var3 < var2.length; ++var3) {
			Enum var4 = var2[var3];
			if (var1 == var4.rsOrdinal()) {
				return var4;
			}
		}

		return null;
	}

    protected void setUp() {
		setUpKeyboard();
		mouseWheel = this.mouseWheel();
		keyHandler.assignProcessor(keyManager, 0);
		setUpClipboard();
		Signlink.masterDisk = new ArchiveDisk(255, Signlink.cacheData, Signlink.cacheMasterIndex, 500000);
	}

	public final void init() {
		nodeID = 1;
		portOff = 0;
		setHighMem();
		isMembers = true;

		try {
			Signlink.init(25);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance = this;
		Sprite.start();

		Sprite.loadManifest();
		settingManager = SettingManager.Companion.load();
		startThread(765, 503, 206, 1);
		setMaxCanvasSize(765, 503);
		//FontArchiver.writeFontArchive();
        try {
            FontArchiver.loadArchive();
        } catch (IOException e) {
			e.printStackTrace();
        }
		LoginBackground.Companion.load();
    }



	@Override
	protected void vmethod1099() {

	}

	int[] quakeMagnitude = new int[5];

	public SettingManager settingManager;
	protected final void resizeGame() {

	}

	private static final Logger logger = LoggerFactory.getLogger("Client");
	public String lastViewedDropTable;

	public boolean isServerUpdating() {
		return anInt1104 > 0;
	}

	public int getChatboxTextWidth() {
		if (chatArea != null) {
			// Match the chat text area width (chatbox minus scrollbar/padding).
			return Math.max(20, chatArea.getWidth() - 22);
		}
		return 497;
	}

	public DevConsole devConsole = new DevConsole();
	public int scrollbar_position;
	int scrollMax = 0;
	public void draw_scrollbar(int x, int y, int width, int height, int offset, int max_lines, int height_seperator, int y_pad)
	{
		scrollMax = (offset * max_lines + (height_seperator - 1));
		if(scrollMax < height - 3)
			scrollMax = height - 3;

		aClass9_1059.scrollPosition = scrollMax - scrollbar_position - (height - 4);
		if(scrollMax > height)
			method65(x, height, MouseHandler.mouseX - 0, MouseHandler.mouseY - y, aClass9_1059, y_pad, true, scrollMax);

		int pos = scrollMax - (height - 4) - aClass9_1059.scrollPosition;
		if(pos < 0)
			pos = 0;

		if(pos > scrollMax - (height - 4))
			pos = scrollMax - (height - 4);

		if(scrollbar_position != pos)
			scrollbar_position = pos;

		drawScrollbar(height, scrollMax - scrollbar_position - (height - 1), x, y, scrollMax);
	}


	boolean screenFlashDrawing;
	boolean screenFlashOpacityDownward;
	double screenFlashOpacity = 0d;
	int screenFlashColor = 0;
	int screenFlashMaxIntensity = 30;
	boolean screenFlashAutoFadeOut;
	public static final int MUD_SPLASH_SENTINEL = 0x4D554453; // "MUDS"
	private int mudSplashTicks = 0;
	private int mudSplashTotalTicks = 0;
	private Sprite mudSplashSprite;
	private int mudSplashSpriteW = -1;
	private int mudSplashSpriteH = -1;
	private boolean mudSplashDirty = false;

	public boolean fogEnabled;
	public int fogOpacity;



	public int getDragSetting(int interfaceId) {
		return interfaceId == 3214 ? Preferences.getPreferences().dragTime : 5;
	}



	public static int IDLE_TIME = 30000; // 1 minute = 3000
	public boolean hintMenu;
	public String hintName;
	public int hintId;

	boolean pollActive;

	private Pattern pattern;

	private Matcher matcher;

	boolean placeHolders = true;

	public static boolean debugModels = false;

	EntityTarget entityTarget;

	public InformationFile informationFile = new InformationFile();

	public static boolean snowVisible = Configuration.CHRISTMAS ? true : false;

	public static int[][] runePouch = new int[][] { { -1, -1 }, { -1, -1 }, { -1, -1 } };

	public static int itemX = 0, itemY = 0;

	public static boolean shiftDrop = true;

	public static boolean shiftDown;
	public static boolean isCtrlPressed = false;

	static boolean removeShiftDropOnMenuOpen;

	public static Settings getUserSettings() {
		return userSettings;
	}

	public static void setUserSettings(Settings settings) {
		Client.userSettings = settings;
	}

	private static Settings userSettings;

	/*
	 * @Override public void keyPressed(KeyEvent event) { MouseHandler.keyPressed(event);
	 * if(loggedIn) { stream.createFrame(186); stream.writeWord(event.getKeyCode());
	 * } }
	 */
	public static void mouseMoved() {
		if (loggedIn)
			// if(idleTime >= (Client.IDLE_TIME - 500))
			stream.createFrame(187);
	}

	/**
	 * Handles rune pouch input
	 * @return
	 * @author Sky
	 */
	private void loadPlayerData() throws IOException {
		File file = new File(Signlink.getCacheDirectory() + "/fkeys.dat");
		if (!file.exists()) {
			return;
		}

		DataInputStream stream = new DataInputStream(new FileInputStream(file));

		try {
			for(int i = 0; i < Keybinding.KEYBINDINGS.length; i++) {
				Keybinding.KEYBINDINGS[i] = stream.readByte();
			}
			if (stream.available() > 0) {
				Configuration.escapeCloseInterface = stream.readByte() == 1;
			}
		} catch (IOException e) {
			System.out.println("Unable to load player data.");
			file.delete();
		} finally {
			stream.close();
		}
	}
	public void savePlayerData() {
		try {
			File file = new File(Signlink.getCacheDirectory() + "/fkeys.dat");
			if (!file.exists()) {
				file.createNewFile();
			}
			DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));

			try {
				for(int i = 0; i < 14; i++) {
					stream.writeByte(Keybinding.KEYBINDINGS[i]);
				}
				stream.writeByte(Configuration.escapeCloseInterface ? 1 : 0);
			} finally {
				stream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleScrollPosition(String text, int frame) {
		if (text.startsWith(":scp:")) {
			int scrollPosition = Integer.parseInt(text.split(" ")[1]);
			RSInterface widget = RSInterface.interfaceCache[frame];
			widget.scrollPosition = scrollPosition;
		} else if (text.startsWith(":scpfind:")) {
			RSInterface widget = RSInterface.interfaceCache[frame];
			sendString(7, widget.scrollPosition + "");
		}

		return;
	}
	public static boolean handleRunePouch(String text, int frame) {
		if (!(text.startsWith("#") && text.endsWith("$") && frame == 49999)) {
			return false;
		}
		try {
			// System.out.println("got here");
			text = text.replace("#", "");
			text = text.replace("$", "");
			String[] runes = text.split("-");
			for (int index = 0; index < runes.length; index++) {
				String[] args = runes[index].split(":");
				int id = Integer.parseInt(args[0]);
				int amt = Integer.parseInt(args[1]);
				runePouch[index][0] = id;
				runePouch[index][1] = amt;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	private boolean updateGameMode = false;

	public void frameMode(boolean resizable) {
		if(isResized() == resizable) {
			return;
		}

		setResized(resizable);

		Bounds bounds = getFrameContentBounds();
		canvasWidth = !isResized() ? 765 : bounds.highX;
		canvasHeight = !isResized() ? 503 : bounds.highY;

		changeTabArea = !isResized() ? false : true;
		changeChatArea = !isResized() ? false : true;
		setMaxCanvasSize(canvasWidth, canvasHeight);
		ResizeableChanged event = new ResizeableChanged();
		event.setResized(resizable);
		callbacks.post(event);

		//setBounds();

		showChatComponents = !isResized() || showChatComponents;
		showTabComponents = !isResized() || showTabComponents;
	}

	private void setBounds() {
		Preferences.getPreferences().fixed = !isResized();
		Preferences.getPreferences().screenWidth = canvasWidth;
		Preferences.getPreferences().screenHeight = canvasHeight;


	}


	long experienceCounter;
	private Sprite mapBack;
	private Sprite[] smallXpSprites = new Sprite[22];
	public static Sprite[] cacheSprite474;
	private static final long serialVersionUID = 1L;
	Sprite[] inputSprites = new Sprite[7];

	int modifiableXValue = 1; // u dont care if it starts at 1? Can't see a real problem with it :P kk
	int achievementCutoff = 100;
	private Sprite[] minimapIcons = new Sprite[2];
	private String macAddress;

	public static void dumpModels() {
		int id = 6610;
		NpcDefinition npc = NpcDefinition.get(id);
		System.out.println(npc.name);
		if (npc.models != null) {
			for (int modelid = 0; modelid < npc.models.length; modelid++) {
				System.out.println(npc.models[modelid]);
			}
		}
	}

	static String intToKOrMilLongName(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3)
			s = s.substring(0, k) + "," + s.substring(k);
		if (s.length() > 8)
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
		else if (s.length() > 4)
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		return " " + s;
	}

	static String intToKOrMil(int j) {
		if (j < 0x186a0)
			return String.valueOf(j);
		if (j < 0x989680)
			return j / 1000 + "K";
		else
			return j / 0xf4240 + "M";
	}


	public static int spellID = 0;

	public static void setTab(int id) {
		needDrawTabArea = true;
		tabID = id;
		tabAreaAltered = true;
	}

	public void setSidebarInterface(int sidebarID, int interfaceID) {
		interfaceManager.setSidebarInterface(sidebarID, interfaceID);
	}

	private boolean menuHasAddFriend(int j) {
		if (j < 0)
			return false;
		int k = menuOpcodes[j];
		if (k >= 2000)
			k -= 2000;
		return k == 337;
	}

	Sprite channelButtons;


	ItemSearch grandExchangeItemSearch = null;

	public static int grandExchangeSearchScrollPostion;

	private void handleGEItemSearchClick(int itemId) {
		// Send the selected item ID to the server for GE buy offer
		// Packet 124 (SelectItemOnInterface): interfaceId, slot, itemId, amount
		stream.createFrame(124);
		stream.writeDWord(35603);                    // interfaceId (GE search button)
		stream.writeDWord(variousSettings[1075]);   // slot (GE slot from varp 1075)
		stream.writeDWord(itemId);                  // itemId
		stream.writeDWord(1);                       // amount (initial quantity)

		// Close the search dialog
		inputDialogState = 0;
		inputTaken = true;
		//amountOrNameInput = "";
		grandExchangeSearchScrollPostion = 0;
	}

	/**
	 * Process GE search item clicks during input processing phase.
	 * Must be called before clickMode3 is reset.
	 */
	private void processGESearchClick() {
		if (inputDialogState != 3 || grandExchangeItemSearch == null) {
			return;
		}
		if (clickMode3 != 1) {
			return;
		}
		if (amountOrNameInput == null || amountOrNameInput.isEmpty()) {
			return;
		}

		final int yOffset = (!isResized() ? 338 : canvasHeight - 165);
		final int xPosition = 15;
		final int yPosition = 32 + yOffset - grandExchangeSearchScrollPostion;

		int itemAmount = grandExchangeItemSearch.getItemSearchResultAmount();
		int[] itemResults = grandExchangeItemSearch.getItemSearchResults();

		int rowCountX = 0;
		int rowCountY = 0;

		for (int itemIdx = 0; itemIdx < itemAmount; itemIdx++) {
			if (itemResults[itemIdx] != -1) {
				final int startX = xPosition + rowCountX * 160;
				final int startY = yPosition + rowCountY * 35;

				// Check if click is within this item's bounds
				if (MouseHandler.saveClickX >= startX && MouseHandler.saveClickX <= startX + 160) {
					if (MouseHandler.saveClickY >= startY && MouseHandler.saveClickY <= startY + 35) {
						// Verify click is within visible area
						if (MouseHandler.saveClickY >= 29 + yOffset && MouseHandler.saveClickY <= 134 + yOffset) {
							handleGEItemSearchClick(itemResults[itemIdx]);
							clickMode3 = 0; // Consume the click
							return;
						}
					}
				}
			}

			rowCountX++;
			if (rowCountX > 2) {
				rowCountY++;
				rowCountX = 0;
			}
		}
	}

	void drawChatArea() {
		if (!isResized())
			hideChatArea = false;

		final int yOffset = (!isResized() ? 338 : canvasHeight - 165);
		final int yOffset2 = !isResized() ? 338 : 0;

		if (hideChatArea)
			gameRenderer.drawChannelButtons();

		if (!hideChatArea) {
			chatArea.drawSprite(0, yOffset);
			gameRenderer.drawChannelButtons();

			TextDrawingArea textDrawingArea = aTextDrawingArea_1271;
			if (MouseHandler.saveClickX >= 0 && MouseHandler.saveClickX <= 518) {
				if (MouseHandler.saveClickY >= (!isResized() ? 335 : canvasHeight - 164)
						&& MouseHandler.saveClickY <= (!isResized() ? 484 : canvasHeight - 30)) {
					if (this.isFieldInFocus()) {
						Client.inputString = "";
						this.resetInputFieldFocus();
					}
				}
			}
			if (messagePromptRaised) {
				newBoldFont.drawCenteredString(aString1121, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(promptInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 1) {
				newBoldFont.drawCenteredString(enterInputInChatString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 2 || inputDialogState == 10) {
				newBoldFont.drawCenteredString(enterInputInChatString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 7) {
				newBoldFont.drawCenteredString("Enter the price for the item:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 8) {
				newBoldFont.drawCenteredString("Amount you want to sell:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(amountOrNameInput + "*", 259, 80 + yOffset, 128, -1);
			} else if (Bank.isSearchingBank()) {
				newBoldFont.drawCenteredString("Enter an item to search for:", 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString(Bank.searchingBankString + "*", 259, 80 + yOffset, 128, -1);
			} else if (inputDialogState == 3) {
				Rasterizer2D.fillPixels(8, 505, 108, 0x463214, 28 + yOffset);
				Rasterizer2D.drawAlphaBox(8, 28 + yOffset, 505, 108, 0x746346, 75);

				newBoldFont.drawCenteredString("@bla@What would you like to buy? @blu@" + amountOrNameInput + "*", 259,
						20 + yOffset, 128, -1);
				if (!Objects.equals(amountOrNameInput, "")) {
					grandExchangeItemSearch = new ItemSearch(amountOrNameInput, 100, true);

					final int xPosition = 15;
					final int yPosition = 32 + yOffset - grandExchangeSearchScrollPostion;
					int rowCountX = 0;
					int rowCountY = 0;

					int itemAmount = grandExchangeItemSearch.getItemSearchResultAmount();

					if (amountOrNameInput.isEmpty()) {
						newRegularFont.drawCenteredString("Start typing the name of an item to search for it.", 259, 70 + yOffset, 0, -1);
					} else if (itemAmount == 0) {
						newRegularFont.drawCenteredString("No matching items found!", 259, 70 + yOffset, 0, -1);
					} else {
						Rasterizer2D.setDrawingAreaOSRS(134 + yOffset, 8, 497, 29 + yOffset);
						for (int itemId = 0; itemId < itemAmount; itemId++) {
							int[] itemResults = grandExchangeItemSearch.getItemSearchResults();

							if (itemResults[itemId] != -1) {
								final int startX = xPosition + rowCountX * 160;
								final int startY = yPosition + rowCountY * 35;
								Sprite itemSprite = ItemDefinition.getSprite(itemResults[itemId], 1, 0);
								if (itemSprite != null)
									itemSprite.drawSprite(startX, startY);

								ItemDefinition itemDef = ItemDefinition.lookup(itemResults[itemId]);

								// Wrap item name to multiple lines if too long (width 145 to fit more text)
								String[] wrappedLines = newRegularFont.wrap(itemDef.name, 145);
								for (int lineIdx = 0; lineIdx < wrappedLines.length && lineIdx < 2; lineIdx++) {
									newRegularFont.drawBasicString(wrappedLines[lineIdx], startX + 40, startY + 14 + (lineIdx * 12), 0, -1);
								}

								// Hover effect - use current mouse position
								if (MouseHandler.mouseX >= startX && MouseHandler.mouseX <= startX + 160) {
									if (MouseHandler.mouseY >= startY && MouseHandler.mouseY <= startY + 35) {
										Rasterizer2D.drawAlphaBox(startX, startY, 160, 35, 0xFFFFFF, 120);
									}
								}
								// Click detection - use saved click position
								if (clickMode3 == 1) {
									if (MouseHandler.saveClickX >= startX && MouseHandler.saveClickX <= startX + 160) {
										if (MouseHandler.saveClickY >= startY && MouseHandler.saveClickY <= startY + 35) {
											handleGEItemSearchClick(itemDef.id);
										}
									}
								}
							}

							rowCountX++;

							if (rowCountX > 2) {
								rowCountY++;
								rowCountX = 0;
							}
						}
                        //Rasterizer2D.defaultDrawingAreaSize();
					}

					int maxScrollPosition = itemAmount / 3 * 35;
					if (itemAmount > 9)
						drawScrollbar(106, grandExchangeSearchScrollPostion > maxScrollPosition ? 0
								: grandExchangeSearchScrollPostion, 29 + yOffset, 496, itemAmount / 3 * 35);

				}
			} else if (clickToContinueString != null) {
				newBoldFont.drawCenteredString(clickToContinueString, 259, 60 + yOffset, 0, -1);
				newBoldFont.drawCenteredString("Click to continue", 259, 80 + yOffset, 128, -1);
			} else if (backDialogID != -1) {
				drawInterface(0, 20, RSInterface.interfaceCache[backDialogID], 20 + yOffset);
			} else if (dialogID != -1) {
				drawInterface(0, 20, RSInterface.interfaceCache[dialogID], 20 + yOffset);
			} else {
				int j77 = -3;
				int j = 0;
				Rasterizer2D.setDrawingArea(0, 7 + yOffset, 497, 122 + yOffset);
				for (int k = 0; k < 500; k++)
					if (chatMessages[k] != null) {
						// System.out.println(chatMessages[k]);
						int chatType = chatTypes[k];
						int yPos = (70 - j77 * 14) + anInt1089 + 5;
						String s1 = chatNames[k];
						//byte byte0 = 0;

						List<Integer> crowns = new ArrayList<>();

						while (s1.startsWith("@cr")) {
							String s2 = s1.substring(3, s1.length());
							int index = s2.indexOf("@");
							if (index != -1) {
								s2 = s2.substring(0, index);
								crowns.add(Integer.parseInt(s2));
								s1 = s1.substring(4 + s2.length());
							}
						}
						if (chatType == 0) {
							if (chatTypeView == 5 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									newRegularFont.drawBasicString(chatMessages[k], 10, yPos - 1 + yOffset, 0, -1);
								}
								j++;
								j77++;
							}
						}
						if ((chatType == 1 || chatType == 2) && (chatType == 1 || publicChatMode == 0
								|| publicChatMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 1 || chatTypeView == 0) {
								int xPos = 11;
								if (!crowns.isEmpty()) {
									for (int crown : crowns) {
										for (int right = 0; right < modIconss.length; right++) {
											if (right == (crown - 1) && modIconss[right] != null) {
												xPos += modIconss[right].myWidth;
												xPos += 2;
												break;
											}
										}
									}
								}
								int nameStartX = xPos - 1;
								int messageX = xPos + newRegularFont.getTextWidth(s1) + 5 - 1;
								int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
								int availableWidth = Math.max(20, maxLineWidth - messageX);
								String[] wrappedLines = newRegularFont.wrap(chatMessages[k], availableWidth);
								int linesDrawn = Math.max(1, wrappedLines.length);
								for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
									int lineOffset = linesDrawn - 1 - lineIndex;
									int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
									if (lineYPos > 0 && lineYPos < 210) {
										if (lineIndex == 0) {
											int crownX = 11;
											if (!crowns.isEmpty()) {
												for (int crown : crowns) {
													for (int right = 0; right < modIconss.length; right++) {
														if (right == (crown - 1) && modIconss[right] != null) {
															modIconss[right].drawAdvancedSprite(crownX - 1,
																	lineYPos + yOffset - modIconss[right].myHeight);
															crownX += modIconss[right].myWidth;
															crownX += 2;
															break;
														}
													}
												}
											}
											newRegularFont.drawBasicString(s1 + ":", crownX - 1, lineYPos - 1 + yOffset, 0, -1);
											newRegularFont.drawBasicString(wrappedLines[0], messageX, lineYPos - 1 + yOffset, 255, -1, false);
										} else {
											newRegularFont.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos - 1 + yOffset, 255, -1, false);
										}
									}
								}
								j += linesDrawn;
								j77 += linesDrawn;
							}
						}

						if (chatType == 99) {
							if (yPos > 0 && yPos < 210) {
								lastViewedDropTable = chatMessages[k];
								newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 10, yPos - 1 + yOffset, 0x7e3200, -1, false);
							}
							j++;
							j77++;
						}


						if ((chatType == 3 || chatType == 7) && (!splitPrivateChat || chatTypeView == 2)
								&& (chatType == 7 || privateChatMode == 0
								|| privateChatMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								int crownWidth = 0;
								if (!crowns.isEmpty()) {
									for (int crown : crowns) {
										for (int right = 0; right < modIconss.length; right++) {
											if (right == (crown - 1) && modIconss[right] != null) {
												crownWidth += modIconss[right].myWidth;
												break;
											}
										}
									}
								}
								int nameStartX = 11 + textDrawingArea.getTextWidth("From ") + crownWidth;
								int messageX = nameStartX + newRegularFont.getTextWidth(s1) + 8;
								int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
								int availableWidth = Math.max(20, maxLineWidth - messageX);
								String[] wrappedLines = newRegularFont.wrap(chatMessages[k], availableWidth);
								int linesDrawn = Math.max(1, wrappedLines.length);
								for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
									int lineOffset = linesDrawn - 1 - lineIndex;
									int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
									if (lineYPos > 0 && lineYPos < 210) {
										if (lineIndex == 0) {
											int k1 = 11;
											newRegularFont.drawBasicString("From", k1, lineYPos - 1 + yOffset, 0, -1);
											k1 += textDrawingArea.getTextWidth("From ");
											if (!crowns.isEmpty()) {
												for (int crown : crowns) {
													for (int right = 0; right < modIconss.length; right++) {
														if (right == (crown - 1) && modIconss[right] != null) {
															modIconss[right].drawAdvancedSprite(k1,
																	lineYPos + yOffset - modIconss[right].myHeight);
															k1 += modIconss[right].myWidth;
															break;
														}
													}
												}
											}
											newRegularFont.drawBasicString(s1 + ":", k1, lineYPos - 1 + yOffset, 0, -1);
											newRegularFont.drawBasicString(wrappedLines[0], messageX, lineYPos - 1 + yOffset, 0x800000, -1, false);
										} else {
											newRegularFont.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos - 1 + yOffset, 0x800000, -1, false);
										}
									}
								}
								j += linesDrawn;
								j77 += linesDrawn;
							}
						}
						if (chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 11, yPos - 1 + yOffset,0x800080, -1, false);
								j++;
								j77++;
							}
						}
						if (chatType == 31 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							int crownId = 29;
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210) {
									int xPos = 11;
									modIconss[crownId].drawAdvancedSprite(xPos - 1, yPos + yOffset - modIconss[crownId].myHeight);
									xPos += modIconss[crownId].myWidth;
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k], xPos - 1, yPos - 1 + yOffset, 0x1950ce, -1, false);
								}
								j++;
								j77++;
							}
						}
						if (chatType == 5 && !splitPrivateChat && privateChatMode < 2) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(chatMessages[k], 10, yPos - 1 + yOffset, 0x800000,-1, false);
								j++;
								j77++;
							}
						}
						if (chatType == 6 && (!splitPrivateChat || chatTypeView == 2) && privateChatMode < 2) {
							if (chatTypeView == 2 || chatTypeView == 0) {
								int nameStartX = 10 + textDrawingArea.getTextWidth("To ");
								int messageX = nameStartX + textDrawingArea.getTextWidth(s1) + 5;
								int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
								int availableWidth = Math.max(20, maxLineWidth - messageX);
								String[] wrappedLines = newRegularFont.wrap(chatMessages[k], availableWidth);
								int linesDrawn = Math.max(1, wrappedLines.length);
								for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
									int lineOffset = linesDrawn - 1 - lineIndex;
									int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
									if (lineYPos > 0 && lineYPos < 210) {
										if (lineIndex == 0) {
											newRegularFont.drawBasicString("To " + s1 + ":", 10, lineYPos - 1 + yOffset, 0, -1);
											newRegularFont.drawBasicString(wrappedLines[0], messageX, lineYPos - 1 + yOffset, 0x800000, -1, false);
										} else {
											newRegularFont.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos - 1 + yOffset, 0x800000, -1, false);
										}
									}
								}
								j += linesDrawn;
								j77 += linesDrawn;
							}
						}

						// Clan chat
						if (chatType == 11 && (clanChatMode == 0)) {
							if (chatTypeView == 11 || chatTypeView == 0) {
								try {
									String lastChatNameEnd = "@dre@";
									String clanChatMessage = chatMessages[k];
									int splitIndex = clanChatMessage.indexOf(lastChatNameEnd);
									if (splitIndex == -1) {
										if (yPos > 0 && yPos < 210) {
											newRegularFont.drawBasicString(clanChatMessage, 8, yPos - 1 + yOffset, 0x7e3200, -1, false);
										}
										j++;
										j77++;
									} else {
										String chatMessage = clanChatMessage.substring(splitIndex + lastChatNameEnd.length());
										String usernameAndFormatting = clanChatMessage.substring(0, splitIndex + lastChatNameEnd.length());
										int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 8 - 5;
										int prefixWidth = newRegularFont.getTextWidth(usernameAndFormatting);
										int nameStartX = 8;
										int nameStartIndex = usernameAndFormatting.lastIndexOf("] ");
										if (nameStartIndex != -1) {
											nameStartX = 8 + newRegularFont.getTextWidth(usernameAndFormatting.substring(0, nameStartIndex + 2));
										}
										int availableWidth = Math.max(20, maxLineWidth - prefixWidth);
										String[] wrappedLines = newRegularFont.wrap(chatMessage, availableWidth);
										int linesDrawn = Math.max(1, wrappedLines.length);
										for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
											int lineOffset = linesDrawn - 1 - lineIndex;
											int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
											if (lineYPos > 0 && lineYPos < 210) {
												if (lineIndex == 0) {
													newRegularFont.drawBasicString(usernameAndFormatting, 8, lineYPos - 1 + yOffset, 0x7e3200, -1, true);
													newRegularFont.drawBasicString(wrappedLines[0], prefixWidth + 8, lineYPos - 1 + yOffset, 0x7e3200, -1, false);
												} else {
													newRegularFont.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos - 1 + yOffset, 0x7e3200, -1, false);
												}
											}
										}
										j += linesDrawn;
										j77 += linesDrawn;
									}
								} catch (Exception e) {
									e.printStackTrace();
									j++;
									j77++;
								}
							}
						}
						if (chatType == 13 && chatTypeView == 12) {
							try {
								String lastChatNameEnd = "@dre@";
								String groupChatMessage = s1 + " " + chatMessages[k];
								int splitIndex = groupChatMessage.indexOf(lastChatNameEnd);
								int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 8 - 5;
								if (splitIndex == -1) {
									String[] wrappedLines = newRegularFont.wrap(groupChatMessage, maxLineWidth);
									int linesDrawn = Math.max(1, wrappedLines.length);
									for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
										int lineOffset = linesDrawn - 1 - lineIndex;
										int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
										if (lineYPos > 0 && lineYPos < 210) {
											newRegularFont.drawBasicString(wrappedLines[lineIndex], 8, lineYPos - 1 + yOffset, 0x7e3200, -1, false);
										}
									}
									j += linesDrawn;
									j77 += linesDrawn;
								} else {
									String chatMessage = groupChatMessage.substring(splitIndex + lastChatNameEnd.length());
									String usernameAndFormatting = groupChatMessage.substring(0, splitIndex + lastChatNameEnd.length());
									int prefixWidth = newRegularFont.getTextWidth(usernameAndFormatting);
									int nameStartX = 8;
									int nameStartIndex = usernameAndFormatting.lastIndexOf("] ");
									if (nameStartIndex != -1) {
										nameStartX = 8 + newRegularFont.getTextWidth(usernameAndFormatting.substring(0, nameStartIndex + 2));
									}
									int availableWidth = Math.max(20, maxLineWidth - prefixWidth);
									String[] wrappedLines = newRegularFont.wrap(chatMessage, availableWidth);
									int linesDrawn = Math.max(1, wrappedLines.length);
									for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
										int lineOffset = linesDrawn - 1 - lineIndex;
										int lineYPos = (70 - (j77 + lineOffset) * 14) + anInt1089 + 5;
										if (lineYPos > 0 && lineYPos < 210) {
											if (lineIndex == 0) {
												newRegularFont.drawBasicString(usernameAndFormatting, 8, lineYPos - 1 + yOffset, 0x7e3200, -1, true);
												newRegularFont.drawBasicString(wrappedLines[0], prefixWidth + 8, lineYPos - 1 + yOffset, 0x7e3200, -1, false);
											} else {
												newRegularFont.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos - 1 + yOffset, 0x7e3200, -1, false);
											}
										}
									}
									j += linesDrawn;
									j77 += linesDrawn;
								}
							} catch (Exception e) {
								e.printStackTrace();
								j++;
								j77++;
							}
						}
						if (chatType == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
							if (chatTypeView == 3 || chatTypeView == 0) {
								if (yPos > 0 && yPos < 210)
									newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 10, yPos - 1 + yOffset,0x7e3200, -1, false);
								j++;
								j77++;
							}
						}
						if (chatType == 16) {
							int j2 = 40 + 11;
							String clanname = clanList[k];
							int clanNameWidth = textDrawingArea.getTextWidth(clanname);
							if (chatTypeView == 11 || chatTypeView == 0) {
								newRegularFont.drawBasicString("[", 19, yPos - 1 + yOffset, 0, -1);
								newRegularFont.drawBasicString("]", clanNameWidth + 16 + 10, yPos - 1 + yOffset, 0, -1);
								newRegularFont.drawBasicString("" + capitalize(clanname) + "", 25, yPos - 1 + yOffset,
										255, -1);
								newRegularFont.drawBasicString(chatNames[k] + ":", j2 - 17, yPos);
								j2 += newRegularFont.getTextWidth(chatNames[k]) + 7;
								newRegularFont.drawBasicString(capitalize(chatMessages[k]), j2 - 16, yPos - 1 + yOffset,0x800000, -1);

								j++;
								j77++;
							}
						}
					}
				Rasterizer2D.set_default_size();
				chatAreaScrollLength = j * 14 + 7 + 5;
				if (chatAreaScrollLength < 111)
					chatAreaScrollLength = 111;
				drawScrollbar(114, chatAreaScrollLength - anInt1089 - 113, 6 + yOffset, 496, chatAreaScrollLength);
				String fixedString;
				if (localPlayer != null && localPlayer.displayName != null) {
					if (localPlayer.title.length() > 0) {
						fixedString = "<col=" + localPlayer.titleColor + ">" + localPlayer.title + "</col>" + " "
								+ localPlayer.displayName;
					} else {
						fixedString = localPlayer.displayName;
					}
				} else {
					fixedString = StringUtils.fixName(capitalize(myUsername));
				}
				String s;
				if (localPlayer != null && localPlayer.displayName != null && localPlayer.title.length() > 1)
					s = localPlayer.title + " " + localPlayer.displayName;
				else
					s = StringUtils.fixName(capitalize(myUsername));
				Rasterizer2D.setDrawingArea(0, yOffset, 519, 142 + yOffset);
				int xOffset = 0;
				if (localPlayer.hasRightsOtherThan(PlayerRights.PLAYER)) {
					for (PlayerRights right : localPlayer.getDisplayedRights()) {
						if (right.hasCrown()) {
                            modIconss[right.spriteId()].drawSprite(9 + xOffset, 134 + yOffset - modIconss[right.spriteId()].myHeight);
							xOffset += modIconss[right.spriteId()].myWidth;
							xOffset += 2;
						}
					}

					newRegularFont.drawBasicString(fixedString, 10 + xOffset, 133 + yOffset, 0, -1);
				} else {
					newRegularFont.drawBasicString(fixedString, 10 + xOffset, 133 + yOffset, 0, -1);
				}

				xOffset += newRegularFont.getTextWidth(fixedString) + 10;
				newRegularFont.drawBasicString(": ", xOffset, 133 + yOffset, 0, -1);
				xOffset += textDrawingArea.getTextWidth(": ");

				if (!isFieldInFocus()) {
					newRegularFont.drawBasicString(inputString + "*",xOffset, 133 + yOffset, 255, -1, false);
				}
				Rasterizer2D.set_default_size();
				Rasterizer2D.method339(120 + yOffset, 0x807660, 506, 7);
			}

		}

	}

	private String clanUsername;
	private String clanMessage;
	private String clanTitle;
	private String[] clanTitles;
	private EnumSet channelRights;

	public Socket openSocket(int port) throws IOException {
		return new Socket(InetAddress.getByName(server), port);
	}

	final void menu()
	{
		menuHandler.menu();
	}


	final void minimapHovers() {
		final boolean fixed = !isResized();
		final int orbXOffset = fixed ? 516 : canvasWidth - 217;
		prayHover = fixed
			? prayHover = MouseHandler.mouseX >= 517 && MouseHandler.mouseX <= 573
			&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 81 : 76)
			&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 109 : 107)
			: MouseHandler.mouseX >= canvasWidth - 210 && MouseHandler.mouseX <= canvasWidth - 157
			&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 90 : 55)
			&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 119 : 104);
		runHover = fixed
			? runHover = MouseHandler.mouseX >= (Configuration.osbuddyGameframe ? 530 : 522)
			&& MouseHandler.mouseX <= (Configuration.osbuddyGameframe ? 582 : 594)
			&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 114 : 109)
			&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 142 : 142)
			: MouseHandler.mouseX >= canvasWidth - (Configuration.osbuddyGameframe ? 196 : 186)
			&& MouseHandler.mouseX <= canvasWidth - (Configuration.osbuddyGameframe ? 142 : 132)
			&& MouseHandler.mouseY >= (Configuration.osbuddyGameframe ? 123 : 132)
			&& MouseHandler.mouseY < (Configuration.osbuddyGameframe ? 150 : 159);
		specialHover = MouseHandler.mouseX >= orbXOffset + 34 && MouseHandler.mouseX <= orbXOffset + 72
			&& MouseHandler.mouseY >= (fixed ? 134 : 139) && MouseHandler.mouseY <= (fixed ? 166 : 172);
		if (specialHover) {
			runHover = false;
		}
		counterHover = fixed ? MouseHandler.mouseX >= 522 && MouseHandler.mouseX <= 544 && MouseHandler.mouseY >= 20 && MouseHandler.mouseY <= 47
			: MouseHandler.mouseX >= canvasWidth - 205 && MouseHandler.mouseX <= canvasWidth - 184 && MouseHandler.mouseY >= 27
			&& MouseHandler.mouseY <= 44;
		worldHover = fixed ? MouseHandler.mouseX >= 715 && MouseHandler.mouseX <= 740 && MouseHandler.mouseY >= 122 && MouseHandler.mouseY <= 135 // 132 160
			: MouseHandler.mouseX >= canvasWidth - 34 && MouseHandler.mouseX <= canvasWidth - 5 && MouseHandler.mouseY >= 143
			&& MouseHandler.mouseY <= 172;

		wikiHover = fixed ? MouseHandler.mouseX >= 704 && MouseHandler.mouseX <= 744 && MouseHandler.mouseY >= 150 && MouseHandler.mouseY <= 161
			: MouseHandler.mouseX >= canvasWidth - 34 && MouseHandler.mouseX <= canvasWidth - 5 && MouseHandler.mouseY >= 160 && MouseHandler.mouseY <= 173;

		teleOrbHover = fixed ? MouseHandler.mouseX >= 709 && MouseHandler.mouseX <= 734 && MouseHandler.mouseY >= 32 && MouseHandler.mouseY <= 57
			: MouseHandler.mouseX >= canvasWidth - 94 && MouseHandler.mouseX <= canvasWidth - 80 && MouseHandler.mouseY >= 160 && MouseHandler.mouseY <= 173;

		pouchHover = fixed ? MouseHandler.mouseX >= 678 && MouseHandler.mouseX <= 739 && MouseHandler.mouseY >= 129 && MouseHandler.mouseY <= 157 :
			MouseHandler.mouseX >= canvasWidth - 65 && MouseHandler.mouseX <= canvasWidth && MouseHandler.mouseY >= 154 && MouseHandler.mouseY <= 185;
	}

	boolean prayHover;
	boolean prayClicked;

	public static boolean counterOn;

	boolean counterHover;

	boolean worldHover;

	private boolean donatorHover;

	private boolean newHover;

	boolean showClanOptions;

	public static int totalRead = 0;

	public static String getFileNameWithoutExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
			return tmpFile.getName().substring(0, whereDot);
		}
		return "";
	}

	public String indexLocation(int cacheIndex, int index) {
		return Signlink.getCacheDirectory() + "index" + cacheIndex + "/" + (index != -1 ? index + ".gz" : "");
	}

	public byte[] getModel(int Index) {
		try {
			File Model = new File(Signlink.getCacheDirectory() + "./pModels/" + Index + ".gz");
			byte[] aByte = new byte[(int) Model.length()];
			FileInputStream fis = new FileInputStream(Model);
			fis.read(aByte);
			System.out.println("" + Index + " aByte = [" + aByte + "]!");
			fis.close();
			return aByte;
		} catch (Exception e) {
			return null;
		}
	}


	public MapRegion currentMapRegion;
	private StringBuilder objectMaps = new StringBuilder();
	private StringBuilder floorMaps = new StringBuilder();
	public final void loadRegion() {
		if (!floorMaps.equals("") || !objectMaps.equals("")) {
			floorMaps = new StringBuilder();
			objectMaps = new StringBuilder();
		}
		Client.mapsLoaded = 0;
		boolean var1 = true;

		int var2;

        for (var2 = 0; var2 < regionLandArchives.length; var2++) {
			floorMaps.append("  ").append(regionLandIds[var2]);
			objectMaps.append("  ").append(regionLocIds[var2]);
			if (regionLandArchives[var2] == null && regionLandIds[var2] != -1) {
				regionLandArchives[var2] = Js5List.maps.getFile(regionLandIds[var2], 0);
				if (regionLandArchives[var2] == null) {
					var1 = false;
					++Client.mapsLoaded;
				}
			}
			if (regionMapArchives[var2] == null && regionLocIds[var2] != -1) {
				try {
					regionMapArchives[var2] = Js5List.maps.getFile(regionLocIds[var2], 0);
					if (regionMapArchives[var2] == null) {
						var1 = false;
						++Client.mapsLoaded;
					}
				} catch (Throwable e) {
					log.info("Missing xteas for region: {}", regions[var2]);
				}
			}
		}

		if (!var1) {
			Client.loadingType = 1;
		} else {
			Client.objectsLoaded = 0;
            var1 = true;

            int xChunk;
            int yChunk;
            for (var2 = 0; var2 < regionLandArchives.length; ++var2) {
                    byte[] var3 = regionMapArchives[var2];
                    if (var3 != null) {
                        xChunk = 64 * (regions[var2] >> 8) - baseX;
                        yChunk = 64 * (regions[var2] & 255) - baseY;
                        if (isInInstance) {
                            xChunk = 10;
                            yChunk = 10;
                        }
                        var1 &= MapRegion.method787(var3, xChunk, yChunk);
                    }
            }

			if (!var1) {
				Client.loadingType = 2;
			} else {
				if (0 != Client.loadingType) {
					gameRenderer.drawLoadingMessage("Loading - please wait." + "<br>" + " (" + 100 + "%" + ")");
				}

				setGameState(GameState.LOADING);
				StaticSound.playPcmPlayers();
				lastKnownPlane = -1;
				incompleteAnimables.clear();

				projectiles.clear();
				scene.clear();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++)
							groundItems[l2][i3][k3] = null;

					}

				}
				unlinkMRUNodes();
				System.gc();

				for (var2 = 0; var2 < 4; ++var2) {
					collisionMaps[var2].init();
				}

				int zChunk;
				for (var2 = 0; var2 < 4; ++var2) {
					for (zChunk = 0; zChunk < 104; ++zChunk) {
						for (xChunk = 0; xChunk < 104; ++xChunk) {
							tileFlags[var2][zChunk][xChunk] = 0;
						}
					}
				}
				StaticSound.playPcmPlayers();
				currentMapRegion = new MapRegion(tileFlags, tileHeights);

				var2 = regionLandArchives.length;
				ObjectSound.clearObjectSounds();
				stream.createFrame(0);
				int tileBits;
				if (!isInInstance) {
					byte[] var6;
					for (zChunk = 0; zChunk < var2; ++zChunk) {
                            xChunk = 64 * (regions[zChunk] >> 8) - baseX;
                            yChunk = 64 * (regions[zChunk] & 255) - baseY;
                            var6 = regionLandArchives[zChunk];
                            if (null != var6) {
                                StaticSound.playPcmPlayers();
                                currentMapRegion.decode_map_terrain(var6, xChunk, yChunk, currentRegionX * 8 - 48, currentRegionY * 8 - 48, collisionMaps);
                            }
					}

					for (zChunk = 0; zChunk < var2; ++zChunk) {
						xChunk = (regions[zChunk] >> 8) * 64 - baseX;
						yChunk = (regions[zChunk] & 255) * 64 - baseY;
						var6 = regionLandArchives[zChunk];
						if (null == var6 && currentRegionY < 800) {
							StaticSound.playPcmPlayers();
							currentMapRegion.method736(xChunk, yChunk, 64, 64);
						}
					}

					stream.createFrame(0);

					for (zChunk = 0; zChunk < var2; ++zChunk) {
						byte[] var15 = regionMapArchives[zChunk];
						if (var15 != null) {
							yChunk = 64 * (regions[zChunk] >> 8) - baseX;
							tileBits = 64 * (regions[zChunk] & 255) - baseY;
							StaticSound.playPcmPlayers();
							currentMapRegion.decode_map_locations(yChunk, collisionMaps, tileBits, scene, var15);
						}
					}
				}

				int level;
				int rotation;
				int worldX;
				if (isInInstance) {
					int worldY;
					int region;
					int var12;
					for (zChunk = 0; zChunk < 4; ++zChunk) {
						StaticSound.playPcmPlayers();
						for (xChunk = 0; xChunk < 13; ++xChunk) {
							for (yChunk = 0; yChunk < 13; ++yChunk) {
								boolean var18 = false;
								level = constructRegionData[zChunk][xChunk][yChunk];
								if (level != -1) {
									rotation = level >> 24 & 3;
									worldX = level >> 1 & 3;
									worldY = level >> 14 & 1023;
									region = level >> 3 & 2047;
									var12 = (worldY / 8 << 8) + region / 8;

									for (int var13 = 0; var13 < regions.length; ++var13) {
										if (regions[var13] == var12 && null != regionLandArchives[var13]) {
											currentMapRegion.method3968(
													regionLandArchives[var13],
													zChunk,
													8 * xChunk,
													8 * yChunk,
													rotation, 8 * (worldY & 7), 8 * (region & 7),
													worldX,
													collisionMaps);
											var18 = true;
											break;
										}
									}
								}

								if (!var18) {
									MapRegion.method1307(zChunk, xChunk * 8, 8 * yChunk);
								}
							}
						}
					}

					for (zChunk = 0; zChunk < 13; ++zChunk) {
						for (xChunk = 0; xChunk < 13; ++xChunk) {
							yChunk = constructRegionData[0][zChunk][xChunk];
							if (yChunk == -1) {
								currentMapRegion.method736(zChunk * 8, 8 * xChunk, 8, 8);
							}
						}
					}

					stream.createFrame(0);

					for (zChunk = 0; zChunk < 4; ++zChunk) {
						StaticSound.playPcmPlayers();
						for (xChunk = 0; xChunk < 13; ++xChunk) {
							for (yChunk = 0; yChunk < 13; ++yChunk) {
								tileBits = constructRegionData[zChunk][xChunk][yChunk];
								if (tileBits != -1) {
									level = tileBits >> 24 & 3;
									rotation = tileBits >> 1 & 3;
									worldX = tileBits >> 14 & 1023;
									worldY = tileBits >> 3 & 2047;
									region = worldY / 8 + (worldX / 8 << 8);

									for (var12 = 0; var12 < regions.length; ++var12) {
										if (regions[var12] == region && null != regionMapArchives[var12]) {
											currentMapRegion.method1668(
													regionMapArchives[var12],
													zChunk,
													xChunk * 8, 8 * yChunk,
													level,
													(worldX & 7) * 8, (worldY & 7) * 8,
													rotation,
													scene, collisionMaps);
											break;
										}
									}
								}
							}
						}
					}
				}

				stream.createFrame(0);
				StaticSound.playPcmPlayers();
				currentMapRegion.method6042(scene, collisionMaps);
				rasterProvider.init();
				stream.createFrame(0);

				zChunk = MapRegion.min_plane;
				if (zChunk > plane) {
					zChunk = plane;
				}

				if (zChunk < plane - 1) {
					zChunk = plane - 1;
				}

				if (low_detail) {
					scene.init(MapRegion.min_plane);
				} else {
					scene.init(0);
				}

				for (xChunk = 0; xChunk < 104; ++xChunk) {
					for (yChunk = 0; yChunk < 104; ++yChunk) {
						this.updateItemPile(xChunk, yChunk);
					}
				}

				StaticSound.playPcmPlayers();
				method63();
				ObjectDefinition.baseModels.clear();
				stream.createFrame(210);
				stream.writeDWord(0x3f008edd);
				System.gc();


				if (!isInInstance) {
					xChunk = (currentRegionX - 6) / 8;
					yChunk = (currentRegionX + 6) / 8;
					tileBits = (currentRegionY - 6) / 8;
					level = (currentRegionY + 6) / 8;

					for (rotation = xChunk - 1; rotation <= 1 + yChunk; ++rotation) {
						for (worldX = tileBits - 1; worldX <= level + 1; ++worldX) {
							if (rotation < xChunk || rotation > yChunk || worldX < tileBits || worldX > level) {
								Js5List.maps.loadRegionFromName("m" + rotation + "_" + worldX);
								Js5List.maps.loadRegionFromName("l" + rotation + "_" + worldX);
							}
						}
					}
				}

				if (plane != lastKnownPlane) {
					lastKnownPlane = plane;
					renderMapScene(plane);
                    stream.createFrame(121);
				}
				setGameState(GameState.LOGGED_IN);
			}
		}
	}

	public void unlinkMRUNodes() {
		ObjectDefinition.baseModels.clear();
		NpcDefinition.modelCache.clear();
		ItemDefinition.models.clear();
		ItemDefinition.sprites.clear();
		Player.mruNodes.clear();
	}

	public void renderMapScene(int i) {
		int ai[] = minimapImage.myPixels;
		int j = ai.length;

		for (int k = 0; k < j; k++)
			ai[k] = 0;

		for (int l = 1; l < 103; l++) {
			int i1 = 24628 + (103 - l) * 512 * 4;
			for (int k1 = 1; k1 < 103; k1++) {
				if ((tileFlags[i][k1][l] & 0x18) == 0)
					scene.draw_minimap_tile(ai, i1, i, k1, l);
				if (i < 3 && (tileFlags[i + 1][k1][l] & 8) != 0)
					scene.draw_minimap_tile(ai, i1, i + 1, k1, l);
				i1 += 4;
			}

		}

		int wallColor = (238 + (int) (Math.random() * 20.0D) - 10 << 16) + (238 + (int) (Math.random() * 20.0D) - 10 << 8) + (238 + (int) (Math.random() * 20.0D) - 10);
		int interactable = 238 + (int) (Math.random() * 20.0D) - 10 << 16;
		minimapImage.init();

		for (int y = 1; y < 103; ++y) {
			for (int x = 1; x < 103; ++x) {
				if ((tileFlags[i][x][y] & 0x18) == 0) {
					drawMapScenes(i, x, y, wallColor, interactable);
				}

				if (i < 3 && (tileFlags[i + 1][x][y] & 8) != 0) {
					drawMapScenes(i + 1, x, y, wallColor, interactable);
				}
			}
		}

		rasterProvider.init();
		mapIconAmount = 0;
		for (int k2 = 0; k2 < 104; k2++) {
			for (int l2 = 0; l2 < 104; l2++) {
				long id = scene.getFloorDecorationTag(plane, k2, l2);
				if (0L != id) {
					int objKey = ObjectKeyUtil.getObjectId(id);
					int function = ObjectDefinition.lookup(objKey).minimapFunction;
					if (function >= 0 && AreaDefinition.lookup(function).field1940) {
						minimapHint[mapIconAmount] = AreaDefinition.lookup(function).getIconSprite();
						minimapHintX[mapIconAmount] = k2;
						minimapHintY[mapIconAmount] = l2;
						mapIconAmount++;
					}
				}
			}

		}


	}

	public void updateItemPile(int plane, int x, int y) {
		NodeDeque var3 = groundItems[plane][x][y];
		if (var3 == null) {
			scene.removeGroundItemPile(plane, x, y);
		} else {
			long var4 = -99999999L;
			TileItem first = null;

			TileItem var7;
			for (var7 = (TileItem)var3.last(); var7 != null; var7 = (TileItem)var3.previous()) {
				ItemDefinition var8 = ItemDefinition.lookup(var7.id);
				long var9 = (long)var8.value;
				if (var8.stackable) {
					var9 *= var7.quantity < Integer.MAX_VALUE ? (long)(var7.quantity + 1) : (long)var7.quantity;
				}

				if (var9 > var4) {
					var4 = var9;
					first = var7;
				}
			}

			if (first == null) {
				scene.removeGroundItemPile(plane, x, y);
			} else {
				var3.addLast(first);
				TileItem second = null;
				TileItem third = null;

				for (var7 = (TileItem)var3.last(); var7 != null; var7 = (TileItem)var3.previous()) {
					if (first.id != var7.id) {
						if (second == null) {
							second = var7;
						}

						if (second.id != var7.id && third == null) {
							third = var7;
						}
					}
				}

				long key = ObjectKeyUtil.calculateTag(x, y, 3, false, 0);
				scene.add_ground_item(x, key, second, getCenterHeight(plane, y * 128 + 64, x * 128 + 64), third, first, plane, y);
			}
		}
	}
	void showPrioritizedNPCs() {
		addNpcsToScene(true);
	}


	void showOtherNpcs() {
		addNpcsToScene(false);
	}


	public final void addNpcsToScene(boolean arg0) {
		for (int idx = 0; idx < npcCount; ++idx) {
			Npc var3 = npcs[npcIndices[idx]];
			var3.setIndex(idx);
			if (null != var3 && var3.isVisible() && var3.desc.isVisible() == arg0) {
				int var4 = var3.x >> 7;
				int var5 = var3.y >> 7;
				if (var4 >= 0 && var4 < 104 && var5 >= 0 && var5 < 104) {
					if (1 == var3.desc.size && 64 == (var3.x & 127) && 64 == (var3.y & 127)) {
						if (viewportDrawCount == tileCycleMap[var4][var5]) {
							continue;
						}

						tileCycleMap[var4][var5] = viewportDrawCount;
					}

					long var6 = ObjectKeyUtil.calculateTag(0, 0, 1, !var3.desc.clickable, npcIndices[idx]);
					var3.lastUpdateTick = loopCycle;
					scene.add_entity(plane, var3.orientation, getCenterHeight(plane, var3.size * 64 - 64 + var3.y, var3.x + (var3.size * 64 - 64)), var6, var3.y, 60 + (var3.size * 64 - 64), var3.x, var3, var3.isWalking);
				}
			}
		}
	}

	public void loadError() {
		String s = "ondemand";// was a constant parameter
		System.out.println(s);
		do
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		while (true);
	}

	public int drawTabInterfaceHoverParent;
	public int drawTabInterfaceHover;
	public int drawTabInterfaceHoverLast;
	public long drawTabInterfaceHoverTimer;
	public RSInterface lastHovered;

	public void resetTabInterfaceHover() {
		interfaceManager.resetTabInterfaceHover();
	}

	public void buildInterfaceMenu(int xPosition, RSInterface widget, int mouseX, int yPosition, int mouseY, int j1) {
		interfaceManager.buildInterfaceMenu(xPosition, widget, mouseX, yPosition, mouseY, j1);
	}

	public void drawScrollbar(int height, int scrollPosition, int yPosition, int xPosition, int scrollMax) {
		interfaceManager.drawScrollbar(height, scrollPosition, yPosition, xPosition, scrollMax);
	}

	/**
	 * NPC Updating
	 * <p>
	 * There is a crash at random npcs so i did some sort of cheap fix to stop it
	 * from throwing runtime exception which will ultimately log a player out, even
	 * regardless of where and when they are in the game, causes to many issues.
	 *
	 * @param stream
	 * @param i
	 */
	public void updateNPCs(Buffer stream, int i) {
		anInt839 = 0;
		anInt893 = 0;
		method139(stream);
		method46(i, stream);
		updateNpcState(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (npcs[l] == null) {
				continue;
			}
			if (npcs[l].lastUpdateTick != loopCycle) {
				callbacks.post(new NpcDespawned(npcs[l]));
				npcs[l].desc = null;
				npcs[l] = null;
			}
		}

		// Cheaphax fix to it doesnt throw exception and boot player
		if (stream.currentPosition == -1 || stream.currentPosition != i) {
			System.out.println("[NPC] Size mismatch : returning");
			return;
		}

		if (stream.currentPosition != i) {
			Signlink.reporterror(
					myUsername + " size mismatch in getnpcpos - pos:" + stream.currentPosition + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < npcCount; i1++) {
			if (npcs[npcIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}
		}
		callbacks.updateNpcs();
	}

	int channelButtonHoverPosition;
	int channelButtonClickPosition;

	public void processChatModeClick() {
		if (MouseHandler.mouseY >= canvasHeight - 22 && MouseHandler.mouseY <= canvasHeight) {
			if (MouseHandler.mouseX >= 5 && MouseHandler.mouseX <= 61) {
				channelButtonHoverPosition = 0;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 71 && MouseHandler.mouseX <= 127) {
				channelButtonHoverPosition = 1;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 137 && MouseHandler.mouseX <= 193) {
				channelButtonHoverPosition = 2;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 203 && MouseHandler.mouseX <= 259) {
				channelButtonHoverPosition = 3;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 269 && MouseHandler.mouseX <= 325) {
				channelButtonHoverPosition = 4;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 335 && MouseHandler.mouseX <= 391) {
				channelButtonHoverPosition = 5;
				inputTaken = true;
			} else if (MouseHandler.mouseX >= 404 && MouseHandler.mouseX <= 515) {
				channelButtonHoverPosition = 6;
				inputTaken = true;
			}
		} else {
			channelButtonHoverPosition = -1;
			inputTaken = true;
		}
		if (clickMode3 == 1) {
			if (MouseHandler.saveClickY >= canvasHeight - 22 && MouseHandler.saveClickY <= canvasHeight) {
				if (MouseHandler.saveClickX >= 5 && MouseHandler.saveClickX <= 61) {
					if (channelButtonClickPosition == 0)
						toggleHideChatArea();
					channelButtonClickPosition = 0;
					chatTypeView = 0;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 71 && MouseHandler.saveClickX <= 127) {
					if (channelButtonClickPosition == 1)
						toggleHideChatArea();
					channelButtonClickPosition = 1;
					chatTypeView = 5;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 137 && MouseHandler.saveClickX <= 193) {
					if (channelButtonClickPosition == 2)
						toggleHideChatArea();
					channelButtonClickPosition = 2;
					chatTypeView = 1;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 203 && MouseHandler.saveClickX <= 259) {
					if (channelButtonClickPosition == 3)
						toggleHideChatArea();
					channelButtonClickPosition = 3;
					chatTypeView = 2;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 269 && MouseHandler.saveClickX <= 325) {
					if (channelButtonClickPosition == 4)
						toggleHideChatArea();
					channelButtonClickPosition = 4;
					chatTypeView = 11;
					inputTaken = true;
				} else if (MouseHandler.saveClickX >= 335 && MouseHandler.saveClickX <= 391) {
					if (channelButtonClickPosition == 5)
						toggleHideChatArea();
					channelButtonClickPosition = 5;
					chatTypeView = 3;
					inputTaken = true;
				}
			}
		}
	}

	static boolean hideChatArea = false;

	private static void toggleHideChatArea() {
		if (!instance.isResized())
			return;
		hideChatArea = !hideChatArea;
	}

	public void updateVarp(int i) {
		ObjectSound.update();
		if (i > Js5List.getConfigSize(Js5ConfigType.VARPLAYER))
			return;

		int j = VariablePlayer.lookup(i).type;
		if (j == 0)
			return;
		int k = variousSettings[i];
		if (j == 1) {
//			if (k == 1)
//				Rasterizer.setBrightness(0.90000000000000002D);
//			if (k == 2)
//				Rasterizer.setBrightness(0.80000000000000004D);
//			if (k == 3)
//				Rasterizer.setBrightness(0.69999999999999996D);
//			if (k == 4)
//				Rasterizer.setBrightness(0.59999999999999998D);
			ItemDefinition.sprites.clear();
			welcomeScreenRaised = true;
		}
		if (j == 3) {
			boolean flag1 = this.musicEnabled;
			int volume = 0;

			if (k == 0) {
				volume = 255;
			} else if (k == 1) {
				volume = 192;
			} else if (k == 2) {
				volume = 128;
			} else if (k == 3) {
				volume = 64;
			} else if (k == 4) {
				volume = 0;
			}

			if(this.musicEnabled != flag1) {
				if(this.musicEnabled) {
					this.nextSong = this.currentSong;
					this.songChanging = true;
					StaticSound.playSong(this.nextSong);
				} else {
					StaticSound.clear();
				}

				this.prevSong = 0;
			}
		}
		if (j == 4) {
			if (k == 0) {
				soundEffectVolume = 127;
			} else if (k == 1) {
				soundEffectVolume = 96;
			} else if (k == 2) {
				soundEffectVolume = 64;
			} else if (k == 3) {
				soundEffectVolume = 32;
			} else if (k == 4) {
				soundEffectVolume = 0;
			}
		}
		if (j == 5)
			anInt1253 = k;
		if (j == 6)
			chatEffects = k;
		if (j == 9)
			anInt913 = k;
	}

	public RSFontOSRS smallFontOSRS,regularFontOSRS,boldFontOSRS,fancyFontOSRS,fancyFontMediumOSRS,fancyFontLargeOSRS;

	public boolean splatFade = true;

	void updateEntities(int viewportOffsetX, int viewportOffsetY) {

		for (int index = -1; index < playerCount + npcCount; index++) {
			Entity actor;

			if (index == -1) {
				actor = localPlayer;
			} else if (index < playerCount) {
				actor = players[playerIndices[index]];
			} else {
				actor = npcs[npcIndices[index - playerCount]];
			}

			if (actor == null) {
				continue;
			}

			if (actor instanceof Npc) {
				NpcDefinition entityDef = ((Npc) actor).desc;

				if (entityDef.configs != null) {
					entityDef = entityDef.morph();
				}

				if (entityDef == null) {
					continue;
				}
			}

			int offsetY = -2;

			if (actor.spokenText != null && (index >= playerCount || publicChatMode == 4 || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) actor).displayName))) {
				npcScreenPos(actor, actor.defaultHeight);
				if (viewportTempX > -1 && overheadTextCount < overheadTextLimit) {
					overheadTextXOffsets[overheadTextCount] = boldFontOSRS.stringWidth(actor.spokenText) / 2;
					overheadTextAscents[overheadTextCount] = boldFontOSRS.ascent;
					overheadTextXs[overheadTextCount] = viewportTempX;
					overheadTextYs[overheadTextCount] = viewportTempY - offsetY;
					overheadTextColors[overheadTextCount] = actor.textColour;
					overheadTextEffects[overheadTextCount] = actor.textEffect;
					overheadTextCyclesRemaining[overheadTextCount] = actor.overheadTextCyclesRemaining;
					overheadText[overheadTextCount] = actor.spokenText;
					++Client.overheadTextCount;
					if (chatEffects == 0 && actor.textEffect >= 1 && actor.textEffect <= 3) {
						overheadTextAscents[overheadTextCount] += 10;
						overheadTextYs[overheadTextCount] += 5;
					}

					if (chatEffects == 0 && actor.textEffect == 4) {
						overheadTextXOffsets[overheadTextCount] = 60;
					}

					if (chatEffects == 0 && actor.textEffect == 5) {
						overheadTextAscents[overheadTextCount] += 5;
					}
					offsetY += 12;
				}
			}


			int hitsplatType2;
			int classicWidth;
			int middleGraphicWidth;


			int verticalOffset = offsetY;
			if (!actor.healthBars.hasActiveHealthBars()) {
				npcScreenPos(actor, actor.defaultHeight + 15);

				for (HealthBar healthBar = (HealthBar) actor.healthBars.last(); healthBar != null; healthBar = (HealthBar) actor.healthBars.previous()) {
					HealthBarUpdate healthUpdate = healthBar.get(loopCycle);

					if (healthUpdate == null && healthBar.isEmpty()) {
						healthBar.remove();
					} else if (healthUpdate != null) {
						HealthBarDefinition definition = healthBar.definition;
						Sprite backSprite = definition.getBackSprite();
						Sprite frontSprite = definition.getFrontSprite();
						int padding = (backSprite != null && frontSprite != null && definition.widthPadding * 2 < frontSprite.myWidth) ? definition.widthPadding : 0;
						int barWidth = (frontSprite != null) ? frontSprite.myWidth - padding * 2 : definition.width;

						int elapsedTicks = loopCycle - healthUpdate.cycle;  // Here's where we define elapsedTicks.
						int opacity = healthBar.calculateOpacity(healthUpdate, definition, elapsedTicks);
						int renderedWidth = healthBar.calculateRenderedWidth(healthUpdate, definition, elapsedTicks, barWidth);

						verticalOffset = healthBar.drawHealthBar(backSprite, frontSprite, barWidth, padding, renderedWidth, opacity, verticalOffset);
					}
				}
			}
			offsetY = verticalOffset;

			if (offsetY == -2) {
				offsetY += 7;
			}

			if (actor instanceof Player) {
				Player player = (Player) actor;

				if (player.skullIcon != -1 || player.headIcon != -1) {
					npcScreenPos(actor, actor.defaultHeight + 15);
					if (viewportTempX > -1) {

						if (player.skullIcon >= 0 && skullIcons != null && player.skullIcon < skullIcons.length) {
							Sprite skull = skullIcons[player.skullIcon];
							if (skull != null) {
								offsetY += 25;
								skull.drawSprite(viewportOffsetX + viewportTempX - 12,
										viewportOffsetY + viewportTempY - offsetY);
							}
						}

						if (player.headIcon >= 0 && headIcons != null && player.headIcon < headIcons.length) {
							Sprite head = headIcons[player.headIcon];
							if (head != null) {
								offsetY += 25;
								head.drawSprite(viewportOffsetX + viewportTempX - 12,
										viewportOffsetY + viewportTempY - offsetY);
							}
						}
					}
				}


				if (player.isHintArrowPointingAtPlayer(index)) {
					npcScreenPos(actor, actor.defaultHeight + 15);
					if (viewportTempX > -1) {
						offsetY += headIconsHint[1].myHeight;
						headIconsHint[1].drawSprite(viewportOffsetX + viewportTempX - 12, viewportOffsetY + viewportTempY - offsetY);
					}
				}

			} else if (actor instanceof Npc) {
				Npc npc = (Npc) actor;

				// Draw NPC icons
				npc.drawIcons();

				if (npc.isHintArrowPointingAtNpc(index)) {
					npcScreenPos(actor, actor.defaultHeight + 15);
					if (viewportTempX > -1) {
						headIconsHint[0].drawSprite(viewportOffsetX + viewportTempX - 12, viewportOffsetY + viewportTempY - 28);
					}
				}

			}

			for (int hitsplatIndex = 0; hitsplatIndex < 4; ++hitsplatIndex) {
				int cycles = actor.hitSplatCycles[hitsplatIndex];
				int types = actor.hitSplatTypes[hitsplatIndex];
				HitSplatDefinition hitSplatDefinition = null;
				int animationDuration = 0;
				if (types >= 0) {
					if (cycles <= Client.loopCycle) {
						continue;
					}


					hitSplatDefinition = HitSplatDefinition.lookup(actor.hitSplatTypes[hitsplatIndex]);
					animationDuration = hitSplatDefinition.animationDuration;
					if (hitSplatDefinition != null && hitSplatDefinition.multimark != null) {
						hitSplatDefinition = hitSplatDefinition.transform();
						if (hitSplatDefinition == null) {
							actor.hitSplatCycles[hitsplatIndex] = -1;
							continue;
						}
						if (splatFade) {
							hitSplatDefinition.fadeat = 40;
							hitSplatDefinition.animationEndY = 10;
							hitSplatDefinition.animationDuration = 50;
							if (actor.hitSplatDamageType[hitsplatIndex] != -1) {
								hitSplatDefinition.classicGraphic = 2224 + (actor.hitSplatDamageType[hitsplatIndex]);
							}
						}
					}
				} else if (cycles < 0) {
					continue;
				}


				hitsplatType2 = actor.hitSplatTypes2[hitsplatIndex];
				HitSplatDefinition hitSplatDefinition2 = null;
				if (hitsplatType2 >= 0) {
					hitSplatDefinition2 = HitSplatDefinition.lookup(hitsplatType2);
					if (hitSplatDefinition2 != null && hitSplatDefinition2.multimark != null) {
						hitSplatDefinition2 = hitSplatDefinition2.transform();
					}
					if (splatFade) {
						hitSplatDefinition2.fadeat = 40;
						hitSplatDefinition2.animationEndY = 10;
						hitSplatDefinition2.animationDuration = 50;
						if (actor.hitSplatDamageType2[hitsplatIndex] != -1) {
							hitSplatDefinition2.classicGraphic = 2224 + actor.hitSplatDamageType2[hitsplatIndex];
						}
					}
				}

				if (cycles - animationDuration <= Client.loopCycle) {
					if (hitSplatDefinition == null) {
						actor.hitSplatCycles[hitsplatIndex] = -1;
					} else {
						npcScreenPos(actor, actor.defaultHeight / 2);
						if (viewportTempX > -1) {
							if (hitsplatIndex == 1) {
								viewportTempY -= 20;
							}

							if (hitsplatIndex == 2) {
								viewportTempX -= 15;
								viewportTempY -= 10;
							}

							if (hitsplatIndex == 3) {
								viewportTempX += 15;
								viewportTempY -= 10;
							}


							classicWidth = 0;
							middleGraphicWidth = 0;
							int leftGraphicWidth = 0;
							int rightGraphicWidth = 0;
							int classicOffsetX = 0;
							int middleOffsetX = 0;
							int leftOffsetX = 0;
							int rightOffsetX = 0;
							Sprite classicGraphic2 = null;
							Sprite middleGraphic2 = null;
							Sprite leftGraphic2 = null;
							Sprite rightGraphic2 = null;
							int classicGraphic2Width = 0;
							int middleGraphic2Width = 0;
							int leftGraphic2Width = 0;
							int rightGraphic2Width = 0;
							int classic2OffsetX = 0;
							int middle2OffsetX = 0;
							int left2OffsetX = 0;
							int right2OffsetX = 0;
							int realHeight = 0;
							Sprite classicGraphic = hitSplatDefinition.getClassicGraphic();
							int graphicHeight;
							if (classicGraphic != null) {
								classicWidth = classicGraphic.myWidth;
								graphicHeight = classicGraphic.myHeight;
								if (graphicHeight > realHeight) {
									realHeight = graphicHeight;
								}

								classicOffsetX = classicGraphic.drawOffsetX;
							}

							Sprite middleGraphic = hitSplatDefinition.getMiddleGraphic();
							if (middleGraphic != null) {
								middleGraphicWidth = middleGraphic.myWidth;
								graphicHeight = middleGraphic.myHeight;
								if (graphicHeight > realHeight) {
									realHeight = graphicHeight;
								}

								middleOffsetX = middleGraphic.drawOffsetX;
							}

							Sprite leftGraphic = hitSplatDefinition.getLeftGraphic();
							if (leftGraphic != null) {
								leftGraphicWidth = leftGraphic.myWidth;
								graphicHeight = leftGraphic.myHeight;
								if (graphicHeight > realHeight) {
									realHeight = graphicHeight;
								}

								leftOffsetX = leftGraphic.drawOffsetX;
							}

							Sprite rightGraphic = hitSplatDefinition.getRightGraphic();
							if (rightGraphic != null) {
								rightGraphicWidth = rightGraphic.myWidth;
								graphicHeight = rightGraphic.myHeight;
								if (graphicHeight > realHeight) {
									realHeight = graphicHeight;
								}

								rightOffsetX = rightGraphic.drawOffsetX;
							}

							if (hitSplatDefinition2 != null) {
								classicGraphic2 = hitSplatDefinition2.getClassicGraphic();
								if (classicGraphic2 != null) {
									classicGraphic2Width = classicGraphic2.myWidth;
									graphicHeight = classicGraphic2.myHeight;
									if (graphicHeight > realHeight) {
										realHeight = graphicHeight;
									}

									classic2OffsetX = classicGraphic2.drawOffsetX;
								}

								middleGraphic2 = hitSplatDefinition2.getMiddleGraphic();
								if (middleGraphic2 != null) {
									middleGraphic2Width = middleGraphic2.myWidth;
									graphicHeight = middleGraphic2.myHeight;
									if (graphicHeight > realHeight) {
										realHeight = graphicHeight;
									}

									middle2OffsetX = middleGraphic2.drawOffsetX;
								}

								leftGraphic2 = hitSplatDefinition2.getLeftGraphic();
								if (leftGraphic2 != null) {
									leftGraphic2Width = leftGraphic2.myWidth;
									graphicHeight = leftGraphic2.myHeight;
									if (graphicHeight > realHeight) {
										realHeight = graphicHeight;
									}

									left2OffsetX = leftGraphic2.drawOffsetX;
								}

								rightGraphic2 = hitSplatDefinition2.getRightGraphic();
								if (rightGraphic2 != null) {
									rightGraphic2Width = rightGraphic2.myWidth;
									graphicHeight = rightGraphic2.myHeight;
									if (graphicHeight > realHeight) {
										realHeight = graphicHeight;
									}

									right2OffsetX = rightGraphic2.drawOffsetX;
								}
							}

							RSFontOSRS hitSplatDefinitionFont = hitSplatDefinition.getFont();
							if (hitSplatDefinitionFont == null) {
								hitSplatDefinitionFont = regularFontOSRS;
							}

							RSFontOSRS hitSplatDefinitionFont2;
							if (hitSplatDefinition2 != null) {
								hitSplatDefinitionFont2 = hitSplatDefinition2.getFont();
								if (hitSplatDefinitionFont2 == null) {
									hitSplatDefinitionFont2 = regularFontOSRS;
								}
							} else {
								hitSplatDefinitionFont2 = regularFontOSRS;
							}

							String damageText = null;
							String damageText2 = null;

							int stringWidth2 = 0;
							damageText = hitSplatDefinition.formatDamage(actor.hitSplatValues[hitsplatIndex]);
							int stringWidth = hitSplatDefinitionFont.stringWidth(damageText);
							if (hitSplatDefinition2 != null) {
								damageText2 = hitSplatDefinition2.formatDamage(actor.hitSplatValues2[hitsplatIndex]);
								stringWidth2 = hitSplatDefinitionFont2.stringWidth(damageText2);
							}

							int var49 = 0;
							int var50 = 0;
							if (middleGraphicWidth > 0) {
								if (leftGraphic == null && rightGraphic == null) {
									var49 = 1;
								} else {
									var49 = stringWidth / middleGraphicWidth + 1;
								}
							}

							if (hitSplatDefinition2 != null && middleGraphic2Width > 0) {
								if (leftGraphic2 == null && rightGraphic2 == null) {
									var50 = 1;
								} else {
									var50 = stringWidth2 / middleGraphic2Width + 1;
								}
							}

							int var51 = 0;
							int var52 = var51;
							if (classicWidth > 0) {
								var51 += classicWidth;
							}

							var51 += 2;
							int var53 = var51;
							if (leftGraphicWidth > 0) {
								var51 += leftGraphicWidth;
							}

							int var54 = var51;
							int var55 = var51;
							int var56;
							if (middleGraphicWidth > 0) {
								var56 = middleGraphicWidth * var49;
								var51 += var56;
								var55 += (var56 - stringWidth) / 2;
							} else {
								var51 += stringWidth;
							}

							var56 = var51;
							if (rightGraphicWidth > 0) {
								var51 += rightGraphicWidth;
							}

							int var57 = 0;
							int var58 = 0;
							int var59 = 0;
							int var60 = 0;
							int var61 = 0;
							int var62;
							if (hitSplatDefinition2 != null) {
								var51 += 2;
								var57 = var51;
								if (classicGraphic2Width > 0) {
									var51 += classicGraphic2Width;
								}

								var51 += 2;
								var58 = var51;
								if (leftGraphic2Width > 0) {
									var51 += leftGraphic2Width;
								}

								var59 = var51;
								var61 = var51;
								if (middleGraphic2Width > 0) {
									var62 = var50 * middleGraphic2Width;
									var51 += var62;
									var61 += (var62 - stringWidth2) / 2;
								} else {
									var51 += stringWidth2;
								}

								var60 = var51;
								if (rightGraphic2Width > 0) {
									var51 += rightGraphic2Width;
								}
							}

							var62 = actor.hitSplatCycles[hitsplatIndex] - Client.loopCycle;
							int var63 = hitSplatDefinition.animationEndX - var62 * hitSplatDefinition.animationEndX / hitSplatDefinition.animationDuration;
							int var64 = var62 * hitSplatDefinition.animationEndY / hitSplatDefinition.animationDuration + -hitSplatDefinition.animationEndY;
							int var65 = var63 + (viewportOffsetX + viewportTempX - (var51 >> 1));
							int var66 = viewportOffsetY + viewportTempY - 12 + var64;
							int var67 = var66;
							int var68 = realHeight + var66;
							int var69 = var66 + hitSplatDefinition.damageYOfset + 15;
							int var70 = var69 - hitSplatDefinitionFont.maxAscent;
							int var71 = var69 + hitSplatDefinitionFont.maxDescent;
							if (var70 < var66) {
								var67 = var70;
							}

							if (var71 > var68) {
								var68 = var71;
							}

							int var72 = 0;
							int var73;
							int var74;
							if (hitSplatDefinition2 != null) {
								var72 = var66 + hitSplatDefinition2.damageYOfset + 15;
								var73 = var72 - hitSplatDefinitionFont2.maxAscent;
								var74 = var72 + hitSplatDefinitionFont2.maxDescent;
								if (var73 < var67) {
									;
								}

								if (var74 > var68) {
									;
								}
							}

							var73 = 255;
							if (hitSplatDefinition.fadeat >= 0) {
								var73 = (var62 << 8) / (hitSplatDefinition.animationDuration - hitSplatDefinition.fadeat);
							}

							if (var73 >= 0 && var73 < 255) {
								if (classicGraphic != null) {
									classicGraphic.drawAdvancedSprite(var65 + var52 - classicOffsetX, var66, var73);
								}

								if (leftGraphic != null) {
									leftGraphic.drawAdvancedSprite(var65 + var53 - leftOffsetX, var66, var73);
								}

								if (middleGraphic != null) {
									for (var74 = 0; var74 < var49; ++var74) {
										middleGraphic.drawAdvancedSprite(var74 * middleGraphicWidth + (var54 + var65 - middleOffsetX), var66, var73);
									}
								}

								if (rightGraphic != null) {
									rightGraphic.drawAdvancedSprite(var65 + var56 - rightOffsetX, var66, var73);
								}

								hitSplatDefinitionFont.drawAlpha(damageText, var65 + var55, var69, hitSplatDefinition.textColor, 0, var73);
								if (hitSplatDefinition2 != null) {
									if (classicGraphic2 != null) {
										classicGraphic2.drawAdvancedSprite(var57 + var65 - classic2OffsetX, var66, var73);
									}

									if (leftGraphic2 != null) {
										leftGraphic2.drawAdvancedSprite(var58 + var65 - left2OffsetX, var66, var73);
									}

									if (middleGraphic2 != null) {
										for (var74 = 0; var74 < var50; ++var74) {
											middleGraphic2.drawAdvancedSprite(var74 * middleGraphic2Width + (var59 + var65 - middle2OffsetX), var66, var73);
										}
									}

									if (rightGraphic2 != null) {
										rightGraphic2.drawAdvancedSprite(var65 + var60 - right2OffsetX, var66, var73);
									}

									hitSplatDefinitionFont2.drawAlpha(damageText2, var61 + var65, var72, hitSplatDefinition2.textColor, 0, var73);
								}
							} else {
								if (classicGraphic != null) {
									classicGraphic.drawSprite(var65 + var52 - classicOffsetX, var66);
								}

								if (leftGraphic != null) {
									leftGraphic.drawSprite(var65 + var53 - leftOffsetX, var66);
								}

								if (middleGraphic != null) {
									for (var74 = 0; var74 < var49; ++var74) {
										middleGraphic.drawSprite(middleGraphicWidth * var74 + (var65 + var54 - middleOffsetX), var66);
									}
								}

								if (rightGraphic != null) {
									rightGraphic.drawSprite(var56 + var65 - rightOffsetX, var66);
								}

								hitSplatDefinitionFont.draw(damageText, var65 + var55, var69, hitSplatDefinition.textColor | -16777216, 0);
								if (hitSplatDefinition2 != null) {
									if (classicGraphic2 != null) {
										classicGraphic2.drawSprite(var57 + var65 - classic2OffsetX, var66);
									}

									if (leftGraphic2 != null) {
										leftGraphic2.drawSprite(var58 + var65 - left2OffsetX, var66);
									}

									if (middleGraphic2 != null) {
										for (var74 = 0; var74 < var50; ++var74) {
											middleGraphic2.drawSprite(var74 * middleGraphic2Width + (var65 + var59 - middle2OffsetX), var66);
										}
									}

									if (rightGraphic2 != null) {
										rightGraphic2.drawSprite(var65 + var60 - right2OffsetX, var66);
									}

									hitSplatDefinitionFont2.draw(damageText2, var65 + var61, var72, hitSplatDefinition2.textColor | -16777216, 0);
								}
							}
						}
					}
				}
			}

			for (int textIndex = 0; textIndex < overheadTextCount; ++textIndex) {
				int textX = overheadTextXs[textIndex];
				int textY = overheadTextYs[textIndex];
				int textOffsetX = overheadTextXOffsets[textIndex];
				int textOffsetY = overheadTextAscents[textIndex];
				boolean needsPositionAdjustment = true;

				while (needsPositionAdjustment) {
					needsPositionAdjustment = false;

					for (int textCheckCount = 0; textCheckCount < textIndex; ++textCheckCount) {
						if (textY + 2 > overheadTextYs[textCheckCount] - overheadTextAscents[textCheckCount] && textY - textOffsetY < overheadTextYs[textCheckCount] + 2 && textX - textOffsetX < overheadTextXOffsets[textCheckCount] + overheadTextXs[textCheckCount] && textX + textOffsetX > overheadTextXs[textCheckCount] - overheadTextXOffsets[textCheckCount] && overheadTextYs[textCheckCount] - overheadTextAscents[textCheckCount] < textY) {
							textY = overheadTextYs[textCheckCount] - overheadTextAscents[textCheckCount];
							needsPositionAdjustment = true;
						}
					}
				}

				viewportTempX = overheadTextXs[textIndex];
				viewportTempY = overheadTextYs[textIndex] = textY;
				String currentText = overheadText[textIndex];
				int textColor = 16776960;
				if (chatEffects == 0) {
					if (overheadTextColors[textIndex] < 6) {
						textColor = field758[overheadTextColors[textIndex]];
					}

					if (overheadTextColors[textIndex] == 6) {
						textColor = viewportDrawCount % 20 < 10 ? 16711680 : 16776960;
					}

					if (overheadTextColors[textIndex] == 7) {
						textColor = viewportDrawCount % 20 < 10 ? 255 : '\uffff';
					}

					if (overheadTextColors[textIndex] == 8) {
						textColor = viewportDrawCount % 20 < 10 ? '뀀' : 8454016;
					}

					int remainingCycle = 150 - overheadTextCyclesRemaining[textIndex];
					int colorIndex = overheadTextColors[textIndex];

					if (colorIndex == 9) {
						if (remainingCycle < 50) {
							textColor = remainingCycle * 1280 + 16711680;
						} else if (remainingCycle < 100) {
							textColor = 16776960 - (remainingCycle - 50) * 327680;
						} else {
							textColor = (remainingCycle - 100) * 5 + 65280;
						}
					} else if (colorIndex == 10) {
						if (remainingCycle < 50) {
							textColor = remainingCycle * 5 + 16711680;
						} else if (remainingCycle < 100) {
							textColor = 16711935 - (remainingCycle - 50) * 327680;
						} else {
							textColor = (remainingCycle - 100) * 327680 + 255 - (remainingCycle - 100) * 5;
						}
					} else if (colorIndex == 11) {
						if (remainingCycle < 50) {
							textColor = 16777215 - remainingCycle * 327685;
						} else if (remainingCycle < 100) {
							textColor = (remainingCycle - 50) * 327685 + 65280;
						} else {
							textColor = 16777215 - (remainingCycle - 100) * 327680;
						}
					}

					if (overheadTextColors[textIndex] == 12 && chatEffectGradientColors[textIndex] == null) {
						int textLength = currentText.length();
						chatEffectGradientColors[textIndex] = new int[textLength];

						for (int charIndex = 0; charIndex < textLength; charIndex++) {
							int colorRatio = (int) (64.0F * (float) charIndex / textLength);
							int colorIndexX = colorRatio << 10 | 896 | 64;
							chatEffectGradientColors[textIndex][charIndex] = ColorUtils.colorLookupTable[colorIndexX];
						}
					}

					if (overheadTextEffects[textIndex] == 0) {

						int spriteDrawX = this.viewportTempX;
						String editableForcedChat = currentText;
						if (currentText.startsWith("<sprite")) {
							try {
								int endIndex = currentText.indexOf('>');

								if (endIndex != -1) {
									int imageId = Integer.valueOf(currentText.substring(8, endIndex));
									editableForcedChat = editableForcedChat.substring(endIndex + 1);
									int textWidth = boldFontOSRS.getTextWidth(editableForcedChat) / 2;
									Sprite icon = ImageCache.get(imageId);
									int iconModY = icon.maxHeight - 1;
									icon.drawSprite(this.viewportTempX - textWidth - 1, (viewportTempY - iconModY));
									spriteDrawX += 11;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						boldFontOSRS.drawText(0, editableForcedChat, viewportTempY + 1 + viewportOffsetY, viewportOffsetX + spriteDrawX);
						boldFontOSRS.drawText(textColor, editableForcedChat, viewportTempY + viewportOffsetY, viewportOffsetX + spriteDrawX);
					}

					if (overheadTextEffects[textIndex] == 1) {
						boldFontOSRS.drawCenteredWave(currentText, viewportOffsetX + viewportTempX, viewportTempY + viewportOffsetY, textColor, 0, viewportDrawCount);
					}

					if (overheadTextEffects[textIndex] == 2) {
						boldFontOSRS.drawCenteredWaveWithShaking(currentText, viewportOffsetX + viewportTempX, viewportTempY + viewportOffsetY, textColor, 0, viewportDrawCount);
					}

					if (overheadTextEffects[textIndex] == 3) {
						boldFontOSRS.drawCenteredShakeWithVariance(currentText, viewportOffsetX + viewportTempX, viewportTempY + viewportOffsetY, textColor, 0, viewportDrawCount, 150 - overheadTextCyclesRemaining[textIndex]);
					}

					if (overheadTextEffects[textIndex] == 4) {
						remainingCycle = (150 - overheadTextCyclesRemaining[textIndex]) * (boldFontOSRS.stringWidth(currentText) + 100) / 150;
						Rasterizer2D.expandClip(viewportOffsetX + viewportTempX - 50, viewportOffsetY, viewportOffsetX + viewportTempX + 50, viewportOffsetY + viewportOffsetY);
						boldFontOSRS.drawBasicString(currentText, viewportOffsetX + viewportTempX + 50 - remainingCycle, viewportTempY + viewportOffsetY, textColor, 0);
						Rasterizer2D.setClip(viewportOffsetX, viewportOffsetY, viewportOffsetX + viewportOffsetX, viewportOffsetY + viewportOffsetY);
					}

					if (overheadTextEffects[textIndex] == 5) {
						int yOffset = 0;
						remainingCycle = 150 - overheadTextCyclesRemaining[textIndex];
						yOffset = 0;
						if (remainingCycle < 25) {
							yOffset = remainingCycle - 25;
						} else if (remainingCycle > 125) {
							yOffset = remainingCycle - 125;
						}

						Rasterizer2D.expandClip(viewportOffsetX, viewportTempY + viewportOffsetY - boldFontOSRS.ascent - 1, viewportOffsetX + viewportOffsetX, viewportTempY + viewportOffsetY + 5);

						boldFontOSRS.drawText(0, currentText, yOffset + viewportTempX + 1 + viewportOffsetY, viewportOffsetX + viewportTempX);
						boldFontOSRS.drawText(textColor, currentText, yOffset + viewportTempY + viewportOffsetY, viewportOffsetX + viewportTempX);

						Rasterizer2D.setClip(viewportOffsetX, viewportOffsetY, viewportOffsetX + viewportOffsetX, viewportOffsetY + viewportOffsetY);
					}
				} else {
					boldFontOSRS.drawCentered(currentText, viewportOffsetX + viewportTempX, viewportTempY + viewportOffsetY, 16776960, 0);
				}
			}
		}
	}


	void delFriend(long l) {
		try {
			if (l == 0L)
				return;
			for (int i = 0; i < friendsCount; i++) {
				if (friendsListAsLongs[i] != l)
					continue;
				friendsCount--;
				needDrawTabArea = true;
				for (int j = i; j < friendsCount; j++) {
					friendsList[j] = friendsList[j + 1];
					friendsNodeIDs[j] = friendsNodeIDs[j + 1];
					friendsListAsLongs[j] = friendsListAsLongs[j + 1];
				}

				stream.createFrame(215);
				stream.writeQWord(l);
				break;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	Sprite tabAreaFixed;
	Sprite[] tabAreaResizable = new Sprite[3];
	boolean drawingTabArea = false;

	public void drawTabArea() {
		interfaceManager.drawTabArea();
	}

	Sprite[] sideIcons = new Sprite[20];
	Sprite[] redStones = new Sprite[6];



	private void drawTabs(int xOffset, int yOffset) {
		interfaceManager.drawTabs(xOffset, yOffset);
	}


	public void processMobChatText() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = maxPlayerCount;
			else
				j = playerIndices[i];
			Player player = players[j];
			if (player != null && player.overheadTextCyclesRemaining > 0) {
				player.overheadTextCyclesRemaining--;
				if (player.overheadTextCyclesRemaining == 0)
					player.spokenText = null;
			}
		}
		for (int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			Npc npc = npcs[l];
			if (npc != null && npc.overheadTextCyclesRemaining > 0) {
				npc.overheadTextCyclesRemaining--;
				if (npc.overheadTextCyclesRemaining == 0)
					npc.spokenText = null;
			}
		}
	}


	void addFriend(long l) {
		try {
			if (l == 0L)
				return;

			String username = StringUtils.fixName(StringUtils.nameForLong(l));

			if (username.equalsIgnoreCase(localPlayer.displayName)) {
				System.err.println("blocked from adding self as friend..");
				return;
			}
			if (friendsCount >= 100 && anInt1046 != 1) {
				pushMessage("Your friendlist is full.", 0, "");
				return;
			}
			if (friendsCount >= 200) {
				pushMessage("Your friendlist is full.", 0, "");
				return;
			}

			for (int i = 0; i < friendsCount; i++)
				if (friendsListAsLongs[i] == l) {

					return;
				}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage("Please remove " + username + " from your ignore list first", 0, "");
					return;
				}

			if (username.equals(localPlayer.displayName)) {
				return;
			} else {
				//friendsList[friendsCount] = s;
				//friendsListAsLongs[friendsCount] = l;
				//friendsNodeIDs[friendsCount] = 0;
				//friendsCount++;
				//needDrawTabArea = true;
				stream.createFrame(188);
				stream.writeQWord(l);
				return;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public int getCenterHeight(int z, int y, int x) {
		int worldX = x >> 7;
		int worldY = y >> 7;
		if (worldX < 0 || worldY < 0 || worldX > 103 || worldY > 103)
			return 0;
		int plane = z;
		if (plane < 3 && (tileFlags[1][worldX][worldY] & 2) == 2)
			plane++;
		int sizeX = x & 0x7f;
		int sizeY = y & 0x7f;
		int i2 = tileHeights[plane][worldX][worldY] * (128 - sizeX)
				+ tileHeights[plane][worldX + 1][worldY] * sizeX >> 7;
		int j2 = tileHeights[plane][worldX][worldY + 1] * (128 - sizeX)
				+ tileHeights[plane][worldX + 1][worldY + 1] * sizeX >> 7;
		return i2 * (128 - sizeY) + j2 * sizeY >> 7;
	}

	public static int[] myHeadAndJaw = new int[2];

	public void resetLogout() {
		if (settingManager.getRememberMe()) {
			AccountData.Companion.save();
		}

		savePlayerData();

		setGameState(GameState.LOGIN_SCREEN);

		logger.debug("being logged out.. from: "+new Throwable().getStackTrace()[1].toString());
		logger.debug("Logging out..");
		firstLoginMessage = "Enter Username & Password";
		loggedIn = false;
		prayClicked = false;
		scene.reset_interactive_obj();
		Rasterizer2D.Rasterizer2D_clear();
		StaticSound.logout();
		captcha = null;
		captchaInput = "";
		frameMode(false);

		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		socketStream = null;
		PacketLog.clear();
		if (entityTarget != null) {
			entityTarget.stop();
		}
		// myUsername = "";
		// myPassword = "";
		unlinkMRUNodes();
		scene.clear();
		for (int i = 0; i < 4; i++)
			collisionMaps[i].init();
		System.gc();
		currentSong = -1;
		BroadcastManager.broadcasts = new Broadcast[1000];
		nextSong = -1;
		prevSong = 0;
		experienceCounter = 0;
		GameTimerHandler.getSingleton().stopAll();
		Preferences.save();

	}

	public void method45() {
		aBoolean1031 = true;

		// Debug: Dump female identity kits once when character design opens
		IdentityKit.dumpFemaleKitsOnce();

		if (Player.DEBUG_APPEARANCE) {
			System.out.println("[DESIGN] Initializing myAppearance for " + (aBoolean1047 ? "MALE" : "FEMALE"));
		}
		for (int j = 0; j < 7; j++) {
			myAppearance[j] = -1;
			int configSize = Js5List.getConfigSize(Js5ConfigType.IDENTKIT);
			for (int k = 0; k < configSize; k++) {
				IdentityKit kit = IdentityKit.lookup(k);
				if (kit.validStyle || kit.bodyPartId != j + (aBoolean1047 ? 0 : 7))
					continue;
				myAppearance[j] = k;
				if (Player.DEBUG_APPEARANCE) {
					System.out.println("[DESIGN] Slot " + j + ": found kit " + k + " with bodyPartId=" + kit.bodyPartId);
				}
				break;
			}
		}
		if (Player.DEBUG_APPEARANCE) {
			System.out.println("[DESIGN] Final myAppearance: head=" + myAppearance[0] + " jaw=" + myAppearance[1] +
				" torso=" + myAppearance[2] + " arms=" + myAppearance[3] + " hands=" + myAppearance[4] +
				" legs=" + myAppearance[5] + " feet=" + myAppearance[6]);
		}
	}

	private void method46(int i, Buffer stream) {
		while (stream.bitPosition + 21 < i * 8) {
			int k = stream.readBits(16);
			if (k == 65535)
				break;
			if (npcs[k] == null)
				npcs[k] = new Npc(); // Teleports works fine if we create a new npc?
			Npc npc = npcs[k];
			npcIndices[npcCount++] = k;
			callbacks.postDeferred(new NpcSpawned(npc));
			npc.lastUpdateTick = loopCycle;
			int l = stream.readBits(5);
			if (l > 15)
				l -= 32;
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(1);
			int npcId = stream.readBits(16);
			npc.npcPetType = stream.readBits(2);
			npc.desc = NpcDefinition.get(npcId);
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = k;
			npc.size = npc.desc.size;
			npc.rotation = npc.desc.rotationSpeed;
			npc.walkSequence = npc.desc.walkingAnimation;
			npc.walkBackSequence = npc.desc.rotate180AnimIndex;
			npc.walkLeftSequence = npc.desc.rotate90CWAnimIndex;
			npc.walkRightSequence = npc.desc.rotate90CCWAnimIndex;
			npc.idleSequence = npc.desc.standingAnimation;
			npc.readyanim_l = npc.desc.readyanim_l;
			npc.readyanim_r = npc.desc.readyanim_r;
			npc.anInt1538 = 0;
			npc.anInt1539 = 0;
			npc.setPos(localPlayer.pathX[0] + i1, localPlayer.pathY[0] + l, j1 == 1);
		}
		stream.finishBitAccess();
	}

	public int currentPercent = 0;
	public int targetPercent = 0;

	public void updateLoading() {
		if(loginScreen.getLoginState() != LoginState.LOADING) return;
		// Define the interpolation speed
		final int interpolationSpeed = 1;

		// Calculate the difference and decide the adjustment needed
		int difference = targetPercent - currentPercent;
		int adjustment;
		if (difference > 0) {
			adjustment = Math.min(interpolationSpeed, difference);
		} else if (difference < 0) {
			adjustment = Math.max(-interpolationSpeed, difference);
		} else {
			adjustment = 0;
		}

		// Update the current percentage
		currentPercent += adjustment;
	}

	protected static final void frameDrawHistory() {
		clock.mark();

		int var0;
		for(var0 = 0; var0 < 32; ++var0) {
			GameEngine.graphicsTickTimes[var0] = 0L;
		}

		for(var0 = 0; var0 < 32; ++var0) {
			GameEngine.clientTickTimes[var0] = 0L;
		}

		gameCyclesToDo = 0;
	}

	@Override
	public void doCycle() {
		loopCycle++;
		getCallbacks().tick();
		getCallbacks().post(ClientTick.INSTANCE);
		updateLoading();
		Js5System.doCycleJs5();
		ArchiveDiskActionHandler.processArchiveDiskActions();
		StaticSound.pulse();
		keyManager.prepareForNextCycle();
		keyHandler.processKeyEvents();
		synchronized(MouseHandler.instance) {
			MouseHandler.MouseHandler_currentButton = MouseHandler.currentButton;
			MouseHandler.mouseX = MouseHandler.MouseHandler_xVolatile;
			MouseHandler.mouseY = MouseHandler.MouseHandler_yVolatile;
			MouseHandler.MouseHandler_millis = MouseHandler.MouseHandler_lastMovedVolatile;
			MouseHandler.clickMode3 = MouseHandler.MouseHandler_lastButtonVolatile;
			MouseHandler.saveClickX = MouseHandler.MouseHandler_lastPressedXVolatile;
			MouseHandler.saveClickY = MouseHandler.MouseHandler_lastPressedYVolatile;
			MouseHandler.lastPressed = MouseHandler.MouseHandler_lastPressedTimeMillisVolatile;
			MouseHandler.MouseHandler_lastButtonVolatile = 0;
		}

		if (mouseWheel != null) {
			int var4 = mouseWheel.useRotation();
			mouseWheelRotation = var4;
		}

		if (gameState == 0) {
			load();
			frameDrawHistory();
		} else if (gameState == 5) {
			load();
			frameDrawHistory();
		} else if (gameState != 10 && gameState != 11) {
			if (gameState == 20) {
				processLoginScreenInput();
			} else if (gameState == 50) {
				processLoginScreenInput();
			} else if (gameState == 25) {
				loadRegion();
			} else if (gameState != 0) {
                if (clearLoginScreen) {
                    jagexNetThread.writePacket(true);
                }
            }
		} else {
			processLoginScreenInput();
		}

		if (gameState == 30) {
			mainGameProcessor();
		} else if (gameState == 40 || gameState == 45) {
			processLoginScreenInput();
		}

		getCallbacks().tickEnd();

	}

	public static void setZoom(int zoomValue) {
		int value = Ints.constrainToRange(zoomValue, 166, 900);
		Client.field625 = value;
		if (Client.field625 <= 0) {
			Client.field625 = 256;
		}

		Client.field626 = value - 51;
		if (Client.field626 <= 0) {
			Client.field626 = 205;
		}
	}

	void showPrioritizedPlayers() {
		showPlayer(localPlayer, maxPlayerCount, true);

		//Draw the player we're interacting with
		//Interacting includes combat, following, etc.
		int interact = localPlayer.interactingEntity - 32768;
		if (interact > 0) {
			Player player = players[interact];
			showPlayer(player, interact, false);
		}
	}

	void showOtherPlayers() {
		for (int l = 0; l < playerCount; l++) {
			Player player = players[playerIndices[l]];
			int index = playerIndices[l];

			//Don't draw interacting player as we've already drawn it on top
			int interact_index = (localPlayer.interactingEntity - 32768);
			if (interact_index > 0 && index == interact_index) {
				continue;
			}

			if (!showPlayer(player, index, false)) {
			}
		}
	}

	private boolean showPlayer(Player player, int i1, boolean flag) {
		if (player == null || !player.isVisible()) {
			return false;
		}

		player.isUnanimated = (low_detail && playerCount > 50 || playerCount > 200) && !flag && player.movementSequence == player.idleSequence;
		int j1 = player.x >> 7;
		int k1 = player.y >> 7;
		if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104) {
			return false;
		}
		long var4 = ObjectKeyUtil.calculateTag(0, 0, 0, false, i1);
		if (player.playerModel != null && loopCycle >= player.animationCycleStart && loopCycle < player.animationCycleEnd) {
			player.isUnanimated = false;
			player.lastUpdateTick = loopCycle;
			player.tileHeight = getCenterHeight(plane, player.y, player.x);
			scene.add_transformed_entity(plane, player.y, player, player.orientation, player.maxY, player.x, player.tileHeight, player.minX, player.maxX, i1, player.minY);
			return false;
		}
		if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
			if (tileCycleMap[j1][k1] == viewportDrawCount) {
				return false;
			}
			tileCycleMap[j1][k1] = viewportDrawCount;
		}
		player.lastUpdateTick = loopCycle;
		player.tileHeight = getCenterHeight(plane, player.y, player.x);
		scene.add_entity(plane, player.orientation, player.tileHeight, var4, player.y, 60, player.x, player, player.isWalking);
		return true;
	}

	boolean promptUserForInput(RSInterface class9) {
		int j = class9.contentType;
		if (anInt900 == 2) {
			if (j == 201) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if (j == 202) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if (j == 205) {
			anInt1011 = 250;
			return true;
		}
		if (j == 501) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if (j == 502) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if (j == 550) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 6;
			aString1121 = "Enter the name of the chat you wish to join";
		}
		if (j >= 300 && j <= 313) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = myAppearance[k];
			if (i2 != -1) {
				do {
					if (j1 == 0 && --i2 < 0)
						i2 = Js5List.getConfigSize(Js5ConfigType.IDENTKIT) - 1;
					if (j1 == 1 && ++i2 >= Js5List.getConfigSize(Js5ConfigType.IDENTKIT))
						i2 = 0;
				} while (IdentityKit.lookup(i2).validStyle || IdentityKit.lookup(i2).bodyPartId != k + (aBoolean1047 ? 0 : 7));
				myAppearance[k] = i2;
				aBoolean1031 = true;
			}
		}
		if (j >= 314 && j <= 323) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if (k1 == 0 && --j2 < 0)
				j2 = APPEARANCE_COLORS[l].length - 1;
			if (k1 == 1 && ++j2 >= APPEARANCE_COLORS[l].length)
				j2 = 0;
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if (j == 324 && !aBoolean1047) {
			aBoolean1047 = true;
			method45();
		}
		if (j == 325 && aBoolean1047) {
			aBoolean1047 = false;
			method45();
		}
		if (j == 326) {
			if (Player.DEBUG_APPEARANCE) {
				System.out.println("[SEND] Sending appearance packet to server:");
				System.out.println("[SEND] gender=" + (aBoolean1047 ? "0 (male)" : "1 (female)"));
				System.out.println("[SEND] myAppearance: head=" + myAppearance[0] + " jaw=" + myAppearance[1] +
					" torso=" + myAppearance[2] + " arms=" + myAppearance[3] + " hands=" + myAppearance[4] +
					" legs=" + myAppearance[5] + " feet=" + myAppearance[6]);
				System.out.println("[SEND] colors: " + anIntArray990[0] + ", " + anIntArray990[1] + ", " +
					anIntArray990[2] + ", " + anIntArray990[3] + ", " + anIntArray990[4]);
			}

			stream.createFrame(101);
			stream.writeUnsignedByte(aBoolean1047 ? 0 : 1);
			// Kit IDs can exceed 255 in rev 235, so send as shorts
			for (int i1 = 0; i1 < 7; i1++)
				stream.writeWord(myAppearance[i1]);

			for (int l1 = 0; l1 < 5; l1++)
				stream.writeUnsignedByte(anIntArray990[l1]);

			return true;
		}
		if (j == 613)
			canMute = !canMute;
		if (j >= 601 && j <= 612) {
			clearTopInterfaces();
			if (!reportAbuseInput.isEmpty()) {
				stream.createFrame(218);
				stream.writeQWord(StringUtils.longForName(reportAbuseInput));
				stream.writeUnsignedByte(j - 601);
				stream.writeUnsignedByte(canMute ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Player player = players[k];
			int l = stream.readUnsignedByte();
			if ((l & 0x40) != 0)
				l += stream.readUnsignedByte() << 8;
			playerups(l, k, stream, player);
		}
	}

	final void drawMapScenes(int arg0, int arg1, int arg2, int arg3, int arg4) {
		long var6 = scene.getBoundaryObjectTag(arg0, arg1, arg2);
		int var8;
		int var9;
		int var10;
		int var11;
		int var13;
		int var14;
		if (0L != var6) {
			var8 = scene.getObjectFlags(arg0, arg1, arg2, var6);
			var9 = var8 >> 6 & 3;
			var10 = var8 & 31;
			var11 = arg3;
			if (ViewportMouse.method5519(var6)) {
				var11 = arg4;
			}

			int[] var12 = minimapImage.Rasterizer2D_pixels;
			var13 = 24624 + arg1 * 4 + 2048 * (103 - arg2);
			var14 = ViewportMouse.Entity_unpackID(var6);
			ObjectDefinition var15 = ObjectDefinition.lookup(var14);
			if (var15.mapscene != -1 && mapSceneSprites != null) {
				IndexedImage var16 = mapSceneSprites[var15.mapscene];
				if (var16 != null) {
					int var17 = (var15.sizeX * 4 - var16.width) / 2;
					int var18 = (var15.sizeY * 4 - var16.height) / 2;
					var16.draw(48 + arg1 * 4 + var17, var18 + 48 + 4 * (104 - arg2 - var15.sizeY));
				}
			} else {
				if (0 == var10 || 2 == var10) {
					if (var9 == 0) {
						var12[var13] = var11;
						var12[var13 + 512] = var11;
						var12[1024 + var13] = var11;
						var12[var13 + 1536] = var11;
					} else if (var9 == 1) {
						var12[var13] = var11;
						var12[1 + var13] = var11;
						var12[2 + var13] = var11;
						var12[3 + var13] = var11;
					} else if (var9 == 2) {
						var12[3 + var13] = var11;
						var12[var13 + 3 + 512] = var11;
						var12[var13 + 3 + 1024] = var11;
						var12[1536 + var13 + 3] = var11;
					} else if (3 == var9) {
						var12[1536 + var13] = var11;
						var12[1 + var13 + 1536] = var11;
						var12[2 + 1536 + var13] = var11;
						var12[1536 + var13 + 3] = var11;
					}
				}

				if (3 == var10) {
					if (var9 == 0) {
						var12[var13] = var11;
					} else if (1 == var9) {
						var12[var13 + 3] = var11;
					} else if (2 == var9) {
						var12[3 + var13 + 1536] = var11;
					} else if (3 == var9) {
						var12[1536 + var13] = var11;
					}
				}

				if (var10 == 2) {
					if (3 == var9) {
						var12[var13] = var11;
						var12[512 + var13] = var11;
						var12[1024 + var13] = var11;
						var12[1536 + var13] = var11;
					} else if (var9 == 0) {
						var12[var13] = var11;
						var12[var13 + 1] = var11;
						var12[var13 + 2] = var11;
						var12[3 + var13] = var11;
					} else if (1 == var9) {
						var12[var13 + 3] = var11;
						var12[512 + 3 + var13] = var11;
						var12[3 + var13 + 1024] = var11;
						var12[3 + var13 + 1536] = var11;
					} else if (2 == var9) {
						var12[var13 + 1536] = var11;
						var12[1 + 1536 + var13] = var11;
						var12[2 + 1536 + var13] = var11;
						var12[3 + var13 + 1536] = var11;
					}
				}
			}
		}

		var6 = scene.getGameObjectTag(arg0, arg1, arg2);
		if (0L != var6) {
			var8 = scene.getObjectFlags(arg0, arg1, arg2, var6);
			var9 = var8 >> 6 & 3;
			var10 = var8 & 31;
			var11 = ViewportMouse.Entity_unpackID(var6);
			ObjectDefinition var25 = ObjectDefinition.lookup(var11);
			int var20;
			if (var25.mapscene != -1 && mapSceneSprites != null) {
				IndexedImage var19 = mapSceneSprites[var25.mapscene];
				if (var19 != null) {
					var14 = (var25.sizeX * 4 - var19.width) / 2;
					var20 = (var25.sizeY * 4 - var19.height) / 2;
					var19.draw(48 + arg1 * 4 + var14, var20 + (104 - arg2 - var25.sizeY) * 4 + 48);
				}
			} else if (9 == var10) {
				var13 = 15658734;
				if (ViewportMouse.method5519(var6)) {
					var13 = 15597568;
				}

				int[] var21 = minimapImage.Rasterizer2D_pixels;
				var20 = 2048 * (103 - arg2) + arg1 * 4 + 24624;
				if (0 != var9 && 2 != var9) {
					var21[var20] = var13;
					var21[1 + var20 + 512] = var13;
					var21[1024 + var20 + 2] = var13;
					var21[3 + 1536 + var20] = var13;
				} else {
					var21[1536 + var20] = var13;
					var21[1 + var20 + 1024] = var13;
					var21[512 + var20 + 2] = var13;
					var21[var20 + 3] = var13;
				}
			}
		}

		var6 = scene.getFloorDecorationTag(arg0, arg1, arg2);
		if (0L != var6) {
			var8 = ViewportMouse.Entity_unpackID(var6);
			ObjectDefinition var22 = ObjectDefinition.lookup(var8);
			if (var22.mapscene != -1 && mapSceneSprites != null) {
				IndexedImage var23 = mapSceneSprites[var22.mapscene];
				if (var23 != null) {
					var11 = (var22.sizeX * 4 - var23.width) / 2;
					int var24 = (var22.sizeY * 4 - var23.height) / 2;
					var23.draw(arg1 * 4 + 48 + var11, var24 + (104 - arg2 - var22.sizeY) * 4 + 48);
				}
			}
		}
	}

	private static void setHighMem() {
		SceneGraph.low_detail = false;

		lowMem = false;
		MapRegion.low_detail = false;
		ObjectDefinition.lowMem = false;
	}

	public static int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	/**
	 * An array containing all the player's experience.
	 */
	public static double[] experience;
	public static int totalExperience;

	public static int getStaticLevelByExperience(int slot) {
		double exp = experience[slot];

		int points = 0;
		int output = 0;
		for (byte lvl = 1; lvl < 100; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}

	private static String[] args = null;
	public static Client instance;
	public static boolean runelite;

	private static int getVersion() {
		String version = System.getProperty("java.version");
		if(version.startsWith("1.")) {
			version = version.substring(2, 3);
		} else {
			int dot = version.indexOf(".");
			if(dot != -1) { version = version.substring(0, dot); }
		} return Integer.parseInt(version);
	}


// bro its still buildding
	public static JFrame appFrame = null;
	public static Container gameContainer = null;


	private static void setLoggingLevel(Level level) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(level);
	}

	public static Client getClient(boolean runelite, String...args) {
		try {
//			try {
//				ClientDiscordRPC.initialize();
//			} catch (Exception e) {
//				System.err.println("Failed to establish Discord RPC.");
//				e.printStackTrace();
//			}

			System.out.println("Running Java version " + getVersion());
			Client.runelite = runelite;
			Client.args = args;
			StringBuilder windowTitleBuilder = new StringBuilder();
			windowTitleBuilder.append(Configuration.CLIENT_TITLE);
			server = Configuration.DEDICATED_SERVER_ADDRESS;
			setLoggingLevel(Level.INFO);

			if (args.length > 0) {
				StringBuilder configurationBuilder = new StringBuilder();
				configurationBuilder.append("[");

				for (int index = 0; index < args.length; index++) {
					configurationBuilder.append(args[index].replace("--", ""));
					if (index != args.length - 1)
						configurationBuilder.append(" ");

					if (args[index].startsWith("server=")) {
						server = args[index].replace("server=", "");
						System.out.println("Custom connection address set through run arguments: " + server);
					} else {
						switch (args[index]) {
							case "--debug":
							case "-db":
								Configuration.DEBUG_MODE = true;
								setLoggingLevel(Level.DEBUG);
								System.out.println("Setting log level to debug.");
								break;

							case "--developer":
							case "-d":
								Configuration.developerMode = true;
								Configuration.cacheName = Configuration.CACHE_NAME_DEV;
								System.out.println("Developer mode enabled.");
								break;
							case "--local":
							case "-l":
							case "localhost":
							case "local":
								if (server.equals(Configuration.DEDICATED_SERVER_ADDRESS)) {
									server = "127.0.0.1";
									System.out.println("Localhost client enabled.");
								} else {
									throw new IllegalArgumentException("Cannot have custom ip and local enabled.");
								}
								break;
							case "test_server":
								port = Configuration.TEST_PORT;
								System.out.println("Test server enabled.");
								break;

							case "pack_data":
								Configuration.packIndexData = true;
								break;
							case "dump_maps":
								Configuration.dumpMaps = true;
								break;
							case "dump_animation_data":
								Configuration.dumpAnimationData = true;
								break;
							case "dump_data_lists":
								Configuration.dumpDataLists = true;
								break;
							default:
								throw new IllegalArgumentException("Run argument not recognized: " + args[index]);
						}
					}
				}

				// Build the client title with configuration arguments
				configurationBuilder.append("]");
				windowTitleBuilder.append(" ");
				windowTitleBuilder.append(configurationBuilder.toString().trim());
			}


			Configuration.clientTitle = windowTitleBuilder.toString();
			nodeID = 1;
			portOff = 0;
			setHighMem();
			isMembers = true;

			instance = new Client();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return Client.instance;
	}

	public void createProjectiles() {
		for (Projectile projectile = (Projectile) projectiles
				.first(); projectile != null; projectile = (Projectile) projectiles.next())
			if (projectile.plane != plane || loopCycle > projectile.cycleEnd)
				projectile.remove();
			else if (loopCycle >= projectile.cycleStart) {
				if (projectile.targetIndex > 0) {
					Npc npc = npcs[projectile.targetIndex - 1];
					if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0
							&& npc.y < 13312)
						projectile.setDestination(npc.x, npc.y,
								getCenterHeight(projectile.plane, npc.y, npc.x) - projectile.endHeight, loopCycle);
				}
				if (projectile.targetIndex < 0) {
					int index = -projectile.targetIndex;
					Player player;
					if (index == localPlayerIndex)
						player = localPlayer;
					else
						player = players[index];
					if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0
							&& player.y < 13312)
						projectile.setDestination(player.x, player.y,
								getCenterHeight(projectile.plane, player.y, player.x) - projectile.endHeight, loopCycle);
				}
				projectile.travel(tickDelta);

				scene.add_entity(plane, projectile.yaw, (int) projectile.z, -1, (int) projectile.y,
						60, (int) projectile.x, projectile, false);
			}
	}


	public static String capitalize(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
							s.substring(i + 2));
				}
			}
		}
		return s;
	}


	public void calcFlamesPosition() {
		char c = '\u0100';
		for (int j = 10; j < 117; j++) {
			int k = (int) (Math.random() * 100D);
			if (k < 50)
				anIntArray828[j + (c - 2 << 7)] = 255;
		}
		for (int l = 0; l < 100; l++) {
			int i1 = (int) (Math.random() * 124D) + 2;
			int k1 = (int) (Math.random() * 128D) + 128;
			int k2 = i1 + (k1 << 7);
			anIntArray828[k2] = 192;
		}

		for (int j1 = 1; j1 < c - 1; j1++) {
			for (int l1 = 1; l1 < 127; l1++) {
				int l2 = l1 + (j1 << 7);
				anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128]
						+ anIntArray828[l2 + 128]) / 4;
			}

		}

		anInt1275 += 128;
		if (anInt1275 > anIntArray1190.length) {
			anInt1275 -= anIntArray1190.length;
			int i2 = (int) (Math.random() * 12D);
			randomizeBackground(aBackgroundArray1152s[i2]);
		}
		for (int j2 = 1; j2 < c - 1; j2++) {
			for (int i3 = 1; i3 < 127; i3++) {
				int k3 = i3 + (j2 << 7);
				int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
				if (i4 < 0)
					i4 = 0;
				anIntArray828[k3] = i4;
			}

		}

		System.arraycopy(anIntArray969, 1, anIntArray969, 0, c - 1);

		anIntArray969[c - 1] = (int) (Math.sin(loopCycle / 14D) * 16D + Math.sin(loopCycle / 15D) * 14D
				+ Math.sin(loopCycle / 16D) * 12D);
		if (anInt1040 > 0)
			anInt1040 -= 4;
		if (anInt1041 > 0)
			anInt1041 -= 4;
		if (anInt1040 == 0 && anInt1041 == 0) {
			int l3 = (int) (Math.random() * 2000D);
			if (l3 == 0)
				anInt1040 = 1024;
			if (l3 == 1)
				anInt1041 = 1024;
		}
	}

	public void resetAnimation(int interfaceId) {
		// Guard: cache array missing or id out of range
		if (RSInterface.interfaceCache == null
				|| interfaceId < 0
				|| interfaceId >= RSInterface.interfaceCache.length) {
			return;
		}

		RSInterface parent = RSInterface.interfaceCache[interfaceId];

		// Guard: parent missing
		if (parent == null) {
			return;
		}

		// Guard: no children defined on this interface
		if (parent.children == null || parent.children.length == 0) {
			return;
		}

		for (int j = 0; j < parent.children.length; j++) {
			int childId = parent.children[j];

			// Your original logic: -1 means stop
			if (childId == -1) {
				break;
			}

			// Guard: invalid child id
			if (childId < 0 || childId >= RSInterface.interfaceCache.length) {
				System.err.println("Invalid childId " + childId + " at index " + j + " inside interface " + interfaceId);
				continue;
			}

			RSInterface child = RSInterface.interfaceCache[childId];

			// Guard: child missing (and don't keep going with it)
			if (child == null) {
				System.err.println("Null child of index " + j + " inside interface " + interfaceId + " (childId=" + childId + ")");
				continue;
			}

			// Recurse if it's a container
			if (child.type == 1) {
				resetAnimation(child.id);
			}

			child.currentFrame = 0;
			child.anInt208 = 0;
		}
	}


	private GameTimer gameTimer;

	private void mainGameProcessor() {
		callbacks.tick();
		callbacks.post(ClientTick.INSTANCE);

		timeOutHasLoggedMessages();

		if (gameTimer != null) {
			if (gameTimer.isCompleted()) {
				gameTimer.stop();
			}
		}


		spin();
		if (anInt1104 > 1)
			anInt1104--;
		if (anInt1011 > 0)
			anInt1011--;

		int packetsHandled = 0;
		while (parsePacket()) {
			if (packetsHandled++ >= 10) {
				break;
			}
		}

		if (loggedIn) {
			callbacks.post(GameTick.INSTANCE);
		}

		if (!loggedIn)
			return;

		if (anInt1016 > 0)
			anInt1016--;
		//if (KeyHandler.keyArray[1] == 1 || KeyHandler.keyArray[2] == 1 || KeyHandler.keyArray[3] == 1 || KeyHandler.keyArray[4] == 1)
			//aBoolean1017 = true;
		if (aBoolean1017 && anInt1016 <= 0) {
			anInt1016 = 20;
			aBoolean1017 = false;
			stream.createFrame(86);
			stream.writeWord(camAngleX);
			stream.method432(viewRotation);
		}
		if (super.canvas.hasFocus() && !aBoolean954) {
			aBoolean954 = true;
			stream.createFrame(3);
			stream.writeUnsignedByte(1);
		}
		if (!super.canvas.hasFocus() && aBoolean954) {
			aBoolean954 = false;
			stream.createFrame(3);
			stream.writeUnsignedByte(0);
		}
		if (gameState != 30) {
			return;
		}

		processPendingSpawns();
		// method90();
		anInt1009++;
		if (anInt1009 > 750) {
			logger.debug("Dropping client due to packets not being received.");
			dropClient();
		}
		method114();
		method95();
		processMobChatText();
		tickDelta++;
		StaticSound.method4532();
		if (crossType != 0) {
			crossIndex += 20;
			if (crossIndex >= 400)
				crossType = 0;
		}
		if (atInventoryInterfaceType != 0) {
			atInventoryLoopCycle++;
			if (atInventoryLoopCycle >= 15) {
				if (atInventoryInterfaceType == 2)
					needDrawTabArea = true;
				if (atInventoryInterfaceType == 3)
					inputTaken = true;
				atInventoryInterfaceType = 0;
			}
		}
		if (activeInterfaceType != 0) {
			anInt989++;
			if (MouseHandler.mouseX > anInt1087 + 5 || MouseHandler.mouseX < anInt1087 - 5 || MouseHandler.mouseY > anInt1088 + 5
					|| MouseHandler.mouseY < anInt1088 - 5)
				aBoolean1242 = true;
			if (MouseHandler.instance.clickMode2 == 0) {
				if (activeInterfaceType == 2)
					needDrawTabArea = true;
				if (activeInterfaceType == 3)
					inputTaken = true;
				activeInterfaceType = 0;
				if (aBoolean1242 && anInt989 >= getDragSetting(draggingItemInterfaceId)) {
					lastActiveInvInterface = -1;
					processRightClick();

					// Bank interface adding items to another tab via dragging on the tab icons at the top
					if (Bank.isBankContainer(RSInterface.interfaceCache[draggingItemInterfaceId]) || draggingItemInterfaceId == Bank.SEARCH_CONTAINER) {
						//System.out.println("BANK TAB SLOT " + mouseX + ", " + mouseY);
						Point southWest, northEast;



						int xOffset = !isResized() ? 0 : (canvasWidth / 2) - 356;
						int yOffset = !isResized() ? 0: (canvasHeight / 2) - 230;

						southWest = new Point(68 + xOffset, 75 + yOffset);
						northEast = new Point(457 + xOffset, 41 + yOffset);
						int[] slots = new int[9];
						for (int i = 0; i < slots.length; i++)
							slots[i] = (41 * i) + (int) southWest.getX();
						for (int i = 0; i < slots.length; i++) {
							if (MouseHandler.mouseX >= slots[i] && MouseHandler.mouseX <= slots[i] + 42
									&& MouseHandler.mouseY >= northEast.getY() && MouseHandler.mouseY <= southWest.getY()) {

								if (draggingItemInterfaceId != Bank.SEARCH_CONTAINER) {
									RSInterface rsi = RSInterface.interfaceCache[58050 + i];
									if (rsi.isMouseoverTriggered) {
										continue;
									}
								}

								// Update client side to hide latency
								if (Bank.getCurrentBankTab() == 0) {
									OptionalInt fromTabOptional = Arrays.stream(Bank.ITEM_CONTAINERS).filter(id -> draggingItemInterfaceId == id).findFirst();
									if (fromTabOptional.isPresent()) {
										RSInterface fromTab = RSInterface.interfaceCache[fromTabOptional.getAsInt()];
										RSInterface toTab = RSInterface.interfaceCache[Bank.ITEM_CONTAINERS[i]];
										if (toTab.getInventoryContainerFreeSlots() > 0 && fromTab.id != toTab.id) {
											RSInterface.insertInventoryItem(fromTab, itemDraggingSlot, toTab);
										}
									}
								}

								if (draggingItemInterfaceId != Bank.SEARCH_CONTAINER) {
									stream.createFrame(214);
									stream.method433(draggingItemInterfaceId);
									stream.method424(0);
									stream.method433(itemDraggingSlot);
									stream.method431(1000 + i);
								} else {
									stream.createFrame(243);
									stream.writeWord(i);
									stream.writeWord(RSInterface.get(draggingItemInterfaceId).inventoryItemId[itemDraggingSlot]);
								}
								return;
							}
						}
					}

					try {
						RSInterface class9 = RSInterface.interfaceCache[draggingItemInterfaceId];
						if (class9 != null) {
							if (mouseInvInterfaceIndex != itemDraggingSlot && lastActiveInvInterface == draggingItemInterfaceId) {
								// Dragging item inside the same container

								int insertMode = 0;
								if (anInt913 == 1 && class9.contentType == 206)
									insertMode = 1;
								if (class9.inventoryItemId[mouseInvInterfaceIndex] <= 0)
									insertMode = 0;

								if (class9.aBoolean235) {
									// Move to empty slot?
									int l2 = itemDraggingSlot;
									int l3 = mouseInvInterfaceIndex;
									class9.inventoryItemId[l3] = class9.inventoryItemId[l2];
									class9.inventoryAmounts[l3] = class9.inventoryAmounts[l2];
									class9.inventoryItemId[l2] = -1;
									class9.inventoryAmounts[l2] = 0;
								} else if (insertMode == 1) {
									// Insert
									int i3 = itemDraggingSlot;
									for (int i4 = mouseInvInterfaceIndex; i3 != i4; ) {
										if (i3 > i4) {
											class9.swapInventoryItems(i3, i3 - 1);
											i3--;
										} else if (i3 < i4) {
											class9.swapInventoryItems(i3, i3 + 1);
											i3++;
										}
									}
								} else {
									// Swap
									class9.swapInventoryItems(itemDraggingSlot, mouseInvInterfaceIndex);
								}

								Bank.shiftTabs();
								stream.createFrame(214);
								stream.method433(draggingItemInterfaceId);
								stream.method424(insertMode);
								stream.method433(itemDraggingSlot);
								stream.method431(mouseInvInterfaceIndex);
							} else if (class9.allowInvDraggingToOtherContainers && lastActiveInvInterface != draggingItemInterfaceId) {
								if (lastActiveInvInterface != -1 && draggingItemInterfaceId != -1) {
									RSInterface draggingFrom = RSInterface.interfaceCache[draggingItemInterfaceId];
									RSInterface draggingTo = RSInterface.interfaceCache[lastActiveInvInterface];
									if (draggingTo != null && draggingFrom != null
											&& draggingTo.allowInvDraggingToOtherContainers
											&& draggingFrom.allowInvDraggingToOtherContainers) {
										int fromSlot = itemDraggingSlot;
										int toSlot = mouseInvInterfaceIndex;
										int insertMode = 0;
										if (anInt913 == 1 && class9.contentType == 206)
											insertMode = 1;

										if (insertMode == 1) {
											// insert
											if (draggingTo.getInventoryContainerFreeSlots() > 0) {
												RSInterface.insertInventoryItem(draggingFrom, fromSlot, draggingTo, toSlot);
											} else {
												return;
											}
										} else {
											//swap
											RSInterface.swapInventoryItems(draggingFrom, fromSlot, draggingTo, toSlot);
										}

										stream.createFrame(242);
										stream.writeWord(draggingTo.id);
										stream.writeWord(draggingFrom.id);
										stream.method424(insertMode);
										stream.writeWord(fromSlot);
										stream.writeWord(toSlot);
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ((anInt1253 == 1 || menuHasAddFriend(menuOptionsCount - 1)) && menuOptionsCount > 2)
					openMenu(MouseHandler.saveClickX, MouseHandler.saveClickY);
				else if (menuOptionsCount > 0)
					menuHandler.processMenuActions(menuOptionsCount - 1);
				atInventoryLoopCycle = 10;
				clickMode3 = 0;
			}
		}
		if (SceneGraph.clickedTileX != -1) {
			if (fullscreenInterfaceID != -1) {
				return;
			}
			int k = SceneGraph.clickedTileX;
			int k1 = SceneGraph.clickedTileY;
			boolean flag = false;
			if (localPlayer.isAdminRights() && controlIsDown) {
				teleport(baseX + k, baseY + k1);
			} else {
				flag = doWalkTo(0, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, k1, true, k);
			}
			SceneGraph.clickedTileX = -1;
			if (flag) {
				crossX = MouseHandler.saveClickX;
				crossY = MouseHandler.saveClickY;
				crossType = 1;
				crossIndex = 0;
			}
		}
		// Process GE search item clicks before other dialog handling
		processGESearchClick();

		if (clickMode3 == 1 && clickToContinueString != null) {
			clickToContinueString = null;
			inputTaken = true;
			clickMode3 = 0;
		}

		menu();
		if (MouseHandler.instance.clickMode2 == 1 || clickMode3 == 1)
			mouseClickCount++;
		if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
			if (anInt1501 < 50 && !isMenuOpen) {
				anInt1501++;
				if (anInt1501 == 50) {
					if (anInt1500 != 0) {
						inputTaken = true;
					}
					if (anInt1044 != 0) {
						needDrawTabArea = true;
					}
				}
			}
		} else if (anInt1501 > 0) {
			anInt1501--;
		}

		if (gameState == GameState.LOGGED_IN.getState()) {
			Camera.updateCamera();
			if (oculusOrbState != 1 || keyManager.lastTypedCharacter <= 0) {
				manageTextInputs();
			}

		}

		++MouseHandler.idleCycles;
		++KeyHandler.idleCycles;

		if (MouseHandler.idleCycles++ > 9000) {
			anInt1011 = 250;
			MouseHandler.idleCycles = 0;
			stream.createFrame(202);
		}

		anInt1010++;
		if (anInt1010 > 50)
			stream.createFrame(0);

		try {
			if (socketStream != null && stream.currentPosition > 0) {
				socketStream.queueBytes(stream.currentPosition, stream.payload);
				stream.currentPosition = 0;
				anInt1010 = 0;
			}
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			resetLogout();
			if (Configuration.developerMode) {
				exception.printStackTrace();
			}
		}
	}

	public void method63() {
		SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst();
		for (; spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext())
			if (spawnedObject.getLongetivity == -1) {
				spawnedObject.delay = 0;
				handleTemporaryObjects(spawnedObject);
			} else {
				spawnedObject.unlink();
			}

	}
	/**
	 * Runescape Loading Bar
	 *
	 * @param percentage
	 * @param s
	 * @param downloadSpeed
	 * @param secondsRemaining
	 * @trees
	 */
	public Sprite logo, loginBackground;


	public int getPixelAmt(int current, int pixels) {
		return (int) (pixels * .01 * current);
	}

	public void drawLoadingText(int percentage, String s) {
		loadingPercent = percentage;
		loadingText = s;
		targetPercent = loadingPercent;
	}

	public void method65(int width, int height, int mouseX, int mouseY, RSInterface class9, int i1, boolean flag, int j1) {
		interfaceManager.method65(width, height, mouseX, mouseY, class9, i1, flag, j1);
	}

	boolean clickObject(long object, int j, int k) {
		try {
			if (fullscreenInterfaceID != -1) {
				return false;
			}
		
			int objectFlag = scene.getObjectFlags(plane, j, k, object);
			int orientation = ObjectKeyUtil.getObjectOrientation(objectFlag);

			ObjectDefinition class46 = ObjectDefinition.lookup(ObjectKeyUtil.getObjectId(object));
			int i2;
			int j2;
			if (orientation == 0 || orientation == 2) {
				i2 = class46.sizeX;
				j2 = class46.sizeY;
			} else {
				i2 = class46.sizeY;
				j2 = class46.sizeX;
			}
			int k2 = class46.surroundings;
			if (orientation != 0) {
				k2 = (k2 << orientation & 0xf) + (k2 >> 4 - orientation);
			}
			doWalkTo(2, localPlayer.pathX[0], localPlayer.pathY[0], 0, j2, 0, i2, k2, j, false, k);

			crossX = MouseHandler.saveClickX;
			crossY = MouseHandler.saveClickY;
			crossType = 2;
			crossIndex = 0;
			return true;
		} catch (Exception e) {
			System.err.println("Error while walking.");
			e.printStackTrace();
			return false;
		}
	}

	public void sendStringAsLong(String string) {
		stream.createFrame(60);
		stream.writeUnsignedByte(1 + string.length());
		stream.writeString(string);
	}

	public void sendString(int identifier, String text) {
		text = identifier + "," + text;
		stream.createFrame(127);
		stream.writeUnsignedByte(text.length() + 1);
		stream.writeString(text);
	}

	public void dropClient() {
		if (anInt1011 > 0) {
			resetLogout();
			return;
		}
		setGameState(GameState.CONNECTION_LOST);
		logger.debug("Dropping client.");

		Rasterizer2D.fillPixels(2, 229, 39, 0xffffff, 2);
		Rasterizer2D.drawPixels(37, 3, 3, 0, 227);
		frameMode(false);
		minimapState = 0;
		Preferences.save();
		destX = 0;
		RSSocket rsSocket = socketStream;
		loggedIn = false;
		loginFailures = 0;
		//login(myUsername, getPassword(), true);
		if (!loggedIn)
			resetLogout();
		try {
			rsSocket.close();
		} catch (Exception _ex) {
		}
	}

	public static String formatNumber(double number) {
		return NumberFormat.getInstance().format(number);
	}

	public int settings[];

	public int[] intStack = new int[1000];
	public int intStackSize;
	public String[] stringStack = new String[1000];
	public int stringStackSize;

	void updateSettings() {
		settings[809] = Configuration.alwaysLeftClickAttack ? 1 : 0;
		settings[817] = Configuration.escapeCloseInterface ? 1 : 0;
	}

	public void onRealButtonClick(int buttonId) {
		Interfaces.taskInterface.actions.onButtonClick(buttonId);
	}


	// processMenuActions and doAction moved to MenuHandler
	public static boolean removeRoofs = true, leftClickAttack = true, chatEffectsEnabled = true, drawOrbs = true;

	private void setConfigButtonsAtStartup() {
		Preferences.getPreferences().updateClientConfiguration();
		if (settingManager.getMuteAll()) {
			StaticSound.updateMusicVolume(0);
			StaticSound.updateSoundEffectVolume(0);
			StaticSound.updateAreaVolume(0);
			SettingsTabWidget.musicVolumeSlider.setValue(255);
			SettingsTabWidget.soundVolumeSlider.setValue(127);
			SettingsTabWidget.areaSoundVolumeSlider.setValue(127);
		}
		setConfigButton(23101, drawOrbs);
		setConfigButton(23109, splitPrivateChat);
		setConfigButton(23107, chatEffectsEnabled);
		setConfigButton(23103, informationFile.isRememberRoof() ? true : false);
		setConfigButton(23105, leftClickAttack);
		setConfigButton(23111, getUserSettings().isGameTimers());
		setConfigButton(23113, getUserSettings().isShowEntityTarget());
		setConfigButton(23115, informationFile.isRememberVisibleItemNames() ? true : false);
		setConfigButton(23118, shiftDrop ? true : false);
		SettingsTabWidget.updateSettings();
		setConfigButton(23001, true);
		setConfigButton(953, true);

		setDropDown(SettingsInterface.OLD_GAMEFRAME, getUserSettings().isOldGameframe());
		setDropDown(SettingsInterface.GAME_TIMERS, getUserSettings().isGameTimers());
		setDropDown(SettingsInterface.ANTI_ALIASING, getUserSettings().isAntiAliasing());
		setDropDown(SettingsInterface.GROUND_ITEM_NAMES, getUserSettings().isGroundItemOverlay());
		setDropDown(SettingsInterface.FOG, getUserSettings().isFog());
		setDropDown(SettingsInterface.SMOOTH_SHADING, getUserSettings().isSmoothShading());
		setDropDown(SettingsInterface.TILE_BLENDING, getUserSettings().isTileBlending());
		setDropDown(SettingsInterface.BOUNTY_HUNTER, getUserSettings().isBountyHunter());
		setDropDown(SettingsInterface.ENTITY_TARGET, getUserSettings().isShowEntityTarget());
		setDropDown(SettingsInterface.CHAT_EFFECT, getUserSettings().getChatColor());
		setDropDown(SettingsInterface.INVENTORY_MENU, getUserSettings().isInventoryContextMenu() ? 1 : 0);
		setDropDown(SettingsInterface.STRETCHED_MODE, getUserSettings().isStretchedMode());

		setDropDown(SettingsInterface.PM_NOTIFICATION, Preferences.getPreferences().pmNotifications ? 0 : 1);

		/** Sets the brightness level **/
		RSInterface class9_2 = RSInterface.interfaceCache[910];
		if (class9_2.scripts != null && class9_2.scripts[0][0] == 5) {
			int i2 = class9_2.scripts[0][1];
			if (variousSettings[i2] != class9_2.anIntArray212[0]) {
				variousSettings[i2] = class9_2.anIntArray212[0];
				updateVarp(i2);
				needDrawTabArea = true;
			}
		}
	}

	boolean clickConfigButton(int i) {
		switch (i) {

			case 953:
				if (tabInterfaceIDs[11] != 23000) {
					tabInterfaceIDs[11] = 23000;
					setConfigButton(i, true);
					setConfigButton(959, false);
				}
				return true;

			case 959:
				if (tabInterfaceIDs[11] != 23100) {
					tabInterfaceIDs[11] = 23100;
					setConfigButton(i, true);
					setConfigButton(953, false);
				}
				return true;

			case 42502:
				setConfigButton(42502, true);
				setConfigButton(42503, false);
				setConfigButton(42504, false);
				setConfigButton(42505, false);
				break;
			case 42503:
				setConfigButton(42502, false);
				setConfigButton(42503, true);
				setConfigButton(42504, false);
				setConfigButton(42505, false);
				break;

			case 166026:
				//if (isResized()) {
				//	setConfigButton(i, true);
				//	setConfigButton(23003, false);
				//	setConfigButton(23005, false);
				//	setGameMode(ScreenMode.FIXED);
				//}

				return true;

			case 166027:
				//if (currentScreenMode != ScFreenMode.RESIZABLE) {
				//setConfigButton(i, true);
				//setConfigButton(23001, false);
				//setConfigButton(23005, false);
				//setGameMode(ScreenMode.RESIZABLE);
				//}
				pushMessage("Coming real soon",  0, "");
				return false;

			case 23005:
				//if (currentScreenMode != ScreenMode.FULLSCREEN) {
				//	setConfigButton(i, true);
				//	setConfigButton(23001, false);
				//	setConfigButton(23003, false);
				//	setGameMode(ScreenMode.FULLSCREEN);
				//}
				pushMessage("Coming real soon",  0, "");
				return true;

			case 23101:
				setConfigButton(i, drawOrbs = !drawOrbs);
				return true;

			case 23109:
				setConfigButton(i, splitPrivateChat = !splitPrivateChat);
				return true;

			case 23103:
				setConfigButton(i, removeRoofs = !removeRoofs);
				informationFile.setRememberRoof(informationFile.isRememberRoof() ? false : true);
				try {
					informationFile.write();
					System.out.println("Written to: " + informationFile.isRememberRoof());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

			case 23105:
				setConfigButton(i, leftClickAttack = !leftClickAttack);
				return true;

			case 23107:
				setConfigButton(i, chatEffectsEnabled = !chatEffectsEnabled);
				return true;

			case 23111:
				//setConfigButton(i, getUserSettings().setGameTimers(!getUserSettings().isGameTimers());
				return true;

			case 23113:
				//setConfigButton(i, showEntityTarget = !showEntityTarget);
				return true;

			case 23118:
				setConfigButton(i, shiftDrop = !shiftDrop);
				return true;

			case 23115:
				//setConfigButton(i, groundItemsOn = !groundItemsOn);
				informationFile.setRememberRoof(informationFile.isRememberVisibleItemNames() ? false : true);
				try {
					informationFile.write();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

		}

		return false;
	}

	private void setDropDown(Setting setting, Object value) {
		RSInterface widget = RSInterface.interfaceCache[setting.getInterfaceId()];
		if (widget == null) {
			throw new NullPointerException("Widget is null: " + setting.getInterfaceId());
		}
		if (widget.dropdown == null) {
			throw new NullPointerException("Widget dropdown is null: " + setting.getInterfaceId());
		}
		if (widget.dropdown.getOptions() == null) {
			throw new NullPointerException("Widget dropdown options is null: " + setting.getInterfaceId());
		}
		if (value instanceof Boolean) {
			boolean choice = (Boolean) value;
			widget.dropdown.setSelected(widget.dropdown.getOptions()[choice ? 0 : 1]);
		} else if (value instanceof Integer) {
			int choice = (int) value;
			widget.dropdown.setSelected(widget.dropdown.getOptions()[choice]);
		}
	}

	void setConfigButton(int interfaceFrame, boolean configSetting) {
		int config = configSetting ? 1 : 0;
		anIntArray1045[interfaceFrame] = config;
		if (variousSettings[interfaceFrame] != config) {
			variousSettings[interfaceFrame] = config;
			needDrawTabArea = true;
			if (dialogID != -1)
				inputTaken = true;
		}
	}

	public void method70() {
		anInt1251 = 0;
		int j = (localPlayer.x >> 7) + baseX;
		int k = (localPlayer.y >> 7) + baseY;
		if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136)
			anInt1251 = 1;
		if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535)
			anInt1251 = 1;
		if (anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062)
			anInt1251 = 0;
	}

	@Override
	public void run() {
		if (drawFlames) {
			drawFlames();
		} else {
			try {
				super.run();
			} catch (OutOfMemoryError e) {
				//ClientWindow.popupMessage("An error has occurred that caused the game to crash.",
						//"Contact us in the Discord #support channel for help.");
				Misc.dumpHeap(true);
				e.printStackTrace();
				throw e;
			}
		}
	}

	public void insertMenuItemNoShift(String action, String target, int opcode, int identifier, int arg1, int arg2) {
		menuHandler.insertMenuItemNoShift(action, target, opcode, identifier, arg1, arg2);
	}

	public void insertMenuItemNoShift(String action, int opcode, int identifier, int arg1, int arg2) {
		menuHandler.insertMenuItemNoShift(action, opcode, identifier, arg1, arg2);
	}

	public void insertMenuItemNoShift(String action, MenuAction opcode, int identifier, int arg1, int arg2) {
		menuHandler.insertMenuItemNoShift(action, opcode, identifier, arg1, arg2);
	}

	public void insertMenuItemNoShift(String action, int opcode, int identifier, int arg1) {
		menuHandler.insertMenuItemNoShift(action, opcode, identifier, arg1);
	}

	public void insertMenuItemNoShift(String action, int opcode, int identifier) {
		menuHandler.insertMenuItemNoShift(action, opcode, identifier);
	}

	public void insertMenuItemNoShift(String action, MenuAction opcode) {
		menuHandler.insertMenuItemNoShift(action, opcode);
	}

	public void insertMenuItemNoShift(String action, int opcode) {
		menuHandler.insertMenuItemNoShift(action, opcode);
	}

	public void insertMenuItemNoShift(String action) {
		menuHandler.insertMenuItemNoShift(action);
	}

	public final void insertMenuItem(String action,
									 String target,
									 int opcode,
									 int identifier,
									 int arg1,
									 int arg2,
									 int unknown,
									 boolean shiftClick) {
		menuHandler.insertMenuItem(action, target, opcode, identifier, arg1, arg2, unknown, shiftClick);
	}

	public void createMenu() { menuHandler.createMenu(); }
	@Override
	public void cleanUpForQuit() {
		if (StaticSound.pcmPlayer0 != null) {
			StaticSound.pcmPlayer0.shutdown();
		}
		if (StaticSound.pcmPlayer1 != null) {
			StaticSound.pcmPlayer1.shutdown();
		}
		Signlink.reporterror = false;
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		ArchiveDiskActionHandler.waitForPendingArchiveDiskActions();
		try {
			Signlink.cacheData.close();

			for (int var3 = 0; var3 < Signlink.archiveCount; ++var3) {
				Signlink.cacheIndexes[var3].close();
			}

			Signlink.cacheMasterIndex.close();
			Signlink.uid.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		socketStream = null;

		mapBack = null;
		cacheSprite = null;
		cacheSprite1 = null;
		cacheSprite2 = null;
		cacheSprite3 = null;

		cacheInterface = null;
		mouseDetection = null;

		mapIcon7 = null;
		mapIcon8 = null;
		mapIcon6 = null;
		mapIcon5 = null;
		mapIcon9 = null;

		aStream_834 = null;

		stream = null;
		aStream_847 = null;
		inStream = null;
		regions = null;
		regionLandArchives = null;
		regionMapArchives = null;
		regionLandIds = null;
		regionLocIds = null;
		tileHeights = null;
		tileFlags = null;
		scene = null;
		collisionMaps = null;
		anIntArrayArray901 = null;
		travelDistances = null;
		bigX = null;
		bigY = null;
		aByteArray912 = null;

		tabAreaFixed = null;
		tabAreaResizable = null;
		compassImage = null;
		redStones = null;
		sideIcons = null;
		mapArea = null;

		/* Null pointers for custom sprites */
		multiOverlay = null;
		chatArea = null;
		chatButtons = null;
		mapArea = null;
		channelButtons = null;
		venomOrb = null;
		/**/

		hitMarks = null;
		headIcons = null;
		skullIcons = null;
		headIconsHint = null;
		mapDotItem = null;
		mapDotNPC = null;
		mapDotPlayer = null;
		mapDotFriend = null;
		mapFunctions = null;
		tileCycleMap = null;
		mapSceneSprites = null;
		players = null;
		playerIndices = null;
		anIntArray894 = null;
		aStreamArray895s = null;
		anIntArray840 = null;
		npcs = null;
		npcIndices = null;
		groundItems = null;
		spawns = null;
		projectiles = null;
		incompleteAnimables = null;
		menuArguments1 = null;
		menuArguments2 = null;
		menuOpcodes = null;
		menuIdentifiers = null;
		menuTargets = null;
		variousSettings = null;
		minimapHintX = null;
		minimapHintY = null;
		minimapHint = null;
		minimapImage = null;
		friendsList = null;
		friendsListAsLongs = null;
		friendsNodeIDs = null;

		nullLoader();
		ObjectDefinition.nullLoader();
		NpcDefinition.nullLoader();
		ItemDefinition.clear();
		FloorUnderlayDefinition.floorCache.clear();
		FloorOverlayDefinition.floorCache.clear();
		VariablePlayer.clear();
		((TextureProvider) Rasterizer3D.clips.Rasterizer3D_textureLoader).clear();
		Js5List.models.clearFiles();
		Js5List.soundEffects.clearFiles();
		Js5List.textures.clearFiles();
		Js5List.musicTracks.clearFiles();
		Js5List.musicJingles.clearFiles();
		Js5List.sprites.clearFiles();
		Js5List.binary.clearFiles();
		Js5List.dbtableindex.clearFiles();
		Js5List.animations.clearFiles();
		Js5List.skeletons.clearFiles();
		Js5List.clientScript.clearFiles();
		Js5List.maps.clearFiles();
		IdentityKit.cached.clear();
		RSInterface.interfaceCache = null;
		DummyClass.cache = null;
		jagexNetThread.closeSocketStream();
		VariablePlayer.cached.clear();
		Player.mruNodes = null;
		System.gc();
	}

	public void printDebug() {
		System.out.println("============");
		System.out.println("flame-cycle:" + anInt1208);

		System.out.println("loop-cycle:" + loopCycle);
		System.out.println("draw-cycle:" + anInt1061);
		System.out.println("ptype:" + incomingPacket);
		System.out.println("psize:" + packetSize);
		if (socketStream != null)
			socketStream.printDebug();
		//MouseHandler.shouldDebug = true;
	}

	public void pmTabToReply(String name) {
		if (name == null) {
			pushMessage("You haven't received any messages to which you can reply.", 0, "");
			return;
		}
		for(int crown = 0; crown < 50; crown++) {
			String crownString = "@cr" + crown + "@";
			if(name.contains(crownString)) {
				name = name.replaceAll(crownString, "");
			}
		}

		long nameAsLong = StringUtils.longForName(name.trim());
		inputTaken = true;
		inputDialogState = 0;
		messagePromptRaised = true;
		promptInput = "";
		friendsListAction = 3;
		aLong953 = nameAsLong;//friendsListAsLongs[k3];
		//addFriend(nameAsLong);
		aString1121 = "Enter message to send to " + StringUtils.fixName(name.trim());//friendsList[k3];
	}

	public void pmTabToReply() {
		String name = null;
		for (int k = 0; k < 100; k++) {
			if (chatMessages[k] == null) {
				continue;
			}
			int l = chatTypes[k];
			if (l == 3 || l == 7) {
				name = chatNames[k];
				break;
			}
		}
		pmTabToReply(name);
	}

	private void manageTextInputs() {
		do {
			while (true) {
				label1776:
				do {
					while (true) {
						while (keyManager.hasNextKey()) {
							int j = keyManager.lastTypedCharacter;
							if (j == -1 || j == 96)
								break;
							if(devConsole.console_open) {
								devConsole.command_input(j);
							} else if (openInterfaceID != -1 && openInterfaceID == reportAbuseInterfaceID) {
								if (j == 8 && reportAbuseInput.length() > 0)
									reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
								if ((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32)
										&& reportAbuseInput.length() < 12)
									reportAbuseInput += (char) j;
							} else if (messagePromptRaised) {
							if (j >= 32 && j <= 122 && promptInput.length() < DEFAULT_CHAT_INPUT_LENGTH) {
									promptInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && promptInput.length() > 0) {
									promptInput = promptInput.substring(0, promptInput.length() - 1);
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									messagePromptRaised = false;
									inputTaken = true;
									if (friendsListAction == 1) {
										long l = StringUtils.longForName(promptInput);
										addFriend(l);
									}
									if (friendsListAction == 2 && friendsCount > 0) {
										long l1 = StringUtils.longForName(promptInput);
										delFriend(l1);
									}

									if (friendsListAction == 557 && promptInput.length() > 0) {//money pouch
										inputString = "::withdrawmp " + promptInput;
										sendPacket(103);
									}


									if (friendsListAction == 3 && promptInput.length() > 0) {
										stream.createFrame(126);
										stream.writeUnsignedByte(0);
										int k = stream.currentPosition;
										stream.writeQWord(aLong953);
										TextInput.method526(promptInput, stream);
										stream.writeBytes(stream.currentPosition - k);
										promptInput = TextInput.processText(promptInput);
										// promptInput = Censor.doCensor(promptInput);
										pushMessage(promptInput, 6, StringUtils.fixName(StringUtils.nameForLong(aLong953)));
										if (privateChatMode == 2) {
											privateChatMode = 1;
											stream.createFrame(95);
											stream.writeUnsignedByte(publicChatMode);
											stream.writeUnsignedByte(privateChatMode);
											stream.writeUnsignedByte(tradeMode);
										}
									}
									if (friendsListAction == 4 && ignoreCount < 100) {
										long l2 = StringUtils.longForName(promptInput);
										addIgnore(l2);
									}
									if (friendsListAction == 5 && ignoreCount > 0) {
										long l3 = StringUtils.longForName(promptInput);
										delIgnore(l3);
									}
									// clan chat
									if (friendsListAction == 6) {
										sendStringAsLong(promptInput);
									} else if (friendsListAction == 8) {
										sendString(1, promptInput);
									} else if (friendsListAction == 9) {
										sendString(2, promptInput);
									} else if (friendsListAction == 10) {
										sendString(3, promptInput);
									} else if (friendsListAction == 12) {
										sendString(5, promptInput);
									} else if (friendsListAction == 13) {
										sendString(6, promptInput);
									} else if (friendsListAction == 14) {//search wiki
										sendString(7, promptInput);
									} else if (friendsListAction == 15) {//player profile
										sendStringAsLong(promptInput);
									} else if (friendsListAction == 16) {//staff tab
										sendStringAsLong(promptInput);
									}

								}
							} else if (inputDialogState == 1) {
								if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
										&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && amountOrNameInput.length() > 0) {
									amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									if (amountOrNameInput.length() > 0) {
										if (amountOrNameInput.toLowerCase().contains("k")) {
											amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
										} else if (amountOrNameInput.toLowerCase().contains("m")) {
											amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
										} else if (amountOrNameInput.toLowerCase().contains("b")) {
											amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
										}
										int i1 = 0;
										try {
											i1 = Integer.parseInt(amountOrNameInput);
										} catch (Exception _ex) {
										}
										stream.createFrame(208);
										stream.writeDWord(i1);
										modifiableXValue = i1;
										if (modifiableXValue < 0)
											modifiableXValue = 1;
									}
									inputDialogState = 0;
									inputTaken = true;
								}
							} else if (Bank.isSearchingBank()) {
								if (j >= 32 && j <= 122 && Bank.searchingBankString.length() < 58) {
									Bank.searchingBankString += (char) j;
									Bank.searchDebug("typed '" + (char) j + "' -> text='" + Bank.searchingBankString + "'");
									inputTaken = true;
								}
								if (j == 8 && Bank.searchingBankString.length() > 0) {
									Bank.searchingBankString = Bank.searchingBankString.substring(0, Bank.searchingBankString.length() - 1);
									Bank.searchDebug("backspace -> text='" + Bank.searchingBankString + "'");
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									Bank.searchDebug("enter pressed -> closing search text='" + Bank.searchingBankString + "'");
									inputDialogState = 0;
									inputTaken = true;
								}
							} else if (inputDialogState == 2 || inputDialogState == 10) {
								if (j >= 32 && j <= 122 && amountOrNameInput.length() < (inputDialogState == 10 ? 128 : 58)) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && amountOrNameInput.length() > 0) {
									amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									if (amountOrNameInput.length() > 0) {
										stream.createFrame(60);
										stream.writeUnsignedByte(amountOrNameInput.length() + 1);
										stream.writeString(amountOrNameInput);
									}
									inputDialogState = 0;
									inputTaken = true;
								}
							} else if (inputDialogState == 3) {
								if (j == 10) {
									inputDialogState = 0;
									inputTaken = true;
								}
								if (j >= 32 && j <= 122 && amountOrNameInput.length() < 40) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && amountOrNameInput.length() > 0) {
									amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
									inputTaken = true;
								}
							} else if (inputDialogState == 7) {
								if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
										&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && amountOrNameInput.length() > 0) {
									amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									if (amountOrNameInput.length() > 0) {
										if (amountOrNameInput.toLowerCase().contains("k")) {
											amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
										} else if (amountOrNameInput.toLowerCase().contains("m")) {
											amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
										} else if (amountOrNameInput.toLowerCase().contains("b")) {
											amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
										}
										try {
											int amount = Integer.parseInt(amountOrNameInput);
											stream.createFrame(208);
											stream.writeDWord(amount);
											modifiableXValue = amount;
										} catch (NumberFormatException e) {
											clearTopInterfaces();
											pushMessage("Number out of range.");
										}
									}
									inputDialogState = 0;
									inputTaken = true;
								}
							} else if (inputDialogState == 8) {
								if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m")
										&& !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
									amountOrNameInput += (char) j;
									inputTaken = true;
								}
								if (j == 8 && amountOrNameInput.length() > 0) {
									amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
									inputTaken = true;
								}
								if (j == 13 || j == 10) {
									if (amountOrNameInput.length() > 0) {
										if (amountOrNameInput.toLowerCase().contains("k")) {
											amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
										} else if (amountOrNameInput.toLowerCase().contains("m")) {
											amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
										} else if (amountOrNameInput.toLowerCase().contains("b")) {
											amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
										}
										int amount = 0;
										amount = Integer.parseInt(amountOrNameInput);
										stream.createFrame(208);
										stream.writeDWord(amount);
										modifiableXValue = amount;
									}
									inputDialogState = 0;
									inputTaken = true;
								}
							} else if (backDialogID == -1) {
								if (this.isFieldInFocus()) {
									RSInterface rsi = this.getInputFieldFocusOwner();
									if (rsi == null) {
										return;
									}
									if (j >= 32 && j <= 122 && rsi.message.length() < rsi.characterLimit) {
										if (rsi.inputRegex.length() > 0) {
											pattern = Pattern.compile(rsi.inputRegex);
											matcher = pattern.matcher(Character.toString(((char) j)));
											if (matcher.matches()) {
												rsi.message += (char) j;
												inputTaken = true;
											}
										} else {
											rsi.message += (char) j;
											inputTaken = true;
										}
									}
									if (j == 8 && rsi.message.length() > 0) {
										rsi.message = rsi.message.substring(0, rsi.message.length() - 1);
										if (rsi.inputFieldListener != null)
											rsi.inputFieldListener.accept(rsi.message);
										inputTaken = true;
									}
									if (rsi.isItemSearchComponent && rsi.message.length() > 2
											&& rsi.defaultInputFieldText.equals("Name")) {
										RSInterface subcomponent = RSInterface.interfaceCache[rsi.id + 2];
										RSInterface scroll = RSInterface.interfaceCache[rsi.id + 4];
										RSInterface toggle = RSInterface.interfaceCache[rsi.id + 9];
										scroll.itemSearchSelectedId = 0;
										scroll.itemSearchSelectedSlot = -1;
										RSInterface.selectedItemInterfaceId = 0;
										rsi.itemSearchSelectedSlot = -1;
										rsi.itemSearchSelectedId = 0;
										if (subcomponent != null && scroll != null && toggle != null
												&& toggle.scripts != null) {
											ItemSearch itemSearch = new ItemSearch(rsi.message.toLowerCase(), 60, false);
											int[] results = itemSearch.getItemSearchResults();
											if (subcomponent != null) {
												int position = 0;
												int length = subcomponent.inventoryItemId.length;
												subcomponent.inventoryItemId = new int[length];
												subcomponent.inventoryAmounts = new int[subcomponent.inventoryItemId.length];
												for (int result : results) {
													if (result > 0) {
														subcomponent.inventoryItemId[position] = result + 1;
														subcomponent.inventoryAmounts[position] = 1;
														position++;
													}
												}
											}
										}
									} else if (rsi.updatesEveryInput && rsi.message.length() > 0 && j != 10 && j != 13) {
										SpawnContainer.get().update(rsi.message);
										if (rsi.inputFieldListener != null)
											rsi.inputFieldListener.accept(rsi.message);
										inputString = "";
										promptInput = "";
										if (rsi.inputFieldSendPacket) {
											stream.createFrame(142);
											stream.writeUnsignedByte(4 + rsi.message.length() + 1);
											stream.writeDWord(rsi.id);
											stream.writeString(rsi.message);
											return;
										}
									} else if ((j == 10 || j == 13) && rsi.message.length() > 0 && !rsi.updatesEveryInput) {
										inputString = "";
										promptInput = "";
										if (rsi.inputFieldListener != null)
											rsi.inputFieldListener.accept(rsi.message);
										inputString = "";
										if (rsi.inputFieldSendPacket) {
											stream.createFrame(142);
											stream.writeUnsignedByte(4 + rsi.message.length() + 1);
											stream.writeDWord(rsi.id);
											stream.writeString(rsi.message);
											return;
										}
									}
								} else {
									int maxInputLength = getChatInputLimit(inputString);
									if (j >= 32 && j <= 122 && inputString.length() < maxInputLength) {
										inputString += (char) j;
										inputTaken = true;
									}
									if (j == 8 && inputString.length() > 0) {
										inputString = inputString.substring(0, inputString.length() - 1);
										inputTaken = true;
									}
									if (j == 9)
										pmTabToReply();

									if ((j == 13 || j == 10) && inputString.length() > 0) {
										if (inputString.startsWith("::")) {
											inputString = inputString.toLowerCase();
										}

										if (inputString.startsWith("::starttimer")) {
											try {
												String[] input = inputString.split(" ");
												int id = Integer.parseInt(input[1]);
												int duration = Integer.parseInt(input[2]);
												GameTimerHandler.getSingleton().startGameTimer(id, TimeUnit.SECONDS, duration, 0);
											} catch (Exception exception) {
												exception.printStackTrace();
											}
										}


										if (inputString.startsWith("::graphics")) {
											String graphics;
											try {
												graphics = inputString.split(" ")[1];
												if (graphics.equalsIgnoreCase("on")) {
													getUserSettings().setSmoothShading(true);
													getUserSettings().setTileBlending(true);
													getUserSettings().setAntiAliasing(true);
												} else if (graphics.equalsIgnoreCase("off")) {
													getUserSettings().setSmoothShading(false);
													getUserSettings().setTileBlending(false);
													getUserSettings().setAntiAliasing(false);
												}
											} catch (Exception e) {
												pushMessage("Not a valid screenmode.", 0, "");
											}
										}


										if (inputString.equals("::317")) {
											if (getUserSettings().isOldGameframe() == false) {
												getUserSettings().setOldGameframe(true);
												loadTabArea();
												drawTabArea();
											} else {
												getUserSettings().setOldGameframe(false);
												loadTabArea();
												drawTabArea();
											}
										}
										if (inputString.equals("::togglecounter"))
											drawExperienceCounter = !drawExperienceCounter;

										if (inputString.equals("::resetcounter") && (j == 13 || j == 10)) {
											stream.createFrame(185);
											stream.writeWord(-1);
											experienceCounter = 0L;
										}

										if (inputString.equals("::snow") && (j == 13 || j == 10) && Configuration.CHRISTMAS) {
											snowVisible = snowVisible ? false : true;
											pushMessage("The snow has been set to " + (snowVisible ? "visible" : "invisible") + ".", 0,
													"");
											loadRegion();
										}

										if (inputString.startsWith("::npcanim")) {
											int id = 0;
											try {
												id = Integer.parseInt(inputString.split(" ")[1]);
												NpcDefinition entity = NpcDefinition.get(id);
												if (entity == null) {
													pushMessage("Entity does not exist.", 0, "");
												} else {
													pushMessage("Id: " + id, 0, "");
													pushMessage("Name: " + entity.name, 0, "");
													pushMessage("Stand: " + entity.standingAnimation, 0, "");
													pushMessage("Walk: " + entity.walkingAnimation, 0, "");
												}
											} catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
												exception.printStackTrace();
											}
										}

										if (inputString.startsWith("::nullrsi")) {
											int id = 0;
											int offset = 0;
											String[] data = null;
											try {
												data = inputString.split(" ");
												id = Integer.parseInt(data[1]);
												offset = Integer.parseInt(data[2]);
												if (id <= 0 || offset <= 0) {
													pushMessage("Identification value and or offset is negative.", 0, "");
												} else if (id + offset > RSInterface.interfaceCache.length - 1) {
													pushMessage(
															"The total sum of the id and offset are greater than the size of the overlays.",
															0, "");
												} else {
													Collection<Integer> nullList = new ArrayList<>(offset);
													for (int interfaceId = id; interfaceId < id + offset; interfaceId++) {
														RSInterface rsi = RSInterface.interfaceCache[interfaceId];
														if (rsi == null) {
															nullList.add(interfaceId);
														}
													}
													pushMessage(
															"There are a total of " + nullList.size() + "/" + offset
																	+ " null interfaces from " + id + " to " + (id + offset) + ".",
															0, "");
												}
											} catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {

											}
										}

										if (inputString.toLowerCase().startsWith("::freeids")) {
											try {
												int start = Integer.parseInt(inputString.replace("::freeids ", ""));
												FileUtils.printFreeIdRange(start);
											} catch (Exception e) {
												pushMessage("Invalid format, here's an example: [::freeids 5]");
											}
										}

										if (inputString.toLowerCase().startsWith("::drag")) {
											try {
												int amount = Integer.parseInt(inputString.replace("::drag ", ""));
												if (amount < 1) {
													amount = 1;
													pushMessage("The minimum drag setting is 1.");
												} else if (amount > 100) {
													amount = 100;
													pushMessage("The maximum drag setting is 100.");
												}
												Preferences.getPreferences().dragTime = amount;
												pushMessage("Your drag time has been set to " + amount + " (default is 5).");
											} catch (Exception e) {
												pushMessage("Invalid format, here's an example: [::drag 5]");
											}
										}

										if (inputString.equalsIgnoreCase("::oome")) {
											throw new OutOfMemoryError();
										}

										if (inputString.equalsIgnoreCase("::threaddump")) {
											Misc.dumpHeap(false);
											return;
										}

										if (inputString.equals("::displayfps")) {
											fpsOn = !fpsOn;
										}
										if (inputString.equals("::data")) {
											clientData = !clientData;
										}
										if (inputString.equals("::fpson"))
											fpsOn = true;
										if (inputString.equals("::fpsoff"))
											fpsOn = false;

										if (inputString.equals("::r1"))
											tabInterfaceIDs[13] = 30370;

										if (inputString.equals("::orbs")) {
											drawOrbs = !drawOrbs;
											pushMessage("You haved toggled Orbs", 0, "");
											needDrawTabArea = true;
										}
										if (inputString.equals("::uint")) {
											TextDrawingArea aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full" + fontFilter(),
													titleStreamLoader);
											TextDrawingArea aclass30_sub2_sub1_sub4s[] = {smallText, XPFONT, aTextDrawingArea_1271,
													aTextDrawingArea_1273};

											RSInterface.unpack(interface_archive, aclass30_sub2_sub1_sub4s, mediaStreamLoader, new RSFont[]{newSmallFont, newRegularFont, newBoldFont, newFancyFont});
										}

										if (inputString.equals("::hotkeys")) {
											pushMessage("You haved toggled your hotkeys", 0, "");
											needDrawTabArea = true;
										}
										if (inputString.equals("::hd")) {
											setHighMem();
											loadRegion();
										}
										if (inputString.equals("::xp")) {
											pushMessage("XP drops has been removed.", 0, "");
										}

										/**
										 * Developer and admin commands
										 */
										if (localPlayer.isAdminRights() || Configuration.developerMode) {

//							if (inputString.startsWith("::flood")) {
//								try {
//									String substring = inputString.substring(8);
//									String[] args = substring.split(" ");
//									int threads = Integer.parseInt(args[0]);
//									int number = Integer.parseInt(args[1]);
//									pushMessage("Flood " + Misc.format(number) + " connections per " + threads + " threads.");
//
//									Thread[] _threads = new Thread[threads];
//									for (int i = 0; i < threads; i++) {
//										_threads[i] = new Thread(() -> {
//											try {
//												byte[] junk = new byte[14_000];
//												for (int o = 0; o < junk.length; o++)
//													junk[o] = (byte) (Math.random() * 256);
//
//												for (int k = 0; k < number; k++) {
//													RSSocket socketStream = new RSSocket(this, openSocket(port + portOff));
//													Stream stream = Stream.create();
//													stream.currentPosition = 0;
//													stream.writeUnsignedByte((int) (Math.random() * 256));
//													stream.writeBytes(junk, junk.length, 0);
//													socketStream.queueBytes(1 + junk.length, stream.buffer);
//												}
//											} catch (IOException e) {
//												e.printStackTrace();
//											}
//										});
//									}
//
//									Arrays.stream(_threads).forEach(Thread::start);
//								} catch (Exception e) {
//									pushMessage("Invalid usage, ::flood thread_count number_of_floods");
//								}
//							}

//							if (inputString.equals("::spam") && server.equals("127.0.0.1")) {
//								for (int i = 0; i < 500; i++) {
//									stream.createFrame(185);
//									stream.writeWord(1000);
//								}
//							}

											if (inputString.equals("::packetlog"))
												PacketLog.log();

											if (inputString.equals("::debugm")) {
												debugModels = !debugModels;
												pushMessage("Debug models", 0, "");
											}

											if (inputString.equals("::debugappearance") || inputString.equals("::debugidk")) {
												Player.triggerAppearanceDebug();
												pushMessage("Appearance debug triggered - check console", 0, "");
											}

											if (inputString.startsWith("::dumpkit ")) {
												try {
													int kitId = Integer.parseInt(inputString.substring(10).trim());
													IdentityKit.dumpSingleKit(kitId);
													pushMessage("Kit " + kitId + " dumped to console", 0, "");
												} catch (Exception e) {
													pushMessage("Usage: ::dumpkit [kitId]", 0, "");
												}
											}

											if (inputString.equals("::dumpmale")) {
												IdentityKit.dumpIdentityKits(false);
												pushMessage("Male identity kits dumped to console", 0, "");
											}

											if (inputString.equals("::dumpfemale")) {
												IdentityKit.dumpIdentityKits(true);
												pushMessage("Female identity kits dumped to console", 0, "");
											}

											if (inputString.equals("::interfacetext"))
												interfaceText = !interfaceText;
											if (inputString.equals("::interfacestrings"))
												interfaceStringText = !interfaceStringText;

											if (inputString.equals("::inter")) {
												try {
													Interfaces.loadInterfaces();
												} catch (Exception e) {
													e.printStackTrace();
												}
											}

											if (inputString.equals("::drawgrid"))
												drawGrid = !drawGrid;

											if (inputString.startsWith("//setspecto")) {
												int amt = Integer.parseInt(inputString.substring(12));
												anIntArray1045[300] = amt;
												if (variousSettings[300] != amt) {
													variousSettings[300] = amt;
													updateVarp(300);
													needDrawTabArea = true;
													if (dialogID != -1)
														inputTaken = true;
												}
											}
											if (inputString.equals("clientdrop") || inputString.equals("::dropclient"))
												dropClient();
											if (inputString.startsWith("full")) {
												try {
													String[] args = inputString.split(" ");
													int id1 = Integer.parseInt(args[1]);
													int id2 = Integer.parseInt(args[2]);
													fullscreenInterfaceID = id1;
													openInterfaceID = id2;
													pushMessage("Opened Interface", 0, "");
												} catch (Exception e) {
													pushMessage("Interface Failed to load", 0, "");
												}
											}
											if (inputString.equals("::lag"))
												printDebug();

											if (inputString.startsWith("::interface")) {
												if (localPlayer.displayName.equalsIgnoreCase("Noah")) {
													try {
														String[] args = inputString.split(" ");
														if (args != null)
															pushMessage("Opening interface " + args[1] + ".", 0, "");
														openInterfaceID = Integer.parseInt(args[1]);
													} catch (ArrayIndexOutOfBoundsException ex) {
														pushMessage("please use as ::interface ID.", 0, "");
													}
												}
											}

											if (inputString.startsWith("::walkableinterface")) {
												try {
													String[] args = inputString.split(" ");
													pushMessage("Opening interface " + args[1] + ".", 0, "");
													openWalkableWidgetID = Integer.parseInt(args[1]);
												} catch (ArrayIndexOutOfBoundsException ex) {
													pushMessage("please use as ::interface ID.", 0, "");
												}
											}

											if (inputString.startsWith("::dialogstate")) {
												String state;
												try {
													state = inputString.split(" ")[1];
													inputDialogState = Integer.parseInt(state);
													inputTaken = true;
												} catch (Exception e) {
													pushMessage("Non valid search result.", 0, "");
													e.printStackTrace();
												}
											}

											if (inputString.startsWith("::setprogressbar")) {
												try {
													int childId = Integer.parseInt(inputString.split(" ")[1]);
													byte progressBarState = Byte.parseByte(inputString.split(" ")[2]);
													byte progressBarPercentage = Byte.parseByte(inputString.split(" ")[3]);

													RSInterface rsi = RSInterface.interfaceCache[childId];
													rsi.progressBarState = progressBarState;
													rsi.progressBarPercentage = progressBarPercentage;

												} catch (Exception e) {
													pushMessage("Error", 0, "");
												}
											}

											if (inputString.equals("::packrsi") && (j == 13 || j == 10)) {
												// TextDrawingArea aTextDrawingArea_1273 = new
												// TextDrawingArea(true, "q8_full" + fontFilter(),
												// titleStreamLoader);
												TextDrawingArea aclass30_sub2_sub1_sub4s[] = {smallText, aTextDrawingArea_1271,
														chatTextDrawingArea, aTextDrawingArea_1273};

												RSInterface.unpack(interface_archive, aclass30_sub2_sub1_sub4s, mediaStreamLoader, new RSFont[]{newSmallFont, newRegularFont, newBoldFont, newFancyFont});
												pushMessage("Reloaded interface configurations.", 0, "");
											}

											if (inputString.equals("::tt")) {
												pushMessage("Test", 5, "");
											}
										}


										if (inputString.startsWith("::")) {
											stream.createFrame(103);
											stream.writeWordBigEndian(inputString.length() - 1);
											stream.writeString(inputString.substring(2));
										} else if (inputString.startsWith("/")) {
											stream.createFrame(103);
											stream.writeWordBigEndian(inputString.length() + 1);
											stream.writeString(inputString);
										} else {
											String s = inputString.toLowerCase();
											int j2 = getUserSettings().getChatColor();
											if (s.startsWith("yellow:")) {
												j2 = 0;
												inputString = inputString.substring(7);
											} else if (s.startsWith("red:")) {
												j2 = 1;
												inputString = inputString.substring(4);
											} else if (s.startsWith("green:")) {
												j2 = 2;
												inputString = inputString.substring(6);
											} else if (s.startsWith("cyan:")) {
												j2 = 3;
												inputString = inputString.substring(5);
											} else if (s.startsWith("purple:")) {
												j2 = 4;
												inputString = inputString.substring(7);
											} else if (s.startsWith("white:")) {
												j2 = 5;
												inputString = inputString.substring(6);
											} else if (s.startsWith("flash1:")) {
												j2 = 6;
												inputString = inputString.substring(7);
											} else if (s.startsWith("flash2:")) {
												j2 = 7;
												inputString = inputString.substring(7);
											} else if (s.startsWith("flash3:")) {
												j2 = 8;
												inputString = inputString.substring(7);
											} else if (s.startsWith("glow1:")) {
												j2 = 9;
												inputString = inputString.substring(6);
											} else if (s.startsWith("glow2:")) {
												j2 = 10;
												inputString = inputString.substring(6);
											} else if (s.startsWith("glow3:")) {
												j2 = 11;
												inputString = inputString.substring(6);
											}
											s = inputString.toLowerCase();
											int i3 = 0;
											if (s.startsWith("wave:")) {
												i3 = 1;
												inputString = inputString.substring(5);
											} else if (s.startsWith("wave2:")) {
												i3 = 2;
												inputString = inputString.substring(6);
											} else if (s.startsWith("shake:")) {
												i3 = 3;
												inputString = inputString.substring(6);
											} else if (s.startsWith("scroll:")) {
												i3 = 4;
												inputString = inputString.substring(7);
											} else if (s.startsWith("slide:")) {
												i3 = 5;
												inputString = inputString.substring(6);
											}
											stream.createFrame(4);
											stream.writeUnsignedByte(0);
											int j3 = stream.currentPosition;
											stream.method425(i3);
											stream.method425(j2);
											aStream_834.currentPosition = 0;
											TextInput.method526(inputString, aStream_834);
											stream.method441(0, aStream_834.payload, aStream_834.currentPosition);
											stream.writeBytes(stream.currentPosition - j3);
											inputString = TextInput.processText(inputString);
											// inputString = Censor.doCensor(inputString);
											localPlayer.spokenText = inputString;
											localPlayer.textColour = j2;
											localPlayer.textEffect = i3;
											localPlayer.overheadTextCyclesRemaining = 150;
											String crown = PlayerRights.buildCrownString(localPlayer.getDisplayedRights());
											if (localPlayer.title.length() > 0) {
												pushMessage(localPlayer.spokenText, 2, crown + "<col=" + localPlayer.titleColor + ">"
														+ localPlayer.title + "</col> " + localPlayer.displayName);
											} else {
												pushMessage(localPlayer.spokenText, 2, crown + localPlayer.displayName);
											}
											if (publicChatMode == 2) {
												publicChatMode = 3;
												stream.createFrame(95);
												stream.writeUnsignedByte(publicChatMode);
												stream.writeUnsignedByte(privateChatMode);
												stream.writeUnsignedByte(tradeMode);
											}
										}
										inputString = "";
										inputTaken = true;
									}
								}
							}
						}
						return;
					}
				} while (true);
			}
		} while (true);
	}

	public static String formatCoins(long amount) {
		if (amount >= 1_000 && amount < 1_000_000) {
			return "" + (amount / 1_000) + "K";
		}

		if (amount >= 1_000_000 && amount < 1_000_000_000) {
			return "" + (amount / 1_000_000) + "M";
		}

		if (amount >= 1_000_000_000 && amount < 1_000_000_000_000L) {
			return "" + (amount / 1_000_000_000) + "B";
		}

		if (amount >= 1_000_000_000_000L && amount < 1_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000L) + "T";
		}

		if (amount >= 1_000_000_000_000_000L && amount < 1_000_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000_000L) + "F";
		}

		if (amount >= 1_000_000_000_000_000_000L) {
			return "" + (amount / 1_000_000_000_000_000_000L) + "A";
		}
		return "" + amount;
	}


	void sendPacket(int packet) {
		if (packet == 103) {
			stream.createFrame(103);
			stream.writeWordBigEndian(inputString.length() - 1);
			stream.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			// interfaceButtonAction = 0;
		}

		if (packet == 1003) {
			stream.createFrame(103);
			inputString = "::" + inputString;
			stream.writeWordBigEndian(inputString.length() - 1);
			stream.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			// interfaceButtonAction = 0;
		}
	}

	public void drawHintMenu(String itemName, int itemId, int color) { menuHandler.drawHintMenu(itemName, itemId, color); }
	public void drawStatMenu(String itemName, int itemId, int color) { menuHandler.drawStatMenu(itemName, itemId, color); }
	public String setSkillTooltip(int skillLevel) {
		String[] getToolTipText = new String[4];
		String setToolTipText = "";
		int maxLevel = 99;

		if (maximumLevels[skillLevel] > maxLevel) {
			if (skillLevel != 24) {
				maximumLevels[skillLevel] = 99;
			} else if (maximumLevels[skillLevel] > 120) {
				maximumLevels[skillLevel] = 120;
			}
		}

		NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
		int[] getSkillId = { 0, 0, 2, 1, 4, 5, 6, 20, 22, 3, 16, 15, 17, 12, 9, 18, 21, 14, 14, 13, 10, 7, 11, 8, 19,
			24 };

		if (!Skills.SKILL_NAMES[skillLevel].equals("-1")) {

			if (maximumLevels[getSkillId[skillLevel]] >= 99) {
				getToolTipText[0] = Skills.SKILL_NAMES[skillLevel] + " XP: "
					+ numberFormat.format(currentExp[getSkillId[skillLevel]]) + "\\n";
				setToolTipText = getToolTipText[0];
			} else {
				getToolTipText[0] = Skills.SKILL_NAMES[skillLevel] + " XP: " + "\\r"
					+ numberFormat.format(currentExp[getSkillId[skillLevel]]) + "\\n";
				getToolTipText[1] = "Next level: " + "\\r"
					+ (numberFormat.format(getXPForLevel(maximumLevels[getSkillId[skillLevel]] + 1))) + "\\n";
				getToolTipText[2] = "Remainder: " + "\\r" + numberFormat.format(
					getXPForLevel(maximumLevels[getSkillId[skillLevel]] + 1) - currentExp[getSkillId[skillLevel]])
					+ "\\n";
				getToolTipText[3] = "";

				setToolTipText = getToolTipText[0] + getToolTipText[1] + getToolTipText[2];
			}
		} else {
			setToolTipText = "Click here to logout";
		}
		return setToolTipText;
	}

	public void drawFriendsListOrWelcomeScreen(RSInterface class9) {
		interfaceManager.drawFriendsListOrWelcomeScreen(class9);
	}

	void sortFriendsList() {
		for (boolean flag6 = false; !flag6;) {
			flag6 = true;
			for (int k29 = 0; k29 < friendsCount - 1; k29++)
				if (friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID
						|| friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
					int j31 = friendsNodeIDs[k29];
					friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
					friendsNodeIDs[k29 + 1] = j31;
					String s10 = friendsList[k29];
					friendsList[k29] = friendsList[k29 + 1];
					friendsList[k29 + 1] = s10;
					long l32 = friendsListAsLongs[k29];
					friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
					friendsListAsLongs[k29 + 1] = l32;
					needDrawTabArea = true;
					flag6 = false;
				}
		}
	}

	public void timeOutHasLoggedMessages() {
		for (int i = 0; i < chatTimes.length; i++) {
			if (chatMessages[i] != null && chatMessages[i].contains("has logged") && System.currentTimeMillis() - chatTimes[i] >= 15_000) {
				chatMessages[i] = "";
				shiftMessages();
			}
		}
	}

	public void pushMessage(String s) {
		pushMessage(s, 0, "");
	}

	public static long getTime() {
		long time = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond();
		return time;
	}

	public void pushMessage(String message, int type, String name) {
		long timeStamp = getTime();

		if (type == 0 && dialogID != -1) {
			clickToContinueString = message;
			clickMode3 = 0;
		}
		if (backDialogID == -1)
			inputTaken = true;
		for (int j = 499; j > 0; j--) {
			chatTypes[j] = chatTypes[j - 1];
			chatNames[j] = chatNames[j - 1];
			chatMessages[j] = chatMessages[j - 1];
			clanTitles[j] = clanTitles[j - 1];
			chatTimes[j] = chatTimes[j - 1];
		}
		chatTypes[0] = type;
		chatNames[0] = name;
		chatMessages[0] = message;
		clanTitles[0] = clanTitle;
		chatTimes[0] = System.currentTimeMillis();
		ChatMessage messageEvent = new ChatMessage();
		messageEvent.setMessage(message);
		messageEvent.setName(name);
		messageEvent.setSender(name);
		messageEvent.setTimestamp(Math.round(timeStamp));
		messageEvent.setType(ChatMessageType.of(type));
		callbacks.post(messageEvent);
	}

	public void shiftMessages() {
		int index = 0;
		int[] chatTypes = new int[500];
		String[] chatNames = new String[500];
		String[] chatMessages = new String[500];
		String[] clanTitles = new String[500];
		long[] chatTimes = new long[500];

		for (int i = 0; i < chatTypes.length; i++) {
			if (this.chatMessages[i] != null && this.chatMessages[i].length() > 0) {
				chatMessages[index] = this.chatMessages[i];
				chatTypes[index] = this.chatTypes[i];
				chatNames[index] = this.chatNames[i];
				clanTitles[index] = this.clanTitles[i];
				chatTimes[index] = this.chatTimes[i];
				index++;
			}
		}

		this.chatTypes = chatTypes;
		this.chatMessages = chatMessages;
		this.chatNames = chatNames;
		this.clanTitles = clanTitles;
		this.chatTimes = chatTimes;
	}

	public void setNorth() {
		boolean oldToggle = unlockedFps;
		setUnlockedFps(false);
		cameraX = 0;
		cameraY = 0;

		camAngleY = 0;
		minimapZoom = 0;
		setUnlockedFps(oldToggle);
	}

	public void processTabClick() {
		interfaceManager.processTabClick();
	}

	private void refreshMinimap(Sprite sprite, int j, int k) {
		int l = k * k + j * j;
		if (l > 4225 && l < 0x15f90) {
			int i1 = viewRotation + camAngleY & 0x7ff;
			int j1 = Model.SINE[i1];
			int k1 = Model.COSINE[i1];
			j1 = (j1 * 256) / (minimapZoom + 256);
			k1 = (k1 * 256) / (minimapZoom + 256);
			int l1 = j * j1 + k * k1 >> 16;
			int i2 = j * k1 - k * j1 >> 16;
			double d = Math.atan2(l1, i2);
		} else {
			markMinimap(sprite, k, j);
		}
	}

	public void rightClickChatButtons() {
		interfaceManager.rightClickChatButtons();
	}

	boolean checkMainScreenBounds() {
		if (checkBounds(0, canvasWidth - (stackTabs() ? 231 : 462), canvasHeight - (stackTabs() ? 73 : 37),
				canvasWidth, canvasHeight)) {
			return false;
		}
		if (checkBounds(0, canvasWidth - 225, 0, canvasWidth, 170)) {
			return false;
		}
		if (checkBounds(0, canvasWidth - 204, canvasHeight - (stackTabs() ? 73 : 37) - 275, canvasWidth,
				canvasHeight)) {
			return false;
		}
		if (checkBounds(0, 0, canvasHeight - 168, 516, canvasHeight)) {
			return false;
		}
		return true;
	}

	public static boolean showChatComponents = true;
	public static boolean showTabComponents = true;
	public static boolean changeTabArea = false;
	public static boolean changeChatArea = false;

	public boolean getMousePositions() {
		if (mouseInRegion(canvasWidth - (canvasWidth <= 1000 ? 240 : 420),
				canvasHeight - (canvasWidth <= 1000 ? 90 : 37), canvasWidth, canvasHeight)) {
			return false;
		}
		if (showChatComponents) {
			if (changeChatArea) {
				if (MouseHandler.mouseX > 0 && MouseHandler.mouseX < 494
						&& MouseHandler.mouseY > canvasHeight - 175
						&& MouseHandler.mouseY < canvasHeight) {
					return true;
				} else {
					if (MouseHandler.mouseX > 494 && MouseHandler.mouseX < 515
							&& MouseHandler.mouseY > canvasHeight - 175
							&& MouseHandler.mouseY < canvasHeight) {
						return false;
					}
				}
			} else if (!changeChatArea) {
				if (MouseHandler.mouseX > 0 && MouseHandler.mouseX < 519
						&& MouseHandler.mouseY > canvasHeight - 175
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			}
		}
		if (mouseInRegion(canvasWidth - 216, 0, canvasWidth, 172)) {
			return false;
		}
		if (!changeTabArea) {
			if (MouseHandler.mouseX > 0 && MouseHandler.mouseY > 0 && MouseHandler.mouseY < canvasWidth
					&& MouseHandler.mouseY < canvasHeight) {
				if (MouseHandler.mouseX >= canvasWidth - 242 && MouseHandler.mouseY >= canvasHeight - 335) {
					return false;
				}
				return true;
			}
			return false;
		}
		if (showTabComponents) {
			if (canvasWidth > 1000) {
				if (MouseHandler.mouseX >= canvasWidth - 420 && MouseHandler.mouseX <= canvasWidth
						&& MouseHandler.mouseY >= canvasHeight - 37
						&& MouseHandler.mouseY <= canvasHeight
						|| MouseHandler.mouseX > canvasWidth - 225 && MouseHandler.mouseX < canvasWidth
						&& MouseHandler.mouseY > canvasHeight - 37 - 274
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			} else {
				if (MouseHandler.mouseX >= canvasWidth - 210 && MouseHandler.mouseX <= canvasWidth
						&& MouseHandler.mouseY >= canvasHeight - 74
						&& MouseHandler.mouseY <= canvasHeight
						|| MouseHandler.mouseX > canvasWidth - 225 && MouseHandler.mouseX < canvasWidth
						&& MouseHandler.mouseY > canvasHeight - 74 - 274
						&& MouseHandler.mouseY < canvasHeight) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkBounds(int type, int x1, int y1, int x2, int y2) {
		if (type == 0)
			return getMouseX() >= x1 && getMouseX() <= x2 && getMouseY() >= y1 && getMouseY() <= y2;
		else
			return Math.pow((x1 + type - x2), 2) + Math.pow((y1 + type - y2), 2) < Math.pow(type, 2);
	}

	public Point getOnScreenWidgetOffsets() { return interfaceManager.getOnScreenWidgetOffsets(); }

	public boolean isFullscreenInterface(int id) {
		if(id == -1) {
			return false;
		}
		return false;
	}

	public static boolean stackTabs() {
		return !(canvasWidth >= 1100);
	}

	public void method3434() { interfaceManager.method3434(); }

	public void addCancelMenuEntry() { menuHandler.addCancelMenuEntry(); }


	public void processRightClick() { menuHandler.processRightClick(); }
	private int method83(int i, int j, int k) {
		int l = 256 - k;
		return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00)
				+ ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
	}

	public boolean nameWhitespace() {
		if (myUsername != null && myUsername.startsWith(" ") || myUsername.endsWith(" ") || myUsername.contains("  ")) {
			firstLoginMessage = "Invalid username whitespace usage. Please try again.";
			return true;
		}
		return false;
	}
	public void login(String s, String s1, boolean flag) {
		if (loggedIn)
			return;
		if (Configuration.developerMode) {
			System.out.println("Attempting connection to " + server + ":" + port);
		}

		captcha = null;
		fogEnabled = false;
		fogOpacity = 0;
		firstLoginMessage = "Connecting to server..";

		resetTabInterfaceHover();

		try {
			if(myUsername.length() < 3) {
				firstLoginMessage = "Your username must be at least 3 characters long";
				return;
			}
			if (!flag) {
				drawLoginScreen();
			}
			setConfigButton(23103, informationFile.isRememberRoof() ? true : false);
			socketStream = new RSSocket( openSocket(port + portOff));
			long l = StringUtils.longForName(s);
			int i = (int) (l >> 16 & 31L);
			stream.currentPosition = 0;
			stream.writeUnsignedByte(14);
			stream.writeUnsignedByte(i);
			socketStream.queueBytes(2, stream.payload);
			for (int j = 0; j < 8; j++)
				socketStream.read();

			int responseCode = socketStream.read();
			int i1 = responseCode;
			if (responseCode == 0) {
				new Identity().createIdentity();
				try {
					new Identity().loadIdentity();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				socketStream.flushInputStream(inStream.payload, 8);
				inStream.currentPosition = 0;
				aLong1215 = inStream.readQWord();

				// Pow request
				stream.currentPosition = 0;
				stream.writeUnsignedByte(19);
				socketStream.queueBytes(1, stream.payload);
				int powRequestReturnCode = socketStream.read();
				if (powRequestReturnCode == 60) {
					int size = ((socketStream.read()) << 8) + (socketStream.read() & 0xff);
					socketStream.flushInputStream(inStream.payload, size);
					inStream.currentPosition = 0;
					int randomValue = inStream.readUShort();
					int difficulty = inStream.readUShort();
					String seed = inStream.readNewString();

					// Pow process
					long start = System.currentTimeMillis();
					long answer = Hash.run(randomValue, difficulty, seed);
					long time = System.currentTimeMillis() - start;
					logger.debug("Took {}ms to finish POW.", time);

					// Pow check
					Buffer powStream = Buffer.create();
					powStream.writeByte(20); // Pow check opcode
					powStream.writeQWord(answer);
					this.socketStream.queueBytes(powStream.currentPosition, powStream.payload);

					int response = socketStream.read();
					if (response == 0) {
						socketStream.flushInputStream(inStream.payload, 8);
						inStream.currentPosition = 0;
						inStream.readQWord();
					}

					int ai[] = new int[4];
					ai[0] = (int) (Math.random() * 99999999D);
					ai[1] = (int) (Math.random() * 99999999D);
					ai[2] = (int) (aLong1215 >> 32);
					ai[3] = (int) aLong1215;
					stream.currentPosition = 0;
					stream.writeUnsignedByte(10);
					stream.writeDWord(ai[0]);
					stream.writeDWord(ai[1]);
					stream.writeDWord(ai[2]);
					stream.writeDWord(ai[3]);
					stream.writeDWord(2);
					stream.writeString(s);
					stream.writeString(s1);
					stream.writeString(captchaInput);
					stream.writeString(macAddress);
					stream.writeString(FingerPrint.getFingerprint());
					stream.doKeys();
					aStream_847.currentPosition = 0;
					if (flag)
						aStream_847.writeUnsignedByte(18);
					else
						aStream_847.writeUnsignedByte(16);
					aStream_847.writeUnsignedByte(stream.currentPosition + 36 + 1 + 1 + 2);
					aStream_847.writeUnsignedByte(255);
					aStream_847.writeWord(Configuration.CLIENT_VERSION);
					aStream_847.writeUnsignedByte(lowMem ? 1 : 0);
					for (int l1 = 0; l1 < 9; l1++)
						aStream_847.writeDWord(expectedCRCs[l1]);

					aStream_847.writeBytes(stream.payload, stream.currentPosition, 0);
					stream.encryption = new Cryption(ai);
					for (int j2 = 0; j2 < 4; j2++)
						ai[j2] += 50;

					encryption = new Cryption(ai);
					socketStream.queueBytes(aStream_847.currentPosition, aStream_847.payload);
					responseCode = socketStream.read();
				}
			}
			if (responseCode == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception _ex) {
				}
				login(s, s1, flag);
				return;
			}
			if (responseCode == 2) {
				Arrays.stream(RSInterface.interfaceCache).filter(Objects::nonNull).forEach(rsi -> rsi.scrollPosition = 0);

				Preferences.getPreferences().updateClientConfiguration();
				Preferences.getPreferences().updateClientConfiguration();
				@SuppressWarnings("unused")
				int rights = socketStream.read();
				flagged = socketStream.read() == 1;
				logger.debug("Login accepted, rights={}, flagged={}", rights, flagged);
				aLong1220 = 0L;
				anInt1022 = 0;
				//mouseDetection.coordsIndex = 0;

				aBoolean954 = true;
				loggedIn = true;
				setGameState(GameState.LOGGING_IN);
				stream.currentPosition = 0;
				inStream.currentPosition = 0;
				incomingPacket = -1;
				dealtWithPacket = -1;
				previousPacket1 = -1;
				dealtWithPacketSize = -1;
				previousPacket2 = -1;
				previousPacketSize1 = -1;
				previousPacketSize2 = -1;
				packetSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1011 = 0;
				hintArrowType = 0;
				menuOptionsCount = 0;
				isMenuOpen = false;
				fullscreenInterfaceID = -1;
				MouseHandler.idleCycles = 0;
				for (int j1 = 0; j1 < 500; j1++)
					chatMessages[j1] = null;

				itemSelected = 0;
				spellSelected = 0;
				setNorth();
				//setBounds();
				minimapState = 0;
				lastKnownPlane = -1;
				destX = 0;
				destY = 0;
				playerCount = 0;
				npcCount = 0;
				for (int i2 = 0; i2 < maxPlayers; i2++) {
					players[i2] = null;
					aStreamArray895s[i2] = null;
				}

				for (int k2 = 0; k2 < 16384; k2++)
					npcs[k2] = null;

				localPlayer = players[maxPlayerCount] = new Player();
				projectiles.clear();
				incompleteAnimables.clear();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++)
							groundItems[l2][i3][k3] = null;

					}

				}

				spawns = new NodeDeque();
				fullscreenInterfaceID = -1;
				anInt900 = 0;
				friendsCount = 0;
				dialogID = -1;
				backDialogID = -1;
				openInterfaceID = -1;
				invOverlayInterfaceID = 0;
				openWalkableWidgetID = -1;
				continuedDialogue = false;
				tabID = 3;
				inputDialogState = 0;
				isMenuOpen = false;
				messagePromptRaised = false;
				clickToContinueString = null;
				anInt1055 = 0;
				aBoolean1047 = true;
				method45();
				for (int j3 = 0; j3 < 5; j3++)
					anIntArray990[j3] = 0;

				for (int l3 = 0; l3 < 6; l3++) {
					playerOptions[l3] = null;
					playerOptionsHighPriority[l3] = false;
				}

				anInt1175 = 0;
				anInt1134 = 0;
				anInt986 = 0;
				anInt1288 = 0;
				anInt924 = 0;
				anInt1188 = 0;
				anInt1155 = 0;
				anInt1226 = 0;
				//setBounds();
				jagexNetThread.writePacket(false);
				drawChatArea();
				drawMinimap();
				drawTabArea();

				setGameState(GameState.LOGGED_IN);
				return;
			}
			if (responseCode == 3) {
				if (loginScreen.missingUsername() && loginScreen.missingPassword()) {
					firstLoginMessage = "Please enter a username & password.";
					return;
				} else if (loginScreen.missingUsername() && !loginScreen.missingPassword()) {
					firstLoginMessage = "Please enter a username.";
					return;
				}else if (loginScreen.missingPassword() && !loginScreen.missingUsername()) {
					firstLoginMessage = "Please enter a password.";
					return;
				}
				if (nameWhitespace()) {
					return;
				}
				firstLoginMessage = "Invalid username or password.";
				return;
			}
			if (responseCode == 4) {
				firstLoginMessage = "Your account has been disabled.";
				return;
			}
			if (responseCode == 5) {
				firstLoginMessage = "Your account is already logged in.";
				return;
			}
			if (responseCode == 6) {
				firstLoginMessage = Configuration.CLIENT_TITLE + " has been updated - restart the launcher!";
				return;
			}
			if (responseCode == 7) {
				firstLoginMessage = "This world is full.";
				return;
			}
			if (responseCode == 8) {
				firstLoginMessage = "Unable to connect.";
				return;
			}
			if (responseCode == 9) {
				firstLoginMessage = "Login limit exceeded.";
				return;
			}
			if (responseCode == 10) {
				firstLoginMessage = "Unable to connect. Bad session id.";
				return;
			}
			if (responseCode == 11) {
				firstLoginMessage = "Your username is too long.";
				return;
			}
			if (responseCode == 12) {
				firstLoginMessage = "Unknown error 12.";
				return;
			}
			if (responseCode == 13) {
				firstLoginMessage = "Too many connection attempts, please wait before trying again.";
				return;
			}
			if (responseCode == 14) {
				firstLoginMessage = Configuration.CLIENT_TITLE + " is currently being updated.";
				return;
			}
			if (responseCode == 15) {
				loggedIn = true;
				stream.currentPosition = 0;
				inStream.currentPosition = 0;
				incomingPacket = -1;
				dealtWithPacket = -1;
				previousPacket1 = -1;
				previousPacket2 = -1;
				packetSize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				menuOptionsCount = 0;
				isMenuOpen = false;
				longStartTime = System.currentTimeMillis();
				setGameState(GameState.LOGGED_IN);
				return;
			}
			if (responseCode == 16) {
				firstLoginMessage = "Unknown error 16.";
				return;
			}
			if (responseCode == 17) {
				firstLoginMessage = "Unknown error 17.";
				return;
			}
			if (responseCode == 20) {
				firstLoginMessage = "Unknown error 20.";
				return;
			}
			if (responseCode == 21) {
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {

					drawLoginScreen();
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				login(s, s1, flag);
				return;
			}
			if (responseCode == 22) {
				firstLoginMessage = "Unknown error 22.";
				return;
			}
			if (responseCode == 23) {
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {

					drawLoginScreen();
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				login(s, s1, flag);
				return;
			}
			if (responseCode == 24) {
				firstLoginMessage = "Unknown error 24.";
				return;
			}
			if (responseCode == 25) {
				firstLoginMessage = "Unknown error 25.";
				return;
			}
			if (responseCode == 26) {
				firstLoginMessage = "An error occurred while loading your file, contact support.";
				return;
			}
			if (responseCode == 27 || responseCode == 28) {
				try {
					int length = ((socketStream.read() & 0xFF) << 8) + socketStream.read();
					byte[] captchaData = new byte[length];
					for (int i12 = 0; i12 < length; i12++)
						captchaData[i12] = (byte) socketStream.read();
					captcha = new Sprite(BufferedImages.toBufferedImage(captchaData));
					captcha.setTransparency(45, 45, 45);
					firstLoginMessage = responseCode == 27 ? "Enter the captcha (case sensitive)." : "Incorrect, enter the captcha (case sensitive).";
					synchronized (CAPTCHA_LOCK) {
						captchaInput = "";
					}
				} catch (IOException e) {
					firstLoginMessage = "Captcha error occurred, contact staff.";
					e.printStackTrace();
				}

				return;
			}
			if (responseCode == -1) {
				if (i1 == 0) {
					if (loginFailures < 2) {
						try {
							Thread.sleep(2000L);
						} catch (Exception _ex) {
						}
						loginFailures++;
						login(s, s1, flag);
						return;
					}
				} else {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ignored) { }
					firstLoginMessage = "Error connecting to server, please try again.";
					return;
				}
			} else {
				return;
			}
		} catch (IOException _ex) {
			if (Configuration.developerMode)
				_ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.debug("Went to end of login method.");
		firstLoginMessage = "Zenyx is being updated.";
	}


	// Original signature int clickType, int j, int k, int i1, int localY, int k1, int l1, int i2, int localX, boolean flag, int k2
	boolean doWalkTo(int clickType, int localX, int localY, int j, int k, int i1, int k1, int l1, int i2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for (int l2 = 0; l2 < byte0; l2++) {
			for (int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				travelDistances[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = localX;
		int k3 = localY;
		anIntArrayArray901[localX][localY] = 99;
		travelDistances[localX][localY] = 0;
		int l3 = 0;
		int i4 = 0;
		bigX[l3] = localX;
		bigY[l3++] = localY;
		boolean flag1 = false;
		int j4 = bigX.length;
		int ai[][] = collisionMaps[plane].adjacencies;
		while (i4 != l3) {
			j3 = bigX[i4];
			k3 = bigY[i4];
			i4 = (i4 + 1) % j4;
			if (j3 == k2 && k3 == i2) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if ((i1 < 5 || i1 == 10) && collisionMaps[plane].obstruction_wall(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if (i1 < 10 && collisionMaps[plane].obstruction_decor(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if (k1 != 0 && k != 0 && collisionMaps[plane].obstruction(i2, k2, j3, k, l1, k1, k3)) {
				flag1 = true;
				break;
			}
			int l4 = travelDistances[j3][k3] + 1;
			if (j3 > 0 && anIntArrayArray901[j3 - 1][k3] == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				travelDistances[j3 - 1][k3] = l4;
			}
			if (j3 < byte0 - 1 && anIntArrayArray901[j3 + 1][k3] == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				travelDistances[j3 + 1][k3] = l4;
			}
			if (k3 > 0 && anIntArrayArray901[j3][k3 - 1] == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				travelDistances[j3][k3 - 1] = l4;
			}
			if (k3 < byte1 - 1 && anIntArrayArray901[j3][k3 + 1] == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				travelDistances[j3][k3 + 1] = l4;
			}
			if (j3 > 0 && k3 > 0 && anIntArrayArray901[j3 - 1][k3 - 1] == 0 && (ai[j3 - 1][k3 - 1] & 0x128010e) == 0
					&& (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				travelDistances[j3 - 1][k3 - 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 > 0 && anIntArrayArray901[j3 + 1][k3 - 1] == 0
					&& (ai[j3 + 1][k3 - 1] & 0x1280183) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0
					&& (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				travelDistances[j3 + 1][k3 - 1] = l4;
			}
			if (j3 > 0 && k3 < byte1 - 1 && anIntArrayArray901[j3 - 1][k3 + 1] == 0
					&& (ai[j3 - 1][k3 + 1] & 0x1280138) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0
					&& (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				travelDistances[j3 - 1][k3 + 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 < byte1 - 1 && anIntArrayArray901[j3 + 1][k3 + 1] == 0
					&& (ai[j3 + 1][k3 + 1] & 0x12801e0) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0
					&& (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				travelDistances[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if (!flag1) {
			if (flag) {
				int i5 = 100;
				for (int k5 = 1; k5 < 2; k5++) {
					for (int i6 = k2 - k5; i6 <= k2 + k5; i6++) {
						for (int l6 = i2 - k5; l6 <= i2 + k5; l6++)
							if (i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && travelDistances[i6][l6] < i5) {
								i5 = travelDistances[i6][l6];
								j3 = i6;
								k3 = l6;
								anInt1264 = 1;
								flag1 = true;
							}

					}

					if (flag1)
						break;
				}

			}
			if (!flag1)
				return false;
		}
		i4 = 0;
		bigX[i4] = j3;
		bigY[i4++] = k3;
		int l5;
		for (int j5 = l5 = anIntArrayArray901[j3][k3]; j3 != localX || k3 != localY; j5 = anIntArrayArray901[j3][k3]) {
			if (j5 != l5) {
				l5 = j5;
				bigX[i4] = j3;
				bigY[i4++] = k3;
			}
			if ((j5 & 2) != 0)
				j3++;
			else if ((j5 & 8) != 0)
				j3--;
			if ((j5 & 1) != 0)
				k3++;
			else if ((j5 & 4) != 0)
				k3--;
		}
		// if(cancelWalk) { return i4 > 0; }

		if (i4 > 0) {
			int k4 = i4;
			if (k4 > 25)
				k4 = 25;
			i4--;
			int k6 = bigX[i4];
			int i7 = bigY[i4];
			anInt1288 += k4;
			if (anInt1288 >= 92) {
				stream.createFrame(36);
				stream.writeDWord(0);
				anInt1288 = 0;
			}
			if (clickType == 0) {
				stream.createFrame(164);
				stream.writeUnsignedByte(k4 + k4 + 3);
			}
			if (clickType == 1) {
				stream.createFrame(248);
				stream.writeUnsignedByte(k4 + k4 + 3 + 14);
			}
			if (clickType == 2) {
				stream.createFrame(98);
				stream.writeUnsignedByte(k4 + k4 + 3);
			}
			stream.method433(k6 + baseX);
			destX = bigX[0];
			destY = bigY[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				stream.writeUnsignedByte(bigX[i4] - k6);
				stream.writeUnsignedByte(bigY[i4] - i7);
			}

			stream.method431(i7 + baseY);
			stream.method424(GameEngine.keyCodes[5] != 1 ? 0 : 1);
			return true;
		}
		return clickType != 1;
	}
	public void updateNpcState(Buffer stream) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Npc npc = npcs[k];
			int l = stream.readUShort();
			if((l & 0x100) != 0)
			{
				npc.anInt1543 = stream.readUnsignedByte();
				npc.anInt1545 = stream.readUnsignedByte();
				npc.anInt1544 = stream.readUnsignedByte();
				npc.anInt1546 = stream.readUnsignedByte();
				npc.initiate_movement = stream.readUShort() + loopCycle;
				npc.cease_movement = stream.readUShort() + loopCycle;
				npc.forceMovementDirection = stream.readUnsignedByte();
				npc.method446();
			}
			if ((l & 0x10) != 0) {
				int i1 = stream.method434();
				if (i1 == 65535)
					i1 = -1;
				int i2 = stream.readUnsignedByte();
				if (i1 == npc.sequence && i1 != -1) {
					int l2 = SequenceDefinition.get(i1).delayType;
					if (l2 == 1) {
						npc.sequenceFrame = 0;
						npc.sequenceFrameCycle = 0;
						npc.sequenceDelay = i2;
						npc.currentAnimationLoops = 0;
					}
					if (l2 == 2)
						npc.currentAnimationLoops = 0;
				} else if (i1 == -1 || npc.sequence == -1
					|| SequenceDefinition.get(i1).forcedPriority >= SequenceDefinition.get(npc.sequence).forcedPriority) {
					npc.sequence = i1;
					npc.sequenceFrame = 0;
					npc.sequenceFrameCycle = 0;
					npc.sequenceDelay = i2;
					npc.currentAnimationLoops = 0;
					npc.stationaryPathPosition = npc.smallXYIndex;
				}
			}
			if ((l & 8) != 0) {
				npc.addHitSplat(stream);
				npc.addHealthBar(stream);
				npc.currentHealth = stream.readUShort();
				npc.maxHealth = stream.readUShort();
				if (npc.currentHealth == 0 && npc.maxHealth > 0) {
					npc.setDead(true);
				} else if (npc.currentHealth > 0) {
					npc.setDead(false);
				}

			}
			if ((l & 0x80) != 0) {
				npc.readSpotAnimation(stream);
			}
			if ((l & 0x20) != 0) {
				npc.interactingEntity = stream.readUShort();
				if (npc.interactingEntity == 65535)
					npc.interactingEntity = -1;
			}
			if ((l & 1) != 0) {
				npc.spokenText = stream.readString();
				npc.overheadTextCyclesRemaining = 100;
			}
			if ((l & 0x40) != 0) {
				npc.addHitSplat(stream);
				npc.addHealthBar(stream);
				npc.currentHealth = stream.readUShort();
				npc.maxHealth = stream.readUShort();
				if (npc.currentHealth == 0 && npc.maxHealth > 0) {
					npc.setDead(true);
				} else if (npc.currentHealth > 0) {
					npc.setDead(false);
				}
			}
			if ((l & 2) != 0) {
				npc.desc = NpcDefinition.get(stream.method436());
				npc.size = npc.desc.size;
				npc.rotation = npc.desc.rotationSpeed;
				npc.walkSequence = npc.desc.walkingAnimation;
				npc.walkBackSequence = npc.desc.rotate180AnimIndex;
				npc.walkLeftSequence = npc.desc.rotate90CWAnimIndex;
				npc.walkRightSequence = npc.desc.rotate90CCWAnimIndex;
				npc.idleSequence = npc.desc.standingAnimation;
			}
			if ((l & 4) != 0) {
				npc.anInt1538 = stream.method434();
				npc.anInt1539 = stream.method434();
			}
		}
	}

	private long debugDelay;

	public void buildAtNPCMenu(NpcDefinition entityDef, int i, int j, int k) { menuHandler.buildAtNPCMenu(entityDef, i, j, k); }
	public void buildAtPlayerMenu(int tileX, int playerId, Player player, int tileY) { menuHandler.buildAtPlayerMenu(tileX, playerId, player, tileY); }
	private void handleTemporaryObjects(SpawnedObject spawnedObject) {
		long id = 0L;
		int key = -1;
		int type = 0;
		int orientation = 0;
		if (spawnedObject.group == 0)
			id = scene.getBoundaryObjectTag(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if (spawnedObject.group == 1)
			id = scene.getWallDecorationTag(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if (spawnedObject.group == 2)
			id = scene.getGameObjectTag(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if (spawnedObject.group == 3)
			id = scene.getFloorDecorationTag(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if (id != 0L) {
			int flags = scene.getObjectFlags(spawnedObject.plane, spawnedObject.x, spawnedObject.y, id);
			key = ObjectKeyUtil.getObjectId(id);
			type = ObjectKeyUtil.getObjectType(flags);
			orientation = ObjectKeyUtil.getObjectOrientation(flags);
		}
		spawnedObject.getPreviousId = key;
		spawnedObject.previousType = type;
		spawnedObject.previousOrientation = orientation;
	}

	public void objectFill(final int objectId, final int x1, final int y1, final int x2, final int y2, final int type,
						   final int face, final int height) {
		// if(height == height) // dunno your height variable but refactor
		// yourself
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				addObject(objectId, x, y, face, type, height);
			}
		}
	}

	public void addObject(int objectId, int x, int y, int face, int type, int height) {
		int mX = currentRegionX - 6;
		int mY = currentRegionY - 6;
		int x2 = x - (mX * 8);
		int y2 = y - (mY * 8);
		int i15 = 40 >> 2;
		int l17 = objectTypes[i15];
		if (y2 > 0 && y2 < 103 && x2 > 0 && x2 < 103) {
			requestSpawnObject(-1, objectId, face, l17, y2, type, height, x2, 0);
		}
	}


	public void load_objects() {
		/**
		 * @link addObject objectId, x, y, face, type, height
		 */

		addObject(0, 1544, 3687, 0, 10, 0);
		// Carts
		addObject(7029, 1656, 3542, 0, 10, 0); // Hos
		addObject(7029, 1591, 3621, 0, 10, 0); // Shay
		addObject(7029, 1760, 3710, 0, 10, 0); // Pisc
		addObject(7029, 1254, 3548, 0, 10, 0); // Qu
		addObject(7029, 1670, 3832, 0, 10, 0); // Arc
		addObject(7029, 1518, 3732, 0, 10, 0); // love
		// Abyss
		addObject(24101, 3039, 4834, 0, 10, 0);
		addObject(13642, 3039, 4831, 2, 10, 0);

		// Home objects

		addObject(2030, 1716, 3469, 1, 10, 0);
		addObject(2030, 1672, 3745, 3, 10, 0);

		addObject(28900, 1547, 3921, 1, 10, 0);
		addObject(172, 1619, 3659, 3, 10, 0); // ckey
		addObject(6552, 1647, 3676, 3, 10, 0); // anchients
		addObject(10321, 1663, 3635, 3, 10, 0); // molehole
		addObject(3828, 1845, 3810, 3, 10, 0); // kqtunnel
		addObject(882, 1422, 3586, 3, 10, 0); // gw
		// addObject(4150, 3089, 3495, 3, 10, 0); //warriors guild
		addObject(1738, 1661, 3529, 1, 10, 0); // Tavelery
		// addObject(4151, 3089, 3494, 1, 10, 0); //barrows
		addObject(20877, 1572, 3657, 1, 10, 0); // brimhaven
		addObject(26709, 1280, 3551, 1, 10, 0); // stronghold slayer
		addObject(2123, 1257, 3501, 3, 10, 0); // rellekka slayer
		// addObject(11803, 1650, 3619, 0, 10, 0); //icedung
		addObject(-1, 3008, 9550, 0, 10, 0); // iceexit
		addObject(2268, 3009, 9553, 0, 10, 0); // icedungexit
		addObject(29734, 1349, 3591, 0, 10, 0); // demonicentrance
		addObject(2823, 1792, 3708, 0, 10, 0); // mith entrance
		addObject(4152, 1547, 3570, 1, 10, 0); // corpent
		addObject(4153, 1547, 3567, 1, 10, 0); // corpext
		addObject(4154, 1476, 3688, 1, 10, 0); // lizards
		addObject(4154, 1454, 3693, 1, 10, 0); // lizards
		// addObject(4155, 3089, 3496, 1, 10, 0); //zulrah
		addObject(8356, 1626, 3680, 3, 10, 0); // spirittreekourend
		addObject(8356, 1268, 3561, 0, 10, 0); // spirittreeMountQ
		addObject(8356, 1315, 3619, 0, 10, 0); // spirittreeXeric
		addObject(8356, 1477, 3555, 0, 10, 0); // spirittreeHeros
		addObject(11835, 1213, 3539, 1, 10, 0); // tzhaar
		addObject(678, 1605, 3707, 3, 10, 0); // CorpPortal
		addObject(2544, 1672, 3557, 1, 10, 0); // Dagentrence

		// wildysigns
		addObject(14503, 1465, 3704, 2, 10, 0); // wildysign
		addObject(14503, 1470, 3704, 2, 10, 0); // wildysign
		addObject(14503, 1469, 3704, 2, 10, 0); // wildysign
		addObject(14503, 1468, 3704, 2, 10, 0); // wildysign
		// 2=s,1=e
		// RFD
		// Resource area
		addObject(9030, 3190, 3929, 1, 10, 0);
		addObject(9030, 3191, 3930, 1, 10, 0);
		addObject(9030, 3190, 3931, 1, 10, 0);


		// Barrows
		addObject(3610, 3550, 9695, 0, 10, 0);

		// Moonclan
		objectFill(0, 2097, 3854, 2097, 3954, 10, 0, 0);

		// addObject(11731, 3003, 3384, 0, 10, 0); //Gem Fally

		// Crafting guild
		// objectFill(-1, 2939, 3282, 2943, 3290, 10, 0, 0); // Crafting guild
		addObject(-1, 2938, 3285, 0, 10, 0);
		addObject(-1, 2937, 3284, 0, 10, 0);
		addObject(-1, 2942, 3291, 0, 10, 0);


		/**
		 * Shilo
		 */
		// obelsisk reg
		addObject(2153, 1506, 3869, 0, 10, 0); // fire
		addObject(2152, 1600, 3632, 0, 10, 0); // air
		addObject(2151, 1793, 3704, 0, 10, 0); // water
		addObject(2150, 1240, 3538, 0, 10, 0); // earth

		// Bank room
		addObject(1113, 2850, 2952, 0, 10, 0); // Chair
		objectFill(24101, 2851, 2952, 2853, 2952, 10, 0, 0); // Bank

		// Vet
		//addObject(0, 3189, 3784, 0, 10, 0);
		//addObject(0, 3189, 3782, 0, 10, 0);
		//addObject(0, 3188, 3783, 0, 10, 0);
		//addObject(0, 3191, 3784, 0, 10, 0);
		//addObject(-1, 3189, 3783, 0, 10, 0);
		/**
		 * @link objectFill objectId, SWX, SWY, NEX, NEY, type, face, height
		 */
		objectFill(-1, 3010, 3371, 3014, 3375, 10, 0, 0);
		objectFill(-1, 1748, 5322, 1755, 5327, 22, 0, 1);
		objectFill(14896, 3010, 3372, 3014, 3374, 10, 0, 0);
		objectFill(14896, 3011, 3371, 3013, 3371, 10, 0, 0);
		objectFill(14896, 3011, 3375, 3013, 3375, 10, 0, 0);

	}

	public static String fontFilter() {
		if (Configuration.newFonts) {
			return "_2";
		}
		return "";
	}

	public FileArchive interface_archive;
	public FileArchive mediaStreamLoader;

	private static GraphicsDefaults spriteIds;
	public static FontLoader fontLoader;

	void load() {
		try {
			int loadingProgress;
			if (Client.titleLoadingStage == 0) {
				drawLoadingText(1, "Starting game engine...");
				tileFlags = new byte[4][104][104];
				tileHeights = new int[4][105][105];
				scene = new SceneGraph(tileHeights);
				for (int j = 0; j < 4; j++) {
					collisionMaps[j] = new CollisionMap(104,104);
				}
				titleStreamLoader = loadArchive("title.dat");
				drawLoadingText(2, "Loading Fonts...");
				smallText = new TextDrawingArea(false, "p11_full" + fontFilter(), titleStreamLoader);
				XPFONT = new TextDrawingArea(true, "q8_full" + fontFilter(), titleStreamLoader);
				aTextDrawingArea_1271 = new TextDrawingArea(false, "p12_full" + fontFilter(), titleStreamLoader);
				chatTextDrawingArea = new TextDrawingArea(false, "b12_full", titleStreamLoader);
				drawLoadingText(3, "Loading Fonts...");
				aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full" + fontFilter(), titleStreamLoader);
				newSmallFont = new RSFont(false, "p11_full" + fontFilter(), titleStreamLoader);
				newRegularFont = new RSFont(false, "p12_full" + fontFilter(), titleStreamLoader);
				newBoldFont = new RSFont(false, "b12_full" + fontFilter(), titleStreamLoader);
				newFancyFont = new RSFont(true, "q8_full" + fontFilter(), titleStreamLoader);

				lato = new RSFont(true, "lato_full", titleStreamLoader);
				latoBold = new RSFont(true, "lato_bold_full", titleStreamLoader);
				kingthingsPetrock = new RSFont(true, "kingthings_petrock_full", titleStreamLoader);
				kingthingsPetrockLight = new RSFont(true, "kingthings_petrock_light_full", titleStreamLoader);

				interface_archive = loadArchive("interface.dat");
				this.mediaStreamLoader = loadArchive("media.dat");
				SpriteLoader1.loadSprites();
				cacheSprite1 = SpriteLoader1.sprites;
				SpriteLoader1.sprites = null;

				SpriteLoader2.loadSprites();
				cacheSprite2 = SpriteLoader2.sprites;
				SpriteLoader2.sprites = null;

				SpriteLoader3.loadSprites();
				cacheSprite3 = SpriteLoader3.sprites;
				SpriteLoader3.sprites = null;

				SpriteLoader4.loadSprites();
				cacheSprite4 = SpriteLoader4.sprites;
				SpriteLoader4.sprites = null;
				SpriteLoader1.load474Sprites();
				cacheSprite474 = SpriteLoader1.sprites474;
				Client.titleLoadingStage = 20;
			} else if (Client.titleLoadingStage == 20) {
				drawLoadingText(4, "Prepared visibility map");
				Client.titleLoadingStage = 30;
			} else if (Client.titleLoadingStage == 30) {
				Js5List.animations = Js5System.createJs5(Js5ArchiveIndex.ANIMATIONS, false, true, true, false);
				Js5List.skeletons = Js5System.createJs5(Js5ArchiveIndex.SKELETONS, false, true, true, false);
				Js5List.configs = Js5System.createJs5(Js5ArchiveIndex.CONFIGS, true, false, true, false);
				Js5List.interfaces = Js5System.createJs5(Js5ArchiveIndex.INTERFACES, false, true, true, false);
				Js5List.soundEffects = Js5System.createJs5(Js5ArchiveIndex.SOUNDEFFECTS, false, true, true, false);
				Js5List.maps = Js5System.createJs5(Js5ArchiveIndex.MAPS, true, true, true, false);
				Js5List.musicTracks = Js5System.createJs5(Js5ArchiveIndex.MUSIC_TRACKS, true, true, true, false);
				Js5List.models = Js5System.createJs5(Js5ArchiveIndex.MODELS, false, true, true, false);
				Js5List.sprites = Js5System.createJs5(Js5ArchiveIndex.SPRITES, false, true, true, false); //here it creates the socket to read from the disk
				Js5List.textures = Js5System.createJs5(Js5ArchiveIndex.TEXTURES, false, true, true, false);
				Js5List.binary = Js5System.createJs5(Js5ArchiveIndex.BINARY, false, true, true, false);
				Js5List.musicJingles = Js5System.createJs5(Js5ArchiveIndex.MUSIC_JINGLES, false, true, true, false);
				Js5List.clientScript = Js5System.createJs5(Js5ArchiveIndex.CLIENTSCRIPT, false, true, true, false);
				Js5List.fonts = Js5System.createJs5(Js5ArchiveIndex.FONTS, true, false, true, false);
				Js5List.musicSamples = Js5System.createJs5(Js5ArchiveIndex.MUSIC_SAMPLES, false, true, true, false);
				Js5List.musicPatches = Js5System.createJs5(Js5ArchiveIndex.MUSIC_PATCHES, false, true, true, false);
				Js5List.archive17 = Js5System.createJs5(Js5ArchiveIndex.ARCHIVE_17, true, true, true, false);
				Js5List.worldmapGeography = Js5System.createJs5(Js5ArchiveIndex.WORLDMAP_GEOGRAPHY, false, true, true, false);
				Js5List.worldmap = Js5System.createJs5(Js5ArchiveIndex.WORLDMAP, false, true, true, false);
				Js5List.worldmapGround = Js5System.createJs5(Js5ArchiveIndex.WORLDMAP_GROUND, false, true, true, false);
				Js5List.dbtableindex = Js5System.createJs5(Js5ArchiveIndex.DBTABLEINDEX, false, true, true, true);
				Js5List.index22 = Js5System.createJs5(Js5ArchiveIndex.DATS, false, true, true, true);
				drawLoadingText(5, "Connecting to update server");
				titleLoadingStage = 40;

			} else if (Client.titleLoadingStage == 40) {
				byte var24 = 0;
				loadingProgress = var24;
				loadingProgress += Js5List.animations.percentage() * 4 / 100;
				loadingProgress += Js5List.index22.percentage() / 100;
				loadingProgress += Js5List.skeletons.percentage() * 2 / 100;
				loadingProgress += Js5List.configs.percentage() / 100;
				loadingProgress += Js5List.interfaces.percentage() / 100;
				loadingProgress += Js5List.soundEffects.percentage() * 10 / 100;
				loadingProgress += Js5List.maps.percentage() * 4 / 100;
				loadingProgress += Js5List.musicTracks.percentage() * 2 / 100;
				loadingProgress += Js5List.models.percentage() * 54 / 100; // reduced by 1%
				loadingProgress += Js5List.sprites.percentage() * 2 / 100;
				loadingProgress += Js5List.textures.percentage() * 2 / 100;
				loadingProgress += Js5List.binary.percentage() * 2 / 100;
				loadingProgress += Js5List.musicJingles.percentage() * 2 / 100;
				loadingProgress += Js5List.clientScript.percentage() * 2 / 100;
				loadingProgress += Js5List.fonts.percentage() * 2 / 100;
				loadingProgress += Js5List.musicSamples.percentage() * 2 / 100;
				loadingProgress += Js5List.musicPatches.percentage() * 2 / 100;
				loadingProgress += (Js5List.archive17.isLoading() && Js5List.archive17.isFullyLoaded() ? 1 : 0);
				loadingProgress += Js5List.worldmapGeography.percentage() / 100;
				loadingProgress += Js5List.worldmap.percentage() / 100;
				loadingProgress += Js5List.worldmapGround.percentage() / 100;
				loadingProgress += Js5List.dbtableindex.percentage() / 100;


				if (loadingProgress != 100) {
					if (loadingProgress != 0) {
						drawLoadingText(10, "Checking for updates - " + loadingProgress + "%");
					}
				} else {
					Js5System.init(Js5List.animations, "Animations");
					Js5System.init(Js5List.skeletons, "Skeletons");
					Js5System.init(Js5List.soundEffects, "Sound FX");
					Js5System.init(Js5List.maps, "Maps");
					Js5System.init(Js5List.musicTracks, "Music Tracks");
					Js5System.init(Js5List.models, "Models");
					Js5System.init(Js5List.sprites, "Sprites");
					Js5System.init(Js5List.textures, "Textures");
					Js5System.init(Js5List.musicJingles, "Music Jingles");
					Js5System.init(Js5List.musicSamples, "Music Samples");
					Js5System.init(Js5List.musicPatches, "Music Patches");
					Js5System.init(Js5List.worldmap, "World Map");
					Js5System.init(Js5List.worldmapGeography, "World Map Geography");
					Js5System.init(Js5List.worldmapGround, "World Map Ground");
					Js5System.init(Js5List.index22, "Animation Keyframes");

					spriteIds = new GraphicsDefaults();
					spriteIds.decode(Js5List.archive17);
					drawLoadingText(15, "Loaded update list");
					Client.titleLoadingStage = 45;
				}
			} else if (Client.titleLoadingStage == 45) {
				StaticSound.setup(!Client.lowMem, Js5List.soundEffects, Js5List.musicTracks, Js5List.musicJingles, Js5List.musicSamples, Js5List.musicPatches);
				drawLoadingText(20, "Prepared sound engine");
				Client.titleLoadingStage = 50;
			} else if (Client.titleLoadingStage == 50) {
				loadingProgress = FontInfo.initialFonts().length;
				fontLoader = new FontLoader();
				fontLoader.createMap();
				if (fontLoader.fonts.size() != loadingProgress) {
					drawLoadingText(25,"Loading fonts - " + fontLoader.fonts.size() * 100 / loadingProgress + "%");
				} else {
					smallFontOSRS = fontLoader.fonts.get(FontInfo.SMALL_FONT);
					regularFontOSRS = fontLoader.fonts.get(FontInfo.REGULAR_FONT);
					boldFontOSRS = fontLoader.fonts.get(FontInfo.BOLD_FONT);
					fancyFontOSRS = fontLoader.fonts.get(FontInfo.FANCY_SMALL);
					fancyFontMediumOSRS = fontLoader.fonts.get(FontInfo.FANCY_MEDIUM);
					fancyFontLargeOSRS = fontLoader.fonts.get(FontInfo.FANCY_CAPS_LARGE);

					drawLoadingText(25, "Loading fonts");
					Client.titleLoadingStage = 60;
				}

			} else {
				int var3;
				if (Client.titleLoadingStage == 60) {
					drawLoadingText(30, "Loaded title screen");
					setGameState(5);
					//setStartupActions();
					Client.titleLoadingStage = 70;
				} else if (Client.titleLoadingStage == 70) {
					if (!Js5List.configs.isFullyLoaded()) {
						drawLoadingText(40, "Loading config - " + Js5List.configs.loadPercent() + "%");
					} else if (!Js5List.configs.isFullyLoaded()) {
						drawLoadingText(40, "Loading config - " + (80 + Js5List.clientScript.loadPercent() / 6) + "%");
					} else {
						Js5List.initConfigSizes();
						NpcDefinition.init(spriteIds.headIconArchive);

						if (Js5List.configs.isFullyLoaded()) {
							AreaDefinition.definitions = new AreaDefinition[Js5List.getConfigSize(Js5ConfigType.AREA)];

							for (int count = 0; count < Js5List.getConfigSize(Js5ConfigType.AREA); ++count) {
								byte[] fileData = Js5List.configs.takeFile(Js5ConfigType.AREA, count);
								AreaDefinition.definitions[count] = new AreaDefinition(count);
								if (fileData != null) {
									AreaDefinition.definitions[count].decode(new Buffer(fileData));
									AreaDefinition.definitions[count].init();
								}
							}
						}


						drawLoadingText(45, "Loaded config");
						Client.titleLoadingStage = 80;
					}
				} else if (Client.titleLoadingStage == 80) {
					loadingProgress = 0;

					if (mapSceneSprites == null) {
						mapSceneSprites = IndexedImage.loadImages(spriteIds.mapScenes, 0);
					} else {
						++loadingProgress;
					}
					if (crosses == null) {
						crosses = Sprite.getSprites(spriteIds.field4589, 0);
					} else {
						++loadingProgress;
					}
					++loadingProgress;
					drawLoadingText(50, "Loaded Sprites");
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					drawLoadingText(52, "Loaded Sprites");
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					++loadingProgress;
					drawLoadingText(54, "Loaded Sprites");
					++loadingProgress;

					++loadingProgress;
					drawLoadingText(55, "Loaded Sprites");
					if (loadingProgress < 14) {
						drawLoadingText(70, "Loading sprites - " + loadingProgress * 100 / 13 + "%");
					} else {
						int var1 = (int) (Math.random() * 21.0) - 10;
						int var2 = (int) (Math.random() * 21.0) - 10;
						var3 = (int) (Math.random() * 21.0) - 10;
						int var4 = (int) (Math.random() * 41.0) - 20;
						if (mapSceneSprites != null && mapSceneSprites.length > 0 && mapSceneSprites[0] != null) {
							mapSceneSprites[0].offsetColor(var1 + var4, var2 + var4, var4 + var3);
						}
						drawLoadingText(60, "Loading sprites");
						Client.titleLoadingStage = 90;
					}
				} else if (Client.titleLoadingStage == 90) {
					if (!Js5List.textures.isFullyLoaded()) {
						drawLoadingText(65, "Loading textures - 0%");
					} else {
						TextureProvider textureProvider = new TextureProvider(Js5List.textures, Js5List.sprites, 20, 0.80000000000000004D, lowMem ? 64 : 128);
						Rasterizer3D.setTextureLoader(textureProvider);
						Rasterizer3D.setBrightness(0.80000000000000004D);
						textureProvider.getLoadedPercentage();
						drawLoadingText(69, "Loading textures");
						Client.titleLoadingStage = 95;
					}
				} else if (Client.titleLoadingStage == 95) {
					if (!Js5List.maps.isFullyLoaded()) {
						drawLoadingText(70, "Loading maps - " + Js5List.maps.percentage() + "%");
					} else {
						drawLoadingText(70, "Loading maps");
						Client.titleLoadingStage = 100;

					}
				} else if (Client.titleLoadingStage == 100) {
					drawLoadingText(72, "Loading textures");
					Client.titleLoadingStage = 110;
				} else if (Client.titleLoadingStage == 110) {
					drawLoadingText(74, "Loading input handler");
					Client.titleLoadingStage = 120;
				} else if (Client.titleLoadingStage == 120) {
					drawLoadingText(76, "Loaded wordpack");
					Client.titleLoadingStage = 130;
				} else if (Client.titleLoadingStage == 130) {
					drawLoadingText(77, "Loaded interfaces");
					TextDrawingArea allFonts[] = {smallText, aTextDrawingArea_1271, chatTextDrawingArea, aTextDrawingArea_1273};
					RSInterface.unpack(interface_archive, allFonts, mediaStreamLoader, new RSFont[]{newSmallFont, newRegularFont, newBoldFont, newFancyFont});
					Client.titleLoadingStage = 140;
				} else if (Client.titleLoadingStage == 140) {
					drawLoadingText(80, "Loaded world map");
					Client.titleLoadingStage = 150;
				} else if (Client.titleLoadingStage == 150) {
					try {
						HoverMenuManager.init();
						SettingsManager.loadSettings();
						ClientScripts.load();

						if (Configuration.dumpDataLists) {
							//NpcDefinition.dumpList();
							//ObjectDefinition.dumpList();
							//resourceProvider.dumpModels();
							TempWriter writer2 = new TempWriter("item_fields");
							FieldGenerator generator = new FieldGenerator(writer2::writeLine);
							IntStream.range(0, 100_000).forEach(id2 -> {
								try {
									ItemDefinition definition = ItemDefinition.lookup(id2);
									generator.add(definition.name + (definition.certTemplateID != -1 ? " noted" : ""), id2);
								} catch (Exception e) {
								}
							});
							writer2.close();
							TempWriter npcvwriter2 = new TempWriter("npc_fields");
							FieldGenerator npcgenerator = new FieldGenerator(npcvwriter2::writeLine);
							IntStream.range(0, 100_000).forEach(id -> {
								try {
									NpcDefinition definition = NpcDefinition.get(id);
									npcgenerator.add(definition.name, id);
								} catch (Exception e) {}
							});
							npcvwriter2.close();
								try {


									try (BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/animation_lengths.cfg"))) {
										IntStream.range(0, 100_000).forEach(id -> {
													SequenceDefinition definition = SequenceDefinition.get(id);
													if (definition.frameLengths != null && definition.frameLengths.length > 0) {
														int sum = 0;
														for (int i = 0; i < definition.frameLengths.length; i++) {
															if (definition.frameLengths[i] < 100) {
																sum += definition.frameLengths[i];
															}
														}
                                                        try {
                                                            writer.write(id + ":" + sum);
                                                        } catch (IOException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                        try {
                                                            writer.newLine();
                                                        } catch (IOException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
												});
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
						}

						variousSettings[304] = 1;
						drawLoadingText(82, "Loading Engine");
						//drawLoadingText(10, "Loading Packed Sprites...");
						try {
							ItemDef.load();

							mapIcon7 = new Sprite(mediaStreamLoader, "mapfunction", 1);
							mapIcon8 = new Sprite(mediaStreamLoader, "mapfunction", 51);
							mapIcon6 = new Sprite(mediaStreamLoader, "mapfunction", 74);
							mapIcon5 = new Sprite(mediaStreamLoader, "mapfunction", 5);
							mapIcon9 = new Sprite(mediaStreamLoader, "mapfunction", 56);
							multiOverlay = new Sprite(mediaStreamLoader, "overlay_multiway", 0);
							grandExchangeSprite0 = new Sprite("Grand Exchange/0");
							grandExchangeSprite1 = new Sprite("Grand Exchange/1");
							grandExchangeSprite2 = new Sprite("Grand Exchange/2");
							grandExchangeSprite3 = new Sprite("Grand Exchange/3");
							grandExchangeSprite4 = new Sprite("Grand Exchange/4");
							drawLoadingText(84, "Loading Engine");
							moneyPouch = new Sprite("/Interfaces/Wiki/964");
							moneyPouch1 = new Sprite("/Interfaces/Wiki/965");
							moneyPouchCoins = new Sprite("/Interfaces/Wiki/963");
							xpbg1 = new Sprite("487");
							xpbg2 = new Sprite("488");
							eventIcon = new Sprite(mediaStreamLoader, "mapfunction", 72);
							bankDivider = new Sprite("bank_divider");
							minimapImage = new Sprite(512, 512);

							loginAsset0 = new Sprite("/loginscreen2/remember_normal"); //Remeber_0
							loginAsset1 = new Sprite("/loginscreen2/remember_hover"); //Remember_1
							loginAsset2 = null; //Remember_2
							loginAsset3 = null; //Remember_3
							loginAsset4 = null; // LOGO
							loginAsset5 = new Sprite("/loginscreen2/login_box"); // Login_Box
							loginAsset6 = new Sprite("/loginscreen2/textfield_normal"); // Input_Box
							loginAsset7 = new Sprite("/loginscreen2/textfield_hover"); // Input_Box_Hover
							loginAsset8 = new Sprite("/loginscreen2/textfield_normal"); // Input_Box_2
							loginAsset9 = new Sprite("/loginscreen2/textfield_hover"); // Input_Box_Hover_2
							loginAsset10 = new Sprite("/loginscreen2/login_normal"); // Play_Now
							loginAsset11 = new Sprite("/loginscreen2/login_hover"); // Play_Now_Hover
							loginAsset12 = new Sprite("/loginscreen2/account_save_normal");
							loginAsset13 = new Sprite("/loginscreen2/account_save_hover");
							loginAsset14 = new Sprite("/loginscreen2/account_save_normal");
							loginAsset15 = new Sprite("/loginscreen2/account_save_hover");
							loginAsset16 = new Sprite("/loginscreen2/account_save_normal");
							loginAsset17 = new Sprite("/loginscreen2/account_save_hover");
							loginsprites =  new Sprite("/loginscreen/background");
							drawLoadingText(86, "Loading Engine");
							loginScreenBackground = new Sprite("/loginscreen/background2");
							logo2021 = new Sprite("/loginscreen/logo");
							loginScreenBackgroundCaptcha = new Sprite("/loginscreen/captcha_background");
							captchaExit = new Sprite("/loginscreen/captcha-exit");
							captchaExitHover = new Sprite("/loginscreen/captcha-exit-hover");

							cacheSprite = new Sprite[684];
							for (int i = 0; i < 684; i++) {
								cacheSprite[i] = new Sprite("Sprites/" + i);
							}
							drawLoadingText(87, "Loading Engine");
							xpSprite = new Sprite("medal");
							for (int i = 0; i < inputSprites.length; i++)
								inputSprites[i] = new Sprite("Interfaces/Inputfield/SPRITE " + (i + 1));

							for (int i = 0; i < tabAreaResizable.length; i++)
								tabAreaResizable[i] = new Sprite("Gameframe/resizable/tabArea " + i);

							loadTabArea();
							drawLoadingText(89, "Loading Engine");
							infinity = new Sprite("infinity");
							chatArea = new Sprite("Gameframe/chatarea");
							channelButtons = new Sprite("Gameframe/channelbuttons");
							venomOrb = new Sprite("orbs/venom");

							for (int index = 0; index < smallXpSprites.length; index++) {
								smallXpSprites[index] = new Sprite("expdrop/" + index);

							}

							for (int c1 = 0; c1 <= 3; c1++)
								chatButtons[c1] = new Sprite(mediaStreamLoader, "chatbuttons", c1);
							chatButtons[3] = new Sprite("1025_0");
							Sprite[] clanIcons = new Sprite[9];
							for (int index = 0; index < clanIcons.length; index++) {
								clanIcons[index] = new Sprite("Clan Chat/Icons/" + index);
							}
							drawLoadingText(90, "Loading Engine");
							Sprite[] iconPack = new Sprite[229];
							for (int index = 0; index < iconPack.length; index++) {
								iconPack[index] = new Sprite("icon_pack/" + index);
							}

							RSFont.unpackImages(modIconss, clanIcons, iconPack);

							mapEdge = new Sprite(mediaStreamLoader, "mapedge", 0);
							mapEdge.method345();

							try {
								for (int l3 = 0; l3 < 119; l3++)
									mapFunctions[l3] = new Sprite("MapFunctions/" + l3);
							} catch (Exception _ex) {
							}
							drawLoadingText(92, "Loading Engine");
							try {
								for (int i4 = 0; i4 < 20; i4++) {
									hitMarks[i4] = new Sprite(mediaStreamLoader, "hitmarks", i4);
								}
							} catch (Exception _ex) {
							}

							hitMarks[19] = new Sprite("heal_hitsplat");

							try {
								for (int h1 = 0; h1 < 6; h1++)
									headIconsHint[h1] = new Sprite(mediaStreamLoader, "headicons_hint", h1);
							} catch (Exception _ex) {
							}
							try {
								for (int j4 = 0; j4 < 18; j4++)
									headIcons[j4] = new Sprite(mediaStreamLoader, "headicons_prayer", j4);
							} catch (Exception _ex) {
							}
							try {
								for (int j45 = 0; j45 < 10; j45++)
									skullIcons[j45] = new Sprite(mediaStreamLoader, "headicons_pk", j45);
							} catch (Exception _ex) {
							}
							for (int i = 0; i < minimapIcons.length; i++) {
								minimapIcons[i] = new Sprite("Mapicons/ICON " + i);
							}
							drawLoadingText(94, "Loading Engine");
							mapFlag = new Sprite(mediaStreamLoader, "mapmarker", 0);
							mapMarker = new Sprite(mediaStreamLoader, "mapmarker", 1);

							mapDotItem = new Sprite(mediaStreamLoader, "mapdots", 0);
							mapDotNPC = new Sprite(mediaStreamLoader, "mapdots", 1);
							mapDotPlayer = new Sprite(mediaStreamLoader, "mapdots", 2);
							mapDotFriend = new Sprite(mediaStreamLoader, "mapdots", 3);
							mapDotTeam = new Sprite(mediaStreamLoader, "mapdots", 4);

							mapDotClan = new Sprite(mediaStreamLoader, "mapdots", 5);
							minimapDot = new Sprite[]{mapDotItem, mapDotNPC, mapDotPlayer, mapDotFriend, mapDotTeam, mapDotClan, null};
							new Sprite(mediaStreamLoader, "mapdots", 4);
							scrollBar1 = new Sprite(mediaStreamLoader, "scrollbar", 0);
							scrollBar2 = new Sprite(mediaStreamLoader, "scrollbar", 1);
							for (int i = 0; i < modIconss.length; i++) {
								modIconss[i] = new Sprite("Player/MODICONS " + i + "");
							}

							for (int index = 0; index < GameTimerHandler.TIMER_IMAGES.length; index++) {
								GameTimerHandler.TIMER_IMAGES[index] = new Sprite("GameTimer/TIMER " + index);
							}
							drawLoadingText(95, "Loading Engine");
							loadPlayerData();

							drawLoadingText(96, "Preparing game engine");
							new Sprite("Gameframe317/fixed/mapBack");
							new Sprite("Gameframe/fixed/mapBack");
							if (getUserSettings().isOldGameframe() == false) {
								mapBack = new Sprite("Gameframe/fixed/mapBack");
							} else {
								mapBack = new Sprite("Gameframe317/fixed/mapBack");
							}
							drawLoadingText(97, "Preparing game engine");
							for (int pixelY = 0; pixelY < 33; pixelY++) {
								int k6 = 999;
								int i7 = 0;
								for (int pixelX = 0; pixelX < 34; pixelX++) {
									if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0) {
										if (k6 == 999)
											k6 = pixelX;
										continue;
									}
									if (k6 == 999)
										continue;
									i7 = pixelX;
									break;
								}
								anIntArray968[pixelY] = k6;
								anIntArray1057[pixelY] = i7 - k6;
							}
							drawLoadingText(99, "Preparing game engine");
							for (int pixelY = 1; pixelY < 153; pixelY++) {
								int j7 = 999;
								int l7 = 0;
								for (int pixelX = 24; pixelX < 177; pixelX++) {
									if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0 && (pixelX > 34 || pixelY > 34)) {
										if (j7 == 999) {
											j7 = pixelX;
										}
										continue;
									}
									if (j7 == 999) {
										continue;
									}
									l7 = pixelX;
									break;
								}
								anIntArray1052[pixelY - 1] = j7 - 24;
								anIntArray1229[pixelY - 1] = l7 - j7;
							}
							drawLoadingText(99, "Preparing game engine");
							try {
								macAddress = GetNetworkAddress.GetAddress("mac");
								if (macAddress == null)
									macAddress = "";
								if (Configuration.developerMode) {
									System.out.println(macAddress);
								}
							} catch (Exception e) {
								e.printStackTrace();
								macAddress = "";
							}

							setConfigButtonsAtStartup();
							try {
								informationFile.read();
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (informationFile.isUsernameRemembered()) {
								myUsername = informationFile.getStoredUsername();
							}
							if (informationFile.isPasswordRemembered()) {
								setPassword(informationFile.getStoredPassword());
							}
							if (informationFile.isRememberRoof()) {
								removeRoofs = true;
							}
							drawLoadingText(100, "Preparing game engine");

							ObjectDefinition.clientInstance = this;
							NpcDefinition.clientInstance = this;

							if (Configuration.PRINT_EMPTY_INTERFACE_SECTIONS) {
								if (Configuration.developerMode) {
									RSInterface.printEmptyInterfaceSections();
								} else {
									System.err.println("PRINT_EMPTY_INTERFACE_SECTIONS is toggled but you must be in dev mode.");
								}
							}

							Preferences.load();

							Client.titleLoadingStage = 210;
							loginScreen.setup();

							loginScreen.setLoginState(LoginState.LOGIN);
							setGameState(GameState.LOGIN_SCREEN);
							return;
						} catch (Exception exception) {
							exception.printStackTrace();
							Signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	static int titleLoadingStage;
	public int loadingPercent;
	public String loadingText;
	public static boolean isLoading = true;

	public static Sprite LOGO = new Sprite(AssetUtils.INSTANCE.getResource("2240.png"));
	public Sprite test45; // Initialized later after Js5 configs are loaded


	public void method91(Buffer stream, int i) {
		while (stream.bitPosition + 10 < i * 8) {
			int j = stream.readBits(11);
			if (j == 2047)
				break;
			if (players[j] == null) {
				players[j] = new Player();
				if (aStreamArray895s[j] != null)
					players[j].updatePlayer(aStreamArray895s[j]);
			}
			playerIndices[playerCount++] = j;
			Player player = players[j];
			player.lastUpdateTick = loopCycle;
			int k = stream.readBits(1);
			if (k == 1)
				anIntArray894[anInt893++] = j;
			int l = stream.readBits(1);
			int i1 = stream.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = stream.readBits(5);
			if (j1 > 15)
				j1 -= 32;
			player.setPos(localPlayer.pathX[0] + j1, localPlayer.pathY[0] + i1, l == 1);
		}
		stream.finishBitAccess();
	}

	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
		return Math.pow((circleX + radius - clickX), 2)
				+ Math.pow((circleY + radius - clickY), 2) < Math.pow(radius, 2);
	}

	public boolean mouseMapPosition() {
		if (MouseHandler.mouseX >= canvasWidth - 21 && MouseHandler.mouseX <= canvasWidth && MouseHandler.mouseY >= 0
				&& MouseHandler.mouseY <= 21) {
			return false;
		}
		return true;
	}

	void processMainScreenClick() {
		if (minimapState != 0)
			return;
		if (clickMode3 == 1) {
			final boolean fixed = !isResized();
			if (fixed ? MouseHandler.saveClickX >= 542 && MouseHandler.saveClickX <= 579 && MouseHandler.saveClickY >= 2 && MouseHandler.saveClickY <= 38
				: MouseHandler.saveClickX >= Client.canvasWidth - 180 && MouseHandler.saveClickX <= Client.canvasWidth - 139
				&& MouseHandler.saveClickY >= 0 && MouseHandler.saveClickY <= 40) {
				setNorth();
				return;
			}

			int i = MouseHandler.saveClickX - 25 - 547;
			int j = MouseHandler.saveClickY - 5 - 3;
			if (isResized()) {
				i = MouseHandler.saveClickX - (canvasWidth - 182 + 24);
				j = MouseHandler.saveClickY - 8;
			}
			if (inCircle(0, 0, i, j, 76) && mouseMapPosition() && !runHover) {
				i -= 73;
				j -= 75;
				int k = viewRotation + camAngleY & 0x7ff;
				int i1 = Rasterizer3D.Rasterizer3D_sine[k];
				int j1 = Rasterizer3D.Rasterizer3D_cosine[k];
				i1 = i1 * (minimapZoom + 256) >> 8;
				j1 = j1 * (minimapZoom + 256) >> 8;
				int k1 = j * i1 + i * j1 >> 11;
				int l1 = j * j1 - i * i1 >> 11;
				int i2 = localPlayer.x + k1 >> 7;
				int j2 = localPlayer.y - l1 >> 7;
				if (localPlayer.isAdminRights() && controlIsDown) {
					teleport(baseX + i2, baseY + j2);
				} else {
					boolean flag1 = doWalkTo(1, localPlayer.pathX[0], localPlayer.pathY[0], 0, 0, 0, 0, 0, j2, true, i2);
					if (flag1) {
						stream.writeUnsignedByte(i);
						stream.writeUnsignedByte(j);
						stream.writeWord(viewRotation);
						stream.writeUnsignedByte(57);
						stream.writeUnsignedByte(camAngleY);
						stream.writeUnsignedByte(minimapZoom);
						stream.writeUnsignedByte(89);
						stream.writeWord(localPlayer.x);
						stream.writeWord(localPlayer.y);
						stream.writeUnsignedByte(anInt1264);
						stream.writeUnsignedByte(63);
					}
				}
			}
			anInt1117++;
			if (anInt1117 > 1151) {
				anInt1117 = 0;
				stream.createFrame(246);
				stream.writeUnsignedByte(0);
				int l = stream.currentPosition;
				if ((int) (Math.random() * 2D) == 0)
					stream.writeUnsignedByte(101);
				stream.writeUnsignedByte(197);
				stream.writeWord((int) (Math.random() * 65536D));
				stream.writeUnsignedByte((int) (Math.random() * 256D));
				stream.writeUnsignedByte(67);
				stream.writeWord(14214);
				if ((int) (Math.random() * 2D) == 0)
					stream.writeWord(29487);
				stream.writeWord((int) (Math.random() * 65536D));
				if ((int) (Math.random() * 2D) == 0)
					stream.writeUnsignedByte(220);
				stream.writeUnsignedByte(180);
				stream.writeBytes(stream.currentPosition - l);
			}
		}
	}
	/**
	 * Get the String residing on the clipboard.
	 *
	 * @return any text found on the Clipboard; if none found, return an empty
	 *         String.
	 */
	public static String getClipboardContents() {
		if (System.currentTimeMillis() - timer < 2000) {
			return "";
		}
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
				timer = System.currentTimeMillis();
			} catch (UnsupportedFlavorException | IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static int getChatInputLimit(String input) {
		if (input != null && (input.startsWith("/") || input.startsWith("::"))) {
			return MAX_CHAT_INPUT_LENGTH;
		}
		return DEFAULT_CHAT_INPUT_LENGTH;
	}

	public static String sanitizeChatInput(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}
		String normalized = text.replace('\r', ' ')
				.replace('\n', ' ')
				.replace('\t', ' ')
				.replace('\u2018', '\'')
				.replace('\u2019', '\'')
				.replace('\u201C', '"')
				.replace('\u201D', '"')
				.replace('\u2013', '-')
				.replace('\u2014', '-');
		StringBuilder sb = new StringBuilder(normalized.length());
		for (int i = 0; i < normalized.length(); i++) {
			char ch = normalized.charAt(i);
			if (ch >= 32 && ch <= 126) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	static long timer = 0;

	private void teleport(int x, int y) {
		String text = "::tele " + x + " " + y;
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void teleportInterface() {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = "::teleport";
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void closeInterfacee() {
		String text = "::close";
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public void closeInterface() { interfaceManager.closeInterface(); }

	@Nullable
	@Override
	public NPC getFollower() {
		return null;
	}

	public static void continueDialogue() {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = "::dialoguecontinuation continue";
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	public static void dialogueOptions(String option) {
		if (System.currentTimeMillis() - timer < 400) {
			return;
		}
		timer = System.currentTimeMillis();
		String text = option == "one" ? "::dialoguecontinuation option_one"
				: option == "two" ? "::dialoguecontinuation option_two"
				: option == "three" ? "::dialoguecontinuation option_three"
				: option == "four" ? "::dialoguecontinuation option_four"
				: "::dialoguecontinuation option_five";
		stream.createFrame(103);
		stream.writeWordBigEndian(text.length() - 1);
		stream.writeString(text.substring(2));
	}

	private String interfaceIntToString(int j) { return interfaceManager.interfaceIntToString(j); }

	@Override
	public URL getCodeBase() {
		try {
			return new URL(server + ":" + (80 + portOff));
		} catch (Exception _ex) {
		}
		return null;
	}

	public void method95() {
		for (int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			Npc npc = npcs[k];
			if (npc != null)
				entityUpdateBlock(npc);
		}
	}

	public void entityUpdateBlock(Entity entity) {
		if (entity.x < 128 || entity.y < 128 || entity.x >= 13184 || entity.y >= 13184) {
			entity.sequence = -1;
			entity.initiate_movement = 0;
			entity.cease_movement = 0;
			entity.clearSpotAnimations();
			entity.x = entity.pathX[0] * 128 + entity.size * 64;
			entity.y = entity.pathY[0] * 128 + entity.size * 64;
			entity.method446();
		}
		if (entity == localPlayer && (entity.x < 1536 || entity.y < 1536 || entity.x >= 11776 || entity.y >= 11776)) {
			entity.sequence = -1;
			entity.initiate_movement = 0;
			entity.cease_movement = 0;
			entity.clearSpotAnimations();
			entity.x = entity.pathX[0] * 128 + entity.size * 64;
			entity.y = entity.pathY[0] * 128 + entity.size * 64;
			entity.method446();
		}
		if (entity.initiate_movement > loopCycle)
			method97(entity);
		else if (entity.cease_movement >= loopCycle)
			method98(entity);
		else
			method99(entity);
		method100(entity);
		entity.updateAnimation();
	}

	public void method97(Entity entity) {
		int i = entity.initiate_movement - loopCycle;
		int j = entity.anInt1543 * 128 + entity.size * 64;
		int k = entity.anInt1545 * 128 + entity.size * 64;
		entity.x += (j - entity.x) / i;
		entity.y += (k - entity.y) / i;
		entity.anInt1503 = 0;
		if (entity.forceMovementDirection == 0)
			entity.setTurnDirection(1024);
		if (entity.forceMovementDirection == 1)
			entity.setTurnDirection(1536);
		if (entity.forceMovementDirection == 2)
			entity.setTurnDirection(0);
		if (entity.forceMovementDirection == 3)
			entity.setTurnDirection(512);
	}

	public void method98(Entity entity) {
		if (entity.cease_movement == loopCycle || entity.sequence == -1 || entity.sequenceDelay != 0
				|| entity.sequenceFrameCycle + 1 > SequenceDefinition.get(entity.sequence).duration(entity.sequenceFrame)) {
			int i = entity.cease_movement - entity.initiate_movement;
			int j = loopCycle - entity.initiate_movement;
			int k = entity.anInt1543 * 128 + entity.size * 64;
			int l = entity.anInt1545 * 128 + entity.size * 64;
			int i1 = entity.anInt1544 * 128 + entity.size * 64;
			int j1 = entity.anInt1546 * 128 + entity.size * 64;
			entity.x = (k * (i - j) + i1 * j) / i;
			entity.y = (l * (i - j) + j1 * j) / i;
		}
		entity.anInt1503 = 0;
		if (entity.forceMovementDirection == 0)
			entity.setTurnDirection(1024);
		if (entity.forceMovementDirection == 1)
			entity.setTurnDirection(1536);
		if (entity.forceMovementDirection == 2)
			entity.setTurnDirection(0);
		if (entity.forceMovementDirection == 3)
			entity.setTurnDirection(512);
		entity.orientation = entity.getTurnDirection();
	}

	public void method99(Entity entity) {
		entity.movementSequence = entity.idleSequence;
		if (entity.smallXYIndex == 0) {
			entity.anInt1503 = 0;
			return;
		}
		if (entity.sequence != -1 && entity.sequenceDelay == 0) {
			SequenceDefinition animation = SequenceDefinition.get(entity.sequence);
			if (entity.stationaryPathPosition > 0 && animation.moveStyle == 0) {
				entity.anInt1503++;
				return;
			}
			if (entity.stationaryPathPosition <= 0 && animation.idleStyle == 0) {
				entity.anInt1503++;
				return;
			}
		}
		int i = entity.x;
		int j = entity.y;
		int k = entity.pathX[entity.smallXYIndex - 1] * 128 + entity.size * 64;
		int l = entity.pathY[entity.smallXYIndex - 1] * 128 + entity.size * 64;
		if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
			entity.x = k;
			entity.y = l;
			return;
		}
		if (i < k) {
			if (j < l)
				entity.setTurnDirection(1280);
			else if (j > l)
				entity.setTurnDirection(1792);
			else
				entity.setTurnDirection(1536);
		} else if (i > k) {
			if (j < l)
				entity.setTurnDirection(768);
			else if (j > l)
				entity.setTurnDirection(256);
			else
				entity.setTurnDirection(512);
		} else if (j < l)
			entity.setTurnDirection(1024);
		else
			entity.setTurnDirection(0);
		int i1 = entity.getTurnDirection() - entity.orientation & 0x7ff;
		if (i1 > 1024)
			i1 -= 2048;
		int j1 = entity.walkBackSequence;
		if (i1 >= -256 && i1 <= 256)
			j1 = entity.walkSequence;
		else if (i1 >= 256 && i1 < 768)
			j1 = entity.walkRightSequence;
		else if (i1 >= -768 && i1 <= -256)
			j1 = entity.walkLeftSequence;
		if (j1 == -1)
			j1 = entity.walkSequence;
		entity.movementSequence = j1;
		int k1 = 4;
		if (entity.orientation != entity.getTurnDirection() && entity.interactingEntity == -1 && entity.rotation != 0)
			k1 = 2;
		if (entity.smallXYIndex > 2)
			k1 = 6;
		if (entity.smallXYIndex > 3)
			k1 = 8;
		if (entity.anInt1503 > 0 && entity.smallXYIndex > 1) {
			k1 = 8;
			entity.anInt1503--;
		}
		if (entity.aBooleanArray1553[entity.smallXYIndex - 1])
			k1 <<= 1;
		if (k1 >= 8 && entity.movementSequence == entity.walkSequence && entity.runAnimIndex != -1)
			entity.movementSequence = entity.runAnimIndex;
		if (i < k) {
			entity.x += k1;
			if (entity.x > k)
				entity.x = k;
		} else if (i > k) {
			entity.x -= k1;
			if (entity.x < k)
				entity.x = k;
		}
		if (j < l) {
			entity.y += k1;
			if (entity.y > l)
				entity.y = l;
		} else if (j > l) {
			entity.y -= k1;
			if (entity.y < l)
				entity.y = l;
		}
		if (entity.x == k && entity.y == l) {
			entity.smallXYIndex--;
			if (entity.stationaryPathPosition > 0)
				entity.stationaryPathPosition--;
		}
	}

	public void method100(Entity entity) {
		if (entity.rotation == 0)
			return;
		if (entity.interactingEntity != -1 && entity.interactingEntity < 32768) {
			Npc npc = npcs[entity.interactingEntity];
			if (npc != null) {
				int i1 = entity.x - npc.x;
				int k1 = entity.y - npc.y;
				if (i1 != 0 || k1 != 0)
					entity.setTurnDirection((int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff);
			}
		}
		if (entity.interactingEntity >= 32768) {
			int j = entity.interactingEntity - 32768;
			if (j == localPlayerIndex)
				j = maxPlayerCount;
			Player player = players[j];
			if (player != null) {
				int l1 = entity.x - player.x;
				int i2 = entity.y - player.y;
				if (l1 != 0 || i2 != 0)
					entity.setTurnDirection((int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff);
			}
		}
		if ((entity.anInt1538 != 0 || entity.anInt1539 != 0) && (entity.smallXYIndex == 0 || entity.anInt1503 > 0)) {
			int k = entity.x - (entity.anInt1538 - baseX - baseX) * 64;
			int j1 = entity.y - (entity.anInt1539 - baseY - baseY) * 64;
			if (k != 0 || j1 != 0) {
				entity.setTurnDirection((int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff);
			}
			entity.anInt1538 = 0;
			entity.anInt1539 = 0;
		}
		int l = entity.getTurnDirection() - entity.orientation & 0x7ff;
		if (l != 0) {
			if (l < entity.rotation || l > 2048 - entity.rotation)
				entity.orientation = entity.getTurnDirection();
			else if (l > 1024)
				entity.orientation -= entity.rotation;
			else
				entity.orientation += entity.rotation;
			entity.orientation &= 0x7ff;
			if (entity.movementSequence == entity.idleSequence && entity.orientation != entity.getTurnDirection()) {
				if (entity.readyanim_l != -1) {
					entity.movementSequence = entity.readyanim_l;
					return;
				}
				entity.movementSequence = entity.walkSequence;
			}
		}
	}

	boolean buildFriendsListMenu(RSInterface class9) {
		int i = class9.contentType;
		if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
			if (i >= 801)
				i -= 701;
			else if (i >= 701)
				i -= 601;
			else if (i >= 101)
				i -= 101;
			else
				i--;
			if (tabInterfaceIDs[tabID] != 42500) {
				insertMenuItemNoShift("Remove" + " @whi@" + friendsList[i], MenuAction.REMOVE2);
				insertMenuItemNoShift("Message" + " @whi@" + friendsList[i], MenuAction.MESSAGE2);
			}

			return true;
		}
		if (i >= 401 && i <= 500) {
			insertMenuItemNoShift("Remove" + " @whi@" + class9.message, MenuAction.REMOVE1);
			return true;
		} else {
			return false;
		}
	}

	int hoverId;

	public void method104() {
		for (SpotAnimEntity spotAnimationEntity = (SpotAnimEntity) incompleteAnimables.last(); null != spotAnimationEntity; spotAnimationEntity = (SpotAnimEntity) incompleteAnimables.previous()) {
			if (plane == spotAnimationEntity.z && !spotAnimationEntity.expired) {
				if (loopCycle >= spotAnimationEntity.cycleStart) {
					spotAnimationEntity.step(tickDelta);
					if (spotAnimationEntity.expired) {
						spotAnimationEntity.unlink();
					} else {
						int radius = 60;
						int orientation = 0;
						boolean drawFrontTilesFirst = false;
						if (spotAnimationEntity instanceof RSRuneLiteObject)
						{
							RSRuneLiteObject rSRuneLiteObject = (RSRuneLiteObject)spotAnimationEntity;
							radius = rSRuneLiteObject.getRadius();
							orientation = rSRuneLiteObject.getOrientation();
							drawFrontTilesFirst = rSRuneLiteObject.drawFrontTilesFirst();
						}

						scene.add_entity(spotAnimationEntity.z, orientation, spotAnimationEntity.height, -1,
								spotAnimationEntity.y, radius, spotAnimationEntity.x,
								spotAnimationEntity, drawFrontTilesFirst);
					}
				}
			} else {
				spotAnimationEntity.unlink();
			}
		}
	}

	public int dropdownInversionFlag;

	public Sprite grandExchangeSprite0,
			grandExchangeSprite1,
			grandExchangeSprite2,
			grandExchangeSprite3,
			grandExchangeSprite4;

	private int interfaceDrawY;
	public boolean interfaceText = false;
	public boolean interfaceStringText = false;

	public static final int[] runeChildren = { 1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228, 1234, 1235, 1236, 1243,
			1244, 1245, 1252, 1253, 1254, 1261, 1262, 1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293,
			1294, 1295, 1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329, 1336, 1337, 1343,
			1344, 1345, 1352, 1353, 1354, 1361, 1362, 1363, 1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392,
			1393, 1400, 1401, 1407, 1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440, 1441, 1442,
			1449, 1450, 1456, 1457, 1463, 1464, 1465, 1472, 1473, 1474, 1481, 1482, 1488, 1489, 1490, 1497,
			1498, 1499, 1506, 1507, 1508, 1515, 1516, 1517, 1524, 1525, 1526, 1533, 1534, 1535, 1547, 1548,
			1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578, 1586, 1587, 1588, 1596, 1597, 1598,
			1605, 1606, 1607, 1616, 1617, 1618, 1627, 1628, 1629, 1638, 1639, 1640, 6007, 6008, 6011, 8673,
			8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449, 12450, 12451, 12459, 12460,
			15881, 15882, 15885, 18474, 18475, 18478 };

	public static final int[] SOME_IDS = { 1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299,
			1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414,
			1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503,
			1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471,
			/* Ancients */
			12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000,
			13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096 };



	public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition) {
		drawInterface(scrollPosition, xPosition, rsInterface, yPosition, false);
	}

	public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition, boolean inheritDrawingArea) {
		interfaceManager.drawInterface(scrollPosition, xPosition, rsInterface, yPosition, inheritDrawingArea);
	}

	/**
	 * int state -1 for buy and sell buttons, 0 for buying, 1 for selling, 2 for
	 * canceled
	 * <p>
	 * int itemId The item that the player wants to buy
	 * <p>
	 * int currentlyBought/Sold How many items the player has bought/sold so far.
	 * <p>
	 * int totalAmount How many items the player wants to buy/sell in total.
	 */
	int[][] grandExchangeInformation = new int[][] { { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 },
			{ -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 }, { -1, -1, -1, -1 },
			{ -1, -1, -1, -1 },
			// { 0, 11802, 0, 10 },
			// { 0, 11804, 4, 14 },
			// { 2, 11826, 2, 2 },
			// { 1, 555, 47500, 52500 },
			// { 0, 560, 35000, 50000 },
			// { 1, 11847, 0, 1 },
			// { -1, -1, -1, -1 }
	};

	public final String methodR(int j) { return interfaceManager.methodR(j); }

	public void drawHoverBox(int xPos, int yPos, String text) { interfaceManager.drawHoverBox(xPos, yPos, text); }

	public void drawBlackBox(int xPos, int yPos) { interfaceManager.drawBlackBox(xPos, yPos); }

	public void loadTabArea() { interfaceManager.loadTabArea(); }
	public void randomizeBackground(IndexedImage background) {
		int j = 256;
		for (int k = 0; k < anIntArray1190.length; k++)
			anIntArray1190[k] = 0;

		for (int l = 0; l < 5000; l++) {
			int i1 = (int) (Math.random() * 128D * j);
			anIntArray1190[i1] = (int) (Math.random() * 256D);
		}
		for (int j1 = 0; j1 < 20; j1++) {
			for (int k1 = 1; k1 < j - 1; k1++) {
				for (int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128]
							+ anIntArray1190[k2 + 128]) / 4;
				}

			}
			int ai[] = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if (background != null) {
			int l1 = 0;
			for (int j2 = 0; j2 < background.subHeight; j2++) {
				for (int l2 = 0; l2 < background.subWidth; l2++)
					if (background.palettePixels[l1++] != 0) {
						int i3 = l2 + 16 + background.xOffset;
						int j3 = j2 + 16 + background.yOffset;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
			}
		}
	}

	private void playerups(int i, int j, Buffer stream, Player player) {
		try {
			if ((i & 0x400) != 0) {
				player.anInt1543 = stream.method428();
				player.anInt1545 = stream.method428();
				player.anInt1544 = stream.method428();
				player.anInt1546 = stream.method428();
				player.initiate_movement = stream.method436() + loopCycle;
				player.cease_movement = stream.readUShortA() + loopCycle;
				player.forceMovementDirection = stream.method428();
				player.method446();
			}
			if ((i & 0x100) != 0) {
				player.readSpotAnimation(stream);

			}
			if ((i & 8) != 0) {
				int l = stream.method434();
				if (l == 65535)
					l = -1;
				int i2 = stream.readNegUByte();
				if (l == player.sequence && l != -1) {
					int i3 = SequenceDefinition.get(l).delayType;
					if (i3 == 1) {
						player.sequenceFrame = 0;
						player.sequenceFrameCycle = 0;
						player.sequenceDelay = i2;
						player.currentAnimationLoops = 0;
					}
					if (i3 == 2)
						player.currentAnimationLoops = 0;
				} else if (l == -1 || player.sequence == -1
						|| SequenceDefinition.get(l).forcedPriority >= SequenceDefinition.get(player.sequence).forcedPriority) {
					player.sequence = l;
					player.sequenceFrame = 0;
					player.sequenceFrameCycle = 0;
					player.sequenceDelay = i2;
					player.currentAnimationLoops = 0;
					player.stationaryPathPosition = player.smallXYIndex;
				}
			}
			if ((i & 4) != 0) {
				String textSpoken = stream.readString();

				// Only show flower poker chat to people within the area
				if (!player.inFlowerPokerArea() || localPlayer.inFlowerPokerChatProximity()) {
					player.spokenText = textSpoken;
					if (player.spokenText.charAt(0) == '~') {
						player.spokenText = player.spokenText.substring(1);
						if (player.lastForceChat == null || !player.lastForceChat.equals(textSpoken))
							pushMessage(player.spokenText, 2, player.displayName);
					} else if (player == localPlayer) {
						if (player.lastForceChat == null || !player.lastForceChat.equals(textSpoken))
							pushMessage(player.spokenText, 2, player.displayName);
					}
					player.textColour = 0;
					player.textEffect = 0;
					player.overheadTextCyclesRemaining = 150;
					player.lastForceChat = textSpoken;
				}
			}
			if ((i & 0x80) != 0) {
				int i1 = stream.method434();
				int rightsPrimaryValue = stream.readUnsignedByte();
				int j3 = stream.readNegUByte();
				int k3 = stream.currentPosition;
				PlayerRights rights = PlayerRights.forRightsValue(rightsPrimaryValue);
				if (player.displayName != null && player.visible) {
					long l3 = StringUtils.longForName(player.displayName);
					boolean flag = false;
					if (!rights.isStaffPosition()) {
						for (int i4 = 0; i4 < ignoreCount; i4++) {
							if (ignoreListAsLongs[i4] != l3)
								continue;
							flag = true;
							break;
						}
					}
					if (!flag && anInt1251 == 0)
						try {
							aStream_834.currentPosition = 0;
							stream.method442(j3, 0, aStream_834.payload);
							aStream_834.currentPosition = 0;
							String s = TextInput.method525(j3, aStream_834);

							// Only show flower poker chat to people within the area
							if (!player.inFlowerPokerArea() || localPlayer.inFlowerPokerChatProximity()) {
								player.spokenText = s;
								player.textColour = i1 >> 8;
								player.privelage = rightsPrimaryValue; // TODO remove privilege sending in send text player update flag
								player.textEffect = i1 & 0xff;
								player.overheadTextCyclesRemaining = 150;
								String crown = PlayerRights.buildCrownString(player.getDisplayedRights());
								String title = player.title != null && !player.title.isEmpty()
										? "<col=" + player.titleColor + ">" + player.title + "</col> "
										: "";
								pushMessage(s, 2, crown + title + player.displayName);
							}
						} catch (Exception exception) {
							exception.printStackTrace();
							Signlink.reporterror("cde2");
						}
				}
				stream.currentPosition = k3 + j3;
			}
			if ((i & 0x1) != 0) {
				player.interactingEntity = stream.method434();
				if (player.interactingEntity == 65535)
					player.interactingEntity = -1;
			}
			if ((i & 0x10) != 0) {
				int j1 = stream.readNegUByte();
				byte abyte0[] = new byte[j1];
				Buffer stream_1 = new Buffer(abyte0);
				stream.readBytes(j1, 0, abyte0);
				aStreamArray895s[j] = stream_1;
				player.updatePlayer(stream_1);
			}
			if ((i & 0x2) != 0) {
				player.anInt1538 = stream.method436();
				player.anInt1539 = stream.method434();
			}
			if ((i & 0x20) != 0) {
				player.addHitSplatPLayer(stream);
				player.addHealthBarPlayer(stream);
				player.currentHealth = stream.readNegUByte();
				player.maxHealth = stream.readUnsignedByte();

			}
			if ((i & 0x200) != 0) {
				player.addHitSplatPLayer(stream);
				player.addHealthBarPlayer(stream);
				player.currentHealth = stream.readUnsignedByte();
				player.maxHealth = stream.readNegUByte();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}


	static int loadingType;
	static int mapsLoaded;
	static int totalMaps;

	public static int objectsLoaded;

	static int totalObjects;

	static int mouseWheelRotation;

	@Override
	public void draw(boolean redraw) {
		gameRenderer.draw(redraw);
	}

	boolean isFriendOrSelf(String s) {
		if (s == null)
			return false;
		for (int i = 0; i < friendsCount; i++)
			if (s.equalsIgnoreCase(friendsList[i]))
				return true;
		return s.equalsIgnoreCase(localPlayer.displayName);
	}

	static String combatDiffColor(int i, int j) {
		int k = i - j;
		if (k < -9)
			return "@red@";
		if (k < -6)
			return "@or3@";
		if (k < -3)
			return "@or2@";
		if (k < 0)
			return "@or1@";
		if (k > 9)
			return "@gre@";
		if (k > 6)
			return "@gr3@";
		if (k > 3)
			return "@gr2@";
		if (k > 0)
			return "@gr1@";
		else
			return "@yel@";
	}

	public static boolean centerInterface() {
		int minimumScreenWidth = 765, minimumScreenHeight = 610;
		if (canvasWidth >= minimumScreenWidth && canvasHeight >= minimumScreenHeight)
			return true;
		return false;
	}


	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance)
				&& (objectY - playerY <= distance && objectY - playerY >= -distance));
	}

	public static int distanceToPoint(int x1, int y1, int x2, int y2) {
		int x = (int) Math.pow(x1 - x2, 2.0D);
		int y = (int) Math.pow(y1 - y2, 2.0D);
		return (int) Math.floor(Math.sqrt(x + y));
	}

	void addIgnore(long l) {
		try {
			if (l == 0L)
				return;

			String username = StringUtils.fixName(StringUtils.nameForLong(l));

			if (username.equalsIgnoreCase(localPlayer.displayName))
				return;

			if (ignoreCount >= 100) {
				pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
				return;
			}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage(username + " is already on your ignore list", 0, "");
					return;
				}
			for (int k = 0; k < friendsCount; k++)
				if (friendsListAsLongs[k] == l) {
					pushMessage("Please remove " + username + " from your friend list first", 0, "");
					return;
				}

			//ignoreListAsLongs[ignoreCount++] = l;
			//needDrawTabArea = true;
			stream.createFrame(133);
			stream.writeQWord(l);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void method114() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = maxPlayerCount;
			else
				j = playerIndices[i];
			Player player = players[j];
			if (player != null)
				entityUpdateBlock(player);
		}

	}

	private void processPendingSpawns() {
		for (SpawnedObject spawnedObject = (SpawnedObject) spawns
				.first(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns
				.next()) {
			if (spawnedObject.getLongetivity > 0)
				spawnedObject.getLongetivity--;
			if (spawnedObject.getLongetivity == 0) {
				if (spawnedObject.getPreviousId < 0 || MapRegion.cached(spawnedObject.getPreviousId, spawnedObject.previousType)) {
					currentMapRegion.addPendingSpawnToScene(spawnedObject.plane, spawnedObject.group, spawnedObject.x,
							spawnedObject.y, spawnedObject.getPreviousId, spawnedObject.previousOrientation,
							spawnedObject.previousType);

					spawnedObject.remove();
				}
			} else {
				if (spawnedObject.delay > 0)
					spawnedObject.delay--;
				if (spawnedObject.delay == 0 && spawnedObject.x >= 1 && spawnedObject.y >= 1
						&& spawnedObject.x <= 102 && spawnedObject.y <= 102
						&& (spawnedObject.id < 0 || MapRegion.cached(spawnedObject.id, spawnedObject.type))) {

					currentMapRegion.addPendingSpawnToScene(spawnedObject.plane, spawnedObject.group, spawnedObject.x,
							spawnedObject.y, spawnedObject.id, spawnedObject.orientation, spawnedObject.type);

					spawnedObject.delay = -1;
					if (spawnedObject.id == spawnedObject.getPreviousId && spawnedObject.getPreviousId == -1)
						spawnedObject.remove();
					else if (spawnedObject.id == spawnedObject.getPreviousId
							&& spawnedObject.orientation == spawnedObject.previousOrientation
							&& spawnedObject.type == spawnedObject.previousType)
						spawnedObject.remove();
				}
			}
		}
	}

	final void openMenu(int x, int y) { menuHandler.openMenu(x, y); }
	public void method117(Buffer stream) {
		stream.initBitAccess();
		int j = stream.readBits(1);
		if (j == 0)
			return;
		int k = stream.readBits(2);
		if (k == 0) {
			anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 1) {
			int l = stream.readBits(3);
			localPlayer.moveInDir(false, l);
			int k1 = stream.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 2) {
			int i1 = stream.readBits(3);
			localPlayer.moveInDir(true, i1);
			int l1 = stream.readBits(3);
			localPlayer.moveInDir(true, l1);
			int j2 = stream.readBits(1);
			if (j2 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			return;
		}
		if (k == 3) {
			plane = stream.readBits(2);
			int j1 = stream.readBits(1);
			int i2 = stream.readBits(1);
			if (i2 == 1)
				anIntArray894[anInt893++] = maxPlayerCount;
			int k2 = stream.readBits(7);
			int l2 = stream.readBits(7);
			localPlayer.setPos(l2, k2, j1 == 1);
		}
	}

	public void nullLoader() {
		aBoolean831 = false;
		while (drawingFlames) {
			aBoolean831 = false;
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
		aBackgroundArray1152s = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
	}

	public boolean processWidgetAnimations(int tick, int interfaceId) {
		return interfaceManager.processWidgetAnimations(tick, interfaceId);
	}


	void delIgnore(long l) {
		try {
			if (l == 0L)
				return;
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					ignoreCount--;
					needDrawTabArea = true;
					System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);

					stream.createFrame(74);
					stream.writeQWord(l);
					return;
				}

			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

    private FileArchive loadArchive(String name) throws IOException {
		int id = 0;
        if (Objects.equals(name, "media.dat")) {
            id = 0;
        } else if (Objects.equals(name, "interface.dat")) {
			id = 1;
		} else if (Objects.equals(name, "title.dat")) {
			id = 2;
        }
        return new FileArchive(AssetUtils.INSTANCE.getResource(name).openStream().readAllBytes());
    }

	private int extractInterfaceValues(RSInterface widget, int id) {
		return interfaceManager.extractInterfaceValues(widget, id);
	}

	static String colorStartTag(int var0) {
		return "<col=" + Integer.toHexString(var0) + ">";
	}

	static String getMenuString(final int menuIndex) {
		if (menuIndex < 0) {
			return "";
		} else {
			final String menuTarget = menuTargets[menuIndex];
			final String menuAction = menuActions[menuIndex];
			return menuTarget != null && !menuTarget.isEmpty()
					? menuAction + " " + menuTarget
					: menuAction;
		}
	}

	public String toolTip;

	Sprite compassImage;
	Sprite[] mapArea = new Sprite[8];

	private Sprite eventIcon;
	public Sprite bankDivider;

	private int[][] getCustomMapIcons() {
		return new int[][] {
				//map icons minimap icons map sprites
				/*
				0 gen store
				1 sword
				2 mage
				3 b axe
				4 med helm
				5 bank
				6 quest
				7 jewerlly
				8 mining
				9 furnace
				10 smithing
				11 dummy
				12 dungeon
				13 leave dung
				14 shortcut
				15 bstaff
				16 gray top
				17 gray legs
				18 small sword
				19 arrow
				20 shield
				21 altar
				22 herb
				23 ring
				24 red gem
				25 crafting chisel
				26 ccandle
				27 fishing rod
				28 fish spot
				29 green top
				30 potions
				31 silk
				32 kebab
				33 beer
				34 chisel?
				35 brown top
				36 woodcutting
				37 wheel
				38 bread
				39 fork and knife
				40 minigame
				41 water
				42 pot
				43 pink skirt
				44 bowl
				45 grain
				46 golden axe
				47 chain body
				48 gray rock, spices?
				49 red man
				50 bowl with red stuff
				51 agility
				52 apple
				53 slayer
				54 haircut
				55 shovel
				56 appearence mask editor
				57 travel arrow
				58 purple portal
				59 pot
				60 fence
				61 barrel
				62 butter
				63 water
				64 hunter
				65 vote
				66 leather
				67 house
				68 saw
				69 hammer and rock
				70 shortcut
				71 box
				72 gift box
				73 sand pit
				74 green achievement star
				75 pets
				76 bounty icon
				77 sword and shirt icon
				78 logs
				79 plants
				80 runecrafting
				81 rc rock
				82 grand exchange
				83 purple star
				84 brown star
				85 question mark tutorial icons
				86 bank
				87 lock
				88 wc
				89 mining
				90 fishing
				91 hunter
				92 smithing
				93 crafting
				94 cooking
				95 melee
				96 prayer
				97 dungeon
				98 ironman
				99 bounty hunter
				100 bond
				101 holiday gift
				102 bottle
				103 partyhat
				104 newspaper
				105 cup of tea
				106 bell
				107 red bottle
				108 whichs pot
				109 rope
				110 hand
				111 band aid
				112 ancient altar
				113 bowl
				 */
				// sprite id, x, y
			//	{0, 3084, 3494}, // general store
				{53, 3076, 3504}, // slayer area updated
			//	{94, 3067, 3472}, // wildy slayer area
				{30, 3080, 3492}, // pool icon
			//	{9, 3089, 3475}, // hespori entrance
			//	{98, 3092, 3480}, // ironman store
				//{77, 3110, 3495}, //supplies area
				{24, 3089, 3505}, //thieving red gem done
				//{66, 3091, 3470}, //thieving leather
				{38, 3095, 3501}, //thieving bakers stall done
				{31, 3098, 3505}, //thieving silk
				{34, 3096, 509}, //thieving silver done
				//{51, 3093, 3474}, //agility
				//{58, 3103, 3471}, //house portal
				{5, 3094, 3491}, //bank
			//	{5, 3119, 3506}, //bank
			//	{112, 3099, 3513}, //ancient altar
				{21, 3089, 3491}, // altar
			//	{57, 3087, 3494}, // travel arrow
			//	{28, 3125, 3490} //fishing
				/*{5, 3090, 3499}, // bank
                {5, 3077, 3517}, // bank
                {5, 3090, 3517}, // bank

                {56, 3094, 3516}, // teleport
                {56, 3097, 3500}, // teleport
                {77, 3097, 3507}, // runecraft
                {64, 3076, 3508}, // housing portal
                {19, 3085, 3515}, // altar
                {79, 3094, 3497}, // grand exchange*/

				// home
//				{5, 3097, 3490}, // bank
//				{5, 3089, 3507}, // bank wild
//				{79, 3091, 3495}, // grand exchange
//				{51, 3079, 3493}, // slayer
//				{0, 3080, 3511}, // general store
//				{56, 3086, 3496}, // teleport
//				{77, 3104, 3491}, // runecraft
//				{64, 3086, 3485}, // housing portal
				//{54, 2326, 3177}, // makeover
				//{44, 2329, 3162}, // star sprite
				//{19, 3086, 3510}, // altar
				//{72, 3089, 3512}, // bounty shop
				//{55, 3086, 3490}, // tutorial
				//{76, 2356, 3152}, // flax

				//skill area
				//{10, 3109, 3496}, // anvil
				//{9, 3109, 3500}, // furnace
				//{35, 3106, 3501}, // spinning wheel

				//{19, 3099, 3507}, // altar
				//{11, 3110, 3517}, // training dummies
				//{72, 2326, 3179}, // bounty
				//{38, 2326, 3175}, // achievement
		};
	}

	void drawMinimap() {
		if (minimapHidden && isResized())
			return;

		int xOffset = !isResized() ? 516 : 0;
		int compassRotation = (viewRotation + camAngleY) & 0x7ff;

		/** Black minimap **/
		if (minimapState == 2) {
			if (!isResized()) {
				mapArea[3].drawSprite(xOffset, 4);
				mapArea[5].drawSprite(xOffset, 0);
			} else {
				mapArea[2].drawSprite(canvasWidth - 183, 0);
				mapArea[4].drawSprite(canvasWidth - 160, 8);
			}
			if(getUserSettings().isOldGameframe() && !isResized() ){
				compassImage.rotate(33, compassRotation, anIntArray1057, 256, anIntArray968,
					(!isResized() ? 28 + xOffset : 25), 4,
					(!isResized() ? 27 + xOffset : canvasWidth - 178), 33, 25);
			}else {
				compassImage.rotate(33, compassRotation, anIntArray1057, 256, anIntArray968,
					(!isResized() ? 25 : 25) , 4,
					(!isResized() ? 29 + xOffset : canvasWidth - 178) , 33, 25);
			}

			return;
		}

		// If not loaded in don't draw mapback
		if (gameState == GameState.LOGGED_IN.getState()) {

			int i = viewRotation + camAngleY & 0x7ff;
			int j = 48 + localPlayer.x / 32;
			int l2 = 464 - localPlayer.y / 32;

			int positionX = (!isResized() ? 9 : 7);
			int positionY = (!isResized() ? 51 + xOffset : canvasWidth - 160);

			minimapImage.rotate(151, i, anIntArray1229, 256 + minimapZoom, anIntArray1052, l2, positionX , positionY, 151, j);

			for (int[] icon : getCustomMapIcons()) {
				markMinimap(mapFunctions[icon[0]], ((icon[1] - baseX) * 4 + 2) - Client.localPlayer.x / 32, ((icon[2] - baseY) * 4 + 2) - Client.localPlayer.y / 32);
			}

			for (int j5 = 0; j5 < mapIconAmount; j5++) {
				int k = (minimapHintX[j5] * 4 + 2) - localPlayer.x / 32;
				int i3 = (minimapHintY[j5] * 4 + 2) - localPlayer.y / 32;
				markMinimap(minimapHint[j5], k, i3);
			}


			if (Configuration.HALLOWEEN) {
				switch (plane) {
					case 0:
						markMinimap(eventIcon, ((3088 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3494 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(eventIcon, ((2607 - baseX) * 4 + 2) - localPlayer.x / 32,
							((4773 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(eventIcon, ((3103 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3482 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;
				}
			}
			if (Configuration.CHRISTMAS && Configuration.CHRISTMAS_EVENT) {
				switch (plane) {
					case 0:
						markMinimap(minimapIcons[0], ((3086 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3498 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(minimapIcons[0], ((2832 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3798 - baseY) * 4 + 2) - localPlayer.y / 32);
						markMinimap(minimapIcons[0], ((2982 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3643 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;

					case 2:
						markMinimap(minimapIcons[0], ((2827 - baseX) * 4 + 2) - localPlayer.x / 32,
							((3810 - baseY) * 4 + 2) - localPlayer.y / 32);
						break;
				}
			}

			for (int k5 = 0; k5 < 104; k5++) {
				for (int l5 = 0; l5 < 104; l5++) {
					NodeDeque class19 = groundItems[plane][k5][l5];
					if (class19 != null) {
						int l = (k5 * 4 + 2) - localPlayer.x / 32;
						int j3 = (l5 * 4 + 2) - localPlayer.y / 32;
						markMinimap(minimapDot[0], l, j3);
					}
				}
			}

			for (int i6 = 0; i6 < npcCount; i6++) {
				Npc npc = npcs[npcIndices[i6]];
				if (npc != null && npc.isVisible()) {
					NpcDefinition entityDef = npc.desc;
					if (entityDef.configs != null)
						entityDef = entityDef.morph();
					if (entityDef != null && entityDef.onMinimap && entityDef.clickable) {
						int i1 = npc.x / 32 - localPlayer.x / 32;
						int k3 = npc.y / 32 - localPlayer.y / 32;
						markMinimap(minimapDot[1], i1, k3);
					}
				}
			}

			for (int j6 = 0; j6 < playerCount; j6++) {
				Player player = players[playerIndices[j6]];
				if (player != null && player.isVisible()) {
					int j1 = player.x / 32 - localPlayer.x / 32;
					int l3 = player.y / 32 - localPlayer.y / 32;
					boolean flag1 = false;
					boolean flag3 = false;
					String clanname;
					for (int j3 = 0; j3 < clanList.length; j3++) {
						if (clanList[j3] == null)
							continue;
						clanname = clanList[j3];
						if (clanname.startsWith("<clan"))
							clanname = clanname.substring(clanname.indexOf(">") + 1, clanname.length());
						if (!clanname.equalsIgnoreCase(player.displayName))
							continue;
						flag3 = true;
						break;
					}
					long l6 = StringUtils.longForName(player.displayName);
					for (int k6 = 0; k6 < friendsCount; k6++) {
						if (l6 != friendsListAsLongs[k6] || friendsNodeIDs[k6] == 0)
							continue;
						flag1 = true;
						break;
					}
					boolean flag2 = false;
					if (localPlayer.team != 0 && player.team != 0 && localPlayer.team == player.team)
						flag2 = true;
					if (flag1)
						markMinimap(minimapDot[3], j1, l3);
					else if (flag3)
						markMinimap(minimapDot[5], j1, l3);
					else if (flag2)
						markMinimap(minimapDot[4], j1, l3);
					else
						markMinimap(minimapDot[2], j1, l3);
				}
			}

			if (hintArrowType != 0 && loopCycle % 20 < 10) {
				if (hintArrowType == 1 && hintArrowNpcIndex >= 0 && hintArrowNpcIndex < npcs.length) {
					Npc npc = npcs[hintArrowNpcIndex];
					if (npc != null) {
						int k1 = npc.x / 32 - localPlayer.x / 32;
						int i4 = npc.y / 32 - localPlayer.y / 32;
						refreshMinimap(mapMarker, i4, k1);
					}
				}
				if (hintArrowType == 2) {
					int l1 = ((hintIconX - baseX) * 4 + 2) - localPlayer.x / 32;
					int j4 = ((hintIconYpos - baseY) * 4 + 2) - localPlayer.y / 32;
					refreshMinimap(mapMarker, j4, l1);
				}
				if (hintArrowType == 10 && hintArrowPlayerIndex >= 0 && hintArrowPlayerIndex < players.length) {
					Player player = players[hintArrowPlayerIndex];
					if (player != null) {
						int i2 = player.x / 32 - localPlayer.x / 32;
						int k4 = player.y / 32 - localPlayer.y / 32;
						refreshMinimap(mapMarker, k4, i2);
					}
				}
			}

			if (destX != 0) {
				int j2 = (destX * 4 + 2) - localPlayer.x / 32;
				int l4 = (destY * 4 + 2) - localPlayer.y / 32;
				markMinimap(mapFlag, j2, l4); //764 1068
			}


		}

		Sprite wiki1 = new Sprite("/Interfaces/Wiki/2420");
		Sprite wiki2 = new Sprite("/Interfaces/Wiki/2421");

		Sprite teleOrb1 = new Sprite("/Interfaces/teleorb/1435");
		Sprite teleOrb2 = new Sprite("/Interfaces/teleorb/1436");
		Rasterizer2D.drawBox((!isResized() ? xOffset + 127 : canvasWidth - 88), (!isResized() ? 83 : 80), 3, 3,
				0xffffff);
		if (!isResized()) {

			mapArea[0].drawSprite(xOffset, 4);
			mapArea[5].drawSprite(xOffset, 0);
		} else
			mapArea[2].drawSprite(canvasWidth - 183, 0);

		compassImage.rotate(33, compassRotation, anIntArray1057, 256, anIntArray968,
			(!isResized() ? 25 : 25), 4,
			(!isResized() ? 29 + xOffset : canvasWidth - 178), 33, 25);
		if (drawOrbs)
			loadAllOrbs(!isResized() ? 0 + xOffset : canvasWidth - 217);
		if (!isResized()) {
			cacheSprite2[6].drawSprite(xOffset + 198 - 2, 17 + 89);//110
			if (worldHover) {
				cacheSprite2[1].drawSprite(xOffset + 202 - 2, 20 + 90);//111
			} else {
				cacheSprite2[0].drawSprite(xOffset + 202 - 2, 20 + 90);
			}
			//if (wikiHover) {
			//	wiki2.drawSprite(185, 153);

			//} else {
			//	wiki1.drawSprite(185, 153);

			//}

			if (teleOrbHover) {
				teleOrb2.drawSprite(xOffset + 192, 25);

			} else {
				teleOrb1.drawSprite(xOffset + 192, 25);

			}
		} else {


			cacheSprite2[6].drawSprite(canvasWidth - 35, 141);
			if (worldHover) {
				cacheSprite2[1].drawSprite(canvasWidth - 31, 145);
			} else {
				cacheSprite2[0].drawSprite(canvasWidth - 31, 145);
			}

			//	if (wikiHover) {
			//	wiki2.drawSprite(currentGameWidth - 40, 165);
			//	} else {
			//		wiki1.drawSprite(currentGameWidth - 40, 165);
			//	}

			if (teleOrbHover) {
				teleOrb2.drawSprite(canvasWidth - 100, 150);

			} else {
				teleOrb1.drawSprite(canvasWidth - 100, 150);

			}

		}

	}

	public static AbstractRasterProvider rasterProvider;

	private boolean hpHover = false;

	private void loadHpOrb(int xOffset) {
		int yOff = Configuration.osbuddyGameframe ? !isResized() ? 0 : -5
				: !isResized() ? 0 : -5;
		int xOff = Configuration.osbuddyGameframe ? !isResized() ? 0 : -6
				: !isResized() ? 0 : -6;
		String cHP = RSInterface.interfaceCache[4016].message;
		String mHP = RSInterface.interfaceCache[4017].message;
		int currentHP = Integer.parseInt(cHP);
		int maxHP = Integer.parseInt(mHP);
		int health = (int) (((double) currentHP / (double) maxHP) * 100D);
		int poisonType = variousSettings[Configs.POISON_CONFIG];
		int hover = poisonType == 0 ? 173 : 173;
		Sprite bg = cacheSprite3[hpHover ? hover : 172];
		Sprite fg = null;
		if (poisonType == 0) {
			fg = cacheSprite3[161];
		}
		if (poisonType == 1) {
			fg = cacheSprite3[162];
		}
		if (poisonType == 2) {
			fg = venomOrb;
		}
		bg.drawSprite(0 + xOffset - xOff, 41 - yOff);
		fg.drawSprite(27 + xOffset - xOff, 45 - yOff);
		if (getOrbFill(health) <= 26) {
			cacheSprite3[160].myHeight = getOrbFill(health);
		} else {
			cacheSprite3[160].myHeight = 26;
		}
		cacheSprite3[160].drawSprite(27 + xOffset - xOff, 45 - yOff);
		cacheSprite3[168].drawSprite(27 + xOffset - xOff, 45 - yOff);
		if (health > 1_000_000_000) {
			infinity.drawSprite(10 + xOffset - xOff, 59 - yOff);
		} else {
			smallText.method382(getOrbTextColor(health), 15 + xOffset - xOff, "" + cHP, 67 - yOff, true);
		}
	}

	public double fillHP;

	public Sprite worldMap1, worldMap2, worldMap3;

	boolean specialHover;
	public int specialEnabled;
	public int specialAttack=100;

	private void drawSpecialOrb(int xOffset) {
		Sprite image = cacheSprite1[specialHover ? 8 : 7];
		Sprite fill = cacheSprite[specialEnabled == 0 ? 9 : 6];
		Sprite sword = cacheSprite[12];
		double percent = specialAttack  / (double) 100;
		//int percent = 100;
		boolean isFixed = !isResized();
		image.drawSprite((isFixed ? 37 : 37) + xOffset, isFixed ? 134 : 139);
		if(specialEnabled==1) {
			//fill.drawSprite((isFixed ? 60 : 133) + xOffset, isFixed ? 134 : 151);
			fill.drawSprite((isFixed ? 63 : 63) + xOffset, isFixed ? 137 : 141);
		}else{
			fill.drawSprite((isFixed ? 64 : 64) + xOffset, isFixed ? 138 : 143);
			//fill.drawSprite((isFixed ? 65 : 133) + xOffset, isFixed ? 139 : 151);
		}
		sword.drawSprite((isFixed ? 64 : 64) + xOffset, isFixed ? 138 : 143);

		smallText.method382(getOrbTextColor((int) (percent * 100)),
				(isFixed ? 53 : 53) + xOffset, specialAttack + "", isFixed ? 159 : 164,
				true);
	}

	private void loadPrayerOrb(int xOffset) {
		int yOff = Configuration.osbuddyGameframe ? !isResized() ? 10 : 2
				: !isResized() ? 0 : -5;
		int xOff = Configuration.osbuddyGameframe ? !isResized() ? -1 : -7
				: !isResized() ? -1 : -7;
		Sprite bg = cacheSprite1[prayHover ? 8 : 7];
		Sprite fg = prayClicked ? new Sprite("Gameframe/newprayclicked") : cacheSprite1[1];
		bg.drawSprite(0 + xOffset - xOff, 75 - yOff);
		fg.drawSprite(27 + xOffset - xOff, 79 - yOff);
		int level = Integer.parseInt(RSInterface.interfaceCache[4012].message.replaceAll("%", ""));
		int max = maximumLevels[5];
		double percent = level / (double) max;
		cacheSprite1[14].myHeight = (int) (26 * (1 - percent));
		cacheSprite1[14].drawSprite(27 + xOffset - xOff, 79 - yOff);
		if (percent <= .25) {
			cacheSprite1[10].drawSprite(30 + xOffset - xOff, 82 - yOff);
		} else {
			cacheSprite1[10].drawSprite(30 + xOffset - xOff, 82 - yOff);
		}
		if (level > 1_000_000_000) {
			infinity.drawSprite(11 + xOffset - xOff, 94 - yOff);
		} else {
			smallText.method382(getOrbTextColor((int) (percent * 100)), 14 + xOffset - xOff, level + "", 101 - yOff, true);
		}


	}

	private void loadRunOrb(int xOffset) {
		int current = Integer.parseInt(RSInterface.interfaceCache[22539].message.replaceAll("%", ""));
		int yOff = Configuration.osbuddyGameframe ? !isResized() ? 15 : 5
				: !isResized() ? 1 : -4;
		int xMinus = Configuration.osbuddyGameframe ? !isResized() ? 11 : 5
				: !isResized() ? -1 : -6;
		Sprite bg = cacheSprite1[runHover ? 8 : 7];
		boolean running = anIntArray1045[173] == 1;
		Sprite fg = cacheSprite1[running ? 4 : 3];
		bg.drawSprite(10 + xOffset - xMinus, 109 - yOff);
		fg.drawSprite(37 + xOffset - xMinus, 113 - yOff);
		int level = current;
		double percent = level / (double) 100;
		cacheSprite1[14].myHeight = (int) (26 * (1 - percent));
		cacheSprite1[14].drawSprite(37 + xOffset - xMinus, 113 - yOff);
		if (percent <= .25) {
			cacheSprite1[running ? 12 : 11].drawSprite(43 + xOffset - xMinus, 117 - yOff);
		} else {
			cacheSprite1[running ? 12 : 11].drawSprite(43 + xOffset - xMinus, 117 - yOff);
		}
		smallText.method382(getOrbTextColor((int) (percent * 100)), 25 + xOffset - xMinus, level + "", 135 - yOff,
				true);
	}

	private Sprite venomOrb;

	private boolean pouchHover;

	Sprite moneyPouch;
	Sprite moneyPouch1;
	Sprite moneyPouchCoins;

	private boolean wikiHover;
	private boolean teleOrbHover;

	private int getMoneyOrbColor(long amount) {
		if (amount >= 0 && amount <= 99999) {
			return 0xFFFF00;
		} else if (amount >= 100000 && amount <= 9999999) {
			return 0xFFFFFF;
		} else {
			return 0x00FF80;
		}
	}


	private void drawMoneyPouch(int xOffset) {

		if (pouchHover) {
			Rasterizer2D.fillCircle((!Client.instance.isResized() ? xOffset + 179 : canvasWidth - 49), (!Client.instance.isResized() ? 142 : 168), 15, 0x6E6D6D);
			moneyPouch.drawSprite(((!Client.instance.isResized() ? xOffset + 162 : canvasWidth - 65)), (!Client.instance.isResized() ? 127 : 153));
			moneyPouchCoins.drawSprite((!Client.instance.isResized() ? xOffset + 170 : canvasWidth - 57), (!Client.instance.isResized() ? 134 : 160));
			String amount = RSInterface.interfaceCache[8135].message;
			long getAmount = Long.parseLong(amount);
			smallText.method382(getMoneyOrbColor(getAmount), (!Client.instance.isResized() ? xOffset +  205 : canvasWidth - 22), formatCoins(getAmount) + "", (!Client.instance.isResized() ? 153 : 178), true);
		} else {

			Rasterizer2D.fillCircle((!Client.instance.isResized() ? xOffset + 179 : canvasWidth - 49), (!Client.instance.isResized() ? 142 : 168), 15, 0x6E6D6D);
			moneyPouch1.drawSprite(((!Client.instance.isResized() ? xOffset + 162 : canvasWidth - 65)), (!Client.instance.isResized() ? 127 : 153));
			moneyPouchCoins.drawSprite((!Client.instance.isResized() ? xOffset + 170 : canvasWidth - 57), (!Client.instance.isResized() ? 134 : 160));
			String amount = RSInterface.interfaceCache[8135].message;
			long getAmount = Long.parseLong(amount);
			smallText.method382(getMoneyOrbColor(getAmount), (!Client.instance.isResized() ? xOffset + 205 : canvasWidth - 22), formatCoins(getAmount) + "", (!Client.instance.isResized() ? 153 : 178), true);
		}
	}

	private void loadAllOrbs(int xOffset) {
		try {
			loadHpOrb(xOffset);
			loadPrayerOrb(xOffset);
			loadRunOrb(xOffset);
			drawSpecialOrb(xOffset);
			//drawMoneyPouch(xOffset);
		} catch (Exception e) {}

		if (drawExperienceCounter) {
			if (counterHover) {
				cacheSprite2[5].drawSprite(
					drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
					!isResized() ? 21 : 25);
			} else {
				cacheSprite2[3].drawSprite(
					drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
					!isResized() ? 21 : 25);
			}
		} else {
			if (counterHover) {
				cacheSprite2[4].drawSprite(
					drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
					!isResized() ? 21 : 25);
			} else {
				cacheSprite2[2].drawSprite(
					drawOrbs && !isResized() ? 0 + xOffset : canvasWidth - 211,
					!isResized() ? 21 : 25);
			}
		}
		if (!isResized()) {
			// cacheSprite[worldHover ? 5 : 4].drawSprite(202, 20);
		} else {
			// cacheSprite[worldHover ? 3 : 2].drawSprite(Client.canvasWidth - 118,
			// 154);
		}
		if (Configuration.osbuddyGameframe) {
			//loadSpecialOrb(xOffset);
		}

	}

	public boolean runClicked = false;

	public int getOrbTextColor(int i) {
		if (i >= 75 && i <= 0x7fffffff)
			return 65280;
		if (i >= 50 && i <= 74)
			return 0xffff00;
		return i < 25 || i > 49 ? 0xff0000 : 0xFFB400;
	}

	private Sprite mapIcon9, mapIcon7, mapIcon8, mapIcon6, mapIcon5;

	public int getOrbFill(int statusInt) {
		if (statusInt <= Integer.MAX_VALUE && statusInt >= 97)
			return 0;
		else if (statusInt <= 96 && statusInt >= 93)
			return 1;
		else if (statusInt <= 92 && statusInt >= 89)
			return 2;
		else if (statusInt <= 88 && statusInt >= 85)
			return 3;
		else if (statusInt <= 84 && statusInt >= 81)
			return 4;
		else if (statusInt <= 80 && statusInt >= 77)
			return 5;
		else if (statusInt <= 76 && statusInt >= 73)
			return 6;
		else if (statusInt <= 72 && statusInt >= 69)
			return 7;
		else if (statusInt <= 68 && statusInt >= 65)
			return 8;
		else if (statusInt <= 64 && statusInt >= 61)
			return 9;
		else if (statusInt <= 60 && statusInt >= 57)
			return 10;
		else if (statusInt <= 56 && statusInt >= 53)
			return 11;
		else if (statusInt <= 52 && statusInt >= 49)
			return 12;
		else if (statusInt <= 48 && statusInt >= 45)
			return 13;
		else if (statusInt <= 44 && statusInt >= 41)
			return 14;
		else if (statusInt <= 40 && statusInt >= 37)
			return 15;
		else if (statusInt <= 36 && statusInt >= 33)
			return 16;
		else if (statusInt <= 32 && statusInt >= 29)
			return 17;
		else if (statusInt <= 28 && statusInt >= 25)
			return 18;
		else if (statusInt <= 24 && statusInt >= 21)
			return 19;
		else if (statusInt <= 20 && statusInt >= 17)
			return 20;
		else if (statusInt <= 16 && statusInt >= 13)
			return 21;
		else if (statusInt <= 12 && statusInt >= 9)
			return 22;
		else if (statusInt <= 8 && statusInt >= 7)
			return 23;
		else if (statusInt <= 6 && statusInt >= 5)
			return 24;
		else if (statusInt <= 4 && statusInt >= 3)
			return 25;
		else if (statusInt <= 2 && statusInt >= 1)
			return 26;
		else if (statusInt <= 0)
			return 27;
		return 0;
	}

	public Sprite xpSprite;
	public Sprite xpbg1;
	public Sprite xpbg2;

	Queue<ExperienceDrop> experienceDrops = new LinkedList<>();

	void processExperienceCounter() {
		if (loopCycle % 1 <= 1 && !experienceDrops.isEmpty()) {
			Collection<ExperienceDrop> remove = new ArrayList<>();
			for (ExperienceDrop drop : experienceDrops) {
				drop.pulse();
				if (drop.getYPosition() == -1) {
					experienceCounter += drop.getAmount();
					remove.add(drop);
				}
			}
			experienceDrops.removeAll(remove);
		}

		if (!drawExperienceCounter || openInterfaceID > -1) {
			return;
		}

		for (ExperienceDrop drop : experienceDrops) {
			String text = drop.toString();
			int x = (!isResized() ? 507 : canvasWidth - 246)
					- newSmallFont.getTextWidth(text);
			int y = drop.getYPosition() - 15;
			int transparency = 256;
			newSmallFont.drawString(text, x, y, 0xFFFFFF, 0x000000, 256);

			for (int skill : drop.getSkills()) {
				if(skill==22){
					continue;
				}

				Sprite sprite = smallXpSprites[skill];
				x -= sprite.myWidth + 3;
				y -= sprite.myHeight - 4;
				sprite.drawAdvancedSprite(x, y, transparency);
				y += sprite.myHeight - 4;
			}
		}

		String experience = NumberFormat.getInstance().format(experienceCounter);

		xpbg1.drawAdvancedSprite(!isResized() ? 395 : canvasWidth - 365, 6);
		xpbg2.drawSprite(!isResized() ? 398 : canvasWidth - 363, 9);

		newSmallFont.drawBasicString(experience, (!isResized() ? 510 : canvasWidth - 252)
				- newSmallFont.getTextWidth(experience), 24, 0xFFFFFF, 0x000000);
	}

	boolean drawExperienceCounter = true;

	public void npcScreenPos(Entity entity, int i) {
		calcEntityScreenPos(entity.x, i, entity.y);
	}

	public void calcEntityScreenPos(int x, int vertical_offset, int y) {
		if (x < 128 || y < 128 || x > 13056 || y > 13056) {
			viewportTempX = -1;
			viewportTempY = -1;
			return;
		}
		int z = getCenterHeight(plane, y, x) - vertical_offset;
		x -= cameraX;
		z -= cameraY;
		y -= cameraZ;

		int sin_y = Rasterizer3D.Rasterizer3D_sine[cameraPitch];
		int cos_y = Rasterizer3D.Rasterizer3D_cosine[cameraPitch];

		int sin_x = Rasterizer3D.Rasterizer3D_sine[cameraYaw];
		int cos_x = Rasterizer3D.Rasterizer3D_cosine[cameraYaw];

		int a_x = y * sin_x + x * cos_x >> 16;
		int b_x = y * cos_x - x * sin_x >> 16;

		int a_y = z * cos_y - b_x * sin_y >> 16;
		int b_y = z * sin_y + b_x * cos_y >> 16;

		if (b_y >= 50) {
			viewportTempX = a_x * Client.viewportZoom / b_y + (Client.viewportWidth / 2);
			viewportTempY = a_y * Client.viewportZoom / b_y + (Client.viewportHeight / 2);
		} else {
			viewportTempX = -1;
			viewportTempY = -1;
		}
	}

	public void requestSpawnObject(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
		SpawnedObject spawnedObject = null;
		for (spawnedObject = (SpawnedObject) spawns
				.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns
				.reverseGetNext()) {
			if (spawnedObject.plane != l1 || spawnedObject.x != i2 || spawnedObject.y != j1
					|| spawnedObject.group != i1)
				continue;
			spawnedObject = spawnedObject;
			break;
		}

		if (spawnedObject == null) {
			spawnedObject = new SpawnedObject();
			spawnedObject.plane = l1;
			spawnedObject.group = i1;
			spawnedObject.x = i2;
			spawnedObject.y = j1;
			handleTemporaryObjects(spawnedObject);
			spawns.insertHead(spawnedObject);
		}
		spawnedObject.id = k;
		spawnedObject.type = k1;
		spawnedObject.orientation = l;
		spawnedObject.delay = j2;
		spawnedObject.getLongetivity = j;
	}

	private boolean interfaceIsSelected(RSInterface class9) {
		return interfaceManager.interfaceIsSelected(class9);
	}

	public void doFlamesDrawing() {



	}

	public void method134(Buffer stream) {
		int j = stream.readBits(8);
		if (j < playerCount) {
			for (int k = j; k < playerCount; k++)
				anIntArray840[anInt839++] = playerIndices[k];

		}
		if (j > playerCount) {
			Signlink.reporterror(myUsername + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for (int l = 0; l < j; l++) {
			int i1 = playerIndices[l];
			Player player = players[i1];
			player.index = i1;
			int j1 = stream.readBits(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.lastUpdateTick = loopCycle;
			} else {
				int k1 = stream.readBits(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.lastUpdateTick = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.lastUpdateTick = loopCycle;
					int l1 = stream.readBits(3);
					player.moveInDir(false, l1);
					int j2 = stream.readBits(1);
					if (j2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
					player.lastUpdateTick = loopCycle;
					int i2 = stream.readBits(3);
					player.moveInDir(true, i2);
					int k2 = stream.readBits(3);
					player.moveInDir(true, k2);
					int l2 = stream.readBits(1);
					if (l2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 3)
					anIntArray840[anInt839++] = i1;
			}
		}
	}

	public Sprite loginAsset0; //Remeber_0
	public Sprite loginAsset1; //Remember_1
	public Sprite loginAsset2; //Remember_2
	public Sprite loginAsset3; //Remember_3
	public Sprite loginAsset4; // LOGO
	public  Sprite loginAsset5; // Login_Box
	public Sprite loginAsset6; // Input_Box
	public Sprite loginAsset7; // Input_Box_Hover
	public Sprite loginAsset8; // Input_Box_2
	public Sprite loginAsset9; // Input_Box_Hover_2
	public Sprite loginAsset10; // Play_Now
	public Sprite loginAsset11; // Play_Now_Hover
	public Sprite loginAsset12;
	public Sprite loginAsset13;
	public Sprite loginAsset14;
	public Sprite loginAsset15;
	public Sprite loginAsset16;
	public Sprite loginAsset17;
	public Sprite loginsprites;
	public String firstLoginMessage = "";

	public boolean rememberMeHover;
	public boolean loginBoxHover;
	public boolean passwordBoxHover;
	public boolean playNowHover;
	public boolean saveAccountHover1;
	public boolean saveAccountHover2;
	public boolean saveAccountHover3;
	long lastLogin = 0;
	long loadDelay = 0;
	private Sprite loginScreenBackground;
	private Sprite loginScreenBackgroundCaptcha;
	private Sprite captchaExit;
	private Sprite captchaExitHover;
	private Sprite logo2021;

	private Sprite captcha;

	public void drawLoginScreen() {
		loginScreen.draw();
	}

	private void drawFlames() {
		try {
			long l = System.currentTimeMillis();
			int i = 0;
			int j = 20;
			while (aBoolean831) {
				anInt1208++;
				if (++i > 10) {
					long l1 = System.currentTimeMillis();
					int k = (int) (l1 - l) / 10 - j;
					j = 40 - k;
					if (j < 5)
						j = 5;
					i = 0;
					l = l1;
				}
				try {
					Thread.sleep(j);
				} catch (Exception _ex) {
				}
			}
		} catch (Exception _ex) {
			drawingFlames = false;
		}
	}


	public void method139(Buffer stream) {
		try {
			stream.initBitAccess();
			int localNpcListSize = stream.readBits(8);
			if (localNpcListSize < npcCount) {
				for (int l = localNpcListSize; l < npcCount; l++)
					anIntArray840[anInt839++] = npcIndices[l];

			}
			if (localNpcListSize > npcCount) {
				Signlink.reporterror(myUsername + " Too many npcs");
				throw new RuntimeException("eek");
			}
			npcCount = 0;
			for (int i1 = 0; i1 < localNpcListSize; i1++) {
				int npcIndex = npcIndices[i1];
				Npc npc = npcs[npcIndex];
				npc.index = npcIndex;
				int k1 = stream.readBits(1);
				if (k1 == 0) {
					npcIndices[npcCount++] = npcIndex;
					npc.lastUpdateTick = loopCycle;
				} else {
					int l1 = stream.readBits(2);
					if (l1 == 0) {
						npcIndices[npcCount++] = npcIndex;
						npc.lastUpdateTick = loopCycle;
						anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 1) {
						npcIndices[npcCount++] = npcIndex;
						npc.lastUpdateTick = loopCycle;
						int i2 = stream.readBits(3);
						npc.moveInDir(false, i2);
						int k2 = stream.readBits(1);
						if (k2 == 1)
							anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 2) {
						npcIndices[npcCount++] = npcIndex;
						npc.lastUpdateTick = loopCycle;
						int j2 = stream.readBits(3);
						npc.moveInDir(true, j2);
						int l2 = stream.readBits(3);
						npc.moveInDir(true, l2);
						int i3 = stream.readBits(1);
						if (i3 == 1)
							anIntArray894[anInt893++] = npcIndex;
					} else if (l1 == 3)
						anIntArray840[anInt839++] = npcIndex;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean clickInRegion(int x1, int y1, int x2, int y2) {
		if (MouseHandler.saveClickX >= x1 && MouseHandler.saveClickX <= x2 && MouseHandler.saveClickY >= y1 && MouseHandler.saveClickY <= y2) {
			return true;
		}
		return false;
	}

	public boolean mouseInRegion(int x1, int y1, int x2, int y2) {
		if (MouseHandler.mouseX >= x1 && MouseHandler.mouseX <= x2 && MouseHandler.mouseY >= y1 && MouseHandler.mouseY <= y2) {
			return true;
		}
		return false;
	}

	public boolean rememberPasswordHover;

	@SuppressWarnings("static-access")
	private void processLoginScreenInput() {
		loginScreen.handleInput();
	}

	private String loginScreenInput(String current, int l1, boolean flag1, int maxWidth, Runnable tab, Runnable enter) {
		if (l1 == 8 && current.length() > 0)
			current = current.substring(0, current.length() - 1);
		if (l1 == 9) {
			if (tab != null)
				tab.run();
		} else if (l1 == 10 || l1 == 13) {
			if (enter != null)
				enter.run();
			return current;
		}
		if (flag1)
			current += (char) l1;
		if (current.length() > maxWidth)
			current = current.substring(0, maxWidth);
		return current;
	}

	private void markMinimap(Sprite sprite, int xPosition, int yPosition) {
		int k = viewRotation + camAngleY & 0x7ff;
		int boundry = xPosition * xPosition + yPosition * yPosition;
		if (boundry > 5750)
			return;
		int i1 = Model.SINE[k];
		int j1 = Model.COSINE[k];
		i1 = (i1 * 256) / (minimapZoom + 256);
		j1 = (j1 * 256) / (minimapZoom + 256);
		int k1 = yPosition * i1 + xPosition * j1 >> 16;
		int l1 = yPosition * j1 - xPosition * i1 >> 16;

		try {
			int xOffset = !isResized() ? 516 : 0;
			if (!isResized())
				sprite.drawSprite(((97 + k1) - sprite.maxWidth / 2) + 30 + xOffset, 83 - l1 - sprite.maxHeight / 2 - 4 + 5);
			else
				sprite.drawSprite(((77 + k1) - sprite.maxWidth / 2) + 4 + (canvasWidth - 167) + xOffset,
						85 - l1 - sprite.maxHeight / 2 - 4);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void updatePlayers(int i, Buffer stream) {
		anInt839 = 0;
		anInt893 = 0;
		method117(stream);
		method134(stream);
		method91(stream, i);
		method49(stream);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (players[l].lastUpdateTick != loopCycle)
				players[l] = null;
		}

		if (stream.currentPosition != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + stream.currentPosition + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < playerCount; i1++)
			if (players[playerIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
				throw new RuntimeException("eek");
			}

	}

	private void setCameraPos(int j, int k, int l, int i1, int j1, int k1) {
		int l1 = 2048 - k & 0x7ff;
		int i2 = 2048 - j1 & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if (l1 != 0) {
			int i3 = Model.SINE[l1];
			int k3 = Model.COSINE[l1];
			int i4 = k2 * k3 - (int) (l2 * i3) >> 16;
			l2 = k2 * i3 + (int) (l2 * k3) >> 16;
			k2 = i4;
		}
		if (i2 != 0) {
			int j3 = Model.SINE[i2];
			int l3 = Model.COSINE[i2];
			int j4 = (int) (l2 * j3) + j2 * l3 >> 16;
			l2 = (int) (l2 * l3) - j2 * j3 >> 16;
			j2 = j4;
		}
		cameraX = l - j2;
		cameraY = i1 - k2;
		cameraZ = k1 - l2;
		cameraPitch = k;
		cameraYaw = j1;
		onCameraPitchChanged(k);
	}

	public void updateStrings(String str, int i) {
		switch (i) {
			case 22499:
				sendFrame126(currentLevels[5]+"/"+ maximumLevels[5], 22499);
				break;
			case 49020:
				achievementCutoff = Integer.parseInt(str);
				break;
			case 1675:
			case 17508:
				sendFrame126(str, 1675);
				sendFrame126(str, 17508);
				break;// Stab
			case 1676:
			case 17509:
				sendFrame126(str, 1676);
				sendFrame126(str, 17509);
				break;// Slash
			case 1677:
			case 17510:
				sendFrame126(str, 1677);
				sendFrame126(str, 17510);
				break;// Crush
			case 1678:
			case 17511:
				sendFrame126(str, 1678);
				sendFrame126(str, 17511);
				break;// Magic
			case 1679:
			case 17512:
				sendFrame126(str, 1679);
				sendFrame126(str, 17512);
				break;// Range
			case 1680:
			case 17513:
				sendFrame126(str, 1680);
				sendFrame126(str, 17513);
				break;// Stab
			case 1681:
			case 17514:
				sendFrame126(str, 1681);
				sendFrame126(str, 17514);
				break;// Slash
			case 1682:
			case 17515:
				sendFrame126(str, 1682);
				sendFrame126(str, 17515);
				break;// Crush
			case 1683:
			case 17516:
				sendFrame126(str, 1683);
				sendFrame126(str, 17516);
				break;// Magic
			case 1684:
			case 17517:
				sendFrame126(str, 1684);
				sendFrame126(str, 17517);
				break;// Range
			case 1686:
			case 17518:
				sendFrame126(str, 1686);
				sendFrame126(str, 17518);
				break;// Strength
			case 1687:
			case 17519:
				sendFrame126(str, 1687);
				sendFrame126(str, 17519);
				break;// Prayer
		}
	}

	public void sendFrame126(String str, int i) { interfaceManager.sendFrame126(str, i); }

	public void sendKeyboardAction(int action) {
		stream.createFrame(201);
		stream.writeByte(action);
	}

	public void sendFrame248(int interfaceID, int sideInterfaceID) { interfaceManager.sendFrame248(interfaceID, sideInterfaceID); }

	private boolean parsePacket() {
		return packetHandler.parsePacket();
	}

	public static final int INTERFACE_ID = 47000;
	public static final int BOXES64 = 28; // 28 * 64 boxes
	private boolean spinClick;
	private int spins;
	private int spinNum;

	public void setSpinClick(boolean spinClick) { this.spinClick = spinClick; }

	public boolean beginMysteryBoxSpin() {
		if (openInterfaceID != INTERFACE_ID || spinClick) {
			return false;
		}
		spinClick = true;
		return true;
	}

	public void spin() {
		if (openInterfaceID != INTERFACE_ID || !spinClick) {
			return;
		}

		RSInterface items = RSInterface.interfaceCache[47100];
		RSInterface boxes = RSInterface.interfaceCache[47200];
		if (spins < 100) {
			shift(items, boxes, 8);
		}
		else if (spins < 200) {
			shift(items, boxes, 5);
		}
		else if (spins < 300) {
			shift(items, boxes, 4);
		}
		else if (spins < 400) {
			shift(items, boxes, 3);
		}
		else if (spins < 488) {
			shift(items, boxes, 2);
		}
		else if (spins < 562) {
			shift(items, boxes, 1);
		}
		else {
			spinComplete();
		}
	}

	private void shift(RSInterface items, RSInterface boxes, int shiftAmount) {
		items.childX[0] -= shiftAmount;
		for(int i=0; i<BOXES64; i++){ boxes.childX[i] -= shiftAmount; }
		spins++;
	}

	private void spinComplete() {
		// Reset
		spins = 0;
		spinClick = false;
		spinNum++;
		// Notify server mystery roll completion using the container-action payload
		// expected by legacy and config-driven mystery handlers (interface 41609).
		stream.createFrame(145);
		stream.method432(41609);
		stream.method432(0);
		stream.method432(0);
	}


	public void reset() {
		//System.out.println("test1");
		if (spinClick) {
			return;
		}

		//System.out.println("test2");

		spinNum = 0;
		RSInterface items = RSInterface.interfaceCache[47100];
		RSInterface boxes = RSInterface.interfaceCache[47200];
		if (items == null || boxes == null || items.childX == null || items.childX.length == 0 || boxes.childX == null) {
			return;
		}
		items.childX[0] = 0;
		int x = 0;
		int limit = Math.min(BOXES64, boxes.childX.length);
		for (int z=0; z<limit; z++) {
			boxes.childX[z] = x;
			x += 2880;
		}
	}

	private String coloredItemName = "";
	int coloredItemColor = 0xffffff;

	public int currentFog = 0;
	public int[] rainbowFog = {0xFF0000,0xFF7F00,0xFFFF00,0x00FF00,0x0000FF,0x4B0082,0x9400D3};
	public long lastFog=0;

	public boolean runHover;


	public static JagexNetThread jagexNetThread = new JagexNetThread();

	static int method1389(int var0) {
		return var0 * 3 + 600;
	}

	public static int field625 = 256;
	static int field626 = 205;
	static int field440 = 1;
	static int field630 = 32767;
	static short field488 = 1;
	static short field562 = 32767;

	static void setViewportShape(int var0, int var1, int var2, int var3, boolean var4) {
		if (var2 < 1) {
			var2 = 1;
		}

		if (var3 < 1) {
			var3 = 1;
		}

		int var5 = var3 - 334;
		int var6;
		if (var5 < 0) {
			var6 = Client.field625;
		} else if (var5 >= 100) {
			var6 = Client.field626;
		} else {
			var6 = (Client.field626 - Client.field625) * var5 / 100 + Client.field625;
		}

		int var7 = var3 * var6 * 512 / (var2 * 334);
		int var8;
		int var9;
		short var17;
		if (var7 < Client.field488) {
			var17 = Client.field488;
			var6 = var17 * var2 * 334 / (var3 * 512);
			if (var6 > Client.field630) {
				var6 = Client.field630;
				var8 = var3 * var6 * 512 / (var17 * 334);
				var9 = (var2 - var8) / 2;
				if (var4) {
					Rasterizer2D.resetClip();
					Rasterizer2D.drawItemBox(var0, var1, var9, var3, -16777216);
					Rasterizer2D.drawItemBox(var0 + var2 - var9, var1, var9, var3, -16777216);
				}

				var0 += var9;
				var2 -= var9 * 2;
			}
		} else if (var7 > Client.field562) {
			var17 = Client.field562;
			var6 = var17 * var2 * 334 / (var3 * 512);
			if (var6 < Client.field440) {
				var6 = Client.field440;
				var8 = var17 * var2 * 334 / (var6 * 512);
				var9 = (var3 - var8) / 2;
				if (var4) {
					Rasterizer2D.resetClip();
					Rasterizer2D.drawItemBox(var0, var1, var2, var9, -16777216);
					Rasterizer2D.drawItemBox(var0, var3 + var1 - var9, var2, var9, -16777216);
				}

				var1 += var9;
				var3 -= var9 * 2;
			}
		}

		viewportZoom = var3 * var6 / 334;
		if (var2 != Client.instance.getViewportWidth() || var3 != Client.instance.getViewportHeight()) {
			int[] var16 = new int[9];

			for (var9 = 0; var9 < var16.length; ++var9) {
				int var10 = var9 * 32 + 15 + 128;
				int var11 = method1389(var10);
				int var12 = Rasterizer3D.Rasterizer3D_sine[var10];
				int var14 = var3 - 334;
				if (var14 < 0) {
					var14 = 0;
				} else if (var14 > 100) {
					var14 = 100;
				}

				int var15 = (Client.zoomWidth - Client.zoomHeight) * var14 / 100 + Client.zoomHeight;
				int var13 = var11 * var15 / 256;
				var16[var9] = var13 * var12 >> 16;
			}

			SceneGraph.buildVisiblityMap(var16, 500, 800, var2 * 334 / var3, 334);
		}

		Client.viewportOffsetX = var0;
		Client.viewportOffsetY = var1;
		Client.viewportWidth = var2;
		Client.viewportHeight = var3;
	}

	public static int viewportWidth = 0;
	public static int viewportHeight = 0;
	static short zoomHeight = 256;
	static short zoomWidth = 320;
	public static int viewportOffsetX = 0;
	public static int viewportOffsetY = 0;



	public void clearTopInterfaces() {
		stream.createFrame(130);
		if (invOverlayInterfaceID != 0) {
			invOverlayInterfaceID = 0;
			needDrawTabArea = true;
			continuedDialogue = false;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
			continuedDialogue = false;
		}
		openInterfaceID = -1;
		fullscreenInterfaceID = -1;
	}
	Sprite[] chatButtons;

	public static class423 archive5;
	public Client() {
        ArchiveLoader.archiveLoaders = new ArrayList<>(10);
		ArchiveLoader.archiveLoadersDone = 0;
		fullscreenInterfaceID = -1;
		oculusOrbSlowedSpeed = 40;
		oculusOrbNormalSpeed = 20;
		camFollowHeight = 50;
		firstLoginMessage = "Enter Username & Password";
		xpAddedPos = expAdded = 0;
		keyManager = new KeyEventProcessorImpl();
		xpLock = false;
		experienceCounter = 0;
		soundType = new int[50];
		soundDelay = new int[50];
		sound = new int[50];
		chatTypeView = 0;
		mapsLoaded = 0;
		totalMaps = 1;
		objectsLoaded = 0;
		totalObjects = 1;
		clanTitles = new String[500];
		archive5 = new class423(8, class421.field4606);
		clanChatMode = 0;
		channelButtonHoverPosition = -1;
		channelButtonClickPosition = 0;
		travelDistances = new int[104][104];
		friendsNodeIDs = new int[200];
		groundItems = new NodeDeque[4][104][104];
		aBoolean831 = false;
		aStream_834 = new Buffer(new byte[5000]);
		npcs = new Npc[65536];
		npcIndices = new int[65536];
		anIntArray840 = new int[1000];
		aStream_847 = Buffer.create();
		aBoolean848 = true;
		setGameState(GameState.STARTING);
		openInterfaceID = -1;
		currentExp = new int[Skills.SKILLS_COUNT];
		quakeDirectionActive = new boolean[5];
		drawFlames = false;
		reportAbuseInput = "";
		localPlayerIndex = -1;
		isMenuOpen = false;
		inputString = "";
		maxPlayers = 2048;
		maxPlayerCount = 2047;
		players = new Player[maxPlayers];
		playerIndices = new int[maxPlayers];
		anIntArray894 = new int[maxPlayers];
		aStreamArray895s = new Buffer[maxPlayers];
		anIntArrayArray901 = new int[104][104];
		aByteArray912 = new byte[16384];
		currentLevels = new int[Skills.SKILLS_COUNT];
		ignoreListAsLongs = new long[100];
		loadingError = false;
		fourPiOverPeriod = new int[5];
		tileCycleMap = new int[104][104];
		chatTypes = new int[500];
		chatNames = new String[500];
		chatMessages = new String[500];
		chatButtons = new Sprite[4];
		aBoolean954 = true;
		friendsListAsLongs = new long[200];
		currentSong = -1;
		drawingFlames = false;
		viewportTempX = -1;
		viewportTempY = -1;
		anIntArray968 = new int[33];
		anIntArray969 = new int[256];

		variousSettings = new int[25000];
		aBoolean972 = false;
		overheadTextLimit = 50;
		overheadTextXs = new int[overheadTextLimit];
		overheadTextYs = new int[overheadTextLimit];
		overheadTextAscents = new int[overheadTextLimit];
		overheadTextXOffsets = new int[overheadTextLimit];
		overheadTextColors = new int[overheadTextLimit];
		overheadTextEffects = new int[overheadTextLimit];
		overheadTextCyclesRemaining = new int[overheadTextLimit];
		overheadText = new String[overheadTextLimit];
		lastKnownPlane = -1;
		hitMarks = new Sprite[20];
		anIntArray990 = new int[5];
		aBoolean994 = false;
		amountOrNameInput = "";
		projectiles = new NodeDeque();
		aBoolean1017 = false;
		openWalkableWidgetID = -1;
		cameraUpdateCounters = new int[5];
		aBoolean1031 = false;
		mapFunctions = new Sprite[119];
		dialogID = -1;
		maximumLevels = new int[Skills.SKILLS_COUNT];
		anIntArray1045 = new int[25000];
		aBoolean1047 = true;
		anIntArray1052 = new int[152];
		anIntArray1229 = new int[152];
		incompleteAnimables = new NodeDeque();
		anIntArray1057 = new int[33];
		aClass9_1059 = new RSInterface();
		barFillColor = 0x4d4233;
		myAppearance = new int[7];
		minimapHintX = new int[1000];
		minimapHintY = new int[1000];
		friendsList = new String[200];
		inStream = Buffer.create();
		expectedCRCs = new int[9];
		menuIdentifiers = new int[2500];
		menuTargets = new String[2500];
		menuShiftClick = new boolean[2500];
		menuArguments1 = new int[2500];
		menuArguments2 = new int[2500];
		menuOpcodes = new int[2500];
		menuActions = new String[2500];
		headIcons = new Sprite[20];
		skullIcons = new Sprite[20];
		headIconsHint = new Sprite[20];
		tabAreaAltered = false;
		aString1121 = "";
		playerOptions = new String[6];
		playerOptionsHighPriority = new boolean[6];
		constructRegionData = new int[4][13][13];
		minimapHint = new Sprite[1000];
		inTutorialIsland = false;
		continuedDialogue = false;
		musicEnabled = true;
		needDrawTabArea = false;
		loggedIn = false;
		canMute = false;
		isInInstance = false;
		isCameraLocked = false;
		myUsername = "";
		setPassword("");
		captchaInput = "";
		genericLoadingError = false;
		reportAbuseInterfaceID = -1;
		spawns = new NodeDeque();
		camAngleX = 128;
		invOverlayInterfaceID = 0;
		stream = Buffer.create();
		menuTargets = new String[500];
		quakeAmplitude = new int[5];
		chatAreaScrollLength = 78;
		promptInput = "";
		modIconss = new Sprite[40];
		tabID = 3;
		inputTaken = false;
		songChanging = true;
		collisionMaps = new CollisionMap[4];
		aBoolean1242 = false;
		rsAlreadyLoaded = false;
		welcomeScreenRaised = true;
		messagePromptRaised = false;
		backDialogID = -1;
		bigX = new int[4000];
		bigY = new int[4000];
		chatTimes = new long[500];
	}

	public int getLocalPlayerX() {
		return baseX + (localPlayer.x - 6 >> 7);
	}

	public int getLocalPlayerY() {
		return baseY + (localPlayer.y - 6 >> 7);
	}

	boolean isInWilderness() {
		if (localPlayer == null) {
			return false;
		}
		int y = getLocalPlayerY();
		return y > 3520 && y < 4000;
	}

	public int xpCounter;
	public int expAdded;
	public int xpAddedPos;
	public boolean xpLock;

	private Sprite chatArea;
	Sprite infinity;

	public String name;
	public String message;
	public int chatTypeView;
	public int clanChatMode;
	public int duelMode;
	public int autocastId = 0;
	public boolean autocast = false;

	public static Sprite[] cacheSprite, cacheSprite1, cacheSprite2, cacheSprite3, cacheSprite4;
	public static Sprite[] cacheInterface;

	private IndexedImage titleButton;
	int ignoreCount;
	long longStartTime;
	private int[][] travelDistances;
	int[] friendsNodeIDs;
	NodeDeque[][][] groundItems;
	private int[] anIntArray828;
	private int[] anIntArray829;
	private volatile boolean aBoolean831;

	private Buffer aStream_834;
	public Npc[] npcs;
	int npcCount;
	public int[] npcIndices;
	private int anInt839;
	private int[] anIntArray840;
	int dealtWithPacket;
	int dealtWithPacketSize;
	int previousPacket1;
	int previousPacket2;
	int previousPacketSize2;
	int previousPacketSize1;
	String clickToContinueString;
	int privateChatMode;
	private Buffer aStream_847;
	boolean aBoolean848;
	static int anInt849;
	private int[] anIntArray850;
	private int[] anIntArray851;
	private int[] anIntArray852;
	private int[] anIntArray853;
	private static int anInt854;
	public int hintArrowType;
	public static int openInterfaceID;
	public int cameraX;
	public int cameraY;
	public int cameraZ;
	public int cameraPitch;
	public int cameraYaw;
	final int[] currentExp;
	private Sprite mapFlag;
	private Sprite mapMarker;

	final boolean[] quakeDirectionActive;
	int weight;
	private MouseDetection mouseDetection;
	private volatile boolean drawFlames;
	String reportAbuseInput;
	int localPlayerIndex;
	public boolean isMenuOpen;
	int anInt886;
	public static String inputString;
	public static final int DEFAULT_CHAT_INPUT_LENGTH = 200;
	public static final int MAX_CHAT_INPUT_LENGTH = 200;
	final int maxPlayers;
	public final int maxPlayerCount;
	public Player[] players;
	public int playerCount;
	int[] playerIndices;
	private int anInt893;
	private int[] anIntArray894;
	private Buffer[] aStreamArray895s;
	private int viewRotationOffset;
	int friendsCount;
	int anInt900;
	private int[][] anIntArrayArray901;
	private byte[] aByteArray912;
	int anInt913;
	int crossX;
	int crossY;
	int crossIndex;
	int crossType;
	public int plane;
	public final int[] currentLevels;
	int anInt924;
	final long[] ignoreListAsLongs;
	private boolean loadingError;
	final int[] fourPiOverPeriod;
	private int[][] tileCycleMap;
	Sprite aClass30_Sub2_Sub1_Sub1_931;
	Sprite aClass30_Sub2_Sub1_Sub1_932;
	public int hintArrowPlayerIndex;
	int hintIconX;
	int hintIconYpos;
	int hintIconFloorPos;
	int anInt937;
	int anInt938;
	private long[] chatTimes;
	int[] chatTypes;
	String[] chatNames;
	String[] chatMessages;
	public int tickDelta;
	public SceneGraph scene;


	long aLong953;
	private boolean aBoolean954;
	long[] friendsListAsLongs;
	int currentSong;
	static int nodeID = 1;
	static int portOff;
	static boolean clientData;
	static boolean isMembers = true;
	static boolean lowMem;
	private volatile boolean drawingFlames;
	public int viewportTempX;
	public int viewportTempY;
	private final int[] field758 = { 0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff };
	private final int[] anIntArray968;
	private final int[] anIntArray969;

	public int variousSettings[];
	boolean aBoolean972;
	private final int overheadTextLimit;
	private final int[] overheadTextXs;
	private final int[] overheadTextYs;
	private final int[] overheadTextAscents;
	private final int[] overheadTextXOffsets;
	private final int[] overheadTextColors;


	public static int overheadTextCount;

	public static int[][] chatEffectGradientColors;
	private final int[] overheadTextEffects;
	private final int[] overheadTextCyclesRemaining;
	private final String[] overheadText;

	private int lastKnownPlane;
	int anInt986;
	private Sprite[] hitMarks;
	int anInt989;
	final int[] anIntArray990;
	final boolean aBoolean994;
	private int cinematicCamXViewpointLoc;
	private int cinematicCamYViewpointLoc;
	private int cinematicCamZViewpointLoc;
	private int anInt998;
	private int anInt999;
	Cryption encryption;
	private Sprite mapEdge;
	public static final int[][] APPEARANCE_COLORS = {
			{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322,
					-21845, 200, 571, 908, 21830, 28946, -15701, -14010}, //HAIR
			{8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239,
					8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, //TORSO
			{25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533,
					8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, //LEGS
			{4626, 11146, 6439, 12, 4758, 10270}, //FEET
			{4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 0, 12821, 100, 1023, 44031, 48127, 11237, 32741, 7167, 53631, 22143, 4550} //SKIN - 0-19 (20 values)
	};


	public static final short[] field3532 = new short[]{6798, 8741, 25238, 4626, 4550};

	public static final short[][] PLAYER_BODY_RECOLOURS = new short[][]{{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 17050, 0, 127, -31821, -17991}};

	public static final short[] field3534 = new short[]{-10304, 9104, -1, -1, -1};

	public static final short[][] field3535 = new short[][]{{6554, 115, 10304, 28, 5702, 7756, 5681, 4510, -31835, 22437, 2859, -11339, 16, 5157, 10446, 3658, -27314, -21965, 472, 580, 784, 21966, 28950, -15697, -14002}, {9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027, 1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019}, new short[0], new short[0], new short[0]};


	Sprite multiOverlay;
	public String amountOrNameInput;
	private int anInt1005;
	int daysSinceLastLogin;
	int packetSize;
	int incomingPacket;
	int anInt1009;
	private int anInt1010;
	private int anInt1011;
	NodeDeque projectiles;
	public int oculusOrbFocalPointX;
	public int oculusOrbFocalPointY;
	private int anInt1016;
	private boolean aBoolean1017;
	public int openWalkableWidgetID;
	static final int[] SKILL_EXPERIENCE;
	int minimapState;
	private int anInt1022;

	Sprite scrollBar1;
	Sprite scrollBar2;
	int anInt1026;
	public final int[] cameraUpdateCounters;
	boolean aBoolean1031;
	private static Sprite[] mapFunctions;
	static int baseX;
	static int baseY;
	int previousAbsoluteX;
	int previousAbsoluteY;
	public int loginFailures;
	int anInt1039;
	private int anInt1040;
	private int anInt1041;
	int dialogID;
	public final int[] maximumLevels;
	final int[] anIntArray1045;
	int anInt1046;
	boolean aBoolean1047;
	public TextDrawingArea smallText;
	public TextDrawingArea XPFONT;
	public TextDrawingArea aTextDrawingArea_1271;
	public TextDrawingArea chatTextDrawingArea;
	public TextDrawingArea aTextDrawingArea_1273;
	public RSFont newSmallFont;
	public RSFont newRegularFont;
	public RSFont newBoldFont;
	public RSFont newFancyFont;

	/**
	 * New fonts
	 */
	public static RSFont lato, latoBold, kingthingsPetrock, kingthingsPetrockLight;

	int anInt1048;
	private String aString1049;
	private static int anInt1051;
	private final int[] anIntArray1052;
	private FileArchive titleStreamLoader;
	int anInt1055;
	public NodeDeque incompleteAnimables;
	private final int[] anIntArray1057;
	public final RSInterface aClass9_1059;

	private static int anInt1061;
	final int barFillColor;
	int friendsListAction;
	final int[] myAppearance;
	int mouseInvInterfaceIndex;
	int lastActiveInvInterface;

	public int currentRegionX;
	public int currentRegionY;
	private int mapIconAmount;
	private int[] minimapHintX;
	private IndexedImage[] mapSceneSprites;
	private int[] minimapHintY;
	private Sprite mapDotItem;
	private Sprite mapDotNPC;
	private Sprite mapDotPlayer;
	private Sprite mapDotFriend;
	private Sprite mapDotTeam;
	private Sprite mapDotClan;
	private int anInt1079;

	String[] friendsList;
	Buffer inStream;
	int draggingItemInterfaceId;
	int itemDraggingSlot;
	int activeInterfaceType;
	int anInt1087;
	int anInt1088;
	public static int anInt1089;
	private final int[] expectedCRCs;
	public static String[] menuTargets;
	public static String[] menuActions;
	public static int[] menuIdentifiers;
	public static boolean[] menuShiftClick;
	public static int[] menuArguments1;
	public static int[] menuArguments2;
	public static int[] menuOpcodes;
	private Sprite[] headIcons;
	private Sprite[] skullIcons;
	Sprite[] headIconsHint;

	public static boolean tabAreaAltered;

	int anInt1104;

	private static int anInt1117;
	int membersInt;
	String aString1121;
	String enterInputInChatString;
	public static Player localPlayer;
	public boolean broadcastActive;
	public long broadcastTimer;
	public String broadcastMessage;
	final String[] playerOptions;
	final boolean[] playerOptionsHighPriority;
	final int[][][] constructRegionData;
	public final static int[] tabInterfaceIDs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	private int cameraOffsetY;
	public int menuOptionsCount;
	static int anInt1134;
	public int spellSelected;
	int anInt1137;
	int spellUsableOn;
	String spellTooltip;
	private Sprite[] minimapHint;
	boolean inTutorialIsland;
	static int anInt1142;
	int energy;
	boolean continuedDialogue;
	Sprite[] crosses;
	boolean musicEnabled;
	private IndexedImage[] aBackgroundArray1152s;
	public static boolean needDrawTabArea;
	int unreadMessages;
	static int anInt1155;
	static boolean fpsOn;
	static boolean drawGrid;
	public static boolean loggedIn;
	boolean canMute;
	boolean isInInstance;
	public boolean isCameraLocked;
	public static int loopCycle;

	int daysSinceRecovChange;
	RSSocket socketStream;
	public static int minimapZoom;
	static int anInt1175;
	private boolean genericLoadingError;
	final int[] objectTypes = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };
	int reportAbuseInterfaceID;
	NodeDeque spawns;
	private int[] anIntArray1180;
	private int[] anIntArray1181;
	private int[] anIntArray1182;
	byte[][] regionLandArchives;
	public int camAngleX;
	static int viewRotation;
	public int camAngleDY;
	public int camAngleDX;
	static int anInt1188;
	int invOverlayInterfaceID;
	private int[] anIntArray1190;
	private int[] anIntArray1191;
	public static Buffer stream;
	int anInt1193;
	boolean splitPrivateChat = true;
	String[] clanList = new String[100];

	final int[] quakeAmplitude;
	public static final int[] SHIRT_SECONDARY_COLORS = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145,
			58654, 5027, 1457, 16565, 34991, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019}; // 29 values to match torso color range 0-28
	private static boolean flagged;
	private int anInt1208;
	public int camAngleY;
	public static int chatAreaScrollLength;
	String promptInput;
	int mouseClickCount;
	public int[][][] tileHeights;
	private long aLong1215;

	public final Sprite[] modIconss;
	private long aLong1220;
	public static int tabID;
	public int hintArrowNpcIndex;
	public static boolean inputTaken;
	public static int inputDialogState;
	static int anInt1226;
	int nextSong;
	boolean songChanging;
	private final int[] anIntArray1229;
	public CollisionMap[] collisionMaps;
	public static int anIntArray1232[];
	int[] regions;
	int[] regionLandIds;
	int[] regionLocIds;
	private int anInt1237;
	private int anInt1238;
	public final int anInt1239 = 100;
	boolean aBoolean1242;
	int atInventoryLoopCycle;
	int atInventoryInterface;
	int atInventoryIndex;
	int atInventoryInterfaceType;
	byte[][] regionMapArchives;
	int tradeMode;
	int gameMode;
	private int chatEffects;
	int anInt1251;
	private final boolean rsAlreadyLoaded;
	private int anInt1253;
	boolean welcomeScreenRaised;
	boolean messagePromptRaised;
	public byte[][][] tileFlags;
	int prevSong;
	int destX;
	int destY;
	private Sprite minimapImage;
	public Sprite backgroundFix;
	private int anInt1264;
	int viewportDrawCount;
	int localX;
	int localY;
	public static int oculusOrbSlowedSpeed;
	public static int oculusOrbNormalSpeed;
	public static int camFollowHeight;
	public int oculusOrbState;
	private int anInt1275;
	public static int backDialogID;
	private int cameraOffsetX;
	private int[] bigX;
	private int[] bigY;
	public int itemSelected;
	int anInt1283;
	int anInt1284;
	int anInt1285;
	String selectedItemName;
	int publicChatMode;
	private static int anInt1288;
	public static int anInt1290;
	public static String server = Configuration.DEDICATED_SERVER_ADDRESS;
	public static int port = Configuration.PORT;
	public static boolean controlIsDown;
	public int drawCount;
	public int fullscreenInterfaceID;
	public int anInt1044;// 377
	public int anInt1129;// 377
	public int anInt1315;// 377
	public int anInt1500;// 377
	public int anInt1501;// 377
	public int[] fullScreenTextureArray;

	public String myUsername = "";
	public String myPassword;

	private static final Object CAPTCHA_LOCK = new Object();
	private String captchaInput;

	public void setPassword(String password) {
		myPassword = password;
	}

	public String getPassword() {
		return myPassword;
	}

	public void launchURL(String url) {
		if (server.equals("173.185.70.167")) {
			javax.swing.JOptionPane.showMessageDialog(this, "Staff just tried to direct you to: " + url);
			return;
		}
		if (!url.toLowerCase().startsWith("http")) {
			url = "http://" + url;
		}
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				@SuppressWarnings("rawtypes")
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				@SuppressWarnings("unchecked")
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { // assume Unix or Linux
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI(url));
				}
			}
		} catch (Exception e) {
			pushMessage("Failed to open URL.", 0, "");
			System.err.println("Failed to launcher url on operating system " + osName);
			e.printStackTrace();
		}
	}
	private void drawInputField(RSInterface child, int xPosition, int yPosition, int width, int height) {
		interfaceManager.drawInputField(child, xPosition, yPosition, width, height);
	}
	public void setInputFieldFocusOwner(RSInterface owner) { interfaceManager.setInputFieldFocusOwner(owner); }
	public RSInterface getInputFieldFocusOwner() { return interfaceManager.getInputFieldFocusOwner(); }
	public void resetInputFieldFocus() { interfaceManager.resetInputFieldFocus(); }
	public boolean isFieldInFocus() { return interfaceManager.isFieldInFocus(); }

	public static boolean scrollbarVisible(RSInterface widget) {
		return InterfaceManager.scrollbarVisible(widget);
	}

	public void mouseWheelDragged(int i, int j) {
		this.camAngleDY += i * 3;
		this.camAngleDX += (j << 1);
	}

	public static int anInt1401 = 256;
	private int musicVolume = 255;
	public static long aLong1432;
	final int[] sound;
	public int soundCount;
	final int[] soundDelay;
	final int[] soundType;
	static int soundEffectVolume = 127;
	public static int[] anIntArray385 = new int[] { 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800,
			12800, 12800, 12800, 12800, 12800, 12800, 12800 };
	public static boolean LOOP_MUSIC = false;

	public static final void sleep(long time) {
		if (time > 0L) {
			if (time % 10L != 0L) {
				threadSleep(time);
			} else {
				threadSleep(time - 1L);
				threadSleep(1L);
			}
		}
	}

	private static final void threadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
		}
	}

	static {
		SKILL_EXPERIENCE = new int[99];
		int i = 0;
		for (int j = 0; j < 99; j++) {
			int l = j + 1;
			int i1 = (int) (l + 300D * Math.pow(2D, l / 7D));
			i += i1;
			SKILL_EXPERIENCE[j] = i / 4;
		}
		anIntArray1232 = new int[32];
		i = 2;
		for (int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}
	}

	public static String md5Hash(String md5) {
		try {

			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();

		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public int getLevelForXPSlayer(int exp) {
		int points = 0;
		int output;
		if (exp > 104273167)
			return 120;
		for (int lvl = 1; lvl <= 120; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public void updateSkillStrings(int i) {
		switch (i) {
			case 0:
				sendFrame126("" + currentLevels[0] + "", 4004);
				sendFrame126("" + getLevelForXP(currentExp[0]) + "", 4005);
				sendFrame126("" + currentExp[0] + "", 4044);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[0]) + 1) + "", 4045);
				break;

			case 1:
				sendFrame126("" + currentLevels[1] + "", 4008);
				sendFrame126("" + getLevelForXP(currentExp[1]) + "", 4009);
				sendFrame126("" + currentExp[1] + "", 4056);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[1]) + 1) + "", 4057);
				break;

			case 2:
				sendFrame126("" + currentLevels[2] + "", 4006);
				sendFrame126("" + getLevelForXP(currentExp[2]) + "", 4007);
				sendFrame126("" + currentExp[2] + "", 4050);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[2]) + 1) + "", 4051);
				break;

			case 3:
				sendFrame126("" + currentLevels[3] + "", 4016);
				sendFrame126("" + getLevelForXP(currentExp[3]) + "", 4017);
				sendFrame126("" + currentExp[3] + "", 4080);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[3]) + 1) + "", 4081);
				break;

			case 4:
				sendFrame126("" + currentLevels[4] + "", 4010);
				sendFrame126("" + getLevelForXP(currentExp[4]) + "", 4011);
				sendFrame126("" + currentExp[4] + "", 4062);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[4]) + 1) + "", 4063);
				break;

			case 5:
				sendFrame126("" + currentLevels[5] + "", 4012);
				sendFrame126("" + getLevelForXP(currentExp[5]) + "", 4013);
				sendFrame126("" + currentExp[5] + "", 4068);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[5]) + 1) + "", 4069);
				sendFrame126("" + currentLevels[5] + "/" + getLevelForXP(currentExp[5]) + "", 687);// Prayer
				// frame
				break;

			case 6:
				sendFrame126("" + currentLevels[6] + "", 4014);
				sendFrame126("" + getLevelForXP(currentExp[6]) + "", 4015);
				sendFrame126("" + currentExp[6] + "", 4074);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[6]) + 1) + "", 4075);
				break;

			case 7:
				sendFrame126("" + currentLevels[7] + "", 4034);
				sendFrame126("" + getLevelForXP(currentExp[7]) + "", 4035);
				sendFrame126("" + currentExp[7] + "", 4134);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[7]) + 1) + "", 4135);
				break;

			case 8:
				sendFrame126("" + currentLevels[8] + "", 4038);
				sendFrame126("" + getLevelForXP(currentExp[8]) + "", 4039);
				sendFrame126("" + currentExp[8] + "", 4146);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[8]) + 1) + "", 4147);
				break;

			case 9:
				sendFrame126("" + currentLevels[9] + "", 4026);
				sendFrame126("" + getLevelForXP(currentExp[9]) + "", 4027);
				sendFrame126("" + currentExp[9] + "", 4110);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[9]) + 1) + "", 4111);
				break;

			case 10:
				sendFrame126("" + currentLevels[10] + "", 4032);
				sendFrame126("" + getLevelForXP(currentExp[10]) + "", 4033);
				sendFrame126("" + currentExp[10] + "", 4128);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[10]) + 1) + "", 4129);
				break;

			case 11:
				sendFrame126("" + currentLevels[11] + "", 4036);
				sendFrame126("" + getLevelForXP(currentExp[11]) + "", 4037);
				sendFrame126("" + currentExp[11] + "", 4140);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[11]) + 1) + "", 4141);
				break;

			case 12:
				sendFrame126("" + currentLevels[12] + "", 4024);
				sendFrame126("" + getLevelForXP(currentExp[12]) + "", 4025);
				sendFrame126("" + currentExp[12] + "", 4104);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[12]) + 1) + "", 4105);
				break;

			case 13:
				sendFrame126("" + currentLevels[13] + "", 4030);
				sendFrame126("" + getLevelForXP(currentExp[13]) + "", 4031);
				sendFrame126("" + currentExp[13] + "", 4122);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[13]) + 1) + "", 4123);
				break;

			case 14:
				sendFrame126("" + currentLevels[14] + "", 4028);
				sendFrame126("" + getLevelForXP(currentExp[14]) + "", 4029);
				sendFrame126("" + currentExp[14] + "", 4116);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[14]) + 1) + "", 4117);
				break;

			case 15:
				sendFrame126("" + currentLevels[15] + "", 4020);
				sendFrame126("" + getLevelForXP(currentExp[15]) + "", 4021);
				sendFrame126("" + currentExp[15] + "", 4092);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[15]) + 1) + "", 4093);
				break;

			case 16:
				sendFrame126("" + currentLevels[16] + "", 4018);
				sendFrame126("" + getLevelForXP(currentExp[16]) + "", 4019);
				sendFrame126("" + currentExp[16] + "", 4086);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[16]) + 1) + "", 4087);
				break;

			case 17:
				sendFrame126("" + currentLevels[17] + "", 4022);
				sendFrame126("" + getLevelForXP(currentExp[17]) + "", 4023);
				sendFrame126("" + currentExp[17] + "", 4098);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[17]) + 1) + "", 4099);
				break;

			case 18:
				sendFrame126("" + currentLevels[18] + "", 12166);
				sendFrame126("" + getLevelForXPSlayer(currentExp[18]) + "", 12167);
				sendFrame126("" + currentExp[18] + "", 12171);
				sendFrame126("" + getXPForLevel(getLevelForXPSlayer(currentExp[18]) + 1) + "", 12172);
				break;

			case 19:
				sendFrame126("" + currentLevels[19] + "", 13926);
				sendFrame126("" + getLevelForXP(currentExp[19]) + "", 13927);
				sendFrame126("" + currentExp[19] + "", 13921);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[19]) + 1) + "", 13922);
				break;

			case 20: // runecraft
				sendFrame126("" + currentLevels[20] + "", 4152);
				sendFrame126("" + getLevelForXP(currentExp[20]) + "", 4153);
				sendFrame126("" + currentExp[20] + "", 4157);
				sendFrame126("" + getXPForLevel(getLevelForXP(currentExp[20]) + 1) + "", 4159);
				break;

			// hunter is 21
		}
	}


	/**
	 * Runelite
	 */
	public DrawCallbacks drawCallbacks;
	@javax.inject.Inject
	Callbacks callbacks;

	private int gpuFlags;

	@Override
	public Callbacks getCallbacks() {
		return callbacks;
	}

	@Override
	public DrawCallbacks getDrawCallbacks() {
		return drawCallbacks;
	}

	@Override
	public void setDrawCallbacks(DrawCallbacks drawCallbacks) {
		this.drawCallbacks = drawCallbacks;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public String getBuildID() {
		return "1";
	}

	@Override
	public List<net.runelite.api.Player> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public List<NPC> getNpcs() {
		List<NPC> npcs = new ArrayList<NPC>(npcCount);
		for (int i = 0; i < npcCount; ++i)
		{
			npcs.add(this.npcs[npcIndices[i]]);
		}
		return npcs;
	}

	@Override
	public RSNPC[] getCachedNPCs() {
		return npcs;
	}

	@Override
	public RSPlayer[] getCachedPlayers() {
		return players;
	}

	@Override
	public int getLocalInteractingIndex() {
		return 0;
	}

	@Override
	public void setLocalInteractingIndex(int idx) {

	}

	@Override
	public RSNodeDeque getTilesDeque() {
		return null;
	}

	@Override
	public RSNodeDeque[][][] getGroundItemDeque() {
		return groundItems;
	}

	@Override
	public RSNodeDeque getProjectilesDeque() {
		return null;
	}

	@Override
	public RSNodeDeque getGraphicsObjectDeque() {
		return null;
	}

	@Override
	public String getUsername() {
		return myUsername;
	}

	@Override
	public int getBoostedSkillLevel(Skill skill) {
		int[] boostedLevels = getBoostedSkillLevels();
		int idx = skill.ordinal();
		if (idx >= boostedLevels.length)
		{
			return -1;
		}
		return boostedLevels[idx];
	}

	@Override
	public int getRealSkillLevel(Skill skill) {
		int idx = skill.ordinal();
		if (idx >= maximumLevels.length)
		{
			return -1;
		}
		return maximumLevels[idx];
	}

	@Override
	public int getTotalLevel() {
		int totalLevel = 0;

		int[] realLevels = getRealSkillLevels();
		int lastSkillIdx = Skill.CONSTRUCTION.ordinal();

		for (int i = 0; i < realLevels.length; i++)
		{
			if (i <= lastSkillIdx)
			{
				totalLevel += realLevels[i];
			}
		}

		return totalLevel;
	}

	@Override
	public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender) {
		return null;
	}

	@Override
	public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender,
									  boolean postEvent) {
		return null;
	}
	public int gameState = -1;
	@Override
	public GameState getGameState() {
		return GameState.of(gameState);
	}

	@Override
	public CinematicState getCinematicState() {
		return null;
	}

	@Override
	public int getRSGameState() {
		return gameState;
	}

	@Override
	public void setRSGameState(int state) {
		if (state != gameState) {

			//jagexNetThread.writePacket(true);

			if (gameState == 25) {
				Client.loadingType = 0;
				Client.mapsLoaded = 0;
				Client.totalMaps = 1;
				Client.objectsLoaded = 0;
				Client.totalObjects = 1;
			}

			if (state != 5 && state != 10) { // L: 1270

				if (state == 20) { // L: 1278
					setupLoginScreen();
				} else if (state == 11) { // L: 1282
					setupLoginScreen();
				} else if (state == 50) { // L: 1285
					setupLoginScreen();
				} else if (clearLoginScreen) { // L: 1290
					jagexNetThread.writePacket(true);
					StaticSound.clear();
					StaticSound.midiPcmStream.clearAll(); // L: 1146
					StaticSound.midiPcmStream.setPcmStreamVolume(0);
					clearLoginScreen = false;
				}
			} else {
				setupLoginScreen();
			}

			gameStateChanged(0);
			gameState = state;

		}
	}

	public static boolean clearLoginScreen;

	public static void setupLoginScreen() {
		StaticSound.update();

	}


	public static void gameStateChanged(int idx) {
		GameState gameState = Client.instance.getGameState();
		Client.instance.getLogger().debug("Game state changed: {}", gameState);
		GameStateChanged gameStateChange = new GameStateChanged();
		gameStateChange.setGameState(gameState);
		Client.instance.getCallbacks().post(gameStateChange);

		if (gameState == GameState.LOGGED_IN) {
			if (Client.instance.getLocalPlayer() == null) {
				return;
			}

		} else if (gameState == GameState.LOGIN_SCREEN) {
			//Laod Varbits
		}
	}

	@Override
	public void setCheckClick(boolean checkClick) {
		scene.clicked = checkClick;
	}

	@Override
	public void setMouseCanvasHoverPositionX(int x) {
		MouseHandler.mouseX = x;
	}

	@Override
	public void setMouseCanvasHoverPositionY(int y) {
		MouseHandler.mouseY = y;
	}

	@Override
	public void setGameState(GameState state) {
		gameState = state.getState();
		GameStateChanged event = new GameStateChanged();
		event.setGameState(state);
		if(callbacks != null) {
			callbacks.post(event);
		}


	}

	@Override
	public void setCinematicState(CinematicState gameState) {

	}

	@Override
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	@Override
	public void stopNow() {
	}

	@Override
	public void setUsername(String name) {
		myUsername = name;
	}
	@Override
	public void setOtp(String otp) {

	}

	@Override
	public int getCurrentLoginField() {
		return 0;
	}

	@Override
	public int getLoginIndex() {
		return 0;
	}

	@Override
	public AccountType getAccountType() {
		return AccountType.NORMAL;
	}

	@Override
	public int getFPS() {
		return fps;
	}

	@Override
	public int getCameraX() {
		return this.cameraX;
	}

	@Override
	public int getCameraY() {
		return this.cameraZ;
	}

	@Override
	public int getCameraZ() {
		return this.cameraY;
	}

	@Override
	public int getCameraPitch() {
		return cameraPitch;
	}

	@Override
	public void setCameraPitch(int cameraPitch) {
		this.cameraPitch = cameraPitch;
	}

	@Override
	public int getCameraYaw() {
		return cameraYaw;
	}

	@Override
	public int getWorld() {
		return 1;
	}

	@Override
	public int getCanvasHeight() {
		return canvasHeight;
	}

	@Override
	public int getCanvasWidth() {
		return canvasWidth;
	}

	@Override
	public int getViewportHeight() {
		return viewportHeight;
	}

	@Override
	public int getViewportWidth() {
		return viewportWidth;

	}

	@Override
	public int getViewportXOffset() {
		return viewportOffsetX;
	}

	@Override
	public int getViewportYOffset() {
		return viewportOffsetY;
	}

	@Override
	public net.runelite.api.Point getMouseCanvasPosition() {
		return new net.runelite.api.Point(MouseHandler.mouseX, MouseHandler.mouseY);
	}

	@Override
	public int[][][] getTileHeights() {
		return tileHeights;
	}

	@Override
	public byte[][][] getTileSettings() {
		return tileFlags;
	}

	@Override
	public int getPlane() {
		return plane;
	}

	@Override
	public SceneGraph getScene() {
		return scene;
	}

	@Override
	public RSPlayer getLocalPlayer() {
		return localPlayer;
	}

	@Override
	public int getLocalPlayerIndex() {
		return localPlayerIndex;
	}

	@Override
	public int getNpcIndexesCount() {
		return npcCount;
	}

	@Override
	public int[] getNpcIndices() {
		return npcIndices;
	}

	@Override
	public ItemComposition getItemComposition(int id) {
		return ItemDefinition.lookup(id);
	}

	@Override
	public ItemComposition getItemDefinition(int id) {
		return ItemDefinition.lookup(id);
	}

	@Override
	public RSSpritePixels[] getSprites(IndexDataBase source, int archiveId, int fileId)
	{
		RSAbstractArchive rsSource = (RSAbstractArchive) source;
		byte[] configData = rsSource.getConfigData(archiveId, fileId);
		if (configData == null)
		{
			return null;
		}

		decodeSprite(configData);

		int indexedSpriteCount = getIndexedSpriteCount();
		int maxWidth = getIndexedSpriteWidth();
		int maxHeight = getIndexedSpriteHeight();
		int[] offsetX = getIndexedSpriteOffsetXs();
		int[] offsetY = getIndexedSpriteOffsetYs();
		int[] widths = getIndexedSpriteWidths();
		int[] heights = getIndexedSpriteHeights();
		byte[][] spritePixelsArray = getSpritePixels();
		int[] indexedSpritePalette = getIndexedSpritePalette();

		RSSpritePixels[] array = new RSSpritePixels[indexedSpriteCount];

		for (int i = 0; i < indexedSpriteCount; ++i)
		{
			int width = widths[i];
			int height = heights[i];

			byte[] pixelArray = spritePixelsArray[i];
			int[] pixels = new int[width * height];

			RSSpritePixels spritePixels = createSpritePixels(pixels, width, height);
			spritePixels.setMaxHeight(maxHeight);
			spritePixels.setMaxWidth(maxWidth);
			spritePixels.setOffsetX(offsetX[i]);
			spritePixels.setOffsetY(offsetY[i]);

			for (int j = 0; j < width * height; ++j)
			{
				pixels[j] = indexedSpritePalette[pixelArray[j] & 0xff];
			}

			array[i] = spritePixels;
		}

		setIndexedSpriteOffsetXs(null);
		setIndexedSpriteOffsetYs(null);
		setIndexedSpriteWidths(null);
		setIndexedSpriteHeights(null);
		setIndexedSpritePalette(null);
		setSpritePixels(null);

		return array;
	}


	@Override
	public RSArchive getIndexSprites() {
		return Js5List.sprites;
	}

	@Override
	public RSArchive getIndexScripts() {
		return null;
	}

	@Override
	public RSArchive getIndexConfig() {
		return null;
	}

	@Override
	public RSArchive getMusicTracks() {
		return null;
	}

	@Override
	public int getBaseX() {
		return baseX;
	}

	@Override
	public int getBaseY() {
		return baseY;
	}

	@Override
	public int getMouseCurrentButton() {
		return 0;
	}

	@Override
	public int getSelectedSceneTileX() {
		return scene.clickedTileX;
	}

	@Override
	public void setSelectedSceneTileX(int selectedSceneTileX) {
		scene.clickedTileX = selectedSceneTileX;
	}

	@Override
	public int getSelectedSceneTileY() {
		return scene.clickedTileY;
	}

	@Override
	public void setSelectedSceneTileY(int selectedSceneTileY) {
		scene.clickedTileY = selectedSceneTileY;
	}

	@Override
	public Tile getSelectedSceneTile() {
		int tileX = SceneGraph.hoverX;
		int tileY = SceneGraph.hoverY;

		if (tileX == -1 || tileY == -1)
		{
			return null;
		}

		return getScene().getTiles()[getPlane()][tileX][tileY];

	}

	@Override
	public boolean isDraggingWidget() {
		return false;
	}

	@Override
	public RSWidget getDraggedWidget() {
		return null;
	}

	@Override
	public RSWidget getDraggedOnWidget() {
		return null;
	}

	@Override
	public void setDraggedOnWidget(net.runelite.api.widgets.Widget widget) {
	}

	@Override
	public RSWidget[][] getWidgets() {
		return new RSWidget[0][];
	}

	@Override
	public RSWidget[] getGroup(int groupId) {
		return new RSWidget[0];
	}

	@Override
	public int getTopLevelInterfaceId() {
		return openInterfaceID;
	}

	@Override
	public RSWidget[] getWidgetRoots() {
		return null;
	}

	@Override
	public RSWidget getWidget(WidgetInfo widget) {
		int groupId = widget.getGroupId();
		int childId = widget.getChildId();

		return getWidget(groupId, childId);
	}

	@Override
	public RSWidget getWidget(int groupId, int childId) {
		return null;
	}

	@Override
	public RSWidget getWidget(int packedID) {
		return null;
	}

	@Override
	public int[] getWidgetPositionsX() {
		return null;
	}

	@Override
	public int[] getWidgetPositionsY() {
		return null;
	}

	@Override
	public boolean isMouseCam() {
		return Configuration.MOUSE_CAM;
	}

	@Override
	public int getCamAngleDX() {
		return camAngleDX;
	}

	@Override
	public void setCamAngleDX(int angle) {
		camAngleDX = angle;
	}

	@Override
	public int getCamAngleDY() {
		return camAngleDY;
	}

	@Override
	public void setCamAngleDY(int angle) {
		camAngleDY = angle;
	}

	@Override
	public RSWidget createWidget() {
		return null;
	}

	@Override
	public void revalidateWidget(net.runelite.api.widgets.Widget w) {

	}

	@Override
	public void revalidateWidgetScroll(net.runelite.api.widgets.Widget[] group, net.runelite.api.widgets.Widget w, boolean postEvent) {

	}

	@Override
	public int getEntitiesAtMouseCount() {
		return ViewportMouse.entityCount;
	}

	@Override
	public void setEntitiesAtMouseCount(int i) {
		ViewportMouse.entityCount = i;
	}

	@Override
	public long[] getEntitiesAtMouse() {
		return ViewportMouse.entityTags;
	}

	@Override
	public int getViewportMouseX() {
		return ViewportMouse.ViewportMouse_x;
	}

	@Override
	public int getViewportMouseY() {
		return ViewportMouse.ViewportMouse_y;
	}

	@Override
	public int getEnergy() {
		return Integer.parseInt(RSInterface.interfaceCache[22539].message.replaceAll("%", ""));
	}

	@Override
	public int getWeight() {
		return 0;
	}

	@Override
	public String[] getPlayerOptions() {
		return null;
	}

	@Override
	public boolean[] getPlayerOptionsPriorities() {
		return null;
	}

	@Override
	public int[] getPlayerMenuTypes() {
		return null;
	}

	@Override
	public int getMouseX() {
		return MouseHandler.mouseX;
	}

	@Override
	public int getMouseY() {
		return MouseHandler.mouseY;
	}

	@Override
	public int getMouseX2() {
		return scene.clickScreenX;
	}

	@Override
	public int getMouseY2() {
		return scene.clickScreenY;
	}

	@Override
	public boolean containsBounds(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		return scene.inBounds(var0, var1, var2, var3, var4, var5, var6, var7);
	}

	@Override
	public boolean isCheckClick() {
		return SceneGraph.clicked;
	}

	@Override
	public RSWorld[] getWorldList() {
		return null;
	}

	@Override
	public void addRSChatMessage(int type, String name, String message, String sender) {

	}

	@Override
	public RSObjectComposition getRSObjectComposition(int objectId) {
		return null;
	}

	@Override
	public RSNPCComposition getRSNpcComposition(int npcId) {
		return null;
	}



	@Override
	public net.runelite.rs.api.RSFont getFontBold12() {
		return boldFontOSRS;
	}


	@Override
	public void rasterizerDrawHorizontalLine(int x, int y, int w, int rgb) {
		Rasterizer2D.drawHorizontalLine(x, y, w, rgb);
	}

	@Override
	public void rasterizerDrawHorizontalLineAlpha(int x, int y, int w, int rgb, int a) {
		Rasterizer2D.drawTransparentHorizontalLine(x, y, w, rgb, a);
	}

	@Override
	public void rasterizerDrawVerticalLine(int x, int y, int h, int rgb) {
		Rasterizer2D.drawVerticalLine(x, y, h, rgb);
	}

	@Override
	public void rasterizerDrawVerticalLineAlpha(int x, int y, int h, int rgb, int a) {
		Rasterizer2D.drawTransparentVerticalLine(x, y, h, rgb, a);
	}


	@Override
	public void rasterizerDrawGradient(int x, int y, int w, int h, int rgbTop, int rgbBottom) {
		Rasterizer2D.drawTransparentGradientBox(x, y, w, h, rgbTop, rgbBottom, 255);
	}

	@Override
	public void rasterizerDrawGradientAlpha(int x, int y, int w, int h, int rgbTop, int rgbBottom, int alphaTop, int alphaBottom) {
		Rasterizer2D.rasterizerDrawGradientAlpha(x, y, w, h, rgbTop, rgbBottom, alphaTop, alphaBottom);
	}
	@Override
	public void rasterizerFillRectangleAlpha(int x, int y, int w, int h, int rgb, int a) {
		Rasterizer2D.drawTransparentBox(x,y,w,h,rgb,a);
	}

	@Override
	public void rasterizerDrawRectangle(int x, int y, int w, int h, int rgb) {
		Rasterizer2D.drawBoxOutline(x, y, w, h, rgb);
	}

	@Override
	public void rasterizerDrawRectangleAlpha(int x, int y, int w, int h, int rgb, int a) {
		Rasterizer2D.drawTransparentBoxOutline(x, y, w, h, rgb, a);
	}


	@Override
	public void rasterizerDrawCircle(int x, int y, int r, int rgb) {

	}

	@Override
	public void rasterizerDrawCircleAlpha(int x, int y, int r, int rgb, int a) {

	}

	@Override
	public RSEvictingDualNodeHashTable getHealthBarCache() {
		return HealthBarDefinition.cached;
	}

	@Override
	public RSEvictingDualNodeHashTable getHealthBarSpriteCache() {
		return HealthBarDefinition.cachedSprites;
	}

	@Override
	public int getMapAngle() {
		return camAngleY;
	}

	@Override
	public void setCameraYawTarget(int cameraYawTarget) {
		camAngleY = cameraYawTarget;
	}

	@Setter
	private boolean resized = false;

	@Override
	public boolean isResized() {
		return resized;
	}

	@Override
	public int getRevision() {
		return 1;
	}

	@Override
	public int[] getMapRegions() {
		return regions;
	}

	@Override
	public int[][][] getInstanceTemplateChunks() {
		return constructRegionData;
	}

	@Override
	public int[][] getXteaKeys() {
		return null;
	}

	@Override
	public int getCycleCntr() {
		return 0;
	}

	@Override
	public void setChatCycle(int value) {

	}

	@Override
	public int[] getVarps() {
		return settings;
	}

	@Override
	public RSVarcs getVarcs() {
		return null;
	}

	@Override
	public Map<Integer, Object> getVarcMap() {
		return null;
	}

	@Override
	public int getVar(VarPlayer varPlayer) {
		return 0;
	}

	@Override
	public int getVar(Varbits varbit) {
		return 0;
	}

	@Override
	public int getVar(VarClientInt varClientInt) {
		return 0;
	}

	@Override
	public String getVar(VarClientStr varClientStr) {
		return null;
	}

	@Override
	public int getVarbitValue(int varbitId) {
		VariableBits varbit = VariableBits.lookup(varbitId);
		if (varbit == null || settings == null) return 0;
		int mask = (1 << (varbit.endBit - varbit.startBit + 1)) - 1;
		return (settings[varbit.baseVar] >> varbit.startBit) & mask;
	}

	@Override
	public int getVarcIntValue(int varcIntId) {
		return 0;
	}

	@Override
	public String getVarcStrValue(int varcStrId) {
		return null;
	}

	@Override
	public void setVar(VarClientStr varClientStr, String value) {
	}

	@Override
	public void setVar(VarClientInt varClientStr, int value) {
	}

	@Override
	public void setVarbit(Varbits varbit, int value) {
	}

	@Override
	public VarbitComposition getVarbit(int id) {
		return null;
	}

	@Override
	public int getVarbitValue(int[] varps, int varbitId) {
		VariableBits varbit = VariableBits.lookup(varbitId);
		if (varbit == null || varps == null || varbit.baseVar >= varps.length) return 0;
		int mask = (1 << (varbit.endBit - varbit.startBit + 1)) - 1;
		return (varps[varbit.baseVar] >> varbit.startBit) & mask;
	}

	@Override
	public int getVarpValue(int[] varps, int varpId) {
		if (varps == null || varpId < 0 || varpId >= varps.length) return 0;
		return varps[varpId];
	}

	@Override
	public int getVarpValue(int i) {
		if (settings == null || i < 0 || i >= settings.length) return 0;
		return settings[i];
	}

	@Override
	public void setVarbitValue(int[] varps, int varbitId, int value) {
		VariableBits varbit = VariableBits.lookup(varbitId);
		if (varbit == null || varps == null || varbit.baseVar >= varps.length) return;
		int mask = (1 << (varbit.endBit - varbit.startBit + 1)) - 1;
		varps[varbit.baseVar] = (varps[varbit.baseVar] & ~(mask << varbit.startBit)) | ((value & mask) << varbit.startBit);
	}

	@Override
	public void queueChangedVarp(int varp) {
	}

	@Override
	public RSNodeHashTable getWidgetFlags() {
		return null;
	}

	@Override
	public RSNodeHashTable getComponentTable() {
		return null;
	}

	@Override
	public RSGrandExchangeOffer[] getGrandExchangeOffers() {
		return null;
	}

	@Override
	public boolean isPrayerActive(Prayer prayer) {
		return false;
	}

	@Override
	public int getSkillExperience(Skill skill) {

		int[] experiences = getSkillExperiences();

		if (skill == Skill.OVERALL)
		{
			logger.debug("getSkillExperience called for {}!", skill);
			return (int) getOverallExperience();
		}

		int idx = skill.ordinal();

		// I'm not certain exactly how needed this is, but if the Skill enum is updated in the future
		// to hold something else that's not reported it'll save us from an ArrayIndexOutOfBoundsException.
		if (idx >= experiences.length)
		{
			return -1;
		}

		return experiences[idx];
	}

	@Override
	public long getOverallExperience() {
		return IntStream.of(currentExp).sum();
	}

	@Override
	public void refreshChat() {
	}

	@Override
	public Map<Integer, ChatLineBuffer> getChatLineMap() {
		return null;
	}

	@Override
	public RSIterableNodeHashTable getMessages() {
		return null;
	}

	@Override
	public ObjectComposition getObjectDefinition(int objectId) {
		return ObjectDefinition.lookup(objectId);
	}

	@Override
	public NPCComposition getNpcDefinition(int npcId) {
		return NpcDefinition.get(npcId);
	}

	@Override
	public StructComposition getStructComposition(int structID) {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getStructCompositionCache() {
		return null;
	}

	@Override
	public RSWorldMapElement[] getMapElementConfigs() {
		return null;
	}

	@Override
	public RSIndexedSprite[] getMapScene() {
		return null;
	}

	public Sprite[] minimapDot = new Sprite[7];


	@Override
	public RSSpritePixels[] getMapDots() {
		return minimapDot;
	}

	@Override
	public int getGameCycle() {
		return loopCycle;
	}

	@Override
	public RSSpritePixels[] getMapIcons() {
		return null;
	}

	@Override
	public RSIndexedSprite[] getModIcons() {
		return null;
	}

	@Override
	public void setRSModIcons(RSIndexedSprite[] modIcons) {

	}

	@Override
	public void setModIcons(IndexedSprite[] modIcons) {

	}

	@Override
	public RSIndexedSprite createIndexedSprite() {
		return null;
	}

	@Override
	public RSSpritePixels createSpritePixels(int[] pixels, int width, int height) {
		return new Sprite(pixels,width,height);
	}

	@Override
	public int getDestinationX() {
		return destX;
	}

	@Override
	public int getDestinationY() {
		return destY;
	}

	@Override
	public RSSoundEffect[] getAudioEffects() {
		return new RSSoundEffect[0];
	}

	@Override
	public int[] getQueuedSoundEffectIDs() {
		return new int[0];
	}

	@Override
	public int[] getSoundLocations() {
		return new int[0];
	}

	@Override
	public int[] getQueuedSoundEffectLoops() {
		return new int[0];
	}

	@Override
	public int[] getQueuedSoundEffectDelays() {
		return new int[0];
	}

	@Override
	public int getQueuedSoundEffectCount() {
		return 0;
	}

	@Override
	public void setQueuedSoundEffectCount(int queuedSoundEffectCount) {

	}

	@Override
	public void queueSoundEffect(int id, int numLoops, int delay) {

	}

	@Override
	public LocalPoint getLocalDestinationLocation()
	{
		int sceneX = getDestinationX();
		int sceneY = getDestinationY();
		if (sceneX != 0 && sceneY != 0)
		{
			return LocalPoint.fromScene(sceneX, sceneY);
		}
		return null;
	}

	@Override
	public List<net.runelite.api.Projectile> getProjectiles() {
		List<net.runelite.api.Projectile> projectileList = new ArrayList<>();
		for (net.runelite.api.Projectile projectile = (net.runelite.api.Projectile) projectiles
				.reverseGetFirst(); projectile != null; projectile = (net.runelite.api.Projectile) projectiles.reverseGetNext()) {
			projectileList.add(projectile);
		}
		return projectileList;
	}

	@Override
	public List<GraphicsObject> getGraphicsObjects() {
		List<GraphicsObject> list = new ArrayList<>();
		for (GraphicsObject projectile = (GraphicsObject) incompleteAnimables
				.reverseGetFirst(); projectile != null; projectile = (GraphicsObject) incompleteAnimables.reverseGetNext()) {
			list.add(projectile);
		}
		return list;
	}

	@Override
	public RuneLiteObject createRuneLiteObject() {
		return new com.client.scene.object.RuneLiteObject();
	}

	RSEvictingDualNodeHashTable newEvictingDualNodeHashTable(int var1) {
		return new EvictingDualNodeHashTable(var1);
	}

	public RSEvictingDualNodeHashTable tmpModelDataCache = newEvictingDualNodeHashTable(16);

	@Nullable
	@Override
	public RSModelData loadModelData(int var0)
	{
		RSModelData modelData = (RSModelData) this.tmpModelDataCache.get(var0);

		if (modelData == null)
		{
			modelData = getModelData(Js5List.models, var0, 0);
			if (modelData == null)
			{
				return null;
			}

			this.tmpModelDataCache.put((RSDualNode) modelData, (long) var0);
		}

		return modelData.newModelData(modelData, true, true, true, true);
	}


	com.client.entity.model.ModelData newModelData(RSModelData[] var1, int var2) {
		return new com.client.entity.model.ModelData((com.client.entity.model.ModelData[]) var1,var2);
	}

	RSModelData[] getModelDataArray() {
		return ObjectDefinition.modelDataArray;
	}

	@Override
	public RSModelData mergeModels(ModelData[] var0, int var1)
	{
		return newModelData(Arrays.copyOf(var0, var1, getModelDataArray().getClass()), var1);
	}




	@Override
	public RSModelData mergeModels(ModelData... var0)
	{
		return mergeModels(var0, var0.length);
	}

	@Override
	public RSModel loadModel(int id)
	{
		return loadModel(id, null, null);
	}

	public RSModel loadModel(int id, short[] colorToFind, short[] colorToReplace)
	{
		RSModelData modeldata = getModelData(getObjectDefinition_modelsArchive(), id, 0);

		if (colorToFind != null)
		{
			for (int i = 0; i < colorToFind.length; ++i)
			{
				modeldata.rs$recolor(colorToFind[i], colorToReplace[i]);
			}
		}

		return modeldata.toModel(modeldata.getAmbient() + 64, modeldata.getContrast() + 850, -30, -50, -30);
	}

	@Override
	public Animation loadAnimation(int id)
	{
		return getSequenceDefinition(id);
	}

	@Override
	public int getMusicVolume() {
		return 0;
	}

	@Override
	public void setMusicVolume(int volume) {
	}

	@Override
	public void playSoundEffect(int id) {

	}

	@Override
	public void playSoundEffect(int id, int x, int y, int range) {
	}

	@Override
	public void playSoundEffect(int id, int x, int y, int range, int delay) {
	}

	@Override
	public void playSoundEffect(int id, int volume) {

	}

	@Override
	public RSAbstractRasterProvider getBufferProvider() {
		return rasterProvider;
	}

	@Override
	public int getMouseIdleTicks() {
		return MouseHandler.idleCycles;
	}

	@Override
	public long getMouseLastPressedMillis() {
		return MouseHandler.lastPressed;
	}

	public long getClientMouseLastPressedMillis() {
		return MouseHandler.lastPressed;
	}

	public void setClientMouseLastPressedMillis(long mills) {
		MouseHandler.lastPressed = mills;
	}

	@Override
	public int getKeyboardIdleTicks() {
		return KeyHandler.idleCycles;
	}

	@Override
	public void changeMemoryMode(boolean lowMemory) {
		setLowMemory(lowMemory);
		setSceneLowMemory(lowMemory);
		setAudioHighMemory(true);
		setObjectDefinitionLowDetail(lowMemory);
		if (getGameState() == GameState.LOGGED_IN)
		{
			setGameState(1);
		}
	}

	public HashMap<Integer, ItemContainer> containers = new HashMap<Integer, ItemContainer>();

	@Override
	public ItemContainer getItemContainer(InventoryID inventory) {
		return containers.get(inventory.getId());
	}

	@Override
	public ItemContainer getItemContainer(int id) {
		return containers.get(id);
	}

	@Override
	public RSNodeHashTable getItemContainers() {
		return null;
	}

	@Override
	public RSItemComposition getRSItemDefinition(int itemId) {
		return ItemDefinition.lookup(itemId);
	}

	@Override
	public SpritePixels createItemSprite(int itemId, int quantity, int border, int shadowColor, int stackable, boolean noted, int scale) {
		assert isClientThread() : "createItemSprite must be called on client thread";

		int zoom = get3dZoom();
		set3dZoom(scale);
		try {
			return createItemSprite(itemId, quantity, border, shadowColor, stackable, noted);
		} finally {
			set3dZoom(zoom);
		}
	}

	public RSSpritePixels createItemSprite(int itemId, int quantity, int border, int shadowColor, int stackable, boolean noted) {
		assert isClientThread() : "createItemSprite must be called on client thread";
		return createRSItemSprite(itemId, quantity, border, shadowColor, stackable, noted);
	}

	@Override
	public RSSpritePixels createRSItemSprite(int itemId, int quantity, int thickness, int borderColor, int stackable, boolean noted) {
		return ItemDefinition.getSprite(itemId, quantity, thickness, borderColor, stackable, noted);
	}

	@Override
	public void decodeSprite(byte[] data) {
		SpriteData.decode(data);
	}

	@Override
	public int getIndexedSpriteCount() {
		return SpriteData.spriteCount;
	}

	@Override
	public int getIndexedSpriteWidth() {
		return SpriteData.spriteWidth;
	}

	@Override
	public int getIndexedSpriteHeight() {
		return SpriteData.spriteHeight;
	}

	@Override
	public int[] getIndexedSpriteOffsetXs() {
		return SpriteData.xOffsets;
	}

	@Override
	public void setIndexedSpriteOffsetXs(int[] indexedSpriteOffsetXs) {
		SpriteData.xOffsets = indexedSpriteOffsetXs;
	}

	@Override
	public int[] getIndexedSpriteOffsetYs() {
		return SpriteData.yOffsets;
	}

	@Override
	public void setIndexedSpriteOffsetYs(int[] indexedSpriteOffsetYs) {
		SpriteData.yOffsets = indexedSpriteOffsetYs;
	}

	@Override
	public int[] getIndexedSpriteWidths() {
		return SpriteData.spriteWidths;
	}

	@Override
	public void setIndexedSpriteWidths(int[] indexedSpriteWidths) {
		SpriteData.spriteWidths = indexedSpriteWidths;
	}

	@Override
	public int[] getIndexedSpriteHeights() {
		return SpriteData.spriteHeights;
	}

	@Override
	public void setIndexedSpriteHeights(int[] indexedSpriteHeights) {
		SpriteData.spriteHeights = indexedSpriteHeights;
	}

	@Override
	public byte[][] getSpritePixels() {
		return SpriteData.pixels;
	}

	@Override
	public void setSpritePixels(byte[][] spritePixels) {
		SpriteData.pixels = spritePixels;
	}

	@Override
	public int[] getIndexedSpritePalette() {
		return SpriteData.spritePalette;
	}

	@Override
	public void setIndexedSpritePalette(int[] indexedSpritePalette) {
		SpriteData.spritePalette = indexedSpritePalette;
	}

	@Override
	public int getIntStackSize() {
		return intStackSize;
	}

	@Override
	public void setIntStackSize(int stackSize) {
		intStackSize = stackSize;
	}

	@Override
	public int[] getIntStack() {
		return intStack;
	}

	@Override
	public int getStringStackSize() {
		return stringStackSize;
	}

	@Override
	public void setStringStackSize(int stackSize) {
		stringStackSize = stackSize;
	}

	@Override
	public String[] getStringStack() {
		return stringStack;
	}

	@Override
	public RSFriendSystem getFriendManager() {
		return null;
	}

	@Override
	public RSWidget getScriptActiveWidget() {
		return null;
	}

	@Override
	public RSWidget getScriptDotWidget() {
		return null;
	}

	@Override
	public RSScriptEvent createRSScriptEvent(Object... args) {
		return null;
	}

	@Override
	public void runScriptEvent(RSScriptEvent event) {

	}

	@Override
	public RSEvictingDualNodeHashTable getScriptCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getRSStructCompositionCache() {
		return null;
	}

	@Override
	public RSStructComposition getRSStructComposition(int id) {
		return null;
	}

	@Override
	public RSParamComposition getRSParamComposition(int id) {
		return null;
	}

	@Override
	public void setMouseLastPressedMillis(long time) {

	}

	@Override
	public int getRootWidgetCount() {
		return 0;
	}

	@Override
	public int getWidgetClickX() {
		return 0;
	}

	@Override
	public int getWidgetClickY() {
		return 0;
	}

	@Override
	public int getStaffModLevel() {
		return 0;
	}

	@Override
	public int getTradeChatMode() {
		return 0;
	}

	@Override
	public int getPublicChatMode() {
		return 0;
	}

	@Override
	public int getClientType() {
		return 0;
	}

	@Override
	public boolean isOnMobile() {
		return false;
	}

	@Override
	public boolean hadFocus() {
		return false;
	}

	@Override
	public int getMouseCrossColor() {
		return 0;
	}

	@Override
	public void setMouseCrossColor(int color) {

	}


	@Override
	public boolean getShowMouseOverText() {
		return false;
	}

	@Override
	public void setShowMouseOverText(boolean showMouseOverText) {

	}

	@Override
	public int[] getDefaultRotations() {
		return new int[0];
	}

	@Override
	public boolean getShowLoadingMessages() {
		return false;
	}

	@Override
	public void setShowLoadingMessages(boolean showLoadingMessages) {

	}

	@Override
	public void setStopTimeMs(long time) {

	}

	@Override
	public void clearLoginScreen(boolean shouldClear) {

	}

	@Override
	public void setLeftTitleSprite(SpritePixels background) {

	}

	@Override
	public void setRightTitleSprite(SpritePixels background) {

	}

	@Override
	public RSBuffer newBuffer(byte[] bytes) {
		return null;
	}

	@Override
	public RSVarbitComposition newVarbitDefinition() {
		return null;
	}

	@Override
	public boolean[] getPressedKeys() {
		return keyHandler.pressedKeys;
	}


	public boolean lowMemoryMusic = false;
	@Override
	public void setLowMemory(boolean lowMemory) {
		this.lowMem = lowMemory;
	}

	@Override
	public void setSceneLowMemory(boolean lowMemory) {
		MapRegion.low_detail = lowMemory;
		SceneGraph.low_detail = lowMemory;
		((TextureProvider) Rasterizer3D.clips.Rasterizer3D_textureLoader).setTextureSize(lowMem ? 64 : 128);

	}

	@Override
	public void setAudioHighMemory(boolean highMemory) {
		lowMemoryMusic = highMemory;
	}

	@Override
	public void setObjectDefinitionLowDetail(boolean lowDetail) {
		ObjectDefinition.lowMem = lowDetail;
	}


	@Override
	public boolean isFriended(String name, boolean mustBeLoggedIn) {
		return false;
	}

	@Override
	public RSFriendsChat getFriendsChatManager() {
		return null;
	}

	@Override
	public RSLoginType getLoginType() {
		return null;
	}

	@Override
	public RSUsername createName(String name, RSLoginType type) {
		return null;
	}

	@Override
	public int rs$getVarbit(int varbitId) {
		return 0;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarbitCache() {
		return null;
	}

	@Override
	public FriendContainer getFriendContainer() {
		return null;
	}

	@Override
	public NameableContainer<Ignore> getIgnoreContainer() {
		return null;
	}

	@Override
	public RSClientPreferences getPreferences() {
		return null;
	}

	@Override
	public int getCameraPitchTarget() {
		return camAngleX;
	}

	@Override
	public void setCameraPitchTarget(int pitch) {
		camAngleX = pitch;
	}

	@Override
	public void setPitchSin(int v) {
		scene.pitchSineY = v;
	}

	@Override
	public void setPitchCos(int v) {
		scene.pitchCosineY = v;
	}

	@Override
	public void setYawSin(int v) {
		scene.yawSineX = v;
	}

	@Override
	public void setYawCos(int v) {
		scene.yawCosineX = v;
	}

	static int lastPitch = 128;
	static int lastPitchTarget = 128;

	@Override
	public void setCameraPitchRelaxerEnabled(boolean enabled) {

		if (pitchRelaxEnabled == enabled)
		{
			return;
		}
		pitchRelaxEnabled = enabled;
		if (!enabled)
		{
			int pitch = getCameraPitchTarget();
			if (pitch > STANDARD_PITCH_MAX)
			{
				setCameraPitchTarget(STANDARD_PITCH_MAX);
			}
		}

	}

	@Override
	public void setInvertYaw(boolean state) {
		invertYaw = state;
	}

	@Override
	public void setInvertPitch(boolean state) {
		invertPitch = state;
	}

	@Override
	public RSWorldMap getRenderOverview() {
		return null;
	}

	private static boolean stretchedEnabled;

	private static boolean stretchedFast;

	private static boolean stretchedIntegerScaling;

	private static boolean stretchedKeepAspectRatio;

	private static double scalingFactor;

	private static Dimension cachedStretchedDimensions;

	private static Dimension cachedRealDimensions;

	@Override
	public boolean isStretchedEnabled()
	{
		return stretchedEnabled;
	}

	@Override
	public void setStretchedEnabled(boolean state)
	{
		stretchedEnabled = state;
	}

	@Override
	public boolean isStretchedFast()
	{
		return stretchedFast;
	}

	@Override
	public void setStretchedFast(boolean state)
	{
		stretchedFast = state;
	}

	@Override
	public void setStretchedIntegerScaling(boolean state)
	{
		stretchedIntegerScaling = state;
	}

	@Override
	public void setStretchedKeepAspectRatio(boolean state)
	{
		stretchedKeepAspectRatio = state;
	}

	@Override
	public void setScalingFactor(int factor)
	{
		scalingFactor = 1 + (factor / 100D);
	}

	@Override
	public double getScalingFactor()
	{
		return scalingFactor;
	}

	@Override
	public Dimension getRealDimensions()
	{
		if (!isStretchedEnabled())
		{
			return getCanvas().getSize();
		}

		if (cachedRealDimensions == null)
		{
			if (isResized())
			{
				Container canvasParent = getCanvas().getParent();

				int parentWidth = canvasParent.getWidth();
				int parentHeight = canvasParent.getHeight();

				int newWidth = (int) (parentWidth / scalingFactor);
				int newHeight = (int) (parentHeight / scalingFactor);

				if (newWidth < Constants.GAME_FIXED_WIDTH || newHeight < Constants.GAME_FIXED_HEIGHT)
				{
					double scalingFactorW = (double)parentWidth / Constants.GAME_FIXED_WIDTH;
					double scalingFactorH = (double)parentHeight / Constants.GAME_FIXED_HEIGHT;
					double scalingFactor = Math.min(scalingFactorW, scalingFactorH);

					newWidth = (int) (parentWidth / scalingFactor);
					newHeight = (int) (parentHeight / scalingFactor);
				}

				cachedRealDimensions = new Dimension(newWidth, newHeight);
			}
			else
			{
				cachedRealDimensions = Constants.GAME_FIXED_SIZE;
			}
		}

		return cachedRealDimensions;
	}

	@Override
	public Dimension getStretchedDimensions()
	{
		if (cachedStretchedDimensions == null)
		{
			Container canvasParent = getCanvas().getParent();

			int parentWidth = canvasParent.getWidth();
			int parentHeight = canvasParent.getHeight();

			Dimension realDimensions = getRealDimensions();

			if (stretchedKeepAspectRatio)
			{
				double aspectRatio = realDimensions.getWidth() / realDimensions.getHeight();

				int tempNewWidth = (int) (parentHeight * aspectRatio);

				if (tempNewWidth > parentWidth)
				{
					parentHeight = (int) (parentWidth / aspectRatio);
				}
				else
				{
					parentWidth = tempNewWidth;
				}
			}

			if (stretchedIntegerScaling)
			{
				if (parentWidth > realDimensions.width)
				{
					parentWidth = parentWidth - (parentWidth % realDimensions.width);
				}
				if (parentHeight > realDimensions.height)
				{
					parentHeight = parentHeight - (parentHeight % realDimensions.height);
				}
			}

			cachedStretchedDimensions = new Dimension(parentWidth, parentHeight);
		}

		return cachedStretchedDimensions;
	}

	@Override
	public void invalidateStretching(boolean resize)
	{
		cachedRealDimensions = null;
		cachedStretchedDimensions = null;

		if (resize && isResized())
		{
			/*
				Tells the game to run resizeCanvas the next frame.

				This is useful when resizeCanvas wouldn't usually run,
				for example when we've only changed the scaling factor
				and we still want the game's canvas to resize
				with regards to the new maximum bounds.
			 */
			setResizeCanvasNextFrame(true);
		}
	}

	@Override
	public void changeWorld(World world) {

	}

	@Override
	public RSWorld createWorld() {
		return null;
	}

	@Override
	public void setAnimOffsetX(int animOffsetX) {

	}

	@Override
	public void setAnimOffsetY(int animOffsetY) {

	}

	@Override
	public void setAnimOffsetZ(int animOffsetZ) {

	}

	@Override
	public RSSpritePixels drawInstanceMap(int z) {
		return null;
	}

	@Override
	public void setMinimapReceivesClicks(boolean minimapReceivesClicks) {

	}

	@Override
	public void runScript(Object... args) {

	}

	@Override
	public ScriptEvent createScriptEvent(Object... args) {
		return null;
	}

	@Override
	public boolean hasHintArrow() {
		return false;
	}

	@Override
	public HintArrowType getHintArrowType() {
		return null;
	}

	@Override
	public void clearHintArrow() {

	}

	@Override
	public void setHintArrow(WorldPoint point) {

	}

	@Override
	public void setHintArrow(net.runelite.api.Player player) {

	}

	@Override
	public void setHintArrow(NPC npc) {

	}

	@Override
	public WorldPoint getHintArrowPoint() {
		return null;
	}

	@Override
	public net.runelite.api.Player getHintArrowPlayer() {
		return null;
	}

	@Override
	public NPC getHintArrowNpc() {
		return null;
	}

	@Override
	public boolean isInterpolatePlayerAnimations() {
		return false;
	}

	@Override
	public void setInterpolatePlayerAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateNpcAnimations() {
		return false;
	}

	@Override
	public void setInterpolateNpcAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateObjectAnimations() {
		return false;
	}

	@Override
	public void setInterpolateObjectAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInterpolateWidgetAnimations() {
		return false;
	}

	@Override
	public void setInterpolateWidgetAnimations(boolean interpolate) {

	}

	@Override
	public boolean isInInstancedRegion() {
		return false; //TODO:
	}

	@Override
	public int getItemPressedDuration() {
		return 0;
	}

	@Override
	public void setItemPressedDuration(int duration) {

	}

	@Override
	public int getFlags() {
		return 0;
	}


	@Override
	public void setIsHidingEntities(boolean state)
	{

	}

	@Override
	public void setOthersHidden(boolean state)
	{

	}

	@Override
	public void setOthersHidden2D(boolean state)
	{

	}

	@Override
	public void setFriendsHidden(boolean state)
	{

	}

	@Override
	public void setFriendsChatMembersHidden(boolean state)
	{

	}

	@Override
	public void setIgnoresHidden(boolean state)
	{

	}

	@Override
	public void setLocalPlayerHidden(boolean state)
	{

	}

	@Override
	public void setLocalPlayerHidden2D(boolean state)
	{

	}

	@Override
	public void setNPCsHidden(boolean state)
	{

	}

	@Override
	public void setNPCsHidden2D(boolean state)
	{

	}

	@Override
	public void setHideSpecificPlayers(List<String> players)
	{

	}


	@Override
	public void setHiddenNpcIndices(List<Integer> npcIndices)
	{

	}

	@Override
	public List<Integer> getHiddenNpcIndices()
	{
		return null;
	}

	@Override
	public void setPetsHidden(boolean state)
	{

	}

	@Override
	public void setAttackersHidden(boolean state)
	{

	}

	@Override
	public void setProjectilesHidden(boolean state)
	{

	}

	@Override
	public void setDeadNPCsHidden(boolean state)
	{

	}

	@Override
	public void addHiddenNpcName(String npc)
	{

	}

	@Override
	public void removeHiddenNpcName(String npc)
	{

	}


	@Override
	public void setBlacklistDeadNpcs(Set<Integer> blacklist) {

	}

	public boolean addEntityMarker(int x, int y, RSRenderable entity)
	{

		return true;
	}

	public boolean shouldDraw(Object entity, boolean drawingUI)
	{


		return true;
	}

	private static boolean invertPitch;
	private static boolean invertYaw;

	@Override
	public RSCollisionMap[] getCollisionMaps() {
		return collisionMaps;
	}

	@Override
	public int getPlayerIndexesCount() {
		return 0;
	}

	@Override
	public int[] getPlayerIndices() {
		return new int[0];
	}

	@Override
	public int[] getBoostedSkillLevels() {
		return currentLevels;
	}

	@Override
	public int[] getRealSkillLevels() {
		return maximumLevels;
	}

	@Override
	public int[] getSkillExperiences() {
		return currentExp;
	}

	@Override
	public int[] getChangedSkills() {
		return new int[0];
	}

	int changedSkillsCount;

	@Override
	public int getChangedSkillsCount() {
		return changedSkillsCount;
	}

	@Override
	public void setChangedSkillsCount(int i) {
		changedSkillsCount = i;
	}

	@Override
	public void queueChangedSkill(Skill skill) {
		int[] skills = getChangedSkills();
		int count = getChangedSkillsCount();
		skills[++count - 1 & 31] = skill.ordinal();
		setChangedSkillsCount(count);
	}



	public static final Map<Integer, SpritePixels> spriteOverrides = new HashMap<Integer, SpritePixels>();


	public static final Map<Integer, SpritePixels> widgetSpriteOverrides = new HashMap<Integer, SpritePixels>();

	@Override
	public Map<Integer, SpritePixels> getSpriteOverrides() {
		return spriteOverrides;
	}

	@Override
	public Map<Integer, SpritePixels> getWidgetSpriteOverrides() {
		return spriteOverrides;
	}


	@Override
	public void setCompass(SpritePixels SpritePixels) {

	}

	@Override
	public RSEvictingDualNodeHashTable getWidgetSpriteCache() {
		return null;
	}

	int tickCount;

	@Override
	public int getTickCount() {
		return tickCount;
	}

	@Override
	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}


	@Override
	public void setInventoryDragDelay(int delay) {

	}


	@Override
	public boolean isHdMinimapEnabled() {
		return scene.hdMinimapEnabled;
	}

	@Override
	public void setHdMinimapEnabled(boolean enabled) {
		scene.hdMinimapEnabled = enabled;
	}

	@Override
	public EnumSet<WorldType> getWorldType() {
		return EnumSet.of(WorldType.MEMBERS);
	}

	@Override
	public int getOculusOrbState() {
		return oculusOrbState;
	}

	@Override
	public void setOculusOrbState(int state) {
		oculusOrbState = state;
	}

	@Override
	public void setOculusOrbNormalSpeed(int speed) {
		oculusOrbNormalSpeed = speed;
	}

	@Override
	public int getOculusOrbFocalPointX() {
		return oculusOrbFocalPointX;
	}

	@Override
	public int getOculusOrbFocalPointY() {
		return oculusOrbFocalPointY;
	}

	@Override
	public void setOculusOrbFocalPointX(int xPos) {
		oculusOrbFocalPointX = xPos;
	}

	@Override
	public void setOculusOrbFocalPointY(int yPos) {
		oculusOrbFocalPointY = yPos;
	}

	private static RSTileItem lastItemDespawn;

	@Override
	public RSTileItem getLastItemDespawn() {
		return lastItemDespawn;
	}


	@Override
	public void setLastItemDespawn(RSTileItem lastItemDespawn)
	{

		lastItemDespawn = lastItemDespawn;
	}

	@Override
	public void openWorldHopper() {

	}

	@Override
	public void hopToWorld(World world) {

	}

	@Override
	public void setSkyboxColor(int skyboxColor) {
		scene.skyboxColor = skyboxColor;
	}

	@Override
	public int getSkyboxColor() {
		return scene.skyboxColor;
	}

	@Override
	public boolean isGpu() {
		return (gpuFlags & 1) == 1;
	}

	@Override
	public void setGpuFlags(int gpuFlags) {
		this.gpuFlags = gpuFlags;
	}

	@Override
	public void setExpandedMapLoading(int chunks) {

	}

	@Override
	public int getExpandedMapLoading() {
		return 0;
	}

	@Override
	public int getGpuFlags() {
		return gpuFlags;
	}

	@Override
	public int get3dZoom() {
		return Rasterizer3D.clips.viewportZoom;
	}

	@Override
	public int set3dZoom(int zoom) {
		Rasterizer3D.clips.viewportZoom = zoom;
		return zoom;
	}

	@Override
	public int getCenterX() {
		return getViewportWidth() / 2;
	}

	@Override
	public int getCenterY() {
		return getViewportHeight() / 2;
	}

	@Override
	public int getCameraX2() {
		return SceneGraph.xCameraPos;
	}

	@Override
	public int getCameraY2() {
		return SceneGraph.zCameraPos;
	}

	@Override
	public int getCameraZ2() {
		return SceneGraph.yCameraPos;
	}

	@Override
	public RSTextureProvider getTextureProvider() {
		return ((TextureProvider) Rasterizer3D.clips.Rasterizer3D_textureLoader);
	}

	@Override
	public int[][] getOccupiedTilesTick() {
		return new int[0][];
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionModelsCache() {
		return null;
	}

	@Override
	public int getCycle() {
		return scene.cycle;
	}

	@Override
	public void setCycle(int cycle) {
		scene.cycle = cycle;
	}

	@Override
	public boolean[][][][] getVisibilityMaps() {
		return scene.visibilityMap;
	}

	@Override
	public RSEvictingDualNodeHashTable getCachedModels2() {
		return null;
	}

	@Override
	public void setRenderArea(boolean[][] renderArea) {
		scene.renderArea = renderArea;
	}

	@Override
	public void setCameraX2(int cameraX2) {
		scene.xCameraPos = cameraX2;
	}

	@Override
	public void setCameraY2(int cameraY2) {
		scene.zCameraPos = cameraY2;
	}

	@Override
	public void setCameraZ2(int cameraZ2) {
		scene.yCameraPos = cameraZ2;
	}

	@Override
	public void setScreenCenterX(int screenCenterX) {
		scene.screenCenterX = screenCenterX;
	}

	@Override
	public void setScreenCenterZ(int screenCenterZ) {
		scene.screenCenterZ = screenCenterZ;
	}

	@Override
	public void setScenePlane(int scenePlane) {
		scene.currentRenderPlane = scenePlane;
	}

	@Override
	public void setMinTileX(int i) {
		scene.minTileX = i;
	}

	@Override
	public void setMinTileZ(int i) {
		scene.minTileZ = i;
	}

	@Override
	public void setMaxTileX(int i) {
		scene.maxTileX = i;
	}

	@Override
	public void setMaxTileZ(int i) {
		scene.maxTileZ = i;
	}

	@Override
	public int getTileUpdateCount() {
		return scene.tileUpdateCount;
	}

	@Override
	public void setTileUpdateCount(int tileUpdateCount) {
		scene.tileUpdateCount = tileUpdateCount;
	}

	@Override
	public boolean getViewportContainsMouse() {
		return false;
	}

	@Override
	public int getRasterizer3D_clipMidX2() {
		return Clips.getClipMidX2();
	}

	@Override
	public int getRasterizer3D_clipNegativeMidX() {
		return Rasterizer3D.clips.clipNegativeMidX;
	}

	@Override
	public int getRasterizer3D_clipNegativeMidY() {
		return Rasterizer3D.clips.clipNegativeMidY;
	}

	@Override
	public int getRasterizer3D_clipMidY2() {
		return Clips.getClipMidY2();
	}
	@Override
	public void checkClickbox(net.runelite.api.Model model, int orientation, int pitchSin, int pitchCos, int yawSin,
							  int yawCos, int x, int y, int z, long hash) {

	}

	@Override
	public RSWidget getIf1DraggedWidget() {
		return null;
	}

	@Override
	public int getIf1DraggedItemIndex() {
		return 0;
	}

	@Override
	public void setSpellSelected(boolean selected) {

	}

	@Override
	public RSEnumComposition getRsEnum(int id) {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemCompositionCache() {
		return null;
	}

	@Override
	public RSSpritePixels[] getCrossSprites() {
		return crosses;
	}


	@Override
	public EnumComposition getEnum(int id) {
		return null;
	}

	@Override
	public void draw2010Menu(int alpha) { menuHandler.draw2010Menu(alpha); }

	void initSubmenu(int x, int y) { menuHandler.initSubmenu(x, y); }
	@Override
	public int[] getGraphicsPixels() {
		return null;
	}

	@Override
	public int getGraphicsPixelsWidth() {
		return 0;
	}

	@Override
	public int getGraphicsPixelsHeight() {
		return 0;
	}

	@Override
	public void rasterizerFillRectangle(int x, int y, int w, int h, int rgb) {
		Rasterizer2D.drawBox(x,y,w,h,rgb);
	}

	@Override
	public int getStartX() {
		return 0;
	}

	@Override
	public int getStartY() {
		return 0;
	}

	@Override
	public int getEndX() {
		return 0;
	}

	@Override
	public int getEndY() {
		return 0;
	}

	@Override
	public void drawOriginalMenu(int alpha) { menuHandler.drawOriginalMenu(alpha); }

	@Override
	public void resetHealthBarCaches() {
		getHealthBarCache().reset();
		getHealthBarSpriteCache().reset();
	}

	@Override
	public void resetHitsplatCaches() {
		HitSplatDefinition.cached.clear();
		HitSplatDefinition.spritesCached.clear();
		HitSplatDefinition.fontsCached.clear();

	}

	@Override
	public boolean getRenderSelf() {
		return false;
	}

	@Override
	public void setRenderSelf(boolean enabled) {

	}

	@Override
	public void invokeMenuAction(String option, String target, int identifier, int opcode, int param0, int param1) {

	}

	@Override
	public RSMouseRecorder getMouseRecorder() {
		return null;
	}

	@Override
	public String getSelectedSpellName() {
		return null;
	}

	@Override
	public void setSelectedSpellName(String name) {

	}

	@Override
	public boolean getSpellSelected() {
		return false;
	}

	@Override
	public RSSoundEffect getTrack(RSAbstractArchive indexData, int id, int var0) {
		return null;
	}

	@Override
	public RSRawPcmStream createRawPcmStream(RSRawSound audioNode, int var0, int volume) {
		return null;
	}

	@Override
	public RSPcmStreamMixer getSoundEffectAudioQueue() {
		return null;
	}

	@Override
	public RSArchive getIndexCache4() {
		return null;
	}

	@Override
	public RSDecimator getSoundEffectResampler() {
		return null;
	}

	@Override
	public void setMusicTrackVolume(int volume) {

	}

	@Override
	public void setViewportWalking(boolean viewportWalking) {

	}

	@Override
	public void playMusicTrack(int var0, RSAbstractArchive var1, int var2, int var3, int var4, boolean var5) {

	}

	@Override
	public RSMidiPcmStream getMidiPcmStream() {
		return null;
	}

	@Override
	public int getCurrentTrackGroupId() {
		return 0;
	}

	@Override
	public String getSelectedSpellActionName() {
		return null;
	}

	@Override
	public int getSelectedSpellFlags() {
		return 0;
	}

	@Override
	public void setHideFriendAttackOptions(boolean yes) {

	}

	@Override
	public void setHideFriendCastOptions(boolean yes) {

	}

	@Override
	public void setHideClanmateAttackOptions(boolean yes) {
	}

	@Override
	public void setHideClanmateCastOptions(boolean yes) {

	}

	@Override
	public void setUnhiddenCasts(Set<String> casts) {

	}

	@Override
	public void addFriend(String name) {

	}

	@Override
	public void removeFriend(String name) {

	}

	@Override
	public void setModulus(BigInteger modulus) {

	}

	@Override
	public BigInteger getModulus() {
		return null;
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	@Override
	public void setAllWidgetsAreOpTargetable(boolean value) {

	}

	@Override
	public void insertMenuItem(String action, String target, int opcode, int identifier, int argument1, int argument2,
							   boolean forceLeftClick) {

	}

	@Override
	public void setSelectedItemID(int id) {

	}

	@Override
	public int getSelectedItemWidget() {
		return 0;
	}

	@Override
	public void setSelectedItemWidget(int widgetID) {

	}

	@Override
	public int getSelectedItemSlot() {
		return 0;
	}

	@Override
	public void setSelectedItemSlot(int idx) {

	}

	@Override
	public int getSelectedSpellWidget() {
		return 0;
	}

	@Override
	public int getSelectedSpellChildIndex() {
		return 0;
	}

	@Override
	public void setSelectedSpellWidget(int widgetID) {

	}

	@Override
	public void setSelectedSpellChildIndex(int index) {

	}

	@Override
	public void scaleSprite(int[] canvas, int[] pixels, int color, int pixelX, int pixelY, int canvasIdx,
							int canvasOffset, int newWidth, int newHeight, int pixelWidth, int pixelHeight, int oldWidth) {

	}

	@Override
	public void promptCredentials(boolean clearPass) {

	}

	@Override
	public RSVarpDefinition getVarpDefinition(int id) {
		return null;
	}

	@Override
	public RSTileItem newTileItem() {
		return new TileItem();
	}

	@Override
	public RSNodeDeque newNodeDeque() {
		return new NodeDeque();
	}

	@Override
	public void updateItemPile(int localX, int localY) {
		updateItemPile(plane,localX,localY);
	}

	@Override
	public void setHideDisconnect(boolean dontShow) {

	}

	@Override
	public void setTempMenuEntry(MenuEntry entry) { menuHandler.setTempMenuEntry(entry); }

	@Override
	public void setShowMouseCross(boolean show) {

	}

	@Override
	public int getDraggedWidgetX() {
		return 0;
	}

	@Override
	public int getDraggedWidgetY() {
		return 0;
	}

	@Override
	public int[] getChangedSkillLevels() {
		return new int[0];
	}

	@Override
	public void setMouseIdleTicks(int cycles) {
		MouseHandler.idleCycles = cycles;
	}

	@Override
	public void setKeyboardIdleTicks(int cycles) {
		KeyHandler.idleCycles = cycles;
	}

	@Override
	public void setGeSearchResultCount(int count) {
	}

	@Override
	public void setGeSearchResultIds(short[] ids) {

	}

	@Override
	public void setGeSearchResultIndex(int index) {

	}

	@Override
	public void setComplianceValue(String key, boolean value) {

	}

	@Override
	public boolean getComplianceValue(String key) {
		return false;
	}

	@Override
	public boolean isMirrored() {
		return false;
	}

	@Override
	public void setMirrored(boolean isMirrored) {

	}

	@Override
	public boolean isComparingAppearance() {
		return false;
	}

	@Override
	public void setComparingAppearance(boolean comparingAppearance) {

	}

	@Override
	public void setLoginScreen(SpritePixels pixels) {

	}

	@Override
	public void setShouldRenderLoginScreenFire(boolean val) {

	}

	@Override
	public boolean shouldRenderLoginScreenFire() {
		return false;
	}

	@Override
	public boolean isKeyPressed(int keycode) {
		boolean[] pressedKeys = getPressedKeys();
		return pressedKeys[keycode];
	}

	@Override
	public int getFollowerIndex() {
		return 0;
	}

	@Override
	public int isItemSelected() {
		return 0;
	}

	@Override
	public String getSelectedItemName() {
		return null;
	}

	@Override
	public RSWidget getMessageContinueWidget() {
		return null;
	}

	@Override
	public void setMusicPlayerStatus(int var0) {

	}

	@Override
	public void setMusicTrackArchive(RSAbstractArchive var0) {

	}

	@Override
	public void setMusicTrackGroupId(int var0) {

	}

	@Override
	public void setMusicTrackFileId(int var0) {

	}

	@Override
	public void setMusicTrackBoolean(boolean var0) {

	}

	@Override
	public void setPcmSampleLength(int var0) {

	}

	@Override
	public int[] getChangedVarps() {
		return new int[0];
	}

	@Override
	public int getChangedVarpCount() {
		return 0;
	}

	@Override
	public void setChangedVarpCount(int changedVarpCount) {

	}

	@Override
	public void setOutdatedScript(String outdatedScript) {

	}

	@Override
	public List<String> getOutdatedScripts() {
		return null;
	}

	@Override
	public RSFrames getFrames(int frameId) {
		return null;
	}

	@Override
	public RSSpritePixels getMinimapSprite() {
		return minimapImage;
	}

	@Override
	public void setMinimapSprite(SpritePixels spritePixels) {

	}

	@Override
	public void drawObject(int z, int x, int y, int randomColor1, int randomColor2) {

	}

	@Override
	public RSScriptEvent createScriptEvent() {
		return null;
	}

	@Override
	public void runScript(RSScriptEvent ev, int ex, int var2) {

	}

	@Override
	public void setHintArrowTargetType(int value) {

	}

	@Override
	public int getHintArrowTargetType() {
		return 0;
	}

	@Override
	public void setHintArrowX(int value) {

	}

	@Override
	public int getHintArrowX() {
		return 0;
	}

	@Override
	public void setHintArrowY(int value) {

	}

	@Override
	public int getHintArrowY() {
		return 0;
	}

	@Override
	public void setHintArrowOffsetX(int value) {

	}

	@Override
	public void setHintArrowOffsetY(int value) {

	}

	@Override
	public void setHintArrowNpcTargetIdx(int value) {

	}

	@Override
	public int getHintArrowNpcTargetIdx() {
		return 0;
	}

	@Override
	public void setHintArrowPlayerTargetIdx(int value) {

	}

	@Override
	public int getHintArrowPlayerTargetIdx() {
		return 0;
	}

	@Override
	public RSSequenceDefinition getSequenceDefinition(int id) {
		return (RSSequenceDefinition) SequenceDefinition.get(id);
	}

	@Override
	public RSIntegerNode newIntegerNode(int contents) {
		return null;
	}

	@Override
	public RSObjectNode newObjectNode(Object contents) {
		return null;
	}

	@Override
	public RSIterableNodeHashTable newIterableNodeHashTable(int size) {
		return null;
	}

	@Override
	public RSVarbitComposition getVarbitComposition(int id) {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_skeletonsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSequenceDefinition_animationsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getNpcDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getObjectDefinition_modelsArchive() {
		return Js5List.models;
	}

	@Override
	public RSAbstractArchive getObjectDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getItemDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getKitDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getKitDefinition_modelsArchive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSpotAnimationDefinition_archive() {
		return null;
	}

	@Override
	public RSAbstractArchive getSpotAnimationDefinition_modelArchive() {
		return null;
	}

	@Override
	public RSBuffer createBuffer(byte[] initialBytes) {
		return null;
	}

	@Override
	public RSSceneTilePaint createSceneTilePaint(int swColor, int seColor, int neColor, int nwColor, int texture, int rgb, boolean isFlat) {
		return null;
	}

	@Override
	public long[] getCrossWorldMessageIds() {
		return null;
	}

	@Override
	public int getCrossWorldMessageIdsIndex() {
		return 0;
	}

	@Override
	public RSClanChannel[] getCurrentClanChannels() {
		return new RSClanChannel[0];
	}

	@Override
	public RSClanSettings[] getCurrentClanSettingsAry() {
		return new RSClanSettings[0];
	}

	@Override
	public RSClanChannel getClanChannel() {
		return null;
	}

	@Override
	public RSClanChannel getGuestClanChannel() {
		return null;
	}

	@Override
	public RSClanSettings getClanSettings() {
		return null;
	}

	@Override
	public RSClanSettings getGuestClanSettings() {
		return null;
	}

	@Override
	public ClanRank getClanRankFromRs(int rank) {
		return null;
	}

	@Override
	public RSIterableNodeHashTable readStringIntParameters(RSBuffer buffer, RSIterableNodeHashTable table) {
		return null;
	}

	@Override
	public int getRndHue() {
		return 0;
	}

	@Override
	public short[][][] getTileUnderlays() {
		return scene.getUnderlayIds();
	}

	@Override
	public short[][][] getTileOverlays() {
		return scene.getOverlayIds();
	}

	@Override
	public byte[][][] getTileShapes() {
		return scene.getTileShapes();
	}

	@Override
	public RSSpotAnimationDefinition getSpotAnimationDefinition(int id) {
		return null;
	}

	@Override
	public RSModelData getModelData(RSAbstractArchive var0, int var1, int var2) {
		return com.client.entity.model.ModelData.getModel(var1);
	}

	@Override
	public boolean isCameraLocked() {
		return isCameraLocked;
	}

	@Override
	public boolean getCameraPitchRelaxerEnabled() {
		return pitchRelaxEnabled;
	}

	public static boolean unlockedFps;
	public long delayNanoTime;
	public long lastNanoTime;
	public static double tmpCamAngleY;
	public static double tmpCamAngleX;

	@Override
	public boolean isUnlockedFps() {
		return unlockedFps;
	}

	@Override
	public long getUnlockedFpsTarget() {
		return delayNanoTime;
	}

	public void updateCamera()
	{
		if (unlockedFps)
		{
			long nanoTime = System.nanoTime();
			long diff = nanoTime - this.lastNanoTime;
			this.lastNanoTime = nanoTime;

			if (this.getGameState() == GameState.LOGGED_IN)
			{
				this.interpolateCamera(diff);
			}
		}
	}

	public static final int STANDARD_PITCH_MIN = 128;
	public static final int STANDARD_PITCH_MAX = 383;
	public static final int NEW_PITCH_MAX = 512;

	public void interpolateCamera(long var1)
	{
		double angleDX = diffToDangle(getCamAngleDY(), var1);
		double angleDY = diffToDangle(getCamAngleDX(), var1);

		tmpCamAngleY += angleDX / 2;
		tmpCamAngleX += angleDY / 2;
		tmpCamAngleX = Doubles.constrainToRange(tmpCamAngleX, Perspective.UNIT * STANDARD_PITCH_MIN, getCameraPitchRelaxerEnabled() ? Perspective.UNIT * NEW_PITCH_MAX : Perspective.UNIT * STANDARD_PITCH_MAX);

		int yaw = toCameraPos(tmpCamAngleY);
		int pitch = toCameraPos(tmpCamAngleX);

		setCameraYawTarget(yaw);
		setCameraPitchTarget(pitch);
	}

	public static int toCameraPos(double var0)
	{
		return (int) (var0 / Perspective.UNIT) & 2047;
	}


	public static double diffToDangle(int var0, long var1)
	{
		double var2 = var0 * Perspective.UNIT;
		double var3 = (double) var1 / 2.0E7D;

		return var2 * var3;
	}

	@Override
	public void posToCameraAngle(int var0, int var1) {
		tmpCamAngleY = var0 * Perspective.UNIT;
		tmpCamAngleX = var1 * Perspective.UNIT;
	}

	public static void onCameraPitchTargetChanged(int idx)
	{
		int newPitch = instance.getCameraPitchTarget();
		int pitch = newPitch;
		if (pitchRelaxEnabled)
		{
			// This works because the vanilla camera movement code only moves %2
			if (lastPitchTarget > STANDARD_PITCH_MAX && newPitch == STANDARD_PITCH_MAX)
			{
				pitch = lastPitchTarget;
				if (pitch > NEW_PITCH_MAX)
				{
					pitch = NEW_PITCH_MAX;
				}
				instance.setCameraPitchTarget(pitch);
			}
		}
		lastPitchTarget = pitch;
	}

	public void onCamAngleDXChange()
	{
		if (invertPitch && getMouseCurrentButton() == 4 && isMouseCam())
		{
			setCamAngleDX(-getCamAngleDX());
		}
	}

	public void onCamAngleDYChange()
	{
		if (invertYaw && getMouseCurrentButton() == 4 && isMouseCam())
		{
			setCamAngleDY(-getCamAngleDY());
		}
	}

	public static void onCameraPitchChanged(int idx)
	{
		int newPitch = instance.getCameraPitch();
		int pitch = newPitch;
		if (pitchRelaxEnabled)
		{
			// This works because the vanilla camera movement code only moves %2
			if (lastPitch > STANDARD_PITCH_MAX && newPitch == STANDARD_PITCH_MAX)
			{
				pitch = lastPitch;
				if (pitch > NEW_PITCH_MAX)
				{
					pitch = NEW_PITCH_MAX;
				}
				instance.setCameraPitch(pitch);
			}
		}
		lastPitch = pitch;
	}

	@Override
	public RSClanChannel getClanChannel(int clanId) {
		return null;
	}

	@Override
	public RSClanSettings getClanSettings(int clanId) {
		return null;
	}

	@Override
	public void setUnlockedFps(boolean unlock) {
		unlockedFps = unlock;
	}

	@Override
	public void setUnlockedFpsTarget(int fps) {
		delayNanoTime = fps;
	}

	@Override
	public net.runelite.api.Deque<AmbientSoundEffect> getAmbientSoundEffects() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getEnumDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFloorUnderlayDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFloorOverlayDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionSpritesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getHitSplatDefinitionDontsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getInvDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getItemDefinitionSpritesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getKitDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getNpcDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getNpcDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionModelDataCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getObjectDefinitionEntitiesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getParamDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getPlayerAppearanceModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionFramesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSequenceDefinitionModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpotAnimationDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpotAnimationDefinitionModlesCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarcIntCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getVarpDefinitionCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getModelsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getFontsCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpriteMasksCache() {
		return null;
	}

	@Override
	public RSEvictingDualNodeHashTable getSpritesCache() {
		return null;
	}

	@Override
	public RSIterableNodeHashTable createIterableNodeHashTable(int size) {
		return null;
	}

	@Override
	public int getSceneMaxPlane() {
		return 0;
	}

	@Override
	public void setIdleTimeout(int id) {

	}

	@Override
	public int getIdleTimeout() {
		return 0;
	}

	public boolean canZoomMap = false;
	public boolean minimapHidden = false;

	@Override
	public void setMinimapZoom(boolean minimapZoom) {
		this.canZoomMap = minimapZoom;
	}

	@Override
	public void setHideMinimap(boolean hide) {
		this.minimapHidden = hide;
	}

	public boolean splatStyles;

	@Override
	public void setStylesActive(boolean active) {
		splatStyles = active;
	}

	@Override
	public double getMinimapZoom() {
		return 4.0 * 256.0 / (minimapZoom + 256);
	}

	@Override
	public boolean isMinimapZoom() {
		return canZoomMap;
	}

	@Override
	public void setMinimapZoom(double zoom) {
		// zoom is pixels-per-tile; invert to native minimapZoom offset: minimapZoom = 256*(4/zoom) - 256
		minimapZoom = (int) (256.0 * 4.0 / zoom - 256);
	}

	public static int viewportZoom;

	@Override
	public void setScale(int scale) {
		viewportZoom = scale;
	}

	@Override
	public int getMouseWheelRotation() {
		return mouseWheelRotation;
	}

	@Override
	public int getScale() {
		return viewportZoom;
	}

	public boolean newclickInRegion(int x1, int y1, Sprite drawnSprite) {
		return  MouseHandler.clickMode3 == 1 && (MouseHandler.saveClickX >= x1 && MouseHandler.saveClickX <= x1 + drawnSprite.myWidth && MouseHandler.saveClickY >= y1
			&& MouseHandler.saveClickY <= y1 + drawnSprite.myHeight);
	}

	public boolean newmouseInRegion(int x1, int y1, Sprite drawnSprite) {
		return MouseHandler.mouseX >= x1 && MouseHandler.mouseX <= x1 + drawnSprite.myWidth && MouseHandler.mouseY >= y1
			&& MouseHandler.mouseY <= y1 + drawnSprite.myHeight;
	}

	public boolean newmouseInRegion(int x1, int y1, int x2, int y2) {
		return  MouseHandler.mouseX >= x1 && MouseHandler.mouseX <= x1 + x2 && MouseHandler.mouseY >= y1
			&& MouseHandler.mouseY <= y1 + y2;
	}
	public boolean newclickInRegion(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		return MouseHandler.clickMode3 == 1
				&& (MouseHandler.saveClickX >= topLeftX && MouseHandler.saveClickX <= bottomRightX
				&& MouseHandler.saveClickY >= topLeftY && MouseHandler.saveClickY <= bottomRightY);
	}


	@Override
	public long getAccountHash() {
		return 0L;
	}

	//Menu

	static int tmpMenuOptionsCount;

	public static RSRuneLiteMenuEntry newBareRuneliteMenuEntry() { return MenuHandler.newBareRuneliteMenuEntry(); }

	public static RSRuneLiteMenuEntry newRuneliteMenuEntry(final int idx) { return MenuHandler.newRuneliteMenuEntry(idx); }

	public void sendStringClient(String text, int interfaceId) {

		if (RSInterface.interfaceCache[interfaceId] == null) {
			return;
		}

		RSInterface.interfaceCache[interfaceId].message = text;
	}

	@Override
	public void setDialogState(String prompt, int i, String value) {
		enterInputInChatString = prompt;
		inputDialogState = i;
		messagePromptRaised = true;
		amountOrNameInput = value;
	}

	@Override
	public String getInputValue() {
		return amountOrNameInput;
	}


	@Override
	public void openChatOption(int firstChildId, int optionAmount, String title, String[] options) {
		// Send title
		sendStringClient(title, firstChildId - 1);

		// Send options.
		for (int i = 0; i < options.length; i++) {
			sendStringClient(options[i], firstChildId + i);
		}

		// Send chatbox interface
		resetAnimation(firstChildId - 2);
		if (backDialogID != -1) {
			backDialogID = -1;
		}
		backDialogID = firstChildId - 2;

		continuedDialogue = false;
	}


	@Override
	public MenuEntry createMenuEntry(int idx) { return menuHandler.createMenuEntry(idx); }
	@Override
	public MenuEntry createMenuEntry(String option, String target, int identifier, int opcode, int param1, int param2, boolean forceLeftClick) {
		return menuHandler.createMenuEntry(option, target, identifier, opcode, param1, param2, forceLeftClick);
	}


	public MenuEntry createMenuEntry(String option, String target, int identifier, int opcode, int param1, int param2, int itemId, boolean forceLeftClick) {
		return menuHandler.createMenuEntry(option, target, identifier, opcode, param1, param2, itemId, forceLeftClick);
	}


	static final RSRuneLiteMenuEntry[] rl$menuEntries = new RSRuneLiteMenuEntry[500];

	@Override
	public MenuEntry[] getMenuEntries() { return menuHandler.getMenuEntries(); }


	@Override
	public int getMenuOptionCount() {
		return menuOptionsCount;
	}


	public static void onMenuOptionsChanged(int idx)
	{
		int tmpOptionsCount = tmpMenuOptionsCount;
		int optionCount = Client.instance.getMenuOptionCount();

		tmpMenuOptionsCount = optionCount;

		if (optionCount < tmpOptionsCount)
		{
			for (int i = optionCount; i < tmpOptionsCount; ++i)
			{
				RSRuneLiteMenuEntry entry = rl$menuEntries[i];
				if (entry == null)
				{
					logger.error("about to crash: opcnt:{} tmpopcnt:{} i:{}", optionCount, tmpOptionsCount, i);
				}
				rl$menuEntries[i].setParent(null);
				rl$menuEntries[i].setConsumer(null);
			}
		}
		else if (optionCount == tmpOptionsCount + 1)
		{
			if (Client.instance.getMenuOptions()[tmpOptionsCount] == null)
			{
				Client.instance.getMenuOptions()[tmpOptionsCount] = "null";
			}

			if (Client.instance.getMenuTargets()[tmpOptionsCount] == null)
			{
				Client.instance.getMenuTargets()[tmpOptionsCount] = "null";
			}

			if (rl$menuEntries[tmpOptionsCount] == null)
			{
				rl$menuEntries[tmpOptionsCount] = newRuneliteMenuEntry(tmpOptionsCount);
			}
			else
			{
				rl$menuEntries[tmpOptionsCount].setConsumer(null);
				rl$menuEntries[tmpOptionsCount].setParent(null);
			}

			MenuEntryAdded menuEntryAdded = new MenuEntryAdded(
					rl$menuEntries[tmpOptionsCount]
			);

			Client.instance.getCallbacks().post(menuEntryAdded);

			if (menuEntryAdded.isModified() && Client.instance.getMenuOptionCount() == optionCount)
			{
				Client.instance.getMenuOptions()[tmpOptionsCount] = menuEntryAdded.getOption();
				Client.instance.getMenuTargets()[tmpOptionsCount] = menuEntryAdded.getTarget();
				Client.instance.getMenuIdentifiers()[tmpOptionsCount] = menuEntryAdded.getIdentifier();
				Client.instance.getMenuOpcodes()[tmpOptionsCount] = menuEntryAdded.getType();
				Client.instance.getMenuArguments1()[tmpOptionsCount] = menuEntryAdded.getActionParam0();
				Client.instance.getMenuArguments2()[tmpOptionsCount] = menuEntryAdded.getActionParam1();
				Client.instance.getMenuForceLeftClick()[tmpOptionsCount] = menuEntryAdded.isForceLeftClick();
			}
		}
	}

	@Override
	public RSMenuAction getTempMenuAction() { return menuHandler.getTempMenuAction(); }

	public void setMenuEntries(MenuEntry[] menuEntries) { menuHandler.setMenuEntries(menuEntries); }
	public void sortMenuEntries(int left, int right) { menuHandler.sortMenuEntries(left, right); }
	public void setMenuOptionCount(int count) { menuHandler.setMenuOptionCount(count); }

	public String[] getMenuOptions() { return menuHandler.getMenuOptions(); }
	public String[] getMenuTargets() { return menuHandler.getMenuTargets(); }
	public int[] getMenuIdentifiers() { return menuHandler.getMenuIdentifiers(); }
	public int[] getMenuOpcodes() { return menuHandler.getMenuOpcodes(); }
	public int[] getMenuArguments1() { return menuHandler.getMenuArguments1(); }
	public int[] getMenuArguments2() { return menuHandler.getMenuArguments2(); }
	public boolean[] getMenuForceLeftClick() { return menuHandler.getMenuForceLeftClick(); }

	public boolean isMenuOpen() { return menuHandler.isMenuOpen(); }

	public int getMenuX() { return menuHandler.getMenuX(); }
	public int getMenuY() { return menuHandler.getMenuY(); }
	public int getMenuHeight() { return menuHandler.getMenuHeight(); }
	public int getMenuWidth() { return menuHandler.getMenuWidth(); }


	@Override
	public int getLeftClickOpensMenu() { return menuHandler.getLeftClickOpensMenu(); }


	@Override
	public void setPrintMenuActions(boolean b) { menuHandler.setPrintMenuActions(b); }

	@Override
	public void setMenuX(int x) { menuHandler.setMenuX(x); }
	@Override
	public void setMenuY(int y) { menuHandler.setMenuY(y); }
	@Override
	public void setMenuHeight(int h) { menuHandler.setMenuHeight(h); }
	@Override
	public void setMenuWidth(int w) { menuHandler.setMenuWidth(w); }



	public boolean isMenuScrollable() { return menuHandler.isMenuScrollable(); }
	public int getMenuScroll() { return menuHandler.getMenuScroll(); }
	public void setMenuScroll(int s) { menuHandler.setMenuScroll(s); }

	@Override
	public int getSubmenuX() { return menuHandler.getSubmenuX(); }
	@Override
	public MenuEntry getClickedMenuEntry() { return menuHandler.getClickedMenuEntry(); }
	@Override
	public int getSubmenuY() { return menuHandler.getSubmenuY(); }
	@Override
	public int getSubmenuWidth() { return menuHandler.getSubmenuWidth(); }
	@Override
	public int getSubmenuHeight() { return menuHandler.getSubmenuHeight(); }
	@Override
	public int getSubmenuScroll() { return menuHandler.getSubmenuScroll(); }
	@Override
	public int getSubmenuIdx() { return menuHandler.getSubmenuIdx(); }
	@Override
	public void setSubmenuX(int x) { menuHandler.setSubmenuX(x); }
	@Override
	public void setSubmenuY(int y) { menuHandler.setSubmenuY(y); }
	@Override
	public void setSubmenuWidth(int w) { menuHandler.setSubmenuWidth(w); }
	@Override
	public void setSubmenuHeight(int h) { menuHandler.setSubmenuHeight(h); }
	@Override
	public void setSubmenuScroll(int s) { menuHandler.setSubmenuScroll(s); }
	@Override
	public void setSubmenuScrollMax(int max) { menuHandler.setSubmenuScrollMax(max); }
	public void setSubmenuIdx(int idx) { menuHandler.setSubmenuIdx(idx); }

	int getFpsValue() {
		return fps;
	}

	void callDrawInitial(int percent, String text, boolean clear) {
		drawInitial(percent, text, clear);
	}

	public void triggerMudSplash(int durationFrames) {
		int frames = Math.max(1, Math.min(255, durationFrames));
		mudSplashTicks = frames;
		mudSplashTotalTicks = frames;
		mudSplashDirty = true;
	}

	public boolean isMudSplashActive() {
		return mudSplashTicks > 0;
	}

	public int getMudSplashTicks() {
		return mudSplashTicks;
	}

	public int getMudSplashTotalTicks() {
		return mudSplashTotalTicks;
	}

	public void consumeMudSplashTick() {
		if (mudSplashTicks > 0) {
			mudSplashTicks--;
		}
	}

	public Sprite getMudSplashSprite(int width, int height) {
		if (mudSplashSprite == null || mudSplashDirty || mudSplashSpriteW != width || mudSplashSpriteH != height) {
			mudSplashSprite = buildMudSplashSprite(width, height);
			mudSplashSpriteW = width;
			mudSplashSpriteH = height;
			mudSplashDirty = false;
		}
		return mudSplashSprite;
	}

	private Sprite buildMudSplashSprite(int width, int height) {
		int[] pixels = new int[width * height];
		java.util.Random rng = new java.util.Random(System.nanoTime());

		int area = width * height;
		int blobCount = Math.max(20, area / 15000);
		int maxRadius = Math.max(24, Math.min(width, height) / 4);

		for (int i = 0; i < blobCount; i++) {
			int r = 18 + rng.nextInt(maxRadius);
			int cx = rng.nextInt(width);
			int cy = rng.nextInt(height);
			int baseAlpha = 60 + rng.nextInt(90);
			int cr = 70 + rng.nextInt(50);
			int cg = 45 + rng.nextInt(35);
			int cb = 25 + rng.nextInt(25);
			int rSq = r * r;

			int yStart = Math.max(0, cy - r);
			int yEnd = Math.min(height - 1, cy + r);
			for (int y = yStart; y <= yEnd; y++) {
				int dy = y - cy;
				int dySq = dy * dy;
				if (dySq > rSq) {
					continue;
				}
				int maxDx = (int) Math.sqrt(rSq - dySq);
				int xStart = Math.max(0, cx - maxDx);
				int xEnd = Math.min(width - 1, cx + maxDx);
				for (int x = xStart; x <= xEnd; x++) {
					int dx = x - cx;
					int distSq = dySq + dx * dx;
					int alpha = (baseAlpha * (rSq - distSq)) / rSq;
					int idx = y * width + x;
					int existingAlpha = (pixels[idx] >>> 24) & 0xff;
					if (alpha > existingAlpha) {
						pixels[idx] = (alpha << 24) | (cr << 16) | (cg << 8) | cb;
					}
				}
			}
		}

		int dropletCount = blobCount * 3;
		for (int i = 0; i < dropletCount; i++) {
			int r = 2 + rng.nextInt(7);
			int cx = rng.nextInt(width);
			int cy = rng.nextInt(height);
			int baseAlpha = 40 + rng.nextInt(70);
			int cr = 65 + rng.nextInt(40);
			int cg = 40 + rng.nextInt(30);
			int cb = 20 + rng.nextInt(20);
			int rSq = r * r;

			int yStart = Math.max(0, cy - r);
			int yEnd = Math.min(height - 1, cy + r);
			for (int y = yStart; y <= yEnd; y++) {
				int dy = y - cy;
				int dySq = dy * dy;
				if (dySq > rSq) {
					continue;
				}
				int maxDx = (int) Math.sqrt(rSq - dySq);
				int xStart = Math.max(0, cx - maxDx);
				int xEnd = Math.min(width - 1, cx + maxDx);
				for (int x = xStart; x <= xEnd; x++) {
					int dx = x - cx;
					int distSq = dySq + dx * dx;
					int alpha = (baseAlpha * (rSq - distSq)) / rSq;
					int idx = y * width + x;
					int existingAlpha = (pixels[idx] >>> 24) & 0xff;
					if (alpha > existingAlpha) {
						pixels[idx] = (alpha << 24) | (cr << 16) | (cg << 8) | cb;
					}
				}
			}
		}

		return new Sprite(pixels, width, height);
	}

}

