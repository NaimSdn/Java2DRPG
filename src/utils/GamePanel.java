package utils;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // Original tile size = 16x16
    final int scale = 4; // Scale for tiles
    final int tileSize = originalTileSize * scale; // Tile size = 64x64
    final int maxScreenCol = 30; // 30 Col
    final int maxScreenRow = 16; // 16 Row

    final int screenWidth = tileSize * maxScreenCol; // Width = 1920px
    final int screenHeight = tileSize * maxScreenRow; // Height = 1024px

    int FPS = 60; // FPS

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Size of screen
        this.setBackground(Color.LIGHT_GRAY); // Background color
        this.setDoubleBuffered(true); // Drawing from this component will be done in an off-screen painting buffer.
        this.addKeyListener(keyHandler); // Add key handler
        this.setFocusable(true); // Focusable to receive key input
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start(); // Start the Thread
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // Delta Game loop
        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            // Showing FPS
            if(timer >= 1000000000){
                System.out.println("FPS = " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if(keyHandler.upPressed){
            playerY -= playerSpeed;
        }else if(keyHandler.downPressed){
            playerY += playerSpeed;
        }else if(keyHandler.leftPressed){
            playerX -= playerSpeed;
        }else if(keyHandler.rightPressed){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY, tileSize, tileSize);
        g2.dispose(); // Release resource
    }
}
