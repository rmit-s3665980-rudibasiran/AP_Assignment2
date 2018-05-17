package AP_Assignment2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javafx.scene.control.Label;
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

	public static Boolean runTextMode = false;
	public static Color menuRectColor = Color.web("#4d4d4d", 1.0);
	public static Color menuRectColorSelected = Color.rgb(0, 0, 0);
	public static Color menuRectBorderSelected = Color.ORANGERED;
	public static Color menuRectBorder = Color.rgb(25, 25, 25);
	public static Color menuRectTextColor = Color.rgb(255, 255, 255);
	public static Color menuRectTextSelected = Color.rgb(200, 200, 200);
	public static Color menuBackColor = Color.web("#666666", 1.0);

	public static String lblStyle = "-fx-font-family:\"Helvetica\";\n" + "    -fx-font-size: 18px;\n"
			+ "  -fx-font-weight: bold;  -fx-text-fill: white;";
	public static String workScreenStyle = "-fx-background-color: #4d4d4d;";
	public static String workScrollStyle = "-fx-background: rgb(80,80,80);";
	public static int workTextFieldArraySize = 5;
	public static int infoLabelWidth = 750;
	public static int infoLabelHeight = 200;
	public static int workbtnWidth = 100;
	public static int txtFieldWidth = 300;
	public static double workWidth = 2000;
	public static double workHeight = 1000;

	public static int startX = 5;
	public static int startY = 5;
	public static int rectOffset = 55;

	public static int menuStartY = 5;

	public static String btnStyle = "-fx-background-color: \n"
			+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n"
			+ "        linear-gradient(#020b02, #3a3a3a),\n"
			+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n"
			+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n"
			+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\n"
			+ "    -fx-background-insets: 0 0 -1 0,0,1;\n" + "    -fx-background-radius: 5,5,4;\n"
			+ "    -fx-padding: 7 30 7 30;\n" + "    -fx-text-fill: #242d35;\n"
			+ "    -fx-font-family: \"Helvetica\";\n" + "    -fx-font-size: 12px;\n" + "    -fx-text-fill: white;";

	public static String btnEffect = "-fx-background-color: \n"
			+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n"
			+ "        linear-gradient(#020b02, #3a3a3a),\n"
			+ "        linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%),\n"
			+ "        linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%);\n"
			+ "    -fx-background-insets: 0 0 -1 0,0,1;\n" + "    -fx-background-radius: 5,5,4;\n"
			+ "    -fx-padding: 7 30 7 30;\n" + "    -fx-text-fill: #242d35;\n"
			+ "    -fx-font-family: \"Helvetica\";\n" + "    -fx-font-size: 12px;\n" + "    -fx-text-fill: black;";

	public static int rectWidth = 200;
	public static int rectHeight = 50;

	public static String spaces = "     ";
	public static int menuSize = 12;
	public static int quitMenu = 0;
	public static int addPerson = 1;
	public static int findPerson = 2;
	public static int displayProfile = 3;
	public static int displayAllProfile = 4;
	public static int updateProfile = 5;
	public static int deletePerson = 6;
	public static int connectPerson = 7;
	public static int findConnection = 8;
	public static int findFamily = 9;
	public static int findClassmates = 10;
	public static int findColleagues = 11;

	public static int friend = 0;
	public static int spouse = 1;
	public static int father = 2;
	public static int mother = 3;
	public static int classmate = 4;
	public static int colleague = 5;

	public static Boolean showDetails = true;
	public static Boolean suppressDetails = false;

	public static int ChildAge = 16;
	public static int YoungChildAge = 2;
	public static int ageGap = 3;

	public static String roleDesc[] = { "Friend", "Spouse", "Father", "Mother", "Classmate", "Colleague" };

	public static String stateDesc[] = { "ACT", "NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA" };

	public static String menuDesc[] = { "Quit", "Add Person", "Find Person", "Display Single Profile",
			"Display All Profile(s)", "Update Profile", "Delete Person", "Connect Person", "Find Connection",
			"Find Family", "Find Classmate", "Find Colleague" };

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

	public static Pane rectMouseEntered(Pane p) {
		Pane newPane = new Pane();
		newPane = p;
		Rectangle r = new Rectangle();
		Label l = new Label();
		for (int i = 0; i < p.getChildren().size(); i++) {
			if (p.getChildren().get(i) instanceof Rectangle) {
				r = (Rectangle) p.getChildren().get(i);
				r.setFill(Helper.menuRectColorSelected);
				r.setStroke(Helper.menuRectColorSelected);

			}
			if (p.getChildren().get(i) instanceof Label) {
				l = (Label) p.getChildren().get(i);
				l.setTextFill(Helper.menuRectTextSelected);
			}
		}
		return newPane;
	}

	public static Pane rectMouseExited(Pane p) {
		Pane newPane = new Pane();
		newPane = p;
		Rectangle r = new Rectangle();
		Label l = new Label();
		for (int i = 0; i < p.getChildren().size(); i++) {
			if (p.getChildren().get(i) instanceof Rectangle) {
				r = (Rectangle) p.getChildren().get(i);
				r.setFill(Helper.menuRectColor);
				r.setStroke(Helper.menuRectColor);
				Helper.doRectEffect(r);

			}
			if (p.getChildren().get(i) instanceof Label) {
				l = (Label) p.getChildren().get(i);
				l.setTextFill(Helper.menuRectTextColor);
			}
		}

		return newPane;
	}

	public static Pane rectMousePressed(Pane p) {
		Pane newPane = new Pane();
		newPane = p;
		Rectangle r = new Rectangle();
		Label l = new Label();
		for (int i = 0; i < p.getChildren().size(); i++) {
			if (p.getChildren().get(i) instanceof Rectangle) {
				r = (Rectangle) p.getChildren().get(i);
				r.setFill(Helper.menuRectColorSelected);
				r.setStroke(Helper.menuRectBorderSelected);
				r.setEffect(null);
			}
			if (p.getChildren().get(i) instanceof Label) {
				l = (Label) p.getChildren().get(i);
				l.setTextFill(Helper.menuRectTextSelected);
			}
		}
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

	public static DropShadow dropShadow() {
		// DropShadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(3);
		dropShadow.setOffsetY(3);
		return dropShadow;
	}
}