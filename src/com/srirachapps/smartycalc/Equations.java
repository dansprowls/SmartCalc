package com.srirachapps.smartycalc;

import java.lang.Math;

public class Equations {

	public double add(double x, double y) {
		return x+y;
	}
	
	public double subtract(double x, double y) {
		return x-y;
	}
	
	public double multiply(double x, double y) {
		return x*y;
	}
	
	public double divide(double x, double y) {
		return x/y;
	}
	
	public double sqrt(double x) {
		return Math.sqrt(x);
	}
	
	public double squared(double x) {
		return Math.pow(x, 2);
	}
	
	public double percent(double x) {
		// TODO: Correct the return function.
		return x/100;
	}
	
	public double recip(double x) {
		return 1/x;
	}
	
	public double negate(double x) {
		if(x != 0)
			return -x;
		else 
			return x;
	}
}

