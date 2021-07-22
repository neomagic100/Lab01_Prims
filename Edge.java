
public class Edge {
	private int startVertex;
	private int destVertex;
	private double weight;
	
	public Edge(int startVertex, int destVertex, double weight) {
		this.startVertex = startVertex;
		this.destVertex = destVertex;
		this.weight = weight;
	}

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

	@Override
	public String toString() {
		return "Edge [startVertex=" + startVertex + ", destVertex=" + destVertex + ", weight=" + weight + "]";
	}
	
	
	
}
