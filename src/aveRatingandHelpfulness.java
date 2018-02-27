import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class aveRatingandHelpfulness {

LinkedList<String> recordList=new LinkedList<String>();
double sumR=0.0; //sum of ratings
double sumH=0.0; //sum of Helpfulness
double aveR=0.0;
double aveH=0.0;
int num;
	public aveRatingandHelpfulness()
	{
		
		for(int i=1;i<35;i++)
		{
			this.sumR=0;
			this.sumH=0; //initialization
			this.num=0;
			this.readData(i);
			this.print(i);
		}
		
	}
	
	 public void readData(int i)
	  {    
	        String s = null;
	        
	         File f = new File("rating_newformat.txt");
	        //File f = new File("test-ratings.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	
	                   String [] edge = new String[3];		            	
		               edge = s.split("\t"); 
		               int c=Integer.parseInt(edge[2]);
		               double r,h;
	                   if(c==i) //category i
	                   {
	                	   num++;
	                	    r= Double.parseDouble(edge[3]);
	                	    h= Double.parseDouble(edge[4]);
	                	    this.sumR += r;
	                	    this.sumH += h;
	                	    
	                   }
	                  
	                 
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings no file!");
	            }
	           
	        
	    }
	 
	
	 public void print(int i)
		{
			 try 
	         {
	          
				
				 FileWriter writer = new FileWriter("Epinions-category_aveRatingandHelpful.txt", true);
				 
				 if(this.num==0)
					 return;
				 this.aveR = this.sumR/this.num;
				 this.aveH = this.sumH/this.num;
				 
				 writer.write("Category:\t"+i+"\t"+this.num+"\t"+this.sumR+"\t"+this.sumH+"\t"+this.aveR+"\t"+this.aveH+"\r\n");
	             
				 writer.flush();
		         writer.close();  
		       
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
		}
				 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		aveRatingandHelpfulness cdf=new aveRatingandHelpfulness();
	}

}
