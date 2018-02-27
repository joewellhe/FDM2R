import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GainTestPair {

	/**
	 * @param args
	 */
	public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes

	 public int nodeSize=2378;//5;circle 6; small 398;large 49289;
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
		 }
	 }
	 
	 public GainTestPair()
	 {
		 this.setDefaultNodes();
		 this.readTrust();
		 this.printTrustCanPredict();
		 this.readRatings();
		 this.printRatingCanPredict();
		 
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
	        File f = new File("trust-fortestpair.txt");
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
	 
	 public void printTrustCanPredict()
		{
		     try 
		         {
		          
					
					 FileWriter writer = new FileWriter("trust-canpredict-new.txt", true);
					for(int i=0;i<nodeList.size();i++)
				   {
					   
					   for(int j=0;j<nodeList.get(i).neighbors.size();j++)
					   {
						  writer.write(nodeList.get(i).name+"\t"+nodeList.get(i).neighbors.get(j).neighbor.name+"\r\n");
						  
					   }
					   
					 
				   }
					 	 
					 //writer.write("\r\n");
					 writer.flush();
			         writer.close();  
			       
			               
				   }catch (Exception e1) {
				         e1.printStackTrace();
				  }	
				 
				 
			 }

	  public void readRatings()
	  {    
	        String s = null;
	        
	         File f = new File("ratings.txt");
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
	                  double time=Double.parseDouble(edge[3]);
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
	  
	  public void printRatingCanPredict()
		{
		     try 
		         {
		          
					
					 FileWriter writer = new FileWriter("ratings-fortestpair.txt", true);
					for(int i=0;i<nodeList.size();i++)
				   {
					   
					   for(int j=0;j<nodeList.get(i).items.size();j++)
					   {
						  writer.write(nodeList.get(i).name+"\t"+nodeList.get(i).items.get(j).ItemID+"\t"+nodeList.get(i).items.get(j).rating+"\t"+nodeList.get(i).items.get(j).time+"\r\n");
						  
					   }
					   
					 
				   }
					 	 
					 //writer.write("\r\n");
					 writer.flush();
			         writer.close();  
			       
			               
				   }catch (Exception e1) {
				         e1.printStackTrace();
				  }	
				 
				 
			 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GainTestPair g = new GainTestPair();
	}

}
