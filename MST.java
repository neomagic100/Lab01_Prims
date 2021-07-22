import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * 
 * MST class
 * 
 */
public class MST {
	private int numVertices;
	private AdjacencyList adjList;
	private ArrayList<Vertex> mst;
	private PriorityQueue<Vertex> pq;
	private double weight;
	private boolean[] usedVertices;
	
	/*
	 * Compare class
	 */
	private class Compare implements Comparator<Vertex>{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			if (v1.getWeight() < v2.getWeight()) return -1;
			else if (v1.getWeight() > v2.getWeight()) return 1;
			else return 0;
		}
	}
	
	/**
	 * Constructor
	 * @param numVertices			Number of Vertices
	 * @param adjList				Adjacency List
	 */
	public MST(int numVertices, AdjacencyList adjList) {
		this.numVertices = numVertices;
		this.adjList = adjList;
		mst = new ArrayList<>();
		pq = new PriorityQueue<>(new Compare());
		pq.offer(new Vertex(0, 0));
		setWeight(0);
		usedVertices = new boolean[numVertices];
	}
	
	/**
	 * Find the minimum spanning tree using Prim's algorithm
	 */
	public void findPrims() {
		int iter = 0;
		
		LinkedList<Vertex> currAdjList;
		
		// Loop until queue is empty
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			mst.add(v);
			if (iter % 5000 == 0)
				System.err.println(String.format("%.3f", (iter/(double)numVertices) * 100.0) + "%: " + v);
			if (v.getDuplicateRef() != null)
				adjList.removeVertex(v);
			weight += v.getWeight();
			currAdjList = adjList.getAdjList()[v.getKey()];
			updateQ(currAdjList, v);
			usedVertices[v.getKey()] = true;
			iter++;
		}
		
		// Remove 0 index with 0 weight
		mst.remove(0);
	}
	
	/**
	 * Update the queue, either adding new Vertices to it or updating fields of existing ones
	 * @param list						Vertices adjacent to last vertex removed from queue
	 * @param lastRemoved				Last Vertex removed
	 */
	public void updateQ(LinkedList<Vertex> list, Vertex lastRemoved) {
		ArrayList<Vertex> tempL = new ArrayList<>();
		
		for (Vertex u: list) {
			if (usedVertices[u.getKey()] == false) {
				u.setParentKey(lastRemoved.getKey());
				Vertex n = vertexInQueue(u);
				if (n == null)
					pq.offer(u);
				else if (u.getWeight() < n.getWeight()){
					n.setParentKey(lastRemoved.getKey());
					n.setWeight(u.getWeight());
					pq.remove(n);
					tempL.add(n);
				}
			}
		}
		
		for (Vertex v: tempL) {
			pq.offer(v);
		}
	}
	
	/**
	 * Match a Vertex with one already in the queue
	 * @param v					Vertex being searched for
	 * @return					Reference to vertex found or null if not found
	 */
	public Vertex vertexInQueue(Vertex v) {
		if (pq.isEmpty())
			return null;
		for (Vertex u: pq) {
			if (v.getKey() == u.getKey())
				return u;
		}
		
		return null;
	}
	
	/**
	 * Print edges to STDERR
	 */
	public void printEdgesToErr() {
		for (Vertex v: mst)
			System.err.println(v);
	}
	
	/**
	 * Print out the total weight of the minimum spanning tree
	 */
	public void printWeight() {
		System.out.println(String.format("%.5f", getWeight()));
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

	public AdjacencyList getAdjList() {
		return adjList;
	}

	public void setAdjList(AdjacencyList adjList) {
		this.adjList = adjList;
	}

	public ArrayList<Vertex> getMst() {
		return mst;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
