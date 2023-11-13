package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.FileNameMap;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import cosc202.andie.toolbar_icons.Icons;

/**
 * <p>
 * This is the main macro editor class, used for creating, saving, and modifying
 * macros
 * </p>
 * 
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Tyler Winmill
 * @version 1.0
 */
public class MacroEditor implements java.io.Serializable {

    // The editor window
    public JFrame editorFrame = new JFrame("Macro Editor");

    // The current selected Macro
    public Macro currentMacro = null;

    // These are for holding the current ImageOperations that are held in the macro
    public JList<ImageOperation> previewList = new JList<ImageOperation>();
    public DefaultListModel<ImageOperation> actionList = new DefaultListModel<ImageOperation>();
    public JPanel actionListPanel = new JPanel(new GridLayout(0, 1));

    // These will hold the current Macro files that exist in the macro folder
    public JList<Macro> macroPreviewList = new JList<Macro>(); // The saved macro JList (what is presented)
    public DefaultListModel<Macro> macroList = new DefaultListModel<Macro>(); // The saved macro list model (what is
                                                                              // stored)
    public JPanel macroListPanel = new JPanel(new GridLayout(0, 1)); // The panel to put the list onto

    // This will hold the information regarding if the macro is recording
    public boolean isRecording = false;

    // Another cheeky singleton so I can access the macroPreviewList
    public static MacroEditor current;

    // The interaction buttons - the different options that can be used
    public JButton[] interactionButtons = {
            new JButton("Create New Macro"),
            new JButton("Save Macro"),
            new JButton("Delete Macro"),
            new JButton("Delete Action"),
            new JButton("Exit Macro Editor")
    };

    // The action buttons - the different actions that can be used
    public JButton[] actionButtons = {
            new JButton("Mean Filter"),
            new JButton("Median Filter"),
            new JButton("Sharpen Filter"),
            new JButton("Gaussian Blur"),
            new JButton("Edge Filter"),
            new JButton("Emboss Filter"),
            new JButton("Greyscale"),
            new JButton("Brightness"),
            new JButton("Contrast"),
            new JButton("Posterize Filter"),
            new JButton("Rotate Clockwise"),
            new JButton("Rotate Anti-Clockwise"),
            new JButton("Vertical Flip"),
            new JButton("Horizontal Flip")
    };

    /** A constructor used to setup the singleton */
    public MacroEditor() {
        if (current != this) {
            current = null;
        }

        current = this;
    }

    /** This will refresh the macro list */
    public void RefreshMacroList() {
        macroPreviewList.setModel(macroList);
        macroPreviewList.revalidate();
    }

    /** This will refresh the action list */
    public void RefreshActionList() {
        previewList.revalidate();
    }

    public void RemoveMacro(Macro toRemove) {
        macroList.removeElement(toRemove);
        MacroActions.current.RemoveFromMenu(toRemove);
        RefreshMacroList();
    }

    public void BeginRecording() {
        if (isRecording)
            return;
        isRecording = true;
        CreateNewMacro();
    }

    public void FinishRecording() {
        if (!isRecording)
            return;
        isRecording = false;

        if (!currentMacro.isEmpty()) {
            SaveMacro();
        }
    }

    public void AddRecordedMacro(ImageOperation op) {
        actionList.addElement(op);
        currentMacro.AddEvent(op);
        previewList.setModel(actionList);
        previewList.revalidate();
    }

    /**
     * This will be used to get a file name from a user
     * 
     * @return The name that is entered or null if cancelled
     */
    public String GetSaveName() {
        JTextField macroName = new JTextField("New Macro");
        int option = JOptionPane.showOptionDialog(null, macroName, "Enter Macro Name!", JOptionPane.OK_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION)
            return CheckNameAvaliability(macroName.getText());
        else
            return null;
    }

    /**
     * This will be used to check the entered file name, looking for instances, and
     * adjusting (i.e. Grey Sharpen -> Grey Sharpen 1)
     * 
     * @param checkName The name to check
     * @return The new name (incremented by 1 if found)
     */
    public String CheckNameAvaliability(String checkName) {
        int instanceCount = 0;
        String toReturn = checkName;
        String fileName = MacroActions.current.macroFolder + checkName + ".mcr";
        File currentFile = new File(fileName);
        while (currentFile.exists()) {
            System.out.println("I Found The File: " + fileName);
            instanceCount++;
            fileName = MacroActions.current.macroFolder + checkName + " (" + (instanceCount) + ").mcr";
            currentFile = new File(fileName);
        }
        if (instanceCount > 0)
            toReturn = checkName + " (" + instanceCount + ")";
        return toReturn;
    }

    /** This is called when creating a new macro and will clear the action list */
    public void CreateNewMacro() {
        previewList.removeAll();
        actionList.clear();
        previewList.revalidate();
        currentMacro = new Macro("New Macro");
    }

    /** This is used when saving a new macro */
    public void SaveMacro() {
        if (MacroEditor.current.isRecording)
            MacroEditor.current.isRecording = false;
        if (currentMacro.isSaved == false) {
            String saveName = GetSaveName();
            if (saveName == null)
                return;

            if (saveName.isBlank() || saveName.isEmpty() || saveName.contains(".")) {
                DialogBox.dialogBox("Invalid Save Name");
            } else {
                currentMacro.SaveMacro(saveName);
                MacroActions.current.AddToMenu(currentMacro);
            }
        } else {
            currentMacro.SaveMacro(currentMacro.toString());
        }
    }

    /**
     * This is used when changing the value of an action in a macro. It's a bit
     * gross but nothing else was working
     * 
     * @param action The action clicked on the action list
     * @return The new image operation to replace the previous with
     */
    public ImageOperation SetOperationSize(Object action) {
        if (action instanceof MeanFilter) {
            action = new MeanFilter(GetSize(1, 10, 1, "Enter Mean Filter Size"));
            return (MeanFilter) action;
        } else if (action instanceof MedianFilter) {
            action = new MedianFilter(GetSize(1, 10, 1, "Enter Median Size"));
            return (MedianFilter) action;
        } else if (action instanceof SharpenFilter) {
            action = new SharpenFilter(GetSize(1, 10, 1, "Enter Sharpen Amount"));
            return (SharpenFilter) action;
        } else if (action instanceof GaussianFilter) {
            action = new GaussianFilter(GetSize(1, 10, 1, "Enter Gaussian Size"));
            return (GaussianFilter) action;
        } else if (action instanceof EdgeFilters) {
            action = new EdgeFilters(
                    (String) GetFilterType("Select Edge Direction", new String[] { "Horizontal", "Vertical" }));
            return (EdgeFilters) action;
        } else if (action instanceof BrightnessFilter) {
            action = new BrightnessFilter(GetSize(-10, 10, 2, "Enter Brightness Amount"));
            return (BrightnessFilter) action;
        } else if (action instanceof ContrastFilter) {
            action = new ContrastFilter(GetSize(-10, 10, 2, "Enter Contrast Amount"));
            return (ContrastFilter) action;
        } else if (action instanceof Rotations.rotate90degreesClockwise) {
            Rotations rot = new Rotations();
            action = rot.new rotate90degreesClockwise();
            return (Rotations.rotate90degreesClockwise) action;
        } else if (action instanceof Rotations.rotate90degreesAntiClockwise) {
            Rotations rot = new Rotations();
            action = rot.new rotate90degreesAntiClockwise();
            return (Rotations.rotate90degreesAntiClockwise) action;
        } else if (action instanceof ConvertToGrey) {
            action = new ConvertToGrey();
            return (ConvertToGrey) action;
        } else if (action instanceof ImageFlip.horizontalFlip) {
            ImageFlip flip = new ImageFlip();
            action = flip.new horizontalFlip();
            return (ImageFlip.horizontalFlip) action;
        } else if (action instanceof ImageFlip.verticalFlip) {
            ImageFlip flip = new ImageFlip();
            action = flip.new verticalFlip();
            return (ImageFlip.verticalFlip) action;
        } else if (action instanceof PosteriseQuickFilter) {
            action = new PosteriseQuickFilter(GetSize(2, 10, 2, "Enter Colour Channel Amount"));
            return (PosteriseQuickFilter) action;
        } else if (action instanceof EmbossFilters) {
            String[] allDirections = new String[] { "Top-Left", "Up", "Top-Right", "Left", "Right", "Bottom-Left",
                    "Down", "Bottom-Right" };
            Object gottenDirection = GetFilterType("Select Emboss Direction", allDirections);
            int direction = Arrays.asList(allDirections).indexOf(gottenDirection);
            if (direction >= 4)
                direction++;
            action = new EmbossFilters(direction);
            return (EmbossFilters) action;
        } else
            return (ImageOperation) action;
    }

    /**
     * For getting the size of a filter
     * 
     * @param min     The minimum size
     * @param max     The maximum size
     * @param tick    The increment counter of the slider
     * @param message What will be displayed on the popup
     * @return The size entered
     */
    public int GetSize(int min, int max, int tick, String message) {
        JSlider slider = new JSlider();
        slider.setValue(1);
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setMajorTickSpacing(tick);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        int option = JOptionPane.showOptionDialog(null, slider, message, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        return slider.getValue();
    }

    /**
     * This is used to get string options from a JOption. This is for the edge, and
     * emboss filters specifically
     * 
     * @param message The message that is displayed
     * @param options The options avaliable
     * @return
     */
    public Object GetFilterType(String message, String[] options) {
        Object toReturn = JOptionPane.showInputDialog(null, message, "Selection",
                JOptionPane.DEFAULT_OPTION, null, options, "0");
        System.out.println(toReturn);
        return toReturn;
    }

    /** The base window */
    public class MacroEditorWindow extends AbstractAction {

        // The action buttons - the different operations that can be applied
        public JPanel actionPanel;
        private static Icons iconInstance = new Icons();
        private static ImageIcon[] iconsSmall = iconInstance.getIconsSmall();

        public MacroEditorWindow() {
            super("Create Macro...", iconsSmall[20]);
            CreateMenu();
        }

        /** This is called when opening the window */
        public void OpenMenu() {
            editorFrame.setLocationRelativeTo(null);
            editorFrame.pack();
            editorFrame.setVisible(true);
        }

        /** This is called when creating the window */
        public void CreateMenu() {
            // Setting icon
            try {

                editorFrame.setIconImage(iconsSmall[20].getImage());

                // Setting up the interaction buttons
                JPanel interactionPanel = new JPanel();
                MacroEditorInteraction intLis = new MacroEditorInteraction();
                for (JButton button : interactionButtons) {
                    button.addActionListener(intLis);
                    interactionPanel.add(button);
                }
                editorFrame.add(interactionPanel, BorderLayout.NORTH);

                // Setting up the action buttons
                JLabel actionTitle = new JLabel("Addable Actions", SwingConstants.CENTER);
                JPanel actionPanel = new JPanel(new GridLayout(0, 1));
                MacroEditorActions actLis = new MacroEditorActions();
                actionPanel.add(actionTitle);
                for (JButton button : actionButtons) {
                    actionPanel.add(button);
                    button.addActionListener(actLis);
                }
                editorFrame.add(actionPanel, BorderLayout.EAST);

                // Setting up the action list preview
                actionListPanel = new JPanel(new GridLayout(0, 1));
                actionListPanel.add(previewList);
                editorFrame.add(actionListPanel, BorderLayout.CENTER);

                // If the user double clicks an item in the JList then reprompt input
                previewList.addMouseListener(new MouseInputAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        if (evt.getClickCount() == 2) {
                            int index = previewList.locationToIndex(evt.getPoint());
                            Object type = previewList.getModel().getElementAt(index);
                            ImageOperation imageOperation = SetOperationSize(type);
                            actionList.setElementAt(imageOperation, index);
                            currentMacro.SetEvent(imageOperation, index);
                            RefreshActionList();
                        }
                    }
                });

                // Setting up the saved macro list
                macroListPanel = new JPanel(new GridLayout(0, 1));
                macroListPanel.add(macroPreviewList);
                macroListPanel.setPreferredSize(new Dimension(100, 300));
                macroPreviewList.setBackground(new Color(.9f, .9f, .9f));

                // If the user double clicks an item in the JList then open the selected macro
                macroPreviewList.addMouseListener(new MouseInputAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        if (evt.getClickCount() == 2) {
                            int index = macroPreviewList.locationToIndex(evt.getPoint());
                            OpenMacro(macroPreviewList.getModel().getElementAt(index));
                        }
                    }
                });

                editorFrame.add(macroListPanel, BorderLayout.WEST);

            } catch (Exception e) {
                DialogBox.dialogBox("Error Creating Macro Menu!");
            }
        }

        /**
         * This is called when the Create Macro button is pressed under the Macro menu
         */
        public void actionPerformed(ActionEvent e) {
            OpenMenu();
        }

        /**
         * This is used when opening a macro
         * 
         * @param toLoad The macro to open
         */
        public void OpenMacro(Macro toLoad) {
            currentMacro = toLoad;
            actionList.clear();
            previewList.removeAll();
            actionList.addAll(toLoad.GetOperations());
            previewList.setModel(actionList);
        }
    }

    /**
     * This is used to control the macro editor actions (things such as saving,
     * deleting, etc...)
     */
    public class MacroEditorInteraction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // If create new macro has been pressed
            if (e.getSource() == interactionButtons[0]) {
                if (currentMacro != null && !currentMacro.isEmpty() && !currentMacro.isSaved) {
                    int option = JOptionPane.showOptionDialog(null, "Would You Like To Save This Macro?",
                            "Warning Unsaved Macro!", JOptionPane.OK_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);

                    if (option == JOptionPane.OK_OPTION) {
                        SaveMacro();
                        CreateNewMacro();
                        return;
                    } else if (option == JOptionPane.NO_OPTION) {
                        CreateNewMacro();
                        return;
                    }
                } else {
                    CreateNewMacro();
                }
            }

            // If saving a macro
            if (e.getSource() == interactionButtons[1]) {
                if (currentMacro == null || currentMacro.isEmpty()) {
                    DialogBox.dialogBox("Please Create a Macro or Add an Event Before Saving!");
                    return;
                }
                SaveMacro();
            }

            // If deleting a macro
            if (e.getSource() == interactionButtons[2]) {
                try {
                    Macro toRemove = macroPreviewList.getSelectedValue();
                    RemoveMacro(toRemove);
                    MacroActions.current.DeleteFile(MacroActions.current.macroFolder + toRemove.toString() + ".mcr");
                } catch (Exception exc) {

                }
            }

            // If deleting an ImageOperation
            if (e.getSource() == interactionButtons[3]) {
                try {
                    ImageOperation toRemove = previewList.getSelectedValue();
                    currentMacro.RemoveEvent(toRemove);
                    actionList.removeElement(toRemove);
                    actionListPanel.revalidate();
                } catch (Exception exc) {

                }
            }

            // If the quit button has been pressed
            if (e.getSource() == interactionButtons[4]) {
                editorFrame.dispose();
                return;
            }
        }
    }

    /** This class is used for the addable actions */
    public class MacroEditorActions implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Creating new Macro!

            if (currentMacro == null) {
                DialogBox.dialogBox("No Macro Selected!");
                return;
            }

            if (e.getSource() == actionButtons[0]) {
                MeanFilter mF = new MeanFilter(GetSize(1, 10, 1, "Enter Mean Size"));
                actionList.addElement(mF);
                currentMacro.AddEvent(mF);
            }

            if (e.getSource() == actionButtons[1]) {
                MedianFilter mF = new MedianFilter(GetSize(1, 10, 1, "Enter Median Size"));
                actionList.addElement(mF);
                currentMacro.AddEvent(mF);
            }

            if (e.getSource() == actionButtons[2]) {
                SharpenFilter sF = new SharpenFilter(GetSize(1, 10, 1, "Enter Sharpen Amount"));
                actionList.addElement(sF);
                currentMacro.AddEvent(sF);
            }

            if (e.getSource() == actionButtons[3]) {
                GaussianFilter gF = new GaussianFilter(GetSize(1, 10, 1, "Enter Gaussian Size"));
                actionList.addElement(gF);
                currentMacro.AddEvent(gF);
            }

            if (e.getSource() == actionButtons[4]) {
                EdgeFilters eF = new EdgeFilters(
                        (String) GetFilterType("Select Edge Direction", new String[] { "Horizontal", "Vertical" }));
                actionList.addElement(eF);
                currentMacro.AddEvent(eF);
            }

            if (e.getSource() == actionButtons[5]) {
                String[] allDirections = new String[] { "Top-Left", "Up", "Top-Right", "Left", "Right", "Bottom-Left",
                        "Down", "Bottom-Right" };
                Object gottenDirection = GetFilterType("Select Emboss Direction", allDirections);
                System.out.println(allDirections);
                int direction = Arrays.asList(allDirections).indexOf(gottenDirection);
                if (direction >= 4)
                    direction++;
                EmbossFilters eF = new EmbossFilters(direction);
                actionList.addElement(eF);
                currentMacro.AddEvent(eF);
            }

            if (e.getSource() == actionButtons[6]) {
                ConvertToGrey cTG = new ConvertToGrey();
                actionList.addElement(cTG);
                currentMacro.AddEvent(cTG);
            }

            if (e.getSource() == actionButtons[7]) {
                BrightnessFilter bF = new BrightnessFilter(GetSize(1, 10, 1, "Enter Brightness Amount"));
                actionList.addElement(bF);
                currentMacro.AddEvent(bF);
            }

            if (e.getSource() == actionButtons[8]) {
                ContrastFilter cF = new ContrastFilter(GetSize(1, 10, 1, "Enter Contrast Amount"));
                actionList.addElement(cF);
                currentMacro.AddEvent(cF);
            }

            if (e.getSource() == actionButtons[9]) {
                PosteriseQuickFilter pF = new PosteriseQuickFilter(GetSize(2, 10, 2, "Enter Colour Channel Amount"));
                actionList.addElement(pF);
                currentMacro.AddEvent(pF);
            }

            if (e.getSource() == actionButtons[10] || e.getSource() == actionButtons[11]) {
                Rotations rotations = new Rotations();

                if (e.getSource() == actionButtons[10]) {
                    actionList.addElement(rotations.new rotate90degreesClockwise());
                    currentMacro.AddEvent(rotations.new rotate90degreesClockwise());
                } else {
                    actionList.addElement(rotations.new rotate90degreesAntiClockwise());
                    currentMacro.AddEvent(rotations.new rotate90degreesAntiClockwise());
                }
            }

            if (e.getSource() == actionButtons[12] || e.getSource() == actionButtons[13]) {
                ImageFlip flipper = new ImageFlip();

                if (e.getSource() == actionButtons[12]) {
                    actionList.addElement(flipper.new verticalFlip());
                    currentMacro.AddEvent(flipper.new verticalFlip());
                } else {
                    actionList.addElement(flipper.new horizontalFlip());
                    currentMacro.AddEvent(flipper.new horizontalFlip());
                }
            }

            SetPreviewList();
        }

        /** This is used for refreshing the action JList */
        public void SetPreviewList() {
            previewList.setModel(actionList);
            actionListPanel.revalidate();
        }
    }
}
