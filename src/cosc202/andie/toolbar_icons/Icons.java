package cosc202.andie.toolbar_icons;

import javax.swing.ImageIcon;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Icons {

    private ImageIcon[] icons;
    private int heightIcons = 30;
    private int widthIcons = 30;
    private final int lastToolbarIndex = 15; // any icons after this will not be in the toolbar

    public Icons() {

        ArrayList<File> files = new ArrayList<File>();

        try {
            // add the files in the following order
            // if wanting to access an icon or file, use the index commented
            // when changing order (such as on the toolbar) remember to update these
            // comments. UPDATE ORDER HERE TO CHANGE APPEARANCE AND IN TOOLBAR CLASS TO
            // CHANGE ACTION
            files.add(new File("./src/cosc202/andie/toolbar_icons/open-icon.png")); // 0 open
            files.add(new File("./src/cosc202/andie/toolbar_icons/save-icon.png"));// 1 save
            files.add(new File("./src/cosc202/andie/toolbar_icons/save-as-icon.png"));// 2 saveAs
            files.add(new File("./src/cosc202/andie/toolbar_icons/export-icon.png"));// 3 export
            files.add(new File("./src/cosc202/andie/toolbar_icons/contrast-icon.png")); // 4 contrast
            files.add(new File("./src/cosc202/andie/toolbar_icons/brightness-icon.png")); // 5 brightness
            files.add(new File("./src/cosc202/andie/toolbar_icons/greyscale-icon.png"));// 6 greyscale
            files.add(new File("./src/cosc202/andie/toolbar_icons/crop-icon.png"));// 7 crop
            files.add(new File("./src/cosc202/andie/toolbar_icons/rotate-anticlockwise-icon.png"));// 8 rotate
            // anticlockwise
            files.add(new File("./src/cosc202/andie/toolbar_icons/rotate-clockwise-icon.png"));// 9 rotate clockwise
            files.add(new File("./src/cosc202/andie/toolbar_icons/flip-horizontal-icon.png"));// 10 flip horizontal
            files.add(new File("./src/cosc202/andie/toolbar_icons/flip-vertical-icon.png"));// 11 flip vertical
            files.add(new File("./src/cosc202/andie/toolbar_icons/zoom-out-icon.png"));// 12 zoom out
            files.add(new File("./src/cosc202/andie/toolbar_icons/zoom-in-icon.png"));// 13 zoom in
            files.add(new File("./src/cosc202/andie/toolbar_icons/undo-icon.png"));// 14 undo
            files.add(new File("./src/cosc202/andie/toolbar_icons/redo-icon.png"));// 15 redo
            // END OF TOOLBAR.

            files.add(new File("./src/cosc202/andie/toolbar_icons/posterise-icon.png"));// 16 posterise
            files.add(new File("./src/cosc202/andie/toolbar_icons/zoom-full-icon.png"));// 17 zoom full
            files.add(new File("./src/cosc202/andie/toolbar_icons/exit-icon.png"));// 18 exit
            files.add(new File("./src/cosc202/andie/toolbar_icons/invert-colours-icon.png"));// 19 invert
            files.add(new File("./src/cosc202/andie/toolbar_icons/macro-icon.png"));// 20 macro
            files.add(new File("src/cosc202/andie/toolbar_icons/Resize-icon.png"));// 21 resize
            files.add(new File("src/cosc202/andie/toolbar_icons/record-icon.png"));// 22 record
            files.add(new File("src/cosc202/andie/toolbar_icons/stop-icon.png"));// 23 stop
            files.add(new File("src/cosc202/andie/toolbar_icons/drawing-icon.png"));// 24 draw

            // LABELS FOR DRAWING
            files.add(new File("src/cosc202/andie/toolbar_icons/freedraw.png")); // 25 Free Draw
            files.add(new File("src/cosc202/andie/toolbar_icons/emptycircle.png")); // 26 Empty circle
            files.add(new File("src/cosc202/andie/toolbar_icons/emptyrect.png")); // 27 Empty Rect
            files.add(new File("src/cosc202/andie/toolbar_icons/filledcircle.png")); // 28 filled Rect
            files.add(new File("src/cosc202/andie/toolbar_icons/filledrect.png")); // 29 Filled Circle
            // End of DRAWING

            files.add(new File("src/cosc202/andie/toolbar_icons/rgb-icon.png"));// 30 rgb
            files.add(new File("src/cosc202/andie/toolbar_icons/red-icon.png"));// 31 red
            files.add(new File("src/cosc202/andie/toolbar_icons/green-icon.png"));// 32 green
            files.add(new File("src/cosc202/andie/toolbar_icons/blue-icon.png"));// 33 blue

            icons = new ImageIcon[files.size()];
            for (int i = 0; i < files.size(); i++) {
                icons[i] = new ImageIcon(ImageIO.read(files.get(i)).getScaledInstance(widthIcons,
                        heightIcons, java.awt.Image.SCALE_SMOOTH)); // Read in the image from the file and set it to the
                                                                    // icon
            }
        } catch (Exception e) {
            System.out.println(e + " icons");
        }

    }

    public int getWidth() {
        return widthIcons;
    }

    public int getHeight() {
        return heightIcons;
    }

    public ImageIcon[] getIcons() {
        return icons;
    }

    public ImageIcon[] getIconsSmall() {
        ImageIcon[] iconsSmall = icons;
        for (int i = 0; i < iconsSmall.length; i++) {// resize the icons to better fit in the drop down menu
            iconsSmall[i].setImage(iconsSmall[i].getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
        }
        return iconsSmall;
    }

    public int getLastToolbarIndex() {
        return lastToolbarIndex;
    }
}
