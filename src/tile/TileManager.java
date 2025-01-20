package tile;

import utils.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map02.txt");
    }

    public void getTileImage() {
        try {
            BufferedImage tileAtlas = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/texture_atlas.png")));

            int tileWidth = 16;
            int tileHeight = 16;

            int[][] tileCoords = {
                    {0, 0},  // Herbe
                    {16, 0}, // Terre
                    {32, 0}, // Sable
                    {0, 16}, // Arbre
                    {0, 32}, // Mur
                    {0, 48}  // Eau
            };

            boolean[] collisionFlags = {
                    false, // Herbe (0)
                    false, // Terre (1)
                    false, // Sable (2)
                    true, // Arbre (3)
                    true, // Mur (4)
                    true // Eau (5)
            };

            for (int i = 0; i < tileCoords.length; i++) {
                tile[i] = new Tile();
                tile[i].image = tileAtlas.getSubimage(tileCoords[i][0], tileCoords[i][1], tileWidth, tileHeight);
                tile[i].collision = collisionFlags[i];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }

                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
