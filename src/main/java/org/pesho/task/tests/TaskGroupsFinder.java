package org.pesho.task.tests;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.pesho.task.properties.GroupsProperties;

public class TaskGroupsFinder {
	
	public static List<TestGroup> findGroupsInTaskDir(Path taskDir, Optional<GroupsProperties> groupProperties) throws Exception {
		List<TestCase> allTests = new TaskTestsFinder().findTests(taskDir);
		int groupsCount = groupProperties.map(p -> p.groupsCount()).orElse(allTests.size());
		
		List<TestGroup> groups = new ArrayList<>();
		for (int i = 0; i < groupsCount; i++) {
			int groupTestsCount = getGroupTestsCount(groupProperties, i);
			List<TestCase> groupTests = new ArrayList<>();
			for (int j = 0; j < groupTestsCount; j++) {
				groupTests.add(allTests.remove(0));
			}
			
			double groupWeight = getGroupWeight(groupProperties, i);
			groups.add(new TestGroup(groupWeight, groupTests));
		}
		return groups;
	}
	
	private static int getGroupTestsCount(Optional<GroupsProperties> groupProperties, int group) {
		return groupProperties.map(p -> p.getTests().get(group)).orElse(1);
	}

	private static double getGroupWeight(Optional<GroupsProperties> groupProperties, int group) {
		return groupProperties.map(p -> p.getWeights().get(group)).orElse(1.0);
	}
	
}
