package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class bankKeys extends RSInterface {

    public static void build(TextDrawingArea[] font) {
        setupPanel(font);
    }

    private static void setupPanel(TextDrawingArea[] font) {
        int interfaceId = 10000;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/bankkeys/";
        setChildren(100, widget);

        int x = 120;
        int y = 13;
        addSprite(interfaceId, 3, spriteLoc + "IMAGE");
        setBounds(interfaceId++, x, y, childId++, widget);

        //close button / icon
        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, x + 260, y + 15, childId++, widget); //Close

        interfaceId++;




    }

}
