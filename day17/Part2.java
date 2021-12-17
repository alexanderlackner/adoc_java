import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Part2 {

	public static void main(String[] args) {
		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			line = br.readLine();
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		int min_x = 0, max_x = 0, min_y = 0, max_y = 0;

		//parsing input to integers
		try {
			max_x = Integer.parseInt(line.substring(line.indexOf("..")+2, line.indexOf(",")));
			min_x = Integer.parseInt(line.substring(line.indexOf("x=")+2, line.indexOf("..")));

			max_y = Integer.parseInt(line.substring(line.indexOf("y=")+2, line.lastIndexOf("..")));
			min_y = Integer.parseInt(line.substring(line.lastIndexOf("..")+2, line.length()));
		} catch (Exception e) {
			System.out.println("crashed parsing int: "+e.getMessage());
		}

		System.out.println(solvePart2(min_x, max_x, min_y, max_y));
	}

	public static int solvePart2(int min_x, int max_x, int min_y, int max_y) {
		ArrayList<Probe> possible_x = calculateAllX(min_x, max_x);
		return calculateProbesThatHit(possible_x, min_x, max_x, min_y, max_y);
	}

	//first calculate all the speeds for which the Probe lands between our x coordinates
	public static ArrayList<Probe> calculateAllX(int min_x, int max_x) {
		ArrayList<Probe> possible_x = new ArrayList<Probe>();
		int sum = 0;
		outerloop:
		for (int i = max_x; i > 0; i--) {
			sum = i;
			if (sum>=min_x && sum<=max_x) {
				possible_x.add(new Probe(i));
				continue;
			}
			for (int j = i-1; j > 0; j--) {
				sum += j;
				if (sum>max_x)
					continue outerloop;
				if (sum>=min_x && sum<= max_x) {
					possible_x.add(new Probe(i));
					continue outerloop;
				}
			}
		}
		return possible_x;
	}

	//for a list of every speed at which x can hit I check how many speeds there are for which y can hit too
	public static int calculateProbesThatHit(ArrayList<Probe> possible_x, int min_x, int max_x, int min_y, int max_y) {
		int total = 0;
		for (Probe p : possible_x) {
			for (int y = max_y; y < 140; y++) {
				//between changing the y speed I reset the Probe to its starting position
				p.resetProbe();
				if(calculateIfProbeHitsTarget(p, y, min_x, max_x, min_y, max_y))
					total++;
			}
		}
		return total;
	}

	//calculate if a Probe can hit the target for given speeds
	public static boolean calculateIfProbeHitsTarget(Probe p, int y_speed, int min_x, int max_x, int min_y, int max_y) {
		p.y_speed = y_speed;
		p.makeStep();
		while(p.x_pos<=max_x && p.y_pos >= max_y) {
			if(hitTarget(p, min_x, max_x, min_y, max_y)) {
				return true;
			}
			p.makeStep();
		}
		return false;
	}

	//check if the coordinates of the Probe are in our target
	public static boolean hitTarget(Probe p, int min_x, int max_x, int min_y, int max_y) {
		if(p.x_pos>=min_x && p.x_pos<=max_x)
			if(p.y_pos<=min_y && p.y_pos>=max_y)
				return true;
		return false;
	}
}
