
public class Vertex {
	private int key;
	private int parentKey;
	private double weight;
	private Vertex duplicateRef;
	
	public Vertex (int key, double weight) {
		this.key = key;
		this.weight = weight;
		parentKey = -1;
	}

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

	@Override
	public String toString() {
		return key + "-" + parentKey + " " + String.format("%.5f", weight);
	}

	public Vertex getDuplicateRef() {
		return duplicateRef;
	}

	public void setDuplicateRef(Vertex duplicateRef) {
		this.duplicateRef = duplicateRef;
	}
	
}
