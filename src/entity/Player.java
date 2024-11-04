package entity;

import utils.GamePanel;
import utils.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        loadSpriteSheet();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void loadSpriteSheet() {

        try {

            BufferedImage idleSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/idle_atlas.png")));
            BufferedImage walkSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk_atlas.png")));

            idleSprites = new BufferedImage[4][4];
            walkSprites = new BufferedImage[4][4];

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    walkSprites[row][col] = walkSheet.getSubimage(
                            col * 16,
                            row * 16,
                            16,
                            16
                    );
                }
            }

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    idleSprites[row][col] = idleSheet.getSubimage(
                            col * 16,
                            row * 16,
                            16,
                            16
                    );
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean isPressed = keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed;

        if(isPressed) {

            if(keyHandler.upPressed){
                direction = "up";
                y -= speed;
            }else if(keyHandler.downPressed){
                direction = "down";
                y += speed;
            }else if(keyHandler.leftPressed){
                direction = "left";
                x -= speed;
            }else {
                direction = "right";
                x += speed;
            }
        }

        spriteCounter++;
        if(spriteCounter > 17) {
            spriteNumber = (spriteNumber + 1) % 4;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        BufferedImage[][] currentSprites = (keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed) ? walkSprites : idleSprites;

        switch (direction) {
            case "up":
                image = currentSprites[1][spriteNumber];
                break;
            case "down":
                image = currentSprites[0][spriteNumber];
                break;
            case "left":
                image = currentSprites[3][spriteNumber];
                break;
            case "right":
                image = currentSprites[2][spriteNumber];
                break;
        }

        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

        // HITBOX
//        g2.setColor(Color.RED);
//        g2.drawRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
