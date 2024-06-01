package fr.uge.codex;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class Card {
	List<ResourceType> corners;
	ResourceType kingdom;
	int score;
	
	public enum ResourceType{
		Animal,
		Plant,
		Fungi,
		Insect,
	}
	
	public Card(List<ResourceType> corners, ResourceType kingdom, int score) {
		// ou ResourceType[] corners ?
		Objects.requireNonNull(corners, "corners doesn't register");
		if (corners.size() != 4) {
			throw new IllegalArgumentException("must have 4 corners in list");
		}
		this.corners = corners;
		this.kingdom = Objects.requireNonNull(kingdom);
		if (score < 0) {
			throw new IllegalArgumentException("score value invalid");
		}
	}
	
	
	public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
		// Taille de carte par défaut : 120x78
		// Taille d'icône par défaut : 24x24
		
		Objects.requireNonNull(g2d);
		
		int i = 0;
		
		for (ResourceType corner : corners) {
			BufferedImage img = ImageLoader.get(corner, false);
			
			switch (i) {
			    case 0:
			        // haut gauche
			        ImageLoader.draw(g2d, (int) x, (int) y, img, scale);
			        break;
			    case 1:
			        // haut droite
			        ImageLoader.draw(g2d, (int)((x + 120 * scale) - 24 * scale), (int) y, img, scale);
			        break;
			    case 2:
			        // bas gauche
			        ImageLoader.draw(g2d, (int) x, (int)((y + 78 * scale) - 24 * scale), img, scale);
			    	break;
			    case 3:
			        // bas droite
			        ImageLoader.draw(g2d, (int)((x + 120 * scale) - 24 * scale), (int)((y + 78 * scale) - 24 * scale), img, scale);
			    	break;
			    default:
			        System.err.println("Erreur: Plus de 4 coins?");
			}
			i++;
		}
	}
	
	public void drawBase(Graphics2D g2d, double x, double y, double scale) {
		// Taille de carte par défaut : 120x78
		BufferedImage img = ImageLoader.get(kingdom, true);
		ImageLoader.draw(g2d, (int)x, (int) y, img, scale);
		
	}
	
	public void draw(Graphics2D g2d, double x, double y, double scale) {
		drawBase(g2d, x, y, scale);
		drawCorners(g2d, x, y, scale);
	}
	
	// corner
	// recto / verso
	// Type ressources (4)
	// Score
	// Cost (goldcard)
}
