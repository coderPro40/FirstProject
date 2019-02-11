/*
 *
 *1:05:34 AM
 *Dec 13, 2017
 */
package com.gcit.projects.daytwo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author ThankGod4Life
 * @date Dec 13, 2017
 *
 */
public class QuestionTwo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// instantiations
		ArrayList<String> myValues = new ArrayList<String>();
		myValues.add("pear"); myValues.add("banana"); myValues.add("tangerine"); myValues.add("strawberry");
		myValues.add("blackberry");
		Iterator<String> iter = myValues.iterator();
		
		// loop
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		// instantiation
		ListIterator<String> it = myValues.listIterator(myValues.size());
		
		// loop
		while (it.hasPrevious()) {
			System.out.println(it.previous());
		}
		
		// instantiation
		ListIterator<String> ito = myValues.listIterator(myValues.size());
		
		// loop
		while (ito.hasPrevious()) {
			String val = ito.previous();
			if(val == "strawberry") {ito.add("banana");}
			System.out.println(val);
		}
	}

}
