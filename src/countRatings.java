/*Jiang 2016/1/30  #ratings per user and per item*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class countRatings {
	int nodesize = 22166+1;
	int itemsize = 296277+1;
	//LinkedList<String> trustorList = new LinkedList<String>();
	//LinkedList<String> trusteeList = new LinkedList<String>();
	LinkedList<Integer> userList = new LinkedList<Integer>();
	LinkedList<Integer> itemList = new LinkedList<Integer>();
	
	//read trust relations; increase #trustor #trustee by one each time
	public void readData()
	  {    
	        String s = null;
	        
	         File f = new File("rating_newformat.txt");
	         if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                   String [] edge = new String[6];		            	
		               edge = s.split("\t");            	
	                   int i1 = Integer.parseInt(edge[0]); //index of user
	                   int i2 = Integer.parseInt(edge[1]); //index of product
	                   
	                   int i3 = userList.get(i1)+1; //update # of user
	                   int i4 = itemList.get(i2)+1; //update # of product
		               userList.set(i1,i3);

		               itemList.set(i2,i4);
		               		 
	               }
	                //delete 0 items
	                while(userList.getLast()==0){
	                	userList.removeLast();
	               
	                }
	                while(itemList.getLast()==0){
	                	itemList.removeLast();
	               
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("readedges no file!");
	            }
	        
	        
	    }
	
	
	public void printCountList()
	 {
		 try 
        {
			
			 FileWriter writer1 = new FileWriter("countList-epinions-ratingPeruser.txt", true);
			  
			 int k = this.userList.size();
			 for(int i=1;i<k;i++)
				 writer1.write(i+"\t"+userList.get(i)+"\r\n");
			 
			 writer1.flush(); 
		     writer1.close(); 
		    
		     FileWriter writer2 = new FileWriter("countList-epinions-ratingPeritem.txt", true);
			  
			  k = this.itemList.size();
			 for(int i=1;i<k;i++)
				 writer2.write(i+"\t"+itemList.get(i)+"\r\n");
			 
			
		     writer2.flush(); 
		     writer2.close();
		  }catch (Exception e1) {
		         e1.printStackTrace();
	     }
	 }
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		countRatings  ct=new countRatings();
	}

	public countRatings()
	{
		for(int i=0;i<nodesize;i++)
		{
			userList.add(0);  //initial # 0
			
			
		}
		for(int i=0;i<itemsize;i++)
		{
			itemList.add(0);  //initial # 0
			
			
		}
		this.readData();
		
		this.printCountList();
	}

}
