public class HashMap {

	public class Node{
		String key;
		int index;

		Node(String key, int index){
			this.key = key;
			this.index = index;
		}
	}

	private int size;
	private Node [] array;
	private int nodesadded;


	HashMap(int size){
		this.size = size;
		array = new Node[size];
		nodesadded = 0;
	}

	public void add(String key, int index){
		if (nodesadded > size/2)
			expandarray();
		int i = ELFhash(key);
		int j = i;
		int n = 1;
		while(array[j] != null){
			j = (i + n^2) % size;
			n++;
		}
		array[j] = new Node(key, index);
		nodesadded++;
	}

	private void expandarray() {
		Node[] temp = array;
		size = size * 2;
		array = new Node[size];
		for(int i = 0; i < temp.length; i++){
			if (temp[i] != null)
				add(temp[i].key, temp[i].index);
		}

	}

	private int ELFhash(String key){
		long h = 0; 
		long g; 
		int i; 
		for (i = 0; i < key.length(); i++) { 
			h = (h << 4) + (int) key.charAt(i); 
			g = h & 0xF0000000L; 
			if (g != 0) 
				h ^= g >>> 24;
			h &= ~g;
		} 
		return (int) (h % size); 
	}

	public int find(String key){
		int i = ELFhash(key);
		int j = i;
		int n = 1;

		while(array[j] != null){
			if(array[j].key.equals(key))
				return array[j].index;
			j = (i + n^2) % size;
			n++;
		}
		return -1;
	}
}
