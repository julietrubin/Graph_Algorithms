import sun.misc.Queue;

class Graphtest {

	public static final int GRAPH_SIZE = 15;
	public static final double EDGE_PERCENT = 0.3;

	/*A standard Depth-First Search, starting from startVertex. 
	 * Note that we are only running one DFS from the start vertex, 
	 * so some vertices may not be visited, 
	 * if there are vertices not reachable from the start vertex. 
	 * The DFS search tree is created in the Parent[] array, 
	 * using the same parent pointer representation as disjoint sets 
	 * (that is, Parent[x] = index of the parent of x, or -1 if x has no parent). 
	 * You can assume that all elements of the array Visited[] are false, 
	 * and all elements of the array Parent[] are -1 when the function is first called. 
	 */
	public static void DFS(Graph G, int Parent[], int startVertex, boolean Visited[]) {
		Edge tmp; 
		Visited[startVertex] = true; 
		for (tmp = G.edges[startVertex]; tmp != null; tmp = tmp.next) { 
			if (!Visited[tmp.neighbor]){ 
				Parent[tmp.neighbor] = startVertex;
				DFS(G, Parent, tmp.neighbor, Visited); 
			}
		} 
	}


	/*Prints out the path from the root of the tree to endvertex, 
	 * all on one line, followed by an end-of-line.
	 *  If there is no path from the root of the tree to the end vertex 
	 *  (or if the root vertex is passed in), just print out the end vertex, 
	 *  followed by a end-of-line. Recursion is your friend here. 
	 *  You may need to use a helper function to get the end-of-line printed out correctly.
	 */
	public static void PrintPath(int Parent[], int endvertex) {
		if(Parent[endvertex] != -1)
			PrintPath(Parent, Parent[endvertex]);
		System.out.print(endvertex + " ");
	}



	/*A standard Breadth-First Search, starting at the given startvertex.
	 *  The BFS search tree is created in the Parent[] array, 
	 *  using the same parent pointer representation as disjoint sets 
	 *  (that is, Parent[x] = index of the parent of x, or -1 if x has no parent). 
	 *  You can assume that all elements of the array Visited[] are false, 
	 *  and all elements of the array Parent[] are -1 when the function is first called.
	 *   Feel free to use the one of the queue classes discussed in lecture: ListQueue.java, ArrayQueue.java */
	public static void BFS(Graph G, int Parent[], int startVertex, boolean Visited[]) {
		Edge tmp; 
		int nextV = 0;  
		Queue Q = new Queue(); 
		Visited[startVertex] = true; 
		Q.enqueue(new Integer(startVertex)); 
		while (!Q.isEmpty()) { 
			try {
				nextV = ((Integer) Q.dequeue()).intValue();
			} catch (InterruptedException e) { e.printStackTrace();} 
			for (tmp = G.edges[nextV]; tmp != null; tmp = tmp.next) { 
				if (!Visited[tmp.neighbor]) { 
					Parent[tmp.neighbor] = nextV;
					Visited[tmp.neighbor] = true; 
					Q.enqueue(new Integer(tmp.neighbor)); 
				} 
			} 
		} 

		// Fill me in! 
		// Recursion, not so much your friend
		// Feel free to use the provided Queue code (but be sure to submit
		// all code necessary for this file to compile and run!)


	}

	public static void main(String args[]) 
	{
		boolean Visited[] = new boolean[GRAPH_SIZE];
		int Parent[] = new int[GRAPH_SIZE];
		Graph G = new Graph(GRAPH_SIZE);
		int i;

		for (i=0; i<G.numVertex;i++) {
			Visited[i] = false;
			Parent[i] = -1;
		}

		G.randomize(EDGE_PERCENT);
		G.print();
		BFS(G,Parent,0,Visited);
		System.out.println("----------------");
		System.out.println("BFS:");
		System.out.println("----------------");

		for (i=0; i<G.numVertex;i++) {
			System.out.print("Path from 0 to " + i + ":  ");
			PrintPath(Parent,i);
			System.out.println();
		}
		for (i=0; i<G.numVertex;i++) {
			Visited[i] = false;
			Parent[i] = -1;
		}

		DFS(G,Parent,0,Visited);
		System.out.println("----------------");
		System.out.println("DFS:");
		System.out.println("----------------");

		for (i=0; i<G.numVertex;i++) {
			System.out.print("Path from 0 to " + i + ":  ");
			PrintPath(Parent,i);
			System.out.println();
		}
	}
}