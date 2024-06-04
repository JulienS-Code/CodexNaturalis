package fr.uge.codex.deck.card;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.uge.codex.player.Player;

public class SelectedCardHandler {
    private Card card;

    public SelectedCardHandler(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void drawOutline(Graphics2D g2d, Player player, int width, int height) {
        Card selectedCard = getCard();
        if (selectedCard == null) {
            return;
        }
        int index = player.getHand().indexOf(selectedCard);
        if (index == -1) {
            return;
        }
        int x = 830 + 200 * index;
        int y = 950;
        g2d.setColor(Color.RED);
        g2d.drawRect(x - 2, y - 2, 172, 116); // Draw a red outline around the selected card
    }
}
