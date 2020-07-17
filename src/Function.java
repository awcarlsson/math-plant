package src;

import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

public class Function{

    private String functionString;
    private ArrayList<Coordinate> functionNodes;

    public Function(String functionString, int nodeNumber){
        this.functionString = functionString;
        createFunctionTree(nodeNumber);
    }

    public void createFunctionTree(int nodeNumber){

        functionNodes = new ArrayList<Coordinate>();
        int count = 0;
        for(int i = -Frame.WIDTH/2; count < nodeNumber*10; i = i + Frame.WIDTH/nodeNumber){
            double x = (double)(i)/10.0;
            double y = 2.0*(x*x)*Math.sin((x*x))/10.0;
            functionNodes.add(new Coordinate(i, (int)(y)));
            count ++;
        }

    }

    public void paintFunction(Graphics2D g){
        for(int i = 0; i < functionNodes.size()-2; i++){
            g.fillOval(functionNodes.get(i).getDisplayX(),functionNodes.get(i).getY(),1,1);
            g.setColor(Color.black);
            g.drawLine(functionNodes.get(i).getDisplayX(),functionNodes.get(i).getDisplayY(),functionNodes.get(i+1).getDisplayX(),functionNodes.get(i+1).getDisplayY());
        } 
    }
    
}