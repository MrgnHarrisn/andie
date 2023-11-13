package cosc202.andie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.awt.event.*;
import java.security.KeyException;

import javax.swing.*;

import cosc202.andie.MacroEditor.MacroEditorInteraction;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    // Originally put here for previous shortcut method but didn't end up using
    MeanFilterAction meanFilter = new MeanFilterAction("Mean filter", null, "Apply a mean filter",
            Integer.valueOf(KeyEvent.VK_B));
    MedianFilterAction medianFilter = new MedianFilterAction("Median filter", null, "Apply a median filter",
            Integer.valueOf(KeyEvent.VK_M));
    SharpenFilterAction sharpenFilter = new SharpenFilterAction("Sharpen filter", null, "Apply a sharpen to the image",
            Integer.valueOf(KeyEvent.VK_S));
    GaussianFilterAction gaussianFilter = new GaussianFilterAction("Gaussian filter", null, "Apply a gaussian filter",
            KeyEvent.VK_G);
    EdgeFilterAction edgeFilter = new EdgeFilterAction("Edge filter", null, "Apply an edge filter",
            (Integer) KeyEvent.VK_E);
    EmbossFiltersAction embossFilter = new EmbossFiltersAction("Emboss filter", null, "Apply an emboss filter",
            (Integer) KeyEvent.VK_R);

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(meanFilter);
        actions.add(medianFilter);
        actions.add(sharpenFilter);
        actions.add(gaussianFilter);
        actions.add(edgeFilter);
        actions.add(embossFilter);
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class MedianFilterAction extends ImageAction {

        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                triggerAction();
            } catch (Exception exc) {
                return;
            }

        }

        public void triggerAction() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(1);
            slider.setMinimum(1);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, slider, "Enter Median Size", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = slider.getValue();
            }

            // Create and apply the filter
            MedianFilter mF = new MedianFilter(radius);
            if (MacroEditor.current.isRecording)
                MacroEditor.current.AddRecordedMacro(mF);
            target.getImage().apply(mF);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            triggerAction();
        }

        public void triggerAction() {

            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(1);
            slider.setMinimum(1);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, slider, "Enter Mean Size", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = slider.getValue();
            }

            // Create and apply the filter
            MeanFilter mF = new MeanFilter(radius);
            if (MacroEditor.current.isRecording)
                MacroEditor.current.AddRecordedMacro(mF);
            target.getImage().apply(mF);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class SharpenFilterAction extends ImageAction {

        SharpenFilterAction(String name, ImageIcon icon, String description, Integer shortcut) {
            super(name, icon, description, shortcut);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(shortcut, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        public void actionPerformed(ActionEvent e) {
            triggerAction();
        }

        public void triggerAction() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(1);
            slider.setMinimum(1);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, slider, "Enter Sharpen Amount",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = slider.getValue();

            }

            try {
                SharpenFilter sF = new SharpenFilter(radius);
                if (MacroEditor.current.isRecording)
                    MacroEditor.current.AddRecordedMacro(sF);
                target.getImage().apply(sF);
            } catch (Exception e) {
                return;
            }

            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class GaussianFilterAction extends ImageAction {

        GaussianFilterAction(String name, ImageIcon icon, String description, Integer shortcut) {
            super(name, icon, description, shortcut);
            // Creating Shortcut using an InputMap and ActionMap to connect a keystroke to
            // an action event
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(shortcut, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);

            target.getParent().revalidate();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                triggerAction();
            } catch (Exception exc) {
                return;
            }
        }

        public void triggerAction() {
            int radius = 0;

            JSlider slider = new JSlider();
            slider.setValue(1);
            slider.setMinimum(1);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            int option = JOptionPane.showOptionDialog(null, slider, "Enter Gaussian Size", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = slider.getValue();

            }

            GaussianFilter gf = new GaussianFilter(radius);
            if (MacroEditor.current.isRecording)
                MacroEditor.current.AddRecordedMacro(gf);
            target.getImage().apply(gf);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class EdgeFilterAction extends ImageAction {

        EdgeFilterAction(String name, ImageIcon icon, String description, Integer shortcut) {
            super(name, icon, description, shortcut);
            // Creating Shortcut using an InputMap and ActionMap to connect a keystroke to
            // an action event
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(shortcut, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);

            target.getParent().revalidate();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                triggerAction();
            } catch (Exception exc) {
                return;
            }
        }

        public void triggerAction() {
            String selectedString;

            Object selected = JOptionPane.showInputDialog(null, "Select direction of filter", "Selection",
                    JOptionPane.DEFAULT_OPTION, null, new String[] { "Horizontal", "Vertical" }, "0");
            if (selected == null) {// null if the user cancels.
                return;
            }
            selectedString = selected.toString();

            // Check the return value from the dialog box.
            EdgeFilters eF = new EdgeFilters(selectedString);
            if (MacroEditor.current.isRecording)
                MacroEditor.current.AddRecordedMacro(eF);
            target.getImage().apply(eF);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class EmbossFiltersAction extends ImageAction {

        EmbossFiltersAction(String name, ImageIcon icon, String description, Integer shortcut) {
            super(name, icon, description, shortcut);
            // Creating Shortcut using an InputMap and ActionMap to connect a keystroke to
            // an action event
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(shortcut, KeyEvent.SHIFT_DOWN_MASK), name);
            actionMap.put(name, this);

            target.getParent().revalidate();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                triggerAction();
            } catch (Exception exc) {
                return;
            }
        }

        public void triggerAction() {
            String[] directionsArr = new String[] { "Top-Left", "Up", "Top-Right", "Left", "Right", "Bottom-Left",
                    "Down", "Bottom-Right" };
            String selectedString;
            int direction = 0;

            Object selected = JOptionPane.showInputDialog(null, "Select Direction of Filter", "Selection",
                    JOptionPane.DEFAULT_OPTION, null, directionsArr, "0");
            if (selected == null)
                return;
            selectedString = selected.toString();
            for (int i = 0; i < directionsArr.length; i++) {
                if (selectedString == directionsArr[i])
                    direction = i;
            }
            if (direction >= 4)
                direction++;
            EmbossFilters eF = new EmbossFilters(direction);
            if (MacroEditor.current.isRecording)
                MacroEditor.current.AddRecordedMacro(eF);
            target.getImage().apply(eF);
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
