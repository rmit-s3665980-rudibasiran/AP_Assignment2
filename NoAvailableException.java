package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Sherri McRae <s3117889@student.rmit.edu.au> 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 19 May 2018 
Description: NoAvailableException
Notes: --
Change History:
 */

public class NoAvailableException extends Exception {

	public NoAvailableException(String errMsg) {
		super(errMsg);
		new AlertDialog(errMsg, AlertDialog.ICON_INFO).showAndWait();
	}

}