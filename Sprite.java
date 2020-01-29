import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite
{
    BufferedImage[] animations;
    protected int x;
    protected int xLoc;
    protected int y;
    protected int yLoc;
    private int height;
    protected int heightLoc;
    private int width;
    protected int widthLoc;
    protected int[] states = new int[]{0,0,0,0};
    private LocationGrid grid;
    private int gridIndex;
    protected int currentImageIndex;
    
    public Sprite(int x, int y, String file, LocationGrid g, int index){
        animations = SpriteAnimations.createSprite(file);
        this.x = x;
        xLoc = x;
        this.y = y;
        yLoc = y;
        width = animations[0].getWidth();
        widthLoc = width;
        height = animations[0].getHeight();
        heightLoc = height;
        grid = g;
        gridIndex = index;
        grid.update(this);
        currentImageIndex = 0;
    }
    
    public BufferedImage appear(BufferedImage b){
        final BufferedImage finalImage = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(b,0,0,null);
        g.drawImage(animations[currentImageIndex],x,y, null);
        g.dispose();
        return finalImage;
    }
    
    public void left(BufferedImage b){
        
        states[0] = 0;
        states[2] = 0;
        states[3] = 0;       
        
        if (x > 10) moveX(-10);
        grid.update(this);
        if (grid.checkCollision(gridIndex)) {
            moveX(10);
            grid.update(this);
        }
        
        currentImageIndex = 4+states[1];
     
        states[1] = (states[1]+1)%4;
    }
    
    public void right(BufferedImage b){
        
        states[0] = 0;
        states[1] = 0;
        states[3] = 0;     
        
        if (x < b.getWidth()-getWidth()-10) moveX(10); 
        grid.update(this);
        if (grid.checkCollision(gridIndex)) {
            moveX(-10);
            grid.update(this);
        }
        
        currentImageIndex = 8+states[2];
   
        states[2] = (states[2]+1)%4;
    }
    
    public void down(BufferedImage b){
        
        states[1] = 0;
        states[2] = 0;
        states[3] = 0;  
        
        moveY(10);
        if (y >= b.getHeight()) {
            y = 0;
            yLoc = y;
        }
        grid.update(this);
        if (grid.checkCollision(gridIndex)) {
            moveY(-10);
            if (y < 0) {
                y = b.getHeight()-10;
                yLoc = y;
            }
            grid.update(this);
        }
        
        currentImageIndex = states[0];
        
        states[0] = (states[0]+1)%4;
    }
    
    public void up(BufferedImage b){
        
        states[0] = 0;
        states[1] = 0;
        states[2] = 0;       
        
        moveY(-10);
        if (y < 0) {
            y = b.getHeight();
            yLoc = y;
        }
        grid.update(this);
        if (grid.checkCollision(gridIndex)) {
            moveY(10);
            if (y > b.getHeight()) { 
                y = 10; 
                yLoc = y;
            }
            grid.update(this);
        }   
        
        currentImageIndex = 12+states[3];
        
        states[3] = (states[3]+1)%4;      
    }
    
    public void moveY(int num){
        y += num;
        yLoc += num;
    }
    
    public void moveX(int num){
        x += num;
        xLoc += num;
    }
    
    public int getX(){
        return x;
    }
    
    public int getXLoc(){
        return xLoc;
    }
    
    public int getY(){
        return y;
    }
    
    public int getYLoc(){
        return yLoc;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getHeightLoc(){
        return heightLoc;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getWidthLoc(){
        return widthLoc;
    }
    
    public int getIndex(){
        return gridIndex;
    }
}
