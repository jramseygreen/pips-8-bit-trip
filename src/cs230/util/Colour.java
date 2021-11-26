package cs230.util;

/**
 * @author Sam Harper 975431
 * @version 1.1 Enum for colours
 *
 */
public enum Colour {

	BLUE("blue"), YELLOW("yellow"), GREEN("green"), RED("red");

	private String col;

	private Colour(String col) {

		this.col = col;
	}

	/**
	 * converts color from string to Colour type
	 * 
	 * @param col
	 *            colour to convert
	 * @return converted colour
	 */
	public static Colour getColour(String col) {

		Colour colour = RED;
		col = col.toLowerCase();

		if (col.equals(BLUE.col)) {

			colour = BLUE;
		} else if (col.equals(YELLOW.col)) {

			colour = YELLOW;
		} else if (col.equals(GREEN.col)) {

			colour = GREEN;
		} else if (col.equals(RED.col)) {

			colour = RED;
		}

		return colour;
	}

	/**
	 * getter
	 * 
	 * @return colour
	 */
	public String getCol() {
		return col;
	}

}
