package entity;

import utils.GamePanel;
import utils.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        collisionArea = new Rectangle(15, 25, 34, 35);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        setDefaultValues();
        loadSpriteSheet();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 5;
        worldY = gamePanel.tileSize * 4;
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean isPressed = keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed;

        if (isPressed) {

            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionHandler.checkTile(this);

            int objectIndex = gamePanel.collisionHandler.checkObject(this, true);
            pickUpObject(objectIndex);

            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
        }

        spriteCounter++;
        if (spriteCounter > 17) {
            spriteNumber = (spriteNumber + 1) % 4;
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int index) {

        if(index != 999){

            String objectName = gamePanel.object[index].name;

            switch (objectName){
                case "Key":
                    hasKey++;

                    gamePanel.object[index] = null;
                    break;
                case "Door":
                    if(hasKey > 0){
                        gamePanel.object[index] = null;
                        hasKey--;
                    }
                    break;
            }
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

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // SHOW COLLISION
        g2.setColor(new Color(255, 0, 0, 128));
        g2.fillRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);

        // SHOW HITBOX
//        g2.setColor(Color.RED);
//        g2.drawRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
