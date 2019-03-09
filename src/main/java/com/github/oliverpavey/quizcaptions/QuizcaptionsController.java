package com.github.oliverpavey.quizcaptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizForge;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Controller
public class QuizcaptionsController implements ErrorController {
	
	@Autowired
	QuizForge quizForge;
	
	@GetMapping("/")
	public String getRoot(Model model) {
		
		Menu menu = new Menu("/quiz?quizId=%d");
		quizForge.getQuizzes().stream().forEach(quiz ->
			menu.add(new MenuChoice(menu, quiz.getName()) ));
		
		model.addAttribute("css", "quiz-menu");
		model.addAttribute("title", "Select a Quiz");
		model.addAttribute("menu", menu);
		
		return "quizcaptions/menu";
	}

	@GetMapping("/quiz")
	public String getQuiz(Model model, 
			@RequestParam(required=false) Integer quizId) {
		
		if (quizId==null)
			return getRoot(model);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return problem(model, "Quiz data could not be found");
		
		Menu menu = new Menu("/round?quizId="+quizId+"&roundId=%d");
		quiz.getRounds().stream().forEach(q ->
			menu.add(new MenuChoice(menu, q.getName()) ));
		
		model.addAttribute("css", "round-menu");
		model.addAttribute("title", "Select a Round");
		model.addAttribute("menu", menu);
		
		return "quizcaptions/menu";
	}
	
	private String roundModel(Model model, Integer quizId, Integer roundId, boolean answers) {
		if (quizId==null)
			return getRoot(model);
		if (roundId==null)
			return getQuiz(model, quizId);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return problem(model, "Quiz data could not be found");
		Round round = quiz.getRound(roundId);
		if (round==null)
			return problem(model, "Round data could not be found");
		
		String page  = answers ? "answer" : "question";
		Menu menu = new Menu("/"+page+"?quizId="+quizId+"&roundId="+roundId+"&questionId=%d");
		IntStream.range(0, round.getQuestions().size()).forEach(questionId ->
			menu.add(new MenuChoice(menu, String.format("Question %d", questionId)) ));

		String current =  answers ? "points" : "round";
		String other   = !answers ? "points" : "round";

		
		model.addAttribute("css", "round");
		model.addAttribute("title", round.getName());
		model.addAttribute("subtitle", answers ? "Answers" : "Questions");
		model.addAttribute("quizId", quizId );
		model.addAttribute("roundId", roundId );
		model.addAttribute("current", current );
		model.addAttribute("other", other );
		model.addAttribute("menu", menu);

		return null;
	}
	
	@GetMapping("/round")
	public String getRound(Model model, 
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId) {
		
		String redirect = roundModel(model, quizId, roundId, false);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/round";
	}
	
	@GetMapping("/points")
	public String getPoints(Model model, 
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId) {
		
		String redirect = roundModel(model, quizId, roundId, true);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/round";
	}
	
	private String qaModel(Model model, 
			Integer quizId, Integer roundId, Integer questionId, 
			boolean includeAnswer) {
		
		if (quizId==null)
			return getRoot(model);
		if (roundId==null)
			return getQuiz(model, quizId);
		if (questionId==null)
			return getRound(model, quizId, questionId);
		Quiz quiz = quizForge.getQuiz(quizId);
		if (quiz==null)
			return problem(model, "Quiz data could not be found");
		Round round = quiz.getRound(roundId);
		if (round==null)
			return problem(model, "Round data could not be found");
		List<Question> questions = round.getQuestions();
		if (questions==null)
			return problem(model, "Questions data could not be found");
		Question question = questions.get(questionId);
		if (question==null)
			return problem(model, "Question identifier not supplied.");
		
		Integer nextId = questionId+1;
		boolean hasNext = nextId < questions.size(); 
		
		model.addAttribute("questionNo", questions.indexOf(question) );
		model.addAttribute("question", question.getQuestion() );
		model.addAttribute("answer", question.getAnswer() );
		model.addAttribute("quizId", quizId );
		model.addAttribute("roundId", roundId );
		model.addAttribute("questionId", questionId );
		model.addAttribute("hasNext", hasNext );
		model.addAttribute("nextId", hasNext ? nextId : null );
		
		return null;
	}
	
	@GetMapping("/question")
	public String getQuestion(Model model,
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId,
			@RequestParam(required=false) Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, false);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/question";
	}
	
	@GetMapping("/answer")
	public String getAnswer(Model model,
			@RequestParam(required=false) Integer quizId,
			@RequestParam(required=false) Integer roundId,
			@RequestParam(required=false) Integer questionId) {
		
		String redirect = qaModel(model, quizId, roundId, questionId, true);
		
		if (redirect!=null)
			return redirect;
		
		return "quizcaptions/answer";
	}
	
	private String problem(Model model, String problem) {
		model.addAttribute("problem", problem);
		return "quizcaptions/problem";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String getError(Model model, HttpServletRequest request) {
		
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
		if (exception==null) {
			exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		}
		
		model.addAttribute("statusCode", statusCode==null ? "null" : String.format("%d",  statusCode) );
		model.addAttribute("status", statusCode==null ? "null" :  HttpStatus.valueOf(statusCode).toString() ); 
		model.addAttribute("exception", exception==null ? "null" : exception.getClass().getName() );
		model.addAttribute("message", exception==null ? "null" : exception.getMessage() );
		
		ArrayList<String> attrNames = Collections.list( request.getAttributeNames() );
		List<ValuePair> attrPairs = attrNames.stream().map(attrName -> {
			Object obj = request.getAttribute(attrName);
			String str = obj==null ? "null" : obj.toString();
			return new ValuePair(attrName, str);
		}).collect(Collectors.toList());
		
		model.addAttribute("attrNames", attrNames );
		model.addAttribute("attrPairs", attrPairs );
		
		return "system/error";
	}
	
	@RequestMapping("/printout")
	public String getPrintoutQuestions(Model model, 
			@RequestParam(required=false) Integer quizId) {
		
		Quiz quiz = quizForge.getQuiz(quizId);
		
		model.addAttribute("quiz", quiz);
		
		return "quizcaptions/printout";
	}
}
