package utils;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    final int originalTileSize = 16; // Original tile size = 16x16
    final int scale = 4; // Scale for tiles
    final int tileSize = originalTileSize * scale; // Tile size = 64x64
    final int maxScreenCol = 30; // 30 Col
    final int maxScreenRow = 16; // 16 Row

    final int screenWidth = tileSize * maxScreenCol; // Width = 1920px
    final int screenHeight = tileSize * maxScreenRow; // Height = 1024px

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Size of screen
        this.setBackground(Color.LIGHT_GRAY); // Background color
        this.setDoubleBuffered(true); // Drawing from this component will be done in an off-screen painting buffer.
    }
}
