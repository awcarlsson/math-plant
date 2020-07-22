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

    public PlantNode(Coordinate coord, double direction, Color color, double branchProb, double maxBranchProb, 
            double leafProb, double maxLeafProb, int height, boolean root) {
        this.coord = coord;
        this.direction = direction;
        this.color = color;
        this.branch = createBranch(branchProb, maxBranchProb, maxLeafProb, height, root);
        this.leaf = createLeaf(leafProb);
    }

    // Some probability for a new branch to spawn
    private Plant createBranch(double branchProb, double maxBranchProb, double maxLeafProb, int height, boolean root) {
        if(Math.random() < branchProb) {
            double branchDir = Plant.getNewDirection(direction, 4);
            if(root) return new Plant(coord, branchDir, 10, 5, height/5, height/2, false, 0, maxBranchProb, 0, 0, color, false, true);
            return new Plant(coord, branchDir, 30, 10, height/5, height/2, true, 0, maxBranchProb, 0.05, maxLeafProb, color, false, false);
        }
        return null;
    }

    public void setBranch(Plant branch){
        this.branch = branch;
    }

    private Leaf createLeaf(double leafProb) {
        if(Math.random() < leafProb){
            return new Leaf(new Coordinate(coord.getX(), coord.getY()), 0, color);
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