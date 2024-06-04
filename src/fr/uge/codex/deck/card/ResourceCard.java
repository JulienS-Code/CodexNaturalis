package fr.uge.codex.deck.card;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.codex.ImageLoader;

public class ResourceCard implements Card {
    private CornerType[] recto;
    private ResourceType kingdom;
    private Scoring score;
    private boolean turned;

    public ResourceCard(CornerType[] recto, ResourceType kingdom, Scoring score) {
        Objects.requireNonNull(recto, "Recto must not be null");
        if (recto.length != 4) {
            throw new IllegalArgumentException("Corners must contain exactly 4 elements");
        }
        this.recto = recto;
        this.kingdom = Objects.requireNonNull(kingdom, "Kingdom must not be null");
        this.score = Objects.requireNonNull(score, "Score must not be null");
        this.turned = false;
    }

    @Override
    public CornerType[] getVerso() {
        return new CornerType[]{OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty, OtherCornerType.Empty};
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
        // Default card size: 120x78
        // Default icon size: 24x24
        // Size for invisible corner fill: 32x36

        Objects.requireNonNull(g2d);
        
        if (turned) {
        	return;
        }

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
                int scaledHeight = (int) (37 * scale);

                switch (i) {
                    case 0 -> g2d.fillRect((int) x, (int) y, scaledWidth, scaledHeight); // top left
                    case 1 -> g2d.fillRect((int) (x + (120 * scale) - scaledWidth), (int) y, scaledWidth, scaledHeight); // top right
                    case 2 -> g2d.fillRect((int) x, (int) (y + (80 * scale) - scaledHeight), scaledWidth, scaledHeight); // bottom left
                    case 3 -> g2d.fillRect((int) (x + (120 * scale) - scaledWidth), (int) (y + (80 * scale) - scaledHeight), scaledWidth, scaledHeight); // bottom right
                    default -> System.err.println("Error: More than 4 corners?");
                }
                continue;
            }
            BufferedImage img = ImageLoader.get(recto[i], false);

            switch (i) {
                case 0 -> ImageLoader.draw(g2d, (int) x, (int) y, img, scale); // top left
                case 1 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) y, img, scale); // top right
                case 2 -> ImageLoader.draw(g2d, (int) x, (int) ((y + 78 * scale) - 24 * scale), img, scale); // bottom left
                case 3 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) ((y + 78 * scale) - 24 * scale), img, scale); // bottom right
                default -> System.err.println("Error: More than 4 corners?");
            }
        }
    }

    public void drawBase(Graphics2D g2d, double x, double y, double scale) {
        Objects.requireNonNull(g2d);

        BufferedImage img = ImageLoader.get(kingdom, true, false, turned);
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
        builder.append("\nResourceCard = corners: ");
        for (CornerType type : recto) {
            builder.append(type).append(" ");
        }
        builder.append(" Kingdom (Resource on verso): ").append(kingdom);
        builder.append(" Score (type = ").append(score.scoringType());
        builder.append(") worth ").append(score.points());
        return builder.toString();
    }

    @Override
    public void turn() {
        turned = !turned;
    }

	public Scoring score() {
		return score;
	}
}
