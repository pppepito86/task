package org.pesho.task.properties;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class TaskPropertiesFinder {
	
	public static Properties findProperties(Path taskDir) throws Exception {
		Optional<File> maybePropsFile = Files.walk(taskDir)
				.filter(Files::isRegularFile)
				.filter(p -> p.getFileName().toString().equalsIgnoreCase("grade.properties"))
				.map(Path::toFile)
				.findFirst();
		Properties props = new Properties();		
		maybePropsFile.ifPresent(propsFile -> {
			try (FileReader fr = new FileReader(propsFile)) {
				props.load(fr);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return props;
	}
	
}
