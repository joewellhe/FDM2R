import java.util.*;

public  class Node
{
	String name;
    
    LinkedList<Item> items;  //rated items
    double rating=0; //rating on the target item
	double time=0; //rating time
    /* ArrayList<Neighbor> neighbors; //out neighbors, trustees
    ArrayList<Neighbor> inneighbors; //in neighbors, trustors
    
    ArrayList<Neighbor> subNeighbors;
    
    
    boolean wasVisited=false;
    boolean wasInGraph=false; 
    boolean visitedAllNeighbor=false;
	boolean wasSetup=false; //check if already convert to container 
    int ratingFlag=0;  //not rating on target item
	
    double b=1;
    int distance=0;
	
	
	double prating=0; //for pagerank
	double wsum=0;//for pagerank
	*/
    public Node(String name)
    {
       
        this.name = name;
       // this.neighbors = new ArrayList<Neighbor>();  // out neighbor
       // this.inneighbors = new ArrayList<Neighbor>(); // in neighbor
       // this.subNeighbors = new ArrayList<Neighbor>();
        this.items = new LinkedList<Item>();
       
    }
    
    public String toString()
    {
        return name;
    }
    
    public boolean isNode(Node node)
    {
        if(this.toString().equals(node.toString()))
            return true;
        else
            return false;
    }
    
    public void setNode(double r, double time)
    {
    	this.rating=r;
    	this.time=time;
    }
     
}
