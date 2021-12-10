
public class VectorMatrix {
	/*Attributes*/
	private int row;
	private int col;
	double[][] matrix;
	
	
	/*Constructor*/
	public VectorMatrix(int row, int col){
		this.row = row;
		this.col = col;
		this.matrix = new double[row][col];
	}

	
	public String toString() {
		String temp = "";
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				temp += String.valueOf(matrix[i][j]) + " ";  
			}
		}
		return temp;
	}
	
	
	
	/*

	public float[][] getVectorMatrix() {
		return matrix;
	}
	*/
	
}
