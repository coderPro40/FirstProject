package com.gcit.projects.dayone;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author ThankGod4Life
 *
 */

public class TaskOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numChosen = 0; // default chosen number
		int randNum = (int) (99 * Math.random()); // random value between 0 and 99
		Scanner userInput = new Scanner(System.in); // for retrieving users inputs
		boolean running = true; // create loop
		int gameCounter = 0; // keep track of turns
		while (running) {
			// TODO Auto-generated method stub

			boolean validUserChoice = false; // for when non-integers are given by users

			while (!validUserChoice) {
				try {
					System.out.print("Please select a number within 0 and 99: ");
					numChosen = Math.abs(userInput.nextInt()); // ensure positive value is chosen
					if (numChosen >= 0 && numChosen <= 99) {
						validUserChoice = true; // escape loop
					}
				} catch (InputMismatchException e) {
					// TODO: handle exception when false value is given
					System.out.println("\nThis isn't an integer. Please try again. \n");
					userInput.nextLine(); // move pointer of input scanner to next line
				}
			}

			if (Math.abs(numChosen - randNum) <= 10) {
				System.out.println(randNum); // output random number
				running = false; // signal to end program
				continue; // end the program
			}
			if (gameCounter >= 4) {
				System.out.println("Sorry");
				running = false; // signal to end program
				continue; // end the program
			}
			gameCounter++; // increment game counter
			System.out.println("Please keep guessing, you have " + (5 - gameCounter) + " more tries. \n"); // display
																											// int
																											// remaining
																											// tries in
																											// string
																											// format
		}
		userInput.close();
	}

}
