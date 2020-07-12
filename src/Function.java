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
        for(int i = -Graph.WIDTH/2; count < nodeNumber*10; i = i + Graph.WIDTH/nodeNumber){
            double x = (double)(i)/10.0;
            double y = 2.0*(x*x)*Math.sin((x*x))/10.0;
            functionNodes.add(new Coordinate(i, (int)(y)));
            count ++;
        }

        return functionNodes;

    }
    
}