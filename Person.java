package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Sherri McRae <s3117889@student.rmit.edu.au> 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Person class
Notes: --
Change History:
 */

public abstract class Person {
	private String _name;
	private int _age;
	private String _gender;
	private String _info;
	private String _state;

	public Person() {
	}

	public Person(String n, int a, String g) {
		_name = n;
		_age = a;
		_gender = g;
		_info = "";
		_state = "";
	}

	public Person(String n, int a, String g, String i) {
		_name = n;
		_age = a;
		_gender = g;
		_info = i;
		_state = "";
	}

	public Person(String n, int a, String g, String i, String s) {
		_name = n;
		_age = a;
		_gender = g;
		_info = i;
		_state = s;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getAge() {
		return _age;
	}

	public void setAge(int age) {
		_age = age;
	}

	public String getGender() {
		return _gender;
	}

	public void setGender(String g) {
		_gender = g;
	}

	public String getInfo() {
		return _info;
	}

	public void setInfo(String i) {
		_info = i;
	}

	public String getState() {
		return _state;
	}

	public void setState(String s) {
		_state = s;
	}
}
