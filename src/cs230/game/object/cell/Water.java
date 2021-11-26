/**
 * @author Nick Fisher - 975390
 * @since 28/11/2019
 * @version 1.0
 * Water:
 * Subclass of barrier, isPassable depends on whether the player has flippers in their inventory
 */
package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.util.GameIds;

public class Water extends Cell {

	/**
	 * Constructor for water, by default it is not passable
	 * @param xCoord int
	 * @param yCoord int
	 */
	public Water(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.CELLS.WATER, false);
	}
	
	/**
	 * Method to check if player has valid item to step on to cell
	 * @param level
	 * @param player
	 */
	@Override
	public void playerSteppedOn(Level level, Player player) {
		
		if (player.hasInInventory(GameIds.ITEMS.FLIPPERS)) {
			setPassable(true);
		} else {
			setPassable(false);
		}
	}
}
