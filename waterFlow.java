import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;


public class waterFlow {
	 static BufferedWriter bw = null;
		//****************MAIN FUNCTION***************************//
	public static void main(String args[]) throws IOException{
	List<String> myList = new ArrayList<String>();
	
	try {
	String fileName=args[1];
	Scanner scanner = new Scanner(new File(fileName));
	
	
	File file = new File("output.txt");
	if (!file.exists()) {
	file.createNewFile();
	}
	FileWriter fw = new FileWriter(file);
	bw = new BufferedWriter(fw);
	
	while (scanner.hasNextLine()) {
	String line = scanner.nextLine();
	Scanner lineScanner = new Scanner(line);
	lineScanner.useDelimiter("\n");
	while (lineScanner.hasNext()) {
	String part = lineScanner.nextLine();
	myList.add(part);
	} 
	lineScanner.close();
	
	}
	
	int num_cases= Integer.valueOf(myList.get(0));
	int j=1;
	Algo[] algos=new Algo[num_cases];
	for(int i=0;i<num_cases;i++){
	
	String algoname=String.valueOf(myList.get(j++));
	String startname=String.valueOf(myList.get(j++));
	String[] end_nodes= myList.get(j++).split("\\s+");
	String[] mid_nodes=new String[0];
	if(myList.get(j).matches(".*[a-zA-Z]+.*")){
	 mid_nodes= myList.get(j++).split("\\s+");
	}
	String date_space=myList.get(j++).trim();
	int num_edges=Integer.valueOf(date_space);				
	String[] edgesvalues=new String[num_edges];
	for(int g=0;g<num_edges;g++){
	edgesvalues[g]=myList.get(j++);
	}
	
	int start_time=Integer.valueOf(myList.get(j++));
	algos[i]= new Algo();
	algos[i].make_algo(algoname,startname,end_nodes,mid_nodes,num_edges,edgesvalues,start_time);
	HashMap<String,PriorityQueue<Connection>> map=prepareAdjacency(algoname, algos[i]);
	if(algoname.equals("BFS")){
	bfs_Search(algos[i],map,bw);
	}
	else
	if(algoname.equals("DFS")){
	dfs(algos[i],map,bw);
	}
	else if(algoname.equals("UCS")){
	ucs_Search(algos[i],map,bw);
	
	}
	}
	scanner.close();
	} 
	
	catch (Exception e) {
	e.printStackTrace();
	}
	finally
	{ 
	
	if(bw!=null)
	bw.close();
	
	}
	}
		    
										//*********FUNCTION TO MAKE ADJACENCY LIST MAP*********//
		static HashMap<String,PriorityQueue<Connection>> prepareAdjacency (String algo, Algo algothm){
			HashMap<String,PriorityQueue<Connection>> map= new HashMap<String,PriorityQueue<Connection> >();
			//ADJACENCY LIST MAP FOR BFS
			if(algo.equals("BFS")){
				for(int i=0;i<algothm.edges.length;i++){
					PriorityQueue<Connection> temp;
					if(map.containsKey(algothm.edges[i].start)){
						temp=map.get(algothm.edges[i].start);
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
						
					}
					else{
						temp=new PriorityQueue<Connection>(10,new bfsComparator());
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
						}
					}
				}
			//ADJACENCY LIST MAP FOR DFS
			else if(algo.equals("DFS")){
				for(int i=0;i<algothm.edges.length;i++){
					if(map.containsKey(algothm.edges[i].start)){
						PriorityQueue<Connection> temp=new PriorityQueue<Connection>(10,new dfsComparator());
						temp=map.get(algothm.edges[i].start);
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
				}
					else{
						PriorityQueue<Connection> temp=new PriorityQueue<Connection>(10,new dfsComparator());
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
					}
				}
			}
			//ADJACENCY LIST MAP FOR UCS
			else if(algo.equals("UCS")){
				for(int i=0;i<algothm.edges.length;i++){
					if(map.containsKey(algothm.edges[i].start)){
						PriorityQueue<Connection> temp=new PriorityQueue<Connection>(10,new ucsComparator());
						temp=map.get(algothm.edges[i].start);
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
				}
					else{
						PriorityQueue<Connection> temp=new PriorityQueue<Connection>(10,new ucsComparator());
						Connection newconn=new Connection();
						newconn.end=algothm.edges[i].end;
						newconn.len=algothm.edges[i].len;
						newconn.off_nums=algothm.edges[i].off_nums;
						newconn.offtimes=algothm.edges[i].offtimes;
						HashSet<Integer> temphash=makeSingleHashSet( algothm.edges[i].offtimes );
						newconn.hashset=temphash;
						temp.add(newconn);
						map.put(algothm.edges[i].start, temp);
				}
			}
		}
			// TO PRINT HASHMAP TO CHECK VALUES
		/*	for (String key : map.keySet()) {
				System.out.println("key" + key);
				PriorityQueue<Connection> q= map.get(key);
				int l=q.size();
				for(int i=0; i< l;i++)
					System.out.println(q.poll().end);
					//System.out.println(q.poll().offtimes);
	 	}*/
			return map;
			}
		
												//*********FUNCTION TO MAKE SINGLE LIST OF OFF TIME VALUES***********//
		static HashSet<Integer> makeSingleHashSet(List<List<Integer>> offtimes ){
			int size=offtimes.size();
			HashSet<Integer> result=new HashSet<Integer>();
			for(int i=0;i<size;i++){
				int start=offtimes.get(i).get(0);
				int end=offtimes.get(i).get(1);
				for(int j=start;j<=end;j++)
					result.add(j);
				}
		return result;
		}
		
		
												//**************BFS SEARCH ALGORITHM***************//
		static void bfs_Search(Algo algothm,HashMap<String,PriorityQueue<Connection>> map,BufferedWriter bw)throws
		 IOException{
			try{
				
				 HashMap<String,Integer> bfsFrontierMap=	new HashMap<String,Integer>();
				//System.out.println("BFS STARTS");
				Queue<String> bfsVisited = new LinkedList<String>();
				Queue<String> bfsFrontier = new LinkedList<String>();
				int total_time=algothm.start_time;
				
				bfsFrontier.add(algothm.start_node);
				bfsFrontierMap.put(algothm.start_node,total_time);
				while(!bfsFrontier.isEmpty()){
					boolean added=false;
					String node=bfsFrontier.remove();
					bfsVisited.add(node);
					for(String s: algothm.end_node){
						if(node.equals(s)){
							if(total_time>=24)
								total_time=(total_time%24);
							String result=node+" "+bfsFrontierMap.get(node);
							  bw.write(result);
						      bw.newLine();
						//	 System.out.println("result="+node+" "+bfsFrontierMap.get(node));
							return;
						}
					}
					if(map.containsKey(node)){
						int l=map.get(node).size();
						total_time=bfsFrontierMap.get(node)+1;
						for(int i=0; i< l;i++){
							String end=map.get(node).poll().end;
							if(!bfsFrontier.contains(end)&&!bfsVisited.contains(end)){
								added=true;
								bfsFrontier.add(end);
								if(total_time>=24)
									total_time=total_time%24;
								bfsFrontierMap.put(end,total_time);

							}
						}
						if(!added)
							total_time=bfsFrontierMap.get(node)-1;
							
						
					}
				}
					String result="None";
					bw.write(result);
			        bw.newLine();
					//System.out.println("None");
					return;
					
			}
			catch (IOException ioe) {
				   ioe.printStackTrace();
				}
		}
		
		
		
												//*******************DFS SEARCH ALGORITHM*********************//
			static Queue<String> dfsVisited = new LinkedList<String>();
			static List<String> visitedNodes = new ArrayList<String>();
			static boolean found=false;
			static HashMap<String,Integer> dfsFrontierMap=	new HashMap<String,Integer>();
			static int min_time=Integer.MAX_VALUE;
			static String result_node="";
			
			static void dfs(Algo algothm,HashMap<String,PriorityQueue<Connection>> map,BufferedWriter bw)throws
			 IOException{
				try{
			//	System.out.println("DFS STARTS");
				dfsVisited.add(algothm.start_node);
				dfsFrontierMap.put(algothm.start_node,algothm.start_time);
				dfs_Search(algothm,algothm.start_node,map,algothm.start_time);
				if(found==true){
					if(min_time>=24)
						min_time=(min_time%24);
					String result=result_node+" "+min_time;
					bw.write(result);
			         bw.newLine();
			//		System.out.println("result="+result_node+" "+min_time);
					dfsVisited.clear();
					visitedNodes.clear();
					dfsFrontierMap.clear();
					min_time=Integer.MAX_VALUE;
					result_node="";
					found=false;
				}
				else{
					String result="None";
					bw.write(result);
			         bw.newLine();
				//	System.out.println("None");
					dfsVisited.clear();
					visitedNodes.clear();
					dfsFrontierMap.clear();
					min_time=Integer.MAX_VALUE;
					result_node="";
					found=false;
				}
				 return;
				
				}
				 catch (IOException ioe) {
					   ioe.printStackTrace();
					}
			}
			
			static void dfs_Search(Algo algothm,String node,HashMap<String,PriorityQueue<Connection>> map, int total_time_dfs){
				if(!dfsVisited.isEmpty()){
					visitedNodes.add(node);
					for(String s: algothm.end_node){
						if(node.equals(s)){
								dfsVisited.clear();
								visitedNodes.clear();
								found=true;
								if(min_time!=dfsFrontierMap.get(node)){
								min_time=Math.min(min_time,dfsFrontierMap.get(node));
								if(!result_node.equals(node)){
									if(min_time==dfsFrontierMap.get(node))
										result_node=node;
										}
								}
								return;
						}
						if(map.containsKey(node)){
							int l=map.get(node).size();
							for(int i=0; i< l;i++){
								int total_time=dfsFrontierMap.get(node)+1;
								String end=map.get(node).poll().end;
								if(!dfsVisited.contains(end)){
								dfsVisited.add(end);
								dfsFrontierMap.put(end, total_time);
								dfs_Search(algothm,end,map,total_time);
								if(found==true)
									return;
								}
							
							}
						}
					}
				}
				return;
			}
			

									//***********************UCS SEARCH ALGORITHM***********************//
		static void ucs_Search(Algo algothm,HashMap<String,PriorityQueue<Connection>> map,BufferedWriter bw)throws
		 IOException{
			try{
				//System.out.println("UCS STARTS");
				Queue<String> ucsExplored = new LinkedList<String>();
				PriorityQueue<ucsFrontierElements> ucsFrontier = new PriorityQueue<ucsFrontierElements>(10,new ucsFrontierQueueComparator());
				HashMap<String,Integer> ucsFrontierMap=	new HashMap<String,Integer>();
				int total_time=algothm.start_time;
				ucsFrontierElements temp1=new ucsFrontierElements();
				temp1.Node=algothm.start_node;
				temp1.cost=total_time;
				ucsFrontier.add(temp1);
				ucsFrontierMap.put(algothm.start_node,total_time);
				
				while(!ucsFrontier.isEmpty()){
					String node=ucsFrontier.poll().Node;
					ucsExplored.add(node);
					for(String s: algothm.end_node){
						if(node.equals(s)){
							int result_value=0;
								if(ucsFrontierMap.get(node)>24)
									 result_value=ucsFrontierMap.get(node)%24;
								else
									 result_value=ucsFrontierMap.get(node);
							String result=node+" "+result_value;
							bw.write(result);
					        bw.newLine();
					//		System.out.println("result="+node+" "+result_value);
							return;
						}
					}
					if(map.containsKey(node)){
						int l=map.get(node).size();
						for(int i=0; i< l;i++){
							boolean c=false;
							int off_time_format=0;
							if(ucsFrontierMap.get(node)>=24)
							 off_time_format=ucsFrontierMap.get(node)%24;
							else
							 off_time_format=ucsFrontierMap.get(node);
							if(map.get(node).peek().hashset.contains(off_time_format)){
								c=true;
							}
							if(!c){
								int cost=map.get(node).peek().len;
								String end=map.get(node).poll().end;
								boolean b=false;
								boolean a=false;
								for(ucsFrontierElements e: ucsFrontier){
									if(e.Node.equals(end)){
										b=true;
									if(e.cost>(cost+ucsFrontierMap.get(node))){
										ucsFrontier.remove(e);
										a=true;
										}
									}
								}
									if(b&&a){
										ucsFrontierMap.put(end, cost+ucsFrontierMap.get(node));
										ucsFrontierElements temp3=new ucsFrontierElements();
										temp3.Node=end;
										temp3.cost=cost+ucsFrontierMap.get(node);
										ucsFrontier.add(temp3);
										}
									if(!b&&!ucsExplored.contains(end)){
									total_time=cost+ucsFrontierMap.get(node);
									ucsFrontierMap.put(end, total_time);
									ucsFrontierElements temp2=new ucsFrontierElements();
									temp2.Node=end;
									temp2.cost=total_time;
									ucsFrontier.add(temp2);
										}
									}
								else
									map.get(node).poll();
								
						}
					}
				}
					String result="None";
					bw.write(result);;
					bw.newLine();
					//System.out.println("None");
			}
			catch (IOException ioe) {
				   ioe.printStackTrace();
				}
		}
		
			
		}
	 												//*******************CLASS ALGORITHM************//
									               //This class stores all the values related to each algorithm after reading input file
		class Algo{
			String algo_name;
			String start_node;
			String[] mid_nodes;
			String[] end_node;
			int edge_num;
			Edge[] edges=new Edge[edge_num];
			int start_time;
			
			public void make_algo(String name,String start,String[] end,String[] mid_nodes, int edge_nums,String[] edgesval, int st_time){
				this.algo_name=name;
				this.start_node=start;
				this.mid_nodes=mid_nodes;
				this.end_node=end;
				this.edge_num=edge_nums;
				this.edges=new Edge[edge_num];
				for(int h=0;h<edge_nums;h++){
					String[] edge_val=edgesval[h].split("\\s+");
					String start_n=edge_val[0];
					String end_n=edge_val[1];
					int ed_len=Integer.valueOf(edge_val[2]);
					int ed_off_len=Integer.valueOf(edge_val[3]);
					String[] offs= new String[ed_off_len];
					if(ed_off_len!=0){
						int d=4;
						for(int f=0;f<ed_off_len;f++){
							offs[f]=edge_val[d++];
						}
						
					}
					this.edges[h]=new Edge();
					this.edges[h].make_edge(start_n,end_n,ed_len,ed_off_len,offs);
				}	
				this.start_time=st_time;
			}
			
		}
													//********************CLASS EDGE******************//
										           //This class stores all the values related to an edge
		class Edge{
			String start;
			String end;
			int len;
			int off_nums;
			List<List<Integer>> offtimes = new ArrayList<>();
			public void make_edge(String start,String end, int len,int off_num,String[] off_time){
				this.start=start;
				this.end=end;
				this.len=len;
				this.off_nums=off_num;
				if(off_num!=0){
					for(int i=0;i<off_nums;i++){
						String[] time=off_time[i].split("-");
						List<Integer> temp=new ArrayList<>();
						temp.add(Integer.valueOf(time[0]).intValue());
						temp.add(Integer.valueOf(time[1]).intValue());
						this.offtimes.add(temp);
					
					}
				}
			}
		}
		
							//************************CLASS CONNECTION(USED FOR ADJACENCY HASHMAP)****************************//
		class Connection{
			String end;
			int len;
			int off_nums;
			List<List<Integer>> offtimes = new ArrayList<>();
			HashSet<Integer> hashset=new HashSet<Integer>();
		}
		
							//*******************CLASS ucsFrontierElements for UCS frontier queue*****************************//
						   //This class stores node and its cost for UCS frontier queue
		class ucsFrontierElements{
			String Node;
			int cost;
		}
		
												//****************COMPARATOR FOR DFS ADJACENCY LIST******************//
		class dfsComparator implements Comparator<Connection>{
			public int compare(Connection obj1, Connection obj2){
				
				return obj1.end.compareTo(obj2.end);
				}
			}
		
												//****************COMPARATOR FOR BFS ADJACENCY LIST******************//
		class bfsComparator implements Comparator<Connection>{
			public int compare(Connection obj1, Connection obj2){
				return obj1.end.compareTo(obj2.end);
					
				}
			}
		
												//****************COMPARATOR FOR UCS ADJACENCY LIST******************//
		class ucsComparator implements Comparator<Connection>{
			public int compare(Connection obj1, Connection obj2){
				
				if(obj1.len<obj2.len)
					return -1;
				else if(obj1.len>obj2.len)
					return 1;
				else{
					return obj1.end.compareToIgnoreCase(obj2.end);
				}
			}
		}
		
											//****************COMPARATOR ucsFrontierQueue for UCS frontier queue******************//
		class ucsFrontierQueueComparator implements Comparator<ucsFrontierElements>{
			public int compare(ucsFrontierElements obj1, ucsFrontierElements obj2){
			 if(obj1.cost<obj2.cost)
				 return -1;
			 else if(obj1.cost>obj2.cost)
				 return 1;
			 else
				 return obj1.Node.compareToIgnoreCase(obj2.Node);
			}

}

