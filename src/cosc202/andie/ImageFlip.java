package cosc202.andie;

import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * <p>
 * ImageOperation to flip an Image.
 * </p>
 * 
 * @author Tommy Light
 * @version 1.0
 */
public class ImageFlip implements java.io.Serializable {

    ImageFlip() {
    }

    public class horizontalFlip implements ImageOperation, java.io.Serializable {

        /**
         * <p>
         * Flips an image horizontally.
         * </p>
         * 
         * @param img the input image to be flipped
         * @return the flipped image
         */
        public BufferedImage apply(BufferedImage img) {
            AffineTransform at = new AffineTransform();
            at.concatenate(AffineTransform.getScaleInstance(-1, 1));
            at.concatenate(AffineTransform.getTranslateInstance(-img.getWidth(), 0));

            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            Graphics2D g = newImg.createGraphics();
            g.transform(at);
            g.drawRenderedImage(img, null);

            return newImg;
        }

        public String toString() {
            return "Horizontal Flip";
        }
    }

    public class verticalFlip implements ImageOperation, java.io.Serializable {

        /**
         * <p>
         * Flips an image vertically.
         * </p>
         * 
         * @param img the input image to be flipped
         * @return the flipped image
         */
        public BufferedImage apply(BufferedImage img) {
            AffineTransform at = new AffineTransform();
            at.concatenate(AffineTransform.getScaleInstance(1, -1));
            at.concatenate(AffineTransform.getTranslateInstance(0, -img.getHeight()));

            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            Graphics2D g = newImg.createGraphics();
            g.transform(at);
            g.drawRenderedImage(img, null);

            return newImg;
        }

        public String toString() {
            return "Vertical Flip";
        }
    }
}