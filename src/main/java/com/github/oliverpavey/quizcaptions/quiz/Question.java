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
public class Question extends Node {
	@NonNull
	private String question;
	@NonNull
	private String answer;
	
	public List<Node> list() {
		return null;
	}
}
