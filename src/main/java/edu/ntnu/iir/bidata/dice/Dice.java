package edu.ntnu.iir.bidata.dice;

/**
 * The Dice interface defines the contract for rolling dice in a game.
 * Implementing classes should provide the logic for rolling the dice.
 */
public interface Dice {

  /**
   * Rolls the dice and returns the total sum of the rolls.
   *
   * @return The sum of the dice rolls.
   */
  int roll();
}
