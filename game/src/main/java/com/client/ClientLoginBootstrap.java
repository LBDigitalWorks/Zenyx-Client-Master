package com.client;

import com.client.features.settings.Preferences;
import com.client.draw.login.LoginState;
import net.runelite.api.GameState;

final class ClientLoginBootstrap {

    private ClientLoginBootstrap() {
    }

    static void restoreRememberedState(Client client) {
        ClientPreferencesFacade preferences = client.preferencesFacade;
        preferences.loadRememberedState();

        if (preferences.isUsernameRemembered()) {
            client.myUsername = preferences.getStoredUsername();
        }
        if (preferences.isPasswordRemembered()) {
            client.setPassword(preferences.getStoredPassword());
        }
        if (preferences.remembersRoofs()) {
            Client.removeRoofs = true;
        }

        Preferences.load();
    }

    static void showLoginScreen(Client client) {
        client.loginScreen.setup();
        client.loginScreen.setLoginState(LoginState.LOGIN);
        client.setGameState(GameState.LOGIN_SCREEN);
    }
}
