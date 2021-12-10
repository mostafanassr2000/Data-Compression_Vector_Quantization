import java.io.IOException;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args) throws IOException {
		
		Scanner reader = new Scanner(System.in);
		//int input;
		double [][]matrix;
		
		/*
		Convertor conv = new Convertor();
		
		matrix = conv.readImage("she.JPG");
		
		for(int i = 0; i < conv.getRow(); i++) {
			for(int j = 0; j < conv.getCol(); j++) {
				System.out.println(matrix[i][j]);
			}
		}
		
		System.out.println(conv.getRow());
		*/
		
	
	
		

		int originalRow = 6, originalCol = 6; // Size of the original image
		int vectorRow = 2, vectorCol = 2; // Size of Vector
		int numOfCodeBooks = 4;
		
		matrix = new double[originalRow][originalCol];
		for(int i = 0; i < originalRow; i++) {
			for(int j = 0; j < originalCol; j++) {
				matrix[i][j] = reader.nextInt();
			}
		}
		
		Compression comp = new Compression(originalRow, originalCol, vectorRow, vectorCol, numOfCodeBooks);
		
		comp.compress(matrix);
		
		
		reader.close();
	}

}


