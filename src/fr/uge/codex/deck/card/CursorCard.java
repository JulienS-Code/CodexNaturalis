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
        List<CornerType> cost = new ArrayList<CornerType>();
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
        cost.add(OtherCornerType.Empty);
    }
	
    @Override
    public boolean turned() {
        return false;
    }

    @Override
	public CornerType[] getRecto() {
		return new CornerType[]{OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
	}
    
    @Override
	public CornerType[] getVerso() {
		return new CornerType[]{OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
	}
    
    @Override
    public ResourceType getKingdom() {
    	return null;
    }
    
    @Override
    public void draw(Graphics2D g2d, double x, double y, double scale) {
	    draw(g2d, x, y, scale);
    }
    
    public static void drawCursor(Graphics2D g2d, double x, double y, double scale) {
        int width = (int) (120 * scale);
        int height = (int) (80 * scale);
        g2d.setColor(new Color(255, 255, 0, 102));
        g2d.drawRect((int) x-1, (int) y-1, width + 2, height + 2);
    }
    
    public void turn() {
    	return;
    }

    @Override
    public String toString() {
        return "CursorCard{}";
    }
}