package cs230;

/**
 * holds all info about the profile
 * 
 * @author Sam Harper 975431
 * @version 1.0
 *
 */
public class Profile {

	private String name;
	private int highestCompletedLevel;

	/**
	 * sets up profile attributes
	 * 
	 * @param name
	 *            name for the profile
	 * @param highestLevel
	 *            highest level reached
	 */
	public Profile(String name, int highestLevel) {

		this.name = name;
		this.highestCompletedLevel = highestLevel;
	}

	/**
	 * getter
	 * 
	 * @return profile name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter
	 * 
	 * @param name
	 *            name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets highest level as an integer
	 * 
	 * @return highest completed level number
	 */
	public int getHighestCompletedLevel() {
		return highestCompletedLevel;
	}

	/**
	 * setter
	 * 
	 * @param highestCompletedLevel
	 *            sets the highest level completed
	 */
	public void setHighestCompletedLevel(int highestCompletedLevel) {
		this.highestCompletedLevel = highestCompletedLevel;
	}

}
