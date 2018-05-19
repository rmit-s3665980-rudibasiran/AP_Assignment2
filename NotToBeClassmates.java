package AP_Assignment2;

public class NotToBeClassmates extends Exception {
	
	public NotToBeClassmates(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}
