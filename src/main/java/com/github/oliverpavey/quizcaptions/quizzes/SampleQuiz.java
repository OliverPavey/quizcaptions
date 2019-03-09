package com.github.oliverpavey.quizcaptions.quizzes;

import org.springframework.stereotype.Component;

import com.github.oliverpavey.quizcaptions.quiz.Question;
import com.github.oliverpavey.quizcaptions.quiz.Quiz;
import com.github.oliverpavey.quizcaptions.quiz.QuizBuilder;
import com.github.oliverpavey.quizcaptions.quiz.QuizSupplier;
import com.github.oliverpavey.quizcaptions.quiz.Round;

@Component
public class SampleQuiz implements QuizSupplier  {

	@Override
	public Quiz quiz() {
		return new Quiz("Test Quiz", new QuizBuilder<Round>().list(
				new Round("Quiz Round 1", new QuizBuilder<Question>().list(
						new Question(
								"Question One?", 
								"Answer One"),
						new Question(
								"Question Two?", 
								"Answer Two"),
						new Question(
								"Question Three?", 
								"Answer Three"),
						new Question(
								"Question Four?", 
								"Answer Four"),
						new Question(
								"Question Five?", 
								"Answer Five")
						)),
				new Round("Quiz Round 2", new QuizBuilder<Question>().list(
						new Question(
								"R2 Question One?", 
								"R2 Answer One"),
						new Question(
								"R2 Question Two?", 
								"R2 Answer Two"),
						new Question(
								"R2 Question Three?", 
								"R2 Answer Three"),
						new Question(
								"R2 Question Four?", 
								"R2 Answer Four"),
						new Question(
								"R2 Question Five?", 
								"R2 Answer Five")
						))
				// Blank Template:
				/*
				new Round("", new QuizBuilder<Question>().list(
						new Question(
								"", 
								""),
						new Question(
								"", 
								""),
						new Question(
								"", 
								""),
						new Question(
								"", 
								""),
						new Question(
								"", 
								"")
						))
				 */
				) );
	}
}
