package src;

import java.awt.Color;
import java.util.ArrayList;

public class PlantNode {
    
    private Coordinate coord;
    private double direction;
    private Color color;
    private ArrayList<PlantNode> branch;

    public PlantNode(int x, int y, double direction, Color color) {
        this.coord = new Coordinate(x, y);
        this.direction = direction;
        this.color = color;
        this.branch = null;
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

    public ArrayList<PlantNode> getBranch(){
        return branch;
    }

    public Color getColor(){
        return color;
    }
}