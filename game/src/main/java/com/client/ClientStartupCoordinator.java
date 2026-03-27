package com.client;

import com.client.definitions.NpcDefinition;
import com.client.definitions.ObjectDefinition;
import com.client.graphics.interfaces.RSInterface;
import com.client.utilities.GetNetworkAddress;

final class ClientStartupCoordinator {

    private ClientStartupCoordinator() {
    }

    static void initializeMapbackMasks(
        Sprite mapBack,
        int[] horizontalOffsets,
        int[] horizontalWidths,
        int[] verticalOffsets,
        int[] verticalWidths
    ) {
        for (int pixelY = 0; pixelY < 33; pixelY++) {
            int start = 999;
            int end = 0;
            for (int pixelX = 0; pixelX < 34; pixelX++) {
                if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0) {
                    if (start == 999) {
                        start = pixelX;
                    }
                    continue;
                }
                if (start == 999) {
                    continue;
                }
                end = pixelX;
                break;
            }
            horizontalOffsets[pixelY] = start;
            horizontalWidths[pixelY] = end - start;
        }

        for (int pixelY = 1; pixelY < 153; pixelY++) {
            int start = 999;
            int end = 0;
            for (int pixelX = 24; pixelX < 177; pixelX++) {
                if (mapBack.myPixels[pixelX + pixelY * mapBack.myWidth] == 0 && (pixelX > 34 || pixelY > 34)) {
                    if (start == 999) {
                        start = pixelX;
                    }
                    continue;
                }
                if (start == 999) {
                    continue;
                }
                end = pixelX;
                break;
            }
            verticalOffsets[pixelY - 1] = start - 24;
            verticalWidths[pixelY - 1] = end - start;
        }
    }

    static String resolveMacAddress() {
        try {
            String macAddress = GetNetworkAddress.GetAddress("mac");
            if (macAddress == null) {
                return "";
            }
            if (Configuration.developerMode) {
                System.out.println(macAddress);
            }
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    static void finishLoginBootstrap(Client client) {
        client.setConfigButtonsAtStartup();
        ClientLoginBootstrap.restoreRememberedState(client);
        client.drawLoadingText(100, "Preparing game engine");

        ObjectDefinition.clientInstance = client;
        NpcDefinition.clientInstance = client;

        if (Configuration.PRINT_EMPTY_INTERFACE_SECTIONS) {
            if (Configuration.developerMode) {
                RSInterface.printEmptyInterfaceSections();
            } else {
                System.err.println("PRINT_EMPTY_INTERFACE_SECTIONS is toggled but you must be in dev mode.");
            }
        }

        Client.titleLoadingStage = 210;
        ClientLoginBootstrap.showLoginScreen(client);
    }
}
