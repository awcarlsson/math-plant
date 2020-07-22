package src;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A plant is a list of PlantNodes. All branches/roots are their own plants.
 * 
 * Plant growth can vary dramatically based on arguments.
 */
public class Plant {
    
    // The length of each stem segment (distance between vertices) is a random value between min and max
    private final double MIN_STEM_LENGTH = 2;
    private final double MAX_STEM_LENGTH = 5;

    // ArrayList of PlantNodes, which when connected create a plant
    private ArrayList<PlantNode> plant;

    // A plant will stop growing when it has reached the maximum height, grows downward for too long,
    // or crashes into soil
    private boolean growing = true;

    // If a plant grows this many nodes downwards, it dies
    private int downToDeath;
    private int downCount;

    // The angle of each new stem segment is the angle of the previous segment + some offset.
    // This offset is equal to a random angle chosen normally distributed around PI/devFactor, 
    // (the lower the devFactor the larger the offset).
    // devFactor decrements for each new node added and can not go lower than highestDev.
    private int devFactor;
    private int highestDev;

    // Number of plant nodes is randomly generated based on min and max in arguments
    private int height;

    // If plant left soil (determines root growth and when to start leaf, branch, deviation change)
    private boolean leftSoil;

    // Probability of a node growing a branch (0-1). Increases as plant increases in height
    private double branchProb;
    private double maxBranchProb;
    private double branchDelt = 0.0005;

    // Probability of a node growing a leaf
    private double leafProb;
    private double maxLeafProb;
    private double leafDelt = 0.001;

    // Initial color
    private Color currColor;

    // Whether this is the original branch (generates seed sprite)
    private boolean og;

    // Whether this plant is actually a root (influences root branch behavior)
    private boolean root;

    
    public Plant(Coordinate coord) {
        this(coord, Math.PI/2, 50, 20, 75, 200, false, 0, 0.04, 0, 0.1, new Color(31, 191, 63), true, false);
    }

    public Plant(Coordinate coord, double up, int devFactor, int highestDev, int minHeight, 
            int maxHeight, boolean leftSoil, double branchProb, double maxBranchProb, 
            double leafProb, double maxLeafProb, Color currColor, boolean og, boolean root) {
        this.plant = new ArrayList<PlantNode>();
        this.devFactor = devFactor;
        this.highestDev = highestDev;
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
        this.downToDeath = (int)(((double)(minHeight))*0.8);
        this.leftSoil = leftSoil;
        this.branchProb = branchProb;
        this.maxBranchProb = maxBranchProb;
        this.leafProb = leafProb;
        this.maxLeafProb = maxLeafProb;
        this.currColor = currColor;
        PlantNode pn = new PlantNode(coord, up, getGreen(), branchProb, maxBranchProb, leafProb, maxLeafProb, height, og);
        // If this is the seed node, create the root as a branch
        if(og) pn.setBranch(new Plant(coord, up + Math.PI, 30, 30, 20, 30, false, 0.3, 0.4, 0, 0, currColor, false, true));
        this.plant.add(pn);
        this.og = og;
        this.root = root;
    }

    // Handles the generation of a new PlantNode and the updating of all previously created branches
    public void updatePlant(Background b) {
        updateBranches(b);
        // Plant grows if it is not at he max height and hasn't grown too far downwards
        if(growing && (plant.size() > height || downCount >= downToDeath)) growing = false;
        if(growing) {
            PlantNode pn = plant.get(plant.size()-1);
            double newDir = getNewDirection(pn.getDirection(), devFactor);
            Coordinate newCoord = getNewCoord(pn, newDir, b);
            if(growing) {
                double newBranchProb = branchProb;
                double newLeafProb = leafProb;
                if(leftSoil == false && !root) newBranchProb = newLeafProb = 0;
                PlantNode newNode = new PlantNode(newCoord, newDir, getGreen(), newBranchProb, maxBranchProb - 0.01, newLeafProb, maxLeafProb + 0.01, height, false);
                if(root) newNode = new PlantNode(newCoord, newDir, getGreen(), newBranchProb, maxBranchProb, 0, 0, height, true);
                if(devFactor > highestDev && leftSoil && !root) devFactor--;
                if(devFactor > highestDev && root) devFactor--;
                if(branchProb <= maxBranchProb && leftSoil && !root) branchProb += branchDelt;
                if(branchProb <= maxBranchProb && root) branchProb += branchDelt;
                if(leafProb <= maxLeafProb && leftSoil) leafProb += leafDelt;
                plant.add(newNode);
            }
        }
    }

    // Get the coordinate for the next PlantNode based on the previous PlantNode
    private Coordinate getNewCoord(PlantNode pn, double newDir, Background b){
        double lengthOfStem = getStemLength(MIN_STEM_LENGTH, MAX_STEM_LENGTH);
        double newX = pn.getX()+Math.cos(newDir)*lengthOfStem;
        // Ceiling to bias y upwards
        double newY = Math.ceil(pn.getY()+Math.sin(newDir)*lengthOfStem);
        // Prevents casting to int from being biased towards x = 0
        if (pn.getX() < 0 && newX > pn.getX()) newX = Math.floor(newX);
        if (pn.getX() > 0 && newX < pn.getX()) newX = Math.ceil(newX);
        // If the plant leaves the soil but grows back into it, stop growing
        if(newY < pn.getY() && !root) downCount++;
        if(newY >= Coordinate.displayYtoY(b.getDirtY()) && leftSoil == false) leftSoil = true;
        if(newY < Coordinate.displayYtoY(b.getDirtY()) && leftSoil == true) growing = false;
        return new Coordinate((int)newX, (int)newY);
    }

    // Updates all branches of the plant
    private void updateBranches(Background b){
        for (PlantNode pn : plant){
            Plant branch = pn.getBranch();
            if(branch != null){
                branch.updatePlant(b);
            }
        }
    }

    // Paints all branches and plant
    public void paintPlant(Graphics2D g, Background b) {
        // For every PlantNode in this stem
        for(int i = 0; i < plant.size(); i++){
            // Get the branch (if it exists) from this plant node and paint it
            Plant branch = plant.get(i).getBranch();
            if(branch != null) branch.paintPlant(g, b);
            // Get the leaf (if it exists) from this plant node and paint it
            Leaf leaf = plant.get(i).getLeaf();
            if(leaf != null) leaf.paintLeaf(g, b);
            // Paint own stem
            if(plant.get(i).getY() < Coordinate.displayYtoY(b.getDirtY())) g.setColor(Color.WHITE);
            else {
                getGreen();
                g.setColor(plant.get(i).getColor());
            }
            if(i > 0){
                Stroke stroke = new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                g.setStroke(stroke);
                g.drawLine(plant.get(i).getDisplayX(), plant.get(i).getDisplayY(), plant.get(i-1).getDisplayX(), plant.get(i-1).getDisplayY());
            }
            if (i == 0 && og) {
                g.setColor(new Color(79, 37, 2));
                g.fillOval(plant.get(i).getDisplayX()-10, plant.get(i).getDisplayY()-10, 20, 20);
                g.setColor(Color.WHITE);
                g.fillOval(plant.get(i).getDisplayX()-5, plant.get(i).getDisplayY()-5, 10, 10);
            }
        }
    }

    // Gets a new shade of green by adding some random offset to the current shade
    private Color getGreen(){
        float hOff = (float)((Math.random() * 0.005) - 0.0025), 
        sOff = (float)((Math.random() * 0.005) - 0.0025),
        bOff = (float)((Math.random() * 0.005) - 0.0025);
        float[] currHSB = Color.RGBtoHSB(currColor.getRed(), currColor.getGreen(), currColor.getBlue(), null);
        // HSB ranges
        // hue: [79, 143] 
        // saturation: [0.3, 1]
        // brightness: [0.33, 0.75]
        float h = currHSB[0] + hOff;
        if (h > 0.3972f) h = 0.3972f;
        else if (h < 0.2194f) h = 0.3972f;
        float s = currHSB[1] + sOff;
        if (s > 0.99f) s = 0.99f;
        else if (s < 0.3f) s = 0.3f;
        float b = currHSB[2] + bOff;
        if (b > 0.75f) b = 0.75f;
        else if (b < 0.33f) b = 0.33f;
        currColor = Color.getHSBColor(h, s, b);
        return currColor;
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    // Returns a new direction based on the oldDirection and some random offset
    public static double getNewDirection(double oldDirection, int deviationFactor){
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/deviationFactor);
        if(offset > Math.PI/3) offset = Math.PI/3;
        if(offset < -Math.PI/3) offset = -Math.PI/3;
        return oldDirection + offset;
    }

    // Returns random stem length between min and max
    private static int getStemLength(double min, double max){
        return (int)(Math.random() * (max - min + 1) + min);
    }
}