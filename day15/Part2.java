import java.io.BufferedReader;
import java.io.FileReader;

public class Part2 {

	static final int dimensions_small = 100;
	static final int dimensions_big = 500;

	public static void main(String[] args) {
		int weight_map[][] = new int[dimensions_small][dimensions_small];
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

		weight_map = createBigMap(weight_map);

		System.out.println(getShortestPathSum(weight_map));
	}

	//creates a bigger map out of the small map
	//idea is to iterate over every element in small array,
	//paste it 25 times with an offset in the big array
	//and increase it based on the offset with wraparound
	public static int[][] createBigMap(int small_map[][]) {
		int big_map[][] = new int[dimensions_big][dimensions_big];
		for (int x = 0; x < small_map.length; x++) {
			for (int y = 0; y < small_map.length; y++) {
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						int v = small_map[x][y]+i+j;
						if (v > 9)
							v = v%9;
						big_map[i*dimensions_small+x][j*dimensions_small+y] = v;
					}
				}
			}
		}
		return big_map;
	}

	//manage array that tracks shortest distant of every element to starting point [0][0]
	//keep checking until the distance array has not changed on the last iteration therefore all the shortest distances have been found
	public static int getShortestPathSum(int weight_map[][]) {
		int dist_map[][] = new int[dimensions_big][dimensions_big];
		boolean changed = true;
		while(changed) {
			changed = false;
			for (int x = 0; x < dimensions_big; x++) {
				for (int y = 0; y < dimensions_big; y++) {
					if (x < dimensions_big-1) {
						if (dist_map[x+1][y] == 0 || dist_map[x][y] + weight_map[x+1][y]<dist_map[x+1][y]) {
							dist_map[x+1][y] = dist_map[x][y] + weight_map[x+1][y];
							changed = true;
						}
					}
					if (y < dimensions_big-1) {
						if (dist_map[x][y+1] == 0 || dist_map[x][y] + weight_map[x][y+1]<dist_map[x][y+1]) {
							dist_map[x][y+1] = dist_map[x][y] + weight_map[x][y+1];
							changed = true;
						}
					}
					if (x > 0) {
						if (dist_map[x-1][y] == 0 || dist_map[x][y] + weight_map[x-1][y]<dist_map[x-1][y]) {
							dist_map[x-1][y] = dist_map[x][y] + weight_map[x-1][y];
							changed = true;
						}
					}
					if (y > 0) {
						if (dist_map[x][y-1] == 0 || dist_map[x][y] + weight_map[x][y-1]<dist_map[x][y-1]) {
							dist_map[x][y-1] = dist_map[x][y] + weight_map[x][y-1];
							changed = true;
						}
					}
				}
			}
		}
		return dist_map[dimensions_big-1][dimensions_big-1];
	}
}