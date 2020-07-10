import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Drawing extends Canvas {
    public static int HEIGHT = 600;
    public static int WIDTH = 1200;

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        Canvas canvas = new Drawing();
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.fillOval(100, 100, 200, 200);
        for (int i = 0; i < WIDTH; i = i + (WIDTH / 100)){
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.drawLine(0,i,WIDTH,i);
        }
    }
}
