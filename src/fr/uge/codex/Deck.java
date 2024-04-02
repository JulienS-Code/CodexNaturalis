package fr.uge.codex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Deck {
	List<Card> cards;
	
	public Deck() {
		System.out.println("Ajout d'une carte");
		
		this.cards.add(new Card());
		System.out.println("Carte ajoutée");
		System.out.println("Lecture du Deck");
		ReaderDeck();
	}
	
	public void ReaderDeck() {
		Path path = Path.of("/CodexNaturalis/data/deck_rendu1.txt");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] mots = line.split(" ");
				for (String mot : mots) {
					System.out.println(mot);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
