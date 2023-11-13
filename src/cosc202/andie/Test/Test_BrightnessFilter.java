package cosc202.andie.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import cosc202.andie.BrightnessFilter;

public class Test_BrightnessFilter {

    int testFigure = 0;

    BrightnessFilter test_bf;

    @BeforeEach
    public void setup() {
        test_bf = new BrightnessFilter(testFigure);
    }

    @Test
    public void BrightnessFilter() {
        test_bf = new BrightnessFilter(testFigure);
        assertEquals(0, test_bf.getAmplify());
    }

}