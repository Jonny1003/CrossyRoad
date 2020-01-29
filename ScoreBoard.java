import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Font;
public class ScoreBoard {
    
    private int prevScore;
    private int score;
    private int highScore;
    
    public ScoreBoard(){
        prevScore = 0;
        score = 0;
        highScore = 0;
    }
    
    public void reinitialize(){
        prevScore = 0;
        score = 0;
    }
    
    public void update(Map m, SpriteChicken c){
        int count = 0;
        for (int i = 0; i < 20; i++){
            if (i*50 > c.getX()){
                score = count;
                return;
            } else if (m.getStrip(i) instanceof RoadStrip){
                count++;
            }            
        }       
    }
    
    public void addScores(){
        prevScore += score;
    }
    
    public int getHighScore(){
        return highScore;
    }
    
    public void checkHighScore(){
        if (score+prevScore > highScore) highScore = score+prevScore;
    }
    
    public BufferedImage appear(BufferedImage img){
        Graphics2D g = img.createGraphics();
        g.setFont(new Font("FONT",Font.PLAIN,18));
        g.drawString("Roads Crossed: "+(score+prevScore),10,20);
        g.drawString("High Score: "+highScore,10,40);
        g.dispose();
        return img;
    }
}
