package src;

public class PlantNode {
    
    private Coordinate coord;
    private double direction;

    public PlantNode(int x, int y, double direction) {
        coord = new Coordinate(x, y);
        this.direction = direction;
    }

    public int getX(){
        return coord.getX();
    }

    public int getY(){
        return coord.getY();
    }

    public int getDisplayX(){
        return coord.getDisplayX();
    }

    public int getDisplayY(){
        return coord.getDisplayY();
    }

    public double getDirection(){
        return direction;
    }
}