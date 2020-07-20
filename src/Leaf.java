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
    private double scale;

    public Leaf(Coordinate coord, int width, int height, double direction, Color color) {
        this.coord = coord;
        this.width = width;
        this.height = height;
        this.direction = getNewDirection(direction);
        this.color = color;
        this.scale = 0.1;
    }

    public void paintLeaf(Graphics2D g, Background b) {
        AffineTransform old = g.getTransform();
        g.translate(coord.getDisplayX(), coord.getDisplayY());
        g.rotate(direction);
        g.translate(-coord.getDisplayX(), -(coord.getDisplayY()));
        g.setColor(color);
        g.fillOval(coord.getDisplayX(), coord.getDisplayY()-(int)(height*scale), (int)(width*scale), (int)(height*scale));
        if(scale <= 1) scale += 0.1;
        g.setTransform(old);
    }

    private double getNewDirection(double oldDirection) {
        //Random r = new Random();
        //double offset = r.nextGaussian() * (Math.PI/4);
        //return oldDirection + offset;
        return Math.random() * 2 * Math.PI;
    }
}