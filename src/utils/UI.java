package utils;

import object.Object_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial;
    BufferedImage keyImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial = new Font("Arial", Font.PLAIN, 40);
        Object_Key key = new Object_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial);
        g2.setColor(Color.black);

        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 100, 75);

    }
}
