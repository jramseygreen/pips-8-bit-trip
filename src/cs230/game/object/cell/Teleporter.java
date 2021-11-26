/**
 * @author Nick Fisher - 975390, Josh Green 956213
 * @since 28/11/2019
 * @version 1.0
 * Teleporter:
 * A class that allows the creation of an object that moves the player 
 * from one teleporter to another
 */
package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.util.GameIds;

public class Teleporter extends Cell {

	private Teleporter nextTeleporter;

	/**
	 * Teleporter constructor, nextTeleporter is set to null until pair is found
	 * 
	 * @param x
	 *            x coordinate of tp
	 * @param y
	 *            y coordinate of tp
	 */
	public Teleporter(int x, int y) {
		super(x, y, GameIds.CELLS.TELEPORTER);
		this.nextTeleporter = null;
		passable = false;
	}

	/**
	 * Simple method that changes the player's coordinates once they step on to the
	 * teleporter
	 * 
	 * @param player
	 * @param xOffset
	 *            x difference between teleporters
	 * @param yOffset
	 *            y difference between teleporters
	 */
	public void movePlayer(Player player, int xOffset, int yOffset) {
		player.setXCoord(nextTeleporter.getXCoord() + xOffset);
		player.setYCoord(nextTeleporter.getYCoord() + yOffset);
	}

	/**
	 * Getter for next tp
	 * 
	 * @return nextTeleporter Teleporter
	 */
	public Teleporter getNextTeleporter() {
		return this.nextTeleporter;
	}

	/**
	 * nextTeleporter setter
	 * 
	 * @param tp
	 *            Teleporter for the next teleporter
	 */
	public void setNextTeleporter(Teleporter tp) {
		this.nextTeleporter = tp;
	}
}
