import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Part1 {

	public static void main(String[] args) {
		//ArrayList of ArrayLists of scanner coordinates;
		ArrayList<Scanner> scanner_list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			ArrayList<Coordinates> coordinates_list = new ArrayList<>();
			int identity = 0;
			while ((line = br.readLine()) != null) {
				if (line.equals("")) {
					scanner_list.add(new Scanner(coordinates_list, identity));
					coordinates_list = new ArrayList<>();
				}
				else {
					if (line.contains("scanner")) {
						identity = Integer.parseInt(line.replaceAll("[^0-9]",""));
					} else {
						String split_line[] = line.split(",");
						coordinates_list.add(new Coordinates(Integer.parseInt(split_line[0]), Integer.parseInt(split_line[1]), Integer.parseInt(split_line[2])));
					}
				}
			}
			scanner_list.add(new Scanner(coordinates_list, identity));
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}
		System.out.println(createScannerMap(scanner_list));

	}

	//we manage 3 lists: a list of scanners which offset and orientation we do not know,
	//a list of scannerswith the correct orientation which offset we know but have not checked yet if they overlap with other scanners
	//and a list with the scanners with correct offset, orientation and we have checked if they overlap with other scanners
	public static int createScannerMap(ArrayList<Scanner> scanner_list) {
		ArrayList<Scanner> checked = new ArrayList<>();
		ArrayList<Scanner> to_check = new ArrayList<>();
		ArrayList<Scanner> to_remove = new ArrayList<>();
		to_check.add(scanner_list.remove(0));

		while(!to_check.isEmpty()) {
			Scanner sc_offset_known = to_check.remove(0);
			to_remove = new ArrayList<>();
			outerloop:
			for (Scanner sc : scanner_list) {
				//make copy of Scanner to rotate freely
				Scanner sc_offset_unknown = new Scanner(sc.coordinates_list, sc.identity);
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						for (int z = 0; z < 4; z++) {
							Coordinates cord = getDistanceFor12Points(sc_offset_known, sc_offset_unknown);
							if (cord!=null) {
								to_remove.add(sc);
								sc_offset_unknown.offset = cord;
								to_check.add(sc_offset_unknown);
								continue outerloop;
							}
							sc_offset_unknown.rotate90DegZ();
						}
						sc_offset_unknown.rotate90DegY();
					}
					sc_offset_unknown.rotate90DegX();
				}
			}
			checked.add(sc_offset_known);
			scanner_list.removeAll(to_remove); 
		}

		for (Scanner sc : checked) {
			sc.updateCoordinatesWithOffset();
		}

		return getTotalNumberOfBeacons(checked);
	}

	//adds all the beacons of every scanner to a list and removes duplicates
	public static int getTotalNumberOfBeacons(ArrayList<Scanner> scanner_list) {
		ArrayList<Coordinates> cord_list = new ArrayList<>();
		for (Scanner sc : scanner_list) {
			outerloop:
			for (Coordinates cord1 : sc.coordinates_list) {
				for (Coordinates cord2 : cord_list) {
					if (cord1.sameCoordinates(cord2))
						continue outerloop;
				}
				cord_list.add(cord1);
			}
		}
		return cord_list.size();
	}

	//tries to find 12 points in each scanner that have the same distance to each other
	public static Coordinates getDistanceFor12Points(Scanner a, Scanner b) {
		int total = 0;
		for (Coordinates a1 : a.coordinates_list) {
			for (Coordinates b1 : b.coordinates_list) {
				Coordinates og_Distance = new Coordinates(a1.x-b1.x, a1.y-b1.y, a1.z-b1.z);
				total = 0;
				outerloop:
				for (Coordinates a2 : a.coordinates_list) {
					for (Coordinates b2 : b.coordinates_list) {
						Coordinates cur_Distance = new Coordinates(a2.x-b2.x, a2.y-b2.y, a2.z-b2.z);
						if (og_Distance.sameCoordinates(cur_Distance)) {
							total++;
							if (total==12) {
								//calculate the offset of scanner b to scanner 0
								int x = a.offset.x + a2.x-b2.x, y = a.offset.y + a2.y-b2.y, z = a.offset.z + a2.z-b2.z;
								return new Coordinates(x, y, z);
							}
							continue outerloop;
						}
					}
				}
			}
		}
		return null;
	} 

}