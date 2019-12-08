package org.pesho.task.properties;

public class TimeProperties {

	public static final double DEFAULT_TIME = 1.0;
	
	private double time = DEFAULT_TIME;
	
	public TimeProperties(String timeProperty) {
		try {
			if (timeProperty != null) {
				this.time = Double.parseDouble(timeProperty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double getTime() {
		return time;
	}
	
}
