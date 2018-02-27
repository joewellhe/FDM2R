
/**
 * 
 * @author  Wenjun Jiang
 *
 */
 
 
import java.util.*;
import java.lang.*;
import java.util.LinkedList;
public class Rater {
	String  rater;
	ArrayList<Pipe> outPipe=new  ArrayList<Pipe>(); //outgoing pipes
	   
    double h=10;  //height
	//double v1=1,v2=0;  //volume
	double t;  //temperature
    
    double b=1;
   
   
    
    public Rater(String s, double t,double b)
    {
        this.rater = s;
        this.t = t;
        this.b=b;
           
    }
   
    public String getRater()
    {
        return rater;
    }
    
   }
