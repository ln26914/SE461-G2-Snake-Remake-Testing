package org.psnbtech;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.FontMetrics;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardPanelTest {
	
	@Mock
	private static SnakeGame mockGame = mock(SnakeGame.class);
	
	@InjectMocks
	private static BoardPanel panel;
	
	@Mock
	private static Graphics graphics = mock(Graphics.class);
	
	
	
	@Mock
	private static FontMetrics mockMetrics = mock(FontMetrics.class);
	
	@Before
	public void setUp() throws Exception {
		panel = new BoardPanel(mockGame);
		panel.setSize(600, 600);
		//graphics = image.getGraphics();
		when(graphics.create()).thenReturn(graphics);
		when(graphics.getFontMetrics()).thenReturn(mockMetrics);
	}

	@After  
	public void tearDown() throws Exception {
		panel = null;
	}

	@Test
	public void testClearBoard() {
		TileType tile = TileType.Fruit;
		Point p = new Point(3, 4);
		panel.setTile(p, tile);
		panel.clearBoard();
		assertNull(panel.getTile(3,4));
	}
  
	@Test
	public void testPaintComponent() {
		when(graphics.getFontMetrics()).thenReturn(mockMetrics);
		when(mockMetrics.stringWidth(anyString())).thenReturn(100);
		when(mockGame.isNewGame()).thenReturn(true);
		panel.paintComponent(graphics);
		verify(graphics).drawString(eq("Snake Game!"), anyInt(), anyInt());
		verify(graphics).drawString(eq("Press Enter to Start"), anyInt(), anyInt());
	}
	
	
}
