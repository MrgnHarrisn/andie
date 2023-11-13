package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;
import java.util.*;

/**
 * <p>
 * ImageOperatio to apply a median filter
 * </p>
 * 
 * <p>
 * a median filter that blurs and image by replacing a pixel with the median RGB
 * values around it
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Morgan Harrison
 * @verson 1.0
 * 
 */

public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of the filter to apply. a radius of 1 is a 3x3 filter a radius of 2
     * is a 5x5 filter.
     */

    private int radius;

    public MedianFilter(int radius) {
        this.radius = radius;
    }

    public MedianFilter() {
        this.radius = 1;
    }

    /**
     * <p>
     * Apply a median filter to the image
     * </p>
     * 
     * <p>
     * The median filter is being applied through a neighbourhood, this can cause
     * problems
     * at the edge of the screen but we solve that by setting the x and y to the
     * radius
     * </p>
     * 
     * @param input the image we are applying the median blur to
     * @return The resulting (blurred)) image.
     */

    @Override
    public BufferedImage apply(BufferedImage img) {
        BufferedImage output = new BufferedImage(img.getColorModel(), img.copyData(null), img.isAlphaPremultiplied(), null);

        int mask = (2 * radius + 1) * (2 * radius + 1);
        int size = (2 * radius + 1);

        Color[] pixels = new Color[mask];

        int[] r = new int[mask];
        int[] g = new int[mask];
        int[] b = new int[mask];

        // Itterate through the image skip to the mid point (not like they can see the
        // image edge anyway)
        try {
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    // Insert spaghetti code here... again mmm very italian

                    // Coutner for the pixels in the mask
                    int counter = 0;

                    // create the kernel

                    for (int kernely = 0; kernely < size; kernely++) {
                        for (int kernelx = 0; kernelx < size; kernelx++, counter++) {
                            int yBord = y + kernely;
                            int xBord = x + kernelx;
                            if (yBord < 0) {
                                yBord = 0;
                            }
                            if (yBord >= img.getHeight()) {
                                yBord = img.getHeight() - 1;
                            }
                            if (xBord < 0) {
                                xBord = 0;
                            }
                            if (xBord >= img.getWidth()) {
                                xBord = img.getWidth() - 1;
                            }

                            pixels[counter] = new Color(img.getRGB(xBord, yBord));

                            // get the pixel's colors
                            // could maybe sort them here?
                            r[counter] = pixels[counter].getRed();
                            g[counter] = pixels[counter].getGreen();
                            b[counter] = pixels[counter].getBlue();
                        }
                    }

                    // sort the array
                    Arrays.sort(r);
                    Arrays.sort(g);
                    Arrays.sort(b);

                    // Arrays.sort(pixels);

                    Color col = new Color(r[r.length / 2], g[g.length / 2], b[b.length / 2]);

                    output.setRGB(x, y, col.getRGB());
                }

            }
        } catch (NullPointerException e) {
            DialogBox.dialogBox("You need an image to apply filter to");
        } catch (ImagingOpException e) {
            DialogBox.dialogBox("You cannot use a negative mask size");
        }
        return output;

    }

    /**
     * @return
     * @desc test purpose, not use in Andie (Hey i ended up finding a use)
     */
    public int getRadius() {
        return radius;
    }

    public String toString() {
        return "Median Filter | Size: "+getRadius();
    }

}
