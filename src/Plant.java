package src;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

public class Plant {
    
    private ArrayList<PlantNode> plant;

    // Angle to initially grow
    private double up;

    // The length of each stem segment (1 and 4 default)
    private double minStemLength;
    private double maxStemLength;

    // The angle of each new stem segment is the angle of the old segment + some offset.
    // This offset is equal to a randomly chosen normal distribution around PI/deviationFactor, 
    // where the lower the deviationFactor the larger the new angle.
    // deviationFactor decrements for each new node added and can not go lower than highestDeviation.
    private int deviationFactor;
    private int highestDeviation;

    // Number of plant nodes is random between minHeight and maxHeight
    private int minHeight;
    private int maxHeight;
    private int height;

    // If a plant grows this many nodes downwards, it dies
    private int downToDeath;
    private int downCount = 0;

    // If plant left soil
    private boolean leftSoil = false;

    // Probability of a node growing a branch (0-1). Increases as plant increases in height
    private double branchProb;
    private double maxBranchProb;

    // Initial color
    private Color currColor;

    // Whether this is the original branch
    private boolean og;

    public Plant(int originX, int originY) {
        this.plant = new ArrayList<PlantNode>();
        this.up = Math.PI/2;
        this.minStemLength = 1;
        this.maxStemLength = 4;
        this.deviationFactor = 50;
        this.highestDeviation = 30;
        this.minHeight = 150;
        this.maxHeight = 300;
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
        this.downToDeath = minHeight/4;
        this.branchProb = 0.00;
        this.maxBranchProb = 0.04;
        this.currColor = new Color(31, 191, 63);
        this.plant.add(new PlantNode(originX, originY, up, getGreen(), branchProb, maxBranchProb));
        this.og = true;
    }

    public Plant(int originX, int originY, double up, double minStemLength, double maxStemLength, 
    int deviationFactor, int highestDeviation, int minHeight, int maxHeight, 
    int downToDeath, double branchProb, double maxBranchProb, Color currColor, boolean og) {
        this.plant = new ArrayList<PlantNode>();
        this.up = up;
        this.minStemLength = minStemLength;
        this.maxStemLength = maxStemLength;
        this.deviationFactor = deviationFactor;
        this.highestDeviation = highestDeviation;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
        this.downToDeath = downToDeath;
        this.branchProb = branchProb;
        this.maxBranchProb = maxBranchProb;
        this.currColor = currColor;
        this.plant.add(new PlantNode(originX, originY, up, getGreen(), branchProb, maxBranchProb));
        this.og = false;
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    public void updatePlant(Background b){

        boolean growing = true;

        if(plant.size() > height || downCount >= downToDeath) {
            growing = false;
        }

        if(growing){

            for (PlantNode pn : plant){
                Plant branch = pn.getBranch();
                if(branch != null){
                    branch.updatePlant(b);
                }
            }

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
                if(leftSoil == false) newBranchProb = 0;
                PlantNode newNode = new PlantNode((int)newX, (int)newY, newDirection, getGreen(), newBranchProb, maxBranchProb);
                if(deviationFactor > highestDeviation)
                    deviationFactor--;
                if(branchProb <= maxBranchProb) branchProb += 0.0005;
                plant.add(newNode);
                System.out.println(plant.size());            
            }
        }
    }

    public static double getNewDirection(double oldDirection, int deviationFactor){
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/deviationFactor);
        if(offset > Math.PI/3) offset = Math.PI/3;
        if(offset < -Math.PI/3) offset = -Math.PI/3;
        return oldDirection + offset;
    }

    private static int getStemLength(double min, double max){
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public void paintPlant(Graphics2D g, Background b) {

        // For every PlantNode in this stem
        for(int i = 0; i < plant.size(); i++){
            // Get the branch (if it exists) from this plant node and paint it
            Plant branch = plant.get(i).getBranch();
            if(branch != null){
                branch.paintPlant(g, b);
            }
            // paint own stem
            if(plant.get(i).getY() < Coordinate.displayYtoY(b.getDirtY()))
                g.setColor(Color.WHITE);
            else {
                getGreen();
                g.setColor(plant.get(i).getColor());
            }
            if(i > 0){
                Stroke stroke = new BasicStroke(6f);
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

    private Color getGreen(){
        float hOff = (float)((Math.random() * 0.005) - 0.0025);
        float sOff = (float)((Math.random() * 0.005) - 0.0025);
        float bOff = (float)((Math.random() * 0.005) - 0.0025);
        float[] currHSB = Color.RGBtoHSB(currColor.getRed(), currColor.getGreen(), currColor.getBlue(), null);
        // HSB ranges
        // hue: [79, 143] 
        // saturation: [0.3, 1]
        // brightness: [0.33, 0.75]
        float h = currHSB[0] + hOff;
        if (h > 0.3972f)
            h = 0.3972f;
        else if (h < 0.2194f)
            h = 0.3972f;
        float s = currHSB[1] + sOff;
        if (s > 0.99f)
            s = 0.99f;
        else if (s < 0.3f)
            s = 0.3f;
        float b = currHSB[2] + bOff;
        if (b > 0.75f)
            b = 0.75f;
        else if (b < 0.33f)
            b = 0.33f;
        currColor = Color.getHSBColor(h, s, b);
        return currColor;
    }
}