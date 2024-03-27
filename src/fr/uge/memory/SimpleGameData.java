package fr.uge.memory;

import java.util.Random;

/**
 * The SimpleGameData class stores all relevant pieces of information for the
 * game status.
 * 
 * @author vincent
 */
public class SimpleGameData {
	/**
	 * 2D array storing cells that represent cards to remember.
	 */
	private final Cell[][] matrix;
	/**
	 * First cell that was clicked on and shall be matched.
	 */
	private Coordinates first;
	/**
	 * Second cell that was clicked on and was unsuccessfully tried to be matched with the first cell.
	 */
	private Coordinates second;
	/**
	 * Number of pairs that have already been matched.
	 */
	private int wins;

	/**
	 * Creates and initializes a new GameData with a given number of lines and
	 * columns.
	 * 
	 * @param nbLines   Height of the game grid.
	 * @param nbColumns Width of the game grid.
	 */
	public SimpleGameData(int nbLines, int nbColumns) {
		if (nbLines < 0 || nbColumns < 0) {
			throw new IllegalArgumentException();
		}
		matrix = new Cell[nbLines][nbColumns];
		first = null;
		second = null;
		wins = 0;
		randomise();
	}

	/**
	 * Randomises the cell IDs in the game grid.
	 */
	private void randomise() {
		var tab = new int[lines() * columns()];
		var random = new Random();
		for (var i = 0; i < tab.length; i++) {
			var j = random.nextInt(i + 1);
			tab[i] = tab[j];
			tab[j] = i / 2;
		}
		for (var i = 0; i < tab.length; i++) {
			matrix[i % lines()][i / lines()] = new Cell(tab[i]);
		}
	}

	/**
	 * Gets the number of lines in the matrix contained in this GameData.
	 * 
	 * @return Number of lines in the matrix.
	 */
	public int lines() {
		return matrix.length;
	}

	/**
	 * Gets the number of columns in the matrix contained in this GameData.
	 * 
	 * @return Number of columns in the matrix.
	 */
	public int columns() {
		return matrix[0].length;
	}

	/**
	 * Gets the ID of the cell specified by its row and column.
	 * 
	 * @param i Row index of the cell.
	 * @param j Column index of the cell.
	 * @return ID of the cell.
	 */
	public int id(int i, int j) {
		return matrix[i][j].id();
	}

	/**
	 * Gets the ID of the cell specified by its row and column.
	 * 
	 * @param i Row index of the cell.
	 * @param j Column index of the cell.
	 * @return Visibility status of the cell.
	 */
	public boolean visible(int i, int j) {
		return matrix[i][j].visible();
	}

	/**
	 * Changes the game state by acknowledging that the cell in column i and row j
	 * has been selected.
	 * 
	 * @param i Cell column.
	 * @param j Cell row.
	 */
	public void clickOnCell(int i, int j) {
		if (i < 0 || columns() <= i || j < 0 || lines() <= j || visible(i, j)) {
			return;
		}
		matrix[i][j].show();
		if (first == null) {
			first = new Coordinates(i, j);
		} else if (second == null) {
			if (matrix[first.i()][first.j()].id() == matrix[i][j].id()) {
				first = null;
				wins++;
			} else {
				second = new Coordinates(i, j);
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Tests whether the game must enter sleep mode.
	 * 
	 * @return True if the game must enter sleep mode.
	 */
	public boolean mustSleep() {
		return second != null;
	}

	/**
	 * Makes the game leave sleep mode, by hiding those cells that had
	 * unsuccessfully been tried for matching.
	 */
	public void wakeUp() {
		matrix[first.i()][first.j()].hide();
		matrix[second.i()][second.j()].hide();
		first = null;
		second = null;
	}

	/**
	 * Tests if the player has won.
	 * 
	 * @return True if the player has won by finding all pairs of objects, and False
	 *         otherwize.
	 */
	public boolean win() {
		return 2 * wins == lines() * columns();
	}
}