import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
    static JPanel panel = new JPanel();
    static JFrame frame = new JFrame("BrickBreaker");
    static JFrame obj = new JFrame();
    
    public static void main(String[] args) {
        panel.removeAll();
        SetInit();
    }
    
    public static void SetInit(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.YELLOW);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel heading = new JLabel("Brick Breaker Game");
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setFont(new Font("Arial", Font.BOLD, 40));
        heading.setForeground(Color.BLACK);
        
        JButton highScoresButton = new JButton("HighScores");
        JButton playButton = new JButton("Play");
        highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalGlue());
        panel.add(heading);
        panel.add(Box.createVerticalGlue());
        panel.add(highScoresButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(playButton);
        panel.add(Box.createVerticalGlue());
        
        // Add ActionListener to the play button
        highScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //showHighScores();
                HsBtnFunc();
            }   
        });

        
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayBtnFunc(heading,highScoresButton,playButton);    
            }
        });

        frame.setBounds(10, 10, 700, 600);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.setVisible(true);        
    }
    public static void HsBtnFunc() {
                showHighScores();
                // Add back button to panel
                JButton backButton = new JButton("Back");
                backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                backButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    // Remove all items from the panel
                    panel.removeAll();
                    SetInit();
                    }
                });
                panel.add(Box.createVerticalGlue());
                panel.add(backButton);
                panel.add(Box.createVerticalGlue());

                // Repaint the panel to show the high scores and back button
                panel.repaint();
    }
    
    public static void PlayBtnFunc(JLabel a,JButton b, JButton d){
                        // Remove all items from the panel
                panel.removeAll();

                // Create a label and a text input field to enter the name
                JLabel nameLabel = new JLabel("Enter your name (numbers not allowed):");
                JTextField nameField = new JTextField(20);
                
                // Set the text input field to be centered on the panel
                panel.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(10, 10, 10, 10);
                c.anchor = GridBagConstraints.CENTER;
                panel.add(nameLabel, c);

                c.gridx = 0;
                c.gridy = 1;
                c.anchor = GridBagConstraints.CENTER;

                // Create a button to start the gameplay
                JButton startButton = new JButton("Start");
                JButton backButton = new JButton("Back");
                
                startButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        // Validate the name input (not empty and no numbers)
                        if (name.isEmpty() || name.matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid name (numbers not allowed).");
                        } else {
                            // Start the gameplay with the entered name
                            frame.dispose();
                            Interface.name=name;
                            Interface.main(new String[] {"arg1", "arg2"}); 
                        }
                    }
                });
                
                backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Remove all items from the panel
                    panel.removeAll();
                    SetInit();
                }
            });
                
                // Add the label, text input field and button to the panel                             
                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(startButton); 
                panel.add(backButton);
                // Refresh the panel to show the new items
                panel.revalidate();
                panel.repaint();
    }
    
    public static void showHighScores() {
        // Hide all components on the panel
        for (Component c : panel.getComponents()) {
            c.setVisible(false);
        }

        // Load high scores from file
        ArrayList<String[]> highScores = new ArrayList<>();
        try {    
            BufferedReader reader = new BufferedReader(new FileReader("hsfile.bb"));
            String line = reader.readLine();
            while (line != null) {
                String[] score = line.split(":");
                highScores.add(score);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (highScores.isEmpty()) {
            // If there are no high scores to display, show a message
            JLabel noScoresLabel = new JLabel("There are no high scores to display.");
            noScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noScoresLabel.setFont(new Font("Arial", Font.BOLD, 20));
            noScoresLabel.setForeground(Color.BLACK);
            panel.add(Box.createVerticalGlue());
            panel.add(noScoresLabel);
            panel.add(Box.createVerticalStrut(20));
        } else {
            // Sort high scores by score (in descending order)
            Collections.sort(highScores, new Comparator<String[]>() {
                public int compare(String[] s1, String[] s2) {
                    return Integer.parseInt(s2[1]) - Integer.parseInt(s1[1]);
                }
            });

            // Display top ten high scores on the panel
            JLabel highScoresLabel = new JLabel("Top Ten High Scores");
            highScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            highScoresLabel.setFont(new Font("Arial", Font.BOLD, 20));
            highScoresLabel.setForeground(Color.BLACK);
            panel.add(Box.createVerticalGlue());
            panel.add(highScoresLabel);
            panel.add(Box.createVerticalStrut(20));

            int count = 0;
            for (String[] score : highScores) {
                if (count >= 10) {
                    break;
                }
                JLabel scoreLabel = new JLabel((count+1) + ". " + score[0] + " - " + score[1]);
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                scoreLabel.setForeground(Color.BLACK);
                panel.add(scoreLabel);
                panel.add(Box.createVerticalStrut(10));
                count++;
            }
        }
    }
    
    
}

