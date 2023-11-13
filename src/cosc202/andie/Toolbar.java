package cosc202.andie;

import javax.swing.*;
import java.util.*;

import cosc202.andie.toolbar_icons.Icons;

import java.awt.*;

/**
 * <p>
 * Creates a Tool bar with several buttons connected to their corresponding
 * Action
 * </p>
 * <p>
 * Attributions for icons at
 * "./src/cosc202/andie/toolbar_icons/Attributions.html"
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class Toolbar extends Component {

    private JToolBar toolbar;
    private JButton[] buttons;

    public Toolbar() {
        createToolbar();
    }

    private void createToolbar() {
        toolbar = new JToolBar();
        Icons iconInstance = new Icons();
        ImageIcon icons[] = iconInstance.getIcons();
        int toolbarLength = iconInstance.getLastToolbarIndex() + 1;
        buttons = new JButton[toolbarLength];
        ArrayList<Action> actions = new ArrayList<Action>();

        int heightButtons = iconInstance.getHeight();
        int widthButtons = iconInstance.getWidth();

        // Grab the actions from ColourActions
        ColourActions colourAction = new ColourActions();
        Action greyscale = colourAction.actions.get(colourAction.actions.indexOf(colourAction.greyscale));
        Action brightness = colourAction.actions.get(colourAction.actions.indexOf(colourAction.brightness));
        Action contrast = colourAction.actions.get(colourAction.actions.indexOf(colourAction.contrast));

        // Grab the actions from ViewActions
        ViewActions viewAction = new ViewActions();
        Action zoomIn = viewAction.actions.get(viewAction.actions.indexOf(viewAction.zoomIn));
        Action zoomOut = viewAction.actions.get(viewAction.actions.indexOf(viewAction.zoomOut));

        // Grab the actions from FileActions
        FileActions fileAction = new FileActions();
        Action open = fileAction.actions.get(fileAction.actions.indexOf(fileAction.open));
        Action save = fileAction.actions.get(fileAction.actions.indexOf(fileAction.save));
        Action saveAs = fileAction.actions.get(fileAction.actions.indexOf(fileAction.saveAs));
        Action exportImage = fileAction.actions.get(fileAction.actions.indexOf(fileAction.exportImage));

        // Grab the actions from EditActions
        EditActions editActions = new EditActions();
        Action undo = editActions.actions.get(editActions.actions.indexOf(editActions.undo));
        Action redo = editActions.actions.get(editActions.actions.indexOf(editActions.redo));
        Action rotateClock = editActions.actions.get(editActions.actions.indexOf(editActions.rotateClock));
        Action rotateAnti = editActions.actions.get(editActions.actions.indexOf(editActions.rotateAnti));
        Action flipVertical = editActions.actions.get(editActions.actions.indexOf(editActions.flipVertical));
        Action flipHorizontal = editActions.actions.get(editActions.actions.indexOf(editActions.flipHorizontal));
        Action crop = editActions.actions.get(editActions.actions.indexOf(editActions.crop));

        // add the actions in the correct order that matches that in the icons array
        actions.add(open);
        actions.add(save);
        actions.add(saveAs);
        actions.add(exportImage);
        actions.add(contrast);
        actions.add(brightness);
        actions.add(greyscale);
        actions.add(crop);
        actions.add(rotateAnti);
        actions.add(rotateClock);
        actions.add(flipHorizontal);
        actions.add(flipVertical);
        actions.add(zoomOut);
        actions.add(zoomIn);
        actions.add(undo);
        actions.add(redo);

        if (actions.size() != buttons.length) {
            System.out.println("Error: actions.size() != buttons.length");
        }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(icons[i]);
            buttons[i].setPreferredSize(new Dimension(widthButtons, heightButtons));
            buttons[i].setBorder(BorderFactory.createEmptyBorder());// make look pretty
            buttons[i].setOpaque(false);// make look pretty

            buttons[i].addActionListener(actions.get(i));

            toolbar.addSeparator(new Dimension(widthButtons / 6, heightButtons)); // make look pretty
            toolbar.add(buttons[i], BorderLayout.WEST); // add the buttons to the toolbar
        }

        toolbar.setFloatable(false); // make look pretty
        toolbar.setBorder(BorderFactory.createEmptyBorder());// make look pretty
        toolbar.setPreferredSize(new Dimension(widthButtons * icons.length * 2,
                heightButtons + 5));// make look pretty, Set the size

    }

    /**
     * <p>
     * Accessor method for the toolbar
     * </p>
     * 
     * @return the toolbar.
     */
    public JToolBar get() {
        return toolbar;
    }

}
