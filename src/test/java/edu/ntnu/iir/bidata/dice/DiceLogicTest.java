package edu.ntnu.iir.bidata.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiceLogicTest {

    @Test
    void testRollInRangeWithOneDie() {
        DiceLogic dice = new DiceLogic(1);
        int roll = dice.roll();
        assertTrue(roll >= 1 && roll <= 6);
    }

    @Test
    void testRollInRangeWithTwoDice() {
        DiceLogic dice = new DiceLogic(2);
        int roll = dice.roll();
        assertTrue(roll >= 2 && roll <= 12);
    }

    @Test
    void testInvalidDiceNumberThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DiceLogic(0));
    }
}
