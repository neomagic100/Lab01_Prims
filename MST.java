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
		pq.offer(new Vertex(0, 0));
		/*for (int i = 0; i < numVertices; i++) {
			pq.offer(new Vertex(i, Double.MAX_VALUE));
		}
		
		Vertex v = pq.poll();
		v.setWeight(0);
		pq.offer(v);*/
	}
	
	public void printq() {
		for (Vertex v: pq)
			System.out.println(v);
	}
	
	public void findPrims() {
		long s, e, t;
		int iter = 0;
		s = System.currentTimeMillis();
		LinkedList<Vertex> currAdjList;
		//System.out.println("Starting Prims");
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			mst.add(v);
			if (v.getDuplicateRef() != null)
				adjList.removeVertex(v);
			if (iter % 10000 == 0)
				System.err.println(iter + " " +v);
			weight += v.getWeight();
			currAdjList = adjList.getAdjList()[v.getKey()];
			updateQ(currAdjList, v);
			
			usedVertices[v.getKey()] = true;
			iter++;
		}
		// Remove 0 index with 0 weight
		mst.remove(0);
		
		e = System.currentTimeMillis();
		t = e - s;
		System.err.printf("Time elapsed: %d ms\n", t);
	}
	
	public void sortQ() {
		ArrayList<Vertex> list = new ArrayList<>();
		while (!pq.isEmpty()) {
			list.add(pq.remove());
		}
		
		for (Vertex v: list) {
			pq.offer(v);
		}
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
	
	public Vertex vertexInQueue(Vertex v) {
		if (pq.isEmpty())
			return null;
		for (Vertex u: pq) {
			if (v.getKey() == u.getKey())
				return u;
		}
		
		return null;
	}

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
			
			
			list.remove(u.getDuplicateRef());
			
		}
		
		for (Vertex v: tempL) {
			pq.offer(v);
		}
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
