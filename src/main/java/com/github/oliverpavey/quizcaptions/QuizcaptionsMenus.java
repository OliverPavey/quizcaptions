package com.github.oliverpavey.quizcaptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizRegister;
import com.github.oliverpavey.quizcaptions.quiz.Round;
import com.github.oliverpavey.quizcaptions.store.Menu;
import com.github.oliverpavey.quizcaptions.store.MenuChoice;

@Component
public class QuizcaptionsMenus {
	
	public static final boolean SHOW_QUESTIONS = false;
	public static final boolean SHOW_ANSWERS = true;

	@Autowired
	private QuizRegister quizRegister;
	
	@Autowired
	private QuizcaptionsEndpoints quizcaptionsEndpoints;
	
	@Autowired
	private QuizcaptionsErrors quizcaptionsErrors;
	
	@Autowired
	private QuizcaptionsNavigation quizcaptionsNavigation;
	
	private void menuModel(Model model, Menu menu) {
		model.addAttribute("title", menu.getTitle());
		model.addAttribute("menu", menu);
	}

	private String roundModel(Model model, String quizId, String roundId, Boolean answers) {
		if (quizId==null)
			return getRoot(model);
		if (roundId==null)
			return getQuiz(model, quizId);
		Quiz quiz = quizRegister.getQuiz(quizId);
		if (quiz==null)
			return quizcaptionsErrors.problem(model, "Quiz data could not be found");
		Round round = quiz.getRound(roundId);
		if (round==null)
			return quizcaptionsErrors.problem(model, "Round data could not be found");
		
		if (answers==null)
			return roundModelPrelude(model, quiz, round);
		else
			return roundModelMain(model, quiz, round, answers);
	}
	
	private String roundModelPrelude(Model model, Quiz quiz, Round round) {
		
		Menu menu = new Menu(round.getName());
		menu.add("Questions",quizcaptionsEndpoints.endpointRoundQuestions(quiz,round));
		menu.add("Answers",quizcaptionsEndpoints.endpointRoundAnswers(quiz,round));
		
		menuModel(model, menu);
		quizcaptionsNavigation.navigationModel(model, quiz, round);
		
		return null;
	}
	
	private String roundModelMain(Model model, Quiz quiz, Round round, boolean answers) {

		Menu menu = new Menu(round.getName().concat(answers ? " Answers" : " Questions"));
			round.getQuestions().stream().forEach(question -> menu.add( 
				String.format("%s %d", 
					answers ? "Answer" : "Question" , 
					round.getQuestions().indexOf(question)+1 ), 
				answers 
					? quizcaptionsEndpoints.endpointAnswer(quiz, round, question) 
					: quizcaptionsEndpoints.endpointQuestion(quiz, round, question) ) );

		menuModel(model, menu);
		quizcaptionsNavigation.navigationModel(model, quiz, round, answers);
		
		return null;
	}
	
	public String getRoot(Model model) {
		
		Menu menu = new Menu("Select a Quiz");
		quizRegister.getQuizzes().stream().forEach(quiz ->
			menu.add( new MenuChoice(
					menu, quiz.getName(), 
					quizcaptionsEndpoints.endpointQuiz(quiz) ) ));
		
		if (menu.getChoices().size()==0)
			return quizcaptionsErrors.problem(model, "No quizzes found. "
					+ "Has environment variable QUIZ_FOLDERS been pointed to a folder "
					+ "(or comma separated list of folders) "
					+ "containing JSON files containing a quiz or quizzes?");
		
		menuModel(model, menu);
		quizcaptionsNavigation.navigationModel(model);
		
		return "quizcaptions/menu";
	}

	public String getQuiz(Model model, String quizId) {
		
		if (quizId==null)
			return getRoot(model);
		Quiz quiz = quizRegister.getQuiz(quizId);
		if (quiz==null)
			return quizcaptionsErrors.problem(model, "Quiz data could not be found");
		
		Menu menu = new Menu("Select a Round");
		quiz.getRounds().stream().forEach(round ->
			menu.add( new MenuChoice(
					menu, round.getName(), 
					quizcaptionsEndpoints.endpointRound(quiz, round) ) ) );
		
		menuModel(model, menu);
		quizcaptionsNavigation.navigationModel(model,quiz);
		
		return "quizcaptions/menu";
	}
	
	public String getRound(Model model, String quizId, String roundId) {

		String redirect = roundModel(model, quizId, roundId, null);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/menu";
	}
	
	public String getRound(Model model, String quizId, String roundId, boolean showAnswers) {
		
		String redirect = roundModel(model, quizId, roundId, showAnswers);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/menu";
	}
}
