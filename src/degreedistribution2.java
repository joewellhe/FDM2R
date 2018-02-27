/*Jiang 2016/1/29  degree distribution of trust relationship#trustors  #trustees*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class degreedistribution2 {

	 int nodesize = 296277+1; //item  in epinions  //22166+1; user in epinions
		LinkedList<Integer> degreeList = new LinkedList<Integer>();
		
		
		public degreedistribution2()
		{
			for(int i=0;i<nodesize;i++)
			{
				degreeList.add(0);  //initial # 0
				
				
			}
			this.readData();
			
			this.printCountList();
		}
		
		//read trust relations; increase #trustor #trustee by one each time
		public void readData()
		  {    
		        String s = null;
		        File f = new File("countList-epinions-ratingPeritem.txt");
		        // File f = new File("countList-epinions-ratingPeruser.txt");
		        //File f = new File("countList-epinions-indegree.txt");
		       
		        // File f = new File("countList-epinions-outdegree.txt");
		         if (f.exists()) {
		          
		            try {
		                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		                while ((s = br.readLine()) != null) {
		                     
		                   String [] edge = new String[2];		            	
			               edge = s.split("\t");            	
		                   //int i1 = Integer.parseInt(edge[0]); //index of trustor
		                   int i2 = Integer.parseInt(edge[1]); //index of trustee
		                   
		                   //int i3 = trustorList.get(i1)+1; //update # of trustor
		                   int i4 = degreeList.get(i2)+1; //update # of trustee
			               //trustorList.set(i1,i3);

		                   degreeList.set(i2,i4);
			               		 
		               }
		                //delete 0 items
		                int i=0;
		                while(i<degreeList.size()){
		                	if(degreeList.get(i)==0)
		                		degreeList.remove(i);
		                	else
		                		i++;
		                }
		               br.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        } else {
		            System.out.println("readedges no file!");
		            }
		        
		        
		    }
		
		
		public void printCountList()
		 {
			 try 
	        {
				
				 FileWriter writer1 = new FileWriter("epinions-ratingPeritem-distribution.txt", true);
				  
				 int k = this.degreeList.size();
				 for(int i=1;i<k;i++)
					 writer1.write(i+"\t"+degreeList.get(i)+"\r\n");
				 
				 writer1.flush(); 
			     writer1.close(); 
			    
			  }catch (Exception e1) {
			         e1.printStackTrace();
		     }
		 }
		
		
		
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			degreedistribution2 dd=new degreedistribution2();
		}


}
