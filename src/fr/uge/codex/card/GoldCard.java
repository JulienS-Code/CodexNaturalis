package fr.uge.codex.card;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.Objects;

import fr.uge.codex.ImageLoader;

public class GoldCard implements Card {
	private final CornerType[] recto;
	private final ResourceType kingdom;
	private final List<CornerType> cost;
	private Scoring score;
	
	public GoldCard(CornerType[] recto, ResourceType kingdom, List<CornerType> cost, Scoring score) {
		super();
		this.recto = Objects.requireNonNull(recto);
		if (recto.length != 4) {
			throw new IllegalArgumentException("Corners must contain exactly 4 elements");
		}
		this.kingdom = Objects.requireNonNull(kingdom);
		this.cost = Objects.requireNonNull(cost);
		this.score = score;
	}

	public Scoring getScore() {
		return score;
	}
	
	public void setScore(Scoring score) {
		this.score = score;
	}
	
	@Override
	public CornerType[] getRecto() {
		return recto;
	}
	
	public ResourceType getKingdom() {
		return kingdom;
	}

	public List<CornerType> getCost() {
		return cost;
	}
	
	public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
		// Taille de carte par défaut : 120x78
		// Taille d'icône par défaut : 24x24
		
		Objects.requireNonNull(g2d);
		
		int i = 0;
		
		for (CornerType corner : recto) {
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
			        ImageLoader.draw(g2d, (int) x, (int)((y + 80 * scale) - 24 * scale), img, scale);
			    	break;
			    case 3:
			        // bas droite
			        ImageLoader.draw(g2d, (int)((x + 120 * scale) - 24 * scale), (int)((y + 80 * scale) - 24 * scale), img, scale);
			    	break;
			    default:
			        System.err.println("Erreur: Plus de 4 coins?");
			}
			i++;
		}
	}
	
	public void drawBase(Graphics2D g2d, double x, double y, double scale) {
		// Taille de carte par défaut : 120x78
		BufferedImage img = ImageLoader.get(kingdom, true, true);
		ImageLoader.draw(g2d, (int)x, (int) y, img, scale);
		
	}
	
	public void draw(Graphics2D g2d, double x, double y, double scale) {
		drawBase(g2d, x, y, scale);
		drawCorners(g2d, x, y, scale);
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("GoldCard = coins : ");
		for (CornerType type : recto) {
			builder.append(type);
			builder.append(" ");
		}
		builder.append(" Kingdom (Resource au verso): ");
		builder.append(kingdom);
		builder.append(" qui coûte : ");
		builder.append(cost);
		builder.append(" et de score (type = ");
		builder.append(score.getScoringType());
		builder.append(") qui vaut ");
		builder.append(score.getPoints());
		return builder.toString();
	}
	
}
