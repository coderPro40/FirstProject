/*
 *
 *7:05:53 AM
 *Dec 16, 2017
 */
package com.gcit.projects.weekoneproject;

import java.sql.SQLException;

/**
 * @author ThankGod4Life
 * @date Dec 16, 2017
 *
 */
public class Administrator {
	LibraryManagementApp app; // sole attribute to be used for commonly repeated methods

	public Administrator() {
		// first to execute=>create instance of LMApp
		app = new LibraryManagementApp();
	}

	public void AdministratorModeOne(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("\n*********************************************************************************");
			System.out.println("Please choose from one of the options below by entering the number next to the text:");
			System.out.println("*********************************************************************************\n");
			System.out.println("1) Add/Update/Delete Book and Author");
			System.out.println("2) Add/Update/Delete Publishers");
			System.out.println("3) Add/Update/Delete Library Branches");
			System.out.println("4) Add/Update/Delete Borrowers");
			System.out.println("5) Over-ride Due Date for a Book Loan");
			System.out.println("6) Quit to previous menu");
			int start = 1, end = 6, result = 0;
			result = app.NumControl(start, end, libStateObj);

			// check validity and give message
			if ((result == 1 || result == 2 || result == 3 || result == 4) && (libStateObj.isModRunning())) {
				// validate whether add update or delete
				libStateObj.setUserChoice(result);
				AdministratorModeTwo(libStateObj);
				libStateObj.setModRunning(true); // to resume prompts for this section
			} else if ((result == 5) && (libStateObj.isModRunning())) {
				// update record for book loan
				libStateObj.setUserChoice(result);
				libStateObj.setAdminChoice("UPDATE");
				AdministratorModeSeven(libStateObj);
				libStateObj.setModRunning(true); // to resume prompts for this section
			} else {
				libStateObj.setModRunning(false);
			}
		}
	}

	private void AdministratorModeTwo(LibrarySystem libStateObj) {
		// to change records on books and author provide choices available
		System.out.println("\n*********************************************************************************");
		System.out.println("What operation do you want to carry out?");
		System.out.println("*********************************************************************************\n");
		System.out.println("1) Add a record");
		System.out.println("2) Update a record");
		System.out.println("3) Delete a record");
		System.out.println("4) Return to previous menu");
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
		String query = "SELECT tbl_book_authors.bookId, tbl_book_authors.authorId, title, authorName "
				+ "From tbl_book_authors " + "LEFT JOIN tbl_book ON tbl_book.bookId = tbl_book_authors.bookId "
				+ "LEFT JOIN tbl_author ON tbl_book_authors.authorId = tbl_author.authorId " + "ORDER BY bookId ASC";
		String[] tables = { "tbl_book", "tbl_author" };
		String[][] columnName = { { "bookId", "Int", "tbl_book", "ID" }, { "authorId", "Int", "tbl_author", "ID" },
				{ "title", "String", "tbl_book", "Non" }, { "authorName", "String", "tbl_author", "Non" } };
		boolean show = true;

		if (libStateObj.getAdminChoice().equals("INSERT INTO")) {
			show = false;
			app.FormatData(libStateObj, query, tables, columnName, show);
			AddValues(libStateObj, tables);
		} else if (libStateObj.getAdminChoice().equals("DELETE FROM")) {
			String[] tables2 = { "tbl_book", "tbl_author", "tbl_book_authors" };
			String[][] columnName2 = { { "bookId", "Int", "tbl_book_authors", "ID" },
					{ "authorId", "Int", "tbl_book_authors", "ID" }, { "bookId", "Int", "tbl_book", "ID" },
					{ "authorId", "Int", "tbl_author", "ID" }, { "title", "String", "tbl_book", "Non" },
					{ "authorName", "String", "tbl_author", "Non" } };
			app.FormatData(libStateObj, query, tables2, columnName2, show);
		} else {
			app.FormatData(libStateObj, query, tables, columnName, show);
		}
	}

	private void AddValues(LibrarySystem libStateObj, String[] tables) {
		boolean show = false;
		libStateObj.setAdminChoice("Whatever");
		String query2 = "select * from tbl_book order by bookId asc",
				query3 = "select * from tbl_author order by authorId asc";
		String[] table = { "tbl_book" }, table2 = { "tbl_author" };
		String[][] column = { { "bookId", "Int", "tbl_book", "ID" }, { "title", "String", "tbl_book", "Non" } },
				column2 = { { "authorId", "Int", "tbl_author", "ID" },
						{ "authorName", "String", "tbl_author", "Non" } };
		app.FormatData(libStateObj, query2, table, column, show);
		int size = libStateObj.getRecordSet().size();
		int newBookId = (Integer.parseInt(libStateObj.getRecordSet().get(size).Ids.get(tables[0]).get(0)));
		app.FormatData(libStateObj, query3, table2, column2, show);
		int size2 = libStateObj.getRecordSet().size();
		int newAuthorId = (Integer.parseInt(libStateObj.getRecordSet().get(size2).Ids.get(tables[1]).get(0)));
		String sqlString = "Insert into tbl_book_authors (bookId, authorId) Values ('" + newBookId + "', '"
				+ newAuthorId + "');";
		try {
			app.AlterTableControl(libStateObj, sqlString);
		} catch (SQLException e) {
			System.out.println("\n################################################################################");
			System.out.println("Couldn't complete the database operation!");
			System.out.println("################################################################################\n");
		}
		libStateObj.setAdminChoice("INSERT INTO");
	}

	private void AdministratorModeFour(LibrarySystem libStateObj) {
		// deal with publisher
		String query = "SELECT * from tbl_publisher ORDER BY publisherId ASC";
		String[] tables = { "tbl_publisher" };
		String[][] columnName = { { "publisherId", "Int", "tbl_publisher", "ID" },
				{ "publisherName", "String", "tbl_publisher", "Non" },
				{ "publisherAddress", "String", "tbl_publisher", "Non" },
				{ "publisherPhone", "String", "tbl_publisher", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
	}

	private void AdministratorModeFive(LibrarySystem libStateObj) {
		// deal with library branch
		String query = "SELECT * from tbl_library_branch ORDER BY branchId ASC";
		String[] tables = { "tbl_library_branch" };
		String[][] columnName = { { "branchId", "Int", "tbl_library_branch", "ID" },
				{ "branchName", "String", "tbl_library_branch", "Non" },
				{ "branchAddress", "String", "tbl_library_branch", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
	}

	private void AdministratorModeSix(LibrarySystem libStateObj) {
		// deal with borrower
		String query = "SELECT * from tbl_borrower ORDER BY cardNo ASC";
		String[] tables = { "tbl_borrower" };
		String[][] columnName = { { "cardNo", "Int", "tbl_borrower", "ID" },
				{ "name", "String", "tbl_borrower", "Non" }, { "address", "String", "tbl_borrower", "Non" },
				{ "phone", "String", "tbl_borrower", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
	}

	private void AdministratorModeSeven(LibrarySystem libStateObj) {
		// updates to due date on book loan
		String query = "SELECT * from tbl_book_loans ORDER BY bookId ASC";
		String[] tables = { "tbl_book_loans" };
		String[][] columnName = { { "bookId", "Int", "tbl_book_loans", "ID" },
				{ "branchId", "Int", "tbl_book_loans", "ID" }, { "cardNo", "Int", "tbl_book_loans", "ID" },
				{ "dueDate", "String", "tbl_book_loans", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
	}
}
