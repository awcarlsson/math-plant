package src;

import java.util.ArrayList;

public class Plant {
    
    private boolean isSeed;
    private Coordinate originCoord;
    private ArrayList<Coordinate> plant;

    public Plant() {
        isSeed = true;
        this.originCoord = new Coordinate(0,0);
        this.plant = new ArrayList<Coordinate>();
        this.plant.add(originCoord);
    }

    public Plant(int originX, int originY) {
        isSeed = true;
        this.originCoord = new Coordinate(originX, originY);
        this.plant = new ArrayList<Coordinate>();
        this.plant.add(originCoord);
    }
    public Coordinate getOrigin() {
        return originCoord;
    }
    public ArrayList<Coordinate> getPlant() {
        return plant;
    }
}