/*Jiang 2016/1/29  #trustors  #trustees*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;

public class countTrustor {
     int nodesize = 49290;
	//LinkedList<String> trustorList = new LinkedList<String>();
	//LinkedList<String> trusteeList = new LinkedList<String>();
	LinkedList<Integer> trustorList = new LinkedList<Integer>();
	LinkedList<Integer> trusteeList = new LinkedList<Integer>();
	
	public countTrustor()
	{
		for(int i=0;i<nodesize;i++)
		{
			trustorList.add(0);  //initial # 0
			trusteeList.add(0);  //initial # 0
			
		}
		this.readData();
		
		this.printCountList();
	}
	
	//read trust relations; increase #trustor #trustee by one each time
	public void readData()
	  {    
	        String s = null;
	        
	         File f = new File("trustnewformat.txt");
	         if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                   String [] edge = new String[2];		            	
		               edge = s.split("\t");            	
	                   int i1 = Integer.parseInt(edge[0]); //index of trustor
	                   int i2 = Integer.parseInt(edge[1]); //index of trustee
	                   
	                   int i3 = trustorList.get(i1)+1; //update # of trustor
	                   int i4 = trusteeList.get(i2)+1; //update # of trustee
		               trustorList.set(i1,i3);

		               trusteeList.set(i2,i4);
		               		 
	               }
	                //delete 0 items
	                while(trustorList.getLast()==0&&trusteeList.getLast()==0){
	                	trustorList.removeLast();
	                	trusteeList.removeLast();
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
			
			 FileWriter writer1 = new FileWriter("countList-epinions-outdegree.txt", true);
			  
			 int k = this.trustorList.size();
			 for(int i=0;i<k;i++)
				 writer1.write(i+"\t"+trustorList.get(i)+"\r\n");
			 
			 writer1.flush(); 
		     writer1.close(); 
		    
		     FileWriter writer2 = new FileWriter("countList-epinions-indegree.txt", true);
			  
			  k = this.trusteeList.size();
			 for(int i=0;i<k;i++)
				 writer2.write(i+"\t"+trusteeList.get(i)+"\r\n");
			 
			
		     writer2.flush(); 
		     writer2.close();
		  }catch (Exception e1) {
		         e1.printStackTrace();
	     }
	 }
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		countTrustor ct=new countTrustor();
	}

}
