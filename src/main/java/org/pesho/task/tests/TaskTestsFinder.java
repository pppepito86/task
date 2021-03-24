package org.pesho.task.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class TaskTestsFinder {
	
	public static void main(String[] args) throws Exception {
//		Path taskDir = Path.of("C:\\Users\\Petar\\Downloads\\routes");
//		new TaskTestsFinder().getTestPatterns(taskDir);
	}

//	private static final Map<String, Integer> WORD_WEIGHTS = Map.of("test", -1, "in", -1, "out", 1, "sol", 1, "ans", 1);
	
	private static final Map<String, Integer> WORD_WEIGHTS = Stream.of(
			new SimpleImmutableEntry<>("test", -1),
			new SimpleImmutableEntry<>("in", -1),
			new SimpleImmutableEntry<>("out", 1),
			new SimpleImmutableEntry<>("sol", 1),
			new SimpleImmutableEntry<>("ans", 1))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	public List<TestCase> findTests(List<Path> paths) throws IOException {
		Set<String> stringPaths = paths.stream()
				.map(Path::toString)
				.collect(Collectors.toSet());
		return findTests(stringPaths);
	}
	
	public List<TestCase> findTests(Path dir) throws IOException {
		Set<String> paths = Files.walk(dir)
				.filter(Files::isRegularFile)
				.map(p -> dir.relativize(p))
				.map(Path::toString)
				.collect(Collectors.toSet());
		return findTests(paths);
	}

	public List<TestCase> findTests(Set<String> paths) throws IOException {
		List<PathPattern> patterns = getTestPatterns(paths);
		int testsCount = patterns.stream()
				.findFirst()
				.map(p -> p.matches(paths).size())
				.orElse(0);
		
		List<String> inputs = patterns.get(0).matches(paths);
		List<String> outputs = patterns.get(1).matches(paths);
		
		return IntStream.range(0, testsCount)
				.mapToObj(i -> new TestCase(i, inputs.get(i), outputs.get(i)))
				.collect(Collectors.toList());
	}
	
	public List<PathPattern> getTestPatterns(Set<String> paths) throws IOException {
		List<PathPattern> patternCandidates = paths.stream()
				.flatMap(p -> getPatterns(p).stream())
				.collect(Collectors.groupingBy(pattern -> pattern.matches(paths).size(), TreeMap::new, Collectors.toList()))
				.lastEntry()
				.getValue();
		
		List<PathPattern> patterns = patternCandidates.stream()
				.sorted(Comparator.comparing(PathPattern::getPath))
				.limit(2)
				.sorted(Comparator.comparingInt(p -> WORD_WEIGHTS.entrySet().stream()
						.mapToInt(e -> e.getValue()*StringUtils.countMatches(p.getPath().toLowerCase(), e.getKey()))
						.sum()))
				.collect(Collectors.toList());
		
		return patterns;
	}

	private List<PathPattern> getPatterns(String path) {
		PathPattern pattern = getPattern(path);
		List<PathPattern> patterns = new ArrayList<>();
		patterns.addAll(pattern.getSinglePatterns());
		patterns.addAll(pattern.getDoublePattenrs());
		return patterns;
	}
	
	private PathPattern getPattern(String path) {
		StringBuilder pattern = new StringBuilder();
		Pattern p = Pattern.compile("0*1");
		Matcher m = p.matcher(path);
		List<String> replacements = new ArrayList<>();
		while (m.find()) {
			m.appendReplacement(pattern, "{"+replacements.size()+"}");
			
			if (m.group().startsWith("0")) replacements.add("%0"+m.group().length()+"d");
			else replacements.add("%d");
		}
		m.appendTail(pattern);
		return new PathPattern(pattern.toString(), replacements);
	}
	
}

class PathPattern {
	
	private String path;
	private List<String> replacements;
	
	PathPattern(String path, List<String> replacements) {
		this.path = path;
		this.replacements = replacements;
	}
	
	public String getPath() {
		return path;
	}
	
	public List<String> getReplacements() {
		return replacements;
	}
	
	public String replace(int... numbers) {
		String curr = path;
		for (int i = 0; i < numbers.length; i++) {
			curr = curr.replace("{"+i+"}", String.format(replacements.get(i), numbers[i]));
		}
		return curr;
	}
	
	public List<PathPattern> getSinglePatterns() {
		List<PathPattern> patterns = new ArrayList<>();
		for (int i = 0; i < replacements.size(); i++) {
			String curr = path;
			
			for (int j = 0; j < replacements.size(); j++) {
				if (i == j) continue;
				curr = curr.replace("{"+j+"}", String.format(replacements.get(j), 1));
			}
			curr = curr.replace("{"+i+"}", "{0}");
			patterns.add(new PathPattern(curr, Arrays.asList(replacements.get(i))));
		}
		return patterns;
	}
	
	public List<PathPattern> getDoublePattenrs() {
		List<PathPattern> patterns = new ArrayList<>();
		for (int i = 0; i < replacements.size(); i++) {
			for (int j = i+1; j < replacements.size(); j++) {
				String curr = path;
				for (int k = 0; k < replacements.size(); k++) {
					if (k == i || k == j) continue;
					curr = curr.replace("{"+k+"}", String.format(replacements.get(k), 1));
				}
				curr = curr.replace("{"+i+"}", "{0}");
				curr = curr.replace("{"+j+"}", "{1}");
				patterns.add(new PathPattern(curr, Arrays.asList(replacements.get(i), replacements.get(j))));
			}
		}
		return patterns;
	}
	
	public List<String> matches(Set<String> paths) {
		List<String> list = new ArrayList<>();
		int[] args = new int[replacements.size()];
		Arrays.fill(args, 1);
		
		for (String path = replace(args); paths.contains(path); path = replace(args)) {
			list.add(path);
			
			for (int i = args.length-1; i >= 0; i--) {
				args[i]++;
				if (i+1 < args.length) args[i+1] = 1;
				if (paths.contains(replace(args))) {
					break;
				}
			}
		}
		
		return list;
	}
	
	@Override
	public String toString() {
		return path + " " + replacements;
	}
	
}
