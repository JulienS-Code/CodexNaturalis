package fr.uge.codex.deck.card;

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
}