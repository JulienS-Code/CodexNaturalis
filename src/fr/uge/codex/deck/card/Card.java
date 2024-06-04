package fr.uge.codex.deck.card;

import java.awt.Graphics2D;

public interface Card {
	boolean turned();
	CornerType[] getRecto();
	CornerType[] getVerso();
	ResourceType getKingdom();
	void draw(Graphics2D g2d, double x, double y, double scale);
}
