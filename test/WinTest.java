/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author BSP
 */

import org.junit.Test;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class WinTest {
   
    
@Test
public  void testWin() {
    Gameplay gameplay = new Gameplay("TestPlayer");
    gameplay.totalBricks = 0;
    gameplay.ballXdir = 0;
    gameplay.ballYdir = 0;
    BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    gameplay.paint(g); // simulate the paint method call
    // verify the score has been increased by 10
    assertTrue(gameplay.score == 10);
}
 
@Test
public void BallMovement() throws InterruptedException{
    Gameplay game = new Gameplay("player1");
    game.ballposX = 50;
    game.ballposY = 50;
    game.ballXdir = -1;
    game.ballYdir = -1;
    game.timer.start();
    Thread.sleep(5000);
    assertEquals(50, game.ballposX);
    assertEquals(50, game.ballposY);
}

@Test
public void PaddleRight(){
    Gameplay game = new Gameplay("player2");
    game.playerX = 300;
    game.moveRight();
    assertEquals(320, game.playerX);
}

@Test
public void PaddleLeft(){
    Gameplay game = new Gameplay("player3");
    game.playerX = 300;
    game.moveLeft();
    assertEquals(280, game.playerX);
}
    
@Test
public void BallBelowPaddle() throws InterruptedException{
    Gameplay game = new Gameplay("player4");
    game.ballposX = 100;
    game.ballposY = 580;
    game.ballXdir = -1;
    game.ballYdir = 1;
    game.timer.start();
    Thread.sleep(5000);
    assertFalse(game.play);
}

@Test
public void HighScoreUpdateOnGameEnd() throws InterruptedException, FileNotFoundException, IOException{
    Gameplay game = new Gameplay("player7");
    game.ballposX = 100;
    game.ballposY = 580;
    game.ballXdir = -1;
    game.ballYdir = 1;
    game.timer.start();
    Thread.sleep(5000);
    assertEquals(0, game.score);
    File file = new File("hsfile.bb");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = reader.readLine();
    assertEquals("Ateeb:2935", line);
}
}
