package com.client;

import java.io.IOException;

import com.client.features.settings.InformationFile;

final class ClientRememberedState {

    private final InformationFile informationFile;

    ClientRememberedState(InformationFile informationFile) {
        this.informationFile = informationFile;
    }

    void load() {
        try {
            informationFile.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isUsernameRemembered() {
        return informationFile.isUsernameRemembered();
    }

    String getStoredUsername() {
        return informationFile.getStoredUsername();
    }

    boolean isPasswordRemembered() {
        return informationFile.isPasswordRemembered();
    }

    String getStoredPassword() {
        return informationFile.getStoredPassword();
    }

    boolean remembersRoofs() {
        return informationFile.isRememberRoof();
    }

    boolean remembersVisibleItemNames() {
        return informationFile.isRememberVisibleItemNames();
    }

    void toggleRememberRoofs() {
        informationFile.setRememberRoof(!informationFile.isRememberRoof());
        save();
    }

    void toggleRememberVisibleItemNames() {
        informationFile.setRememberVisibleItemNames(!informationFile.isRememberVisibleItemNames());
        save();
    }

    private void save() {
        try {
            informationFile.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
