import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Jiang
 *count the negative and positive ratings of each item
 */
public class negativestat2 {

	/**
	 * 
	 */
	public ArrayList<Item> itemList = new ArrayList<Item>(); // items contains negative ratings
	LinkedList<timePosNeg> posnegList = new LinkedList<timePosNeg>(); //sum of positive/negative at different time
	double thres=3; //threshold rating of positive and negative
	public negativestat2()
	{
		// this.setDefaultNodes();
		
		 this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 this.printNegaCount();
		// this.stat_negative();
	}
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	        
	        File f = new File("Epinions-negarating-byitemtime.txt");
	       
	        //File f = new File("Ciao-negarating-byitemtime.txt");
	       
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	             	
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[3];	 //itemid, rating, time	             	
		               edge = s.split("\t");            	
	                   double q=Double.parseDouble(edge[1]); //rating
	                   double t=Double.parseDouble(edge[2]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[0],q,t);
	                	   
	                	   if(q<this.thres)
	                	   {
	                		   item.negCount++;
	                		       					
	                	   }
	                	   else {
	                		   item.posCount++;
						       }
	                	   itemList.add(item);
	                	   
	                   }// end first item
	                      
	                   else  {//other items
	                	   if(itemList.get(itemList.size()-1).ItemID.equals(edge[0])) //same item with last one
	                      { 
	                		    if(q<this.thres) //first negative
	                	      {
	                		    	itemList.get(itemList.size()-1).negCount++;  				
	                		   }
	                		   else { 
	                			   itemList.get(itemList.size()-1).posCount++;
							    }
	                	     } //end the same with last item
	                	     else {  //a new item
	                		  
	                	       item = new Item(edge[0],q,t);
	  	                	   itemList.add(item);
	  	                	   if(q<this.thres) //first negative
	  	                		{
		                		   item.negCount++;
		                		       					
		                	   }
		                	   else {
		                		   item.posCount++;
							       }
						       
	                         }//end a new item
	                   }//end other items
	                }//end while
	               br.close();
	                } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by item-time no file!");
	            }
	           	        
	    }
	 
	 public void printNegaCount() //print item-rating-time + - count
	 {
		 try 
	         {
				 FileWriter writer = new FileWriter("Epinions-negaCount-byitem.txt", true);
			   // FileWriter writer = new FileWriter("Ciao-negaCount-byitem.txt", true);
			    for(int i=0;i<itemList.size();i++)
				 {		
					 writer.write(itemList.get(i).ItemID+"\t"+itemList.get(i).negCount+"\t"+itemList.get(i).posCount+"\r\n");
					
				 }
				
				 writer.flush();
		         writer.close(); 
		        }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
	 }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		negativestat2 ns = new negativestat2();
	}

}
