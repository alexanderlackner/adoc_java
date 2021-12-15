import java.io.BufferedReader;
import java.io.FileReader;

public class Part1 {

	public static void main(String[] args) {
		int weight_map[][] = new int[100][100];
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			int x = 0;
			while ((line = br.readLine()) != null) {
				
				for (int y = 0, n = line.length(); y < n; y++) {
					weight_map[x][y] = line.charAt(y) - '0';
				}
				x++;
			}
			
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		System.out.println(getShortestPathSum(weight_map));
	}
	
	//manage array that tracks shortest distant of every element to starting point [0][0]
	//only checks down and right and a single iteration which is not enough. luckily good enough for Part 1
	public static int getShortestPathSum(int weight_map[][]) {
		int dist_map[][] = new int[100][100];
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				if (x < 99) {
					if (dist_map[x+1][y] == 0 || dist_map[x][y] + weight_map[x+1][y]<dist_map[x+1][y])
						dist_map[x+1][y] = dist_map[x][y] + weight_map[x+1][y];
				}
				if (y < 99) {
					if (dist_map[x][y+1] == 0 || dist_map[x][y] + weight_map[x][y+1]<dist_map[x][y+1])
						dist_map[x][y+1] = dist_map[x][y] + weight_map[x][y+1];
				}
			}
		}
		return dist_map[99][99];
	}
}