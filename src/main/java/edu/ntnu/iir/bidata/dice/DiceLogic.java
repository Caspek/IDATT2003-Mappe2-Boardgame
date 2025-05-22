package edu.ntnu.iir.bidata.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * The DiceLogic class implements the Dice interface and provides functionality to roll multiple dice.
 * It manages a list of Die objects and calculates the total sum of their rolls.
 */
public class DiceLogic implements Dice {
    private final List<Die> dice;

    /**
     * Constructs a DiceLogic object with the specified number of dice.
     * @param numberOfDice The number of dice to use.
     * @throws IllegalArgumentException if the number of dice is less than 1.
     */
    public DiceLogic(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Number of dice must be at least 1.");
        }
        this.dice = new ArrayList<>();
        for (int i = 0; i < numberOfDice; i++) {
            this.dice.add(new Die());
        }
    }



    /**
     * Rolls all the dice and returns the total sum.
     * @return The sum of the dice rolls.
     */
    @Override
    public int roll() {
        int sum = 0;
        for (Die die : dice) {
            sum += die.roll();
        }
        return sum;
    }
}

