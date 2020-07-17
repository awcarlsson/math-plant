package src;

import java.awt.Color;

public class PlantNode {
    
    private Coordinate coord;
    private double direction;
    private Color color;

    public PlantNode(int x, int y, double direction, Color color) {
        coord = new Coordinate(x, y);
        this.direction = direction;
        this.color = color;
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

    public Color getColor(){
        return color;
    }
}