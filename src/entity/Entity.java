package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;
    public int spriteCounter = 0;
    public int spriteNumber = 0;

    public String direction;

    public BufferedImage[][] idleSprites;
    public BufferedImage[][] walkSprites;

    public Rectangle collisionArea;
    public int collisionAreaDefaultX;
    public int collisionAreaDefaultY;
    public boolean collisionOn = false;
}
