package cs230.game.object.item;

import cs230.util.Colour;
import cs230.util.GameIds;

/**
 * represents the keys to open doors
 * 
 * @author Josh Green 956213
 * @version 1.0
 */
public class Key extends GameItem {

	/**
	 * the colour of the key
	 */
	private Colour colour;

	/**
	 * creates a key object at the (xCoord, yCoord) with the a defined colour
	 * 
	 * @param xCoord
	 *            the x coordinate of the key
	 * @param yCoord
	 *            the y coordinate of the key
	 * @param colour
	 *            the colour of the key
	 */
	public Key(int xCoord, int yCoord, Colour colour) {

		super(xCoord, yCoord, GameIds.ITEMS.KEY);
		this.colour = colour;
		setTexture(GameIds.ITEMS.KEY + colour.getCol());
	}

	/**
	 * gets the colour of the key
	 * 
	 * @return colour
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * sets the colour attribute of the key object
	 * 
	 * @param colour
	 *            a Colour object
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
