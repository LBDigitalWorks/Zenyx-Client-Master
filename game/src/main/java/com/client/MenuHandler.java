package com.client;

import com.client.broadcast.Broadcast;
import com.client.broadcast.BroadcastManager;
import com.client.collection.node.NodeDeque;
import com.client.definitions.ItemDefinition;
import com.client.NPCDropInfo;
import com.client.definitions.NpcDefinition;
import com.client.definitions.ObjectDefinition;
import com.client.Rasterizer2D;
import com.client.draw.font.osrs.RSFontOSRS;
import com.client.engine.impl.MouseHandler;
import com.client.entity.model.ViewportMouse;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.impl.GroupIronmanBank;
import com.client.graphics.interfaces.impl.Autocast;
import com.client.graphics.interfaces.impl.Bank;
import com.client.graphics.interfaces.impl.GrandExchange;
import com.client.Configuration;
import com.client.graphics.interfaces.impl.Keybinding;
import com.client.graphics.interfaces.impl.SettingsTabWidget;
import com.client.graphics.interfaces.settings.SettingsInterface;
import com.client.hover.HoverMenuManager;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import com.client.mixins.menu.RuneLiteMenuEntry;
import com.client.utilities.ObjectKeyUtil;
import com.client.TileItem;
import com.client.StringUtils;
import com.client.utilities.settings.SettingsManager;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.rs.api.RSMenuAction;
import net.runelite.rs.api.RSRuneLiteMenuEntry;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

import static com.client.engine.impl.MouseHandler.clickMode3;
import static net.runelite.api.MenuAction.UNKNOWN;

/**
 * Handles right-click menu building, rendering, and action processing.
 * Extracted from Client.java as part of the architecture refactoring effort.
 */
@Slf4j
public class MenuHandler {

    final Client client;

    // Menu position and size
    private int menuX;
    private int menuY;
    private int menuWidth;

    private int menuHeight;
    private int menuScroll = 0;
    int menuScrollMax = 0;

    // Submenu state
    private int submenuX = 0;
    private int submenuY = 0;
    private int submenuWidth = 0;
    private int submenuHeight = 0;
    private int submenuScroll = 0;
    int submenuScrollMax = 0;
    private int submenuIdx = -1;
    private int clickedIdx = -1;

    // Debug / RuneLite state
    boolean printMenuActions = false;
    RSMenuAction tempMenuAction;

    // Menu color constants
    static final int MENU_BORDER_OUTER_2010 = 0x6D6A5B;
    static final int MENU_BORDER_INNER_2010 = 0x524A3D;
    static final int MENU_PADDING_2010 = 0x2B2622;
    static final int MENU_BACKGROUND_2010 = 0x2B271C;
    static final int MENU_TEXT_2010 = 0xC6B895;
    static final int MENU_HEADER_GRADIENT_TOP_2010 = 0x322E22;
    static final int MENU_HEADER_GRADIENT_BOTTOM_2010 = 0x090A04;
    static final int ORIGINAL_BG = 0x5D5447;

    public MenuHandler(Client client) {
        this.client = client;
    }

    private int bankTabPreviewToOpenButton(int param1) {
        for (int i = 0; i < Bank.BANK_TAB_ITEM_DISPLAYS.length; i++) {
            if (Bank.BANK_TAB_ITEM_DISPLAYS[i] == param1) {
                return Bank.OPEN_TAB_BUTTONS[i];
            }
        }
        return -1;
    }

    private int bankTabSpriteToActionButton(int param1, String option) {
        for (int i = 0; i < Bank.TAB_SPRITE_DISPLAYS.length; i++) {
            if (Bank.TAB_SPRITE_DISPLAYS[i] != param1) {
                continue;
            }
            if (i >= 0 && i < Bank.OPEN_TAB_BUTTONS.length) {
                return Bank.OPEN_TAB_BUTTONS[i];
            }
        }
        return -1;
    }

    private int remapGroupStorageButton(int param1) {
        if (client.openInterfaceID != GroupIronmanBank.BANK_INTERFACE_ID) {
            return param1;
        }

        return GroupIronmanBank.remapButton(param1);
    }

    private static boolean isMysteryBoxItem(int itemId) {
        return itemId == 6199 || itemId == 6828 || itemId == 11739 || itemId == 13346;
    }

    private void openMysteryBoxFallbackIfNeeded(int interfaceId, int itemId, String option) {
        // Disabled: server-side open path is authoritative and this fallback races interface state.
    }

    // ========== Getters / Setters ==========

    public int getMenuX() { return menuX; }
    public int getMenuY() { return menuY; }
    public int getMenuWidth() { return menuWidth; }
    public int getMenuHeight() { return menuHeight; }

    public void setMenuX(int x) { menuX = x; }
    public void setMenuY(int y) { menuY = y; }
    public void setMenuWidth(int w) { menuWidth = w; }
    public void setMenuHeight(int h) { menuHeight = h; }

    public boolean isMenuOpen() { return client.isMenuOpen; }
    public int getMenuOptionCount() { return client.menuOptionsCount; }
    public void setMenuOptionCount(int count) { client.menuOptionsCount = count; }

    public int getMenuScroll() { return menuScroll; }
    public void setMenuScroll(int s) { menuScroll = Ints.constrainToRange(s, 0, menuScrollMax); }
    public boolean isMenuScrollable() { return menuScrollMax > 0; }

    public int getSubmenuX() { return submenuX; }
    public int getSubmenuY() { return submenuY; }
    public int getSubmenuWidth() { return submenuWidth; }
    public int getSubmenuHeight() { return submenuHeight; }
    public int getSubmenuScroll() { return submenuScroll; }
    public int getSubmenuIdx() { return submenuIdx; }

    public void setSubmenuX(int x) { submenuX = x; }
    public void setSubmenuY(int y) { submenuY = y; }
    public void setSubmenuWidth(int w) { submenuWidth = w; }
    public void setSubmenuHeight(int h) { submenuHeight = h; }
    public void setSubmenuScroll(int s) { submenuScroll = Ints.constrainToRange(s, 0, submenuScrollMax); }
    public void setSubmenuScrollMax(int max) { submenuScrollMax = max; }
    public void setSubmenuIdx(int idx) { submenuIdx = idx; }

    public MenuEntry getClickedMenuEntry() {
        MenuEntry toRet = null;
        if (clickedIdx != -1) {
            toRet = Client.rl$menuEntries[clickedIdx];
            clickedIdx = -1;
        }
        return toRet;
    }

    public int getLeftClickOpensMenu() { return 0; }

    public void setPrintMenuActions(boolean b) { printMenuActions = b; }

    public RSMenuAction getTempMenuAction() { return tempMenuAction; }

    public void setTempMenuEntry(MenuEntry entry) {
        if (entry == null || tempMenuAction == null) {
            return;
        }
        tempMenuAction.setOption(entry.getOption());
        tempMenuAction.setOpcode(entry.getType().getId());
        tempMenuAction.setIdentifier(entry.getIdentifier());
        tempMenuAction.setParam0(entry.getParam0());
        tempMenuAction.setParam1(entry.getParam1());
    }

    public String[] getMenuOptions() { return Client.menuActions; }
    public String[] getMenuTargets() { return Client.menuTargets; }
    public int[] getMenuIdentifiers() { return Client.menuIdentifiers; }
    public int[] getMenuOpcodes() { return Client.menuOpcodes; }
    public int[] getMenuArguments1() { return Client.menuArguments1; }
    public int[] getMenuArguments2() { return Client.menuArguments2; }
    public boolean[] getMenuForceLeftClick() { return Client.menuShiftClick; }

    // ========== insertMenuItemNoShift overloads ==========

    public void insertMenuItemNoShift(String action, String target, int opcode, int identifier, int arg1, int arg2) {
        insertMenuItem(action, target, opcode, identifier, arg1, arg2, -1, false);
    }

    public void insertMenuItemNoShift(String action, int opcode, int identifier, int arg1, int arg2) {
        insertMenuItemNoShift(action, "", opcode, identifier, arg1, arg2);
    }

    public void insertMenuItemNoShift(String action, MenuAction opcode, int identifier, int arg1, int arg2) {
        insertMenuItemNoShift(action, "", opcode.getId(), identifier, arg1, arg2);
    }

    public void insertMenuItemNoShift(String action, int opcode, int identifier, int arg1) {
        insertMenuItemNoShift(action, opcode, identifier, arg1, -1);
    }

    public void insertMenuItemNoShift(String action, int opcode, int identifier) {
        insertMenuItemNoShift(action, opcode, identifier, -1);
    }

    public void insertMenuItemNoShift(String action, MenuAction opcode) {
        insertMenuItemNoShift(action, opcode.getId(), -1);
    }

    public void insertMenuItemNoShift(String action, int opcode) {
        insertMenuItemNoShift(action, opcode, -1);
    }

    public void insertMenuItemNoShift(String action) {
        insertMenuItemNoShift(action, -1);
    }

    // ========== insertMenuItem ==========

    public final void insertMenuItem(String action, String target, int opcode, int identifier, int arg1, int arg2, int unknown, boolean shiftClick) {
        if (!client.isMenuOpen) {
            if (client.menuOptionsCount < 500) {
                Client.menuActions[client.menuOptionsCount] = action;
                Client.menuTargets[client.menuOptionsCount] = target;
                Client.menuOpcodes[client.menuOptionsCount] = opcode;
                Client.menuIdentifiers[client.menuOptionsCount] = identifier;
                Client.menuArguments1[client.menuOptionsCount] = arg1;
                Client.menuArguments2[client.menuOptionsCount] = arg2;
                Client.menuShiftClick[client.menuOptionsCount] = shiftClick;
                ++client.menuOptionsCount;
                Client.onMenuOptionsChanged(0);
            }
        }
    }

    // ========== addCancelMenuEntry ==========

    public void addCancelMenuEntry() {
        client.method3434();
        Client.menuActions[0] = "Cancel";
        Client.menuTargets[0] = "";
        Client.menuOpcodes[0] = 1006;
        Client.menuShiftClick[0] = false;
        client.menuOptionsCount = 1;
        Client.onMenuOptionsChanged(0);
    }

    // ========== createMenu ==========

    public void createMenu() {
        if (client.itemSelected == 0 && client.spellSelected == 0) {
            insertMenuItemNoShift((Client.localPlayer.isAdminRights() && client.controlIsDown) ? "Teleport here" : "Walk here", MenuAction.WALK, -1, MouseHandler.mouseX, MouseHandler.mouseY);
        }
        long previous = -1;
        for (int cached = 0; cached < ViewportMouse.method1116(); cached++) {
            long current = ViewportMouse.entityTags[cached];
            int x = ViewportMouse.method7552(ViewportMouse.entityTags[cached]);
            int y = ViewportMouse.method134(cached);
            long var19 = ViewportMouse.entityTags[cached];
            int opcode = (int) (var19 >>> 14 & 3L);
            int uid = ViewportMouse.Entity_unpackID(ViewportMouse.entityTags[cached]);
            if (current == previous) {
                continue;
            }

            previous = current;
            if (opcode == 2 && client.scene.getObjectFlags(client.plane, x, y, previous) >= 0) {
                ObjectDefinition objectDef = ObjectDefinition.lookup(uid);
                if (objectDef.transforms != null) {
                    objectDef = objectDef.method580();
                }
                if (objectDef == null) {
                    continue;
                }

                if (client.itemSelected == 1) {
                    insertMenuItemNoShift("Use " + client.selectedItemName + " with @cya@" + objectDef.name, MenuAction.ITEM_USE_ON_GAME_OBJECT, ObjectKeyUtil.getObjectId(current), x, y);
                } else if (client.spellSelected == 1) {
                    if ((client.spellUsableOn & 4) == 4) {
                        insertMenuItemNoShift(client.spellTooltip + " @cya@" + objectDef.name, MenuAction.SPELL_CAST_ON_GAME_OBJECT, ObjectKeyUtil.getObjectId(current), x, y);
                    }
                } else {
                    if (objectDef.actions != null) {
                        for (int type = 4; type >= 0; type--)
                            if (objectDef.actions[type] != null) {
                                int type0 = 0;
                                if (type == 0) {
                                    type0 = 502;
                                }
                                if (type == 1) {
                                    type0 = 900;
                                }
                                if (type == 2) {
                                    type0 = 113;
                                }
                                if (type == 3) {
                                    type0 = 872;
                                }
                                if (type == 4) {
                                    type0 = 1062;
                                }
                                MenuAction action = MenuAction.of(type0);
                                insertMenuItemNoShift(objectDef.actions[type] + " @cya@" + objectDef.name, action, ObjectKeyUtil.getObjectId(current), x, y);

                            }

                    }

                    final String examineText = client.debugModels ? "Examine @cya@" + objectDef.name
                            + " (ID: " + ObjectKeyUtil.getObjectId(current)
                            + ", Models: " + Arrays.toString(objectDef.getModelIds()) + ")"
                            : "Examine @cya@" + objectDef.name;
                    insertMenuItemNoShift(examineText, MenuAction.EXAMINE_OBJECT, ObjectKeyUtil.getObjectId(current), x, y);
                }
            }
            if (opcode == 1) {
                Npc npc = client.npcs[uid];
                if (npc.desc.size == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
                    for (int j2 = 0; j2 < client.npcCount; j2++) {
                        Npc npc2 = client.npcs[client.npcIndices[j2]];
                        if (npc2 != null && npc2 != npc && npc2.desc.size == 1 && npc2.x == npc.x && npc2.y == npc.y && npc2.isShowMenuOnHover()) {
                            buildAtNPCMenu(npc2.desc, client.npcIndices[j2], y, x);
                        }
                    }

                    for (int l2 = 0; l2 < client.playerCount; l2++) {
                        Player player = client.players[client.playerIndices[l2]];
                        if (player != null && player.x == npc.x && player.y == npc.y) {
                            buildAtPlayerMenu(x, client.playerIndices[l2], player, y);
                        }
                    }

                }
                if (npc.isShowMenuOnHover()) {
                    buildAtNPCMenu(npc.desc, uid, y, x);
                }
            }
            if (opcode == 0) {
                Player player = client.players[uid];
                if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
                    for (int k2 = 0; k2 < client.npcCount; k2++) {
                        Npc class30_sub2_sub4_sub1_sub1_2 = client.npcs[client.npcIndices[k2]];
                        if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.size == 1
                                && class30_sub2_sub4_sub1_sub1_2.x == player.x
                                && class30_sub2_sub4_sub1_sub1_2.y == player.y && class30_sub2_sub4_sub1_sub1_2.isShowMenuOnHover())
                            buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, client.npcIndices[k2], y, x);
                    }

                    for (int i3 = 0; i3 < client.playerCount; i3++) {
                        Player class30_sub2_sub4_sub1_sub2_2 = client.players[client.playerIndices[i3]];
                        if (class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player
                                && class30_sub2_sub4_sub1_sub2_2.x == player.x
                                && class30_sub2_sub4_sub1_sub2_2.y == player.y)
                            buildAtPlayerMenu(x, client.playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, y);
                    }

                }
                buildAtPlayerMenu(x, uid, player, y);
            }
            if (opcode == 3) {
                NodeDeque class19 = client.groundItems[client.plane][x][y];
                if (class19 != null) {
                    for (TileItem item = (TileItem) class19.getFirst(); item != null; item = (TileItem) class19.getNext()) {
                        ItemDefinition itemDef = ItemDefinition.lookup(item.id);
                        if (client.itemSelected == 1) {
                            insertMenuItemNoShift("Use", client.selectedItemName + " " + "->" + " " + Client.colorStartTag(16748608) + itemDef.name, 16, item.id, x, y);
                        } else if (client.spellSelected == 1) {
                            if ((client.spellUsableOn & 1) == 1) {
                                insertMenuItemNoShift("", client.spellTooltip + " " + "->" + " " + Client.colorStartTag(16748608) + itemDef.name, 17, item.id, x, y);
                            }
                        } else {
                            String[] var24 = itemDef.groundActions;

                            for (int var34 = 4; var34 >= 0; --var34) {
                                if (item.method622(var34)) {
                                    if (var24 != null && var24[var34] != null) {
                                        int var21 = 0;
                                        if (var34 == 0) {
                                            var21 = 652;
                                        }

                                        if (var34 == 1) {
                                            var21 = 567;
                                        }

                                        if (var34 == 2) {
                                            var21 = 234;
                                        }

                                        if (var34 == 3) {
                                            var21 = 244;
                                        }

                                        if (var34 == 4) {
                                            var21 = 213;
                                        }

                                        insertMenuItemNoShift(var24[var34], Client.colorStartTag(16748608) + itemDef.name, var21, itemDef.id, x, y);
                                    } else if (var34 == 2) {
                                        insertMenuItemNoShift("Take", Client.colorStartTag(16748608) + itemDef.name, 20, itemDef.id, x, y);
                                    }
                                }
                            }
                            insertMenuItemNoShift("Examine @lre@" + itemDef.name, MenuAction.EXAMINE_ITEM_GROUND, item.id, x, y);
                        }
                    }
                }
            }
        }
    }

    // ========== buildAtNPCMenu ==========

    public void buildAtNPCMenu(NpcDefinition entityDef, int i, int j, int k) {
        if (client.menuOptionsCount >= 400)
            return;
        if (entityDef.configs != null)
            entityDef = entityDef.morph();
        if (entityDef == null)
            return;
        if (!entityDef.clickable)
            return;
        String s = entityDef.name;
        if (entityDef.combatLevel != 0)
            s = s + client.combatDiffColor(Client.localPlayer.combatLevel, entityDef.combatLevel) + " (level-" + entityDef.combatLevel
                    + ")";
        HoverMenuManager.reset();
        if (client.itemSelected == 1) {
            insertMenuItemNoShift("Use " + client.selectedItemName + " with @yel@" + s, MenuAction.ITEM_USE_ON_NPC, i, k, j);
            return;
        }
        if (client.spellSelected == 1) {
            if ((client.spellUsableOn & 2) == 2) {
                insertMenuItemNoShift(client.spellTooltip + " @yel@" + s, MenuAction.SPELL_CAST_ON_NPC, i, k, j);
            }
        } else {
            for (int l = 4; l >= 0; l--)
                if (entityDef.actions[l] != null
                        && !entityDef.actions[l].equalsIgnoreCase("attack"))
                {
                    MenuAction type = MenuAction.WALK;
                    if (l == 0)
                        type = MenuAction.NPC_FIRST_OPTION;
                    if (l == 1)
                        type = MenuAction.NPC_SECOND_OPTION;
                    if (l == 2)
                        type = MenuAction.NPC_THIRD_OPTION;
                    if (l == 3)
                        type = MenuAction.NPC_FOURTH_OPTION;
                    if (l == 4)
                        type = MenuAction.NPC_FIFTH_OPTION;

                    insertMenuItemNoShift(entityDef.actions[l] + " @yel@" + s, type, i, k, j);
                }
        }
        if (entityDef.actions != null) {
            for (int i1 = 4; i1 >= 0; i1--) {
                if (entityDef.actions[i1] != null && entityDef.actions[i1].equalsIgnoreCase("attack")) {
                    char c = '\0';
                    if (Configuration.npcAttackOptionPriority == 3)
                        continue;
                    if (Configuration.npcAttackOptionPriority == 0 && entityDef.combatLevel > Client.localPlayer.combatLevel
                            || Configuration.npcAttackOptionPriority == 1)
                        c = '\u07D0';
                    int type = MenuAction.WALK.getId();
                    if (i1 == 0)
                        type = 20 + c;
                    if (i1 == 1)
                        type = 412 + c;
                    if (i1 == 2)
                        type = 225 + c;
                    if (i1 == 3)
                        type = 965 + c;
                    if (i1 == 4)
                        type = 478 + c;
                    insertMenuItemNoShift(entityDef.actions[i1] + " @yel@" + s, type, i, k, j);
                }
            }

        }
        if (Client.clientData) {
            Npc npc = client.npcs[i];
            insertMenuItemNoShift("Examine @yel@" + s
                    + " @whi@("
                    + "id=" + entityDef.npcId
                    + ", index=" + i
                    + ", stand=" + entityDef.standingAnimation
                    + ", pos=[" + npc.getAbsoluteX() + ", " + npc.getAbsoluteY() + "]"
                    + ", footprintSize=" + entityDef.footprintSize
                    + ")", MenuAction.EXAMINE_NPC, i, k, j);
        } else {
            insertMenuItemNoShift("Examine @yel@" + s, MenuAction.EXAMINE_NPC, i, k, j);
        }
    }

    // ========== buildAtPlayerMenu ==========

    public void buildAtPlayerMenu(int tileX, int playerId, Player player, int tileY) {
        if (player == Client.localPlayer)
            return;
        if (!player.visible)
            return;
        if (client.menuOptionsCount >= 400)
            return;
        String s;
        if (player.title.length() < 0)
            s = player.displayName + client.combatDiffColor(Client.localPlayer.combatLevel, player.combatLevel) + " (level: "
                    + player.combatLevel + ")";
        else if (player.title.length() != 0)
            s = "@or1@" + player.title + "@whi@ " + player.displayName
                    + client.combatDiffColor(Client.localPlayer.combatLevel, player.combatLevel) + " (level: " + player.combatLevel
                    + ")";
        else
            s = player.displayName + client.combatDiffColor(Client.localPlayer.combatLevel, player.combatLevel) + " (level: "
                    + player.combatLevel + ")";
        HoverMenuManager.reset();
        if (client.itemSelected == 1) {
            Client.instance.insertMenuItemNoShift("Use " + client.selectedItemName + " with @whi@" + s, MenuAction.ITEM_USE_ON_PLAYER, playerId, tileX, tileY);
        } else if (client.spellSelected == 1) {
            if ((client.spellUsableOn & 8) == 8) {
                Client.instance.insertMenuItemNoShift(client.spellTooltip + " @whi@" + s, MenuAction.SPELL_CAST_ON_PLAYER, playerId, tileX, tileY);
            }
        } else {
            for (int type = 5; type >= 0; type--)
                if (client.playerOptions[type] != null) {
                    char c = '\0';
                    if (client.playerOptions[type].equalsIgnoreCase("attack")) {
                        if (Configuration.playerAttackOptionPriority == 3)
                            continue;
                        if (Configuration.playerAttackOptionPriority == 0 && player.combatLevel > Client.localPlayer.combatLevel
                                || Configuration.playerAttackOptionPriority == 1)
                            c = '\u07D0';
                        if (Client.localPlayer.team != 0 && player.team != 0)
                            if (Client.localPlayer.team == player.team)
                                c = '\u07D0';
                            else
                                c = '\0';
                    } else if (client.playerOptionsHighPriority[type])
                        c = '\u07D0';
                    int type0 = 0;
                    if (type == 0) {
                        type0 = 561 + c;
                    } else if (type == 1) {
                        type0 = 779 + c;
                    } else if (type == 2) {
                        type0 = 27 + c;
                    } else if (type == 3) {
                        type0 = 577 + c;
                    } else if (type == 4) {
                        type0 = 729 + c;
                    } else if (type == 5) {
                        if (Client.localPlayer.hasRightsBetween(0, 4)) {
                            Client.menuOpcodes[client.menuOptionsCount] = 745 + c;
                        } else {
                            continue;
                        }
                    } else if (type == 6) {
                        type0 = 50 + c;
                    } else if (type == 7) {
                        type0 = 51 + c;
                    }
                    MenuAction action = MenuAction.of(type0);
                    Client.instance.insertMenuItemNoShift(client.playerOptions[type] + " @whi@" + s, action, playerId, tileX, tileY);
                }
        }
    }

    // ========== Chat area menu builders ==========

    private void buildPublicChat(int j) {
        int l = 0;
        for (int i1 = 0; i1 < 500; i1++) {
            if (client.chatMessages[i1] == null)
                continue;
            if (client.chatTypeView != 1)
                continue;
            int j1 = client.chatTypes[i1];
            String s = client.chatNames[i1];
            int k1 = (70 - l * 14 + 42) + client.anInt1089 + 4 + 5;
            if (k1 < -23)
                break;
            if (s != null) {
                for (int i = 0; i < 50; i++) {
                    String crown = "@cr" + i + "@";
                    if (s.startsWith(crown)) {
                        s = s.substring(crown.length());
                    }
                }

                if (s.startsWith("<col=")) {
                    s = s.substring(s.indexOf("</col>") + 6);
                }
            }
            if ((j1 == 1 || j1 == 2) && (j1 == 1 || client.publicChatMode == 0 || client.publicChatMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1 && !s.equals(Client.localPlayer.displayName)) {
                    if (Client.localPlayer.hasRightsLevel(1)) {
                        insertMenuItemNoShift("Report abuse @whi@" + s, 606);
                    }
                    insertMenuItemNoShift("Add ignore" + " @whi@" + s, 42);
                    insertMenuItemNoShift("Reply to" + " @whi@" + s, 639);
                    insertMenuItemNoShift("Add friend" + "  @whi@" + s, 337);
                }
                l++;
            }
        }
    }

    private void buildFriendChat(int j) {
        int l = 0;
        for (int i1 = 0; i1 < 500; i1++) {
            if (client.chatMessages[i1] == null)
                continue;
            if (client.chatTypeView != 2)
                continue;
            int j1 = client.chatTypes[i1];
            String s = client.chatNames[i1];
            int k1 = (70 - l * 14 + 42) + client.anInt1089 + 4 + 5;
            if (k1 < -23)
                break;
            if (s != null) {
                for (int i = 0; i < 50; i++) {
                    String crown = "@cr" + i + "@";
                    if (s.startsWith(crown)) {
                        s = s.substring(crown.length());
                    }
                }
            }
            if ((j1 == 5 || j1 == 6) && (!client.splitPrivateChat || client.chatTypeView == 2)
                    && (j1 == 6 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s)))
                l++;
            if ((j1 == 3 || j1 == 7) && (!client.splitPrivateChat || client.chatTypeView == 2)
                    && (j1 == 7 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    if (Client.localPlayer.hasRightsLevel(1)) {
                        insertMenuItemNoShift("Report abuse @whi@" + s, 606);
                    }
                    insertMenuItemNoShift("Add ignore" + " @whi@" + s, 42);
                    insertMenuItemNoShift("Reply to" + " @whi@" + s, 639);
                    insertMenuItemNoShift("Add friend" + "  @whi@" + s, 337);
                }
                l++;
            }
        }
    }

    private void buildDuelorTrade(int j) {
        int l = 0;
        for (int i1 = 0; i1 < 500; i1++) {
            if (client.chatMessages[i1] == null)
                continue;
            if (client.chatTypeView != 3 && client.chatTypeView != 4)
                continue;
            int j1 = client.chatTypes[i1];
            String s = client.chatNames[i1];
            int k1 = (70 - l * 14 + 42) + client.anInt1089 + 4 + 5;
            if (k1 < -23)
                break;
            if (s != null) {
                for (int i = 0; i < 50; i++) {
                    String crown = "@cr" + i + "@";
                    if (s.startsWith(crown)) {
                        s = s.substring(crown.length());
                    }
                }
            }

            // Trade
            if (client.chatTypeView == 3 && j1 == 4 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept trade" + "  @whi@" + client.name, MenuAction.ACCEPT_TRADE);
                }
                l++;
            }

            // Gamble
            if (client.chatTypeView == 3 && j1 == 31 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept gamble request @whi@" + s, 11_501);
                }
                l++;
            }

            // Challenge
            if (client.chatTypeView == 4 && j1 == 8 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept challenge" + "  @whi@" + client.name, MenuAction.ACCEPT_CHALLENEGE);
                }
                l++;
            }
            if (j1 == 12) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Go-to" + "  @whi@" + client.name, MenuAction.GO_TO);
                }
                l++;
            }
        }
    }

    private void buildChatAreaMenu(int j) {
        int l = 0;
        for (int i1 = 0; i1 < 500; i1++) {
            if (client.chatMessages[i1] == null)
                continue;
            int j1 = client.chatTypes[i1];
            int k1 = (70 - l * 14 + 42) + client.anInt1089 + 4 + 5;
            if (k1 < -23)
                break;
            String s = client.chatNames[i1];
            if (client.chatTypeView == 1) {
                buildPublicChat(j);
                break;
            }
            if (client.chatTypeView == 2) {
                buildFriendChat(j);
                break;
            }
            if (client.chatTypeView == 3 || client.chatTypeView == 4) {
                buildDuelorTrade(j);
                break;
            }
            if (client.chatTypeView == 5) {
                break;
            }
            while (s.startsWith("@cr")) {
                String s2 = s.substring(3, s.length());
                int index = s2.indexOf("@");
                if (index != -1) {
                    s2 = s2.substring(0, index);
                    s = s.substring(4 + s2.length());
                }
            }
            if (s != null && s.startsWith("<col=") && s.indexOf("</col>") != -1) {
                s = s.substring(s.indexOf("</col>") + 6);
            }
            if (j1 == 0)
                l++;
            if ((j1 == 1 || j1 == 2) && (j1 == 1 || client.publicChatMode == 0 || client.publicChatMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1 && !s.equals(Client.localPlayer.displayName)) {
                    if (Client.localPlayer.hasRightsLevel(1)) {
                        insertMenuItemNoShift("Report abuse @whi@" + s, 606);
                    }
                    insertMenuItemNoShift("Add ignore" + " @whi@" + s, MenuAction.ADD_IGNORE_2);
                    insertMenuItemNoShift("Add friend" + " @whi@" + s, MenuAction.ADD_FRIEND_2);
                    insertMenuItemNoShift("Reply to" + " @whi@" + s, 639);
                }
                l++;
            }
            if ((j1 == 3 || j1 == 7) && !client.splitPrivateChat
                    && (j1 == 7 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    if (Client.localPlayer.hasRightsLevel(1)) {
                        insertMenuItemNoShift("Report abuse @whi@" + s, 606);
                    }
                    insertMenuItemNoShift("Add ignore" + " @whi@" + s, MenuAction.ADD_IGNORE_2);
                    insertMenuItemNoShift("Add friend" + " @whi@" + s, MenuAction.ADD_FRIEND_2);
                    insertMenuItemNoShift("Reply to" + " @whi@" + s, 639);
                }
                l++;
            }

            if (j1 == 99) {
                client.lastViewedDropTable = client.chatMessages[j] == null ? "" : client.chatMessages[j];
                insertMenuItemNoShift("Open Drop Table", 10_992);
                l++;
            }

            // Trade
            if (j1 == 4 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept trade" + " @whi@" + s, MenuAction.ACCEPT_TRADE);
                }
                l++;
            }
            if ((j1 == 5 || j1 == 6) && !client.splitPrivateChat && client.privateChatMode < 2)
                l++;
            if (j1 == 8 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept challenge" + " @whi@" + s, MenuAction.ACCEPT_CHALLENEGE);
                }
                l++;
            }

            if (j1 == 31 && (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    insertMenuItemNoShift("Accept Gamble Request From@whi@ " + s, 11_501);
                }
                l++;
            }
        }
    }

    private void buildSplitPrivateChatMenu() {
        if (!client.splitPrivateChat)
            return;
        int i = 0;
        if (client.anInt1104 != 0)
            i = 1;
        for (int j = 0; j < 100; j++) {
            if (client.chatMessages[j] != null) {
                int k = client.chatTypes[j];
                String s = client.chatNames[j];
                while (s.startsWith("@cr")) {
                    String s2 = s.substring(3, s.length());
                    int index = s2.indexOf("@");
                    if (index != -1) {
                        s2 = s2.substring(0, index);
                        s = s.substring(4 + s2.length());
                    }
                }
                if ((k == 3 || k == 7)
                        && (k == 7 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s))) {
                    int yPosition = (!client.isResized() ? 330 : Client.canvasHeight - 173) - i * 13;
                    int messageLength = client.newRegularFont.getTextWidth("From:  " + s + client.chatMessages[j]) + 25;
                    if (MouseHandler.mouseX >= 0 && MouseHandler.mouseX <= messageLength) {
                        if (MouseHandler.mouseY >= yPosition - 10 && client.getMouseY() <= yPosition + 3) {
                            if (messageLength > 450)
                                messageLength = 450;
                            if (Client.localPlayer.hasRightsLevel(1)) {
                                insertMenuItemNoShift("Report abuse @whi@" + s, 606);
                            }
                            insertMenuItemNoShift("Add ignore" + " @whi@" + s, 42);
                            insertMenuItemNoShift("Reply to" + " @whi@" + s, 639);
                            insertMenuItemNoShift("Add friend" + "  @whi@" + s, 337);
                        }
                    }
                    if (++i >= 5)
                        return;
                }
                if ((k == 5 || k == 6) && client.privateChatMode < 2 && ++i >= 5)
                    return;
            }
        }
    }

    private void buildBroadcasts() {

        int i = 0;

        if (BroadcastManager.isDisplayed()) {

            Broadcast bc = BroadcastManager.getCurrentBroadcast();

            if (bc == null)
                return;

            boolean update = client.isServerUpdating();

            int baseX = !client.isResized() ? 5 : 0;
            int yPosition = (!client.isResized() ? 330 : Client.canvasHeight - 173) - i * 13;
            yPosition -= update ? 13 : 0;
            String[] wrappedLines = BroadcastManager.getWrappedLines(client);
            if (wrappedLines.length == 0) {
                wrappedLines = new String[] { bc.getDecoratedMessage() };
            }
            int linesDrawn = wrappedLines.length;
            int topY = yPosition - (linesDrawn - 1) * 13;
            int messageLength = 0;
            for (String line : wrappedLines) {
                messageLength = Math.max(messageLength, client.newRegularFont.getTextWidth(line));
            }
            if (client.openInterfaceID <= 0 && MouseHandler.mouseX >= baseX && MouseHandler.mouseX <= baseX + messageLength) {
                if (MouseHandler.mouseY >= topY - 10 && client.getMouseY() <= yPosition) {
                    insertMenuItemNoShift("Dismiss", 11_115);
                    if (bc.type != 0) {
                        insertMenuItemNoShift(bc.type == 1 ? "Open URL" : bc.type == 2 ? "Teleport To Location" : null, bc.type == 1 ? 11_111 : 11_112);
                    }
                }
            }
            if (++i >= 1)
                return;
        }
    }

    // ========== processMinimapActions ==========

    private void processMinimapActions() {
        final boolean fixed = !client.isResized();
        if (fixed ? MouseHandler.mouseX >= 542 && MouseHandler.mouseX <= 579 && MouseHandler.mouseY >= 2 && MouseHandler.mouseY <= 38
            : MouseHandler.mouseX >= Client.canvasWidth - 180 && MouseHandler.mouseX <= Client.canvasWidth - 139
            && MouseHandler.mouseY >= 0 && MouseHandler.mouseY <= 40) {
            HoverMenuManager.reset();
            insertMenuItemNoShift("Face North", MenuAction.FACE_NORTH);
        }
        if (client.counterHover && Client.drawOrbs) {
            insertMenuItemNoShift("Reset @or1@XP total", 475);
            insertMenuItemNoShift(client.drawExperienceCounter ? "Hide @or1@XP drops" : "Show @or1@XP drops", 474);
        }
        if (client.worldHover && Client.drawOrbs) {
            insertMenuItemNoShift("Fullscreen @or1@World Map", MenuAction.WORLD_MAP_FULLSCREEN);
            insertMenuItemNoShift("Floating @or1@World Map", MenuAction.WORLD_MAP);
        }

        if (client.runHover) {
            insertMenuItemNoShift("Toggle Run", MenuAction.TOGGLE_RUN);
        }
        if (client.specialHover && Client.drawOrbs) {
            insertMenuItemNoShift("Use @or1@Special Attack", 1501);
        }
        if (client.prayHover && Client.drawOrbs) {
            insertMenuItemNoShift("Setup Quick-Prayers", MenuAction.TOGGLE_QUICK_PRAYERS);
            insertMenuItemNoShift(client.prayClicked ? "Turn Quick Prayers off" : "Turn Quick Prayers on", MenuAction.SETUP_QUICK_PRAYER);
        }
    }

    // ========== processRightClick ==========

    public void processRightClick() {

            if (client.activeInterfaceType != 0)
                return;
            addCancelMenuEntry();
            if (client.isResized()) {
                if (client.fullscreenInterfaceID != -1) {
                    client.anInt886 = 0;
                    client.anInt1315 = 0;
                    client.buildInterfaceMenu((Client.canvasWidth / 2) - 765 / 2,
                            RSInterface.interfaceCache[client.fullscreenInterfaceID], MouseHandler.mouseX,
                            (Client.canvasHeight / 2) - 503 / 2, MouseHandler.mouseY, 0);
                    if (client.anInt886 != client.anInt1026) {
                        client.anInt1026 = client.anInt886;
                    }
                    if (client.anInt1315 != client.anInt1129) {
                        client.anInt1129 = client.anInt1315;
                    }
                    return;
                }
            }
            buildSplitPrivateChatMenu();
            buildBroadcasts();
            client.anInt886 = 0;
            client.anInt1315 = 0;

            if (!client.isResized()) {
                if (client.isFullscreenInterface(client.openInterfaceID) ) {

                    if(!client.isResized()) {
                        client.buildInterfaceMenu(0, RSInterface.interfaceCache[client.openInterfaceID], MouseHandler.mouseX , 0, MouseHandler.mouseY - 4,0);
                    } else {
                        client.buildInterfaceMenu((int) client.getOnScreenWidgetOffsets().getX(), RSInterface.interfaceCache[client.openInterfaceID], MouseHandler.mouseX, (int) client.getOnScreenWidgetOffsets().getY(), MouseHandler.mouseY,0);
                    }
                } else if (client.getMouseX() > 0 && client.getMouseY() > 0 && client.getMouseX() < 516 && client.getMouseY() < 343) {
                    if (client.openInterfaceID != -1) {
                        client.buildInterfaceMenu(0, RSInterface.interfaceCache[client.openInterfaceID], client.getMouseX(), 0, client.getMouseY(), 0);
                    } else {
                        createMenu();
                    }
                }
            } else {
                if (client.checkMainScreenBounds()) {
                    int interfaceX = (Client.canvasWidth / 2) - 256 - 99;
                    int interfaceY = (Client.canvasHeight / 2) - 167 - 63;
                    if (client.openInterfaceID != -1 && client.openInterfaceID != 44900) {
                        client.buildInterfaceMenu(interfaceX, RSInterface.interfaceCache[client.openInterfaceID],
                                client.getMouseX(), interfaceY, client.getMouseY(), 0);
                    }

                    if (client.openInterfaceID == -1 || client.getMouseX() < interfaceX || client.getMouseY() < interfaceY || client.getMouseX() > interfaceX + 516 || client.getMouseY() > interfaceY + 338) {
                        createMenu();
                    }
                }
            }

            if (client.anInt886 != client.anInt1026) {
                client.anInt1026 = client.anInt886;
            }
            if (client.anInt1315 != client.anInt1129) {
                client.anInt1129 = client.anInt1315;
            }

            client.anInt886 = 0;
            client.anInt1315 = 0;
            client.resetTabInterfaceHover();
            if (!client.isResized()) {
                if (client.getMouseX() > 516 && client.getMouseY() > 205 && client.getMouseX() < 765 && client.getMouseY() < 466) {
                    if (client.invOverlayInterfaceID != 0) {
                        client.buildInterfaceMenu(547, RSInterface.interfaceCache[client.invOverlayInterfaceID], client.getMouseX(), 205, client.getMouseY(),
                                0);
                    } else if (Client.tabInterfaceIDs[Client.tabID] != -1) {
                        client.buildInterfaceMenu(547, RSInterface.interfaceCache[Client.tabInterfaceIDs[Client.tabID]], client.getMouseX(), 205, client.getMouseY(),
                                0);
                    }
                }
            } else {
                int y = client.stackTabs() ? 73 : 37;
                if (client.getMouseX() > Client.canvasWidth - 197 && client.getMouseY() > Client.canvasHeight - 275 - y + 10
                        && client.getMouseX() < Client.canvasWidth - 7 && client.getMouseY() < Client.canvasHeight - y - 5) {
                    if (client.invOverlayInterfaceID != 0 && !client.isFullscreenInterface(client.openInterfaceID)) {
                        client.buildInterfaceMenu(Client.canvasWidth - 197, RSInterface.interfaceCache[client.invOverlayInterfaceID],
                                client.getMouseX(), Client.canvasHeight - 275 - y + 10, client.getMouseY(), 0);
                    } else if (Client.tabInterfaceIDs[Client.tabID] != -1 && !client.isFullscreenInterface(client.openInterfaceID)) {
                        client.buildInterfaceMenu(Client.canvasWidth - 197, RSInterface.interfaceCache[Client.tabInterfaceIDs[Client.tabID]],
                                client.getMouseX(), Client.canvasHeight - 275 - y + 10, client.getMouseY(), 0);
                    }
                }
            }
            if (client.anInt886 != client.anInt1048) {
                Client.needDrawTabArea = true;
                Client.tabAreaAltered = true;
                client.anInt1048 = client.anInt886;
            }
            if (client.anInt1315 != client.anInt1044) {
                Client.needDrawTabArea = true;
                Client.tabAreaAltered = true;
                client.anInt1044 = client.anInt1315;
            }
            client.anInt886 = 0;
            client.anInt1315 = 0;
            /* Chat area clicking */
            if (!client.isResized()) {
                if (client.getMouseX() > 0 && client.getMouseY() > 338 && client.getMouseX() < 490 && client.getMouseY() < 463) {
                    if (Client.backDialogID != -1)
                        client.buildInterfaceMenu(20, RSInterface.interfaceCache[Client.backDialogID], client.getMouseX(), 358, client.getMouseY(), 0);
                    else if (client.getMouseY() < 463 && client.getMouseX() < 490)
                        buildChatAreaMenu(client.getMouseY() - 338);
                }
            } else {
                if (client.getMouseX() > 0 && client.getMouseY() > Client.canvasHeight - 165 && client.getMouseX() < 490 && client.getMouseY() < Client.canvasHeight - 40) {
                    if (Client.backDialogID != -1)
                        client.buildInterfaceMenu(20, RSInterface.interfaceCache[Client.backDialogID], client.getMouseX(),
                                Client.canvasHeight - 145, client.getMouseY(), 0);
                    else if (client.getMouseY() < Client.canvasHeight - 40 && client.getMouseX() < 490)
                        buildChatAreaMenu(client.getMouseY() - (Client.canvasHeight - 165));
                }
            }
            if (Client.backDialogID != -1 && client.anInt886 != client.anInt1039) {
                Client.inputTaken = true;
                client.anInt1039 = client.anInt886;
            }
            if (Client.backDialogID != -1 && client.anInt1315 != client.anInt1500) {
                Client.inputTaken = true;
                client.anInt1500 = client.anInt1315;
            }
            /* Enable custom right click areas */
            if (client.anInt886 != client.anInt1026)
                client.anInt1026 = client.anInt886;
            client.anInt886 = 0;

            client.rightClickChatButtons();
            processMinimapActions();
            boolean finishedSorting = true;
            for (int menuIndex = 0; menuIndex < getMenuOptionCount() - 1; ++menuIndex) {
                if (getMenuOpcodes()[menuIndex] < 1000 && getMenuOpcodes()[menuIndex + 1] > 1000) {
                    sortMenuEntries(menuIndex, menuIndex + 1);
                    finishedSorting = false;
                }
            }
            if (finishedSorting && !isMenuOpen()) {
                client.getCallbacks().post(new PostMenuSort());
            }
    }

    // ========== drawHintMenu / drawStatMenu ==========

    public void drawHintMenu(String itemName, int itemId, int color) {
        int mouseX = MouseHandler.mouseX;
        int mouseY = MouseHandler.mouseY;
        if (Client.menuTargets[client.menuOptionsCount] != null) {
            if (Client.menuTargets[client.menuOptionsCount].contains("Walk")) {
                return;
            }
        }
        if (client.toolTip.contains("Walk") || client.toolTip.contains("World") || !client.toolTip.contains("W")) {
            return;
        }
        if (client.openInterfaceID != -1) {
            return;
        }
        if (client.isResized()) {
            if (MouseHandler.mouseY < Client.instance.getViewportHeight() - 450 && MouseHandler.mouseX < Client.instance.getViewportWidth() - 200) {
                return;
            }
            mouseX -= 100;
            mouseY -= 50;
        }

        if (client.controlIsDown) {
            drawStatMenu(itemName, itemId, color);
            return;
        }

        if (client.menuOptionsCount < 2 && client.itemSelected == 0 && client.spellSelected == 0) {
            return;
        }
        if (client.isMenuOpen) {
            return;
        }
        if (Client.tabID != 3) {
            return;
        }


        if (!client.isResized()) {
            if (MouseHandler.mouseY < 214 || MouseHandler.mouseX < 561) {
                return;
            }

            mouseY -= 158;

        }


        Rasterizer2D.drawBoxOutline(mouseX, mouseY + 5, 150, 36, 0x696969);
        Rasterizer2D.drawTransparentBox(mouseX + 1, mouseY + 6, 150, 37, 0x000000, 90);

        Client.instance.newSmallFont.drawBasicString(itemName, mouseX + 150 / (12 + Client.instance.newSmallFont.getTextWidth(itemName)) + 30, mouseY + 17, color, 1);
        Client.instance.newSmallFont.drawBasicString("Press CTRL to view the stats", mouseX + 4, mouseY + 35, color, 1);



    }

    public void drawStatMenu(String itemName, int itemId, int color) {
        if (client.menuOptionsCount < 2 && client.itemSelected == 0 && client.spellSelected == 0) {
            return;
        }
        if (Client.menuTargets[client.menuOptionsCount] != null) {
            if (Client.menuTargets[client.menuOptionsCount].contains("Walk")) {
                return;
            }
        }
        if (client.toolTip.contains("Walk") || client.toolTip.contains("World") || !client.toolTip.contains("W")) {
            return;
        }
        if (client.isMenuOpen) {
            return;
        }
        if (Client.tabID != 3) {
            return;
        }
        int mouseX = MouseHandler.mouseX;
        int mouseY = MouseHandler.mouseY;
        if (client.isResized()) {
            mouseX -= 100;
            mouseY -= 50;
        }
        if (!client.isResized()) {
            if (MouseHandler.mouseY < 214 || MouseHandler.mouseX < 561) {
                return;
            }
            mouseX -= 516;
            mouseY -= 158;
            if (MouseHandler.mouseX > 600 && MouseHandler.mouseX < 685) {
                mouseX -= 60;

            }
            if (MouseHandler.mouseX > 685) {
                mouseX -= 120;
            }
            if (MouseHandler.mouseY > 392) {
                mouseY -= 130;
            }
        }
        short stabAtk = 0;
        int slashAtk = 0;
        int crushAtk = 0;
        int magicAtk = 0;
        int rangedAtk = 0;

        int stabDef = 0;
        int slashDef = 0;
        int crushDef = 0;
        int magicDef = 0;
        int rangedDef = 0;

        int prayerBonus = 0;
        int strengthBonus = 0;

        Rasterizer2D.drawBoxOutline(mouseX, mouseY + 5, 150, 120, 0x696969);
        Rasterizer2D.drawTransparentBox(mouseX + 1, mouseY + 6, 150, 121, 0x000000, 90);

        Client.instance.newSmallFont.drawBasicString(itemName, mouseX + 150 / (12 + Client.instance.newSmallFont.getTextWidth(itemName)) + 30, mouseY + 17, color, 1);

        Client.instance.newSmallFont.drawBasicString("ATK", mouseX + 62, mouseY + 30, color, 1);
        Client.instance.newSmallFont.drawBasicString("DEF", mouseX + 112, mouseY + 30, color, 1);

        Client.instance.newSmallFont.drawBasicString("Stab", mouseX + 2, mouseY + 43, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(stabAtk), mouseX + 62, mouseY + 43, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(stabDef), mouseX + 112, mouseY + 43, color, 1);

        Client.instance.newSmallFont.drawBasicString("Slash", mouseX + 2, mouseY + 56, 0xFF00FF, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(slashAtk), mouseX + 62, mouseY + 56, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(slashDef), mouseX + 112, mouseY + 56, color, 1);


        Client.instance.newSmallFont.drawBasicString("Crush", mouseX + 2, mouseY + 69, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(crushAtk), mouseX + 62, mouseY + 69, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(crushDef), mouseX + 112, mouseY + 69, color, 1);


        Client.instance.newSmallFont.drawBasicString("Magic", mouseX + 2, mouseY + 80, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(magicAtk), mouseX + 62, mouseY + 80, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(magicDef), mouseX + 112, mouseY + 80, color, 1);


        Client.instance.newSmallFont.drawBasicString("Ranged", mouseX + 2, mouseY + 95, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(rangedAtk), mouseX + 62, mouseY + 95, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(rangedDef), mouseX + 112, mouseY + 95, color, 1);


        Client.instance.newSmallFont.drawBasicString("Strength", mouseX + 2, mouseY + 108, color, 1);
        Client.instance.newSmallFont.drawBasicString("Prayer", mouseX + 2, mouseY + 121, color, 1);

        Client.instance.newSmallFont.drawBasicString(Integer.toString(strengthBonus), mouseX + 112, mouseY + 108, color, 1);
        Client.instance.newSmallFont.drawBasicString(Integer.toString(prayerBonus), mouseX + 112, mouseY + 121, color, 1);


        Client.instance.newSmallFont.drawBasicString("Stab", mouseX + 2, mouseY + 43, color, 1);
        Client.instance.newSmallFont.drawBasicString("Slash", mouseX + 2, mouseY + 56, color, 1);
        Client.instance.newSmallFont.drawBasicString("Crush", mouseX + 2, mouseY + 69, color, 1);
        Client.instance.newSmallFont.drawBasicString("Magic", mouseX + 2, mouseY + 80, color, 1);
        Client.instance.newSmallFont.drawBasicString("Range", mouseX + 2, mouseY + 95, color, 1);
        Client.instance.newSmallFont.drawBasicString("Strength", mouseX + 2, mouseY + 108, color, 1);
        Client.instance.newSmallFont.drawBasicString("Prayer", mouseX + 2, mouseY + 121, color, 1);
    }

    // ========== menu() - main menu loop ==========

    final void menu() {
        boolean var1 = false;
        int var2;
        int var5;
        while (!var1) {
            var1 = true;
            for (var2 = 0; var2 < getMenuOptionCount() - 1; ++var2) {
                if (getMenuOpcodes()[var2] < 1000 && getMenuOpcodes()[var2 + 1] > 1000) {
                    sortMenuEntries(var2, var2 + 1);
                    var1 = false;
                }
            }
            if (var1 && !isMenuOpen()) {
                client.getCallbacks().post(new PostMenuSort());
            }
        }

        if (client.activeInterfaceType == 0) {

            int var19 = clickMode3;
            if (client.spellSelected == 1 && MouseHandler.saveClickX >= 516 && MouseHandler.saveClickY >= 160 && MouseHandler.saveClickX <= 765 && MouseHandler.saveClickY <= 205) {
                var19 = 0;
            }
            int var4;
            int var7;
            int var8;
            int var20;
            if (isMenuOpen()) {
                int var3;
                if (var19 != 1 && (client.isMouseCam() || var19 != 4)) {
                    var2 = client.getMouseX();
                    var3 = client.getMouseY();
                    //this checks if the mouse is contained in the menu area
                    boolean regmenuContainsMouse = (var2 >= getMenuX() - 10 && var2 <= getMenuX() + getMenuWidth() + 10 && var3 >= getMenuY() - 10 && var3 <= getMenuY() + getMenuHeight() + 10);
                    boolean submenuContainsMouse = (var2 >= getSubmenuX() - 10 && var2 <= getSubmenuX() + getSubmenuWidth() + 10 && var3 >= getSubmenuY() - 10 && var3 <= getSubmenuY() + getSubmenuHeight() + 10);
                    if (!regmenuContainsMouse && !submenuContainsMouse) {
                        client.isMenuOpen = false;
                        setSubmenuIdx(-1);
                        for (var8 = 0; var8 < client.getRootWidgetCount(); ++var8) {
                            //RESET
                        }
                    } else if (!submenuContainsMouse) {
                        setSubmenuIdx(-1);
                    }

                    if (isMenuOpen() && client.getMouseWheelRotation() != 0) {
                        if (regmenuContainsMouse && menuScrollMax > 0) {

                            setMenuScroll(menuScroll + client.getMouseWheelRotation());
                            if (menuScroll < 0) {
                                menuScroll = 0;
                            } else if (menuScroll > menuScrollMax) {
                                menuScroll = menuScrollMax;
                            }
                        }

                        if (submenuContainsMouse && submenuScrollMax > 0) {
                            setSubmenuScroll(submenuScroll + client.getMouseWheelRotation());
                            if (submenuScroll < 0) {
                                submenuScroll = 0;
                            } else if (submenuScroll > submenuScrollMax) {
                                submenuScroll = submenuScrollMax;
                            }
                        }
                    }
                }

                if (var19 == 1 || !client.isMouseCam() && var19 == 4) {
                    var7 = -1;

                    MenuEntry[] entries = getMenuEntries();
                    int rootMenuTotalCount = 0;
                    int submenuTotalCount = 0;
                    for (var8 = 0; var8 < entries.length; ++var8) {
                        MenuEntry e = entries[var8];
                        if (e.getParent() == null) {
                            rootMenuTotalCount++;
                        } else if (((RSRuneLiteMenuEntry) e.getParent()).getIdx() == getSubmenuIdx()) {
                            submenuTotalCount++;
                        }
                    }
                    int rootMenuCount = 0;
                    int submenuCount = 0;
                    for (var8 = 0; var8 < entries.length; ++var8) {
                        MenuEntry e = entries[var8];
                        if (e.getParent() == null) {
                            int rowY = (rootMenuTotalCount - 1 - rootMenuCount - getMenuScroll()) * 15 + getMenuY() + 31;
                            if (getMouseLastPressedX() > getMenuX() && getMouseLastPressedX() < getMenuWidth() + getMenuX() && getMouseLastPressedY() > rowY - 13 && getMouseLastPressedY() < rowY + 3) {
                                var7 = var8;
                            }
                            rootMenuCount++;
                        } else if (((RSRuneLiteMenuEntry) e.getParent()).getIdx() == getSubmenuIdx()) {
                            int rowY = (submenuTotalCount - 1 - submenuCount - getSubmenuScroll()) * 15 + getSubmenuY() + 31;
                            if (getMouseLastPressedX() > getSubmenuX() && getMouseLastPressedX() < getSubmenuWidth() + getSubmenuX() && getMouseLastPressedY() > rowY - 13 && getMouseLastPressedY() < rowY + 3) {
                                var7 = var8;
                            }
                            submenuCount++;
                        }
                    }

                    clickedIdx = var7;
                    int var11;
                    int var12;
                    int var16;
                    int var15;
                    if (var7 != -1 && var7 >= 0) {
                        processMenuActions(var7);
                    }

                    client.isMenuOpen = false;

                    for (var12 = 0; var12 < client.getRootWidgetCount(); ++var12) {
                        //RESRT WIDGETS
                    }
                    setSubmenuIdx(-1);
                }
            } else {
                var2 = getMenuOptionCount() - 1;
                if (var19 == 1 && client.menuOptionsCount > 0) {
                    long i1 = Client.menuOpcodes[client.menuOptionsCount - 1];
                    if (i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74
                            || i1 == 454 || i1 == 539 || i1 == 493 || i1 == 847 || i1 == 447
                            || i1 == 1125) {
                        int l1 = Client.menuArguments1[client.menuOptionsCount - 1];
                        int j2 = Client.menuArguments2[client.menuOptionsCount - 1];
                        RSInterface class9 = RSInterface.interfaceCache[j2];
                        if (class9.aBoolean259 || class9.aBoolean235) {
                            client.aBoolean1242 = false;
                            client.anInt989 = 0;
                            client.draggingItemInterfaceId = j2;
                            client.itemDraggingSlot = l1;
                            client.activeInterfaceType = 2;
                            client.anInt1087 = MouseHandler.saveClickX;
                            client.anInt1088 = MouseHandler.saveClickY;
                            if (RSInterface.interfaceCache[j2].parentID == client.openInterfaceID)
                                client.activeInterfaceType = 1;
                            if (RSInterface.interfaceCache[j2].parentID == Client.backDialogID)
                                client.activeInterfaceType = 3;
                            return;
                        }
                    }
                }

                if ((var19 == 1 || !client.isMouseCam() && var19 == 4) && shouldLeftClickOpenMenu()) {
                    var19 = 2;
                }

                if ((var19 == 1 || !client.isMouseCam() && var19 == 4) && getMenuOptionCount() > 0 && var2 >= 0) {
                    processMenuActions(var2);
                }

                if (var19 == 2 && getMenuOptionCount() > 0) {
                    this.openMenu(getMouseLastPressedX(), getMouseLastPressedY());
                }
                if (Client.loggedIn) {
                    client.processTabClick();
                    client.processMainScreenClick();

                    // processTabClick2();
                    client.processChatModeClick();
                    client.minimapHovers();
                    client.processChatModeClick();
                }
            }
        }
    }

    private boolean shouldLeftClickOpenMenu() {
        return false;
    }

    private int getMouseLastPressedY() {
        return MouseHandler.saveClickY;
    }

    private int getMouseLastPressedX() {
        return MouseHandler.saveClickX;
    }

    // ========== processMenuActions ==========

    void processMenuActions(int menuActionIndex) {
        if (menuActionIndex >= 0) {
            int param0 = Client.menuArguments1[menuActionIndex];
            int param1 = Client.menuArguments2[menuActionIndex];
            int opcode = Client.menuOpcodes[menuActionIndex];
            int id = Client.menuIdentifiers[menuActionIndex];
            int var5 = 0;
            String option = Client.menuActions[menuActionIndex];
            String target = Client.menuTargets[menuActionIndex];

            RSRuneLiteMenuEntry menuEntry = null;

            for (int i = getMenuOptionCount() - 1; i >= 0; --i) {
                if (getMenuOpcodes()[i] == opcode
                        && getMenuIdentifiers()[i] == id
                        && getMenuArguments1()[i] == param0
                        && getMenuArguments2()[i] == param1
                        && (option != null && option.equals(getMenuOptions()[i]))
                        && (target != null && target.equals(getMenuTargets()[i]))
                ) {
                    menuEntry = Client.rl$menuEntries[i];
                    break;
                }
            }

            boolean isTemp = false;
            if (getTempMenuAction() != null) {
                isTemp = getTempMenuAction().getOpcode() == opcode &&
                        getTempMenuAction().getIdentifier() == id &&
                        getTempMenuAction().getOption().equals(option) &&
                        getTempMenuAction().getTarget().equals(target) &&
                        getTempMenuAction().getParam0() == param0 &&
                        getTempMenuAction().getParam1() == param1;
            }

            if (menuEntry == null && isTemp) {
                int i;
                if (getMenuOptionCount() < 500) {
                    i = getMenuOptionCount();
                    setMenuOptionCount(getMenuOptionCount() + 1);
                } else {
                    i = 0;
                }

                getMenuOpcodes()[i] = opcode;
                getMenuIdentifiers()[i] = id;
                getMenuOptions()[i] = option;
                getMenuTargets()[i] = target;
                getMenuArguments1()[i] = param0;
                getMenuArguments2()[i] = param1;
                getMenuForceLeftClick()[i] = false;
                menuEntry = Client.rl$menuEntries[i];

                if (menuEntry == null) {
                    menuEntry = Client.rl$menuEntries[i] = newRuneliteMenuEntry(i);
                }
            }

            MenuOptionClicked event;
            if (menuEntry == null) {
                MenuEntry tmpEntry = createMenuEntry(option, target, id, opcode, param0, param1, -1, false);
                event = new MenuOptionClicked(tmpEntry);


            } else {

                log.info("Menu click op {} targ {} action {} id {} p0 {} p1 {}", option, target, opcode, id, param0, param1);
                event = new MenuOptionClicked(menuEntry);
                client.getCallbacks().post(event);

                if (menuEntry.getConsumer() != null) {
                    try {
                        menuEntry.getConsumer().accept(menuEntry);
                    } catch (Exception ex) {
                        client.getLogger().warn("exception in menu callback", ex);
                    }
                }

                if (event.isConsumed()) {
                    return;
                }
            }



            /*
             * The RuneScape client may deprioritize an action in the menu by incrementing the opcode with 2000,
             * undo it here so we can get the correct opcode
             */
            boolean decremented = false;
            if (opcode >= 2000) {
                decremented = true;
                opcode -= 2000;
            }

            if (printMenuActions) {
                log.info(
                        "|MenuAction|: MenuOption={} MenuTarget={} Id={} " +
                                "Opcode={}/{} " +
                                "Param0={} Param1={} CanvasX={} CanvasY={}",
                        event.getMenuOption(), event.getMenuTarget(), event.getId(),
                        event.getMenuAction(), opcode + (decremented ? 2000 : 0),
                        event.getParam0(), event.getParam1(), -1, -1
                );

                if (menuEntry != null) {
                    log.info(
                            "|MenuEntry|: Idx={} MenuOption={} " +
                                    "MenuTarget={} Id={} MenuAction={} " +
                                    "Param0={} Param1={} Consumer={}",
                            menuEntry.getIdx(), menuEntry.getOption(),
                            menuEntry.getTarget(), menuEntry.getIdentifier(), menuEntry.getType(),
                            menuEntry.getParam0(), menuEntry.getParam1(), menuEntry.getConsumer()
                    );
                }
            }

            doAction(menuActionIndex, event.getParam0(), event.getParam1(),
                    event.getMenuAction() == UNKNOWN ? opcode : event.getMenuAction().getId(),
                    event.getId(), -1, event.getMenuOption(), event.getMenuTarget(),
                    -1, -1);
        }
    }

    // ========== doAction (part 1) ==========

    private void doAction(int menuRow, int param0, int param1, int opcode, int id, int na, String option, String target, int canvasX, int canvasY) {
        log.info("doAction: menuRow: {} p0: {} p1: {} op: {} id: {} na: {} opt: {} tar: {} cX: {} cY: {}", menuRow, param0, param1, opcode, id, na, option, target, canvasX, canvasY);
        if (menuRow < 0)
            return;
        param1 = remapGroupStorageButton(param1);
        openMysteryBoxFallbackIfNeeded(param1, id, option);
        if (client.openInterfaceID == Bank.BANK_INTERFACE_ID) {
            int remappedSpriteButton = bankTabSpriteToActionButton(param1, option);
            if (remappedSpriteButton != -1) {
                Bank.handleButton(remappedSpriteButton);
                Client.stream.createFrame(185);
                Client.stream.writeWord(remappedSpriteButton);
                return;
            }
            int remappedBankButton = bankTabPreviewToOpenButton(param1);
            if (remappedBankButton != -1) {
                Bank.handleButton(remappedBankButton);
                Client.stream.createFrame(185);
                Client.stream.writeWord(remappedBankButton);
                return;
            }
        }
        if (option != null && option.equalsIgnoreCase("Select Preset")
                && ((param1 >= 21591 && param1 <= 21609) || (param1 >= 50418 && param1 <= 50637))) {
            Client.stream.createFrame(184);
            Client.stream.writeWord(param1);
            return;
        }
        if (Client.inputDialogState != 0 && Client.inputDialogState != 3) {
            Client.inputDialogState = 0;
            Client.inputTaken = true;
        }

        switch (param1) {
            case 42522:
                if (client.isResized()) {
                    client.setConfigButton(menuRow, true);
                    client.setConfigButton(23003, false);
                    client.setConfigButton(23005, false);
                    client.frameMode(false);
                }
                break;

            case 42523:
                if (!client.isResized()) {
                    client.setConfigButton(menuRow, true);
                    client.setConfigButton(23001, false);
                    client.setConfigButton(23005, false);
                    client.frameMode(true);
                }
                break;
        }

        if (opcode == 1100) {
            RSInterface button = RSInterface.interfaceCache[param1];
            button.setMenuVisible(button.isMenuVisible() ? false : true);
        }
        if (opcode == MenuAction.FACE_NORTH.getId()) {
            client.setNorth();
            return;
        }
        if (opcode == 474) {
            client.drawExperienceCounter = !client.drawExperienceCounter;
        }

        if (opcode == 8992) {
            String messageToSearch = client.lastViewedDropTable;
            if (messageToSearch == null) {
                return;
            }
            NPCDropInfo info = NPCDropInfo.getEntry(messageToSearch);
            if (info == null) {
                return;
            }
            Client.stream.createFrame(177);
            Client.stream.writeWord(info.npcIndex);
            return;
        }

        if (opcode == 475) {
            Client.stream.createFrame(185);
            Client.stream.writeWord(-1);
            client.experienceCounter = 0L;
        }

        if (opcode == 855) {//wiki search
            Client.inputTaken = true;
            Client.inputDialogState = 0;
            client.messagePromptRaised = true;
            client.promptInput = "";
            client.friendsListAction = 14;
            client.aString1121 = "Search the OSRS wiki (opens browser)";
        }

        if (opcode == 944) {//tele orb
            Client.stream.createFrame(185);
            Client.stream.writeWord(7000);
        }
        if (opcode == 945) {//tele orb lat teleport option
            Client.stream.createFrame(185);
            Client.stream.writeWord(7001);
        }
        if (opcode == 1501) { // special attack orb
            if (client.isInWilderness()) {
                client.pushMessage("@red@You cannot use the special attack orb in the Wilderness.", 0, "");
                return;
            }
            Client.stream.createFrame(209);
            Client.stream.writeWord(29138);
            return;
        }

        if (opcode == 713) {//Money pouch
            client.aString1121 = "Enter amount to withdraw";
            Client.stream.createFrame(185);
            Client.stream.writeWord(716);
        }

        if (opcode == 714) {//Money pouch
            Client.stream.createFrame(185);
            Client.stream.writeWord(714);
        }
        if (opcode == 715) {//Money pouch
            Client.stream.createFrame(185);
            Client.stream.writeWord(715);
        }


        if (opcode == 852) {
            client.launchURL("https://explv.github.io/");
        }
        if (opcode == 1850) {
            Client.stream.createFrame(185);
            Client.stream.writeWord(5100);
        }
        if (opcode == 769) {
            RSInterface d = RSInterface.interfaceCache[param1];
            RSInterface p = RSInterface.interfaceCache[id];
            if (!d.dropdown.isOpen()) {
                if (p.dropdownOpen != null) {
                    p.dropdownOpen.dropdown.setOpen(false);
                }
                p.dropdownOpen = d;
            } else {
                p.dropdownOpen = null;
            }
            d.dropdown.setOpen(!d.dropdown.isOpen());
        } else if (opcode == 770) {
            RSInterface d = RSInterface.interfaceCache[param1];
            RSInterface p = RSInterface.interfaceCache[id];
            if (param0 < 0 || param0 >= d.dropdown.getOptions().length)
                return;
            d.dropdown.setSelected(d.dropdown.getOptions()[param0]);
            d.dropdown.setOpen(false);
            d.dropdown.getMenuItem().select(param0, d);
            try {
                SettingsManager.saveSettings(Client.instance);
            } catch (IOException io) {
                io.printStackTrace();
            }
            p.dropdownOpen = null;
        }
        if (opcode == 850) {
            if (Client.tabInterfaceIDs[Client.tabID] == 17200) {
                Client.stream.createFrame(185);
                Client.stream.writeWord(5200 + param1);
            }
        }
        if (opcode == 661) {
            Client.stream.createFrame(232);
            Client.stream.method432(1);
            Client.stream.writeWord(id);
        }
        if (opcode == 662) {
            Client.stream.createFrame(232);
            Client.stream.method432(2);
            Client.stream.writeWord(id);
        }
        if (opcode == 663) {
            Client.stream.createFrame(232);
            Client.stream.method432(3);
            Client.stream.writeWord(id);
        }
        if (opcode == 664) {
            Client.stream.createFrame(232);
            Client.stream.method432(4);
            Client.stream.writeWord(id);
        }

        switch (opcode) {
            case 1500: // Toggle quick prayers
                client.prayClicked = !client.prayClicked;
                Client.stream.createFrame(185);
                Client.stream.writeWord(5000);
                break;

            case 1506: // Select quick prayers
                Client.stream.createFrame(185);
                Client.stream.writeWord(5001);
                client.setTab(5);
                break;
        }
        if (opcode == 1200) {
            Client.stream.createFrame(185);
            Client.stream.writeWord(param1);
            RSInterface item = RSInterface.interfaceCache[param1];
            RSInterface menu = RSInterface.interfaceCache[item.mOverInterToTrigger];
            menu.setMenuItem(item.getMenuItem());
            menu.setMenuVisible(false);
        }
        if (opcode >= 1700 && opcode <= 1710) {
            RSInterface button = RSInterface.get(param1);
            if (button.buttonListener != null)
                button.buttonListener.accept(param1);
            if (button.newButtonClicking) {
                Client.stream.createFrame(184);
                Client.stream.writeWord(param1);
                client.onRealButtonClick(param1);
            } else {
                Client.stream.createFrame(185);
                int offset = param1 + (param1 - 58030) * 10 + (opcode - 1700);
                Client.stream.writeWord(offset);
                if (client.openInterfaceID == GroupIronmanBank.BANK_INTERFACE_ID) {
                    GroupIronmanBank.handleButton(offset);
                } else {
                    Bank.handleButton(offset);
                }
            }
        }
        if (opcode == 300) {
            Client.stream.createFrame(141);
            Client.stream.method432(param0);
            Client.stream.writeWord(param1);
            Client.stream.method432(id);
            Client.stream.writeDWord(client.modifiableXValue);
        }
        if (opcode == 291) {
            Client.stream.createFrame(140);
            Client.stream.writeWord(param1);
            Client.stream.method433(id);
            Client.stream.method431(param0);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            RSInterface inventoryInterface = RSInterface.interfaceCache[param1];
            if (inventoryInterface != null && inventoryInterface.parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (inventoryInterface != null && inventoryInterface.parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 582) {
            Npc npc = client.npcs[id];
            if (npc != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, npc.pathY[0], false, npc.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(57);
                Client.stream.method432(client.anInt1285);
                Client.stream.method432(id);
                Client.stream.method431(client.anInt1283);
                Client.stream.method432(client.anInt1284);
            }
        }
        if (opcode == 234) {
            boolean flag1 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag1)
                flag1 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(236);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.writeWord(id);
            Client.stream.method431(param0 + Client.baseX);
        }
        if (opcode == 62 && client.clickObject(id, param1, param0)) {
            Client.stream.createFrame(192);
            Client.stream.writeWord(client.anInt1284);
            Client.stream.writeDWord(id);
            Client.stream.method433(param1 + Client.baseY);
            Client.stream.method431(client.anInt1283);
            Client.stream.method433(param0 + Client.baseX);
            Client.stream.writeWord(client.anInt1285);
        }
        if (opcode == 511) {
            boolean flag2 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag2)
                flag2 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(25);
            Client.stream.method431(client.anInt1284);
            Client.stream.method432(client.anInt1285);
            Client.stream.writeWord(id);
            Client.stream.method432(param1 + Client.baseY);
            Client.stream.method433(client.anInt1283);
            Client.stream.writeWord(param0 + Client.baseX);
        }
        if (opcode == 74) {
            Client.stream.createFrame(122);
            Client.stream.writeWord(param1);
            Client.stream.writeWord(param0);
            Client.stream.writeWord(id);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            RSInterface inventoryInterface = RSInterface.interfaceCache[param1];
            if (inventoryInterface != null && inventoryInterface.parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (inventoryInterface != null && inventoryInterface.parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;

            // Fallback for servers that do not send interface-open packets on inventory open.
            openMysteryBoxFallbackIfNeeded(param1, id, option);
        }
        doActionPart2(menuRow, param0, param1, opcode, id, na, option, target, canvasX, canvasY);
    }

    // ========== doAction (part 2) ==========

    private void doActionPart2(int menuRow, int param0, int param1, int opcode, int id, int na, String option, String target, int canvasX, int canvasY) {
        if (opcode == 315) {
            RSInterface class9 = RSInterface.interfaceCache[param1];
            boolean flag8 = true;
            if (class9.type == RSInterface.TYPE_CONFIG || class9.id == 50009) {
                class9.active = !class9.active;
            } else if (class9.type == RSInterface.TYPE_CONFIG_HOVER) {
                RSInterface.handleConfigHover(class9);
            }
            if (class9.contentType > 0)
                flag8 = client.promptUserForInput(class9);
            if (flag8) {
                SettingsTabWidget.settings(param1);
                switch (param1) {
                    case 41552:
                        Client.shiftDrop = RSInterface.interfaceCache[41552].active;
                        break;
                    case Keybinding.ESCAPE_CONFIG:
                        // Sync the checkbox visual state to the actual config flag
                        Configuration.escapeCloseInterface = RSInterface.interfaceCache[Keybinding.ESCAPE_CONFIG].active;
                        client.savePlayerData();
                        break;
                    case Keybinding.RESTORE_DEFAULT:
                        Keybinding.restoreDefault();
                        Keybinding.updateInterface();
                        client.savePlayerData();
                        break;
                    case 35603:
                        Client.inputTaken = true;
                        Client.inputDialogState = 3;
                        client.amountOrNameInput = "";
                        client.grandExchangeSearchScrollPostion = 0;
                        break;

                    case 23003:
                        Configuration.alwaysLeftClickAttack = !Configuration.alwaysLeftClickAttack;
                        client.updateSettings();
                        break;
                    case 23005:
                        Configuration.hideCombatOverlay = !Configuration.hideCombatOverlay;
                        client.updateSettings();
                        break;
                    case 37708:
                        if (client.coloredItemColor != 0xffffff) {
                            Client.inputTaken = true;
                            Client.inputDialogState = 0;
                            client.messagePromptRaised = true;
                            client.promptInput = "";
                            client.friendsListAction = 25;
                            client.aString1121 = "Enter an item name to set this color to. (Name must match exact)";
                        } else {
                            client.pushMessage("You must select a color first...", 0, "");
                        }
                        break;
                    case 37710:
                        Client.inputTaken = true;
                        Client.inputDialogState = 0;
                        client.messagePromptRaised = true;
                        client.promptInput = "";
                        client.friendsListAction = 26;
                        client.aString1121 = "Enter a minimum item value to display on the ground.";
                        break;
                    case 37706:
                        Robot robot2;
                        PointerInfo pointer2;
                        pointer2 = MouseInfo.getPointerInfo();
                        java.awt.Point coord2 = pointer2.getLocation();
                        try {
                            robot2 = new Robot();
                            coord2 = MouseInfo.getPointerInfo().getLocation();
                            Color color = robot2.getPixelColor((int) coord2.getX(), (int) coord2.getY());
                            String hex2 = String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
                            int hex3 = Integer.parseInt(hex2, 16);
                            client.coloredItemColor = hex3;
                            RSInterface.interfaceCache[37707].message = "Current color chosen!";
                            RSInterface.interfaceCache[37707].textColor = client.coloredItemColor;
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 62013:
                        RSInterface input = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId + 1];
                        RSInterface itemContainer = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId];
                        if (RSInterface.selectedItemInterfaceId <= 0) {
                            return;
                        }
                        if (input != null && itemContainer != null) {
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(input.message);
                            } catch (NumberFormatException nfe) {
                                client.pushMessage("The amount must be a non-negative numerical value.", 0, "");
                                break;
                            }
                            if (itemContainer.itemSearchSelectedId < 0) {
                                itemContainer.itemSearchSelectedId = 0;
                            }
                            if (itemContainer.itemSearchSelectedSlot < 0) {
                                itemContainer.itemSearchSelectedSlot = 0;
                            }
                            Client.stream.createFrame(124);
                            Client.stream.writeDWord(RSInterface.selectedItemInterfaceId);
                            Client.stream.writeDWord(itemContainer.itemSearchSelectedSlot);
                            Client.stream.writeDWord(itemContainer.itemSearchSelectedId - 1);
                            Client.stream.writeDWord(amount);
                        }
                        break;

                    case 32013:
                        RSInterface input2 = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId + 1];
                        RSInterface itemContainer2 = RSInterface.interfaceCache[RSInterface.selectedItemInterfaceId];
                        if (RSInterface.selectedItemInterfaceId <= 0) {
                            return;
                        }
                        if (input2 != null && itemContainer2 != null) {
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(input2.message);
                            } catch (NumberFormatException nfe) {
                                client.pushMessage("The amount must be a non-negative numerical value.", 0, "");
                                break;
                            }
                            if (itemContainer2.itemSearchSelectedId < 0) {
                                itemContainer2.itemSearchSelectedId = 0;
                            }
                            if (itemContainer2.itemSearchSelectedSlot < 0) {
                                itemContainer2.itemSearchSelectedSlot = 0;
                            }
                            Client.stream.createFrame(124);
                            Client.stream.writeDWord(RSInterface.selectedItemInterfaceId);
                            Client.stream.writeDWord(itemContainer2.itemSearchSelectedSlot);
                            Client.stream.writeDWord(itemContainer2.itemSearchSelectedId - 1);
                            Client.stream.writeDWord(amount);
                        }
                        break;
                    case 19144:
                        client.sendFrame248(15106, 3213);
                        client.resetAnimation(15106);
                        Client.inputTaken = true;
                        break;
                    default:
                        if (client.openInterfaceID == Bank.BANK_INTERFACE_ID) {
                            Bank.handleButton(param1);
                        }
                        if (RSInterface.get(param1) != null && RSInterface.get(param1).newButtonClicking) {
                            Client.stream.createFrame(184);
                            Client.stream.writeWord(param1);
                        } else {
                            RSInterface widget = RSInterface.get(param1);

                            if (widget.hasTooltip()) {
                                String tooltip = widget.tooltip.toLowerCase();
                                boolean isSpecialAttackButton = tooltip.contains("use") && tooltip.contains("special attack");
                                if (isSpecialAttackButton) {
                                    Client.stream.createFrame(209);
                                    Client.stream.writeWord(param1);
                                    return;
                                }
                            }

                            boolean legacyBankQuantityOnly = param1 >= 58066 && param1 <= 58075;
                            if (!legacyBankQuantityOnly) {
                                Client.stream.createFrame(185);
                                Client.stream.writeWord(param1);
                            }
                            switch (param1) {
                                case 58066:
                                case 58067:
                                    client.modifiableXValue = 1;
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(5386);
                                    break;
                                case 58068:
                                case 58069:
                                    client.modifiableXValue = 5;
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(5387);
                                    break;
                                case 58070:
                                case 58071:
                                    client.modifiableXValue = 10;
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(5388);
                                    break;
                                case 58072:
                                case 58073:
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(5389);
                                    break;
                                case 58074:
                                case 58075:
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(5390);
                                    break;
                            }
                            if (param1 == 47004) {
                                // Compatibility: some server builds listen for legacy spin button ids.
                                Client.stream.createFrame(185);
                                Client.stream.writeWord(183156);
                            }
                            switch (param1) {
                                case SettingsTabWidget.HIDE_LOCAL_PET_OPTIONS:
                                    SettingsTabWidget.toggleHidePetOption();
                                    break;
                            }
                        }

                        if (param1 >= 61101 && param1 <= 61200) {
                            int selected = param1 - 61101;
                            for (int ii = 0, slot = -1; ii < Js5List.getConfigSize(Js5ConfigType.ITEM) && slot < 100; ii++) {
                                ItemDefinition def = ItemDefinition.lookup(ii);

                                if (def.name == null || def.certTemplateID == ii - 1 || def.certID == ii - 1 || RSInterface.interfaceCache[61254].message.isEmpty()) {
                                    continue;
                                }

                                if (def.name.toLowerCase().contains(RSInterface.interfaceCache[61254].message.toLowerCase())) {
                                    slot++;
                                }

                                if (slot != selected) {
                                    continue;
                                }

                                long num = Long.valueOf(RSInterface.interfaceCache[61255].message.replaceAll(",", ""));

                                if (num > Integer.MAX_VALUE) {
                                    num = Integer.MAX_VALUE;
                                }

                                Client.stream.createFrame(149);
                                Client.stream.writeWord(def.id);
                                Client.stream.writeDWord((int) num);
                                Client.stream.writeUnsignedByte(client.variousSettings[1075]);
                                break;
                            }
                        }
                        break;
                }
            }
        }
        if (opcode == 561) {
            Player player = client.players[id];
            if (player != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, player.pathY[0], false,
                    player.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                client.anInt1188 += id;
                if (client.anInt1188 >= 90) {
                    Client.stream.createFrame(136);
                    client.anInt1188 = 0;
                }
                Client.stream.createFrame(128);
                Client.stream.writeWord(id);
            }
        }
        if (opcode == 745) {
            Client.stream.createFrame(8);
            Client.stream.writeDWord(id);
        }
        if (opcode == 20) {
            Npc class30_sub2_sub4_sub1_sub1_1 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_1 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_1.pathY[0],
                    false, class30_sub2_sub4_sub1_sub1_1.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(155);
                Client.stream.method431(id);
            }
        }
        if (opcode == 779) {
            Player class30_sub2_sub4_sub1_sub2_1 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_1 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_1.pathY[0],
                    false, class30_sub2_sub4_sub1_sub2_1.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(153);
                Client.stream.method431(id);
            }
        }
        if (opcode == 516) {
            if (!client.isMenuOpen) {
                client.scene.register_click(MouseHandler.saveClickY - 4, MouseHandler.saveClickX - 4, Client.instance.plane);
            } else {
                client.scene.register_click(param0 - 4, param1 - 4, Client.instance.plane);
            }
        }
        if (opcode == 1062) {
            client.anInt924 += Client.baseX;
            if (client.anInt924 >= 113) {
                Client.stream.createFrame(183);
                Client.stream.writeDWordBigEndian(0xe63271);
                client.anInt924 = 0;
            }
            client.clickObject(id, param1, param0);
            Client.stream.createFrame(228);
            Client.stream.writeDWord(id);
            Client.stream.method432(param1 + Client.baseY);
            Client.stream.writeWord(param0 + Client.baseX);
        }
        if (opcode == 679 && !client.continuedDialogue) {
            Client.stream.createFrame(40);
            Client.stream.writeWord(param1);
            client.continuedDialogue = true;
        }
        if (opcode == Autocast.AUTOCAST_MENU_ACTION_ID) {
            RSInterface button = RSInterface.get(param1);
            Client.stream.createFrame(5);
            Client.stream.writeWord(button.autocastSpellId);
            Client.stream.writeByte(button.autocastDefensive ? 1 : 0);
            if (Configuration.developerMode) {
                System.out.println("Autocast: " + button.autocastSpellId + ", defensive: " + button.autocastDefensive);
            }
        }
        if (opcode == 431) {
            Client.stream.createFrame(129);
            Client.stream.method432(param0);
            Client.stream.writeWord(param1);
            Client.stream.method432(id);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 337 || opcode == 42 || opcode == 792 || opcode == 322) {
            String s = Client.menuTargets[menuRow];
            int k1 = s.indexOf("@whi@");
            if (k1 != -1) {
                long l3 = StringUtils.longForName(s.substring(k1 + 5).trim());
                if (opcode == 337)
                    client.addFriend(l3);
                if (opcode == 42)
                    client.addIgnore(l3);
                if (opcode == 792)
                    client.delFriend(l3);
                if (opcode == 322)
                    client.delIgnore(l3);
            }
        }
        if (opcode == 1337) {
            client.inputString = "::placeholder-" + param0 + "-" + id;
            Client.stream.createFrame(103);
            Client.stream.writeWord(client.inputString.length() - 1);
            Client.stream.writeString(client.inputString.substring(2));
            client.inputString = "";
        }
        if (opcode == 53) {
            Client.stream.createFrame(135);
            Client.stream.method431(param0);
            Client.stream.method432(param1);
            Client.stream.method431(id);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 539) {
            Client.stream.createFrame(16);
            Client.stream.writeWord(id);
            Client.stream.method433(param0);
            Client.stream.method433(param1);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }

        if (opcode == 9111 || opcode == 9112 || opcode == 9115) {
            Broadcast bc = BroadcastManager.getCurrentBroadcast();
            if (bc == null) {
                System.err.println("No broadcast found for this msg..");
                return;
            }
            if (opcode == 9115) {
                BroadcastManager.removeIndex(bc.index);
                return;
            }
            Client.stream.createFrame(199);
            Client.stream.writeByte(bc.index);
            return;
        }

        doActionPart3(menuRow, param0, param1, opcode, id, na, option, target, canvasX, canvasY);
    }

    // ========== doAction (part 3) ==========

    private void doActionPart3(int menuRow, int param0, int param1, int opcode, int id, int na, String option, String target, int canvasX, int canvasY) {
        if (opcode == 484 || opcode == 6 || opcode == 9501) {
            String s1 = Client.menuTargets[menuRow];
            int l1 = s1.indexOf("@whi@");
            if (l1 != -1) {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = StringUtils.fixName(StringUtils.nameForLong(StringUtils.longForName(s1)));
                boolean flag9 = false;
                for (int j3 = 0; j3 < client.playerCount; j3++) {
                    Player class30_sub2_sub4_sub1_sub2_7 = client.players[client.playerIndices[j3]];
                    if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.displayName == null
                        || !class30_sub2_sub4_sub1_sub2_7.displayName.equalsIgnoreCase(s7))
                        continue;
                    if (opcode == 484) {
                        Client.stream.createFrame(39);
                        Client.stream.method431(client.playerIndices[j3]);
                    }
                    if (opcode == 6 || opcode == 9501) {
                        client.anInt1188 += id;
                        if (client.anInt1188 >= 90) {
                            Client.stream.createFrame(136);
                            client.anInt1188 = 0;
                        }
                        Client.stream.createFrame(128);
                        Client.stream.writeWord(client.playerIndices[j3]);
                    }
                    flag9 = true;
                    break;
                }

                if (!flag9)
                    client.pushMessage("Unable to find " + s7, 0, "");
            }
        }
        if (opcode == 870) {
            Client.stream.createFrame(53);
            Client.stream.writeWord(param0);
            Client.stream.method432(client.anInt1283);
            Client.stream.method433(id);
            Client.stream.writeWord(client.anInt1284);
            Client.stream.method431(client.anInt1285);
            Client.stream.writeWord(param1);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 847) {
            Client.stream.createFrame(87);
            Client.stream.method432(id);
            Client.stream.writeWord(param1);
            Client.stream.method432(param0);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 626) {
            RSInterface class9_1 = RSInterface.interfaceCache[param1];
            client.spellSelected = 1;
            client.spellID = class9_1.id;
            client.anInt1137 = param1;
            client.spellUsableOn = class9_1.spellUsableOn;
            System.out.println("Spell id=" + client.spellID);
            client.itemSelected = 0;
            Client.needDrawTabArea = true;
            client.spellID = class9_1.id;
            String s4 = class9_1.selectedActionName;
            if (s4.indexOf(" ") != -1)
                s4 = s4.substring(0, s4.indexOf(" "));
            String s8 = class9_1.selectedActionName;
            if (s8.indexOf(" ") != -1)
                s8 = s8.substring(s8.indexOf(" ") + 1);
            client.spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
            if (client.spellUsableOn == 16) {
                Client.needDrawTabArea = true;
                Client.tabID = 3;
                Client.tabAreaAltered = true;
            }
            return;
        }
        if (opcode == 104) {
            RSInterface class9_1 = RSInterface.interfaceCache[param1];
            client.spellID = class9_1.id;
            if (!client.autocast) {
                client.autocast = true;
                client.autocastId = class9_1.id;
                Client.stream.createFrame(185);
                Client.stream.writeWord(class9_1.id);
            } else if (client.autocastId == class9_1.id) {
                client.autocast = false;
                client.autocastId = 0;
                Client.stream.createFrame(185);
                Client.stream.writeWord(6666);
            } else if (client.autocastId != class9_1.id) {
                client.autocast = true;
                client.autocastId = class9_1.id;
                Client.stream.createFrame(185);
                Client.stream.writeWord(class9_1.id);
            }
        }
        if (opcode == 78) {
            Client.stream.createFrame(117);
            Client.stream.writeWord(param1);
            Client.stream.writeWord(id);
            Client.stream.method431(param0);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 27) {
            Player class30_sub2_sub4_sub1_sub2_2 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_2 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_2.pathY[0],
                    false, class30_sub2_sub4_sub1_sub2_2.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                client.anInt986 += id;
                if (client.anInt986 >= 54) {
                    Client.stream.createFrame(189);
                    Client.stream.writeUnsignedByte(234);
                    client.anInt986 = 0;
                }
                Client.stream.createFrame(73);
                Client.stream.method431(id);
            }
        }
        if (opcode == 213) {
            boolean flag3 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag3)
                flag3 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(79);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.writeWord(id);
            Client.stream.method432(param0 + Client.baseX);
        }
        if (opcode == 632) {
            Client.stream.createFrame(145);
            Client.stream.method432(param1);
            Client.stream.method432(param0);
            Client.stream.method432(id);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 1050) {
            if (!client.runClicked) {
                client.runClicked = true;
                Client.stream.createFrame(185);
                Client.stream.writeWord(152);
            } else {
                client.runClicked = false;
                Client.stream.createFrame(185);
                Client.stream.writeWord(152);
            }
        }
        if (opcode == 1004) {
            if (Client.tabInterfaceIDs[10] != -1) {
                Client.needDrawTabArea = true;
                client.setSidebarInterface(14, 2449);
                Client.tabID = 10;
                Client.tabAreaAltered = true;
            }
        }
        if (opcode == 1003) { client.clanChatMode = 2; Client.inputTaken = true; }
        if (opcode == 1002) { client.clanChatMode = 1; Client.inputTaken = true; }
        if (opcode == 1001) { client.clanChatMode = 0; Client.inputTaken = true; }
        if (opcode == 1000) { client.chatTypeView = 11; Client.inputTaken = true; }
        if (opcode == 999) { client.chatTypeView = 0; Client.inputTaken = true; }
        if (opcode == 998) { client.chatTypeView = 5; Client.inputTaken = true; }
        if (opcode == 1005) { client.chatTypeView = 12; Client.inputTaken = true; }
        if (opcode == 997) { client.publicChatMode = 3; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 996) { client.publicChatMode = 2; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 995) { client.publicChatMode = 1; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 994) { client.publicChatMode = 0; Client.inputTaken = true; }
        if (opcode == 993) { client.chatTypeView = 1; Client.inputTaken = true; }
        if (opcode == 1006) {
            for (int index = 0; index < client.chatMessages.length; index++) {
                if (client.chatMessages[index] != null) {
                    int type = client.chatTypes[index];
                    if (type == 3 || type == 7 || type == 5 || type == 6) {
                        client.chatMessages[index] = null;
                        client.chatTypes[index] = 0;
                        client.chatNames[index] = null;
                    }
                }
            }
            Client.inputTaken = true;
        }
        if (opcode == 992) { client.privateChatMode = 2; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 991) { client.privateChatMode = 1; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 990) { client.privateChatMode = 0; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 989) { client.chatTypeView = 2; Client.inputTaken = true; }
        if (opcode == 987) { client.tradeMode = 2; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 986) { client.tradeMode = 1; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 985) { client.tradeMode = 0; Client.stream.createFrame(95); Client.stream.writeUnsignedByte(client.publicChatMode); Client.stream.writeUnsignedByte(client.privateChatMode); Client.stream.writeUnsignedByte(client.tradeMode); Client.inputTaken = true; }
        if (opcode == 984) { client.chatTypeView = 3; Client.inputTaken = true; }
        if (opcode == 983) { client.duelMode = 2; Client.inputTaken = true; }
        if (opcode == 982) { client.duelMode = 1; Client.inputTaken = true; }
        if (opcode == 981) { client.duelMode = 0; Client.inputTaken = true; }
        if (opcode == 980) { client.chatTypeView = 4; Client.inputTaken = true; }

        doActionPart4(menuRow, param0, param1, opcode, id, na, option, target, canvasX, canvasY);
    }

    // ========== doAction (part 4) ==========

    private void doActionPart4(int menuRow, int param0, int param1, int opcode, int id, int na, String option, String target, int canvasX, int canvasY) {
        if (opcode == 493) {
            Client.stream.createFrame(75);
            Client.stream.method433(param1);
            Client.stream.method431(param0);
            Client.stream.writeWord(id);
            client.atInventoryLoopCycle = 0;
            client.atInventoryInterface = param1;
            client.atInventoryIndex = param0;
            client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID)
                client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID)
                client.atInventoryInterfaceType = 3;
        }
        if (opcode == 652) {
            boolean flag4 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag4)
                flag4 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(156);
            Client.stream.method432(param0 + Client.baseX);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.method433(id);
        }
        if (opcode == 17) {
            boolean flag5 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag5)
                flag5 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(181);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.writeWord(id);
            Client.stream.method431(param0 + Client.baseX);
            Client.stream.method432(client.anInt1137);
        }
        if (opcode == 647) {
            Client.stream.createFrame(213);
            Client.stream.writeWord(param1);
            Client.stream.writeWord(param0);
            switch (param1) {
                case 18304:
                    if (param0 == 0) {
                        Client.inputTaken = true;
                        Client.inputDialogState = 0;
                        client.messagePromptRaised = true;
                        client.promptInput = "";
                        client.friendsListAction = 8;
                        client.aString1121 = "Enter your clan chat title";
                    }
                    break;
            }
        }
        switch (param1) {
            case 48612:
                Client.inputTaken = true;
                Client.inputDialogState = 0;
                client.messagePromptRaised = true;
                client.promptInput = "";
                client.friendsListAction = 12;
                client.aString1121 = "Enter the name of the item you want to lookup";
                break;
            case 48615:
                Client.inputTaken = true;
                Client.inputDialogState = 0;
                client.messagePromptRaised = true;
                client.promptInput = "";
                client.friendsListAction = 13;
                client.aString1121 = "Enter the name of the player you want to lookup";
                break;
            case 41504:
                Client.inputTaken = true;
                Client.inputDialogState = 0;
                client.messagePromptRaised = true;
                client.promptInput = "";
                client.friendsListAction = 15;
                client.aString1121 = "Enter the name of the player you want to lookup";
                break;
            case 43720: case 43721: case 43722: case 43723: case 43724:
            case 43725: case 43726: case 43727: case 43728: case 43729:
            case 43730: case 43731: case 43732: case 43733: case 43734:
            case 43735: case 43736: case 43737: case 43738: case 43739:
            case 43740: case 43741:
                Client.inputTaken = true;
                Client.inputDialogState = 0;
                client.messagePromptRaised = true;
                client.promptInput = "";
                client.friendsListAction = 16;
                client.aString1121 = "Enter the name of the player you want to punish";
                break;
        }
        if (opcode == 646) {
            if (client.openInterfaceID == GroupIronmanBank.BANK_INTERFACE_ID) {
                GroupIronmanBank.handleButton(param1);
            } else {
                Bank.handleButton(param1);
            }
            Client.stream.createFrame(185);
            Client.stream.writeWord(param1);
            if (!client.clickConfigButton(param1)) {
                RSInterface class9_2 = RSInterface.interfaceCache[param1];
                if (!class9_2.ignoreConfigClicking) {
                    if (class9_2.scripts != null && class9_2.scripts[0][0] == 5) {
                        int i2 = class9_2.scripts[0][1];
                        if (client.variousSettings[i2] != class9_2.anIntArray212[0]) {
                            client.variousSettings[i2] = class9_2.anIntArray212[0];
                            client.updateVarp(i2);
                            Client.needDrawTabArea = true;
                        }
                    }
                    switch (param1) {
                        case 18129:
                            if (RSInterface.interfaceCache[18135].message.toLowerCase().contains("join")) {
                                Client.inputTaken = true;
                                Client.inputDialogState = 0;
                                client.messagePromptRaised = true;
                                client.promptInput = "";
                                client.friendsListAction = 6;
                                client.aString1121 = "Enter the name of the chat you wish to join";
                            } else {
                                client.sendString(0, "");
                            }
                            break;
                        case 18132:
                            client.openInterfaceID = 18300;
                            break;
                        case 18527:
                            Client.inputTaken = true;
                            Client.inputDialogState = 0;
                            client.messagePromptRaised = true;
                            client.promptInput = "";
                            client.friendsListAction = 9;
                            client.aString1121 = "Enter a name to add";
                            break;
                        case 18528:
                            Client.inputTaken = true;
                            Client.inputDialogState = 0;
                            client.messagePromptRaised = true;
                            client.promptInput = "";
                            client.friendsListAction = 10;
                            client.aString1121 = "Enter a name to add";
                            break;
                    }
                }
            }
        }
        if (opcode == 225) {
            Npc class30_sub2_sub4_sub1_sub1_2 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_2 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_2.pathY[0],
                    false, class30_sub2_sub4_sub1_sub1_2.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                client.anInt1226 += id;
                if (client.anInt1226 >= 85) {
                    Client.stream.createFrame(230);
                    Client.stream.writeUnsignedByte(239);
                    client.anInt1226 = 0;
                }
                Client.stream.createFrame(17);
                Client.stream.method433(id);
            }
        }
        if (opcode == 965) {
            Npc class30_sub2_sub4_sub1_sub1_3 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_3 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_3.pathY[0],
                    false, class30_sub2_sub4_sub1_sub1_3.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                client.anInt1134++;
                if (client.anInt1134 >= 96) {
                    Client.stream.createFrame(152);
                    Client.stream.writeUnsignedByte(88);
                    client.anInt1134 = 0;
                }
                Client.stream.createFrame(21);
                Client.stream.writeWord(id);
            }
        }
        if (opcode == 413) {
            Npc class30_sub2_sub4_sub1_sub1_4 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_4 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_4.pathY[0],
                    false, class30_sub2_sub4_sub1_sub1_4.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(131);
                Client.stream.method433(id);
                Client.stream.method432(client.anInt1137);
            }
        }
        if (opcode == 200) {
            if (client.openInterfaceID != 48599 && client.openInterfaceID != 48598 && client.openInterfaceID != 48600)
                client.clearTopInterfaces();
            else {
                Client.stream.createFrame(185);
                Client.stream.writeWord(15333);
            }
        }
        if (opcode == 201) {
            for (int index = 0; index < GrandExchange.grandExchangeItemBoxIds.length; index++)
                if (param1 == GrandExchange.grandExchangeItemBoxIds[index] + 1) {
                    client.openInterfaceID = GrandExchange.grandExchangeOfferStatusInterfaceIds[index];
                    // Notify server to populate offer status texts
                    Client.stream.createFrame(185);
                    Client.stream.writeWord(param1);
                }
            switch (param1) {
                case 35557:
                case 35705:
                    client.openInterfaceID = 35000;
                    Client.stream.createFrame(185);
                    Client.stream.writeWord(param1);
                    break;
                case 25705:
                case 25557:
                    client.openInterfaceID = 25000;
                    break;
            }
        }
        if (opcode == 1025) {
            Npc class30_sub2_sub4_sub1_sub1_5 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_5 != null) {
                NpcDefinition entityDef = class30_sub2_sub4_sub1_sub1_5.desc;
                if (entityDef != null) {
                    if (entityDef.configs != null)
                        entityDef = entityDef.morph();
                    if (entityDef != null) {
                        String s9 = null;
                        if (entityDef.description != null)
                            s9 = new String(entityDef.description);
                        else if (entityDef.combatLevel == 0)
                            s9 = "It's a " + entityDef.name + ".";
                        if (s9 != null) {
                            client.pushMessage(s9, 0, "");
                        }
                    }
                    if (entityDef.combatLevel > 0) {
                        Client.stream.createFrame(137);
                        Client.stream.writeWord((int) id);
                    }
                }
            }
        }
        if (opcode == 900) {
            client.clickObject(id, param1, param0);
            Client.stream.createFrame(252);
            Client.stream.writeDWord(id);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.method432(param0 + Client.baseX);
        }
        if (opcode == 412) {
            Npc class30_sub2_sub4_sub1_sub1_6 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_6 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_6.pathY[0],
                    false, class30_sub2_sub4_sub1_sub1_6.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(72);
                Client.stream.method432(id);
            }
        }
        if (opcode == 365) {
            Player class30_sub2_sub4_sub1_sub2_3 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_3 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_3.pathY[0],
                    false, class30_sub2_sub4_sub1_sub2_3.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(249);
                Client.stream.method432(id);
                Client.stream.method431(client.anInt1137);
            }
        }
        if (opcode == 729) {
            Player class30_sub2_sub4_sub1_sub2_4 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_4 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_4.pathY[0],
                    false, class30_sub2_sub4_sub1_sub2_4.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(39);
                Client.stream.method431(id);
            }
        }
        if (opcode == 577) {
            Player class30_sub2_sub4_sub1_sub2_5 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_5 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_5.pathY[0],
                    false, class30_sub2_sub4_sub1_sub2_5.pathX[0]);
                client.crossX = MouseHandler.saveClickX;
                client.crossY = MouseHandler.saveClickY;
                client.crossType = 2;
                client.crossIndex = 0;
                Client.stream.createFrame(139);
                Client.stream.method431(id);
            }
        }
        if (opcode == 956 && client.clickObject(id, param1, param0)) {
            Client.stream.createFrame(35);
            Client.stream.method431(param0 + Client.baseX);
            Client.stream.method432(client.anInt1137);
            Client.stream.method432(param1 + Client.baseY);
            Client.stream.writeDWord(id);
        }
        if (opcode == 567) {
            boolean flag6 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag6)
                flag6 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX;
            client.crossY = MouseHandler.saveClickY;
            client.crossType = 2;
            client.crossIndex = 0;
            Client.stream.createFrame(23);
            Client.stream.method431(param1 + Client.baseY);
            Client.stream.method431(id);
            Client.stream.method431(param0 + Client.baseX);
        }
        if (opcode == 867) {
            if ((id & 3) == 0) client.anInt1175++;
            if (client.anInt1175 >= 59) { Client.stream.createFrame(200); Client.stream.createFrame(201); Client.stream.writeWord(25501); client.anInt1175 = 0; }
            Client.stream.createFrame(43); Client.stream.method431(param1); Client.stream.method432(id); Client.stream.method432(param0);
            client.atInventoryLoopCycle = 0; client.atInventoryInterface = param1; client.atInventoryIndex = param0; client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID) client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID) client.atInventoryInterfaceType = 3;
        }
        if (opcode == 543) {
            Client.stream.createFrame(237); Client.stream.writeWord(param0); Client.stream.method432(id); Client.stream.writeWord(param1); Client.stream.method432(client.anInt1137);
            client.atInventoryLoopCycle = 0; client.atInventoryInterface = param1; client.atInventoryIndex = param0; client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID) client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID) client.atInventoryInterfaceType = 3;
        }
        if (opcode == 606) {
            String s2 = Client.menuTargets[menuRow]; int j2 = s2.indexOf("@whi@");
            if (j2 != -1)
                if (client.openInterfaceID == -1) {
                    client.clearTopInterfaces(); client.reportAbuseInput = s2.substring(j2 + 5).trim(); client.canMute = false;
                    for (int i3 = 0; i3 < RSInterface.interfaceCache.length; i3++) {
                        if (RSInterface.interfaceCache[i3] == null || RSInterface.interfaceCache[i3].contentType != 600) continue;
                        client.reportAbuseInterfaceID = client.openInterfaceID = RSInterface.interfaceCache[i3].parentID; break;
                    }
                } else { client.pushMessage("Please close the interface you have open before using 'report abuse'", 0, ""); }
        }
        if (opcode == 491) {
            Player class30_sub2_sub4_sub1_sub2_6 = client.players[id];
            if (class30_sub2_sub4_sub1_sub2_6 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub2_6.pathY[0], false, class30_sub2_sub4_sub1_sub2_6.pathX[0]);
                client.crossX = MouseHandler.saveClickX; client.crossY = MouseHandler.saveClickY; client.crossType = 2; client.crossIndex = 0;
                Client.stream.createFrame(14); Client.stream.method432(client.anInt1284); Client.stream.writeWord(id); Client.stream.writeWord(client.anInt1285); Client.stream.method431(client.anInt1283);
            }
        }
        if (opcode == 639) {
            String s3 = Client.menuTargets[menuRow]; int k2 = s3.indexOf("@whi@");
            if (k2 != -1) {
                long l4 = StringUtils.longForName(s3.substring(k2 + 5).trim()); int k3 = -1;
                for (int i4 = 0; i4 < client.friendsCount; i4++) { if (client.friendsListAsLongs[i4] != l4) continue; k3 = i4; break; }
                if (k3 != -1 && client.friendsNodeIDs[k3] > 0) {
                    Client.inputTaken = true; Client.inputDialogState = 0; client.messagePromptRaised = true; client.promptInput = ""; client.friendsListAction = 3;
                    client.aLong953 = client.friendsListAsLongs[k3]; client.aString1121 = "Enter message to send to " + client.friendsList[k3];
                }
            }
        }
        if (opcode == 1052) { Client.stream.createFrame(185); Client.stream.writeWord(154); }
        if (opcode == 1004) {
            if (Client.tabInterfaceIDs[14] != -1) { Client.needDrawTabArea = true; Client.tabID = 14; Client.tabAreaAltered = true; }
        }
        if (opcode == 454) {
            Client.stream.createFrame(41); Client.stream.writeWord(id); Client.stream.method432(param0); Client.stream.method432(param1);
            client.atInventoryLoopCycle = 0; client.atInventoryInterface = param1; client.atInventoryIndex = param0; client.atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[param1].parentID == client.openInterfaceID) client.atInventoryInterfaceType = 1;
            if (RSInterface.interfaceCache[param1].parentID == Client.backDialogID) client.atInventoryInterfaceType = 3;
        }
        if (opcode == 696) { client.viewRotation = 0; client.camAngleX = 120; }
        if (opcode == 478) {
            Npc class30_sub2_sub4_sub1_sub1_7 = client.npcs[id];
            if (class30_sub2_sub4_sub1_sub1_7 != null) {
                client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, class30_sub2_sub4_sub1_sub1_7.pathY[0], false, class30_sub2_sub4_sub1_sub1_7.pathX[0]);
                client.crossX = MouseHandler.saveClickX; client.crossY = MouseHandler.saveClickY; client.crossType = 2; client.crossIndex = 0;
                if ((id & 3) == 0) client.anInt1155++;
                if (client.anInt1155 >= 53) { Client.stream.createFrame(85); Client.stream.writeUnsignedByte(66); client.anInt1155 = 0; }
                Client.stream.createFrame(18); Client.stream.method431(id);
            }
        }
        if (opcode == 113) { client.clickObject(id, param1, param0); Client.stream.createFrame(70); Client.stream.method431(param0 + Client.baseX); Client.stream.writeWord(param1 + Client.baseY); Client.stream.writeDWord(id); }
        if (opcode == 872) { client.clickObject(id, param1, param0); Client.stream.createFrame(234); Client.stream.method433(param0 + Client.baseX); Client.stream.writeDWord(id); Client.stream.method433(param1 + Client.baseY); }
        if (opcode == 502) { client.clickObject(id, param1, param0); Client.stream.createFrame(132); Client.stream.method433(param0 + Client.baseX); Client.stream.writeDWord(id); Client.stream.method432(param1 + Client.baseY); }
        if (opcode == 1126 || opcode == 1125) {
            RSInterface class9_4 = RSInterface.interfaceCache[param1];
            if (class9_4 != null) { Client.stream.createFrame(134); Client.stream.writeDWord(id); Client.stream.writeDWord(class9_4.inventoryAmounts[param0]); }
        }
        if (opcode == 1130) {
            RSInterface class9_4 = RSInterface.interfaceCache[param1];
            if (class9_4 != null) { class9_4.itemSearchSelectedId = class9_4.inventoryItemId[param0]; class9_4.itemSearchSelectedSlot = param0; RSInterface.selectedItemInterfaceId = class9_4.id; }
        }
        if (opcode == 169) {
            if (param1 == 58002) { client.anInt913 = 0; client.variousSettings[304] = 1; client.variousSettings[305] = 0; }
            else if (param1 == 18929) { client.anInt913 = 1; client.variousSettings[304] = 0; client.variousSettings[305] = 1; }
            else {
                if (client.openInterfaceID == GroupIronmanBank.BANK_INTERFACE_ID) {
                    GroupIronmanBank.handleButton(param1);
                }
                RSInterface button = RSInterface.get(param1);
                if (button.newButtonClicking) { Client.stream.createFrame(184); Client.stream.writeWord(param1); client.onRealButtonClick(param1); }
                else { Client.stream.createFrame(185); Client.stream.writeWord(param1); if (button.buttonListener != null) button.buttonListener.accept(param1);
                    if (Client.clientData) { client.pushMessage("Client side button id: " + param1); } }
                RSInterface class9_3 = RSInterface.interfaceCache[param1];
                if (!class9_3.ignoreConfigClicking) {
                    if (class9_3.scripts != null && class9_3.scripts[0][0] == 5) { int l2 = class9_3.scripts[0][1]; client.variousSettings[l2] = 1 - client.variousSettings[l2]; client.updateVarp(l2); Client.needDrawTabArea = true; }
                }
            }
        }
        if (opcode == 447) {
            client.itemSelected = 1; client.anInt1283 = param0; client.anInt1284 = param1; client.anInt1285 = id;
            client.selectedItemName = ItemDefinition.lookup(id).name; client.spellSelected = 0; Client.needDrawTabArea = true; return;
        }
        if (opcode == 1226) {
            int objectId = id; ObjectDefinition objectDef = ObjectDefinition.lookup(objectId); String s10;
            if (objectDef.description != null) s10 = new String(objectDef.description); else s10 = "It's a " + objectDef.name + ".";
            client.pushMessage(s10, 0, "");
            Client.stream.createFrame(146); Client.stream.method433(param0 + Client.baseX); Client.stream.writeWord(objectId); Client.stream.method432(param1 + Client.baseY);
        }
        if (opcode == 244) {
            boolean flag7 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 0, 0, 0, 0, param1, false, param0);
            if (!flag7) flag7 = client.doWalkTo(2, Client.localPlayer.pathX[0], Client.localPlayer.pathY[0], 0, 1, 0, 1, 0, param1, false, param0);
            client.crossX = MouseHandler.saveClickX; client.crossY = MouseHandler.saveClickY; client.crossType = 2; client.crossIndex = 0;
            Client.stream.createFrame(253); Client.stream.method431(param0 + Client.baseX); Client.stream.method433(param1 + Client.baseY); Client.stream.method432(id);
        }
        if (opcode == 1448) {
            ItemDefinition itemDef_1 = ItemDefinition.lookup(id); String s6; s6 = "It's a " + itemDef_1.name + "."; client.pushMessage(s6, 0, "");
        }
        client.itemSelected = 0;
        client.spellSelected = 0;
        Client.needDrawTabArea = true;
    }

    // ========== openMenu ==========

    final void openMenu(int x, int y) {
        final MenuOpened event = new MenuOpened();
        event.setMenuEntries(getMenuEntries());
        client.callbacks.post(event);

        if (event.isModified()) {
            setMenuEntries(event.getMenuEntries());
        }

        int tempWidth = client.getFontBold12().getTextWidth("Choose Option");

        int var4;
        int var5;
        int realCount = 0;
        for (var4 = 0; var4 < getMenuOptionCount(); ++var4) {
            MenuEntry tempMenuEntry = getMenuEntries()[var4];
            if (tempMenuEntry.getParent() != null) {
                continue;
            }
            realCount++;
            String s = tempMenuEntry.getOption();
            if (!tempMenuEntry.getTarget().isEmpty()) {
                s = s + " " + tempMenuEntry.getTarget();
            }
            if (tempMenuEntry.getType() == MenuAction.RUNELITE_SUBMENU) {
                s = s + " <col=ffffff><gt>";
            }
            tempWidth = Math.max(client.getFontBold12().getTextWidth(s), tempWidth);
        }

        tempWidth += 8;
        var4 = realCount * 15 + 22;
        var5 = x - tempWidth / 2;
        if (var5 + tempWidth > client.getCanvasWidth()) {
            var5 = client.getCanvasWidth() - tempWidth;
        }
        if (var5 < 0) {
            var5 = 0;
        }
        int var6 = y;
        if (var4 + y > client.getCanvasHeight()) {
            var6 = client.getCanvasHeight() - var4;
        }
        if (var6 < 0) {
            var6 = 0;
        }

        setMenuX(var5);
        setMenuY(var6);
        setMenuWidth(tempWidth);
        setMenuHeight(realCount * 15 + 22);

        client.isMenuOpen = true;

        setMenuScroll(0);
        menuScrollMax = 0;
        if (var4 > client.getCanvasHeight()) {
            menuScrollMax = (var4 - client.getCanvasHeight() + 14) / 15;
        }
    }

    // ========== Menu Rendering ==========

    public void draw2010Menu(int alpha) {
        int x = getMenuX(); int y = getMenuY(); int w = getMenuWidth(); int h = getMenuHeight();
        draw2010MenuNest(null, x, y, w, h, getMenuScroll(), alpha);
    }

    private void draw2010MenuNest(MenuEntry parent, int x, int y, int w, int h, int scroll, int alpha) {
        client.rasterizerDrawHorizontalLineAlpha(x + 2, y, w - 4, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawHorizontalLineAlpha(x + 2, y + h - 1, w - 4, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawVerticalLineAlpha(x, y + 2, h - 4, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawVerticalLineAlpha(x + w - 1, y + 2, h - 4, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawRectangleAlpha(x + 1, y + 5, w - 2, h - 6, MENU_PADDING_2010, alpha);
        client.rasterizerDrawHorizontalLineAlpha(x + 1, y + 17, w - 2, MENU_PADDING_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + 2, y + h - 3, 0, MENU_PADDING_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + w - 3, y + h - 3, 0, MENU_PADDING_2010, alpha);
        client.rasterizerDrawGradientAlpha(x + 2, y + 1, w - 4, 16, MENU_HEADER_GRADIENT_TOP_2010, MENU_HEADER_GRADIENT_BOTTOM_2010, alpha, alpha);
        client.rasterizerFillRectangleAlpha(x + 1, y + 1, 2, 4, MENU_PADDING_2010, alpha);
        client.rasterizerFillRectangleAlpha(x + w - 3, y + 1, 2, 4, MENU_PADDING_2010, alpha);
        client.rasterizerDrawHorizontalLineAlpha(x + 2, y + 18, w - 4, MENU_BORDER_INNER_2010, alpha);
        client.rasterizerDrawHorizontalLineAlpha(x + 3, y + h - 3, w - 6, MENU_BORDER_INNER_2010, alpha);
        client.rasterizerDrawVerticalLineAlpha(x + 2, y + 18, h - 21, MENU_BORDER_INNER_2010, alpha);
        client.rasterizerDrawVerticalLineAlpha(x + w - 3, y + 18, h - 21, MENU_BORDER_INNER_2010, alpha);
        client.rasterizerFillRectangleAlpha(x + 3, y + 19, w - 6, h - 22, MENU_BACKGROUND_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + 1, y + 1, 0, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + w - 2, y + 1, 0, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + 1, y + h - 2, 0, MENU_BORDER_OUTER_2010, alpha);
        client.rasterizerDrawCircleAlpha(x + w - 2, y + h - 2, 0, MENU_BORDER_OUTER_2010, alpha);

        RSFontOSRS font = (RSFontOSRS) client.getFontBold12();
        String optionStr = parent == null ? "Choose Option" : parent.getTarget();
        font.drawTextLeftAligned(optionStr, x + 3, y + 14, MENU_TEXT_2010, -1);

        MenuEntry[] entries = getMenuEntries();
        int mouseX = client.getMouseX();
        int mouseY = client.getMouseY();
        int realCount = 0;
        for (int i = 0; i < entries.length; i++) { if (entries[i].getParent() == parent) { realCount++; } }
        int realIdx = 0;
        for (int i = 0; i < entries.length; i++) {
            MenuEntry entry = entries[i];
            if (entry.getParent() == parent && realCount - 1 - realIdx >= scroll) {
                int rowY = y + (realCount - 1 - realIdx - scroll) * 15 + 31;
                realIdx++;
                String s = entry.getOption();
                if (!entry.getTarget().isEmpty()) { s = s + " " + entry.getTarget(); }
                if (entry.getType() == MenuAction.RUNELITE_SUBMENU) { s = s + " <col=ffffff><gt>"; }
                font.drawTextLeftAligned(s, x + 3, rowY, MENU_TEXT_2010, -1);
                if (mouseX > x && mouseX < w + x && mouseY > rowY - 13 && mouseY < rowY + 3) {
                    client.rasterizerFillRectangleAlpha(x + 3, rowY - 12, w - 6, 15, 0xffffff, 80);
                    if (entry.getType() == MenuAction.RUNELITE_SUBMENU) { setSubmenuIdx(i); initSubmenu(x + w, rowY - 31); }
                }
                if (getSubmenuIdx() == i) {
                    draw2010MenuNest(entry, getSubmenuX(), getSubmenuY(), getSubmenuWidth(), getSubmenuHeight(), getSubmenuScroll(), alpha);
                }
            }
        }
    }

    void initSubmenu(int x, int y) {
        if (getSubmenuIdx() != -1) {
            RSRuneLiteMenuEntry rootEntry = (RSRuneLiteMenuEntry) getMenuEntries()[getSubmenuIdx()];
            String s = rootEntry.getOption();
            if (!rootEntry.getTarget().isEmpty()) { s = s + " " + rootEntry.getTarget(); }
            if (rootEntry.getType() == MenuAction.RUNELITE_SUBMENU) { s = s + " <col=ffffff><gt>"; }
            int tempWidth = client.getFontBold12().getTextWidth(s);
            int realCount = 0;
            for (MenuEntry me : getMenuEntries()) {
                if (me.getParent() == null) continue;
                if (((RSRuneLiteMenuEntry) me.getParent()).getIdx() == rootEntry.getIdx()) {
                    realCount++;
                    tempWidth = Math.max(client.getFontBold12().getTextWidth(
                            (me.getTarget().length() > 0 ? me.getOption() + " " + me.getTarget() : me.getOption())
                    ), tempWidth);
                }
            }
            tempWidth += 8;
            int var4 = realCount * 15 + 22;
            int var5 = x;
            if (var5 + tempWidth > client.getCanvasWidth()) { var5 = x - tempWidth - getMenuWidth(); }
            if (var5 < 0) { var5 = 0; }
            int var6 = y;
            if (var4 + y > client.getCanvasHeight()) { var6 = client.getCanvasHeight() - var4; }
            if (var6 < 0) { var6 = 0; }
            setSubmenuX(var5); setSubmenuY(var6); setSubmenuWidth(tempWidth); setSubmenuHeight(realCount * 15 + 22);
            setSubmenuScroll(0); setSubmenuScrollMax(0);
            if (var4 > client.getCanvasHeight()) { setSubmenuScrollMax(var4 - client.getCanvasHeight()); }
        }
    }

    public void drawOriginalMenu(int alpha) {
        int x = getMenuX(); int y = getMenuY(); int w = getMenuWidth(); int h = getMenuHeight();
        drawOriginalMenuNest(null, x, y, w, h, getMenuScroll(), alpha);
    }

    private void drawOriginalMenuNest(MenuEntry parent, int x, int y, int w, int h, int scroll, int alpha) {
        if (alpha != 255) {
            client.rasterizerFillRectangleAlpha(x, y, w, h, ORIGINAL_BG, alpha);
            client.rasterizerFillRectangleAlpha(x + 1, y + 1, w - 2, 16, 0, alpha);
            client.rasterizerDrawRectangleAlpha(x + 1, y + 18, w - 2, h - 19, 0, alpha);
        } else {
            client.rasterizerFillRectangle(x, y, w, h, ORIGINAL_BG);
            client.rasterizerFillRectangle(x + 1, y + 1, w - 2, 16, 0);
            client.rasterizerDrawRectangle(x + 1, y + 18, w - 2, h - 19, 0);
        }

        RSFontOSRS font = (RSFontOSRS) client.getFontBold12();
        String optionStr = parent == null ? "Choose Option" : parent.getTarget();
        font.drawTextLeftAligned(optionStr, x + 3, y + 14, ORIGINAL_BG, -1);

        MenuEntry[] entries = getMenuEntries();
        int mouseX = client.getMouseX();
        int mouseY = client.getMouseY();
        int realCount = 0;
        for (int i = 0; i < entries.length; i++) { if (entries[i].getParent() == parent) { realCount++; } }
        int realIdx = 0;
        for (int i = 0; i < entries.length; i++) {
            MenuEntry entry = entries[i];
            if (entry.getParent() == parent && realCount - 1 - realIdx >= scroll) {
                int rowY = y + (realCount - 1 - realIdx - scroll) * 15 + 31;
                realIdx++;
                String s = entry.getOption();
                if (!entry.getTarget().isEmpty()) { s = s + " " + entry.getTarget(); }
                if (entry.getType() == MenuAction.RUNELITE_SUBMENU) { s = s + " <col=ffffff><gt>"; }
                int highlight = 0xFFFFFF;
                if (mouseX > x && mouseX < w + x && mouseY > rowY - 13 && mouseY < rowY + 3) {
                    highlight = 0xFFFF00;
                    if (entry.getType() == MenuAction.RUNELITE_SUBMENU) { setSubmenuIdx(i); initSubmenu(x + w, rowY - 31); }
                }
                font.drawTextLeftAligned(s, x + 3, rowY, highlight, 0);
                if (getSubmenuIdx() == i) {
                    drawOriginalMenuNest(entry, getSubmenuX(), getSubmenuY(), getSubmenuWidth(), getSubmenuHeight(), getSubmenuScroll(), alpha);
                }
            }
        }
    }

    // ========== RuneLite Menu Entry API ==========

    public static RSRuneLiteMenuEntry newBareRuneliteMenuEntry() {
        return new RuneLiteMenuEntry();
    }

    public static RSRuneLiteMenuEntry newRuneliteMenuEntry(final int idx) {
        return new RuneLiteMenuEntry(idx);
    }

    public MenuEntry createMenuEntry(int idx) {
        if (getMenuOptionCount() >= 500) {
            throw new IllegalStateException();
        } else {
            if (idx < 0) {
                idx = getMenuOptionCount() + idx + 1;
                if (idx < 0) {
                    throw new IllegalArgumentException();
                }
            }

            RSRuneLiteMenuEntry menuEntry;
            if (idx < getMenuOptionCount()) {
                RSRuneLiteMenuEntry tmpEntry = Client.rl$menuEntries[getMenuOptionCount()];
                if (tmpEntry == null) {
                    tmpEntry = Client.rl$menuEntries[getMenuOptionCount()] = newRuneliteMenuEntry(getMenuOptionCount());
                }

                for (int i = getMenuOptionCount(); i > idx; Client.rl$menuEntries[i].setIdx(i--)) {
                    getMenuOptions()[i] = getMenuOptions()[i - 1];
                    getMenuTargets()[i] = getMenuTargets()[i - 1];
                    getMenuIdentifiers()[i] = getMenuIdentifiers()[i - 1];
                    getMenuOpcodes()[i] = getMenuOpcodes()[i - 1];
                    getMenuArguments1()[i] = getMenuArguments1()[i - 1];
                    getMenuArguments2()[i] = getMenuArguments2()[i - 1];
                    getMenuForceLeftClick()[i] = getMenuForceLeftClick()[i - 1];

                    Client.rl$menuEntries[i] = Client.rl$menuEntries[i - 1];
                }

                setMenuOptionCount(getMenuOptionCount() + 1);
                Client.tmpMenuOptionsCount = getMenuOptionCount();

                menuEntry = tmpEntry;
                Client.rl$menuEntries[idx] = tmpEntry;

                tmpEntry.setIdx(idx);
            } else {
                if (idx != getMenuOptionCount()) {
                    throw new IllegalArgumentException();
                }

                menuEntry = Client.rl$menuEntries[getMenuOptionCount()];

                if (menuEntry == null) {
                    menuEntry = Client.rl$menuEntries[getMenuOptionCount()] = newRuneliteMenuEntry(getMenuOptionCount());
                }

                setMenuOptionCount(getMenuOptionCount() + 1);
                Client.tmpMenuOptionsCount = getMenuOptionCount();
            }

            menuEntry.setOption("");
            menuEntry.setTarget("");
            menuEntry.setIdentifier(0);
            menuEntry.setType(MenuAction.RUNELITE);
            menuEntry.setParam0(0);
            menuEntry.setParam1(0);
            menuEntry.setItemId(-1);
            menuEntry.setParent(null);
            menuEntry.setConsumer(null);

            return menuEntry;
        }
    }

    public MenuEntry createMenuEntry(String option, String target, int identifier, int opcode, int param1, int param2, boolean forceLeftClick) {
        RSRuneLiteMenuEntry menuEntry = newBareRuneliteMenuEntry();
        menuEntry.setOption(option);
        menuEntry.setTarget(target);
        menuEntry.setIdentifier(identifier);
        menuEntry.setType(MenuAction.of(opcode));
        menuEntry.setParam0(param1);
        menuEntry.setParam1(param2);
        menuEntry.setParent(null);
        menuEntry.setConsumer(null);
        menuEntry.setForceLeftClick(forceLeftClick);
        return menuEntry;
    }

    public MenuEntry createMenuEntry(String option, String target, int identifier, int opcode, int param1, int param2, int itemId, boolean forceLeftClick) {
        RSRuneLiteMenuEntry menuEntry = newBareRuneliteMenuEntry();
        menuEntry.setOption(option);
        menuEntry.setTarget(target);
        menuEntry.setIdentifier(identifier);
        menuEntry.setType(MenuAction.of(opcode));
        menuEntry.setParam0(param1);
        menuEntry.setParam1(param2);
        menuEntry.setParent(null);
        menuEntry.setConsumer(null);
        menuEntry.setForceLeftClick(forceLeftClick);
        menuEntry.setItemId(itemId);
        return menuEntry;
    }

    public MenuEntry[] getMenuEntries() {
        RSRuneLiteMenuEntry[] menuEntries = Arrays.copyOf(Client.rl$menuEntries, Client.instance.getMenuOptionCount());
        for (RSRuneLiteMenuEntry menuEntry : menuEntries) {
            if (menuEntry.getOption() == null) {
                menuEntry.setOption("null");
            }
            if (menuEntry.getTarget() == null) {
                menuEntry.setTarget("null");
            }
        }
        return menuEntries;
    }

    public void setMenuEntries(MenuEntry[] menuEntries) {
        boolean var2 = false;

        if (getTempMenuAction() != null && getMenuOptionCount() > 0) {
            var2 = getTempMenuAction().getParam0() == getMenuArguments1()[getMenuOptionCount() - 1] &&
                    getTempMenuAction().getParam1() == getMenuArguments2()[getMenuOptionCount() - 1] &&
                    getTempMenuAction().getOption().equals(getMenuOptions()[getMenuOptionCount() - 1]) &&
                    getTempMenuAction().getIdentifier() == getMenuIdentifiers()[getMenuOptionCount() - 1] &&
                    getTempMenuAction().getOpcode() == getMenuOpcodes()[getMenuOptionCount() - 1];
        }

        for (int i = 0; i < menuEntries.length; ++i) {
            RSRuneLiteMenuEntry menuEntry = (RSRuneLiteMenuEntry) menuEntries[i];
            if (menuEntry.getIdx() != i) {
                sortMenuEntries(menuEntry.getIdx(), i);
            }
        }

        setMenuOptionCount(menuEntries.length);
        Client.tmpMenuOptionsCount = menuEntries.length;

        if (var2 && getMenuOptionCount() > 0) {
            getTempMenuAction().setParam0(getMenuArguments1()[getMenuOptionCount() - 1]);
            getTempMenuAction().setParam1(getMenuArguments2()[getMenuOptionCount() - 1]);
            getTempMenuAction().setOption(getMenuOptions()[getMenuOptionCount() - 1]);
            getTempMenuAction().setIdentifier(getMenuIdentifiers()[getMenuOptionCount() - 1]);
            getTempMenuAction().setOpcode(getMenuOpcodes()[getMenuOptionCount() - 1]);
            client.getCallbacks().post(WidgetPressed.INSTANCE);
        }
    }

    public void sortMenuEntries(int left, int right) {
        String menuOption = getMenuOptions()[left];
        getMenuOptions()[left] = getMenuOptions()[right];
        getMenuOptions()[right] = menuOption;

        String menuTarget = getMenuTargets()[left];
        getMenuTargets()[left] = getMenuTargets()[right];
        getMenuTargets()[right] = menuTarget;

        int menuIdentifier = getMenuIdentifiers()[left];
        getMenuIdentifiers()[left] = getMenuIdentifiers()[right];
        getMenuIdentifiers()[right] = menuIdentifier;

        int menuOpcode = getMenuOpcodes()[left];
        getMenuOpcodes()[left] = getMenuOpcodes()[right];
        getMenuOpcodes()[right] = menuOpcode;

        int menuArguments1 = getMenuArguments1()[left];
        getMenuArguments1()[left] = getMenuArguments1()[right];
        getMenuArguments1()[right] = menuArguments1;

        int menuArgument2 = getMenuArguments2()[left];
        getMenuArguments2()[left] = getMenuArguments2()[right];
        getMenuArguments2()[right] = menuArgument2;

        boolean menuForceLeftClick = getMenuForceLeftClick()[left];
        getMenuForceLeftClick()[left] = getMenuForceLeftClick()[right];
        getMenuForceLeftClick()[right] = menuForceLeftClick;

        RSRuneLiteMenuEntry var2 = Client.rl$menuEntries[left];
        Client.rl$menuEntries[left] = Client.rl$menuEntries[right];
        Client.rl$menuEntries[right] = var2;

        Client.rl$menuEntries[left].setIdx(left);
        Client.rl$menuEntries[right].setIdx(right);
    }
}
