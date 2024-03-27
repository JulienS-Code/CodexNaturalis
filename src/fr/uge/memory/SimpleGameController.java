package fr.uge.memory;

import java.awt.Color;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * The SimpleGameController class deals with the main game loop, including
 * retrieving raw user actions, sending them for analysis to the GameView and
 * GameData, and dealing with time events.
 * 
 * @author vincent
 */
public class SimpleGameController {
	/**
	 * Default constructor, which does basically nothing.
	 */
	public SimpleGameController() {
		
	}

	/**
	 * Goes once in the game loop, which consists in retrieving user actions,
	 * transmissing it to the GameView and GameData, and dealing with time events.
	 * 
	 * @param context {@code ApplicationContext} of the game.
	 * @param data    GameData of the game.
	 * @param view    GameView of the game.
	 * @return        True if the game must continue, False if it must stop.
	 */
	private static boolean gameLoop(ApplicationContext context, SimpleGameData data, SimpleGameView view) {
		var event = context.pollOrWaitEvent(10);
		if (event == null) {
			return true;
		}
		var action = event.getAction();
		if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
			return false;
		}
		if (action != Action.POINTER_DOWN) {
			return true;
		}
		var location = event.getLocation();
		data.clickOnCell(view.columnFromX(location.x), view.lineFromY(location.y));
		SimpleGameView.draw(context, data, view);
		if (data.mustSleep()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return false;
			}
			data.wakeUp();
			SimpleGameView.draw(context, data, view);
		}
		if (data.win()) {
			System.out.println("You have won!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * Sets up the game, then launches the game loop.
	 * 
	 * @param context {@code ApplicationContext} of the game.
	 */
	private static void memoryGame(ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
		var width = screenInfo.getWidth();
		var height = screenInfo.getHeight();
		var margin = 50;

		var images = new ImageLoader("data", "blank.png", "lego1.png", "lego2.png", "lego3.png", "lego4.png", "lego5.png",
				"lego6.png", "lego7.png", "lego8.png");
		var data = new SimpleGameData(4, 4);
		var view = SimpleGameView.initGameGraphics(margin, margin, (int) Math.min(width, height) - 2 * margin, data,
				images);
		SimpleGameView.draw(context, data, view);

		while (true) {
			if (!gameLoop(context, data, view)) {
				System.out.println("Thank you for quitting!");
				context.exit(0);
				return;
			}
		}
	}

	/**
	 * Executable program.
	 * 
	 * @param args Spurious arguments.
	 */
	public static void main(String[] args) {
		Application.run(Color.WHITE, SimpleGameController::memoryGame);
	}
}