package org.pesho.task;

import java.nio.file.Path;

public class TaskNameFinder {
	
	public static String findName(Path taskDir) throws Exception {
		return taskDir.getFileName().toString();
	}
	
}
