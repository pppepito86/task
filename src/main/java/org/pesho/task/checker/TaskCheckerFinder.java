package org.pesho.task.checker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class TaskCheckerFinder {
	
	public static void main(String[] args) throws Exception {
		Path taskDir = Path.of("C:\\Users\\Petar\\Documents\\informatics\\ejoi\\2018\\hills");
		Optional<TaskChecker> checker = findChecker(taskDir);
		System.out.println(checker.get().getPath());
	}

	public static Optional<TaskChecker> findChecker(Path taskDir) throws Exception {
		return Files.walk(taskDir)
				.filter(p -> p.getFileName().toString().equalsIgnoreCase("checker.cpp"))
				.map(taskDir::relativize)
				.map(p -> new TaskChecker(p))
				.findFirst();
	}
	
}
