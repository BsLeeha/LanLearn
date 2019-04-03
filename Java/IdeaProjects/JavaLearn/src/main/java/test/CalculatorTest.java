package test;

import junit.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    @Before
    /*
     * for memory allocation, always run before the test
     */
    public void init() {
        System.out.println("run init");
    }

    @After
    /*
     * for memory release, always run after the test
     */
    public void close() {
        System.out.println("run close");
    }

    @Test
    public void testAdd() {
        // java support method overload
        Assert.assertEquals(3, new Calculator().add(1, 2));
    }

}
