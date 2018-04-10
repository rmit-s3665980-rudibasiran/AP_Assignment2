package AP_Assignment2;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 22 March 2018 
Description: GlobalClass
Notes: Global variables and re-usable functions so that they need not be declared/created anew each time
 */

public class GlobalClass {

	public static int menuSize = 10;
	public static int quitMenu = 0;
	public static int addPerson = 1;
	public static int findPerson = 2;
	public static int displayProfile = 3;
	public static int displayAllProfile = 4;
	public static int updateProfile = 5;
	public static int deletePerson = 6;
	public static int connectPerson = 7;
	public static int findFriends = 8;
	public static int findFamily = 9;

	public static int friend = 0;
	public static int spouse = 1;
	public static int father = 2;
	public static int mother = 3;

	public static Boolean showDetails = true;
	public static Boolean suppressDetails = false;

	public static int minorAge = 16;
	public static int babyAge = 2;
	public static int ageGap = 3;

	public static String roleDesc[] = { "Friend", "Spouse", "Father", "Mother" };

}