package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Object_Door extends ParentObject {

    public Object_Door() {
        name = "Door";

        try {

            BufferedImage objectAtlas = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/objects_texture_atlas.png")));

            int tileWidth = 16;
            int tileHeight = 16;
            int tileCoordsX = 0;
            int tileCoordsY = 0;

            image = objectAtlas.getSubimage(tileCoordsX, tileCoordsY, tileWidth, tileHeight);

        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
