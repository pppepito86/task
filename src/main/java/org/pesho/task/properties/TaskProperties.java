package org.pesho.task.properties;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class TaskProperties {

	public enum PROPS {
		TIME, MEMORY, GROUPS, WEIGHTS, FEEDBACK
	}
	
	private TimeProperties time;
	private MemoryProperties memory;
	private Optional<GroupsProperties> groups;
	
	public TaskProperties(Properties props) {
		Map<String, String> map = props.entrySet().stream()
				.collect(Collectors.toMap(
						e -> e.getKey().toString().toUpperCase(),
						e -> e.getValue().toString()));	

		this.time = new TimeProperties(map.get(PROPS.TIME.toString()));
		this.memory = new MemoryProperties(map.get(PROPS.MEMORY.toString()));
		
		if (map.get(PROPS.GROUPS.toString()) != null) {
			this.groups = Optional.of(new GroupsProperties(map.get(PROPS.GROUPS.toString()), map.get(PROPS.WEIGHTS.toString())));
		} else {
			this.groups = Optional.empty();
		}
	}
	
	public TimeProperties getTimeProperties() {
		return time;
	}
	
	public MemoryProperties getMemoryProperties() {
		return memory;
	}
	
	public Optional<GroupsProperties> getGroupsProperties() {
		return groups;
	}
	
}
