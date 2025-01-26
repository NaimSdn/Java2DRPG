package utils;

import entity.Entity;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

//        int entityLeftCol = entityLeftWorldX / gp.tileSize;
//        int entityRightCol = entityRightWorldX / gp.tileSize;
//        int entityTopRow = entityTopWorldY / gp.tileSize;
//        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int entityLeftCol = (int) Math.floor((double) entityLeftWorldX / gp.tileSize);
        int entityRightCol = (int) Math.floor((double) entityRightWorldX / gp.tileSize);
        int entityTopRow = (int) Math.floor((double) entityTopWorldY / gp.tileSize);
        int entityBottomRow = (int) Math.floor((double) entityBottomWorldY / gp.tileSize);

        int tileNum1 = 0, tileNum2 = 0;

        switch (entity.direction) {
            case "up":
                entityTopRow = (int) Math.floor((double) (entityTopWorldY - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManager.getTileNumber(entityLeftCol, entityTopRow);
                tileNum2 = gp.tileManager.getTileNumber(entityRightCol, entityTopRow);
                break;
            case "down":
                entityBottomRow = (int) Math.floor((double) (entityBottomWorldY + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManager.getTileNumber(entityLeftCol, entityBottomRow);
                tileNum2 = gp.tileManager.getTileNumber(entityRightCol, entityBottomRow);
                break;
            case "left":
                entityLeftCol = (int) Math.floor((double) (entityLeftWorldX - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManager.getTileNumber(entityLeftCol, entityTopRow);
                tileNum2 = gp.tileManager.getTileNumber(entityLeftCol, entityBottomRow);
                break;
            case "right":
                entityRightCol = (int) Math.floor((double) (entityRightWorldX + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManager.getTileNumber(entityRightCol, entityTopRow);
                tileNum2 = gp.tileManager.getTileNumber(entityRightCol, entityBottomRow);
                break;
        }

        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            System.out.println("Collision detected at: (" + entityLeftCol + "," + entityTopRow + ") and (" + entityRightCol + "," + entityBottomRow + ")");
            System.out.println("Tile numbers: " + tileNum1 + ", " + tileNum2);
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.object.length; i++) {

            if (gp.object[i] != null) {

                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                gp.object[i].collisionArea.x = gp.object[i].worldX + gp.object[i].collisionArea.x;
                gp.object[i].collisionArea.y = gp.object[i].worldY + gp.object[i].collisionArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(gp.object[i].collisionArea)) {
                            if (gp.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(gp.object[i].collisionArea)) {
                            if (gp.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(gp.object[i].collisionArea)) {
                            if (gp.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(gp.object[i].collisionArea)) {
                            if (gp.object[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                gp.object[i].collisionArea.x = gp.object[i].collisionAreaDefaultX;
                gp.object[i].collisionArea.y = gp.object[i].collisionAreaDefaultY;
            }
        }

        return index;
    }
}
