package fr.uge.codex;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

public class Menu {
	public void renderPlayButton(Graphics2D g2d, float playBtnX, float playBtnY, float btnW, float btnH, Rectangle2D newGameBox) {
        GradientPaint gradient = new GradientPaint(playBtnX, playBtnY, Color.LIGHT_GRAY, playBtnX, playBtnY + btnH, Color.DARK_GRAY);
        g2d.setPaint(gradient);
        g2d.fill(newGameBox);
        g2d.setColor(Color.GRAY);
        g2d.draw(newGameBox);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        String playText = "Jouer";
        int playTextWidth = g2d.getFontMetrics().stringWidth(playText);
        int playTextHeight = g2d.getFontMetrics().getAscent();
        g2d.drawString(playText, playBtnX + (btnW - playTextWidth) / 2, playBtnY + (btnH + playTextHeight) / 2 - 5);
        
	}
	
	public void renderQuitButton(Graphics2D g2d, float quitBtnX, float quitBtnY, float btnW, float btnH, Rectangle2D quitBox) {
		GradientPaint gradient = new GradientPaint(quitBtnX, quitBtnY, Color.LIGHT_GRAY, quitBtnX, quitBtnY + btnH, Color.DARK_GRAY);
		g2d.setPaint(gradient);
		g2d.fill(quitBox);
		g2d.setColor(Color.GRAY);
		g2d.draw(quitBox);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 25));
		String quitText = "Quitter";
		int quitTextWidth = g2d.getFontMetrics().stringWidth(quitText);
		int quitTextHeight = g2d.getFontMetrics().getAscent();
		g2d.drawString(quitText, quitBtnX + (btnW - quitTextWidth) / 2, quitBtnY + (btnH + quitTextHeight) / 2 - 5);
	}
	
	public void renderTitle(Graphics2D g2d, float w, float h) {
		String title = "CODEX NATURALIS";
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 80));
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (w - titleWidth) / 2, h /(10/3));
	}
	
	public boolean isClick(Event event) {
        if (event == null) {
			return false;
		}
        
        Action action = event.getAction();
        
        if (action == Action.POINTER_DOWN) {
        	return true;
        } else {
			return false;
		}
	}
	
    public void renderMenu() {
        Application.run(Color.BLACK, context -> {
            float w = context.getScreenInfo().getWidth();
            float h = context.getScreenInfo().getHeight();

            float btnW = 200;
            float btnH = 50;

            float playBtnX = w / 2 - btnW / 2;
            float playBtnY = h / 12 + h / 2 - btnH;
            var newGameBox = new Rectangle2D.Float(playBtnX, playBtnY, btnW, btnH);
            
            float quitBtnX = w / 2 - btnW / 2;
            float quitBtnY = h / 10 + h / 2;
            var quitBox = new Rectangle2D.Float(quitBtnX, quitBtnY, btnW, btnH);
            
            while (true) {
                context.renderFrame(graphics -> {
                    Graphics2D g2d = (Graphics2D) graphics;
                    renderPlayButton(g2d, playBtnX, playBtnY, btnW, btnH, newGameBox);
					renderQuitButton(g2d, quitBtnX, quitBtnY, btnW, btnH, quitBox);
					renderTitle(g2d, w, h);
                 });

                Event event = context.pollOrWaitEvent(10);
                
                if (event == null){
                	continue; 
                	
                } else if (isClick(event)) {
                    var pointeur = event.getLocation();
                    
                    if (newGameBox.contains(pointeur)) {
                        System.out.println("Bouton 'Nouvelle Partie' cliqué!");
                        break;
                    } else if (quitBox.contains(pointeur)) {
                    	context.exit(0); // On stoppe tout
                    }
                }
            }
        });
    }
}