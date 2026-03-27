package com.client;

import com.client.features.settings.Preferences;
import com.client.utilities.settings.Settings;

public final class ClientPreferencesFacade {

    private final Client client;

    ClientPreferencesFacade(Client client) {
        this.client = client;
    }

    public Settings userSettings() {
        return Client.getUserSettings();
    }

    public Preferences preferences() {
        return Preferences.getPreferences();
    }

    public boolean isOldGameframe() {
        return userSettings().isOldGameframe();
    }

    public boolean isGameTimersEnabled() {
        return userSettings().isGameTimers();
    }

    public boolean isAntiAliasingEnabled() {
        return userSettings().isAntiAliasing();
    }

    public boolean isGroundItemOverlayEnabled() {
        return userSettings().isGroundItemOverlay();
    }

    public boolean isFogEnabled() {
        return userSettings().isFog();
    }

    public boolean isSmoothShadingEnabled() {
        return userSettings().isSmoothShading();
    }

    public boolean isTileBlendingEnabled() {
        return userSettings().isTileBlending();
    }

    public boolean isBountyHunterEnabled() {
        return userSettings().isBountyHunter();
    }

    public boolean isEntityTargetEnabled() {
        return userSettings().isShowEntityTarget();
    }

    public int getChatColor() {
        return userSettings().getChatColor();
    }

    public boolean isInventoryContextMenuEnabled() {
        return userSettings().isInventoryContextMenu();
    }

    public boolean isStretchedModeEnabled() {
        return userSettings().isStretchedMode();
    }

    public boolean isPmNotificationsEnabled() {
        return preferences().pmNotifications;
    }

    public int getDragTime() {
        return preferences().dragTime;
    }

    public void setDragTime(int dragTime) {
        preferences().dragTime = dragTime;
    }

    public void updateClientConfiguration() {
        preferences().updateClientConfiguration();
    }

    public boolean isMuted() {
        return client.settingManager.getMuteAll();
    }

    public boolean remembersRoofs() {
        return client.rememberedState.remembersRoofs();
    }

    public boolean remembersVisibleItemNames() {
        return client.rememberedState.remembersVisibleItemNames();
    }

    public void loadRememberedState() {
        client.rememberedState.load();
    }

    public boolean isUsernameRemembered() {
        return client.rememberedState.isUsernameRemembered();
    }

    public String getStoredUsername() {
        return client.rememberedState.getStoredUsername();
    }

    public boolean isPasswordRemembered() {
        return client.rememberedState.isPasswordRemembered();
    }

    public String getStoredPassword() {
        return client.rememberedState.getStoredPassword();
    }

    public void setAntiAliasingEnabled(boolean enabled) {
        userSettings().setAntiAliasing(enabled);
    }

    public void setBountyHunterEnabled(boolean enabled) {
        userSettings().setBountyHunter(enabled);
    }

    public void setChatColor(int chatColor) {
        userSettings().setChatColor(chatColor);
    }

    public void setFogEnabled(boolean enabled) {
        userSettings().setFog(enabled);
    }

    public void setOldGameframe(boolean enabled) {
        userSettings().setOldGameframe(enabled);
    }

    public void setInventoryContextMenuEnabled(boolean enabled) {
        userSettings().setInventoryContextMenu(enabled);
    }

    public void setStartMenuColor(int color) {
        userSettings().setStartMenuColor(color);
    }

    public void setEntityTargetEnabled(boolean enabled) {
        userSettings().setShowEntityTarget(enabled);
    }

    public void setGameTimersEnabled(boolean enabled) {
        userSettings().setGameTimers(enabled);
    }

    public void setSmoothShadingEnabled(boolean enabled) {
        userSettings().setSmoothShading(enabled);
    }

    public void setGroundItemOverlayEnabled(boolean enabled) {
        userSettings().setGroundItemOverlay(enabled);
    }

    public void setTileBlendingEnabled(boolean enabled) {
        userSettings().setTileBlending(enabled);
    }

    public void setStretchedModeEnabled(boolean enabled) {
        userSettings().setStretchedMode(enabled);
    }

    public void setPmNotificationsEnabled(boolean enabled) {
        preferences().pmNotifications = enabled;
    }

    public boolean isHidePetOptionsEnabled() {
        return preferences().hidePetOptions;
    }

    public void setHidePetOptionsEnabled(boolean enabled) {
        preferences().hidePetOptions = enabled;
    }

    public double getBrightness() {
        return preferences().brightness;
    }

    public void setBrightness(double brightness) {
        preferences().brightness = brightness;
    }

    public void setFixedMode(boolean fixed) {
        preferences().fixed = fixed;
    }

    public void setScreenWidth(int width) {
        preferences().screenWidth = width;
    }

    public void setScreenHeight(int height) {
        preferences().screenHeight = height;
    }

    public double getMusicVolume() {
        return preferences().musicVolume;
    }

    public void setMusicVolume(double volume) {
        preferences().musicVolume = volume;
    }

    public double getSoundVolume() {
        return preferences().soundVolume;
    }

    public void setSoundVolume(double volume) {
        preferences().soundVolume = volume;
    }

    public double getAreaSoundVolume() {
        return preferences().areaSoundVolume;
    }

    public void setAreaSoundVolume(double volume) {
        preferences().areaSoundVolume = volume;
    }

    public void setGroundItemTextShowMoreThan(String value) {
        preferences().groundItemTextShowMoreThan = value;
    }

    public void setGroundItemTextShow(String value) {
        preferences().groundItemTextShow = value;
    }

    public void setGroundItemTextHide(String value) {
        preferences().groundItemTextHide = value;
    }

    public boolean isGroundItemAlwaysShowUntradablesEnabled() {
        return preferences().groundItemAlwaysShowUntradables;
    }

    public void setGroundItemAlwaysShowUntradablesEnabled(boolean enabled) {
        preferences().groundItemAlwaysShowUntradables = enabled;
    }
}
