/**
 * @author Josh Green 956213
 * @since 27/11/2019
 * @version 1.0
 * Player:
 * An avatar that is used for the user to play with. 
 * It contains behaviour that enhances gameplay
 */
package cs230.game.object.actor;

import java.util.ArrayList;

import cs230.KeyHandler;
import cs230.game.Level;
import cs230.game.object.cell.Cell;
import cs230.game.object.cell.Crumble;
import cs230.game.object.cell.Teleporter;
import cs230.game.object.item.GameItem;
import cs230.util.Direction;
import cs230.util.GameIds;
import cs230.util.Message;

public class Player extends Actor {

	ArrayList<GameItem> inventory = new ArrayList<>();
	private int tokens = 0;
	private boolean hasMoved = false;
	private boolean dead = false;

	/**
	 * Player constructor method.
	 * 
	 * @param xCoord
	 *            the x coordinate of the actor.
	 * @param yCoord
	 *            the y coordinate of the actor.
	 */
	public Player(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.ENTITIES.PLAYER);
	}

	/**
	 * Method checking if the player has inventory.
	 * 
	 * @param inventory
	 *            arraylist holding the gameitems of the player.
	 * @return returns true if the player has inventory and false if he does not.
	 */

	public ArrayList<GameItem> getInventory() {
		return inventory;
	}

	/**
	 * @param name
	 *            item id to search for
	 * @return true when item with item id is in inventory
	 */
	public boolean hasInInventory(String name) {

		boolean foundItem = false;
		for (GameItem item : inventory) {

			if (item.getId().equals(name)) {

				foundItem = true;
			}
		}

		return foundItem;
	}

	/**
	 * adds item to inventory
	 * 
	 * @param item
	 *            item to add to inventory
	 */
	public void addToInventory(GameItem item) {
		inventory.add(item);
	}

	/**
	 * removes item from inventory
	 * 
	 * @param item
	 *            item to remove
	 */
	public void removeFromInventory(GameItem item) {
		inventory.remove(item);
	}

	/**
	 * gets keys being pressed and converts them into a single direction object
	 * 
	 * @return direction object based on key presses
	 */
	private Direction getKey() {

		Direction direction = Direction.NO_DIRECTION;

		if (KeyHandler.isPressed("moveLeft")) {
			direction = Direction.WEST;
			
		} else if (KeyHandler.isPressed("moveRight")) {
			direction = Direction.EAST;
			
		} else if (KeyHandler.isPressed("moveUp")) {
			direction = Direction.NORTH;
			
		} else if (KeyHandler.isPressed("moveDown")) {
			direction = Direction.SOUTH;
		}

		return direction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.game.object.actor.Actor#update(cs230.game.Level)
	 */
	@Override
	public void update(Level level) {

		hasMoved = false;

		/*
		 * check if key pressed getCell in that direction <check for fire/water/wall>
		 * <move>
		 */

		Direction dir = getKey();
		if (dir != Direction.NO_DIRECTION) {
			// texture depends on player direction (flips)
			if (dir.getXOffset() == 1) {
				setTexture(GameIds.ENTITIES.PLAYER + "_alt");
				
			} else {
				setTexture(GameIds.ENTITIES.PLAYER);
			}

			Cell cell = level.getCellAt(xCoord + dir.getXOffset(), yCoord + dir.getYOffset());

			if (cell != null) {

				cell.playerSteppedOn(level, this); // dealing with collision 1

				// moves player if the next cell is passable
				if (cell.isPassable()) {

					xCoord += dir.getXOffset();
					yCoord += dir.getYOffset();
					hasMoved = true;
				}

				/*
				 * dealing with collision 2
				 */

				/*
				 * displays a message with level completion time sets the boolean for completion
				 * 
				 */
				if (cell.getId() == GameIds.CELLS.GOAL) {
					level.setCompleted(true);

					new Message("Current Time taken", "You completed level" + level.getCurLevelName() + " in:",
							level.getTimeTaken() + " seconds!");

				} else if (cell.getId() == GameIds.CELLS.TELEPORTER) {
					/*
					 * checks if nex teleporter exit is passable
					 */
					Teleporter t = (Teleporter) cell;
					if (canExit(t, level, dir)) {
						
						t.movePlayer(this, dir.getXOffset(), dir.getYOffset());
					}

				} else if (cell.getId() == GameIds.CELLS.ICE) {
					/*
					 * moves player until a barrier is hit or there is a passable cell type
					 */
					boolean sliding = true;
					while (sliding) {

						Cell iceCell = level.getCellAt(xCoord, yCoord);
						if (iceCell != null && iceCell.getId() == GameIds.CELLS.ICE) {

							xCoord += dir.getXOffset();
							yCoord += dir.getYOffset();
						} else {

							sliding = false;
						}
					}
					if (!level.getCellAt(xCoord, yCoord).isPassable()) {
						
						xCoord += Direction.invert(dir).getXOffset();
						yCoord += Direction.invert(dir).getYOffset();
					}

				} else if (cell.getId() == GameIds.CELLS.CRUMBLE) {
					// sets crumble status after moving to tile
					Crumble c = (Crumble) cell;
					if (c.isCrumbled()) {

						c.setPassable(false);
					}
				}
			}
		}
	}

	/**
	 * error checks teleporter exit
	 * 
	 * @param t
	 *            Teleporter
	 * @param level
	 *            current level
	 * @param dir
	 *            player direction
	 * @return true if can exit in player direction
	 */
	private boolean canExit(Teleporter t, Level level, Direction dir) {
		
		if (level.getCellAt(t.getNextTeleporter().getXCoord() + dir.getXOffset(),
				t.getNextTeleporter().getYCoord() + dir.getYOffset()).isPassable()) {
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * checks if player moved
	 * 
	 * @return true if player has moved
	 */
	public boolean hasMoved() {
		return hasMoved;
	}

	/**
	 * getter
	 * 
	 * @return number of tokens in player possesion
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * setter
	 * 
	 * @param tokens
	 *            number of tokens to set
	 */
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	/**
	 * checks aliveness
	 * 
	 * @return true if player is dead
	 */
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}