package src;
import java.lang.Math;
import java.util.ArrayList;
public class Function{

    private String functionString;

    public Function(String functionString){

        this.functionString = functionString;

    }

    public ArrayList<Coordinate> createFunctionTree(int nodeNumber){

        ArrayList<Coordinate> functionNodes = new ArrayList<Coordinate>();
        int count = 0;
        for(int i = -Graph.WIDTH/2; count < nodeNumber; i = i + Graph.WIDTH/nodeNumber){
            double y = 2.0*((double)(i))*Math.sin(i);
            functionNodes.add(new Coordinate(i, (int)(y)));
            count ++;
        }

        return functionNodes;

    }
    
}