package com.github.oliverpavey.quizcaptions.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizBuilder<T> {

	@SuppressWarnings("unchecked")
	public List<T> list(T... items) {
		return new ArrayList<T>( Arrays.asList( items ) );
	}
}
