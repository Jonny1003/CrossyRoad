import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class SpriteCarDown extends Sprite {
    
    LocationGrid grid;
    private static final int xDiff = 35;
    private static final int yDiff = 0;
    
    public SpriteCarDown(int x, int y, LocationGrid g, int index){
        super(x-xDiff,y-yDiff,"car3",g,index);
        xLoc = x+5;  
        yLoc = y;
        widthLoc = 35;
        heightLoc = 40;
        grid = g;
        g.update(this);
    }
    
    public boolean carDown(BufferedImage b){
        
        moveY(10);
        if (y >= b.getHeight()) {
            y = -heightLoc;
            yLoc = y+yDiff;
        }
        grid.update(this);
        if (grid.checkCarCrash(getIndex())) {          
            moveY(-10);
            if (y < 0) {
                y = b.getHeight();
                yLoc = y+yDiff;
            }
            grid.update(this);
            return true;
        }
        
        currentImageIndex = states[0];
        
        states[0] = (states[0]+1)%4;
        return false;
    }
    
    public boolean carUp(BufferedImage b){     
        
        moveY(-10);
        if (y < 0-heightLoc) {
            y = b.getHeight();
            yLoc = y+yDiff;
        }
        grid.update(this);
        if (grid.checkCarCrash(getIndex())) {           
            moveY(10);
            if (y > b.getHeight()) {
                y = -heightLoc;
                yLoc = y+yDiff;
            }
            grid.update(this);
            return true;
        }    
        
        currentImageIndex = 12+states[3];
        
        states[3] = (states[3]+1)%4;   
        return false;
    }  
}
