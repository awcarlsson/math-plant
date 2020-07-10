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
    public static void setup(){
        JFrame frame = new JFrame("Scene");
        Canvas canvas = new Scene();
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.fillOval(100, 100, 200, 200);
        for (int i = 0; i < WIDTH; i = i + (WIDTH / (100 * ASPECT_RATIO))){
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.drawLine(0,i,WIDTH,i);
        }

        




    }
}
