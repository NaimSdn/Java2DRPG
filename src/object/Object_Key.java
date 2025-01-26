package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Object_Key extends ParentObject {

    public Object_Key() {
        name = "Key";

        try {

            BufferedImage objectAtlas = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/objects_texture_atlas.png")));

            int tileWidth = 16;
            int tileHeight = 16;
            int tileCoordsX = 16;
            int tileCoordsY = 16;

            image = objectAtlas.getSubimage(tileCoordsX, tileCoordsY, tileWidth, tileHeight);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
