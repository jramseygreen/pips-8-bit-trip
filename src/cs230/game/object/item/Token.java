package cs230.game.object.item;

import cs230.util.GameIds;

/**
 * class represnts the piles of tokens that can be collected to open doors
 * 
 * @author Josh Green 956213
 * @version 1.0
 */
public class Token extends GameItem {

	/**
	 * the number of tokens in this pile
	 */
	private int numToken;

	/**
	 * creates a token object at (xCoord, yCoord) with a defied number of tokens
	 * 
	 * @param xCoord
	 *            the x coordinate of the token
	 * @param yCoord
	 *            the y coordinate of the token
	 * @param numToken
	 *            the number of tokens in this pile
	 */
	public Token(int xCoord, int yCoord, int numToken) {

		super(xCoord, yCoord, GameIds.ITEMS.TOKEN);
		this.numToken = numToken;
	}

	/**
	 * gets the number of tokens in this pile
	 * 
	 * @return an int representing the number of tokens
	 */
	public int getNumToken() {
		return numToken;
	}

	/**
	 * Sets the number of tokens
	 * 
	 * @param numToken
	 *            an int representing how many tokens will be in this pile
	 */
	public void setNumToken(int numToken) {
		this.numToken = numToken;
	}
}
