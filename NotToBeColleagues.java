package AP_Assignment2;

public class NotToBeColleagues extends Exception {
	
	public NotToBeColleagues(String errMsg) {
		super(errMsg); 
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}


}
