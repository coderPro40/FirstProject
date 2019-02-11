/*
 *
 *1:46:37 AM
 *Dec 15, 2017
 */
package com.gcit.projects.weekoneproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author ThankGod4Life
 * @date Dec 15, 2017
 *
 */
public class LibrarySystem {
	private boolean running = false;
	private boolean modRunning = true;
	private int userChoice = 0;
	private boolean transactionWin = true;
	private HashMap<Integer, AdminObj> recordSet;
	private String[] chosenTable;
	private int userChoiceTwo = 0;
	private String userEntry = "";
	private String adminChoice = "";
	private Scanner scanner;
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost/library?useSSL=true";
	private final String username = "root";
	private final String password = "SoccerFootballer18";
	private Connection conn = null;

	public LibrarySystem() {
		try {
			// variables
			Class.forName(driver).newInstance(); // 1. Register Drivers
			setConn(DriverManager.getConnection(url, username, password)); // 2. Create connection
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @return the scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}

	/**
	 * @param scanner
	 *            the scanner to set
	 */
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		try {
			this.conn = conn;
			this.conn.setAutoCommit(Boolean.FALSE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the modRunning
	 */
	public boolean isModRunning() {
		return modRunning;
	}

	/**
	 * @param modRunning the modRunning to set
	 */
	public void setModRunning(boolean modRunning) {
		this.modRunning = modRunning;
	}

	/**
	 * @return the userChoice
	 */
	public int getUserChoice() {
		return userChoice;
	}

	/**
	 * @param userChoice the userChoice to set
	 */
	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}

	/**
	 * @return the userChoiceTwo
	 */
	public int getUserChoiceTwo() {
		return userChoiceTwo;
	}

	/**
	 * @param userChoiceTwo the userChoiceTwo to set
	 */
	public void setUserChoiceTwo(int userChoiceTwo) {
		this.userChoiceTwo = userChoiceTwo;
	}

	/**
	 * @return the adminChoice
	 */
	public String getAdminChoice() {
		return adminChoice;
	}

	/**
	 * @param adminChoice the adminChoice to set
	 */
	public void setAdminChoice(String adminChoice) {
		this.adminChoice = adminChoice;
	}

	/**
	 * @return the recordSet
	 */
	public HashMap<Integer, AdminObj> getRecordSet() {
		return recordSet;
	}

	/**
	 * @param recordSet the recordSet to set
	 */
	public void setRecordSet(HashMap<Integer, AdminObj> recordSet) {
		this.recordSet = recordSet;
	}

	/**
	 * @return the chosenTable
	 */
	public String[] getChosenTable() {
		return chosenTable;
	}

	/**
	 * @param chosenTable the chosenTable to set
	 */
	public void setChosenTable(String[] chosenTable) {
		this.chosenTable = chosenTable;
	}

	/**
	 * @return the userEntry
	 */
	public String getUserEntry() {
		return userEntry;
	}

	/**
	 * @param userEntry the userEntry to set
	 */
	public void setUserEntry(String userEntry) {
		this.userEntry = userEntry;
	}

	/**
	 * @return the transactionWin
	 */
	public boolean isTransactionWin() {
		return transactionWin;
	}

	/**
	 * @param transactionWin the transactionWin to set
	 */
	public void setTransactionWin(boolean transactionWin) {
		this.transactionWin = transactionWin;
	}

	/**
	 * @return the dtf
	 */
}
