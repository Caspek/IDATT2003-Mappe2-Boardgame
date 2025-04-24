package edu.ntnu.iir.bidata.dice;

import java.util.Random;

public class Die {
    private static final int MAX_VALUE = 6;
    private final Random random;

    public Die() {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(MAX_VALUE) + 1;
    }
}
