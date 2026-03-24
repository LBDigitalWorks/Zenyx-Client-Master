package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class PerkInterface extends RSInterface {

    public static void build(TextDrawingArea[] font) {
        setupPerks(font);
    }

    private static void setupPerks(TextDrawingArea[] font) {
        int interfaceId = 2000;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);

        String[] actions = { "PvM Red", "PvM Blue", "Skilling - QoL" };
        setChildren(17 + (actions.length * 3), widget);

        int x = 13, y = 14;

        addSprite(interfaceId, 0, "Interfaces/perks/SPRITE");
        setBounds(interfaceId++, x, y, childId++, widget);

        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, x + 461, y + 10, childId++, widget); //Close

        addHoveredButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, interfaceId + 1);
        setBounds(interfaceId++, x + 461, y + 10, childId++, widget); //Close
        interfaceId++;

        addText(interfaceId, "Perk Management", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, x + 242, y + 9, childId++, widget);

        for(int index = 0, buttonX = x + 11, buttonY = y + 40; index < actions.length; index++, buttonX += 107) {
            addHoverButton(interfaceId, "Interfaces/perks/IMAGE", 3, 102, 29, actions[index], -1, interfaceId + 1, 1);
            setBounds(interfaceId++, buttonX, buttonY, childId++, widget);

            addHoveredButton(interfaceId, "Interfaces/perks/IMAGE", 4, 102, 29, interfaceId + 1);
            setBounds(interfaceId++, buttonX, buttonY, childId++, widget);
            interfaceId++;

            addText(interfaceId, actions[index], font, 2, 0xFE981F, true, true);
            setBounds(interfaceId++, buttonX + 51, buttonY + 6, childId++, widget);
        }

        addText(interfaceId, "Prestige Points: ", font, 1, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 339, y + 48, childId++, widget);

        //points dispay
        addText(interfaceId, "" + interfaceId, font, 0, 0xbf751d, false, true);
        interfaceCache[interfaceId].aBoolean235 = true;
        setBounds(interfaceId++, x + 435, y + 50, childId++, widget);

        addText(interfaceId, "Description", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 325, y + 80, childId++, widget);

        addText(interfaceId, "Healing Blade" + interfaceId, font, 1, 0xbf751d, true, true);
        setBounds(interfaceId++, x + 361, y + 98, childId++, widget);

        addText(interfaceId, "Perks", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 107, y + 80, childId++, widget);

        addText(interfaceId, "Price:", font, 1, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 266, y + 243, childId++, widget);

        //price value
        addText(interfaceId, ""+ interfaceId, font, 0, 0xbf751d, true, true);
        setBounds(interfaceId++, x + 321, y + 246, childId++, widget);

        addText(interfaceId, "Earn Prestige Points by Prestiging Skills!", font, 0, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 255, y + 278, childId++, widget);

        addHoverButton(interfaceId, "Interfaces/perks/IMAGE", 3, 102, 29, "Purchase", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, x + 361, y + 237, childId++, widget);

        addHoveredButton(interfaceId, "Interfaces/perks/IMAGE", 4, 102, 29, interfaceId + 1);
        setBounds(interfaceId++, x + 361, y + 237, childId++, widget);
        interfaceId++;

        addText(interfaceId, "Purchase", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, (x + 361) + 51, (y + 237) + 6, childId++, widget);

        int descriptionLayerId = interfaceId++;
        RSInterface descriptionLayer = addInterface(descriptionLayerId);
        descriptionLayer.width = 206;
        descriptionLayer.height = 115;
        descriptionLayer.scrollMax = 1000;
        int descriptionLayerY = 7;
        int subChild = 0;

        setChildren(3, descriptionLayer);

        //description box
        addText(interfaceId," ", font, 0, 0xcccccc, false, true);
        setBounds(interfaceId++, 9, descriptionLayerY, subChild++, descriptionLayer);
        addText(interfaceId," ", font, 0, 0xcccccc, false, true);
        setBounds(interfaceId++, 9, descriptionLayerY+12, subChild++, descriptionLayer);
        addText(interfaceId," ", font, 0, 0xcccccc, false, true);
        setBounds(interfaceId++, 9, descriptionLayerY+24, subChild++, descriptionLayer);

        int perkLayerId = interfaceId++;
        RSInterface perkLayer = addInterface(perkLayerId);
        perkLayer.width = 206;
        perkLayer.height = 195;
        perkLayer.scrollMax = 1000;
        subChild = 0;
        int totalPerks = 50;

        setChildren(3 * totalPerks, perkLayer);

        for(int index = 0, buttonX = 0, buttonY = 0; index < totalPerks; index++, buttonY += 21) {
            addHoverButton(interfaceId, "Interfaces/perks/BUTTON", 1, 206, 21, "Select", -1, interfaceId + 1, 1);
            setBounds(interfaceId++, buttonX, buttonY, subChild++, perkLayer);

            addHoveredButton(interfaceId, "Interfaces/perks/BUTTON", 2, 206, 21, interfaceId + 1);
            setBounds(interfaceId++, buttonX, buttonY, subChild++, perkLayer);
            interfaceId++;

            addText(interfaceId, "Always Adze" + interfaceId, font, 1, 0xfe3200, true, true);//old color 0x01ff00
            setBounds(interfaceId++, buttonX + 108, buttonY + 2, subChild++, perkLayer);
        }

        setBounds(perkLayerId, x + 13, y + 97, childId++, widget);
        setBounds(descriptionLayerId, x + 251, y + 115, childId++, widget);
    }

}
