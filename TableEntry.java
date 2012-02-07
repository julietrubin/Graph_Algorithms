public class TableEntry {
	int distance;
	int path;
	boolean known;

	public TableEntry(int distance, int path, boolean known){
		this.distance = distance;
		this.path = path;
		this.known = known;
	}
}
