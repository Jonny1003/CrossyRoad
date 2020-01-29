import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

public abstract class Strip
{
    public final int SIZE = 14;
    public int index;
    public int[] parts;
    
    public static BufferedImage loadImage(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }
    
    public int[] getObjects(){
        return parts;
    }

    //integer value 0-19 designating x-axis location
    public int getIndex(){
        return index;
    }
    
    public void setIndex(int i){
        index = i;
    }
    
    public BufferedImage addStrip(Map m, BufferedImage frame){
        final BufferedImage finalImage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(frame,0,0,null);

        for (int i = 0; i < parts.length; i++){
            if (parts[i] == 1){
                g.drawImage(m.getObjects()[parts[i]],50*index,50*i-50,null);
            } else {
                g.drawImage(m.getObjects()[parts[i]],50*index,50*i,null);
            }
        }

        g.dispose();
        return finalImage;
    }
    
    public int getRandomEmptySpace(){
        int r = (int) (Math.random()*SIZE);
        for (int i = r; i < r+SIZE; i++){
            if (parts[i%SIZE] == 0){
                return i%SIZE;
            }
        }
        return -1;
    }
    
}
