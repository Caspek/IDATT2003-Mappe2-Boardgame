import edu.ntnu.iir.bidata.Dice;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DiceTest {

    @Test
    public void testRoll() {
        Dice dice = new Dice(1);
        for (int i = 0; i < 10; i++) {
            int result = dice.roll();
            assertTrue(result >= 1 && result <= 6, "Dice roll out of bounds: " + result);
        }
    }
}
