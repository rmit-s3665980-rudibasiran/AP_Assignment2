package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Adult Class (Between 17 and 150 inclusive)
Notes: --
 */

public class Adult extends Person {

	String _info;
	String _baby;

	public Adult(String name, int age, String g, String info) {
		super.setName(name);
		super.setAge(age);
		super.setGender(g);
		_info = info;
	}

	public Adult(String name, int age, String g) {
		super.setName(name);
		super.setAge(age);
		super.setGender(g);
	}

	public Adult(String name, int age) {
		super.setName(name);
		super.setAge(age);
	}

	public void setInfo(String info) {
		_info = info;
	}

	public String getInfo() {
		return _info;
	}

}
