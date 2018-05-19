package AP_Assignment2;

public class NoSuchAgeException extends Exception {
	
	public NoSuchAgeException(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}
