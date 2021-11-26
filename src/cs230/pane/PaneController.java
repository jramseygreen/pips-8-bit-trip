package cs230.pane;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * This class manages all the different GUI panes and handles the interactions
 * between them. This was designed to be as easy to use as possible in order to
 * make the data flow between the different GUIs as simple as possible.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class PaneController {
	
	public static final String START_PANE = "startPane";
	public static final String LEVEL_SEL_PANE = "levelSelectPane";
	public static final String GAME_PANE = "gamePane";
	public static final String SEL_USER_PROFILE_PANE = "selectUserProfilePane";
	public static final String HIGH_SCORES_PANE = "highScoresPane";
	public static final String CREATE_PROFILE_PANE = "createProfilePane";

	// map a string to it's associated gui data
	private static HashMap<String, Pair> panes = new HashMap<>();

	// the current pair being shown
	private static Pair currentPair;

	/**
	 * Load the various GUI panes from it's FXML.
	 */
	public static void loadPanes() {
		panes.put(START_PANE, loadFxml("fxml/StartPane.fxml"));
		panes.put(LEVEL_SEL_PANE, loadFxml("fxml/LevelSelectPane.fxml"));
		panes.put(GAME_PANE, loadFxml("fxml/GamePane.fxml"));
		panes.put(SEL_USER_PROFILE_PANE, loadFxml("fxml/SelectUserProfile.fxml"));
		panes.put(HIGH_SCORES_PANE, loadFxml("fxml/HighScoresPane.fxml"));
		panes.put(CREATE_PROFILE_PANE, loadFxml("fxml/CreateProfilePane.fxml"));
	}

	/**
	 * Switch the current window content to the specified pane.
	 * 
	 * @param scene
	 *            - the scene to switch into/outof.
	 * @param name
	 *            - the name of the pane to switch to
	 */
	public static void switchTo(Scene scene, String name) {

		if (panes.containsKey(name)) {

			if (currentPair != null) {

				currentPair.controller.dispose();
			}

			currentPair = panes.get(name);
			currentPair.controller.init();

			// catch to prevent a strange null exception in JavaFX, perhaps to do with
			// AnimationTimers and when they stop.
			try {

				scene.setRoot(currentPair.pane);
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * Get the controller for this GUI pane.
	 * 
	 * @param name
	 *            - the name of the pane to get the controller of
	 * @return The controller of that pane
	 */
	public static BaseController getControllerOf(String name) {

		if (panes.containsKey(name)) {

			return panes.get(name).controller;
		}

		return null;
	}

	/**
	 * Load FXML from files.
	 * 
	 * @param path
	 * @return The GUI data
	 */
	private static Pair loadFxml(String path) {
		FXMLLoader loader = new FXMLLoader(PaneController.class.getClassLoader().getResource(path));
		PaneController.Pair pair = new Pair();

		try {

			pair.pane = loader.load();

			if (pair.pane != null) {

				pair.controller = loader.getController();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return pair;
	}

	/**
	 * Internal class to link the Pane controller to it's GUI layout data.
	 * 
	 * @author Sam Harper - 975431
	 *
	 */
	private static class Pair {

		public Pane pane;
		public BaseController controller;
	}

}
