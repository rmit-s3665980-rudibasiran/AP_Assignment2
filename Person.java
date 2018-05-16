package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Person Class 
Notes: --
 */

public abstract class Person {
	private String _name;
	private int _age;
	private String _gender;

	public Person() {
	}

	public Person(String n, int a, String g) {
		_name = n;
		_age = a;
		_gender = g;
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
}
