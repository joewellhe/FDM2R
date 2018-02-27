import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

//check the co-rate/co-purchase 
//time-awareness co-rating based recommendation
public class co_rating {

	public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items
	public int nodeSize=22166;//2378;//22166;//9;////2378;//5;circle 6; small 398;large 49289;
	Node sink = new Node("");
	Item targetItem = new Item("",-1,0);
	double time=0;
	double delta = 60;//1440;//10080; //30min, 60 min, (1440min)24 hours,(10080min)1 week
	LinkedList<Item> corate_items = new LinkedList<Item>();;  //co_rated items
	LinkedList<Node> corate_nodes = new LinkedList<Node>();  //co_rated nodes
	
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
		 }
	 }
	 
	public co_rating()
	{
		 this.setDefaultNodes();
		
		 this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 this.readRatings_usertime();  //get userList, for each user: <item, rating, time>
		 System.out.println("read file2 complete");
		 this.stat_corating();
	}
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	        
	        // File f = new File("rating_newformat_epinions_9itemtime.txt");
	        File f = new File("rating_epinions_byitem-time.txt");
	       // File f = new File("ciao-rating-byitemtime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[5];	 //no helpfulness	             	
		               edge = s.split("\t");            	
	                          
	                   int o=Integer.parseInt(edge[0])-1;  //node/user
	                   double q=Double.parseDouble(edge[3]); //rating
	                   double t=Double.parseDouble(edge[4]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[1],q,t);
	                	   item.nodes.add(nodeList.get(o));
	                	   int k=item.nodes.size()-1;
	                	    item.nodes.get(k).setNode(q,t); //set node's rating and time	                	   
	                	   itemList.add(item);
	                   }
	                  // if(this.checkItemNodeDuplication(nodeList.get(o),item)) //if already exist, go to next
	                	//   continue;
	                   
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])) //same item with last one
	                   { 
	                	   itemList.get(itemList.size()-1).nodes.add(nodeList.get(o)); 
	                	   int k=itemList.get(itemList.size()-1).nodes.size()-1;
	                	   itemList.get(itemList.size()-1).nodes.get(k).setNode(q,t);
	                	   item = new Item(edge[1],q,0);// an item's node list
                   
	                   }
		               else  //new item
		               { 		                   
		                     item = new Item(edge[1],q,0);
		                     item.nodes.add(nodeList.get(o));
		                	 int k=item.nodes.size()-1;
		                	 item.nodes.get(k).setNode(q,t); //set node's rating and time	                	   
		                	  
		                     itemList.add(item);
		                   
	                   }
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by item-time no file!");
	            }
	           	        
	    }
	 
	 public void readRatings_usertime()
	  {    
	        String s = null;
	        
	       //  File f = new File("rating_newformat_epinions_9usertime.txt");
	        File f = new File("rating_epinions_byuser-time.txt");
	      //  File f = new File("ciao-rating-byusertime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	   Item item;
		                   String [] edge = new String[5];	 //no helpfulness	             	
			               edge = s.split("\t");            	
		                          
		                   int o=Integer.parseInt(edge[0])-1;  //node/user
		                   double q=Double.parseDouble(edge[3]); //rating
		                   double t=Double.parseDouble(edge[4]); //rating time
		                    //build  itemlist for each user
			               { 		                   
			                     item = new Item(edge[1],q,t);
			                     nodeList.get(o).items.add(item);
			                	 		                   
		                   }
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by user-time no file!");
	            }
	           	       
	    }
	 
	 public boolean checkItemDuplication(Node n, String ItemID)
	   {
		   boolean flag=false; //default nonrater
		   for(int i=0;i<n.items.size();i++)
		   {
			   if(n.items.get(i).ItemID.equals(ItemID))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
	   }
	 
	 public void set_sink(Node sink, Item targetItem, double time)
	 {
		 this.sink = sink;
		 this.targetItem = targetItem;
		 this.time = time;
	 }
	 
	  public int itemPosition()
	 {
			 int p=-1; //index of target item in itemList
			 for(int i=0;i<itemList.size();i++)
			 {
				 if(itemList.get(i).equals(targetItem))
				 {
					p=i;
					 break;
				 }
			 }
			 return p;
	 }
	  public int nodePosition(String n)
		 {
				 int p=-1; //index of a node in nodeList
				 for(int i=0;i<nodeList.size();i++)
				 {
					 if(nodeList.get(i).name.equals(n))
					 {
						p=i;
						 break;
					 }
				 }
				 return p;
		 }
	  public void stat_corating_sink()
	  {
		  int p = this.itemPosition(); //targetItem's index in itemList
		  int k=itemList.get(p).nodes.size(); //targetItem's reviewers (include sink)
			 
		  for(int r=0;r<k;r++) //reviewers
		 {    
			  int p1=this.nodePosition(itemList.get(p).nodes.get(r).name);
			  if(p1==-1) //no node
				  break;
			  int nodeflag=0;//has co-rated items in a session?
			  for(int i1=0;i1<nodeList.get(p1).items.size();i1++) //reviewers' rated items
			  {					  
				  double t1=nodeList.get(p1).items.get(i1).time;
				  if(this.time-this.delta<=t1 &&t1<=this.time) //only items in a time session
				  {
						nodeflag=1;  //has corated items in a session
						
						if( this.corate_items.size()==0||!this.corate_items.contains(nodeList.get(p1).items.get(i1)))
							this.corate_items.add(nodeList.get(p1).items.get(i1));
				  }
		      }//reviewers' rated items
			  if( nodeflag==1 && (this.corate_nodes.size()==0||!this.corate_nodes.contains(nodeList.get(p1))))
				  this.corate_nodes.add(nodeList.get(p1));
			  
			  /*20170730  I found that I may miss some co-rated users before
			   * need to check other consumers of co-rated items in future*/ 
			  /*20180127 I found it's ok. i_0 is the core of calculation*/
			 // for(int i2=0;i2<this.corate_items.size();i2++)
			 // {
				  
			 // }
	     }
	  }
	  
	  public void stat_corating()
	 {
		 int treat=0; //alreadytreated
		 for(int i=0;i<itemList.size();i++)
		 {
			 int k=itemList.get(i).nodes.size();
			 
			 for(int r=0;r<k;r++) //reviewers
			 {
				 double t=itemList.get(i).nodes.get(r).time;
				 this.set_sink(itemList.get(i).nodes.get(r),itemList.get(i),t);
				 
				 int n = Integer.parseInt(itemList.get(i).nodes.get(r).name)-1;  //index of sink
				 this.stat_corating_sink(); //check corating for current sink and target item
				 this.printCorating();
				 treat++;
				 if(treat%1000==0)  //print every 1000 treated
				   System.out.println(""+treat);
				 this.corate_items.clear(); //clear for next
				 this.corate_nodes.clear(); //clear for next
			  } //reviewers
		      
			 
		 }
		 
		// System.out.println("sum="+sum); // number of trust relations that rates on same items
	 }
	 
	  public void printCorating() //print co-rate item size and node size
		 {
			 try 
		         {
					 FileWriter writer = new FileWriter("Epinions-coratingsize-60min.txt", true);
					// FileWriter writer = new FileWriter("Ciao-coratingsize-10080min.txt", true);
						
					 writer.write(this.corate_items.size()+"\t"+this.corate_nodes.size()+"\r\n");
				//this.sink.name+"\t"+this.targetItem.ItemID+"\t"++this.time+"\t"
					 writer.flush();
			         writer.close();  
					 
			               
				   }catch (Exception e1) {
				         e1.printStackTrace();
				  }	
				 
		 }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       co_rating cr=new co_rating();
	}

}
