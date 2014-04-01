package com.home.dots.utils;

public class MathUtils {
	
	public static int random(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

}
