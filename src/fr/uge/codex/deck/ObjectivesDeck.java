package fr.uge.codex.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.ArtifactObjectiveCard;
import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.ObjectiveCard;
import fr.uge.codex.deck.card.PatternObjectiveCard;
import fr.uge.codex.deck.card.ResourceObjectiveCard;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;

/**
 * Represents a deck of objectives cards.
 */
public class ObjectivesDeck {
    private final List<ObjectiveCard> objectiveCards;

    /**
     * Constructs a new deck of objectives cards with an empty list of cards.
     */
    public ObjectivesDeck() {
        this.objectiveCards = new ArrayList<>();
    }

    /**
     * Creates an objective card from the provided line of data and adds it to the deck.
     *
     * @param parsedLine the line of data to parse.
     * @throws NullPointerException if the provided parsedLine is null.
     */
    public void createCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);
        objectiveCards.add(makeCardFromLine(parsedLine));
    }

    /**
     * Parses a line of data and creates an objective card from it.
     *
     * @param parsedLine the line of data to parse.
     * @return the objective card created from the parsed line.
     * @throws NullPointerException if the provided parsedLine is null.
     * @throws IllegalArgumentException if the objective type in the parsed line is unknown.
     */
    public ObjectiveCard makeCardFromLine(String[] parsedLine) {
        Objects.requireNonNull(parsedLine);

        String currentType = parsedLine[1];
        List<CornerType> criteria = new ArrayList<>();
        List<String> corners = new ArrayList<>();
        Scoring score = Scoring.makeScoring(parsedLine[parsedLine.length - 1]);

        int i = 2;
        switch (currentType) {
            case "Pattern":
                while (!parsedLine[i].equals("Scoring")) {
                    if (isCorner(parsedLine[i])) {
                        corners.add(parsedLine[i]);
                    } else {
                        for (ResourceType type : ResourceType.values()) {
                            if (parsedLine[i].equals(type.toString())) {
                                criteria.add(type);
                                break;
                            }
                        }
                    }
                    i++;
                }
                return new PatternObjectiveCard(criteria, corners, score);

            case "Resource":
                while (!parsedLine[i].equals("Scoring")) {
                    for (ResourceType type : ResourceType.values()) {
                        if (parsedLine[i].equals(type.toString())) {
                            criteria.add(type);
                            break;
                        }
                    }
                    i++;
                }
                return new ResourceObjectiveCard(criteria, score);

            case "Artifact":
                while (!parsedLine[i].equals("Scoring")) {
                    for (ArtifactType type : ArtifactType.values()) {
                        if (parsedLine[i].equals(type.toString())) {
                            criteria.add(type);
                            break;
                        }
                    }
                    i++;
                }
                return new ArtifactObjectiveCard(criteria, score);

            default:
                throw new IllegalArgumentException("Unknown objective type: " + currentType);
        }
    }

    /**
     * Checks if a word represents a corner type.
     *
     * @param word the word to check.
     * @return true if the word represents a corner type, false otherwise.
     */
    private static boolean isCorner(String word) {
        return word.equals("TopLeft") || word.equals("TopRight") ||
                word.equals("BottomLeft") || word.equals("BottomRight") ||
                word.equals("Top") || word.equals("Bottom");
    }

    /**
     * Shuffles the deck of objective cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(objectiveCards);
    }

    /**
     * Picks an objective card from the deck.
     *
     * @return the picked objective card.
     * @throws NoSuchElementException if the deck is empty.
     */
    public ObjectiveCard pick() {
        if (objectiveCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
        return objectiveCards.remove(0);
    }

    @Override
    public String toString() {
        return objectiveCards.toString();
    }
}
