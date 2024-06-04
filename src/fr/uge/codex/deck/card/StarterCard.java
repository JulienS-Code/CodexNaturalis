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

    /**
     * Constructs a new StarterCard with the specified recto, verso, and resources.
     *
     * @param recto     the list of ResourceType for the recto side of the card.
     * @param verso     the list of CornerType for the verso side of the card.
     * @param resources the list of ResourceType for the resources of the card.
     * @throws NullPointerException     if any of the arguments are null.
     * @throws IllegalArgumentException if recto or verso does not contain exactly 4 elements.
     */
    public StarterCard(List<ResourceType> recto, List<CornerType> verso, List<ResourceType> resources) {
        Objects.requireNonNull(recto, "recto must not be null");
        if (recto.size() != 4) {
            throw new IllegalArgumentException("Recto must contain exactly 4 elements");
        }
        Objects.requireNonNull(verso, "verso must not be null");
        if (verso.size() != 4) {
            throw new IllegalArgumentException("Verso must contain exactly 4 elements");
        }
        Objects.requireNonNull(resources, "resources must not be null");
        this.recto = recto;
        this.verso = verso;
        this.resources = resources;
        this.turned = false;
    }

    /**
     * Returns the list of resources for this card.
     *
     * @return the list of resources.
     */
    public List<ResourceType> resources() {
        return resources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean turned() {
        return turned;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CornerType[] getRecto() {
        return recto.toArray(new CornerType[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CornerType[] getVerso() {
        return verso.toArray(new CornerType[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceType getKingdom() {
        return null;
    }

    /**
     * Draws the corners of the card on the specified Graphics2D context at the given coordinates and scale.
     *
     * @param g2d   the Graphics2D context to draw on.
     * @param x     the x-coordinate.
     * @param y     the y-coordinate.
     * @param scale the scale factor.
     * @throws NullPointerException if g2d is null.
     */
    public void drawCorners(Graphics2D g2d, double x, double y, double scale) {
        Objects.requireNonNull(g2d, "g2d must not be null");

        // Default card size: 120x78
        // Default icon size: 24x24

        for (int i = 0; i < recto.size(); i++) {
            BufferedImage img = ImageLoader.get(recto.get(i), false);

            if (this.turned) img = ImageLoader.get(verso.get(i), false);

            switch (i) {
                case 0 -> ImageLoader.draw(g2d, (int) x, (int) y, img, scale); // top left
                case 1 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) y, img, scale); // top right
                case 2 -> ImageLoader.draw(g2d, (int) x, (int) ((y + 78 * scale) - 24 * scale), img, scale); // bottom left
                case 3 -> ImageLoader.draw(g2d, (int) ((x + 120 * scale) - 24 * scale), (int) ((y + 78 * scale) - 24 * scale), img, scale); // bottom right
                default -> System.err.println("Error: More than 4 corners?");
            }
        }
    }

    /**
     * Draws the base of the card on the specified Graphics2D context at the given coordinates and scale.
     *
     * @param g2d   the Graphics2D context to draw on.
     * @param x     the x-coordinate.
     * @param y     the y-coordinate.
     * @param scale the scale factor.
     * @throws NullPointerException if g2d is null.
     */
    public void drawBase(Graphics2D g2d, double x, double y, double scale) {
        Objects.requireNonNull(g2d, "g2d must not be null");

        BufferedImage img = ImageLoader.getStarter(this.turned);
        g2d.drawImage(img, (int) x, (int) y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
    }

    /**
     * Draws the card on the specified Graphics2D context at the given coordinates and scale.
     *
     * @param g2d   the Graphics2D context to draw on.
     * @param x     the x-coordinate.
     * @param y     the y-coordinate.
     * @param scale the scale factor.
     * @throws NullPointerException if g2d is null.
     */
    public void draw(Graphics2D g2d, double x, double y, double scale) {
        Objects.requireNonNull(g2d, "g2d must not be null");
        drawBase(g2d, x, y, scale);
        drawCorners(g2d, x, y, scale);
    }

    /**
     * Returns a string representation of the StarterCard.
     *
     * @return a string representation of the StarterCard.
     */
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

    /**
     * Toggles the turned state of the card.
     */
    @Override
    public void turn() {
        turned = !turned;
    }
}
