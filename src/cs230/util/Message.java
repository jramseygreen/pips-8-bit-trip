package cs230.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author @Josh - 956213
 * @ver 1.0 creates an alert type and displays it
 *
 */
public class Message {

	/**
	 * makes alert and displays
	 * 
	 * @param title
	 *            title of message window
	 * @param header
	 *            header of message box
	 * @param msg
	 *            message to show
	 * @param type
	 *            type of messagebox
	 * 
	 */
	public Message(String title, String header, String msg) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);

		alert.show();
	}
}
