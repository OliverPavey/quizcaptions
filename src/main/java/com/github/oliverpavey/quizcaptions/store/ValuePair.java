package com.github.oliverpavey.quizcaptions.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor 
@ToString
public class ValuePair {
	private String key;
	private String value;
}