package com.client.sound;

import com.client.settings.ClientSettingsSync;

public enum SoundType {
    MUSIC, SOUND, AREA_SOUND
    ;

    public double getVolume() {
        switch (this) {
            case MUSIC:
                return ClientSettingsSync.getMusicVolume();
            case SOUND:
                return ClientSettingsSync.getSoundEffectVolume();
            case AREA_SOUND:
                return ClientSettingsSync.getAreaSoundEffectVolume();
            default:
                throw new IllegalStateException("Didn't handle " + toString());
        }
    }
}
