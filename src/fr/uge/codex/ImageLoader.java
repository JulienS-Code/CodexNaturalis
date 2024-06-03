package fr.uge.codex;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import fr.uge.codex.deck.card.CornerType;

public class ImageLoader {
    private static final Map<Path, BufferedImage> imageCache = new ConcurrentHashMap<>();

    public static BufferedImage get(CornerType resource, boolean isCard) {
        return get(resource, isCard, false);
    }

    public static BufferedImage get(CornerType resource, boolean isCard, boolean isGolden) {
        Objects.requireNonNull(resource);

        Path imagePath;
        if (isCard) {
            if (isGolden) {
                imagePath = Paths.get("./data/img/cards/golden", "golden_" + resource.toString().toLowerCase() + ".png"); // golden card
            } else {
                imagePath = Paths.get("./data/img/cards", resource.toString().toLowerCase() + ".png"); // normal card
            }
        } else {
            imagePath = Paths.get("./data/img/icons", "icon_" + resource.toString().toLowerCase() + ".png"); // icon
        }

        return imageFromPath(imagePath);
    }

    public static BufferedImage imageFromPath(Path path) {
        return imageCache.computeIfAbsent(path, p -> {
            try {
                return ImageIO.read(p.toFile());
            } catch (IOException e) {
                System.out.println(p); // To display the path that could not be found
                throw new RuntimeException(e);
            }
        });
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
