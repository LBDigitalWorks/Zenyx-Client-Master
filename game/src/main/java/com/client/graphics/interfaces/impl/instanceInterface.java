package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class instanceInterface extends RSInterface {

    public static void build(TextDrawingArea[] font) {
        setupPanel(font);
    }

    private static void setupPanel(TextDrawingArea[] font) {
        int interfaceId = 62000;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/instances/";

        setChildren(200, widget);


        int x = 17, y = 13;
        addSprite(interfaceId, 0, spriteLoc + "image");
        setBounds(interfaceId++, x, y, childId++, widget);

        int quanityStart = x + 240;
        //spawn quantity
        addText(interfaceId, "Spawn Quantity:", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,quanityStart, y+9, childId++, widget);
        //minus buttons
        addHoverButton(interfaceId, spriteLoc + "minus", 1, 16, 16, "Minus 1", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, quanityStart + 110, y + 10, childId++, widget);
        interfaceId++;
        //text between minus and plus
        addText(interfaceId, "0", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,quanityStart + 125, y+10, childId++, widget);
        //plus buttons
        addHoverButton(interfaceId, spriteLoc + "plus", 1, 16, 16, "Add 1", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, quanityStart + 155, y + 10, childId++, widget);
        interfaceId++;
        //enter X
        addHoverButton(interfaceId, spriteLoc + "x", 0, 16, 16, "Enter X", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, quanityStart + 171, y + 10, childId++, widget);
        interfaceId++;

        //close button / icon
        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, x + 461, y + 10, childId++, widget); //Close
        interfaceId++;

        //window title
        addText(interfaceId, "Instance Selector", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 10, y + 9, childId++, widget);

        //Text above scroll box
        addText(interfaceId, "NPC                       Cost Per Spawn (in plat tokens)", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 14, y + 40, childId++, widget);

        int perkLayerId = interfaceId++;
        int maxEntries = 50;//set to +1 over the desired player count
        RSInterface modLayer = addInterface(perkLayerId);
        modLayer.width = 450;
        modLayer.height = 230;
        modLayer.scrollMax = maxEntries*30;
        int subChild = 0;

        setChildren(maxEntries * 7, modLayer);

        for (int i = 0; i < maxEntries; i++) {
            addHoverButton(interfaceId, "", 1, 450, 15, "Select", -1, interfaceId + 1, 1);
            setBounds(interfaceId++, 0, 5 + (30 * i ), subChild++, modLayer);

            //npc name
            addText(interfaceId, "", font, 0, 0xfe3200, false, true);
            setBounds(interfaceId++, 4, 5 + (30 * i), subChild++, modLayer);


            //cost per spawn
            addText(interfaceId, "", font, 0, 0x0de031, false, true);
            setBounds(interfaceId++, 120, 5 + (30 * i), subChild++, modLayer);

            //total cost
            addText(interfaceId, "", font, 0, 0xFE981F, false, true);
            setBounds(interfaceId++, 190, 5 + (30 * i), subChild++, modLayer);
            addText(interfaceId, "", font, 0, 0x0de031, false, true);
            setBounds(interfaceId++, 245, 5 + (30 * i), subChild++, modLayer);




            //adds line between entries
            addSprite(interfaceId, 0,spriteLoc + "line");
            setBounds(interfaceId++, 0, 25 + (30 * i), subChild++, modLayer);
            addSprite(interfaceId, 0,spriteLoc + "line");
            setBounds(interfaceId++, 100, 25 + (30 * i), subChild++, modLayer);
        }
        setBounds(perkLayerId, x + 10, y + 60, childId++, widget);

        addHoverButton(interfaceId, spriteLoc + "check", 1, 16, 16, "Confirm", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, quanityStart + 194, y + 10, childId++, widget); //Confirm

    }

}
