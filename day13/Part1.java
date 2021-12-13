import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class Part1 {
	
	public static void main(String[] args) {
		//Points are comparable and consist of 2 coordinates x and y
		ArrayList<Point>  point_list = new ArrayList<Point>();
		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String split_line[];

			while ((line = br.readLine()) != null) {
				//since we only need to execute a single fold break after first fold instruction
				if (line.contains("fold")) {
					break;
				//add point to grid
				} else {
					split_line = line.split(",");
					int x = 0, y = 0;
					try {
						x = Integer.parseInt(split_line[0]);
						y = Integer.parseInt(split_line[1]);
					} catch (Exception e) {
						System.out.println("crashed casting x or y: "+e.getMessage());
					}
					point_list.add(new Point(x, y));
				}
			}
			
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}
		int fold_line = 0;
		try {
			fold_line = Integer.parseInt(line.replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			System.out.println("crashed casting fold_line: "+e.getMessage());
		}
		//in my example first fold is left
		point_list = foldLeft(point_list, fold_line);
		point_list = removeDuplicates(point_list);
		System.out.println(point_list.size());
	}

	//sort list and remove duplicate points
	public static ArrayList<Point> removeDuplicates (ArrayList<Point> point_list) {
		Collections.sort(point_list);
		int i = 1;
		while(i<point_list.size()) {
			if (point_list.get(i).equals(point_list.get(i-1))) {
				point_list.remove(i);
			} else {
				i++;
			}
		}
		return point_list;
	}

	//fold the ArrayList up by adding a new point with new coordinates if point would be folded up
	//remove old points in the process
	public static ArrayList<Point> foldUp (ArrayList<Point> point_list, int fold_line) {

		ListIterator<Point> iter = new ArrayList<Point>(point_list).listIterator();
		while(iter.hasNext()){
			Point p = iter.next();
			if (p.y > fold_line)
				point_list.add(new Point(p.x, fold_line-(p.y-fold_line)));
    	}
		point_list.removeIf(p -> p.y>=fold_line);
		return point_list;
	}

	//fold the ArrayList up by adding a new point with new coordinates if point would be folded left
	//remove old points in the process
	public static ArrayList<Point> foldLeft (ArrayList<Point> point_list, int fold_line) {

		ListIterator<Point> iter = new ArrayList<Point>(point_list).listIterator();
		while(iter.hasNext()){
			Point p = iter.next();
			if (p.x > fold_line)
				point_list.add(new Point(fold_line-(p.x-fold_line), p.y));
    	}
		point_list.removeIf(p -> p.x>=fold_line);
		return point_list;
	}
}
