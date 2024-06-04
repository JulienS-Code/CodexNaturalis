package fr.uge.codex.deck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ObjectiveCard;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.codex.deck.card.StarterCard;

/**
 * Represents a deck containing different types of cards: starters, resources, golds, and objectives.
 */
public class Deck {
    private final String file = "data/deck.txt";
    private ResourcesDeck resourcesDeck;
    private GoldsDeck goldsDeck;
    private ObjectivesDeck objectivesDeck;
    private StartersDeck startersDeck;

    /**
     * Constructs a new deck by reading card data from a file.
     * Initializes the resources deck, golds deck, objectives deck, and starters deck.
     */
    public Deck() {
        startersDeck = new StartersDeck();
        resourcesDeck = new ResourcesDeck();
        goldsDeck = new GoldsDeck();
        objectivesDeck = new ObjectivesDeck();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsedLine = line.split(" ");
                switch (parsedLine[0]) {
                    case "StarterCard" -> startersDeck.createCardFromLine(parsedLine);
                    case "ResourceCard" -> resourcesDeck.createCardFromLine(parsedLine);
                    case "GoldCard" -> goldsDeck.createCardFromLine(parsedLine);
                    case "Objective" -> objectivesDeck.createCardFromLine(parsedLine);
                }
            }
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

    /**
     * Shuffles all decks in the main deck.
     */
    public void shuffleDeck() {
        startersDeck.shuffleDeck();
        resourcesDeck.shuffleDeck();
        goldsDeck.shuffleDeck();
        objectivesDeck.shuffleDeck();
    }

    /**
     * Picks a starter card from the deck.
     *
     * @return The picked starter card.
     * @throws IllegalStateException if the starters deck is not initialized.
     */
    public StarterCard pickStarterCard() {
        if (startersDeck != null) {
            return startersDeck.pick();
        }
        throw new IllegalStateException("StartersDeck is not initialized");
    }

    /**
     * Picks a resource card from the deck.
     *
     * @return The picked resource card.
     * @throws IllegalStateException if the resources deck is not initialized.
     */
    public ResourceCard pickResourceCard() {
        if (resourcesDeck != null) {
            return resourcesDeck.pick();
        }
        throw new IllegalStateException("ResourcesDeck is not initialized");
    }

    /**
     * Picks a gold card from the deck.
     *
     * @return The picked gold card.
     * @throws IllegalStateException if the golds deck is not initialized.
     */
    public GoldCard pickGoldCard() {
        if (goldsDeck != null) {
            return goldsDeck.pick();
        }
        throw new IllegalStateException("GoldsDeck is not initialized");
    }

    /**
     * Picks an objective card from the deck.
     *
     * @return The picked objective card.
     * @throws IllegalStateException if the objectives deck is not initialized.
     */
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
