/*
 *
 *7:05:53 AM
 *Dec 16, 2017
 */
package com.gcit.projects.weekoneproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ThankGod4Life
 * @date Dec 16, 2017
 *
 */
public class AdministratorOld {
	LibraryManagementApp app; // sole attribute to be used for commonly repeated methods

	public AdministratorOld() {
		// first to execute=>create instance of LMApp
		app = new LibraryManagementApp();
	}

	public void AdministratorModeOne(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("Please choose from one of the options below by entering the number next to the text\n");
			System.out.println("1) Add/Update/Delete Book and Author");
			System.out.println("2) Add/Update/Delete Publishers");
			System.out.println("3) Add/Update/Delete Library Branches");
			System.out.println("4) Add/Update/Delete Borrowers");
			System.out.println("5) Over-ride Due Date for a Book Loan");
			System.out.println("6) Quit to previous menu\n");
			int start = 1, end = 6, result = 0;
			result = app.NumControl(start, end, libStateObj);

			// check validity and give message
			if ((result == 1 || result == 2 || result == 3 || result == 4) && (libStateObj.isModRunning())) {
				// validate whether add update or delete
				libStateObj.setUserChoice(result);
				AdministratorModeTwo(libStateObj);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}

			if ((result == 5) && (libStateObj.isModRunning())) {
				// update record for book loan
				libStateObj.setUserChoice(result);
				libStateObj.setAdminChoice("UPDATE");
				AdministratorModeSeven(libStateObj);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}
		}
	}

	private void AdministratorModeTwo(LibrarySystem libStateObj) {
		// to change records on books and author provide choices available
		System.out.println("What operation do you want to carry out?\n");
		System.out.println("1) Add a record");
		System.out.println("2) Update a record");
		System.out.println("3) Delete a record");
		System.out.println("4) Return to previous menu\n");
		int start = 1, end = 4, result = 0;
		String[] options = { "INSERT INTO", "UPDATE", "DELETE FROM" };
		result = app.NumControl(start, end, libStateObj);

		// link to respective branches/ Administrator modes
		if ((libStateObj.getUserChoice() == 1) && (result >= 1 && result < end)) {
			libStateObj.setAdminChoice(options[result - 1]);
			AdministratorModeThree(libStateObj);
		}
		if ((libStateObj.getUserChoice() == 2) && (result >= 1 && result < end)) {
			libStateObj.setAdminChoice(options[result - 1]);
			AdministratorModeFour(libStateObj);
		}
		if ((libStateObj.getUserChoice() == 3) && (result >= 1 && result < end)) {
			libStateObj.setAdminChoice(options[result - 1]);
			AdministratorModeFive(libStateObj);
		}
		if ((libStateObj.getUserChoice() == 4) && (result >= 1 && result < end)) {
			libStateObj.setAdminChoice(options[result - 1]);
			AdministratorModeSix(libStateObj);
		}
	}

	private void AdministratorModeThree(LibrarySystem libStateObj) {
		// deal with book and author
		String query = "SELECT "
				+ "    tbl_book_authors.bookId, tbl_book_authors.authorId, title, authorName FROM   tbl_book_authors "
				+ "        LEFT JOIN    tbl_book ON tbl_book.bookId = tbl_book_authors.bookId"
				+ "        LEFT JOIN    tbl_author ON tbl_book_authors.authorId = tbl_author.authorId "
				+ "UNION SELECT tbl_book_authors.bookId, tbl_book_authors.authorId, title, authorName FROM   tbl_book_authors"
				+ "        right JOIN    tbl_book ON tbl_book.bookId = tbl_book_authors.bookId"
				+ "        right JOIN    tbl_author ON tbl_book_authors.authorId = tbl_author.authorId "
				+ " ORDER BY bookId ASC";
		String[] tables = { "tbl_book", "tbl_author" };
		String[][] columnName = { { "bookId", "Int", "tbl_book", "ID" }, { "authorId", "Int", "tbl_author", "ID" },
				{ "title", "String", "tbl_book", "Non" }, { "authorName", "String", "tbl_author", "Non" } };
		AdministratorModeEight(libStateObj, query, tables, columnName);
	}

	private void AdministratorModeFour(LibrarySystem libStateObj) {
		// deal with publisher
		String query = "SELECT * from tbl_publisher ORDER BY publisherId ASC";
		String[] tables = { "tbl_publisher" };
		String[][] columnName = { { "publisherId", "Int", "tbl_publisher", "ID" },
				{ "publisherName", "String", "tbl_publisher", "Non" },
				{ "publisherAddress", "String", "tbl_publisher", "Non" },
				{ "publisherPhone", "String", "tbl_publisher", "Non" } };
		AdministratorModeEight(libStateObj, query, tables, columnName);
	}

	private void AdministratorModeFive(LibrarySystem libStateObj) {
		// deal with library branch
		String query = "SELECT * from tbl_library_branch ORDER BY branchId ASC";
		String[] tables = { "tbl_library_branch" };
		String[][] columnName = { { "branchId", "Int", "tbl_library_branch", "ID" },
				{ "branchName", "String", "tbl_library_branch", "Non" },
				{ "branchAddress", "String", "tbl_library_branch", "Non" } };
		AdministratorModeEight(libStateObj, query, tables, columnName);
	}

	private void AdministratorModeSix(LibrarySystem libStateObj) {
		// deal with borrower
		String query = "SELECT * from tbl_borrower ORDER BY cardNo ASC";
		String[] tables = { "tbl_borrower" };
		String[][] columnName = { { "cardNo", "Int", "tbl_borrower", "ID" },
				{ "name", "String", "tbl_borrower", "Non" }, { "address", "String", "tbl_borrower", "Non" },
				{ "phone", "String", "tbl_borrower", "Non" } };
		AdministratorModeEight(libStateObj, query, tables, columnName);
	}

	private void AdministratorModeSeven(LibrarySystem libStateObj) {
		// updates to due date on book loan
		String query = "SELECT * from tbl_book_loans ORDER BY bookId ASC";
		String[] tables = { "tbl_book_loans" };
		String[][] columnName = { { "bookId", "Int", "tbl_book_loans", "ID" },
				{ "branchId", "Int", "tbl_book_loans", "ID" }, { "cardNo", "Int", "tbl_book_loans", "ID" },
				{ "dateOut", "String", "tbl_book_loans", "Non" }, { "dueDate", "String", "tbl_book_loans", "Non" },
				{ "dateIn", "String", "tbl_book_loans", "Non" } };
		AdministratorModeEight(libStateObj, query, tables, columnName);
	}

	private void AdministratorModeEight(LibrarySystem libStateObj, String query, String[] tables,
			String[][] columnName) {
		// use while to iterate values
		try {
			HashMap<Integer, AdminObj> adminO = new HashMap<Integer, AdminObj>();
			PreparedStatement pstmt = libStateObj.getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			int value = 1;
			System.out.println("\nHere are all the records currently available");
			while (rs.next()) {
				// instantiate objects upon creation
				AdminObj admj = new AdminObj();
				String output = ""; // for each iteration
				admj.Ids = new HashMap<String, ArrayList<String>>();
				admj.currentVals = new HashMap<String, ArrayList<String>>();
				admj.columnNames = new HashMap<String, ArrayList<String>>();
				admj.IdNames = new HashMap<String, ArrayList<String>>();
				// ArrayList<String> list1 = new ArrayList<String>();
				// ArrayList<String> list2 = new ArrayList<String>();
				// ArrayList<String> list3 = new ArrayList<String>();
				// ArrayList<String> list4 = new ArrayList<String>();
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
							output += rs.getString(columnName[i][0]);
						} else {
							for (int j = 0; j < tables.length; j++) {
								if (columnName[i][2].equals(tables[j])) {
									list.get((4 * j) + 2).add(String.valueOf(rs.getInt(columnName[i][0]))); // column
																											// value
								}
							}
							output += rs.getInt(columnName[i][0]);
						}
						for (int j = 0; j < tables.length; j++) {
							if (columnName[i][2].equals(tables[j])) {
								list.get((4 * j) + 3).add(columnName[i][0]);
							}
						}
						output += "  ";
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
				// admj.Ids.put(tables[0], list1);
				// admj.IdNames.put(tables[0], list2);
				// admj.currentVals.put(tables[0], list3);
				// admj.columnNames.put(tables[0], list4);
				adminO.put(value, admj);
				System.out.println(value + ") " + output);
				value++; // increment
			}

			// for deciding which course of action to take
			if (libStateObj.getAdminChoice().equals("UPDATE")) {
				UpdateRecords(libStateObj, adminO, tables);
			} else if (libStateObj.getAdminChoice().equals("INSERT INTO")) {
				AddRecords(libStateObj, adminO, tables);
			} else {
				DeleteRecords(libStateObj, adminO, tables);
			}

		} catch (SQLException e) {
			System.out.println("\nIt appears there are no records to update! Returning to previous menu.\n");
		}
	}

	private void UpdateRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// update records is almost self explanatory
		System.out.println("\nPlease select a record from the id list above you'll like to update. \n");
		int start = 1, end = adminO.size(), result = app.NumControl(start, end, libStateObj);

		// now verify entry
		if (result >= 1 && result <= end) {
			AdminObj entity = adminO.get(result); // get user choice regarding the records
			String setString = "";
			System.out.println("*** Enter 'quit' at any prompt to cancel operation ***\n");
			boolean running = true;

			// begin loop for all the tables involved
			for (int i = 0; i < tables.length && running; i++) {
				String userEntry = "";
				int count = 0;
				for (int j = 0; j < entity.currentVals.get(tables[i]).size() && running; j++) {
					System.out
							.println("You have chosen to update the record with Id: " + entity.Ids.get(tables[i]).get(0)
									+ " and value: " + entity.currentVals.get(tables[i]).get(j) + "\n");
					System.out.println("Please enter a new value or enter N/A for no change: \n");
					userEntry = app.StrControl(libStateObj); // string input buffer

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
							+ entity.IdNames.get(tables[i]).get(0) + " = '" + entity.Ids.get(tables[i]).get(0) + "';";
					try {
						app.AlterTableControl(libStateObj, sqlString);

						System.out.println("Completed Updates Successfully!\n");
					} catch (SQLException e) {
						System.out.println("SQL error occurred!");
					}
				}
			}
		}
	}

	private void DeleteRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// basically delete record
		System.out.println("\nPlease select a record from the id list above you'll like to eliminate. \n");
		int start = 1, end = adminO.size(), result = app.NumControl(start, end, libStateObj);

		// now verify entry
		if (result >= 1 && result <= end) {
			AdminObj entity = adminO.get(result); // get user choice regarding the records
			System.out.println("*** Enter 'quit' at any prompt to cancel operation ***\n");
			boolean running = true;

			// begin loop for all the tables involved
			for (int i = 0; i < tables.length && running; i++) {
				String userEntry = "";
				System.out.println(
						"You have chosen to dispose of the record with Id: " + entity.Ids.get(tables[i]).get(0));
				System.out.println("\nType 'quit' if you're having any second thoughts right now!\n");
				userEntry = app.StrControl(libStateObj); // string input buffer

				// in case of N/A
				if (userEntry.equals("quit")) {

					// end this procedure
					running = false;
					continue; // procedure
				}
				String sqlString = "Delete from " + tables[i] + " where " + entity.IdNames.get(tables[i]).get(0)
						+ " = '" + entity.Ids.get(tables[i]).get(0) + "';";
				System.out.println(sqlString);
				
				try {
					app.AlterTableControl(libStateObj, sqlString);

					System.out.println("Completed Alteration Successfully!\n");
				} catch (SQLException e) {
					System.out.println("SQL error occurred!");
				}
			}
		}
	}

	private void AddRecords(LibrarySystem libStateObj, HashMap<Integer, AdminObj> adminO, String[] tables) {
		// add to records
		String setString = "";
		String setValue = "";
		System.out.println("\n*** Enter 'quit' at any prompt to cancel operation ***\n");
		boolean running = true;
		AdminObj entity = adminO.get(1); // get user choice regarding the records

		// begin loop for all the tables involved
		for (int i = 0; i < tables.length && running; i++) {
			String userEntry = "";
			int count = 0;

			// keep loop going
			for (int j = 0; j < entity.currentVals.get(tables[i]).size() && running; j++) {
				System.out.println(
						"You are currently adding to value " + entity.columnNames.get(tables[i]).get(j) + "\n");
				System.out.println("Please enter value to add to new record or enter N/A to leave null: \n");
				userEntry = app.StrControl(libStateObj); // string input buffer

				// in case of N/A
				if (!userEntry.equals("quit")) {
					if (!userEntry.equals("N/A")) {
						if (j < 1 || count < 1) {
							setString = entity.columnNames.get(tables[i]).get(j); // start and reset
							setValue = userEntry;
						} else {
							setString += ", " + entity.columnNames.get(tables[i]).get(j);
							setValue += "', '" + userEntry;
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
				String sqlString = "Insert into " + tables[i] + " (" + setString + ") values ('" + setValue + "');";

				try {
					app.AlterTableControl(libStateObj, sqlString);

					System.out.println("Completed Updates Successfully!\n");
				} catch (SQLException e) {
					System.out.println("SQL error occurred!");
				}
			}
		}
	}
}
