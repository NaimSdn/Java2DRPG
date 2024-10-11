package utils;

import javax.swing.*;

public class Window extends JFrame {

    public void createWindow() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X button working
        frame.setResizable(false); // No resize for now
        frame.setTitle("2D-RPG"); // Title of the frame

        GamePanel gamePanel = new GamePanel(); // Init game panel
        frame.add(gamePanel); // Adding game panel on the frame

        frame.pack();

        frame.setLocationRelativeTo(null); // Make the frame in the middle of the screen
        frame.setVisible(true); // Make the frame visible
    }
}
