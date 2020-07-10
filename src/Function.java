package src;

import java.util.ArrayList;
public class Function{

    private String functionString;

    public Function(String functionString){

        this.functionString = functionString;

    }

    public ArrayList<Coordinate> createFunctionTree(int nodeNumber){

        ArrayList<Coordinate> functionNodes = new ArrayList<Coordinate>();

        for(int i = 0; i < nodeNumber; i ++){
            int y = i - (nodeNumber/2);
            functionNodes.add(new Coordinate(i, y));
        }

        return functionNodes;

    }
    
}