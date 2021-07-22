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
		initQ();
		setWeight(0);
		usedVertices = new boolean[numVertices];
	}
	
	public void initQ() {
		for (int i = 0; i < numVertices; i++) {
			pq.offer(new Vertex(i, Double.MAX_VALUE));
		}
		
		Vertex v = pq.poll();
		v.setWeight(0);
		pq.offer(v);
	}
	
	public void printq() {
		for (Vertex v: pq)
			System.out.println(v);
	}
	
	public void findPrims() {
		long s, e, t;
		s = System.currentTimeMillis();
		LinkedList<Vertex> currAdjList;
		
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			mst.add(v);
			weight += v.getWeight();
			currAdjList = adjList.getAdjList()[v.getKey()];
			for (Vertex u: currAdjList) {
				if (usedVertices[u.getKey()] == false) {
					updateQ(currAdjList, v.getKey());
				}
			}
			usedVertices[v.getKey()] = true;
		}
		// Remove 0 index with 0 weight
		mst.remove(0);
		
		e = System.currentTimeMillis();
		t = e - s;
		System.err.printf("Time elapsed: %d ms\n", t);
	}
	
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

	public void setMst(ArrayList<Vertex> mst) {
		this.mst = mst;
	}

	public PriorityQueue<Vertex> getPq() {
		return pq;
	}

	public void setPq(PriorityQueue<Vertex> pq) {
		this.pq = pq;
	}

	public boolean[] getUsedVertices() {
		return usedVertices;
	}

	public void setUsedVertices(boolean[] usedVertices) {
		this.usedVertices = usedVertices;
	}

	public void updateQ(LinkedList<Vertex> list, int parentKey) {
		ArrayList<Vertex> tempList = new ArrayList<>();
		for (Vertex v: list) {
			for (Vertex u: pq) {
				if (v.getKey() == u.getKey() && v.getWeight() < u.getWeight()) {
					u.setWeight(v.getWeight());
					u.setParentKey(parentKey);
					tempList.add(u);
					//pq.remove(u);
				}
			}
		}
		
		for (int i = 0; i < tempList.size(); i++) {
			pq.remove(tempList.get(i));
			pq.offer(tempList.get(i));
		}
		
//		while (!pq.isEmpty()) {
//			Vertex v = pq.poll();
//			tempList.add(v);
//		}
//		
//		for (Vertex v: tempList)
//			pq.offer(v);
		
	}
	
	/*public void findPrims() {
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
		
		
		
	}*/
	
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
