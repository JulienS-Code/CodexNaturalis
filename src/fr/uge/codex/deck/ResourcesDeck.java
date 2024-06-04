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

/**
 * Represents a deck of resource cards.
 */
public class ResourcesDeck {
    private final List<ResourceCard> resourceCards;

    /**
     * Constructs a new deck of resource cards with an empty list of cards.
     */
    public ResourcesDeck() {
        this.resourceCards = new ArrayList<>();
    }

    /**
     * Adds a resource card to the deck.
     *
     * @param card the resource card to add.
     * @throws NullPointerException if the provided card is null.
     */
    public void add(ResourceCard card) {
        Objects.requireNonNull(card);
        resourceCards.add(card);
    }

    /**
     * Removes a resource card from the deck.
     *
     * @param card the resource card to remove.
     * @throws NullPointerException if the provided card is null.
     */
    public void remove(ResourceCard card) {
        Objects.requireNonNull(card);
        resourceCards.remove(card);
    }

    /**
     * Creates a resource card from the provided line of data and adds it to the deck.
     *
     * @param parsedLine the line of data to parse.
     * @throws NullPointerException if the provided parsedLine is null.
     */
    public void createCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);
        resourceCards.add(makeCardFromLine(parsedLine));
    }

    /**
     * Parses a line of data and creates a resource card from it.
     *
     * @param parsedLine the line of data to parse.
     * @return the resource card created from the parsed line.
     * @throws NullPointerException if the provided parsedLine is null.
     */
    public ResourceCard makeCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);

        CornerType[] recto = parseResources(parsedLine);
        ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
        Scoring score = Scoring.makeScoring(parsedLine[9]);

        return new ResourceCard(recto, kingdom, score);
    }

    /**
     * Parses the resource corners from the provided line of data.
     *
     * @param parsedLine the line of data to parse.
     * @return an array of corner types representing the resource corners.
     * @throws NullPointerException if the provided parsedLine is null.
     */
    private CornerType[] parseResources(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);
        CornerType[] recto = new CornerType[4];
        for (int i = 2; i <= 5; i++) {
            if (parsedLine[i].startsWith("R:")) {
                recto[i - 2] = ResourceType.valueOf(parsedLine[i].substring(2));
            } else if (parsedLine[i].startsWith("A:")) {
                recto[i - 2] = ArtifactType.valueOf(parsedLine[i].substring(2));
            } else if (parsedLine[i].equals("Empty")) {
                recto[i - 2] = OtherCornerType.Empty;
            } else if (parsedLine[i].equals("Invisible")) {
                recto[i - 2] = OtherCornerType.Invisible;
            }
        }
        return recto;
    }

    /**
     * Shuffles the deck of resource cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(resourceCards);
    }

    /**
     * Picks a resource card from the deck.
     *
     * @return the picked resource card.
     * @throws NoSuchElementException if the deck is empty.
     */
    public ResourceCard pick() {
        if (resourceCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
        return resourceCards.remove(0);
    }

    @Override
    public String toString() {
        return resourceCards.toString();
    }
}
