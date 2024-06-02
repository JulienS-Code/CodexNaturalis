package fr.uge.codex.deck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.ArtifactObjectiveCard;
import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.ObjectiveCard;
import fr.uge.codex.deck.card.PatternObjectiveCard;
import fr.uge.codex.deck.card.ResourceObjectiveCard;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;

public class ObjectivesDeck {
	List<ObjectiveCard> objectiveCards = new ArrayList<ObjectiveCard>();
	private final String file;
	
	public ObjectivesDeck(String file) throws IOException {
		this.file = Objects.requireNonNull(file);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				createCardFromLine(line);
			}
		} 
	}
	
	public void createCardFromLine(String line) {
		Objects.requireNonNull(line);
		String type = "Objective";
		String[] parsedLine = line.split(" ");
		String currentType = parsedLine[0];
		if (type.equals(currentType)) {
			objectiveCards.add(makeCardFromLine(line));
		}
	}
	
	public ObjectiveCard makeCardFromLine(String line) {
		Objects.requireNonNull(line);
		String[] parsedLine = line.split(" ");
		
		String currentType = parsedLine[1];
		List<CornerType> criteria = new ArrayList<CornerType>();
		List<String> corners = new ArrayList<>();
		Scoring score = Scoring.makeScoring(parsedLine[parsedLine.length-1]);
		
		int i = 2;		
		switch (currentType) {
	        case "Pattern":
	        	while (!parsedLine[i].equals("Scoring")) {
	                if (isCorner(parsedLine[i])) {
	                    corners.add(parsedLine[i]);
	                } else {
	                	for (ResourceType type : ResourceType.values()) {
	    	                if (parsedLine[i].equals(type.toString())) {
	    	                    criteria.add(type);
	    	                    break;
	    	                }
	    	            }
	                }
	                i++;
	            }
	            return new PatternObjectiveCard(criteria, corners, score);
	            
	        case "Resource":
	        	while (!parsedLine[i].equals("Scoring")) {
	                for (ResourceType type : ResourceType.values()) {
    	                if (parsedLine[i].equals(type.toString())) {
    	                    criteria.add(type);
    	                    break;
    	                }
    	            }
		        	i++;
	            }
	            return new ResourceObjectiveCard(criteria, score);
	            
	        case "Artifact":
	        	while (!parsedLine[i].equals("Scoring")) {
	                for (ArtifactType type : ArtifactType.values()) {
    	                if (parsedLine[i].equals(type.toString())) {
    	                    criteria.add(type);
    	                    break;
    	                }
    	            }
		        	i++;
	            }
	            return new ArtifactObjectiveCard(criteria, score);
	            
	        default:
	            throw new IllegalArgumentException("Unknown objective type: " + currentType);
	    }
	}
	
	private static boolean isCorner(String word) {
        return word.equals("TopLeft") || word.equals("TopRight") || 
        		word.equals("BottomLeft") || word.equals("BottomRight") || 
        		word.equals("Top") || word.equals("Bottom");
    }
	
	public void shuffleDeck() {
		Collections.shuffle(objectiveCards);
	}
	
	public ObjectiveCard pick() {
		if (objectiveCards.isEmpty()) {
            throw new NoSuchElementException("No cards left in the deck.");
        }
		return objectiveCards.removeFirst();
	}
	
	@Override
	public String toString() {
		return objectiveCards.toString();
	}
}


