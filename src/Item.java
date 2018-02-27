import java.util.ArrayList;

public class Item {
	String ItemID;
	double rating;  //works when as target item
	double time;    //works when as target item
	ArrayList<Node> nodes; //nodes who rate on the item
	
	/*int posCount=0; //number of positive ratings at the time
	int negCount=0; //number of negative ratings at the time
	boolean negFlag=false; // is containing negative rating?
	int count=0; // total ratings
	double aveDistance[] =new double[9]; 
	boolean aveFlag[]=new boolean[9]; //false;// if can calculte ave rating
	double avePre[] =new double[9];  //average rating before the first negative
	double avePost[] =new double[9]; // average rating after the first negative
	
	//double helpfulness;
	double prating[] =new double[10];//predictRating
	int category=0; //item's category (27 in Epinions and 6 in Ciao)
	
	boolean canPredict=false;
	//boolean testPairFlag=false;
	*/
	
	/*public Item(String s, double r, double time, int pos, int neg)//, double help)
	{
		this.ItemID=s;
		this.rating=r;
		this.time = time;
		this.posCount = pos;
		this.negCount = neg;
	}*/
	
	public Item(String s, double r, double time)//, double help)
	{
		this.ItemID=s;
		this.rating=r;
		this.time = time;
		this.nodes = new ArrayList<Node>();
		//this.helpfulness=help;
		//for(int i=0;i<10;i++)
			//prating[i]=0;
		/*for(int i=0;i<9;i++)
			{
			aveDistance[i]=0;
			aveFlag[i]=false;
			avePre[i]=0;
			avePost[i]=0;
			}*/
	}
	

	public Item(String s, double r, double time,ArrayList<Node> nodes)//, double help)
	{
		this.ItemID=s;
		this.rating=r;
		this.time = time;
		this.nodes = new ArrayList<Node>();
		//this.helpfulness=help;
		/*for(int i=0;i<10;i++)
			prating[i]=0;*/
	}
}
