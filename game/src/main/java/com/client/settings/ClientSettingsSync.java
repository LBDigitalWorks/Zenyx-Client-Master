package com.client.settings;

import com.client.Client;
import com.client.ClientPreferencesFacade;
import com.client.features.settings.Preferences;

public final class ClientSettingsSync {

    private ClientSettingsSync() {
    }

    public static void syncAudioToPreferences(Preferences preferences) {
        SettingManager manager = getSettingManager();
        if (manager == null) {
            return;
        }

        preferences.soundVolume = manager.getSoundEffectVolume();
        preferences.areaSoundVolume = manager.getAreaSoundEffectVolume();
        preferences.musicVolume = manager.getMusicVolume();
    }

    public static void syncAudioToPreferences(ClientPreferencesFacade preferences) {
        SettingManager manager = getSettingManager();
        if (manager == null) {
            return;
        }

        preferences.setSoundVolume(manager.getSoundEffectVolume());
        preferences.setAreaSoundVolume(manager.getAreaSoundEffectVolume());
        preferences.setMusicVolume(manager.getMusicVolume());
    }

    public static void syncAudioToProfile(Preferences preferences) {
        SettingManager manager = getSettingManager();
        if (manager == null) {
            return;
        }

        manager.setSoundEffectVolume((int) preferences.soundVolume);
        manager.setAreaSoundEffectVolume((int) preferences.areaSoundVolume);
        manager.setMusicVolume((int) preferences.musicVolume);
    }

    public static void syncAudioToProfile(ClientPreferencesFacade preferences) {
        SettingManager manager = getSettingManager();
        if (manager == null) {
            return;
        }

        manager.setSoundEffectVolume((int) preferences.getSoundVolume());
        manager.setAreaSoundEffectVolume((int) preferences.getAreaSoundVolume());
        manager.setMusicVolume((int) preferences.getMusicVolume());
    }

    public static int getMusicVolume() {
        SettingManager manager = getSettingManager();
        if (manager != null) {
            return manager.getMusicVolume();
        }
        return (int) Preferences.getPreferences().musicVolume;
    }

    public static int getSoundEffectVolume() {
        SettingManager manager = getSettingManager();
        if (manager != null) {
            return manager.getSoundEffectVolume();
        }
        return (int) Preferences.getPreferences().soundVolume;
    }

    public static int getAreaSoundEffectVolume() {
        SettingManager manager = getSettingManager();
        if (manager != null) {
            return manager.getAreaSoundEffectVolume();
        }
        return (int) Preferences.getPreferences().areaSoundVolume;
    }

    private static SettingManager getSettingManager() {
        Client client = Client.instance;
        if (client == null) {
            return null;
        }
        return client.settingManager;
    }
}
