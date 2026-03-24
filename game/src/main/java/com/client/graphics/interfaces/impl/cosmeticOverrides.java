package com.client.graphics.interfaces.impl;/*
package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

import java.awt.*;

public class cosmeticOverrides extends RSInterface {

    public static void build(TextDrawingArea[] font) {
        setupCosmetics(font);
        setupHelp(font);
    }

    static int buttonX = 366;
    static int buttonY = 276;

    private static void setupCosmetics(TextDrawingArea[] font) {
        int interfaceId = 57000;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/cosmeticOverrides/";

        setChildren(17, widget);


        int x = 80, y = 13;
        addSprite(interfaceId, 1, spriteLoc + "BG");
        setBounds(interfaceId++, 5, 5, childId++, widget);

        addSprite(interfaceId, 0, spriteLoc + "Slots");
        setBounds(interfaceId++, x + 250, y + 50, childId++, widget);

        //close button / icon
        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, 477, 12, childId++, widget); //Close
        addHoveredButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, interfaceId + 1);
        setBounds(interfaceId++, 477, 12, childId++, widget); //Close
        interfaceId++;

        int quanityStart = x + 80;

        int slotX = 386;
        int slotY = 72;
        int yOffset = 40;
        int xOffset = 40;
        //titles
        addText(interfaceId, "Elvarg's Cosmetic Overrides", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,quanityStart, y+9, childId++, widget);

        //head slot - 57006
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX, slotY, childId++, widget);

        //cape slot - 57007
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX-xOffset, slotY+yOffset, childId++, widget);

        //amulet slot - 57008
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX, slotY+yOffset, childId++, widget);

        //weapon slot - 57009
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX-xOffset, slotY+(yOffset*2), childId++, widget);

        //body slot - 57010
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX, slotY+(yOffset*2), childId++, widget);

        //shield slot - 57011
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX+xOffset, slotY+(yOffset*2), childId++, widget);

        //legs slot - 57012
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX, slotY+(yOffset*3), childId++, widget);

        //gloves slot - 57013
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX-xOffset, slotY+(yOffset*4), childId++, widget);

        //boots slot - 57014
        addItemContainer(interfaceId, 1, 1, 1, 1, false, "Remove");
        setBounds(interfaceId++, slotX, slotY+(yOffset*4), childId++, widget);

        //char model
        addChar(interfaceId);
        setBounds(interfaceId++, 115, 195, childId++, widget);

        addHoverButton(interfaceId, spriteLoc+"button", 1, 72, 32, "How To", -1, interfaceId+1, 1);
        setBounds(interfaceId++, buttonX, buttonY, childId++, widget);
        addText(interfaceId, "How To", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, buttonX + 15, buttonY + 9, childId++, widget);
    }

    private static void setupHelp(TextDrawingArea[] font){
        int interfaceId = 57100;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/cosmeticOverrides/";

        setChildren(9, widget);


        int x = 5, y = 5;
        addSprite(interfaceId, 1, spriteLoc + "BG");
        setBounds(interfaceId++, x, y, childId++, widget);

        //close button / icon
        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, 477, 12, childId++, widget); //Close
        addHoveredButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, interfaceId + 1);
        setBounds(interfaceId++, 477, 12, childId++, widget); //Close
        interfaceId++;

        addText(interfaceId, "Elvarg's Cosmetic Overrides", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,160, 22, childId++, widget);


        int textStart = 270;
        addText(interfaceId, "How to use custom cosmetics!", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, textStart,45, childId++, widget);

        addChar(interfaceId);
        //drawNpcOnInterface(interfaceId, 658, 1500);
        setBounds(interfaceId++, 115, 195, childId++, widget);

        addText(interfaceId,
                "While the interface is open, all \\n" +
                     "you need to do is click on any\\n" +
                     "gear piece you wish to have as\\n" +
                     "a cosmetic override!\\n\\n\\n\\n" +
                     "To remove a cosmetic piece\\n" +
                     "simply click the item inside the\\n" +
                     "interface and it will be placed\\n" +
                     "back into your inventory.", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, textStart,75, childId++, widget);

        addHoverButton(interfaceId, spriteLoc+"button", 1, 72, 32, "Go Back", -1, interfaceId+1, 1);
        setBounds(interfaceId++, buttonX, buttonY, childId++, widget);
        addText(interfaceId, "Go Back", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, buttonX + 12, buttonY + 9, childId++, widget);
    }
}
*/
