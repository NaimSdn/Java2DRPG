package utils;

import object.Object_Chest;
import object.Object_Door;
import object.Object_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.object[0] = new Object_Key();
        gp.object[0].worldX = 5 * gp.tileSize;
        gp.object[0].worldY = 3 * gp.tileSize;

        gp.object[1] = new Object_Door();
        gp.object[1].worldX = 6 * gp.tileSize;
        gp.object[1].worldY = 3 * gp.tileSize;

        gp.object[2] = new Object_Chest();
        gp.object[2].worldX = 7 * gp.tileSize;
        gp.object[2].worldY = 3 * gp.tileSize;
    }

}
