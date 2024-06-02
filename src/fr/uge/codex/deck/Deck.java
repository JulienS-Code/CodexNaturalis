package fr.uge.codex.deck;

import java.io.IOException;

import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ObjectiveCard;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.codex.deck.card.StarterCard;

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
			shuffleDeck();
			
		} catch (IOException e) {
			System.err.println("Erreur lors de la création des decks : " + e.getMessage());
			startersDeck = null;
			resourcesDeck = null;
			goldsDeck = null;
			objectivesDeck = null;
			System.err.println("-> Des decks vides ont été créés");
		}
	}
	
	public void shuffleDeck() {
		startersDeck.shuffleDeck();
		resourcesDeck.shuffleDeck();
		goldsDeck.shuffleDeck();
		objectivesDeck.shuffleDeck();
	}
	
	public StarterCard pickStarterCard() {
		if (startersDeck != null) {
			return startersDeck.pick();			
		}
		throw new IllegalStateException("StartersDeck is not initialized");
	}
	
	public ResourceCard pickResourceCard() {
		if (resourcesDeck != null) {
			return resourcesDeck.pick();
		}
		throw new IllegalStateException("ResourcesDeck is not initialized");
	}
	
	public GoldCard pickGoldCard() {
		if (goldsDeck != null) {
			return goldsDeck.pick();
		}
		throw new IllegalStateException("GoldsDeck is not initialized");
	}
	
	public ObjectiveCard pickObjectiveCard() {
		if (objectivesDeck != null) {
			return objectivesDeck.pick();
		}
		throw new IllegalStateException("ObjectivesDeck is not initialized");
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
