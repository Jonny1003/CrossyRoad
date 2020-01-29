
public class LocationGrid
{
    private int[][] locations;

    private int[][] bgLocations;

    public LocationGrid(){
        //create a large number of spaces just in case
        locations = new int[40][4];  
        //14 by 20 location spaces on the board 
        //plus one more to track the void object location
        bgLocations = new int[14*20+1][4];
    }

    public void update(Sprite s){
        int i = s.getIndex();
        locations[i][0] = s.getXLoc();
        locations[i][1] = s.getYLoc();
        locations[i][2] = s.getWidthLoc()+s.getXLoc();
        locations[i][3] = s.getHeightLoc()+s.getYLoc();
    }

    public void update(Strip r){
        int[] obj = r.getObjects();
        int ind = r.getIndex();
        for (int i = 0; i < obj.length; i++){
            if (obj[i] == 2){
                bgLocations[ind*14+i][0] = ind*50+10;
                bgLocations[ind*14+i][1] = i*50+10;             
                bgLocations[ind*14+i][2] = ind*50+40;
                bgLocations[ind*14+i][3] = i*50+40; 
            } else if (obj[i] == 1){
                bgLocations[ind*14+i][0] = ind*50+10;
                bgLocations[ind*14+i][1] = i*50+10;   
                bgLocations[ind*14+i][2] = bgLocations[ind*14+i][0]+30;
                bgLocations[ind*14+i][3] = bgLocations[ind*14+i][1]+30;  
            } 
        }
    }

    public void update(Void v){
        bgLocations[bgLocations.length-1][0] = 0;
        bgLocations[bgLocations.length-1][1] = 0;
        bgLocations[bgLocations.length-1][2] = v.getWidth();
        bgLocations[bgLocations.length-1][3] = 700;
    }

    public boolean checkCollision(int index, int[][] array){
        int[] coord = locations[index];
        for (int i = 0; i < array.length; i++){
            if (index != i){
                for (int j = 0; j < coord.length; j++){
                    if (coord[0] >= array[i][0] && coord[0] <= array[i][2]
                    && coord[1] >= array[i][1] && coord[1] <= array[i][3]){
                        return true;
                    } else if (coord[2] >= array[i][0] && coord[2] <= array[i][2]
                    && coord[1] >= array[i][1] && coord[1] <= array[i][3]){
                        return true;
                    } else if (coord[0] >= array[i][0] && coord[0] <= array[i][2]
                    && coord[3] >= array[i][1] && coord[3] <= array[i][3]){
                        return true;
                    } else if (coord[2] >= array[i][0] && coord[2] <= array[i][2]
                    && coord[3] >= array[i][1] && coord[3] <= array[i][3]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkCarCrash(int index){
        if (checkCollision(0,locations)) return true;
        return checkCollision(index, locations);
    }

    public boolean checkCollision(int index){
        if (checkCollision(index, bgLocations)) return true;
        else if (checkCollision(index,locations)) return true;
        return checkCollision(0, locations);
    }
    
    public int[][] getBackgroundLocations(){
        return bgLocations;
    }
    
    public int[][] getObjectLocations(){
        return locations;
    }
}
