package AP_Assignment2;

import java.util.ArrayList;

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

		// creating people
		Adult rudi = new Adult("Rudi Basiran", 48, "M", "Systems Analyst");
		Adult ahysa = new Adult("Ahysa Ahmad", 45, "F");
		Child rida = new Child("Rida Aqyda", 7, "F");

		Adult arthur = new Adult("Arthur Simanjuntak", 45, "M", "Wave Engineer");
		Adult sheree = new Adult("Sheree Reaver", 45, "F", "Civil Engineer");
		Child rangi = new Child("Rangi Simanjuntak", 8, "M");
		Child tane = new Child("Tane Simanjuntak", 6, "M");

		Adult andrew = new Adult("Andrew James", 45, "M", "HR Officer");
		Adult nat = new Adult("Nat James", 45, "F");
		Child callum = new Child("Callum James", 8, "M");

		Adult rasyid = new Adult("Rasyid", 42, "M");
		Adult amalina = new Adult("Amalina", 42, "F");
		Child sabrina = new Child("Sabrina", 15, "F");
		Child riyan = new Child("Riyan", 13, "M");

		Adult sherri = new Adult("Sherri McRae", 43, "F", "Student");
		Adult huani = new Adult("Huani Neupane", 28, "F", "Student");
		Adult chloe = new Adult("Chloe Loh", 25, "F", "Student");
		Adult wanyi = new Adult("Wanyi Beh", 32, "F", "Student");

		// adding people to the network
		network.add(rudi);
		network.add(ahysa);
		network.add(rida);
		network.add(arthur);
		network.add(sheree);
		network.add(rangi);
		network.add(tane);

		network.add(andrew);
		network.add(nat);
		network.add(callum);

		network.add(rasyid);
		network.add(amalina);
		network.add(sabrina);
		network.add(riyan);

		network.add(sherri);
		network.add(huani);
		network.add(chloe);
		network.add(wanyi);

		// adding connections
		connection.add(new Relationship(rudi, GlobalClass.spouse, ahysa));
		connection.add(new Relationship(rudi, GlobalClass.father, rida));
		connection.add(new Relationship(ahysa, GlobalClass.mother, rida));

		connection.add(new Relationship(arthur, GlobalClass.spouse, sheree));
		connection.add(new Relationship(arthur, GlobalClass.father, rangi));
		connection.add(new Relationship(sheree, GlobalClass.mother, rangi));

		connection.add(new Relationship(arthur, GlobalClass.father, tane));
		connection.add(new Relationship(sheree, GlobalClass.mother, tane));

		connection.add(new Relationship(andrew, GlobalClass.father, callum));

		connection.add(new Relationship(rasyid, GlobalClass.spouse, amalina));
		connection.add(new Relationship(rasyid, GlobalClass.father, sabrina));
		connection.add(new Relationship(amalina, GlobalClass.mother, sabrina));
		connection.add(new Relationship(rasyid, GlobalClass.father, riyan));
		connection.add(new Relationship(amalina, GlobalClass.mother, riyan));

		connection.add(new Relationship(rudi, GlobalClass.friend, sherri));
		connection.add(new Relationship(rudi, GlobalClass.friend, huani));
		connection.add(new Relationship(rudi, GlobalClass.friend, chloe));

		connection.add(new Relationship(rida, GlobalClass.friend, rangi));

		// end: initial set up of network
	}

	public void menuAction(int menuItem, ArrayList<Person> network, ArrayList<Relationship> connection) {

		if (menuItem == GlobalClass.addPerson)
			addPerson(network);

		else if (menuItem == GlobalClass.findPerson)
			findPerson(network);

		else if (menuItem == GlobalClass.displayProfile) {
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				displayProfile(p, connection);
			} else
				System.out.println("[" + name + "] not found");
		}

		else if (menuItem == GlobalClass.displayAllProfile) {
			// go through the whole network and show their whole profiles
			// reuse already defined displayProfile()
			for (int i = 0; i < network.size(); i++) {
				displayProfile(network.get(i), connection);
				Helper.drawLine();
			}

		} else if (menuItem == GlobalClass.updateProfile) {
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				updateProfile(p, connection);
			} else
				System.out.println("[" + name + "] not found");

		} else if (menuItem == GlobalClass.deletePerson) {
			// delete person and assume ok to delete their relationships if any
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				deletePerson(p, network, connection);
			} else
				System.out.println("[" + name + "] not found");

		} else if (menuItem == GlobalClass.connectPerson) {
			String input = Helper.getStringInput("Enter Name of First Person (Friend or Parent): ");
			if (findPerson(network, input)) {
				Person p = network.get(getIndexByProperty(network, input));
				input = Helper.getStringInput("Enter Name of Second Person (Friend or Child: ");
				if (findPerson(network, input)) {
					Person q = network.get(getIndexByProperty(network, input));
					int conn = -1;
					// loop till correct role is selected
					do {
						System.out.println(GlobalClass.friend + ". " + GlobalClass.roleDesc[GlobalClass.friend]);
						System.out.println(GlobalClass.spouse + ". " + GlobalClass.roleDesc[GlobalClass.spouse]);
						System.out.println(GlobalClass.father + ". " + GlobalClass.roleDesc[GlobalClass.father]);
						System.out.println(GlobalClass.mother + ". " + GlobalClass.roleDesc[GlobalClass.mother]);
						conn = Helper.getIntegerInput("Choose Relationship: ");
					} while (conn > 3 | conn < 0);

					connectPerson(p, q, conn, connection);
				} else
					System.out.println("[" + input + "] not found");
			} else
				System.out.println("[" + input + "] not found");
		}

		else if (menuItem == GlobalClass.findFriends) {
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

		else if (menuItem == GlobalClass.findFamily) {
			// list down spouse and children
			String name = Helper.getStringInput("Enter Name: ");
			if (findPerson(network, name)) {
				Person p = network.get(getIndexByProperty(network, name));
				findFamily(p, connection);
			} else
				System.out.println("[" + name + "] not found");
		}
	}

	public void updateProfile(Person p, ArrayList<Relationship> connection) {
		Boolean proceed = true;
		if (p instanceof Adult) {
			Adult a = (Adult) p;

			String newInfo = Helper.getStringInput("Enter New Info: ");
			a.setInfo(newInfo);

			int newAge = Helper.getIntegerInput("Enter New Age: ");
			if (newAge <= GlobalClass.minorAge) {
				// test adult with spouse change age
				if (findSpouse(p, connection, GlobalClass.suppressDetails)) {
					System.out.println("You cannot change your age to " + newAge + " as you have a spouse.");
					proceed = false;
				}
				// test adult with spouse change age
				if (findChildren(p, connection, GlobalClass.suppressDetails)) {
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

			if (newAge <= GlobalClass.babyAge) {
				// test child change age to baby
				if (findFriends(p, connection, GlobalClass.suppressDetails)) {
					System.out.println("You cannot change your age to " + newAge
							+ " as you have friends; children below " + GlobalClass.babyAge + " cannot have friends.");
					proceed = false;
				}
			} else if (newAge > GlobalClass.minorAge) {
				// test child with parents change age
				if (findParents(p, connection, GlobalClass.suppressDetails)) {
					System.out.println("You cannot change your age to above " + GlobalClass.minorAge
							+ " as you have linked parents.");
					proceed = false;
				}

				// test child change age who has friends within age-gap
				for (int i = 0; i < connection.size(); i++) {

					if ((connection.get(i).getPersonA().getName().equals(p.getName())
							& connection.get(i).getConn() == GlobalClass.friend
							& Math.abs((connection.get(i).getPersonB().getAge() - newAge)) > GlobalClass.ageGap)
							| (connection.get(i).getPersonB().getName().equals(p.getName())
									& connection.get(i).getConn() == GlobalClass.friend & Math.abs(
											(connection.get(i).getPersonA().getAge() - newAge)) > GlobalClass.ageGap)) {
						System.out.println("You cannot change your age to " + newAge
								+ " as you have friends who are within the " + GlobalClass.ageGap + "-year age gap.");
						proceed = false;
					}
				}
			}
			if (proceed)
				c.setAge(newAge);
		}

		findFriends(p, connection, GlobalClass.suppressDetails);

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
						& connection.get(i).getConn() == GlobalClass.father) {
					for (int j = 0; j < connection.size(); j++) {
						// check if q's father is same as p's
						if (connection.get(j).getPersonB().getName().equals(q.getName())
								& connection.get(j).getConn() == GlobalClass.father & connection.get(j).getPersonA()
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
						& connection.get(i).getConn() == GlobalClass.mother) {
					for (int j = 0; j < connection.size(); j++) {
						// check if q's father is same as p's
						if (connection.get(j).getPersonB().getName().equals(q.getName())
								& connection.get(j).getConn() == GlobalClass.mother & connection.get(j).getPersonA()
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
			if (Math.abs(p.getAge() - q.getAge()) > GlobalClass.ageGap) {
				System.out.println("Age gap between children wishing to connect as friends is " + GlobalClass.ageGap);
				proceed = false;
			}
		}

		// test child connect adult as parent, should be other way
		if ((conn == GlobalClass.father || conn == GlobalClass.mother) & (p instanceof Child & q instanceof Adult)) {
			System.out.println("To connect as parents, the Adult's name should be entered first.");
			proceed = false;
		}

		// test adult connect child (correct gender)
		if ((conn == GlobalClass.father & !p.getGender().equals("M"))
				| (conn == GlobalClass.mother & !p.getGender().equals("F"))) {
			System.out.println("To connect as parents, the Adult's gender must be correct.");
			proceed = false;
		}

		// test adult connect child (father already exists)
		if ((conn == GlobalClass.father && findFather(q, connection)) & (p instanceof Adult & q instanceof Child)) {
			System.out.println("Father already exists.");
			proceed = false;
		}

		// test adult connect child (mother already exists)
		if ((conn == GlobalClass.mother && findMother(q, connection)) & (p instanceof Adult & q instanceof Child)) {
			System.out.println("Mother already exists.");
			proceed = false;
		}

		// test child connect friend (adult)
		if (conn == GlobalClass.friend
				& ((p instanceof Adult & q instanceof Child) | (p instanceof Child & q instanceof Adult))) {
			System.out.println("Adults cannot have be friends with Children.");
			proceed = false;

		}
		// test child below 2 connect friend
		if ((p instanceof Child | q instanceof Child) & conn == GlobalClass.friend) {
			if (p.getAge() <= GlobalClass.babyAge | q.getAge() <= GlobalClass.babyAge) {
				System.out.println("Children below " + GlobalClass.babyAge + " cannot have friends.");
				proceed = false;
			}
		}

		// test connect spouse (1 not adult)
		if ((p instanceof Child | q instanceof Child) & conn == GlobalClass.spouse) {
			System.out.println("Children cannot have spouses.");
			proceed = false;
		}

		// test connect spouse (spouse already exists)
		if ((p instanceof Adult & q instanceof Adult) & conn == GlobalClass.spouse) {
			if (findSpouse(p, connection, GlobalClass.suppressDetails)
					| findSpouse(q, connection, GlobalClass.suppressDetails)) {
				System.out.println("One or both adults already have spouse(s).");
				proceed = false;
			}
		}

		// test connect spouse (no spouse yet, both adults)
		if ((p instanceof Adult & q instanceof Adult) & conn == GlobalClass.spouse) {
			if (!findSpouse(p, connection, GlobalClass.suppressDetails)
					& !findSpouse(q, connection, GlobalClass.suppressDetails)) {
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
					& connection.get(i).getConn() == GlobalClass.friend
					& connection.get(i).getPersonB().getName().equals(q.getName()))
				found = true;

			if (connection.get(i).getPersonA().getName().equals(q.getName())
					& connection.get(i).getConn() == GlobalClass.friend
					& connection.get(i).getPersonB().getName().equals(p.getName()))
				found = true;
		}
		return found;
	}

	public void findFamily(Person p, ArrayList<Relationship> connection) {
		if (p instanceof Adult) {
			Adult a = (Adult) p;
			findSpouse(p, connection, GlobalClass.showDetails);
			findChildren(p, connection, GlobalClass.showDetails);

		}
		if (p instanceof Child) {
			Child c = (Child) p;
			findParents(c, connection, GlobalClass.showDetails);
			findSiblings(p, connection, GlobalClass.showDetails);
		}
	}

	public Boolean findSiblings(Person p, ArrayList<Relationship> connection, Boolean print) {
		Boolean found = false;
		ArrayList<Person> siblings = new ArrayList<>();

		for (int i = 0; i < connection.size(); i++) {
			// find parents of child first
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& (connection.get(i).getConn() == GlobalClass.father
							| connection.get(i).getConn() == GlobalClass.mother)) {

				for (int j = 0; j < connection.size(); j++) {
					// match Person A and then get Person B (sibling) if it's not the same as
					// child's
					if (connection.get(j).getPersonA().getName().equals(connection.get(i).getPersonA().getName())
							& (connection.get(j).getConn() == GlobalClass.father
									| connection.get(j).getConn() == GlobalClass.mother)
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

			findSpouse(p, connection, GlobalClass.showDetails);
			findChildren(p, connection, GlobalClass.showDetails);
			findFriends(p, connection, GlobalClass.showDetails);
		}
		if (p instanceof Child) {
			Child c = (Child) p;
			findParents(c, connection, GlobalClass.showDetails);
			findFriends(c, connection, GlobalClass.showDetails);
		}
	}

	public Boolean findParents(Person p, ArrayList<Relationship> connection, Boolean print) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == GlobalClass.father) {
				found = true;
				if (print)
					System.out.println("Father: " + connection.get(i).getPersonA().getName());
			}

			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == GlobalClass.mother) {
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
					& connection.get(i).getConn() == GlobalClass.father) {
				found = true;
			}
		}
		return found;
	}

	public Boolean findMother(Person p, ArrayList<Relationship> connection) {
		Boolean found = false;
		for (int i = 0; i < connection.size(); i++) {
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == GlobalClass.mother) {
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
					& connection.get(i).getConn() == GlobalClass.friend) {
				count++;
				found = true;
				if (print)
					System.out
							.println(((count == 1) ? "Friends :\n-" : "-") + connection.get(i).getPersonB().getName());
			}
			if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == GlobalClass.friend) {
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
			if (connection.get(i).getPersonA().getName().equals(p.getName()) & connection.get(i)
					.getConn() == ((p.getGender() == "M") ? GlobalClass.father : GlobalClass.mother)) {
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
					& connection.get(i).getConn() == GlobalClass.spouse) {
				found = true;
				if (print)
					System.out.println("Spouse: " + connection.get(i).getPersonB().getName());

			} else if (connection.get(i).getPersonB().getName().equals(p.getName())
					& connection.get(i).getConn() == GlobalClass.spouse) {
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
