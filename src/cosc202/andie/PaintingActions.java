package cosc202.andie;

import javax.swing.JMenu;

public class PaintingActions {

    PaintingFrame pf = new PaintingFrame();
    JMenu menu = new JMenu("Draw");
    ImagePanel p;

    public PaintingActions() {
    }

    /**
     * Create the menu and return it
     * 
     * @return the menu we made
     */
    public JMenu createJMenu(ImagePanel ip) {
        menu.add(pf.new PaintingWindow(ip));
        return menu;
    }

}
