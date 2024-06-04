package fr.uge.codex.deck.card;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

public class ObjectiveCard implements Card {
	private final ObjectiveType type;
	protected final List<CornerType> criteria;
	protected final Scoring score;

	public ObjectiveCard(ObjectiveType type, List<CornerType> criteria, Scoring score) {
		super();
		this.type = Objects.requireNonNull(type);
		this.criteria = Objects.requireNonNull(criteria);
		this.score = Objects.requireNonNull(score);
	}
	
	@Override
	public CornerType[] getVerso() {
		return new CornerType[]{OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
	}
	
	public ObjectiveType getType() {
		return type;
	}
	
	public List<CornerType> getCriteria() {
		return criteria;
	}
	
	public Scoring getScore() {
		return score;
	}
	
	@Override
	public boolean turned() {
		return false;
	}
	
	@Override
	public CornerType[] getRecto() {
		return criteria.toArray(new CornerType[0]);
	}
	
	@Override
	public ResourceType getKingdom() {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nObjectiveCards de type ");
		builder.append(type);
		builder.append(" de critères : ");
		builder.append(criteria);
		builder.append(" et de score (type = ");
		builder.append(score.scoringType());
		builder.append(") qui vaut ");
		builder.append(score.points());
		return builder.toString();
	}

	@Override
	public void draw(Graphics2D g2d, double x, double y, double scale) {
		return;
	}

}

