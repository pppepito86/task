package org.pesho.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.pesho.task.checker.TaskChecker;
import org.pesho.task.description.TaskDescription;
import org.pesho.task.grader.TaskGrader;
import org.pesho.task.scoring.Scoring;
import org.pesho.task.solutions.TaskSolutions;
import org.pesho.task.tests.TaskConstraints;
import org.pesho.task.tests.TestGroup;

public class Task {
	
	private String name;
	private TaskDescription description;
	private TaskConstraints constraints;
	private List<TestGroup> groups;
	private Optional<TaskGrader> grader;
	private Optional<TaskChecker> checker;
	private Scoring scoring;
	private TaskSolutions solutions;
	
	private Task(TaskBuilder builder) {
		this.name = builder.name;
		this.description = builder.description;
		this.constraints = builder.constraints;
		this.groups = builder.groups;
		this.grader = builder.grader;
		this.checker = builder.checker;
		this.scoring = builder.scoring;
		this.solutions = builder.solutions;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", description=" + description + ", constraints=" + constraints + ", groups="
				+ groups + ", grader=" + grader + ", checker=" + checker + ", scoring=" + scoring + ", solutions="
				+ solutions + "]";
	}

	public static class TaskBuilder {
		private String name;
		private TaskDescription description;
		private TaskConstraints constraints = TaskConstraints.DEFAULT;
		private List<TestGroup> groups = new ArrayList<>();
		private Optional<TaskGrader> grader = Optional.empty();
		private Optional<TaskChecker> checker = Optional.empty();
		private Scoring scoring = Scoring.DEFAULT;
		private TaskSolutions solutions;
	
		public TaskBuilder(String name) {
			this.name = name;
		}

		public TaskBuilder setDescription(TaskDescription description) {
			this.description = description;
			return this;
		}
		
		public TaskBuilder setConstraints(TaskConstraints constraints) {
			this.constraints = constraints;
			return this;
		}
		
		public TaskBuilder setGroups(List<TestGroup> groups) {
			this.groups = groups;
			return this;
		}
		
		public TaskBuilder setChecker(Optional<TaskChecker> checker) {
			this.checker = checker;
			return this;
		}
		
		public TaskBuilder setGrader(Optional<TaskGrader> grader) {
			this.grader = grader;
			return this;
		}
	
		public Task build() {
			return new Task(this);
		}
	}
	
}

