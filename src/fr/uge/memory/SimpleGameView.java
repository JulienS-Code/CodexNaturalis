package fr.uge.memory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;

/**
 * The SimpleGameView class deals with the display of the game the screen, and
 * with the interpretation of which zones were clicked on by the user.
 * 
 * @author vincent
 * @param xOrigin    Abscissa of the left-hand corner of the area displaying the
 *                   game. Abscissas are counted from left to right.
 * @param yOrigin    Ordinate of the left-hand corner of the area displaying the
 *                   game. Ordinates are counted from top to bottom.
 * @param height     Height of the (square) area displaying the game.
 * @param width      Width of the (square) area displaying the game.
 * @param squareSize Side of the (square) areas displaying individual cells / images.
 * @param loader     ImageLoader that dealt with loading the pictures to be
 *                   memorized.
 *
 */
public record SimpleGameView(int xOrigin, int yOrigin, int height, int width, int squareSize, ImageLoader loader) {

	/**
	 * Create a new GameView
	 * 
	 * @param xOrigin Abscissa of the left-hand corner of the area displaying the
	 *                game. Abscissas are counted from left to right.
	 * @param yOrigin Ordinate of the left-hand corner of the area displaying the
	 *                game. Ordinates are counted from top to bottom.
	 * @param length  Side of the (square) area displaying the game.
	 * @param data    SimpleGameData storing each piece of information on the
	 *                current state of the game to be displayed.
	 * @param loader  ImageLoader that dealt with loading the pictures to be
	 *                memorized.
	 * @return SimpleGameView
	 */
	public static SimpleGameView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data,
			ImageLoader loader) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(loader);
		var squareSize = length / data.lines();
		return new SimpleGameView(xOrigin, yOrigin, length, data.columns() * squareSize, squareSize, loader);
	}

	/**
	 * Throws exception if the value is not in the required range.
	 * 
	 * @param min   Minimal acceptable value.
	 * @param value Value being tested.
	 * @param max   Maximal acceptable value.
	 */
	private static void checkRange(double min, double value, double max) {
		if (value < min || value > max) {
			throw new IllegalArgumentException("Invalid coordinate: " + value);
		}
	}

	/**
	 * Gets row or column number based on the given coordinate and the origin of the
	 * display area for this coordinate.
	 * 
	 * @param coord  Coordinate whose row / column we want to know.
	 * @param origin Origin coordinate of the display area.
	 * @return Row / column index that was pointed at by coord.
	 */
	private int indexFromRealCoord(float coord, int origin) {
		return (int) ((coord - origin) / squareSize);
	}

	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException if the float coordinate does not fit in the
	 *                                  game board.
	 */
	public int lineFromY(float y) {
		checkRange(yOrigin, y, y + width);
		return indexFromRealCoord(y, yOrigin);
	}

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x a float x-coordinate
	 * @return the index of the corresponding column.
	 * @throws IllegalArgumentException if the float coordinate does not fit in the
	 *                                  game board.
	 */
	public int columnFromX(float x) {
		checkRange(xOrigin, x, x + height);
		return indexFromRealCoord(x, xOrigin);
	}

	/**
	 * Gets base coordinate for the given row /column.
	 * 
	 * @param index  Index of the row / column.
	 * @param origin Base coordinate of the display area.
	 * @return Base coordinate of the row / column.
	 */
	private float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	/**
	 * Gets base abscissa for the column of index i.
	 * 
	 * @param i Column index.
	 * @return Base abscissa of the column.
	 */
	private float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	/**
	 * Gets base ordinate for the row of index j.
	 * 
	 * @param j Row index.
	 * @return Base ordinate of the row.
	 */
	private float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	/**
	 * Displays an image in a given part of the display area.
	 * 
	 * @param graphics Graphics engine that will actually display the image.
	 * @param image    Image to be displayed.
	 * @param x        Base abscissa of the part in which the image will be
	 *                 displayed.
	 * @param y        Base ordinate of the part in which the image will be
	 *                 displayed.
	 * @param dimX     Width of the part in which the image will be displayed.
	 * @param dimY     Height of the part in which the image will be displayed.
	 */
	private void drawImage(Graphics2D graphics, BufferedImage image, float x, float y, float dimX, float dimY) {
		var width = image.getWidth();
		var height = image.getHeight();
		var scale = Math.min(dimX / width, dimY / height);
		var transform = new AffineTransform(scale, 0, 0, scale, x + (dimX - scale * width) / 2,
				y + (dimY - scale * height) / 2);
		graphics.drawImage(image, transform, null);
	}

	/**
	 * Displays the content of the cell in column i and row j.
	 * 
	 * @param graphics Graphics engine that will actually display the cell.
	 * @param data     GameData containing the game data.
	 * @param i        Column of the cell.
	 * @param j        Row of the cell.
	 */
	private void drawCell(Graphics2D graphics, SimpleGameData data, int i, int j) {
		var x = xFromI(i);
		var y = yFromJ(j);
		var image = data.visible(i, j) ? loader.image(data.id(i, j)) : loader.blank();
		drawImage(graphics, image, x + 2, y + 2, squareSize - 4, squareSize - 4);
	}

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics Graphics engine that will actually display the game.
	 * @param data     GameData containing the game data.
	 */
	private void draw(Graphics2D graphics, SimpleGameData data) {
		// example
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, height, width));

		for (int i = 0; i < data.lines(); i++) {
			for (int j = 0; j < data.columns(); j++) {
				drawCell(graphics, data, i, j);
			}
		}
	}

	/**
	 * Draws the game board from its data, using an existing
	 * {@code ApplicationContext}.
	 * 
	 * @param context {@code ApplicationContext} of the game.
	 * @param data    GameData containing the game data.
	 * @param view    GameView on which to draw.
	 */
	public static void draw(ApplicationContext context, SimpleGameData data, SimpleGameView view) {
		context.renderFrame(graphics -> view.draw(graphics, data)); // do not modify
	}
}