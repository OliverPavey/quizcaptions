package com.github.oliverpavey.quizcaptions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizRegister;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Component
public class QuizcaptionsQuestions {
	
	public static final boolean QUESTION = false;
	public static final boolean ANSWER = true;

	@Autowired
	private QuizRegister quizRegister;
	
	@Autowired
	private QuizcaptionsErrors quizcaptionsErrors;
	
	@Autowired
	private QuizcaptionsMenus quizcaptionsMenus;
	
	@Autowired
	private QuizcaptionsNavigation quizcaptionsNavigation;
	
	private String qaModel(Model model, 
			String quizId, String roundId, Integer questionId, 
			boolean showAnswer) {
		
		if (quizId==null)
			return quizcaptionsMenus.getRoot(model);
		if (roundId==null)
			return quizcaptionsMenus.getQuiz(model, quizId);
		if (questionId==null)
			return quizcaptionsMenus.getRound(model, quizId, roundId, QuizcaptionsMenus.SHOW_QUESTIONS);
		Quiz quiz = quizRegister.getQuiz(quizId);
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

		quizcaptionsNavigation.navigationModel(model, quiz, round, question, showAnswer);
		
		return null;
	}

	public String getQuestion(Model model, String quizId, String roundId, Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, QUESTION);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/question";
	}
	
	public String getAnswer(Model model, String quizId, String roundId, Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, ANSWER);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/question";
	}
}
