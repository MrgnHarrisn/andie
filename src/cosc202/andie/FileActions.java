package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import cosc202.andie.toolbar_icons.Icons;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    // get the array of icons
    Icons iconInstance = new Icons();
    ImageIcon[] iconsSmall = iconInstance.getIconsSmall();

    // Creating variables to make shortcuts easier
    public FileOpenAction open = new FileOpenAction("Open", iconsSmall[0], "Open a file",
            Integer.valueOf(KeyEvent.VK_O));
    public FileSaveAction save = new FileSaveAction("Save", iconsSmall[1], "Save the file",
            Integer.valueOf(KeyEvent.VK_S));
    public FileSaveAsAction saveAs = new FileSaveAsAction("Save As", iconsSmall[2], "Save a copy",
            Integer.valueOf(KeyEvent.VK_A));
    public ExportImageAction exportImage = new ExportImageAction("Export Image", iconsSmall[3], "export the image",
            Integer.valueOf(KeyEvent.VK_E));
    public FileExitAction exit = new FileExitAction("Exit", iconsSmall[18], "Exit the program", Integer.valueOf(0));
    public OpenFileFromURLAction openFromURL = new OpenFileFromURLAction("Open from URL", iconsSmall[0],
            "Opens an image from a URL",
            Integer.valueOf(KeyEvent.VK_U));

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(open);
        actions.add(openFromURL);
        actions.add(save);
        actions.add(saveAs);
        actions.add(exportImage);
        actions.add(exit);
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * A class that exports the current image
     */

    public class ExportImageAction extends ImageAction {

        ExportImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();

            // An array to hold the filters
            ArrayList<FileNameExtensionFilter> exten = new ArrayList<>();
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Create filters

            exten.add(new FileNameExtensionFilter("png", "png"));
            exten.add(new FileNameExtensionFilter("jpeg", "jpeg"));
            exten.add(new FileNameExtensionFilter("jpg", "jpg"));

            // Add file extensions
            for (FileNameExtensionFilter fnef : exten) {
                fileChooser.addChoosableFileFilter(fnef);
            }

            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    String name = fileChooser.getSelectedFile().getName();
                    String extension = name.substring(name.lastIndexOf(".") + 1, name.length());
                    if (extension.equals(name)) {
                        extension = fileChooser.getFileFilter().getDescription();
                    }

                    // Add the extension s the user doesn't have to
                    imageFilepath += "." + extension;

                    // If the user does put .png or .jpg etc. then it is assumed to be part of the
                    // name

                    ImageIO.write(target.getImage().getCurrentImage(), extension, new File(imageFilepath));

                } catch (IOException ex) {
                    DialogBox.dialogBox("you need to use a valid file name and extension");
                }
            }

            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            openImage();
        }

        public void openImage() {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    DialogBox.dialogBox("File not found!");
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            saveImage();
        }

        public void saveImage() {
            try {
                target.getImage().save();
            } catch (Exception ex) {
                DialogBox.dialogBox("No file to save");
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }

        // Creating this function to make shortcuts easier
        public void saveFile() {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    DialogBox.dialogBox("No image to save");
                }
            }
        }

    }

    public class OpenFileFromURLAction extends ImageAction {

        OpenFileFromURLAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = target.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK), name);
            actionMap.put(name, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                openImage();
            } catch (Exception e1) {

            }
        }

        public void openImage() {
            String imgURL = (String) JOptionPane.showInputDialog(new JFrame(), "Please enter URL");
            URL url;
            // This looks horrible
            try {
                url = new URL(imgURL);
                BufferedImage bi;
                try {
                    bi = ImageIO.read(url);
                    String filePath = saveFile(bi);
                    // Saving hte file takes forever
                    try {
                        System.out.println("Loading the Image");
                        target.getImage().open(filePath);
                    } catch (Exception e) {
                        DialogBox.dialogBox("Error Opening the image");
                    }
                } catch (IOException e) {
                    DialogBox.dialogBox("Cannot open image");
                }
            } catch (MalformedURLException e) {
                DialogBox.dialogBox("MalformedURL benig used");
            }
        }

        public String saveFile(BufferedImage bi) {
            JFileChooser fileChooser = new JFileChooser();

            // An array to hold the filters
            ArrayList<FileNameExtensionFilter> exten = new ArrayList<>();
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Create filters

            exten.add(new FileNameExtensionFilter("png", "png"));
            exten.add(new FileNameExtensionFilter("jpeg", "jpeg"));
            exten.add(new FileNameExtensionFilter("jpg", "jpg"));

            // Add file extensions
            for (FileNameExtensionFilter fnef : exten) {
                fileChooser.addChoosableFileFilter(fnef);
            }

            int result = fileChooser.showSaveDialog(target);
            String imageFilepath = "";

            if (result == JFileChooser.APPROVE_OPTION) {
                try {

                    imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    String name = fileChooser.getSelectedFile().getName();
                    String extension = name.substring(name.lastIndexOf(".") + 1, name.length());
                    if (extension.equals(name)) {
                        extension = fileChooser.getFileFilter().getDescription();
                    }

                    // Add the extension s the user doesn't have to
                    imageFilepath += "." + extension;

                    // If the user does put .png or .jpg etc. then it is assumed to be part of the
                    // name

                    ImageIO.write(bi, extension, new File(imageFilepath));

                } catch (IOException ex) {
                    DialogBox.dialogBox("you neeed to use a valid file name and extension");
                }
            }
            return imageFilepath;
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

}
