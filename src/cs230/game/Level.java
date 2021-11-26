package cs230.game;

import java.util.LinkedList;

import cs230.game.object.actor.Actor;
import cs230.game.object.actor.Player;
import cs230.game.object.cell.Cell;

/**
 * deals with all the level information
 * 
 * @author Josh Green 956213, Sam Harper 975431
 * @version 1.6
 *
 */
public class Level {

	private int mapWidth;
	private int mapHeight;
	private Cell[][] cells;
	private LinkedList<Actor> enemies;
	private boolean completed;
	private long timeTaken;

	private int spawnX;
	private int spawnY;
	private Player player;

	private String nextLevelName = null;
	private String curLevelName = null;

	/**
	 * setter
	 * 
	 * @param completed
	 *            true if level is complete
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * getter
	 * 
	 * @return the current level being played
	 */
	public String getCurLevelName() {
		return curLevelName;
	}

	/**
	 * setter
	 * 
	 * @param curLevelName
	 *            the current level being played
	 */
	public void setCurLevelName(String curLevelName) {
		this.curLevelName = curLevelName;
	}

	/**
	 * constructs a level
	 * 
	 * @param width
	 *            width of map
	 * @param height
	 *            height of map
	 * @param data
	 *            array of cells to put on map
	 */
	public Level(int width, int height, Cell[][] data) {
		this.cells = data;
		this.mapWidth = width;
		this.mapHeight = height;
		enemies = new LinkedList<>();
		spawnX = 0;
		spawnY = 0;
		timeTaken = 0;
		completed = false;
	}

	/**
	 * if actor is in given cell return
	 * 
	 * @param x
	 *            x coordinate to check
	 * @param y
	 *            y coordinate to check
	 * @return found actor
	 */
	public Actor getEnemyAt(int x, int y) {

		Actor foundActor = null;
		for (Actor enemy : enemies) {

			if (enemy.getXCoord() == x && enemy.getYCoord() == y) {

				foundActor = enemy;
			}
		}

		return foundActor;
	}

	/**
	 * Update level data with current player.
	 * 
	 * @param player
	 */
	public void update() {

		player.update(this);

		if (player.hasMoved()) {

			// update enemies
			for (Actor enemy : enemies) {

				enemy.update(this);
			}
		}
	}

	/**
	 * makes player to play the game adds player object ready to begin the level
	 */
	public void launch() {

		player = new Player(spawnX, spawnY);
	}

	/**
	 * getter
	 * 
	 * @return returns a list of all the enemies
	 */
	public LinkedList<Actor> getEnemies() {
		return enemies;
	}

	/**
	 * adds given enemies to level
	 * 
	 * @param enemies
	 *            linked list of enemies
	 */
	public void setEnemies(LinkedList<Actor> enemies) {
		this.enemies = enemies;
	}

	/**
	 * adds enemy to level
	 * 
	 * @param enemy
	 *            enemy to add
	 */
	public void addEnemy(Actor enemy) {
		enemies.add(enemy);
	}

	/**
	 * getter
	 * 
	 * @return map width
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * getter
	 * 
	 * @return map height
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * gets all cells on map
	 * 
	 * @return all cells on map
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * returns cell at x y
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return cell at coordinate
	 */
	public Cell getCellAt(int x, int y) {

		Cell foundCell = null;
		if (y >= 0 && y < mapHeight) {

			for (int i = 0; i < cells[y].length; i++) {

				if (cells[y][i].getXCoord() == x && cells[y][i].getYCoord() == y) {
					foundCell = cells[y][i];
				}
			}
		}

		return foundCell;
	}

	/**
	 * replacese this.cell at x y with cell
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param cell
	 *            replacement cell
	 */
	public void changeCellAt(int x, int y, Cell cell) {

		if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {

			cells[y][x] = cell;
		}
	}

	/**
	 * getter
	 * 
	 * @return true if level is finished
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * getter
	 * 
	 * @return time taken in seconds
	 */
	public long getTimeTaken() {
		return timeTaken;
	}

	/**
	 * setter
	 * 
	 * @param timeTaken
	 *            long of time taken in seconds
	 */
	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * getter returns x spawn
	 * 
	 * @return player spawn coordinate
	 */
	public int getSpawnX() {
		return spawnX;
	}

	/**
	 * getter returns y spawn
	 * 
	 * @return player spawn coordinate
	 */
	public int getSpawnY() {
		return spawnY;
	}

	/**
	 * setter
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void setSpawn(int x, int y) {

		this.spawnX = x;
		this.spawnY = y;
	}

	/**
	 * getter
	 * 
	 * @return next level file
	 */
	public String getNextLevelName() {
		return nextLevelName;
	}

	/**
	 * setter
	 * 
	 * @param nextLevelName
	 *            level file
	 */
	public void setNextLevelName(String nextLevelName) {
		this.nextLevelName = nextLevelName;
	}

	/**
	 * getter
	 * 
	 * @return player object
	 */
	public Player getPlayer() {
		return player;
	}
}
