package cosc202.andie.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.GaussianFilter;

public class TestGaussian {
    // Checks that default constructor creates the same kernel as a filter with radius 1.
    @Test
    void testDefault(){
        GaussianFilter testGaussian1 = new GaussianFilter();
        GaussianFilter testGaussian2 = new GaussianFilter(1);
        
        Assertions.assertArrayEquals(testGaussian1.getKernelArray(), testGaussian2.getKernelArray());
    }
    // Checks that a filter with a radius 1, 2 and 3 create a kernel array with the expected values.
    @Test
    void testKernel(){
        GaussianFilter testGaussian = new GaussianFilter();

        double[] expectedArray1 = new double[]{
            0.000, 0.011, 0.000,
            0.011, 0.957, 0.011,
            0.000, 0.011, 0.000
        };
        double[] expectedArray2 = new double[]{
            0.000, 0.001, 0.004, 0.001, 0.000,
            0.001, 0.038, 0.116, 0.038, 0.001,
            0.004, 0.116, 0.358, 0.116, 0.004,
            0.001, 0.038, 0.116, 0.038, 0.001,
            0.000, 0.001, 0.004, 0.001, 0.000
        };
        double[] expectedArray3 = new double[]{
            0.000, 0.000, 0.001, 0.002, 0.001, 0.000, 0.000,
            0.000, 0.003, 0.013, 0.022, 0.013, 0.003, 0.000,
            0.001, 0.013, 0.059, 0.097, 0.059, 0.013, 0.001,
            0.002, 0.022, 0.097, 0.159, 0.097, 0.022, 0.002,
            0.001, 0.013, 0.059, 0.097, 0.059, 0.013, 0.001,
            0.000, 0.003, 0.013, 0.022, 0.013, 0.003, 0.000,
            0.000, 0.000, 0.001, 0.002, 0.001, 0.000, 0.000,
        };

        float[] gfArr;
        double[] tempArr;

        testGaussian = new GaussianFilter(1);
        gfArr = testGaussian.getKernelArray();
        tempArr = new double[gfArr.length];
        for(int i = 0; i < gfArr.length; i++){
            tempArr[i] = Math.round(gfArr[i] * 1000d) / 1000d;
        }
        Assertions.assertArrayEquals(expectedArray1, tempArr);

        testGaussian = new GaussianFilter(2);
        gfArr = testGaussian.getKernelArray();
        tempArr = new double[gfArr.length];
        for(int i = 0; i < gfArr.length; i++){
            tempArr[i] = Math.round(gfArr[i] * 1000d) / 1000d;
        }
        Assertions.assertArrayEquals(expectedArray2, tempArr);

        testGaussian = new GaussianFilter(3);
        gfArr = testGaussian.getKernelArray();
        tempArr = new double[gfArr.length];
        for(int i = 0; i < gfArr.length; i++){
            tempArr[i] = Math.round(gfArr[i] * 1000d) / 1000d;
        }
        Assertions.assertArrayEquals(expectedArray3, tempArr);
    }
}
