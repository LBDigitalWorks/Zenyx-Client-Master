package com.client.graphics.interfaces.impl;

import com.client.Client;
import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class aioShops extends RSInterface {
    private static int containerInterfaceId;
    private static int containerWidth = 132;
    private static int containerHeight = 23;
    private static int containerCount = containerWidth * containerHeight;

    public static void build(TextDrawingArea[] font) {
        setupPanel(font);
    }

    private static void setupPanel(TextDrawingArea[] font) {
        int interfaceId = 56000;
        int childId = 0;
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/allshops/";

        setChildren(200, widget);


        int x = 17, y = 13;
        addSprite(interfaceId, 0, spriteLoc + "IMAGE");//bg
        setBounds(interfaceId++, x, y, childId++, widget);

        int quanityStart = x + 10;
        //titles
        addText(interfaceId, "All Elvarg Shops! Currently Viewing:", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,quanityStart, y+9, childId++, widget);

        //current shop name
        addText(interfaceId, String.valueOf(interfaceId), font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++,quanityStart + 235, y+9, childId++, widget);
        interfaceId++;




        // Create the main scrollable container
        RSInterface container = addInterface(interfaceId++);
        container.height = 260;
        container.width = 120;
        container.scrollMax = 500; // Make sure this is enough for your content
        RSInterface stringContainer = addStringContainer(interfaceId++, container.id, Client.instance.newRegularFont, false, 0x000080, false, 5, "Test");
        stringContainer.invAutoScrollHeightOffset = 6;
        container.totalChildren(1);
        container.child(0, interfaceId++, x+140, y);
        widget.child(childId++, container.id, 8, 50);



        // Container
        RSInterface scrollable = addInterface(interfaceId++);
        scrollable.scrollMax = 6000;
        scrollable.width = 470;
        scrollable.height = 260;
        scrollable.invAutoScrollHeightOffset = 0;
        scrollable.totalChildren(1);
        containerInterfaceId = interfaceId;
        addItemContainerAutoScrollable(interfaceId, 9, 400, 3, 3, false, scrollable.id, "Price","Buy 1", "Buy 5", "Buy 10", "Buy X");
        //get(containerInterfaceId).hideInvStackSizes = true;
        scrollable.child(0, interfaceId++, x+140, y);

        widget.child(childId++, scrollable.id, 8, 50);

        widget.child(childId++, Interfaces.CLOSE_BUTTON_SMALL, x + 461, y + 10);
        widget.child(childId++, Interfaces.CLOSE_BUTTON_SMALL_HOVER, x + 461, y + 10);
        interfaceCache[scrollable.id].invAlwaysInfinity = true;
        //update("");
    }

}
