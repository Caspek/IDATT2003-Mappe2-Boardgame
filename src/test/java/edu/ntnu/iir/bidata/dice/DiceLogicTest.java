package edu.ntnu.iir.bidata.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceLogicTest {

    // enhetstesten skal teste 1 ting, ett enkelt scenario
    // Negative test
    //Sjekker at hvis man kaster dice, så skal assert.equales ikke være lik -1

    @Test
    public void assertEqualsCantEqual() {
        DiceLogic numberOfDice = new DiceLogic();

        assertNotEquals(-1, Die);



    }
}