package com.client.features;

import java.awt.event.KeyEvent;

/**
 * @author Leviticus | www.rune-server.ee/members/leviticus
 * @version 1.0
 */
public enum KeyboardAction {

    RELOAD_LAST_PRESET(1, KeyEvent.VK_R, true, false, false),
    LAST_TELEPORT(2, KeyEvent.VK_T, true, false, false),
    OPEN_BANK(3, KeyEvent.VK_B, true, false, false),
    D_Spec(4, KeyEvent.VK_S, true, false, false),
    COLLECTION_LOG(5, KeyEvent.VK_C, true, false, false),
    DONATION_ZONE_TELEPORT(6, KeyEvent.VK_D, true, false, false),
    HOME_TELEPORT(7, KeyEvent.VK_H, true, false, false);

    private final int action;
    private final int key;
    private final boolean shift;
    private final boolean ctrl;
    private final boolean alt;


    KeyboardAction(int action, int key, boolean ctrl, boolean shift, boolean alt) {
        this.action = action;
        this.key = key;
        this.ctrl = ctrl;
        this.shift = shift;
        this.alt = alt;
    }

    public int getAction() {
        return action;
    }

    public boolean canActivate(int key, boolean ctrl, boolean shift, boolean alt) {
        return this.ctrl == ctrl && this.shift == shift && this.alt == alt && this.key == key;
    }
}
