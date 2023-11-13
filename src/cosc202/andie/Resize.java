package cosc202.andie;

import java.awt.image.*;
import java.awt.Graphics2D;

/**
 * <p>
 * Image Operation of resize an image;
 * <p>
 * 
 * @author Kris Mao
 * @ vision 1.2
 */
public class Resize implements ImageOperation {

    public int scale;

    /**
     * <p>
     * 
     * @param scale : scale of image resize;
     */
    public void resizeScale(int scale) {
        this.scale = scale;
    }

    /**
     * <p>
     * resize bigger by 50%
     * <p>
     * 
     * @param img : image need to be resized;
     * @return : image get small or big along with exist silder
     */
    public BufferedImage apply(BufferedImage img) {

        try {

            // System.out.println(this.scale);
            int width = img.getWidth();
            int height = img.getHeight();

            // System.out.println(percent);
            int scaledWidth = (int) ((int) (width * scale) / 100);
            int scaledHeight = (int) ((int) (height * scale) / 100);

            BufferedImage output = new BufferedImage(scaledWidth, scaledHeight, img.getType());

            Graphics2D g2d = output.createGraphics();
            try {
                g2d.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
            } catch (IllegalArgumentException e) {
                DialogBox.dialogBox("Cannot be increased by 0");
            }
            g2d.dispose();
            return output;
        } catch (NullPointerException e) {
            DialogBox.dialogBox("You need an image to apply filter to");
            return img; // This is still going to keep the filter running i think (So it allows it to
                        // undo when nothing has happened...)
        }
    }

    /**
     * @return scale
     * @desc purely test purpose
     */
    public int getScale() {
        return scale;
    }

}
