package cosc202.andie.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import cosc202.andie.ContrastFilter;

public class Test_ContrastFilter {

    @RepeatedTest(10)
    @Test
    public void test_cf() {
        int testFigure = 1;
        ContrastFilter test_cf = new ContrastFilter(testFigure);
        assertEquals(testFigure, test_cf.getConValue());
    }
}
