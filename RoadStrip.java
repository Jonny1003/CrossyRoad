import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class RoadStrip extends Strip
{
    
    public RoadStrip(int index, LocationGrid g){
        this.index = index;
        parts = new int[SIZE];
        for (int i = 0; i < parts.length; i++){
            parts[i] = 3;
        }
    }
    
    @Override
    public int getRandomEmptySpace(){
        return (int) (Math.random()*SIZE);
    }
    
    @Override
    public BufferedImage addStrip(Map m, BufferedImage b){
        return b;
    }
}
