package fr.uge.codex.deck.card;

import java.awt.Graphics2D;
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
        // Taille de carte par défaut : 120x78
        BufferedImage img = ImageLoader.get(kingdom, true);
        ImageLoader.draw(g2d, (int) x, (int) y, img, scale);
    }

    public void draw(Graphics2D g2d, double x, double y, double scale) {
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
