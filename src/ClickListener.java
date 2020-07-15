package src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener{

    private static Plant p = null;

    @Override
    public void mouseClicked(MouseEvent e) {
        p = new Plant(Coordinate.displayXtoX(e.getX()), Coordinate.displayYtoY(e.getY()));
        System.out.println("click");
    }

    public static Plant seedPlanted(){
        return p;
    }

    @Override
    public void mousePressed(MouseEvent e) {
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