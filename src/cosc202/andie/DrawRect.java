package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class DrawRect implements ImageOperation {

    private Rectangle r;
    private Color c;
    private int width;
    private boolean fill;

    DrawRect(Rectangle r, Color c, int width, boolean fill) {
        this.r = r;
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
            g.fillRect(r.x, r.y, r.width, r.height);
        } else {
            g.drawRect(r.x, r.y, r.width, r.height);
        }
        g.dispose();
        return input;
    }

}
