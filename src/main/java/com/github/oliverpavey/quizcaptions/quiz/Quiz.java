package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	private String code;
	private String name;
	private List<Round> rounds;
	
	public Round getRound(int roundId) {
		return rounds.get(roundId);
	}
}
