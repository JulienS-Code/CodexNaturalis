package fr.uge.codex.card;

public enum ArtifactType implements CornerType {
	Quill,
	Manuscript,
	Inkwell;
	
	@Override
    public boolean isResourceType(String word) {
        return false; // ArtifactType ne peut pas être un type de ressource
    }

    @Override
    public boolean isArtifactType(String word) {
        for (ArtifactType type : ArtifactType.values()) {
            if (type.name().equals(word)) {
                return true;
            }
        }
        return false;
    }
}
