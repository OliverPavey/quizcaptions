package com.github.oliverpavey.quizcaptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
class ValuePair {
	private String key;
	private String value;
}