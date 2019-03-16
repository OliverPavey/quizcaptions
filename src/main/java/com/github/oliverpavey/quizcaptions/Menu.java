package com.github.oliverpavey.quizcaptions;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper=false, exclude={"choices"})
public class Menu {
	private final String template;
	private final List<MenuChoice> choices = new ArrayList<>();

	public Object add(MenuChoice menuChoice) {
		return choices.add(menuChoice);
	} 
}
