package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Leaf {
    
    private Coordinate coord;
    private int width;
    private int height;
    private double direction;
    private Color color;

    public Leaf(Coordinate coord, int width, int height, double direction, Color color) {
        this.coord = coord;
        this.width = width;
        this.height = height;
        this.direction = getNewDirection();
        this.color = color;
    }

    public void paintLeaf(Graphics2D g, Background b) {
        if(coord.getDisplayY() < b.getDirtY()) {
            AffineTransform old = g.getTransform();
            g.translate(coord.getDisplayX(), coord.getDisplayY()+height/2);
            g.rotate(direction);
            g.translate(-coord.getDisplayX(), -(coord.getDisplayY()+height/2));
            g.setColor(color);
            g.fillOval(coord.getDisplayX(), coord.getDisplayY(), width, height);
            g.setTransform(old);
        }
    }

    private double getNewDirection() {
        /**
        Random r = new Random();
        double offset = r.nextGaussian() * (Math.PI/deviationFactor);
        if(offset > Math.PI/2) offset = Math.PI/2;
        if(offset < -Math.PI/2) offset = -Math.PI/2;
        return oldDirection + offset;
        */
        return Math.random() * 2 * Math.PI;
    }
}