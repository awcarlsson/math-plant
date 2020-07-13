package src;

public class Coordinate {

    private int x;
    private int y;

    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static int translateX(int x){
        return x+Frame.WIDTH/2;
    }

    public static int translateY(int y){
        return -y+Frame.HEIGHT/2;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getDisplayX(){
        return x+Frame.WIDTH/2;
    }

    public int getDisplayY(){
        return -y+Frame.HEIGHT/2;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y=y;
    }
}