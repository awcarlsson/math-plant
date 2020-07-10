package src;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Graph extends Canvas {

    private static int HEIGHT = 600;
    private static int WIDTH = 1200;
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
    }
}