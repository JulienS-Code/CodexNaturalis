package fr.uge.codex.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.deck.Deck;
import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ResourceCard;

public class Player {
	private final int id;
	private final Inventory inventory;
	private final List<Card> hand;
	private final List<Card> drawPile;
	
	public Player(int id) {
		this.id = id;
		this.inventory = new Inventory();
		this.hand = new ArrayList<Card>();
		this.drawPile = new ArrayList<Card>();
	}
	
	public void initPile(Deck deck) {
		Objects.requireNonNull(deck);
		
		drawPile.add(deck.pickResourceCard());
		drawPile.add(deck.pickGoldCard());
	}

	public int id() {
		return id;
	}

	public List<Card> getHand() {
		return hand;
	}
	
	public boolean pick(int index, Deck deck) {
		return pick(drawPile.get(index), deck);
	}
	
	public boolean pick(Card card, Deck deck) {
		Objects.requireNonNull(card);
		Objects.requireNonNull(deck);
		
		if (hand.size() >= 3) {
			return false;
		}
		
		if (!drawPile.contains(card)) {
			return false;
		}
		
		hand.add(card);
		drawPile.remove(card);
		
		if (card instanceof ResourceCard) {
			drawPile.add(deck.pickResourceCard());
		} else if (card instanceof GoldCard) {
			drawPile.add(deck.pickGoldCard());
		}

		return true;
	}
	
	public boolean remove(Card card) {
		Objects.requireNonNull(card);
		return hand.remove(card);
	}
	
	public void drawHand(Graphics2D g2d, double width, double height) {
		// Draw the cards at the bottom of the screen, from left to right
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
			card.draw(g2d, 830 + (200) * (i), 960, 1.4);
		}
	}
	
	public void drawPile(Graphics2D g2d, double width, double height) {
		Objects.requireNonNull(g2d);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 26));
		g2d.drawString("Pioche (cliquez pour piocher)", 220, (int) height - 150);
		
		for (int i = 0; i < drawPile.size(); i++) {
			Card card = drawPile.get(i);
			card.draw(g2d, 230 + (200) * (i), 960, 1.4);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player ");
		builder.append(id);
		builder.append(" qui a ");
		builder.append(inventory.score());
		builder.append(" points ");
		return builder.toString();
	}
}
