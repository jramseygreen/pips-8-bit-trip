package cs230.pane;

import java.util.ArrayList;

import cs230.Profile;
import cs230.util.HighScoreManager;
import cs230.util.Score;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The controller for the high scores GUI.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class HighScoresPaneController extends BaseController {

	@FXML
	private Label lblLevel;

	@FXML
	private TableView<ScoreModel> tblScores;

	@FXML
	private TableColumn<ScoreModel, Number> tblColRank;

	@FXML
	private TableColumn<ScoreModel, String> tblColName;

	@FXML
	private TableColumn<ScoreModel, Number> tblColTime;

	@FXML
	private Button btnResetScore;

	@FXML
	private Button btnOk;

	String levelName = null;
	Profile profile = null;
	long timeTaken = -1;

	/**
	 * Called by the FXML loader when it loads the FXML.
	 */
	@FXML
	public void initialize() {

		tblColRank.setCellValueFactory(cellData -> cellData.getValue().getRank());
		tblColName.setCellValueFactory(cellData -> cellData.getValue().getProfileName());
		tblColTime.setCellValueFactory(cellData -> cellData.getValue().getTimeTaken());
	}

	/**
	 * Callback for the reset scores button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnResetScoreClicked(ActionEvent e) {

		HighScoreManager.resetScoresForLevel(levelName);
		HighScoreManager.saveScores();
		tblScores.getItems().clear();
	}

	/**
	 * Callback for the ok button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnOkClicked(ActionEvent e) {

		PaneController.getControllerOf(PaneController.GAME_PANE).sendData(levelName);

		if (profile != null) {

			PaneController.getControllerOf(PaneController.GAME_PANE).sendData(profile);
		}

		if (levelName.isEmpty()) {

			PaneController.switchTo(btnOk.getScene(), PaneController.START_PANE);
		} else {

			PaneController.switchTo(btnOk.getScene(), PaneController.GAME_PANE);
		}
	}

	/**
	 * When switched in by the PaneController
	 */
	@Override
	public void init() {

		tblScores.getItems().clear();

		if (profile != null) {

			HighScoreManager.addScore(levelName, new Score(profile.getName(), timeTaken));
			HighScoreManager.saveScores();
		}

		ArrayList<Score> scores = HighScoreManager.getScoresForLevel(levelName);

		if (scores != null) {

			int rank = 1;
			for (Score score : scores) {

				tblScores.getItems().add(new ScoreModel(rank, score));
				rank++;
			}
		}
	}

	/**
	 * Receive a Profile object.
	 */
	@Override
	public void sendData(Profile profile) {
		this.profile = profile;
	}

	/**
	 * Receive a string object.
	 */
	@Override
	public void sendData(String data) {

		this.levelName = data;
	}

	/**
	 * Receive a long value.
	 */
	@Override
	public void sendData(long data) {

		this.timeTaken = data;
	}

	/**
	 * Used to link the table view to the data supplied.
	 * 
	 * @author Sam Harper - 975431
	 * @version 1.0
	 */
	private class ScoreModel {

		private IntegerProperty rank;
		private StringProperty profileName;
		private LongProperty timeTaken;

		/**
		 * Constructor
		 * 
		 * @param rank
		 *            - what position the high score is in the leaderboard.
		 * @param score
		 *            - The time taken.
		 */
		public ScoreModel(int rank, Score score) {

			this.rank = new SimpleIntegerProperty(rank);
			this.profileName = new SimpleStringProperty(score.getName());
			this.timeTaken = new SimpleLongProperty(score.getTime());
		}

		/**
		 * Get rank.
		 * 
		 * @return rank of score.
		 */
		public IntegerProperty getRank() {
			return rank;
		}

		/**
		 * Get profile name.
		 * 
		 * @return profile name.
		 */
		public StringProperty getProfileName() {
			return profileName;
		}

		/**
		 * Get time taken
		 * 
		 * @return time taken.
		 */
		public LongProperty getTimeTaken() {
			return timeTaken;
		}
	}
}
