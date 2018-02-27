import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class dynFluid {

		public Node source;
		public Item item;
		public double rating;
		public double rmse=0;
		public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes
		public ArrayList<Node> nList=new ArrayList<Node>(); //sub graph nodes
		public  ArrayList<Rater> raterList = new ArrayList<Rater>();
		public   ArrayList<NonRater> nraterList = new ArrayList<NonRater>();
		public  ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
		public ArrayList<TestPair> testPairList = new ArrayList<TestPair>(); //	   test pair
		  public int k=100;//sample total numbers
		  public double dtime=0.04; //time slot
		  public int length=6;
		  public int nodeSize=2378;//49289;
		  public int index=0;  //0 is source itself
		 // NonRater sinkcopy;
		  double Ah=0, Bh=0, ABh=0, Al=0, Bl=0, ABl=0, Ph=0, Pl=0, Rh=0, Rl=0, Fh=0, Fl=0; //accuracy
		  
		  
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
		  
	 public dynFluid()
	 {
		 this.setDefaultNodes();
		 this.readTrust();
		 this.readRatings();
		 this.readTestPair();
	 }

	
	
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
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
	 public void readTrust()
	  {    
	        String s = null;
	       
	        File f = new File("trust-fortestpair.txt");
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
	                   
	               }
	               br.close();  
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read trust no file!");
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
	  public void readRatings()
	  {    
	        String s = null;
	        
	        File f = new File("ratings-fortestpair.txt");
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
	            System.out.println("readedges no file!");
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
		 this.countHop=0;
		 this.canPredict=0;
		 this.item.canPredict=false;
		 
		 
	  }
	  
	  int countCannotpredict=0; //find the first user/item can predict, stop. and see the result
	  //compare serveral method
	  public void compare()
	  {
		  Ah= Bh=ABh=Al=Bl= ABl=Ph= Pl= Rh= Rl=Fh= Fl=0; //accuracy
		  for(int i=0;i<nodeList.size();i++)  // first: only test the node 3 nodeList.size()
		  {
			   
			  this.source=nodeList.get(i);
			   for(int j=0;j<source.items.size();j++)
			   {		
				  
				  this.item=source.items.get(j);
				  if(this.checkTestPair(source, item)==false)
					   break;
				//  System.out.println(this.source.toString()+": "+ this.item.ItemID);
				  this.clear();
				  source.items.remove(j); //leave one out  
				  this.constructSubgraph();
				  
				  
				  if(this.canPredict==1)
					  this.item.prating[4]=  this.RatingPrediction();
				  source.items.add(j,this.item);
			   }
		  }
		  this.printAccuracy();
		 // this.printRating(); //print all rating  //not necessary
			 
	  }
	  
	  public boolean checkTestPair(Node source, Item item)
	  {
		  for(int i=0;i<testPairList.size();i++)
          {
        	  if(testPairList.get(i).source.equals(source.name)&&testPairList.get(i).item.equals(item.ItemID))
        		   return true;
          }
		  return false;
	  }
	  public void readTestPair()
	  {    
	        String s = null;
	        
	      
	       File f = new File("1214-testpair.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                   String [] edge = new String[2];		            	
		               edge = s.split("\t");            	
	                          
	                   TestPair tp=new TestPair(edge[0],edge[1]);
	                   testPairList.add(tp);
	                  
	                   
	               }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read test pairs no file!");
	            }
	           
	        
	    }
	// precision, recall, fscore
	  public void accuracy()
	  {
		  	// each pair of <node, item,rating>
  			for(int i=0;i<nodeSize;i++)
	    		for(int j=0;j<nodeList.get(i).items.size();j++)
	    		{
	    			double A = nodeList.get(i).items.get(j).rating;
	    			double B = nodeList.get(i).items.get(j).prating[4];	
	    			if(B==0)
	    				continue;
	    			
	    			if(A>=3)
	    				Ah++;
	    			else
	    				Al++;
	    			if(B>=3)
	    				Bh++;
	    			else
	    				Bl++;
	    			if(A>=3&&B>=3)
	    				ABh++;
	    			if(A<3&&B<3)
	    				ABl++;
	    			
	    				
	    		}
  			
  			if(Ah!=0)  //recall high
	    		Rh = ABh/Ah;
  			
	    	if(Bh!=0) //precision high
	    		Ph = ABh/Bh;
	    	
	    	if(Al!=0)  //recall low
	    		Rl = ABl/Al;
  			
	    	if(Bl!=0) //precision low
	    		Pl = ABl/Bl;
	    	
	    	if(Ph+Rh!=0)
	    		Fh=2*Ph*Rh/(Ph+Rh);
	    	if(Pl+Rl!=0)
	    		Fl=2*Pl*Rl/(Pl+Rl);
	    	
	  }
	  // compare the real rating and calculated rating
	    public void RMSE()
	    {
	    	double sumPower =0; //RMSE fenzi
	    	int  sumUR = 0; //RMSE fenmu
	    	
	    	// each pair of <node, item,rating>
	    		for(int i=0;i<nodeSize;i++)
		    		for(int j=0;j<nodeList.get(i).items.size();j++)
		    		{
		    			double prating=nodeList.get(i).items.get(j).prating[4];
		    			if(prating!=0)
		    			{
		    				sumPower+=Math.pow(nodeList.get(i).items.get(j).rating-prating, 2);
		    				sumUR++;	
		    			}
		    			
		    		}
		    	
		    	if(sumUR>0)
		    		rmse=Math.sqrt(sumPower/sumUR);
	    	
	    }
	    	  public void printAccuracy()
	 		 {
	    		  this.RMSE();
	    		  this.accuracy();
	 			 try 
	 	         {
	 	          
	 				
	 				 FileWriter writer = new FileWriter("1214-dynfluid-rmse.txt", true);
	 				 writer.write("\r\n k="+this.k+"\t dt="+this.dtime+"\t length="+this.length+"\r\n");
		 				
	 				 writer.write(this.rmse+"\t"); //10 methods rating
	 				
	 				 writer.write("\r\n");
	 				 writer.flush();
	 		         writer.close();  
	 				 
	 		         FileWriter writer1 = new FileWriter("1214-dynfluid-accuracy.txt", true);
	 				 writer1.write("\r\n k="+this.k+"\t dt="+this.dtime+"\t length="+this.length+"\r\n");
	 				
	 				 writer1.write(this.Rh+"\t"+this.Ph+"\t"+this.Fh+"\t"+this.Rl+"\t"+this.Pl+"\t"+this.Fl); 
	 				 writer1.write("\r\n"+Ah+"\t"+ Bh+"\t"+ABh+"\t"+Al+"\t"+Bl+"\t"+ABl+"\r\n");
	 				 
	 				 
	 				 writer1.flush();
	 		         writer1.close();  
	 				 
	 		               
	 			   }catch (Exception e1) {
	 			         e1.printStackTrace();
	 			  }	
	 			 
	 			 
	 		 }
	    	  
	  	public void printRating()
		 {
			 try 
	         {
	          
				
				 FileWriter writer = new FileWriter("1214-dynfluid-rating.txt", true);
				
				 for(int i=0;i<nodeList.size();i++)  // first: only test the node 35
				  {
					   this.source=nodeList.get(i);
					   for(int j=0;j<source.items.size();j++)
					   {
						   this.item=source.items.get(j);
						   writer.write("\r\n"+source.name+"\t"+item.ItemID+"\t"+item.rating+"\t"+item.prating[4]+"\r\n");
					   }
				  }
				 writer.flush();
		         writer.close();  
				 
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
			 
		 }
	     
	
	  
	  
	  public double getRating(Node n)
	  {
		  double rating=0;
		  for(int i=0;i<n.items.size();i++)
		  {
			  if(n.items.get(i).ItemID.equals(this.item.ItemID))
			  {
				  rating=n.items.get(i).rating;
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
	  int canPredict=0;  //if can find a subgraph,then it becomes 1
	  
	   int countHop=0;
	   ArrayList<Neighbor> path=new ArrayList<Neighbor>();
	   public void subgraph(Node source)

	   {

		  if(source.distance>=this.length)//&&countHop>=this.length

			   return;
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
	  
	   // flowrating initialization
	   public void initialization()
	   {
		   raterList.clear();
		   nraterList.clear();
		   pipeList.clear();
		  
		   NonRater nrater=new NonRater(source.name,source.b);
		   this.setup(nrater,this.source); 
		 
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
	   
	   
	   public void setup(NonRater nrater, Node source)
	   {
		   
		   nraterList.add(nrater); //add a nonrater
		   for(int i=0;i<source.subNeighbors.size();i++)
		   {
			   
			   Pipe pipe=new Pipe(source.subNeighbors.get(i).neighbor.name,source.name,source.subNeighbors.get(i).t);
			   nrater.inPipe.add(pipe); //trustful neighbors form its inpipe
			   pipeList.add(pipe); //add a pipe
			   if(source.subNeighbors.get(i).neighbor.wasSetup==false)
			   {
				  // if(this.checkRater(source.subNeighbors.get(i).neighbor)) //neighbor is a rater
				   if(source.subNeighbors.get(i).neighbor.ratingFlag==1)
				   {
						  
					   Rater rater = new Rater(source.subNeighbors.get(i).neighbor.name,this.getRating(source.subNeighbors.get(i).neighbor),source.neighbors.get(i).neighbor.b);
					   rater.outPipe.add(pipe);
					   raterList.add(rater); //add a rater
					   source.subNeighbors.get(i).neighbor.wasSetup=true;
				   }
				   else //neighbor is a nonrater
				   {
					   NonRater nrater1=new NonRater(source.subNeighbors.get(i).neighbor.name,source.neighbors.get(i).neighbor.b);
					   nrater1.outPipe.add(pipe);
					   source.subNeighbors.get(i).neighbor.wasSetup=true;
					   this.setup(nrater1,source.subNeighbors.get(i).neighbor);
				   }
			   }
			   else
			   {
				   if(source.subNeighbors.get(i).neighbor.ratingFlag==1) //neighbor is a rater
				   {
						  
					   Rater rater = this.SearchRater(source.subNeighbors.get(i).neighbor.name); 
					   if(rater!=null)
						   rater.outPipe.add(pipe);
					  // raterList.add(rater); //add a rater
					  // source.neighbors.get(i).neighbor.wasSetup=true;
				   }
				   else
				   {
					   NonRater nrater1=this.SearchNonRater(source.subNeighbors.get(i).neighbor.name);
					   if(nrater1!=null)
						   nrater1.outPipe.add(pipe);
				   }
				   
			   }
			   
		   }
		   
	   }
	  
	     public Rater SearchRater(String name)
		  {
			  Rater rater1=null;
			  
			  for(int i=0;i<raterList.size();i++)
			  {
				  if(raterList.get(i).rater.equals(name))
				  {
					  rater1 = raterList.get(i);
					  break;
				  }
			  }
			  
			  return rater1;
		  }
		   
	  public NonRater SearchNonRater(String name)
	  {
		  NonRater nrater1=null;
		  
		  for(int i=0;i<nraterList.size();i++)
		  {
			  if(nraterList.get(i).nonrater.equals(name))
			  {
				  nrater1 = nraterList.get(i);
				  break;
			  }
		  }
		  
		  return nrater1;
	  }
	   
	  
	  
	  public double RatingPrediction()
	   {
		   this.initialization();
		   double prating=0;
		   
		  // this.nraterIndex(); //random choose a nonrater and will print its heights
		   for(int i=0;i<k;i++)
		   {
			   prating = this.flowRating(i); 
			   		   
		   } 
		   
		   return prating;
	   }
	  
	     
	  public Rater searchFrom(String from)
	  {
		  for(int i=0;i<raterList.size();i++)
		  {
			  if(raterList.get(i).rater.equals(from))
				  return raterList.get(i);
		  }
		  return null;
	  }
	  
	  public NonRater nsearchFrom(String from)
	  {
		  for(int i=0;i<nraterList.size();i++)
		  {
			  if(nraterList.get(i).nonrater.equals(from))
				  return nraterList.get(i);
		  }
		  return null;
	  }
	  
	  public NonRater searchTo(String to)
	  {
		  for(int i=0;i<nraterList.size();i++)
		  {
			  if(nraterList.get(i).nonrater.equals(to))
				  return nraterList.get(i);
		  }
		  return null;
	  }
	  
	  //a time slot
	   public double flowRating(int k)
	   {
		   for(int i=0;i<pipeList.size();i++)
		   {
			   updatePipe(pipeList.get(i));
		   }
		   
		   for(int i=0;i<nraterList.size();i++)
		   {
			   updateNonRater(nraterList.get(i),k);
		   }
		  	return nraterList.get(0).t2; //source temperature 
	   }
	   
	   public void updatePipe(Pipe p)
	   {
		   Rater rfrom=this.searchFrom(p.from);
		   
		   NonRater nrfrom;
		   double hfrom=0, hto=0, existvolume=0; //heights of two connected containers
		   if(rfrom!=null)  //from is rater
		   {
			   hfrom=rfrom.h;//height
			   p.t=rfrom.t; //temperature
			   existvolume=rfrom.h*rfrom.b;
		   }
			   
		   else  //from is nonrater
		   {
			   nrfrom=this.nsearchFrom(p.from);
			   if(nrfrom!=null)
			   {
				   hfrom=nrfrom.h1;//current  height
				   p.t=nrfrom.t1; //current temperature
				   existvolume=nrfrom.h1*nrfrom.b;
			   }
		   }
		   
		   NonRater to=this.searchTo(p.to);
		   if(to!=null)
		   {
			   hto=to.h1;
		   }
		   
		   if(hfrom>hto&&hto>=0)
			   p.v=Math.sqrt(2*9.81*(hfrom-hto)); //
		   else
			   p.v=0;
		   
		   p.s=p.v*p.w*this.dtime;//volume=speed*pipe area *time slot
		   
		   // if not enough fluid in pfrom
		   if(p.s>existvolume) // flow out can not be larger than existing fluid
			   p.s=existvolume;  // in fact, not very accurate. so dt should be small enough 
	   }
	   
	 
	   
	  // double b=1;//cross-sectional area
	   public void updateNonRater(NonRater nrater, int k)
	   {
		   double s2=nrater.s1; //initial volume
		   double energy=nrater.s1*nrater.t1;
		   // flow in
		   for(int i=0;i<nrater.inPipe.size();i++)
		   {
			   //volume
			   s2+=nrater.inPipe.get(i).s;
			   //temperature-> energy
			   energy+=nrater.inPipe.get(i).s*nrater.inPipe.get(i).t;
		   }
		  
		 // flow out
		   for(int i=0;i<nrater.outPipe.size();i++)
		   {
			   if(s2<nrater.outPipe.get(i).s)
				   break;
			   //volume			   
			   s2-=nrater.outPipe.get(i).s;
			   //temperature-> energy
			   if(energy<nrater.outPipe.get(i).s*nrater.outPipe.get(i).t)
				   break;
			   energy-=nrater.outPipe.get(i).s*nrater.outPipe.get(i).t;
			  
		   }
		  
		   
		   
		   if(s2!=0)
			   nrater.t2=energy/s2;
		   else
			   nrater.t2=0;
		   
		   
		   nrater.s2=s2;
		   nrater.h2=s2/nrater.b;
		   
		   // rewrite s1,h1,t1 for next use
		   if(nrater.h2>10)
		   {
			   nrater.h2=10;
			   nrater.s2=nrater.h2*nrater.b;
		   }
		   nrater.s1=nrater.s2;
		   nrater.h1=nrater.h2;
		   nrater.t1=nrater.t2;
		   
	   }
	 
	  
	     public static void main(String[] args)
		 { 
			
	    	 dynFluid  s= new dynFluid();
			 s.setLength(3);
			 s.setDTime(0.04);
			 s.setK(10); //k=250,dt=0.04
			 s.compare();
		     
		 }
}

/*2013/12/12 dynFluid, extract new test pairs from new datasets with time information*/
 