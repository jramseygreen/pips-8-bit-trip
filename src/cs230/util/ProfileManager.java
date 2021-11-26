package cs230.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import cs230.Profile;

/**
 * Manages all Profiles.
 * 
 * @author Sam Harper 975431, Josh Green 956213
 * @version 1.0
 *
 */
public class ProfileManager {

	private static final String PROFILE_FILE = "Profiles.txt";
	private static final String SAVES = "saves/";

	private static ArrayList<Profile> profiles = new ArrayList<>();

	/**
	 * reads in all profiles
	 */
	public static void loadProfiles() {

		ArrayList<String> profileFile = readFile();

		if (profileFile != null && profileFile.size() > 0) {

			parseProfiles(profileFile);
		}
	}

	/**
	 * @param name
	 *            name of profile to delete deletes profile
	 */
	public static void deleteProfile(String name) {

		if (doesProfileExist(name)) {

			Profile profile = getProfile(name);
			profiles.remove(profile);
		}
	}

	/**
	 * @param profile
	 *            current profile
	 * @return true if current profile has a save file already
	 */
	public static boolean doesSaveFileExistFor(Profile profile) {

		File profileSave = new File(SAVES + profile.getName() + ".txt");
		return profileSave.exists();

	}

	/**
	 * @param profileFile
	 *            file in array list form parses all data from a profile txt file
	 */
	private static void parseProfiles(ArrayList<String> profileFile) {

		for (String line : profileFile) {

			String name = getName(line);

			if (!name.isEmpty()) {

				String n = getInfo(line);
				int highestCompletedLevel = Integer.parseInt(n);

				profiles.add(new Profile(name, highestCompletedLevel));
			}
		}
	}

	/**
	 * @param line
	 *            name containing line
	 * @return name returns the name as a string from the file
	 */
	private static String getName(String line) {

		String token = "";
		if (line.contains(LevelIO.FILE_TOKEN_SEPARATOR)) {

			token = line.substring(0, line.indexOf(LevelIO.FILE_TOKEN_SEPARATOR));
		}
		return token;
	}

	/**
	 * @param line
	 *            line contating info in file
	 * @return returns contents between delimmiters
	 */
	private static String getInfo(String line) {

		return line.substring(line.indexOf(LevelIO.FILE_TOKEN_SEPARATOR) + 1);
	}

	/**
	 * @return array list of file contents reads a file into an arraylist
	 */
	private static ArrayList<String> readFile() {

		File pFile = new File(SAVES, PROFILE_FILE);

		ArrayList<String> data = null;

		if (pFile.exists()) {

			data = new ArrayList<>();

			try (Scanner profileScanner = new Scanner(pFile)) {

				while (profileScanner.hasNextLine()) {

					String line = profileScanner.nextLine();

					// remove any possible whitespace
					line = line.replaceAll("\\s+", "");

					// line contains useful data
					if (!line.isEmpty() && !line.startsWith(LevelIO.FILE_COMMENT)) {

						data.add(line);
					}
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		}

		return data;
	}

	/**
	 * saves every profile
	 */
	public static void saveProfiles() {

		ArrayList<String> data = new ArrayList<>();

		for (Profile profile : profiles) {

			data.add(profile.getName() + LevelIO.FILE_TOKEN_SEPARATOR + profile.getHighestCompletedLevel());
		}

		writeFile(data);
	}

	/**
	 * @param data
	 *            array list of strings to write out writes data out to a file
	 */
	private static void writeFile(ArrayList<String> data) {

		File pFile = new File(SAVES + PROFILE_FILE);

		if (pFile.exists()) {

			pFile.delete();
		} else {
			pFile.getParentFile().mkdirs();
		}

		try (PrintWriter fileWriter = new PrintWriter(pFile)) {

			for (String line : data) {

				fileWriter.println(line);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * getter
	 * 
	 * @return all profile names
	 */
	public static String[] getProfileNames() {

		String[] names = new String[profiles.size()];

		for (int i = 0; i < profiles.size(); i++) {

			names[i] = profiles.get(i).getName();
		}

		return names;
	}

	/**
	 * @param name
	 *            profile name
	 * @return true if profile with name of name exists
	 */
	public static boolean doesProfileExist(String name) {

		boolean alreadyExists = false;
		for (Profile profile : profiles) {

			if (profile.getName().equalsIgnoreCase(name)) {

				alreadyExists = true;
			}
		}

		return alreadyExists;
	}

	/**
	 * @param profile
	 *            profile to add to profiles array adds profile to profile array
	 */
	public static void addProfile(Profile profile) {

		profiles.add(profile);
	}

	/**
	 * getter
	 * 
	 * @param name
	 *            name of profile to get
	 * @return profile with given name
	 */
	public static Profile getProfile(String name) {

		Profile foundProfile = null;
		for (Profile profile : profiles) {

			if (profile.getName().equalsIgnoreCase(name)) {
				foundProfile = profile;
			}
		}

		return foundProfile;
	}
}
