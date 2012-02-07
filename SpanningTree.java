public class SpanningTree {
	public static void main(String[] args) {
		Graph g = new Graph(args[0], 7, 17);
		g.kruskal();
		g.prim(4);
		g.printKruskal();
		System.out.println("Original Graph");
		g.printOriginalGraph();
		System.out.println("-----------------------------------------------");
		System.out.println("Kruskal");
		g.printKruskal();
		System.out.println("-----------------------------------------------");
		System.out.println("Prim");
		g.printPrim();
	}
}
