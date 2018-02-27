/*20180127 calculating support and competitive ratio*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;


public class itemRelation {
	public ArrayList<Node> nodeList = new ArrayList<Node>(); //all nodes
	public ArrayList<Item> itemList = new ArrayList<Item>(); //all items
	public int nodeSize=2378;//22166;////22166;//9;////2378;//5;circle 6; small 398;large 49289;
	Node sink = new Node("");
	Item targetItem = new Item("",-1,0);
	double time=0;
	double delta = 60;//1440;//10080; //30min, 60 min, (1440min)24 hours,(10080min)1 week
	LinkedList<Item> corate_items = new LinkedList<Item>();;  //co_rated items
	//LinkedList<Node> corate_nodes = new LinkedList<Node>();  //co_rated nodes with sink
	LinkedList<Node> corate_nodesij = new LinkedList<Node>();  //co_rated nodes of item i and item j
	
	 public void setDefaultNodes()
	 {
		 for(int i=0;i<nodeSize;i++)
		 {
			 
			 Node n=new Node(Integer.toString(i+1));
			 nodeList.add(n);
			 
		 }
	 }
	 
	public itemRelation()
	{
		 this.setDefaultNodes();
		
		 this.readRatings_itemtime();  //get itemList, for each item: <reviewer, rating, time>
		 System.out.println("read file1 complete");
		 this.readRatings_usertime();  //get userList, for each user: <item, rating, time>
		 System.out.println("read file2 complete");
		 this.calculateRatio_corating();
	}
	
	 public void readRatings_itemtime()
	  {    
	        String s = null;
	        
	        // File f = new File("epinions_9user_itemtime.txt");
	       // File f = new File("rating_epinions_byitem-time.txt");
	        File f = new File("ciao-rating-byitemtime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	Item item;
	                   String [] edge = new String[5];	 //no helpfulness	             	
		               edge = s.split("\t");            	
	                          
	                   int o=Integer.parseInt(edge[0])-1;  //node/user
	                   double q=Double.parseDouble(edge[3]); //rating
	                   double t=Double.parseDouble(edge[4]); //rating time
	                   if(itemList.size()==0) //the first item
	                   {    
	                	   item = new Item(edge[1],q,t);
	                	   item.nodes.add(nodeList.get(o));
	                	   int k=item.nodes.size()-1;
	                	    item.nodes.get(k).setNode(q,t); //set node's rating and time	                	   
	                	   itemList.add(item);
	                   }
	                  // if(this.checkItemNodeDuplication(nodeList.get(o),item)) //if already exist, go to next
	                	//   continue;
	                   
	                   else   if(itemList.get(itemList.size()-1).ItemID.equals(edge[1])) //same item with last one
	                   { 
	                	   itemList.get(itemList.size()-1).nodes.add(nodeList.get(o)); 
	                	   int k=itemList.get(itemList.size()-1).nodes.size()-1;
	                	   itemList.get(itemList.size()-1).nodes.get(k).setNode(q,t);
	                	   item = new Item(edge[1],q,0);// an item's node list
                   
	                   }
		               else  //new item
		               { 		                   
		                     item = new Item(edge[1],q,0);
		                     item.nodes.add(nodeList.get(o));
		                	 int k=item.nodes.size()-1;
		                	 item.nodes.get(k).setNode(q,t); //set node's rating and time	                	   
		                	  
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
	 
	 public void readRatings_usertime()
	  {    
	        String s = null;
	        
	       //  File f = new File("rating_newformat_epinions_9usertime.txt");
	       // File f = new File("rating_epinions_byuser-time.txt");
	        File f = new File("ciao-rating-byusertime.txt");
	        if (f.exists()) {
	          
	            try {
	                BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	                while ((s = br.readLine()) != null) {
	                     
	                	   Item item;
		                   String [] edge = new String[5];	 //no helpfulness	             	
			               edge = s.split("\t");            	
		                          
		                   int o=Integer.parseInt(edge[0])-1;  //node/user
		                   double q=Double.parseDouble(edge[3]); //rating
		                   double t=Double.parseDouble(edge[4]); //rating time
		                    //build  itemlist for each user
			               { 		                   
			                     item = new Item(edge[1],q,t);
			                     nodeList.get(o).items.add(item);
			                	 		                   
		                   }
	                	   
	                }
	               br.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("read ratings by user-time no file!");
	            }
	           	       
	    }
	 
	 public boolean checkItemDuplication(Node n, String ItemID)
	   {
		   boolean flag=false; //default nonrater
		   for(int i=0;i<n.items.size();i++)
		   {
			   if(n.items.get(i).ItemID.equals(ItemID))
			   {
				   flag=true;
				   break;
			   }
		   }
		   
		   return flag;
	   }
	 
	 public void set_sink(Node sink, Item targetItem, double time)
	 {
		 this.sink = sink;
		 this.targetItem = targetItem;
		 this.time = time;
	 }
	 
	  public int itemPosition()
	 {
			 int p=-1; //index of target item in itemList
			 for(int i=0;i<itemList.size();i++)
			 {
				 if(itemList.get(i).equals(targetItem))
				 {
					p=i;
					 break;
				 }
			 }
			 return p;
	 }
	  public int nodePosition(String n)
		 {
				 int p=-1; //index of a node in nodeList
				 for(int i=0;i<nodeList.size();i++)
				 {
					 if(nodeList.get(i).name.equals(n))
					 {
						p=i;
						 break;
					 }
				 }
				 return p;
				 /*can be simplified, since nodelist is from 1 to size, so p=n*/
		 }
	  
	  public void calculate_ratio_sink()
	  {
		 // int p = this.itemPosition(); //targetItem's index in itemList
		  int k=targetItem.nodes.size(); //targetItem's reviewers (include sink)
		 	 
		  for(int r=0;r<k;r++) //reviewers
		 {    
			 // int p1=this.nodePosition(targetItem.nodes.get(r).name);
			 // if(p1==-1) //no node
				//  break;

			  for(int i1=0;i1<targetItem.nodes.get(r).items.size();i1++) //reviewers' rated items
			  {					  
				  double t1=targetItem.nodes.get(r).items.get(i1).time;
				  if(this.time-this.delta<=t1 &&t1<=this.time) //only items in a time session
				  {
						
						if( this.corate_items.size()==0||!this.corate_items.contains(targetItem.nodes.get(r).items.get(i1)))
							this.corate_items.add(targetItem.nodes.get(r).items.get(i1));
				  }
		      }//reviewers' rated items
			 	
			  int pflag = -1;
			  for(int i2=0;i2<this.corate_items.size();i2++)
			  {
				  pflag = this.calculate_ratio(this.targetItem, this.corate_items.get(i2)); //ratio for (targetitem, sink) pair
				  if(pflag==1) this.printRatio();
				  this.ratio1=this.ratio2=0;
			  }
			  
	     }
	  }
	  
	  public int nodePosition(String n, ArrayList<Node> ns)
		 {
				 int p=-1; //index of a node in nodeList
				 for(int i=0;i<ns.size();i++)
				 {
					 if(ns.get(i).name.equals(n))
					 {
						p=i;
						 break;
					 }
				 }
				 return p;
		 }
	  
	  double ratio1=0.0; //support ratio
	  double ratio2=0.0; //competitive ratio
	  public int calculate_ratio(Item i1, Item i2)
	  {
		  
		  int sum1 = i1.nodes.size();
		  int sum2 = 0;  //this.corate_nodes.size();
		  double rd = 0;//rating distance of co-raters (i1,i2)
		  int flag =-1;
		  for(int i=0;i<sum1; i++) //reviewers of  item i1
		  {
			  flag = this.nodePosition(i1.nodes.get(i).name, i2.nodes);
			  if(flag!=-1)
			  {  sum2++;
			     rd += i2.nodes.get(flag).rating-i1.nodes.get(i).rating;
			     System.out.println(sum2+"; rd:"+rd);
			  }
			  
		  }
		  
		  if(sum2==0)
			  return -1;
		  else {
			this.ratio1 = sum2/sum1;  //support ratio
		    this.ratio2 = rd/(5*sum2);  //competitive ratio
		    return 1;
		}
		  
	  }
	  
	  public void calculateRatio_corating()
	 {
		 int treat=0; //alreadytreated
		 for(int i=0;i<itemList.size();i++)
		 {
			 int k=itemList.get(i).nodes.size();
			 
			 for(int r=0;r<k;r++) //reviewers
			 {
				 double t=itemList.get(i).nodes.get(r).time;
				 this.set_sink(itemList.get(i).nodes.get(r),itemList.get(i),t);
				 
				// int n = Integer.parseInt(itemList.get(i).nodes.get(r).name)-1;  //index of sink
				 this.calculate_ratio_sink(); //check support and competitive ratios for current sink and target item
				
				 treat++;
				 if(treat%1000==0)  //print every 1000 treated
				   System.out.println(""+treat);
				 this.corate_items.clear(); //clear for next
				 this.corate_nodesij.clear(); //clear for next
				 this.ratio1=this.ratio2=0;
			  } //reviewers
		      
			 
		 }
		 
		// System.out.println("sum="+sum); // number of trust relations that rates on same items
	 }
	 
	  public void printRatio() //print ratios
		 {
			 try 
		         {
					// FileWriter writer = new FileWriter("Epinions-ratio-60min.txt", true);
					 FileWriter writer = new FileWriter("Ciao-ratio-60min.txt", true);
						
					 writer.write(this.ratio1+"\t"+this.ratio2+"\r\n");
					 writer.flush();
			         writer.close();  
					 
			               
				   }catch (Exception e1) {
				         e1.printStackTrace();
				  }	
				 
		 }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       itemRelation ir=new itemRelation();
	}


}
