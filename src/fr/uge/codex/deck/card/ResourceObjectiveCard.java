package fr.uge.codex.deck.card;

import java.util.List;

public class ResourceObjectiveCard extends ObjectiveCard {

	public ResourceObjectiveCard(List<CornerType> criteria, Scoring score) {
		super(ObjectiveType.Resource, criteria, score);
	}

}
