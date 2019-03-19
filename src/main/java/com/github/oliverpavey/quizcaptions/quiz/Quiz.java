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
public class Quiz extends Node {
	@NonNull
	private String code;
	@NonNull
	private String name;
	@NonNull
	private List<Round> rounds;
	
	public Round getRound(int roundId) {
		return rounds.get(roundId);
	}
	
	public List<Round> list() {
		return getRounds();
	}
	
	public Round getRound(String roundId) {
		
		if (!roundId.matches("[A-Z]"))
			return null;
		
		int index = roundId.charAt(0) - 'A';
		
		return rounds.get(index);
	}
	
	public int getIndex(Round round) {
		return rounds.indexOf(round);
	}
	
	public String getRoundName(Round round) {
		return Character.toString( (char) ('A'+getIndex(round)) );
	}
}
