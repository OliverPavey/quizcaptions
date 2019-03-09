package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Round {
	private final String name;
	private final List<Question> questions;
}
