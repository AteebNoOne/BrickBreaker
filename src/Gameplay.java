
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener 
{
	public static void main(String[] args){
            
        }    
	public boolean play;
	public int score;
	public String name;
	public int totalBricks;
	
	public Timer timer;
	public int delay;
	
	public int playerX;
	
	public int ballposX;
	public int ballposY ;
	public int ballXdir ;
	public int ballYdir ;
        
        
	
	private MapGenerator map;
	
	public Gameplay(String name)
	{		
                this.delay=8;
                this.totalBricks = 48;
                this.score=0;
                this.name = name;
                this.play = false;
                this.playerX = 310;
	
                this.ballposX = 120;
                this. ballposY = 350;
                this.ballXdir = -1;
                this.ballYdir = -2;                
		map = new MapGenerator(4, 12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
                timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
		map.draw((Graphics2D) g);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// the scores 		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,30);
		
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
	
		// when you won the game
		if(totalBricks <= 0)
		{
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             g.drawString("Extra Score +10", 270,250);
             score+=10;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);
             g.drawString("Press (B) to go Back", 230,370);   
             
		}
		
		// when you lose the game
		if(ballposY > 570)
        {
			play = false;
                        ballXdir = 0;
                        ballYdir = 0;
                 
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Game Over, Scores: "+score, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);     
            g.drawString("Press (B) to go Back", 230,370); 
        }
		
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
                                saveScore(name,score);
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
                }
                if (e.getKeyCode() == KeyEvent.VK_B){
                    if(!play){   
                        saveScore(name,score);
                        Main.main(new String[] {"arg1", "arg2"});    
                        Interface.obj.dispose();
                    }
                }
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		play = true;
		playerX+=20;	
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;	 	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(play)
		{			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball		
			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//scores++;
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							totalBricks--;
							
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}		
			
			repaint();		
		}
	}


    public static void saveScore(String name, int score) {
        try {
        File file = new File("hsfile.bb");
        if (!file.exists()) {
        file.createNewFile();
        }
        Path filePath = Paths.get("hsfile.bb");
        boolean isNamePresent = false;
        // Check if name already exists in file
        BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
        String[] parts = line.split(":");
        if (parts[0].equals(name)) {
        int existingScore = Integer.parseInt(parts[1]);
        // Update the score if the new score is higher
        line = name + ":" + (existingScore+score);

        isNamePresent = true;
        }
        stringBuilder.append(line + "\n");
        line = reader.readLine();
        }
        reader.close();
            // Add the new name and score if it is not already present in the file
            if (!isNamePresent) {
                stringBuilder.append(name + ":" + score + "\n");
            }

            // Write the updated high scores to the file
            FileWriter writer = new FileWriter(filePath.toFile());
            writer.write(stringBuilder.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // File system does not support setting attributes
        }
    }
   
}
