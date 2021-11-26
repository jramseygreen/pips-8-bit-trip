/**
 * @author Oliver Ryall, Sam Harper - 974243, 975431
 * @since 27/11/2019
 * @version 1.0
 * DumbTargetingEnemy:
 * This class creates the dumb targeting enemy, computes its movement 
 * and kills the player when in contact.
 */
package cs230.game.object.actor;

import cs230.game.Level;
import cs230.game.object.cell.Cell;
import cs230.util.Direction;
import cs230.util.GameIds;

public class DumbTargetingEnemy extends Actor {

	/**
	 * Constructor to create a dumb targeting enemy
	 * 
	 * @param xCoord
	 *            x coordinate of the enemy
	 * @param yCoord
	 *            y coordinate of the enemy
	 */
	public DumbTargetingEnemy(int xCoord, int yCoord) {
		super(xCoord, yCoord, GameIds.ENTITIES.ENEMY_DUMB);
	}

	/**
	 * Method computing the direction of the movement of the Player
	 * 
	 * @param player
	 *            Player object
	 * @return returns the direction of movement
	 */
	private Direction computeDirection(Player player) {

		int dx = (xCoord - player.getXCoord());
		int dy = (yCoord - player.getYCoord());
		Direction direction = Direction.NO_DIRECTION;

		if (dx == dy) {

			direction = Direction.NO_DIRECTION;
		} else if (dx == 0) {

			direction = dy < 0 ? Direction.SOUTH : Direction.NORTH;
		} else if (dy == 0) {

			direction = dx < 0 ? Direction.EAST : Direction.WEST;
		} else {

			boolean moveY = dy > dx;
			int biggerDiff = dy > dx ? dy : dx;

			if (moveY) {

				direction = biggerDiff < 0 ? Direction.SOUTH : Direction.NORTH;
			} else {

				direction = biggerDiff < 0 ? Direction.EAST : Direction.WEST;
			}

		}

		return direction;
	}

	/**
	 * checking if the enemy has moved.
	 * 
	 * @return return false if it hasn't moved.
	 */
	public boolean hasMoved() {
		return false;
	}

	/**
	 * Method checking if the enemy has moved and if movement is possible. This also
	 * checks if the enemy is in the same place as the Player and if so kills the
	 * player
	 */
	@Override
	public void update(Level level) {

		Direction dir = computeDirection(level.getPlayer());

		if (dir != Direction.NO_DIRECTION) {

			Cell cell = level.getCellAt(xCoord + dir.getXOffset(), yCoord + dir.getYOffset());

			if (cell != null) {

				if (cell.isPassable()) {

					xCoord += dir.getXOffset();
					yCoord += dir.getYOffset();
				}
			}
		}

		if (equals(level.getPlayer())) {

			level.getPlayer().setDead(true);
		}

	}
}
