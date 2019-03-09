package com.github.oliverpavey.quizcaptions.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {
	private final String question;
	private final String answer;
}
