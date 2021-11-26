package cs230.game.object.item;

import cs230.util.GameIds;

/**
 * represents the help items that can be picked up for the tutorial
 * 
 * @author Josh Green 956213
 * @version 1.0
 */
public class Help extends GameItem {

	/**
	 * represents the message that will be displayed when it is picked up
	 */
	private String message;

	/**
	 * Help constructor
	 * 
	 * @param xCoord
	 *            the x coordinate of the help object
	 * @param yCoord
	 *            the y coordinate of the help object
	 * @param message
	 *            the message that will be displayed when picke dup
	 */
	public Help(int xCoord, int yCoord, String message) {
		
		super(xCoord, yCoord, GameIds.ITEMS.HELP);
		this.message = message;
	}

	/**
	 * gets the message
	 * 
	 * @returns the message that should be displayed when picked up
	 */
	public String getMessage() {
		return message;
	}

}
