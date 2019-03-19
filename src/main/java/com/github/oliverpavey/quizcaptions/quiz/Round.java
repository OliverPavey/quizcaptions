package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@RequiredArgsConstructor
public class Round extends Node {
	@NonNull
	private String name;
	@NonNull
	private List<Question> questions;
	
	public List<Question> list() {
		return getQuestions();
	}
}
