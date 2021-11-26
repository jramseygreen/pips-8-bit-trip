package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.util.GameIds;

/**
 * crumble tiles only allow you to pass once
 * 
 * @author Josh Green 956213
 * @version 1.0
 *
 */
public class Crumble extends Cell {

	private boolean isCrumbled;

	/**
	 * sets up crumble tile as uncrumbled
	 * 
	 * @param xCoord
	 *            x coordinate
	 * @param yCoord
	 *            y coordinate
	 */
	public Crumble(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.CELLS.CRUMBLE, true);
		isCrumbled = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.game.object.cell.Cell#playerSteppedOn(cs230.game.Level,
	 * cs230.game.object.actor.Player)
	 */
	public void playerSteppedOn(Level level, Player player) {
		isCrumbled = true;
		this.setTexture("crumbleTrue");
	}

	/**
	 * @return true if this cell is crumbled
	 */
	public boolean isCrumbled() {
		return isCrumbled;
	}

}
