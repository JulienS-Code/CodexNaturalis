package fr.uge.codex.deck.card;

import java.awt.Graphics2D;

/**
 * Represents a generic card.
 */
public interface Card {
    
    /**
     * Checks if the card is turned.
     *
     * @return true if the card is turned, false otherwise.
     */
    boolean turned();
    
    /**
     * Gets the corner types on the recto side of the card.
     *
     * @return an array of corner types on the recto side.
     */
    CornerType[] getRecto();
    
    /**
     * Gets the corner types on the verso side of the card.
     *
     * @return an array of corner types on the verso side.
     */
    CornerType[] getVerso();
    
    /**
     * Gets the kingdom of the card.
     *
     * @return the kingdom of the card.
     */
    ResourceType getKingdom();
    
    /**
     * Draws the card on the graphics context at the specified position and scale.
     *
     * @param g2d the graphics context to draw on.
     * @param x   the x-coordinate of the top-left corner of the card.
     * @param y   the y-coordinate of the top-left corner of the card.
     * @param scale the scale factor to apply when drawing the card.
     */
    void draw(Graphics2D g2d, double x, double y, double scale);
    
    /**
     * Turns the card (i.e., flips it over).
     */
    void turn();
}
