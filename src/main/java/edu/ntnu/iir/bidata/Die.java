package edu.ntnu.iir.bidata;

import java.util.Random;

public class Die {
    private int lastRolledValue;
    private transient final Random random;

    public Die() {
        this.random = new Random();
    }

    public int roll() {
        lastRolledValue = random.nextInt(6) + 1;
        return lastRolledValue;
    }

}
