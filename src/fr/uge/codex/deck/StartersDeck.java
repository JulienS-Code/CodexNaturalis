package fr.uge.codex.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.OtherCornerType;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.StarterCard;

public class StartersDeck {
	List<StarterCard> starterCards;
	
	public void add(StarterCard card) {
		Objects.requireNonNull(card);
		starterCards.add(card);
	}
	
	public void remove(StarterCard card) {
		Objects.requireNonNull(card);
		starterCards.remove(card);
	}
	
	public StartersDeck()  {
		this.starterCards = new ArrayList<StarterCard>();
	}
	
	public void createCardFromLine(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		starterCards.add(makeCardFromLine(parsedLine));
	}
	
	private StarterCard makeCardFromLine(String[] parsedLine) {
		
		List<ResourceType> recto = new ArrayList<ResourceType>();
		for (int i = 2; i <= 5; i++) {
	        recto.add(ResourceType.valueOf(parsedLine[i].substring(2)));
	    }
		
		List<CornerType> verso = new ArrayList<CornerType>();
		for (int i = 7; i <= 10; i++) {
	        verso.add(parseCornerType(parsedLine[i]));
	    }
		
		List<ResourceType> resources = new ArrayList<>();
	    for (int i = 12; i < parsedLine.length; i++) {
	        resources.add(ResourceType.valueOf(parsedLine[i].substring(2)));
	    }
		
		return new StarterCard(recto, verso, resources);
	}
	
	private CornerType parseCornerType(String word) {
	    if (word.startsWith("R:")) {
	        return ResourceType.valueOf(word.substring(2));
	    } else if (word.equals("Empty")) {
	        return OtherCornerType.Empty;
	    } else if (word.equals("Invisible")) {
	        return OtherCornerType.Invisible;
	    } else {
	        throw new IllegalArgumentException("Invalid CornerType: " + word);
	    }
	}
	
	public void shuffleDeck() {
		Collections.shuffle(starterCards);
	}
	
	public StarterCard pick() {
		if (starterCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
		return starterCards.removeFirst();
	}

	@Override
	public String toString() {
		return starterCards.toString();
	}
	
}




