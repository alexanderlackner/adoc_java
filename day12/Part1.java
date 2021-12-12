import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Part1 {
	
	public static void main(String[] args) {
		ArrayList<Cave> cave_list = new ArrayList<Cave>();
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			String split_line[];

			while ((line = br.readLine()) != null) {
				split_line = line.split("-");
				cave_list = addCavesToList(cave_list, split_line);
			}
			System.out.println(recursivelyVisitCaves(cave_list, getCaveFromList(cave_list, "start")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//find a path with no duplicate small cave
	public static int recursivelyVisitCaves(ArrayList<Cave> cave_list, Cave current_cave) {
		//return 1 if we found end cave
		if (current_cave.getIdentifier().equals("end"))
			return 1;
		current_cave.setVisitedToTrue();
		int total = 0;
		for (Cave next_cave : current_cave.getConnectedCaves()) {
			//skip cave if it has been visited before and is small
			if (!next_cave.isBigCave() && next_cave.getVisited())
				continue;
			else {
				total += recursivelyVisitCaves(cave_list, next_cave);
			}
		}
		current_cave.setVisitedToFalse();
		return total;
	}

	//returns a cave c with given identifier
	public static Cave getCaveFromList (ArrayList<Cave> cave_list, String identifier) {
		for (Cave c : cave_list) {
			if (c.getIdentifier().equals(identifier))
				return c;
		}
		return null;
	}

	//adds caves if necessary and manages their connection
	public static ArrayList<Cave> addCavesToList(ArrayList<Cave> cave_list, String[] identifiers) {
		Cave cave1, cave2;
		cave1 = getCaveFromList(cave_list, identifiers[0]);
		if (cave1 == null) {
			cave2 = getCaveFromList(cave_list, identifiers[1]);
			if (cave2 == null) {
				cave1 = new Cave(identifiers[0]);
				cave2 = new Cave(identifiers[1]);
				cave1.addCave(cave2);
				cave2.addCave(cave1);
				cave_list.add(cave1);
				cave_list.add(cave2);
			} else {
				cave1 = new Cave(identifiers[0]);
				cave1.addCave(cave2);
				cave2.addCave(cave1);
				cave_list.add(cave1);
			}
		} else {
			cave2 = getCaveFromList(cave_list, identifiers[1]);
			if (cave2 == null) {
				cave2 = new Cave(identifiers[1]);
				cave1.addCave(cave2);
				cave2.addCave(cave1);
				cave_list.add(cave2);
			} else {
				cave1.addCave(cave2);
				cave2.addCave(cave1);
			}
		}
		return cave_list;	
	}
}
