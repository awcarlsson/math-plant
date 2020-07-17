package src;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

public class Plant {
    
    private ArrayList<PlantNode> plant;
    private double minStemLength = 1.0;
    private double maxStemLength = 5.0;
    private int deviationFactor = 75;
    private int highestDeviation = 8;
    private Color currColor = new Color(31, 191, 63);

    public Plant() {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(0, 0, Math.PI/2, getGreen()));
    }

    public Plant(int originX, int originY) {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(originX, originY, Math.PI/2, getGreen()));
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    public void updatePlant(){
        PlantNode lastNode = plant.get(plant.size()-1);
        double newDirection = getNewDirection(lastNode.getDirection());
        double lengthOfStem = getStemLength(minStemLength, maxStemLength);
        PlantNode newNode = new PlantNode((int)(lastNode.getX()+Math.cos(newDirection)*lengthOfStem),
        (int)(lastNode.getY()+Math.sin(newDirection)*lengthOfStem), newDirection, getGreen());
        if(deviationFactor > highestDeviation);
            deviationFactor--;
        plant.add(newNode);
    }

    private double getNewDirection(double oldDirection){
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/deviationFactor);
        if(offset > Math.PI/3) offset = Math.PI/3;
        if(offset < -Math.PI/3) offset = -Math.PI/3;
        return oldDirection + offset;
    }

    private double getStemLength(double min, double max){
        return (Math.random() * (max - min + 1) + min);
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