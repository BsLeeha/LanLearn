package Codewars;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class RemoveCharsTest {
    @Test
    public void testRemoval() {
        assertEquals("loquen", RemoveChars.remove("eloquent"));
        assertEquals("ountr", RemoveChars.remove("country"));
        assertEquals("erso", RemoveChars.remove("person"));
        assertEquals("lac", RemoveChars.remove("place"));
    }
}