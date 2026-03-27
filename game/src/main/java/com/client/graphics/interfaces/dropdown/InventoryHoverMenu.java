package com.client.graphics.interfaces.dropdown;

import com.client.Client;
import com.client.graphics.interfaces.MenuItem;
import com.client.graphics.interfaces.RSInterface;

public class InventoryHoverMenu implements MenuItem {
    @Override
    public void select(int optionSelected, RSInterface rsInterface) {
        if (optionSelected == 0) {
				Client.instance.preferences().setInventoryContextMenuEnabled(false);
			} else {
				Client.instance.preferences().setInventoryContextMenuEnabled(true);
			}
			switch(optionSelected){
				case 0: //off
					Client.instance.preferences().setStartMenuColor(0xFFFFFF);
					return;
				case 1: //magenta
					Client.instance.preferences().setStartMenuColor(0xFF00FF);
					return;
				case 2://lime
					Client.instance.preferences().setStartMenuColor(0x00FF00);
					return;
				case 3://cyan
					Client.instance.preferences().setStartMenuColor(0x00FFFF);
					return;
				case 4://red
					Client.instance.preferences().setStartMenuColor(0xFF0000);
					return;
			}
    }
}
