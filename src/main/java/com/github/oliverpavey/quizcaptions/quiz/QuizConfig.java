package com.github.oliverpavey.quizcaptions.quiz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="quiz")
@Data
public class QuizConfig {

	private String folders;
	
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	public String[] getFolderList() {
		return folders==null ? EMPTY_STRING_ARRAY : folders.split(",");
	}
}
