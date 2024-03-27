package fr.uge.zen5.demo;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event.Action;

class Area {
	private Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, 0, 0);

	void draw(ApplicationContext context, float x, float y) {
		context.renderFrame(graphics -> {
			// hide the previous rectangle
			graphics.setColor(Color.ORANGE);
			graphics.fill(ellipse);

			// show a new ellipse at the position of the pointer
			graphics.setColor(Color.MAGENTA);
			ellipse = new Ellipse2D.Float(x - 20, y - 20, 40, 40);
			graphics.fill(ellipse);
		});
	}
}

public class Demo {

	private static void checkRange(double min, double value, double max) {
		if (value < min || value > max) {
			throw new IllegalArgumentException("Invalid coordinate: " + value);
		}
	}

	public static void main(String[] args) {
		Application.run(Color.ORANGE, context -> {
			var counter = 0;
			var screenInfo = context.getScreenInfo();
			var width = screenInfo.getWidth();
			var height = screenInfo.getHeight();
			System.out.println("size of the screen (" + width + " x " + height + ")");

			context.renderFrame(graphics -> {
				graphics.setColor(Color.ORANGE);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			});

			var area = new Area();
			while (true) {
				var event = context.pollOrWaitEvent(10);
				if (event == null) {
					continue;
				}
				var action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					System.out.println(event.getKey());
				} else if (action == Action.KEY_RELEASED) {
					counter++;
					if (counter == 10) {
						context.exit(0);
						return;
					}
				} else {
					var location = event.getLocation();
					checkRange(0, location.x, width);
					checkRange(0, location.y, height);
					area.draw(context, location.x, location.y);
				}

			}
		});
	}
}