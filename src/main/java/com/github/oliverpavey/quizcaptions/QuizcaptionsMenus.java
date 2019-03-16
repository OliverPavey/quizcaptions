package com.github.oliverpavey.quizcaptions;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizForge;
import com.github.oliverpavey.quizcaptions.quiz.Round;
import com.github.oliverpavey.quizcaptions.store.Menu;
import com.github.oliverpavey.quizcaptions.store.MenuChoice;

@Component
public class QuizcaptionsMenus {

	@Autowired
	private QuizForge quizForge;
	
	@Autowired
	private QuizcaptionsErrors quizcaptionsErrors;
	
	private String roundModel(Model model, Integer quizId, Integer roundId, boolean answers) {
		if (quizId==null)
			return getRoot(model);
		if (roundId==null)
			return getQuiz(model, quizId);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return quizcaptionsErrors.problem(model, "Quiz data could not be found");
		Round round = quiz.getRound(roundId);
		if (round==null)
			return quizcaptionsErrors.problem(model, "Round data could not be found");
		
		String page  = answers ? "answer" : "question";
		Menu menu = new Menu("/"+page+"?quizId="+quizId+"&roundId="+roundId+"&questionId=%d");
		IntStream.range(0, round.getQuestions().size()).forEach(questionId ->
			menu.add(new MenuChoice(menu, String.format("Question %d", questionId+1)) ));

		String current =  answers ? "points" : "round";
		String other   = !answers ? "points" : "round";

		
		model.addAttribute("title", round.getName());
		model.addAttribute("subtitle", answers ? "Answers" : "Questions");
		model.addAttribute("quizId", quizId );
		model.addAttribute("roundId", roundId );
		model.addAttribute("current", current );
		model.addAttribute("other", other );
		model.addAttribute("menu", menu);

		return null;
	}
	
	public String getRoot(Model model) {
		
		Menu menu = new Menu("/quiz?quizId=%d");
		quizForge.getQuizzes().stream().forEach(quiz ->
			menu.add(new MenuChoice(menu, quiz.getName()) ));
		
		model.addAttribute("title", "Select a Quiz");
		model.addAttribute("menu", menu);
		
		return "quizcaptions/menu";
	}

	public String getQuiz(Model model, Integer quizId) {
		
		if (quizId==null)
			return getRoot(model);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return quizcaptionsErrors.problem(model, "Quiz data could not be found");
		
		Menu menu = new Menu("/round?quizId="+quizId+"&roundId=%d");
		quiz.getRounds().stream().forEach(q ->
			menu.add(new MenuChoice(menu, q.getName()) ));
		
		model.addAttribute("title", "Select a Round");
		model.addAttribute("menu", menu);
		
		return "quizcaptions/menu";
	}
	
	public String getRound(Model model, Integer quizId, Integer roundId) {
		
		String redirect = roundModel(model, quizId, roundId, false);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/menu";
	}
	
	public String getPoints(Model model, Integer quizId, Integer roundId) {
		
		String redirect = roundModel(model, quizId, roundId, true);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/menu";
	}
}
