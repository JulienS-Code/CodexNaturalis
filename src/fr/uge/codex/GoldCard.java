package fr.uge.codex;

import java.util.List;

public class GoldCard extends Card {
	private final ResourceType cost;
	
	public GoldCard(List<ResourceType> corners, ResourceType kingdom, int score, ResourceType cost) {
		super(corners, kingdom, score);
		this.cost = cost;
	}
}
