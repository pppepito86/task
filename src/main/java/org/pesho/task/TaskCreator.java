package org.pesho.task;

import java.nio.file.Path;

import org.pesho.task.Task.TaskBuilder;
import org.pesho.task.checker.TaskCheckerFinder;
import org.pesho.task.grader.TaskGraderFinder;
import org.pesho.task.properties.TaskProperties;
import org.pesho.task.properties.TaskPropertiesFinder;
import org.pesho.task.tests.TaskConstraints;
import org.pesho.task.tests.TaskGroupsFinder;

public class TaskCreator {
	
	public static void main(String[] args) throws Exception {
		Path taskDir = Path.of("C:\\Users\\Petar\\Documents\\informatics\\ejoi\\2017\\Day 1\\magic");
		Task task = createTask(taskDir);
		System.out.println(task);
	}

	public static Task createTask(Path taskDir) throws Exception {
		TaskProperties taskProperties = new TaskProperties(TaskPropertiesFinder.findProperties(taskDir));
		
		return new TaskBuilder(TaskNameFinder.findName(taskDir))
				.setConstraints(new TaskConstraints(taskProperties))
				.setGroups(TaskGroupsFinder.findGroupsInTaskDir(taskDir, taskProperties.getGroupsProperties()))
				.setChecker(TaskCheckerFinder.findChecker(taskDir))
				.setGrader(TaskGraderFinder.findGrader(taskDir))
				.build();
	}
	
}
