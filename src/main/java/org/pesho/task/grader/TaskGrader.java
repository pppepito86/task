package org.pesho.task.grader;

import java.nio.file.Path;

public class TaskGrader {

	private Path path;
	
	public TaskGrader(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
}
