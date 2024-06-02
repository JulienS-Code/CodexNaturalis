package fr.uge.codex.player;

import java.util.ArrayList;
import java.util.List;

import fr.uge.codex.deck.card.Card;

public class Player {
	private final int id;
	private int score;
	private final List<Card> hand;
	
	public Player(int id) {
		this.id = id;
		this.score = 0;
		this.hand = new ArrayList<Card>();
	}

	public int score() {
		return score;
	}

	public void addPoints(int pts) {
		this.score += pts;
	}

	public int id() {
		return id;
	}

	public List<Card> getHand() {
		return hand;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player ");
		builder.append(id);
		builder.append(" qui a ");
		builder.append(score);
		builder.append(" points ");
		return builder.toString();
	}
}
