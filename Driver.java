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
		} catch (FileNotFoundException e) {
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
					if (Helper.roleDesc[i].toLowerCase().equals(conn.toLowerCase())) {
						relationship = i;
						break;
					}

				if (findPerson(person1) & findPerson(person2)) {
					Person p1 = _network.get(getIndexByProperty(person1));
					Person p2 = _network.get(getIndexByProperty(person2));
					_relationship.add(new Relationship(p1, relationship, p2));
				}
			}
		} catch (NumberFormatException e) {
		} catch (FileNotFoundException e) {
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

		try {
			String name = t[0].getText().toString();
			String strAge = t[1].getText().toString();
			int age = Integer.parseInt(strAge);
			String gender = t[2].getText().toString();
			String info = t[3].getText().toString();

			if (findPerson(name)) {
				output = "[" + _network.get(getIndexByProperty(name)).getName() + "] already exists.";
			} else {
				addPerson(name, age, gender, info);
				output = "[" + name + "] has been added to MiniNet.";
			}
		} catch (NumberFormatException e) {
			output = "Age should be integer.";
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

	public String displayProfile(String name) {
		String output = "";
		if (findPerson(name)) {
			Person p = _network.get(getIndexByProperty(name));
			output = "Name: " + p.getName() + ", (" + p.getGender() + "), " + p.getAge();
			if (p instanceof Adult) {
				Adult a = (Adult) p;
				if (a.getInfo() != null)
					output = output + "\n" + "About: " + (a.getInfo().isEmpty() ? "-" : a.getInfo());

				if (haveSpouse(a))
					output = output + "\n" + findSpouse(a);

				if (haveChildren(a))
					output = output + "\n" + findChildren(a);

				if (haveFriends(a))
					output = output + "\n" + findFriends(a);

			} else if (p instanceof Child) {
				Child c = (Child) p;

				if (haveParents(c))
					output = output + "\n" + findParents(c);

				if (haveFriends(c))
					output = output + "\n" + findFriends(c);
			}
		}
		return output;
	}

	public String displayProfile(TextField t[]) {
		String output = "";
		String name = t[0].getText().toString();

		if (findPerson(name)) {
			output = output + displayProfile(name);

		} else {
			output = "[" + name + "] not found.";
		}
		return output;
	}

	public Boolean haveSpouse(Person p) {
		return (findSpouse(p).equals("") ? false : true);
	}

	public String findSpouse(Person p) {
		String output = "";
		Boolean found = false;
		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonA().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.spouse) {
				output = "Spouse: " + _relationship.get(i).getPersonB().getName();
				found = true;

			} else if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.spouse) {
				output = "Spouse: " + _relationship.get(i).getPersonA().getName();
				found = true;
			}
		}
		if (!found)
			output = "";
		return output;
	}

	public Boolean haveChildren(Person p) {
		return (findChildren(p).equals("") ? false : true);
	}

	public String findChildren(Person p) {
		String output = "";
		int count = 0;
		Boolean found = false;
		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonA().getName().equals(p.getName())
					& (_relationship.get(i).getConn() == Helper.father
							| _relationship.get(i).getConn() == Helper.mother)) {
				count++;
				found = true;
				output = output + ((count == 1) ? "Children :\n-" : "\n-")
						+ _relationship.get(i).getPersonB().getName();
			}
		}

		if (!found)
			output = "";
		return output;
	}

	public Boolean haveFriends(Person p) {
		return (findFriends(p).equals("") ? false : true);
	}

	public String findFriends(Person p) {
		int count = 0;
		String output = "";
		Boolean found = false;

		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonA().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.friend) {
				count++;
				found = true;
				output = output + ((count == 1) ? "Friends :\n-" : "\n-") + _relationship.get(i).getPersonB().getName();
			}
			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.friend) {
				count++;
				found = true;
				output = output + ((count == 1) ? "Friends :\n-" : "\n-") + _relationship.get(i).getPersonA().getName();
			}
		}
		if (!found)
			output = "";
		return output;
	}

	public Boolean haveParents(Person p) {
		return (findParents(p).equals("") ? false : true);
	}

	public String findParents(Person p) {
		Boolean found = false;
		String output = "";
		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.father) {
				found = true;
				output = output + "Father: " + _relationship.get(i).getPersonA().getName() + "\n";
			}

			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.mother) {
				found = true;
				output = output + "Mother: " + _relationship.get(i).getPersonA().getName() + "\n";
			}
		}
		if (!found)
			output = "";
		return output;
	}

	public String displayAllProfile() {

		String output = "";
		for (int i = 0; i < _network.size(); i++) {
			output = output + "\n\n" + displayProfile(_network.get(i).getName());
		}
		return output;
	}

	public String updateProfile(TextField t[]) {
		Boolean proceed = true;
		String output = "";

		try {
			String name = t[0].getText().toString();
			String strAge = t[1].getText().toString();
			int newAge = Integer.parseInt(strAge);
			String newGender = t[2].getText().toString();
			String newInfo = t[3].getText().toString();

			Person p = _network.get(getIndexByProperty(name));

			if (p instanceof Adult) {
				Adult a = (Adult) p;

				if (newAge <= Helper.minorAge) {
					// test adult with spouse change age
					if (haveSpouse(p)) {
						output = output + "You cannot change your age to " + newAge + " as you have a spouse.\n";
						proceed = false;
					}
					// test adult with spouse change age
					if (haveChildren(p)) {
						output = output + "You cannot change your age to " + newAge + " as you have children.\n";
						proceed = false;
					}

				}

				if (!newGender.equals(p.getGender())) {
					if (haveSpouse(p)) {
						output = output + "You cannot change your gender to " + newGender + " as you have a spouse.\n";
						proceed = false;
					}

					if (haveChildren(p)) {
						output = output + "You cannot change your gender to " + newGender + " as you have children.\n";
						proceed = false;
					}

					if (!newGender.equals("M") | !newGender.equals("F")) {
						output = output + "Gender should be M or F only.\n";
						proceed = false;
					}
				}

				if (proceed) {
					a.setAge(newAge);
					a.setGender(newGender);
				}

				// need to upgrade/downgrade person: not_done_yet

			} else if (p instanceof Child) {
				Child c = (Child) p;

				if (newAge <= Helper.babyAge) {
					// test child change age to baby
					if (haveFriends(p)) {
						output = output + "You cannot change your age to " + newAge
								+ " as you have friends; children below " + Helper.babyAge + " cannot have friends.\n";
						proceed = false;
					}
				} else if (newAge > Helper.minorAge) {
					// test child with parents change age
					if (haveParents(p)) {
						output = output + "You cannot change your age to above " + Helper.minorAge
								+ " as you have linked parents.\n";
						proceed = false;
					}

					// test child change age who has friends within age-gap
					for (int i = 0; i < _relationship.size(); i++) {

						if ((_relationship.get(i).getPersonA().getName().equals(p.getName())
								& _relationship.get(i).getConn() == Helper.friend
								& Math.abs((_relationship.get(i).getPersonB().getAge() - newAge)) > Helper.ageGap)
								| (_relationship.get(i).getPersonB().getName().equals(p.getName())
										& _relationship.get(i).getConn() == Helper.friend
										& Math.abs((_relationship.get(i).getPersonA().getAge()
												- newAge)) > Helper.ageGap)) {
							output = output + "You cannot change your age to " + newAge
									+ " as you have friends who are within the " + Helper.ageGap + "-year age gap.\n";
							proceed = false;
						}
					}
				}
				if (proceed) {
					if (newAge <= Helper.minorAge) {
						YoungChild y = (YoungChild) p;
						// need to clone, rebuild all relationships & delete old instance : not_done_yet
						y.setAge(newAge);
					}

				}
			}
			if (proceed)
				output = "Record updated.";
		} catch (NumberFormatException e) {
			output = "Age should be integer.";
		}
		return output;

	}

	public String deletePerson(TextField t[]) {

		String output = "";

		String name = t[0].getText().toString();
		Person p = _network.get(getIndexByProperty(name));
		if (findPerson(name)) {
			for (int i = _relationship.size() - 1; i >= 0; i--) {
				if (_relationship.get(i).getPersonA().getName().equals(p.getName()))
					_relationship.remove(i);
			}

			for (int i = _relationship.size() - 1; i >= 0; i--) {
				if (_relationship.get(i).getPersonB().getName().equals(p.getName()))
					_relationship.remove(i);
			}

			_network.remove(p);
			output = "[" + name + "] removed from MiniNet.";
		} else
			output = "[" + name + "] not found.";
		return output;
	}

	public String connectPerson(TextField t[]) {
		Boolean proceed = true;
		String output = "";

		try {
			String name1 = t[0].getText().toString();
			String name2 = t[1].getText().toString();
			String strConn = t[2].getText().toString();
			int conn = Integer.parseInt(strConn);

			if (!findPerson(name1)) {
				output = "[" + name1 + "] not found.\n";
				proceed = false;
			} else if (!findPerson(name2)) {
				output = "[" + name2 + "] not found.\n";
				proceed = false;
			} else {
				proceed = false;
				for (int i = 0; i < Helper.roleDesc.length; i++) {
					if (conn == i) {
						proceed = true;
						break;
					}
				}
				if (!proceed)
					output = "[" + conn + "] is not a valid relationship.\n";
			}

			if (proceed) {

				Person p = _network.get(getIndexByProperty(name1));
				Person q = _network.get(getIndexByProperty(name2));

				// test 2 adults set as parents to each other
				if (p instanceof Adult & q instanceof Adult & (conn == Helper.father | conn == Helper.mother)) {
					output = output + p.getName() + " and " + q.getName()
							+ " are adults; they cannot have parent/child relatonships.\n";
					proceed = false;
				}
				// test child connect friend (child inside family)
				if (p instanceof Child & q instanceof Child) {
					for (int i = 0; i < _relationship.size(); i++) {
						// find p's father
						if (_relationship.get(i).getPersonB().getName().equals(p.getName())
								& _relationship.get(i).getConn() == Helper.father) {
							for (int j = 0; j < _relationship.size(); j++) {
								// check if q's father is same as p's
								if (_relationship.get(j).getPersonB().getName().equals(q.getName())
										& _relationship.get(j).getConn() == Helper.father
										& _relationship.get(j).getPersonA().getName()
												.equals(_relationship.get(i).getPersonA().getName())) {
									output = output + p.getName() + " and " + q.getName()
											+ " are siblings; they cannot be friends.\n";
									proceed = false;
									break;
								}
							}
						}
						// find p's mother
						if (_relationship.get(i).getPersonB().getName().equals(p.getName())
								& _relationship.get(i).getConn() == Helper.mother) {
							for (int j = 0; j < _relationship.size(); j++) {
								// check if q's father is same as p's
								if (_relationship.get(j).getPersonB().getName().equals(q.getName())
										& _relationship.get(j).getConn() == Helper.mother
										& _relationship.get(j).getPersonA().getName()
												.equals(_relationship.get(i).getPersonA().getName())) {
									output = output + p.getName() + " and " + q.getName()
											+ " are siblings; they cannot be friends.\n";
									proceed = false;
									break;
								}
							}
						}
					}
				}
				// test child connect friend (child within age-range)
				if (p instanceof Child & q instanceof Child) {
					if (Math.abs(p.getAge() - q.getAge()) > Helper.ageGap) {
						output = output + "Age gap between children wishing to connect as friends is " + Helper.ageGap
								+ ".\n";
						proceed = false;
					}
				}

				// test child connect adult as parent, should be other way
				if ((conn == Helper.father || conn == Helper.mother) & (p instanceof Child & q instanceof Adult)) {
					output = output + "To connect as parents, the Adult's name should be entered first.\n";
					proceed = false;
				}

				// test adult connect child (correct gender)
				if ((conn == Helper.father & !p.getGender().equals("M"))
						| (conn == Helper.mother & !p.getGender().equals("F"))) {
					output = output + "To connect as parents, the Adult's gender must be correct.\n";
					proceed = false;
				}

				// test adult connect child (father already exists)
				if ((conn == Helper.father && haveFather(q)) & (p instanceof Adult & q instanceof Child)) {
					output = output + "Father already exists.\n";
					proceed = false;
				}

				// test adult connect child (mother already exists)
				if ((conn == Helper.mother && haveMother(q)) & (p instanceof Adult & q instanceof Child)) {
					output = output + "Mother already exists.\n";
					proceed = false;
				}

				// test child connect friend (adult)
				if (conn == Helper.friend
						& ((p instanceof Adult & q instanceof Child) | (p instanceof Child & q instanceof Adult))) {
					output = output + "Adults cannot be friends with Children.\n";
					proceed = false;

				}
				// test child below 2 connect friend
				if ((p instanceof Child | q instanceof Child) & conn == Helper.friend) {
					if (p.getAge() <= Helper.babyAge | q.getAge() <= Helper.babyAge) {
						output = output + "Children below " + Helper.babyAge + " cannot have friends.\n";
						proceed = false;
					}
				}

				// test connect spouse (1 not adult)
				if ((p instanceof Child | q instanceof Child) & conn == Helper.spouse) {
					output = output + "Children cannot have spouses.\n";
					proceed = false;
				}

				// test connect spouse (spouse already exists)
				if ((p instanceof Adult & q instanceof Adult) & conn == Helper.spouse) {
					if (haveSpouse(p)) {
						output = output + p.getName() + " already have a spouse; please get divorced first.\n";
						proceed = false;
					}
					if (haveSpouse(p)) {
						output = output + q.getName() + " already have a spouse; please get divorced first.\n";
						proceed = false;
					}
					if (p.getGender().equals(q.getGender())) {
						output = "While we support your rights, our system doesn't cater to that yet.\n";
						proceed = false;
					}
				}

				// test connect spouse (no spouse yet, both adults)
				if ((p instanceof Adult & q instanceof Adult) & conn == Helper.spouse) {
					if (!haveSpouse(p) & !haveSpouse(q)) {
						output = output + "Congratulations! Just Married!\n";
					}
				}

				// test connect friends (connections already exists) 999
				Relationship r = new Relationship(p, conn, q);

				for (int i = 0; i < _relationship.size(); i++) {
					if (r.getPersonA().getName().equals(_relationship.get(i).getPersonA().getName())
							& r.getConn() == _relationship.get(i).getConn()
							& r.getPersonB().getName().equals(_relationship.get(i).getPersonB().getName())) {
						output = output + "Connection already exists:\n";
						output = output + p.getName() + " < " + Helper.roleDesc[_relationship.get(i).getConn()] + "> "
								+ q.getName() + "\n";
						proceed = false;
					}
				}

				// test connect relationship already exists (other way)
				for (int i = 0; i < _relationship.size(); i++) {
					if (r.getPersonA().getName().equals(_relationship.get(i).getPersonB().getName())
							& r.getConn() == _relationship.get(i).getConn()
							& r.getPersonB().getName().equals(_relationship.get(i).getPersonA().getName())) {
						output = output + "Connection already exists:\n";
						output = output + p.getName() + " < " + Helper.roleDesc[_relationship.get(i).getConn()] + "> "
								+ q.getName() + "\n";
						proceed = false;
					}
				}
				if (proceed)
					_relationship.add(new Relationship(p, conn, q));
			}
		} catch (NumberFormatException e) {
			output = "Invalid relationship entered.";

		}
		return output;
	}

	public Boolean haveFather(Person p) {
		Boolean found = false;
		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.father) {
				found = true;
			}
		}
		return found;
	}

	public Boolean haveMother(Person p) {
		Boolean found = false;
		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == Helper.mother) {
				found = true;
			}
		}
		return found;
	}

	public String findConnection(TextField t[]) {

		Boolean proceed = true;
		String output = "";
		Boolean found = false;

		String name1 = t[0].getText().toString();
		String name2 = t[1].getText().toString();

		if (!findPerson(name1)) {
			output = "[" + name1 + "] not found.\n";
			proceed = false;
		} else if (!findPerson(name2)) {
			output = "[" + name2 + "] not found.\n";
			proceed = false;
		} else {
			Person p = _network.get(getIndexByProperty(name1));
			Person q = _network.get(getIndexByProperty(name2));
			for (int i = 0; i < _relationship.size(); i++) {
				if ((_relationship.get(i).getPersonA().getName().equals(p.getName())
						& _relationship.get(i).getPersonB().getName().equals(q.getName()))
						| (_relationship.get(i).getPersonA().getName().equals(q.getName())
								& _relationship.get(i).getPersonB().getName().equals(p.getName()))) {
					found = true;
					output = p.getName() + " < " + Helper.roleDesc[_relationship.get(i).getConn()] + "> " + q.getName();
					break;
				}
			}
			if (!found)
				output = p.getName() + " and " + q.getName() + " are not connected.";
		}
		return output;
	}
}
