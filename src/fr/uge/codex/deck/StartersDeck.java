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

/**
 * Represents a deck of starter cards.
 */
public class StartersDeck {
    private final List<StarterCard> starterCards;

    /**
     * Constructs a new deck of starter cards with an empty list of cards.
     */
    public StartersDeck() {
        this.starterCards = new ArrayList<>();
    }

    /**
     * Adds a starter card to the deck.
     *
     * @param card the starter card to add.
     * @throws NullPointerException if the provided card is null.
     */
    public void add(StarterCard card) {
        Objects.requireNonNull(card);
        starterCards.add(card);
    }

    /**
     * Removes a starter card from the deck.
     *
     * @param card the starter card to remove.
     * @throws NullPointerException if the provided card is null.
     */
    public void remove(StarterCard card) {
        Objects.requireNonNull(card);
        starterCards.remove(card);
    }

    /**
     * Creates a starter card from the provided line of data and adds it to the deck.
     *
     * @param parsedLine the line of data to parse.
     * @throws NullPointerException if the provided parsedLine is null.
     */
    public void createCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);
        starterCards.add(makeCardFromLine(parsedLine));
    }

    /**
     * Parses a line of data and creates a starter card from it.
     *
     * @param parsedLine the line of data to parse.
     * @return the starter card created from the parsed line.
     * @throws NullPointerException if the provided parsedLine is null.
     * @throws IllegalArgumentException if the provided parsedLine contains invalid data.
     */
    private StarterCard makeCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);

        List<ResourceType> recto = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            recto.add(ResourceType.valueOf(parsedLine[i].substring(2)));
        }

        List<CornerType> verso = new ArrayList<>();
        for (int i = 7; i <= 10; i++) {
            verso.add(parseCornerType(parsedLine[i]));
        }

        List<ResourceType> resources = new ArrayList<>();
        for (int i = 12; i < parsedLine.length; i++) {
            resources.add(ResourceType.valueOf(parsedLine[i].substring(2)));
        }

        return new StarterCard(recto, verso, resources);
    }

    /**
     * Parses a corner type from the provided string.
     *
     * @param word the string representing the corner type.
     * @return the parsed corner type.
     * @throws IllegalArgumentException if the provided string is not a valid corner type.
     */
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

    /**
     * Shuffles the deck of starter cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(starterCards);
    }

    /**
     * Picks a starter card from the deck.
     *
     * @return the picked starter card.
     * @throws NoSuchElementException if the deck is empty.
     */
    public StarterCard pick() {
        if (starterCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
        return starterCards.remove(0);
    }

    @Override
    public String toString() {
        return starterCards.toString();
    }
}
