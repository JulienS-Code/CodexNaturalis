package fr.uge.codex.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourcesDeck{
	List<ResourceCard> resourceCards = new ArrayList<ResourceCard>();
	private final String file;

	public ResourcesDeck(String file) throws IOException {
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
		String type = "ResourceCard";
		String[] parsedLine = line.split(" ");
		String currentType = parsedLine[0];
		if (type.equals(currentType)) {
			resourceCards.add(makeCardFromLine(line));
		}
	}
	
	public ResourceCard makeCardFromLine(String line) {
		Objects.requireNonNull(line);
		String[] parsedLine = line.split(" ");
		
		CornerType[] recto = parseResources(parsedLine);
		ResourceType kingdom = ResourceType.valueOf(parsedLine[7]);
		Scoring score = Scoring.makeScoring(parsedLine[9]);
		
		return new ResourceCard(recto, kingdom, score);
	}
	
	private CornerType[] parseResources(String[] parsedLine) {
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

	@Override
	public String toString() {
		return resourceCards.toString();
	}
	
}



