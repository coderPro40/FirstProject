/*
 *
 *12:51:13 AM
 *Dec 13, 2017
 */
package com.gcit.projects.daytwo;

import java.util.*;

/**
 * @author ThankGod4Life
 * @date Dec 13, 2017
 *
 */
public class QuestionOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 1: Hash instances are stored in a way that offers no guaranteed ordering of
		// sets

		// Create a set called mySet
		Set mySet = new HashSet();
		// Ensure that this set contains an interesting selection of fruit
		String fruit1 = "pear", fruit2 = "banana", fruit3 = "tangerine", fruit4 = "strawberry", fruit5 = "blackberry";
		mySet.add(fruit1);
		mySet.add(fruit2);
		mySet.add(fruit3);
		mySet.add(fruit2);
		mySet.add(fruit4);
		mySet.add(fruit5);

		// 2: Cardinality
		System.out.println(mySet.size());
		// 3: remove blackberry & strawberry
		mySet.remove("blackberry");
		mySet.remove("strawberry");
		System.out.println(mySet);
		// 4: remove remaining fruits
		mySet.clear();
		// 5: show set is now empty
		System.out.println(mySet.isEmpty());
	}

}
