package src;

import java.awt.Color;

public class PlantNode {
    
    private Coordinate coord;
    private double direction;
    private Color color;
    private Plant branch;

    public PlantNode(int x, int y, double direction, Color color, double branchProb, double maxBranchProb) {
        this.coord = new Coordinate(x, y);
        this.direction = direction;
        this.color = color;
        this.branch = createBranch(branchProb, maxBranchProb);
    }

    private Plant createBranch(double branchProb, double maxBranchProb) {
        if(Math.random() < branchProb){
            double branchDir = Plant.getNewDirection(direction, 4);
            return new Plant(coord.getX(), coord.getY(), branchDir, 1, 4, 30, 10, 50, 200, 100, 0, maxBranchProb - 0.01, color, false);
        }
        return null;
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

    public Plant getBranch(){
        return branch;
    }

    public Color getColor(){
        return color;
    }
}