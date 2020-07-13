package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(100, this); // Number of milliseconds for each update (16 = 60 fps)
    private Plant p;

    public GraphicsHandler(){
        p = new Plant();
        timer.start();
    }

    // Paints all elements to the screen
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        this.setBackground(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;

        // DRAW OBJECTS HERE

        g2d.setColor(new Color(207, 237, 255));
        g2d.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT/2);
        g2d.setColor(new Color(115, 89, 71));
        g2d.fillRect(0, Frame.HEIGHT/2, Frame.WIDTH, Frame.HEIGHT);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(Frame.WIDTH/2,0,Frame.WIDTH/2,Frame.HEIGHT); // y-axis
        g2d.drawLine(0,Frame.HEIGHT/2,Frame.WIDTH,Frame.HEIGHT/2); // x-axis

        /* Function f = new Function("Hello");
        ArrayList<Coordinate> points = f.createFunctionTree(500);

        for(int i = 0; i < points.size()-2; i++){
            g2d.fillOval(points.get(i).getX(),points.get(i).getY(),1,1);
            g2d.setColor(Color.black);
            g2d.drawLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
        } */
        ArrayList<PlantNode> nodes = p.getPlant();
        for(int i = 0; i < nodes.size(); i++){
            if(nodes.get(i).getY() < 0)
                g2d.setColor(Color.WHITE);
            else
                g2d.setColor(Color.GREEN);
            if(i > 0){
                Stroke stroke = new BasicStroke(6f);
                g2d.setStroke(stroke);
                g2d.drawLine(nodes.get(i).getDisplayX(), nodes.get(i).getDisplayY(), nodes.get(i-1).getDisplayX(), nodes.get(i-1).getDisplayY());
            }
            if (i == 0) {
                g2d.setColor(new Color(79, 37, 2));
                g2d.fillOval(nodes.get(i).getDisplayX()-10, nodes.get(i).getDisplayY()-10, 20, 20);
            }
        }
    }

    // Logic for on screen elements
    @Override
    public void actionPerformed(ActionEvent e) {
        p.updatePlant();
        repaint();
    }
}