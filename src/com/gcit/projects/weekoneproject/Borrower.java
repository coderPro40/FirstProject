/*
 *
 *5:51:27 AM
 *Dec 17, 2017
 */
package com.gcit.projects.weekoneproject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author ThankGod4Life
 * @date Dec 17, 2017
 *
 */
public class Borrower {
	LibraryManagementApp app;

	public Borrower() {
		// first to execute=>create instance of LMApp
		app = new LibraryManagementApp();
	}

	public void BorrowerModeOne(LibrarySystem libStateObj) {
		// get card no from user using prompt
		libStateObj.setModRunning(true);
		libStateObj.setAdminChoice("Null");
		String query = "SELECT * from tbl_borrower ORDER BY cardNo ASC";
		String[] tables = { "tbl_borrower" };
		String[][] columnName = { { "cardNo", "Int", "tbl_borrower", "ID" },
				{ "name", "String", "tbl_borrower", "Non" }, { "address", "String", "tbl_borrower", "Non" },
				{ "phone", "String", "tbl_borrower", "Non" } };
		boolean show = false;
		app.FormatData(libStateObj, query, tables, columnName, show);

		// loop for entry
		while (libStateObj.isModRunning()) {
			// get string input
			System.out.println("\n*********************************************************************************");
			System.out.println("Enter your Card Number to continue or type 'quit' to end:");
			System.out.println("*********************************************************************************\n");
			String userEntry = app.StrControl(libStateObj);
			boolean validEntry = false;

			for (int i = 1; i <= libStateObj.getRecordSet().size(); i++) {
				// validate the user's entry
				if (userEntry.equals(libStateObj.getRecordSet().get(i).Ids.get(tables[0]).get(0))) {
					validEntry = true;
				}
			}

			if (userEntry.equals("quit")) {
				libStateObj.setModRunning(false);
				continue;
			}

			if (validEntry) {
				libStateObj.setUserEntry(userEntry);
				BorrowerModeTwo(libStateObj);
				libStateObj.setModRunning(false);
				continue; // return to menu
			}
			
			System.out.println("\n################################################################################");
			System.out.println("You entered an invalid number! Please try again.");
			System.out.println("################################################################################\n");
		}
	}

	private void BorrowerModeTwo(LibrarySystem libStateObj) {
		// continue program by providing options for users
		libStateObj.setModRunning(true);

		// authenticate user's choice
		while (libStateObj.isModRunning()) {
			System.out.println("\n*********************************************************************************");
			System.out.println("Please select from one of the following options");
			System.out.println("*********************************************************************************\n");
			System.out.println("1) Checkout a book");
			System.out.println("2) Return a book");
			System.out.println("3) Quit to previous");
			int start = 1, end = 3, result = 0;
			result = app.NumControl(start, end, libStateObj);

			if (result == 1) {
				// check out book
				String action = "checking";
				BorrowerModeThree(libStateObj, action);
				libStateObj.setModRunning(true);
				continue;
			}

			if (result == 2) {
				// return book
				String action = "returning";
				BorrowerModeThree(libStateObj, action);
				libStateObj.setModRunning(true);
				continue;
			}

			if (result == 3) {
				// return to previous menu
				libStateObj.setModRunning(false);
				continue;
			}

		}
	}

	private void BorrowerModeThree(LibrarySystem libStateObj, String action) {
		// for borrowing books
		System.out.println("\n*********************************************************************************");
		System.out.println("Pick which branch you want to check out from:");
		System.out.println("*********************************************************************************\n");
		libStateObj.setAdminChoice("Null");
		String query = "SELECT * from tbl_library_branch ORDER BY branchId ASC";
		String[] tables = { "tbl_library_branch" };
		String[][] columnName = { { "branchId", "Int", "tbl_library_branch", "ID" },
				{ "branchName", "String", "tbl_library_branch", "Non" },
				{ "branchAddress", "String", "tbl_library_branch", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
		int lSize = libStateObj.getRecordSet().size() + 1;
		System.out.println(lSize + ")  |               | Quit to previous menu");
		int start = 1, end = lSize, result = 0;
		boolean running = true;

		// authenticate user's choice
		while (running) {
			result = app.NumControl(start, end, libStateObj);

			if (result >= 1 && result < lSize) {
				// check out book
				String branchId = libStateObj.getRecordSet().get(result).Ids.get(tables[0]).get(0);
				libStateObj.setUserChoice(Integer.parseInt(branchId));
				BorrowerModeFour(libStateObj, action);
				running = false;
				continue;
			}

			if (result >= lSize) {
				// return to previous menu
				running = false;
				continue;
			}

		}
	}

	private void BorrowerModeFour(LibrarySystem libStateObj, String action) {
		// select book for user
		System.out.println("\n*********************************************************************************");
		System.out.println("Pick the book you want to check out:");
		System.out.println("*********************************************************************************\n");
		String query = "SELECT " + " * " + "FROM" + "    library.tbl_library_branch AS tlb" + "        left JOIN"
				+ "    library.tbl_book_copies AS tbc ON tlb.branchId = tbc.branchId" + "        left JOIN"
				+ "    library.tbl_book AS tb ON tbc.bookId = tb.bookId" + "        left JOIN"
				+ "    library.tbl_book_authors AS tba ON tb.bookId = tba.bookId" + "        left JOIN"
				+ "    library.tbl_author AS ta ON tba.authorId = ta.authorId"
				+ " Group by tb.bookId having tlb.branchId = " + libStateObj.getUserChoice() + " and tbc.noOfCopies > 0"
				+ " order by tbc.bookId asc";
		libStateObj.setAdminChoice("Null");
		String[] tables = { "tbl_book", "tbl_author", "tbl_book_copies" };
		String[][] columnName = { { "bookId", "Int", "tbl_book", "ID" },
				{ "noOfCopies", "Int", "tbl_book_copies", "ID" }, { "title", "String", "tbl_book", "Non" },
				{ "authorName", "String", "tbl_author", "Non" } };
		boolean show = true;
		app.FormatData(libStateObj, query, tables, columnName, show);
		int lSize = libStateObj.getRecordSet().size() + 1;
		System.out.println(lSize + ")  |               | Quit to cancel operation");
		int start = 1, end = lSize, result = 0;
		boolean running = true;

		// authenticate user's choice
		while (running) {
			result = app.NumControl(start, end, libStateObj);
			if (result >= 1 && result < lSize) {
				// check out book
				String bookId = libStateObj.getRecordSet().get(result).Ids.get(tables[0]).get(0);
				Integer copiesNo = Integer.parseInt(libStateObj.getRecordSet().get(result).Ids.get(tables[2]).get(0));
				libStateObj.setUserChoiceTwo(Integer.parseInt(bookId));
				BorrowerModeFive(libStateObj, action, copiesNo);
				running = false;
				continue;
			}

			if (result >= lSize) {
				// return to previous menu
				running = false;
				continue;
			}

		}
	}

	private void BorrowerModeFive(LibrarySystem libStateObj, String action, Integer copiesNo) {
		// complete current transaction
		String sqlString = "", sqlString2 = "";
		if (action.equals("checking")) {
			LocalDate now = LocalDate.now();
			copiesNo -= 1;
			LocalDate dueDate = now.plus(1, ChronoUnit.WEEKS);
			sqlString = "Insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES ('"
					+ libStateObj.getUserChoiceTwo() + "', '" + libStateObj.getUserChoice() + "', '"
					+ libStateObj.getUserEntry() + "', '" + now + "', '" + dueDate + "');";
			sqlString2 = "Update tbl_book_copies set noOfCopies = '" + copiesNo + "' where bookId = '"
					+ libStateObj.getUserChoiceTwo() + "' and branchId = '" + libStateObj.getUserChoice() + "';";
		} else {
			LocalDate now = LocalDate.now();
			// could check if the date in already contains a value so you can skip
			copiesNo += 1;
			sqlString = "Update tbl_book_loans set dateIn = '" + now + "' where bookId = '"
					+ libStateObj.getUserChoiceTwo() + "' and branchId = '" + libStateObj.getUserChoice()
					+ "' and cardNo = '" + libStateObj.getUserEntry() + "';";
			sqlString2 = "Update tbl_book_copies set noOfCopies = '" + copiesNo + "' where bookId = '"
					+ libStateObj.getUserChoiceTwo() + "' and branchId = '" + libStateObj.getUserChoice() + "';";
		}
		
		try {
			libStateObj.setTransactionWin(true);
			app.AlterTableControl(libStateObj, sqlString);
			if (libStateObj.isTransactionWin()) {
				app.AlterTableControl(libStateObj, sqlString2);
				libStateObj.setTransactionWin(true);
				System.out.println("\n*********************************************************************************");
				System.out.println("Completed the Transaction Successfully!");
				System.out.println("*********************************************************************************\n");
			}
		} catch (SQLException e) {
			System.out.println("\n################################################################################");
			System.out.println("Couldn't complete Database operation!");
			System.out.println("################################################################################\n");
		}
	}
}
