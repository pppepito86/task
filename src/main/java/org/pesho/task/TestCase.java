package org.pesho.task;

import java.nio.file.Path;

public class TestCase {

	private int number;
	private Path input;
	private Path output;
	private boolean hasFeedback;
	
	public TestCase() {
	}
	
	public TestCase(int number, Path input, Path output) {
		this.number = number;
		this.input = input;
		this.output = output;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Path getInput() {
		return input;
	}
	
	public Path getOutput() {
		return output;
	}
	
	public boolean hasFeedback() {
		return hasFeedback;
	}

	@Override
	public String toString() {
		return "TestCase [number=" + number + ", input=" + input + ", output=" + output + ", hasFeedback=" + hasFeedback
				+ "]";
	}
	
}

/*

{
	"points" : 100,
	"groups" : [
		{
			"wight":0.5,
			"tests":["test01.in, test01.out",""]
		
		},
		{},
		{}
	]


}




*/