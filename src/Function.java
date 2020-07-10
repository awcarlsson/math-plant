package src;

import java.util.ArrayList;
public class Function{

    private String functionString;

    public Function(String functionString){

        this.functionString = functionString;

    }

    public ArrayList<Coordinate> createFunctionTree(int nodeNumber){

        ArrayList<Coordinate> functionNodes = new ArrayList<Coordinate>();
        int count = 0;
        for(int i = 0; count < nodeNumber; i = i + Graph.WIDTH/nodeNumber){
            int y = i;
            functionNodes.add(new Coordinate(i, y));
            count ++;
        }

        return functionNodes;

    }
    
}