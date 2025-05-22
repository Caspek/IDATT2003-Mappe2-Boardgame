package edu.ntnu.iir.bidata.dice;

/**
 * The Dice interface defines the contract for rolling dice in a game.
 * Implementing classes should provide the logic for rolling the dice.
 */
public interface Dice {

  /**
   * Rolls the dice and returns the result.
   *
   * @return The result of the dice roll.
   */
  int roll();
}
