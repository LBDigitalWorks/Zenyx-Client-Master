package com.client.features.settings;

import com.client.utilities.FileOperations;
import com.client.Buffer;
import com.client.FileArchive;
import com.client.settings.ClientStoragePaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class InformationFile {

	/**
	 * The location of the file that contains, or will contain information
	 * about the client.
	 */
	private final Path FILE_LOCATION = ClientStoragePaths.accountDataFile().toPath();

	/**
	 * Determines if the user name is remembered or not.
	 */
	private boolean usernameRemembered, passwordRemembered;

	/**
	 * Remembers the roof option
	 */
	private boolean rememberRoof = false;
	private boolean isresized = false;

	/**
	 * Remember items on ground
	 */
	private boolean rememberVisibleItemNames;

	/**
	 * The user name to be stored, if {@link #storedUsername}'s state is true.
	 */
	private String storedUsername = "";
	private String storedPassword = "";

	private String storedUsername1 = "";
	private String storedPassword1 = "";

	private String storedUsername2 = "";
	private String storedPassword2 = "";

	private String storedUsername3 = "";
	private String storedPassword3 = "";

	/**
	 * Writes some information to a file about the client.
	 *
	 * @throws IOException thrown if the file cannot be deleted, cannot be created, or if
	 *                     the backing buffer cannot be written to the file.
	 */
	public void write() throws IOException {
		// Deletes the existing file if it exists
		Files.deleteIfExists(FILE_LOCATION);

		// Create a new file in the system directory if the file does not exist
		Files.createFile(FILE_LOCATION);

		// Create a new stream to store information in
		Buffer stream = Buffer.create();

		// Writes the opcode '0' and a string of characters that make up the players user name
		stream.writeByte(0);
		stream.writeByte(usernameRemembered ? 1 : 0);

		// Writes the opcode '1' and a string of characters that make up the players pass word
		stream.writeByte(1);
		stream.writeHiddenString(storedUsername);

		// Writes the opcode '2' and a string of characters that make up the roofs
		stream.writeByte(2);
		stream.writeByte(rememberRoof ? 1 : 0);

		// Writes the opcode '3' and a string of characters that make up the ground item names
		stream.writeByte(3);
		stream.writeByte(rememberVisibleItemNames ? 1 : 0);

		stream.writeByte(4);
		stream.writeByte(passwordRemembered ? 1 : 0);

		// Writes the opcode '1' and a string of characters that make up the players pass word
		stream.writeByte(5);
		stream.writeHiddenString(storedPassword);

		stream.writeByte(6);
		stream.writeByte(isresized ? 1 : 0);

		stream.writeByte(7);
		stream.writeHiddenString(storedUsername1);

		stream.writeByte(8);
		stream.writeHiddenString(storedUsername2);

		stream.writeByte(9);
		stream.writeHiddenString(storedUsername3);

		stream.writeByte(10);
		stream.writeHiddenString(storedPassword1);

		stream.writeByte(11);
		stream.writeHiddenString(storedPassword2);

		stream.writeByte(12);
		stream.writeHiddenString(storedPassword3);
		// Writes all bytes to the file from a new byte array which has been resized
		FileOperations.writeFile(FILE_LOCATION.toString(), Arrays.copyOf(stream.payload, stream.currentPosition));
	}

	/**
	 * Reads some information from the file, if the file exists.
	 *
	 * @throws IOException           refer to the function {@link FileArchive#getBytesFromFile(File)}
	 * @throws IllegalStateException thrown if an opcode read cannot be found
	 */
	public void read() throws IOException, IllegalStateException {
		// Determines the location of the file and generates one based off of the path
		File file = FILE_LOCATION.toFile();

		// Determines if the file exists, and discontinues if it does not
		if (!file.exists()) {
			//throw some no file found exception if necessary
			return;
		}

		// Creates a new byte array with the information from the file
		byte[] buffer = FileArchive.getBytesFromFile(file);

		// Creates a new stream using the byte buffer as the backing array
		Buffer stream = new Buffer(buffer);

		// Continues to read from the buffer until it can no longer
		while (stream.currentPosition < buffer.length) {

			// Reads the first byte that determines what data we're reading
			int opcode = stream.readSignedByte();

			// Here we read information from the underlays depending on the opcode
			switch (opcode) {
				case 0:
					usernameRemembered = stream.readSignedByte() == 1;
					break;

				case 1:
					storedUsername = stream.readHiddenString();
					break;

				case 2:
					rememberRoof = stream.readSignedByte() == 1;
					break;

				case 3:
					rememberVisibleItemNames = stream.readSignedByte() == 1;
					break;

				case 4:
					passwordRemembered = stream.readSignedByte() == 1;
					break;

				case 5:
					storedPassword = stream.readHiddenString();
					break;

				case 6:
					isresized = stream.readSignedByte() == 1;
					break;

				case 7:
					storedUsername1 = stream.readHiddenString();
					break;

				case 8:
					storedUsername2 = stream.readHiddenString();
					break;

				case 9:
					storedUsername3 = stream.readHiddenString();
					break;

				case 10:
					storedPassword1 = stream.readHiddenString();
					break;

				case 11:
					storedPassword2 = stream.readHiddenString();
					break;

				case 12:
					storedPassword3 = stream.readHiddenString();
					break;

				default:
					throw new IllegalStateException("Opcode #" + opcode + " does not exist.");
			}
		}
	}

	/**
	 * Modifies the current string that is being stored as the user name
	 *
	 * @param storedUsername the new stored user name
	 */
	public void setStoredUsername(String storedUsername) {
		this.storedUsername = storedUsername;
	}

	public void setStoredUsername1(String storedUsername1) {
		this.storedUsername1 = storedUsername1;
	}

	public void setStoredUsername2(String storedUsername2) {
		this.storedUsername2 = storedUsername2;
	}

	public void setStoredUsername3(String storedUsername3) {
		this.storedUsername3 = storedUsername3;
	}

	/**
	 * Retrieves the currently stored user name
	 *
	 * @return the stored user name
	 */
	public String getStoredUsername() {
		return storedUsername;
	}

	public String getStoredUsername1() {
		return storedUsername1;
	}

	public String getStoredUsername2() {
		return storedUsername2;
	}

	public String getStoredUsername3() {
		return storedUsername3;
	}

	/**
	 * Modifies the current string that is being stored as the user name
	 *
	 * @param storedPassword the new stored user name
	 */
	public void setStoredPassword(String storedPassword) {
		this.storedPassword = storedPassword;
	}

	public void setStoredPassword1(String storedPassword1) {
		this.storedPassword1 = storedPassword1;
	}

	public void setStoredPassword2(String storedPassword2) {
		this.storedPassword2 = storedPassword2;
	}

	public void setStoredPassword3(String storedPassword3) {
		this.storedPassword3 = storedPassword3;
	}

	/**
	 * Retrieves the currently stored user name
	 *
	 * @return the stored user name
	 */
	public String getStoredPassword() {
		return storedPassword;
	}

	public String getStoredPassword1() {
		return storedPassword1;
	}

	public String getStoredPassword2() {
		return storedPassword2;
	}

	public String getStoredPassword3() {
		return storedPassword3;
	}

	/**
	 * Determines if the stored user name should be remembered and stored
	 *
	 * @param usernameRemembered {@code true} if the user name should be remembered, otherwise false.
	 */
	public void setUsernameRemembered(boolean usernameRemembered) {
		this.usernameRemembered = usernameRemembered;
	}

	public void setPasswordRemembered(boolean passwordRemembered) {
		this.passwordRemembered = passwordRemembered;
	}
	public boolean setIsresized(boolean isresized) {
		return isresized;
	}

	/**
	 * Determines if the user name is being remembered in the file or not
	 *
	 * @return    {@code true} if the user name is to be remembered.
	 */
	public boolean isUsernameRemembered() {
		return usernameRemembered;
	}

	public boolean isPasswordRemembered() {
		return passwordRemembered;
	}

	public boolean isRememberRoof() {
		return rememberRoof;
	}
	public boolean isresized() {
		return isresized;
	}

	public void setRememberRoof(boolean rememberRoof) {
		this.rememberRoof = rememberRoof;
	}

	/**
	 * @return the rememberVisibleItemNames
	 */
	public boolean isRememberVisibleItemNames() {
		return rememberVisibleItemNames;
	}

	/**
	 * @param rememberVisibleItemNames the rememberVisibleItemNames to set
	 */
	public void setRememberVisibleItemNames(boolean rememberVisibleItemNames) {
		this.rememberVisibleItemNames = rememberVisibleItemNames;
	}
}
