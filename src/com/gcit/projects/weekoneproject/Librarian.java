/*
 *
 *5:47:11 AM
 *Dec 16, 2017
 */
package com.gcit.projects.weekoneproject;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author ThankGod4Life
 * @date Dec 16, 2017
 *
 */
public class Librarian {
	LibraryManagementApp app; // sole attribute to be used for commonly repeated methods

	public Librarian() {
		// first to execute=>create instance of LMApp
		app = new LibraryManagementApp();
	}

	public void LibrarianModeOne(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("\n*********************************************************************************");
			System.out.println("Please choose from one of the options below by entering the number next to the text:");
			System.out.println("*********************************************************************************\n");
			System.out.println("1) Enter Branch you manage");
			System.out.println("2) Quit to previous menu");
			int start = 1, end = 2, result = 0;
			result = app.NumControl(start, end, libStateObj);

			if ((result >= 1 && result < end) && (libStateObj.isModRunning())) {
				// continue program
				LibrarianModeTwo(libStateObj);
				libStateObj.setModRunning(true); // to resume prompt for this section
			} else {
				System.out.println("\n################################################################################");
				System.out.println("You either selected an invalid input or chose to go back");
				System.out.println("Returning to main menu");
				System.out.println("################################################################################\n");
				libStateObj.setModRunning(false); // to resume prompt for this section
			}
		}
	}

	private void LibrarianModeTwo(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// While loop for branch choices
		while (libStateObj.isModRunning()) {
			libStateObj.setAdminChoice("Null"); // retrieve values
			String query = "select * from tbl_library_branch order by branchId";
			String[] tables = { "tbl_library_branch" };
			String[][] columnName = { { "branchId", "Int", "tbl_library_branch", "ID" },
					{ "branchName", "String", "tbl_library_branch", "Non" },
					{ "branchAddress", "String", "tbl_library_branch", "Non" } };
			System.out.println("\n*********************************************************************************");
			System.out.println("Please choose from one of the options below by entering the number next to the text:");
			System.out.println("*********************************************************************************\n");
			boolean show = true;
			app.FormatData(libStateObj, query, tables, columnName, show);
			int count = (libStateObj.getRecordSet().size() + 1);
			System.out.println(count + ")  |               | Quit to previous menu");
			int start = 1, end = count, result = 0;
			result = app.NumControl(start, end, libStateObj);

			// check validity and give message
			if ((result >= 1 && result < end) && (libStateObj.isModRunning())) {
				// continue program
				libStateObj.setUserChoiceTwo(result);
				AdminObj entity = libStateObj.getRecordSet().get(result);
				LibrarianModeThree(libStateObj, entity, tables);
				libStateObj.setModRunning(true); // to resume prompt for this section
			} else {
				System.out.println("\n################################################################################");
				System.out.println("You either selected an invalid input or chose to go back");
				System.out.println("Returning to previous menu");
				System.out.println("################################################################################\n");
				libStateObj.setModRunning(false); // to resume prompt for this section
			}
		}
	}

	private void LibrarianModeThree(LibrarySystem libStateObj, AdminObj entity, String[] tables) {
		// to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("\n*********************************************************************************");
			System.out.println("Please choose from one of the options below by entering the number next to the text");
			System.out.println("*********************************************************************************\n");
			System.out.println("1) Update the details of the Library");
			System.out.println("2) Add copies of Book to the Branch");
			System.out.println("3) Quit to previous");
			int start = 1, end = 3, result = 0;
			result = app.NumControl(start, end, libStateObj);

			// check validity and give message
			if ((result == 1) && (libStateObj.isModRunning())) {
				// continue program
				System.out.println("\n*********************************************************************************");
				System.out.println(
						"Keep in mind changes to branch name are only reflected after exiting to the branch select menu");
				System.out.println("*********************************************************************************\n");
				app.UpdateRecords(libStateObj, libStateObj.getRecordSet(), tables);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}
			if ((result == 2) && (libStateObj.isModRunning())) {
				// continue program
				LibrarianModeFour(libStateObj, entity, tables);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}
			if(result == 3) {
				System.out.println("\n################################################################################");
				System.out.println("You either selected an invalid input or chose to go back");
				System.out.println("Returning to previous menu");
				System.out.println("################################################################################\n");
				libStateObj.setModRunning(false);
			}
		}

	}

	private void LibrarianModeFour(LibrarySystem libStateObj, AdminObj entity, String[] tables) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// loop for book copies section
		while (libStateObj.isModRunning()) {
			HashMap<Integer, AdminObj> holder = libStateObj.getRecordSet();
			// use SQL queries to retrieve possible library locations on file
			libStateObj.setAdminChoice("Null");
			String query = "SELECT " + " * " + "FROM" + "    library.tbl_library_branch AS tlb" + "        left JOIN"
					+ "    library.tbl_book_copies AS tbc ON tlb.branchId = tbc.branchId" + "        left JOIN"
					+ "    library.tbl_book AS tb ON tbc.bookId = tb.bookId" + "        left JOIN"
					+ "    library.tbl_book_authors AS tba ON tb.bookId = tba.bookId" + "        left JOIN"
					+ "    library.tbl_author AS ta ON tba.authorId = ta.authorId"
					+ " Group by tb.bookId having tlb.branchId = " + entity.Ids.get(tables[0]).get(0)
					+ " order by tbc.bookId asc";
			String[] table = { "tbl_book_copies", "tbl_book", "tbl_author" };
			String[][] columnName = { { "bookId", "Int", "tbl_book_copies", "ID" },
					{ "title", "String", "tbl_book", "Non" }, { "authorName", "String", "tbl_author", "Non" },
					{ "noOfCopies", "Int", "tbl_book_copies", "Non" } };
			System.out.println("\n*********************************************************************************");
			System.out.println("Pick the Book whose copies you want to add to your Library branch:");
			System.out.println("*********************************************************************************\n");

			boolean show = true;
			app.FormatData(libStateObj, query, table, columnName, show); // change record set for user's book copies choice
			int count = libStateObj.getRecordSet().size();
			int start = 1, end = count, result = 0;

			// semantic condition
			if (count > 0) {
				result = app.NumControl(start, end, libStateObj);
				libStateObj.setModRunning(true); // will always run until user provides valid input
			} else {
				System.out.println("\n################################################################################");
				System.out.println(
						"There are currently no books in this branch of library! Please consider another one.");
				System.out.println("################################################################################\n");
				libStateObj.setModRunning(false); // handle condition of no books in library branch
			}

			// check validity and give message
			if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
				// continue program
				AdminObj entity2 = libStateObj.getRecordSet().get(result);
				LibrarianModeFive(libStateObj, entity, entity2, tables, table);
				libStateObj.setModRunning(false); // to end prompt for this section
			}

			libStateObj.setRecordSet(holder); // so always has original
		}
	}

	private void LibrarianModeFive(LibrarySystem libStateObj, AdminObj entity, AdminObj entity2, String[] tables,
			String[] table) {
		// user choice - 1 on index of ArrayList to get book object and existing no of
		// copies
		System.out.println("\n*********************************************************************************");
		System.out.println("Existing number of copies: " + entity2.currentVals.get(table[0]).get(0));
		System.out.println("*********************************************************************************");
		System.out.println("Enter updated number of copies from 1 to 16: ");
		System.out.println("*********************************************************************************\n");
		int start = 1, end = 16, result = 0;
		result = app.NumControl(start, end, libStateObj);

		// check validity and give message
		if (result >= 1 && result <= end) {
			try {
				int addition = Integer.parseInt(entity2.currentVals.get(table[0]).get(0)) + result;
				String sqlString = "Update tbl_book_copies set noOfCopies = '" + addition + "' where bookId = '"
						+ entity2.Ids.get(table[0]).get(0) + "' and branchId = '" + entity.Ids.get(tables[0]).get(0)
						+ "';";
				app.AlterTableControl(libStateObj, sqlString);
				System.out.println("\n*********************************************************************************");
				System.out.println("Completed Updates Successfully!");
				System.out.println("*********************************************************************************\n");
			} catch (SQLException e) {
				System.out.println("\n################################################################################");
				System.out.println("Couldn't complete the database operation!");
				System.out.println("################################################################################\n");
			}
		}
	}
}
