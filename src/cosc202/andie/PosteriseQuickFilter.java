package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to alter the number of colours used in an Image
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class PosteriseQuickFilter implements ImageOperation, java.io.Serializable {

    private int[] availableValues;
    private static final int TOTAL_VALUES = 256;
    int colourChannels;

    public PosteriseQuickFilter(int numValues) {
        colourChannels = numValues;
        this.availableValues = new int[numValues];
        availableValues[0] = 0;
        availableValues[numValues - 1] = 255;

        int interval = TOTAL_VALUES / (numValues - 1);

        for (int i = 1; i < numValues - 1; i++) {
            availableValues[i] = availableValues[i - 1] + interval;
        }

    }

    /**
     * <p>
     * Apply the specified amount of posterise to the image
     * </p>
     * 
     * @param input The image to alter the contrast on
     * @return The resulting posterised image.
     */
    public BufferedImage apply(BufferedImage img) {

        try {
            int height = img.getTileHeight();
            int width = img.getTileWidth();
            Color colour, snappedColour;
            int r, g, b;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    colour = new Color(img.getRGB(x, y));
                    r = snapToNearest(colour.getRed());
                    g = snapToNearest(colour.getGreen());
                    b = snapToNearest(colour.getBlue());

                    snappedColour = new Color(r, g, b);
                    img.setRGB(x, y, snappedColour.getRGB());
                }

            }

        } catch (NullPointerException e) {
            DialogBox.dialogBox("Need an image to apply filter to");
        }
        return img;

    }

    private int snapToNearest(int value) {
        int minDistance = 300;
        int snappedValue = value;

        for (int i : availableValues) {
            if (Math.abs(i - value) < minDistance) {
                minDistance = Math.abs(i - value);
                snappedValue = i;
            }

        }

        return snappedValue;
    }

    public String toString() {
        return "Posterise Filter | Colour Channels: " + colourChannels;
    }

}
