package com.github.oliverpavey.quizcaptions;

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
import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizConfig;
import com.github.oliverpavey.quizcaptions.quiz.QuizRegister;
import com.github.oliverpavey.quizcaptions.quiz.Round;

import lombok.extern.java.Log;

@Component
@Log
public class QuizcaptionsLoader {

	@Autowired
	private QuizConfig quizConfig;
	
	@Autowired
	private QuizRegister quizRegister;
	
	@PostConstruct
	public void loadQuizzes() {
		List<File> jsonFiles = findJsonQuizzes();
		loadJsonQuizzes(jsonFiles);
		linkRegisterTree();
	}

	private List<File> findJsonQuizzes() {
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
		return jsonFiles;
	}
	
	private void loadJsonQuizzes(List<File> jsonFiles) {
		for (File jsonFile : jsonFiles) {
			try {
				String content = new String(Files.readAllBytes(jsonFile.toPath()),StandardCharsets.UTF_8);
				ObjectMapper mapper = new ObjectMapper(); // Jackson Object Mapper
				Quiz quiz = mapper.readValue(content, Quiz.class);
				quizRegister.getQuizzes().add(quiz);
				String msg = String.format("LOADED: %s", jsonFile.getName());
				log.info(msg);
			} catch (IOException e) {
				String msg = String.format("FAILED TO LOAD: %s", jsonFile.getName());
				log.log(Level.SEVERE, msg, e);
			}
		}
	}

	private void linkRegisterTree() {
		quizRegister.setParent(null);
		for (Quiz quiz : quizRegister.getQuizzes()) {
			quiz.setParent(quizRegister);
			for (Round round : quiz.getRounds()) {
				round.setParent(quiz);
				for (Question question : round.getQuestions()) {
					question.setParent(round);
				}
			}
		}
	}
}
