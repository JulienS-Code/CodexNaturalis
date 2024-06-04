package fr.uge.codex.player;

import fr.uge.codex.deck.Deck;
import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ResourceCard;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the game, with an inventory, a hand of cards, and a draw pile.
 */
public class Player {
    private final int id;
    private final Inventory inventory;
    private final List<Card> hand;
    private final List<Card> drawPile;

    /**
     * Constructs a player with the given ID.
     * @param id The ID of the player.
     */
    public Player(int id) {
        this.id = id;
        this.inventory = new Inventory();
        this.hand = new ArrayList<Card>();
        this.drawPile = new ArrayList<Card>();
    }

	/**
	 * Gets the player's inventory.
	 * @return The player's inventory.
	 */
	public Inventory inventory() {
		return inventory;
	}
	
    /**
     * Initializes the draw pile with resource and gold cards from the provided deck.
     * @param deck The deck to initialize the draw pile from.
     */
    public void initPile(Deck deck) {
        Objects.requireNonNull(deck);

        drawPile.add(deck.pickResourceCard());
        drawPile.add(deck.pickGoldCard());
    }

    /**
     * Gets the ID of the player.
     * @return The ID of the player.
     */
    public int id() {
        return id;
    }

    /**
     * Gets the player's current hand of cards.
     * @return The list of cards in the player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Adds the given card to the player's inventory.
     * @param card The card to be added to the inventory.
     */
    public void addCardToInventory(Card card) {
        inventory.updateInventory(card);
    }

    /**
     * Picks a card from the draw pile at the given index and adds it to the player's hand.
     * @param index The index of the card in the draw pile.
     * @param deck The deck to pick the card from.
     * @return True if the card was successfully picked and added to the hand, false otherwise.
     */
    public boolean pick(int index, Deck deck) {
        Objects.requireNonNull(deck);
        if (index < 0 || index >= drawPile.size()) {
            return false;
        }
        return pick(drawPile.get(index), deck);
    }

    /**
     * Picks the specified card from the draw pile and adds it to the player's hand.
     * @param card The card to be picked.
     * @param deck The deck to pick the card from.
     * @return True if the card was successfully picked and added to the hand, false otherwise.
     */
    public boolean pick(Card card, Deck deck) {
        Objects.requireNonNull(card);
        Objects.requireNonNull(deck);

        if (hand.size() >= 3) {
            return false;
        }

        int index = drawPile.indexOf(card);
        if (index == -1) {
            return false;
        }

        hand.add(card);

        if (card instanceof ResourceCard) {
            drawPile.set(index, deck.pickResourceCard());
        } else if (card instanceof GoldCard) {
            drawPile.set(index, deck.pickGoldCard());
        }

        return true;
    }

    /**
     * Removes the specified card from the player's hand.
     * @param card The card to be removed.
     * @return True if the card was successfully removed, false otherwise.
     */
    public boolean remove(Card card) {
        Objects.requireNonNull(card);
        return hand.remove(card);
    }

    /**
     * Draws the player's hand of cards on the screen.
     * @param g2d The graphics context to draw on.
     * @param width The width of the drawing area.
     * @param height The height of the drawing area.
     */
    public void drawHand(Graphics2D g2d, double width, double height) {
        Objects.requireNonNull(g2d);

        if (hand.size() == 0) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 26));
            g2d.drawString("Aucune carte", 850, (int) height - 160);
            return;
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 26));
        g2d.drawString("Vos cartes (cliquez pour sélectionner)", 850, (int) height - 160);

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            card.draw(g2d, 830 + (200) * (i), 950, 1.4);
        }
    }

    /**
     * Draws the player's draw pile on the screen.
     * @param g2d The graphics context to draw on.
     * @param width The width of the drawing area.
     * @param height The height of the drawing area.
     */
    public void drawPile(Graphics2D g2d, double width, double height) {
        Objects.requireNonNull(g2d);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 26));
        g2d.drawString("Pioche (cliquez pour piocher)", 220, (int) height - 160);

        for (int i = 0; i < drawPile.size(); i++) {
            Card card = drawPile.get(i);
            card.draw(g2d, 230 + (200) * (i), 950, 1.4);
        }
    }

    /**
     * Turns all the cards in the player's hand and draw pile face down.
     */
    public void turnCards() {
        for (Card card : hand) {
            card.turn();
        }

        for (Card card : drawPile) {
            card.turn();
        }
    }

    /**
     * Returns a string representation of the player, including their ID and current score.
     * @return The string representation of the player.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Player ");
        builder.append(id);
        builder.append(" : ");
        builder.append(inventory.score());
        builder.append(" points ");

		return builder.toString();
	}
}
