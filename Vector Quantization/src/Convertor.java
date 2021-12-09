import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Convertor {
	/*Attributes*/
	File imageLocation;
	BufferedImage image;
	int row, col;
	
	/*Constructor*/
	public Convertor() {
		image = null;
	}
	
	/*Methods*/
	public float[][] readImage(String filePath) throws IOException{
		imageLocation = new File(filePath);

		image = ImageIO.read(imageLocation);
		
		row = image.getWidth();
		col = image.getHeight();
		
		float[][] pixels = new float[col][row];
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				
				int p = image.getRGB(i, j);
				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				pixels[j][i] = r;

				p = (a << 24) | (r << 16) | (g << 8) | b;
				image.setRGB(i, j, p);
			}

		}
		return pixels;
	}

	

	public static void writeImage(int[][] pixels, Vector<Integer> output, String outputFilePath, int height,
			int width) {
		if (output != null) {
			pixels = new int[height][width];
			for (int i = 0; i < output.size(); ++i) {
				int r = i / width;
				int c = i % width;
				pixels[r][c] = output.get(i);
			}
		}
		File fileout = new File(outputFilePath);
		BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

//				int a = 255;
//				int pix = pixerls[y][x];
//				int p = (a << 24) | (pix << 16) | (pix << 8) | pix;
//				image2.setRGB(x, y, p);
				image2.setRGB(x, y, (pixels[y][x] << 16) | (pixels[y][x] << 8) | (pixels[y][x]));
			}
		}
		try {
			ImageIO.write(image2, "jpg", fileout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/*Setters and Getters*/
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
	
	
	
}
