package com.github.oliverpavey.quizcaptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		return quizcaptionsMenus.getRoot(model);
	}

	@GetMapping("/quiz")
	public String getQuiz(Model model, 
			@RequestParam(required=false) Integer quizId) {
		
		return quizcaptionsMenus.getQuiz(model, quizId);
	}
	
	@GetMapping("/round")
	public String getRound(Model model, 
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId) {
		
		return quizcaptionsMenus.getRound(model, quizId, roundId);
	}
	
	@GetMapping("/points")
	public String getPoints(Model model, 
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId) {
		
		return quizcaptionsMenus.getPoints(model, quizId, roundId);
	}
	
	@GetMapping("/question")
	public String getQuestion(Model model,
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId,
			@RequestParam(required=false) Integer questionId) {
		
		return quizcaptionsQuestions.getQuestion(model, quizId, roundId, questionId);
	}
	
	@GetMapping("/answer")
	public String getAnswer(Model model,
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId,
			@RequestParam(required=false) Integer questionId) {
		
		return quizcaptionsQuestions.getAnswer(model, quizId, roundId, questionId);
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String getError(Model model, HttpServletRequest request) {
		
		return quizcaptionsErrors.getError(model, request);
	}
	
	@RequestMapping("/printout")
	public String getPrintoutQuestions(Model model, 
			@RequestParam(required=false) Integer quizId) {
		
		return quizcaptionsPrintouts.getPrintoutQuestions(model, quizId);
	}
}
