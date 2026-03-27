package com.client.settings;

import java.io.File;

import net.runelite.client.RuneLite;

/**
 * Central location for client-managed settings files.
 */
public final class ClientStoragePaths {

    private ClientStoragePaths() {
    }

    public static File preferencesFile() {
        return new File(RuneLite.RUNELITE_DIR, "properties.json");
    }

    public static File accountDataFile() {
        return new File(RuneLite.RUNELITE_DIR, "account_data.dat");
    }

    public static File profileSettingsFile() {
        return new File(RuneLite.PROFILES_DIR, "settings.json");
    }

    public static File legacySerializedSettingsFile() {
        return new File(RuneLite.PROFILES_DIR, "settings.ser");
    }
}
