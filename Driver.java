import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
	
	public static AdjacencyList adjList;

	public static void main(String[] args) {
		Scanner fscnr = null;
		int numVertices, numEdges;
		try {
			fscnr = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		numVertices = fscnr.nextInt();
		numEdges = fscnr.nextInt();
		fscnr.nextLine();
		
		adjList = new AdjacencyList(numVertices, numEdges);
		
		// Read all edges into adjacency list
		while (fscnr.hasNext()) {
			adjList.addEdgeToAdjacencyList(new Edge(fscnr.nextInt(), fscnr.nextInt(), fscnr.nextDouble()));
		}
	
		fscnr.close();
		
		adjList.print();
		System.out.println();
		MST m = new MST(numVertices, adjList);
		m.findPrims();
		System.out.println(m.getWeight());
		
		
	}

}
