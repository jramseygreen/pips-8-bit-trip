package cs230.pane;

import cs230.Profile;

/**
 * Superclass for all controllers for GUIs. Allows easy passing of data between
 * them.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class BaseController {

	/**
	 * Called when the GUI pane is being switched in.
	 */
	public void init() {
	}

	/**
	 * Called when the GUI pane is being switched out.
	 */
	public void dispose() {
	}

	/**
	 * Transfer a long to this controller.
	 * 
	 * @param val
	 *            - the val to receive.
	 */
	public void sendData(long val) {
	}

	/**
	 * Transfer an int to this controller.
	 * 
	 * @param val
	 *            - the val to receive.
	 */
	public void sendData(int val) {
	}

	/**
	 * Transfer a string to this controller.
	 * 
	 * @param sr
	 *            - the string to receive.
	 */
	public void sendData(String sr) {
	}

	/**
	 * Transfer a profile to this controller.
	 * 
	 * @param profile
	 *            - the profile to receive.
	 */
	public void sendData(Profile profile) {
	}
}
