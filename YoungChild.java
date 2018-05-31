package AP_Assignment2;

import java.io.Serializable;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Sherri McRae <s3117889@student.rmit.edu.au> 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created:  9 April 2018
Description: YoungChild Class <= 2 years old
Notes: --
Change History:
 */

public class YoungChild extends Person implements Serializable {

	public YoungChild(String name, int age, String g, String i, String s) {
		super(name, age, g, i, s);

	}

	public YoungChild(String name, int age, String g) {
		super(name, age, g);

	}

	public YoungChild(String name, int age, String g, String i) {
		super(name, age, g, i);

	}

}
