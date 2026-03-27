package com.client.features.settings;


import java.io.File;
import java.io.IOException;

import com.client.audio.StaticSound;
import com.client.draw.Rasterizer3D;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.impl.NotificationTab;
import com.client.graphics.interfaces.impl.SettingsTabWidget;
import com.client.settings.ClientStoragePaths;
import com.client.settings.ClientSettingsSync;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Preferences {

	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Preferences.class.getName());

	private static Preferences preferences = new Preferences();

	public static Preferences getPreferences() {
		return preferences;
	}

	public static void load() {
		try {
			File preferencesFile = getFileLocation();
			if (preferencesFile.exists()) {
				ObjectNode node = new ObjectMapper().readValue(preferencesFile, ObjectNode.class);

				if (node.has("soundVolume")) {
					preferences.soundVolume = node.get("soundVolume").doubleValue();
					StaticSound.updateSoundEffectVolume((int) preferences.soundVolume);
				}
				if (node.has("areaSoundVolume")) {
					preferences.areaSoundVolume = node.get("areaSoundVolume").doubleValue();
					StaticSound.updateAreaVolume((int) preferences.areaSoundVolume);
				}
				if (node.has("musicVolume")) {
					preferences.musicVolume = node.get("musicVolume").doubleValue();
					StaticSound.updateMusicVolume((int) preferences.musicVolume);
				}
				if (node.has("brightness"))
					preferences.brightness = node.get("brightness").doubleValue();
				if (node.has("screenWidth"))
					preferences.screenWidth = node.get("screenWidth").intValue();
				if (node.has("screenHeight"))
					preferences.screenHeight = node.get("screenHeight").intValue();
				if (node.has("dragTime"))
					preferences.dragTime = node.get("dragTime").intValue();
				if (node.has("hidePetOptions"))
					preferences.hidePetOptions = node.get("hidePetOptions").booleanValue();
				if (node.has("pmNotifications"))
					preferences.pmNotifications = node.get("pmNotifications").booleanValue();
				if (node.has("mode1"))
					preferences.fixed = node.get("mode1").booleanValue();
				if (node.has("groundItemTextShowMoreThan"))
					preferences.groundItemTextShowMoreThan = node.get("groundItemTextShowMoreThan").textValue();
				if (node.has("groundItemTextShow"))
					preferences.groundItemTextShow = node.get("groundItemTextShow").textValue();
				if (node.has("groundItemTextHide"))
					preferences.groundItemTextHide = node.get("groundItemTextHide").textValue();
				if (node.has("groundItemAlwaysShowUntradables"))
					preferences.groundItemAlwaysShowUntradables = node.get("groundItemAlwaysShowUntradables").booleanValue();

				ClientSettingsSync.syncAudioToProfile(preferences);

			} else {
				save();
			}
		} catch (Exception e) {
			log.severe("Error while loading preferences.");
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			ClientSettingsSync.syncAudioToPreferences(preferences);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(getFileLocation(), preferences);
		} catch (IOException e) {
			log.severe("Error while saving preferences.");
			e.printStackTrace();
		}
	}

	public static File getFileLocation() {
		return ClientStoragePaths.preferencesFile();
	}

	public double soundVolume = 127;
	public double areaSoundVolume = 127;
	public double musicVolume = 255;
	public double brightness = 0.75;
	public Boolean fixed = true;
	public int screenWidth;
	public int screenHeight;
	public int dragTime = 5;
	public boolean hidePetOptions;
	public boolean pmNotifications;
	public String groundItemTextShowMoreThan = "";
	public String groundItemTextShow = "";
	public String groundItemTextHide = "";
	public boolean groundItemAlwaysShowUntradables;

	public Preferences() { }

	public void updateClientConfiguration() {
		// Brightness
		Rasterizer3D.setBrightness(brightness);
		SettingsTabWidget.brightnessSlider.setValue(brightness);
		SettingsTabWidget.musicVolumeSlider.setValue(255 - ClientSettingsSync.getMusicVolume());
		SettingsTabWidget.soundVolumeSlider.setValue(127 - ClientSettingsSync.getSoundEffectVolume());
		SettingsTabWidget.areaSoundVolumeSlider.setValue(127 - ClientSettingsSync.getAreaSoundEffectVolume());
		RSInterface.interfaceCache[SettingsTabWidget.HIDE_LOCAL_PET_OPTIONS].active = hidePetOptions;

		NotificationTab.instance.scrollable.update(">value", groundItemTextShowMoreThan);
		NotificationTab.instance.scrollable.update("show", groundItemTextShow);
		NotificationTab.instance.scrollable.update("hide", groundItemTextHide);
		NotificationTab.instance.scrollable.updateButtonText(NotificationTab.ALWAYS_SHOW_UNTRADABLES_BUTTON_ID);
	}


}
