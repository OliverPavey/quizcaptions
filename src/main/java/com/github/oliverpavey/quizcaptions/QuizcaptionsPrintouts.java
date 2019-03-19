package com.github.oliverpavey.quizcaptions;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizRegister;

@Component
public class QuizcaptionsPrintouts {

	@Autowired
	private QuizRegister quizRegister;
	
	public String getPrintoutQuestions(Model model, String quizId) {
		
		Quiz quiz = quizRegister.getQuiz(quizId);
		
		Map<Integer, Character> letters = IntStream.range(0,25).boxed()
				.collect(Collectors.toMap(i-> i, i-> (char)('A'+i)));
		
		model.addAttribute("quiz", quiz);
		model.addAttribute("letters", letters);
		
		return "quizcaptions/printout";
	}
}
