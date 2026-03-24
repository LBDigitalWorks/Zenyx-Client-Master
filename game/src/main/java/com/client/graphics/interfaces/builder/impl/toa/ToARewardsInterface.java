package com.client.graphics.interfaces.builder.impl.toa;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.builder.InterfaceBuilder;
import com.client.graphics.interfaces.impl.Interfaces;

public class ToARewardsInterface extends InterfaceBuilder {
    private final String spriteLoc = "Interfaces/TOA/";

    public ToARewardsInterface() {
        super(12000);
    }

    @Override
    public void build() {
        TextDrawingArea[] font = defaultTextDrawingAreas;
        int x = 120;
        int y = 74;

        addSprite(nextInterface(), 0, spriteLoc + "IMAGE");
        child(x, y);

        // Compact chest-card rewards layout.
        addItemContainer(nextInterface(), 2, 3, 18, 12, true);
        child(x + 20, y + 44);

        // Claim to inventory button.
        addButton(nextInterface(), 0, spriteLoc + "button", 36, 36, "Claim To Inventory", 1);
        child(x + 14, y + 140);
        addSprite(nextInterface(), 0, spriteLoc + "backpack");
        child(x + 18, y + 148);

        // Claim to bank button.
        addButton(nextInterface(), 0, spriteLoc + "button", 36, 36, "Claim To Bank", 1);
        child(x + 57, y + 140);
        addSprite(nextInterface(), 0, spriteLoc + "bank");
        child(x + 65, y + 149);

        child(Interfaces.CLOSE_BUTTON_SMALL, x + 250, y + 5);
        child(Interfaces.CLOSE_BUTTON_SMALL_HOVER, x + 250, y + 5);

        addText(nextInterface(), "Tombs of Amascut", font, 2, 0xFE981F, true, true);
        child(x + 138, y + 8);
    }
}
