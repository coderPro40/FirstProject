/*
 *
 *2:18:01 AM
 *Dec 13, 2017
 */
package com.gcit.projects.daytwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ThankGod4Life
 * @date Dec 13, 2017
 *
 */
public class Practice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// create student class to be used throughout program
		ArrayList<Student> students = new ArrayList<Student>();
		students = getFileData();
	}
	
	public static void mergeAndFloat() {
		
	}
	
	public static ArrayList<Student> getFileData() {
		String path = "C:\\Users\\ThankGod4Life\\OneDrive\\GCIT-Solutions\\DayTwo\\info.txt";	// file path
		ArrayList<Student> students = new ArrayList<>();	// for student objects
		String line = "";									// for file lines
		
		try {
			FileReader file = new FileReader(path);			// follow file name
			BufferedReader buff = new BufferedReader(file);	// act as scanner for file
			while ((line = buff.readLine()) != null) {
				Student stud = new Student();
				String[] values = new String[2];
				values = line.split(" ");
				
				// store values from file in student class and store in array
				stud.name = values[0];
				stud.mark = Integer.parseInt(values[1]);
				students.add(stud);
				
				//System.out.println(stud.name +" "+ stud.mark);
			}
			
			// close buffer reader
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("Unable to open file.");
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error reading file.");
		}
		return students;
	}

}
