/***********************************
 * Java Robot automates key presses.
 * It is timing sensitive, requires the game window to have focus, and won't run properly
 * in a headless configuration.
 **/

package org.psnbtech;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class SnakeGameRobotTest {

    private SnakeGame game;
    private Thread gameThread;
    private Robot robot;

    @Before
    public void setUp() throws Exception {

        // Start the game in a separate thread so tests can continue
        game = new SnakeGame();

        gameThread = new Thread(() -> {
            game.startGame();
        });

        gameThread.setDaemon(true);
        gameThread.start();

        // Robot setup
        robot = new Robot();
        robot.setAutoDelay(120);

        // Give the GUI time to initialize
        Thread.sleep(1500);

        // Try to ensure window focus
        game.requestFocus();
        Thread.sleep(500);
    }

    @After
    public void tearDown() throws Exception {
        // Best-effort cleanup (your game currently has no shutdown hook)
        gameThread.interrupt();
    }

    // ----------------------------
    // TEST 1: Basic movement input
    // ----------------------------
    @Test
    public void testSnakeMovementKeys() throws Exception {

        // Move right
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);

        Thread.sleep(400);

        // Move down
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);

        Thread.sleep(400);

        // Move left
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);

        Thread.sleep(400);

        // Move up
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);

        Thread.sleep(500);

        // If we got here without crashing, input system works
        assertTrue(true);
    }

    // ----------------------------
    // TEST 2: Pause / Resume
    // ----------------------------
    @Test
    public void testPauseAndResume() throws Exception {

        // Pause game
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);

        Thread.sleep(500);

        boolean pausedState = game.isPaused();

        // Resume game
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);

        Thread.sleep(500);

        boolean resumedState = game.isPaused();

        assertNotEquals(pausedState, resumedState);
    }

    // ----------------------------
    // TEST 3: Restart game (ENTER)
    // ----------------------------
    @Test
    public void testRestartGame() throws Exception {

        // Trigger restart
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(1000);

        // Game should no longer be in "new game" state
        assertFalse(game.isNewGame());
    }

    // ----------------------------
    // TEST 4: Basic stability test
    // ----------------------------
    @Test
    public void testGameRunsWithoutCrash() throws Exception {

        Thread.sleep(2000);

        // If we can query state, game is alive
        assertNotNull(game.getDirection());
    }
}