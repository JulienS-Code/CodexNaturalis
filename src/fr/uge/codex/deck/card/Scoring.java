package fr.uge.codex.deck.card;

import java.util.Objects;

public record Scoring(ScoringType scoringType, int points, ArtifactType artifactType) {

    public Scoring {
        Objects.requireNonNull(scoringType, "ScoringType must not be null");
        if (points < 0) {
            throw new IllegalArgumentException("Score must be positive");
        }
        if (scoringType == ScoringType.BY_ARTIFACT) {
            Objects.requireNonNull(artifactType, "ArtifactType must not be null for BY_ARTIFACT scoring");
        } else {
            if (artifactType != null) {
                throw new IllegalArgumentException("ArtifactType must be null for non BY_ARTIFACT scoring");
            }
        }
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

    public static Scoring makeScoring(String word) {
        Objects.requireNonNull(word);
        if (word.equals("None")) {
            return Scoring.none();
        } else if (word.startsWith("D:")) {
            int points = Integer.parseInt(word.substring(2));
            return Scoring.direct(points);
        } else if (word.startsWith("C:")) {
            int points = Integer.parseInt(word.substring(2));
            return Scoring.byCorner(points);
        } else if (word.startsWith("Q:") || word.startsWith("I:") || word.startsWith("M:")) {
            char artifactChar = word.charAt(0);
            int points = Integer.parseInt(word.substring(2));
            ArtifactType artifactType = parseArtifactType(artifactChar);
            return Scoring.byArtifact(artifactType, points);
        } else {
            throw new IllegalArgumentException("Invalid Scoring: " + word);
        }
    }

    private static ArtifactType parseArtifactType(char artifactChar) {
        return switch (artifactChar) {
            case 'Q' -> ArtifactType.Quill;
            case 'I' -> ArtifactType.Inkwell;
            case 'M' -> ArtifactType.Manuscript;
            default -> throw new IllegalArgumentException("Invalid artifact type: " + artifactChar);
        };
    }

    @Override
    public String toString() {
        return switch (scoringType) {
            case DIRECT -> "D:" + points;
            case BY_ARTIFACT -> artifactType.toString().charAt(0) + ":" + points;
            case BY_CORNER -> "C:" + points;
            case NONE -> "None";
            default -> throw new IllegalStateException("Unexpected value: " + scoringType);
        };
    }
}
