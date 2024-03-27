package fr.uge.memory;

/**
 * The Cell class deals with minimal information about cells of the memory game.
 * 
 * @author vincent
 *
 */
public class Cell {
	
	/**
	 *  ID of the card represented by the cell.
	 */
	private final int id;
	
	/**
	 *  Visibility status of the card represented by the cell.
	 */
	private boolean visible;

	/**
	 * Creates a new cell containing a card to to remember.
	 * 
	 * @param i ID of the card to remember.
	 */
	public Cell(int i) {
		id = i;
		visible = false;
	}

	/**
	 * Makes the card invisible, which shall result in showing the card's back.
	 */
	public void hide() {
		visible = false;
	}

	/**
	 * Makes the card visible, which shall result in showing the card's face.
	 */
	public void show() {
		visible = true;
	}

	/**
	 * Gets the ID of the object to remember.
	 * 
	 * @return ID of the object to remember.
	 */
	public int id() {
		return id;
	}

	/**
	 * Gets the state (visible or invisible) of the cell.
	 * 
	 * @return True if the card is visible, false otherwise.
	 */
	public boolean visible() {
		return visible;
	}
}
