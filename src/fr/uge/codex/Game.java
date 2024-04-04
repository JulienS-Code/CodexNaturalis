package fr.uge.codex;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.uge.codex.Card.ResourceType;
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
		List<ResourceType> lstCorners = new ArrayList<ResourceType>();
		lstCorners.add(ResourceType.Animal);
		lstCorners.add(ResourceType.Animal);
		lstCorners.add(ResourceType.Animal);
		lstCorners.add(ResourceType.Animal);
		ResourceCard card = new ResourceCard(lstCorners, ResourceType.Animal, 0);
		System.out.println("Début de la partie\nVoici la première carte : ");
		System.out.println(card);
		Deck deck = new Deck();
	}
	
	public static void main(String[] args) {
		Application.run(Color.LIGHT_GRAY, Game::codexNaturalis);
		
	}
	
	//création de deck
	Deck deck() {
		return new Deck();
	}
}
