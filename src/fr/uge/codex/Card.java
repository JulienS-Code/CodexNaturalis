package fr.uge.codex;

import java.util.List;
import java.util.Objects;

public class Card {
	List<ResourceType> corners;
	ResourceType kingdom;
	int score;
	
	public enum ResourceType{
		Animal,
		Plant,
		Fungi,
		Insect,
	}
	
	public Card(List<ResourceType> corners, ResourceType kingdom, int score) {
		// ou ResourceType[] corners ?
		Objects.requireNonNull(corners, "corners doesn't register");
		if (corners.size() != 4) {
			throw new IllegalArgumentException("must have 4 corners in list");
		}
		this.corners = corners;
		this.kingdom = Objects.requireNonNull(kingdom);
		if (score < 0) {
			throw new IllegalArgumentException("score value invalid");
		}
	}
	
	// corner
	// recto / verso
	// Type ressources (4)
	// Score
	// Cost (goldcard)
}
