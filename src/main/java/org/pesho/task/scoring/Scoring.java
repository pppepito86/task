package org.pesho.task.scoring;

public class Scoring {
	
	public static final Scoring DEFAULT = new Scoring(100);

	private int points;
	
	public Scoring(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
