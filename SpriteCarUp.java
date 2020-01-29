
public class SpriteCarUp extends SpriteCarDown{
    public SpriteCarUp(int x, int y, LocationGrid g, int index){
        super(x,y,g,index);
        xLoc = x;   
        yLoc = y+35;
        widthLoc = 40;
        heightLoc = 40;
    }
    
}
