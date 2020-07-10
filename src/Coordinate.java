package src;

public class Coordinate {
    private int x;
    private int y;
    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x+Graph.WIDTH/2;
    }
    public int getY(){
        return -y+Graph.HEIGHT/2;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y=y;
    }
}