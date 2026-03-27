package com.client;

import com.client.audio.StaticSound;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.SettingsTabWidget;
import com.client.graphics.interfaces.settings.Setting;
import com.client.graphics.interfaces.settings.SettingsInterface;
import com.client.settings.ClientSettingsSync;

final class ClientSettingsBootstrap {

    private ClientSettingsBootstrap() {
    }

    static void syncCanvasPreferences(Client client) {
        ClientPreferencesFacade preferences = client.preferencesFacade;
        preferences.setFixedMode(!client.isResized());
        preferences.setScreenWidth(Client.canvasWidth);
        preferences.setScreenHeight(Client.canvasHeight);
    }

    static void applyStartupSettings(Client client) {
        ClientPreferencesFacade preferences = client.preferencesFacade;

        preferences.updateClientConfiguration();
        applyMuteState(client);

        client.setConfigButton(23101, Client.drawOrbs);
        client.setConfigButton(23109, client.splitPrivateChat);
        client.setConfigButton(23107, Client.chatEffectsEnabled);
        client.setConfigButton(23103, preferences.remembersRoofs());
        client.setConfigButton(23105, Client.leftClickAttack);
        client.setConfigButton(23111, preferences.isGameTimersEnabled());
        client.setConfigButton(23113, preferences.isEntityTargetEnabled());
        client.setConfigButton(23115, preferences.remembersVisibleItemNames());
        client.setConfigButton(23118, Client.shiftDrop);
        SettingsTabWidget.updateSettings();
        client.setConfigButton(23001, true);
        client.setConfigButton(953, true);

        client.setDropDown(SettingsInterface.OLD_GAMEFRAME, preferences.isOldGameframe());
        client.setDropDown(SettingsInterface.GAME_TIMERS, preferences.isGameTimersEnabled());
        client.setDropDown(SettingsInterface.ANTI_ALIASING, preferences.isAntiAliasingEnabled());
        client.setDropDown(SettingsInterface.GROUND_ITEM_NAMES, preferences.isGroundItemOverlayEnabled());
        client.setDropDown(SettingsInterface.FOG, preferences.isFogEnabled());
        client.setDropDown(SettingsInterface.SMOOTH_SHADING, preferences.isSmoothShadingEnabled());
        client.setDropDown(SettingsInterface.TILE_BLENDING, preferences.isTileBlendingEnabled());
        client.setDropDown(SettingsInterface.BOUNTY_HUNTER, preferences.isBountyHunterEnabled());
        client.setDropDown(SettingsInterface.ENTITY_TARGET, preferences.isEntityTargetEnabled());
        client.setDropDown(SettingsInterface.CHAT_EFFECT, preferences.getChatColor());
        client.setDropDown(SettingsInterface.INVENTORY_MENU, preferences.isInventoryContextMenuEnabled() ? 1 : 0);
        client.setDropDown(SettingsInterface.STRETCHED_MODE, preferences.isStretchedModeEnabled());
        client.setDropDown(SettingsInterface.PM_NOTIFICATION, preferences.isPmNotificationsEnabled() ? 0 : 1);
    }

    static void applyBrightnessConfig(Client client) {
        RSInterface brightnessWidget = RSInterface.interfaceCache[910];
        if (brightnessWidget.scripts != null && brightnessWidget.scripts[0][0] == 5) {
            int settingIndex = brightnessWidget.scripts[0][1];
            if (client.variousSettings[settingIndex] != brightnessWidget.anIntArray212[0]) {
                client.variousSettings[settingIndex] = brightnessWidget.anIntArray212[0];
                client.updateVarp(settingIndex);
                client.needDrawTabArea = true;
            }
        }
    }

    private static void applyMuteState(Client client) {
        if (!client.preferencesFacade.isMuted()) {
            return;
        }

        StaticSound.updateMusicVolume(0);
        StaticSound.updateSoundEffectVolume(0);
        StaticSound.updateAreaVolume(0);
        SettingsTabWidget.musicVolumeSlider.setValue(255);
        SettingsTabWidget.soundVolumeSlider.setValue(127);
        SettingsTabWidget.areaSoundVolumeSlider.setValue(127);
        ClientSettingsSync.syncAudioToPreferences(client.preferencesFacade);
    }
}
