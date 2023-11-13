package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian (blur) filter to the image.
 * </p>
 * 
 * <p>
 * A Gaussian filter is a filter that blurs an image by replacing each pixel
 * with the
 * average of nearby pixels with a preference to nearer pixels, within a given
 * radius.
 * </p>
 * 
 * @author Tommy Light
 * @version 1.0
 */
public class GaussianFilter implements ImageOperation, java.io.Serializable {
    private int radius;
    private int size;
    private float[] array;

    /**
     * <p>
     * Construct a Gaussian filter with the given size.
     * </p>
     * 
     * <p>
     * Functions identically to the Mean filter therefore the following explanation
     * is the same in both.
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    public GaussianFilter(int radius) {
        this.radius = radius;
        this.size = (2*radius+1) * (2*radius+1);
        this.array = new float[size];
        // Creating the kernel array to apply to the image.
        double s = (double)radius/3;
        int count = 0;
        float total = 0;
        for(int x = -radius; x <= radius; x++){
            for(int y = -radius; y <= radius; y++){
                double ans = 1 / (2 * Math.PI * Math.pow(s, 2)) * Math.pow(Math.E, -((Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(s, 2))));
                array[count] = (float)(ans);
                total += ans;
                count++;
            }
        }
        for(int i = 0; i < size; i++){
            array[i] = array[i] / total;
        }


    }

    /**
     * <p>
     * Construct a Gaussian filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Gaussian filter has radius 1.
     * </p>
     * 
     * @param radius
     */
    public GaussianFilter(){
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * <p>
     * The Gaussian filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Applying kernel to image.
        try {
            Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
            ExtendedFilter eF = new ExtendedFilter(input, kernel);
            return eF.filter();
        } catch (NullPointerException e) {
            DialogBox.dialogBox("An image is required to apply the method");
        } catch (ImagingOpException e) {
            DialogBox.dialogBox("-" + size + " is not a valid mask size");
        } catch (Exception e) {
            DialogBox.dialogBox(e.toString());
        }

        return input;

    }


    public float[] getKernelArray(){
        return this.array;
    }

    public String toString() {
        return "Gaussian Filter | Size: "+size;
    }
}