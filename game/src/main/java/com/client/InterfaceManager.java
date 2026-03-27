package com.client;

import com.client.IdentityKit;
import com.client.definitions.ItemDefinition;
import com.client.Skills;
import com.client.definitions.VariableBits;
import com.client.definitions.anim.SequenceDefinition;
import com.client.draw.ImageCache;
import com.client.sign.Signlink;
import com.client.draw.Rasterizer3D;
import com.client.RSFont;
import com.client.draw.rasterizer.Clips;
import com.client.draw.widget.components.Background;
import com.client.draw.widget.components.Divider;
import com.client.draw.widget.components.Rasterizer;
import com.client.engine.impl.MouseHandler;
import com.client.entity.model.Model;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.impl.GroupIronmanBank;
import com.client.graphics.interfaces.impl.Bank;
import com.client.graphics.interfaces.impl.DropdownMenu;
import com.client.graphics.interfaces.impl.GrandExchange;
import com.client.graphics.interfaces.impl.Interfaces;
import com.client.graphics.interfaces.impl.Slider;
import com.client.graphics.interfaces.impl.QuestTab;
import com.client.hover.HoverMenuManager;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import com.client.model.Items;

import java.awt.Point;
import java.util.Arrays;

import net.runelite.api.MenuAction;

import static com.client.engine.impl.MouseHandler.clickMode3;

/**
 * Handles all interface/widget management for the game client.
 * Extracted from Client.java to improve code organization.
 *
 * Responsible for: interface drawing, tab area rendering, scrollbars,
 * widget animations, interface menus, input fields, and tab click processing.
 */
public class InterfaceManager {

    final Client client;

    public InterfaceManager(Client client) {
        this.client = client;
    }

    // Fields remain on Client for now - accessed via client.fieldName
    // Will be moved in a follow-up once all large drawing methods are also extracted

    // ========== Phase 1: State Methods ==========

    public void closeInterface() {
        if (client.invOverlayInterfaceID != 0) {
            client.invOverlayInterfaceID = 0;
            client.needDrawTabArea = true;
            client.tabAreaAltered = true;
        }
        if (client.backDialogID != -1) {
            client.backDialogID = -1;
            client.inputTaken = true;
        }
        if (client.inputDialogState != 0) {
            client.inputDialogState = 0;
            client.inputTaken = true;
        }
        if (this.isFieldInFocus()) {
            this.resetInputFieldFocus();
            Client.inputString = "";
        }
        client.fullscreenInterfaceID = -1;
        client.openInterfaceID = -1;
        client.continuedDialogue = false;
    }

    public void sendFrame248(int interfaceID, int sideInterfaceID) {
        if (client.backDialogID != -1) {
            client.backDialogID = -1;
            client.inputTaken = true;
        }
        if (client.inputDialogState != 0) {
            client.inputDialogState = 0;
            client.inputTaken = true;
        }
        client.openInterfaceID = interfaceID;
        client.invOverlayInterfaceID = sideInterfaceID;
        client.needDrawTabArea = true;
        client.tabAreaAltered = true;
        client.continuedDialogue = false;
    }

    public void setSidebarInterface(int sidebarID, int interfaceID) {
        Client.tabInterfaceIDs[sidebarID] = interfaceID;
        Client.tabID = sidebarID;
        client.needDrawTabArea = true;
        client.tabAreaAltered = true;
    }

    public void sendFrame126(String str, int i) {
        RSInterface component = RSInterface.interfaceCache[i];
        if (component != null) {
            component.message = str;
            if (component.type == 4 && component.atActionType == 1) {
                component.hoverText = str;
            }
            if (component.parentID == Client.tabInterfaceIDs[Client.tabID])
                client.needDrawTabArea = true;
        }
    }

    public void resetTabInterfaceHover() {
        client.drawTabInterfaceHover = 0;
        if (client.lastHovered != null) {
            client.lastHovered.isHovered = false;
            client.lastHovered.hoverRunnable.run();
            client.lastHovered = null;
        }
    }

    public void method3434() {
        client.menuOptionsCount = 0;
        client.isMenuOpen = false;
    }

    public Point getOnScreenWidgetOffsets() {
        boolean newTab = true;
        int w = 512, h = 334;
        int x = (Client.canvasWidth / 2) - (newTab ? 286 : 256);
        int y = (Client.canvasHeight / 2) - 169;

        if (x + 65656 > (Client.canvasWidth - 225)) {
            x = x - 90;
            if (x < 0) {
                x = 0;
            }
        }
        if (y + 565656 > (Client.canvasHeight - 192)) {
            y = y - 83;
            if (y < 0) {
                y = 0;
            }
        }
        return new Point(x, y);
    }

    // ========== Phase 2: Utility Methods ==========

    int extractInterfaceValues(RSInterface widget, int id) {
        if (widget.scripts == null || id >= widget.scripts.length)
            return -2;
        try {
            int script[] = widget.scripts[id];
            int accumulator = 0;
            int counter = 0;
            int operator = 0;
            do {
                int instruction = script[counter++];
                int value = 0;
                byte next = 0;
                if (instruction == 0)
                    return accumulator;
                if (instruction == 1)
                    value = client.currentLevels[script[counter++]];
                if (instruction == 2)
                    value = client.maximumLevels[script[counter++]];
                if (instruction == 3)
                    value = client.currentExp[script[counter++]];
                if (instruction == 4) {
                    RSInterface inventoryContainer = RSInterface.interfaceCache[script[counter++]];
                    int item = script[counter++];
                    if (item >= 0 && item < Js5List.getConfigSize(Js5ConfigType.ITEM)
                        && (!ItemDefinition.lookup(item).membersObject || client.isMembers)) {
                        int itemId = item + 1;
                        int actualItemId = item;
                        for (int slot = 0; slot < inventoryContainer.inventoryItemId.length; slot++) {
                            if (inventoryContainer.inventoryItemId[slot] == itemId)
                                value += inventoryContainer.inventoryAmounts[slot];
                        }

                        if (inventoryContainer.id == 3214) {
                            RSInterface equipment = RSInterface.interfaceCache[1688];
                            if (actualItemId == Items.NATURE_RUNE) {
                                if (equipment.hasItem(Items.BRYOPHYTAS_STAFF)) {
                                    return Integer.MAX_VALUE;
                                }
                            }
                            if (actualItemId == Items.FIRE_RUNE) {
                                if (equipment.hasItem(Items.TOME_OF_FIRE)) {
                                    return Integer.MAX_VALUE;
                                }
                            }
                        }

                        if ((client.runePouch[0][0] + 1) == itemId) {
                            value += client.runePouch[0][1];
                        }
                        if ((client.runePouch[1][0] + 1) == itemId) {
                            value += client.runePouch[1][1];
                        }
                        if ((client.runePouch[2][0] + 1) == itemId) {
                            value += client.runePouch[2][1];
                        }
                    }
                }
                if (instruction == 5) {
                    value = client.variousSettings[script[counter++]];
                }
                if (instruction == 6)
                    value = Client.SKILL_EXPERIENCE[client.maximumLevels[script[counter++]] - 1];
                if (instruction == 7)
                    value = (client.variousSettings[script[counter++]] * 100) / 46875;
                if (instruction == 8)
                    value = client.localPlayer.combatLevel;
                if (instruction == 9) {
                    for (int l1 = 0; l1 < Skills.SKILLS_COUNT; l1++)
                        if (Skills.SKILLS_ENABLED[l1])
                            value += client.maximumLevels[l1];
                }
                if (instruction == 10) {
                    RSInterface equipmentContainer = RSInterface.interfaceCache[script[counter++]];
                    int item = script[counter++] + 1;
                    if (item >= 0 && item < Js5List.getConfigSize(Js5ConfigType.ITEM)
                        && (!ItemDefinition.lookup(item).membersObject || client.isMembers)) {
                        for (int stored = 0; stored < equipmentContainer.inventoryItemId.length; stored++) {
                            if (equipmentContainer.inventoryItemId[stored] != item)
                                continue;
                            value = 0x3b9ac9ff;
                            break;
                        }
                    }
                }
                if (instruction == 11)
                    value = client.energy;
                if (instruction == 12)
                    value = client.weight;
                if (instruction == 13) {
                    int bool = client.variousSettings[script[counter++]];
                    int shift = script[counter++];
                    value = (bool & 1 << shift) == 0 ? 0 : 1;
                }
                if (instruction == 14) {
                    int index = script[counter++];
                    VariableBits bits = VariableBits.lookup(index);
                    int setting = bits.baseVar;
                    int low = bits.startBit;
                    int high = bits.endBit;
                    int mask = Client.anIntArray1232[high - low];
                    value = client.variousSettings[setting] >> low & mask;
                }
                if (instruction == 15)
                    next = 1;
                if (instruction == 16)
                    next = 2;
                if (instruction == 17)
                    next = 3;
                if (instruction == 18)
                    value = (client.localPlayer.x >> 7) + client.baseX;
                if (instruction == 19)
                    value = (client.localPlayer.y >> 7) + client.baseY;
                if (instruction == 20)
                    value = script[counter++];
                if (next == 0) {
                    if (operator == 0)
                        accumulator += value;
                    if (operator == 1)
                        accumulator -= value;
                    if (operator == 2 && value != 0)
                        accumulator /= value;
                    if (operator == 3)
                        accumulator *= value;
                    operator = 0;
                } else {
                    operator = next;
                }
            } while (true);
        } catch (Exception _ex) {
            return -1;
        }
    }

    boolean interfaceIsSelected(RSInterface class9) {
        if (class9.anIntArray245 == null)
            return false;
        for (int i = 0; i < class9.anIntArray245.length; i++) {
            int j = extractInterfaceValues(class9, i);
            int k = class9.anIntArray212[i];
            if (class9.anIntArray245[i] == 2) {
                if (j >= k)
                    return false;
            } else if (class9.anIntArray245[i] == 3) {
                if (j <= k)
                    return false;
            } else if (class9.anIntArray245[i] == 4) {
                if (j == k)
                    return false;
            } else if (j != k)
                return false;
        }
        return true;
    }

    String interfaceIntToString(int j) {
        if (j < 0x3b9ac9ff)
            return String.valueOf(j);
        else
            return "*";
    }

    public final String methodR(int j) {
        if (j >= 0 && j < 10000)
            return String.valueOf(j);
        if (j >= 10000 && j < 10000000)
            return j / 1000 + "K";
        if (j >= 10000000 && j < 999999999)
            return j / 1000000 + "M";
        if (j >= 999999999)
            return "*";
        else
            return "?";
    }

    public static boolean scrollbarVisible(RSInterface widget) {
        if (widget.id == 55010) {
            if (RSInterface.interfaceCache[55024].message.length() <= 0) {
                return false;
            }
        } else if (widget.id == 55050) {
            if (RSInterface.interfaceCache[55064].message.length() <= 0) {
                return false;
            }
        }
        return true;
    }

    public void resetInputFieldFocus() {
        for (RSInterface rsi : RSInterface.interfaceCache)
            if (rsi != null)
                rsi.isInFocus = false;
        RSInterface.currentInputFieldId = -1;
    }

    public boolean isFieldInFocus() {
        if (client.openInterfaceID == -1 && client.invOverlayInterfaceID <= 0) {
            return false;
        }
        for (RSInterface rsi : RSInterface.interfaceCache) {
            if (rsi != null) {
                if (rsi.type == 16 && rsi.isInFocus) {
                    return true;
                }
            }
        }
        return false;
    }

    public RSInterface getInputFieldFocusOwner() {
        for (RSInterface rsi : RSInterface.interfaceCache)
            if (rsi != null)
                if (rsi.isInFocus)
                    return rsi;
        return null;
    }

    public void setInputFieldFocusOwner(RSInterface owner) {
        for (RSInterface rsi : RSInterface.interfaceCache)
            if (rsi != null)
                if (rsi == owner)
                    rsi.isInFocus = true;
                else
                    rsi.isInFocus = false;
    }

    // ========== Phase 5: Animation ==========

    public boolean processWidgetAnimations(int tick, int interfaceId) {
        if (tick <= 0) {
            return false;
        }
        if (RSInterface.interfaceCache == null || interfaceId < 0 || interfaceId >= RSInterface.interfaceCache.length) {
            return false;
        }

        RSInterface parent = RSInterface.interfaceCache[interfaceId];
        if (parent == null) {
            return false;
        }

        if (parent.children == null || parent.children.length == 0) {
            return false;
        }

        boolean changed = false;

        for (int k = 0; k < parent.children.length; k++) {
            int childId = parent.children[k];

            if (childId == -1) {
                break;
            }

            if (childId < 0 || childId >= RSInterface.interfaceCache.length) {
                continue;
            }

            RSInterface child = RSInterface.interfaceCache[childId];
            if (child == null) {
                continue;
            }

            if (child.type == 1) {
                changed |= processWidgetAnimations(tick, child.id);
                continue;
            }

            if (child.type == 6 && (child.disabledAnimationId != -1 || child.enabledAnimationId != -1)) {
                boolean selected = interfaceIsSelected(child);
                int seqId = selected ? child.enabledAnimationId : child.disabledAnimationId;

                if (seqId == -1) {
                    continue;
                }

                SequenceDefinition anim = SequenceDefinition.get(seqId);
                if (anim == null || anim.frameCount <= 0) {
                    child.currentFrame = 0;
                    child.anInt208 = 0;
                    continue;
                }

                if (child.currentFrame < 0 || child.currentFrame >= anim.frameCount) {
                    child.currentFrame = 0;
                }

                child.anInt208 += tick;

                int safety = 0;
                while (child.anInt208 > anim.duration(child.currentFrame)) {
                    int dur = anim.duration(child.currentFrame);
                    if (dur < 0) dur = 0;

                    child.anInt208 -= (dur + 1);
                    child.currentFrame++;

                    if (child.currentFrame >= anim.frameCount) {
                        child.currentFrame -= anim.frameCount;
                        if (child.currentFrame < 0 || child.currentFrame >= anim.frameCount) {
                            child.currentFrame = 0;
                        }
                    }

                    changed = true;

                    if (++safety > 1000) {
                        child.anInt208 = 0;
                        child.currentFrame = 0;
                        break;
                    }
                }
            }
        }

        return changed;
    }

    // ========== Phase 3: Interaction Methods ==========

    public void buildInterfaceMenu(int xPosition, RSInterface widget, int mouseX, int yPosition, int mouseY, int j1) {
        if (widget == null) {
            return;
        }
        if (widget.type != 0 || widget.children == null || widget.isMouseoverTriggered)
            return;
        if (mouseX < xPosition || mouseY < yPosition || mouseX > xPosition + widget.width || mouseY > yPosition + widget.height)
            return;
        int childCount = widget.children.length;
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            try {
                int drawX = widget.childX[childIndex] + xPosition;
                int drawY = (widget.childY[childIndex] + yPosition) - j1;
                RSInterface childInterface = RSInterface.interfaceCache[widget.children[childIndex]];
                HoverMenuManager.reset();
                if (childInterface == null) {
                    break;
                }
                if (childInterface.clickingDisabled) {
                    break;
                }

                drawX += childInterface.anInt263;
                drawY += childInterface.anInt265;

                if (mouseX >= drawX && mouseY >= drawY && mouseX <= drawX + childInterface.width && mouseY <= drawY + childInterface.height) {
                    if (childInterface.hoverInterfaceId != 0) {
                        client.drawTabInterfaceHoverParent = childInterface.id;
                        client.drawTabInterfaceHover = childInterface.hoverInterfaceId;
                    }

                    if (childInterface.hoverRunnable != null && client.lastHovered == null) {
                        childInterface.isHovered = true;
                        childInterface.hoverRunnable.run();
                        client.lastHovered = childInterface;
                    }
                }

                if (mouseX >= drawX && mouseY >= drawY && mouseX <= drawX + childInterface.width && mouseY <= drawY + childInterface.height) {
                    childInterface.toggled = true;
                } else {
                    childInterface.toggled = false;
                }

                if ((childInterface.mOverInterToTrigger >= 0 || childInterface.defaultHoverColor != 0) && mouseX >= drawX && mouseY >= drawY
                        && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                    if (childInterface.mOverInterToTrigger >= 0) {
                        client.anInt886 = childInterface.mOverInterToTrigger;
                    } else {
                        client.anInt886 = childInterface.id;
                    }
                }
                if (childInterface.atActionType == RSInterface.AT_ACTION_TYPE_OPTION_DROPDOWN) {

                    boolean flag = false;
                    childInterface.hovered = false;
                    childInterface.dropdownHover = -1;

                    if (childInterface.dropdown.isOpen()) {

                        // Inverted keybinds dropdown
                        if (childInterface.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && childInterface.inverted && mouseX >= drawX
                                && mouseX < drawX + (childInterface.dropdown.getWidth() - 16)
                                && mouseY >= drawY - childInterface.dropdown.getHeight() - 10 && mouseY < drawY) {
                            resetTabInterfaceHover();
                            int yy = mouseY - (drawY - childInterface.dropdown.getHeight());

                            if (mouseX > drawX + (childInterface.dropdown.getWidth() / 2)) {
                                childInterface.dropdownHover = ((yy / 15) * 2) + 1;
                            } else {
                                childInterface.dropdownHover = (yy / 15) * 2;
                            }
                            flag = true;
                        } else if (!childInterface.inverted && mouseX >= drawX && mouseX < drawX + (childInterface.dropdown.getWidth() - 16)
                                && mouseY >= drawY + 19 && mouseY < drawY + 19 + childInterface.dropdown.getHeight()) {
                            resetTabInterfaceHover();
                            int yy = mouseY - (drawY + 19);

                            if (childInterface.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && childInterface.dropdown.doesSplit()) {
                                if (mouseX > drawX + (childInterface.dropdown.getWidth() / 2)) {
                                    childInterface.dropdownHover = ((yy / 15) * 2) + 1;
                                } else {
                                    childInterface.dropdownHover = (yy / 15) * 2;
                                }
                            } else {
                                childInterface.dropdownHover = yy / 14;
                            }
                            flag = true;
                        }
                        if (flag) {
                            if (client.menuOptionsCount != 1) {
                                client.menuOptionsCount = 1;
                            }
                            client.insertMenuItemNoShift("Select", MenuAction.DROPDOWN_SELECT, widget.id, childInterface.dropdownHover, childInterface.id);
                        }
                    }
                    if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.dropdown.getWidth() && mouseY < drawY + 24
                            && client.menuOptionsCount == 1) {
                        childInterface.hovered = true;
                        client.insertMenuItemNoShift(childInterface.dropdown.isOpen() ? "Hide" : "Show", MenuAction.DROPDOWN, widget.id, childInterface.dropdownHover, childInterface.id);
                    }
                }



                if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + (childInterface.type == 4 ? 100 : childInterface.width)
                        && mouseY < drawY + childInterface.height) {
                    if (childInterface.actions != null && !childInterface.invisible && !childInterface.drawingDisabled) {

                        if (!(childInterface.contentType == 206 && interfaceIsSelected(childInterface))) {
                            if (childInterface.type == 5) {

                                boolean drawOptions = true;

                                if (childInterface.parentID == 37128) {
                                    drawOptions = client.showClanOptions;
                                }

                                if (drawOptions) {
                                    for (int action = childInterface.actions.length - 1; action >= 0; action--) {
                                        if (childInterface.actions[action] != null) {
                                            String s = childInterface.actions[action]
                                                    + (childInterface.type == 4 ? " @or1@" + childInterface.message : "");

                                            if (s.contains("img")) {
                                                int prefix = s.indexOf("<img=");
                                                int suffix = s.indexOf(">");
                                                s = s.replaceAll(s.substring(prefix + 5, suffix), "");
                                                s = s.replaceAll("</img>", "");
                                                s = s.replaceAll("<img=>", "");
                                            }
                                            if (s.contains("ico")) {
                                                int prefix = s.indexOf("<ico=");
                                                int suffix = s.indexOf(">");
                                                s = s.replaceAll(s.substring(prefix + 5, suffix), "");
                                                s = s.replaceAll("</ico>", "");
                                                s = s.replaceAll("<ico=>", "");
                                            }
                                            client.insertMenuItemNoShift(s, MenuAction.UNKNOWN_WIDGET, 0, action, childInterface.id);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (childInterface.type == RSInterface.TYPE_GRAND_EXCHANGE) {
                    RSInterface class9_2;
                    int slot = childInterface.grandExchangeSlot;
                    RSInterface itemBox = RSInterface.interfaceCache[GrandExchange.grandExchangeItemBoxIds[slot]];
                    if (itemBox != null && !itemBox.interfaceHidden)
                        class9_2 = itemBox;
                    else
                        class9_2 = RSInterface.interfaceCache[GrandExchange.grandExchangeBuyAndSellBoxIds[slot]];
                    buildInterfaceMenu(drawX, class9_2, mouseX, drawY, mouseY, j1);
                }
                if (childInterface.type == 9 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                    client.anInt1315 = childInterface.id;
                }
                if (childInterface.type == 5 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                    client.hoverId = childInterface.id;
                }

                if (childInterface.type == 0) {
                    buildInterfaceMenu(drawX, childInterface, mouseX, drawY, mouseY, childInterface.scrollPosition);
                    if (childInterface.scrollMax > childInterface.height) {
                        if (scrollbarVisible(childInterface)) {
                            method65(drawX + childInterface.width, childInterface.height, mouseX, mouseY, childInterface, drawY, true, childInterface.scrollMax);
                        }
                    }
                } else {
                    if (childInterface.atActionType == RSInterface.OPTION_OK && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        boolean flag = false;
                        if (childInterface.contentType != 0)
                            flag = client.buildFriendsListMenu(childInterface);
                        if (!flag) {
                            client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.WIDGET_TYPE_1, -1, -1, childInterface.id);
                        }
                        if (childInterface.type == RSInterface.TYPE_HOVER || childInterface.type == RSInterface.TYPE_CONFIG_HOVER
                                || childInterface.type == RSInterface.TYPE_ADJUSTABLE_CONFIG
                                || childInterface.type == RSInterface.TYPE_BOX) {
                            childInterface.toggled = true;
                        }

                    }
                    if (childInterface.atActionType == 2 && client.spellSelected == 0 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        String s = childInterface.selectedActionName;
                        if (s.indexOf(" ") != -1)
                            s = s.substring(0, s.indexOf(" "));
                        client.insertMenuItemNoShift(s + " @gre@" + childInterface.spellName, MenuAction.SPELL, -1, -1, childInterface.id);
                    }
                    // close interface button
                    if (childInterface.atActionType == 3 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift("Close", MenuAction.CLOSE, -1, -1, childInterface.id);
                    }
                    if (childInterface.atActionType == 4 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.TOGGLE_SETTINGS, 0, 0, childInterface.id);
                    }
                    if (childInterface.atActionType == RSInterface.TYPE_TOOLTIP && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        if (childInterface.tooltips != null) {
                            for(int index = 0; index < childInterface.tooltips.length; index++) {
                                if (childInterface.tooltips[index] != null) {
                                    if(childInterface.tooltips[index] != "") {
                                        client.insertMenuItemNoShift(childInterface.tooltips[index], MenuAction.WIDGET_TYPE_1, 315, index, childInterface.id);
                                    }
                                }
                            }
                        } else {
                            if(childInterface.tooltip != "") {
                                client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.WIDGET_TYPE_1, 0, 0, childInterface.id);
                            }
                        }
                    }

                    if (childInterface.atActionType == 5 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.OPTION_RESET_SETTINGS, 0, 0, childInterface.id);
                    }
                    if (childInterface.atActionType == 6 && !client.continuedDialogue && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.OPTION_CONTINUE, 0, 0, childInterface.id);
                    }

                    if (client.spellSelected == 0 && childInterface.atActionType == RSInterface.AT_ACTION_TYPE_AUTOCAST && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.AUTOCAST_MENU_ACTION_ID, 0, 0, childInterface.id);
                    }

                    if (childInterface.type == 8 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.anInt1315 = childInterface.id;
                    }
                    if (childInterface.atActionType == 8 && !client.continuedDialogue && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        for (int s1 = 0; s1 < childInterface.tooltips.length; s1++) {
                            client.insertMenuItemNoShift(childInterface.tooltips[s1], MenuAction.of(1700 + s1), 0, 0, childInterface.id);
                        }
                    }
                    if (childInterface.atActionType == 9 && !client.continuedDialogue && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.WIDGET_DUNNO_2, 0, 0, childInterface.id);
                    }
                    if (childInterface.atActionType == 10 && !client.continuedDialogue && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width
                            && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.getMenuItem().getText(), MenuAction.WIDGET_DUNNO_3, 0, 0, childInterface.id);
                    }
                    if (childInterface.atActionType == 11 && mouseX >= drawX && mouseY >= drawY && mouseX < drawX + childInterface.width && mouseY < drawY + childInterface.height) {
                        HoverMenuManager.reset();
                        client.insertMenuItemNoShift(childInterface.tooltip, MenuAction.WIDGET_DUNNO_4, 0, 0, childInterface.id);
                    }
                    if (mouseX >= drawX && mouseY >= drawY && mouseX < drawX + (childInterface.type == 4 ? 100 : childInterface.width)
                            && mouseY < drawY + childInterface.height) {

                        if (childInterface.actions != null) {
                            if ((childInterface.type == 4 && childInterface.message.length() > 0) || childInterface.type == 5) {
                                for (int action = childInterface.actions.length - 1; action >= 0; action--) {
                                    if (childInterface.actions[action] != null) {
                                        HoverMenuManager.reset();
                                        client.insertMenuItemNoShift(childInterface.actions[action] + (childInterface.type == 4 ? " " + childInterface.message : ""), MenuAction.UNKNOWN_WIDGET, 0, action, childInterface.id);
                                    }
                                }
                            }
                        }
                    }
                    if (childInterface.type == 2) {
                        int k2 = 0;
                        for (int l2 = 0; l2 < childInterface.height; l2++) {
                            for (int i3 = 0; i3 < childInterface.width; i3++) {
                                boolean smallSprite = client.openInterfaceID == 26000
                                        && GrandExchange.isSmallItemSprite(childInterface.id) || childInterface.smallInvSprites;
                                int size = smallSprite ? 18 : 32;
                                int j3 = drawX + i3 * (size + childInterface.invSpritePadX);
                                int k3 = drawY + l2 * (size + childInterface.invSpritePadY);
                                if (k2 < 20) {
                                    j3 += childInterface.spritesX[k2];
                                    k3 += childInterface.spritesY[k2];
                                }
                                if (mouseX >= j3 && mouseY >= k3 && mouseX < j3 + size && mouseY < k3 + size) {
                                    client.mouseInvInterfaceIndex = k2;
                                    client.lastActiveInvInterface = childInterface.id;
                                    int itemID = childInterface.inventoryItemId[k2] - 1;
                                    if (childInterface.id == 23231) {
                                        itemID = (childInterface.inventoryItemId[k2] & 0x7FFF) - 1;
                                    }
                                    if (childInterface.inventoryItemId[k2] > 0) {
                                        ItemDefinition itemDef = ItemDefinition.lookup(itemID);
                                        boolean hasDestroyOption = false;
                                        if (client.itemSelected == 1 && childInterface.isInventoryInterface) {
                                            if (childInterface.id != client.anInt1284 || k2 != client.anInt1283) {
                                                client.insertMenuItemNoShift("Use " + client.selectedItemName + " with @lre@" + itemDef.name, MenuAction.ITEM_USE_ON_WIDGET_ITEM, itemDef.id, k2, childInterface.id);
                                            }
                                        } else if (client.spellSelected == 1 && childInterface.isInventoryInterface) {
                                            if ((client.spellUsableOn & 0x10) == 16) {
                                                client.insertMenuItemNoShift(client.spellTooltip + " @lre@" + itemDef.name, MenuAction.SPELL_CAST_ON_WIDGET, itemDef.id, k2, childInterface.id);
                                            }
                                        } else {
                                            if (childInterface.isInventoryInterface && client.fullscreenInterfaceID != 44900) {

                                                for (int type = 4; type >= 3; type--)
                                                    if (itemDef.itemActions != null && itemDef.itemActions[type] != null) {

                                                        int type0 = 0;
                                                        if (HoverMenuManager.shouldDraw(itemDef.id)) {
                                                            HoverMenuManager.showMenu = true;
                                                            HoverMenuManager.hintName = itemDef.name;
                                                            HoverMenuManager.hintId = itemDef.id;
                                                        } else {
                                                            HoverMenuManager.reset();
                                                        }
                                                        if (itemDef.itemActions[type].contains("Wield") || itemDef.itemActions[type].contains("Wear")) {
                                                            client.hintMenu = true;
                                                            client.hintName = itemDef.name;
                                                            client.hintId = itemDef.id;
                                                        } else {
                                                            client.hintMenu = false;
                                                        }
                                                        if (type == 3)
                                                            type0 = 493;
                                                        if (type == 4)
                                                            type0 = 847;
                                                        hasDestroyOption = itemDef.itemActions[type].contains("Destroy");

                                                        client.insertMenuItemNoShift(itemDef.itemActions[type] + " @lre@" + itemDef.name, MenuAction.of(type0), itemDef.id, k2, childInterface.id);
                                                    } else if (type == 4) {
                                                        client.insertMenuItemNoShift("Drop" + " @lre@" + itemDef.name, MenuAction.ITEM_DROP, itemDef.id, k2, childInterface.id);
                                                    }

                                            }
                                            if (childInterface.usableItemInterface) {
                                                if (HoverMenuManager.shouldDraw(itemDef.id)) {
                                                    HoverMenuManager.showMenu = true;
                                                    HoverMenuManager.hintName = itemDef.name;
                                                    HoverMenuManager.hintId = itemDef.id;
                                                } else {
                                                    HoverMenuManager.reset();
                                                }
                                                client.hintMenu = false;
                                                client.insertMenuItemNoShift("Use @lre@" + itemDef.name, MenuAction.ITEM_USE, itemDef.id, k2, childInterface.id);

                                                if (!hasDestroyOption && !client.isMenuOpen && client.shiftDrop && client.shiftDown) {
                                                    client.insertMenuItemNoShift("Drop @lre@" + itemDef.name, MenuAction.ITEM_DROP, itemDef.id, k2, childInterface.id);
                                                    client.removeShiftDropOnMenuOpen = true;
                                                }
                                            }
                                            if (childInterface.isInventoryInterface && itemDef.itemActions != null) {
                                                for (int type = 2; type >= 0; type--)
                                                    if (itemDef.itemActions[type] != null) {

                                                        if (itemDef.itemActions[type].contains("Wield") || itemDef.itemActions[type].contains("Wear")) {
                                                            HoverMenuManager.showMenu = true;
                                                            HoverMenuManager.hintName = itemDef.name;
                                                            HoverMenuManager.hintId = itemDef.id;
                                                        } else {
                                                            HoverMenuManager.reset();
                                                        }
                                                        if (HoverMenuManager.showMenu) {
                                                            HoverMenuManager.drawHintMenu();
                                                        }

                                                        int type0 = 0;

                                                        if (type == 0)
                                                            type0 = 74;
                                                        if (type == 1)
                                                            type0 = 454;
                                                        if (type == 2)
                                                            type0 = 539;


                                                        client.insertMenuItemNoShift(itemDef.itemActions[type]
                                                                + " @lre@" + itemDef.name, MenuAction.of(type0), itemDef.id, k2, childInterface.id);

                                                        if (!hasDestroyOption && !client.isMenuOpen && client.shiftDrop && client.shiftDown) {
                                                            client.insertMenuItemNoShift("Drop @lre@" + itemDef.name, MenuAction.ITEM_DROP, itemDef.id, k2, childInterface.id);
                                                            client.removeShiftDropOnMenuOpen = true;
                                                        }
                                                    }

                                            }

                                            if (childInterface.actions != null) {
                                                if (client.openInterfaceID == 35650 && childInterface.id == 3823) {
                                                    // GE sell screen: only show "Offer"
                                                    client.insertMenuItemNoShift("Offer @lre@" + itemDef.name, MenuAction.of(632), itemDef.id, k2, childInterface.id);
                                                } else if (client.openInterfaceID == 21553 && childInterface.parentID == 3213) {
                                                    String[] options = new String[]{"Place in inventory", "Use as equipment"};
                                                    for (int type = 8; type >= 0; type--) {
                                                        if (type > options.length - 1)
                                                            continue;
                                                        String option = childInterface.actions[type];
                                                        if (options[type] != null) {
                                                            int type0 = 0;
                                                            if (type == 0)
                                                                type0 = 632;
                                                            if (type == 1)
                                                                type0 = 78;
                                                            if (type == 2)
                                                                type0 = 867;
                                                            if (type == 3)
                                                                type0 = 431;
                                                            if (type == 4)
                                                                type0 = 53;
                                                            if (type == 7)
                                                                type0 = 1337;

                                                            MenuAction action = MenuAction.of(type0);
                                                            client.insertMenuItemNoShift(option + " @lre@" + itemDef.name, action, itemDef.id, k2, childInterface.id);
                                                        }
                                                    }

                                                } else {
                                                    if (Bank.isBankContainer(childInterface)) {
                                                        if (childInterface.id == 5382 && client.placeHolders && childInterface.inventoryAmounts[k2] <= 0) {
                                                            childInterface.actions = new String[8];
                                                            childInterface.actions[1] = "Release";
                                                        } else {
                                                            if (childInterface.actions == null || childInterface.actions.length < 7) {
                                                                childInterface.actions = new String[] {
                                                                        "Withdraw-1",
                                                                        "Withdraw-5",
                                                                        "Withdraw-10",
                                                                        "Withdraw-All",
                                                                        "Withdraw-X",
                                                                        "Withdraw-All-but-1",
                                                                        null
                                                                };
                                                            }

                                                            if (client.modifiableXValue > 0) {
                                                                if (childInterface.actions.length <= 5) {
                                                                    childInterface.actions = Arrays.copyOf(childInterface.actions, 7);
                                                                }
                                                                childInterface.actions[5] = "Withdraw-" + client.modifiableXValue;
                                                            }
                                                        }
                                                        if (childInterface.actions == null || childInterface.actions.length < 7) {
                                                            childInterface.actions = new String[] {
                                                                    "Withdraw-1",
                                                                    "Withdraw-5",
                                                                    "Withdraw-10",
                                                                    "Withdraw-All",
                                                                    "Withdraw-X",
                                                                    "Withdraw-All-but-1",
                                                                    null
                                                            };
                                                        }

                                                        if (client.modifiableXValue > 0) {
                                                            if (childInterface.actions.length <= 5) {
                                                                childInterface.actions = Arrays.copyOf(childInterface.actions, 7);
                                                            }
                                                            childInterface.actions[5] = "Withdraw-" + client.modifiableXValue;
                                                        }
                                                    }


                                                        for (int j4 = 8; j4 >= 0; j4--) {
                                                        if (j4 > childInterface.actions.length - 1)
                                                            continue;
                                                        if (childInterface.actions[j4] != null) {
                                                            String[] menuTarget = new String[30];
                                                            menuTarget[j4] = childInterface.actions[j4] + " @lre@" + itemDef.name;
                                                            int type0 = 0;
                                                            if (childInterface.id != 1688) {
                                                                if (j4 == 0)
                                                                    type0 = 632;
                                                                if (j4 == 1)
                                                                    type0 = 78;
                                                                if (j4 == 2)
                                                                    type0 = 867;
                                                                if (j4 == 3)
                                                                    type0 = 431;
                                                                if (j4 == 4)
                                                                    type0 = 53;
                                                                if (j4 == 5)
                                                                    type0 = 300;
                                                                if (j4 == 6)
                                                                    type0 = 291;

                                                            } else {
                                                                if (itemDef.equipActions[j4] == null) {
                                                                    menuTarget[3] = "Operate @lre@" + itemDef.name;
                                                                    menuTarget[4] = "Remove @lre@" + itemDef.name;
                                                                } else {
                                                                    childInterface.actions = itemDef.equipActions;
                                                                    menuTarget[client.menuOptionsCount] = itemDef.equipActions[j4] + " @lre@" + itemDef.name;
                                                                }
                                                                if (j4 == 0)
                                                                    type0 = 632;
                                                                if (j4 == 1)
                                                                    type0 = 661;
                                                                if (j4 == 2)
                                                                    type0 = 662;
                                                                if (j4 == 3)
                                                                    type0 = 663;
                                                                if (j4 == 4)
                                                                    type0 = 664;
                                                            }


                                                            MenuAction action = MenuAction.of(type0);
                                                            client.insertMenuItemNoShift(menuTarget[j4], action, itemDef.id, k2, childInterface.id);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (childInterface.parentID >= 58040 && childInterface.parentID <= 58048
                                                || childInterface.parentID >= 32100 && childInterface.parentID <= 32156
                                                || childInterface.parentID >= 32200 && childInterface.parentID <= 32222) {
                                            return;
                                        }

                                        if (childInterface.isItemSearchComponent) {											client.insertMenuItemNoShift(
                                                "Select @lre@" + itemDef.name,
                                                MenuAction.ITEM_SELECT,
                                                itemDef.id,
                                                k2,
                                                childInterface.id
                                        );
                                        } else if (!Bank.isBankContainer(childInterface)) {
                                            client.insertMenuItemNoShift(
                                                    "Examine @lre@" + itemDef.name,
                                                    MenuAction.EXAMINE_ITEM,
                                                    itemDef.id,
                                                    k2,
                                                    childInterface.id
                                            );
                                        }

                                    }
                                }
                                k2++;
                            }

                        }

                    }
                }

            } catch (Exception | StackOverflowError e) {
                System.err.println(String.format("Error building interface menu: rootId=%d, childIndex=%d, childId=%d", widget.id, childIndex, widget.children[childIndex]));
                e.printStackTrace();
            }
        }
    }

    public void processTabClick() {
        if (clickMode3 == 1) {
            if (!client.isResized()) {
                int x = 516;
                int y = 168;
                int[] points = new int[] { 3, 41, 74, 107, 140, 173, 206, 244 };
                for (int index = 0; index < points.length - 1; index++) {
                    if (MouseHandler.saveClickX >= x + points[index] && MouseHandler.saveClickX <= x + points[index + 1]) {
                        if (MouseHandler.saveClickY >= y && MouseHandler.saveClickY <= y + 36) {
                            if (Client.tabInterfaceIDs[index] != -1) {
                                Client.tabID = index;
                                Client.needDrawTabArea = true;
                                Client.tabAreaAltered = true;
                            }
                        } else if (MouseHandler.saveClickY >= y + 298 && MouseHandler.saveClickY <= y + 36 + 298) {
                            if (Client.tabInterfaceIDs[index + 7] != -1) {
                                if (index + 7 == 13) {
                                    Client.stream.createFrame(185);
                                    Client.stream.writeWord(21406);
                                }
                                Client.tabID = index + 7;
                                Client.needDrawTabArea = true;
                                Client.tabAreaAltered = true;
                            }
                        }
                        Client.needDrawTabArea = true;
                        Client.tabAreaAltered = true;
                    }
                }
            } else {
                int x = Client.canvasWidth - (Client.stackTabs() ? 231 : 462);
                int y = Client.canvasHeight - (Client.stackTabs() ? 73 : 37);
                for (int index = 0; index < 14; index++) {
                    if (MouseHandler.saveClickX >= x && MouseHandler.saveClickX <= x + 33) {
                        if (MouseHandler.saveClickY >= y && MouseHandler.saveClickY <= y + 36) {
                            if (Client.tabInterfaceIDs[index] != -1) {
                                Client.tabID = index;
                                Client.needDrawTabArea = true;
                                Client.tabAreaAltered = true;
                            }
                        } else if (Client.stackTabs() && MouseHandler.saveClickY >= y + 36 && MouseHandler.saveClickY <= y + 36 + 36) {
                            if (Client.tabInterfaceIDs[index + 7] != -1) {
                                Client.tabID = index + 7;
                                Client.needDrawTabArea = true;
                                Client.tabAreaAltered = true;
                            }
                        }
                    }
                    x += 33;
                }
            }
        }
    }

    public void method65(int width, int height, int mouseX, int mouseY, RSInterface class9, int i1, boolean flag, int j1) {
        int anInt992;
        if (client.aBoolean972)
            anInt992 = 32;
        else
            anInt992 = 0;
        client.aBoolean972 = false;
        if (mouseX >= width && mouseX < width + 16 && mouseY >= i1 && mouseY < i1 + 16) {
            class9.scrollPosition -= client.mouseClickCount * 4;
            if (flag) {
                client.needDrawTabArea = true;
            }
        } else if (mouseX >= width && mouseX < width + 16 && mouseY >= (i1 + height) - 16 && mouseY < i1 + height) {
            class9.scrollPosition += client.mouseClickCount * 4;
            if (flag) {
                client.needDrawTabArea = true;
            }
        } else if (mouseX >= width - anInt992 && mouseX < width + 16 + anInt992 && mouseY >= i1 + 16 && mouseY < (i1 + height) - 16 && client.mouseClickCount > 0) {
            int l1 = ((height - 32) * height) / j1;
            if (l1 < 8)
                l1 = 8;
            int i2 = mouseY - i1 - 16 - l1 / 2;
            int j2 = height - 32 - l1;
            class9.scrollPosition = ((j1 - height) * i2) / j2;
            if (flag)
                client.needDrawTabArea = true;
            client.aBoolean972 = true;
        }
    }

    public void rightClickChatButtons() {
        if (MouseHandler.mouseY >= Client.canvasHeight - 22 && MouseHandler.mouseY <= Client.canvasHeight) {
            if (MouseHandler.mouseX >= 5 && MouseHandler.mouseX <= 61) {
                client.insertMenuItemNoShift("View All",MenuAction.VIEW_ALL);
            } else if (MouseHandler.mouseX >= 71 && MouseHandler.mouseX <= 127) {
                client.insertMenuItemNoShift("View Game",MenuAction.VIEW_GAME);
            } else if (MouseHandler.mouseX >= 137 && MouseHandler.mouseX <= 193) {
                client.insertMenuItemNoShift("Hide public", MenuAction.HIDE_PUBLIC);
                client.insertMenuItemNoShift("Off public", MenuAction.OFF_PUBLIC);
                client.insertMenuItemNoShift("Friends public", MenuAction.FRIENDS_PUBLIC);
                client.insertMenuItemNoShift("On public", MenuAction.ON_PUBLIC);
                client.insertMenuItemNoShift("View public", MenuAction.VIEW_PUBLIC);
            } else if (MouseHandler.mouseX >= 203 && MouseHandler.mouseX <= 259) {
                client.insertMenuItemNoShift("Off private", MenuAction.OFF_PRIVATE);
                client.insertMenuItemNoShift("Friends private", MenuAction.FRIENDS_PRIVATE);
                client.insertMenuItemNoShift("On private", MenuAction.ON_PRIVATE);
                client.insertMenuItemNoShift("View private", MenuAction.VIEW_PRIVATE);
            } else if (MouseHandler.mouseX >= 269 && MouseHandler.mouseX <= 325) {
                client.insertMenuItemNoShift("Off clan chat", MenuAction.OFF_CLAN_CHAT);
                client.insertMenuItemNoShift("Friends clan chat", MenuAction.FRIENDS_CLAN_CHAT);
                client.insertMenuItemNoShift("On clan chat", MenuAction.ON_CLAN_CHAT);
                client.insertMenuItemNoShift("View clan chat", MenuAction.VIEW_CLAN_CHAT);
            } else if (MouseHandler.mouseX >= 335 && MouseHandler.mouseX <= 391) {
                client.insertMenuItemNoShift("Off trade", MenuAction.OFF_TRADE);
                client.insertMenuItemNoShift("Friends trade", MenuAction.FRIENDS_TRADE);
                client.insertMenuItemNoShift("On trade", MenuAction.ON_TRADE);
                client.insertMenuItemNoShift("View trade", MenuAction.VIEW_TRADE);
            } else if (MouseHandler.mouseX >= 404 && MouseHandler.mouseX <= 515) {
                client.insertMenuItemNoShift("Report Abuse", MenuAction.REPORT);
            }
        }
    }

    // ========== Phase 4: Drawing Methods (smaller) ==========

    public void drawScrollbar(int height, int scrollPosition, int yPosition, int xPosition, int scrollMax) {
        client.scrollBar1.drawSprite(xPosition, yPosition);
        client.scrollBar2.drawSprite(xPosition, (yPosition + height) - 16);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x000001, 16);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x3d3426, 15);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x342d21, 13);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x2e281d, 11);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x29241b, 10);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x252019, 9);
        Rasterizer2D.drawPixels(height - 32, yPosition + 16, xPosition, 0x000001, 1);
        int k1 = ((height - 32) * height) / scrollMax;
        if (k1 < 8)
            k1 = 8;
        int l1 = ((height - 32 - k1) * scrollPosition) / (scrollMax - height);
        Rasterizer2D.drawPixels(k1, yPosition + 16 + l1, xPosition, client.barFillColor, 16);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x000001, k1, xPosition);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x817051, k1, xPosition + 1);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x73654a, k1, xPosition + 2);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x6a5c43, k1, xPosition + 3);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x6a5c43, k1, xPosition + 4);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x655841, k1, xPosition + 5);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x655841, k1, xPosition + 6);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x61553e, k1, xPosition + 7);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x61553e, k1, xPosition + 8);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x5d513c, k1, xPosition + 9);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x5d513c, k1, xPosition + 10);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x594e3a, k1, xPosition + 11);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x594e3a, k1, xPosition + 12);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x514635, k1, xPosition + 13);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x4b4131, k1, xPosition + 14);
        Rasterizer2D.method339(yPosition + 16 + l1, 0x000001, 15, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x000001, 15, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x655841, 14, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x6a5c43, 13, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x6d5f48, 11, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x73654a, 10, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x76684b, 7, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x7b6a4d, 5, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x7e6e50, 4, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x817051, 3, xPosition);
        Rasterizer2D.method339(yPosition + 17 + l1, 0x000001, 2, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x564b38, 15, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x5d513c, 14, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x625640, 11, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x655841, 10, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x6a5c43, 7, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x6e6046, 5, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x716247, 4, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x7b6a4d, 3, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x817051, 2, xPosition);
        Rasterizer2D.method339(yPosition + 18 + l1, 0x000001, 1, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x514635, 15, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x564b38, 14, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x5d513c, 11, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x61553e, 9, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x655841, 7, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x6a5c43, 5, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x6e6046, 4, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x73654a, 3, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x817051, 2, xPosition);
        Rasterizer2D.method339(yPosition + 19 + l1, 0x000001, 1, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x4b4131, 15, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x544936, 14, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x594e3a, 13, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x5d513c, 10, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x61553e, 8, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x655841, 6, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x6a5c43, 4, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x73654a, 3, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x817051, 2, xPosition);
        Rasterizer2D.method339(yPosition + 20 + l1, 0x000001, 1, xPosition);
        Rasterizer2D.method341(yPosition + 16 + l1, 0x000001, k1, xPosition + 15);
        Rasterizer2D.method339(yPosition + 15 + l1 + k1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x000001, 15, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x3f372a, 14, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x443c2d, 10, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x483e2f, 9, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x4a402f, 7, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x4b4131, 4, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x564b38, 3, xPosition);
        Rasterizer2D.method339(yPosition + 14 + l1 + k1, 0x000001, 2, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x443c2d, 15, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x4b4131, 11, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x514635, 9, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x544936, 7, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x564b38, 6, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x594e3a, 4, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x625640, 3, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x6a5c43, 2, xPosition);
        Rasterizer2D.method339(yPosition + 13 + l1 + k1, 0x000001, 1, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x443c2d, 15, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x4b4131, 14, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x544936, 12, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x564b38, 11, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x594e3a, 10, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x5d513c, 7, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x61553e, 4, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x6e6046, 3, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x7b6a4d, 2, xPosition);
        Rasterizer2D.method339(yPosition + 12 + l1 + k1, 0x000001, 1, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x000001, 16, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x4b4131, 15, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x514635, 14, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x564b38, 13, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x594e3a, 11, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x5d513c, 9, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x61553e, 7, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x655841, 5, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x6a5c43, 4, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x73654a, 3, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x7b6a4d, 2, xPosition);
        Rasterizer2D.method339(yPosition + 11 + l1 + k1, 0x000001, 1, xPosition);
    }

    public void drawHoverBox(int xPos, int yPos, String text) {
        try {
            String[] results = text.split("\n");
            int height = (results.length * 16) + 3;
            int width;
            width = client.aTextDrawingArea_1271.getTextWidth(results[0]) + 6;
            for (int i = 1; i < results.length; i++)
                if (width <= client.aTextDrawingArea_1271.getTextWidth(results[i]) + 6)
                    width = client.aTextDrawingArea_1271.getTextWidth(results[i]) + 6;
            Rasterizer2D.drawPixels(height, yPos, xPos, 0x544433, width);
            Rasterizer2D.fillPixels(xPos, width, height, 0, yPos);
            yPos += 14;
            for (int i = 0; i < results.length; i++) {
                client.aTextDrawingArea_1271.method389(false, xPos + 3, 0, results[i], yPos);
                yPos += 16;
            }
        }catch (Exception e) {}
    }

    public void drawBlackBox(int xPos, int yPos) {
        Rasterizer2D.drawBox(xPos - 2, yPos - 1, 1, 71, 0x726451);
        Rasterizer2D.drawBox(xPos + 174, yPos, 1, 69, 0x726451);
        Rasterizer2D.drawBox(xPos - 2, yPos - 2, 178, 1, 0x726451);
        Rasterizer2D.drawBox(xPos, yPos + 68, 174, 1, 0x726451);
        Rasterizer2D.drawBox(xPos - 1, yPos - 1, 1, 71, 0x2E2B23);
        Rasterizer2D.drawBox(xPos + 175, yPos - 1, 1, 71, 0x2E2B23);
        Rasterizer2D.drawBox(xPos, yPos - 1, 175, 1, 0x2E2B23);
        Rasterizer2D.drawBox(xPos, yPos + 69, 175, 1, 0x2E2B23);
        Rasterizer2D.drawTransparentBox(xPos, yPos, 174, 68, 0, 220);
    }

    void drawInputField(RSInterface child, int xPosition, int yPosition, int width, int height) {
        int clickX = MouseHandler.saveClickX, clickY = MouseHandler.saveClickY;
        Sprite[] inputSprites = client.inputSprites;
        int xModification = 0, yModification = 0;
        for (int row = 0; row < width; row += 12) {
            if (row + 12 > width)
                row -= 12 - (width - row);
            inputSprites[6].drawSprite(xModification <= 0 ? xPosition + row : xPosition + xModification, yPosition);
            for (int collumn = 0; collumn < height; collumn += 12) {
                if (collumn + 12 > height)
                    collumn -= 12 - (height - collumn);
                inputSprites[6].drawSprite(xPosition + row,
                        yModification <= 0 ? yPosition + collumn : yPosition + yModification);
            }
        }
        inputSprites[1].drawSprite(xPosition, yPosition);
        inputSprites[0].drawSprite(xPosition, yPosition + height - 8);
        inputSprites[2].drawSprite(xPosition + width - 4, yPosition);
        inputSprites[3].drawSprite(xPosition + width - 4, yPosition + height - 8);
        xModification = 0;
        yModification = 0;
        for (int top = 0; top < width; top += 8) {
            if (top + 8 > width)
                top -= 8 - (width - top);
            inputSprites[5].drawSprite(xPosition + top, yPosition);
            inputSprites[5].drawSprite(xPosition + top, yPosition + height - 1);
        }
        for (int bottom = 0; bottom < height; bottom += 8) {
            if (bottom + 8 > height)
                bottom -= 8 - (height - bottom);
            inputSprites[4].drawSprite(xPosition, yPosition + bottom);
            inputSprites[4].drawSprite(xPosition + width - 1, yPosition + bottom);
        }
        String message = child.message;
        if (client.aTextDrawingArea_1271.getTextWidth(message) > child.width - 10)
            message = message.substring(message.length() - (child.width / 10) - 1, message.length());
        if (child.displayAsterisks)
            client.aTextDrawingArea_1271.method389(false, (xPosition + 4), child.textColor,
                    new StringBuilder().append("").append(StringUtils.passwordAsterisks(message))
                            .append(((!child.isInFocus ? 0 : 1) & (client.loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "")
                            .toString(),
                    (yPosition + (height / 2) + 6));
        else
            client.aTextDrawingArea_1271.method389(false, (xPosition + 4), child.textColor,
                    new StringBuilder().append("").append(message)
                            .append(((!child.isInFocus ? 0 : 1) & (client.loopCycle % 40 < 20 ? 1 : 0)) != 0 ? "|" : "")
                            .toString(),
                    (yPosition + (height / 2) + 6));


        boolean clicked = false;
        if (client.drawingTabArea && !client.isResized()) {
            int x = 516;
            int y = 168;
            clicked = clickX >= xPosition + x && clickX <= xPosition + x + child.width && clickY >= yPosition + y && clickY <= yPosition + y + child.height;
        } else {
            clicked = clickX >= xPosition && clickX <= xPosition + child.width && clickY >= yPosition && clickY <= yPosition + child.height;
        }

        if (clicked && !child.isInFocus && getInputFieldFocusOwner() != child) {
            if ((MouseHandler.instance.clickMode2 == 1 && !client.isMenuOpen)) {
                RSInterface.currentInputFieldId = child.id;
                setInputFieldFocusOwner(child);
                if (child.message != null && child.message.equals(child.defaultInputFieldText))
                    child.message = "";
                if (child.message == null)
                    child.message = "";
            }
        }
    }

    public void loadTabArea() {
        if (!client.preferences().isOldGameframe()) {
            for (int i = 0; i < client.redStones.length; i++)
                client.redStones[i] = new Sprite("Gameframe/redstones/redstone" + i);

            for (int i = 0; i < client.sideIcons.length; i++)
                client.sideIcons[i] = new Sprite("Gameframe/sideicons/sideicon" + i);

            client.mapArea[0] = new Sprite("Gameframe/fixed/mapArea");
            client.mapArea[1] = new Sprite("Gameframe/fixed/mapBorder");
            client.mapArea[2] = new Sprite("Gameframe/resizable/mapArea");
            client.mapArea[3] = new Sprite("Gameframe/fixed/blackMapArea");
            client.mapArea[4] = new Sprite("Gameframe/resizable/mapBlack");
            client.mapArea[5] = new Sprite("Gameframe/fixed/topframe");
            client.mapArea[6] = new Sprite("Gameframe/fixed/chatborder");
            client.mapArea[7] = new Sprite("Gameframe/fixed/frame");

            client.tabAreaFixed = new Sprite("Gameframe/fixed/tabArea");
            client.compassImage = new Sprite("Gameframe/compassImage");
        }else {
            for (int i = 0; i < client.redStones.length; i++)
                client.redStones[i] = new Sprite("Gameframe317/redstones/redstone" + i);

            for (int i = 0; i < client.sideIcons.length; i++)
                client.sideIcons[i] = new Sprite("Gameframe317/sideicons/sideicon" + i);

            client.mapArea[0] = new Sprite("Gameframe/fixed/mapArea");
            client.mapArea[1] = new Sprite("Gameframe317/fixed/mapBorder");
            client.mapArea[2] = new Sprite("Gameframe317/resizable/mapArea");
            client.mapArea[3] = new Sprite("Gameframe317/fixed/blackMapArea");
            client.mapArea[4] = new Sprite("Gameframe317/resizable/mapBlack");
            client.mapArea[5] = new Sprite("Gameframe/fixed/topframe");
            client.mapArea[6] = new Sprite("Gameframe/fixed/chatborder");
            client.mapArea[7] = new Sprite("Gameframe/fixed/frame");

            client.tabAreaFixed = new Sprite("Gameframe317/fixed/tabArea");
            client.compassImage = new Sprite("Gameframe317/compassImage");
        }
    }

    // ========== Phase 6: Tab Area Drawing ==========

    public void drawTabArea() {
        client.drawingTabArea = true;
        boolean fixedMode = !client.isResized();
        final int xOffset = !client.isResized() ? 516 : 0;
        final int yOffset = !client.isResized() ? 168 : 0;

        if (fixedMode) {
            client.tabAreaFixed.drawSprite(xOffset,yOffset);
            if (client.invOverlayInterfaceID == 0)
                drawTabs(xOffset,yOffset);

        } else {
            (Client.stackTabs() ? client.tabAreaResizable[1] : client.tabAreaResizable[2]).drawSprite(
                    Client.canvasWidth - (Client.stackTabs() ? 231 : 462),
                    Client.canvasHeight - (Client.stackTabs() ? 73 : 37) );

            client.tabAreaResizable[0].drawSprite((Client.canvasWidth - 204),
                    Client.canvasHeight - 275 - (Client.stackTabs() ? 73 : 37));

            if (client.invOverlayInterfaceID == 0)
                drawTabs(xOffset,yOffset);
        }
        int y = Client.stackTabs() ? 73 : 37;

        // Set open tab to one that has an interface
        if (Client.tabInterfaceIDs[Client.tabID] <= 0) {
            for (int i = Client.tabID; i >= 0; i--) {
                if (Client.tabInterfaceIDs[i] >= 1) {
                    Client.tabID = i;
                    break;
                }
            }

            if (Client.tabInterfaceIDs[Client.tabID] <= 0)
                Client.tabID = 3;
        }

        if (client.invOverlayInterfaceID != 0) {
            client.drawInterface(0, (fixedMode ? 31 + xOffset : Client.canvasWidth - 197),
                    RSInterface.interfaceCache[client.invOverlayInterfaceID],
                    (fixedMode ? 37 + yOffset : Client.canvasHeight - 275 - y + 10));
        } else if (Client.tabInterfaceIDs[Client.tabID] != -1) {
            try {
                client.drawInterface(0, (fixedMode ? 31 + xOffset : Client.canvasWidth - 197),
                        RSInterface.interfaceCache[Client.tabInterfaceIDs[Client.tabID]],
                        (fixedMode ? 37 + yOffset : Client.canvasHeight - 275 - y + 10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (client.drawTabInterfaceHover != 0) {
            RSInterface parent = RSInterface.interfaceCache[client.drawTabInterfaceHoverParent];
            if (client.drawTabInterfaceHoverTimer == 0 || client.drawTabInterfaceHoverLast != client.drawTabInterfaceHover)
                client.drawTabInterfaceHoverTimer = System.currentTimeMillis();
            client.drawTabInterfaceHoverLast = client.drawTabInterfaceHover;
            if (System.currentTimeMillis() - client.drawTabInterfaceHoverTimer >= parent.hoverInterfaceDelay) {
                RSInterface hover = RSInterface.interfaceCache[client.drawTabInterfaceHover];
                int xOffset1 = (fixedMode ? 516 + xOffset : 0);
                int yOffset1 = (fixedMode ? 168 + yOffset : 0);
                int drawingAreaWidth = Client.canvasWidth - xOffset1;
                int drawingAreaHeight = Client.canvasHeight - yOffset1;
                int mouseX = MouseHandler.mouseX - xOffset1;
                int mouseY = MouseHandler.mouseY - yOffset1;
                int interfaceWidth = hover.width + 12; // Was drawing offscreen a bit, don't have time to proper fix

                if (mouseX + interfaceWidth > drawingAreaWidth) {
                    mouseX = drawingAreaWidth - interfaceWidth;
                }

                if (mouseY + hover.height > drawingAreaHeight) {
                    mouseY = drawingAreaHeight - hover.height;
                }


                client.drawInterface(0, mouseX, hover, mouseY, true);
            }
        } else {
            client.drawTabInterfaceHoverTimer = 0;
        }



        client.drawingTabArea = false;
    }

    void drawTabs(int xOffset,int yOffset) {
        if (!client.isResized()) {
            final int[][] sideIconCoordinates = new int[][] { { 17, 17 }, { 49, 15 }, { 83, 15 }, { 113, 13 },
                    { 146, 10 }, { 180, 11 }, { 214, 15 }, { 14, 311 }, { 49, 314 }, { 82, 314 }, { 116, 310 },
                    { 148, 312 }, { 184, 311 }, { 216, 311 }, { 216, 311 } };
            final int[][] sideIconCoordinates1 = new int[][] { { 24, 8 }, { 49, 5}, { 79, 5 }, { 108, 3}, { 147, 5 },
                    { 176, 5 }, { 205, 8 }, { 22, 300 }, { 49, 304 }, { 77, 304 }, { 111, 303 }, { 147, 301 },
                    { 180, 303 }, { 204, 303 }, { 204, 303 } };
            if (Client.tabInterfaceIDs[Client.tabID] != -1) {
                if (!client.preferences().isOldGameframe()) {
                    if (Client.tabID == 0)
                        client.redStones[0].drawSprite(5 + xOffset, 0 + yOffset);
                    if (Client.tabID == 1)
                        client.redStones[4].drawSprite(43 + xOffset, 0 + yOffset);
                    if (Client.tabID == 2)
                        client.redStones[4].drawSprite(76 + xOffset, 0 + yOffset);
                    if (Client.tabID == 3)
                        client.redStones[4].drawSprite(109 + xOffset, 0 + yOffset);
                    if (Client.tabID == 4)
                        client.redStones[4].drawSprite(142 + xOffset, 0 + yOffset);
                    if (Client.tabID == 5)
                        client.redStones[4].drawSprite(175 + xOffset, 0 + yOffset);
                    if (Client.tabID == 6)
                        client.redStones[1].drawSprite(208 + xOffset, 0 + yOffset);
                    if (Client.tabID == 7)
                        client.redStones[2].drawSprite(5 + xOffset, 298 + yOffset);
                    if (Client.tabID == 8)
                        client.redStones[4].drawSprite(43 + xOffset, 298 + yOffset);
                    if (Client.tabID == 9)
                        client.redStones[4].drawSprite(76 + xOffset, 298 + yOffset);
                    if (Client.tabID == 10)
                        client.redStones[4].drawSprite(109 + xOffset, 298 + yOffset);
                    if (Client.tabID == 11)
                        client.redStones[4].drawSprite(142 + xOffset, 298 + yOffset);
                    if (Client.tabID == 12)
                        client.redStones[4].drawSprite(175 + xOffset, 298 + yOffset);
                    if (Client.tabID == 13)
                        client.redStones[3].drawSprite(208 + xOffset, 298 + yOffset);
                }else {
                    if (Client.tabID == 0)
                        client.redStones[1].drawSprite(14 + xOffset, 0 + yOffset);
                    if (Client.tabID == 1)
                        client.redStones[2].drawSprite(47 + xOffset, 0 + yOffset);
                    if (Client.tabID == 2)
                        client.redStones[2].drawSprite(74 + xOffset, 0 + yOffset);
                    if (Client.tabID == 3)
                        client.redStones[3].drawSprite(102 + xOffset, 0 + yOffset);
                    if (Client.tabID == 4)
                        client.redStones[2].drawSprite(144 + xOffset, 0 + yOffset);
                    if (Client.tabID == 5)
                        client.redStones[2].drawSprite(172 + xOffset, 0 + yOffset);
                    if (Client.tabID == 6)
                        client.redStones[0].drawSprite(201 + xOffset, 0 + yOffset);
                    if (Client.tabID == 7)
                        client.redStones[4].drawSprite(13 + xOffset, 296 + yOffset);
                    if (Client.tabID == 8)
                        client.redStones[2].drawSprite(46 + xOffset, 297 + yOffset);
                    if (Client.tabID == 9)
                        client.redStones[2].drawSprite(74 + xOffset, 298 + yOffset);
                    if (Client.tabID == 10)
                        client.redStones[3].drawSprite(102 + xOffset, 297 + yOffset);
                    if (Client.tabID == 11)
                        client.redStones[2].drawSprite(144 + xOffset, 296 + yOffset);
                    if (Client.tabID == 12)
                        client.redStones[2].drawSprite(171 + xOffset, 296 + yOffset);
                    if (Client.tabID == 13)
                        client.redStones[5].drawSprite(201 + xOffset, 298 + yOffset);
                }

            }
// Example: tabInterfaceIDs already stores the interface for each tab
// sideIcons[i] corresponds to the sprite for that tab
            for (int index = 0; index <= 14; index++) {
                int interfaceId = Client.tabInterfaceIDs[index];
                if (interfaceId <= 0) continue; // skip empty tabs

                int drawX = sideIconCoordinates[index][0] + xOffset;
                int drawY = sideIconCoordinates[index][1] + yOffset - 8;

                // Determine which sprite to draw based on the interface ID
                Sprite iconToDraw = null;

                switch (interfaceId) {
                    case 2423:
                        iconToDraw = client.sideIcons[0];
                        break;
                    case 25402:
                        iconToDraw = client.sideIcons[1];
                        break;
                    case QuestTab.INTERFACE_ID:
                        iconToDraw = client.sideIcons[2];
                        break;
                    case 3213:
                        iconToDraw = client.sideIcons[3];
                        break;
                    case 1644:
                        iconToDraw = client.sideIcons[4];
                        break;
                    case 15608:
                        iconToDraw = client.sideIcons[5];
                        break;
                    case 938: // modern magic book
                        iconToDraw = client.sideIcons[6];
                        break;
                    case 838: // ancient
                        iconToDraw = client.sideIcons[18];
                        break;
                    case 29999: // lunar/other
                        iconToDraw = client.sideIcons[19];
                        break;
                    case 49200: // arceuus
                        iconToDraw = Interfaces.getArceuusSidebarIcon();
                        break;
                    case 18128:
                        iconToDraw = client.sideIcons[7];
                        break;
                    case 5065:
                        iconToDraw = client.sideIcons[8];
                        break;
                    case 5715:
                        iconToDraw = client.sideIcons[9];
                        break;
                    case 2449:
                        iconToDraw = client.sideIcons[10];
                        break;
                    case 42500: // wrench tab
                        iconToDraw = client.sideIcons[11];
                        break;
                    case 26041: // run tab
                        iconToDraw = client.sideIcons[12];
                        break;
                    case 962: // run tab or poll tab
                        iconToDraw = client.sideIcons[17];
                        break;
                    case 30_370:
                        iconToDraw =  client.sideIcons[14]; break;
                    case 21429:
                        iconToDraw =  client.sideIcons[15]; break;
                    default:
                        iconToDraw = client.sideIcons[index]; // fallback
                        break;
                }

                if (iconToDraw != null) {
                    iconToDraw.drawSprite(drawX, drawY);
                }
            }

        } else {
            final int[][] sideIconOffsets = new int[][] {
                    { 7, 8 }, { 4, 6 }, { 6, 7 }, { 3, 4 },
                    { 3, 2 }, { 4, 3 }, { 4, 6 }, { 5, 5 },
                    { 5, 6 }, { 5, 6 }, { 6, 3 }, { 5, 5 },
                    { 6, 4 }, { 5, 5 }
            };

// Starting position
            int x = Client.canvasWidth - (Client.stackTabs() ? 231 : 462) + xOffset;
            int y = Client.canvasHeight - (Client.stackTabs() ? 73 : 37) + yOffset;

// Draw redstone highlight for current tab
            for (int tabIndex = 0; tabIndex < 14; tabIndex++) {
                if (Client.tabID == tabIndex) {
                    client.redStones[4].drawSprite(x, y);
                }
                // Increment positions for stacked tabs
                if (Client.stackTabs()) {
                    if (tabIndex != 6) x += 33;
                    else { y += 36; x = Client.canvasWidth - 231; }
                } else {
                    x += 33;
                }
            }

// Reset positions for drawing the icons
            x = Client.canvasWidth - (Client.stackTabs() ? 231 : 462) + xOffset;
            y = Client.canvasHeight - (Client.stackTabs() ? 73 : 37) + yOffset;

// Draw each tab icon linked to its interface
            for (int index = 0; index < 14; index++) {
                int interfaceId = Client.tabInterfaceIDs[index];
                if (interfaceId == -1) {
                    // Skip empty tabs
                    if (Client.stackTabs()) {
                        if (index != 6) x += 33;
                        else { y += 36; x = Client.canvasWidth - 231; }
                    } else { x += 33; }
                    continue;
                }

                // Determine which sprite to draw based on interface ID
                Sprite iconToDraw = null;

                switch (interfaceId) {
                    case 2423: iconToDraw = client.sideIcons[0]; break;
                    case 25402: iconToDraw = client.sideIcons[1]; break;
                    case QuestTab.INTERFACE_ID: iconToDraw = client.sideIcons[2]; break;
                    case 3213: iconToDraw = client.sideIcons[3]; break;
                    case 1644: iconToDraw = client.sideIcons[4]; break;
                    case 15608: iconToDraw = client.sideIcons[5]; break;
                    case 938: // modern magic book
                        iconToDraw = client.sideIcons[6];
                        break;
                    case 838: // ancient
                        iconToDraw = client.sideIcons[18];
                        break;
                    case 29999: // lunar/other
                        iconToDraw = client.sideIcons[19];
                        break;
                    case 49200: // arceuus
                        iconToDraw = Interfaces.getArceuusSidebarIcon();
                        break;
                    case 21429:
                        iconToDraw =  client.sideIcons[15]; break;
                    case 30_370:
                        iconToDraw =  client.sideIcons[14]; break;
                    case 18128: iconToDraw = client.sideIcons[7]; break;
                    case 5065: iconToDraw = client.sideIcons[8]; break;
                    case 5715: iconToDraw = client.sideIcons[9]; break;
                    case 2449: iconToDraw = client.sideIcons[10]; break;
                    case 42500: iconToDraw = client.sideIcons[11]; break;
                    case 26041: iconToDraw = client.sideIcons[12]; break;
                    case 962: iconToDraw = client.sideIcons[17]; break; // special run/poll tab
                    default:
                        iconToDraw = client.sideIcons[index]; // fallback if no special case
                        break;
                }

                // Draw the icon with offset
                if (iconToDraw != null) {
                    iconToDraw.drawSprite(x + sideIconOffsets[index][0], y + sideIconOffsets[index][1]);
                }

                // Handle stacked tabs increment
                if (Client.stackTabs()) {
                    if (index != 6) x += 33;
                    else { y += 36; x = Client.canvasWidth - 231; }
                } else {
                    x += 33;
                }
            }

        }

        if (client.openInterfaceID == 25650) {
            if (!client.isResized()) {
                client.grandExchangeSprite4.drawSprite1(29 + xOffset, 37 + yOffset, 162 + (int) (50 * Math.sin(client.loopCycle / 15.0)));
            } else {
                client.grandExchangeSprite4.drawSprite1((Client.canvasWidth - 197) + xOffset,
                        (Client.stackTabs() ? Client.canvasHeight - 341 : Client.canvasHeight - 305) + yOffset,
                        162 + (int) (50 * Math.sin(client.loopCycle / 15.0)));
            }
        }
    }

    // ========== Phase 7: Friends List / Welcome Screen ==========

    public void drawFriendsListOrWelcomeScreen(RSInterface class9) {
        int j = class9.contentType;
        if ((j >= 205) && (j <= (205 + 25))) {
            j -= 205;
            class9.message = client.setSkillTooltip(j);
            return;
        }
        if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
            if (j == 1 && client.anInt900 == 0) {
                class9.message = "Loading friend list";
                class9.atActionType = 0;
                return;
            }
            if (j == 1 && client.anInt900 == 1) {
                class9.message = "Connecting to friendserver";
                class9.atActionType = 0;
                return;
            }
            if (j == 2 && client.anInt900 != 2) {
                class9.message = "Please wait...";
                class9.atActionType = 0;
                return;
            }
            int k = client.friendsCount;
            if (client.anInt900 != 2)
                k = 0;
            if (j > 700)
                j -= 601;
            else
                j--;
            if (j >= k) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            } else {
                class9.message = client.friendsList[j];
                class9.atActionType = 1;
                return;
            }
        }
        if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
            int l = client.friendsCount;
            if (client.anInt900 != 2)
                l = 0;
            if (j > 800)
                j -= 701;
            else
                j -= 101;
            if (j >= l) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            }
            if (client.friendsNodeIDs[j] == 0)
                class9.message = "@red@Offline";
            else if (client.friendsNodeIDs[j] == client.nodeID)
                class9.message = "@gre@Online";
            else
                class9.message = "@red@Offline";
            class9.atActionType = 1;
            return;
        }

        if (j == 203) {
            int i1 = client.friendsCount;
            if (client.anInt900 != 2)
                i1 = 0;
            class9.scrollMax = i1 * 15 + 20;
            if (class9.scrollMax <= class9.height)
                class9.scrollMax = class9.height + 1;
            return;
        }
        if (j >= 401 && j <= 500) {
            if ((j -= 401) == 0 && client.anInt900 == 0) {
                class9.message = "Loading ignore list";
                class9.atActionType = 0;
                return;
            }
            if (j == 1 && client.anInt900 == 0) {
                class9.message = "Please wait...";
                class9.atActionType = 0;
                return;
            }
            int j1 = client.ignoreCount;
            if (client.anInt900 == 0)
                j1 = 0;
            if (j >= j1) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            } else {
                class9.message = StringUtils.fixName(StringUtils.nameForLong(client.ignoreListAsLongs[j]));
                class9.atActionType = 1;
                return;
            }
        }
        if (j == 503) {
            class9.scrollMax = client.ignoreCount * 15 + 20;
            if (class9.scrollMax <= class9.height)
                class9.scrollMax = class9.height + 1;
            return;
        }
        if (j == 327) {
            class9.modelRotation1 = 150;
            class9.modelRotation2 = (int) (Math.sin((double) client.loopCycle  / 40D) * 256D) & 0x7ff;
            if (client.aBoolean1031) {
                for (int k1 = 0; k1 < 7; k1++) {
                    int l1 = client.myAppearance[k1];
                    if (l1 >= 0 && !IdentityKit.lookup(l1).bodyCached())
                        return;
                }

                client.aBoolean1031 = false;
                com.client.entity.model.ModelData aclass30_sub2_sub4_sub6s[] = new com.client.entity.model.ModelData[7];
                int i2 = 0;
                for (int j2 = 0; j2 < 7; j2++) {
                    int k2 = client.myAppearance[j2];
                    if (k2 >= 0)
                        aclass30_sub2_sub4_sub6s[i2++] = IdentityKit.lookup(k2).getBody();
                }

                com.client.entity.model.ModelData model = new com.client.entity.model.ModelData(aclass30_sub2_sub4_sub6s, i2);
                for (int l2 = 0; l2 < 5; l2++) {
                    if (client.anIntArray990[l2] != 0) {

                        if (client.anIntArray990[l2] < Client.PLAYER_BODY_RECOLOURS[l2].length) {
                            model.recolor(Client.field3532[l2], Client.PLAYER_BODY_RECOLOURS[l2][client.anIntArray990[l2]]);
                        }

                        if (l2 == 1) {
                            if (client.anIntArray990[l2] < Client.field3535[l2].length) {
                                model.recolor(Client.field3534[l2], Client.field3535[l2][client.anIntArray990[l2]]);
                            }
                        }

                    }
                }

                Model finalModel = model.toModel(64, 850, -30, -50, -30);

                SequenceDefinition.get(client.localPlayer.idleSequence).transformWidgetModel(finalModel, 0);
                class9.defaultMediaType = 5;
                class9.defaultMedia = 0;
                class9.method208(client.aBoolean994, finalModel);
            }
        }
        if (j == 328) {
            RSInterface rsInterface = class9;
            int verticleTilt = 150;
            int animationSpeed = (int) (Math.sin((double) client.loopCycle / 40D) * 256D) & 0x7ff;
            class9.modelRotation1 = verticleTilt;
            class9.modelRotation2 = animationSpeed;
            if (client.aBoolean1031) {

                Model characterDisplay = client.localPlayer.getAnimatedModel();

                int staticFrame = client.localPlayer.idleSequence;

                SequenceDefinition.get(staticFrame).transformWidgetModel(characterDisplay, 0);
                // characterDisplay.light(64, 850, -30, -50, -30, true);
                rsInterface.defaultMediaType = 5;
                rsInterface.defaultMedia = 0;
                class9.method208(client.aBoolean994, characterDisplay);
            }
            return;
        }
        if (j == 324) {
            if (client.aClass30_Sub2_Sub1_Sub1_931 == null) {
                client.aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
                client.aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
            }
            if (client.aBoolean1047) {
                class9.sprite1 = client.aClass30_Sub2_Sub1_Sub1_932;
                return;
            } else {
                class9.sprite1 = client.aClass30_Sub2_Sub1_Sub1_931;
                return;
            }
        }
        if (j == 325) {
            if (client.aClass30_Sub2_Sub1_Sub1_931 == null) {
                client.aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
                client.aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
            }
            if (client.aBoolean1047) {
                class9.sprite1 = client.aClass30_Sub2_Sub1_Sub1_931;
                return;
            } else {
                class9.sprite1 = client.aClass30_Sub2_Sub1_Sub1_932;
                return;
            }
        }
        if (j == 600) {
            class9.message = client.reportAbuseInput;
            if (client.loopCycle % 20 < 10) {
                class9.message += "|";
                return;
            } else {
                class9.message += " ";
                return;
            }
        }
        if (j == 613)
            if (client.localPlayer.hasRightsLevel(1)) {
                if (client.canMute) {
                    class9.textColor = 0xff0000;
                    // class9.message =
                    // "Moderator option: Mute player for 48 hours: <ON>";
                } else {
                    class9.textColor = 0xffffff;
                    // class9.message =
                    // "Moderator option: Mute player for 48 hours: <OFF>";
                }
            } else {
                class9.message = "";
            }
        if (j == 650 || j == 655)
            if (client.anInt1193 != 0) {
                String s;
                if (client.daysSinceLastLogin == 0)
                    s = "earlier today";
                else if (client.daysSinceLastLogin == 1)
                    s = "yesterday";
                else
                    s = client.daysSinceLastLogin + " days ago";
                class9.message = "You last logged in " + s + " from: " + Signlink.dns;
            } else {
                class9.message = "";
            }
        if (j == 651) {
            if (client.unreadMessages == 0) {
                class9.message = "0 unread messages";
                class9.textColor = 0xffff00;
            }
            if (client.unreadMessages == 1) {
                class9.message = "1 unread message";
                class9.textColor = 65280;
            }
            if (client.unreadMessages > 1) {
                class9.message = client.unreadMessages + " unread messages";
                class9.textColor = 65280;
            }
        }
        if (j == 652)
            if (client.daysSinceRecovChange == 201) {
                if (client.membersInt == 1)
                    class9.message = "@yel@This is a non-members world: @whi@Since you are a member we";
                else
                    class9.message = "";
            } else if (client.daysSinceRecovChange == 200) {
                class9.message = "You have not yet set any password recovery questions.";
            } else {
                String s1;
                if (client.daysSinceRecovChange == 0)
                    s1 = "Earlier today";
                else if (client.daysSinceRecovChange == 1)
                    s1 = "Yesterday";
                else
                    s1 = client.daysSinceRecovChange + " days ago";
                class9.message = s1 + " you changed your recovery questions";
            }
        if (j == 653)
            if (client.daysSinceRecovChange == 201) {
                if (client.membersInt == 1)
                    class9.message = "@whi@recommend you use a members world instead. You may use";
                else
                    class9.message = "";
            } else if (client.daysSinceRecovChange == 200)
                class9.message = "We strongly recommend you do so now to secure your account.";
            else
                class9.message = "If you do not remember making this change then cancel it immediately";
        if (j == 654) {
            if (client.daysSinceRecovChange == 201)
                if (client.membersInt == 1) {
                    class9.message = "@whi@this world but member benefits are unavailable whilst here.";
                    return;
                } else {
                    class9.message = "";
                    return;
                }
            if (client.daysSinceRecovChange == 200) {
                class9.message = "Do this from the 'account management' area on our front webpage";
                return;
            }
            class9.message = "Do this from the 'account management' area on our front webpage";
        }
    }

    // ========== Phase 8: Draw Interface ==========

    private static final int[] BOUNTY_INTERFACE_IDS = {
            21001, 28030, 28006, 28021, 28003, 28004, 28005, 28029,
            28023, 28024, 28025, 28026, 28027, 28028, 28001, 28022
    };

    public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition) {
        drawInterface(scrollPosition, xPosition, rsInterface, yPosition, false);
    }

    public void drawInterface(int scrollPosition, int xPosition, RSInterface rsInterface, int yPosition, boolean inheritDrawingArea) {
        try {
            if (rsInterface.type != 0 || rsInterface.children == null)
                return;
            if (rsInterface.isMouseoverTriggered && client.anInt1026 != rsInterface.id && client.anInt1048 != rsInterface.id
                    && client.anInt1039 != rsInterface.id)
                return;

            int clipLeft = Rasterizer2D.Rasterizer2D_xClipStart;
            int clipTop = Rasterizer2D.Rasterizer2D_yClipStart;
            int clipRight = Rasterizer2D.Rasterizer2D_xClipEnd;
            int clipBottom = Rasterizer2D.Rasterizer2D_yClipEnd;

            if (!inheritDrawingArea) {
                Rasterizer2D.setDrawingArea(xPosition, yPosition, xPosition + rsInterface.width, yPosition + rsInterface.height);
            }
            Rasterizer3D.resetRasterClipping();
            int childCount = rsInterface.children.length;
            for (int childId = 0; childId < childCount; childId++) {
                try {
                    int _x = rsInterface.childX[childId] + xPosition;
                    int _y = (rsInterface.childY[childId] + yPosition) - scrollPosition;
                    RSInterface class9_1 = RSInterface.interfaceCache[rsInterface.children[childId]];

                    _x += class9_1.anInt263;
                    _y += class9_1.anInt265;

                    if (class9_1.interfaceHidden)
                        continue;

                    if (!client.getUserSettings().isBountyHunter() && Arrays.stream(BOUNTY_INTERFACE_IDS).anyMatch(id -> id == class9_1.id)) {
                        continue;
                    }

                    if (class9_1.contentType > 0)
                        drawFriendsListOrWelcomeScreen(class9_1);

                    if (class9_1.id != 28060 && class9_1.id != 28061) {
                        for (int m5 = 0; m5 < Client.SOME_IDS.length; m5++) {
                            if (class9_1.id == Client.SOME_IDS[m5] + 1) {
                                if (m5 > 61) {
                                    drawBlackBox(_x + 1, _y);
                                } else {
                                    drawBlackBox(_x, _y + 1);
                                }
                            }
                        }
                    }

                    for (int r = 0; r < Client.runeChildren.length; r++)
                        if (class9_1.id == Client.runeChildren[r])
                            class9_1.modelZoom = 775;

                    if (class9_1.type == 0) {
                        if (class9_1.scrollPosition > class9_1.scrollMax - class9_1.height)
                            class9_1.scrollPosition = class9_1.scrollMax - class9_1.height;
                        if (class9_1.scrollPosition < 0)
                            class9_1.scrollPosition = 0;
                        drawInterface(class9_1.scrollPosition, _x, class9_1, _y, inheritDrawingArea || rsInterface.scrollMax > 0);

                        // Hardcodes
                        if (class9_1.scrollMax > class9_1.height) {
                            // clan chat
                            if (class9_1.id == 18143) {
                                int clanMates = 0;
                                for (int i = 18155; i < 18244; i++) {
                                    RSInterface line = RSInterface.interfaceCache[i];
                                    if (line.message.length() > 0) {
                                        clanMates++;
                                    }
                                }
                                class9_1.scrollMax = (clanMates * 14) + class9_1.height + 1;
                            }
                            if (class9_1.id == 18322 || class9_1.id == 18423) {
                                int members = 0;
                                for (int i = class9_1.id + 1; i < class9_1.id + 1 + 100; i++) {
                                    RSInterface line = RSInterface.interfaceCache[i];
                                    if (line != null && line.message != null) {
                                        if (line.message.length() > 0) {
                                            members++;
                                        }
                                    }
                                }
                                class9_1.scrollMax = (members * 14) + 1;
                            }
                            if (rsInterface.parentID == 49200 || rsInterface.parentID == 49100 || rsInterface.parentID == 51100
                                    || rsInterface.parentID == 53100) {
                                int scrollMax = class9_1.scrollMax;

                                if (client.achievementCutoff > 1) {
                                    scrollMax = client.achievementCutoff * 65;
                                } else {
                                    client.achievementCutoff = 282;
                                }
                                class9_1.scrollMax = scrollMax;
                            }
                            if (scrollbarVisible(class9_1)) {
                                drawScrollbar(class9_1.height, class9_1.scrollPosition, _y, _x + class9_1.width,
                                        class9_1.scrollMax);
                            }
                        }

                    } else if (class9_1.type != 1)
                        if (class9_1.type == 2) {
                            if (client.interfaceText) {
                                client.newSmallFont.drawBasicString("Container: " + class9_1.id, _x, _y);
                            }
                            try {
                                Bank.setupMainTab(class9_1, _x, _y);
                                GroupIronmanBank.setupMainTab(class9_1, _x, _y);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Item container
                            if (class9_1.invAutoScrollHeight) {
                                int lastRow = -1;
                                int rowCount = 0;
                                int i3 = 0;

                                for (int l3 = 0; l3 < class9_1.height; l3++) {
                                    for (int l4 = 0; l4 < class9_1.width; l4++) {
                                        if (class9_1.inventoryItemId[i3] > 1) {
                                            if (lastRow != l3) {
                                                lastRow = l3;
                                                rowCount++;
                                            }
                                        }

                                        i3++;
                                    }
                                }

                                RSInterface scrollable = RSInterface.interfaceCache[class9_1.invAutoScrollInterfaceId];
                                scrollable.scrollMax = scrollable.height + 1;
                                int heightPerRow = class9_1.invSpritePadY + 32;
                                if (heightPerRow * rowCount > scrollable.scrollMax) {
                                    scrollable.scrollMax += (heightPerRow * rowCount) - scrollable.scrollMax + scrollable.invAutoScrollHeightOffset;
                                }
                            }

                            int i3 = 0;
                            for (int l3 = 0; l3 < class9_1.height; l3++) {
                                for (int l4 = 0; l4 < class9_1.width; l4++) {
                                    // System.out.println("id? " + class9_1.inv[i3]);
                                    int xPos = _x + l4 * (32 + class9_1.invSpritePadX);
                                    int yPos = _y + l3 * (32 + class9_1.invSpritePadY);
                                    if (i3 < 20) {
                                        xPos += class9_1.spritesX[i3];
                                        yPos += class9_1.spritesY[i3];
                                    }
                                    if (class9_1.inventoryItemId[i3] > 0) {
                                        int k6 = 0;
                                        int j7 = 0;
                                        int j9 = class9_1.inventoryItemId[i3] - 1;
                                        if (class9_1.id == 23231) {
                                            j9 = (class9_1.inventoryItemId[i3] & 0x7FFF) - 1;
                                        }
                                        if (xPos > Rasterizer2D.Rasterizer2D_xClipStart - 32 && xPos < Rasterizer2D.Rasterizer2D_xClipEnd && yPos > Rasterizer2D.Rasterizer2D_yClipStart - 32
                                                && yPos < Rasterizer2D.Rasterizer2D_yClipEnd || client.activeInterfaceType != 0 && client.itemDraggingSlot == i3) {
                                            int l9 = 0;
                                            if (client.itemSelected == 1 && client.anInt1283 == i3 && client.anInt1284 == class9_1.id)
                                                l9 = 0xffffff;

                                            int itemSpriteOpacity = 256;
                                            /**
                                             * Placeholder opacity editing
                                             */
                                            if (class9_1.inventoryAmounts[i3] <= 0)
                                                itemSpriteOpacity = 100;

                                            Sprite itemSprite = null;

                                            if (client.openInterfaceID == 29875) {
                                                if (class9_1.inventoryItemId[i3] < 555
                                                        || class9_1.inventoryItemId[i3] > 567 && class9_1.inventoryItemId[i3] != 9076) {
                                                    itemSpriteOpacity = 100;
                                                }
                                            }

                                            if (class9_1.id == 23121) {
                                                final boolean isLastBitEnabled = ((class9_1.inventoryItemId[i3] >> 15) & 0x1) == 1;
                                                if (isLastBitEnabled) {
                                                    itemSpriteOpacity = 90;
                                                }
                                            }

                                            boolean smallSprite = client.openInterfaceID == 26000
                                                    && GrandExchange.isSmallItemSprite(class9_1.id) || class9_1.smallInvSprites;

                                            ItemDefinition itemDef = ItemDefinition.lookup(j9);
                                            if (smallSprite) {
                                                itemSprite = ItemDefinition.getSmallSprite(j9, class9_1.inventoryAmounts[i3]);
                                                if (itemDef.customSmallSpriteLocation != null)
                                                {
                                                    itemSprite = new Sprite(itemDef.customSmallSpriteLocation);
                                                } else if (itemDef.customSpriteLocation != null)
                                                {
                                                    itemSprite = new Sprite(itemDef.customSpriteLocation);
                                                }
                                            } else {
                                                itemSprite = ItemDefinition.getSprite(j9, class9_1.inventoryAmounts[i3], l9);
                                                if (itemDef.customSpriteLocation != null)
                                                {
                                                    itemSprite = new Sprite(itemDef.customSpriteLocation);
                                                }
                                            }

                                            if (class9_1.id >= 32212 && class9_1.id <= 32212 + 11) {
                                                if (class9_1.inventoryItemId[i3] > 0) {
                                                    if (class9_1.sprite2 != null) {
                                                        class9_1.sprite2.drawSprite(xPos + k6 - 2, yPos + j7 - 2);
                                                    }
                                                }
                                            }
                                            if (itemSprite != null) {
                                                if (client.activeInterfaceType != 0 && client.itemDraggingSlot == i3 && client.draggingItemInterfaceId == class9_1.id) {
                                                    k6 = MouseHandler.mouseX - client.anInt1087;
                                                    j7 = MouseHandler.mouseY - client.anInt1088;
                                                    if (k6 < 5 && k6 > -5)
                                                        k6 = 0;
                                                    if (j7 < 5 && j7 > -5)
                                                        j7 = 0;
                                                    if (client.anInt989 < client.getDragSetting(class9_1.id)) {
                                                        k6 = 0;
                                                        j7 = 0;
                                                    }
                                                    itemSprite.drawTransparentSprite(xPos + k6, yPos + j7, 128);
                                                    if (yPos + j7 < Rasterizer2D.Rasterizer2D_yClipStart && rsInterface.scrollPosition > 0) {
                                                        int i10 = (client.tickDelta * (Rasterizer2D.Rasterizer2D_yClipStart - yPos - j7)) / 3;
                                                        if (i10 > client.tickDelta * 10)
                                                            i10 = client.tickDelta * 10;
                                                        if (i10 > rsInterface.scrollPosition)
                                                            i10 = rsInterface.scrollPosition;
                                                        rsInterface.scrollPosition -= i10;
                                                        client.anInt1088 += i10;
                                                    }
                                                    if (yPos + j7 + 32 > Rasterizer2D.Rasterizer2D_yClipEnd
                                                            && rsInterface.scrollPosition < rsInterface.scrollMax
                                                            - rsInterface.height) {
                                                        int j10 = (client.tickDelta * ((yPos + j7 + 32) - Rasterizer2D.Rasterizer2D_yClipEnd)) / 3;
                                                        if (j10 > client.tickDelta * 10)
                                                            j10 = client.tickDelta * 10;
                                                        if (j10 > rsInterface.scrollMax - rsInterface.height
                                                                - rsInterface.scrollPosition)
                                                            j10 = rsInterface.scrollMax - rsInterface.height
                                                                    - rsInterface.scrollPosition;
                                                        rsInterface.scrollPosition += j10;
                                                        client.anInt1088 -= j10;
                                                    }
                                                } else if (client.atInventoryInterfaceType != 0 && client.atInventoryIndex == i3
                                                        && client.atInventoryInterface == class9_1.id) {
                                                    itemSprite.drawTransparentSprite(xPos, yPos, 128);
                                                } else {
                                                    // itemSprite.drawSprite(k5, j6);
                                                    /**
                                                     * Draws item sprite
                                                     */
                                                    // if u want the glow to render after the item (instead of before it)
                                                    // then just move the if statement below after itemSprite.drawTransparentSprite(...)
                                                    if(itemDef.glowColor != -1) {
                                                        Rasterizer2D.renderGlow(xPos, yPos, itemDef.glowColor, 32);
                                                    }
                                                    itemSprite.drawTransparentSprite(xPos, yPos, itemSpriteOpacity);
                                                }
                                                if (class9_1.id == RSInterface.selectedItemInterfaceId
                                                        && class9_1.itemSearchSelectedSlot > -1
                                                        && class9_1.itemSearchSelectedSlot == i3) {
                                                    for (int i = 32; i > 0; i--) {
                                                        Rasterizer2D.method338(yPos + j7, i, 256 - Byte.MAX_VALUE, 0x395D84, i,
                                                                xPos + k6);
                                                    }
                                                    Rasterizer2D.method338(yPos + j7, 32, 256, 0x395D84, 32, xPos + k6);
                                                }

                                                if (class9_1.forceInvStackSizes || !smallSprite && !class9_1.hideInvStackSizes) {
                                                    if (class9_1.parentID < 58040 || class9_1.parentID > 58048) {
                                                        if (itemSprite.maxWidth == 33 || class9_1.inventoryAmounts[i3] != 1) {
                                                            if (class9_1.id != 23121 || class9_1.id == 23121 && ((class9_1.inventoryItemId[i3] >> 15) & 0x1) == 0) {
                                                                if (class9_1.invAlwaysInfinity || class9_1.id == Interfaces.SHOP_CONTAINER && class9_1.inventoryAmounts[i3] == 1_000_000_000) {
                                                                    client.infinity.drawSprite(xPos + k6, yPos + j7);
                                                                } else {
                                                                    int k10 = class9_1.inventoryAmounts[i3];
                                                                    int xOffset = class9_1.forceInvStackSizes ? 10 : 0;
                                                                    if (k10 >= 1)
                                                                        client.smallText.method385(0xFFFF00, client.intToKOrMil(k10), yPos + 9 + j7,
                                                                                xOffset+ xPos + k6);
                                                                    client.smallText.method385(0, client.intToKOrMil(k10), yPos + 10 + j7, xOffset+ xPos + 1 + k6);
                                                                    if (k10 > 99999 && k10 < 10000000) {
                                                                        client.smallText.method385(0xFFFFFF, client.intToKOrMil(k10), yPos + 9 + j7,
                                                                                xOffset+ xPos + k6);
                                                                    } else if (k10 > 9999999) {
                                                                        client.smallText.method385(0x00ff80, client.intToKOrMil(k10), yPos + 9 + j7,
                                                                                xOffset+ xPos + k6);
                                                                    } else {
                                                                        client.smallText.method385(0xFFFF00, client.intToKOrMil(k10), yPos + 9 + j7,
                                                                                xOffset+ xPos + k6);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (class9_1.sprites != null && i3 < 20) {
                                        Sprite class30_sub2_sub1_sub1_1 = class9_1.sprites[i3];
                                        if (class30_sub2_sub1_sub1_1 != null)
                                            class30_sub2_sub1_sub1_1.drawSprite(xPos, yPos);
                                    }
                                    i3++;
                                }
                            }
                        } else if (class9_1.type == RSInterface.TYPE_BACKGROUND) {
                            if(class9_1.forceStyle == Background.ForceType.NONE) {
                                if(class9_1.sprite2 == null) {
                                    class9_1.sprite2 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,false);
                                    class9_1.sprite1 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,true);
                                }
                            } else if(class9_1.forceStyle == Background.ForceType.OSRS) {
                                class9_1.sprite2 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,true);
                                class9_1.sprite1 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,true);

                            } else if(class9_1.forceStyle == Background.ForceType.NEW) {
                                class9_1.sprite2 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,false);
                                class9_1.sprite1 = Background.INSTANCE.buildBackground(class9_1.width, class9_1.height, class9_1.thin,false);
                            }

                            Sprite sprite;

                            sprite = class9_1.sprite1;
                            if(class9_1.sprite1 != null) {
                                sprite.drawAdvancedSprite(_x, _y);
                            }
                        } else if (class9_1.type == RSInterface.TYPE_DIVIDER) {
                            if(class9_1.sprite1 == null) {
                                if(class9_1.forceStyle == Background.ForceType.NONE) {
                                    class9_1.sprite1 = Divider.INSTANCE.buildDevider(class9_1.across,class9_1.width,true);
                                } else if(class9_1.forceStyle == Background.ForceType.OSRS) {
                                    class9_1.sprite1 = Divider.INSTANCE.buildDevider(class9_1.across,class9_1.width,true);
                                } else if(class9_1.forceStyle == Background.ForceType.NEW) {
                                    class9_1.sprite1 = Divider.INSTANCE.buildDevider(class9_1.across,class9_1.width,false);
                                }
                            }

                            Sprite sprite;



                            sprite = class9_1.sprite1;

                            sprite.drawAdvancedSprite(_x, _y);
                        } else if (class9_1.type == RSInterface.TYPE_RASTERIZER) {

                            boolean hover1 = class9_1.toggled && class9_1.outerBorderHover != 0;
                            boolean hover2 = class9_1.toggled && class9_1.innerBorderHover != 0;
                            boolean hover3 = class9_1.toggled && class9_1.backgroundHover != 0;

                            if(class9_1.rasterizerType == Rasterizer.RasterizerType.NORMAL) {
                                Rasterizer2D.drawBox(_x,_y, class9_1.width, class9_1.height, hover1 ? class9_1.outerBorderHover : class9_1.outerBorder);
                                Rasterizer2D.drawBox(_x + 1, _y + 1, class9_1.width - 2, class9_1.height - 2, hover2 ? class9_1.innerBorderHover : class9_1.innerBorder);
                                Rasterizer2D.drawBox(_x + 2, _y + 2, class9_1.width - 4, class9_1.height - 4, hover3 ? class9_1.backgroundHover : class9_1.background);
                            } else if(class9_1.rasterizerType == Rasterizer.RasterizerType.OUTLINE) {
                                Rasterizer2D.drawBoxOutline(_x, _y, class9_1.width, class9_1.height, hover1 ? class9_1.outerBorderHover : class9_1.outerBorder);
                                Rasterizer2D.drawBoxOutline(_x + 1, _y + 1, class9_1.width - 2, class9_1.height - 2, hover2 ? class9_1.innerBorderHover : class9_1.innerBorder);
                                Rasterizer2D.drawTransparentBox(_x + 2, _y + 2, class9_1.width - 4, class9_1.height - 4, hover3 ? class9_1.backgroundHover : class9_1.background,170);
                            } else if(class9_1.rasterizerType == Rasterizer.RasterizerType.GRAY_SCALE) {
                                Rasterizer2D.filterGrayscale(_x, _y , class9_1.width, class9_1.height, 200);
                            } else if(class9_1.rasterizerType == Rasterizer.RasterizerType.BOX) {
                                Rasterizer2D.drawTransparentBox(_x,_y, class9_1.width, class9_1.height, hover1 ? class9_1.outerBorderHover : class9_1.outerBorder,class9_1.opacity);
                            }
                        } else if (class9_1.type == RSInterface.TYPE_BUTTON) {
                            Sprite sprite = null;
                            if(class9_1.sprite2 == null && class9_1.disabledSpriteId != -1) {
                                class9_1.sprite2 = ImageCache.get(class9_1.disabledSpriteId);
                            }
                            if(class9_1.sprite1 == null && class9_1.enabledSpriteId != -1) {
                                class9_1.sprite1 = ImageCache.get(class9_1.enabledSpriteId);
                            }

                            if (class9_1.toggled && class9_1.sprite2 != null) {
                                sprite = class9_1.sprite2;
                            } else {
                                sprite = class9_1.sprite1;
                            }


                            if (sprite != null) {
                                sprite.drawAdvancedSprite(_x, _y);
                            }

                        } else if (class9_1.type == 3) {
                            boolean flag = false;
                            if (client.anInt1039 == class9_1.id || client.anInt1048 == class9_1.id || client.anInt1026 == class9_1.id)
                                flag = true;
                            int colour;
                            if (interfaceIsSelected(class9_1)) {
                                colour = class9_1.secondaryColor;
                                if (flag && class9_1.secondaryHoverColor != 0)
                                    colour = class9_1.secondaryHoverColor;
                            } else {
                                colour = class9_1.textColor;
                                if (flag && class9_1.defaultHoverColor != 0)
                                    colour = class9_1.defaultHoverColor;
                            }

                            if (class9_1.opacity == 0) {
                                if (class9_1.filled)
                                    Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, colour
                                    );
                                else
                                    Rasterizer2D.drawBoxOutline(_x, _y, class9_1.width,
                                            class9_1.height, colour);
                            } else if (class9_1.filled)
                                Rasterizer2D.drawTransparentBox(_x, _y, class9_1.width, class9_1.height, colour,
                                        256 - (class9_1.opacity & 0xff));
                            else
                                Rasterizer2D.drawTransparentBoxOutline(_x, _y, class9_1.width, class9_1.height,
                                        colour, 256 - (class9_1.opacity & 0xff)
                                );
                        } else if (class9_1.type == 4 || class9_1.type == RSInterface.TYPE_TEXT_DRAW_FROM_LEFT) {
                            TextDrawingArea textDrawingArea = class9_1.textDrawingAreas;
                            String s = class9_1.message;
                            if (client.interfaceStringText) {
                                s = class9_1.id + "";
                            }
                            boolean flag1 = false;
                            if (client.anInt1039 == class9_1.id || client.anInt1048 == class9_1.id || client.anInt1026 == class9_1.id)
                                flag1 = true;
                            int i4 = 0;
                            if (interfaceIsSelected(class9_1)) {
                                i4 = class9_1.secondaryColor;
                                if (flag1 && class9_1.secondaryHoverColor != 0)
                                    i4 = class9_1.secondaryHoverColor;
                                if (class9_1.aString228.length() > 0)
                                    s = class9_1.aString228;
                            } else {
                                i4 = class9_1.textColor;
                                if (flag1 && class9_1.defaultHoverColor != 0) {
                                    i4 = class9_1.defaultHoverColor;
                                    if (i4 == 49152)
                                        i4 = 16777215;
                                }
                            }
                            if (class9_1.atActionType == 6 && client.continuedDialogue) {
                                s = "Please wait...";
                                i4 = 255;
                            }
                            if ((class9_1.parentID == 1151) || (class9_1.parentID == 12855)) {
                                switch (i4) {
                                    case 16773120:
                                        i4 = 0xFE981F;
                                        break;
                                    case 7040819:
                                        i4 = 0xAF6A1A;
                                        break;
                                }
                            }
                            if (class9_1.hoverText != null && !class9_1.hoverText.isEmpty()) {
                                if (MouseHandler.mouseX > _x
                                        && MouseHandler.mouseX < _x + class9_1.textDrawingAreas.getTextWidth(class9_1.message)
                                        && MouseHandler.mouseY > _y && MouseHandler.mouseY < _y + 15) {
                                    s = class9_1.hoverText;
                                    i4 = class9_1.hoverTextColor;
                                }
                            }
                            if((client.backDialogID != -1 || client.dialogID != -1 || class9_1.message.contains("Click here to continue")) &&
                                    (rsInterface.id == client.backDialogID || rsInterface.id == client.dialogID )){
                                if(i4 == 0xffff00)
                                    i4 = 255;
                                if(i4 == 49152)
                                    i4 = 0xffffff;
                            }
                            for (int l6 = _y + textDrawingArea.anInt1497; s.length() > 0; l6 += textDrawingArea.anInt1497) {
                                if (s.indexOf("%") != -1) {
                                    do {
                                        int k7 = s.indexOf("%1");
                                        if (k7 == -1)
                                            break;
                                        if (class9_1.id < 4000 || class9_1.id > 5000 && class9_1.id != 13921
                                                && class9_1.id != 13922 && class9_1.id != 12171 && class9_1.id != 12172)
                                            s = s.substring(0, k7) + methodR(extractInterfaceValues(class9_1, 0))
                                                    + s.substring(k7 + 2);
                                        else
                                            s = s.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
                                                    + s.substring(k7 + 2);
                                    } while (true);
                                    do {
                                        int l7 = s.indexOf("%2");
                                        if (l7 == -1)
                                            break;
                                        s = s.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
                                                + s.substring(l7 + 2);
                                    } while (true);
                                    do {
                                        int i8 = s.indexOf("%3");
                                        if (i8 == -1)
                                            break;
                                        s = s.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
                                                + s.substring(i8 + 2);
                                    } while (true);
                                    do {
                                        int j8 = s.indexOf("%4");
                                        if (j8 == -1)
                                            break;
                                        s = s.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
                                                + s.substring(j8 + 2);
                                    } while (true);
                                    do {
                                        int k8 = s.indexOf("%5");
                                        if (k8 == -1)
                                            break;
                                        s = s.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
                                                + s.substring(k8 + 2);
                                    } while (true);
                                }
                                int l8 = s.indexOf("\\n");
                                String s1;
                                if (l8 != -1) {
                                    s1 = s.substring(0, l8);
                                    s = s.substring(l8 + 2);
                                } else {
                                    s1 = s;
                                    s = "";
                                }
                                RSFont font = null;
                                if (textDrawingArea == client.smallText) {
                                    font = client.newSmallFont;
                                } else if (textDrawingArea == client.aTextDrawingArea_1271) {
                                    font = client.newRegularFont;
                                } else if (textDrawingArea == client.chatTextDrawingArea) {
                                    font = client.newBoldFont;
                                } else if (textDrawingArea == client.aTextDrawingArea_1273) {
                                    font = client.newFancyFont;
                                }
                                if (rsInterface.parentID == 49100 || rsInterface.parentID == 51100 || rsInterface.parentID == 53100) {
                                    int parent = rsInterface.parentID == 49100 ? 49100 : rsInterface.parentID == 51100 ? 51100 : 53100;
                                    int subId = (class9_1.id - parent) % 100;
                                    if (subId > client.achievementCutoff) {
                                        continue;
                                    }
                                }
                                if (client.interfaceStringText) {
                                    s1 = "" + class9_1.id;
                                }

                                // Word-wrap: if widget has explicit width and text exceeds it, break at word boundaries
                                if (class9_1.width > 0 && !class9_1.centerText && font != null && font.getTextWidth(s1) > class9_1.width) {
                                    while (font.getTextWidth(s1) > class9_1.width) {
                                        int breakIdx = s1.lastIndexOf(' ');
                                        if (breakIdx <= 0) break;
                                        String overflow = s1.substring(breakIdx + 1);
                                        s1 = s1.substring(0, breakIdx);
                                        s = overflow + (s.length() > 0 ? "\\n" + s : "");
                                    }
                                }

                                if (class9_1.type == RSInterface.TYPE_TEXT_DRAW_FROM_LEFT) {
                                    int width = font.getTextWidth(s1);
                                    font.drawBasicString(s1, _x - width, l6, i4, class9_1.textShadow ? 0 : -1);
                                } else if (class9_1.centerText) {
                                    font.drawCenteredString(s1, _x + class9_1.width / 2, l6, i4, class9_1.textShadow ? 0 : -1);
                                } else {
                                    font.drawBasicString(s1, _x, l6, i4, class9_1.textShadow ? 0 : -1);
                                }
                            }
                        } else if (class9_1.type == 5) {
                            Sprite sprite;
                            if(interfaceIsSelected(class9_1) || class9_1.active) {
                                sprite = class9_1.sprite2;
                            } else {
                                sprite = class9_1.sprite1;
                            }

                            if(client.spellSelected == 1 && class9_1.id == client.spellID && client.spellID != 0 && sprite != null) {
                                if (client.spellID == 23619) {
                                    ImageCache.get(2145).drawAdvancedSprite(_x, _y);
                                } else if(client.spellID == 23633) {
                                    ImageCache.get(2146).drawAdvancedSprite(_x, _y);
                                } else if(client.spellID == 23603) {
                                    ImageCache.get(2147).drawAdvancedSprite(_x, _y);
                                } else if(client.spellID == 23583) {
                                    ImageCache.get(2148).drawAdvancedSprite(_x, _y);
                                } else {
                                    sprite.drawSprite(_x, _y, 0xffffff);
                                }
                            }

                            if(sprite != null) {
                                if (class9_1.drawsTransparent) {

                                    sprite.drawAdvancedSprite(_x, _y, class9_1.opacity);
                                } else {
                                    sprite.drawSprite(_x, _y);
                                }
                            }
                        } else if (class9_1.type == 6) {

                            Rasterizer3D.setCustomClipBounds(class9_1.width / 2 + _x, class9_1.height / 2 + _y);

                            int i5 = Rasterizer3D.Rasterizer3D_sine[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
                            int l5 = Rasterizer3D.Rasterizer3D_cosine[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
                            boolean selected = interfaceIsSelected(class9_1);
                            int emoteAnimation;
                            if (selected)
                                emoteAnimation = class9_1.enabledAnimationId;
                            else
                                emoteAnimation = class9_1.disabledAnimationId;
                            Model model;
                            try {
                                if (emoteAnimation == -1) {
                                    model = class9_1.getAnimatedModel(null, -1, selected);
                                } else {
                                    SequenceDefinition sequenceDefinition = SequenceDefinition.get(emoteAnimation);
                                    model = class9_1.getAnimatedModel(
                                            sequenceDefinition,
                                            class9_1.currentFrame,
                                            selected);
                                }

                                if (model != null) {
                                    model.calculateBoundsCylinder();
                                    model.renderonGpu = false;
                                    model.renderModel(class9_1.modelRotation2, 0, class9_1.modelRotation1, 0, i5, l5);
                                    model.renderonGpu = true;
                                }

                            } catch (Exception e) {
                            }

                            Rasterizer3D.clips.setClipBounds();
                        } else if (class9_1.type == 7) {
                            TextDrawingArea textDrawingArea_1 = class9_1.textDrawingAreas;
                            int k4 = 0;
                            for (int j5 = 0; j5 < class9_1.height; j5++) {
                                for (int i6 = 0; i6 < class9_1.width; i6++) {
                                    if (class9_1.inventoryItemId[k4] > 0) {
                                        ItemDefinition itemDef = ItemDefinition.lookup(class9_1.inventoryItemId[k4] - 1);
                                        String s2 = itemDef.name;
                                        if (itemDef.stackable || class9_1.inventoryAmounts[k4] != 1)
                                            s2 = s2 + " x" + client.intToKOrMilLongName(class9_1.inventoryAmounts[k4]);
                                        int i9 = _x + i6 * (115 + class9_1.invSpritePadX);
                                        int k9 = _y + j5 * (12 + class9_1.invSpritePadY);
                                        if (class9_1.centerText)
                                            textDrawingArea_1.method382(class9_1.textColor, i9 + class9_1.width / 2, s2, k9,
                                                    class9_1.textShadow);
                                        else
                                            textDrawingArea_1.method389(class9_1.textShadow, i9, class9_1.textColor, s2, k9);
                                    }
                                    k4++;
                                }
                            }
                        } else if (class9_1.type == 8
                                && (client.anInt1500 == class9_1.id || client.anInt1044 == class9_1.id || client.anInt1129 == class9_1.id)
                                && client.anInt1501 == 50 && !client.isMenuOpen) {
                            if (class9_1.parentID == 3917) {
                                return;
                            }
                            int boxWidth = 0;
                            int boxHeight = 0;

                            /**
                             * Skill tab hovers Remove "next level at" and "remaining" for xp if we're level
                             * 99.
                             */

                            TextDrawingArea textDrawingArea_2 = client.aTextDrawingArea_1271;
                            for (String s1 = class9_1.message; s1.length() > 0;) {
                                if (s1.indexOf("%") != -1) {
                                    do {
                                        int k7 = s1.indexOf("%1");
                                        if (k7 == -1)
                                            break;
                                        s1 = s1.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
                                                + s1.substring(k7 + 2);
                                    } while (true);
                                    do {
                                        int l7 = s1.indexOf("%2");
                                        if (l7 == -1)
                                            break;
                                        s1 = s1.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
                                                + s1.substring(l7 + 2);
                                    } while (true);
                                    do {
                                        int i8 = s1.indexOf("%3");
                                        if (i8 == -1)
                                            break;
                                        s1 = s1.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
                                                + s1.substring(i8 + 2);
                                    } while (true);
                                    do {
                                        int j8 = s1.indexOf("%4");
                                        if (j8 == -1)
                                            break;
                                        s1 = s1.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
                                                + s1.substring(j8 + 2);
                                    } while (true);
                                    do {
                                        int k8 = s1.indexOf("%5");
                                        if (k8 == -1)
                                            break;
                                        s1 = s1.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
                                                + s1.substring(k8 + 2);
                                    } while (true);
                                }
                                int l7 = s1.indexOf("\\n");

                                String s4;
                                if (l7 != -1) {
                                    s4 = s1.substring(0, l7);
                                    s1 = s1.substring(l7 + 2);
                                } else {
                                    s4 = s1;
                                    s1 = "";
                                }
                                int j10 = textDrawingArea_2.getTextWidth(s4);
                                if (j10 > boxWidth) {
                                    boxWidth = j10;
                                }
                                boxHeight += textDrawingArea_2.anInt1497 + 1;
                            }

                            if (Client.tabInterfaceIDs[Client.tabID] == 17200) {
                                return;
                            }
                            boxWidth += 6;
                            boxHeight += 7;
                            int xPos = (_x + class9_1.width) - 5 - boxWidth;
                            int yPos = _y + class9_1.height + 5;
                            if (xPos < _x + 5)
                                xPos = _x + 5;
                            if (xPos + boxWidth > xPosition + rsInterface.width)
                                xPos = (xPosition + rsInterface.width) - boxWidth;
                            if (yPos + boxHeight > yPosition + rsInterface.height)
                                yPos = (_y - boxHeight);
                            switch (class9_1.id) {
                                case 9217:
                                case 9220:
                                case 9223:
                                case 9226:
                                case 9229:
                                case 9232:
                                case 9235:
                                case 9238:
                                    xPos -= 80;
                                    break;
                                case 9239:
                                    yPos -= 100;
                                    break;
                            }
                            if (class9_1.inventoryhover) {

                                //System.out.println(canvasWidth - 648);
                                if (xPos + boxWidth > Client.canvasWidth - 8 - boxWidth + 100) {
                                    xPos = Client.canvasWidth - 8 - boxWidth;
                                }
                                if (yPos + boxHeight > Client.canvasHeight - 118 - boxHeight + 100
                                    && yPos + boxHeight < Client.canvasHeight - 118 - boxHeight + 120) {
                                    yPos = Client.canvasHeight - 148 - boxHeight;
                                } else if (yPos + boxHeight > Client.canvasHeight - 118 - boxHeight + 100) {
                                    yPos = Client.canvasHeight - 118 - boxHeight;
                                }
                            }
                            Rasterizer2D.drawPixels(boxHeight, yPos, xPos, 0xFFFFA0, boxWidth);
                            Rasterizer2D.fillPixels(xPos, boxWidth, boxHeight, 0, yPos);
                            String s2 = class9_1.message;
                            for (int j11 = yPos + textDrawingArea_2.anInt1497 + 2; s2
                                    .length() > 0; j11 += textDrawingArea_2.anInt1497 + 1) {// anInt1497
                                if (s2.indexOf("%") != -1) {
                                    do {
                                        int k7 = s2.indexOf("%1");
                                        if (k7 == -1)
                                            break;
                                        s2 = s2.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0))
                                                + s2.substring(k7 + 2);
                                    } while (true);
                                    do {
                                        int l7 = s2.indexOf("%2");
                                        if (l7 == -1)
                                            break;
                                        s2 = s2.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1))
                                                + s2.substring(l7 + 2);
                                    } while (true);
                                    do {
                                        int i8 = s2.indexOf("%3");
                                        if (i8 == -1)
                                            break;
                                        s2 = s2.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2))
                                                + s2.substring(i8 + 2);
                                    } while (true);
                                    do {
                                        int j8 = s2.indexOf("%4");
                                        if (j8 == -1)
                                            break;
                                        s2 = s2.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3))
                                                + s2.substring(j8 + 2);
                                    } while (true);
                                    do {
                                        int k8 = s2.indexOf("%5");
                                        if (k8 == -1)
                                            break;
                                        s2 = s2.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4))
                                                + s2.substring(k8 + 2);
                                    } while (true);
                                }
                                int l11 = s2.indexOf("\\n");
                                String s5;
                                if (l11 != -1) {
                                    s5 = s2.substring(0, l11);
                                    s2 = s2.substring(l11 + 2);
                                } else {
                                    s5 = s2;
                                    s2 = "";
                                }
                                if (class9_1.centerText) {
                                    textDrawingArea_2.method382(yPos, xPos + class9_1.width / 2, s5, j11, false);
                                } else {
                                    if (s5.contains("\\r")) {
                                        String text = s5.substring(0, s5.indexOf("\\r"));
                                        String text2 = s5.substring(s5.indexOf("\\r") + 2);
                                        textDrawingArea_2.method389(false, xPos + 3, 0, text, j11);
                                        int rightX = boxWidth + xPos - textDrawingArea_2.getTextWidth(text2) - 2;
                                        textDrawingArea_2.method389(false, rightX, 0, text2, j11);
                                    } else
                                        textDrawingArea_2.method389(false, xPos + 3, 0, s5, j11);
                                }
                            }

                        } else if (class9_1.type == RSInterface.TYPE_HOVER || class9_1.type == RSInterface.TYPE_CONFIG_HOVER) {
                            // Draw sprite
                            boolean flag = false;

                            if (class9_1.toggled) {
                                class9_1.sprite1.drawAdvancedSprite(_x, _y, class9_1.opacity);
                                flag = true;
                                class9_1.toggled = false;
                            } else {
                                class9_1.sprite2.drawAdvancedSprite(_x, _y, class9_1.opacity);
                            }

                            // Draw text
                            if (class9_1.message == null) {
                                continue;
                            }
                            if (class9_1.centerText) {
                                client.newRegularFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y + class9_1.msgY,
                                        flag ? class9_1.defaultHoverColor : class9_1.textColor, 0x000000);

                                // class9_1.rsFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y +
                                // class9_1.msgY,
                                // flag ? class9_1.anInt216 : class9_1.textColor, 0);
                            } else {
                                client.newRegularFont.drawCenteredString(class9_1.message, _x + class9_1.msgX, _y + class9_1.msgY,
                                        flag ? class9_1.defaultHoverColor : class9_1.textColor, 0x000000);
                                // class9_1.rsFont.drawBasicString(class9_1.message, _x + 5, _y + class9_1.msgY,
                                // flag ? class9_1.anInt216 : class9_1.textColor, 0);
                            }
                        } else if (class9_1.type == RSInterface.TYPE_CONFIG) {
                            Sprite sprite = class9_1.active ? class9_1.sprite2 : class9_1.sprite1;
                            sprite.drawSprite(_x, _y);
                        } else if (class9_1.type == RSInterface.TYPE_SLIDER) {
                            Slider slider = class9_1.slider;
                            if (slider != null) {
                                slider.draw(_x, _y, 255);
                            }
                        } else if (class9_1.type == RSInterface.TYPE_DROPDOWN) {

                            DropdownMenu d = class9_1.dropdown;

                            int bgColour = class9_1.dropdownColours[2];
                            int fontColour = 0xfe971e;
                            int downArrow = 30;

                            if (class9_1.hovered || d.isOpen()) {
                                downArrow = 31;
                                fontColour = 0xffb83f;
                                bgColour = class9_1.dropdownColours[3];
                            }

                            Rasterizer2D.drawPixels(20, _y, _x, class9_1.dropdownColours[0], d.getWidth());
                            Rasterizer2D.drawPixels(18, _y + 1, _x + 1, class9_1.dropdownColours[1], d.getWidth() - 2);
                            Rasterizer2D.drawPixels(16, _y + 2, _x + 2, bgColour, d.getWidth() - 4);

                            int xOffset = class9_1.centerText ? 3 : 16;
                            if (rsInterface.id == 41900) {
                                client.newRegularFont.drawCenteredString(d.getSelected(), _x + (d.getWidth() - xOffset) / 2, _y + 14,
                                        fontColour, 0);
                            } else {
                                client.newSmallFont.drawCenteredString(d.getSelected(), _x + (d.getWidth() - xOffset) / 2, _y + 14,
                                        fontColour, 0);
                            }

                            if (d.isOpen()) {
                                // Up arrow
                                client.cacheSprite3[29].drawSprite(_x + d.getWidth() - 18, _y + 2);

                                Rasterizer2D.drawPixels(d.getHeight(), _y + 19, _x, class9_1.dropdownColours[0], d.getWidth());
                                Rasterizer2D.drawPixels(d.getHeight() - 2, _y + 20, _x + 1, class9_1.dropdownColours[1],
                                        d.getWidth() - 2);
                                Rasterizer2D.drawPixels(d.getHeight() - 4, _y + 21, _x + 2, class9_1.dropdownColours[3],
                                        d.getWidth() - 4);

                                int yy = 2;
                                for (int i = 0; i < d.getOptions().length; i++) {
                                    if (class9_1.dropdownHover == i) {
                                        if (class9_1.id == 28102) {
                                            Rasterizer2D.drawAlphaBox(_x + 2, _y + 19 + yy, d.getWidth() - 4, 13, 0xd0914d, 80);
                                        } else {
                                            Rasterizer2D.drawPixels(13, _y + 19 + yy, _x + 2, class9_1.dropdownColours[4],
                                                    d.getWidth() - 4);
                                        }
                                        if (rsInterface.id == 41900) {
                                            client.newRegularFont.drawCenteredString(d.getOptions()[i],
                                                    _x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xffb83f, 0);
                                        } else {
                                            client.newSmallFont.drawCenteredString(d.getOptions()[i],
                                                    _x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xffb83f, 0);
                                        }

                                    } else {
                                        Rasterizer2D.drawPixels(13, _y + 19 + yy, _x + 2, class9_1.dropdownColours[3],
                                                d.getWidth() - 4);
                                        if (rsInterface.id == 41900) {
                                            client.newRegularFont.drawCenteredString(d.getOptions()[i],
                                                    _x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xfe971e, 0);
                                        } else {
                                            client.newSmallFont.drawCenteredString(d.getOptions()[i],
                                                    _x + (d.getWidth() - xOffset) / 2, _y + 29 + yy, 0xfe971e, 0);
                                        }

                                    }
                                    yy += 14;
                                }
                                drawScrollbar(d.getHeight() - 4, class9_1.scrollPosition, _y + 21, _x + d.getWidth() - 18,
                                        d.getHeight() - 5);

                            } else {
                                client.cacheSprite3[downArrow].drawSprite(_x + d.getWidth() - 18, _y + 2);
                            }
                        } else if (class9_1.type == RSInterface.TYPE_KEYBINDS_DROPDOWN) {

                            DropdownMenu d = class9_1.dropdown;

                            // If dropdown inverted, don't draw following 2 menus
                            if (client.dropdownInversionFlag > 0) {
                                client.dropdownInversionFlag--;
                                continue;
                            }

                            Rasterizer2D.drawPixels(18, _y + 1, _x + 1, 0x544834, d.getWidth() - 2);
                            Rasterizer2D.drawPixels(16, _y + 2, _x + 2, 0x2e281d, d.getWidth() - 4);
                            client.newRegularFont.drawBasicString(d.getSelected(), _x + 7, _y + 15, 0xff8a1f, 0);
                            client.cacheSprite3[82].drawSprite(_x + d.getWidth() - 18, _y + 2); // Arrow TODO

                            if (d.isOpen()) {

                                RSInterface.interfaceCache[class9_1.id - 1].active = true; // Alter stone colour

                                int yPos = _y + 18;

                                // Dropdown inversion for lower stones
                                if (class9_1.inverted) {
                                    yPos = _y - d.getHeight() - 10;
                                    client.dropdownInversionFlag = 2;
                                }

                                Rasterizer2D.drawPixels(d.getHeight() + 12, yPos, _x + 1, 0x544834, d.getWidth() - 2);
                                Rasterizer2D.drawPixels(d.getHeight() + 10, yPos + 1, _x + 2, 0x2e281d, d.getWidth() - 4);

                                int yy = 2;
                                int xx = 0;
                                int bb = d.getWidth() / 2;

                                for (int i = 0; i < d.getOptions().length; i++) {

                                    int fontColour = 0xff981f;
                                    if (class9_1.dropdownHover == i) {
                                        fontColour = 0xffffff;
                                    }

                                    if (xx == 0) {
                                        client.newRegularFont.drawBasicString(d.getOptions()[i], _x + 5, yPos + 14 + yy, fontColour,
                                                0x2e281d);
                                        xx = 1;

                                    } else {
                                        client.newRegularFont.drawBasicString(d.getOptions()[i], _x + 5 + bb, yPos + 14 + yy,
                                                fontColour, 0x2e281d);
                                        xx = 0;
                                        yy += 15;
                                    }
                                }
                            } else {
                                RSInterface.interfaceCache[class9_1.id - 1].active = false;
                            }
                        } else if (class9_1.type == RSInterface.TYPE_ADJUSTABLE_CONFIG) {
                            if (class9_1.id != 37010) {
                                //Rasterizer2D.setDrawingArea(canvasHeight, 0, canvasWidth, 0);
                            }
                            if(class9_1.id == 37100) {
                                if (_y < 41 || _y > 230) {
                                    return;
                                }
                            }
                            Rasterizer2D.drawAlphaBox(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.opacity);
                            //Rasterizer2D.setDrawingArea(yPosition + class9_1.height, xPosition, xPosition + class9_1.width, yPosition);
                            /**
                             int totalWidth = class9_1.width;
                             int spriteWidth = class9_1.sprite2.myWidth;
                             int totalHeight = class9_1.height;
                             int spriteHeight = class9_1.sprite2.myHeight;
                             Sprite behindSprite = class9_1.active ? class9_1.enabledAltSprite : class9_1.disabledAltSprite;

                             if (class9_1.toggled) {
                             behindSprite.drawSprite(_x, _y);
                             class9_1.sprite2.drawSprite(_x + (totalWidth / 2) - spriteWidth / 2,
                             _y + (totalHeight / 2) - spriteHeight / 2, class9_1.spriteOpacity);
                             class9_1.toggled = false;
                             } else {
                             behindSprite.drawSprite(_x, _y);
                             class9_1.sprite2.drawSprite(_x + (totalWidth / 2) - spriteWidth / 2,
                             _y + (totalHeight / 2) - spriteHeight / 2);
                             }
                             **/
                        } else if (class9_1.type == 16) {
                            drawInputField(class9_1, _x, _y, class9_1.width, class9_1.height);
                        }else if (class9_1.type == RSInterface.TYPE_BOX) {
                            // Draw outline
                            Rasterizer2D.drawBox(_x - 2, _y - 2, class9_1.width + 4, class9_1.height + 4, 0x0e0e0c);
                            Rasterizer2D.drawBox(_x - 1, _y - 1, class9_1.width + 2, class9_1.height + 2, 0x474745);
                            // Draw base box
                            if (class9_1.toggled) {
                                Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, class9_1.secondaryHoverColor);
                                class9_1.toggled = false;
                            } else {
                                Rasterizer2D.drawBox(_x, _y, class9_1.width, class9_1.height, class9_1.hoverTextColor);
                            }
                        } else if (class9_1.type == 19) {
                            if (class9_1.backgroundSprites.length > 1) {
                                if (class9_1.sprite1 != null) {
                                    class9_1.sprite1.drawAdvancedSprite(_x, _y);
                                }
                            }
                        } else if (class9_1.type == RSInterface.TYPE_STRING_CONTAINER) {
                            int x = _x;
                            int y = _y;

                            // Set the scroll max based on the strings
                            if (class9_1.scrollableContainerInterfaceId != 0) {
                                RSInterface container = RSInterface.get(class9_1.scrollableContainerInterfaceId);
                                int scrollMax = class9_1.invAutoScrollHeightOffset;
                                for (String string : class9_1.stringContainer)
                                    scrollMax += class9_1.invSpritePadY;
                                if (scrollMax > container.height + 1) {
                                    container.scrollMax = scrollMax;
                                } else {
                                    container.scrollMax = container.height + 1;
                                }

                                container.scrollMax += container.stringContainerContainerExtraScroll;
                            }

                            // Draw the container
                            for (String string : class9_1.stringContainer) {
                                if (class9_1.centerText) {
                                    class9_1.font.drawCenteredString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
                                } else {
                                    class9_1.font.drawBasicString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
                                }
                                y += class9_1.invSpritePadY;
                            }
                        } else if (class9_1.type == RSInterface.TYPE_HORIZONTAL_STRING_CONTAINER) {
                            int x = _x;
                            int y = _y;

                            for (int i = 0; i < class9_1.stringContainer.size(); i++) {
                                String string = class9_1.stringContainer.get(i);
                                if (class9_1.centerText) {
                                    class9_1.font.drawCenteredString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
                                } else {
                                    class9_1.font.drawBasicString(string, x, y, class9_1.textColor, class9_1.textShadow ? 0 : -1);
                                }
                                x += 32 + class9_1.invSpritePadX;
                                if ((i + 1) % class9_1.width == 0) {
                                    y += 32 + class9_1.invSpritePadY;
                                    x = _x;
                                }
                            }
                        } else if (class9_1.type == RSInterface.TYPE_PROGRESS_BAR) {
                            Rasterizer2D.drawPixels(class9_1.height, _y, _x, class9_1.fillColor, class9_1.width);
                        } else if (class9_1.type == RSInterface.TYPE_PROGRESS_BAR_2021) {
                            double percentage = class9_1.progressBar2021Percentage;
                            int color = RSInterface.getRgbProgressColor(percentage);

                            int progressBarWidth = (int) ((double) class9_1.width * percentage);
                            Rasterizer2D.drawPixels(class9_1.height, _y, _x, color, progressBarWidth);
                            Rasterizer2D.drawBorder(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor);
                        } else if (class9_1.type == RSInterface.TYPE_DRAW_BOX) {
                            //DrawingArea.drawRoundedRectangle(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.transparency, true, true);

                            Rasterizer2D.drawTransparentBox(_x, _y, class9_1.width, class9_1.height, class9_1.fillColor, class9_1.opacity);
                            Rasterizer2D.drawBorder(_x, _y, class9_1.width, class9_1.height, class9_1.borderColor);
                        } else if (class9_1.type == RSInterface.TYPE_GRAND_EXCHANGE) {
                            int slot = class9_1.grandExchangeSlot;
                            RSInterface geChildInterface;
                            RSInterface itemBox = RSInterface.interfaceCache[GrandExchange.grandExchangeItemBoxIds[slot]];
                            if (itemBox != null && !itemBox.interfaceHidden) {
                                geChildInterface = itemBox;
                            } else {
                                geChildInterface = RSInterface.interfaceCache[GrandExchange.grandExchangeBuyAndSellBoxIds[slot]];
                            }
                            drawInterface(0, _x, geChildInterface, _y);
                        }

                    drawLegacyAchievementRowHover(class9_1.id, _x, _y);

                    if (client.interfaceText) {
                        client.newSmallFont.drawString(class9_1.id + "", _x - 12, _y, 0xFFFFFFFF, 0, 256);
                    }
                } catch (Exception | StackOverflowError e) {
                    System.err.println(String.format("Error on interface child, parentId=%s, childIndex=%s, childId=%s", rsInterface.id, childId, rsInterface.children[childId]));
                    e.printStackTrace();
                }
            }

            Bank.drawOnBank(rsInterface, 0, 0);
            GroupIronmanBank.drawOnBank(rsInterface, 0, 0);

            if (!inheritDrawingArea) {
                Rasterizer2D.setDrawingArea(clipLeft, clipTop, clipRight, clipBottom);
            }
            if (rsInterface.id == 42000) {
                client.cacheSprite2[76].drawSprite1(24, 280, 200 + (int) (50 * Math.sin(client.loopCycle / 15.0)));
            }
            if (rsInterface.id == 16244) {
                if (MouseHandler.mouseX > 165 && MouseHandler.mouseX < 610 && MouseHandler.mouseY > 428 && MouseHandler.mouseY < 470) {
                    Rasterizer2D.drawAlphaBox(165, 428, 444, 42, 0xffffff, 40);
                }
                // cacheSprite2[76].drawSprite1(24, 280,
                // 200 + (int) (50 * Math.sin(tick / 15.0)));
                // DrawingArea.drawBox(30, 400, 400, 400, 0xffffff);
            }
            if (Client.tabID == 0) {
                // TODO blue spec bar
                // DrawingArea.drawBox(53, 250, 148, 24, 0xffffff);
            }

        } catch (Exception | StackOverflowError e) {
            System.err.println(String.format("Error on interface j=%d, xPosition=%d, id=%d, yPosition=%d", scrollPosition, xPosition, rsInterface.id, yPosition));
            e.printStackTrace();
        }
    }

    private void drawLegacyAchievementRowHover(int componentId, int componentX, int componentY) {
        final int rowWidth = 484;
        final int rowHeight = 65;
        int rowIndex;
        int titleId;
        int descId;
        int progressId;
        int rowX;
        int rowY;

        if (componentId >= 49801 && componentId <= 49900) {
            rowIndex = componentId - 49801;
            titleId = 49501 + rowIndex;
            descId = 49601 + rowIndex;
            progressId = 49801 + rowIndex;
            rowX = componentX - 159;
            rowY = componentY - 43;
        } else if (componentId >= 51801 && componentId <= 51900) {
            rowIndex = componentId - 51801;
            titleId = 51501 + rowIndex;
            descId = 51601 + rowIndex;
            progressId = 51801 + rowIndex;
            rowX = componentX - 159;
            rowY = componentY - 43;
        } else if (componentId >= 53801 && componentId <= 53900) {
            rowIndex = componentId - 53801;
            titleId = 53501 + rowIndex;
            descId = 53601 + rowIndex;
            progressId = 53801 + rowIndex;
            rowX = componentX - 159;
            rowY = componentY - 43;
        } else {
            return;
        }

        if (MouseHandler.mouseX < rowX || MouseHandler.mouseX > rowX + rowWidth
                || MouseHandler.mouseY < rowY || MouseHandler.mouseY > rowY + rowHeight) {
            return;
        }

        String title = RSInterface.interfaceCache[titleId].message;
        String desc = RSInterface.interfaceCache[descId].message;
        String progress = RSInterface.interfaceCache[progressId].message;

        if (desc == null || desc.isEmpty()) {
            return;
        }

        String progressLine = (progress == null || progress.isEmpty()) ? "" : "Progress: " + progress;
        String[] lines = progressLine.isEmpty()
                ? new String[] { title, desc }
                : new String[] { title, desc, progressLine };

        int lineHeight = client.newSmallFont.baseCharacterHeight + 2;
        int textHeight = 0;
        int boxWidth = 0;
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            boxWidth = Math.max(boxWidth, client.newSmallFont.getTextWidth(line));
            textHeight += lineHeight;
        }

        if (textHeight == 0) {
            return;
        }

        int boxX = MouseHandler.mouseX + 14;
        int boxY = MouseHandler.mouseY + 14;
        int width = boxWidth + 8;
        int height = textHeight + 6;

        if (boxX + width > Client.canvasWidth - 8) {
            boxX = Client.canvasWidth - width - 8;
        }
        if (boxY + height > Client.canvasHeight - 8) {
            boxY = Client.canvasHeight - height - 8;
        }

        Rasterizer2D.drawPixels(height, boxY, boxX, 0xFFFFA0, width);
        Rasterizer2D.fillPixels(boxX, width, height, 0x000000, boxY);

        int textY = boxY + lineHeight;
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            client.newSmallFont.drawBasicString(line, boxX + 4, textY, 0x000000, 0);
            textY += lineHeight;
        }
    }
}
