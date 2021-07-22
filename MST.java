import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MST {
	private int numVertices;
	private AdjacencyList adjList;
	private ArrayList<Vertex> mst;
	private PriorityQueue<Vertex> pq;
	private double weight;
	private boolean[] usedVertices;
	
	private class Compare implements Comparator<Vertex>{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			if (v1.getWeight() < v2.getWeight()) return -1;
			else if (v1.getWeight() > v2.getWeight()) return 1;
			else return 0;
		}
	}
	
	public MST(int numVertices, AdjacencyList adjList) {
		this.numVertices = numVertices;
		this.adjList = adjList;
		mst = new ArrayList<>();
		pq = new PriorityQueue<>(new Compare());
		setWeight(0);
		usedVertices = new boolean[numVertices];
	}
	
	public void findPrims() {
		// Initialize PriorityQueue
		boolean updated = false;
		int adjIndex = 0;
		Vertex start = adjList.findMinInListj(adjIndex);
		pq.offer(start);
		
		while (!pq.isEmpty()) {
			// extract min
			Vertex min = pq.poll();
			if (min == null) // Empty
				break;
			
			// Put min in MST
			mst.add(min);
			int parentKey = min.getDuplicateRef().getKey();
			usedVertices[min.getKey()] = true;
			this.adjList.removeVertex(min);
			setWeight(getWeight() + min.getWeight());
			System.out.println(min);
			// Add adjacencies to PriorityQueue
			LinkedList<Vertex> currAdj = adjList.getAdjList()[adjIndex];
			for (int i = 0; i < currAdj.size(); i++) {
				Vertex currV = currAdj.get(i);
				Vertex foundV = vertexValueInQ(currV.getKey());
				if (foundV != null) {
					updated = true;
					if (currV.getWeight() < foundV.getWeight()) {
						foundV.setWeight(currV.getWeight());
						foundV.setParentKey(parentKey);
					}
						
				}
				else if (usedVertices[currV.getKey()] == false) {
					currV.setParentKey(parentKey);
					pq.offer(currV);
					
				}
			}
			
			// Reorder pq
			//if (updated)
				updatePQ();
			
			updated = false;
			
			// Remove Vertex from adjacency List
			//this.adjList.removeVertex(min);
			adjIndex = min.getKey();
		}
		
		
		
	}
	
	public void updatePQ() {
		ArrayList<Vertex> list = new ArrayList<>();
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			if (v == null)
				break;
			list.add(v);
		}
		
		for (Vertex v: list) {
			pq.offer(v);
		}
	}
	
	public Vertex vertexValueInQ(int val) {
		for (Vertex v: pq) {
			if (val == v.getKey())
				return v;
		}
		
		return null;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
