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
Date Created: 18 March 2018 
Description: Driver class (formerly Network class)
Notes: Constructor will load initial people and relationships. The menuAction method acts upon user menu selection.
 */

public class Driver {

	public Driver() {
	}

	public Driver(ArrayList<Person> network, ArrayList<Relationship> connection) {

		// start: initial set up of network for demo
		// otherwise constructor is normally empty
		loadData(network, connection);
		// creating people
		// end: initial set up of network
	}

	public void loadData(ArrayList<Person> network, ArrayList<Relationship> connection) {

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

				if (age > Helper.minorAge) {
					Adult a = new Adult(name, age, gender, info);
					network.add(a);
				} else if (age <= Helper.minorAge & age > Helper.babyAge) {
					Child c = new Child(name, age, gender, info);
					network.add(c);
				} else if (age >= Helper.babyAge) {
					YoungChild y = new YoungChild(name, age, gender);
					network.add(y);
				}
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

				if (findPerson(network, person1) & findPerson(network, person2)) {
					Person p1 = network.get(getIndexByProperty(network, person1));
					Person p2 = network.get(getIndexByProperty(network, person2));
					connection.add(new Relationship(p1, relationship, p2));
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

	public void menuAction(int menuItem, ArrayList<Person> network, ArrayList<Relationship> connection) {

		if (menuItem == Helper.addPerson)
			addPerson(network);

		else if (menuItem == Helper.findPerson)
			findPerson(network);

		else if (menuItem == Helper.displayProfile) {
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				displayProfile(p, connection);
			} else
				System.out.println("[" + name + "] not found");
		}

		else if (menuItem == Helper.displayAllProfile) {
			// go through the whole network and show their whole profiles
			// reuse already defined displayProfile()
			for (int i = 0; i < network.size(); i++) {
				displayProfile(network.get(i), connection);
				Helper.drawLine();
			}

		} else if (menuItem == Helper.updateProfile) {
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				updateProfile(p, network, connection);
			} else
				System.out.println("[" + name + "] not found");

		} else if (menuItem == Helper.deletePerson) {
			// delete person and assume ok to delete their relationships if any
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				deletePerson(p, network, connection);
			} else
				System.out.println("[" + name + "] not found");

		} else if (menuItem == Helper.connectPerson) {
			String input = Helper.getStringInput("Enter Name of First Person (Friend or Parent): ");
			if (findPerson(network, input)) {
				Person p = network.get(getIndexByProperty(network, input));
				input = Helper.getStringInput("Enter Name of Second Person (Friend or Child: ");
				if (findPerson(network, input)) {
					Person q = network.get(getIndexByProperty(network, input));
					int conn = -1;
					// loop till correct role is selected
					do {
						System.out.println(Helper.friend + ". " + Helper.roleDesc[Helper.friend]);
						System.out.println(Helper.spouse + ". " + Helper.roleDesc[Helper.spouse]);
						System.out.println(Helper.father + ". " + Helper.roleDesc[Helper.father]);
						System.out.println(Helper.mother + ". " + Helper.roleDesc[Helper.mother]);
						conn = Helper.getIntegerInput("Choose Relationship: ");
					} while (conn > 3 | conn < 0);

					connectPerson(p, q, conn, connection);
				} else
					System.out.println("[" + input + "] not found");
			} else
				System.out.println("[" + input + "] not found");
		}

		else if (menuItem == Helper.findFriends) {
			// find out where Person A is friends with Person B
			// possible to extend to check all relationships instead in future
			String input = Helper.getStringInput("Enter Name of First Person (Friend): ");
			if (findPerson(network, input)) {
				Person p = network.get(getIndexByProperty(network, input));
				input = Helper.getStringInput("Enter Name of Second Person (Friend) ");
				if (findPerson(network, input)) {
					Person q = network.get(getIndexByProperty(network, input));
					if (findFriends(p, q, connection))
						System.out.println(p.getName() + " is a friend of " + q.getName());
					else
						System.out.println(p.getName() + " is NOT a friend of " + q.getName());
				} else
					System.out.println("[" + input + "] not found");
			} else
				System.out.println("[" + input + "] not found");
		}

		else if (menuItem == Helper.findFamily) {
			// list down spouse and children
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				findFamily(p, connection);
			} else
				System.out.println("[" + name + "] not found");
		}
	}

	public void updateProfile(Person p, ArrayList<Person> nt, ArrayList<Relationship> connection) {
		Boolean proceed = true;
		if (p instanceof Adult) {
			Adult a = (Adult) p;

			String newInfo = Helper.getStringInput("Enter New Info: ");
			a.setInfo(newInfo);

			int newAge = Helper.getIntegerInput("Enter New Age: ");
			if (newAge <= Helper.minorAge) {
				// test adult with spouse change age
				if (findSpouse(p, connection, Helper.suppressDetails)) {
					System.out.println("You cannot change your age to " + newAge + " as you have a spouse.");
					proceed = false;
				}
				// test adult with spouse change age
				if (findChildren(p, connection, Helper.suppressDetails)) {
					System.out.println("You cannot change your age to " + newAge + " as you have children.");
					proceed = false;
				}
			}

			// need to upgrade/downgrade person: not_done_yet

			if (proceed)
				a.setAge(newAge);

		} else if (p instanceof Child) {
			Child c = (Child) p;
			int newAge = Helper.getIntegerInput("Enter New Age: ");

			if (newAge <= Helper.babyAge) {
				// test child change age to baby
				if (findFriends(p, connection, Helper.suppressDetails)) {
					System.out.println("You cannot change your age to " + newAge
							+ " as you have friends; children below " + Helper.babyAge + " cannot have friends.");
					proceed = false;
				}
			} else if (newAge > Helper.minorAge) {
				// test child with parents change age
				if (findParents(p, connection, Helper.suppressDetails)) {
					System.out.println(
							"You cannot change your age to above " + Helper.minorAge + " as you have linked parents.");
					proceed = false;
				}

				// test child change age who has friends within age-gap
				for (int i = 0; i < connection.size(); i++) {

					if ((connection.get(i).getPersonA().getName().equals(p.getName())
							& connection.get(i).getConn() == Helper.friend
							& Math.abs((connection.get(i).getPersonB().getAge() - newAge)) > Helper.ageGap)
							| (connection.get(i).getPersonB().getName().equals(p.getName())
									& connection.get(i).getConn() == Helper.friend
									& Math.abs((connection.get(i).getPersonA().getAge() - newAge)) > Helper.ageGap)) {
						System.out.println("You cannot change your age to " + newAge
								+ " as you have friends who are within the " + Helper.ageGap + "-year age gap.");
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

		findFriends(p, connection, Helper.suppressDetails);

	}

	public void deletePerson(Person p, ArrayList<Person> nt, ArrayList<Relationship> connection) {

		for (int i = connection.size() - 1; i >= 0; i--) {
			if (connection.get(i).getPersonA().getName().equals(p.getName()))
				connection.remove(i);
		}

		for (int i = connection.size() - 1; i >= 0; i--) {
			if (connection.get(i).getPersonB().getName().equals(p.getName()))
				connection.remove(i);
		}

		nt.remove(p);

	}

	public void connectPerson(Person p, Person q, int conn, ArrayList<Relationship> connection) {
		Boolean proceed = true;

		// test child connect friend (child inside family)
		if (p instanceof Child & q instanceof Child) {
			for (int i = 0; i < connection.size(); i++) {
				// find p's father
				if (connection.get(i).getPersonB().getName().equals(p.getName())
						& connection.get(i).getConn() == Helper.father) {
					for (int j = 0; j < connection.size(); j++) {
						// check if q's father is same as p's
						if (connection.get(j).getPersonB().getName().equals(q.getName())
								& connection.get(j).getConn() == Helper.father & connection.get(j).getPersonA()
										.getName().equals(connection.get(i).getPersonA().getName())) {
							System.out.println(
									p.getName() + " and " + q.getName() + " are siblings; they cannot be friends");
							proceed = false;
							break;
						}
					}
				}
				// find p's mother
				if (connection.get(i).getPersonB().getName().equals(p.getName())
						& connection.get(i).getConn() == Helper.mother) {
					for (int j = 0; j < connection.size(); j++) {
						// check if q's father is same as p's
						if (connection.get(j).getPersonB().getName().equals(q.getName())
								& connection.get(j).getConn() == Helper.mother & connection.get(j).getPersonA()
										.getName().equals(connection.get(i).getPersonA().getName())) {
							System.out.println(
									p.getName() + " and " + q.getName() + " are siblings; they cannot be friends");
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
				System.out.println("Age gap between children wishing to connect as friends is " + Helper.ageGap);
				proceed = false;
			}
		}

		// test child connect adult as parent, should be other way
		if ((conn == Helper.father || conn == Helper.mother) & (p instanceof Child & q instanceof Adult)) {
			System.out.println("To connect as parents, the Adult's name should be entered first.");
			proceed = false;
		}

		// test adult connect child (correct gender)
		if ((conn == Helper.father & !p.getGender().equals("M"))
				| (conn == Helper.mother & !p.getGender().equals("F"))) {
			System.out.println("To connect as parents, the Adult's gender must be correct.");
			proceed = false;
		}

		// test adult connect child (father already exists)
		if ((conn == Helper.father && findFather(q, connection)) & (p instanceof Adult & q instanceof Child)) {
			System.out.println("Father already exists.");
			proceed = false;
		}

		// test adult connect child (mother already exists)
		if ((conn == Helper.mother && findMother(q, connection)) & (p instanceof Adult & q instanceof Child)) {
			System.out.println("Mother already exists.");
			proceed = false;
		}

		// test child connect friend (adult)
		if (conn == Helper.friend
				& ((p instanceof Adult & q instanceof Child) | (p instanceof Child & q instanceof Adult))) {
			System.out.println("Adults cannot have be friends with Children.");
			proceed = false;

		}
		// test child below 2 connect friend
		if ((p instanceof Child | q instanceof Child) & conn == Helper.friend) {
			if (p.getAge() <= Helper.babyAge | q.getAge() <= Helper.babyAge) {
				System.out.println("Children below " + Helper.babyAge + " cannot have friends.");
				proceed = false;
			}
		}

		// test connect spouse (1 not adult)
		if ((p instanceof Child | q instanceof Child) & conn == Helper.spouse) {
			System.out.println("Children cannot have spouses.");
			proceed = false;
		}

		// test connect spouse (spouse already exists)
		if ((p instanceof Adult & q instanceof Adult) & conn == Helper.spouse) {
			if (findSpouse(p, connection, Helper.suppressDetails) | findSpouse(q, connection, Helper.suppressDetails)) {
				System.out.println("One or both adults already have spouse(s).");
				proceed = false;
			}
		}

		// test connect spouse (no spouse yet, both adults)
		if ((p instanceof Adult & q instanceof Adult) & conn == Helper.spouse) {
			if (!findSpouse(p, connection, Helper.suppressDetails)
					& !findSpouse(q, connection, Helper.suppressDetails)) {
				System.out.println("Just Married!");
			}
		}

		// test connect friends (connections already exists)
		Relationship r = new Relationship(p, conn, q);

		for (int i = 0; i < connection.size(); i++) {
			if (r.getPersonA().getName().equals(connection.get(i).getPersonA().getName())
					& r.getConn() == connection.get(i).getConn()
					& r.getPersonB().getName().equals(connection.get(i).getPersonB().getName())) {
				System.out.println("Connection already exists.");
				proceed = false;
			}
		}

		// test connect relationship already exists (other way)
		for (int i = 0; i < connection.size(); i++) {
			if (r.getPersonA().getName().equals(connection.get(i).getPersonB().getName())
					& r.getConn() == connection.get(i).getConn()
					& r.getPersonB().getName().equals(connection.get(i).getPersonA().getName())) {
				System.out.println("Connection already exists.");
				proceed = false;
			}
		}
		if (proceed)
			connection.add(new Relationship(p, conn, q));
	}

	public Boolean findFriends(Person p, Person q, ArrayList<Relationship> connection) {
		Boolean found = false;

		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonA().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.friend
					& connection.get(i).getPersonB().getName().equals(q.getName()))
				found = true;

			if (connection.get(i).getPersonA().getName().equals(q.getName())
					& connection.get(i).getConn() == Helper.friend
					& connection.get(i).getPersonB().getName().equals(p.getName()))
				found = true;
		}
		return found;
	}

	public void findFamily(Person p, ArrayList<Relationship> connection) {
		if (p instanceof Adult) {
			Adult a = (Adult) p;
			findSpouse(p, connection, Helper.showDetails);
			findChildren(p, connection, Helper.showDetails);

		}
		if (p instanceof Child) {
			Child c = (Child) p;
			findParents(c, connection, Helper.showDetails);
			findSiblings(p, connection, Helper.showDetails);
		}
	}

	public Boolean findSiblings(Person p, ArrayList<Relationship> connection, Boolean print) {
		Boolean found = false;
		ArrayList<Person> siblings = new ArrayList<>();

		for (int i = 0; i < connection.size(); i++) {
			// find parents of child first
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& (connection.get(i).getConn() == Helper.father | connection.get(i).getConn() == Helper.mother)) {

				for (int j = 0; j < connection.size(); j++) {
					// match Person A and then get Person B (sibling) if it's not the same as
					// child's
					if (connection.get(j).getPersonA().getName().equals(connection.get(i).getPersonA().getName())
							& (connection.get(j).getConn() == Helper.father
									| connection.get(j).getConn() == Helper.mother)
							& !connection.get(j).getPersonB().getName().equals(p.getName())) {
						// if not already in, add into arraylist
						if (!siblings.contains(connection.get(j).getPersonB()))
							siblings.add(connection.get(j).getPersonB());
					}
				}
			}
		}
		int count = 0;
		for (int i = 0; i < siblings.size(); i++) {
			count++;
			found = true;
			if (print)
				System.out.println(((count == 1) ? "Siblings :\n-" : "-") + siblings.get(i).getName());
		}
		return found;
	}

	public void displayProfile(Person p, ArrayList<Relationship> connection) {
		System.out.println("Name: " + p.getName() + ", (" + p.getGender() + "), " + p.getAge());
		if (p instanceof Adult) {
			Adult a = (Adult) p;
			if (a.getInfo() != null)
				System.out.println("About: " + a.getInfo());

			findSpouse(p, connection, Helper.showDetails);
			findChildren(p, connection, Helper.showDetails);
			findFriends(p, connection, Helper.showDetails);
		}
		if (p instanceof Child) {
			Child c = (Child) p;
			findParents(c, connection, Helper.showDetails);
			findFriends(c, connection, Helper.showDetails);
		}
	}

	public Boolean findParents(Person p, ArrayList<Relationship> connection, Boolean print) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.father) {
				found = true;
				if (print)
					System.out.println("Father: " + connection.get(i).getPersonA().getName());
			}

			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.mother) {
				found = true;
				if (print)
					System.out.println("Mother: " + connection.get(i).getPersonA().getName());
			}
		}
		return found;
	}

	public Boolean findFather(Person p, ArrayList<Relationship> connection) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.father) {
				found = true;
			}
		}
		return found;
	}

	public Boolean findMother(Person p, ArrayList<Relationship> connection) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.mother) {
				found = true;
			}
		}
		return found;
	}

	public Boolean findFriends(Person p, ArrayList<Relationship> connection, Boolean print) {
		int count = 0;
		Boolean found = false;

		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonA().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.friend) {
				count++;
				found = true;
				if (print)
					System.out
							.println(((count == 1) ? "Friends :\n-" : "-") + connection.get(i).getPersonB().getName());
			}
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.friend) {
				count++;
				found = true;
				if (print)
					System.out
							.println(((count == 1) ? "Friends :\n-" : "-") + connection.get(i).getPersonA().getName());
			}
		}
		return found;
	}

	public Boolean findChildren(Person p, ArrayList<Relationship> connection, Boolean print) {
		int count = 0;
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonA().getName().equals(p.getName())
					& connection.get(i).getConn() == ((p.getGender() == "M") ? Helper.father : Helper.mother)) {
				count++;
				found = true;
				if (print)
					System.out
							.println(((count == 1) ? "Children :\n-" : "-") + connection.get(i).getPersonB().getName());
			}
		}
		return found;
	}

	public Boolean findSpouse(Person p, ArrayList<Relationship> connection, Boolean print) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonA().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.spouse) {
				found = true;
				if (print)
					System.out.println("Spouse: " + connection.get(i).getPersonB().getName());

			} else if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == Helper.spouse) {
				found = true;
				if (print)
					System.out.println("Spouse: " + connection.get(i).getPersonA().getName());

			}
		}
		return found;
	}

	public void addPerson(ArrayList<Person> nt) {

		String name = Helper.getStringInput("Enter Name: ");
		if (findPerson(nt, name))
			System.out.println("[" + nt.get(getIndexByProperty(nt, name)).getName() + "] already exists.");
		else {
			int age = Helper.getIntegerInput("Enter Age: ");

			String gender = Helper.getStringInput("Enter Gender (M/F): ");
			// need to validate M/F : not_done_yet
			if (age > 16) {
				String info = Helper.getStringInput("Enter Info: ");
				Adult na = new Adult(name, age, gender, info);
				nt.add(na);
			} else {
				Child nc = new Child(name, age, gender);
				nt.add(nc);
			}
			System.out.println("[" + name + "] added to MiniNet.");
		}
	}

	public Boolean findPerson(ArrayList<Person> nt, String name) {
		if (getIndexByProperty(nt, name) >= 0)
			return true;
		else
			return false;
	}

	public void findPerson(ArrayList<Person> nt) {

		String name = Helper.getStringInput("Enter Person's Name: ");
		if (findPerson(nt, name))
			System.out.println("[" + nt.get(getIndexByProperty(nt, name)).getName() + "] found");
		else
			System.out.println("[" + name + "] not found");

	}

	public String findPerson(ArrayList<Person> nt, TextField t[]) {
		String output = "";

		String name = t[0].getText().toString();
		if (findPerson(nt, name)) {
			output = "[" + nt.get(getIndexByProperty(nt, name)).getName() + "] found.";
		} else
			output = "[" + name + "] not found.";

		return output;
	}

	public int getIndexByProperty(ArrayList<Person> nt, String name) {
		int result = -1;
		for (int i = 0; i < nt.size(); i++) {
			if (nt.get(i).getName().toUpperCase().equals(name.toUpperCase())) {
				result = i;
				break;
			}
		}
		return result;
	}

}
