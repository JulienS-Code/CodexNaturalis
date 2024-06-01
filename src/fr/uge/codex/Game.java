package fr.uge.codex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.uge.codex.card.Deck;
import fr.uge.codex.card.ResourceCard;
import fr.uge.memory.SimpleGameData;
import fr.uge.memory.SimpleGameView;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;

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
        ResourceCard card = deck.getFirstCard();
		
        Application.run(Color.BLACK, context -> {
        	Menu.renderMenu(context);
        	
        	context.renderFrame(graphics -> {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.fill(new Rectangle2D.Float(
    				0, 0,
    				context.getScreenInfo().getWidth(),
    				context.getScreenInfo().getHeight())
                );
            }); // On efface le menu
        	
            while (true) {
                context.renderFrame(graphics -> {
                    Graphics2D g2d = (Graphics2D) graphics;
                    
                    card.draw(g2d, 100, 600, 2);
                    card.draw(g2d, 500, 600, 0.5);
                    card.draw(g2d, 200, 400, 1);
                });

                // On attend une action
                context.pollOrWaitEvent(1000);
            }
        });
	}
}
