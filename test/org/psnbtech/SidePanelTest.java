package org.psnbtech;       // Assigns to same package as the game classes so the tests can access them.

import org.junit.Test;      // Imports junit 4's @Test annotation

import java.awt.FontMetrics;        // Imports these java drawing classes so we can replicate the game.
import java.awt.Graphics;

import static org.mockito.Mockito.*; // Imports Mockito which lets us simulate running parts of the application. static keyword allows calling class methods without having to append the library class name for every method call. Instead of Mockito.mock(...), we can just type mock()

public class SidePanelTest {

    // PURPOSE: This method ...
    @Test       // Marks this method as a junit test method. If there is no assertion/verification fails and no exception crashes it, the test passes.
    public void testPaintComponentDrawsStatisticsAndControls(){
        // Arrange. Create all the mock parts we need.
        SnakeGame mockGame = mock(SnakeGame.class);                 // Creates a fake simulated SnakeGame class object as SidePanel needs SnakeGame object to run. A SnakeGame object is the actual SnakeGame application.
        Graphics mockGraphics = mock(Graphics.class);               // Creates a fake simulated Graphics class object as SidePanel needs a Graphics object to run. A Graphics object is TODO
        FontMetrics mockFontMetrics = mock(FontMetrics.class);      // Creates a fake simulated FontMetrics class object as SidePanel needs a Graphics object to run. A FontMetrics object is TODO

        when(mockGame.getScore()).thenReturn(100);              // Create fake game data to test. So when the SidePanel calls game.getScore(), then we should return 100.
        when(mockGame.getFruitsEaten()).thenReturn(5);          // Create fake game data to test. So when the SidePanel calls game.getFruitsEaten(), then we should return 5.
        when(mockGame.getNextFruitScore()).thenReturn(20);      // Create fake game data to test. So when the SidePanel calls game.getNextFruitScore(), then we should return 20.

        when(mockGraphics.getFontMetrics()).thenReturn(mockFontMetrics);    // If getFontMetrics() is called, give the fake FontMetrics we mocked.
        when(mockFontMetrics.stringWidth("Snake Game")).thenReturn(100);    //Pretend the title text is 100 pixels wide.

        SidePanel panel = new SidePanel(mockGame);          // We are creating a REAL SidePanel class object to go onto our FAKE game.
        panel.setSize(300,600);              // Set the width and height of our panel. This is needed because getWidth() is used inside paintComponent

        // Act. Run the method.
        panel.paintComponent(mockGraphics);     // Run the method that we are testing.

        // Assert statistics text was drawn
        verify(mockGraphics).drawString("Total Score: 100", 50, 180);       // Verify if this exact string was draw at coordinates x=50 and y=180
        verify(mockGraphics).drawString("Fruit Eaten: 5", 50, 210);         // Verify if this exact string was draw at coordinates x=50 and y=210
        verify(mockGraphics).drawString("Fruit Score: 20", 50, 240);        // Verify if this exact string was draw at coordinates x=50 and y=240

        // Assert controls text was drawn
        verify(mockGraphics).drawString("Move Up: W / Up Arrowkey", 50, 350);       // Verify if this exact string was draw at coordinates x=50 and y=350
        verify(mockGraphics).drawString("Move Down: S / Down Arrowkey", 50, 380);   // Verify if this exact string was draw at coordinates x=50 and y=380
        verify(mockGraphics).drawString("Move Left: A / Left Arrowkey", 50, 410);   // Verify if this exact string was draw at coordinates x=50 and y=410
        verify(mockGraphics).drawString("Move Right: D / Right Arrowkey", 50, 440); // Verify if this exact string was draw at coordinates x=50 and y=440
        verify(mockGraphics).drawString("Pause Game: P",50,480);                    // Verify if this exact string was draw at coordinates x=50 and y=480
    }
}
