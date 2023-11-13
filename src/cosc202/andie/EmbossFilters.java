package cosc202.andie;

import java.io.Serializable;
import java.awt.image.*;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to apply Emboss filters to the image.
 * </p>
 * 
 * <p>
 * An Emboss filter is a filter that enhances the edges
 * in the direction of the convolution within an image.
 * </p>
 * 
 * @author Tommy Light
 * @version 1.0
 */
public class EmbossFilters implements ImageOperation, Serializable {

    private int direction;
    
    /**
     * <p>
     * Construct an Emboss filter in the given direction.
     * </p>
     * 
     * <p>
     * The direction of the filter is the direction of the convolution kernel that will be used.
     * A direction of 0 indicates up and to the left and 8 to the bottom right.
     * </p>
     * 
     * @param direction
     */
    public EmbossFilters(int direction) {
        this.direction = direction;
        System.out.println("Direction: "+direction);
    }
    
    /**
     * <p>
     * Apply an Emboss filter to an image.
     * </p>
     * 
     * <p>
     * The Emboss filter is implemented via convolution.
     * The direction of the convolution kernel is specified by the
     * {@link direction}.
     * </p>
     * 
     * @param inputImage
     * @return The resulting (filtered) image.
     */
    public BufferedImage apply(BufferedImage inputImage) {

        try {
            float[] array = new float[9];
            Arrays.fill(array, 0);
            if (direction < 5) {
                array[direction] = 1;
                array[8 - direction] = -1;
            } else if (direction >= 5) {
                array[direction] = -1;
                array[8 - direction] = 1;
            } else {
                DialogBox.dialogBox("Invalid direction!");
                return inputImage;
            }
            Kernel kernel = new Kernel(3, 3, array);

            ExtendedFilter ef = new ExtendedFilter(inputImage, kernel);
            return ef.filter(true);

        } catch (Exception e) {
            DialogBox.dialogBox("You must select an image first!");
            System.out.println(e);
            return inputImage;
        }
    }
    
    /**
     * <p>
     * Gives the direction of the filter as a string
     * </p>
     * 
     * <p>
     * The direction of the convolution kernel is specified by the
     * {@link direction}.
     * </p>
     * 
     * @return The direction of the filter as a string.
     */
    public String GetDirectionName() {
        switch (direction) {
            case 0:
                return "Top Left";
            case 1:
                return "Up";
            case 2:
                return "Top Right";
            case 3:
                return "Left";
            case 5:
                return "Right";
            case 6:
                return "Bottom Left";
            case 7:
                return "Down";
            case 8:
                return "Bottom Right";
            default:
                return "Fail";
        }
    }

    public String toString() {
        return "Emboss Filter | Direction: "+GetDirectionName();
    }
}