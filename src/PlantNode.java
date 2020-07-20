package src;

import java.awt.Color;

/**
 * A PlantNode is a vertex of the plant. Potential for a new Plant (i.e. a branch) to spawn from a node
 */
public class PlantNode {
    
    private Coordinate coord;
    private double direction;
    private Color color;
    private Plant branch;
    private Leaf leaf;

    public PlantNode(int x, int y, double direction, Color color, double branchProb, double maxBranchProb, 
            double leafProb, double maxLeafProb, int height) {
        this.coord = new Coordinate(x, y);
        this.direction = direction;
        this.color = color;
        this.branch = createBranch(branchProb, maxBranchProb, maxLeafProb, height);
        this.leaf = createLeaf(leafProb);
    }

    // Some probability for a new branch to spawn
    private Plant createBranch(double branchProb, double maxBranchProb, double maxLeafProb, int height) {
        if(Math.random() < branchProb){
            double branchDir = Plant.getNewDirection(direction, 4);
            // TODO: replace x and y with coord
            return new Plant(coord.getX(), coord.getY(), branchDir, 1, 4, 30, 10, height/5, height/2, 30, true, 0, maxBranchProb - 0.01, 0.05, maxLeafProb + 0.01, color, false);
        }
        return null;
    }

    private Leaf createLeaf(double leafProb) {
        if(Math.random() < leafProb){
            return new Leaf(new Coordinate(coord.getX(), coord.getY()), 30, 10, 0, color);
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

    public Leaf getLeaf(){
        return leaf;
    }

    public Color getColor(){
        return color;
    }
}