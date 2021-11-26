/**
 * @author Josh Green - 956213
 * @version 1.1
 * WallFollowingEnemy:
 * An actor subclass that has a particular movement behaviour.
 * It will make sure that it is adjacent to a wall at all times.
 */
package cs230.game.object.actor;

import cs230.game.Level;
import cs230.game.object.cell.Cell;
import cs230.util.Direction;
import cs230.util.GameIds;

public class WallFollowingEnemy extends Actor {

	private Direction direction;
	private Direction barrierSide;

	/**
	 * WallFollowingEnemy constructor.
	 * 
	 * @param xCoord
	 *            - int -spawn point for the actor.
	 * @param yCoord
	 *            - y spawn point for the actor.
	 * @param dir
	 *            - given starting direction.
	 * @param side
	 *            - which side it "puts its hands on".
	 */
	public WallFollowingEnemy(int xCoord, int yCoord, Direction dir, Direction side) {
		super(xCoord, yCoord, GameIds.ENTITIES.ENEMY_WALL);
		this.direction = dir;
		this.barrierSide = side;
	}

	/**
	 * updates enemy data each turn
	 */
	@Override
	public void update(cs230.game.Level level) {

		if (this.hasBarrier(level)) {
			
			if (!this.getNextCell(level, direction).equals(level.getPlayer())) {
				
				if (getNextCell(level, direction).isPassable()) {
					xCoord += direction.getXOffset();
					yCoord += direction.getYOffset();
				} else {
					
					if (getNextCell(level, Direction.invert(barrierSide)).isPassable()) {
						Direction dir = direction;
						direction = Direction.invert(barrierSide);
						barrierSide = dir;
					} else {
						direction = Direction.invert(direction);
					}
				}
			}

		} else {

			xCoord += barrierSide.getXOffset();
			yCoord += barrierSide.getYOffset();

			Direction dir = direction;
			direction = barrierSide;
			barrierSide = Direction.invert(dir);
		}

		Player player = level.getPlayer();
		if (player.equals(this)) {
			player.setDead(true);
		}

	}

	/**
	 * Gets the next cell in the enemy's path in the form of a cell.
	 * 
	 * @param level
	 *            A Level object that contains the details of the game that need to
	 *            be updated.
	 * @param direction
	 *            A Direction object that gives the current direction of the enemy.
	 * @return The next cell.
	 */
	private Cell getNextCell(Level level, Direction direction) {
		
		Cell cell = level.getCellAt(xCoord + direction.getXOffset(), yCoord + direction.getYOffset());
		return cell;
	}

	/**
	 * Checks if the enemy has a barrier next to it
	 * 
	 * @param level
	 *            Current game status.
	 * @return True or false boolean deciding if the next cell is a barrier or not.
	 */
	private boolean hasBarrier(Level level) {
		
		if (!level.getCellAt(xCoord + barrierSide.getXOffset(), yCoord + barrierSide.getYOffset()).isPassable()) {
			return true;
		} else {
			return false;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public Direction getBarrierSide() {
		return barrierSide;
	}

}
