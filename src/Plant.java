package src;

import java.util.ArrayList;
import java.util.Random;

public class Plant {
    
    private ArrayList<PlantNode> plant;
    private double minStemLength = 30.0;
    private double maxStemLength = 50.0;

    public Plant() {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(0, 0, Math.PI/2));
    }

    public Plant(int originX, int originY) {
        this.plant = new ArrayList<PlantNode>();
        this.plant.add(new PlantNode(originX, originY, Math.PI/2));
    }

    public ArrayList<PlantNode> getPlant() {
        return plant;
    }

    public void updatePlant(){
        PlantNode lastNode = plant.get(plant.size()-1);
        double newDirection = getNewDirection(lastNode.getDirection());
        double lengthOfStem = getStemLength(minStemLength, maxStemLength);
        System.out.println(lengthOfStem);
        System.out.println(Math.cos(newDirection)*lengthOfStem);
        PlantNode newNode = new PlantNode((int)(lastNode.getX()+Math.cos(newDirection)*lengthOfStem),
        (int)(lastNode.getY()+Math.sin(newDirection)*lengthOfStem), newDirection);
        System.out.println(newNode.getX() + "," + newNode.getY());
        plant.add(newNode);
    }

    private double getNewDirection(double oldDirection){
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/12);
        if(offset > Math.PI/2) offset = Math.PI/2;
        if(offset < -Math.PI/2) offset = -Math.PI/2;
        return oldDirection + offset;
    }

    private double getStemLength(double min, double max){
        return (Math.random() * (max - min + 1) + min);
    }
}