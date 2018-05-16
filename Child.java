package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Child Class (Between 3 and 16 inclusive)
Notes: --
 */

public class Child extends Person {

	String _info;

	public Child(String name, int age, String g) {
		super(name, age, g);

	}

	public Child(String name, int age, String g, String i) {
		super(name, age, g);
		_info = i;
	}

	public void setInfo(String info) {
		_info = info;
	}

	public String getInfo() {
		return _info;
	}

}
