package fr.uge.codex.card;


public enum ResourceType implements CornerType {
	Animal,
	Plant,
	Fungi,
	Insect;
	
	@Override
    public boolean isResourceType(String word) {
        for (ResourceType type : ResourceType.values()) {
            if (type.name().equals(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isArtifactType(String word) {
        return false; // ResourceType ne peut pas être un type d'artefact
    }

}
