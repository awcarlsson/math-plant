package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(100, this); // Number of milliseconds for each update (16 = 60 fps)

    // Global objects in scene
    // TODO: replace with better system
    private Plant p;
    private boolean planted = false;
    private Function f = new Function("Hello");
    private ArrayList<Coordinate> points = f.createFunctionTree(500);

    public GraphicsHandler(){
        //p = new Plant();
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
        for(int i = 0; i < points.size()-2; i++){
            g2d.fillOval(points.get(i).getDisplayX(),points.get(i).getY(),1,1);
            g2d.setColor(Color.black);
            g2d.drawLine(points.get(i).getDisplayX(),points.get(i).getDisplayY(),points.get(i+1).getDisplayX(),points.get(i+1).getDisplayY());
        } 
    
        // Plant
        if(planted)
            p.paintPlant(g2d);
    }

    // Logic for on screen elements, performed each tick
    @Override
    public void actionPerformed(ActionEvent e) {
        if(ClickListener.seedPlanted() != null){
            p = ClickListener.seedPlanted();
            planted = true;
        }
        if(planted)
            p.updatePlant();
        repaint();
    }
}