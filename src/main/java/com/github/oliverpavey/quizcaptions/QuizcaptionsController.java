package com.github.oliverpavey.quizcaptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizcaptionsController implements ErrorController {
	
	@Autowired
	private QuizcaptionsErrors quizcaptionsErrors;
	
	@Autowired
	private QuizcaptionsMenus quizcaptionsMenus;
	
	@Autowired
	private QuizcaptionsQuestions quizcaptionsQuestions;
	
	@Autowired
	private QuizcaptionsPrintouts quizcaptionsPrintouts;
	
	@GetMapping("/")
	public String getRoot(Model model) {
		
		return "redirect:/quiz";
	}
	
	@GetMapping("/quiz")
	public String getQuizRoot(Model model) {
		
		return quizcaptionsMenus.getRoot(model);
	}

	@GetMapping("/quiz/{quizId}")
	public String getQuiz(Model model, 
			@PathVariable("quizId") String quizId) {
		
		return quizcaptionsMenus.getQuiz(model, quizId);
	}
	
	@GetMapping("/quiz/{quizId}/{roundId}")
	public String getRound(Model model, 
			@PathVariable("quizId") String quizId,
			@PathVariable("roundId") String roundId) {
		
		return quizcaptionsMenus.getRound(model, quizId, roundId);
	}
	
	@GetMapping("/quiz/{quizId}/{roundId}/question")
	public String getRoundQuestions(Model model, 
			@PathVariable("quizId") String quizId,
			@PathVariable("roundId") String roundId) {
		
		return quizcaptionsMenus.getRound(model, quizId, roundId, QuizcaptionsMenus.SHOW_QUESTIONS);
	}
	
	@GetMapping("/quiz/{quizId}/{roundId}/answer")
	public String getRoundAnswers(Model model, 
			@PathVariable("quizId") String quizId,
			@PathVariable("roundId") String roundId) {
		
		return quizcaptionsMenus.getRound(model, quizId, roundId, QuizcaptionsMenus.SHOW_ANSWERS);
	}
	
	@GetMapping("/quiz/{quizId}/{roundId}/question/{questionId}")
	public String getQuestion(Model model,
			@PathVariable("quizId") String quizId,
			@PathVariable("roundId") String roundId,
			@PathVariable("questionId") Integer questionId) {
		
		return quizcaptionsQuestions.getQuestion(model, quizId, roundId, questionId-1);
	}
	
	@GetMapping("/quiz/{quizId}/{roundId}/answer/{questionId}")
	public String getAnswer(Model model,
			@PathVariable("quizId") String quizId,
			@PathVariable("roundId") String roundId,
			@PathVariable("questionId") Integer questionId) {
		
		return quizcaptionsQuestions.getAnswer(model, quizId, roundId, questionId-1);
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String getError(Model model, HttpServletRequest request) {
		
		return quizcaptionsErrors.getError(model, request);
	}
	
	@RequestMapping("/quiz/{quizId}/printout")
	public String getPrintoutQuestions(Model model, 
			@PathVariable("quizId") String quizId) {
		
		return quizcaptionsPrintouts.getPrintoutQuestions(model, quizId);
	}
}
