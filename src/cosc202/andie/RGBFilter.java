package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to alter the image to only be displayed in Red, Green, or Blue
 * values
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class RGBFilter implements ImageOperation, java.io.Serializable {

    private int RGOrB;// 0 = red, 1 = green, 2 = blue
    private String value;

    /**
     * <p>
     * Create a new RGBFilter operation.
     * </p>
     * 
     * @param RGOrB the input value of which color filter to apply
     */
    public RGBFilter(int RGOrB) {
        this.RGOrB = RGOrB;
    }

    /**
     * <p>
     * Apply the colour filter to the image
     * </p>
     * 
     * @param input The image to alter the contrast on
     * @return The resulting altered image.
     */
    public BufferedImage apply(BufferedImage img) {
        try {

            int height = img.getTileHeight();
            int width = img.getTileWidth();
            Color color, alteredColor;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    color = new Color(img.getRGB(x, y));

                    switch (RGOrB) {

                        case 0:// Red
                            alteredColor = new Color(color.getRed(), 0, 0);
                            value = "Red";
                            break;

                        case 1:// Green
                            alteredColor = new Color(0, color.getGreen(), 0);
                            value = "Green";
                            break;

                        case 2:// Blue
                            alteredColor = new Color(0, 0, color.getBlue());
                            value = "Blue";
                            break;

                        default:
                            alteredColor = new Color(color.getRed(), 0, 0);
                            value = "Red";

                            break;
                    }
                    img.setRGB(x, y, alteredColor.getRGB());
                }

            }

        } catch (NullPointerException e) {
            DialogBox.dialogBox("Need an image to apply filter to");
        }

        return img;

    }

    /**
     * @return value
     * @desc test
     */
    public String getValue() {
        return value;
    }

    public String toString() {
        return "RGB Filter | Size: " + value;
    }
}
