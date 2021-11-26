package cs230.pane;

import cs230.Profile;
import cs230.util.ProfileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The controller for the select user profile GUI.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class SelectUserProfileController extends BaseController {

	@FXML
	private ListView<String> lstView;

	@FXML
	private Button btnCreateProfile;

	@FXML
	private Button btnStart;
	
	@FXML
	private Button btnDelete;

	/**
	 * Callback for when the pane is switched in.
	 */
	@Override
	public void init() {

		lstView.getItems().clear();

		String[] names = ProfileManager.getProfileNames();
		for (String name : names) {

			lstView.getItems().add(name);
		}

		if (names.length > 0) {

			lstView.getSelectionModel().clearAndSelect(0);
		}
	}

	/**
	 * Callback for create profile button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnCreateProfileClicked(ActionEvent e) {

		PaneController.switchTo(btnCreateProfile.getScene(), PaneController.CREATE_PROFILE_PANE);
	}

	/**
	 * Callback for the start button.
	 * 
	 * @param e
	 */
	@FXML
	public void btnStartClicked(ActionEvent e) {

		String name = lstView.getSelectionModel().getSelectedItem();
		Profile profile = ProfileManager.getProfile(name);

		PaneController.getControllerOf(PaneController.LEVEL_SEL_PANE).sendData(profile);
		PaneController.switchTo(btnStart.getScene(), PaneController.LEVEL_SEL_PANE);
	}
	
	/**
	 * Callback for the delete button.
	 */
	public void btnDeleteClicked(ActionEvent e) {
		
		String name = lstView.getSelectionModel().getSelectedItem();
		ProfileManager.deleteProfile(name);
		ProfileManager.saveProfiles();
		lstView.getItems().remove(name);
	}

}
