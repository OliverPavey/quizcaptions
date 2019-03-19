package com.github.oliverpavey.quizcaptions.quiz;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value={"parent","endpoint"})
public abstract class Node {
	private Node parent;
	
	public int index() {
		if (parent == null)
			return 0;
		return parent.list().indexOf(this);
	}
	
	public Optional<Node> previous() {
		int prev = index()-1;
		if (prev < 0)
			return Optional.ofNullable( null );
		return Optional.ofNullable( parent.list().get(prev) );
	}
	
	public Optional<Node> following() {
		int next = index()+1;
		if (next >= parent.list().size())
			return Optional.ofNullable( null );
		return Optional.ofNullable( parent.list().get(next) );
	}
	
	public Node previousOrParent() {
		return previous().orElse(parent);
	}
	
	public Node followingOrParent() {
		return following().orElse(parent);
	}
	
	public abstract List<? extends Node> list();
}
