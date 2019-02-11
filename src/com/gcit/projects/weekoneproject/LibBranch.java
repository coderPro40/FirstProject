/*
 *
 *9:23:46 AM
 *Dec 15, 2017
 */
package com.gcit.projects.weekoneproject;

/**
 * @author ThankGod4Life
 * @date Dec 15, 2017
 *
 */
public class LibBranch {
	public int branchID = 0;
	public String branchName = "";
	public String branchAddress = "";
	public int bookID = 0;
	public int noOfCopies = 0;
}

///*
//*
//*1:39:37 AM
//*Dec 15, 2017
//*/
//package com.gcit.projects.weekoneproject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.InputMismatchException;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
//* @author ThankGod4Life
//* @date Dec 15, 2017
//*
//*/
//public class LibraryManagementApp {
//
//	/**
//	 * @param args
//	 */
//
//	/*
//	 * https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-
//	 * by-property
//	 * 
//	 * public class CustomComparator implements Comparator<MyObject> {
//	 * 
//	 * @Override public int compare(MyObject o1, MyObject o2) { return
//	 * o1.getStartDate().compareTo(o2.getStartDate()); } }
//	 */
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		LibrarySystem libStateObj = new LibrarySystem();
//		libStateObj.setRunning(true); // initialize to true
//		libStateObj.setScanner(new Scanner(System.in));
//
//		// main loop
//		while (libStateObj.isRunning()) {
//			/*
//			 * there's a problem with instantiating java scanner object within while loop.
//			 * It keeps storing the <Enter> key as new line object. Thus will lead to a
//			 * while loop.
//			 */
//			int choice = WelcomeMessage(libStateObj);
//			if (choice == 1) {
//				// for librarian
//				LibrarianModeOne(libStateObj);
//			}
//			if (choice == 2) {
//				// for administrator
//			}
//			if (choice == 3) {
//				// for borrower
//			}
//		}
//		libStateObj.getScanner().close(); // end scanner object
//	}
//
//	private static void LibrarianModeOne(LibrarySystem libStateObj) {
//		// set to true to begin prompts for this section
//		libStateObj.setModRunning(true);
//
//		// while loop for choices
//		while (libStateObj.isModRunning()) {
//			System.out.println("Please choose from one of the options below by entering the number next to the text\n");
//			System.out.println("1) Enter Branch you manage");
//			System.out.println("2) Quit to previous menu\n");
//			int start = 1, end = 2, result = 0;
//			result = NumControl(start, end, libStateObj);
//
//			if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
//				// continue program
//				LibrarianModeTwo(libStateObj);
//				libStateObj.setModRunning(true); // to resume prompt for this section
//			}
//		}
//	}
//
//	private static void LibrarianModeTwo(LibrarySystem libStateObj) {
//		// set to true to begin prompts for this section
//		libStateObj.setModRunning(true);
//
//		// While loop for branch choices
//		while (libStateObj.isModRunning()) {
//			try {
//				// use SQL queries to retrieve possible library locations on file
//				PreparedStatement pstmt = libStateObj.getConn().prepareStatement("select * from tbl_library_branch");
//				ResultSet rs = pstmt.executeQuery();
//				HashMap<Integer, LibBranch> srchUpdMap = new HashMap<Integer, LibBranch>();
//				ArrayList<LibBranch> sortAry = new ArrayList<LibBranch>();
//				int count = 1;
//				System.out.println(
//						"Please choose from one of the options below by entering the number next to the text\n");
//
//				// use while to iterate values
//				while (rs.next()) {
//					System.out.println(rs.getInt("branchId") + ") " + rs.getString("branchName") + ", "
//							+ rs.getString("branchAddress"));
//					LibBranch libB = new LibBranch(); // instantiate attributes
//					libB.branchID = rs.getInt("branchId");
//					libB.branchName = rs.getString("branchName");
//					libB.branchAddress = rs.getString("branchAddress");
//					srchUpdMap.put(rs.getInt("branchId"), libB); // store in hash map for retrieval and update
//																	// functionality
//					sortAry.add(libB); // store in array list for quick sorting functionality
//					count++;
//				}
//				System.out.println(count + ") Quit to previous menu\n");
//				int start = 1, end = count, result = 0;
//				result = NumControl(start, end, libStateObj);
//
//				// check validity and give message
//				if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
//					// continue program
//					libStateObj.setUserChoice(result); // user's/ librarian's choice
//					LibrarianModeThree(libStateObj, sortAry, srchUpdMap);
//					libStateObj.setModRunning(true); // to resume prompt for this section
//					libStateObj.setUserChoice(0); // to resume for this section
//				}
//			} catch (SQLException e) {
//				System.out.println("SQL error occurred!");
//			}
//		}
//	}
//
//	private static void LibrarianModeThree(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
//			HashMap<Integer, LibBranch> srchUpdMap) {
//		// to begin prompts for this section
//		libStateObj.setModRunning(true);
//
//		// while loop for choices
//		while (libStateObj.isModRunning()) {
//			System.out.println("Please choose from one of the options below by entering the number next to the text\n");
//			System.out.println("1) Update the details of the Library");
//			System.out.println("2) Add copies of Book to the Branch");
//			System.out.println("3) Quit to previous\n");
//			int start = 1, end = 3, result = 0;
//			result = NumControl(start, end, libStateObj);
//
//			// check validity and give message
//			if ((result == 1) && (libStateObj.isModRunning())) {
//				// continue program
//				LibrarianModeFour(libStateObj, sortAry, srchUpdMap);
//				libStateObj.setModRunning(true); // to resume prompts for this section
//			}
//			if ((result == 2) && (libStateObj.isModRunning())) {
//				// continue program
//				LibrarianModeFive(libStateObj, srchUpdMap);
//				libStateObj.setModRunning(true); // to resume prompts for this section
//			}
//		}
//
//	}
//
//	private static void LibrarianModeFour(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
//			HashMap<Integer, LibBranch> srchUpdMap) {
//		// TODO Auto-generated method stub
//		LibBranch entity = srchUpdMap.get(libStateObj.getUserChoice());
//		System.out.println("You have chosen to update the Branch with Branch Id: " + entity.branchID
//				+ " and Branch Name: " + entity.branchName + "\n");
//		System.out.println("*** Enter 'quit' at any prompt to cancel operation ***\n");
//		System.out.println("Please enter new branch name or enter N/A for no change: \n");
//		String userEntry = StrControl(libStateObj); // string input buffer
//
//		// case where user enters quit command
//		if (!(userEntry.equals("quit"))) { // object to object needs .equal
//			System.out.println("Please enter new branch address or enter N/A for no change: \n");
//			String userEntryTwo = StrControl(libStateObj); // string input buffer
//
//			// condition for new branch data entry
//			if ((!userEntry.equals("N/A") || (!userEntryTwo.equals("N/A"))) && (!userEntryTwo.equals("quit"))) {
//				try {
//					// for storing original values on N/A
//					if (userEntry.equals("N/A")) {
//						userEntry = entity.branchName;
//					}
//					if (userEntryTwo.equals("N/A")) {
//						userEntryTwo = entity.branchAddress;
//					}
//					String sqlString = "Update tbl_library_branch set branchName = '" + userEntry
//							+ "', branchAddress = '" + userEntryTwo + "' where branchId = '" + entity.branchID + "';";
//					AlterTableControl(libStateObj, sqlString);
//					System.out.println("Completed Updates Successfully!\n");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					System.out.println("SQL error occurred!");
//				}
//			}
//		}
//	}
//
//	private static void LibrarianModeFive(LibrarySystem libStateObj, HashMap<Integer, LibBranch> srchUpdMap) {
//		// set to true to begin prompts for this section
//		libStateObj.setModRunning(true);
//		LibBranch entity = srchUpdMap.get(libStateObj.getUserChoice());
//
//		// loop for book copies section
//		while (libStateObj.isModRunning()) {
//			try {
//				// use SQL queries to retrieve possible library locations on file
//				ArrayList<LibBranch> sortAry = new ArrayList<LibBranch>();
//				HashMap<Integer, LibBranch> srchUpdMapTo = new HashMap<Integer, LibBranch>();
//				PreparedStatement pstmt = libStateObj.getConn().prepareStatement("SELECT " + "    *" + "FROM"
//						+ "    library.tbl_library_branch AS tlb" + "        left JOIN"
//						+ "    library.tbl_book_copies AS tbc ON tlb.branchId = tbc.branchId" + "        left JOIN"
//						+ "    library.tbl_book AS tb ON tbc.bookId = tb.bookId" + "        left JOIN"
//						+ "    library.tbl_book_authors AS tba ON tb.bookId = tba.bookId" + "        left JOIN"
//						+ "    library.tbl_author AS ta ON tba.authorId = ta.authorId"
//						+ " Group by tb.bookId having tlb.branchId = " + entity.branchID + " order by tb.bookId asc");
//				ResultSet rs = pstmt.executeQuery();
//				int count = 1;
//				System.out.println("Pick the Book whose copies you want to add to your Library branch\n");
//
//				// use while to iterate values
//				while (rs.next()) {
//
//					System.out.println(count + ") " + rs.getString("title") + " by " + rs.getString("authorName"));
//					LibBranch libB = new LibBranch(); // instantiate attributes
//					libB.branchID = entity.branchID;
//					libB.bookID = rs.getInt("bookId");
//					libB.noOfCopies = rs.getInt("noOfCopies");
//					libB.branchAddress = rs.getString("branchAddress");
//					srchUpdMapTo.put(rs.getInt("bookId"), libB); // store in hash map for retrieval and update
//																	// functionality
//					sortAry.add(libB); // store in array list for quick sorting functionality
//					count++;
//				}
//				int start = 1, end = count, result = 0;
//				result = NumControl(start, end, libStateObj);
//				libStateObj.setModRunning(true); // will always run until user provides valid input
//				
//				// check validity and give message
//				if ((result >= 1 && result <= end) && (libStateObj.isModRunning())) {
//					// continue program
//					libStateObj.setUserChoiceTwo(result); // user's/ librarian's choice
//					LibrarianModeSix(libStateObj, sortAry, srchUpdMapTo);
//					libStateObj.setModRunning(false); // to end prompt for this section
//				}
//			} catch (SQLException e) {
//				System.out.println("SQL error occurred!");
//			}
//		}
//	}
//
//	private static void LibrarianModeSix(LibrarySystem libStateObj, ArrayList<LibBranch> sortAry,
//			HashMap<Integer, LibBranch> srchUpdMap) {
//		// user choice - 1 on index of ArrayList to get book object and existing no of
//		// copies
//		LibBranch secondEntity = sortAry.get(libStateObj.getUserChoiceTwo() - 1);
//		System.out.println("Existing number of copies: " + secondEntity.noOfCopies);
//		System.out.println("\nEnter updated number of copies from 1 to 16: ");
//		int start = 1, end = 16, result = 0;
//		result = NumControl(start, end, libStateObj);
//
//		// check validity and give message
//		if (result >= 1 && result <= end) {
//			try {
//				String sqlString = "Update tbl_book_copies set noOfCopies = '" + result + "' where bookId = '"
//						+ secondEntity.bookID + "' and branchId = '" + secondEntity.branchID + "';";
//				AlterTableControl(libStateObj, sqlString);
//				System.out.println("Completed Updates Successfully!\n");
//			} catch (SQLException e) {
//				System.out.println("SQL error occurred!");
//			}
//		}
//	}
//
//	private static int WelcomeMessage(LibrarySystem libStateObj) {
//		// TODO Auto-generated method stub
//		System.out.println("Welcome to the GCIT Library Management System. Which category of user are you?\n");
//		System.out.println("1) Librarian");
//		System.out.println("2) Administrator");
//		System.out.println("3) Borrower\n");
//		int start = 1, end = 3, result = NumControl(start, end, libStateObj);
//
//		// check validity and give message
//		if (result < 1 || result > 3) {
//			System.out.println("You entered a value not specified in the list! Try again.\n");
//		}
//
//		return result;
//	}
//
//	private static void AlterTableControl(LibrarySystem libStateObj, String sqlString) throws SQLException {
//		// use SQL queries to update library location information on file
//		try {
//			PreparedStatement pstmt = libStateObj.getConn().prepareStatement(sqlString);
//			pstmt.executeUpdate(); // execute the statement but don't commit yet
//			libStateObj.getConn().commit(); // commit changes to the database
//		} catch (SQLException e) {
//			libStateObj.getConn().rollback();
//		}
//	}
//
//	private static String StrControl(LibrarySystem libStateObj) {
//		String userEntry = "";
//
//		// for getting user's input
//		try {
//			if (libStateObj.getScanner().hasNextLine()) {
//				// store in string
//				userEntry = libStateObj.getScanner().nextLine();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("You entered an invalid value! Try again.\n");
//			libStateObj.getScanner().nextLine();
//		} finally {
//			// TODO: handle finally clause
//			if (libStateObj.getScanner().hasNextLine()) {
//				libStateObj.getScanner().nextLine();
//			}
//		}
//		return userEntry;
//	}
//
//	private static int NumControl(int start, int end, LibrarySystem libStateObj) {
//		// TODO Auto-generated method stub
//		int choice = 0, verify = 0;
//
//		// control entry from user
//		try {
//			// if next int in range of start and stop
//			if (libStateObj.getScanner().hasNextInt(end + 1)
//					&& (verify = libStateObj.getScanner().nextInt()) > (start - 1)) {
//				choice = verify;
//			}
//		} catch (InputMismatchException | IllegalStateException e) {
//			System.out.println("You entered the wrong input type! Try again.\n");
//			libStateObj.getScanner().nextLine();
//		} finally {
//			// TODO: handle finally clause
//			if (libStateObj.getScanner().hasNextLine()) {
//				libStateObj.getScanner().nextLine();
//			}
//		}
//
//		// check validity and give message
//		if (choice < 1 || choice > end) {
//			System.out.println("You entered a value not specified in the list! Try again.\n");
//		} else if (choice == end) {
//			// return to previous screen
//			System.out.println("Returning to previous screen.\n");
//			libStateObj.setModRunning(false);
//		}
//
//		return choice;
//	}
//
//}
