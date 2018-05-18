package AP_Assignment2;

public class NotToBeColleagues extends Exception {
	
	public NotToBeColleagues(String errMsg) {
		super(errMsg); 
	    System.out.println("Error message is: " + errMsg);
	}


}
