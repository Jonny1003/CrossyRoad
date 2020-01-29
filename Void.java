import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class Void
{
    private BufferedImage black;
    private LocationGrid grid;
    private int width;
    
    public Void(LocationGrid g)
    {
        grid = g;
        grid.update(this);
        width = 0;
    }
    
    public int getWidth(){
        return width;
    }
    
    public void increaseWidth(int num){
        width+=num;
    }
    
    public BufferedImage appear(BufferedImage img){
        grid.update(this);
        final BufferedImage finalImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(img,0,0,null);
        g.drawImage(new BufferedImage(width+1,700,BufferedImage.TYPE_INT_RGB),0,0,null);
        g.dispose();
        return finalImage;
    }
}
