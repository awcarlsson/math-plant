package src;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

public class Plant {
    
    private ArrayList<PlantNode> plant;

    // Angle to initially grow
    private double up = Math.PI/2;

    // The length of each stem segment (1 and 4 default)
    private double minStemLength = 1;
    private double maxStemLength = 4;

    // The angle of each new stem segment is the angle of the old segment + some offset.
    // This offset is equal to a randomly chosen normal distribution around PI/deviationFactor, 
    // where the lower the deviationFactor the larger the new angle.
    // deviationFactor decrements for each new node added and can not go lower than highestDeviation.
    private int deviationFactor = 50;
    private int highestDeviation = 30;

    // Number of plant nodes is random between minHeight and maxHeight
    private int minHeight = 150;
    private int maxHeight = 300;
    private int height;

    // If a plant grows this many nodes downwards, it dies
    private int downToDeath = minHeight/4;
    private int downCount = 0;

    // Probability of a node growing a branch. Increases as plant increases in height
    private int branchProb = 0;

    // Initial color
    private Color currColor = new Color(31, 191, 63);

    public Plant() {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(0, 0, up, getGreen()));
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
    }

    public Plant(int originX, int originY) {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(originX, originY, up, getGreen()));
        this.height = (int)(Math.random() * (maxHeight - minHeight + 1) + minHeight);
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    public void updatePlant(){
        boolean growing = checkIfGrowing();
        if(growing){
            PlantNode lastNode = plant.get(plant.size()-1);
            double newDirection = getNewDirection(lastNode.getDirection());
            double lengthOfStem = getStemLength(minStemLength, maxStemLength);
            double newX = lastNode.getX()+Math.cos(newDirection)*lengthOfStem;
            // Ceiling to bias y upwards
            double newY = Math.ceil(lastNode.getY()+Math.sin(newDirection)*lengthOfStem);
            // Prevents casting to int from being biased towards x = 0
            if (lastNode.getX() < 0 && newX > lastNode.getX()) newX = Math.floor(newX);
            if (lastNode.getX() > 0 && newX < lastNode.getX()) newX = Math.ceil(newX);
            if(newY < lastNode.getY()) downCount++; 
            // else downCount = 0;
            PlantNode newNode = new PlantNode((int)newX, (int)newY, newDirection, getGreen());
            if(deviationFactor > highestDeviation)
                deviationFactor--;
            plant.add(newNode);
        }
    }

    private boolean checkIfGrowing(){
        if(plant.size() > height || downCount >= downToDeath) {
            return false;
        }
        return true; 
    }

    private double getNewDirection(double oldDirection){
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/deviationFactor);
        if(offset > Math.PI/3) offset = Math.PI/3;
        if(offset < -Math.PI/3) offset = -Math.PI/3;
        return oldDirection + offset;
    }

    private int getStemLength(double min, double max){
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public void paintPlant(Graphics2D g) {
        for(int i = 0; i < plant.size(); i++){
            if(plant.get(i).getY() < 0)
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
            if (i == 0) {
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