package src;

import javax.swing.JFrame;

public class Frame extends JFrame{
    
    public static final double ASPECT_RATIO = 16D/9D;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = (int)((double)WIDTH/ASPECT_RATIO);
    public static final String NAME = "Plant Game";

    GraphicsHandler graphicsHandler = new GraphicsHandler();

    public Frame() {
        this.setName(NAME);
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(graphicsHandler);
        this.getContentPane().addMouseListener(new ClickListener());
        this.setVisible(true);
    }
}