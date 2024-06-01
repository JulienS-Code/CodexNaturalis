package fr.uge.codex.deck.card;

public enum OtherCornerType implements CornerType {
	Empty, 
	Invisible;

	@Override
	public boolean isResourceType(String word) {
		return false;
	}

	@Override
	public boolean isArtifactType(String word) {
		return false;
	}
	
}