package AP_Assignment2;
/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Sherri McRae <s3117889@student.rmit.edu.au> 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Adult Class (Between 17 and 150 inclusive)
Notes: --
Change History:

 */

import java.io.Serializable;

public class Adult extends Person implements Serializable {

	public Adult() {

	}

	public Adult(String name, int age, String g, String i, String s) {
		super(name, age, g, i, s);

	}

	public Adult(String name, int age, String g, String i) {
		super(name, age, g, i);

		int i = 99;
	}

	public Adult(String name, int age, String g) {
		super(name, age, g);
	}

	public Adult(String name, int age) {
		super.setName(name);
		super.setAge(age);
	}

}
