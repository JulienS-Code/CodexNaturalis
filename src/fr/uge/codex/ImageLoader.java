package fr.uge.codex;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import fr.uge.codex.card.CornerType;

/**
 * The ImageLoader class deals with retrieving and storing images from files.
 * 
 * @author vincent
 *
 */
public class ImageLoader {
    public static BufferedImage get(CornerType resource, boolean isCard) {
        if (isCard) {
            return imageFromPath(
        		"./data/img/cards/" + resource.toString().toLowerCase() + ".png"
    		);
        }
        
        return imageFromPath(
        	"./data/img/icons/icon_ " + resource.toString().toLowerCase() + ".png"
        );
    }

    public static BufferedImage imageFromPath(String path) {
        try {
	        return ImageIO.read(Paths.get(path).toFile());
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
