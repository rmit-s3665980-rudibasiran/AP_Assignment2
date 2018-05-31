package AP_Assignment2;

import java.io.Serializable;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
- Sherri McRae <s3117889@student.rmit.edu.au> 

Date Created: 27 March 2018 
Description: Relationship Class 
Notes: --
Change History:
 */

public class Relationship implements Serializable {
	private Person _personA;
	private int _connection;
	private Person _personB;

	public Relationship(Person a, int conn, Person b) {
		_personA = a;
		_connection = conn;
		_personB = b;
	}

	public Person getPersonA() {
		return _personA;
	}

	public Person getPersonB() {
		return _personB;
	}

	public int getConn() {
		return _connection;
	}
}
