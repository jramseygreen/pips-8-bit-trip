package cs230;

import java.util.HashMap;

/**
 * @author Sam Harper 975431
 * @version 1.0
 *
 */
public class KeyHandler {

	private static HashMap<String, Boolean> keyMap = new HashMap<>();

	/**
	 * returns the pressed key
	 * 
	 * @param key  - the pressed key
	 */
	public static void pressed(String key) {

		keyMap.put(key, true);
	}

	/**
	 * releases a key
	 * 
	 * @param key
	 *            key to release
	 */
	private static void release(String key) {

		keyMap.put(key, false);
	}

	/**
	 * getter
	 * 
	 * @param key key to check
	 * @return true if key is pressed
	 */
	public static boolean isPressed(String key) {

		boolean pressed = false;

		if (keyMap.containsKey(key)) {

			pressed = keyMap.get(key);
			release(key);
		}

		return pressed;
	}
}
