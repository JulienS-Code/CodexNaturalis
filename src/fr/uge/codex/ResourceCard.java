package fr.uge.codex;

import java.util.List;
import java.util.Objects;

public class ResourceCard extends Card {
	
	public ResourceCard(List<ResourceType> corners, ResourceType kingdom, int score) {
		super(corners, kingdom, score);
	}

	@Override
	public String toString() {
		return "ResourceCard Recto " + corners + " Kingdom " + kingdom + " Scoring " + score;
	}
}
