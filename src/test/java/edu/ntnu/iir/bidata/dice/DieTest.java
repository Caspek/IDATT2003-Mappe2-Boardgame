package edu.ntnu.iir.bidata.dice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DieTest {

    @Test
    public void testRollReturnsValueWithinRange() {
        Die die = new Die(6);
        for (int i = 0; i < 100; i++) {
            int value = die.roll();
            assertTrue(value >= 1 && value <= 6, "Die roll is out of range");
        }
    }

    @Test
    public void testConstructorRejectsInvalidMaxValue() {
        assertThrows(IllegalArgumentException.class, () -> new Die(0));
    }
}

