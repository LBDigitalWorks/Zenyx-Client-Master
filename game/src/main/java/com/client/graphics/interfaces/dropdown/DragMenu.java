package com.client.graphics.interfaces.dropdown;

import com.client.Client;
import com.client.graphics.interfaces.MenuItem;
import com.client.graphics.interfaces.RSInterface;

public class DragMenu implements MenuItem {
    @Override
    public void select(int optionSelected, RSInterface rsInterface) {
        switch (optionSelected) {
            case 0:
                Client.instance.preferences().setDragTime(5);
                Client.instance.pushMessage("Your drag time has been set to default");
                break;
            case 1:
                Client.instance.preferences().setDragTime(6);
                Client.instance.pushMessage("Your drag time has been set to 6 (default is 5).");
                break;

            case 2:
                Client.instance.preferences().setDragTime(8);
                Client.instance.pushMessage("Your drag time has been set to 8 (default is 5).");
                break;

            case 3:
                Client.instance.preferences().setDragTime(10);
                Client.instance.pushMessage("Your drag time has been set to 10 (default is 5).");
                break;

            case 4:
                Client.instance.preferences().setDragTime(12);
                Client.instance.pushMessage("Your drag time has been set to 12 (default is 5).");
                break;
        }

    }
}
