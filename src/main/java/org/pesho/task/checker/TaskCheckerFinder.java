package org.pesho.task.checker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class TaskCheckerFinder {
	
	private static final Map<String, Integer> WORD_WEIGHTS = Map.of("checker", -1);
	
	public static final Comparator<Path> COMPARATOR = Comparator
			.<Path>comparingInt(p -> WORD_WEIGHTS.entrySet().stream()
					.mapToInt(e -> e.getValue()*StringUtils.countMatches(p.toString().toLowerCase(), e.getKey()))
					.sum())
			.thenComparing(Path::toString);
	
	public static void main(String[] args) throws Exception {
		Path taskDir = Path.of("C:\\Users\\Petar\\Documents\\informatics\\ejoi\\2018\\hills");
		Optional<TaskChecker> checker = findChecker(taskDir);
		System.out.println(checker.get().getPath());
	}

	public static Optional<TaskChecker> findChecker(Path taskDir) throws Exception {
		return Files.walk(taskDir)
				.map(taskDir::relativize)
				.filter(p -> p.getFileName().toString().equalsIgnoreCase("checker.cpp"))
				.sorted(COMPARATOR)
				.map(TaskChecker::new)
				.findFirst();
	}
	
}
