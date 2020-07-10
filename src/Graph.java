package src;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;

public class Graph extends Canvas {

    public static int HEIGHT = 600;
    public static int WIDTH = 1200;
    private static int ASPECT_RATIO = WIDTH/HEIGHT;

    public Graph() {
    }
    
    // 
    public static void setup(){
        JFrame frame = new JFrame("Graph");
        Canvas canvas = new Graph();
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        for (int i = 0; i < WIDTH; i = i + (WIDTH / (100 * ASPECT_RATIO))){
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.drawLine(0,i,WIDTH,i);
        }

        Function f = new Function("Hello");
        ArrayList<Coordinate> points = f.createFunctionTree(200);

        for(Coordinate point : points){
            g.drawOval(point.getX(),point.getY(),10,10);
        }


    }
}