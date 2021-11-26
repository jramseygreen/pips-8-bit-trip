package cs230.game;

import cs230.KeyHandler;
import cs230.Pip8BitTrip;
import cs230.Profile;
import cs230.pane.BaseController;
import cs230.pane.PaneController;
import cs230.util.LevelIO;
import cs230.util.Message;
import cs230.util.Renderer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Sam Harper 975431
 *
 */
public class Game extends BaseController {
	// fxml
	@FXML
	private StackPane pausePane;
	@FXML
	private Button btnResume;
	@FXML
	private Button btnRestart;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnExit;

	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	private Renderer renderer = new Renderer();

	private AnimationTimer timer;
	private String levelName;
	private Level curLevel;

	private Profile profile = null;

	private boolean paused = false;

	private boolean switched = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.pane.BaseController#init()
	 */
	@Override
	public void init() {

		// initialize canvas
		canvas.setWidth(Pip8BitTrip.APPLICATION_SIZE_WIDTH);
		canvas.setHeight(Pip8BitTrip.APPLICATION_SIZE_HEIGHT);
		gc = canvas.getGraphicsContext2D(); // add graphics controller

		timer = new AnimationTimer() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javafx.animation.AnimationTimer#handle(long)
			 */
			/**
			 * updates and renders
			 * 
			 * @param time
			 */
			@Override
			public void handle(long time) {

				update();
				render();
			}
		};

		/*
		 * if the profile isn't set, can't save
		 */
		if (profile == null) {

			btnSave.setDisable(true);
		}

		// launch a passed level
		curLevel = LevelIO.loadLevel(levelName);
		curLevel.launch();

		switched = false;
		timer.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.pane.BaseController#dispose()
	 */
	/**
	 * removes animation timer and profile;
	 */
	@Override
	public void dispose() {

		timer.stop();
		profile = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.pane.BaseController#sendData(java.lang.String)
	 */
	/**
	 * passes level name
	 * 
	 * @param str
	 *            level name
	 */
	@Override
	public void sendData(String str) {

		levelName = str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs230.pane.BaseController#sendData(cs230.Profile)
	 */
	/**
	 * returns the profile
	 * 
	 * @param profile
	 *            player profile
	 */
	@Override
	public void sendData(Profile profile) {

		this.profile = profile;
	}

	/**
	 * updates the view depending on what needs to be displayed
	 */
	private void update() {

		if (KeyHandler.isPressed("pause")) {

			pause();
		}

		if (!paused) {

			curLevel.update();

			if (curLevel.isCompleted() && !switched) {

				if (profile != null) {

					PaneController.getControllerOf("highScoresPane").sendData(curLevel.getTimeTaken());
					PaneController.getControllerOf("highScoresPane").sendData(profile);
				}

				PaneController.getControllerOf("highScoresPane").sendData(curLevel.getNextLevelName());
				PaneController.switchTo(pausePane.getScene(), "highScoresPane");

				switched = true;
			}

			if (curLevel.getPlayer().isDead()) {

				new Message("Death", "Death", "You died lol");
				restart();
			}
		}
	}

	/**
	 * render the level, clear then get new
	 */
	private void render() {

		clearScreen();

		renderer.renderLevel(gc, curLevel);

		// add background and current level string to window
		gc.setFill(Color.WHITE);
		gc.fillText(levelName, 10, 10);
	}

	/**
	 * set the graphics controller to a black square filling the screen
	 */
	private void clearScreen() {

		gc.setFill(Color.BLACK);
		gc.fillRect(0.0, 0.0, Pip8BitTrip.APPLICATION_SIZE_WIDTH, Pip8BitTrip.APPLICATION_SIZE_HEIGHT);
	}

	/**
	 * pause menu logic
	 */
	private void pause() {

		paused = !paused;
		pausePane.setVisible(paused);
	}

	/**
	 * restart button on pause menu
	 */
	private void restart() {

		curLevel = LevelIO.loadLevel(levelName);
		curLevel.launch();
	}

	// below for pause menu stuff
	@FXML
	public void btnResumeClicked(ActionEvent e) {

		pause();
	}

	@FXML
	public void btnRestartClicked(ActionEvent e) {

		restart();
		pause();
	}

	@FXML
	public void btnSaveClicked(ActionEvent e) {

		LevelIO.saveLevel(profile.getName(), curLevel);
	}

	@FXML
	public void btnExitClicked(ActionEvent e) {

		PaneController.switchTo(btnExit.getScene(), "startPane");
		pause();
	}
}
