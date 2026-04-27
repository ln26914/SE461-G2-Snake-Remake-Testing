package org.psnbtech;

/**
 * The {@code TileType} class represents the different
 * types of tiles that can be displayed on the screen.
 * @author Brendan Jones
 *
 */

/**
 * Enumerated type -- Set number of states. Each state has a name
 * The names of the states just exist. There is no code.
 * This functionality could be done by giving each tile an integer, and matching the values of that integer to the
 * various tile types.
 *
 * Using an enumerated type makes this easier to read. Imagine fruit = 1, SnakeHead = 2, SnakeBody = 3
 */

public enum TileType {

	Fruit,
	
	SnakeHead,
	
	SnakeBody
	
}
