package fr.uge.codex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.codex.Card.ResourceType;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		// Ouverture du fichier deck.txt 
		// makeCardFromLine pour chaque ligne du fichier
		System.out.println("Ajout d'une carte (provoque une erreur car args null)");
		//this.cards.add(new Card(null, null, 0));
		//System.out.println("Carte ajoutée");
		System.out.println("Lecture du Deck");
		String line = "ResourceCard Recto Empty Empty Invisible R:Plant Kingdom Plant Scoring 1";
		makeCardFromLine(line);
		ReaderDeck();
	}
	
	public Card makeCardFromLine(String line) {
		Objects.requireNonNull(line, "Line should be a String not null");
		String[] parsedLine = line.split(" ");
		String type = parsedLine[0];
		String[] resources = {parsedLine[2], parsedLine[3], parsedLine[4], parsedLine[5]};
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		String score = parsedLine[9];
		System.out.println("Présentation d'une carte\n" + type + " Recto " + resources + " Kingdom " + kingdom + " Scoring " + score);
		return new Card(null, null, 0);
	}
	
	
	public List<ResourceType> makeCornersFromLine(String[] line) {
		Objects.requireNonNull(line);
		List<ResourceType> lstCorners = new ArrayList<ResourceType>();
		return lstCorners;
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
