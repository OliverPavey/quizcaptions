package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizForge {

	@Autowired
	private List<QuizSupplier> quizzes;

	public List<Quiz> getQuizzes() {
		return quizzes.stream()
				.map(supplier -> supplier.quiz())
				.collect(Collectors.toList());
	}
	
	public Quiz getQuiz(int index) {
		return getQuizzes().get(index);
	}
}
