import java.io.BufferedReader;
import java.io.FileReader;

public class Part2 {

	public static void main(String[] args) {
		//read input into 10*10 array
		int energy_level[][] = new int[10][10];
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			int j = 0;
			while ((line = br.readLine()) != null) {
			   	for (int i = 0; i < line.length(); i++) {
					energy_level[j][i] = line.charAt(i)-48;
			   	}
			   	j++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(countUntilAllFlash(energy_level));
	}

	//count the iterations until all octopus flash simultaniously
	public static int countUntilAllFlash(int energy_level[][]) {
		int iteration = 1;
		while(true) {
			//since every octopus can only flash once per round keep track of which octopus have flashed
			boolean has_flashed[][] = new boolean[10][10];
			energy_level = incrementArrayByOne(energy_level);
			boolean changed = true;
			//iterate over array until no more changes have been made in the last iteration
			while(changed) {
				changed = false;
				for (int x = 0; x < energy_level.length; x++) {
					for (int y = 0; y < energy_level.length; y++) {
						//if the octopus hasnt flashed yet and his energy > 9 increase all fields around him by one
						if (energy_level[x][y]>9 && !has_flashed[x][y]) {
							energy_level = flash(energy_level, x, y);
							has_flashed[x][y] = true;
							changed = true;
						}
					}
				}
			}
			//set all flashed fields to zero
			energy_level = setFlashToZero(energy_level);
			//check if everyone has flashed
			if (areAllTrue(has_flashed))
				return iteration;
			iteration++;
		}
	}

	//checks if an entire array is true
	public static boolean areAllTrue(boolean[][] has_flashed) {
		for (boolean[] array : has_flashed)
			for(boolean b : array) if(!b) return false;
		return true;
	}

	//sets the energy level of all octopus that have flashed to 0
	public static int[][] setFlashToZero(int energy_level[][]) {
		for (int x = 0; x < energy_level.length; x++) {
			for (int y = 0; y < energy_level.length; y++) {
				if(energy_level[x][y]>9)
					energy_level[x][y]=0;
			}
		}
		return energy_level;
	}

	//all adjacent octopus have their energy increased by 1
	public static int[][] flash(int energy_level[][], int x, int y) {
		if (x>0) {
			energy_level[x-1][y] = energy_level[x-1][y]+1;
			if (y>0)
				energy_level[x-1][y-1] = energy_level[x-1][y-1]+1;
			if (y<9)
				energy_level[x-1][y+1] = energy_level[x-1][y+1]+1;
		}
		if (x<9) {
			energy_level[x+1][y] = energy_level[x+1][y]+1;
			if (y>0)
				energy_level[x+1][y-1] = energy_level[x+1][y-1]+1;
			if (y<9)
				energy_level[x+1][y+1] = energy_level[x+1][y+1]+1;
		}
		if(y>0) {
			energy_level[x][y-1] = energy_level[x][y-1]+1;
		}
		if(y<9) {
			energy_level[x][y+1] = energy_level[x][y+1]+1;
		}

		return energy_level;
	}
	
	//increment every single element in an array by 1
	public static int[][] incrementArrayByOne(int energy_level[][]) {
		for (int i = 0; i < energy_level.length; i++) {
			for (int j = 0; j < energy_level.length; j++) {
				energy_level[i][j]=energy_level[i][j]+1;
			}
		}
		return energy_level;
	}
	
}