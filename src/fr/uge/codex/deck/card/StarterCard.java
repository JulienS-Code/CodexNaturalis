package fr.uge.codex.deck.card;

import java.util.List;
import java.util.Objects;

public record StarterCard(List<ResourceType> recto, List<CornerType> verso, List<ResourceType> resources) implements Card {
	
	public StarterCard {
		Objects.requireNonNull(recto);
		if (recto.size() != 4) {
			throw new IllegalArgumentException("Corners must contain exactly 4 elements");
		}
		Objects.requireNonNull(verso);
		if (verso.size() != 4) {
			throw new IllegalArgumentException("Corners must contain exactly 4 elements");
		}
		Objects.requireNonNull(resources);
		boolean turned = false;
	}

	@Override
	public boolean turned() {
		return this.turned();
	}

	@Override
	public CornerType[] getRecto() {
		return recto.toArray(new CornerType[0]);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nStarterCard = recto : ");
		builder.append(recto);
		builder.append(", verso : ");
		builder.append(verso);
		builder.append(" et de resources : ");
		builder.append(resources);
		return builder.toString();
	}
}
