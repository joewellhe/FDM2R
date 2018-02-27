
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.*;
import java.util.LinkedList;

public class degreedistribution{
 
	LinkedList<String> degreeList = new LinkedList<String>();
	LinkedList<Integer> countList = new LinkedList<Integer>();
	public degreedistribution()
	{
		this.readDegree();
		this.stat();
		this.printCountList();
	}
	
	public void readDegree()
	  {    
	        String s = null;
	        StringBuffer sb = new StringBuffer();
	      
	        //File f = new File("Epinions-coratingsize-item.txt");
	        //File f = new File("Epinions-coratingsize-user-delete1and2.txt");
	        //File f = new File("Ciao-coratingsize-item.txt");
	       // File f = new File("Ciao-coratingsize-user.txt");
	       // File f = new File("Ciao-coratingsize-60min-user-delete12.txt");
	       // File f = new File("Ciao-coratingsize-60min-item-delete12.txt");
	       // File f = new File("Ciao-coratingsize-1440min-item-delete12.txt");
	      //  File f = new File("Ciao-coratingsize-1440min-user-delete12.txt");
	       // File f = new File("Ciao-coratingsize-10080min-item-delete12.txt");
	       //File f = new File("epinions-coratingsize-60min-item-delete12.txt");
	       // File f = new File("epinions-coratingsize-1440min-user-delete12.txt");
	        File f = new File("epinions-coratingsize-10080min-user-delete1-4.txt");
	          
	           if (f.exists()) {
	           // System.out.println("该文件存在");
	            try {
	                BufferedReader br =
	                new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	               
	               // FileWriter writer = new FileWriter("degreedistribution.txt", true);
	   			
	                while ((s = br.readLine()) != null) {
	                	
	                	degreeList.add(s);
	                
	               }
	                
	               // writer.flush();
	   	   	      //  writer.close(); 
	                      
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("file not found!");
	            }
	           
	    }
	public void stat() //count the occur times
	{
		countList.clear();
		countList.add(1);
		for(int i=0;i<degreeList.size()-1;i++)
		{
			if(degreeList.get(i+1).equals(degreeList.get(i)))		
			{
				int temp = countList.getLast();
				temp++;
				countList.set(countList.size()-1, temp);
			}
			else
				countList.add(1);
		}
	}
	
	public void printCountList()
	 {
		 try 
        {
			
			 FileWriter writer1 = new FileWriter("countList-epinions-10080min-corate-user.txt", true);
			  
			 int k = this.countList.size();
			 for(int i=0;i<k;i++)
				 writer1.write(countList.get(i)+"\r\n");
			 
			 writer1.flush(); 
		     writer1.close();  
		                     
		  }catch (Exception e1) {
		         e1.printStackTrace();
	     }
	 }
	 public static void main(String[] args)
	 { 
		 		 
		 degreedistribution dd= new degreedistribution();
		 
		 
	 }
}
