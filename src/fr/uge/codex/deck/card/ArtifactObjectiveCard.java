package fr.uge.codex.deck.card;

import java.util.List;

public class ArtifactObjectiveCard extends ObjectiveCard {
	public ArtifactObjectiveCard(List<CornerType> criteria, Scoring score) {
		super(ObjectiveType.Artifact, criteria, score);
	}
}