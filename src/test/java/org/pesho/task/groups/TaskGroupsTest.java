package org.pesho.task.groups;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.pesho.task.tests.TaskTestsFinder;
import org.pesho.task.tests.TestCase;

public class TaskGroupsTest {

	@Test
	public void test() throws Exception {
		for (int i = 1; i <= 4; i++) test(i);
	}

	private void test(int n) throws Exception {
		Path inPath = Paths.get(this.getClass().getResource("/tests/test"+n+".in").toURI());
		Path solPath = Paths.get(this.getClass().getResource("/tests/test"+n+".sol").toURI());
		List<Path> allFiles = Files.lines(inPath).map(Paths::get).collect(Collectors.toList());
		Collections.reverse(allFiles);

		List<TestCase> tests = new TaskTestsFinder().findTests(allFiles);
		List<String> expectedLines = Files.readAllLines(solPath);
		
		assertThat(tests.size(), equalTo(expectedLines.size()));
		for (int i = 0; i < tests.size(); i++) {
			TestCase test = tests.get(i);
			String expected = expectedLines.get(i);
			String output = test.getInput() + " " + test.getOutput();
			expected = expected.replaceAll("\\\\", "/");
			output = output.replaceAll("\\\\", "/");
			assertThat(output, equalTo(expected));
		}
	}

}
