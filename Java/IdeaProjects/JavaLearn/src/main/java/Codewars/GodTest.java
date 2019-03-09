package Codewars;


import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

class GodTest {
    @Test
    public void makingAdam(){
        Human[] paradise = God.create();
        assertTrue("Adam are a man", paradise[0] instanceof Man);
    }
}