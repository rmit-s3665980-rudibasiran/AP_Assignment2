package AP_Assignment2;

public class NoAvailableException extends Exception {
	
	public NoAvailableException(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}

}
