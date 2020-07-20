package src;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A plant is a list of PlantNodes which are generated based on specified constructor conditions
 */
public class Plant {
    
    private ArrayList<PlantNode> plant;

    private boolean growing = true;

    // The length of each stem segment
    private double minStemLength;
    private double maxStemLength;

    // The angle of each new stem segment is the angle of the previous segment + some offset.
    // This offset is equal to a random angle chosen normally distributed around PI/deviationFactor, 
    // (the lower the deviationFactor the larger the offset).
    // deviationFactor decrements for each new node added and can not go lower than highestDeviation.
    private int deviationFactor;
    private int highestDeviation;

    // Number of plant nodes is random between minHeight and maxHeight
    private int height;

    // If a plant grows this many nodes downwards, it dies
    private int downToDeath;
    private int downCount = 0;

    // If plant left soil
    private boolean leftSoil;

    // Probability of a node growing a branch (0-1). Increases as plant increases in height
    private double branchProb;
    private double maxBranchProb;

    // Probability of a node growing a leaf
    private double leafProb;
    private double maxLeafProb;

    // Initial color
    private Color currColor;

    // Whether this is the original branch (generates seed sprite)
    private boolean og;

    public Plant(int originX, int originY) {
        this(originX, originY, Math.PI/2, 1, 4, 50, 30, 100, 200, 25, false, 0, 0.04, 0, 0.08, new Color(31, 191, 63), true);
    }

    public Plant(int originX, int originY, double up, double minStemLength, double maxStemLength, 
            int deviationFactor, int highestDeviation, int minHeight, int maxHeight, int downToDeath, 
            boolean leftSoil, double branchProb, double maxBranchProb, double leafProb, double maxLeafProb,
            Color currColor, boolean og) {
        this.plant = new ArrayList<PlantNode>();
        this.minStemLength = minStemLength;
        this.maxStemLength = maxStemLength;
        this.deviationFactor = deviationFactor;
        this.highestDeviation = highestDeviation;
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
        this.downToDeath = downToDeath;
        this.leftSoil = leftSoil;
        this.branchProb = branchProb;
        this.maxBranchProb = maxBranchProb;
        this.leafProb = leafProb;
        this.maxLeafProb = maxLeafProb;
        this.currColor = currColor;
        this.plant.add(new PlantNode(originX, originY, up, getGreen(), branchProb, maxBranchProb, leafProb, maxLeafProb, height));
        this.og = og;
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    // Handles the generation of a new PlantNode and the updating of all previously created branches
    public void updatePlant(Background b){
        // Updates all branches of the plant
        for (PlantNode pn : plant){
            Plant branch = pn.getBranch();
            if(branch != null){
                branch.updatePlant(b);
            }
        }
        // Plant grows if it is not at he max height and hasn't grown too far downwards
        if(growing && (plant.size() > height || downCount >= downToDeath)) {
            growing = false;
        }
        if(growing){
            PlantNode lastNode = plant.get(plant.size()-1);
            double newDirection = getNewDirection(lastNode.getDirection(), deviationFactor);
            double lengthOfStem = getStemLength(minStemLength, maxStemLength);
            double newX = lastNode.getX()+Math.cos(newDirection)*lengthOfStem;
            // Ceiling to bias y upwards
            double newY = Math.ceil(lastNode.getY()+Math.sin(newDirection)*lengthOfStem);
            // Prevents casting to int from being biased towards x = 0
            if (lastNode.getX() < 0 && newX > lastNode.getX()) newX = Math.floor(newX);
            if (lastNode.getX() > 0 && newX < lastNode.getX()) newX = Math.ceil(newX);
            // If the plant leaves the soil but grows back into it, stop growing
            if(newY < lastNode.getY()) downCount++;
            if(newY >= Coordinate.displayYtoY(b.getDirtY()) && leftSoil == false) {
                leftSoil = true;
            }
            if(newY < Coordinate.displayYtoY(b.getDirtY()) && leftSoil == true) {
                growing = false;
            }
            if(growing) {
                double newBranchProb = branchProb;
                double newLeafProb = leafProb;
                if(leftSoil == false) newBranchProb = newLeafProb = 0;
                PlantNode newNode = new PlantNode((int)newX, (int)newY, newDirection, getGreen(), newBranchProb, maxBranchProb, newLeafProb, maxLeafProb, height);
                if(deviationFactor > highestDeviation && leftSoil) deviationFactor--;
                if(branchProb <= maxBranchProb && leftSoil) branchProb += 0.0005;
                if(leafProb <= maxLeafProb && leftSoil) leafProb += 0.001;
                plant.add(newNode);
            }
        }
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
}