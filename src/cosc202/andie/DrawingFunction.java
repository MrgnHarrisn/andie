package cosc202.andie;

import java.awt.*;
import java.awt.event.*;

public class DrawingFunction extends Frame implements MouseMotionListener {

    DrawingFunction() {
        addMouseMotionListener(this);
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        Graphics g = getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(e.getX(), e.getY(), 20, 20);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**public static void main(String[] args) {
        new DrawingFunction();
    }*/

}
