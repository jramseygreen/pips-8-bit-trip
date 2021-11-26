package cs230.game.object.item;

import cs230.game.object.GameObject;
import cs230.game.object.actor.Player;

/**
 * this class represents game items that can be picked up
 * 
 * @author Josh Green 956213, Sam Harper 975431
 * @version 1.0
 */
public class GameItem extends GameObject {

	/**
	 * string id representing the gameItem type
	 */
	protected String id;

	/**
	 * GameItem constructor
	 * 
	 * @param xCoord
	 *            x coordinate of the game object
	 * @param yCoord
	 *            y coordinate of the game object
	 * @param id
	 *            the id of the game object
	 */
	public GameItem(int xCoord, int yCoord, String id) {
		super(xCoord, yCoord, id);
		this.id = id;
	}

	/**
	 * gets the id of the game item
	 * 
	 * @return string of the game item id type
	 */
	public String getId() {
		return id;
	}

}
