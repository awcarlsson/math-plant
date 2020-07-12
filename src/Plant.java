package src;

import java.util.ArrayList;

public class Plant {
    
    private boolean isSeed;
    private Coordinate originCoord;
    private ArrayList<Coordinate> plant;

    public Plant() {
        System.out.println("initialized");
        isSeed = true;
        this.originCoord = new Coordinate(0,0);
        this.plant.add(originCoord);
    }

    public Plant(int originX, int originY) {
        isSeed = true;
        this.originCoord = new Coordinate(originX, originY);
        this.plant.add(originCoord);
    }
    public Coordinate getPlantOrigin() {
        return originCoord;
    }
    public ArrayList<Coordinate> getPlantCoords() {
        return plant;
    }
}