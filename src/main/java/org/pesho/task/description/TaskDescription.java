package org.pesho.task.description;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class TaskDescription {

	private static final String DESCRIPTION_EXTENSION = "pdf";
	
	private static final Map<String, Integer> WORD_WEIGHTS = Map.of(
			"statement", -1, "description", -1,
			"analys", 1, "analis", 1, "analiz", 1);
	
	public static final Comparator<Path> COMPARATOR = Comparator
			.<Path>comparingInt(p -> WORD_WEIGHTS.entrySet().stream()
					.mapToInt(e -> e.getValue()*StringUtils.countMatches(p.toString().toLowerCase(), e.getKey()))
					.sum())
			.thenComparing(Path::toString);
	
	public Optional<Path> getTaskDescription(Path path) throws IOException {
		List<Path> paths = Files.walk(path)
				.filter(Files::isRegularFile)
				.map(p -> path.relativize(p))
				.collect(Collectors.toList());
		return getTaskDescription(paths);
	}

	public Optional<Path> getTaskDescription(List<Path> paths) {
		return paths.stream()
				.filter(p -> getExtension(p).equals(DESCRIPTION_EXTENSION))
				.sorted(COMPARATOR)
				.findFirst();
	}
	
	private String getExtension(Path path) {
		return FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
	}
	
	public static void main(String[] args) throws Exception {
		Path path = Path.of("C:\\Users\\Petar\\Documents\\informatics\\noi3\\E\\E\\E1_fraction");
		TaskDescription description = new TaskDescription();
		System.out.println(description.getTaskDescription(path));
	}
	
}
