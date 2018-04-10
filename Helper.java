package AP_Assignment2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 9 April 2018 
Description: Helper Class
Notes: 
 */

public class Helper {
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
}