package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.List;

public class Dice {
    public List<Die> dice;

    public Dice(int numberOfDice) {
        dice = new ArrayList<>();
        for (int i = 0; i < numberOfDice; i++) {
            dice.add(new Die());
        }
    }

    public int roll() {
        int sum = 0;
        for (Die die : dice) {
            sum += die.roll();
        }
        return sum;
    }

}
