package edu.ntnu.iir.bidata;

import java.util.List;
import java.util.Random;

public class Dice extends BoardGame {
    private List<Die> dice;
    private Random random;

    public Dice(int numberOfDice) {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }

    //public int getDie(int dieNumber) {

    //}
}
