/**
 * @author Sam Harper 975431, Nick Fisher 975390, Josh Green 956213
 * @version 1.3
 */
package cs230.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import cs230.game.Level;
import cs230.game.object.actor.Actor;
import cs230.game.object.actor.DumbTargetingEnemy;
import cs230.game.object.actor.Player;
import cs230.game.object.actor.SmartTargetingEnemy;
import cs230.game.object.actor.StraightLineEnemy;
import cs230.game.object.actor.WallFollowingEnemy;
import cs230.game.object.cell.Cell;
import cs230.game.object.cell.ColouredDoor;
import cs230.game.object.cell.Crumble;
import cs230.game.object.cell.Fire;
import cs230.game.object.cell.Ice;
import cs230.game.object.cell.Teleporter;
import cs230.game.object.cell.TokenDoor;
import cs230.game.object.cell.Water;
import cs230.game.object.item.GameItem;
import cs230.game.object.item.Help;
import cs230.game.object.item.Key;
import cs230.game.object.item.Token;
import cs230.util.GameIds.CELLS;

public class LevelIO {

	public static final String FILE_COMMENT = "#";
	public static final String FILE_TOKEN_SEPARATOR = ":";
	public static final String FILE_DATA_SEPARATOR = ",";

	private static final String LEVEL_DIR = "levels";
	private static final String SAVE_DIR = "saves";

	private static final String FILE_BLOCK_CLOSE = "}";
	private static final int FILE_CELL_PLACEHOLDER = 0;

	private static final String LEVEL_KEY_SPAWN = "spawn";
	private static final String LEVEL_KEY_MAP = "map";
	private static final String LEVEL_KEY_GOAL = "goal";
	private static final String LEVEL_KEY_TELEPORTER = "teleporter";
	private static final String LEVEL_KEY_CRUMBLE = "crumble";
	private static final String LEVEL_KEY_DOOR_TOKEN = "tokendoor";
	private static final String LEVEL_KEY_DOOR_COL = "coldoor";
	private static final String LEVEL_KEY_INVENTORY = "inv";
	private static final String LEVEL_KEY_TIME_TAKEN = "timetaken";

	/**
	 * A method that obtains all level's names from their files in levels\ and adds
	 * them to an array list.
	 * 
	 * @return ArrayList of String containing the names of the files
	 */
	public static ArrayList<String> getAllLevelNames() {

		File levelDir = null;
		try {

			levelDir = new File(LevelIO.class.getClassLoader().getResource(LEVEL_DIR).toURI());
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		ArrayList<String> levelNames = new ArrayList<>();
		if (levelDir != null) {

			// for each file inside of "levels/", check if it's a text file
			for (File levelFile : levelDir.listFiles()) {

				if (levelFile.getName().endsWith(".txt")) {
					// if so, add the name to the ArrayList
					levelNames.add(levelFile.getName());
				}
			}
		}

		return levelNames;
	}

	/**
	 * Saves a currently loaded game state to a text file. 
	 * @param name
	 *            Name of the file
	 * @param level
	 *            Current game status in the form of a level object
	 */
	public static void saveLevel(String name, Level level) {

		ArrayList<String> saveFileData = new ArrayList<>();

		saveFileData.add(toLine(LEVEL_KEY_MAP, level.getMapWidth() + FILE_DATA_SEPARATOR + level.getMapHeight() + "{"));

		ArrayList<GameItem> itemsToSave = saveMapData(level.getMapWidth(), level.getCells(), saveFileData);

		// spawn
		saveFileData.add(LEVEL_KEY_SPAWN + FILE_TOKEN_SEPARATOR + level.getSpawnX() + 
				FILE_DATA_SEPARATOR + level.getSpawnY());

		// goal
		saveFileData.add(LEVEL_KEY_GOAL + FILE_TOKEN_SEPARATOR + level.getNextLevelName());

		// time taken
		saveFileData.add(LEVEL_KEY_TIME_TAKEN + FILE_TOKEN_SEPARATOR + level.getTimeTaken());

		// gameItems
		saveGameItems(itemsToSave, saveFileData);

		// enemies
		saveEnemies(level.getEnemies(), saveFileData);

		// player
		savePlayer(level.getPlayer(), saveFileData);

		// write data
		writeFile(saveFileData, name);
	}

	/**
	 * Convert player info to file format.
	 * @param player
	 * @param saveFileData
	 */
	private static void savePlayer(Player player, ArrayList<String> saveFileData) {

		String pos = player.getId() + FILE_TOKEN_SEPARATOR + player.getCoords() + FILE_DATA_SEPARATOR
				+ player.getTokens();
		saveFileData.add(pos);

		// save inventory
		for (GameItem item : player.getInventory()) {

			if (!item.getId().equals(GameIds.ITEMS.HELP)) {

				String line = LEVEL_KEY_INVENTORY + FILE_TOKEN_SEPARATOR + item.getId();

				if (item.getId().equals(GameIds.ITEMS.KEY)) {

					Key key = (Key) item;
					line += FILE_DATA_SEPARATOR + key.getColour().getCol();
				}

				saveFileData.add(line);
			}
		}
	}

	/**
	 * Convert enemies to the file format.
	 * @param enemies
	 * @param saveFileData
	 */
	private static void saveEnemies(LinkedList<Actor> enemies, ArrayList<String> saveFileData) {

		for (Actor actor : enemies) {

			String line = actor.getId() + FILE_TOKEN_SEPARATOR + actor.getCoords();

			if (actor.getId().equals(GameIds.ENTITIES.ENEMY_LINE)) {

				StraightLineEnemy en = (StraightLineEnemy) actor;
				line += FILE_DATA_SEPARATOR + en.getDirection().getDir();
			} else if (actor.getId().equals(GameIds.ENTITIES.ENEMY_WALL)) {

				WallFollowingEnemy en = (WallFollowingEnemy) actor;
				line += FILE_DATA_SEPARATOR + en.getDirection().getDir() + FILE_DATA_SEPARATOR
						+ en.getBarrierSide().getDir();
			}

			saveFileData.add(line);
		}
	}

	/**
	 * Convert Game Items to the file format.
	 * @param itemsToSave
	 * @param saveFileData
	 */
	private static void saveGameItems(ArrayList<GameItem> itemsToSave, ArrayList<String> saveFileData) {

		for (GameItem item : itemsToSave) {

			String line = item.getId() + FILE_TOKEN_SEPARATOR + item.getCoords();

			if (item.getId().equals(GameIds.ITEMS.KEY)) {

				Key key = (Key) item;
				line += FILE_DATA_SEPARATOR + key.getColour().getCol();
			} else if (item.getId().equals(GameIds.ITEMS.HELP)) {

				Help help = (Help) item;
				line += FILE_DATA_SEPARATOR + help.getMessage();
			} else if (item.getId().equals(GameIds.ITEMS.TOKEN)) {

				Token token = (Token) item;
				line += FILE_DATA_SEPARATOR + token.getNumToken();
			}

			saveFileData.add(line);
		}
	}

	/**
	 * Join a token with it's info.
	 * @param token
	 * @param info
	 * @return
	 */
	private static String toLine(String token, String info) {

		return token + FILE_TOKEN_SEPARATOR + info;
	}

	/**
	 * Save Map data to the file format.
	 * @param mapWidth
	 * @param mapData
	 * @param fileData
	 * @return
	 */
	private static ArrayList<GameItem> saveMapData(int mapWidth, Cell[][] mapData, ArrayList<String> fileData) {

		ArrayList<GameItem> items = new ArrayList<>();

		ArrayList<Cell> specialCells = new ArrayList<>();
		for (int y = 0; y < mapData.length; y++) {

			int xCoord = 0;
			String line = "";

			for (int x = 0; x < mapData[y].length; x++) {

				Cell cell = mapData[y][x];

				if (cell.getXCoord() > xCoord) {

					while (xCoord < cell.getXCoord()) {

						line += FILE_CELL_PLACEHOLDER + FILE_DATA_SEPARATOR;
						xCoord++;
					}
				}
				
				line += cell.getId() + FILE_DATA_SEPARATOR;
				xCoord++;

				switch (cell.getId()) {
				case GameIds.CELLS.CRUMBLE:
				case GameIds.CELLS.TELEPORTER:
				case GameIds.CELLS.DOOR_TOKEN:
				case GameIds.CELLS.DOOR_COL:
					specialCells.add(cell);
					break;
				default:
					break;
				}

				if (cell.hasItem()) {
					items.add(cell.getItem());
				}
			}
			
			if (xCoord < mapWidth) {

				while (xCoord < mapWidth) {

					line += FILE_CELL_PLACEHOLDER + FILE_DATA_SEPARATOR;
					xCoord++;
				}
			}
			
			if (line.endsWith(FILE_DATA_SEPARATOR)) {
				line = line.substring(0, line.length() - 1);
			}
			
			fileData.add(line);
		}

		fileData.add(FILE_BLOCK_CLOSE);

		for (Cell cell : specialCells) {

			String line = "";
			switch (cell.getId()) {
			case GameIds.CELLS.TELEPORTER:
				Teleporter tp = (Teleporter) cell;
				line += LEVEL_KEY_TELEPORTER + FILE_TOKEN_SEPARATOR + tp.getCoords() + FILE_DATA_SEPARATOR
						+ tp.getNextTeleporter().getCoords();
				break;
			case GameIds.CELLS.CRUMBLE:
				Crumble crumble = (Crumble) cell;
				line += LEVEL_KEY_CRUMBLE + FILE_TOKEN_SEPARATOR + crumble.getCoords() + FILE_DATA_SEPARATOR
						+ crumble.isCrumbled();
				break;
			case GameIds.CELLS.DOOR_TOKEN:
				TokenDoor tDoor = (TokenDoor) cell;
				line += LEVEL_KEY_DOOR_TOKEN + FILE_TOKEN_SEPARATOR + tDoor.getCoords() + FILE_DATA_SEPARATOR
						+ tDoor.getNumTokens();
				break;
			case GameIds.CELLS.DOOR_COL:
				ColouredDoor cDoor = (ColouredDoor) cell;
				line += LEVEL_KEY_DOOR_TOKEN + FILE_TOKEN_SEPARATOR + cDoor.getCoords() + FILE_DATA_SEPARATOR
						+ cDoor.getColour().getCol();
				break;
			default:
				break;
			}

			fileData.add(line);
		}

		return items;
	}

	/**
	 * A method that loads a given level, dependent on name
	 * 
	 * @param name
	 *            String of the filename to load
	 * @return The newly loaded level object
	 */
	public static Level loadLevel(String name) {

		/*
		 * read in file data > read map data parse mapData read tokens process tokens
		 */

		ArrayList<String> fileData = readFile(name);

		int[][] mapDataIds = readMapData(fileData);
		int height = mapDataIds.length;
		int width = mapDataIds[0].length;

		// read map data into objects
		Cell[][] mapData = parseMapData(mapDataIds);

		Level level = new Level(width, height, mapData);

		// parse tokens and update cell and level data
		parseFileTokens(level, fileData);

		return level;

	}

	/**
	 * A method that processes each token in a line to corresponding objects
	 * 
	 * @param level
	 * @param fileData
	 */
	private static void parseFileTokens(Level level, ArrayList<String> fileData) {

		for (String line : fileData) {
			if (line.contains(FILE_TOKEN_SEPARATOR)) {

				String token = getToken(line);
				Scanner infoScanner = new Scanner(getInfo(line));
				processToken(token, infoScanner, level);
			}
		}
	}

	/**
	 * A method that considers each token (item from the scanner) and executes the
	 * appropriate method to read the file in
	 * 
	 * @param token
	 *            String used as a way of checking what GameObject is needed to be
	 *            parsed
	 * @param infoScanner
	 *            Scanner
	 * @param level
	 *            Level object passed through many methods so the loading process
	 *            can alter the level
	 */
	private static void processToken(String token, Scanner infoScanner, Level level) {

		switch (token) {
		case LEVEL_KEY_SPAWN:
			processTokenSpawn(infoScanner, level);
			break;
		case LEVEL_KEY_INVENTORY:
			processTokenInventory(infoScanner, level);
			break;
		case LEVEL_KEY_GOAL:
			processTokenGoal(infoScanner, level);
			break;
		case LEVEL_KEY_TELEPORTER:
			processTokenTeleporter(infoScanner, level);
			break;
		case LEVEL_KEY_DOOR_TOKEN:
			processTokenDoor(infoScanner, level);
			break;
		case LEVEL_KEY_DOOR_COL:
			processTokenColDoor(infoScanner, level);
			break;
		case LEVEL_KEY_TIME_TAKEN:
			level.setTimeTaken(infoScanner.nextLong());
			break;
		case GameIds.ITEMS.TOKEN:
			processTokenToken(infoScanner, level);
			break;
		case GameIds.ENTITIES.ENEMY_LINE:
			processTokenLineEnemy(infoScanner, level);
			break;
		case GameIds.ENTITIES.ENEMY_DUMB:
			processTokenDumbEnemy(infoScanner, level);
			break;
		case GameIds.ITEMS.HELP:
			processTokenHelp(infoScanner, level);
			break;
		case GameIds.ENTITIES.ENEMY_SMART:
			processTokenSmartEnemy(infoScanner, level);
			break;
		case GameIds.ENTITIES.ENEMY_WALL:
			processTokenWallEnemy(infoScanner, level);
			break;
		case GameIds.ITEMS.ICE_SKATE:
		case GameIds.ITEMS.FLIPPERS:
		case GameIds.ITEMS.FIRE_BOOT:
			processTokenGeneric(infoScanner, level, token);
			break;
		case GameIds.ITEMS.KEY:
			processTokenDoorKey(infoScanner, level);
			break;
		default:
		}
	}

	/**
	 * A method for processing the help item/tile.
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenHelp(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		String message = "";
		while (infoScanner.hasNext()) {
			message = message + (infoScanner.next() + " ");
		}
		Cell c = level.getCellAt(x, y);
		c.setItem(new Help(x, y, message));

	}

	/**
	 * A method to allow for processing the straight line enemy.
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenLineEnemy(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		String direction = infoScanner.next();
		StraightLineEnemy enemy = new StraightLineEnemy(x, y, Direction.getDirection(direction));
		level.addEnemy(enemy);

	}

	/**
	 * Processing method that enables a wall following enemy to be loaded into the
	 * level
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenWallEnemy(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		String direction = infoScanner.next();
		String barrierSide = infoScanner.next();

		level.addEnemy(
				new WallFollowingEnemy(x, y, Direction.getDirection(direction), Direction.getDirection(barrierSide)));

	}

	/**
	 * A processing method that allows for a smart tracking enemy to be loaded into
	 * the level
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenSmartEnemy(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		SmartTargetingEnemy enemy = new SmartTargetingEnemy(x, y, level);
		level.addEnemy(enemy);
	}

	/**
	 * Dumb targeting enemy processor, creates new object of enemy each time
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenDumbEnemy(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		DumbTargetingEnemy enemy = new DumbTargetingEnemy(x, y);
		level.addEnemy(enemy);
	}

	/**
	 * A processor to make sure the correct number of tokens is passed
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenToken(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();

		int tokenNum = infoScanner.nextInt();
		Cell c = level.getCellAt(x, y);
		c.setItem(new Token(x, y, tokenNum));
	}

	/**
	 * Processes behaviour associated with Door (specifically key)
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenDoorKey(Scanner infoScanner, Level level) {

		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();

		String colour = infoScanner.next();

		Cell c = level.getCellAt(x, y);
		c.setItem(new Key(x, y, Colour.getColour(colour)));
	}

	/**
	 * A processing method that will can handle any game item
	 * 
	 * @param infoScanner
	 * @param level
	 * @param id
	 *            String name of the GameItem
	 */
	private static void processTokenGeneric(Scanner infoScanner, Level level, String id) {

		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);

		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();

		Cell c = level.getCellAt(x, y);
		c.setItem(new GameItem(x, y, id));
	}

	/**
	 * A method that adds new items to the players inventory
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenInventory(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		level.getPlayer().addToInventory(new GameItem(-1, -1, infoScanner.next()));
	}

	/**
	 * Process the colour door by creating a new ColouredDoor object by casting a
	 * cell as a ColouredDoor and assigning it a colour
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenColDoor(Scanner infoScanner, Level level) {
		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		String colour = infoScanner.next();

		Cell cell = level.getCellAt(x, y);

		if (cell.getId() == GameIds.CELLS.DOOR_COL) {

			ColouredDoor cDoor = (ColouredDoor) cell;

			cDoor.setColour(Colour.getColour(colour));
		}
	}

	/**
	 * Process the token door by creating a new TokenDoor object by casting a cell
	 * as a TokenDoor and assigning it a number of tokens
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenDoor(Scanner infoScanner, Level level) {

		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();
		int numTokens = infoScanner.nextInt();

		Cell cell = level.getCellAt(x, y);

		if (cell.getId() == GameIds.CELLS.DOOR_TOKEN) {

			TokenDoor tDoor = (TokenDoor) cell;
			tDoor.setNumTokens(numTokens);
		}
	}

	/**
	 * A method that takes care of processing a pair of teleporters at once
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenTeleporter(Scanner infoScanner, Level level) {

		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		// getting the two teleporter's coords
		// tp1
		int link1X = infoScanner.nextInt();
		int link1Y = infoScanner.nextInt();
		// tp2
		int link2X = infoScanner.nextInt();
		int link2Y = infoScanner.nextInt();

		Cell cell1 = level.getCellAt(link1X, link1Y);
		Cell cell2 = level.getCellAt(link2X, link2Y);

		if (cell1.getId() == GameIds.CELLS.TELEPORTER && cell2.getId() == GameIds.CELLS.TELEPORTER) {
			// casting a cell as a teleporter to enable it's behaviour
			Teleporter t1 = (Teleporter) cell1;
			Teleporter t2 = (Teleporter) cell2;
			t1.setNextTeleporter(t2);
			t2.setNextTeleporter(t1);
		}
	}

	/**
	 * The goal cell is created by getting the next map's file name specified in the
	 * file and setting the nextLevelName property level to that. If it doesn't
	 * exist then the game ends when the player steps on the goal
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenGoal(Scanner infoScanner, Level level) {

		String levelName = "";
		if (infoScanner.hasNext()) {

			levelName = infoScanner.next();
		}

		level.setNextLevelName(levelName);

	}

	/**
	 * Spawn coordinates fed directly to a level object
	 * 
	 * @param infoScanner
	 * @param level
	 */
	private static void processTokenSpawn(Scanner infoScanner, Level level) {

		infoScanner.useDelimiter(FILE_DATA_SEPARATOR);
		int x = infoScanner.nextInt();
		int y = infoScanner.nextInt();

		level.setSpawn(x, y);
	}

	/**
	 * Method prepares a 2d array of Cell and fills each row of objData with a row
	 * of cells
	 * 
	 * @param mapData
	 * @return 2d Cell array representation of map
	 */
	private static Cell[][] parseMapData(int[][] mapData) {

		Cell[][] objData = new Cell[mapData.length][];

		int yCoord = 0;
		for (int i = 0; i < mapData.length; i++) {

			objData[i] = parseMapRow(yCoord, mapData[i]);
			yCoord++;
		}

		return objData;
	}

	/**
	 * Insert a row of cells into a cell array
	 * 
	 * @param yCoord
	 * @param mapDataRow
	 * @return Row of cells containing the most
	 */
	private static Cell[] parseMapRow(int yCoord, int[] mapDataRow) {

		// count number of actual cells (not placeholders)
		int count = 0;
		for (int i = 0; i < mapDataRow.length; i++) {
			// if index is valid data increase count
			if (mapDataRow[i] != FILE_CELL_PLACEHOLDER) {
				count++;
			}
		}

		Cell[] cellRow = null;

		if (count > 0) {

			int xCoord = 0;
			// make a Cell array the size of how many valid pieces of data there are in
			// mapDataRow
			cellRow = new Cell[count];

			int index = 0;
			for (int i = 0; i < mapDataRow.length; i++) {

				int cellType = mapDataRow[i];

				if (cellType != FILE_CELL_PLACEHOLDER) {

					cellRow[index] = parseCellType(xCoord, yCoord, cellType);
					index++;
				}

				xCoord++;
			}
		}

		return cellRow;
	}

	/**
	 * This method creates a cell object based on the passed cell digit
	 * representation
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param cellType
	 * @return New Cell created
	 */
	private static Cell parseCellType(int xCoord, int yCoord, int cellType) {

		Cell cell = null;

		switch (cellType) {
		case CELLS.WALL:
			cell = new Cell(xCoord, yCoord, CELLS.WALL, false);
			break;
		case CELLS.GROUND:
			cell = new Cell(xCoord, yCoord, CELLS.GROUND);
			break;
		case CELLS.FIRE:
			cell = new Fire(xCoord, yCoord);
			break;
		case CELLS.ICE:
			cell = new Ice(xCoord, yCoord);
			break;
		case CELLS.CRUMBLE:
			cell = new Crumble(xCoord, yCoord);
			break;
		case CELLS.WATER:
			cell = new Water(xCoord, yCoord);
			break;
		case CELLS.GOAL:
			cell = new Cell(xCoord, yCoord, CELLS.GOAL);
			break;
		case CELLS.TELEPORTER:
			cell = new Teleporter(xCoord, yCoord);
			break;
		case CELLS.DOOR_TOKEN:
			cell = new TokenDoor(xCoord, yCoord);
			break;
		case CELLS.DOOR_COL:
			cell = new ColouredDoor(xCoord, yCoord);
			break;
		default:
		}
		return cell;
	}

	/**
	 * Places cell's representation as integers into a 2d array
	 * 
	 * @param fileData
	 * @return
	 */
	private static int[][] readMapData(ArrayList<String> fileData) {

		int mapWidth = 0;
		int mapHeight = 0;

		int[][] mapData = null;

		int lineNum = findToken(LEVEL_KEY_MAP, fileData);

		if (lineNum != -1) {

			String mapLine = getInfo(fileData.get(lineNum));
			mapLine = mapLine.substring(0, mapLine.length() - 1);

			Scanner widthHeight = new Scanner(mapLine);
			widthHeight.useDelimiter(FILE_DATA_SEPARATOR);
			mapWidth = widthHeight.nextInt();
			mapHeight = widthHeight.nextInt();
			widthHeight.close();

			if (mapWidth > 0 && mapHeight > 0) {
				// create
				mapData = new int[mapHeight][mapWidth];
				readMapIntegers(mapData, lineNum + 1, fileData);
			}
		}

		return mapData;
	}

	/**
	 * For each line in mapdata, reads the file until it encounters a curly brace
	 * 
	 * @param mapData
	 * @param lineNum
	 * @param fileData
	 */
	private static void readMapIntegers(int[][] mapData, int lineNum, ArrayList<String> fileData) {

		boolean done = false;

		int mapLine = 0;
		while (!done && lineNum < fileData.size()) {

			String line = fileData.get(lineNum);

			if (line.contains(FILE_BLOCK_CLOSE)) {
				line = line.substring(0, line.indexOf(FILE_BLOCK_CLOSE));
				done = true;
			}

			if (!line.isEmpty()) {

				ArrayList<Integer> nums = readNums(line);

				for (int j = 0; j < mapData[mapLine].length; j++) {

					mapData[mapLine][j] = nums.get(j);
				}
				mapLine++;
			}

			lineNum++;
		}
	}

	/**
	 * From a string read in the integers it contains.
	 * @param line
	 * @return
	 */
	private static ArrayList<Integer> readNums(String line) {

		ArrayList<Integer> nums = new ArrayList<>();

		Scanner parseNums = new Scanner(line);
		parseNums.useDelimiter(FILE_DATA_SEPARATOR);

		while (parseNums.hasNextInt()) {

			nums.add(parseNums.nextInt());
		}

		parseNums.close();
		return nums;
	}

	private static String getInfo(String line) {

		return line.substring(line.indexOf(FILE_TOKEN_SEPARATOR) + 1);
	}

	private static String getToken(String line) {

		return line.substring(0, line.indexOf(FILE_TOKEN_SEPARATOR)).toLowerCase();
	}

	/**
	 * finds a token in file. -1 if not found.
	 * 
	 * @param token
	 * @param fileData
	 * @return
	 */
	private static int findToken(String token, ArrayList<String> fileData) {

		boolean done = false;
		int lineNum = 0;

		// finds token and its line number
		while (!done && lineNum < fileData.size()) {

			String line = fileData.get(lineNum);
			line = getToken(line);

			if (line.equalsIgnoreCase(token)) {

				done = true;
			} else {

				lineNum++;
			}
		}

		if (lineNum >= fileData.size())
			lineNum = -1;

		return lineNum;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private static ArrayList<String> readFile(String name) {

		ArrayList<String> fileData = new ArrayList<>();

		try (BufferedReader fileScanner = new BufferedReader(new InputStreamReader(
				LevelIO.class.getClassLoader().getResource(LEVEL_DIR + "/" + name).openStream()))) {

			String line;
			while ((line = fileScanner.readLine()) != null) {

				// remove any possible whitespace
				line = line.replaceAll("\\s+", "");

				// line contains useful data
				if (!line.isEmpty() && !line.startsWith(FILE_COMMENT)) {

					fileData.add(line);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return fileData;
	}

	/**
	 * A method that writes the data passed as a parameter to a file under the name
	 * variable
	 * 
	 * @param data
	 * @param name
	 */
	private static void writeFile(ArrayList<String> data, String name) {
		File file = new File(SAVE_DIR, name + ".txt");
		
		if(file.exists()) {
			file.delete();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

			for (String line : data) {

				writer.write(line + "\n");
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
}
