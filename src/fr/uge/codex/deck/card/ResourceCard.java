package fr.uge.codex.deck.card;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import fr.uge.codex.ImageLoader;

public record ResourceCard(CornerType[] recto, ResourceType kingdom, Scoring score) implements Card {

    public ResourceCard {
        Objects.requireNonNull(recto, "Recto must not be null");
        if (recto.length != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        Objects.requireNonNull(kingdom, "Kingdom must not be null");
        Objects.requireNonNull(score, "Score must not be null");
        boolean turned = false;
    }

    @Override
    public boolean turned() {
        return turned();
    }

    @Override
    public CornerType[] getRecto() {
        return recto;
    }

    public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
        // Taille de carte par défaut : 120x78
        // Taille d'icône par défaut : 24x24

        Objects.requireNonNull(g2d);

        for (int i = 0; i < recto.length; i++) {
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
        int width = (int) (img.getWidth() * scale);
        int height = (int) (img.getHeight() * scale);
        RoundRectangle2D outerRect = new RoundRectangle2D.Double(x, y, width, height, 5 * scale, 5 * scale);
        g2d.setColor(Color.BLACK);
        g2d.fill(outerRect);
        RoundRectangle2D innerRect = new RoundRectangle2D.Double(x + 1, y + 1, width - 2, height - 2, 5 * scale, 5 * scale);
        g2d.clip(innerRect);
        g2d.drawImage(img, (int) x, (int) y, width, height, null);
        g2d.setClip(null);
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
