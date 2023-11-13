package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

//import apple.laf.JRSUIConstants.State;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

import cosc202.andie.toolbar_icons.Icons;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions {

    Rectangle selection;
    Point start;

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    // get the array of icons
    Icons iconInstance = new Icons();
    ImageIcon[] iconsSmall = iconInstance.getIconsSmall();

    // Putting these up here to make shortcuts easier
    UndoAction undo = new UndoAction("Undo", iconsSmall[14], "Undo", Integer.valueOf(KeyEvent.VK_Z));
    RedoAction redo = new RedoAction("Redo", iconsSmall[15], "Redo", Integer.valueOf(KeyEvent.VK_Y));
    RotationAction rotateClock = new RotationAction("Rotate Clockwise", iconsSmall[9], "Rotate Clockwise 90 Degrees",
            Integer.valueOf(KeyEvent.VK_LEFT));
    RotationAction rotateAnti = new RotationAction("Rotate Anti-Clockwise", iconsSmall[8],
            "Rotate Anti-Clockwise 90 Degrees",
            Integer.valueOf(KeyEvent.VK_RIGHT));
    FlipAction flipVertical = new FlipAction("Vertical Flip", iconsSmall[11], "Flip Vertically",
            Integer.valueOf(KeyEvent.VK_UP));
    FlipAction flipHorizontal = new FlipAction("Horizontal Flip", iconsSmall[10], "Flip Horizontally",
            Integer.valueOf(KeyEvent.VK_DOWN));
    ResizeAction resize = new ResizeAction("Resize", iconsSmall[21], "Alter the size", Integer.valueOf(KeyEvent.VK_R));
    CropAction crop = new CropAction("Crop", iconsSmall[7], "Select want image", Integer.valueOf(KeyEvent.VK_C));
    // public Object resizeBigger;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        actions.add(undo);
        actions.add(redo);
        actions.add(rotateClock);
        actions.add(rotateAnti);
        actions.add(flipVertical);
        actions.add(flipHorizontal);
        actions.add(resize);
        actions.add(crop);
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu("Edit");

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Undo();
        }

        public void Undo() {
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Redo();
        }

        public void Redo() {
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RotationAction extends ImageAction {

        /**
         * <p>
         * Create a new rotation action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        private String name;

        RotationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
            this.name = name;
        }

        public void actionPerformed(ActionEvent e) {
            Rotations r = new Rotations();

            if (this.name == "Rotate Clockwise") {
                Rotations.rotate90degreesClockwise cW = r.new rotate90degreesClockwise();
                if (MacroEditor.current.isRecording) MacroEditor.current.AddRecordedMacro(cW);
                target.getImage().apply(cW);
            } else {
                Rotations.rotate90degreesAntiClockwise aCW = r.new rotate90degreesAntiClockwise();
                if (MacroEditor.current.isRecording) MacroEditor.current.AddRecordedMacro(aCW);
                target.getImage().apply(aCW);
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class FlipAction extends ImageAction {

        /**
         * <p>
         * Create a new flip action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        private String name;

        FlipAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
            this.name = name;
        }

        public void actionPerformed(ActionEvent e) {
            ImageFlip f = new ImageFlip();

            if (this.name == "Vertical Flip") {
                ImageFlip.verticalFlip vF = f.new verticalFlip();
                if (MacroEditor.current.isRecording) MacroEditor.current.AddRecordedMacro(vF);
                target.getImage().apply(vF);
            } else if (this.name == "Horizontal Flip") {
                ImageFlip.horizontalFlip hF = f.new horizontalFlip();
                if (MacroEditor.current.isRecording) MacroEditor.current.AddRecordedMacro(hF);
                target.getImage().apply(hF);
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Create a new rotation action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            // this.name = name;
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        public void actionPerformed(ActionEvent e) {
            setResize();
        }

        public void setResize() {
            int scale = 0;
            /**
             * option one: sidler
             */

            JSlider slider = new JSlider();
            slider.setValue(100);
            slider.setMinimum(0);
            slider.setMaximum(200);
            slider.setMajorTickSpacing(50);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            Object[] options1 = { "Apply", "Cancel" };

            int option = JOptionPane.showOptionDialog(null, slider, "RESIZE", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, iconsSmall[21], options1, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                scale = slider.getValue();
            }

            /**
             * option two: Text enter
             */
            // String name = JOptionPane.showInputDialog(null, "Please enter size (range 10~
            // 200).");
            // scale = Integer.parseInt(name);

            Resize r = new Resize();
            r.resizeScale(scale);

            target.getImage().apply(r);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * selection a area of image and cut it of.
     * <p>
     * 
     * @author Kris Mao
     * @version 2.7.2
     */
    public class CropAction extends ImageAction {

        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * create UI for user to selection the area that they willing to cropped,
         * and a 'crop it' button to crop image and return it back to main
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if (target.getImage().getCurrentImage() == null) {
                DialogBox.dialogBox("No image to be crop"); // exception handler if there is no image should jump out a
                                                            // dialog box.
            } else {
                JFrame f = new JFrame();

                JDialog preview = new JDialog(f, "preview", true);

                BufferedImage orig = target.getImage().getCurrentImage();

                BufferedImage copy = new BufferedImage(orig.getWidth(), orig.getHeight(), orig.getType());

                final JLabel screenLabel = new JLabel(new ImageIcon(copy));
                JScrollPane screenScroll = new JScrollPane(screenLabel);
                JPanel panel = new JPanel(new BorderLayout());
                final JLabel selectionLabel = new JLabel();

                screenScroll.setPreferredSize(new Dimension((int) orig.getWidth() + 100, (int) orig.getHeight() + 100));

                preview.add(panel, BorderLayout.CENTER);

                panel.add(screenLabel, BorderLayout.CENTER);
                JButton crop = new JButton("Crop it");
                crop.addActionListener(new ActionListener() {

                    // add action listener to operate crop function and shut the window
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        preview.setVisible(false);
                        Cropping c = new Cropping(selection);
                        target.getImage().apply(c);
                        target.repaint();
                        target.getParent().revalidate();
                    }

                });
                panel.add(crop, BorderLayout.AFTER_LAST_LINE);
                repaint(orig, copy);
                screenLabel.repaint();

                screenLabel.addMouseMotionListener(new MouseMotionAdapter() { // Mouse motion listener to capture users'
                                                                              // mouse movement.
                    // unused
                    @Override
                    public void mouseMoved(MouseEvent e) {
                    }

                    /**
                     *
                     * @param e
                     * @desc when dragging the mouse should draw a grey area
                     */
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        screenLabel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                        selection.setBounds((int) Math.min(start.x, e.getX()) - 50,
                                (int) Math.min(start.y, e.getY()) - 25,
                                (int) Math.abs((e.getX()) - (start.x)),
                                (int) Math.abs((e.getY()) - (start.y)));
                        repaint(orig, copy);
                        screenLabel.repaint();
                    }
                });

                screenLabel.addMouseListener(new MouseListener() { // when user press the mouse start to draw the
                                                                   // rectangle, gone if mouse released
                    // unused
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    /**
                     *
                     * @param e
                     * @desc press mouse to start selection
                     */
                    @Override
                    public void mousePressed(MouseEvent e) {

                        start = e.getPoint();
                        selection = new Rectangle(start);
                        repaint(orig, copy);
                        selectionLabel.repaint();
                    }

                    /**
                     * @desc when the mouse clicker is released the rectangle shall gone
                     */
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        screenLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        int endX = e.getX();
                        int endY = e.getY();
                        if (endX > start.x && endY > start.y) {
                            selection = new Rectangle(start.x, start.y, endX - start.x, endY -
                                    start.y);
                        }

                        repaint(orig, copy);

                    }

                    // unused
                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    // unused
                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                });

                preview.setSize(orig.getWidth() + 100, orig.getHeight() + 100);

                preview.setVisible(true);

            }
        }

        /**
         * 
         * @param orig
         * @param copy
         * @desc draw the rectangle on top of orig image
         */
        public void repaint(BufferedImage orig, BufferedImage copy) {
            Graphics2D g = copy.createGraphics();
            g.setBackground(new Color(255, 255, 255, 0));
            g.clearRect(0, 0, orig.getHeight(), orig.getHeight());
            g.drawImage(orig, 0, 0, null);
            if (selection != null) {
                g.setColor(Color.RED);
                g.draw(selection);
                g.setColor(new Color(255, 255, 255, 150));
                g.fill(selection);
                g.dispose();
            }

        }

    }

}
