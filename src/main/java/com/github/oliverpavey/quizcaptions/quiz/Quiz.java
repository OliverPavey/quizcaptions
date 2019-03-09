package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quiz {
	private final String name;
	private final List<Round> rounds;
	
	public Round getRound(int roundId) {
		return rounds.get(roundId);
	}
}
