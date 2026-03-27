package com.client.graphics.interfaces.dropdown;

import com.client.Client;
import com.client.graphics.interfaces.MenuItem;
import com.client.graphics.interfaces.RSInterface;

public class StretchedModeMenu implements MenuItem {


    public static void updateStretchedMode(boolean stretched) {

    }

    @Override
    public void select(int optionSelected, RSInterface rsInterface) {
        boolean bool = optionSelected == 0;
        if (Client.instance.preferences().isStretchedModeEnabled() == bool)
            return;
        Client.instance.preferences().setStretchedModeEnabled(bool);
        updateStretchedMode(bool);
    }
}
