package AP_Assignment2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 9 April 2018 
Description: Helper Class
Notes: 
 */

public class Helper {

	public static Color menuRectColor = Color.rgb(25, 25, 25);
	public static Color menuRectColorSelected = Color.rgb(200, 200, 200);
	public static Color menuRectBorder = Color.rgb(25, 25, 25);
	public static Color menuTextColor = Color.rgb(200, 200, 200);
	public static Color menuBackColor = Color.rgb(40, 40, 40);

	public static String spaces = "     ";
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

	public static String menuDesc[] = { "Quit", "Add Person", "Find Person", "Display Single Profile",
			"Display All Profile(s)", "Update Profile", "Delete Person", "Connect Person", "Find Friends",
			"Find Family" };

	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(new Date());
	}

	public static boolean isTimeABeforeTimeB(String timeA, String timeB) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
			Date dA = dateFormat.parse(timeA);
			Date dB = dateFormat.parse(timeB);
			if (dA.getTime() < dB.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			//
		}
		return false;
	}

	public static String getDateAndTimeInput(String prompt) {
		Scanner input = new Scanner(System.in);
		String ans;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
		dateFormat.setLenient(false);
		boolean dateValid;
		do {
			System.out.println("");
			System.out.print(prompt);
			ans = input.nextLine();
			ans = ans.trim();
			dateValid = true;
			try {
				Date d = dateFormat.parse(ans);
			} catch (Exception e) {
				dateValid = false;
			}
		} while (!dateValid);
		return ans;
	}

	public static String getStringInput(String prompt) {
		Scanner input = new Scanner(System.in);
		String ans;

		do {
			System.out.println("");
			System.out.print(prompt);
			ans = input.nextLine();
			ans = ans.trim();
		} while (ans.length() == 0);
		return ans;
	}

	public static double getDoubleInput(String prompt) {

		Scanner input = new Scanner(System.in);
		double ans = 0;
		boolean inputValid;
		do {
			System.out.print(prompt);
			String s = input.nextLine();
			// Convert string input to integer
			try {
				ans = Double.parseDouble(s);
				inputValid = true;
			} catch (Exception e) {
				inputValid = false;
			}
		} while (!inputValid);
		return ans;
	}

	public static int getIntegerInput(String prompt) {
		Scanner input = new Scanner(System.in);
		int ans = 0;
		boolean inputValid;
		do {
			System.out.println("");
			System.out.print(prompt);
			String s = input.nextLine();
			// Convert string input to integer
			try {
				ans = Integer.parseInt(s);
				inputValid = true;
			} catch (Exception e) {
				inputValid = false;
			}
		} while (!inputValid);
		return ans;
	}

	public static int getIntegerInput(String prompt, int lowerBound, int upperBound) {
		Scanner input = new Scanner(System.in);
		int ans = 0;
		boolean inputValid;
		do {
			System.out.println("");
			System.out.print(prompt);
			String s = input.nextLine();
			// Convert string input to integer
			try {
				ans = Integer.parseInt(s);
				if (ans >= lowerBound && ans <= upperBound) {
					System.out.println("Invalid Option!");
					inputValid = true;
				} else {
					inputValid = false;
				}
			} catch (Exception e) {
				inputValid = false;
			}
		} while (!inputValid);
		return ans;
	}

	public static void getAnyKey(String prompt) {
		System.out.println("");
		System.out.print(prompt);
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
	}

	public static void drawLine() {
		for (int x = 0; x < 50; x++)
			System.out.print("-");
		System.out.println("");
	}

	public static Pane rollOver(Pane p) {
		Pane newPane = new Pane();
		newPane = p;
		DropShadow rollOverColor = new DropShadow();
		rollOverColor.setColor(Color.ORANGERED);

		newPane.setEffect(rollOverColor);
		return newPane;
	}

	public static Pane clickColor(Pane p) {
		Pane newPane = new Pane();
		newPane = p;

		DropShadow clickColor = new DropShadow();
		clickColor.setColor(Color.GREEN);
		newPane.setEffect(clickColor);
		return newPane;
	}

	public static Rectangle doRectEffect(Rectangle r) {
		Rectangle newRect = new Rectangle();
		newRect = r;
		int shadowWidth = 2;
		int shadowHeight = 2;
		int shadowOffsetX = 5;
		int shadowOffsetY = 5;
		int shadowRadius = 2;

		DropShadow ds = new DropShadow();
		ds.setWidth(shadowWidth);
		ds.setHeight(shadowHeight);
		ds.setOffsetX(shadowOffsetX);
		ds.setOffsetY(shadowOffsetY);
		ds.setRadius(shadowRadius);
		ds.setColor(Color.BLACK);
		ds.setBlurType(BlurType.GAUSSIAN);

		newRect.setEffect(ds);
		return newRect;
	}
}