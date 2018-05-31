package AP_Assignment2;

import java.io.Serializable;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Sherri McRae <s3117889@student.rmit.edu.au> 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Child Class (Between 3 and 16 inclusive)
Notes: --
Change History:
 */

public class Child extends Person implements Serializable {

	public Child(String name, int age, String g, String i, String s) {
		super(name, age, g, i, s);

	}

	public Child(String name, int age, String g) {
		super(name, age, g);

	}

	public Child(String name, int age, String g, String i) {
		super(name, age, g, i);

	}

}
