import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class SpriteChicken extends Sprite


{
    
    LocationGrid grid;
    
    public SpriteChicken(int x, int y, LocationGrid g, int index){
        super(x,y,"chicken",g,index);
        grid = g;
    }
    
    public BufferedImage die(BufferedImage img){
        final BufferedImage finalImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        final BufferedImage blood = SpriteAnimations.loadSprite("blood");
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(img,0,0,null);
        g.drawImage(blood,getX(),getY(), null);
        g.drawImage(blood,getX()-10,getY()-10, null);
        g.drawImage(blood,getX()+10,getY()+10, null);
        g.dispose();
        return finalImage;
    }
    
     public BufferedImage addPlayAgain(BufferedImage img){
        final BufferedImage again = SpriteAnimations.loadSprite("PlayAgain");
        Graphics2D g = img.createGraphics();
        g.drawImage(again,425,300, null);;
        g.dispose();
        return img;
    }
}
