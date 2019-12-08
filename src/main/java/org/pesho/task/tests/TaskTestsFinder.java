package org.pesho.task.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.pesho.task.TestCase;

public class TaskTestsFinder {
	
	public static void main(String[] args) throws Exception {
		Path taskDir = Path.of("C:\\Users\\Petar\\Documents\\informatics\\ejoi\\2018\\contest-9009\\problems\\prime-tree");
		List<TestCase> taskTests = findTestsInTaskDir(taskDir);
		System.out.println(taskTests.size());
		System.out.println(taskTests);
	}

	public static List<TestCase> findTestsInTaskDir(Path taskDir) throws Exception {
		Optional<Path> testsDir = Files.walk(taskDir)
				.filter(Files::isDirectory)
				.filter(p -> p.getFileName().toString().equalsIgnoreCase("test") || 
						p.getFileName().toString().equalsIgnoreCase("tests"))
				.sorted((a, b) -> {
					String s1 = a.toString();
					String s2 = b.toString();
					if (s1.length() != s2.length()) return Integer.compare(s1.length(), s2.length());
					return s1.compareTo(s2);
				})
				.findFirst();
		
		List<TaskTest> tests = testsDir.map(p -> allFiles(p).stream().map(taskDir::relativize).map(TaskTest::new).collect(Collectors.toList()))
				.map(TaskTestsFinder::findTestsInTestsDir)
				.orElse(new ArrayList<>());
		
		List<TestCase> cases = new ArrayList<>();
		for (int i = 0; i < tests.size(); i+=2) {
			cases.add(new TestCase(i/2+1, tests.get(i).getPath(), tests.get(i+1).getPath()));
		}
		return cases;
	}
	
	private static List<TaskTest> findTestsInTestsDir(List<TaskTest> allFiles) {
		List<TaskTest> testCaseOne = testCaseOne(allFiles);
		
		List<TaskTest> allTests = allFiles.stream().filter(f -> allFiles.stream().anyMatch(ff -> f.isNeighbourTest(ff.getPath())))
				.sorted((a, b) -> a.addLeadingZerosToNumbersToGetLength(5).replace("ans", "zzz").replace("out", "zzz").replace("sol", "zzz")
						.compareTo(b.addLeadingZerosToNumbersToGetLength(5).replace("ans", "zzz").replace("out", "zzz").replace("sol", "zzz")))
				.collect(Collectors.toList());
	
		if (!allTests.get(0).getPath().equals(testCaseOne.get(0).getPath())) allTests.remove(0);

		if (!allTests.get(1).getPath().equals(testCaseOne.get(1).getPath())) {
			List<TaskTest> fixedTests = new ArrayList<>();
			for (int i = 0; i < allTests.size()/2; i++) {
				fixedTests.add(allTests.get(i));
				fixedTests.add(allTests.get(i+allTests.size()/2));
			}
			allTests = fixedTests;
		}
		
		return allTests;
	}
	
	private static List<TaskTest> testCaseOne(List<TaskTest> allFiles) {
		List<TaskTest> input1Candidates = allFiles.stream()
				.filter(f -> allFiles.stream().anyMatch(ff -> f.isNeighbourTest(ff.getPath())))
				.collect(Collectors.toList());

		int maxOnces = allFiles.stream().mapToInt(t -> t.countOnes()).max().orElse(0);
		
		input1Candidates = input1Candidates.stream()
				.filter(t -> t.countOnes() == maxOnces)
				.sorted((a, b) -> a.addLeadingZerosToNumbersToGetLength(5).replace("ans", "zzz").replace("out", "zzz").replace("sol", "zzz")
						.compareTo(b.addLeadingZerosToNumbersToGetLength(5).replace("ans", "zzz").replace("out", "zzz").replace("sol", "zzz")))
				.collect(Collectors.toList());

		return input1Candidates;
	}

	private static List<Path> allFiles(Path path) {
		try {
			return Files.walk(path)
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
