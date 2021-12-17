import java.io.BufferedReader;
import java.io.FileReader;

public class Part1 {
	/* I can choose an x that gurantees us that it doesnt matter which y I choose, I always stay within our range of x
	*  if I shoot something up with speed y, it will reach y=0 again with speed -y.
	*  the highest speed I can get is therefore the maximum depth-1
	*  now all thats left is calculating the highest point I reach which is the sum of maximum depth-1 + maximum depth-2 + ... + 1
	*/
	public static void main(String[] args) {
		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			line = br.readLine();
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		//getting y coordinate from String
		String substring = line.substring(line.indexOf("y=")+3, line.lastIndexOf("..")+2);
		int i = 0;

		try {
			i = Integer.parseInt(substring)-1;
		} catch (Exception e) {
			System.out.println("crashed parsing int: "+e.getMessage());
		}
		System.out.println((i*(i+1))/2);

	}
}
