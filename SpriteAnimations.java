import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteAnimations
{

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(BufferedImage sheet,int xGrid, int yGrid) {
        int TILE_SIZEY = sheet.getWidth()/3;
        int TILE_SIZEX = sheet.getHeight()/4;
        return sheet.getSubimage(yGrid * TILE_SIZEY,xGrid * TILE_SIZEX , TILE_SIZEY, TILE_SIZEX);
    }

    public static BufferedImage[] createSprite(String file){
        BufferedImage f = loadSprite(file); 
        BufferedImage[] out = new BufferedImage[16];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (j%2 == 0){
                    out[4*i+j] = getSprite(f, i, 1);                    
                } else {
                    out[4*i+j] = getSprite(f,i,(j-1)%3);
                }
            }
        }
        return out;
    }
}
