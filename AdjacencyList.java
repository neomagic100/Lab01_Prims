import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class AdjacencyList {
	private int numVertices;
	private int numEdges;
	private LinkedList<Vertex>[] adjList;
	private ArrayList<Edge> edges;
	

	
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
	
	public void addEdgeToAdjacencyList(Edge e) {
		edges.add(e);
		Vertex v1 = new Vertex(e.getStartVertex(), e.getWeight());
		Vertex v2 = new Vertex(e.getDestVertex(), e.getWeight());
		v1.setDuplicateRef(v2);
		v2.setDuplicateRef(v1);
		
		adjList[v2.getKey()].add(v1);
		adjList[v1.getKey()].add(v2);
	}
	
	public void removeVertex(Vertex v) {
		Vertex v1 = v;
		Vertex v2 = v.getDuplicateRef();
		
		adjList[v1.getKey()].removeFirstOccurrence(v2);
		adjList[v2.getKey()].removeFirstOccurrence(v1);
	}
	
	public Vertex findMinInListj(int j) {
		Vertex ret = new Vertex(-1, Double.MAX_VALUE);
		for (int i = 0; i < adjList[j].size(); i++) {
			if (adjList[j].get(i).getWeight() < ret.getWeight())
				ret = adjList[j].get(i);
		}
		
		return ret;
	}
	
	public void print() {
		for (int i = 0; i < numVertices; i++) {
			System.out.print(i + " ");
			for (Vertex v: adjList[i]) {
				System.out.print(v + " ");
			}
			System.out.println();
		}
	}

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

	public void setAdjList(LinkedList<Vertex>[] adjList) {
		this.adjList = adjList;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	
	
	
	
}
