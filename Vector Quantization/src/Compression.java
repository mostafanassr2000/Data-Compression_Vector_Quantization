import java.util.ArrayList;

public class Compression {
	int originalRow, originalCol; // Size of the original image
	int vectorRow, vectorCol; // Size of Vector
	int row, col; // size of matrix of matrices (Converted Matrix)
	int numberOfBlocks;
	// numberOfBlocks = (originalRow * originalCol) / (vectorRow * vectorCol);
	ArrayList<VectorMatrix> originalVectors;

	Compression(int originalRow, int originalCol, int vectorRow, int vectorCol) {

		this.originalRow = originalRow;
		this.originalCol = originalCol;
		this.vectorRow = vectorRow;
		this.vectorCol = vectorCol;
		row = originalRow / vectorRow;
		col = originalCol / vectorCol;

		originalVectors = new ArrayList<VectorMatrix>();
	}

	// Splitting the numerical image to blocks and saving them in an ArrayList.
	public void splitToBlocks(float[][] numericalImage) {

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
