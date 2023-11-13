package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;

/**
 * An image operation that draws pixels to the screen in an array with a selected
 */
public class DrawLine implements ImageOperation {

    private Location[] positions = null;
    private Color c;
    private int width;

    /**
     * Image operation that draws a line over the image
     * @param l the array of locations we want to draw lines between
     * @param c the color we want the line to be
     * @param width the brush size
     */
    DrawLine(Location[] l, Color c, int width) {
        positions = l;
        this.c = c;
        this.width = width;
    }

    private RenderingHints getHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return hints;
    }

    /**
     * This does not currently allow for a redo? Maybe doesn't keep the pixels that are stored in an ArrayList?
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Need to make the size of the brush bigger
        Graphics2D g = input.createGraphics();
        g.setRenderingHints(getHints());
        g.setStroke(new BasicStroke(width));
        g.setColor(c);
        // This stops it from painting when there is no color selected
        try {
            // System.out.println("Doing this");
            for (int i = 1; i < positions.length; i++) {
                // get the location
                Location current = positions[i];
                Location last = positions[i-1];
                if (positions.length > 1) {
                    try {
                        // set the location to the color we want
                        g.drawLine(current.x, current.y, last.x, last.y);
                    } catch (Exception e) {
                        // This stops it from painting pixels not on the screen
                    }
                } else {

                }

            }
        } catch (Exception e) {

        }
        // Return the image
        return input;
    }

}