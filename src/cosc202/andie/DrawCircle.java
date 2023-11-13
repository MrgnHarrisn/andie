package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
public class DrawCircle implements ImageOperation {

    private Location start;
    private Location end;
    private Color c;
    private int width;
    private boolean fill;

    /**
     * 
     * @param start starting point for the circle
     * @param end end point for the circle
     * @param c the color of the circle
     * @param width this is the brush size
     * @param fill weather or not it should be filled in
     */
    DrawCircle(Location start, Location end, Color c, int width, boolean fill) {
        this.start = start;
        this.end = end;
        this.c = c;
        this.width = width;
        this.fill = fill;
    }

    private RenderingHints getHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return hints;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setRenderingHints(getHints());
        g.setStroke(new BasicStroke(width));
        g.setColor(c);
        if (fill) {
            g.fillOval((int) Math.min(start.x, end.x),
                (int) Math.min(start.y, end.y),
                (int) Math.abs((end.x - 20) - (start.x - 20)),
                (int) Math.abs((end.y - 20) - (start.y - 20)));
        } else {
            g.drawOval((int) Math.min(start.x, end.x),
                (int) Math.min(start.y, end.y),
                (int) Math.abs((end.x - 20) - (start.x - 20)),
                (int) Math.abs((end.y - 20) - (start.y - 20)));
        }
        g.dispose();
        return input;
    }

}
