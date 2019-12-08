package org.pesho.task.grader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class TaskGraderFinder {
	
	public static void main(String[] args) throws Exception {
		Path taskDir = Path.of("C:\\Users\\Petar\\Documents\\informatics\\ejoi\\2018\\hills");
		Optional<TaskGrader> checker = findGrader(taskDir);
		System.out.println(checker.get().getPath());
	}

	public static Optional<TaskGrader> findGrader(Path taskDir) throws Exception {
		return Files.walk(taskDir)
				.filter(p -> p.getFileName().toString().equalsIgnoreCase("grader.cpp"))
				.map(taskDir::relativize)
				.map(p -> new TaskGrader(p))
				.findFirst();
	}
	
}
