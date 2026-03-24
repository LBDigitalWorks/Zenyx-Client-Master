package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class StaffModeration extends RSInterface {

    public static void build(TextDrawingArea[] font) {
        setupPanel(font);
    }

    private static void setupPanel(TextDrawingArea[] font) {
        int interfaceId = 60000;
        int childId = 0;
        String[] commands = {"CC Mute", "CC Unmute", "Gamble Ban", "Ungamble Ban", "Jail", "Unjail", "Kick", "Ban", "Ban IP", "Find", "Netban", "Net Mute", "Questioning", "Teletome", "Teleto", "Copy Player", "Check Bank", "Check Inventory"};
        RSInterface widget = addInterface(interfaceId++);
        String spriteLoc = "Interfaces/staffmoderation/";

        setChildren(100, widget);


        int x = 17, y = 13;
        addSprite(interfaceId, 0, spriteLoc + "IMAGE");
        setBounds(interfaceId++, x, y, childId++, widget);

        //close button / icon
        addHoverButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, interfaceId + 1, 1);
        setBounds(interfaceId++, x + 461, y + 10, childId++, widget); //Close
        addHoveredButton(interfaceId, "Interfaces/HelpInterface/IMAGE", 3, 16, 16, interfaceId + 1);
        setBounds(interfaceId++, x + 461, y + 10, childId++, widget); //Close
        interfaceId++;

        //window title
        addText(interfaceId, "Staff Moderation", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, x + 242, y + 9, childId++, widget);

        //background box for name info
        addSprite(interfaceId, 0, spriteLoc + "names");
        setBounds(interfaceId++, x + 8, y + 35, childId++, widget);
        addText(interfaceId, "Selected Player: ", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, x + 68, y + 38, childId++, widget);
        addText(interfaceId, "Selected Action: ", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, x + 68, y + 53, childId++, widget);
        addText(interfaceId, "None", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 128, y + 38, childId++, widget);
        addText(interfaceId, "None", font, 2, 0xFE981F, false, true);
        setBounds(interfaceId++, x + 128, y + 53, childId++, widget);

        //Confirm Button
        addButton(interfaceId, 1, spriteLoc + "button", "Confirm");
        setBounds(interfaceId++, x + 350, y + 40, childId++, widget);
        // Add the text inside the button
        addText(interfaceId, "Confirm", font, 2, 0xFE981F, true, true);
        setBounds(interfaceId++, x+415, y+50, childId++, widget);

        int buttonX = x + 8, buttonY = y + 80;
        int yAdd = 35;
        int textAlignX = 60, textAlignY = 10;

        for (int index = 0; index < 6; index++, buttonY += yAdd) {
            // Add the button
            addButton(interfaceId, 1, spriteLoc + "button", commands[index]);
            setBounds(interfaceId++, buttonX, buttonY, childId++, widget);

            // Add the text inside the button
            addText(interfaceId, commands[index], font, 2, 0xFE981F, true, true);
            setBounds(interfaceId++, buttonX + textAlignX, buttonY + textAlignY, childId++, widget);
        }
        buttonX += 125; buttonY = y + 80;
        for (int index = 6; index < 12; index++, buttonY += yAdd) {
            // Add the button
            addButton(interfaceId, 1, spriteLoc + "button", commands[index]);
            setBounds(interfaceId++, buttonX, buttonY, childId++, widget);

            // Add the text inside the button
            addText(interfaceId, commands[index], font, 2, 0xFE981F, true, true);
            setBounds(interfaceId++, buttonX + textAlignX, buttonY + textAlignY, childId++, widget);
        }
        buttonX += 125; buttonY = y + 80;
        for (int index = 12; index < 18; index++, buttonY += yAdd) {
            // Add the button
            addButton(interfaceId, 1, spriteLoc + "button", commands[index]);
            setBounds(interfaceId++, buttonX, buttonY, childId++, widget);

            // Add the text inside the button
            addText(interfaceId, commands[index], font, 2, 0xFE981F, true, true);
            setBounds(interfaceId++, buttonX + textAlignX, buttonY + textAlignY, childId++, widget);
        }

        addText(interfaceId, "Players:", font, 1, 0xFE981F, true, true);
        setBounds(interfaceId++, x + 430, y + 78, childId++, widget);

        int perkLayerId = interfaceId++;
        RSInterface modLayer = addInterface(perkLayerId);
        modLayer.width = 60;
        modLayer.height = 195;
        modLayer.scrollMax = 1515;//+16 here per player count add - 1515 == 100
        int subChild = 0;
        int playerCount = 101;//set to +1 over the desired player count

        setChildren(playerCount * 3, modLayer);

        for (int i = 0; i < playerCount; i++) {
            addHoverButton(interfaceId, "", 1, 175, 15, "Select", -1, interfaceId + 1, 1);
            setBounds(interfaceId++, 0, 5 + (15 * i ), subChild++, modLayer);

            addText(interfaceId, "", font, 0, 0xfe3200, false, true);//old color 0x01ff00
            setBounds(interfaceId++, 4, 5 + (15 * i), subChild++, modLayer);
        }
        setBounds(perkLayerId, x + 400, y + 100, childId++, widget);
    }

}
