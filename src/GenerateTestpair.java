import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class GenerateTestpair {

	public Node source;
	public Item item;
	public double rating;
	
	 int  countCanpredict; //for coverage use
	public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes
	public ArrayList<Node> nList=new ArrayList<Node>(); //sub graph nodes
		   
		  public int k=100;//sample total numbers
		  public double dtime=0.01; //time slot
		  public int length=6;
		  public int nodeSize=2378;//ciao: 2378 users;
		  public int index=0;  //0 is source itself
		 public int testpair=1;

		 int canPredict=0;  //if can find a subgraph,then it becomes 1
		  
		  public void setTestPair(int testpair)
		   {
			   this.testpair=testpair;
		   }
		  public void setNodeSize(int n)
		   {
			   this.nodeSize=n;;
		   }
	 public void setDTime(double dtime)
	   {
		   this.dtime = dtime;
	   }
	   
	 public void setIndex(int index)
	   {
		   this.index = index;
	   }
	   
	   
		  public void setK(int k)
		  {
			  this.k=k;
		  }
		
		  
		  public void setLength(int L)
		  {
			  this.length=L;
		  }
		  
	 public GenerateTestpair()
	 {
		 this.setDefaultNodes();
		 this.readTrust();
		 this.readRatings();
		System.out.println(nodeList.size());
	 }

	
	 
	 public void addNode(Node node)
	  {
	      if(nodeList.contains(node))
	          return;
	  
	  	      
	      nodeList.add(node);
	           
	  }
	 
	
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
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
	   
	 public void readTrust()
	  {    
	        String s = null;
	       // File f = new File("trust-small.txt");
	        File f = new File("ciao-trust.txt"); 
	       // File f = new File("trust-test-circle.txt");
	       // File f = new File("trust-canpredict.txt");
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
		                   
		                   nodeList.get(o).neighbors.add(new Neighbor(nodeList.get(p),q));
		                  
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
	 
	

	  public void readRatings()
	  {    
	        String s = null;
	        
	       // File f = new File("ratings-small.txt");
	        File f = new File("ciao-ratings.txt");
	      //  File f = new File("ratings-test-circle.txt");
	       // File f = new File("ratings-test.txt");
	      // File f = new File("ratings-canpredict.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                   String [] edge = new String[4];		            	
		               edge = s.split("\t");            	
	                          
	                   int o=Integer.parseInt(edge[0])-1;
	                   if(this.checkItemDuplication(nodeList.get(o),edge[1])) //if already exist, go to next
	                	   continue;
	                  // int p=Integer.parseInt(edge[1]);
	                   double q=Double.parseDouble(edge[2]); //rating
	                   double time=Double.parseDouble(edge[3]); //time
	                   nodeList.get(o).items.add(new Item(edge[1],q,time)); 
	                   
	               }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings no file!");
	            }
	           
	        
	    }
	  
	 
	  public void clear()
	  {
		  for(int i=0;i<nList.size();i++)
		  {
			  nList.get(i).wasInGraph=false;
			  nList.get(i).visitedAllNeighbor=false;
			  nList.get(i).wasVisited = false;
			  nList.get(i).subNeighbors.clear();
			  nList.get(i).ratingFlag=0;
			  nList.get(i).wasSetup=false;
			  nList.get(i).distance=0;
		  }
		 nList.clear();
		// this.countHop=0;
		 this.canPredict=0;
		// this.item.canPredict=false;
	  }
	  
	  
	  //compare serveral method
	  public void compare()
	  {
		  int userNum=0;
		  int flag=0;//if at least one item can predict, userNum++
		  for(int i=0;i<nodeList.size();i++)  // first: only test the node 3 
		  {
			  if(flag==1)
				  userNum++;
			  if(userNum>=this.testpair)  //only deal with testpair user
				  break;
			   this.source=nodeList.get(i);
			   flag=0;
			   int itemNum=0;
			   for(int j=0;j<source.items.size()&& itemNum<=5;j++) //each user at most 5 items
			   {
				   
				   if(this.countCanpredict>=this.testpair)  //only deal with the number of testpair
						  break;
				   this.item=source.items.get(j);
				   this.clear();
				   
				  source.items.remove(j); //leave one out
				  this.constructSubgraph();
				
				
				if(this.canPredict==1)
				{
					flag=1;
					itemNum++;
					this.printCanPredict();
					this.countCanpredict++;
					System.out.println(countCanpredict);
				}
				source.items.add(j,this.item);	
				
			   }
		  }
		  
		  this.printCanPredict();
		 
			 
	  }
	  
	  
	     
	 
	 
	  public void addPathNode(ArrayList<Neighbor> path)
	   {
		  int j=path.size()-1;
		 for(int i=j;i>=0;i--)
		 {
			 if(!nList.contains(path.get(i).neighbor))
			 {
				 nList.add(path.get(i).neighbor);
				 path.get(i).neighbor.wasInGraph=true;
			// path.get(i).neighbor.inSubGraph=true;
				 if(i>0 && path.get(i-1).neighbor.neighbors.contains(path.get(i)))
					 path.get(i-1).neighbor.subNeighbors.add(path.get(i));
			 }
			// else
			//	 break;
			 
		 }
		 
	   }
	  
	 
	  double time =0;
	  public double getRating(Node n)
	  {
		  double rating=0;
		  for(int i=0;i<n.items.size();i++)
		  {
			  if(n.items.get(i).ItemID.equals(this.item.ItemID))
			  {
				  rating=n.items.get(i).rating;
				  this.time = n.items.get(i).time;
				  break;
			  }
		  }
		  
		  return rating;
		  
	  }
	  
	  public void constructSubgraph()
	   {
		  this.source.wasInGraph=true; //in fact, it doesn't matter.
		  nList.add(source);
		  
		  this.subgraph(this.source);
		 
		  
	   }
	  
	  
	  
	   int countHop=0;
	   ArrayList<Neighbor> path=new ArrayList<Neighbor>();
	   public void subgraph(Node source)

	   {

		   

 		  if(source.distance>=this.length)//&&countHop>=this.length

			   return;
 
		 // countHop++;

		   for(int i=0;i<source.neighbors.size();i++)

		   { 
 
			  source.neighbors.get(i).neighbor.distance=source.distance+1;

			   if(this.checkRater(source.neighbors.get(i).neighbor))
			   {
				   source.neighbors.get(i).neighbor.ratingFlag=1;

				   if(!source.neighbors.get(i).neighbor.wasInGraph) // not join graph yet, add node
				  {
					   this.canPredict=1;

					   this.item.canPredict=true;
 
					  source.neighbors.get(i).neighbor.wasInGraph=true;

					   nList.add(source.neighbors.get(i).neighbor);
 
				  }
				  //  add edge

				   if(!source.subNeighbors.contains(source.neighbors.get(i)))
					   source.subNeighbors.add(source.neighbors.get(i));			
 
				  //add non raters who can reach the rater into subgraph

				   this.addPathNode(path);			  

			   }

			   else 
			  {

				   if(!source.neighbors.get(i).neighbor.visitedAllNeighbor)

				   // check to avoid cycle
				if(source.neighbors.get(i).neighbor.distance<this.length)
				  if(!source.neighbors.get(i).neighbor.wasVisited&&!path.contains(source.neighbors.get(i)))
				  {
					  path.add(source.neighbors.get(i));

					  source.neighbors.get(i).neighbor.wasVisited=true;
 
					  subgraph(source.neighbors.get(i).neighbor);

				   }

			   }
 
		  } // visit all neighbor

		   source.visitedAllNeighbor = true;

		   // check source's neighbor
 
			 for(int i=0;i<source.neighbors.size();i++)
			  {

				  if(source.neighbors.get(i).neighbor.distance<=this.length&&source.neighbors.get(i).neighbor.wasInGraph&&!source.subNeighbors.contains(source.neighbors.get(i)))
 
					 source.subNeighbors.add(source.neighbors.get(i));

			  }
 
	  }
	  
	   public boolean checkRater(Node n)
	   {
		   boolean flag=false; //default nonrater
		   for(int i=0;i<n.items.size();i++)
		   {
			   if(n.items.get(i).ItemID.equals(this.item.ItemID))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
	   }
	   
	   
	   public void printNeighbors()
	   {
		   System.out.println("Neighbors");
		   for(int i=0;i<nodeList.size();i++)
		   {
			   System.out.println(nodeList.get(i).name+"-----");
			   for(int j=0;j<nodeList.get(i).neighbors.size();j++)
			   {
				   System.out.println(j+": "+nodeList.get(i).neighbors.get(j).neighbor.name);
			   }
			   
		   }
			   
	   }
	   
	   public void printSubNeighbors()
	   {
		   System.out.println("SubNeighbors");
		   for(int i=0;i<nList.size();i++)
		   {
			   System.out.println(nList.get(i).name+"-----");
			   for(int j=0;j<nList.get(i).subNeighbors.size();j++)
			   {
				   System.out.println(j+": "+nList.get(i).subNeighbors.get(j).neighbor.name);
			   }
			   
		   }
			   
	   }
	   
	  
	    	  //print subgraphs for future ease
	    	  public void printCanPredict()
		 		 {
	    		     if(this.canPredict==0)
	    		    	 return;
		 			 try 
		 	         {
		 	          
		 				
		 				 FileWriter writer = new FileWriter("trust-fortestpair.txt", true);
		 				FileWriter writer1 = new FileWriter("ratings-fortestpair.txt", true);
		 				FileWriter writer2 = new FileWriter("1214-testpair.txt", true);
		 				
		 				writer1.write(source+"\t"+this.item.ItemID+"\t"+this.item.rating+"\r\n");
		 				writer2.write(source+"\t"+this.item.ItemID+"\t"+this.item.rating+"\r\n");
		 				for(int i=0;i<nList.size();i++)
		 			   {
		 				   
		 				   for(int j=0;j<nList.get(i).subNeighbors.size();j++)
		 				   {
		 					  writer.write(nList.get(i).name+"\t"+nList.get(i).subNeighbors.get(j).neighbor.name+"\r\n");
		 					  
		 				   }
		 				   
		 				  //print rating
		 				   if(nList.get(i).ratingFlag==1)
		 					  writer1.write(nList.get(i).name+"\t"+this.item.ItemID+"\t"+this.getRating(nList.get(i))+"\t"+this.time+"\r\n");
		 			   }
		 				 	 
		 				 //writer.write("\r\n");
		 				 writer.flush();
		 		         writer.close();  
		 		        writer1.flush();
		 		         writer1.close();  
		 		        writer2.flush();
		 		         writer2.close(); 
		 		               
		 			   }catch (Exception e1) {
		 			         e1.printStackTrace();
		 			  }	
		 			 
		 			 
		 		 }
	   
	     public static void main(String[] args)
		 { 
			
			 GenerateTestpair s= new GenerateTestpair();
		     s.setLength(3); //default 6
			 s.setK(2); //k=1000,dt=0.01
			
			 s.setTestPair(1000);
			 
			 s.compare();
				
			
		    
			 
		 }
}
/*20131214, Generate part test pairs, to test the existence of first influence and recent influence*/
