package AP_Assignment2;

public class TooYoungException extends Exception {

	public TooYoungException(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}
}
