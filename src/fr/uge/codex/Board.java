package fr.uge.codex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ResourceCard;
import fr.umlv.zen5.ApplicationContext;

public class Board {
	Grid grid;
	double scale = 1;
	int width;
	int height;
	ApplicationContext ctx;
	boolean needsReset = false;
	int Xoffset = 0;
	int Yoffset = 0;
	
	public Board(ApplicationContext context) {
		Objects.requireNonNull(context);
		
		grid = new Grid();
		width = (int) context.getScreenInfo().getWidth();
		height = (int) context.getScreenInfo().getHeight();
		ctx = context;
	}
	
	public boolean add(Card card, int x, int y) {
		Objects.requireNonNull(card);
		
		if (grid.get(x, y) != null) {
			return false;
		}
		grid.add(x, y, card);
		return true;
		
		// TODO: vérifier si le coin est disponible avant de poser la carte
		// on ne peut pas poser sur un coin Invisible ou s'il n'y a pas de carte voisine
	}
	
	public boolean remove(int x, int y) {
		if (grid.get(x, y) == null) {
			return false;
		}
		grid.remove(x, y);
		return true;
	}
	
	public Card pop(int x, int y) {
		Card card = grid.get(x, y);
		if (card == null) {
			return null;
		}
		grid.remove(x, y);
		return card;
	}
	
    public void display(Graphics2D g2d) {
    	Objects.requireNonNull(g2d);
    	
        if (grid.isEmpty()) return;
        
        if (needsReset) {
        	erase(g2d);
        	needsReset = false;
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point point : grid.keys()) {
            if (point.x < minX) minX = point.x;
            if (point.x > maxX) maxX = point.x;
            if (point.y < minY) minY = point.y;
            if (point.y > maxY) maxY = point.y;
        }

        int scaledOffsetX = (int) ((120 - 24) * scale);
        int scaledOffsetY = (int) ((80 - 32) * scale);

        int gridWidth = (maxX - minX + 1) * scaledOffsetX;
        int gridHeight = (maxY - minY + 1) * scaledOffsetY;
        int offsetX = (width - gridWidth) / 2 - (minX * scaledOffsetX);
        int offsetY = (height - gridHeight) / 2 - (minY * scaledOffsetY);
        
        // on affiche les cartes les plus récentes au dessus
        List<Point> order = grid.getOrder(); 
        
        for (Point point : order) {
            Card card = grid.get(point);
            int displayX = point.x * scaledOffsetX + offsetX + Xoffset;
            int displayY = point.y * scaledOffsetY + offsetY + Yoffset;
            if (card instanceof ResourceCard) {
                ((ResourceCard) card).draw(g2d, displayX, displayY, scale);
            } else if (card instanceof GoldCard) {
                ((GoldCard) card).draw(g2d, displayX, displayY, scale);
            } // TODO: polymorphisme pour Card.draw()
        }
    }
    
    public void erase(Graphics2D g2d) {
    	g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
    }
    
    public void zoomIn() {
		scale *= 1.1;
		needsReset = true;
	}
	
	public void zoomOut() {
		scale *= 0.9;
		needsReset = true;
	}
	
	public void zoomReset() {
		scale = 1;
		needsReset = true;
	}
	
	public void reset() {
		grid = new Grid();
		needsReset = true;
	}
	
	public void move(int x, int y) {
		Xoffset += x;
		Yoffset += y;
		needsReset = true;
	}
	
	public void moveReset() {
		Xoffset = 0;
		Yoffset = 0;
		needsReset = true;
	}
}