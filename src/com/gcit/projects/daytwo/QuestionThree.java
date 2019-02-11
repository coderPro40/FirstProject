/*
 *
 *2:18:01 AM
 *Dec 13, 2017
 */
package com.gcit.projects.daytwo;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ThankGod4Life
 * @date Dec 13, 2017
 *
 */
public class QuestionThree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// create student class to be used throughout program
		getFileData();
	}

	public static void getFileData() {
		String path = "C:\\Users\\ThankGod4Life\\OneDrive\\GCIT-Solutions\\DayTwo\\info.txt"; // file path
		HashMap<String, Integer> student = new HashMap<String, Integer>();
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		String line = ""; // for file lines

		try {
			FileReader file = new FileReader(path); // follow file name
			BufferedReader buff = new BufferedReader(file); // act as scanner for file
			while ((line = buff.readLine()) != null) {
				String[] values = new String[2];
				values = line.split(" ");

				if (student.containsKey(values[0])) { // key has been encountered multiple times
					values[1] = String.valueOf(Integer.parseInt(values[1]) + student.get(values[0]));
					count.put(values[0], (count.get(values[0]) + 1)); // increment current count of that name
				} else {
					count.put(values[0], 1); // first time key is encountered
				}
				student.put(values[0], Integer.parseInt(values[1]));
			}

			// close buffer reader
			// System.out.println(student + " " + count);
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("Unable to open file.");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error reading file.");
		}
		mergeAndFloat(student, count); // next section
	}

	public static void mergeAndFloat(HashMap<String, Integer> student, HashMap<String, Integer> count) {
		// while loop to get both ranking and floating one dp average, and Iterator also
		Iterator<Map.Entry<String, Integer>> iter = student.entrySet().iterator();
		Iterator<Map.Entry<String, Integer>> itra = count.entrySet().iterator();
		HashMap<String, Float> average = new HashMap<String, Float>();
		HashMap<Float, Integer> rank = new HashMap<Float, Integer>();
		while (itra.hasNext()) {
			Map.Entry<String, Integer> countr = itra.next();
			Map.Entry<String, Integer> studnt = iter.next();
			float avg = (float) studnt.getValue() / countr.getValue();
			String name = studnt.getKey(); // store name of student

			// convert to floating point average of 1 dp
			float mark = Float.parseFloat(new DecimalFormat("0.0").format(avg));
			average.put(name, mark);
			// for now set the rank to 0
			rank.put(mark, 0);
		}

		// System.out.println(average);
		displayAlphaAndNum(student, count, average, rank); // display alpha keys
		rankAndDeviate(student, count, average, rank); // next section
	}

	public static void displayAlphaAndNum(HashMap<String, Integer> student, HashMap<String, Integer> count,
			HashMap<String, Float> average, HashMap<Float, Integer> rank) {
		if (rank.containsValue(0)) {
			Map<String, Integer> nStudent = new TreeMap<String, Integer>(student); // sort key values
			Map<String, Integer> nCount = new TreeMap<String, Integer>(count); // sort key values
			Map<String, Float> nAverage = new TreeMap<String, Float>(average); // sort key values
			Iterator<Map.Entry<String, Integer>> itro = nStudent.entrySet().iterator(); // Iterate sorted values
			Iterator<Map.Entry<String, Integer>> itri = nCount.entrySet().iterator(); // Iterate sorted values
			Iterator<Map.Entry<String, Float>> itie = nAverage.entrySet().iterator(); // Iterate sorted values

			System.out.println("\n. Alpha order");
			while (itro.hasNext()) {
				Map.Entry<String, Integer> nStudnt = itro.next();
				Map.Entry<String, Integer> nCont = itri.next();
				Map.Entry<String, Float> nAvrg = itie.next();
				System.out.println(". " + nStudnt.getKey() + " " + nCont.getValue() + " " + nAvrg.getValue());
			}
			System.out.println("\n");
		} else {
			Map<String, Integer> nCount = sortByValues(count, rank); // sort values
			Map<String, Float> nAverage = sortByValues(average, rank); // sort values
		}

	}

	private static HashMap sortByValues(HashMap non, HashMap trend) {
		// TODO Auto-generated method stub

		return null;
	}

	public static void rankAndDeviate(HashMap<String, Integer> student, HashMap<String, Integer> count,
			HashMap<String, Float> average, HashMap<Float, Integer> rank) {
		// sort floats and then realign to get sorted values and ranks
		Map<Float, Integer> nRank = new TreeMap<Float, Integer>(rank); // sort hashmap by values to get ranks
		Iterator<Map.Entry<Float, Integer>> itro = nRank.entrySet().iterator();
		int least = (average.size() + 1); // lowest rank
		int curCount = (average.size() + 1);
		float prevAvg = 0;
		float total = 0;
		while (itro.hasNext()) {
			Map.Entry<Float, Integer> nRnk = itro.next();
			total = Float.parseFloat(new DecimalFormat("0.0").format(total + nRnk.getKey()));
			float curAvg = nRnk.getKey();
			curCount -= 1; // keeps going till done
			least = (prevAvg == curAvg) ? least : curCount;
			prevAvg = curAvg; // update the checker
			rank.put(nRnk.getKey(), least); // update original map
		}
		// System.out.println(rank);
		// sort by key numerically
		float avgTot = Float.parseFloat(new DecimalFormat("0.0").format(total / student.size()));
		displayAlphaAndNum(student, count, average, rank);
		displayStudNoAndAvg(student, count, average, rank, avgTot);
	}

	public static void displayStudNoAndAvg(HashMap<String, Integer> student, HashMap<String, Integer> count,
			HashMap<String, Float> average, HashMap<Float, Integer> rank, float avgTot) {
		System.out.println("\n");
		System.out.println("Number of students: " + student.size());
		System.out.println("Average of student mark: " + avgTot);
		System.out.println("\n");
	}
}
