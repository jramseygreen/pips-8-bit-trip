/**
 * @author Nick Fisher - 975390, Josh Green 956213
 * @since 28/11/2019
 * @version 1.0
 * Door:
 * A subclass of barrier, isPassable depends either on the number of tokens or the colour of the key
 */
package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.game.object.item.Token;
import cs230.util.GameIds;
import cs230.util.Message;

public class TokenDoor extends Cell {

	private int numTokens;

	/**
	 * Door constructor, instantiated with a false passable variable and a
	 * DOOR_TOKEN cell type
	 * 
	 * @param x
	 *            - int
	 * @param y
	 *            - int
	 */
	public TokenDoor(int x, int y) {
		super(x, y, GameIds.CELLS.DOOR_TOKEN, false);
	}

	/**
	 * Checks player has enough tokens, if they do, then tokens are subtracted from
	 * total
	 * 
	 * @param level
	 * @param player
	 */
	@Override
	public void playerSteppedOn(Level level, Player player) {
		
		if (player.getTokens() >= this.numTokens) {
			
			level.changeCellAt(xCoord, yCoord, new Cell(getXCoord(), getYCoord(), GameIds.CELLS.GROUND));
			player.setTokens(player.getTokens() - this.numTokens);
		} else {

			new Message("Token Door Information", "Current player tokens: " + player.getTokens(),
					"This door requires " + this.numTokens + " tokens to open.");

		}

	}

	/**
	 * Getter for number of tokens
	 * 
	 * @return int NumTokens
	 */
	public int getNumTokens() {
		return numTokens;
	}

	/**
	 * Setter for number of tokens
	 * 
	 * @param numTokens
	 */
	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}

}
