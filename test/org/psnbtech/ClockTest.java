package org.psnbtech;

import org.junit.Test;      // Imports junit 4's @Test annotation

// Imports Mockito which lets us simulate running parts of the application.
// static keyword allows calling class methods without having to append the library class name for every method call.
// Instead of Mockito.mock(...), we can just type mock()
import static org.mockito.Mockito.*;

/* ****************************************
Important Information about the clock class:
- When a Clock is created, it starts with a parameter that determines the cycles per second.
- The actual cycle length is obtained by dividing 1000 millisecond by the number of cycles per second.
 ********************************* */

public class ClockTest {
    // Goal: Achieve Node Coverage for every function in Clock.java
    // Fortunately, there are very few nodes across all the control flow diagrams
    // Clock cycles are measured in increments of 1000 milliseconds.

    // If the game is not paused, waiting a little over a second should mean that a cycle has passed.
    @Test
    public void testUpdateWhenNotPaused() throws Exception {
        Clock clock = new Clock(1.0f); // 1 cycle per second

        Thread.sleep(1100); // ensure ~1 cycle passes
        clock.update();

        assertTrue(clock.peekElapsedCycle());
    }

    // If the game is paused, waiting a little over a second should mean a cycle has not passed.
    @Test
    public void testUpdateWhenPaused() throws Exception {
        Clock clock = new Clock(1.0f);

        clock.setPaused(true);

        Thread.sleep(1100);
        clock.update();

        assertFalse(clock.peekElapsedCycle());
    }

    // If a clock is just created and never started, it won't have any elapsed cycles.
    @Test
    public void testHasElapsedCycleFalse() {
        Clock clock = new Clock(10.0f); // fast cycles

        assertFalse(clock.hasElapsedCycle());
    }

    // If a clock is created and started, it will have elapsed cycles if it's running for long enough.
    @Test
    public void testHasElapsedCycleTrue() throws Exception {
        Clock clock = new Clock(1.0f);

        Thread.sleep(1100);
        clock.update();

        assertTrue(clock.hasElapsedCycle());
    }

    // If a clock is created, we cannot peek the elapsed cycle as no cycles have transpired.
    @Test
    public void testPeekElapsedCycleFalse() {
        Clock clock = new Clock(1.0f);

        assertFalse(clock.peekElapsedCycle());
    }

    // If a clock is created and started, we can peek the elapsed cycle once cycles have transpired.
    @Test
    public void testPeekElapsedCycleTrue() throws Exception {
        Clock clock = new Clock(1.0f);

        Thread.sleep(1100);
        clock.update();

        assertTrue(clock.peekElapsedCycle());
    }

    // A clock object must not accrue more than one elapsed cycle at a time.
    @Test
    public void testHasElapsedCycleConsumesValue() throws Exception {
        Clock clock = new Clock(1.0f);

        Thread.sleep(1100);
        clock.update();

        assertTrue(clock.hasElapsedCycle());
        assertFalse(clock.hasElapsedCycle()); // second call consumes it
    }

    // The Reset function should restore the state of the timer to its initial state.
    @Test
    public void testResetRestoresState() throws Exception {
        Clock clock = new Clock(1.0f);

        Thread.sleep(1100);
        clock.update();

        assertTrue(clock.peekElapsedCycle());

        clock.reset();

        assertFalse(clock.peekElapsedCycle());
        assertFalse(clock.isPaused());
    }

    // Test IsPaused
}
