/**
 * @author Oliver Ryall - 974243, Sam Harper 975431
 * @since 27/11/2019
 * @version 1.0
 * Actor:
 * An abstract class that enforces certain methods and attributes to 
 * be implemented into it's subclasses (Player and enemies).
 */
package cs230.game.object.actor;

import cs230.game.Level;
import cs230.game.object.GameObject;

public abstract class Actor extends GameObject {

	private String id;

	/**
	 * Actor constructor method
	 * 
	 * @param xCoord
	 *            the x coordinate of the actor.
	 * @param yCoord
	 *            the y coordinate of the actor.
	 * @param velX
	 *            for the movement in the x direction.
	 * @param velY
	 *            for the movement in the y direction
	 */
	public Actor(int xCoord, int yCoord, String id) {
		super(xCoord, yCoord, id);
		this.id = id;
	}

	/**
	 * Getter method for String id
	 * 
	 * @return returns id
	 */
	public String getId() {
		return id;
	}

	public abstract void update(Level level);

}
