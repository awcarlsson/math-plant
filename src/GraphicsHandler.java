package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(32, this); // Number of milliseconds for each update (16 = 60 fps)

    // Global objects in scene
    // TODO: replace with better system
    private ArrayList<Plant> plants = new ArrayList<Plant>();
    private Function f = new Function("Hello", 100);

    public GraphicsHandler(){
        timer.start();
    }

    // Paints all elements to the screen
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;

        // DRAW OBJECTS HERE

        // Dirt and sky
        g2d.setColor(new Color(207, 237, 255));
        g2d.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT/2);
        g2d.setColor(new Color(115, 89, 71));
        g2d.fillRect(0, Frame.HEIGHT/2, Frame.WIDTH, Frame.HEIGHT);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(Frame.WIDTH/2,0,Frame.WIDTH/2,Frame.HEIGHT); // y-axis
        g2d.drawLine(0,Frame.HEIGHT/2,Frame.WIDTH,Frame.HEIGHT/2); // x-axis

        // Function
        //f.paintFunction(g2d);
    
        // Plant
        for (Plant p : plants)
            p.paintPlant(g2d);
    }

    // Logic for on screen elements, performed each tick
    @Override
    public void actionPerformed(ActionEvent e) {

        Plant plant = ClickListener.seedPlanted();
        if(plant != null){
            plants.add(plant);
        }

        if(plants.size() > 0)
            for (Plant p : plants){
                p.updatePlant();
            }

        repaint();
    }
}