/*=============================================================================
 |   Assignment:  Lab 01 - Building a Prim's MST for an input graph
 |
 |       Author:  Michael Bernhardt - solo submission
 |     Language:  Java
 |
 |   To Compile:  javac Lab01.java
 |
 |   To Execute:  java Lab01 filename
 |                     where filename is in the current directory and contains
 |                           a record containing the number of vertices,
 |                           a record containing the number of edges,
 |                           many records containing the following:
 |                              Source edge number (integer)
 |                              Destination edge number (integer)
 |                              Edge weight (double)
 |
 |        Class:  COP3503 - CS II Summer 2021
 |   Instructor:  McAlpin
 |     Due Date:  July 26, 2021
 |
 +=============================================================================*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lab01 {

	public static AdjacencyList adjList;  // Adjacency List
	public static MST mst;				  // Minimum Spanning Tree

	public static void main(String[] args) {
		Scanner fscnr = null;
		int numVertices = 0, numEdges = 0;

		try {
			fscnr = new Scanner(new File(args[0]));
			numVertices = fscnr.nextInt();
			numEdges = fscnr.nextInt();
			fscnr.nextLine();

			adjList = new AdjacencyList(numVertices, numEdges);

			// Read all edges into adjacency list
			while (fscnr.hasNext()) {
				adjList.addEdgeToAdjacencyList(new Edge(fscnr.nextInt(), fscnr.nextInt(), fscnr.nextDouble()));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			fscnr.close();
		}

		// Create the Minimum Spanning Tree and run Prim's algorithm
		mst = new MST(numVertices, adjList);
		mst.findPrims();
		mst.printEdgesToErr();
		mst.printWeight();
	}
}


/*
 *
 * MST class
 *
 */
class MST {
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
		LinkedList<Vertex> currAdjList;

		// Loop until queue is empty
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			mst.add(v);
			if (v.getDuplicateRef() != null)
				adjList.removeVertex(v);
			weight += v.getWeight();
			currAdjList = adjList.getAdjList()[v.getKey()];
			updateQ(currAdjList, v);
			usedVertices[v.getKey()] = true;
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


/*
 *
 * AdjacencyList class
 *
 */
class AdjacencyList {
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


/*
 *
 * Edge Class
 *
 */
class Edge {
	private int startVertex;
	private int destVertex;
	private double weight;

	/**
	 * Constructor
	 * @param startVertex			Vertex 1
	 * @param destVertex			Vertex 2
	 * @param weight				Weight of the edge
	 */
	public Edge(int startVertex, int destVertex, double weight) {
		this.startVertex = startVertex;
		this.destVertex = destVertex;
		this.weight = weight;
	}

	/*
	 * Getters and Setters
	 */
	public int getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(int startVertex) {
		this.startVertex = startVertex;
	}

	public int getDestVertex() {
		return destVertex;
	}

	public void setDestVertex(int destVertex) {
		this.destVertex = destVertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}


/*
 *
 * Vertex class
 *
 */
class Vertex {
	private int key;
	private int parentKey;
	private double weight;
	private Vertex duplicateRef;

	/**
	 * Constructor
	 * @param key			 Vertex key
	 * @param weight		 Weight to this Vertex
	 */
	public Vertex (int key, double weight) {
		this.key = key;
		this.weight = weight;
		parentKey = -1;
		duplicateRef = null;
	}

	/*
	 * Getters and Setters
	 */
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getParentKey() {
		return parentKey;
	}

	public void setParentKey(int parentKey) {
		this.parentKey = parentKey;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex getDuplicateRef() {
		return duplicateRef;
	}

	public void setDuplicateRef(Vertex duplicateRef) {
		this.duplicateRef = duplicateRef;
	}

	@Override
	public String toString() {
		return key + "-" + parentKey + " " + String.format("%.5f", weight);
	}
}

/*=============================================================================
|     I Michael Bernhardt (mi450095) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
