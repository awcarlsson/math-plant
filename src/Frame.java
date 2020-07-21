package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * The frame in which all graphics appear
 */
public class Frame extends JFrame {

    public static final double ASPECT_RATIO = 16D / 9D;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = (int) ((double) WIDTH / ASPECT_RATIO);
    public static final String NAME = "Plant Game";

    private static GraphicsHandler graphicsHandler = new GraphicsHandler();

    public Frame() {
        this.setName(NAME);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(graphicsHandler);
        graphicsHandler.setLayout(null);
        JButton reset = new JButton("Reset");
        reset.addActionListener(new Reset());
        graphicsHandler.add(reset);
        reset.setSize(100, 100);
        reset.setLocation(40, HEIGHT - 150);
        this.getContentPane().addMouseListener(new PlantListener());
        this.setVisible(true);
    }

    static class Reset implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            graphicsHandler.reset();
        }

    }
}