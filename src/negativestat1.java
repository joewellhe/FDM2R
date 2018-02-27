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
 * select items and records with negative  ratings 
 */
public class negativestat1 {

	/**
	 * @param args
	 */
	public ArrayList<Item> itemList = new ArrayList<Item>(); // items contains negative ratings
	LinkedList<timePosNeg> posnegList = new LinkedList<timePosNeg>(); //sum of positive/negative at different time
	double thres=3; //threshold rating of positive and negative
	public negativestat1()
	{
		// this.setDefaultNodes();
		
		 this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 //this.printNegaCount();
		// this.stat_negative();
	}
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	        
	       // File f = new File("epinions_9user_itemtime.txt");
	        File f = new File("rating_epinions_byitem-time.txt");
	       // File f = new File("ciao-rating-byitemtime.txt");
	       
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	              //  FileWriter writer = new FileWriter("Ciao-negarating-byitemtime.txt", true); //items have negative ratings
	              //  FileWriter writer = new FileWriter("Epinions-9user-negarating-byitemtime.txt", true); //items have negative ratings
	                FileWriter writer = new FileWriter("Epinions-negarating-byitemtime.txt", true); //items have negative ratings
					
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[5];	 //no helpfulness	             	
		               edge = s.split("\t");            	
	                   double q=Double.parseDouble(edge[3]); //rating
	                   double t=Double.parseDouble(edge[4]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[1],q,t);
	                	   itemList.add(item);
	                	   if(q<this.thres)
	                	   {
	                		   item.negFlag=true;
	                		
	                		   writer.write(edge[1]+"\t"+q+"\t"+t+"\r\n");
	       					
	                	   }
	                	 
	                	   
	                   }// end first item
	                      
	                   else  {//other items
	                	   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])) //same item with last one
	                      { 
	                		   if(itemList.get(itemList.size()-1).negFlag==true) //already negative
	                			   writer.write(edge[1]+"\t"+q+"\t"+t+"\r\n"); //print current
	                		   else if(q<this.thres) //first negative
	                	      {
	                			   itemList.get(itemList.size()-1).negFlag=true; 
	                			  int k=1;
	                			  int s1 = itemList.size();
	                			   do {  //print before
	                				   k++;
								      } while(s1>=k&&itemList.get(s1-k).ItemID.equals(edge[1]));
	                			   for(int i=k-1;i>=1;i--)
	                			   {
	                				   writer.write(itemList.get(s1-i).ItemID+"\t"+itemList.get(s1-i).rating+"\t"+itemList.get(s1-i).time+"\r\n");
	                			   }
	                				  
	                			   writer.write(edge[1]+"\t"+q+"\t"+t+"\r\n"); //print current
	                			   				
	                		   }
	                		   else { //still positive
	                			   item = new Item(edge[1],q,t);
	    	                	   itemList.add(item);
							    }
	                	     } //end the same with last item
	                	     else {  //a new item
	                		  
	                	       item = new Item(edge[1],q,t);
	  	                	   itemList.add(item);
	  	                	   if(q<this.thres)
	  	                	   {
	  	                		   item.negFlag=true;
	  	                		
	  	                		   writer.write(edge[1]+"\t"+q+"\t"+t+"\r\n");
	  	       					
	  	                	   }
						       
	                         }//end a new item
	                   }//end other items
	                }//end while
	               br.close();
	               writer.flush();
			         writer.close(); 
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by item-time no file!");
	            }
	           	        
	    }
	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		negativestat1 ns = new negativestat1();
	}

}
