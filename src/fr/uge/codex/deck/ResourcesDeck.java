package fr.uge.codex.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.OtherCornerType;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;

public class ResourcesDeck{
	List<ResourceCard> resourceCards;
	
	public void add(ResourceCard card) {
		Objects.requireNonNull(card);
		resourceCards.add(card);
	}
	
	public void remove(ResourceCard card) {
		Objects.requireNonNull(card);
		resourceCards.remove(card);
	}

	public ResourcesDeck() {
		this.resourceCards = new ArrayList<ResourceCard>();
	}

	public void createCardFromLine(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		resourceCards.add(makeCardFromLine(parsedLine));
	}
	
	public ResourceCard makeCardFromLine(String[] parsedLine) {
		
		CornerType[] recto = parseResources(parsedLine);
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		Scoring score = Scoring.makeScoring(parsedLine[9]);
		
		return new ResourceCard(recto, kingdom, score);
	}
	
	private CornerType[] parseResources(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		CornerType[] recto = new CornerType[4];
		for (int i = 2; i <= 5; i++) {
			if (parsedLine[i].startsWith("R:")) {
				recto[i-2] = ResourceType.valueOf(parsedLine[i].substring(2));
			} else if (parsedLine[i].startsWith("A:" )) {
				recto[i-2] = ArtifactType.valueOf(parsedLine[i].substring(2));
			} else if (parsedLine[i].equals("Empty")) {
				recto[i-2] = OtherCornerType.Empty;
			} else if (parsedLine[i].equals("Invisible")) {
				recto[i-2] = OtherCornerType.Invisible;
			}
		}
		return recto;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(resourceCards);
	}
	
	public ResourceCard pick() {
		if (resourceCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
		return resourceCards.removeFirst();
	}

	@Override
	public String toString() {
		return resourceCards.toString();
	}
	
}



