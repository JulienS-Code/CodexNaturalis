package fr.uge.codex.card;

import java.util.List;
import java.util.Objects;

public class StarterCard implements Card {
	private final CornerType[] recto;
	private final CornerType[] verso;
	private final List<ResourceType> resources;
	

	public StarterCard(CornerType[] recto, CornerType[] verso, List<ResourceType> resources) {
		super();
		this.recto = Objects.requireNonNull(recto);
		if (recto.length != 4) {
			throw new IllegalArgumentException("Corners must contain exactly 4 elements");
		}
		this.verso = Objects.requireNonNull(verso);
		if (verso.length != 4) {
			throw new IllegalArgumentException("Corners must contain exactly 4 elements");
		}
		this.resources = Objects.requireNonNull(resources);
	}

	@Override
	public CornerType[] getRecto() {
		return recto;
	}
	
}
