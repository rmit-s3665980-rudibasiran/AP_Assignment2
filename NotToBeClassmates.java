package AP_Assignment2;

public class NotToBeClassmates extends Exception {
	
	public NotToBeClassmates(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}

}
