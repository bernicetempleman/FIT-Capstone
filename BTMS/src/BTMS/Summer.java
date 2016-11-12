/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BTMS;

// CSE 373, Winter 2013, Marty Stepp

import BTMS.BTMS;

// A Summer is a task that can be run in a thread that computes the sum
// of a given range of numbers in a large array.

public class Summer implements Runnable {
	private int[] a;
	private int min, max;
	private int sum;
	
	public Summer(int[] a, int min, int max) {
		this.a = a;
		this.min = min;
		this.max = max;
	}
	
	public int getSum() {
		return sum;
	}
	
	public void run() {
		this.sum = BTMS.sumRange(a, min, max);
	}
}