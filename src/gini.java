import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class gini {
	int p = 13; //#province
	double [][] percent= new double [p][4]; // percent in a year 
	double [][] ave = new double [20][4]; //average of 4 choices in 20 year
	double [] gini= new double [4];
	
	public gini()
	{
		this.readPercent();
	}
	
	
	public void readPercent()
	  {    
		 
		 this.readAverage();
	        String s = null;
	        
	         File f = new File("gini.txt");
	       
	        if (f.exists()) {
	          int i=0;
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	
	                   String [] edge = new String[52];		            	
		               edge = s.split("\t");            	
	                          
	                for(int o=0;o<p;o++) //13 provinces  
	                for(int j=0;j<4;j++) //4 choices
	                 {
	                   double q=Double.parseDouble(edge[o*4+j]); //rating
	                   percent[o][j]=q;
	                 }
	                
	                for(int k=0;k<4;k++)
	                {
	                	gini[k]=this.computegini(i, k);
	                }
	                
	                System.out.println(gini[0]+"\t"+gini[1]+"\t"+gini[2]+"\t"+gini[3]);
	                i++;
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read gini no file!");
	            }
	           
	        
	    }
	
	 public void readAverage()
	  {    
		
		 String s = null;
	        
	         File f = new File("ave.txt");
	       
	        if (f.exists()) {
	        	int i=0;
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	
	                   String [] edge = new String[4];		            	
		               edge = s.split("\t");            	
		                       	
	                          
	                 
	                for(int j=0;j<4;j++) //4 choices
	                 {
	                   double q=Double.parseDouble(edge[j]); //rating
	                  // System.out.println(q);
	                   ave[i][j]=q;
	                 }      
	               i++;
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read gini no file!");
	            }
	           
	        
	    }
	 
	 
	public double computegini(int y, int k) // year, choice
	{
		double sum=0.0;
		for(int i=0;i<p;i++) //p provinces
        	 for(int j=0;j<p;j++) //p provinces
         {
            sum+= Math.abs(percent[i][k]-percent[j][k]);
         }
        
		double gi=sum/(2*p*p*ave[y][k]);
		return gi;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		gini g=new gini();
		
	}

}
