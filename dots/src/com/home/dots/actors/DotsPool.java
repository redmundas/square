package com.home.dots.actors;

import java.util.LinkedList;
import java.util.Queue;

public class DotsPool {
	
	private Queue<Dot> pool = new LinkedList<Dot>();
	
	public DotsPool(int size) {
		for(int i = 0; i < size; i++) {
			pool.add(new Dot());
		}
	}
	
	public Dot borrowDot() {
		return pool.poll();
	}
	
	public void returnDot(Dot dot) {
		pool.add(dot);
	}
}
