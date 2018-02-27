import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class changeDataformat {

	public changeDataformat()
	{
		this.readData();
	}
	
	public void readData()
	  {    
	        String s = null;
	        
	       //  File f = new File("trust.txt");
	        // int k=3; //number of strings in a line
	         File f = new File("rating_with_timestamp.txt");
	         int k=7; //number of strings in a line
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                   String [] edge = new String[k];		            	
		               edge = s.split("  ");            	
	                    
		             //  System.out.println(edge[0]+";"+edge[1]+";"+edge[2]);
		               String [] edge1 = new String[k];	
		               edge1 = edge;
		               
		               BigDecimal bd = new BigDecimal("9.8310000e+003");  
		               
		               
		               int j=1;
		               while(j<k)
		               {
		            	  // System.out.println(j+":"+edge[j]);
		            	   bd = new BigDecimal(edge[j]);  
			               edge1[j]=bd.toPlainString();
			               int i = edge1[j].indexOf('.');
			               if(i!=-1)
			                edge1[j]=edge1[j].substring(0, i);
			               j++;
		               }
		               
		               
		              
		               
		               try 
				         {
				          
							
							 FileWriter writer = new FileWriter("rating_newformat.txt", true);
							 for(j=1;j<k;j++)
				               {
								  writer.write(edge1[j]+"\t");
				               }
							 writer.write("\r\n");
							 writer.flush();
					         writer.close();  
					       
					               
						   }catch (Exception e1) {
						         e1.printStackTrace();
						  }	
						 
	               }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("readedges no file!");
	            }
	           
	        
	    }
	  
	  
		     
				 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		changeDataformat cdf=new changeDataformat();
	}

}
