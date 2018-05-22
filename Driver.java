package AP_Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import org.hsqldb.Server;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
- Sherri McRae <s3117889@student.rmit.edu.au> 
Date Created: 16 May 2018 
Description: New Driver based on AP1
Notes: --
Change History: 
- Rudi Basiran <s3665980@student.rmit.edu.au> : 16 May 2018 : Created based on old driver
- Sherri McRae <s3117889@student.rmit.edu.au> : 22 May 2018 : Added Classmate and Colleague exceptions
- Rudi Basiran <s3665980@student.rmit.edu.au> : 22 May 2018 : Added DB reading/writing. 
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

	public void moveDriverToDatabase() {
		if (Helper.doDatabase) {
			if (Helper.doDatabase) {

				for (int i = 0; i < _network.size(); i++) {
					System.out.println("Verifying Person(s) in Database: " + _network.get(i).getName());
					loadDatabasePerson(_network.get(i).getName(), "", _network.get(i).getInfo(),
							_network.get(i).getGender(), _network.get(i).getAge(), _network.get(i).getState());
				}

				for (int i = 0; i < _relationship.size(); i++) {
					System.out.println(
							"Forging Relationships(s) in Database: " + _relationship.get(i).getPersonA().getName()
									+ " <<" + Helper.roleDesc[_relationship.get(i).getConn()] + ">> "
									+ _relationship.get(i).getPersonB().getName());

					loadDatabaseRelations(_relationship.get(i).getPersonA().getName(),
							_relationship.get(i).getPersonB().getName(), _relationship.get(i).getConn());
				}
			}
		}
	}

	public void loadData() {
		String peopleFile = "";
		String relationsFile = "";
		Boolean reset = true;

		// 4 possible scenarios
		if (Helper.doDatabase) {
			if (!DatabasePopulated() & _network.size() <= 0)
				reset = true;

			else if (DatabasePopulated() & _network.size() <= 0) {
				try {
					reloadDatabaseintoDriver();
				} catch (NoSuchAgeException e) {
				}
				reset = false;
			}

			else if (!DatabasePopulated() & _network.size() > 0)
				reset = true;

			else if (DatabasePopulated() & _network.size() > 0)
				reset = false;
		} else
			reset = true;

		if (reset) {
			try {
				destroyDatabase(); // flush the database

				if (Helper.doDatabase)
					createDatabase();

				peopleFile = "people.txt";
				relationsFile = "relation.txt";
				Scanner input = new Scanner(new File(Helper.path + peopleFile));
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
					if (findPerson(name)) {

					} else {
						if (age > Helper.ChildAge) {
							Adult a = new Adult(name, age, gender, info, state);
							_network.add(a);

						} else if (age <= Helper.ChildAge & age > Helper.YoungChildAge) {
							Child c = new Child(name, age, gender, info, state);
							_network.add(c);

						} else if (age <= Helper.YoungChildAge) {
							YoungChild y = new YoungChild(name, age, gender, info, state);
							_network.add(y);

						}

					}
				}
			} catch (NumberFormatException e) {

			} catch (FileNotFoundException e) {

			}

			try {
				Scanner input = new Scanner(new File(Helper.path + relationsFile));
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
			} catch (FileNotFoundException e) {
			}
		}

	}

	public void destroyDatabase() {

		try {
			Server hsqlServer = null;
			Connection connection = null;
			ResultSet rs = null;
			hsqlServer = new Server();
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);
			hsqlServer.setDatabaseName(0, Helper.databaseName);
			hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
			hsqlServer.start();
			Class.forName(Helper.jdbc);
			connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

			connection.prepareStatement("drop table person if exists;").execute();
			connection.prepareStatement("drop table relations if exists;").execute();

			connection.commit();
			connection.close();
			hsqlServer.stop();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createDatabase() {
		if (Helper.doDatabase) {
			try {
				Server hsqlServer = null;
				Connection connection = null;
				ResultSet rs = null;
				hsqlServer = new Server();
				hsqlServer.setLogWriter(null);
				hsqlServer.setSilent(true);
				hsqlServer.setDatabaseName(0, Helper.databaseName);
				hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
				hsqlServer.start();
				Class.forName(Helper.jdbc);
				connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

				connection.prepareStatement("drop table person if exists;").execute();
				connection.prepareStatement("drop table relations if exists;").execute();

				String sql = "";
				sql = "create table person (" + "name varchar(50) not null, " + "photo varchar(50), "
						+ "info varchar(100), " + "gender varchar(1), " + "age integer, "
						+ "state varchar(5), primary key (name));";
				try {
					connection.prepareStatement(sql).execute();
				} catch (Exception e) {

				}
				sql = "create table relations (name1 varchar(50) not null, name2 varchar(50) not null, conn integer, primary key (name1,name2));";
				try {
					connection.prepareStatement(sql).execute();
				} catch (Exception e) {

				}

				connection.commit();
				connection.close();
				hsqlServer.stop();

			} catch (

			ClassNotFoundException e) {
			} catch (SQLException e) {
			}
		}
	}

	public void reloadDatabaseintoDriver() throws NoSuchAgeException {
		if (Helper.doDatabase) {
			try {
				Server hsqlServer = null;
				Connection connection = null;
				ResultSet rs = null;
				hsqlServer = new Server();
				hsqlServer.setLogWriter(null);
				hsqlServer.setSilent(true);
				hsqlServer.setDatabaseName(0, Helper.databaseName);
				hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
				hsqlServer.start();
				Class.forName(Helper.jdbc);
				connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

				String sql = "select name, photo, info, gender, age, state from person order by name desc;";

				rs = connection.prepareStatement(sql).executeQuery();
				while (rs.next()) {
					String name = rs.getString(1);
					String photo = rs.getString(2);
					String info = rs.getString(3);
					String gender = rs.getString(4);
					int age = rs.getInt(5);
					String state = rs.getString(6);

					if (age > Helper.ChildAge) {
						Adult a = new Adult(name, age, gender, info, state);
						_network.add(a);

					} else if (age <= Helper.ChildAge & age > Helper.YoungChildAge) {
						Child c = new Child(name, age, gender, info, state);

						_network.add(c);

					} else if (age <= Helper.YoungChildAge) {
						YoungChild y = new YoungChild(name, age, gender, info, state);

						_network.add(y);

					} else if (age < 0) {
						throw new NoSuchAgeException("You cannot enter an age below zero or above 150.");
					} else if (age > 150) {
						throw new NoSuchAgeException("You cannot enter an age below zero or above 150.");
					}

					if (Helper.doDatabase) {
						System.out.println("Re-creating Human(s) in Driver: " + name);

					}
				}

				sql = "select name1, name2, conn from relations order by name1 desc;";

				rs = connection.prepareStatement(sql).executeQuery();
				while (rs.next()) {
					String person1 = rs.getString(1);
					String person2 = rs.getString(2);
					int relationship = rs.getInt(3);

					if (findPerson(person1) & findPerson(person2) & relationship >= 0) {
						Person p1 = _network.get(getIndexByProperty(person1));
						Person p2 = _network.get(getIndexByProperty(person2));

						// connectPerson(p1, p2, relationship);
						_relationship.add(new Relationship(p1, relationship, p2));

						if (Helper.doDatabase) {
							System.out.println("Forging Relationships(s) in Driver: " + person1 + " <<"
									+ Helper.roleDesc[relationship] + ">> " + person2);

						}
					}

				}
				connection.commit();

				connection.close();
				hsqlServer.stop();
			} catch (ClassNotFoundException e) {

			} catch (SQLException e) {

			}
		}
	}

	public void loadDatabasePerson(String name, String photo, String info, String gender, int age, String state) {

		if (Helper.doDatabase) {
			try {

				Server hsqlServer = null;
				Connection connection = null;
				ResultSet rs = null;
				hsqlServer = new Server();
				hsqlServer.setLogWriter(null);
				hsqlServer.setSilent(true);
				hsqlServer.setDatabaseName(0, Helper.databaseName);
				hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
				hsqlServer.start();
				Class.forName(Helper.jdbc);
				connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

				String sql = "insert into person (name, photo, info, gender, age, state)" + "values ('" + name + "','"
						+ photo + "','" + info + "','" + gender + "'," + age + ",'" + state + "');";
				try {
					connection.prepareStatement(sql).execute();
					connection.commit();
				} catch (Exception e) {

				}

				connection.close();
				hsqlServer.stop();

			} catch (ClassNotFoundException e) {

			} catch (SQLException e) {

			}
		}
	}

	public void loadDatabaseRelations(String name1, String name2, int conn) {
		if (Helper.doDatabase) {
			try {
				Server hsqlServer = null;
				Connection connection = null;
				ResultSet rs = null;
				hsqlServer = new Server();
				hsqlServer.setLogWriter(null);
				hsqlServer.setSilent(true);
				hsqlServer.setDatabaseName(0, Helper.databaseName);
				hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
				hsqlServer.start();
				Class.forName(Helper.jdbc);
				connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

				String sql = "insert into relations (name1, name2, conn) " + "values ('" + name1 + "','" + name2 + "',"
						+ conn + ");";

				try {
					connection.prepareStatement(sql).execute();
					connection.commit();
				} catch (Exception e) {

				}

				connection.close();
				hsqlServer.stop();

			} catch (ClassNotFoundException e) {

			} catch (SQLException e) {

			}
		}
	}

	public Boolean DatabasePopulated() {

		Boolean tablesPopulated = false;

		if (Helper.doDatabase) {

			try {
				Server hsqlServer = null;
				Connection connection = null;
				ResultSet rs = null;
				hsqlServer = new Server();
				hsqlServer.setLogWriter(null);
				hsqlServer.setSilent(true);
				hsqlServer.setDatabaseName(0, Helper.databaseName);
				hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
				hsqlServer.start();
				Class.forName(Helper.jdbc);
				connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

				int countPeople = 0;
				int countRelations = 0;

				String sql = "select count(*) from person;";
				rs = connection.prepareStatement(sql).executeQuery();
				rs.next();
				countPeople = rs.getInt(1);
				System.out.println("No. of Person(s) in DB: " + countPeople);

				if (countPeople >= 1)
					tablesPopulated = true;

				sql = "select count(*) from relations;";
				rs = connection.prepareStatement(sql).executeQuery();
				rs.next();
				countRelations = rs.getInt(1);
				System.out.println("No. of Relationship(s) in DB: " + countRelations);
				if (countRelations >= 1)
					tablesPopulated = true;

				connection.close();
				hsqlServer.stop();
			} catch (ClassNotFoundException e) {

			} catch (SQLException e) {

			}
		}
		return tablesPopulated;
	}

	public String viewDatabase(String name) {

		String output = "";
		String sql = "";
		Boolean found = false;

		try {
			Server hsqlServer = null;
			Connection connection = null;
			ResultSet rs = null;
			hsqlServer = new Server();
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);
			hsqlServer.setDatabaseName(0, Helper.databaseName);
			hsqlServer.setDatabasePath(0, Helper.path + Helper.DatabaseFileName);
			hsqlServer.start();
			Class.forName(Helper.jdbc);
			connection = DriverManager.getConnection(Helper.jdbcConn, Helper.dbUser, Helper.dbUserPwd);

			sql = "select name " + "from person where lower(name) like lower('%" + name + "%') order by name desc;";

			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()) {

				output = output + rs.getString(1);
				found = true;
			}
			connection.commit();
			connection.close();
			hsqlServer.stop();
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}

		if (!found)
			output = "";
		return output;
	}

	public void writeDatabase() {

	}

	public Boolean findPerson(String name) {
		return ((getIndexByProperty(name) >= 0) ? true : false);
	}

	public String findPerson(TextField t[]) {
		String output = "";

		String name = t[0].getText().toString();
		if (findPerson(name)) {
			output = output + "[" + _network.get(getIndexByProperty(name)).getName() + "] found in network.\n";
			output = output + "[" + findPerson(name, Helper.findInDB) + "] found in DB.";
		} else
			output = "[" + name + "] not found.";

		return output;
	}

	public String findPerson(String name, int sourceType) {
		String output = "";

		if (sourceType == Helper.findInDB) {
			output = output + viewDatabase(name);
		}

		return output;
	}

	public void addPerson(String name, int age, String gender, String info, String state) throws NoSuchAgeException {
		Boolean proceed = false;

		if (findPerson(name)) {
		} else {
			if (age > Helper.ChildAge) {
				Adult a = new Adult(name, age, gender, info, state);
				_network.add(a);
				proceed = true;

			} else if (age <= Helper.ChildAge & age > Helper.YoungChildAge) {
				Child c = new Child(name, age, gender, info, state);

				_network.add(c);
				proceed = true;

			} else if (age <= Helper.YoungChildAge) {
				YoungChild y = new YoungChild(name, age, gender, info, state);

				_network.add(y);
				proceed = true;

			} else if (age < 0) {
				throw new NoSuchAgeException("You cannot enter an age below zero or above 150.");
			} else if (age > 150) {
				throw new NoSuchAgeException("You cannot enter an age below zero or above 150.");
			}
		}
		if (proceed & Helper.doDatabase)
			loadDatabasePerson(name, "", info, gender, age, state);

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
					try {
						addPerson(name, age, gender, info, state);
					} catch (NoSuchAgeException e) {
					}
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
		updateDB(name, age, info, state, gender);

	}

	public void updateDB(String name, int age, String info, String state, String gender) {
		// 999
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

	public Boolean isConnected(Person p, Person q, int conn) {
		Boolean connected = false;

		Relationship r = new Relationship(p, conn, q);

		for (int i = 0; i < _relationship.size(); i++) {
			if (r.getPersonA().getName().equals(_relationship.get(i).getPersonA().getName())
					& r.getConn() == _relationship.get(i).getConn()
					& r.getPersonB().getName().equals(_relationship.get(i).getPersonB().getName())) {

				connected = true;
			}
		}

		// test connect relationship already exists (other way)
		for (int i = 0; i < _relationship.size(); i++) {
			if (r.getPersonA().getName().equals(_relationship.get(i).getPersonB().getName())
					& r.getConn() == _relationship.get(i).getConn()
					& r.getPersonB().getName().equals(_relationship.get(i).getPersonA().getName())) {

				connected = true;
			}
		}
		return connected;
	}

	public String connectPerson(Person p, Person q, int conn) {
		String output = "";

		if (!isConnected(p, q, conn)) {
			_relationship.add(new Relationship(p, conn, p));
			if (Helper.doDatabase)
				loadDatabaseRelations(p.getName(), q.getName(), conn);
		}

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
					throw new NotToBeCoupledException(output);
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
					throw new NotToBeFriendsException(output);
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
					throw new NotToBeFriendsException(output);
				}
			} catch (Exception e) {
			}

			// test child/adult connect as classmates
			if (conn == Helper.classmate
					& ((p instanceof Adult & q instanceof Child) | (p instanceof Child & q instanceof Adult))) {
				output = output + "Adults cannot be classmates with Children.\n";
				proceed = false;
			}
			// NotToBeClassmates
			try {
				if (!proceed) {
					throw new NotToBeClassmates(output);
				}
			} catch (Exception e) {
			}

			// test child as colleagues
			if (conn == Helper.colleague & ((p instanceof Child | q instanceof Child))) {
				output = output + "Children can not have colleagues.\n";
				proceed = false;
			}
			// NotToBeColleagues
			try {
				if (!proceed) {
					throw new NotToBeColleagues(output);
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

			if (isConnected(p, q, conn)) {
				output = output + "Connection already exists:\n";
				output = output + p.getName() + " < " + Helper.roleDesc[conn] + "> " + q.getName() + "\n";
				proceed = false;
			}

			if (proceed) {

				// test connect friends (connections already exists) 999
				Relationship r = new Relationship(p, conn, q);
				_relationship.add(r);

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
