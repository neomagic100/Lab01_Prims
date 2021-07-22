import java.util.ArrayList;
import java.util.LinkedList;

/*
 * 
 * AdjacencyList class 
 *
 */
public class AdjacencyList {
	private int numVertices;
	private int numEdges;
	private LinkedList<Vertex>[] adjList;
	private ArrayList<Edge> edges;
	
	/**
	 * Constructor
	 * @param numVertices		Number of Vertices
	 * @param numEdges			Number of Edges
	 */
	@SuppressWarnings("unchecked")
	public AdjacencyList(int numVertices, int numEdges) {
		this.numVertices = numVertices;
		this.numEdges = numEdges;
		adjList = new LinkedList[numVertices];
		edges = new ArrayList<>();
		for (int i = 0; i < numVertices; i++) {
			adjList[i] = new LinkedList<>();
		}
	}
	
	/**
	 * Add an edge and its vertices to the adjacency list
	 * @param e				Edge to add
	 */
	public void addEdgeToAdjacencyList(Edge e) {
		edges.add(e);
		Vertex v1 = new Vertex(e.getStartVertex(), e.getWeight());
		Vertex v2 = new Vertex(e.getDestVertex(), e.getWeight());
		v1.setDuplicateRef(v2);
		v2.setDuplicateRef(v1);
		
		adjList[v2.getKey()].add(v1);
		adjList[v1.getKey()].add(v2);
	}
	
	/**
	 * Remove a Vertex from the adjacency list
	 * @param v					Vertex to remove
	 */
	public void removeVertex(Vertex v) {
		Vertex v1 = v;
		Vertex v2 = v.getDuplicateRef();
		
		adjList[v1.getKey()].removeFirstOccurrence(v2);
		adjList[v2.getKey()].removeFirstOccurrence(v1);
	}

	/*
	 * Getters and Setters
	 */
	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public LinkedList<Vertex>[] getAdjList() {
		return adjList;
	}
}
