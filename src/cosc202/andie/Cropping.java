package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Allow user to select the area they want of the image
 * <p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Kris Mao
 * @version 2.0
 */

public class Cropping implements ImageOperation {
    private Rectangle selection;
    private double X;
    private double Y;
    private double W;
    private double H;

    private int x;
    private int y;
    private int w;
    private int h;

    /**
     * @desc Construer
     * @param selection
     */
    public Cropping(Rectangle selection) {
        this.selection = selection;
    }

    public Rectangle getSelection() {
        return selection;
    }

    /**
     * load the size and location of the pass in rectangle
     * and return a copy of the rectangle size of original image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        X = selection.getX();
        Y = selection.getY();
        W = selection.getWidth();
        H = selection.getHeight();

        x = (int) X;
        y = (int) Y;
        w = (int) W;
        h = (int) H;

        if (X < 0) {// if point of X coordinate is out of edge
            x = 0;
        }
        if (Y < 0) {// if point of Y coordinate is out of edge
            y = 0;
        }
        if (X + W > input.getWidth()) {// if rectangle occur out of edge
            w = (int) (input.getWidth() - X);
        }
        if (Y + H > input.getHeight()) {// if rectangle occur out of edge
            h = (int) (input.getHeight() - y);
        }

        return input.getSubimage(x, y, w, h);

    }

}