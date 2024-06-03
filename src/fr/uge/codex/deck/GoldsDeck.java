package fr.uge.codex.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.OtherCornerType;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;


public class GoldsDeck {
	List<GoldCard> goldCards;
	
	public void add(GoldCard card) {
		Objects.requireNonNull(card);
		goldCards.add(card);
	}
	
	public void remove(GoldCard card) {
		Objects.requireNonNull(card);
		goldCards.remove(card);
	}

	public GoldsDeck() {
		this.goldCards = new ArrayList<GoldCard>();
	}
	
	public void createCardFromLine(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		goldCards.add(makeCardFromLine(parsedLine));
	}
	
	public GoldCard makeCardFromLine(String[] parsedLine) {
		
		CornerType[] recto = parseResources(parsedLine);
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		List<CornerType> cost = parseCost(parsedLine);
		Scoring score = Scoring.makeScoring(parsedLine[parsedLine.length-1]);
		
		return new GoldCard(recto, kingdom, cost, score);
	}
	
	public CornerType[] parseResources(String[] parsedLine) {
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
	
	public List<CornerType> parseCost(String[] parsedLine) {
		List<CornerType> cost = new ArrayList<CornerType>();
		boolean foundCost = false;
		for (String word : parsedLine) {
			if (foundCost && !word.equals("Scoring")) {
				for (ResourceType type : ResourceType.values()) {
	                if (word.equals(type.toString())) {
	                    cost.add(type);
	                    break;
	                }
	            }
			} else if (word.equals("Cost")) {
				foundCost = true;
			} else if (word.equals("Scoring")) {
				break;
			} 
		}
		return cost;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(goldCards);
	}
	
	public GoldCard pick() {
		if (goldCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
		return goldCards.removeFirst();
	}

	@Override
	public String toString() {
		return goldCards.toString();
	}
	
	
}
