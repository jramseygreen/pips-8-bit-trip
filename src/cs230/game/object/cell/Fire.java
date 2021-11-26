/**
 * @author Nick Fisher - 975390
 * @since 28/11/2019
 * @version 1.0
 * Fire:
 * A barrier subclass that is not passable unless the player has 
 * fireboots in their inventory
 */
package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.util.GameIds;

public class Fire extends Cell {

	/**
	 * Fire constructor
	 * 
	 * @param x
	 *            - x coordinate of particular cell
	 * @param y
	 *            - x coordinate of particular cell
	 * @param item
	 *            - item in cell
	 * @param type
	 *            - type of cell as a string
	 * @param texture
	 *            - javafx image of texture of the cell
	 * @param isPassable
	 *            - boolean variable that changes based on an item in the player's
	 *            inventory
	 */
	public Fire(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.CELLS.FIRE, false);
	}

	/**
	 * Checks if player has valid item to step on to cell
	 * 
	 * @param level
	 * @param player
	 */
	@Override
	public void playerSteppedOn(Level level, Player player) {
		
		if (player.hasInInventory(GameIds.ITEMS.FIRE_BOOT)) {
			setPassable(true);
		} else {
			setPassable(false);
		}
	}
}
