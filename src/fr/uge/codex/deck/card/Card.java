package fr.uge.codex.deck.card;


public interface Card {
	boolean turned();
	CornerType[] getRecto();
	CornerType[] getVerso();
	ResourceType getKingdom();
}
