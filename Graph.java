import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Graph {
	private HashMap hm;
	private String[] vertexArray;
	private Edge[] adjacencyList;
	private CostList costList;
	private int[] disjoint ;
	private Edge[] kruskal;
	private Edge[] prim;
	private int size;

	Graph(String filename, int sizeVertexArray, int sizeHM){
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}

		vertexArray = new String[sizeVertexArray];
		hm = new HashMap(sizeHM);
		int i = 0;
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			if(line.equals("."))
				break;
			vertexArray[i] = line;
			hm.add(line.trim(), i);
			i++;
		}

		size = i;
		adjacencyList = new Edge[size];
		while(scan.hasNextLine()){
			int vertex1 = hm.find(scan.nextLine().trim());
			int vertex2 = hm.find(scan.nextLine().trim());
			int cost = scan.nextInt();
			scan.nextLine();
			addEdge(vertex1, vertex2, cost, adjacencyList);
			addEdge(vertex2, vertex1, cost, adjacencyList);
			costList = addCostList(vertex1, vertex2, cost);
		}
	}

	private  CostList addCostList(int vertex1, int vertex2, int cost) {
		CostList newCost = new CostList(vertex1, vertex2, cost);
		if (costList == null)
			return newCost;
		if (costList.cost > cost){
			newCost.next = costList;
			return newCost;
		}
		CostList temp = costList;
		if (temp.next != null){
			for (; temp.next != null && temp.next.cost < cost; temp = temp.next);
		}
		newCost.next = temp.next;
		temp.next = newCost;
		return costList;
	}


	public void printOriginalGraph(){
		printGraph(adjacencyList);
	}
	public void printKruskal(){
		printGraph(kruskal);
	}
	public void printPrim(){
		printGraph(prim);
	}


	private void printGraph(Edge[] list){
		for(int i = 0; i < list.length; i++){
			System.out.print(vertexArray[i] + " ---> ");
			Edge temp = list[i];
			if (temp != null){
				for (; temp != null; temp = temp.next){
					System.out.print(vertexArray[temp.neighbor] + " ");
					System.out.print(temp.cost + " ");
				}
				System.out.println();
			}
		}
	}


	private void addEdge(int vertex1, int vertex2, int cost, Edge[] list) {
		Edge temp = list[vertex1];
		list[vertex1] = new Edge(vertex2, cost);
		list[vertex1].next = temp;
	}



	public void kruskal(){
		disjoint = new int[size];
		kruskal = new Edge[size];
		for (int i = 0; i < size; i++)
			disjoint[i] = -1;

		for (CostList temp = costList; temp != null; temp = temp.next){
			if (union(temp.vertex1, temp.vertex2)){
				addEdge(temp.vertex1, temp.vertex2, temp.cost, kruskal);
				addEdge(temp.vertex2, temp.vertex1, temp.cost, kruskal);
			}
		}
	}

	private boolean union(int x, int y) { 
		int rootx = find(x); 
		int rooty = find(y); 
		if (rootx == rooty)
			return false;

		if (disjoint[rootx] < disjoint[rooty])
			disjoint[rooty] = x; 
		else { 
			if (disjoint[rootx] == disjoint[rooty])
				disjoint[rooty]--; 
			disjoint[rootx] = y; 
		}
		return true;
	}

	private int find(int x){
		if (disjoint[x] < 0) 
			return x; 
		else { 
			disjoint[x] = find(disjoint[x]); 
			return disjoint[x]; 
		} 
	}



	public void prim(int s){
		prim = new Edge[size];
		TableEntry[] t = new TableEntry[size];
		MinHeap mh = new MinHeap(size);
		for (int i = 0; i < size; i++){
			t[i] = new TableEntry(Integer.MAX_VALUE, -1, false);
			mh.insert(i, Integer.MAX_VALUE);
		}

		t[s].distance = 0;
		t[s].path = s;
		mh.reduce_key(s, 0);

		int v;
		for (int i = 0; i < size; i++){
			v = mh.remove_min();
			t[v].known = true;
			for (Edge e = adjacencyList[v]; e != null; e = e.next){
				if (t[e.neighbor].distance > e.cost && !t[e.neighbor].known){
					t[e.neighbor].distance = e.cost;
					t[e.neighbor].path = v;
					mh.reduce_key(e.neighbor, e.cost);
				}
			}
		}

		for (int i = 0; i < size; i++){
			if (i == s)
				continue;

			addEdge(i, t[i].path, t[i].distance, prim);
			addEdge(t[i].path, i, t[i].distance, prim);
		}

	}
}