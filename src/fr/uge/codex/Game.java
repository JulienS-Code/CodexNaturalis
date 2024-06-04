package fr.uge.codex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import fr.uge.codex.deck.Deck;
import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.SelectedCardHandler;
import fr.uge.codex.player.Inventory;
import fr.uge.codex.player.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

public class Game {

	public static void main(String[] args) {
		Deck deck = new Deck();
		System.out.println("Début de la partie, voici le deck non mélangé : ");
		System.out.println(deck);
		Inventory inventory = new Inventory();
		Player player = new Player(0);

		player.initPile(deck);

		Application.run(Color.BLACK, context -> {
			Menu.renderMenu(context);
			Board board = new Board(context);
			board.add(deck.pickStarterCard(), 0, 0, true);

			int width = (int) context.getScreenInfo().getWidth();
			int height = (int) context.getScreenInfo().getHeight();

			context.renderFrame(graphics -> {
				Graphics2D g2d = (Graphics2D) graphics;
				g2d.fill(new Rectangle2D.Float(0, 0, width, height));
			}); // On efface le menu

			int cursorX = 0;
			int cursorY = 0;
			var handler = new SelectedCardHandler(null);
			board.setCursor(cursorX, cursorY);

			while (true) {
				context.renderFrame(graphics -> {
					Graphics2D g2d = (Graphics2D) graphics;
					board.display(g2d);
					board.displayOverlay(context, g2d);
					player.drawHand(g2d, width, height);
					player.drawPile(g2d, width, height);
					drawSelectedCardOutline(g2d, handler, player, width, height);
				});

				// On attend une action

				Event event = context.pollOrWaitEvent(2500);
				if (event == null) {
					continue;
				}

				if (event.getAction() == Action.KEY_PRESSED) {
					switch (event.getKey().toString()) {
						// Quitter (ECHAP ou autre)
						case "UNDEFINED":
							context.exit(0);
							return;

						// Zoom
						case "O":
							board.zoomOut();
							break;
						case "P":
							board.zoomIn();
							break;

						// Mouvement du plateau
						case "UP":
							board.move(0, 25);
							break;
						case "DOWN":
							board.move(0, -25);
							break;
						case "LEFT":
							board.move(25, 0);
							break;
						case "RIGHT":
							board.move(-25, 0);
							break;
						case "R": // Reset
							board.moveReset();
							board.zoomReset();
							cursorX = 0;
							cursorY = 0;
							board.setCursor(cursorX, cursorY);
							break;

						// Ajout de cartes
						case "SPACE":
							Card selectedCard = handler.getCard();
							if (selectedCard == null) {
								continue;
							}
							if (board.add(selectedCard, cursorX, cursorY)) {
								inventory.addCardToInventory(selectedCard);
								player.remove(selectedCard);
								handler.setCard(null);
							}
							break;

						//   Z
						// Q S D
						case "Z":
							cursorY--;
							board.setCursor(cursorX, cursorY);
							break;
						case "Q":
							cursorX--;
							board.setCursor(cursorX, cursorY);
							break;
						case "S":
							cursorY++;
							board.setCursor(cursorX, cursorY);
							break;
						case "D":
							cursorX++;
							board.setCursor(cursorX, cursorY);
							break;

						// DEBUG
						default:
							System.out.println(event.getKey().toString());
							break;
					}
				} else if (Menu.isClick(event)) {
					Point click = new Point(
							(int) event.getLocation().getX(),
							(int) event.getLocation().getY()
					);

					// CHECK PIOCHE
					Rectangle pick1 = new Rectangle(230, 960, 168, 112);
					Rectangle pick2 = new Rectangle(430, 960, 168, 112);

					if (pick1.contains(click)) {
						player.pick(0, deck);
					} else if (pick2.contains(click)) {
						player.pick(1, deck);
					}

					// CHECK MAIN
					Rectangle main1 = new Rectangle(830, 960, 168, 112);
					Rectangle main2 = new Rectangle(1030, 960, 168, 112);
					Rectangle main3 = new Rectangle(1230, 960, 168, 112);

					if (main1.contains(click)) {
						handler.setCard(player.getHand().get(0));
					} else if (main2.contains(click) && player.getHand().size() > 1) {
						handler.setCard(player.getHand().get(1));
					} else if (main3.contains(click) && player.getHand().size() > 2) {
						handler.setCard(player.getHand().get(2));
					}

				} else {
					System.out.println(event.getAction().toString());
				}
			}
		});
	}

	private static void drawSelectedCardOutline(Graphics2D g2d, SelectedCardHandler handler, Player player, int width, int height) {
		Card selectedCard = handler.getCard();
		if (selectedCard == null) {
			return;
		}
		int index = player.getHand().indexOf(selectedCard);
		if (index == -1) {
			return;
		}
		int x = 830 + 200 * index;
		int y = 960;
		g2d.setColor(Color.RED);
		g2d.drawRect(x - 2, y - 2, 172, 116); // Draw a red outline around the selected card
	}
}
