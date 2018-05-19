package AP_Assignment2;

public class NoParentException extends Exception {
	
	public NoParentException(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}
