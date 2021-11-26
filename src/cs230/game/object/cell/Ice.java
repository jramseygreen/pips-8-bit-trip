package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.util.GameIds;

/**
 * @author Josh Green 956213
 * @version 1.3 Checks to see if player has boots
 *
 */
public class Ice extends Cell {

	/**
	 * sets up a new ice cell
	 * 
	 * @param xCoord
	 *            cell x coordinate
	 * @param yCoord
	 *            cell y coordinate
	 */
	public Ice(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.CELLS.ICE, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.game.object.cell.Cell#playerSteppedOn(cs230.game.Level,
	 * cs230.game.object.actor.Player)
	 */
	@Override
	public void playerSteppedOn(Level level, Player player) {

		if (player.hasInInventory(GameIds.ITEMS.ICE_SKATE)) {
			setPassable(true);
		} else {
			setPassable(false);
		}
	}

}
