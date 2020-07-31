package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

/**
 * Handles the updating and painting of objects to the graphics window.
 */
public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(32, this); // Number of milliseconds for each update (32 = 30 fps)

    // Global objects in scene
    private static ArrayList<Plant> plants = new ArrayList<Plant>();
    private static Function f = new Function("x^2", 100);
    private static Background b = new Background(Frame.HEIGHT*3/4);
    private static Light l = new Light(0, 300, 50);

    public GraphicsHandler() {
        timer.start();
    }

    // Paints all elements to the screen
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(new Color(207, 237, 255));
        Graphics2D g2d = (Graphics2D) g;

        // DRAW OBJECTS HERE

        // Background
        b.paintBackground(g2d);

        // Function
        f.paintFunction(g2d);

        // Plant
        for (Plant p : plants) p.paintPlant(g2d, b);

        // Light
        l.paintLight(g2d);
    }

    // Logic for on screen elements, performed each tick
    @Override
    public void actionPerformed(ActionEvent e) {

        // Check if a user has planted a plant
        Plant plant = PlantListener.seedPlanted(b);
        if(plant != null) plants.add(plant);
        for (Plant p : plants) p.updatePlant(b);

        repaint();
    }

    public void reset(){
        plants = new ArrayList<Plant>();
    }

    public void updateFunction(String funcString){
        f.setFunction(funcString);
        //System.out.println(funcString);
    }
}