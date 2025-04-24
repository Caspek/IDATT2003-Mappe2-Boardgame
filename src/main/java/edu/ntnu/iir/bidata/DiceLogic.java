package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.List;

public class DiceLogic implements Dice {
    private final List<Die> dice;

    public DiceLogic(int numberOfDice) {
        this.dice = new ArrayList<>();
        for (int i = 0; i < numberOfDice; i++) {
            this.dice.add(new Die());
        }
    }

    @Override
    public int roll() {
        int sum = 0;
        for (Die die : dice) {
            sum += die.roll();
        }
        return sum;
    }
}

