package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to add a sharpen filter to an image
 * </p>
 * 
 * <p>
 * It works by removing the weight value of each side of a given pixel (exluding corners) bu a given amount. This creates a contrast between each pixel and its surrounding values
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Tyler Winmill
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    
    private int amplify = 1;

    public SharpenFilter() { }

    public SharpenFilter(int amplify) { this.amplify = amplify; }

    public BufferedImage apply(BufferedImage toSharpen) {

        try {
            // I found this has been the best result so far, but I will continue to try reduce the intenseness but currently it works!
            int size = 2 * amplify + 1;

            // Creating the kernal mask to use
            float[] array = CreateArray();

            // Applying the fitler and returning the newly sharpened image
            Kernel kernel = new Kernel(size ,size, array);
            ExtendedFilter ef = new ExtendedFilter(toSharpen, kernel);
            return ef.filter();

        } catch (NullPointerException e) {
            DialogBox.dialogBox("You need an image to apply the filter to!");
        } catch (Exception e) {
            DialogBox.dialogBox(e.toString());
        }
        return toSharpen;
    }

    /**
     * This will create a sizable grid to use in the kernal.
     * E.g. an amplify of 1 would give a 3x3 grid of
     * [0,   -.3f, 0      ]
     * [-.3f, -.3f, -.3f,   ]
     * [0    -.3f, 0      ]
     * @return Returns the grid in a a 1D array
     */
    public float[] CreateArray() {
        int size = 2 * amplify + 1;
        float[] toReturn = new float[size * size];
        float[][] tempArray = new float[size][];
        int counter = 0;
        
        // Creating the grid in a 2D array
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = new float[size];
            for (int y = 0; y < tempArray[i].length; y++) {
                // Checking to see if it is in the middle of a row or column
                if (i == tempArray.length / 2 && y != tempArray[i].length / 2 || i != tempArray.length / 2 && y == tempArray[i].length / 2) {
                    tempArray[i][y] = -1.0f / size;
                }
                // Checking to see if it is the centre pixel
                else if (i == tempArray.length / 2 && y == tempArray[i].length / 2) {
                    tempArray[i][y] = 3.0f;
                }
            }
        }

        // Flattening 2D array into a 1D array to use
        for (int i = 0; i < tempArray.length; i++) {
            for (int y = 0; y < tempArray[i].length; y++) {
                toReturn[counter] = tempArray[i][y];
                counter++;
            }
        }


        return toReturn;
    }

    public String toString() {
        return "Sharpen Filter | Size: "+amplify;
    }
}