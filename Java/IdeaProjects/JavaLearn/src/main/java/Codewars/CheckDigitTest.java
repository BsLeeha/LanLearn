package Codewars;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class CheckDigitTest {

    CheckDigit myUtil = new CheckDigit();

    @Test
    public void test0() throws Exception
    {
        assertEquals(false,myUtil.isDigit("s2324"));
    }
    @Test
    public void test1() throws Exception
    {
        assertEquals(true,myUtil.isDigit("-234.4"));
    }
}