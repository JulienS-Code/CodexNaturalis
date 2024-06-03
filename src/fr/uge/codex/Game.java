package fr.uge.codex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.uge.codex.deck.Deck;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.memory.SimpleGameData;
import fr.uge.memory.SimpleGameView;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

public class Game {
	
	public Game() {
		
	}
	
	public static boolean gameLoop(ApplicationContext context, SimpleGameData data, SimpleGameView view) {
		return false;
	}
	
	public static void codexNaturalis(ApplicationContext context) {
		Deck deck = new Deck();
		System.out.println("Début de la partie, voici le deck non mélangé : ");
		System.out.println(deck);
	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		System.out.println(deck);
        ResourceCard card = deck.pickResourceCard();
        System.out.println(card);
		
        Application.run(Color.BLACK, context -> {
        	Menu.renderMenu(context);
        	Board board = new Board(context);
			board.add(card, 0, 0, true);
			board.add(card, 1, 0);
			board.add(card, 2, 1);
			board.add(card, 1, 1);
			
        	context.renderFrame(graphics -> {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.fill(new Rectangle2D.Float(
    				0, 0,
    				context.getScreenInfo().getWidth(),
    				context.getScreenInfo().getHeight())
                );
            }); // On efface le menu
        	
        	// DEBUG: Pour placer des cartes avec le clavier
            int currentX = 0;
            int currentY = 0;
            ResourceCard currentCard = deck.pickResourceCard();
            
            while (true) {
                context.renderFrame(graphics -> {
                    Graphics2D g2d = (Graphics2D) graphics;
					board.display(g2d);
                });

                // On attend une action
                
                Event event = context.pollOrWaitEvent(1000);
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
							board.move(0, -25);
							break;
						case "DOWN":
							board.move(0, 25);
							break;
						case "LEFT":
							board.move(-25, 0);
							break;
						case "RIGHT":
							board.move(25, 0);
							break;
						case "R":
							board.moveReset();
							board.zoomReset();
							currentX = 0;
							currentY = 0;
							board.setGhost(currentX, currentY);
							break;
						
						// Ajout de cartes
						case "A":
							if (board.add(currentCard, currentX, currentY)) {
								currentCard = deck.pickResourceCard();
								board.removeGhost();
							}
							break;
							
						//   U         Z
						// H J K  =  Q S D
						case "Z":
							currentY--;
							board.setGhost(currentX, currentY);
							break;
						case "Q":
							currentX--;
							board.setGhost(currentX, currentY);
							break;
						case "S":
							currentY++;
							board.setGhost(currentX, currentY);
							break;
						case "D":
							currentX++;
							board.setGhost(currentX, currentY);
							break;
							
						// DEBUG
						default:
							System.out.println(event.getKey().toString());
							break;
					}
				} else {
					System.out.println(event.getAction().toString());
				}
            }
        });
	}
}
