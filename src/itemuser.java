import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class itemuser {
	//String ItemID;
	//ArrayList<Neighbor> neighbors;
	
	public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items

	 public int nodeSize=22166;//2378;//5;circle 6; small 398;large 49289;
	
	
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
		 }
	 }
	 
	public itemuser()
	{
		 this.setDefaultNodes();
		 this.readTrust();
		 //this.printTrustCanPredict();
		 this.readRatings();
		 //this.printRatingCanPredict();
		// this.printnode();
		// this.printitem();
		 this.stat();
	}
	
	public void printnode()
	{
		 for(int i=0;i<nodeList.size();i++)
		 {
			 System.out.println(nodeList.get(i).name+":neighbors");
			 for(int j=0;j<nodeList.get(i).neighbors.size();j++)
			 {
				 System.out.println(nodeList.get(i).neighbors.get(j).neighbor.name);
			 }
			 System.out.println("inneighbors:");
			 for(int j=0;j<nodeList.get(i).inneighbors.size();j++)
			 {
				 System.out.println(nodeList.get(i).inneighbors.get(j).neighbor.name);
			 }
			 
			 System.out.println("Rated Items:");
			 for(int j=0;j<nodeList.get(i).items.size();j++)
			 {
				 System.out.println(nodeList.get(i).items.get(j).ItemID+";Rating= "+nodeList.get(i).items.get(j).rating);
			 }
		 }
		 
		 
	}
	
	public void printitem()
	{
		System.out.println("Items number: "+itemList.size());
		for(int j=0;j<itemList.size();j++)
		 {
			 System.out.println(itemList.get(j).ItemID+"Item nodes:");
			 for(int i=0;i<itemList.get(j).nodes.size();i++)
			 {
				 System.out.println(itemList.get(j).nodes.get(i).name);
			 }
		 }
	}
	 public boolean checkNeighborDuplication(Node n, String neighborID)
	   {
		   boolean flag=false; //default nonrater
		   for(int i=0;i<n.neighbors.size();i++)
		   {
			   if(n.neighbors.get(i).neighbor.name.equals(neighborID))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
	   }
	 
	 public boolean checkInNeighborDuplication(Node n, String neighborID)
	   {
		   boolean flag=false; //default nonrater
		   for(int i=0;i<n.inneighbors.size();i++)
		   {
			   if(n.inneighbors.get(i).neighbor.name.equals(neighborID))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
	   }
	 
	 
	public void readTrust()
	  {    
	        String s = null;
	        File f = new File("trustnewformat.txt");
	        //File f = new File("test-trust.txt"); 
	       // File f = new File("trust-test.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =
	                new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	String [] edge = new String[3];
		            	
		            	edge = s.split("\t");
		            	
	                          
	                   int o=Integer.parseInt(edge[0])-1;
	                   if(!this.checkNeighborDuplication(nodeList.get(o),edge[1])) //if already exist, go to next
	                   {
	                	   //continue;
	                	   int p=Integer.parseInt(edge[1])-1;
		                   double q=1;//Double.parseDouble(edge[2]); //trust
		                   
		                   nodeList.get(o).neighbors.add(new Neighbor(nodeList.get(p),q)); //out neighbors
		                   
		                   //
		                  //
		                   if(!this.checkInNeighborDuplication(nodeList.get(p),edge[0])) //in neighbors
		                   {   
			                   nodeList.get(p).inneighbors.add(new Neighbor(nodeList.get(o),q));
			                  
		                   }
	                   }
	                   
	                  
	                   //  System.out.println(nodeList.get(o).name+", add a neighbor"+nodeList.get(o).neighbors.get(nodeList.get(o).neighbors.size()-1).neighbor.name);
	                   
	               }
	               br.close();  
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read trust no file!");
	            }
	           
	        
	    }
	
	 public boolean checkItemNodeDuplication(Node n, Item item) //check duplication in item's nodes
	   {
		   boolean flag=false; //
		   for(int i=0;i<item.nodes.size();i++)
		   {
			   if(item.nodes.get(i).isNode(n))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
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
	 
	 public void readRatings()
	  {    
	        String s = null;
	        
	         File f = new File("rating_newformat.txt");
	        //File f = new File("test-ratings.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[4];		            	
		               edge = s.split("\t");            	
	                          
	                   int o=Integer.parseInt(edge[0])-1;
	                   double q=Double.parseDouble(edge[2]); //rating
	                   if(itemList.size()==0) //the first one item
	                   {    
	                	   item = new Item(edge[1],q,0);
	                	   item.nodes.add(nodeList.get(o));
	                	   itemList.add(item);
	                   }
	                  // if(this.checkItemNodeDuplication(nodeList.get(o),item)) //if already exist, go to next
	                	//   continue;
	                   
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])) //same item with last one
	                   { 
	                	   itemList.get(itemList.size()-1).nodes.add(nodeList.get(o)); 
	                	    item = new Item(edge[1],q,0);// an item's node list
                    
	                   }
		               else
		               { 		                   
		                     item = new Item(edge[1],q,0);
		                     itemList.add(item);
		                   
	                   }
	                   
	                   if(this.checkItemDuplication(nodeList.get(o),edge[1])) //if already exist, go to next
	                	   continue;
	                  // int p=Integer.parseInt(edge[1]);
	                  // double q=Double.parseDouble(edge[2]); //rating
	                  // double time=Double.parseDouble(edge[3]); //time
	                   nodeList.get(o).items.add(item); 
	                   
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings no file!");
	            }
	           
	        
	    }
	 
	 public void stat()
	 {
		 int sum=0;
		 for(int i=0;i<itemList.size();i++)
		 {
			 
			sum += checktrustee(itemList.get(i));
			 
		 }
		 
		 System.out.println("sum="+sum); // number of trust relations that rates on same items
	 }
	 
	 public boolean IsContain(ArrayList<Neighbor> neighbors, Node n)
	 {
		 boolean flag = false;
		 for(int i=0;i<neighbors.size();i++)
		 {
			 if(neighbors.get(i).neighbor.name.equals(n.name))
			 {
				 flag=true;
				 break;
			 }
		 }
		 return flag;
	 }
	 
	 // check if exist trust relations for an item
	 public int checktrustee(Item item) 
	 {
		 int count = 0;
		 int size = item.nodes.size();
		 for(int j=0;j<size-1;j++) //the last node doesnot need check
		 {
			 for(int i=j+1;i<size;i++)
			 {
				// if(item.nodes.get(i).neighbors.contains(item.nodes.get(j))) //n is a trustee  of node nl.get(i)
				 if(IsContain(item.nodes.get(i).neighbors, item.nodes.get(j)))
				 {
					 
					 count++;
					 printuseritem(item, i,j); //trustee
				 }
			
			 }
		 } 
		 return count;
	 }
	 public void printuseritem(Item item, int i, int j) //i is trustor, j is the trustee
	 {
		 try 
	         {
	          
				
				 FileWriter writer = new FileWriter("Epinions-trusteesrating.txt", true);
				// i trust j; i and j rate on the same item;
				 int indegree=item.nodes.get(i).inneighbors.size();
				 int outdegree=item.nodes.get(i).neighbors.size();
				 double ri=0,rj=0;
				 for(int i1=0;i1<item.nodes.get(i).items.size();i1++)
				 {
					 if(item.nodes.get(i).items.get(i1).ItemID.equals(item.ItemID))
					 {
						 ri=item.nodes.get(i).items.get(i1).rating;// node i's rating on item
						 break;
					 }
				 }
				 
				 for(int i1=0;i1<item.nodes.get(j).items.size();i1++)
				 {
					 if(item.nodes.get(j).items.get(i1).ItemID.equals(item.ItemID))
					 {
						 rj=item.nodes.get(j).items.get(i1).rating; // node j's rating on item
						 break;
					 }
				 }
				 writer.write(item.ItemID+"\t"+item.nodes.get(i).name+"\t"+item.nodes.get(j).name+"\t"+indegree+"\t"+outdegree+"\t"+ri+"\t"+rj+"\r\n");
					   
			 
				 writer.flush();
		         writer.close();  
				 
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
			 
		 
	 }
	 
	 public static void main(String[] args)
	 { 
		
    	 itemuser  iu= new itemuser();
		 
	     
	 }
}
