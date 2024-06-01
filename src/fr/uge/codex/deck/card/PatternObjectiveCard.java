package fr.uge.codex.deck.card;

import java.util.List;
import java.util.Objects;

public class PatternObjectiveCard extends ObjectiveCard {
	private final List<String> corners;

	public PatternObjectiveCard(List<CornerType> criteria, List<String> corners, Scoring score) {
		super(ObjectiveType.Pattern, criteria, score);
		this.corners = Objects.requireNonNull(corners);
	}

	public List<String> getCorners() {
		return corners;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nObjectiveCard de type Pattern, de critères : ");
		builder.append(criteria);
		builder.append(" sur les angles ");
		builder.append(corners);
		builder.append(" et de score (type = ");
		builder.append(score.scoringType());
		builder.append(") qui vaut ");
		builder.append(score.points());
		return builder.toString();
	}
	
}
