import java.io.IOException;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args) throws IOException {
		
		Scanner reader = new Scanner(System.in);
		//int input;
		float [][]matrix;
		
		
		Convertor conv = new Convertor();
		
		matrix = conv.readImage("she.JPG");
		/*
		for(int i = 0; i < conv.getRow(); i++) {
			for(int j = 0; j < conv.getCol(); j++) {
				System.out.println(matrix[i][j]);
			}
		}*/
		
		System.out.println(conv.getRow());
		
		/*
		Compression comp = new Compression(conv.getRow(), conv.getCol(), 4, 4);
		
		comp.splitToBlocks(matrix);
		comp.printVectors();*/
		
		
		
		
		reader.close();
	}

}

/*
float [][]matrix = new float[4][4];
for(int i = 0; i < 4; i++) {
	for(int j = 0; j < 4; j++) {
		matrix[i][j] = reader.nextInt();
	}
}*/