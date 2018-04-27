package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 18 March 2018 
Description: Menu Class 
Notes: --
 */

public class Menu {
	private int _choice;
	private Boolean _quit;

	private String[] _menuItems = new String[Helper.menuSize];

	public Menu() {
		_choice = 1;
		_quit = false;
		_menuItems[Helper.quitMenu] = "Quit";
		_menuItems[Helper.addPerson] = "Add Person";
		_menuItems[Helper.findPerson] = "Find Person";
		_menuItems[Helper.displayProfile] = "Display Single Profile";
		_menuItems[Helper.displayAllProfile] = "Display All Profile(s)";
		_menuItems[Helper.updateProfile] = "Update Profile";
		_menuItems[Helper.deletePerson] = "Delete Person";
		_menuItems[Helper.connectPerson] = "Connect Person";
		_menuItems[Helper.findFriends] = "Find Friends";
		_menuItems[Helper.findFamily] = "Find Family";

	}

	public int getOption() {
		return _choice;
	}

	public void displayMenu() {

		Helper.drawLine();

		System.out.println("MiniNet Menu");

		Helper.drawLine();

		for (int i = 1; i < _menuItems.length; i++)
			System.out.println((i) + ": " + _menuItems[i]);

		System.out.println((0) + ": " + _menuItems[0]);

		_choice = Helper.getIntegerInput("Enter Option: ", 0, _menuItems.length);
		if (_choice == 0)
			_quit = true;
		Helper.drawLine();

	}

	public Boolean exitMenu() {
		return _quit;
	}

}
