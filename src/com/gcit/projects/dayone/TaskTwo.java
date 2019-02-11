/**
 * 
 */
package com.gcit.projects.dayone;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author ThankGod4Life
 *
 */
public class TaskTwo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean running = true; // loop is currently running

		Scanner userNameInput = new Scanner(System.in); // for choosing names
		Scanner userContinueDecision = new Scanner(System.in); // for choosing to continue program
		while (running) {
			int noOfPlayers = 2; // for future changes
			int oddChipNo = 0; // odd number of chips needed
			oddChipNo = choice(0, 0);

			ArrayList<TaskTwoPlayer> players = new ArrayList<TaskTwoPlayer>(noOfPlayers - 1); // array list of players
																								// number
			// create player objects
			for (int i = 0; i < noOfPlayers; i++) {
				TaskTwoPlayer player = new TaskTwoPlayer(); // player instance
				player.id = i + 1; // player's id
				players.add(player); // place in array

			}

			for (TaskTwoPlayer plyr : players) {
				boolean validName = false; // assume invalid
				while (!validName) {
					System.out.println("What is the name of player " + plyr.id + " ?");
					plyr.name = userNameInput.nextLine(); // get entire line of input from user for evaluation
					boolean validString = true; // for valid alpha strings
					for (int i = 0; (i < plyr.name.length()) && (validString); i++) {
						// iterate through each char
						if (((int) plyr.name.charAt(i) > 64 && (int) plyr.name.charAt(i) < 91)
								|| ((int) plyr.name.charAt(i) > 96 && (int) plyr.name.charAt(i) < 123)) {
							// if char passes test - that is it's in alpha ascii
							continue;
						}
						System.out.println("A value given isn't in the alphabet. Do try again.");
						validString = false; // it's not valid
					}

					for (int i = 0; (i < players.size()) && (validString); i++) {
						if (plyr.name.equalsIgnoreCase(players.get(i).name) && (plyr.id - 1) != i) {
							System.out.println("Both players can't be named " + plyr.name + ". Pick a different name.");
							validString = false; // it's not valid
						}
					}

					if (validString) {
						// all conditions checked successfully
						validName = true; // escape
					}
				}
			}
			boolean runState = true;
			int turnCounter = 0;
			int pTurn = 0; // for player's turn
			while (runState) {
				System.out.println("\n********************************\n\n");

				for (TaskTwoPlayer plyr : players) {
					System.out.println(plyr.name + " has " + plyr.score + " chips.");
				}

				System.out.println("It's your turn " + players.get(pTurn).name + ".");
				System.out.println("There are " + oddChipNo + " chips remaining.");
				int plyChoice = 0;
				if (oddChipNo > 1) {
					System.out.println("You may take any number of chips from 1 to " + ((oddChipNo) / 2) + ".");
					plyChoice = choice(1, ((oddChipNo) / 2)); // get the player's choice
				} else {
					System.out.println("You may choose the number 1.");
					plyChoice = choice(1, (oddChipNo)); // get the player's choice
				}
				players.get(pTurn).score += plyChoice; // increase individual values
				oddChipNo -= plyChoice; // reduce the values
				turnCounter++;
				pTurn = turnCounter % noOfPlayers;
				if (oddChipNo <= 0) {
					runState = false; // game is over
				}
			}

			int winner = 0;
			System.out.println("\n");
			for (TaskTwoPlayer plyr : players) {
				System.out.println(plyr.name + " has " + plyr.score + " chips.");
				if (plyr.score % 2 == 0) {
					winner = (plyr.id - 1);
				}
			}
			System.out.println(players.get(winner).name + " wins!");

			int result = choice(2, 0);
			if (result == 0) {
				running = true;
				System.out.println("\n");
			}
			if (result == 1) {
				running = false;
			}
		}

		userNameInput.close();
		userContinueDecision.close();
	}

	// class for users choice of numbers
	public static int choice(int choose, int limit) {
		int returnVal = 0;
		boolean validInput = false; // default
		Scanner userChipInput = new Scanner(System.in); // for choosing chips

		while (!validInput) {
			try {
				if (choose == 0) {
					System.out.println("How many chips does the initial pile contain? ");
					returnVal = Math.abs(userChipInput.nextInt());
					boolean conditions = (returnVal >= 3) && (returnVal % 2 > 0);
					if (conditions) {
						// if with a remainder of 1 or is a number greater or equal to 3
						validInput = true;
						continue; // loop is halted
					}
					System.out.println("Number of chips must be at least 3 and also be an odd number");
				} else if (choose == 1) {
					System.out.println("How many will you take? ");
					returnVal = Math.abs(userChipInput.nextInt());
					boolean conditions = (returnVal >= 1) && (returnVal <= limit);
					if (conditions) {
						// if with a remainder of 1 or is a number greater or equal to 3
						validInput = true;
						continue; // loop is halted
					}
					System.out.println("Illegal move: you must choose a number between the given interval");
				} else {
					System.out.println("Play another game? (y/n)");
					String check = userChipInput.next();
					if (check.equalsIgnoreCase("y")) {
						returnVal = 0;
						validInput = true;
						continue; // loop is halted
					}
					if (check.equalsIgnoreCase("n")) {
						returnVal = 1;
						validInput = true;
						continue; // loop is halted
					}
					if (!(check.equalsIgnoreCase("y")) && !(check.equalsIgnoreCase("n"))) {
						System.out.println("Please input a valid command.");
					}
				}
			} catch (InputMismatchException e) {
				// TODO: handle exception
				System.out.println("Must provide an integer value. Please try again.");
				userChipInput.nextLine(); // does exactly the statement of moving buffer to next line
			}
		}

		return returnVal;
	}

}
