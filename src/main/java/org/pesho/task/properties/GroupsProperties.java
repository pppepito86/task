package org.pesho.task.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupsProperties {

	private List<Integer> tests;
	private List<Double> weights;
	
	public GroupsProperties(String groupsProperty, String weightsProperty) {
		tests = Arrays.stream(groupsProperty.split(","))
				.map(GroupsProperties::testInGroup)
				.collect(Collectors.toList());

		if (weightsProperty != null) {
			weights = Arrays.stream(weightsProperty.split(","))
					.map(Double::parseDouble)
					.collect(Collectors.toList());
			if (tests.size() != weights.size()) throw new RuntimeException("groups and weights not mathcing");
		} else {
			weights = Collections.nCopies(tests.size(), 1.0);
		}
	}

	private static int testInGroup(String group) {
		if (!group.contains("-")) return Integer.parseInt(group.trim());
		return Integer.parseInt(group.split("-")[1].trim()) - Integer.parseInt(group.split("-")[0].trim()) + 1;
	}
	
	public List<Integer> getTests() {
		return tests;
	}
	
	public List<Double> getWeights() {
		return weights;
	}
	
	public int groupsCount() {
		return tests.size();
	}
	
}
