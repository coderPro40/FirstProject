/*
 *
 *1:39:37 AM
 *Dec 15, 2017
 */
package com.gcit.projects.weekoneproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ThankGod4Life
 * @date Dec 15, 2017
 *
 */
public class LibraryManagementApp {

	/**
	 * @param args
	 */

	/*
	 * https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-
	 * by-property
	 * 
	 * public class CustomComparator implements Comparator<MyObject> {
	 * 
	 * @Override public int compare(MyObject o1, MyObject o2) { return
	 * o1.getStartDate().compareTo(o2.getStartDate()); } }
	 */

	public static void main(String[] args) {
		// objects needed for the program
		Librarian libn = new Librarian();
		Administrator adms = new Administrator();
		Borrower brr = new Borrower();
		LibrarySystem libStateObj = new LibrarySystem();
		libStateObj.setRunning(true); // initialize to true
		libStateObj.setScanner(new Scanner(System.in));

		// main loop
		while (libStateObj.isRunning()) {
			/*
			 * there's a problem with instantiating java scanner object within while loop.
			 * It keeps storing the <Enter> key as new line object. Thus will lead to a
			 * while loop.
			 */
			int choice = WelcomeMessage(libStateObj);
			libStateObj.setAdminChoice("Null");
			if (choice == 1) {
				// opening port for librarian
				libn.LibrarianModeOne(libStateObj);
			}
			if (choice == 2) {
				// opening port for administrator
				adms.AdministratorModeOne(libStateObj);
			}
			if (choice == 3) {
				// for borrower
				brr.BorrowerModeOne(libStateObj);
			}
		}
		libStateObj.getScanner().close(); // end scanner object
		try {
			libStateObj.getConn().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int WelcomeMessage(LibrarySystem libStateObj) {
		System.out.println("\n*********************************************************************************");
		System.out.println("Welcome to the GCIT Library Management System. Which category of user are you?");
		System.out.println("Special Announcement: * = informational | # = warning");
		System.out.println("*********************************************************************************\n");
		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");
		System.out.println("4) Exit Library System");
		int start = 1, end = 4, verify = 0, result = 0;

		// control entry from user
		try {
			// if next int in range of start and stop
			if (libStateObj.getScanner().hasNextInt(end + 1)
					&& (verify = libStateObj.getScanner().nextInt()) > (start - 1)) {
				result = verify;
			}
		} catch (InputMismatchException | IllegalStateException e) {
			System.out.println("\n################################################################################");
			System.out.println("You entered the wrong input type! Try again.");
			System.out.println("################################################################################\n");
			libStateObj.getScanner().nextLine();
		} finally {
			// TODO: handle finally clause
			if (libStateObj.getScanner().hasNextLine()) {
				libStateObj.getScanner().nextLine();
			}
		}

		// check validity and give message
		if (result < start || result > end) {
			System.out.println("\n################################################################################");
			System.out.println("You entered a value not specified in the list! Try again.");
			System.out.println("################################################################################\n");

		} else if (result == end) {
			// return to previous screen
			System.out.println("\n################################################################################");
			System.out.println("Closing System. Thanks for connecting!.");
			System.out.println("################################################################################\n");

			libStateObj.setRunning(false);
		}

		return result;
	}

	public void FormatData(LibrarySystem libStateObj, String query, String[] tables, String[][] columnName,
			boolean show) {
		// use while to iterate values
		try {
			HashMap<Integer, AdminObj> adminO = new HashMap<Integer, AdminObj>();
			PreparedStatement pstmt = libStateObj.getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			int value = 1;
			String names = ""; // for each iteration

			if (show) {
				System.out
						.println("\n*********************************************************************************");
				System.out.println("Here are all the records currently available");
				System.out
						.println("*********************************************************************************\n");
				for (int i = 0; i < columnName.length; i++) {
					names += (!columnName[i][3].equals("ID")) ? " |               | " + columnName[i][0] : "";
				}
				System.out.println("records" + " " + names);
			}

			while (rs.next()) {
				// instantiate objects upon creation
				AdminObj admj = new AdminObj();
				String output = ""; // for each iteration
				admj.Ids = new HashMap<String, ArrayList<String>>();
				admj.currentVals = new HashMap<String, ArrayList<String>>();
				admj.columnNames = new HashMap<String, ArrayList<String>>();
				admj.IdNames = new HashMap<String, ArrayList<String>>();
				ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

				for (int i = 0; i < (tables.length * 4); i++) {
					ArrayList<String> liste = new ArrayList<String>();
					list.add(liste);
				}

				for (int i = 0; i < columnName.length; i++) {
					// 0 for value 1 for if value type use 2 for table name
					if (columnName[i][3].equals("ID")) {
						// store primary keys and their ids
						for (int j = 0; j < tables.length; j++) {
							if (columnName[i][2].equals(tables[j])) {
								list.get(4 * j).add(String.valueOf(rs.getInt(columnName[i][0]))); // value
								list.get((4 * j) + 1).add(columnName[i][0]); // name
							}
						}
					} else {
						// conditional statement
						if (columnName[i][1].equals("String")) {
							for (int j = 0; j < tables.length; j++) {
								if (columnName[i][2].equals(tables[j])) {
									list.get((4 * j) + 2).add(rs.getString(columnName[i][0])); // value
								}
							}
							output += " |               | " + rs.getString(columnName[i][0]);
						} else {
							for (int j = 0; j < tables.length; j++) {
								if (columnName[i][2].equals(tables[j])) {
									list.get((4 * j) + 2).add(String.valueOf(rs.getInt(columnName[i][0]))); // column
																											// value
								}
							}
							output += " |               | " + rs.getInt(columnName[i][0]);
						}
						for (int j = 0; j < tables.length; j++) {
							if (columnName[i][2].equals(tables[j])) {
								list.get((4 * j) + 3).add(columnName[i][0]);
							}
						}
					}
				}

				for (int i = 0; i < (tables.length * 4); i++) {
					int counter = i / 4;
					// System.out.println(list.get(i));

					if ((i % 4) == 0) {
						admj.Ids.put(tables[counter], list.get(i));
					}
					if ((i % 4) == 1) {
						admj.IdNames.put(tables[counter], list.get(i));
					}
					if ((i % 4) == 2) {
						admj.currentVals.put(tables[counter], list.get(i));
					}
					if ((i % 4) == 3) {
						admj.columnNames.put(tables[counter], list.get(i));
					}
				}

				adminO.put(value, admj);
				if (show) {
					System.out.println(value + ") " + output);
				}
				value++; // increment
			}

			// for deciding which course of action to take
			if (libStateObj.getAdminChoice().equals("UPDATE")) {
				System.out
						.println("\n*********************************************************************************");
				System.out.println("Please select a record from the id list above you'll like to update. ");
				System.out
						.println("*********************************************************************************\n");
				int start = 1, end = adminO.size(), result = NumControl(start, end, libStateObj);

				if (result >= 1 && result <= end) {
					libStateObj.setUserChoiceTwo(result);
					UpdateRecords(libStateObj, adminO, tables);
				}
			} else if (libStateObj.getAdminChoice().equals("INSERT INTO")) {
				AddRecords(libStateObj, adminO, tables);
			} else if (libStateObj.getAdminChoice().equals("DELETE FROM")) {
				System.out
						.println("\n*********************************************************************************");
				System.out.println("* Please select a record from the id list above you'll like to eliminate. *");
				System.out
						.println("*********************************************************************************\n");
				int start = 1, end = adminO.size(), result = NumControl(start, end, libStateObj);

				// now verify entry
				if (result >= 1 && result <= end) {
					libStateObj.setUserChoiceTwo(result);
					DeleteRecords(libStateObj, adminO, tables);
				}
			} else {
				// special condition that only executes if call isn't to insert, modify or
				// delete table values
				libStateObj.setRecordSet(adminO);
				libStateObj.setChosenTable(tables);
			}

		} catch (SQLException e) {
			System.out.println("\n################################################################################");
			System.out.println("Couldn't retrieve any records for update! Returning to previous menu.");
			System.out.println("################################################################################\n");
		}
	}

	public void UpdateRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// update records is almost self explanatory
		AdminObj entity = adminO.get(libStateObj.getUserChoiceTwo()); // get user choice regarding the records
		String setString = "", idString = "";
		System.out.println("\n################################################################################");
		System.out.println("# Enter 'quit' at any prompt to cancel operation #");
		System.out.println("################################################################################\n");
		boolean running = true;

		// begin loop for all the tables involved
		for (int i = 0; i < tables.length && running; i++) {
			String userEntry = "", idUse = "";
			int count = 0;
			int count2 = 0;
			for (int j = 0; j < entity.Ids.get(tables[i]).size() && running; j++) {
				idUse += entity.Ids.get(tables[i]).get(j) + " ";
				if (j < 1 || count2 < 1) {
					idString = entity.IdNames.get(tables[i]).get(j) + " = '" + entity.Ids.get(tables[i]).get(j); // start and
																								// reset
				} else {
					idString += "' and " + entity.IdNames.get(tables[i]).get(j) + " = '" + entity.Ids.get(tables[i]).get(j);
				}
				count2++;
			}
			for (int j = 0; j < entity.currentVals.get(tables[i]).size() && running; j++) {
				System.out
						.println("\n*********************************************************************************");
				System.out.println("You have chosen to update the record with Id: " + idUse + " and value: "
						+ entity.currentVals.get(tables[i]).get(j) + "");
				System.out.println("*********************************************************************************");

				System.out.println("Please enter a new value or enter N/A for no change: ");
				System.out
						.println("*********************************************************************************\n");
				userEntry = StrControl(libStateObj); // string input buffer

				// in case of N/A
				if (!userEntry.equals("quit")) {
					if (!userEntry.equals("N/A")) {
						if (j < 1 || count < 1) {
							setString = entity.columnNames.get(tables[i]).get(j) + " = '" + userEntry; // start and
																										// reset
						} else {
							setString += "', " + entity.columnNames.get(tables[i]).get(j) + " = '" + userEntry;
						}
						count++;
					}
					continue; // procedure doesn't end
				}

				// end this procedure
				running = false;
			}
			if (userEntry.equals("quit")) {
				// end this procedure
				running = false;
				continue;
			}
			if (!setString.equals("")) {
				String sqlString = "Update " + tables[i] + " set " + setString + "' where "
						+ idString + "';";
				try {
					AlterTableControl(libStateObj, sqlString);
					System.out.println(
							"\n*********************************************************************************");
					System.out.println("Completed Updates Successfully!");
					System.out.println(
							"*********************************************************************************\n");
				} catch (SQLException e) {
					System.out.println(
							"\n################################################################################");
					System.out.println("Couldn't complete Database operation!");
					System.out.println(
							"################################################################################\n");
				}
			}
		}
	}

	private void DeleteRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// basically delete record
		AdminObj entity = adminO.get(libStateObj.getUserChoiceTwo()); // get user choice regarding the records
		System.out.println("\n################################################################################");
		System.out.println("# Enter 'quit' right now as this is your only chance to revert this decision!! #");
		System.out.println("################################################################################\n");
		String userEntry = StrControl(libStateObj); // string input buffer
		boolean running = (userEntry.equals("quit")) ? false : true;

		// begin loop for all the tables involved
		for (int i = 0; i < tables.length && running; i++) {
			for (int j = 0; j < entity.Ids.get(tables[i]).size(); j++) {
				System.out.println(
						"You have chosen to dispose of the record with Id: " + entity.Ids.get(tables[i]).get(j));
				String sqlString = "Delete from " + tables[i] + " where " + entity.IdNames.get(tables[i]).get(j)
						+ " = '" + entity.Ids.get(tables[i]).get(0) + "';";
				System.out.println(sqlString);

				try {
					AlterTableControl(libStateObj, sqlString);
					System.out.println("Completed Alteration Successfully!\n");
				} catch (SQLException e) {
					System.out.println(
							"\n################################################################################");
					System.out.println("Couldn't complete Database operation!");
					System.out.println(
							"################################################################################\n");
				}
			}
		}
	}

	private void AddRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// add to records
		String setString = "";
		String setValue = "";
		System.out.println("\n################################################################################");
		System.out.println("# Enter 'quit' to cancel operation or press any other button to continue #");
		System.out.println("################################################################################\n");
		String userEntry = StrControl(libStateObj); // string input buffer
		boolean running = (userEntry.equals("quit")) ? false : true;
		AdminObj entity = adminO.get(1); // get user choice regarding the records

		// begin loop for all the tables involved
		for (int i = 0; i < tables.length && running; i++) {
			int count = 0;

			// keep loop going
			for (int j = 0; j < entity.currentVals.get(tables[i]).size() && running; j++) {
				System.out
						.println("\n*********************************************************************************");
				System.out
						.println("You are currently adding to value " + entity.columnNames.get(tables[i]).get(j) + "");
				System.out.println("*********************************************************************************");
				System.out.println("Please enter value to add to new record or enter N/A to leave null: ");
				System.out
						.println("*********************************************************************************\n");
				userEntry = StrControl(libStateObj); // string input buffer

				if (j < 1 || count < 1) {
					setString = entity.columnNames.get(tables[i]).get(j); // start and reset
					setValue = userEntry;
				} else {
					setString += ", " + entity.columnNames.get(tables[i]).get(j);
					setValue += "', '" + userEntry;
				}
				count++;
			}
			if (!setString.equals("")) {
				String sqlString = "Insert into " + tables[i] + " (" + setString + ") values ('" + setValue + "');";

				try {
					AlterTableControl(libStateObj, sqlString);
					System.out.println(
							"\n*********************************************************************************");
					System.out.println("Completed Updates Successfully!");
					System.out.println(
							"*********************************************************************************\n");
				} catch (SQLException e) {
					System.out.println(
							"\n################################################################################");
					System.out.println("Couldn't complete Database operation!");
					System.out.println(
							"################################################################################\n");
				}
			}
		}
	}

	public void AlterTableControl(LibrarySystem libStateObj, String sqlString) throws SQLException {
		// use SQL queries to update library location information on file
		try {
			PreparedStatement pstmt = libStateObj.getConn().prepareStatement(sqlString);
			pstmt.executeUpdate(); // execute the statement but don't commit yet
			libStateObj.getConn().commit(); // commit changes to the database
			System.out.println("\n*********************************************************************************");
			System.out.println("Process completed.");
			System.out.println("*********************************************************************************\n");
		} catch (SQLException e) {
			libStateObj.getConn().rollback();
			libStateObj.setTransactionWin(false);
			System.out.println("\n################################################################################");
			System.out.println("Process could not be completed.");
			System.out.println("################################################################################\n");
		}
	}

	public String StrControl(LibrarySystem libStateObj) {
		String userEntry = "";

		// for getting user's input
		try {
			if (libStateObj.getScanner().hasNextLine()) {
				// store in string
				userEntry = libStateObj.getScanner().nextLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("\n################################################################################");
			System.out.println("You entered an invalid value! Try again.");
			System.out.println("################################################################################\n");
			libStateObj.getScanner().nextLine();
		} finally {
			// TODO: handle finally clause
			if (libStateObj.getScanner().hasNextLine()) {
				libStateObj.getScanner().nextLine();
			}
		}
		return userEntry;
	}

	public int NumControl(int start, int end, LibrarySystem libStateObj) {
		int choice = 0, verify = 0;

		// control entry from user
		try {
			// if next int in range of start and stop
			if (libStateObj.getScanner().hasNextInt(end + 1)
					&& (verify = libStateObj.getScanner().nextInt()) > (start - 1)) {
				choice = verify;
			}
		} catch (InputMismatchException | IllegalStateException e) {
			System.out.println("\n################################################################################");
			System.out.println("You entered the wrong input type! Try again.");
			System.out.println("################################################################################\n");
			libStateObj.getScanner().nextLine();
		} finally {
			// TODO: handle finally clause
			if (libStateObj.getScanner().hasNextLine()) {
				libStateObj.getScanner().nextLine();
			}
		}

		// check validity and give message
		if (choice < 1 || choice > end) {
			System.out.println("\n################################################################################");
			System.out.println("You entered a value not specified in the list! Try again.\n");
			System.out.println("################################################################################\n");
		}

		return choice;
	}
}
