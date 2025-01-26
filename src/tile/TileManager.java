package tile;

import utils.GamePanel;
import utils.PerlinNoise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    private PerlinNoise perlinNoise;
    private long seed = 12345L;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        perlinNoise = new PerlinNoise(seed);

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

    public void draw(Graphics2D g2) {

        int startCol = (gp.player.worldX - gp.player.screenX) / gp.tileSize;
        int endCol = (gp.player.worldX + gp.player.screenX) / gp.tileSize;

        int startRow = (gp.player.worldY - gp.player.screenY) / gp.tileSize;
        int endRow = (gp.player.worldY + gp.player.screenY) / gp.tileSize;

        for (int worldCol = startCol; worldCol <= endCol; worldCol++) {
            for (int worldRow = startRow; worldRow <= endRow; worldRow++) {

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
            }
        }
    }
}
