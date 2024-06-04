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

/**
 * Represents a deck of gold cards.
 */
public class GoldsDeck {
	List<GoldCard> goldCards;
	
    /**
     * Constructs a new gold deck with an empty list of cards.
     */
	public GoldsDeck() {
		this.goldCards = new ArrayList<GoldCard>();
	}
	
    /**
     * Adds a gold card to the deck.
     *
     * @param card the gold card to add.
     * @throws NullPointerException if the provided card is null.
     */
	public void add(GoldCard card) {
		Objects.requireNonNull(card);
		goldCards.add(card);
	}
	
    /**
     * Removes a gold card from the deck.
     *
     * @param card the gold card to remove.
     * @throws NullPointerException if the provided card is null.
     */
	public void remove(GoldCard card) {
		Objects.requireNonNull(card);
		goldCards.remove(card);
	}

    /**
     * Creates a gold card from the provided line of data and adds it to the deck.
     *
     * @param parsedLine the line of data to parse.
     * @throws NullPointerException if the provided parsedLine is null.
     */
	public void createCardFromLine(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		goldCards.add(makeCardFromLine(parsedLine));
	}
	
    /**
     * Parses a line of data and creates a gold card from it.
     *
     * @param parsedLine the line of data to parse.
     * @return the gold card created from the parsed line.
     * @throws NullPointerException if the provided parsedLine is null.
     */
	public GoldCard makeCardFromLine(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		
		CornerType[] recto = parseResources(parsedLine);
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		List<CornerType> cost = parseCost(parsedLine);
		Scoring score = Scoring.makeScoring(parsedLine[parsedLine.length-1]);
		
		return new GoldCard(recto, kingdom, cost, score);
	}
	
    /**
     * Parses the resource information from a parsed line of data.
     *
     * @param parsedLine the parsed line of data.
     * @return an array of corner types representing the resources.
     * @throws NullPointerException if the provided parsedLine is null.
     */
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
	
    /**
     * Parses the cost information from a parsed line of data.
     *
     * @param parsedLine the parsed line of data.
     * @return a list of corner types representing the cost.
     */
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
	
    /**
     * Shuffles the deck of gold cards.
     */
	public void shuffleDeck() {
		Collections.shuffle(goldCards);
	}
	
    /**
     * Picks a gold card from the deck.
     *
     * @return the picked gold card.
     * @throws NoSuchElementException if the deck is empty.
     */
	public GoldCard pick() {
		if (goldCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
		return goldCards.removeFirst();
	}

	/**
	 * Returns a string representation of the deck.
	 *
	 * @return the string representation of the deck.
	 */
	@Override
	public String toString() {
		return goldCards.toString();
	}
	
	
}
