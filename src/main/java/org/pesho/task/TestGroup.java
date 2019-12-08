package org.pesho.task;

import java.util.List;

public class TestGroup {

	private double weight;
	private List<TestCase> testCases;

	public TestGroup() {
	}
	
	public TestGroup(double weight, List<TestCase> testCases) {
		this.weight = weight;
		this.testCases = testCases;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public List<TestCase> getTestCases() {
		return testCases;
	}

	@Override
	public String toString() {
		return "TestGroup [weight=" + weight + ", testCases=" + testCases + "]";
	}
	
}