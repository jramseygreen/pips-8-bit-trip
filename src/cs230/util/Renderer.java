package cs230.util;

import java.util.HashMap;

import cs230.Pip8BitTrip;
import cs230.game.Level;
import cs230.game.object.actor.Actor;
import cs230.game.object.actor.Player;
import cs230.game.object.cell.Cell;
import cs230.game.object.item.GameItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Sam Harper 975431
 * @version 1.5 renders everything here and loads all textures into hashmap
 *          saves memory by rendering this way
 *
 */
public class Renderer {

	private static final int CELL_SIZE = 128;
	private static final int PLAYER_POS_X = (Pip8BitTrip.APPLICATION_SIZE_WIDTH / 2) - (CELL_SIZE / 2);
	private static final int PLAYER_POS_Y = (Pip8BitTrip.APPLICATION_SIZE_HEIGHT / 2) - (CELL_SIZE / 2);
	private HashMap<String, Image> textureMap = new HashMap<>();
	private long timeStamp = System.currentTimeMillis();

	/**
	 * driver
	 */
	public Renderer() {

		loadTextures();
	}

	/**
	 * loads all texture types
	 */
	private void loadTextures() {

		loadCellTextures();
		loadActorTextures();
		loadItemTextures();

	}

	/**
	 * loads all item textures to hashmap
	 */
	private void loadItemTextures() {

		textureMap.put(GameIds.ITEMS.FLIPPERS, loadTexture("image/item/Flippers.png"));
		textureMap.put(GameIds.ITEMS.ICE_SKATE, loadTexture("image/item/Ice_skates.png"));
		textureMap.put(GameIds.ITEMS.FIRE_BOOT, loadTexture("image/item/Fire_boots.png"));
		textureMap.put(GameIds.ITEMS.TOKEN, loadTexture("image/item/Token.png"));
		textureMap.put(GameIds.ITEMS.HELP, loadTexture("image/item/Help.png"));

		textureMap.put(GameIds.ITEMS.KEY + Colour.YELLOW.getCol(), loadTexture("image/item/Yellow_key.png"));
		textureMap.put(GameIds.ITEMS.KEY + Colour.RED.getCol(), loadTexture("image/item/Red_key.png"));
		textureMap.put(GameIds.ITEMS.KEY + Colour.BLUE.getCol(), loadTexture("image/item/Blue_key.png"));
		textureMap.put(GameIds.ITEMS.KEY + Colour.GREEN.getCol(), loadTexture("image/item/Green_key.png"));
	}

	/**
	 * loads all actor textures to hashmap
	 */
	private void loadActorTextures() {

		textureMap.put(GameIds.ENTITIES.PLAYER, loadTexture("image/actor/Pip.png"));
		textureMap.put(GameIds.ENTITIES.PLAYER + "_alt", loadTexture("image/actor/Pip_Right.png"));
		textureMap.put(GameIds.ENTITIES.ENEMY_DUMB, loadTexture("image/actor/Enemy_dumb.png"));
		textureMap.put(GameIds.ENTITIES.ENEMY_LINE, loadTexture("image/actor/Enemy_straight.png"));
		textureMap.put(GameIds.ENTITIES.ENEMY_SMART, loadTexture("image/actor/Enemy_smart.png"));
		textureMap.put(GameIds.ENTITIES.ENEMY_WALL, loadTexture("image/actor/Enemy_wall.png"));
	}

	/**
	 * Loads all cell textures to hashmap Done this way to save memory
	 */
	private void loadCellTextures() {

		textureMap.put(String.valueOf(GameIds.CELLS.WALL), loadTexture("image/cell/Wall.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.GROUND), loadTexture("image/cell/Floor.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.FIRE), loadTexture("image/cell/Fire.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.WATER), loadTexture("image/cell/Water.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.GOAL), loadTexture("image/cell/Goal.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.TELEPORTER), loadTexture("image/cell/Teleporter.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.DOOR_TOKEN), loadTexture("image/cell/Token_door.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.ICE), loadTexture("image/cell/Ice.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.CRUMBLE), loadTexture("image/cell/Floor.png"));
		textureMap.put("crumbleTrue", loadTexture("image/cell/Floor_Broken.png"));

		textureMap.put(String.valueOf(GameIds.CELLS.DOOR_COL) + Colour.YELLOW.getCol(),
				loadTexture("image/cell/Yellow_door.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.DOOR_COL) + Colour.RED.getCol(),
				loadTexture("image/cell/Red_door.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.DOOR_COL) + Colour.BLUE.getCol(),
				loadTexture("image/cell/Blue_door.png"));
		textureMap.put(String.valueOf(GameIds.CELLS.DOOR_COL) + Colour.GREEN.getCol(),
				loadTexture("image/cell/Green_door.png"));

	}

	/**
	 * @param path
	 *            path of texture
	 * @return image type of texture imports a png
	 */
	private Image loadTexture(String path) {

		return new Image(this.getClass().getClassLoader().getResourceAsStream(path));
	}

	/**
	 * @param gc
	 *            graphics context to use
	 * @param level
	 *            level to render renders a level using the graphics context
	 */
	public void renderLevel(GraphicsContext gc, Level level) {

		// records the time taken in seconds
		if (!level.isCompleted()) {
			if (System.currentTimeMillis() - timeStamp >= 1000) {
				timeStamp = System.currentTimeMillis();
				level.setTimeTaken(level.getTimeTaken() + 1);
			}
		}

		Player player = level.getPlayer();
		// tile to render around
		int startTileX = player.getXCoord();
		int startTileY = player.getYCoord();

		// work backwards to work out what cell needs drawing
		// calculate X tile and pixel loc to draw from
		int pixelCoordX = PLAYER_POS_X;
		while (startTileX > 0 && pixelCoordX >= 1) {
			pixelCoordX -= CELL_SIZE;
			startTileX--;
		}

		// calculate Y tile and pixel loc to draw from.
		int pixelCoordY = PLAYER_POS_Y;
		while (startTileY > 0 && pixelCoordY >= 1) {
			pixelCoordY -= CELL_SIZE;
			startTileY--;
		}

		Cell[][] cells = level.getCells();

		for (int y = startTileY; y < cells.length; y++) {

			for (int x = 0; x < cells[y].length; x++) {

				Cell c = cells[y][x];
				int calcDrawX = pixelCoordX + (c.getXCoord() * CELL_SIZE) - (startTileX * CELL_SIZE);
				drawTileAt(gc, calcDrawX, pixelCoordY, textureMap.get(c.getTexture()));

				if (c.hasItem()) {

					GameItem item = c.getItem();

					drawTileAt(gc, calcDrawX, pixelCoordY, textureMap.get(item.getTexture()));
				}

				Actor enemy = level.getEnemyAt(x, y);

				if (enemy != null) {
					drawTileAt(gc, calcDrawX, pixelCoordY, textureMap.get(enemy.getTexture()));
				}
			}
			pixelCoordY += CELL_SIZE;
		}

		drawTileAt(gc, (PLAYER_POS_X), (PLAYER_POS_Y), textureMap.get(player.getTexture()));
	}

	/**
	 * @param gc
	 *            graphics context
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param img
	 *            image to draw draws an image using the graphics context at given
	 *            position
	 */
	private void drawTileAt(GraphicsContext gc, int x, int y, Image img) {

		gc.drawImage(img, x, y, CELL_SIZE, CELL_SIZE);
	}
}
