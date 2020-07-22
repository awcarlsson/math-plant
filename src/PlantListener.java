package src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles users clicking on the soil to plant a seed
 */
public class PlantListener implements MouseListener{

    private static boolean newPlant = false;
    private static Plant p = null;
    private static int y;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public static Plant seedPlanted(Background b){
        if(newPlant && y >= b.getDirtY()) {
            newPlant = false;
            return p;
        } else
            return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        y = e.getY();
        p = new Plant(new Coordinate(Coordinate.displayXtoX(e.getX()), Coordinate.displayYtoY(e.getY())));
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