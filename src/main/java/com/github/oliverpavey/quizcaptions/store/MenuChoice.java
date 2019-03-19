package com.github.oliverpavey.quizcaptions.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper=false, exclude={"menu"})
public class MenuChoice {
	private final Menu menu;
	private final String title;
	private final String endpoint;
	
	private int getIndex() {
		return menu.getChoices().indexOf(this);
	}
	
	public char getLetter() {
		return (char)( 'A' + getIndex() );
	}
}
