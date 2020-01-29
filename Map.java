import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
public class Map {
    private TreeRockStrip firstStrip;
    private Strip[] map;
    private LocationGrid grid;
    private BufferedImage finalImage;
    private BufferedImage[] objImages;

    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    
    public Map(LocationGrid g){
        grid = g;

        this.firstStrip = new TreeRockStrip();
        grid.update(firstStrip);

        map = new Strip[WIDTH/50];

        map[0] = firstStrip;
        
        for (int i = 1; i < map.length-1; i++){
            if (randomInt() == 0){
                map[i] = new TreeRockStrip(map[i-1],i);               
            } else {
                map[i] = new RoadStrip(i,grid);
            }
            grid.update(map[i]);
        }
        map[map.length-1] = new TreeRockStrip(map[map.length-2],map.length-1);
        grid.update(map[map.length-1]);

        objImages = new BufferedImage[4];
        objImages[1] = loadImage("tree");
        objImages[2] = loadImage("rock");
        objImages[3] = loadImage("road");
    }

    public Map(TreeRockStrip firstStrip, LocationGrid g){
        grid = g;

        this.firstStrip = firstStrip;
        this.firstStrip.setIndex(0);
        
        grid.update(firstStrip);

        map = new Strip[WIDTH/50];

        map[0] = firstStrip;
        
        for (int i = 1; i < map.length-1; i++){
            if (randomInt() == 0){
                map[i] = new TreeRockStrip(map[i-1],i);               
            } else {
                map[i] = new RoadStrip(i,grid);
            }
            grid.update(map[i]);
        }
        map[map.length-1] = new TreeRockStrip(map[map.length-2],map.length-1);
        grid.update(map[map.length-1]);

        objImages = new BufferedImage[4];
        objImages[1] = loadImage("tree");
        objImages[2] = loadImage("rock");
        objImages[3] = loadImage("road");
    }  

    //returns 0 or 1 based on probability function
    public int randomInt(){
        int pick = (int)(Math.random()*10);
        if (pick < 6) return 1;
        return 0;
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

    public BufferedImage addMapObjects(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(img,0,0,null);
        for (int i = 0; i < map.length; i++){
            out = map[i].addStrip(this,out);
        }
        g.dispose();
        return out;
    }
    
    public BufferedImage addMapBackground(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(img,0,0,null);
        for (int i = 0; i < map.length; i++){
            int[] objGrid = map[i].getObjects(); 
            for (int j = 0; j < objGrid.length; j++){
                if (objGrid[j] == 3){
                    g.drawImage(objImages[3],50*i,50*j,null);                    
                }
            }
        }
        g.dispose();
        return out;
    }

    public BufferedImage getMap(){
        return finalImage;
    }
    
    public Strip getStrip(int i){
        return map[i];
    }

    public BufferedImage[] getObjects(){
        return objImages;
    }
}
