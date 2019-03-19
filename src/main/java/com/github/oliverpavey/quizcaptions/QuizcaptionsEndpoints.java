package com.github.oliverpavey.quizcaptions;

import org.springframework.stereotype.Component;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Component
public class QuizcaptionsEndpoints {

	public String endpointRoot() {
		return "/quiz";
	}
	
	public String endpointQuiz(Quiz quiz) {
		return String.format( "/quiz/%s" , quiz.getCode() );
	}
	
	public String endpointRound(Quiz quiz, Round round) {
		return String.format( "/quiz/%s/%s", quiz.getCode() , quiz.getRoundName(round) );
	}
	
	public String endpointRoundQuestions(Quiz quiz, Round round) {
		return endpointRound(quiz, round).concat("/question");
	}
	
	public String endpointRoundAnswers(Quiz quiz, Round round) {
		return endpointRound(quiz, round).concat("/answer");
	}
	
	private String endpointQuestion(Quiz quiz, Round round, String mode, Question question) {
		return String.format( "/quiz/%s/%s/%s/%d", 
				quiz.getCode() , 
				quiz.getRoundName(round) , 
				mode, 
				round.getQuestions().indexOf(question)+1);
	}
	
	public String endpointQuestion(Quiz quiz, Round round, Question question) {
		return endpointQuestion(quiz, round, "question", question);
	}
	
	public String endpointAnswer(Quiz quiz, Round round, Question question) {
		return endpointQuestion(quiz, round, "answer", question);
	}
	
	public String endpointPrintout(Quiz quiz) {
		return String.format( "/quiz/%s/printout" , quiz.getCode() );
	}
}
