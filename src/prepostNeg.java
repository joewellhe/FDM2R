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
 *the average rating before and after the first negative rating, by item
 */
public class prepostNeg {
	/**
	 * @param args
	 */
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items
	//LinkedList<timePosNeg> posnegList = new LinkedList<timePosNeg>(); //sum of positive/negative at different time
	 LinkedList <Item> curItem = new LinkedList<Item>();
	 //LinkedList <Item> ratingList = new LinkedList<Item>();
	double thres=3; //threshold rating of positive and negative
	
	
	public prepostNeg() {
		// TODO Auto-generated constructor stub
		this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 this.printPrePostAve();
	}

	
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	       // File f = new File("Epinions-negarating-byitemtime.txt");
	        File f = new File("Ciao-negarating-byitemtime.txt");
		          
	      //  File f = new File("Ciao-negarating-byitemtime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                	 String [] edge = new String[3];	 //itemid, rating, time	             	
			               edge = s.split("\t");            	
		                   double q=Double.parseDouble(edge[1]); //rating
		                  // double t=Double.parseDouble(edge[2]); //rating time
		                   double t=0;
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[0],q,t);
	                	   item.count++;
	                	   itemList.add(item);
	                	   curItem.add(item);  //read and process together
	                   }
	                      
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[0])) //same item with last one
	                   { 
	                	   
	                		   itemList.get(itemList.size()-1).count++;
	                		   
	                		   item = new Item(edge[1],q,t);
	                		   curItem.add(item);  //read and process together
	                   }
		               else  //new item time
		               { 		
		            	     this.compDistance(itemList.size()-1); 
		            	     
		            	     this.curItem.clear();
		            	     this.negIndex = -1;
		            	     
		                     item = new Item(edge[0],q,t);
		                     item.count++;  
		                     itemList.add(item);	
		                     curItem.add(item);  //read and process together
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
	 int reviewNumber=0; //# of reviews before and after the first negative rating
	 double aveDistance=0;
	 public void setNumber(int i)
	 {
		 this.reviewNumber=i;
	 }
	 
		 
	 int negIndex=-1; //index of the first negative rating
	 public void compDistance(int i)//, String itemID)
	 {
		 double curSum1=0; //pre sum ratings of reviewnumber current item
		 double curSum2=0; //post sum
		 
		 int j=itemList.get(i).count; //item's rating count
		 
		 
		 for(int l=0;l<j;l++)
		 {
			 if(curItem.get(l).rating<this.thres)
             {
				 this.negIndex = l;   //the first negative
				 break;
			 }
		 }
		 
		 for(int k=2;k<=10;k++)  //2-10 ratings'average compare to current one
		 {
			 curSum1=0; //pre sum ratings of reviewnumber current item
			  curSum2=0; //post sum
			 this.setNumber(k);
			 if(this.negIndex<this.reviewNumber||this.negIndex+this.reviewNumber>=j) //negIndex before and after need have at least reviewnumber  
				 return;
			 
			 //average of pre 
			 for(int o=this.negIndex-this.reviewNumber;o<this.negIndex;o++)
			 {
				 curSum1 += curItem.get(o).rating;
			 }
			 
			 	//average of post 
				 for(int q=this.negIndex+1; q<=this.reviewNumber+this.negIndex;q++)
				 {
					  curSum2 += curItem.get(q).rating;
				 }
			
				 itemList.get(i).avePre[k-2] = curSum1/this.reviewNumber;
				 itemList.get(i).avePost[k-2] = curSum2/this.reviewNumber;
				 
		 }
		
		 
	 }
	 
	 public void printPrePostAve() //print average before and after first negative
	 {
		 try 
	         {
				// FileWriter writer = new FileWriter("Epinions-neg-prepostave.txt", true);//-9user
				 FileWriter writer = new FileWriter("Ciao-neg-prepostave.txt", true);
				
				  for(int num=2;num<=10;num++) 
				    {
				    	
				    	for(int j=0;j<itemList.size();j++)
					   {
						 if(itemList.get(j).avePre[num-2]!=0&&itemList.get(j).avePost[num-2]!=0)
						   writer.write(itemList.get(j).ItemID+"\t"+itemList.get(j).avePre[num-2]+"\t"+itemList.get(j).avePost[num-2]+"\r\n");
						 writer.flush();
					  }
					   
				    }
				
		         writer.close();  
				 
		               
			   }catch (Exception e1) {
			         e1.printStackTrace();
			  }	
			 
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		prepostNeg ppN=new prepostNeg();
	}

}
