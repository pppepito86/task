package org.pesho.task.checker;

import java.nio.file.Path;

public class TaskChecker {

	private Path path;
	
	public TaskChecker(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
}
