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
 *check the necessary review number by calculating the average distance
 */
public class ReviewNumber {

	/**
	 * 
	 */
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items
	LinkedList<timePosNeg> posnegList = new LinkedList<timePosNeg>(); //sum of positive/negative at different time
	 LinkedList <Item> curItem = new LinkedList<Item>();
	 LinkedList <Item> ratingList = new LinkedList<Item>();
	double thres=3; //threshold rating of positive and negative
	
	public ReviewNumber() {
		// TODO Auto-generated constructor stub
		this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 this.printDistance();
	}
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	        
	       // File f = new File("epinions_9user_itemtime.txt");
	        File f = new File("rating_epinions_byitem-time.txt");
	      //  File f = new File("ciao-rating-byitemtime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[5];	 //no helpfulness	             	
		               edge = s.split("\t");            	
	                          
	                   //int o=Integer.parseInt(edge[0])-1;  //node/user
	                   double q=Double.parseDouble(edge[3]); //rating
	                   double t=0;//Double.parseDouble(edge[4]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[1],q,t);
	                	   item.count++;
	                	   itemList.add(item);
	                	   curItem.add(item);  //read and process together
	                   }
	                      
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])) //same item with last one
	                   { 
	                	   
	                		   itemList.get(itemList.size()-1).count++;
	                		   
	                		   item = new Item(edge[1],q,t);
	                		   curItem.add(item); //read and process together
	                   }
		               else  //new item  //can process curItem
		               { 		        
		            	     this.compDistance(itemList.size()-1); 
		            	     
		            	     this.curItem.clear();
		     				/*for(int i=0;i<9;i++)
		            	     {
		     					itemList.get(itemList.size()-1).aveFlag[i] = false;
		            	     }*/
		     				 item = new Item(edge[1],q,t);
		                     item.count++;  
		                     itemList.add(item);	
		                     curItem.add(item);
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
	/**
	 * @param args
	 */
	 int reviewNumber=0;
	 double aveDistance=0;
	 public void setNumber(int i)
	 {
		 this.reviewNumber=i;
	 }
	 
	 	
	 public void compDistance(int i)
	 {
		 double curSum=0; //sum ratings of reviewnumber current item
		 double curAve=0;
		 int c=itemList.get(i).count; //item's rating count
		  for(int j=2;j<=10;j++)  //2-10 ratings'average compare to current one
		 {
			 this.setNumber(j);
			 
			 if(c<this.reviewNumber+1)
			 {
				// itemList.get(i).aveFlag = false;
				 return; //only consider items that have enough ratings
			 }
			 itemList.get(i).aveFlag[j-2] = true;
			 curSum=0; //sum ratings of reviewnumber current item
			 curAve=0;
			 			 
			 for(int o=this.reviewNumber-1;o>=0;o--)
			 {
				 curSum += curItem.get(o).rating;
			 }
			 curAve = curSum/this.reviewNumber;
			 itemList.get(i).aveDistance[j-2] = Math.abs(curAve-curItem.get(reviewNumber).rating);
			
		 }
			
		 
	
	 }
	 
	 public void printDistance() //print average distance
	 {
		 try 
	         {
			 FileWriter writer = new FileWriter("Epinions-reviewnumber-distance.txt", true);//-9user
				
				 // FileWriter writer = new FileWriter("Ciao-reviewnumber-distance.txt", true);
				    for(int num=2;num<=10;num++) 
				    {
				    	
				    	for(int j=0;j<itemList.size();j++)
					   {
						 if(itemList.get(j).aveFlag[num-2])
							 writer.write(itemList.get(j).ItemID+"\t"+itemList.get(j).aveDistance[num-2]+"\r\n");
						 writer.flush();
					  }
					   
				    }
					// System.out.println("review number "+i+" complete");
					
				 
				
		         writer.close();  
				 
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReviewNumber rn =new ReviewNumber(); 
	}

}
