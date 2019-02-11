/*
 *
 *5:47:11 AM
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
public class LibrarianOld {
	LibraryManagementApp app; // sole attribute to be used for commonly repeated methods

	public LibrarianOld() {
		// first to execute=>create instance of LMApp
		app = new LibraryManagementApp();
	}

	public void LibrarianModeOne(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("Please choose from one of the options below by entering the number next to the text\n");
			System.out.println("1) Enter Branch you manage");
			System.out.println("2) Quit to previous menu\n");
			int start = 1, end = 2, result = 0;
			result = app.NumControl(start, end, libStateObj);

			if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
				// continue program
				LibrarianModeTwo(libStateObj);
				libStateObj.setModRunning(true); // to resume prompt for this section
			}
		}
	}

	private void LibrarianModeTwo(LibrarySystem libStateObj) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);

		// While loop for branch choices
		while (libStateObj.isModRunning()) {
			try {
				// use SQL queries to retrieve possible library locations on file
				PreparedStatement pstmt = libStateObj.getConn().prepareStatement("select * from tbl_library_branch");
				ResultSet rs = pstmt.executeQuery();
				HashMap<Integer, LibBranch> srchUpdMap = new HashMap<Integer, LibBranch>();
				ArrayList<LibBranch> sortAry = new ArrayList<LibBranch>();
				int count = 1;
				System.out.println(
						"Please choose from one of the options below by entering the number next to the text\n");

				// use while to iterate values
				while (rs.next()) {
					System.out.println(rs.getInt("branchId") + ") " + rs.getString("branchName") + ", "
							+ rs.getString("branchAddress"));
					LibBranch libB = new LibBranch(); // instantiate attributes
					libB.branchID = rs.getInt("branchId");
					libB.branchName = rs.getString("branchName");
					libB.branchAddress = rs.getString("branchAddress");
					srchUpdMap.put(rs.getInt("branchId"), libB); // store in hash map for retrieval and update
																	// functionality
					sortAry.add(libB); // store in array list for quick sorting functionality
					count++;
				}
				System.out.println(count + ") Quit to previous menu\n");
				int start = 1, end = count, result = 0;
				result = app.NumControl(start, end, libStateObj);

				// check validity and give message
				if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
					// continue program
					libStateObj.setUserChoice(result); // user's/ librarian's choice
					LibrarianModeThree(libStateObj, sortAry, srchUpdMap);
					libStateObj.setModRunning(true); // to resume prompt for this section
					libStateObj.setUserChoice(0); // to resume for this section
				}
			} catch (SQLException e) {
				System.out.println("SQL error occurred!");
			}
		}
	}

	private void LibrarianModeThree(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
			HashMap<Integer, LibBranch> srchUpdMap) {
		// to begin prompts for this section
		libStateObj.setModRunning(true);

		// while loop for choices
		while (libStateObj.isModRunning()) {
			System.out.println("Please choose from one of the options below by entering the number next to the text\n");
			System.out.println("1) Update the details of the Library");
			System.out.println("2) Add copies of Book to the Branch");
			System.out.println("3) Quit to previous\n");
			int start = 1, end = 3, result = 0;
			result = app.NumControl(start, end, libStateObj);

			// check validity and give message
			if ((result == 1) && (libStateObj.isModRunning())) {
				// continue program
				LibrarianModeFour(libStateObj, sortAry, srchUpdMap);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}
			if ((result == 2) && (libStateObj.isModRunning())) {
				// continue program
				LibrarianModeFive(libStateObj, srchUpdMap);
				libStateObj.setModRunning(true); // to resume prompts for this section
			}
		}

	}

	private void LibrarianModeFour(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
			HashMap<Integer, LibBranch> srchUpdMap) {
		// objects and variable instantiations for name entry
		LibBranch entity = srchUpdMap.get(libStateObj.getUserChoice());
		System.out.println("You have chosen to update the Branch with Branch Id: " + entity.branchID
				+ " and Branch Name: " + entity.branchName + "\n");
		System.out.println("*** Enter 'quit' at any prompt to cancel operation ***\n");
		System.out.println("Please enter new branch name or enter N/A for no change: \n");
		String userEntry = app.StrControl(libStateObj); // string input buffer

		// case where user enters quit command
		if (!(userEntry.equals("quit"))) { // object to object needs .equal
			System.out.println("Please enter new branch address or enter N/A for no change: \n");
			String userEntryTwo = app.StrControl(libStateObj); // string input buffer

			// condition for new branch data entry
			if ((!userEntry.equals("N/A") || (!userEntryTwo.equals("N/A"))) && (!userEntryTwo.equals("quit"))) {
				try {
					// for storing original values on N/A
					if (userEntry.equals("N/A")) {
						userEntry = entity.branchName;
					}
					if (userEntryTwo.equals("N/A")) {
						userEntryTwo = entity.branchAddress;
					}
					String sqlString = "Update tbl_library_branch set branchName = '" + userEntry
							+ "', branchAddress = '" + userEntryTwo + "' where branchId = '" + entity.branchID + "';";
					app.AlterTableControl(libStateObj, sqlString);
					System.out.println("Completed Updates Successfully!\n");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("SQL error occurred!");
				}
			}
		}
	}

	private void LibrarianModeFive(LibrarySystem libStateObj, HashMap<Integer, LibBranch> srchUpdMap) {
		// set to true to begin prompts for this section
		libStateObj.setModRunning(true);
		LibBranch entity = srchUpdMap.get(libStateObj.getUserChoice());

		// loop for book copies section
		while (libStateObj.isModRunning()) {
			try {
				// use SQL queries to retrieve possible library locations on file
				ArrayList<LibBranch> sortAry = new ArrayList<LibBranch>();
				HashMap<Integer, LibBranch> srchUpdMapTo = new HashMap<Integer, LibBranch>();
				PreparedStatement pstmt = libStateObj.getConn().prepareStatement("SELECT " + "    *" + "FROM"
						+ "    library.tbl_library_branch AS tlb" + "        left JOIN"
						+ "    library.tbl_book_copies AS tbc ON tlb.branchId = tbc.branchId" + "        left JOIN"
						+ "    library.tbl_book AS tb ON tbc.bookId = tb.bookId" + "        left JOIN"
						+ "    library.tbl_book_authors AS tba ON tb.bookId = tba.bookId" + "        left JOIN"
						+ "    library.tbl_author AS ta ON tba.authorId = ta.authorId"
						+ " Group by tb.bookId having tlb.branchId = " + entity.branchID + " order by tb.bookId asc");
				ResultSet rs = pstmt.executeQuery();
				int count = 1;
				System.out.println("Pick the Book whose copies you want to add to your Library branch\n");

				// use while to iterate values
				while (rs.next()) {

					System.out.println(count + ") " + rs.getString("title") + " by " + rs.getString("authorName"));
					LibBranch libB = new LibBranch(); // instantiate attributes
					libB.branchID = entity.branchID;
					libB.bookID = rs.getInt("bookId");
					libB.noOfCopies = rs.getInt("noOfCopies");
					libB.branchAddress = rs.getString("branchAddress");
					srchUpdMapTo.put(rs.getInt("bookId"), libB); // store in hash map for retrieval and update
																	// functionality
					sortAry.add(libB); // store in array list for quick sorting functionality
					count++;
				}
				int start = 1, end = count, result = 0;

				// semantic condition
				if (count > 1) {
					result = app.NumControl(start, end, libStateObj);
					libStateObj.setModRunning(true); // will always run until user provides valid input
				} else {
					// System.out.println("There are no books in this branch of library! Please
					// consider another one.\n");
					libStateObj.setModRunning(false); // handle condition of no books in library branch
				}

				// check validity and give message
				if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
					// continue program
					libStateObj.setUserChoiceTwo(result); // user's/ librarian's choice
					LibrarianModeSix(libStateObj, sortAry, srchUpdMapTo);
					libStateObj.setModRunning(false); // to end prompt for this section
				}
			} catch (SQLException e) {
				System.out.println("SQL error occurred!");
			}
		}
	}

	private void LibrarianModeSix(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
			HashMap<Integer, LibBranch> srchUpdMap) {
		// user choice - 1 on index of ArrayList to get book object and existing no of
		// copies
		LibBranch secondEntity = sortAry.get(libStateObj.getUserChoiceTwo() - 1);
		System.out.println("Existing number of copies: " + secondEntity.noOfCopies);
		System.out.println("\nEnter updated number of copies from 1 to 16: ");
		int start = 1, end = 16, result = 0;
		result = app.NumControl(start, end, libStateObj);

		// check validity and give message
		if (result >= 1 && result <= end) {
			try {
				String sqlString = "Update tbl_book_copies set noOfCopies = '" + result + "' where bookId = '"
						+ secondEntity.bookID + "' and branchId = '" + secondEntity.branchID + "';";
				app.AlterTableControl(libStateObj, sqlString);
				System.out.println("Completed Updates Successfully!\n");
			} catch (SQLException e) {
				System.out.println("SQL error occurred!");
			}
		}
	}
}
