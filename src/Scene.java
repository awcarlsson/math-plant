package src;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;
/**
 * Creates the whole scene visible to the user
 */
public class Scene extends Canvas {

    private static int HEIGHT = 600;
    private static int WIDTH = 1200;
    private static int ASPECT_RATIO = WIDTH/HEIGHT;

    public Scene() {
    }
    
    // 
    public void setup(){
        JFrame frame = new JFrame("Scene");
        Canvas canvas = new Scene();
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.fillOval(100, 100, 200, 200);
        Plant p = new Plant(0, 0);
        //ArrayList<Coordinate> p_coords = p.getPlantCoords();
        //System.out.println(p.getPlantOrigin().getX());
        /* for (Coordinate coord : p_coords){
            System.out.println(coord.getX());
            g.fillOval(coord.getX(),coord.getY(),100,100);
        } */
        g.fillOval(100, 100, 200, 200);
        for (int i = 0; i < WIDTH; i = i + (WIDTH / (100 * ASPECT_RATIO))){
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.drawLine(0,i,WIDTH,i);
        } 
    }
}