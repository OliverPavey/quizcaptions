package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Round {
	private String name;
	private List<Question> questions;
}
