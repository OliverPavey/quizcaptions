package com.github.oliverpavey.quizcaptions.quiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class QuizRegister extends Node {
	
	private List<Quiz> quizzes = new ArrayList<>();

	public List<Quiz> getQuizzes() {
		return quizzes;
	}
	
	public List<Quiz> list() {
		return getQuizzes();
	}
	
	public Quiz getQuiz(int index) {
		return getQuizzes().get(index);
	}
	
	public Quiz getQuiz(String code) {
		return getQuizzes().stream().filter(q -> code.equals(q.getCode())).findFirst().orElse(null);
	}
}
