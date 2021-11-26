package cs230.util;

/**
 * @author Sam Harper 975431, Josh Green 956213
 * @version 1.0 enum for direction - allows us to easily access directions of
 *          entities
 *
 */
public enum Direction {

	NO_DIRECTION("nodir", 0, 0), NORTH("north", 0, -1), SOUTH("south", 0, 1), EAST("east", 1, 0), WEST("west", -1, 0);

	private String dir;
	private int xOffset;
	private int yOffset;

	/**
	 * @param dir
	 *            direction
	 * @param xOffset
	 *            delta in x
	 * @param yOffset
	 *            delta in y allows access
	 */
	private Direction(String dir, int xOffset, int yOffset) {

		this.dir = dir;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * inverts given direction
	 * 
	 * @param dir
	 *            direction
	 * @return inverted direction
	 */
	public static Direction invert(Direction dir) {

		Direction inverted = dir;
		switch (dir) {
		case NORTH:
			inverted = SOUTH;
			break;
		case SOUTH:
			inverted = NORTH;
			break;
		case EAST:
			inverted = WEST;
			break;
		case WEST:
			inverted = EAST;
			break;
		default:
			inverted = NO_DIRECTION;
		}

		return inverted;
	}

	/**
	 * gets direction object from string
	 * 
	 * @param direction
	 *            direction
	 * @return direction type
	 */
	public static Direction getDirection(String direction) {

		Direction dir = NO_DIRECTION;
		direction = direction.toLowerCase();

		if (direction.equals(NORTH.dir)) {

			dir = NORTH;
		} else if (direction.equals(SOUTH.dir)) {

			dir = SOUTH;
		} else if (direction.equals(EAST.dir)) {

			dir = EAST;
		} else if (direction.equals(WEST.dir)) {

			dir = WEST;
		}

		return dir;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public String getDir() {
		return dir;
	}
}
