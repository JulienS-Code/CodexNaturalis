package fr.uge.codex.card;

import java.util.Objects;

public class Scoring {
	private final ScoringType scoringType;
	private final int points;
	private final ArtifactType artifactType;
	
	private Scoring(ScoringType scoringType, int points, ArtifactType artifactType) {
        this.scoringType = Objects.requireNonNull(scoringType);
        this.points = points;
        if (points < 0) {
        	throw new IllegalArgumentException("Score must be positive");
        }
        this.artifactType = artifactType;
    }
	
	public static Scoring direct(int points) {
		return new Scoring(ScoringType.DIRECT, points, null);
	}
	
	public static Scoring byArtifact(ArtifactType artifactType, int points) {
		return new Scoring(ScoringType.BY_ARTIFACT, points, Objects.requireNonNull(artifactType));
	}
	
	public static Scoring byCorner(int points) {
		return new Scoring(ScoringType.BY_CORNER, points, null);
	}
	
	public static Scoring none() {
		return new Scoring(ScoringType.NONE, 0, null);
	}
	
	public ScoringType getScoringType() {
        return scoringType;
    }

    public int getPoints() {
        return points;
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }
    
    public static Scoring makeScoring(String token) {
        Objects.requireNonNull(token);
        if (token.equals("None")) {
            return Scoring.none();
        } else if (token.startsWith("D:")) {
            int points = Integer.parseInt(token.substring(2));
            return Scoring.direct(points);
        } else if (token.startsWith("C:")) {
            int points = Integer.parseInt(token.substring(2));
            return Scoring.byCorner(points);
        } else if (token.startsWith("Q:") || token.startsWith("I:") || token.startsWith("M:")) {
            char artifactChar = token.charAt(0);
            int points = Integer.parseInt(token.substring(2));
            ArtifactType artifactType = parseArtifactType(artifactChar);
            return Scoring.byArtifact(artifactType, points);
        } else {
            throw new IllegalArgumentException("Invalid Scoring: " + token);
        }
    }
    
    private static ArtifactType parseArtifactType(char artifactChar) {
        switch (artifactChar) {
            case 'Q':
                return ArtifactType.Quill;
            case 'I':
                return ArtifactType.Inkwell;
            case 'M':
                return ArtifactType.Manuscript;
            default:
                throw new IllegalArgumentException("Invalid artifact type: " + artifactChar);
        }
    }
    
    @Override
    public String toString() {
        switch (scoringType) {
            case DIRECT:
                return "D:" + points;
            case BY_ARTIFACT:
                return artifactType.toString().charAt(0) + ":" + points;
            case BY_CORNER:
                return "C:" + points;
            case NONE:
                return "None";
            default:
                throw new IllegalStateException("Unexpected value: " + scoringType);
        }
    }
}


