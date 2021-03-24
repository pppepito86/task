package org.pesho.task.scoring;

public class Scoring {
	
	public static final Scoring DEFAULT = new Scoring(100);

	public enum TYPE {
		MIN,
		MIN_FAST,
		SUM
	}
	
	private int points;
	private int precision;
	private TYPE type;
	
	public Scoring(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
