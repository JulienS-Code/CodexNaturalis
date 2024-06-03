package fr.uge.codex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.GhostCard;

public class Grid {
    private Map<Point, Card> grid;
    private List<Point> order;  // Pour stocker l'ordre d'insertion
    private Point ghostPoint; // Pour stocker le curseur
    private GhostCard ghostCard;

    public Grid() {
        grid = new HashMap<>();
        order = new ArrayList<>();
        ghostPoint = null;
        ghostCard = new GhostCard();
    }
    
    public void setGhost(int x, int y) {
    	add(x, y, new GhostCard());
    }
    
    public void removeGhost() {
    	ghostPoint = null;
    }

    public boolean add(int x, int y, Card card) {
        Point point = new Point(x, y);
        
        if (card instanceof GhostCard) {
            ghostPoint = point;
            return true;
        }
        // Si l'on ajoute une vraie carte le curseur n'est plus nécessaire
        ghostPoint = null;
        
        if (grid.containsKey(point)) {
            return false; // emplacement déjà pris
        }
        
        grid.put(point, card);
        order.add(point);
        return true;
    }

    public Card get(int x, int y) {
        return get(new Point(x, y));
    }
    
    public Card get(Point point) {
		return grid.get(point);
	}

    public boolean remove(int x, int y) {
        Point point = new Point(x, y);
        if (grid.containsKey(point)) {
            grid.remove(point);
            order.remove(point);
            return true;
        }
        return false;
    }

    public ArrayList<Point> keys() {
        return new ArrayList<>(grid.keySet());
    }

    public ArrayList<Card> values() {
        return new ArrayList<>(grid.values());
    }

    public boolean isEmpty() {
        return grid.isEmpty();
    }

    public Set<Map.Entry<Point, Card>> entrySet() {
        return grid.entrySet();
    }
    
	public int size() {
		return grid.size();
	}

    public int index(Card card) {
        for (int i = 0; i < order.size(); i++) {
            Point point = order.get(i);
            if (grid.get(point).equals(card)) {
                return i;
            }
        }
        return -1; // not found
    }
    
    public List<Point> getOrder() {
        return order;
    }
    
    public Point getGhostPoint() {
    	return ghostPoint;
    }
    
    public GhostCard getGhostCard() {
    	return ghostCard;
    }
}
