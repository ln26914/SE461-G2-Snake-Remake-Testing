package org.psnbtech;       // Assigns to same package as the game classes so the tests can access them.

import org.junit.Test;      // Imports junit 4's @Test annotation

import java.awt.FontMetrics;        // Imports these java drawing classes so we can replicate the game.
import java.awt.Graphics;

import static org.mockito.Mockito.*; // Imports Mockito which lets us simulate running parts of the application. static keyword allows calling class methods without having to append the library class name for every method call. Instead of Mockito.mock(...), we can just type mock()

public class SidePanelTest {

    // PURPOSE: This method tests the paintComponent() method of the SidePanel class (it is the only method in that class). It creates a mock of the game to create a SidePanel object on, and runs the method to check if the strings that show the game data rendered correctly.
    @Test       // Marks this method as a junit test method. If there is no assertion/verification fails and no exception crashes it, the test passes.
    public void testPaintComponentDrawsStatisticsAndControls(){
        // Arrange. Create all the mock parts we need.
        SnakeGame mockGame = mock(SnakeGame.class);                 // Creates a fake simulated SnakeGame class object as SidePanel needs SnakeGame object to run. A SnakeGame object is the actual SnakeGame application. Replace the real game with a mock version that we have full control of.
        Graphics mockGraphics = mock(Graphics.class);               // Creates a fake simulated Graphics class object as SidePanel needs a Graphics object to run. A Graphics object holds the data of what the UI tries to draw. This mock class records all that data without actually displaying it.
        FontMetrics mockFontMetrics = mock(FontMetrics.class);      // Creates a fake simulated FontMetrics class object as SidePanel needs a Graphics object to run. A FontMetrics object is data that holds the formatting for all the text.

        when(mockGraphics.create()).thenReturn(mockGraphics);       // A background library runs this method in SidePanel, so this method needs to have a mocked return of our created graphics object.
        when(mockGame.getScore()).thenReturn(100);              // Create fake game data to test. So when the SidePanel calls game.getScore(), then we should return 100. In this test, our score is 100 to prevent unpredictability rendering it untestable. Without mocking it, it would return 0 by default.
        when(mockGame.getFruitsEaten()).thenReturn(5);          // Create fake game data to test. So when the SidePanel calls game.getFruitsEaten(), then we should return 5. In this test, our FruitsEaten is 5 to prevent unpredictability rendering it untestable.
        when(mockGame.getNextFruitScore()).thenReturn(20);      // Create fake game data to test. So when the SidePanel calls game.getNextFruitScore(), then we should return 20. In this test, our NextFruitScore is 20 to prevent unpredictability rendering it untestable. These are done so all the data that the panel will display is defined.

        when(mockGraphics.getFontMetrics()).thenReturn(mockFontMetrics);    // If getFontMetrics() is called, give the fake FontMetrics we mocked.
        when(mockFontMetrics.stringWidth("Snake Game")).thenReturn(100);    //Pretend the title text is 100 pixels wide. This is necessary to control the positioning math for the centering text.

        SidePanel panel = new SidePanel(mockGame);          // We are creating a REAL SidePanel class object to go onto our FAKE game.
        panel.setSize(300,600);              // Set the width and height of our panel. This is needed because getWidth() is used inside paintComponent. Without this, width defaults to 0 and positions break.

        // Act. Run the method.
        panel.paintComponent(mockGraphics);     // Run the method that we are testing. Use all of the mocked, controlled objects we defined.

        // Assert statistics text was drawn
        verify(mockGraphics).drawString("Total Score: 100", 50, 180);       // Verify if this exact string was draw at coordinates x=50 and y=180
        verify(mockGraphics).drawString("Fruit Eaten: 5", 50, 210);         // Verify if this exact string was draw at coordinates x=50 and y=210
        verify(mockGraphics).drawString("Fruit Score: 20", 50, 240);        // Verify if this exact string was draw at coordinates x=50 and y=240

        // Assert controls text was drawn
        verify(mockGraphics).drawString("Move Up: W / Up Arrowkey", 50, 350);       // Verify if this exact string was draw at coordinates x=50 and y=350
        verify(mockGraphics).drawString("Move Down: S / Down Arrowkey", 50, 380);   // Verify if this exact string was draw at coordinates x=50 and y=380
        verify(mockGraphics).drawString("Move Left: A / Left Arrowkey", 50, 410);   // Verify if this exact string was draw at coordinates x=50 and y=410
        verify(mockGraphics).drawString("Move Right: D / Right Arrowkey", 50, 440); // Verify if this exact string was draw at coordinates x=50 and y=440
        verify(mockGraphics).drawString("Pause Game: P",50,470);                    // Verify if this exact string was draw at coordinates x=50 and y=480
    }
}
