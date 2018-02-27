
/**
 * 
 * @author  Wenjun Jiang
 *
 */
 
 
import java.util.*;
import java.lang.*;
import java.util.LinkedList;
public class NonRater {
	String  nonrater;
    ArrayList<Pipe> inPipe=new  ArrayList<Pipe>(); //incoming pipes
    ArrayList<Pipe> outPipe=new  ArrayList<Pipe>(); //outgoing pipes
    
    double h1=0,h2=0;  //height
	double s1=0,s2=0;  //volume
	double t1=0,t2=0;  //temperature
    
    double b=1;
   
   
    
    public NonRater(String s, double b)
    {
        this.nonrater = s;
        this.b=b;
        
    }
   
    public String getNonRater()
    {
        return nonrater;
    }
    
   }
