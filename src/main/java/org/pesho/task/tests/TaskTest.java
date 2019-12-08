package org.pesho.task.tests;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskTest {

	private Path path;
	
	public TaskTest(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
	public int countOnes() {
		return addLeadingZerosToNumbersToGetLength(5).split("00001", -1).length-1;
	}
	
	public boolean isNeighbourTest(Path p) {
		StringBuilder a = new StringBuilder(path.toString());
		StringBuilder b = new StringBuilder(p.toString());
		if (a.equals(b)) return false;
		while (a.length() > 0 && b.length() > 0 && a.charAt(0) == b.charAt(0)) {
			a.deleteCharAt(0);
			b.deleteCharAt(0);
		}
		while (a.length() > 0 && b.length() > 0 && a.charAt(a.length()-1) == b.charAt(b.length()-1)) {
			a.deleteCharAt(a.length()-1);
			b.deleteCharAt(b.length()-1);
		}
		try {
			int x = Integer.parseInt(a.toString());
			int y = Integer.parseInt(b.toString());
			return Math.abs(x-y) == 1;
		} catch (Exception e) {
			return false;
		}
	}

	public String addLeadingZerosToNumbersToGetLength(int length) {
		String name = path.getFileName().toString();
		StringBuilder ans = new StringBuilder();

		int index = 0;
		while (true) {
			while (index < name.length() && !Character.isDigit(name.charAt(index))) ans.append(name.charAt(index++));
			if (index == name.length()) break;
			
			StringBuilder number = new StringBuilder();
			while (index < name.length() && Character.isDigit(name.charAt(index))) number.append(name.charAt(index++));
			while (number.length() < length) number.insert(0, '0');
			ans.append(number);
		}
		
		return ans.toString();
	}
	
	public List<Path> expectedSecondFile() {
		ArrayList<Path> ans = new ArrayList<Path>();
		String name = path.getFileName().toString();
		int number = 0;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isDigit(c)) number = 10*number+c-'0';
			else number = 0;

			if (number == 1 && (i==name.length()-1 || !Character.isDigit(name.charAt(i+1)))) {
				String expectedSecondFile = name.substring(0, i) + "2" + name.substring(i+1);
				ans.add(path.getParent().resolve(expectedSecondFile));
			}
		}
		return ans;
	}
	
	@Override
	public String toString() {
		return path.toString();
	}
	
}
