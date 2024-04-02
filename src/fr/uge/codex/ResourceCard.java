package fr.uge.codex;

import java.util.List;
import java.util.Objects;

public class ResourceCard extends Card {
	private final List<Resource> corners;
	private final Resource verso;
	private final int score;
	
	public enum Resource {
		animal,
		plant,
		fungi,
		insect
	}
	
	public ResourceCard(List<Resource> corners, Resource verso, int score) {
		Objects.requireNonNull(corners, "corners doesn't register");
		if (corners.size() != 4) {
			throw new IllegalArgumentException("must have 4 corners in list");
		}
		this.corners = corners;
		this.verso = Objects.requireNonNull(verso);
		if (score < 0) {
			throw new IllegalArgumentException("score value invalid");
		}
		this.score = 0;
	}

	@Override
	public String toString() {
		return "ResourceCard Recto " + corners + " Kingdom " + verso + " Scoring " + score;
	}
}
