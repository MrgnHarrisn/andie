package cosc202.andie.Test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import cosc202.andie.Resize;

public class Test_Resize {
    int testFigure = 10;

    Resize test_resize;

    @BeforeEach
    void setup() {
        test_resize = new Resize();
        test_resize.resizeScale(testFigure);
    }

    @RepeatedTest(10)
    @Test
    void test_getSize() {
        assertEquals(testFigure, test_resize.getScale());
    }
}