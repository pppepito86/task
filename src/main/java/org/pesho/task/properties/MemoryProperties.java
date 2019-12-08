package org.pesho.task.properties;

public class MemoryProperties {

	public static final int DEFAULT_MEMORY = 256;
	
	private int memory = DEFAULT_MEMORY;
	
	public MemoryProperties(String memoryProperty) {
		try {
			if (memoryProperty != null) {
				this.memory = Integer.parseInt(memoryProperty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMemory() {
		return memory;
	}
	
}
