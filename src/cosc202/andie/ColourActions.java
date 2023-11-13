package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.toolbar_icons.Icons;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    // get the array of icons
    Icons iconInstance = new Icons();
    ImageIcon[] iconsSmall = iconInstance.getIcons();

    // Variable for shortcut to reach
    public ConvertToGreyAction greyscale = new ConvertToGreyAction("Greyscale", iconsSmall[6], "Convert to greyscale",
            Integer.valueOf(KeyEvent.VK_G));
    public BrightnessFilterAction brightness = new BrightnessFilterAction("Brightness", iconsSmall[5],
            "Change Image Brightness",
            Integer.valueOf(KeyEvent.VK_B));
    public ContrastFilterAction contrast = new ContrastFilterAction("Contrast", iconsSmall[4], "Change Image Contrast",
            Integer.valueOf(KeyEvent.VK_C));
    public PosteriseQuickFilterAction posteriseQuick = new PosteriseQuickFilterAction("Quick Posterise", iconsSmall[16],
            "Change Colours available",
            null);
    public PosteriseKMeansFilterAction posteriseKMeans = new PosteriseKMeansFilterAction("Posterise", iconsSmall[16],
            "Change Colours available",
            null);
    public InvertColoursAction invert = new InvertColoursAction("Invert Colours", iconsSmall[19],
            "Invert Image Colours",
            null);
    public RGBFilterAction rgb = new RGBFilterAction("RGB filters", iconsSmall[30],
            "Red, Green, and Blue filters",
            null);

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(greyscale);
        actions.add(brightness);
        actions.add(contrast);
        actions.add(invert);
        actions.add(rgb);
        actions.add(posteriseKMeans);
        actions.add(posteriseQuick);

        for (int i = 0; i < iconsSmall.length; i++) {// resize the icons to better fit in the drop down menu
            iconsSmall[i].setImage(iconsSmall[i].getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
        }
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                setGreyscale();
            } catch (Exception exc) {
                return;
            }
        }

        public void setGreyscale() {
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class BrightnessFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        public void actionPerformed(ActionEvent e) {
            SetBrightness();
        }

        public void SetBrightness() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(0);
            slider.setMinimum(-10);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(2);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            int option = JOptionPane.showOptionDialog(null, slider, "Enter Brightness Amount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconsSmall[5], null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = slider.getValue();

            }

            target.getImage().apply(new BrightnessFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class ContrastFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new contrast action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ContrastFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        public void actionPerformed(ActionEvent e) {
            SetContrast();
        }

        public void SetContrast() {

            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(0);
            slider.setMinimum(-10);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(2);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            int option = JOptionPane.showOptionDialog(null, slider, "Enter Contrast Amount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconsSmall[4], null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                if (slider.getValue() == -10) {// stops from going completly grey
                    radius = slider.getValue() + 1;
                } else {
                    radius = slider.getValue();
                }
            }

            target.getImage().apply(new ContrastFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class InvertColoursAction extends ImageAction {

        /**
         * <p>
         * Create a new invert colours action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        InvertColoursAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            setInvert();
        }

        public void setInvert() {

            target.getImage().apply(new InvertColours());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class PosteriseQuickFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Posterise Quick action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PosteriseQuickFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            setPosteriseQuick();
        }

        public void setPosteriseQuick() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(0);
            slider.setMinimum(2);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(2);
            slider.setMinorTickSpacing(1);
            slider.setLabelTable(slider.createStandardLabels(2, 2));
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setSnapToTicks(true);

            int option = JOptionPane.showOptionDialog(null, slider, "Enter Number of Channel Colours",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconsSmall[16], null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {

                radius = slider.getValue();

            }

            target.getImage().apply(new PosteriseQuickFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class PosteriseKMeansFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Posterise K-means action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PosteriseKMeansFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            setPosteriseKMeans();
        }

        public void setPosteriseKMeans() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(0);
            slider.setMinimum(2);
            slider.setMaximum(50);
            slider.setMajorTickSpacing(10);
            slider.setMinorTickSpacing(2);
            slider.setLabelTable(slider.createStandardLabels(10, 10));
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setSnapToTicks(true);

            int option = JOptionPane.showOptionDialog(null, slider, "Enter Number of Colours",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconsSmall[16], null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {

                radius = slider.getValue();

            }

            target.getImage().apply(new PosteriseKMeansFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class RGBFilterAction extends ImageAction {

        private JButton[] buttons;
        private int value;

        /**
         * <p>
         * Create a new RGB Filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RGBFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            buttons = new JButton[3];
        }

        public void actionPerformed(ActionEvent e) {
            setRGBFilter();
        }

        /**
         * Choose and invoke the RGB Filter
         */
        public void setRGBFilter() {
            value = 0;
            JButton red = new JButton("Red", iconsSmall[31]);
            JButton green = new JButton("Green", iconsSmall[32]);
            JButton blue = new JButton("Blue", iconsSmall[33]);

            buttons[0] = red;
            buttons[1] = green;
            buttons[2] = blue;

            ActionListener listener = new ButtonListener();
            for (JButton button : buttons) {
                button.addActionListener(listener);
            }

            int option = JOptionPane.showOptionDialog(null, buttons, "Choose Colour Filter",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconsSmall[30], null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {

            }

            target.getImage().apply(new RGBFilter(value));
            target.repaint();
            target.getParent().revalidate();
        }

        /**
         * Class for handling button presses
         */
        private class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(buttons[0])) {// red
                    value = 0;
                }
                if (e.getSource().equals(buttons[1])) {// green
                    value = 1;
                }
                if (e.getSource().equals(buttons[2])) {// blue
                    value = 2;
                }

            }

        }

    }
}
