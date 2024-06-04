package fr.uge.codex.deck.card;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public record CursorCard() implements Card {

    public CursorCard {
    	CornerType[] recto = {OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
        if (recto.length != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        Scoring score = Scoring.none();
        List<CornerType> cost = new ArrayList<CornerType>();
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
        boolean turned = false;
    }

    @Override
    public boolean turned() {
        return turned();
    }

    @Override
    public CornerType[] getRecto() {
        CornerType[] recto = {OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
        return recto;
    }
    
    @Override
    public ResourceType getKingdom() {
    	return null;
    }
    
    public static void draw(Graphics2D g2d, double x, double y, double scale) {
        int width = (int) (120 * scale);
        int height = (int) (80 * scale);
        g2d.setColor(new Color(255, 255, 0, 102));
        g2d.drawRect((int) x, (int) y, width, height);
    }

    @Override
    public String toString() {
        return "GhostCard()";
    }
}