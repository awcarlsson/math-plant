package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

/**
 * Handles the updating and painting of objects to the graphics window
 */
public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(32, this); // Number of milliseconds for each update (32 = 30 fps)

    // Global objects in scene
    // TODO: replace with better system
    private ArrayList<Plant> plants = new ArrayList<Plant>();
    private Function f = new Function("Hello", 100);
    private Background b = new Background(Frame.HEIGHT*3/4);

    public GraphicsHandler() {
        timer.start();
    }

    // Paints all elements to the screen
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;

        // DRAW OBJECTS HERE

        // Background
        b.paintBackground(g2d);

        // Function
        //f.paintFunction(g2d);
    
        // Plant
        for (Plant p : plants)
            p.paintPlant(g2d, b);
    }

    // Logic for on screen elements, performed each tick
    @Override
    public void actionPerformed(ActionEvent e) {

        Plant plant = ClickListener.seedPlanted(b);
        if(plant != null){
            plants.add(plant);
        }

        for (Plant p : plants)
            p.updatePlant(b);

        repaint();
    }
}