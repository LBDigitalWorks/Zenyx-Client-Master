package com.client.utilities.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.client.Client;
import com.client.settings.ClientStoragePaths;

/**
 * 
 * @author Grant_ | www.rune-server.ee/members/grant_ | 12/10/19
 *
 */
public class SettingsManager {

	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(SettingsManager.class.getName());

	public static final int DEFAULT_FOG_COLOR = 0;
	public static final int DEFAULT_START_MENU_COLOR = 0;
	public static final int DEFAULT_CHAT_COLOR_OPTION = 0;

	public static void saveSettings(Client client) throws IOException {
		// Serialization
		File file = ClientStoragePaths.legacySerializedSettingsFile();
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
		try {
			output.writeObject(client.getUserSettings());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			output.flush();
			output.close();
		}
	}
	
	public static void loadSettings() {
        try {
        	File discover = ClientStoragePaths.legacySerializedSettingsFile();
        	if (!discover.exists()) {
        		Client.setUserSettings(Settings.getDefault());
        		return;
        	}

        	try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(discover))) {
				Settings settings = (Settings) input.readObject();
				input.close();
				if (settings != null) {
					Client.setUserSettings(settings);
				}
			}
       	} catch (IOException ex) {
        	log.severe("Could not load settings, setting to default.");
			Client.setUserSettings(Settings.getDefault());
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
		}
	}
}
