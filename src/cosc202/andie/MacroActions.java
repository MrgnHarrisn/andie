package cosc202.andie;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;

import cosc202.andie.PaintingFrame.PaintingWindow.ButtonListener;
import cosc202.andie.toolbar_icons.Icons;

/**
 * <p>
 * Used to setup the Macro JMenu, and save/load macros
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
public class MacroActions {
    private Icons iconInstance = new Icons();
    private ImageIcon[] iconsSmall = iconInstance.getIconsSmall();

    ArrayList<Macro> currentMacros = new ArrayList<>();
    MacroEditor editor = new MacroEditor();
    JMenu menu = new JMenu("Macros");
    public final String macroFolder = System.getProperty("user.dir") + "\\src\\cosc202\\andie\\Macros\\";
    public JMenuItem recordButton = new JMenuItem("Record Macro", iconsSmall[22]);
    public JMenuItem stopButton = new JMenuItem("Stop Recording", iconsSmall[23]);

    // Singleton
    public static MacroActions current;

    /** Constructor used to set up the singleton and load the macros */
    public MacroActions() {
        File folder = new File(macroFolder);
        LoadAllMacros(folder);

        // Setting up the singleton
        if (current != this) {
            current = null;
        }
        current = this;
    }

    /**
     * Creates the JMenu for the macros
     * 
     * @return The created JMenu
     */
    public JMenu CreateMenu() {
        menu.add(editor.new MacroEditorWindow());
        MacroRecorder macLis = new MacroRecorder();
        recordButton.addActionListener(macLis);
        stopButton.addActionListener(macLis);
        menu.add(recordButton);
        menu.add(stopButton);
        return menu;
    }

    /**
     * This adds a new Macro to the Macro JMenu and currentMacros ArrayList
     * 
     * @param toAdd The Macro to add
     */
    public void AddToMenu(Macro toAdd) {
        if (!currentMacros.contains(toAdd)) {
            JMenuItem item = menu.add(toAdd);
            toAdd.SetJMenu(item);
            currentMacros.add(toAdd);
            MacroEditor.current.macroList.addElement(toAdd);
            MacroEditor.current.RefreshMacroList();
        } else {
            System.out.println("This already exists!");
        }
    }

    /**
     * This removes a JMenuItem from the JMenu
     * 
     * @param toRemove The Macro to remove
     */
    public void RemoveFromMenu(Macro toRemove) {
        if (currentMacros.contains(toRemove)) {
            menu.remove(toRemove.GetJMenu());
            currentMacros.remove(toRemove);
            menu.revalidate();
            menu.repaint();
        } else {

        }
    }

    /**
     * This loads all the macros from the Macro folder
     * 
     * @param folder The folder to access the Macros from
     */
    public void LoadAllMacros(File folder) {
        if (folder.listFiles().length <= 0)
            return;

        try {
            for (File file : folder.listFiles()) {
                String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
                boolean isMacro = extension.equals(".mcr");
                if (isMacro) {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Macro loaded = (Macro) ois.readObject();
                    AddToMenu(loaded);
                    fis.close();
                    ois.close();
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            DialogBox.dialogBox("Error Loading!");
        }
    }

    /**
     * This will be used to delete files from the Macro folder
     * 
     * @param fileName The name of the file to delete
     */
    public void DeleteFile(String fileName) {
        try {
            File toDelete = new File(fileName);
            toDelete.delete();
        } catch (Exception e) {
            DialogBox.dialogBox("Something Went Wrong!");
        }
    }

    /** This class is for the recording side, used for the button presses */
    public class MacroRecorder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == recordButton) {
                MacroEditor.current.BeginRecording();
            }

            if (e.getSource() == stopButton) {
                MacroEditor.current.FinishRecording();
            }

        }

    }
}
