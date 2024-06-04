package fr.uge.codex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.CursorCard;

public class Grid {
    private Map<Point, Card> grid;
    private List<Point> order;  // Pour stocker l'ordre d'insertion
    private Point cursorPoint; // Pour stocker le curseur

    /**
     * Constructs a new Grid.
     */
    public Grid() {
        grid = new HashMap<>();
        order = new ArrayList<>();
        cursorPoint = null;
    }
    
    /**
     * Sets the cursor at the specified position.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     */
    public void setCursor(int x, int y) {
    	add(x, y, new CursorCard());
    }
    
    /**
     * Removes the cursor.
     */
    public void removeCursor() {
    	cursorPoint = null;
    }

    /**
     * Adds a card to the grid at the specified position.
     *
     * @param x     the x-coordinate.
     * @param y     the y-coordinate.
     * @param card  the card to add.
     * @return true if the card was added successfully, false otherwise.
     */
    public boolean add(int x, int y, Card card) {
        Point point = new Point(x, y);
        
        if (card instanceof CursorCard) {
            cursorPoint = point;
            return true;
        }
        // Si l'on ajoute une vraie carte le curseur n'est plus nécessaire
        cursorPoint = null;
        
        if (grid.containsKey(point)) {
            return false; // emplacement déjà pris
        }
        
        grid.put(point, card);
        order.add(point);
        return true;
    }

    /**
     * Gets the card at the specified position.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the card at the specified position, or null if no card is present.
     */
    public Card get(int x, int y) {
        return get(new Point(x, y));
    }
    
    /**
     * Gets the card at the specified position.
     *
     * @param point the position.
     * @return the card at the specified position, or null if no card is present.
     */
    public Card get(Point point) {
		return grid.get(point);
	}

    /**
     * Removes the card at the specified position.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if a card was removed, false otherwise.
     */
    public boolean remove(int x, int y) {
        Point point = new Point(x, y);
        if (grid.containsKey(point)) {
            grid.remove(point);
            order.remove(point);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of all the positions in the grid.
     *
     * @return a list of all the positions in the grid.
     */
    public ArrayList<Point> keys() {
        return new ArrayList<>(grid.keySet());
    }

    /**
     * Returns a list of all the cards in the grid.
     *
     * @return a list of all the cards in the grid.
     */
    public ArrayList<Card> values() {
        return new ArrayList<>(grid.values());
    }

    /**
     * Checks if the grid is empty.
     *
     * @return true if the grid is empty, false otherwise.
     */
    public boolean isEmpty() {
        return grid.isEmpty();
    }

    /**
     * Returns a set of all the entries in the grid.
     *
     * @return a set of all the entries in the grid.
     */
    public Set<Map.Entry<Point, Card>> entrySet() {
        return grid.entrySet();
    }
    
    /**
     * Returns the number of cards in the grid.
     *
     * @return the number of cards in the grid.
     */
	public int size() {
		return grid.size();
	}

    /**
     * Returns the index of the specified card in the insertion order.
     *
     * @param card the card to search for.
     * @return the index of the card in the insertion order, or -1 if not found.
     */
    public int index(Card card) {
        for (int i = 0; i < order.size(); i++) {
            Point point = order.get(i);
            if (grid.get(point).equals(card)) {
                return i;
            }
        }
        return -1; // not found
    }
    
    /**
     * Returns the insertion order of the cards.
     *
     * @return the insertion order of the cards.
     */
    public List<Point> getOrder() {
        return order;
    }
    
    /**
     * Returns the position of the cursor.
     *
     * @return the position of the cursor.
     */
    public Point getCursorPoint() {
    	return cursorPoint;
    }
}
