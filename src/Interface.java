import java.awt.Color;

import javax.swing.JFrame;

public class Interface {
    public static String name;
    public static JFrame obj;
	public static void main(String[] args) {
                obj = new JFrame();
		Gameplay gamePlay = new Gameplay(name);
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
                obj.setVisible(true);	
	}

}
