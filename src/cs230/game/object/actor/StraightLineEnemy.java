package cs230.game.object.actor;

import cs230.game.Level;
import cs230.game.object.cell.Cell;
import cs230.util.Direction;
import cs230.util.GameIds;

/**
 * @author Ben Nettleship
 * @version 1.1 StraightLineEnemy: An actor subclass that has a particular
 *          movement behaviour. It walks in a specific direction until it hits a
 *          wall then turn around
 */
public class StraightLineEnemy extends Actor {

	/**
	 * this attribute holds the direction the enemy travels in this attribute
	 * changes once it hits a wall
	 */
	private Direction direction;

	/**
	 * Straight line enemy constructor
	 * 
	 * @param xCoord
	 *            starting coordinate in the x direction
	 * @param yCoord
	 *            starting coordinate in the y direction
	 * @param dir
	 */
	public StraightLineEnemy(int xCoord, int yCoord, Direction dir) {
		super(xCoord, yCoord, GameIds.ENTITIES.ENEMY_LINE);
		this.direction = dir;
	}

	/**
	 * updates enemy data every turn
	 */
	@Override
	public void update(Level level) {

		Cell c = level.getCellAt(xCoord + direction.getXOffset(), yCoord + direction.getYOffset());

		if (!c.isPassable()) {

			direction = Direction.invert(direction);
		} else {

			xCoord += direction.getXOffset();
			yCoord += direction.getYOffset();
		}

		Player player = level.getPlayer();
		if (player.equals(this)) {

			player.setDead(true);
		}
	}

	public Direction getDirection() {
		return direction;
	}

}
