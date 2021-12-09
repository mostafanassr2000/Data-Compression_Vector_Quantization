import java.util.ArrayList;

public class Node {
	/*Attributes*/
	Node left;	//Child
	Node right;	//Child
	VectorMatrix matrix;
	ArrayList<VectorMatrix> collections;
	
	public Node() {
		left = null;
		right = null;
		matrix = null;
		collections = new ArrayList<VectorMatrix>();
	}
}
