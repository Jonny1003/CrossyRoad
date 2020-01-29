import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateBackground
{

    private static final int FINAL_SIZE_X = 700;
    private static final int FINAL_SIZE_Y = 1000;

    public static void main(String[] args){
        createBackground();
    }

    public static BufferedImage loadImage(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static void createBackground() {
        BufferedImage tile = loadImage("grass");
        final BufferedImage finalImage = new BufferedImage(FINAL_SIZE_Y, FINAL_SIZE_X,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();

        int numX = FINAL_SIZE_X / tile.getWidth();
        int numY = FINAL_SIZE_Y / tile.getHeight();
        for (int x = 0; x < numX; x++){
            for (int y = 0; y < numY; y++){
                g.drawImage(tile, y*tile.getHeight(), x*tile.getWidth(), null);            
            }
        }
        g.dispose();
        
        File background = new File("background.png");
        try {
            ImageIO.write(finalImage, "PNG", background);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
