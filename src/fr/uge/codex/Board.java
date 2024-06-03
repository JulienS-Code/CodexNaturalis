package fr.uge.codex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.CursorCard;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.OtherCornerType;
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
		return add(card, x, y, false);
	}
	
	public boolean add(Card card, int x, int y, boolean isStarter) {
		Objects.requireNonNull(card);
	

		if (card instanceof CursorCard) {
			grid.add(x, y, card); // Elle s'ajoute à grid.cursorCards
		}
		
		if (grid.get(x, y) != null) {
			System.out.println("DEBUG: La case ("+x+","+y+") est déjà occupée");
			return false;
		}
		
		
		if (!isStarter) {
			if (!isPlayable(x, y)) {
				System.out.println("DEBUG: La case ("+x+","+y+") n'est pas jouable");
				return false;
			}
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
        int scaledOffsetY = (int) ((80 - 30) * scale);

        int offsetX = (width) / 2 - (scaledOffsetX);
        int offsetY = (height) / 2 - (scaledOffsetY);
        
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
        
        Point point = grid.getCursorPoint();
        if (point != null) {
            int displayX = point.x * scaledOffsetX + offsetX + Xoffset;
            int displayY = point.y * scaledOffsetY + offsetY + Yoffset;
            CursorCard.draw(g2d, displayX, displayY, scale);
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
	
	
	private List<Point> getNeighbors(int x, int y) {
		List<Point> neighbors = new ArrayList<>();
		neighbors.add(new Point(x, y - 1));
		neighbors.add(new Point(x, y + 1));
		neighbors.add(new Point(x - 1, y));
		neighbors.add(new Point(x + 1, y));
		return neighbors;
	}
	
	private List<Point> getDiagonals(int x, int y) {
		List<Point> diagonals = new ArrayList<>();
		diagonals.add(new Point(x - 1, y - 1));
		diagonals.add(new Point(x - 1, y + 1));
		diagonals.add(new Point(x + 1, y - 1));
		diagonals.add(new Point(x + 1, y + 1));
		return diagonals;
	}
	
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
	                        corners.add(card.getRecto()[3]);
	                        break;
	                    case 1:
	                        corners.add(card.getRecto()[1]);
	                        break;
	                }
	                break;
	            case 1:
	                switch (yOffset) {
	                    case -1:
	                        corners.add(card.getRecto()[2]);
	                        break;
	                    case 1:
	                        corners.add(card.getRecto()[0]);
	                        break;
	                }
	                break;
	        }
	    }
		
		return corners;
	}
	
	public void setCursor(int x, int y) {
		grid.setCursor(x, y);
		needsReset = true;
	}
	
	public void removeCursor() {
		grid.removeCursor();
		needsReset = true;
	}
}
