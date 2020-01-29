import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board extends JLabel implements KeyListener{

    //chicken and cars
    private SpriteChicken chicken;
    private boolean chickenIsDead = false;

    //map
    private Map map;

    //location tracking
    private ArrayList<SpriteCarDown> carsDown;
    private ArrayList<SpriteCarUp> carsUp;
    private LocationGrid grid;

    //image objects
    private BufferedImage bg;
    private BufferedImage finalFrame;
    private Void darkness;
    private int darknessTime;

    //image updating and timing
    private Timer timer, darknessTimer, carTimer;
    private boolean isPressed = false;
    private ScoreBoard scoreBoard;

    public Board(){
        darknessTime = 50;
        initialize(new TreeRockStrip(),  5, 300);
        scoreBoard = new ScoreBoard();
    }

    public void initialize(TreeRockStrip firstStrip, int x, int y){
        //initialize global variables
        bg = loadImage("background");

        grid = new LocationGrid();

        darkness = new Void(grid);

        chicken = new SpriteChicken(x,y,grid,0);             

        //build the map
        map = new Map(firstStrip, grid);
        bg = map.addMapBackground(bg);

        //add vehicle sprites
        carsDown = new ArrayList();
        carsUp = new ArrayList();
        int count = 1;
        for (int i = 0; i < 20; i++){
            if (map.getStrip(i) instanceof RoadStrip){
                int yLoc = (int) (Math.random()*700);
                if (Math.random() < 0.5){                   
                    carsDown.add(new SpriteCarDown(i*50,yLoc,grid,count));
                    count++;
                    if (Math.random() > 0.7){
                        carsDown.add(new SpriteCarDown(i*50,(yLoc+150+(int)(Math.random()*400))%700,grid,count));
                        count++;
                    }
                } else {
                    carsUp.add(new SpriteCarUp(i*50,yLoc,grid,count));
                    count++;
                    if (Math.random() > 0.7){
                        carsUp.add(new SpriteCarUp(i*50,(yLoc+150+(int)(Math.random()*400))%700,grid,count));
                        count++;
                    }
                }
            }
        }     

        //implement event timers
        carTimer = new Timer(80, new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e){
                    for (SpriteCarDown c : carsDown){
                        if (c.carDown(bg)) {
                            endGame();
                        }
                    }
                    for (SpriteCarUp c : carsUp){
                        if (c.carUp(bg)) {
                            endGame();
                        }
                    }
                }
            });
        carTimer.start();

        darknessTimer = new Timer(darknessTime, new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e){
                    darkness.increaseWidth(1);
                    if (grid.checkCollision(chicken.getIndex())) {
                        endGame();
                    }
                }
            });
        darknessTimer.setInitialDelay(4000);

        timer = new Timer(1,new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e){
                    update();
                    if (chicken.getX() > bg.getWidth()-50){
                        TreeRockStrip s = (TreeRockStrip) map.getStrip(19);
                        startTimers(false);
                        darknessTime = darknessTime*2/3+1;
                        initialize(s, 3,chicken.getY());
                        scoreBoard.addScores();
                    }
                }
            });

        startTimers(true);
    }  

    public void startTimers(boolean b){
        if (b){
            darknessTimer.start();
            carTimer.start();
            timer.start();  
        } else {
            darknessTimer.stop();
            carTimer.stop();
            timer.stop(); 
        }
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

    public void update(){   
        finalFrame = carsDown.get(0).appear(bg);
        for (int i = 0; i < carsDown.size(); i++){
            finalFrame = carsDown.get(i).appear(finalFrame);
        }
        finalFrame = chicken.appear(finalFrame);
        for (int i = 0; i < carsUp.size(); i++){
            finalFrame = carsUp.get(i).appear(finalFrame);
        }
        finalFrame = map.addMapObjects(finalFrame);       
        finalFrame = darkness.appear(finalFrame);
        finalFrame = scoreBoard.appear(finalFrame);
        setIcon(new ImageIcon(finalFrame));
    }

    public void endGame(){
        scoreBoard.checkHighScore();
        finalFrame = chicken.die(bg);
        chickenIsDead = true;        
        for (int i = 0; i < carsDown.size(); i++){
            carsDown.get(i).carDown(finalFrame);
            finalFrame = carsDown.get(i).appear(finalFrame);
        }                  
        for (SpriteCarUp c: carsUp){
            c.carUp(finalFrame);
            finalFrame = c.appear(finalFrame);
        }   
        finalFrame = map.addMapObjects(finalFrame);                          
        finalFrame = darkness.appear(finalFrame); 
        finalFrame = scoreBoard.appear(finalFrame);
        finalFrame = chicken.addPlayAgain(finalFrame);
        setIcon(new ImageIcon(finalFrame));          
        startTimers(false);
    }  

    //keylistener methods
    @Override
    public void keyPressed(KeyEvent e){     
        if (!chickenIsDead && !isPressed){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){  
                Timer t = new Timer(20, new ActionListener(){
                            int count = 0;
                            @Override 
                            public void actionPerformed(ActionEvent e){
                                chicken.right(bg);
                                count++;
                                if (count == 2) {
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        });
                t.start();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                Timer t = new Timer(20, new ActionListener(){
                            int count = 0;
                            @Override 
                            public void actionPerformed(ActionEvent e){
                                chicken.left(bg);
                                count++;
                                if (count == 2) {
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        });
                t.start();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                Timer t = new Timer(20, new ActionListener(){
                            int count = 0;
                            @Override 
                            public void actionPerformed(ActionEvent e){
                                chicken.down(bg);
                                count++;
                                if (count == 2) {
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        });
                t.start();
            } else if (e.getKeyCode() == KeyEvent.VK_UP){
                Timer t = new Timer(20, new ActionListener(){
                            int count = 0;
                            @Override 
                            public void actionPerformed(ActionEvent e){
                                chicken.up(bg);
                                count++;
                                if (count == 2) {
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        });
                t.start();
            }
            scoreBoard.update(map, chicken);
        } else if (!isPressed) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                initialize(new TreeRockStrip(), 0, 300);
                scoreBoard.reinitialize();
                chickenIsDead = false;
                darknessTime = 50;
            }
        }
        isPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e){ 
        isPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e){
    }
}
