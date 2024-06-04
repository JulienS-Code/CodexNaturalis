package fr.uge.codex.deck.card;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.codex.ImageLoader;

public record ResourceCard(CornerType[] recto, ResourceType kingdom, Scoring score) implements Card {
	static boolean turned = false;
	
    public ResourceCard {
        Objects.requireNonNull(recto, "Recto must not be null");
        if (recto.length != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        Objects.requireNonNull(kingdom, "Kingdom must not be null");
        Objects.requireNonNull(score, "Score must not be null");
    }

    @Override
    public boolean turned() {
        return turned;
    }

    @Override
    public CornerType[] getRecto() {
        return recto;
    }
    
    @Override
    public ResourceType getKingdom() {
    	return kingdom;
    }

    public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
        // Taille de carte par défaut : 120x78
        // Taille d'icône par défaut : 24x24
    	// Taille de remplissage pour coin invisible : 32x36

        Objects.requireNonNull(g2d);
        
    	var colorMap = new HashMap<CornerType, Color>();
    	colorMap.put(ResourceType.Fungi, new Color(240, 69, 46));
    	colorMap.put(ResourceType.Insect, new Color(157, 55, 155));
    	colorMap.put(ResourceType.Plant, new Color(102, 198, 116));
    	colorMap.put(ResourceType.Animal, new Color(110, 204, 191));

        for (int i = 0; i < recto.length; i++) {
        	if (recto[i] == OtherCornerType.Invisible) {
        		// Corner size: 32x36
				Color color = colorMap.get(kingdom);
				g2d.setColor(color);

	            int scaledWidth = (int) (30 * scale);
	            int scaledHeight = (int) (36 * scale);
	            
		        switch (i) {
	                case 0 -> g2d.fillRect((int) x, (int) y, scaledWidth, scaledHeight); // haut gauche
	                case 1 -> g2d.fillRect((int) (x + (120 * scale) - scaledWidth), (int) y, scaledWidth, scaledHeight); // haut droite
	                case 2 -> g2d.fillRect((int) x, (int) (y + (80 * scale) - scaledHeight), scaledWidth, scaledHeight); // bas gauche
	                case 3 -> g2d.fillRect((int) (x + (120 * scale) - scaledWidth), (int) (y + (80 * scale) - scaledHeight), scaledWidth, scaledHeight); // bas droite
	                default -> System.err.println("Erreur: Plus de 4 coins?");
		        }
		        continue;
        	} 
            BufferedImage img = ImageLoader.get(recto[i], false);

            switch (i) {
                case 0 -> ImageLoader.draw(g2d, (int) x, (int) y, img, scale); // haut gauche
                case 1 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) y, img, scale); // haut droite
                case 2 -> ImageLoader.draw(g2d, (int) x, (int) ((y + 78 * scale) - 24 * scale), img, scale); // bas gauche
                case 3 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) ((y + 78 * scale) - 24 * scale), img, scale); // bas droite
                default -> System.err.println("Erreur: Plus de 4 coins?");
            }
        }
    }

    public void drawBase(Graphics2D g2d, double x, double y, double scale) {
    	Objects.requireNonNull(g2d);

        BufferedImage img = ImageLoader.get(kingdom, true);
        g2d.drawImage(img, (int) x, (int) y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
    }

    public void draw(Graphics2D g2d, double x, double y, double scale) {
    	Objects.requireNonNull(g2d);
        drawBase(g2d, x, y, scale);
        drawCorners(g2d, x, y, scale);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nResourceCard = coins : ");
        for (CornerType type : recto) {
            builder.append(type).append(" ");
        }
        builder.append(" Kingdom (Resource au verso): ").append(kingdom);
        builder.append(" et de score (type = ").append(score.scoringType());
        builder.append(") qui vaut ").append(score.points());
        return builder.toString();
    }
}