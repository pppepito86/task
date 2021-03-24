package org.pesho.task.tests;

import org.pesho.task.properties.TaskProperties;

public class TaskConstraints {

	public static final TaskConstraints DEFAULT = new TaskConstraints(1, 256);
	
	private double time;
	private int memory;
	private int rejudgeTimes;
	
	public TaskConstraints(double time, int memory) {
		this.time = time;
		this.memory = memory;
	}
	
	public TaskConstraints(TaskProperties props) {
		this.time = props.getTimeProperties().getTime();
		this.memory = props.getMemoryProperties().getMemory();
	}
	
	public double getTime() {
		return time;
	}
	
	public int getMemory() {
		return memory;
	}

	@Override
	public String toString() {
		return "TaskConstraints [time=" + time + ", memory=" + memory + "]";
	}
	
}
