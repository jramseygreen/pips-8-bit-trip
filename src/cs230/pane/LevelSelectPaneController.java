package cs230.pane;

import java.util.ArrayList;

import cs230.Profile;
import cs230.util.LevelIO;
import cs230.util.ProfileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The controller for the level selection GUI.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class LevelSelectPaneController extends BaseController {

	@FXML
	private ListView<String> lstLevelSelect;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnStart;

	private Profile profile = null;

	/**
	 * Called when being switched in.
	 */
	@Override
	public void init() {

		lstLevelSelect.getItems().clear();
		ArrayList<String> levels = LevelIO.getAllLevelNames();
		for (String name : levels) {

			if (profile != null) {

				int num = Integer.parseInt(name.substring(0, 2));

				if (num <= profile.getHighestCompletedLevel()) {

					lstLevelSelect.getItems().add(name);
				}
			} else {

				lstLevelSelect.getItems().add(name);
			}
		}

		if (profile != null && ProfileManager.doesSaveFileExistFor(profile)) {

			lstLevelSelect.getItems().add(profile.getName() + ".txt");
		}

		if (levels.size() > 0) {
			lstLevelSelect.getSelectionModel().select(0);
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
	 * Callback for the back button
	 * 
	 * @param e
	 */
	@FXML
	public void btnBackClicked(ActionEvent e) {

		PaneController.switchTo(btnBack.getScene(), PaneController.START_PANE);
	}

	/**
	 * Callback for the start button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnStartClicked(ActionEvent e) {

		String name = lstLevelSelect.getSelectionModel().getSelectedItem();

		PaneController.getControllerOf(PaneController.GAME_PANE).sendData(name);

		if (profile != null) {

			PaneController.getControllerOf(PaneController.GAME_PANE).sendData(profile);
		}

		PaneController.switchTo(btnStart.getScene(), PaneController.GAME_PANE);
	}

	/**
	 * Called when being switched out.
	 */
	@Override
	public void dispose() {

		profile = null;
	}
}
