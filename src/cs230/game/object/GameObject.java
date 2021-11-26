package cs230.game.object;

import cs230.util.LevelIO;

/**
 * This is the main super class for all objects
 * 
 * @author Josh Green 956213
 * @version 1.2
 *
 */
public abstract class GameObject {

	protected int xCoord;
	protected int yCoord;
	protected String texture;

	/**
	 * sets the x, y and texture values
	 * 
	 * @param xCoord
	 *            x cooordinate to be stored
	 * @param yCoord
	 *            y coordinate to be stored
	 * @param texture
	 *            Texture for the object
	 */
	public GameObject(int xCoord, int yCoord, String texture) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.texture = texture;
	}

	/**
	 * getter
	 * 
	 * @return object's x coordinate
	 */
	public int getXCoord() {
		return xCoord;
	}

	/**
	 * setter
	 * 
	 * @param xCoord
	 *            x coordinate
	 */
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * getter
	 * 
	 * @return y coordinate
	 */
	public int getYCoord() {
		return yCoord;
	}

	public String getCoords() {

		return xCoord + LevelIO.FILE_DATA_SEPARATOR + yCoord;
	}

	/**
	 * setter
	 * 
	 * @param yCoord
	 *            y coordniate
	 */
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * getter
	 * 
	 * @return texture id
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * setter
	 * 
	 * @param texture
	 *            texture id
	 */
	public void setTexture(String texture) {
		this.texture = texture;
	}

	/**
	 * Boolean checking if the x and y coordinates are the same as the actor and
	 * returning true if they are.
	 * 
	 * @param actor
	 * @return true if they are equal.
	 */
	public boolean equals(GameObject object) {

		return xCoord == object.getXCoord() && yCoord == object.getYCoord();
	}

}
