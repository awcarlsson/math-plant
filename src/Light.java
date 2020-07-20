package src;

import java.awt.*;

public class Light {
    
    private Coordinate coord;
    private double originAngle;
    private int distanceFromOrigin;
    private int radius;

    public Light(double originAngle, int distanceFromOrigin, int radius){
        this.coord = new Coordinate((int)Math.cos(originAngle)*distanceFromOrigin, (int)Math.sin(originAngle)*distanceFromOrigin);
        this.originAngle = originAngle;
        this.distanceFromOrigin = distanceFromOrigin;
        this.radius = radius;
    }

    public void paintLight(Graphics2D g){
        g.setColor(Color.YELLOW);
        coord.setX((int)Math.cos(originAngle)*distanceFromOrigin);
        coord.setY((int)Math.sin(originAngle)*distanceFromOrigin);
        g.fillOval(coord.getDisplayX()-radius/2, coord.getDisplayY()-radius/2, radius, radius);
    }
}