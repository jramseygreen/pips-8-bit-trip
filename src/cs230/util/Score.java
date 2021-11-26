package cs230.util;

/**
 * @author Sam Harper 975431
 * @version 1.0 creates a score type to hold a profile and a time together
 *
 */
public class Score {

	private String profileName;
	private long time;

	/**
	 * @param profileName
	 *            name of the profile
	 * @param time
	 *            time take in level
	 */
	public Score(String profileName, long time) {

		this.profileName = profileName;
		this.time = time;
	}

	public String getName() {
		return profileName;
	}

	public long getTime() {
		return time;
	}
}
