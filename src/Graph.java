package src;

import java.awt.Canvas;
import java.awt.Color;
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
    public void setup(){
        JFrame frame = new JFrame("Graph");
        Canvas canvas = new Graph();
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, WIDTH, HEIGHT/2);
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT/2, WIDTH, HEIGHT);


        for (int i = 0; i < WIDTH; i = i + (WIDTH / (100 * ASPECT_RATIO))){
            g.setColor(Color.gray);
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.setColor(Color.gray);
            g.drawLine(0,i,WIDTH,i);
        }

        Function f = new Function("Hello");
        ArrayList<Coordinate> points = f.createFunctionTree(500);

        for(int i = 0; i < points.size()-2; i++){
            g.fillOval(points.get(i).getX(),points.get(i).getY(),1,1);
            g.setColor(Color.black);
            g.drawLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
        }


    }
}