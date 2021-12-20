import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Part1_2 {

	public static void main(String[] args) {
		//represent each field with a byte to save memory
		byte image[][] = new byte[104][104];
		String rules = null;
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line = br.readLine();
			rules = line;
			br.readLine();
			int x = 2;
			while ((line = br.readLine()) != null) {
				for (int y = 2; y < image.length-2; y++) {
					if(line.charAt(y-2)=='#')
						image[x][y] = 1;
					else
						image[x][y] = 0;
				}
				x++;
			}
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		//change 50 to 2 for part 1
		System.out.println(getLitPixelsAfterFilter(50, image, rules));
	}

	public static int getLitPixelsAfterFilter(int iterations, byte image[][], String rule) {

		for (int i = 0; i < iterations; i++) {
			byte image_copy[][] = new byte[image.length][image.length];
			for (int x = 1, n = image.length-1; x < n; x++) {
				for (int y = 1, m = image.length-1; y < m; y++) {
					image_copy[x][y] = getNewPixelValue(image, x, y, rule);
				}
			}
			image = addPadding(image_copy, i);
		}

		int total = 0;
		for (byte[] array : image) {
			for (byte value : array) {
				total += value;
			}
		}
		return total;
	}

	//after every iteration adds padding around the array and increses its size by 2 in every dimension
	public static byte[][] addPadding(byte image_copy[][], int iteration) {
		byte image[][] = new byte[image_copy.length+2][image_copy.length+2];
		//since every unlit pixel surrounded by unlit pixels becomes lit
		//all the padded pixels need to light up on every 2nd iteration 
		if (iteration%2 == 0) {
			for (byte[] array : image) {
				Arrays.fill(array, (byte)1);
			}
			for (int x = 1; x < image_copy.length-1; x++) {
				for (int y = 1; y < image_copy.length-1; y++) {
					image[x+1][y+1] = image_copy[x][y];
				}
			}
		} else {
			for (int x = 0; x < image_copy.length-1; x++) {
				for (int y = 0; y < image_copy.length-1; y++) {
					image[x+1][y+1] = image_copy[x][y];
				}
			}
		}

		return image;
	}

	//calculates binary value for a 3x3 sliding window
	//#..	 100
	//.#. => 010 => 100010110 => 278
	//##.	 110
	public static byte getNewPixelValue(byte image[][], int x, int y, String rule) {
		int pow_of_2 = 256, total = 0;
		for (int i = x-1; i < x+2; i++) {
			for (int j = y-1; j < y+2; j++) {
				total += image[i][j] * pow_of_2;
				pow_of_2 = pow_of_2/2;
			}
		}
		if(rule.charAt(total)=='#')
			return 1;
		return 0;
	}
}
