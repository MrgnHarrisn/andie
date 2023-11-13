package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to alter the contrast of an Image
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class ContrastFilter implements ImageOperation, java.io.Serializable {

    private int conValue;

    /**
     * <p>
     * Create a new ContrastFilter operation.
     * </p>
     * 
     * @param conValue the input value of how much to change the contrast by
     */
    public ContrastFilter(int conValue) {
        this.conValue = conValue;
    }

    /**
     * <p>
     * Apply the specified amount of contrast to the image
     * </p>
     * 
     * @param input The image to alter the contrast on
     * @return The resulting contrasted image.
     */
    public BufferedImage apply(BufferedImage img) {
        float scaler;
        try {
            scaler = 1.0f + conValue / 10.0f;

            System.out.println(scaler);
            RescaleOp rescaleOp = new RescaleOp(scaler, (-12.75f * conValue), null);
            rescaleOp.filter(img, img);
        } catch (NullPointerException e) {
            DialogBox.dialogBox("Need an image to apply filter to");
        }

        return img;

    }

    /**
     * @return conValue
     * @desc test
     */
    public int getConValue() {
        return conValue;
    }

    public String toString() {
        return "Contrast Filter | Size: " + conValue;
    }
}
