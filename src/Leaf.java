package src;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Leaf {
    
    private final int WIDTH = 30;
    private final int HEIGHT = 10;

    private Coordinate coord;
    private double direction;
    private Color color;
    private double scale;

    public Leaf(Coordinate coord, double direction, Color color) {
        this.coord = coord;
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
        g.fillOval(coord.getDisplayX(), coord.getDisplayY()-(int)(HEIGHT*scale), (int)(WIDTH*scale), (int)(HEIGHT*scale));
        if(scale <= 1) scale += 0.1;
        g.setTransform(old);
    }

    private double getNewDirection(double oldDirection) {
        // Maybe use oldDirection, random for now!
        return Math.random() * 2 * Math.PI;
    }
}