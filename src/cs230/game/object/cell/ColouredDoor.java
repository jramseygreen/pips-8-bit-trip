/**
 * @author Nick Fisher - 975390
 * @since 28/11/2019
 * @version 1.0
 * Door:
 * A subclass of barrier, isPassable depends either on the number of tokens or the colour of the key
 */
package cs230.game.object.cell;

import java.util.Iterator;

import cs230.game.Level;
import cs230.game.object.actor.Player;
import cs230.game.object.item.GameItem;
import cs230.game.object.item.Key;
import cs230.util.Colour;
import cs230.util.GameIds;

public class ColouredDoor extends Cell {

	private Colour colour;

	/**
	 * Door constructor that sets coords, type, isPassable and colour
	 * 
	 * @param x
	 *            x Coordinate of door
	 * @param y
	 *            y Coordinate of door
	 */
	public ColouredDoor(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.CELLS.DOOR_COL, false);
		this.colour = Colour.RED;
	}

	/**
	 * Method that updates the door based on the player's inventory i.e. whether the
	 * player has a key of corresponding colour
	 * 
	 * @param level
	 *            Level object to make changes to the cell containing door
	 * @param player
	 *            Player object containing inventory
	 */
	@Override
	public void playerSteppedOn(Level level, Player player) {
		Iterator<GameItem> itr = player.getInventory().iterator();
		while (itr.hasNext()) {

			GameItem invItem = itr.next();

			if (invItem.getId().equals(GameIds.ITEMS.KEY)) {

				Key item = (Key) invItem;
				if (item.getColour() == this.colour) {
					level.changeCellAt(xCoord, yCoord, new Cell(getXCoord(), getYCoord(), GameIds.CELLS.GROUND));
					itr.remove();
				}
			}
		}
	}

	/**
	 * Getter for colour
	 * 
	 * @return colour
	 */
	public Colour getColour() {

		return colour;
	}

	/**
	 * Setter for colour
	 * 
	 * @param colour
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
		setTexture(String.valueOf(GameIds.CELLS.DOOR_COL) + colour.getCol());
	}

}
