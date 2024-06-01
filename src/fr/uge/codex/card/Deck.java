package fr.uge.codex.card;

import java.io.IOException;

public class Deck {
	private final String file = "data/deck.txt";
	private ResourcesDeck resourcesDeck;
	private GoldsDeck goldsDeck;
	
	public Deck() {
		try {
			resourcesDeck = new ResourcesDeck(file);
			goldsDeck = new GoldsDeck(file);			
		} catch (IOException e) {
			System.err.println("Erreur lors de la création des decks : " + e.getMessage());
			System.out.println("\nDes decks vides ont été créés\n");
			resourcesDeck = null;
			goldsDeck = null;
		}
	}
	
	public ResourceCard getFirstCard() {
		return resourcesDeck.resourceCards.getFirst();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResourcesDeck : ");
		builder.append(resourcesDeck);
		builder.append("\nGoldsDeck : ");
		builder.append(goldsDeck);
		return builder.toString();
	}
	
}
