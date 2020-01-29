import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
public class Window extends JFrame {
    
    private Board board;
    
    public Window(){
        super("CROSSY ROADS");
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(1,1));
        
        board = new Board();

        addKeyListener(board);
        add(board);
        
        setMinimumSize(new Dimension(1000,700));

        pack();
        setFocusable(true);
        setVisible(true);       
    }

    public static void main(String[] args){
        new Window();
    }   

}
