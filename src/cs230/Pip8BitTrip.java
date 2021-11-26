package cs230;

import cs230.pane.PaneController;
import cs230.util.HighScoreManager;
import cs230.util.ProfileManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * controls what javafx shows
 * 
 * @author Sam Harper 975431
 * @version 1.0
 *
 */
public class Pip8BitTrip extends Application {

	public static final String APPLICATION_NAME = "Pips 8 Bit Trip";
	public static final int APPLICATION_SIZE_WIDTH = 1280;
	public static final int APPLICATION_SIZE_HEIGHT = 960;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) {

		// create everything
		stage.setResizable(false);
		stage.setTitle(APPLICATION_NAME);

		Scene rootScene = new Scene(new Group(), APPLICATION_SIZE_WIDTH, APPLICATION_SIZE_HEIGHT);
		rootScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

			handleKeyPressed(key);
		});
		// add everything
		HighScoreManager.loadScores();
		ProfileManager.loadProfiles();

		PaneController.loadPanes();
		PaneController.switchTo(rootScene, PaneController.START_PANE);

		// display
		stage.setScene(rootScene);
		stage.show();
	}

	/**
	 * deals with the key presses
	 * 
	 * @param key key pressed on keyboard
	 */
	private static void handleKeyPressed(KeyEvent key) {

		switch (key.getCode()) {
		case UP:
			KeyHandler.pressed("moveUp");
			break;

		case DOWN:
			KeyHandler.pressed("moveDown");
			break;

		case LEFT:
			KeyHandler.pressed("moveLeft");
			break;

		case RIGHT:
			KeyHandler.pressed("moveRight");
			break;
		case ESCAPE:
			KeyHandler.pressed("pause");
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
