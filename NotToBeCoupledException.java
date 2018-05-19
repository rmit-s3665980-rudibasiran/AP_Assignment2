package AP_Assignment2;

public class NotToBeCoupledException extends Exception {
	
	public NotToBeCoupledException(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}
