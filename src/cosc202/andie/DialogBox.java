package cosc202.andie;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogBox {
    private static JFrame frame = new JFrame();
    
    /**
     * A class that creates a dialog box
     * @param frame         The frame we want this attached to
     * @param message       The message we want to say
     * @param title         The title of the message          
     * @param msgType       This decides the display type of the box
     */

    public DialogBox() {}

    public static void dialogBox(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

}
