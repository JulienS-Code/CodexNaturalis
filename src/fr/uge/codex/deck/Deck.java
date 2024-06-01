package fr.uge.codex.deck;

import java.io.IOException;

import fr.uge.codex.deck.card.ResourceCard;

public class Deck {
	private final String file = "data/deck.txt";
	private ResourcesDeck resourcesDeck;
	private GoldsDeck goldsDeck;
	private ObjectivesDeck objectivesDeck;
	private StartersDeck startersDeck;
	
	public Deck() {
		try {
			startersDeck = new StartersDeck(file);
			resourcesDeck = new ResourcesDeck(file);
			goldsDeck = new GoldsDeck(file);
			objectivesDeck = new ObjectivesDeck(file);
		} catch (IOException e) {
			System.err.println("Erreur lors de la création des decks : " + e.getMessage());
			System.out.println("\nDes decks vides ont été créés\n");
			startersDeck = null;
			resourcesDeck = null;
			goldsDeck = null;
			objectivesDeck = null;
		}
	}
	
	public ResourceCard getFirstCard() {
		return resourcesDeck.resourceCards.getFirst();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StartersDeck : ");
		builder.append(startersDeck);
		builder.append("\nResourcesDeck : ");
		builder.append(resourcesDeck);
		builder.append("\nGoldsDeck : ");
		builder.append(goldsDeck);
		builder.append("\nObjectivesDeck : ");
		builder.append(objectivesDeck);
		return builder.toString();
	}
	
}
