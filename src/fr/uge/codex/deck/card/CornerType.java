package fr.uge.codex.deck.card;

/**
 * Represents a corner type of a card.
 */
public interface CornerType {
    
    /**
     * Checks if the provided word represents a resource type.
     *
     * @param word the word to check.
     * @return true if the word represents a resource type, false otherwise.
     */
    boolean isResourceType(String word);
    
    /**
     * Checks if the provided word represents an artifact type.
     *
     * @param word the word to check.
     * @return true if the word represents an artifact type, false otherwise.
     */
    boolean isArtifactType(String word);
}