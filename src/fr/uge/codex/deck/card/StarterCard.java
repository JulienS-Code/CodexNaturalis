package fr.uge.codex.deck.card;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.ImageLoader;

public class StarterCard implements Card {
    private final List<ResourceType> recto;
    private final List<CornerType> verso;
    private final List<ResourceType> resources;
    private boolean turned;

    public StarterCard(List<ResourceType> recto, List<CornerType> verso, List<ResourceType> resources) {
        Objects.requireNonNull(recto);
        if (recto.size() != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        Objects.requireNonNull(verso);
        if (verso.size() != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        Objects.requireNonNull(resources);
        this.recto = recto;
        this.verso = verso;
        this.resources = resources;
        this.turned = false;
    }
    

	@Override
	public CornerType[] getVerso() {
		return new CornerType[]{OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
	}
    
    public List<ResourceType> resources() {
	    return resources;
    }
    
	@Override
	public boolean turned() {
		return turned;
	}

	@Override
	public CornerType[] getRecto() {
		return recto.toArray(new CornerType[0]);
	}
	
	@Override
	public CornerType[] getVerso() {
		return verso.toArray(new CornerType[0]);
	}
	
	@Override
	public ResourceType getKingdom() {
		return null;
	}
	
    public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
        // Taille de carte par défaut : 120x78
        // Taille d'icône par défaut : 24x24

        Objects.requireNonNull(g2d);
        
        for (int i = 0; i < recto.size(); i++) {
            BufferedImage img = ImageLoader.get(recto.get(i), false);
            
        	if (this.turned()) img = ImageLoader.get(verso.get(i), false);

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

        BufferedImage img = ImageLoader.getStarter(this.turned());
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
		builder.append("\nStarterCard = recto : ");
		builder.append(recto);
		builder.append(", verso : ");
		builder.append(verso);
		builder.append(" et de resources : ");
		builder.append(resources);
		return builder.toString();
	}
}
