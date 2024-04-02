package fr.uge.codex;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.uge.codex.ResourceCard.Resource;
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
		List<Resource> lstCorners = new ArrayList<Resource>();
		lstCorners.add(Resource.animal);
		lstCorners.add(Resource.animal);
		lstCorners.add(Resource.animal);
		lstCorners.add(Resource.animal);
		ResourceCard card = new ResourceCard(lstCorners, Resource.animal, 0);
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
