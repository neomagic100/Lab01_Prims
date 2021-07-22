/*
 * 
 * Edge Class
 * 
 */
public class Edge {
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
