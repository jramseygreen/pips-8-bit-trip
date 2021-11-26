package cs230.pane;

import cs230.Profile;
import cs230.util.ProfileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The controller for the create profile GUI.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class CreateProfilePaneController extends BaseController {

	/**
	 * The default start level
	 */
	private static final String START_LEVEL = "01TestLevel.txt";

	@FXML
	private TextField txtFName;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnStart;

	/**
	 * Callback for the start button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnStartClicked(ActionEvent e) {

		String name = txtFName.getText();

		Profile newProfile = new Profile(name, 1);
		ProfileManager.addProfile(newProfile);
		ProfileManager.saveProfiles();

		PaneController.getControllerOf(PaneController.GAME_PANE).sendData(START_LEVEL);
		PaneController.getControllerOf(PaneController.GAME_PANE).sendData(newProfile);
		PaneController.switchTo(btnStart.getScene(), PaneController.GAME_PANE);
	}

	/**
	 * Callback for the back button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnBackClicked(ActionEvent e) {

		PaneController.switchTo(btnBack.getScene(), PaneController.START_PANE);
	}

}
