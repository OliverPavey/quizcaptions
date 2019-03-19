package com.github.oliverpavey.quizcaptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Component
public class QuizcaptionsNavigation {
	
	@Autowired
	QuizcaptionsEndpoints quizcaptionsEndpoints;
	
	private void common(Model model) {
		model.addAttribute("navHome", quizcaptionsEndpoints.endpointRoot() );
	}

	public void navigationModel(Model model) {
		
		common(model);
		model.addAttribute("navUp"  , null );
		model.addAttribute("navPrev", null );
		model.addAttribute("navNext", null );
	}
	
	public void navigationModel(Model model, Quiz quiz) {
		
		common(model);
		model.addAttribute("navUp"  , quizcaptionsEndpoints.endpointRoot() );
		model.addAttribute("navPrev", null );
		model.addAttribute("navNext", null );
		model.addAttribute("navPrint", quizcaptionsEndpoints.endpointPrintout(quiz) );
	}
	
	public void navigationModel(Model model, Quiz quiz, Round round) {

		common(model);
		model.addAttribute("navUp"  , quizcaptionsEndpoints.endpointQuiz(quiz) );
		model.addAttribute("navPrev", null );
		model.addAttribute("navNext", null );
	}
	
	public void navigationModel(Model model, Quiz quiz, Round round, boolean answer) {

		String endpointUp = quizcaptionsEndpoints.endpointRound(quiz,round);
		
		Question questionFirst = round.getQuestions().isEmpty() ? null 
			: (Question) round.getQuestions().iterator().next();
		
		String endpointNext = questionFirst==null ? endpointUp : !answer 
				? quizcaptionsEndpoints.endpointQuestion(quiz, round, questionFirst)
				: quizcaptionsEndpoints.endpointAnswer(quiz, round, questionFirst);
				
		common(model);
		model.addAttribute("navUp"  , endpointUp );
		model.addAttribute("navPrev", null );
		model.addAttribute("navNext", endpointNext );
	}
	
	public void navigationModel(Model model, Quiz quiz, Round round, Question question, boolean answer) {

		String endpointUp = !answer 
				? quizcaptionsEndpoints.endpointRoundQuestions(quiz, round)
				: quizcaptionsEndpoints.endpointRoundAnswers(quiz, round);  
		
		Question questionPrev = (Question) question.previous().orElse(null);
		Question questionNext = (Question) question.following().orElse(null);
		
		String endpointPrev = questionPrev==null ? null : !answer 
				? quizcaptionsEndpoints.endpointQuestion(quiz, round, questionPrev)
				: quizcaptionsEndpoints.endpointAnswer(quiz, round, questionPrev);
		
		String endpointNext = questionNext==null ? null : !answer 
				? quizcaptionsEndpoints.endpointQuestion(quiz, round, questionNext)
				: quizcaptionsEndpoints.endpointAnswer(quiz, round, questionNext);
				
		common(model);
		model.addAttribute("navUp"  , endpointUp );
		model.addAttribute("navPrev", endpointPrev );
		model.addAttribute("navNext", endpointNext );
	}
}
