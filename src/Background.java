package src;

import java.awt.*;

public class Background {

    private int dirtY;

    public Background(){
        this.dirtY = Frame.HEIGHT/2;
    }

    public Background(int dirtY){
        this.dirtY = dirtY;
    }

    public int getDirtY(){
        return dirtY;
    }

    public void paintBackground(Graphics2D g){

        g.setColor(new Color(207, 237, 255));
        g.fillRect(0, 0, Frame.WIDTH, dirtY);
        g.setColor(new Color(115, 89, 71));
        g.fillRect(0, dirtY, Frame.WIDTH, Frame.HEIGHT);
    
        g.setColor(Color.BLACK);
        g.drawLine(Frame.WIDTH/2,0,Frame.WIDTH/2,Frame.HEIGHT); // y-axis
        g.drawLine(0,Frame.HEIGHT/2,Frame.WIDTH,Frame.HEIGHT/2); // x-axis
    }
}