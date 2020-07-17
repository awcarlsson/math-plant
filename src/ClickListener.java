package src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener{

    private static boolean newPlant = false;
    private static Plant p = null;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public static Plant seedPlanted(){
        if(newPlant) {
            newPlant = false;
            return p;
        } else
            return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        p = new Plant(Coordinate.displayXtoX(e.getX()), Coordinate.displayYtoY(e.getY()));
        newPlant = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}