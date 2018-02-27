import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class trustorDegree {

	LinkedList<String> recordList=new LinkedList<String>();
	
	public trustorDegree()
	{
		this.readData();
		this.print();
	}
	
	 public void readData()
	  {    
	        String s = null;
	        
	         File f = new File("ciao-trustordegree.txt");
	        //File f = new File("test-ratings.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	
	                   String [] edge = new String[3];		            	
		               edge = s.split("\t");            	
	                   if(recordList.size()==0)
	                	   recordList.add(s);
	                   else
	                	   this.insertRecord(s);
	                  
	                 
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings no file!");
	            }
	           
	        
	    }
	 
	 public void insertRecord(String s)
	  {
		  String [] edge = new String[3];		            	
         edge = s.split("\t");
         
         String trustor = edge[0];
        // System.out.println(itemID);
         
         String r=recordList.getLast();
		  edge = r.split("\t");
		  if(trustor.equals(edge[0])) //exist
		  {
			  return;
		  }
		  
		  else
			  recordList.add(s);
	  }
	 
	 public void print()
		{
			 try 
	         {
	          
				
				 FileWriter writer = new FileWriter("ciao-trustor_deduplication.txt", true);
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
		trustorDegree cdf=new trustorDegree();
	}

	 
}
