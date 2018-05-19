package AP_Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

	public void loadDataDB() {

	}

	public void writeDataDB() {

	}

	public void loadData() {
		// String path = "/Users/rudibasiran/Google
		// Drive/RMIT/Java/oxygen/JavaFX/AP_Assignment2/";

		// start: initial set up of network for demo
		try {

			Scanner input = new Scanner(new File(Helper.path + "people.txt"));
			input.useDelimiter(";|\n");

			while (input.hasNext()) {
				// Name;Photo;Info;Gender;Age;State
				String name = input.next();
				String photo = input.next();
				String info = input.next();
				String gender = input.next();
				String strAge = input.next();
				int age = Integer.parseInt(strAge);
				String state = input.next();

				addPerson(name, age, gender, info, state);

			}
		} catch (NumberFormatException e) {
		} catch (FileNotFoundException e) {
		}

		try {
			Scanner input = new Scanner(new File(Helper.path + "relation.txt"));
			input.useDelimiter(";|\n");

			while (input.hasNext()) {

				String person1 = input.next();
				String person2 = input.next();
				String conn = input.next();

				int relationship = -1;
				for (int i = 0; i < Helper.roleDesc.length; i++)
					if (Helper.roleDesc[i].toLowerCase().equals(conn.toLowerCase())) {
						relationship = i;
						break;
					}

				if (findPerson(person1) & findPerson(person2) & relationship >= 0) {
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

	public void addPerson(String name, int age, String gender, String info, String state)throws NoSuchAgeException {
		if (age > Helper.ChildAge) {
			Adult a = new Adult(name, age, gender, info, state);
			_network.add(a);
		} else if (age <= Helper.ChildAge & age > Helper.YoungChildAge) {
			Child c = new Child(name, age, gender, info, state);
			_network.add(c);
		} else if (age <= Helper.YoungChildAge) {
			YoungChild y = new YoungChild(name, age, gender, info, state);
			_network.add(y);
		} else if (age < 0 || age > 150) {
					throw new NoSuchAgeException("You can not enter an age below zero or above 150.");
			}

							
						
			
		}
	}

	public String addPerson(TextField t[], ComboBox<String> sCombobox, ToggleGroup rbgGender) {
		String output = "";
		Boolean proceed = false;

		try {
			String name = t[0].getText().toString();
			String strAge = t[1].getText().toString();
			int age = Integer.parseInt(strAge);
			RadioButton chkGender = (RadioButton) rbgGender.getSelectedToggle();
			String strGender = chkGender.getText();
			String gender = String.valueOf(strGender.charAt(0));
			String info = t[3].getText().toString();
			String state = sCombobox.getValue();

			if (findPerson(name)) {
				output = "[" + _network.get(getIndexByProperty(name)).getName() + "] already exists.";
			} else {

				proceed = false;
				for (int i = 0; i < Helper.stateDesc.length; i++) {
					if (state.equals(Helper.stateDesc[i])) {
						proceed = true;
						break;
					}
				}

				if (!proceed) {
					output = output + "[" + state + "] is not a valid state.\n";
					output = output + "Valid states are:-\n";
					for (int i = 0; i < Helper.stateDesc.length; i++) {
						output = output + (i == 0 ? "-" : "\n- ") + Helper.stateDesc[i];
					}
				}
				if (proceed) {
					addPerson(name, age, gender, info, state);
					output = "[" + name + "] has been added to MiniNet.";
				}
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

			if (p.getInfo() != null)
				output = output + "\n" + "About: " + (p.getInfo().isEmpty() ? "-" : p.getInfo());

			if (p.getState() != null)
				output = output + "\n" + "State: " + (p.getState().isEmpty() ? "-" : p.getState());

			if (haveSpouse(p))
				output = output + "\n" + findSpouse(p);

			if (haveParents(p))
				output = output + "\n" + findParents(p);

			if (haveChildren(p))
				output = output + "\n" + findChildren(p);

			if (haveSiblings(p))
				output = output + "\n" + findSiblings(p);

			if (haveConnections(p, Helper.friend))
				output = output + "\n" + findConnections(p, Helper.friend);

			if (haveConnections(p, Helper.colleague))
				output = output + "\n" + findConnections(p, Helper.colleague);

			if (haveConnections(p, Helper.classmate))
				output = output + "\n" + findConnections(p, Helper.classmate);

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
				output = output + ((count == 1) ? "Children :\n- " : "\n- ")
						+ _relationship.get(i).getPersonB().getName();
			}
		}

		if (!found)
			output = "";
		return output;
	}

	public String findConnections(TextField t[], int type) {
		String output = "";

		String name = t[0].getText().toString();

		if (findPerson(name)) {
			Person p = _network.get(getIndexByProperty(name));
			if (haveConnections(p, type))
				output = output + "\n" + findConnections(p, type);
		} else
			output = "[" + name + "] not found.";
		return output;
	}

	public Boolean haveConnections(Person p, int type) {
		return (findConnections(p, type).equals("") ? false : true);
	}

	public String findConnections(Person p, int type) {
		int count = 0;
		String output = "";
		Boolean found = false;

		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonA().getName().equals(p.getName())
					& _relationship.get(i).getConn() == type) {
				count++;
				found = true;
				output = output + ((count == 1) ? Helper.roleDesc[type] + "(s)" + " :\n- " : "\n- ")
						+ _relationship.get(i).getPersonB().getName();
			}
		}

		for (int i = 0; i < _relationship.size(); i++) {
			if (_relationship.get(i).getPersonB().getName().equals(p.getName())
					& _relationship.get(i).getConn() == type) {
				count++;
				found = true;
				output = output + ((count == 1) ? Helper.roleDesc[type] + "(s)" + " :\n- " : "\n- ")
						+ _relationship.get(i).getPersonA().getName();
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

	public void updateProfile(Person p, String name, int age, String info, String state, String gender) {
		p.setName(name);
		p.setAge(age);
		p.setGender(gender);
		p.setState(state);
		p.setInfo(info);
	}

	public String updateProfile(TextField t[], ComboBox<String> connComboBox, ToggleGroup rbgGender) {
		Boolean proceed = true;
		String output = "";

		try {
			String name = t[0].getText().toString();
			String strAge = t[1].getText().toString();
			int newAge = Integer.parseInt(strAge);

			RadioButton chkGender = (RadioButton) rbgGender.getSelectedToggle();
			String strGender = chkGender.getText();
			String newGender = String.valueOf(strGender.charAt(0));

			String newInfo = t[3].getText().toString();
			String newState = connComboBox.getValue();

			proceed = false;
			for (int i = 0; i < Helper.stateDesc.length; i++) {
				if (newState.equals(Helper.stateDesc[i])) {
					proceed = true;
					break;
				}
			}
			if (!proceed) {
				output = output + "[" + newState + "] is not a valid state.\n";
				output = output + "Valid states are:-\n";
				for (int i = 0; i < Helper.stateDesc.length; i++) {
					output = output + (i == 0 ? "-" : "\n- ") + Helper.stateDesc[i];
				}
			}

			if (proceed) {
				if (findPerson(name)) {
					Person p = _network.get(getIndexByProperty(name));

					if (p instanceof Adult) {
						Adult a = (Adult) p;

						if (newAge <= Helper.ChildAge) {
							// test adult with spouse change age
							if (haveSpouse(p)) {
								output = output + "You cannot change your age to " + newAge
										+ " as you have a spouse.\n";
								proceed = false;
							}
							// test adult with spouse change age
							if (haveChildren(p)) {
								output = output + "You cannot change your age to " + newAge
										+ " as you have children.\n";
								proceed = false;
							}

						}

						if (!newGender.equals(p.getGender())) {
							if (haveSpouse(p)) {
								output = output + "You cannot change your gender to " + strGender
										+ " as you have a spouse.\n";
								proceed = false;
							}

							if (haveChildren(p)) {
								output = output + "You cannot change your gender to " + strGender
										+ " as you have children.\n";
								proceed = false;
							}
						}

						if (proceed) {
							updateProfile(a, a.getName(), newAge, newInfo, newState, newGender);
						}

						// need to upgrade/downgrade person: not_done_yet

					} else if (p instanceof Child) {
						Child c = (Child) p;

						if (newAge <= Helper.YoungChildAge) {
							// test child change age to baby
							if (haveConnections(p, Helper.friend)) {
								output = output + "You cannot change your age to " + newAge
										+ " as you have friends; children below " + Helper.YoungChildAge
										+ " cannot have friends.\n";
								proceed = false;
							}
						} else if (newAge > Helper.ChildAge) {
							// test child with parents change age
							if (haveParents(p)) {
								output = output + "You cannot change your age to above " + Helper.ChildAge
										+ " as you have linked parents.\n";
								proceed = false;
							}

							// test child change age who has friends within age-gap
							for (int i = 0; i < _relationship.size(); i++) {

								if ((_relationship.get(i).getPersonA().getName().equals(p.getName())
										& _relationship.get(i).getConn() == Helper.friend
										& Math.abs(
												(_relationship.get(i).getPersonB().getAge() - newAge)) > Helper.ageGap)
										| (_relationship.get(i).getPersonB().getName().equals(p.getName())
												& _relationship.get(i).getConn() == Helper.friend
												& Math.abs((_relationship.get(i).getPersonA().getAge()
														- newAge)) > Helper.ageGap)) {
									output = output + "You cannot change your age to " + newAge
											+ " as you have friends who are within the " + Helper.ageGap
											+ "-year age gap.\n";
									proceed = false;
								}
							}
						}

						if (proceed) {
							if (newAge <= Helper.ChildAge) {
								YoungChild y = (YoungChild) p;
								// need to clone, rebuild all relationships & delete old instance : not_done_yet
							}
							updateProfile(c, c.getName(), newAge, newInfo, newState, newGender);
						}
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

		if (findPerson(name)) {
			Person p = _network.get(getIndexByProperty(name));
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

	public String connectPerson(TextField t[], ComboBox<String> connComboBox) {
		Boolean proceed = true;
		String output = "";
		// classmates for child
		// colleagues for adults

		String name1 = t[0].getText().toString();
		String name2 = t[1].getText().toString();
		String strConn = connComboBox.getValue();
		int conn = -1;
		for (int i = 0; i < Helper.roleDesc.length; i++) {
			if (Helper.roleDesc[i].equals(strConn)) {
				conn = i;
				break;
			}
		}

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
						+ " are adults; they cannot have parent/child relationships.\n";
				proceed = false;
			}
			// NotToBeCoupledException
			try {
				if (!proceed) {
					throw new NotToBeCoupled(output);
				}
			} catch (Exception e) {
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
			// NotToBeFriendsException
				try {
					if (!proceed) {
						throw new NotToBeFriends(output);
					}
				} catch (Exception e) {
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
			// NotToBeFriendsException
			try {
				if (!proceed) {
					throw new NotToBeFriends(output);
					}
				} catch (Exception e) {
			}
			
			// test child below 2 connect friend
			if ((p instanceof Child | q instanceof Child) & conn == Helper.friend) {
				if (p.getAge() <= Helper.YoungChildAge | q.getAge() <= Helper.YoungChildAge) {
					output = output + "Children below " + Helper.YoungChildAge + " cannot have friends.\n";
					proceed = false;
				}
			}
			// TooYoungException
			try {
				if (!proceed) {
					throw new TooYoungException(output);
					}
				} catch (Exception e) {
			}

			// test connect spouse (1 not adult)
			if ((p instanceof Child | q instanceof Child) & conn == Helper.spouse) {
				output = output + "Children cannot have spouses.\n";
				proceed = false;
			}
			// NotToBeCoupledException
			try {
				if (!proceed) {
					throw new NotToBeCoupledException(output);
					}
				} catch (Exception e) {
			}

			// test connect spouse (spouse already exists)
			if ((p instanceof Adult & q instanceof Adult) & conn == Helper.spouse) {

				if (haveSpouse(p)) {
					output = output + p.getName() + " already has a spouse; please get divorced first.\n";
					proceed = false;
				}

				if (haveSpouse(q)) {
					output = output + q.getName() + " already has a spouse; please get divorced first.\n";
					proceed = false;
				}
				if (p.getGender().equals(q.getGender())) {
					output = "While we support your rights, our system doesn't cater to that yet.\n";
					proceed = false;
				}

				// NoAvailableException
				try {
					if (!proceed) {
						throw new NoAvailableException(output);
					}
				} catch (Exception e) {
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
			if (proceed) {
				_relationship.add(new Relationship(p, conn, q));
				for (int i = 0; i < _relationship.size(); i++) {
					if (r.getPersonA().getName().equals(_relationship.get(i).getPersonA().getName())
							& r.getConn() == _relationship.get(i).getConn()
							& r.getPersonB().getName().equals(_relationship.get(i).getPersonB().getName())) {
						output = output + "Connection added:\n";
						output = output + p.getName() + " < " + Helper.roleDesc[_relationship.get(i).getConn()] + "> "
								+ q.getName() + "\n";
					}
				}
			}
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

	public String findFamily(TextField t[]) {
		String output = "";
		String name = t[0].getText().toString();

		if (!findPerson(name)) {
			output = "[" + name + "] not found.\n";
		} else {
			Person p = _network.get(getIndexByProperty(name));

			if (haveParents(p))
				output = output + "\n" + findParents(p);

			if (haveSpouse(p))
				output = output + "\n" + findSpouse(p);

			if (haveChildren(p))
				output = output + "\n" + findChildren(p);

			if (haveSiblings(p))
				output = output + "\n" + findSiblings(p);
		}

		if (output.equals(""))
			output = "No family members saved in MiniNet.\n";
		return output;
	}

	public Boolean haveSiblings(Person p) {
		return (findSiblings(p).equals("") ? false : true);
	}

	public String findSiblings(Person p) {
		Boolean found = false;
		String output = "";
		HashMap<String, Person> siblings = new HashMap<>();

		int tParent[] = { Helper.father, Helper.mother };

		for (int x = 0; x < tParent.length; x++) {
			for (int i = 0; i < _relationship.size(); i++) {
				// find parents of child first
				if (_relationship.get(i).getPersonB().getName().equals(p.getName())
						& (_relationship.get(i).getConn() == tParent[x])) {

					Person parent = _relationship.get(i).getPersonA();

					for (int j = 0; j < _relationship.size(); j++) {
						if (_relationship.get(j).getPersonA().getName().equals(parent.getName())
								& (_relationship.get(j).getConn() == tParent[x])) {
							Person child = _relationship.get(j).getPersonB();
							siblings.put(child.getName(), child);
						}
					}
				}
			}
		}

		siblings.remove(p.getName());

		int count = 0;
		for (Entry<String, Person> entry : siblings.entrySet()) {
			count++;
			found = true;
			output = output + ((count == 1) ? "Sibling(s) " + " :\n- " : "\n- ") + entry.getValue().getName();
		}

		if (!found)
			output = "";
		return output;
	}

}
