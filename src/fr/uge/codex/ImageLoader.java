package fr.uge.codex;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import fr.uge.codex.Card.ResourceType;

/**
 * The ImageLoader class deals with retrieving and storing images from files.
 * 
 * @author vincent
 *
 */
public class ImageLoader {
    public static BufferedImage get(ResourceType resource, boolean isCard) {
    	String fp;
    	String filename;
        if (isCard) {
        	fp = "./data/img/cards/";
            filename = resource.toString().toLowerCase() + ".png";
        } else {
            fp = "./data/img/icons/";
            filename = "icon_" + resource.toString().toLowerCase() + ".png";
        }

        Path imagePath = Paths.get(fp, filename);
        
        try {
        	return ImageIO.read(imagePath.toFile());
        } catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    public static void draw(Graphics2D g2d, int x, int y, BufferedImage img, double scale) {
    	if (scale == 1) {
			g2d.drawImage(img, x, y, null);
			return;
		}
    	
    	int newWidth = (int) (img.getWidth() * scale);
        int newHeight = (int) (img.getHeight() * scale);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, x, y, newWidth, newHeight, null);
    }
}