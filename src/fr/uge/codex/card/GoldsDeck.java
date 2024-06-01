package fr.uge.codex.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GoldsDeck {
	private final List<GoldCard> goldCards = new ArrayList<GoldCard>();
	private final String file;

	public GoldsDeck(String file) throws IOException {
		this.file = Objects.requireNonNull(file);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				createCardFromLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createCardFromLine(String line) {
		Objects.requireNonNull(line);
		String type = "GoldCard";
		String[] parsedLine = line.split(" ");
		String currentType = parsedLine[0];
		if (type.equals(currentType)) {
			goldCards.add(makeCardFromLine(line));
		}
	}
	
	public GoldCard makeCardFromLine(String line) {
		Objects.requireNonNull(line);
		String[] parsedLine = line.split(" ");
		
		CornerType[] recto = parseResources(parsedLine);
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		List<CornerType> cost = parseCost(parsedLine);
		Scoring score = Scoring.makeScoring(parsedLine[parsedLine.length-1]);
//		System.out.println(score);
		
		return new GoldCard(recto, kingdom, cost, score);
	}
	
	public CornerType[] parseResources(String[] parsedLine) {
		Objects.requireNonNull(parsedLine);
		CornerType[] recto = new CornerType[4];
		for (int i = 2; i <= 5; i++) {
			if (parsedLine[i].startsWith("R:")) {
				recto[i-2] = ResourceType.valueOf(parsedLine[i].substring(2));
			} else if (parsedLine[i].startsWith("A:" )) {
				recto[i-2] = ArtifactType.valueOf(parsedLine[i].substring(2));
			} else if (parsedLine[i].equals("Empty")) {
				recto[i-2] = OtherCornerType.Empty;
			} else if (parsedLine[i].equals("Invisible")) {
				recto[i-2] = OtherCornerType.Invisible;
			}
		}
		return recto;
	}
	
	public List<CornerType> parseCost(String[] parsedLine) {
		List<CornerType> cost = new ArrayList<CornerType>();
		boolean foundCost = false;
		for (String word : parsedLine) {
			if (foundCost && !word.equals("Scoring")) {
				for (ResourceType type : ResourceType.values()) {
	                if (word.equals(type.toString())) {
	                    cost.add(type);
	                    break;
	                }
	            }
			} else if (word.equals("Cost")) {
				foundCost = true;
			} else if (word.equals("Scoring")) {
				break;
			} 
		}
		return cost;
	}

	@Override
	public String toString() {
		return goldCards.toString();
	}
	
	
}
