import java.util.ArrayList;

public class Node {
	/*Attributes*/
	Node left;	//Child
	Node right;	//Child
	VectorMatrix matrixObj;
	ArrayList<VectorMatrix> collection;
	
	/*Constructor*/
	public Node() {
		left = null;
		right = null;
		matrixObj = null;
		collection = new ArrayList<VectorMatrix>();
	}
	
	
	
}
