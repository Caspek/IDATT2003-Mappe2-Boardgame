package edu.ntnu.iir.bidata.dice;

import java.util.Random;

public class Die {
    private static final Random random = new Random(); // Shared Random instance
    private final int maxValue;

    /**
     * Constructs a Die object with the default maximum value of 6.
     */
    public Die() {
        this(6); // Default to a six-sided die
    }

    /**
     * Constructs a Die object with a specified maximum value.
     * @param maxValue The maximum value of the die.
     * @throws IllegalArgumentException if the maxValue is less than 1.
     */
    public Die(int maxValue) {
        if (maxValue < 1) {
            throw new IllegalArgumentException("Maximum value must be at least 1.");
        }
        this.maxValue = maxValue;
    }

    /**
     * Rolls the die and returns a random value between 1 and maxValue (inclusive).
     * @return A random integer between 1 and maxValue.
     */
    public int roll() {
        return random.nextInt(maxValue) + 1; // Generate a value between 1 and maxValue
    }
}