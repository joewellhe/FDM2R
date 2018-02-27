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
 * count the negative and positive ratings at different time
 */
public class negativestat {

	/**
	 * @param args
	 */
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items
	LinkedList<timePosNeg> posnegList = new LinkedList<timePosNeg>(); //sum of positive/negative at different time
	double thres=3; //threshold rating of positive and negative
	public negativestat()
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
	        
	       // File f = new File("rating_newformat_epinions_9itemtime.txt");
	        File f = new File("rating_epinions_byitem-time.txt");
	     // File f = new File("ciao-rating-byitemtime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[5];	 //no helpfulness	             	
		               edge = s.split("\t");            	
	                          
	                   //int o=Integer.parseInt(edge[0])-1;  //node/user
	                   double q=Double.parseDouble(edge[3]); //rating
	                   double t=Double.parseDouble(edge[4]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[1],q,t);
	                	   if(q<this.thres)
	                		   item.negCount++;
	                	   else {
	                		   item.posCount++;
						       }
	                	   itemList.add(item);
	                   }
	                      
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])&&t==itemList.get(itemList.size()-1).time) //same item and same time with last one
	                   { 
	                	   if(q<this.thres)
	                		   itemList.get(itemList.size()-1).negCount++;
	                	   else {
	                		   itemList.get(itemList.size()-1).posCount++;
						       }
	                   }
		               else  //new item time
		               { 		                   
		                     item = new Item(edge[1],q,t);
		                     if(q<this.thres)
		                		   item.negCount++;
		                	 else {
		                		   item.posCount++;
							       }  
		                     itemList.add(item);
		                   
	                   }
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by item-time no file!");
	            }
	           	        
	    }
	 
	 public int locate_time(double t)
	 {
		 for(int i=0;i<posnegList.size();i++)
		 {	
			 if(t==posnegList.get(i).time)
				 return i;
		 }
		 
		 return -1;
	 }
	 public void printNegaCount() //print item-rating-time + - count
	 {
		 try 
	         {
				 FileWriter writer = new FileWriter("Epinions-negaCount.txt", true);
				 FileWriter writer1 = new FileWriter("Epinions-negaCount-sum.txt", true);
				// FileWriter writer = new FileWriter("Ciao-negaCount.txt", true);
				// FileWriter writer1 = new FileWriter("Ciao-negaCount-sum.txt", true);
			     //int posSum=0;
			    // int negSum=0;
				 for(int i=0;i<itemList.size();i++)
				 {		
					 writer.write(itemList.get(i).ItemID+"\t"+itemList.get(i).time+"\t"+itemList.get(i).negCount+"\t"+itemList.get(i).posCount+"\r\n");
					// posSum += itemList.get(i).posCount;
					// negSum += itemList.get(i).negCount;
					 int location = this.locate_time(itemList.get(i).time);
					 if(posnegList.size()==0||location==-1) //new list or new time
					 {
						 timePosNeg tpn = new timePosNeg(itemList.get(i).time,itemList.get(i).posCount,itemList.get(i).negCount);
						 posnegList.add(tpn);
					 }
					 else   //find the time
					 {
						 posnegList.get(location).negCount += itemList.get(i).negCount;
						 posnegList.get(location).posCount += itemList.get(i).posCount;
					 }
				 }
				 for(int i=0;i<posnegList.size();i++)
				 {		
					 writer1.write(posnegList.get(i).time+"\t"+posnegList.get(i).negCount+"\t"+posnegList.get(i).posCount+"\r\n");
				 }
				 
				 writer.flush();
		         writer.close(); 
		         
		         writer1.flush();
		         writer1.close(); 
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		negativestat ns = new negativestat();
	}

}
