package AP_Assignment2;

public class TooYoungException extends Exception {

	public TooYoungException(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}
}
