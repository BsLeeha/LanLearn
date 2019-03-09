package Codewars;


import static org.junit.Assert.assertArrayEquals;

class XbonacciTest {

    private Xbonacci variabonacci;

    void setUp() {
        variabonacci = new Xbonacci();
    }

    void tearDown() {
        variabonacci = null;
    }

    private double precision = 1e-10;

    void tribonacci() {
        assertArrayEquals(new double []{1,1,1,3,5,9,17,31,57,105}, variabonacci.tribonacci(new double []{1,1,1},10), precision);
        assertArrayEquals(new double []{0,0,1,1,2,4,7,13,24,44}, variabonacci.tribonacci(new double []{0,0,1},10), precision);
        assertArrayEquals(new double []{0,1,1,2,4,7,13,24,44,81}, variabonacci.tribonacci(new double []{0,1,1},10), precision);
    }
}