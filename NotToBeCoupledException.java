package AP_Assignment2;

public class NotToBeCoupledException extends Exception {
	
	public NotToBeCoupledException(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}

}
