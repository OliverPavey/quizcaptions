package com.github.oliverpavey.quizcaptions.store;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper=false, exclude={"choices"})
public class Menu {
	private String title;
	private final List<MenuChoice> choices = new ArrayList<>();

	public MenuChoice add(MenuChoice menuChoice) {
		choices.add(menuChoice);
		return menuChoice;
	}
	
	public MenuChoice add(String title, String endpoint) {
		MenuChoice menuChoice = new MenuChoice(this, title, endpoint);
		return add(menuChoice);
	}
}
