package cosc202.andie;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.chrono.IsoEra;
import java.util.ArrayList;
import javax.swing.JMenuItem;

/**
 * <p>
 * An ArrayList of ImageOperations that can be saved and run
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
public class Macro extends ImageAction {

    public ArrayList<ImageOperation> operations = new ArrayList<ImageOperation>(); // Storing the operations
    public String macroFileName = null;
    private JMenuItem jMenu;
    boolean isSaved = false;

    // public int menuIndex;
    /**
     * The constructor, this will be used to set the event trigger
     * 
     * @param macroName The name of the Macro
     * @param trigger
     */
    public Macro(String macroName) {
        super(macroName, null, "A Macro", null);
    }

    /**
     * Add an Image Operation to the ArrayList
     * 
     * @param operation The operation to add
     */
    public void AddEvent(ImageOperation operation) {
        operations.add(operation);
    }

    /**
     * Removes an event from the operation using an index
     * 
     * @param index The index to remove
     */
    public void RemoveEvent(int index) {
        try {
            operations.remove(index);
        } catch (Exception e) {
            DialogBox.dialogBox("Event Not Found at Given Index");
        }
    }

    /**
     * Removes an event from the operation using an ImageOperation
     * 
     * @param toRemove The image operation to remove
     */
    public void RemoveEvent(ImageOperation toRemove) {
        if (operations.contains(toRemove)) {
            operations.remove(toRemove);
        } else {
            DialogBox.dialogBox("Event Not Found!");
        }
    }

    public void SetEvent(ImageOperation toSet, int index) {
        try {
            operations.set(index, toSet);
        } catch (Exception e) {
            DialogBox.dialogBox("Failed To Set Event!");
        }
    }

    /**
     * Return the ArrayList of operations
     * 
     * @return The ArrayList of operations
     */
    public ArrayList<ImageOperation> GetOperations() {
        return operations;
    }

    public void SetName(String name) {
        this.putValue(NAME, name);
    }

    /**
     * This will be used to trigger the macro
     * 
     * @param e The trigger event
     */
    public void actionPerformed(ActionEvent e) {
        try {
            for (ImageOperation op : operations) {
                target.getImage().apply(op);
            }
        } catch (Exception exc) {
            System.out.println(exc);
            DialogBox.dialogBox("There is no image to apply!");
            return;
        }

        target.repaint();
        target.getParent().revalidate();
    }

    /**
     * Saves a macro to a file
     * 
     * @param fileName The name of the save file name
     */
    public void SaveMacro(String fileName) {
        try {
            SetName(fileName);
            isSaved = true;
            macroFileName = fileName;
            String fileLoc = MacroActions.current.macroFolder + fileName + ".mcr";
            File toSave = new File(fileLoc);

            if (!toSave.exists()) {
                toSave.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(toSave);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception e) {
            DialogBox.dialogBox("There was an error saving the Macro!");
        }
    }

    /** This will turn the macro into a string */
    public String toString() {
        return macroFileName;
    }

    /**
     * This will be used to set the JMenuItem used (this will be used when removing
     * the macro from the JMenu)
     * 
     * @param item The JMenuItem assiged to the macro
     */
    public void SetJMenu(JMenuItem item) {
        jMenu = item;
    }

    /**
     * This will return the JMenuItem
     * 
     * @return The JMenuItem connected to the macro
     */
    public JMenuItem GetJMenu() {
        return jMenu;
    }

    /**
     * This will return whether the macro has operations or not
     * 
     * @return true if it's empty or false if it is populated
     */
    public boolean isEmpty() {
        return (operations.size() <= 0);
    }
}