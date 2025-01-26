package tile;

import utils.GamePanel;
import utils.PerlinNoise;

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
    private PerlinNoise perlinNoise;
    //public int mapTileNumber[][];
    private long seed = 12345L;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        perlinNoise = new PerlinNoise(seed);

        //mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
        //generateProceduralMap(seed);  // Graine définie pour obtenir toujours la même carte
        getTileImage();
    }

    public void getTileImage() {
        try {
            BufferedImage tileAtlas = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tiles_texture_atlas.png")));

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

    public int getTileNumber(int col, int row) {

        double noiseValue = perlinNoise.noise(col * 0.1, row * 0.1);

        if (noiseValue < -0.2) return 5;
        else if (noiseValue < 0.0) return 2;
        else if (noiseValue < 0.3) return 0;
        else return 1;
    }

//    public void generateProceduralMap(long seed) {
//        PerlinNoise perlinNoise = new PerlinNoise(seed);
//
//        for (int row = 0; row < gp.maxWorldRow; row++) {
//            for (int col = 0; col < gp.maxWorldCol; col++) {
//                double value = perlinNoise.noise(col * 0.1, row * 0.1);
//
//                if (value < -0.2) {
//                    mapTileNumber[col][row] = 5;
//                } else if (value < 0.0) {
//                    mapTileNumber[col][row] = 2;
//                } else if (value < 0.3) {
//                    mapTileNumber[col][row] = 0;
//                } else {
//                    mapTileNumber[col][row] = 1;
//                }
//            }
//        }
//    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            //int tileNum = mapTileNumber[worldCol][worldRow];
            int tileNum = getTileNumber(worldCol, worldRow);

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
