package org.psnbtech;

/**
 * The {@code Direction} enum is used to determine which way the Snake is moving.
 * @author Brendan Jones
 *
 */

/**
 * Enumerated type -- Set number of states. Each state has a name
 * The names of the states just exist. There is no code.
 * This functionality could be done by giving each tile an integer, and matching the values of that integer to the
 * various tile types.
 *
 * Using an enumerated type makes this easier to read. Imagine North = 0, East = 1, South = 2, West = 3
 */

public enum Direction {

	/**
	 * Moving North (Up).
	 */
	North,
	
	/**
	 * Moving East (Right).
	 */
	East,
	
	/**
	 * Moving South (Down).
	 */
	South,
	
	/**
	 * Moving West (Left).
	 */
	West
	
}
