package cosc202.andie.Test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import cosc202.andie.SharpenFilter;

public class Test_Sharpen {

    // Checking to see if the sizes match
    @Test
    public void testSizes() {
        // Size of 1
        SharpenFilter sharpenOne = new SharpenFilter(1);

        float[] gottenOne = sharpenOne.CreateArray();
        float[] expectedOne = new float[] {
            0, -1f/3f, 0,
            -1f/3f, 3f, -1f/3f,
            0, -1f/3f, 0
        };

        Assertions.assertArrayEquals(gottenOne, expectedOne);

        // Size of 3
        SharpenFilter sharpenTwo = new SharpenFilter(3);

        float[] gottenTwo = sharpenTwo.CreateArray();
        float[] expectedTwo = new float[] {
                        0, 0, 0, -1f/7f, 0, 0, 0,
                        0, 0, 0, -1f/7f, 0, 0, 0,
                        0, 0, 0, -1f/7f, 0, 0, 0,
            -1f/7f, -1f/7, -1f/7, 3, -1f/7f, -1f/7, -1f/7, 
                        0, 0, 0, -1f/7f, 0, 0, 0,
                        0, 0, 0, -1f/7f, 0, 0, 0,
                        0, 0, 0, -1f/7f, 0, 0, 0
        };

        Assertions.assertArrayEquals(gottenTwo, expectedTwo);
    }
}
