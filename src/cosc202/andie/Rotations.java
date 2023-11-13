package cosc202.andie;

import java.awt.image.*;
import java.awt.Graphics2D;

/**
 * <p>
 * ImageOperation to rotate an Image
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class Rotations implements java.io.Serializable {

    public Rotations() {
    }

    public class rotate90degreesAntiClockwise implements ImageOperation, java.io.Serializable {
        /**
         * <p>
         * Rotates a image by 90 degrees Anti Clockwise
         * </p>
         * 
         * @param img the input image to be rotated
         * @return the rotated image
         */
        public BufferedImage apply(BufferedImage img) {

            try {
                int h = img.getHeight();
                int w = img.getWidth();

                BufferedImage newImg = new BufferedImage(h, w, img.getType());

                Graphics2D g = newImg.createGraphics();
                g.rotate(-Math.PI / 2, w / 2, h / 2);
                g.translate((h - w) / 2, (h - w) / 2);
                g.drawRenderedImage(img, null);
                return newImg;
            } catch (NullPointerException e) {
                DialogBox.dialogBox("You need an image to apply filter to");
                return img;
            }
        }

        public String toString() {
            return "90° Rotation - Anti-Clockwise";
        }
    }

    public class rotate90degreesClockwise implements ImageOperation, java.io.Serializable {
        /**
         * <p>
         * Rotates a image by 90 degrees Clockwise
         * </p>
         * 
         * @param img the input image to be rotated
         * @return the rotated image
         */
        public BufferedImage apply(BufferedImage img) {

            if (img == null) {DialogBox.dialogBox("Need an image to edit");}

            int h = img.getHeight();
            int w = img.getWidth();

            BufferedImage newImg = new BufferedImage(h, w, img.getType());

            Graphics2D g = newImg.createGraphics();
            g.rotate(Math.PI / 2, w / 2, h / 2);
            g.translate((w - h) / 2, (w - h) / 2);
            g.drawRenderedImage(img, null);
            return newImg;
        }

        public String toString() {
            return "90° Rotation - Clockwise";
        }
    }

}
