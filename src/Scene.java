package src;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Graphics;

import javax.swing.JFrame;

import java.util.ArrayList;

/**
 * Creates the whole scene visible to the user
 */
public class Scene extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final double ASPECT_RATIO = 16D/9D;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = (int)((double)WIDTH/ASPECT_RATIO);
    public static final int SCALE = 1;
    public static final String NAME = "Plant Game";

    private JFrame frame;

    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public Scene(){

        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public synchronized void start(){
        System.out.println(ASPECT_RATIO);
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop(){
        running = false;
    }

    public void run(){
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;

            while(delta >= 1){
                ticks++;
                tick();
                delta--;
                shouldRender = true;
            }

            if(shouldRender){
                frames++;
                render();
            }

            if(System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                System.out.println(frames + ", " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(){
        tickCount++;
    }

    static int ovalX = 0;
    static int ovalY = 0;
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.fillOval(Coordinate.translateX(ovalX), Coordinate.translateY(ovalY), 50, 50);
        ovalX++;
        if(ovalX > WIDTH/2 + 50)
            ovalX = -WIDTH/2 - 50;
        // Add all objects in scene here
        
        /* for (int i = 0; i < WIDTH; i = i + (int)((double)WIDTH / (100 * ASPECT_RATIO))){
            g.setColor(Color.gray);
            g.drawLine(i,0,i,HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i = i + (HEIGHT / 100)){
            g.setColor(Color.gray);
            g.drawLine(0,i,WIDTH,i);
        } */

/*      Function f = new Function("Hello");
        ArrayList<Coordinate> points = f.createFunctionTree(500);

        for(int i = 0; i < points.size()-2; i++){
            g.fillOval(points.get(i).getX(),points.get(i).getY(),1,1);
            g.setColor(Color.black);
            g.drawLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
        } */

        // End

        g.dispose();
        bs.show();
    }
}