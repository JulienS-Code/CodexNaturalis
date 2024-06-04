package fr.uge.codex.deck.card;


public interface Card {
	boolean turned();
	CornerType[] getRecto();
	ResourceType getKingdom();
}
