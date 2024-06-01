package fr.uge.codex;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.uge.codex.card.CornerType;

public class ImageLoader {
	
	public static BufferedImage get(CornerType resource, boolean isCard) {
		return get(resource, isCard, false);
	}
	
    public static BufferedImage get(CornerType resource, boolean isCard, boolean isGolden) {
        Objects.requireNonNull(resource);
    	
    	if (isCard) {
        	if (isGolden) {
        		return imageFromPath(
            		Paths.get("./data/img/cards/golden", "golden_" + resource.toString().toLowerCase() + ".png")
        		); // golden card
        	}
            return imageFromPath(
        		Paths.get("./data/img/cards", resource.toString().toLowerCase() + ".png")
    		); // normal card
        }
        
        return imageFromPath(
        	Paths.get("./data/img/icons", "icon_" + resource.toString().toLowerCase() + ".png")
        ); // icon
    }

    public static BufferedImage imageFromPath(Path path) {
        try {
	        return ImageIO.read(path.toFile());
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
