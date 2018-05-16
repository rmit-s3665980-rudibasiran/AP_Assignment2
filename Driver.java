package AP_Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.TextField;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 16 May 2018 
Description: New Driver
Notes: --

Change History: 
- Rudi Basiran <s3665980@student.rmit.edu.au> : 16 May 2018 : Created based on old driver

 */

public class Driver {

	private ArrayList<Person> _network = new ArrayList<Person>();
	private ArrayList<Relationship> _relationship = new ArrayList<Relationship>();

	public Driver() {
		_network = new ArrayList<Person>();
		_relationship = new ArrayList<Relationship>();
	}

	public ArrayList<Person> getNetwork() {
		return _network;
	}

	public ArrayList<Relationship> getRelationship() {
		return _relationship;
	}

	public void loadData() {
		String path = "/Users/rudibasiran/Google Drive/RMIT/Java/oxygen/JavaFX/AP_Assignment2/";
		// start: initial set up of network for demo
		try {

			Scanner input = new Scanner(new File(path + "people.txt"));
			input.useDelimiter(";|\n");

			while (input.hasNext()) {

				String name = input.next();
				String strAge = input.next();
				int age = Integer.parseInt(strAge);
				String gender = input.next();
				String info = input.next();
				addPerson(name, age, gender, info);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Scanner input = new Scanner(new File(path + "relationships.txt"));
			input.useDelimiter(";|\n");

			while (input.hasNext()) {

				String person1 = input.next();
				String conn = input.next();
				String person2 = input.next();

				int relationship = -1;
				for (int i = 0; i < Helper.roleDesc.length; i++)
					if (Helper.roleDesc[i].equals(conn))
						relationship = i;

				if (findPerson(person1) & findPerson(person2)) {
					Person p1 = _network.get(getIndexByProperty(person1));
					Person p2 = _network.get(getIndexByProperty(person2));
					_relationship.add(new Relationship(p1, relationship, p2));
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean findPerson(String name) {
		return ((getIndexByProperty(name) >= 0) ? true : false);
	}

	public String findPerson(TextField t[]) {
		String output = "";

		String name = t[0].getText().toString();
		if (findPerson(name)) {
			output = "[" + _network.get(getIndexByProperty(name)).getName() + "] found.";
		} else
			output = "[" + name + "] not found.";

		return output;
	}

	public void addPerson(String name, int age, String gender, String info) {
		if (age > Helper.minorAge) {
			Adult a = new Adult(name, age, gender, info);
			_network.add(a);
		} else if (age <= Helper.minorAge & age > Helper.babyAge) {
			Child c = new Child(name, age, gender, info);
			_network.add(c);
		} else if (age >= Helper.babyAge) {
			YoungChild y = new YoungChild(name, age, gender);
			_network.add(y);
		}
	}

	public String addPerson(TextField t[]) {
		String output = "";

		String name = t[0].getText().toString();
		String strAge = t[1].getText().toString();
		int age = Integer.parseInt(strAge);
		String gender = t[2].getText().toString();
		String info = t[3].getText().toString();

		if (findPerson(name)) {
			output = "[" + _network.get(getIndexByProperty(name)).getName() + "] already exists.";
		} else {
			addPerson(name, age, gender, info);
			output = "[" + name + "] created.";
		}
		return output;
	}

	public int getIndexByProperty(String name) {
		int result = -1;
		for (int i = 0; i < _network.size(); i++) {
			if (_network.get(i).getName().toUpperCase().equals(name.toUpperCase())) {
				result = i;
				break;
			}
		}
		return result;
	}

	public String displayProfile(TextField t[]) {
		String output = "";
		String name = t[0].getText().toString();

		if (findPerson(name)) {
			output = "[" + _network.get(getIndexByProperty(name)).getName() + "] already exists.";
			Person p = _network.get(getIndexByProperty(name));
			output = "Name: " + p.getName() + ", (" + p.getGender() + "), " + p.getAge();
			if (p instanceof Adult) {
				Adult a = (Adult) p;
				if (a.getInfo() != null)
					output = output + "\n" + "About: " + (a.getInfo().isEmpty() ? "-" : a.getInfo());

				// findSpouse(p, connection, Helper.showDetails);
				// findChildren(p, connection, Helper.showDetails);
				// findFriends(p, connection, Helper.showDetails);
			}
			if (p instanceof Child) {
				Child c = (Child) p;
				// findParents(c, connection, Helper.showDetails);
				// findFriends(c, connection, Helper.showDetails);
			}

		} else {
			output = "[" + name + "] not found.";
		}

		return output;
	}

}
