import java.util.ArrayList;

import javax.crypto.ShortBufferException;

public class Compression {
	int originalRow, originalCol; // Size of the original image
	int vectorRow, vectorCol; // Size of Vector
	int row, col; // size of matrix of matrices (Converted Matrix)
	int numOfBlocks;
	int numOfCodeBooks;
	int numOfLeafNodes;

	Node root;
	ArrayList<VectorMatrix> originalVectors;
	ArrayList<Node> leafNodes;

	Compression(int originalRow, int originalCol, int vectorRow, int vectorCol, int numOfCodeBooks) {

		this.originalRow = originalRow;
		this.originalCol = originalCol;
		this.vectorRow = vectorRow;
		this.vectorCol = vectorCol;
		this.numOfCodeBooks = numOfCodeBooks;
		this.numOfLeafNodes = 0;

		this.row = originalRow / vectorRow;
		this.col = originalCol / vectorCol;
		this.numOfBlocks = (originalRow * originalCol) / (vectorRow * vectorCol);

		this.originalVectors = new ArrayList<VectorMatrix>();
		this.leafNodes = new ArrayList<Node>();
	}

	// Splitting the numerical image to blocks and saving them in an ArrayList.
	public void splitToBlocks(double[][] numericalImage) {

		for (int i = 0; i < originalRow; i += vectorRow) {
			for (int j = 0; j < originalCol; j += vectorCol) {
				VectorMatrix block = new VectorMatrix(vectorRow, vectorCol);
				for (int k = i; k < (vectorRow + i); k++) {
					for (int l = j; l < (vectorCol + j); l++) {
						block.matrix[k - i][l - j] = numericalImage[k][l];
					}
				}
				originalVectors.add(block);
			}
		}

	}

	public void compress(double[][] numericalImage) {
		splitToBlocks(numericalImage);
		calcNodeAverage(true); // Creating the root with average values.
		vectorTree(this.root);
		printCollections();
		// printLeafNodes();
		// inOrderTraverse(this.root);
	}

	public void vectorTree(Node root) {

		this.numOfLeafNodes = 0;
		calcLeafNodes(root); // Calculating number of leaf nodes.

		while (leafNodes.size() < numOfCodeBooks) {

			for (int i = 0; i < leafNodes.size(); i++) {
				splitNode(leafNodes.get(i));
			}

			leafNodes = new ArrayList<Node>();
			calcLeafNodes(root);
			calcNearestVector();
			calcNodeAverage(false);
		}
		
		
		
		

	}

	public void splitNode(Node root) {

		// Creating a left node using ceiling
		Node leftNode = new Node();
		VectorMatrix leftMatrix = new VectorMatrix(vectorRow, vectorCol);

		for (int i = 0; i < vectorRow; i++) {
			for (int j = 0; j < vectorCol; j++) {
				leftMatrix.matrix[i][j] = Math.ceil((root.matrixObj.matrix[i][j] - 1));
			}
		}
		leftNode.matrixObj = leftMatrix;
		root.left = leftNode;

		// Creating a right node using flooring
		Node rightNode = new Node();
		VectorMatrix rightMatrix = new VectorMatrix(vectorRow, vectorCol);

		for (int i = 0; i < vectorRow; i++) {
			for (int j = 0; j < vectorCol; j++) {
				rightMatrix.matrix[i][j] = Math.floor((root.matrixObj.matrix[i][j] + 1));
			}
		}
		rightNode.matrixObj = rightMatrix;
		root.right = rightNode;
	}

	public void calcNodeAverage(boolean isRoot) {

		if (isRoot) { // Creating the root with average values.
			Node rootNode = new Node();
			VectorMatrix rootMatrix = new VectorMatrix(vectorRow, vectorCol);

			for (int i = 0; i < vectorRow; i++) {
				for (int j = 0; j < vectorCol; j++) {
					for (int k = 0; k < originalVectors.size(); k++) {
						rootMatrix.matrix[i][j] += originalVectors.get(k).matrix[i][j]; // Calculating the sum of each
																						// index
					}
					rootMatrix.matrix[i][j] /= numOfBlocks; // Dividing over number of blocks
				}
			}

			rootNode.matrixObj = rootMatrix;
			this.root = rootNode;
		} else { // Calculating a new average from a collection
			int temp = 0;
			for (int x = 0; x < leafNodes.size(); x++) {
				Node currentNode = leafNodes.get(x);
				
				//Skip the node that doesn't have collection.
				if(currentNode.collection.size() == 0) {
					continue;
				}
				
				for (int i = 0; i < vectorRow; i++) {
					for (int j = 0; j < vectorCol; j++) {
						for (int k = 0; k < currentNode.collection.size(); k++) {
							temp += currentNode.collection.get(k).matrix[i][j]; // Calculating the sum of each index
							currentNode.matrixObj.matrix[i][j] = temp;
						}
						temp = 0;
						currentNode.matrixObj.matrix[i][j] /= currentNode.collection.size(); // Dividing over number of
																								// blocks
					}
				}
			}
		}
	}

	// Adding the current leaf nodes to the arraylist
	public void calcLeafNodes(Node root) {

		if (root.left == null && root.right == null) { // is leaf
			this.numOfLeafNodes++;
			leafNodes.add(root);
		} else {
			calcLeafNodes(root.left);
			calcLeafNodes(root.right);
		}

	}

	public void calcNearestVector() {
		int distance = 0;
		int shortestDistance = 0;
		int nearestVectorIndex = 0;

		for (int i = 0; i < originalVectors.size(); i++) {

			for (int k = 0; k < vectorRow; k++) {
				for (int l = 0; l < vectorCol; l++) {
					shortestDistance += Math
							.pow(originalVectors.get(i).matrix[k][l] - leafNodes.get(0).matrixObj.matrix[k][l], 2);
				}
			}

			for (int j = 0; j < leafNodes.size(); j++) {
				for (int k = 0; k < vectorRow; k++) {
					for (int l = 0; l < vectorCol; l++) {
						distance += Math
								.pow(originalVectors.get(i).matrix[k][l] - leafNodes.get(j).matrixObj.matrix[k][l], 2);
					}
				}
				// Getting the shortest distance between the original vectors and the leaf nodes
				if (distance < shortestDistance) {
					shortestDistance = distance;
					nearestVectorIndex = j;
				}
				distance = 0;
			}

			// Adding the original vector to the collection of the nearest leaf vector
			leafNodes.get(nearestVectorIndex).collection.add(originalVectors.get(i));
			nearestVectorIndex = 0;
		}
	}

	public void printCollections() {
		for (int i = 0; i < leafNodes.size(); i++) {
			System.out.println("---------------------------------------");

			System.out.print("Leaf Node" + (i + 1) + ": ");
			System.out.println(leafNodes.get(i).matrixObj);

			System.out.println("Collection: ");
			System.out.println("---------------");
			for (int j = 0; j < leafNodes.get(i).collection.size(); j++) {
				System.out.println(leafNodes.get(i).collection.get(j));
			}

			System.out.println("----------------------------------------");
		}
	}

	public void printLeafNodes() {

		for (int i = 0; i < leafNodes.size(); i++) {
			System.out.println(leafNodes.get(i).matrixObj);
		}

	}

	public void inOrderTraverse(Node root) {
		if (root == null) {
			return;
		} else {
			inOrderTraverse(root.left);

			System.out.println("---------------------------------");
			for (int i = 0; i < vectorRow; i++) {
				for (int j = 0; j < vectorCol; j++) {
					System.out.println(root.matrixObj.matrix[i][j]);
				}
			}
			System.out.println("---------------------------------");

			inOrderTraverse(root.right);
		}

	}

	public void printVectors() {
		for (int i = 0; i < originalVectors.size(); i++) {
			for (int k = 0; k < vectorRow; k++) {
				for (int l = 0; l < vectorCol; l++) {
					System.out.println(originalVectors.get(i).matrix[k][l]);
				}
			}
		}
	}

}
