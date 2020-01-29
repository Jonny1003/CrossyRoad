import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
public class TreeRockStrip extends Strip{
    // 0 = no image
    // 1 = tree
    // 2 = rock

    public TreeRockStrip(Strip prevStrip, int index){
        parts = new int[SIZE];
        this.index = index;
        int num = prevStrip.getRandomEmptySpace();
        parts[num] = 0;

        parts[0] = 1;
        parts[SIZE-1] = 1;

        for (int i = 1; i < SIZE-1; i++){
            if (i != num){
                int pick = randomObject();
                parts[i] = randomObject();
            }
        }    
    }

    public TreeRockStrip(){
        parts = new int[SIZE];
        this.index = 0;

        parts[0] = 1;
        parts[SIZE-1] = 1;

        for (int i = 1; i < SIZE-1; i++){
            int pick = randomObject();              
            parts[i] = pick;
            if (i == 6) parts[i] = 0;
        }    
    }

    public static int randomObject(){
        int r = (int) (Math.random()*15);
        if (r < 3){
            return 1;
        } else if(r < 4){
            return 2;
        } 
        return 0;       
    }
    

    public int[] getObjects(){
        return parts;
    }

    //integer value 0-19 designating x-axis location
    public int getIndex(){
        return index;
    }
}
