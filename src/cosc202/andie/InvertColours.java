package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to invert the colours of an Image
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class InvertColours implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new InvertColours operation.
     * </p>
     */
    public InvertColours() {
    }

    /**
     * <p>
     * Invert the image's colours
     * </p>
     * 
     * @param input The image to invert the colours on
     * @return The resulting inverted colour image.
     */
    public BufferedImage apply(BufferedImage img) {
        try {
            RescaleOp rescaleOp = new RescaleOp((1.0f + -20 / 10.0f), (-12.75f * -20), null);
            rescaleOp.filter(img, img);
        } catch (NullPointerException e) {
            DialogBox.dialogBox("Need an image to apply filter to");
        }

        return img;

    }

}
