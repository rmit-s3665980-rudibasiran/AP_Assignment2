package AP_Assignment2;

public class NotToBeFriendsException extends Exception {
	
		public NotToBeFriendsException(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}
