package AP_Assignment2;

public class NoAvailableException extends Exception {

	public NoAvailableException(String errMsg) {
		super(errMsg);
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}