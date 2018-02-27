import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;
public class sortRecords {
	
	LinkedList<String> recordList=new LinkedList<String>();
	
	public sortRecords()
	{
		this.readData();
		this.print();
	}
	
	public void readData()
	  {    
	        String s = null;
	        
	       //  File f = new File("trust.txt");
	        // int k=3; //number of strings in a line
	         File f = new File("rating_newformat.txt");
	         
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                    
	                	if(recordList.size()==0)
	                		recordList.add(s);
	                	else
	                	{
	                       
	                       this.insertRecord(s);
	                	}
		               //compare itemId
		                
	               }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("readedges no file!");
	            }
	           
	        
	    }
	  
	  public void insertRecord(String s)
	  {
		  String [] edge = new String[6];		            	
          edge = s.split("\t");
          
          int itemID = Integer.parseInt(edge[1]);
         // System.out.println(itemID);
          
          String r=recordList.get(0);
		  edge = r.split("\t");
		  if(itemID<Integer.parseInt(edge[1])) //current record is smaller than the first
		  {
			  recordList.add(0,s);  //add as the first
			  return;
		  }
		  
		  /*r=recordList.getLast();
		  edge = r.split("\t");
		  if(itemID>=Integer.parseInt(edge[1])) //current record is smaller than the first
		  {
			  recordList.add(s);  //add as the last
			  return;
		  }*/
		  
		  for(int i=recordList.size()-1;i>=0;i--)
		  {
			  r=recordList.get(i);
			  edge = r.split("\t");
			  if(itemID>=Integer.parseInt(edge[1])) //current record is not small
			  {
				  recordList.add(i+1,s);  //add directly
				  return;
			  }
			  else //current record itemID is smaller
				  continue; // continue to previous record
		  }
	  }
		public void print()
		{
			 try 
	         {
	          
				
				 FileWriter writer = new FileWriter("rating_sorted.txt", true);
				 for(int j=0;j<recordList.size();j++)
	               {
					  writer.write(recordList.get(j)+"\r\n");
	               }
				 
				 writer.flush();
		         writer.close();  
		       
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
		}
				 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sortRecords cdf=new sortRecords();
	}

}
