package cosc202.andie;

import java.io.Serializable;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply Edge filters to the image.
 * </p>
 * 
 * <p>
 * An Edge filter is a filter that enhances the edges
 * going either vertically of horizontally by applying
 * a sobel filter to the image.
 * </p>
 * 
 * @author Tommy Light
 * @version 1.0
 */
public class EdgeFilters implements ImageOperation, Serializable {

    private String direction;
    private float[] array;
    
    /**
     * <p>
     * Construct an Edge filter in the given direction.
     * </p>
     * 
     * <p>
     * The direction of the filter is the direction of the convolution kernel that
     * will be used.
     * The valid directions are either vertical or horizontal.
     * </p>
     * 
     * @param direction
     */
    public EdgeFilters(String direction) {
        this.direction = direction;
    }
    
    /**
     * <p>
     * Apply an Edge filter to an image.
     * </p>
     * 
     * <p>
     * The Edge filter is implemented via convolution.
     * The direction of the convolution kernel is specified by the
     * {@link direction}.
     * </p>
     * 
     * @param inputImage
     * @return The resulting (filtered) image.
     */
    public BufferedImage apply(BufferedImage inputImage) {

        try {
            if(direction=="Horizontal"){
                array = new float[]{ -1 / 2, 0, 1 / 2, -1, 0, 1, -1 / 2, 0, 1 / 2 };
            }else if (direction == "Vertical"){
                array = new float[] { -1 / 2, -1, -1 / 2, 0, 0, 0, 1 / 2, 1, 1 / 2 };
            }else{
                System.out.println("Invalid direction!");
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

    public String toString() {
        return "Edge Filter | Direction: " + direction;
    }

}