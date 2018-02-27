
/**
 * 
 * @author  Wenjun Jiang
 *
 */
 
 
import java.util.*;
import java.lang.*;
import java.util.LinkedList;
public class Neighbor {
    Node neighbor;

    
    double t=1; //trustworthiness 
   
   
    
    public Neighbor(Node s, double t)
    {
        this.neighbor = s;
        this.t = t;
           
    }
   
    public Node getNeighbor()
    {
        return neighbor;
    }
    
   }
