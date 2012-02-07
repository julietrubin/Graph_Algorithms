public class MinHeap {
	
	public class Node{
		public int vertex; 
		public int priority;

		public Node(int vertex, int priority){
			this.vertex = vertex;
			this.priority = priority; 
		}
	}

	private int[] nodepointer;
	private Node[] Heap;
	private int maxsize;
	private int size;

	public MinHeap(int max) {
		maxsize = max + 1;
		Heap = new Node[maxsize];
		nodepointer = new int[maxsize];
		size = 0;
		Heap[size] = new Node(-1, Integer.MIN_VALUE);
	}

	private int leftchild(int pos) {
		return 2*pos;
	}

	private int parent(int pos) {
		return  pos / 2;
	}

	private boolean isleaf(int pos) {
		return ((pos > size/2) && (pos <= size));
	}

	private void swap(int pos1, int pos2) {
		Node tmp;

		tmp = Heap[pos1];
		Heap[pos1] = Heap[pos2];
		Heap[pos2] = tmp;

		nodepointer[Heap[pos1].vertex] = pos1;
		nodepointer[Heap[pos2].vertex] = pos2;
	}

	public void insert(int elem, int priority) {
		size++;
		Heap[size] = new Node(elem, priority);
		nodepointer[elem] = size;

		int current = size;

		while (Heap[current].priority < Heap[parent(current)].priority) {
			swap(current, parent(current));
			current = parent(current);
		}	
	}

	public void print() {
		int i;
		for (i=1; i<=size;i++)
			System.out.print(Heap[i] + " ");
		System.out.println();
	}

	public void reduce_key(int elem, int new_priority){
		int current = nodepointer[elem];
		Heap[current].priority = new_priority;
		//if(Heap[current].priority > Heap[leafChild])
		while (Heap[current].priority < Heap[parent(current)].priority) {
			swap(current, parent(current));
			current = parent(current);
		}
		
	}

	public int remove_min() {
		swap(1,size);
		size--;
		if (size != 0)
			pushdown(1);
		return Heap[size+1].vertex;
	}

	private void pushdown(int position) {
		int smallestchild;
		while (!isleaf(position)) {
			smallestchild = leftchild(position);
			if ((smallestchild < size) && (Heap[smallestchild].priority > Heap[smallestchild+1].priority))
				smallestchild = smallestchild + 1;
			if (Heap[position].priority <= Heap[smallestchild].priority) return;
			swap(position,smallestchild);
			position = smallestchild;
		}
	}

}


