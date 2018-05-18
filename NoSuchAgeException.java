package AP_Assignment2;

public class NoSuchAgeException extends Exception {
	
	public NoSuchAgeException(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}

}
