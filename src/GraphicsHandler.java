package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GraphicsHandler extends JPanel implements ActionListener {

    private Timer timer = new Timer(16, this); // Timer set to 60 fps
    int x = 0;

    public GraphicsHandler(){
        timer.start();
    }

    // Paints all elements to the screen
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        this.setBackground(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;

        // DRAW OBJECTS HERE

        g2d.drawLine(Frame.WIDTH/2,0,Frame.WIDTH/2,Frame.HEIGHT); // y-axis
        g2d.drawLine(0,Frame.HEIGHT/2,Frame.WIDTH,Frame.HEIGHT/2); // x-axis

        Function f = new Function("Hello");
        ArrayList<Coordinate> points = f.createFunctionTree(x);

        for(int i = 0; i < points.size()-2; i++){
            g2d.fillOval(points.get(i).getX(),points.get(i).getY(),1,1);
            g2d.setColor(Color.black);
            g2d.drawLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
        }
        
        Plant p = new Plant();
        g2d.fillOval(p.getOrigin().getX(), p.getOrigin().getY(), 50, 50);
    }

    // Logic for on screen elements
    @Override
    public void actionPerformed(ActionEvent e) {
        x = x + 1;
        repaint();
    }
}