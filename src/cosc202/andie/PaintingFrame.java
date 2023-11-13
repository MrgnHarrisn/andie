package cosc202.andie;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Rectangle;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;

import cosc202.andie.PaintingFrame.PaintingWindow.ColorManager;
import cosc202.andie.PaintingFrame.PaintingWindow.ShapesManager;
import cosc202.andie.toolbar_icons.Icons;

/**
 * WARNING POORLY MADE CODE AHEAD SHOULD HAVE MADE INTO SEPERATE CLASSES
 */
public class PaintingFrame {

    public ImagePanel panel;
    public ColorManager pm;
    private ShapesManager sm;
    public static JFrame frame;
    private JButton[] shapes = { new JButton("Free Drawing"), new JButton("Ellipse"), new JButton("Rectangle"), new JButton("Filled Ellipse"), new JButton("Filled Rectangle") };
    private int action = 0;

    /**
     * Probably don't need this
     */
    PaintingFrame() {
    }

    /**
     * This is the windows that manages the overall painting and drawing control
     */
    public class PaintingWindow extends AbstractAction {

        private static Icons iconInstance = new Icons();
        private static ImageIcon[] iconsSmall = iconInstance.getIconsSmall();

        /**
         * THis is th constructor for us to begin painting
         */
        PaintingWindow(ImagePanel ip) {
            super("Draw on image", iconsSmall[24]);
            panel = ip;
            MouseActionListener mal = new MouseActionListener();
            ip.addMouseListener(mal);
            ip.addMouseMotionListener(mal);
        }

        public void createWindow() {
            // allows only one frame with the use of a singleton
            JTabbedPane tabs = new JTabbedPane();
            if (frame == null) {
                pm = new ColorManager();
                sm = new ShapesManager(shapes);
                frame = new JFrame("Painter!");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.add(tabs);
                // For some reason we have to add it here
                tabs.addTab("Color", pm);
                tabs.addTab("Shapes", sm);
                frame.setIconImage(iconsSmall[24].getImage());
                frame.pack();
                frame.setSize(700, 600);
                frame.setResizable(false);
                frame.setVisible(true);
            } else {
                frame.setVisible(true);
            }

        }

        /**
         * This is the panel that holds the color picker that allows you to choose a
         * color
         */
        public class ColorManager extends JPanel {
            JColorChooser colorPicker = new JColorChooser();
            private JSpinner spinner = new JSpinner();

            ColorManager() {
                add(colorPicker);
                add(new JLabel("  "));
                add(new JLabel("Bush Size"));
                add(spinner);
            }

            /**
             * Get's the colo from the color picker
             * 
             * @return
             */
            public Color getColor() {
                return colorPicker.getColor();
            }

            /**
             * Returns the brush size
             * @return
             */
            public Integer getBrushSize() {
                Integer output = (Integer) spinner.getValue();
                return output;
            }

        }

        /**
         * This adds the shapes cnotrol buttons to the screen
         */
        public class ShapesManager extends JPanel {

            /**
             * Constructor for a panel that shows shapes to draw
             */
            ShapesManager(JButton[] btns) {
                int i = 25;
                for (JButton btn : btns) {
                    add(btn);
                    btn.addActionListener(new ButtonListener());
                    btn.setIcon(iconsSmall[i]);
                    i++;
                }
            }
        }

        /**
         * This is the class that listens to the button clicks
         */
        public class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(shapes[0])) {
                    // System.out.println("Free Drawing");
                    action = 0;
                } else if (e.getSource().equals(shapes[1])) {
                    // System.out.println("Drawing a Circle");
                    action = 1;
                } else if (e.getSource().equals(shapes[2])) {
                    // System.out.println("Drawing a Square");
                    action = 2;
                } else if (e.getSource().equals(shapes[3])) {
                    action = 3;
                } else {
                    action = 4;
                }
                // System.out.println("Action: " + action);
            }

        }

        /**
         * We can check for mosue inputs here
         */

        @Override
        public void actionPerformed(ActionEvent e) {

            createWindow();

        }

    }

    /**
     * This checks your mouse actions
     */
    private class MouseActionListener implements MouseMotionListener, MouseListener {

        public ArrayList<Location> pixels = new ArrayList<Location>(); // For free drawing

        // these will store the positions for shapes
        private Location last, current;

        // I need to make this so much better what am I even looking at...

        @Override
        public void mouseClicked(MouseEvent e) {

            if (frame != null && frame.isVisible()) {
                // pixels.add(new Location(e.getX(), e.getY()));
                // panel.getImage().apply(new DrawLine(pixels, pm.getColor(), pm.getBrushSize()));
                // panel.getParent().revalidate();
                // panel.repaint();
                // pixels.clear();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (frame != null && frame.isVisible()) {
                if (action == 0) {
                    // Do this if we aren't jsut drawing anything
                    pixels.add(getScaledLocation(e.getX(), e.getY()));
                } else {
                    last = getScaledLocation(e.getX(), e.getY());
                }
            }
        }

        public Location[] toArray(ArrayList<Location> l) {
            Location[] output = new Location[l.size()];
            int i = 0;
            for (Location loca : l) {
                output[i] = loca;
                i++;
            }
            return output;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // System.out.println(pixels);
            if (frame != null && frame.isVisible()) {
                if (action == 0) {
                    panel.getImage().apply(new DrawLine(toArray(pixels), pm.getColor(), pm.getBrushSize()));
                    panel.getParent().revalidate();
                    panel.repaint();
                    pixels.clear();
                } else if (action == 1 || action == 3) {   // we actually shouldn't be drawing them here
                DrawOval(last, current);
            } else if (action == 2 || action == 4) {
                DrawRect(last, current);
            }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // System.out.println("Mouse Entered");

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // System.out.println("Mouse exited");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // System.out.println("Dragging");

            if (frame != null && frame.isVisible()) {
                current = getScaledLocation(e.getX(), e.getY());
                // Why is there still an offset??
                if (action == 0) {
                    pixels.add(getScaledLocation(e.getX(), e.getY()));
                } else {
                    current = getScaledLocation(e.getX(), e.getY());
                }
            }
        }
        
        /**
         * Draws a rectangle to the screen
         * @param start starting point the user drew
         * @param end end point hte user stopped drawing
         */
        public void DrawRect(Location start, Location end) {
            Rectangle r = new Rectangle();
            r.setBounds((int) Math.min(start.x, end.x),
                                (int) Math.min(start.y, end.y),
                                (int) Math.abs((end.x - 20) - (start.x - 20)),
                                (int) Math.abs((end.y - 20) - (start.y - 20)));
            if (action == 2) {
                panel.getImage().apply(new DrawRect(r, pm.getColor(), pm.getBrushSize(), false));
            } else {
                panel.getImage().apply(new DrawRect(r, pm.getColor(), pm.getBrushSize(), true));
            }
            panel.repaint();
            panel.getParent().revalidate();
        }

        /**
         * Draws a circle to the screen
         * @param start starting point the user drew
         * @param end end point hte user stopped drawing
         */
        public void DrawOval(Location start, Location end) {
            // System.out.println("Drawing a Rectangle");
            if (action == 1) {
                panel.getImage().apply(new DrawCircle(start, end, pm.getColor(), pm.getBrushSize(), false));
            } else {
                panel.getImage().apply(new DrawCircle(start, end, pm.getColor(), pm.getBrushSize(), true));
            }
            panel.repaint();
            panel.revalidate();
        }

        

        public Location getScaledLocation(int mX, int mY) {

                // Calculate the actual coordinates based on the zoom
                double zoom = panel.getZoom() / 100;

                // The current image width and height with zoom
                int currentWidth = (int) (panel.getImage().getCurrentImage().getWidth() * zoom);
                int currentHeight = (int) (panel.getImage().getCurrentImage().getHeight() * zoom);

                int x = mX;
                int y = mY;
                int newX = (x * panel.getImage().getCurrentImage().getWidth())/currentWidth;
                int newY = (y * panel.getImage().getCurrentImage().getHeight()/currentHeight);
                return new Location(newX, newY);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

            // This is not needed

        }

    }

}
