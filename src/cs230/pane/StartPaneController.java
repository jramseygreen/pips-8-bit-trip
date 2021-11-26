package cs230.pane;

import cs230.util.MessageOfTheDay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The controller for the start pane GUI.
 * 
 * @author Sam Harper - 975431
 * @version 1.0
 *
 */
public class StartPaneController extends BaseController {

	@FXML
	private Label lblMotd;

	@FXML
	private Button btnStartGame;

	@FXML
	private Button gameBtn;

	@FXML
	private Button exitBtn;

	/**
	 * Callback for when the pane is switched in.
	 */
	@Override
	public void init() {

		lblMotd.setText(MessageOfTheDay.getMessage());
	}

	/**
	 * Start game button callback.
	 * 
	 * @param e
	 */
	@FXML
	public void btnStartGameClicked(ActionEvent e) {

		PaneController.switchTo(gameBtn.getScene(), PaneController.SEL_USER_PROFILE_PANE);
	}

	/**
	 * Level select button callback
	 * 
	 * @param e
	 */
	@FXML
	public void gameBtnClicked(ActionEvent e) {

		PaneController.switchTo(gameBtn.getScene(), PaneController.LEVEL_SEL_PANE);
	}

	/**
	 * Exit button callback.
	 * 
	 * @param e
	 */
	@FXML
	public void exitBtnClicked(ActionEvent e) {

		Platform.exit();
	}
}
