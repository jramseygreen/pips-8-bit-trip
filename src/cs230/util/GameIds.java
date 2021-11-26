package cs230.util;

/**
 * Contains all in game ids
 * 
 * @author Sam Harper 975431, Josh Green 956213
 * @version 1.5
 */
public class GameIds {

	/**
	 * 
	 * Cell ids
	 */
	public static class CELLS {

		public static final int WALL = 1;
		public static final int GROUND = 2;
		public static final int FIRE = 3;
		public static final int WATER = 4;
		public static final int GOAL = 5;
		public static final int TELEPORTER = 6;
		public static final int DOOR_TOKEN = 7;
		public static final int DOOR_COL = 8;
		public static final int ICE = 9;
		public static final int CRUMBLE = 10;
	}

	/**
	 * Item ids
	 *
	 */
	public static class ITEMS {

		public static final String FIRE_BOOT = "fireboot";
		public static final String ICE_SKATE = "iceskate";
		public static final String FLIPPERS = "flippers";
		public static final String TOKEN = "token";
		public static final String KEY = "doorkey";
		public static final String HELP = "help";
	}

	/**
	 * Entity ids
	 *
	 */
	public static class ENTITIES {

		public static final String PLAYER = "player";
		public static final String ENEMY_WALL = "wallfollowingenemy";
		public static final String ENEMY_LINE = "straightlineenemy";
		public static final String ENEMY_SMART = "smarttargetingenemy";
		public static final String ENEMY_DUMB = "dumbtargetingenemy";
	}
}
