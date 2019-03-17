package com.github.oliverpavey.quizcaptions.quiz;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@Component
@Log
public class QuizForge {

	@Autowired
	private QuizConfig quizConfig;
	
	private List<Quiz> quizzes = new ArrayList<>();

	public List<Quiz> getQuizzes() {
		return quizzes;
	}
	
	public Quiz getQuiz(int index) {
		return getQuizzes().get(index);
	}
	
	public Quiz getQuiz(String code) {
		return getQuizzes().stream().filter(q -> code.equals(q.getCode())).findFirst().orElse(null);
	}
	
	@PostConstruct
	private void loadQuizzes() {
		
		String[] folderNames = quizConfig.getFolderList();
		List<File> jsonFiles = new ArrayList<>();
		for (String folderName : folderNames) {
			File folder = new File(folderName);
			File[] moreJsonFiles = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".json");
				}
			});
			jsonFiles.addAll( Arrays.asList(moreJsonFiles) );
		}
		
		for (File jsonFile : jsonFiles) {
			try {
				String content = new String(Files.readAllBytes(jsonFile.toPath()),StandardCharsets.UTF_8);
				ObjectMapper mapper = new ObjectMapper(); // Jackson Object Mapper
				Quiz quiz = mapper.readValue(content, Quiz.class);
				quizzes.add(quiz);
				String msg = String.format("LOADED: %s", jsonFile.getName());
				log.info(msg);
			} catch (IOException e) {
				String msg = String.format("FAILED TO LOAD: %s", jsonFile.getName());
				log.log(Level.SEVERE, msg, e);
			}
		}
	}
}
