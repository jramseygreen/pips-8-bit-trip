/**
 * @author Sam Harper 975431, Josh Green 956213
 * @since 22/11/2019
 * @version 1.0
 * Cell:
 * An abstract class that enforces certain methods and attributes to 
 * be implemented into it's subclasses (all cell).
 */
package cs230.game.object.cell;

import cs230.game.Level;
import cs230.game.object.GameObject;
import cs230.game.object.actor.Player;
import cs230.game.object.item.GameItem;
import cs230.game.object.item.Help;
import cs230.game.object.item.Token;
import cs230.util.GameIds;
import cs230.util.Message;

public class Cell extends GameObject {

	protected GameItem item;
	protected int id;
	protected boolean passable;

	/**
	 * Cell constructor that takes in a boolean as well, used for barrier typically.
	 * 
	 * @param xCoord
	 *            x Coordinate of the new cell
	 * @param yCoord
	 *            y Coordinate of the new cell
	 * @param id
	 *            The digit representation of the cell, typically used in file
	 *            saving and loading.
	 * @param passable
	 *            Boolean representing whether the player can pass through
	 */
	public Cell(int xCoord, int yCoord, int id, boolean passable) {
		
		super(xCoord, yCoord, String.valueOf(id));
		this.id = id;
		this.item = null;
		this.passable = passable;
	}

	/**
	 * Constructor for cell taking in only 3 arguments
	 * 
	 * @param xCoord
	 *            x Coordinate of the new cell
	 * @param yCoord
	 *            y Coordinate of the new cell
	 * @param id
	 *            The digit representation of the cell, typically used in file
	 *            saving and loading.
	 */
	public Cell(int xCoord, int yCoord, int id) {

		this(xCoord, yCoord, id, true);
	}

	/**
	 * Function that returns whether item held on a cell or not
	 * 
	 * @return Boolean Returns whether an item is held on the cell
	 */
	public boolean onTile() {
		
		if (this.item != null) {
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * Getter for item
	 * 
	 * @return Item in cell
	 */
	public GameItem getItem() {
		return this.item;
	}

	/**
	 * Sets what item is contained within the cell
	 */
	public void setItem(GameItem item) {
		this.item = item;
	}

	/**
	 * Getter for id
	 * 
	 * @return id as an integer
	 */
	public int getId() {

		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 *            Cell id
	 */
	public void setId(int id) {

		this.id = id;
	}

	/**
	 * Checks whether cell contains an item
	 * 
	 * @return Boolean
	 */
	public boolean hasItem() {
		
		if (item != null) {
			return true;
			
		} else
			return false;
	}

	/**
	 * If there is an item on this cell, then this method applies the changes needed
	 * 
	 * @param level
	 *            Level object containing current game status
	 * @param player
	 *            Player object containing current player attributes and methods
	 */
	public void playerSteppedOn(Level level, Player player) {

		if (hasItem()) {
			if (getItem().getId() == GameIds.ITEMS.HELP) {
				Help h = (Help) getItem();
				new Message("Help", "Help", h.getMessage());
			}

			if (getItem().getId() == GameIds.ITEMS.TOKEN) {
				Token item = (Token) getItem();
				player.setTokens(player.getTokens() + item.getNumToken());
				// for (int i=0; i<=item.getNumToken(); player.addToInventory(new
				// Token(0,0,1)));

			} else {
				player.addToInventory(item);
			}
			item = null;
		}

	}

	/**
	 * Getter for passable
	 * 
	 * @return Boolean for whether or not it is passable.
	 */
	public boolean isPassable() {
		return passable;
	}

	public boolean isPassableEnemy() {
		
		if (this.getId() == GameIds.CELLS.GROUND) {
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * Setter for passable
	 * 
	 * @param passable
	 */
	public void setPassable(boolean passable) {
		this.passable = passable;
	}

}
