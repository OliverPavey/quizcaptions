package com.github.oliverpavey.quizcaptions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizForge;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Component
public class QuizcaptionsQuestions {

	@Autowired
	private QuizForge quizForge;
	
	@Autowired
	private QuizcaptionsErrors quizcaptionsErrors;
	
	@Autowired
	private QuizcaptionsMenus quizcaptionsMenus;
	
	private String qaModel(Model model, 
			Integer quizId, Integer roundId, Integer questionId, 
			boolean showAnswer) {
		
		if (quizId==null)
			return quizcaptionsMenus.getRoot(model);
		if (roundId==null)
			return quizcaptionsMenus.getQuiz(model, quizId);
		if (questionId==null)
			return quizcaptionsMenus.getRound(model, quizId, questionId);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return quizcaptionsErrors.problem(model, "Quiz data could not be found");
		Round round = quiz.getRound(roundId);
		if (round==null)
			return quizcaptionsErrors.problem(model, "Round data could not be found");
		List<Question> questions = round.getQuestions();
		if (questions==null)
			return quizcaptionsErrors.problem(model, "Questions data could not be found");
		Question question = questions.get(questionId);
		if (question==null)
			return quizcaptionsErrors.problem(model, "Question identifier not supplied.");
		
		Integer nextId = questionId+1;
		boolean hasNext = nextId < questions.size(); 
		
		model.addAttribute("showAnswer", showAnswer);
		model.addAttribute("questionNo", questions.indexOf(question) );
		model.addAttribute("question", question.getQuestion() );
		model.addAttribute("answer", showAnswer ? question.getAnswer() : "" );
		model.addAttribute("quizId", quizId );
		model.addAttribute("roundId", roundId );
		model.addAttribute("questionId", questionId );
		model.addAttribute("hasNext", hasNext );
		model.addAttribute("nextId", hasNext ? nextId : null );
		
		return null;
	}
	
	public String getQuestion(Model model, Integer quizId, Integer roundId, Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, false);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/question";
	}
	
	public String getAnswer(Model model, Integer quizId, Integer roundId, Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, true);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/question";
	}
}
