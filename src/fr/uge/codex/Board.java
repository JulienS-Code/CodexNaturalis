package fr.uge.codex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.CursorCard;
import fr.uge.codex.deck.card.OtherCornerType;
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
    
    /**
     * Constructs a Board with the given ApplicationContext.
     * 
     * @param context the ApplicationContext of the game.
     * @throws NullPointerException if context is null.
     */
    public Board(ApplicationContext context) {
        Objects.requireNonNull(context);
        
        grid = new Grid();
        width = (int) context.getScreenInfo().getWidth();
        height = (int) context.getScreenInfo().getHeight();
        ctx = context;
    }
    
    /**
     * Adds a card to the board at the specified coordinates.
     * 
     * @param card the card to add.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the card was successfully added, false otherwise.
     * @throws NullPointerException if card is null.
     */
    public boolean add(Card card, int x, int y) {
        return add(card, x, y, false);
    }
    
    /**
     * Adds a card to the board at the specified coordinates with the option to set it as a starter card.
     * 
     * @param card the card to add.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param isStarter whether the card is a starter card.
     * @return true if the card was successfully added, false otherwise.
     * @throws NullPointerException if card is null.
     */
    public boolean add(Card card, int x, int y, boolean isStarter) {
        Objects.requireNonNull(card);

        if (card instanceof CursorCard) {
            grid.add(x, y, card); // Elle s'ajoute à grid.cursorCards
        }
        
        if (grid.get(x, y) != null) {
            // System.out.println("DEBUG: La case ("+x+","+y+") est déjà occupée");
            return false;
        }
        
        if (!isStarter) {
            if (!isPlayable(x, y)) {
                // System.out.println("DEBUG: La case ("+x+","+y+") n'est pas jouable");
                return false;
            }
        }
        
        grid.add(x, y, card);
        return true;
        
        // TODO: vérifier si le coin est disponible avant de poser la carte
        // on ne peut pas poser sur un coin Invisible ou s'il n'y a pas de carte voisine
    }
    
    /**
     * Removes a card from the board at the specified coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the card was successfully removed, false otherwise.
     */
    public boolean remove(int x, int y) {
        if (grid.get(x, y) == null) {
            return false;
        }
        grid.remove(x, y);
        return true;
    }
    
    /**
     * Removes and returns the card at the specified coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the card that was removed, or null if there was no card at the specified coordinates.
     */
    public Card pop(int x, int y) {
        Card card = grid.get(x, y);
        if (card == null) {
            return null;
        }
        grid.remove(x, y);
        return card;
    }
    
    /**
     * Displays the board using the specified Graphics2D context.
     * 
     * @param g2d the Graphics2D context.
     * @throws NullPointerException if g2d is null.
     */
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
        int scaledOffsetY = (int) ((80 - 30) * scale);

        int offsetX = (width) / 2 - (scaledOffsetX);
        int offsetY = (height) / 2 - (scaledOffsetY);
        
        // on affiche les cartes les plus récentes au dessus
        List<Point> order = grid.getOrder(); 
        
        for (Point point : order) {
            Card card = grid.get(point);
            int displayX = point.x * scaledOffsetX + offsetX + Xoffset;
            int displayY = point.y * scaledOffsetY + offsetY + Yoffset;
            card.draw(g2d, displayX, displayY, scale);
        }
        
        Point point = grid.getCursorPoint();
        if (point != null) {
            int displayX = point.x * scaledOffsetX + offsetX + Xoffset;
            int displayY = point.y * scaledOffsetY + offsetY + Yoffset;
            CursorCard.drawCursor(g2d, displayX, displayY, scale);
        }
    }
    
    /**
     * Erases the current board display.
     * 
     * @param g2d the Graphics2D context.
     * @throws NullPointerException if g2d is null.
     */
    public void erase(Graphics2D g2d) {
        Objects.requireNonNull(g2d);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }
    
    /**
     * Zooms in on the board.
     */
    public void zoomIn() {
        scale *= 1.1;
        needsReset = true;
    }
    
    /**
     * Zooms out of the board.
     */
    public void zoomOut() {
        scale *= 0.9;
        needsReset = true;
    }
    
    /**
     * Resets the zoom level of the board.
     */
    public void zoomReset() {
        scale = 1;
        needsReset = true;
    }
    
    /**
     * Resets the board, clearing all cards.
     */
    public void reset() {
        grid = new Grid();
        needsReset = true;
    }
    
    /**
     * Moves the board by the specified offsets.
     * 
     * @param x the x offset.
     * @param y the y offset.
     */
    public void move(int x, int y) {
        Xoffset += x;
        Yoffset += y;
        needsReset = true;
    }
    
    /**
     * Resets the board position to the default.
     */
    public void moveReset() {
        Xoffset = 0;
        Yoffset = 0;
        needsReset = true;
    }
    
    /**
     * Checks if a card can be played at the specified coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the card can be played, false otherwise.
     */
    public boolean isPlayable(int x, int y) {
        if (grid.get(x, y) != null) {
            return false;
        }
        
        var corners = new ArrayList<CornerType>();
        
        //TODO: Vérifier si la carte est retournée, si oui on prend son verso
        if (hasNeighbors(x, y)) {
            return false;
        }

        corners.addAll(checkDiagonals(x, y));
        
        if (corners.isEmpty()) {
            return false;
        } // pas de carte voisin
        
        for (CornerType corner : corners) {
            if (corner instanceof OtherCornerType) {
                if ((OtherCornerType) corner == OtherCornerType.Invisible) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Gets the neighbors of the specified coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return a list of neighboring points.
     */
    private List<Point> getNeighbors(int x, int y) {
        List<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(x, y - 1));
        neighbors.add(new Point(x, y + 1));
        neighbors.add(new Point(x - 1, y));
        neighbors.add(new Point(x + 1, y));
        return neighbors;
    }
	
    /**
     * Returns the diagonal neighbors of the given coordinates.
     * 
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return a list of diagonal points.
     */
	private List<Point> getDiagonals(int x, int y) {
		List<Point> diagonals = new ArrayList<>();
		diagonals.add(new Point(x - 1, y - 1));
		diagonals.add(new Point(x - 1, y + 1));
		diagonals.add(new Point(x + 1, y - 1));
		diagonals.add(new Point(x + 1, y + 1));
		return diagonals;
	}
	
	/**
	 * Checks if there are neighbors around the specified coordinates.
	 *
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return true if there are neighbors, false otherwise.
	 */
	private boolean hasNeighbors(int x, int y) {
		
		List<Point> neighbors = getNeighbors(x, y);
		
		for (Point point : neighbors) {
	        Card card = grid.get(point);
	        
	        if (card == null) {
	        	continue;
	        }
	        
	        int xOffset = point.x - x;
	        int yOffset = point.y - y;

	        switch (xOffset) {
	            case -1: // Gauche
	                return true;
	            case 1: // Droite
	                return true;
	            default: // même x donc haut ou bas
	                switch (yOffset) {
	                    case -1: // Haut
	                        return true;
	                    case 1: // Bas
	                        return true;
	                }
	                break;
	        }
	    }
		return false;
	}
	
	/**
	 * Checks the diagonal neighbors of the given coordinates and retrieves the corner types.
	 * 
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return a list of CornerType representing the corners of the diagonal cards.
	 */
	private List<CornerType> checkDiagonals(int x, int y) {
		
		var diagonals = getDiagonals(x, y);
		var corners = new ArrayList<CornerType>();
		
		for (Point point : diagonals) {
	        Card card = grid.get(point);
	        
	        if (card == null) {
	        	continue;
	        }

	        int xOffset = point.x - x;
	        int yOffset = point.y - y;

	        switch (xOffset) {
	            case -1:
	                switch (yOffset) {
	                    case -1:
	                    	if (card.turned()) {
	                    		corners.add(card.getVerso()[3]);
	                    	} else {
	                    		corners.add(card.getRecto()[3]);
	                    	}
	                        break;
	                    case 1:
	                    	if (card.turned()) {
	                    		corners.add(card.getVerso()[1]);
	                    	} else {
	                    		corners.add(card.getRecto()[1]);
	                    	}
	                        break;
	                }
	                break;
	            case 1:
	                switch (yOffset) {
	                    case -1:
	                    	if (card.turned()) {
	                    		corners.add(card.getVerso()[2]);
	                    	} else {
	                    		corners.add(card.getRecto()[2]);
	                    	}
	                        break;
	                    case 1:
	                    	if (card.turned()) {
	                    		corners.add(card.getVerso()[0]);
	                    	} else {
	                    		corners.add(card.getRecto()[0]);
	                    	}
	                        break;
	                }
	                break;
	        }
	    }
		
		return corners;
	}
	
	/**
	 * Sets the cursor to the specified coordinates on the grid.
	 * 
	 * @param x the x-coordinate where the cursor should be placed.
	 * @param y the y-coordinate where the cursor should be placed.
	 */
	public void setCursor(int x, int y) {
		grid.setCursor(x, y);
		needsReset = true;
	}
	
	/**
	 * Removes the cursor from the grid.
	 */
	public void removeCursor() {
		grid.removeCursor();
		needsReset = true;
	}
	
	/**
	 * Displays an overlay on the given Graphics2D context.
	 * 
	 * @param context the ApplicationContext of the game.
	 * @param g2d the Graphics2D context to draw on.
	 * @throws NullPointerException if context or g2d is null.
	 */
	public void displayOverlay(ApplicationContext context, Graphics2D g2d) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(g2d);
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, height - 200, width, 200);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(0, height - 200, width, 200);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 200, height);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(-1, -1, 200, height+2);
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(width - 200, 0, 200, height);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(width - 200, -1, 200, height+2);
		
		BufferedImage turnBtn = ImageLoader.imageFromPath(Paths.get("./data/img/icons/icon_turn.png"));
		g2d.drawImage(turnBtn, width - 460, height - 100, null);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 26));
		g2d.drawString("Retourner", width - 400, height - 68);
	}
}
