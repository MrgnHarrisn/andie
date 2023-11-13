package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to change the brightness of an image
 * </p>
 * 
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Tyler Winmill
 * @version 1.0
 */
public class BrightnessFilter implements ImageOperation, java.io.Serializable {

  public int amplify;

  public BrightnessFilter(int amount) {
    this.amplify = amount;
  }

  public BufferedImage apply(BufferedImage previousImage) {
    float scale = (amplify > 0 ? 1.0f + (amplify / 10.0f) : 1 - Math.abs((amplify / 10.0f)));
    // System.out.println(scale);
    RescaleOp rescaleOp = new RescaleOp(scale, 0, null);
    rescaleOp.filter(previousImage, previousImage);

    return previousImage;
  }

  /**
   * @return amplify
   * @desc purely test purpose
   */
  public int getAmplify() {
    return amplify;
  }

  public String toString() {
      return "Brightness Filter | Size: "+amplify;
  }

}
