package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;
import java.lang.Math;

/**
 * <p>
 * New class to apply filters to the image instead of using a convolveOp.
 * </p>
 * 
 * <p>
 * Extended filter applies a given kernel to a given image including the edges
 * by making invalid pixels equal to the nearest valid pixel.
 * </p>
 * 
 * @author Tommy Light
 * @version 1.0
 */
public class ExtendedFilter {

    private BufferedImage input;
    private BufferedImage output;
    private float[] kArray;
    private float[][] kernel2d;
    private int radius;
    
    /**
     * <p>
     * Creates an extended filter with a given kernel and image.
     * </p>
     * 
     * @param input Image to apply the filter to.
     * @param k     Kernel to be applied to the image.
     */
    public ExtendedFilter(BufferedImage input, Kernel k){
        this.input = input;
        this.output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        this.kArray = k.getKernelData(null);
        this.kernel2d = new float[k.getWidth()][k.getHeight()];
        this.radius = (k.getWidth()-1)/2;

        int count = 0;
        for(int x = 0; x < k.getWidth(); x++){
            for(int y = 0; y < k.getHeight(); y++){
                this.kernel2d[x][y] = kArray[count];
                count++;
            }
        }

    }

    /**
     * <p>
     * Applies the kernel to each pixel to apply the filter to the image.
     * </p>
     * 
     * @param NEGATIVE_RESULTS Boolean to specify if the filter produces negative results.
     * @return Filtered image
     */
    public BufferedImage filter(boolean NEGATIVE_RESULTS){
        float resultR;
        float resultG;
        float resultB;
        int xBord;
        int yBord;
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                resultR = 0;
                resultG = 0;
                resultB = 0;
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        // Makes invalid pixels equal the nearest valid pixel.
                        yBord = y + dy;
                        xBord = x + dx;
                        if (yBord < 0) {
                            yBord = 0;
                        }
                        if (yBord >= input.getHeight()) {
                            yBord = input.getHeight()-1;
                        }
                        if (xBord < 0) {
                            xBord = 0;
                        }
                        if (xBord >= input.getWidth()) {
                            xBord = input.getWidth()-1;
                        }
                        Color pixel = new Color(input.getRGB(xBord, yBord));
                        resultR += kernel2d[radius + dx][radius + dy] * pixel.getRed();
                        resultG += kernel2d[radius + dx][radius + dy] * pixel.getGreen();
                        resultB += kernel2d[radius + dx][radius + dy] * pixel.getBlue();
                    }
                }
                // Applies offset for filters that produce negative results.
                if(NEGATIVE_RESULTS){
                    resultR += 128;
                    resultG += 128;
                    resultB += 128;
                }
                // Ensures red, green and blue values are valid and within expected values.
                if (resultR < 0) {
                    resultR = 0;
                }if (resultG < 0) {
                    resultG = 0;
                }if (resultB < 0) {
                    resultB = 0;
                }if (resultR > 255) {
                    resultR = 255;
                }if (resultG > 255) {
                    resultG = 255;
                }if (resultB > 255) {
                    resultB = 255;
                }
                Color outputCol = new Color(Math.round(resultR), Math.round(resultG), Math.round(resultB));
                output.setRGB(x, y, outputCol.getRGB());
            }
        }
        return output;
    }
    /**
     * <p>
     * Applies the filter assuming there are no negative values.
     * </p>
     * 
     * @return filtered image.
     */
    public BufferedImage filter() {
        return filter(false);
    }
}
