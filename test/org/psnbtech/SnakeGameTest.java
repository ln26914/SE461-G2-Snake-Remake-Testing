package org.psnbtech;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SnakeGameTest {

    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object invokePrivateMethod(Object obj, String methodName) throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj);
    }

    @Test
    public void testBooleanGetters() throws Exception {     // all dataflow
        SnakeGame game = new SnakeGame();

        setPrivateField(game, "isNewGame", true);
        setPrivateField(game, "isGameOver", false);
        setPrivateField(game, "isPaused", true);

        assertTrue(game.isNewGame());
        assertFalse(game.isGameOver());
        assertTrue(game.isPaused());
    }

    @Test
    public void testScoreGetters() throws Exception {       // all data flow
        SnakeGame game = new SnakeGame();

        setPrivateField(game, "score", 250);
        setPrivateField(game, "fruitsEaten", 4);
        setPrivateField(game, "nextFruitScore", 70);

        assertEquals(250, game.getScore());
        assertEquals(4, game.getFruitsEaten());
        assertEquals(70, game.getNextFruitScore());
    }

    @Test
    public void testGetDirection() throws Exception {       // data flow
        SnakeGame game = new SnakeGame();

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.North);

        setPrivateField(game, "directions", directions);

        assertEquals(Direction.North, game.getDirection());
    }

    @Test
    public void testSpawnFruitSetsNextFruitScoreTo100() throws Exception {  // graph coverage (method has loops & conditionals) + data flow (nextFruitScore is defined and tested)
        // Arrange
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);
        Random mockRandom = mock(Random.class);

        LinkedList<Object> fakeSnake = new LinkedList<>();

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "random", mockRandom);
        setPrivateField(game, "snake", fakeSnake);
        setPrivateField(game, "nextFruitScore", 25);

        when(mockRandom.nextInt(anyInt())).thenReturn(0);
        when(mockBoard.getTile(anyInt(), anyInt())).thenReturn(null);

        Method spawnFruit = SnakeGame.class.getDeclaredMethod("spawnFruit");
        spawnFruit.setAccessible(true);

        // Act
        spawnFruit.invoke(game);

        // Assert
        assertEquals(100, game.getNextFruitScore());
        verify(mockBoard).setTile(0, 0, TileType.Fruit);
    }

    @Test
    public void testUpdateSnakeHitsWallReturnsSnakeBody() throws Exception {    // graph coverage
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);

        LinkedList<Point> snake = new LinkedList<>();
        snake.add(new Point(0, 0));

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.North);

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "snake", snake);
        setPrivateField(game, "directions", directions);

        TileType result = (TileType) invokePrivateMethod(game, "updateSnake");

        assertEquals(TileType.SnakeBody, result);

        game.dispose();
    }

    @Test
    public void testUpdateSnakeMovesIntoEmptyTile() throws Exception {      //graph coverage + data flow
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);

        Point start = new Point(5, 5);
        Point expectedHead = new Point(5, 4);

        LinkedList<Point> snake = new LinkedList<>();
        snake.add(start);

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.North);

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "snake", snake);
        setPrivateField(game, "directions", directions);

        when(mockBoard.getTile(expectedHead.x, expectedHead.y)).thenReturn(null);

        TileType result = (TileType) invokePrivateMethod(game, "updateSnake");

        assertNull(result);
        assertEquals(expectedHead, snake.peekFirst());

        verify(mockBoard).setTile(start, TileType.SnakeBody);
        verify(mockBoard).setTile(expectedHead, TileType.SnakeHead);

        game.dispose();
    }

    @Test
    public void testUpdateGameFruitCollisionIncreasesScoreAndSpawnsFruit() throws Exception {   //graph coverage + data flow
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);
        Random mockRandom = mock(Random.class);
        Clock mockClock = mock(Clock.class);

        Point start = new Point(5, 5);
        Point fruitLocation = new Point(5, 4);

        LinkedList<Point> snake = new LinkedList<>();
        snake.add(start);

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.North);

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "random", mockRandom);
        setPrivateField(game, "logicTimer", mockClock);
        setPrivateField(game, "snake", snake);
        setPrivateField(game, "directions", directions);
        setPrivateField(game, "score", 0);
        setPrivateField(game, "fruitsEaten", 0);
        setPrivateField(game, "nextFruitScore", 50);

        when(mockBoard.getTile(anyInt(), anyInt())).thenReturn(null);
        when(mockBoard.getTile(fruitLocation.x, fruitLocation.y)).thenReturn(TileType.Fruit);
        when(mockRandom.nextInt(anyInt())).thenReturn(0);

        invokePrivateMethod(game, "updateGame");

        assertEquals(1, game.getFruitsEaten());
        assertEquals(50, game.getScore());
        assertEquals(100, game.getNextFruitScore());

        verify(mockBoard).setTile(0, 0, TileType.Fruit);

        game.dispose();
    }

    @Test
    public void testUpdateGameWallCollisionSetsGameOverAndPausesTimer() throws Exception {      // graph coverage
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);
        Clock mockClock = mock(Clock.class);

        LinkedList<Point> snake = new LinkedList<>();
        snake.add(new Point(0, 0));

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.North);

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "logicTimer", mockClock);
        setPrivateField(game, "snake", snake);
        setPrivateField(game, "directions", directions);
        setPrivateField(game, "isGameOver", false);

        invokePrivateMethod(game, "updateGame");

        assertTrue(game.isGameOver());
        verify(mockClock).setPaused(true);

        game.dispose();
    }

    @Test
    public void testResetGameSetsDefaultState() throws Exception {      // data flow
        SnakeGame game = new SnakeGame();

        BoardPanel mockBoard = mock(BoardPanel.class);
        Random mockRandom = mock(Random.class);
        Clock mockClock = mock(Clock.class);

        LinkedList<Point> snake = new LinkedList<>();
        snake.add(new Point(1, 1));

        LinkedList<Direction> directions = new LinkedList<>();
        directions.add(Direction.East);

        setPrivateField(game, "board", mockBoard);
        setPrivateField(game, "random", mockRandom);
        setPrivateField(game, "logicTimer", mockClock);
        setPrivateField(game, "snake", snake);
        setPrivateField(game, "directions", directions);
        setPrivateField(game, "score", 999);
        setPrivateField(game, "fruitsEaten", 9);
        setPrivateField(game, "isNewGame", true);
        setPrivateField(game, "isGameOver", true);

        when(mockBoard.getTile(anyInt(), anyInt())).thenReturn(null);
        when(mockRandom.nextInt(anyInt())).thenReturn(0);

        invokePrivateMethod(game, "resetGame");

        assertEquals(0, game.getScore());
        assertEquals(0, game.getFruitsEaten());
        assertFalse(game.isNewGame());
        assertFalse(game.isGameOver());
        assertEquals(Direction.North, game.getDirection());
        assertEquals(1, snake.size());

        verify(mockBoard).clearBoard();
        verify(mockClock).reset();
        verify(mockBoard).setTile(new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2), TileType.SnakeHead);
        verify(mockBoard).setTile(0, 0, TileType.Fruit);

        game.dispose();
    }

}
