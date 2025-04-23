import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.iir.bidata.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class DiceTest {
    private Dice dice;

    @BeforeEach
    public void setUp() {
        dice = new Dice(1); //
    }

    @Test
    public void testRollReturnsValueWithinRange() {
        int result = dice.roll();
        assertTrue(result >= 1 && result <= 6, "Roll should be between 1 and 6");
        System.out.println(result);
    }

    @Test
    public void testRollWithMultipleDice() {
        dice = new Dice(2); // Check when we roll 2 dice, can be used for larger boards
        int result = dice.roll();
        assertTrue(result >= 2 && result <= 12, "Roll should be between 2 and 12");
        System.out.println(result);
    }

    @Test
    public void testRollWithZeroDice() {
        dice = new Dice(0); // Should roll a 0 because there is no dice
        int result = dice.roll();
        assertEquals(0, result, "Roll should be 0 when no dice are used");
        System.out.println(result);
    }
}