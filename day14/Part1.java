import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Part1 {
	public static void main(String[] args) {
		String line = null, template = null;
		//make HashMap of rules which char we insert
		HashMap<String, Character> insertion_rules = new HashMap<String, Character>();
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String split_line[];

			while ((line = br.readLine()) != null) {
				if (line.contains("->")) {
					split_line = line.split(" -> ");
					insertion_rules.put(split_line[0], split_line[1].charAt(0));
				} else {
					template = line;
				}
			}
			
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		System.out.print(solvePart1(insertion_rules, template));
	}

	//recursively solve the problem by only always looking at 2 chars
	//example call for NN: NN -> NCN track C and call for NC and CN
	//this is way too slow for Part2
	public static long[] polymerAdder(HashMap<String, Character> insertion_rules, StringBuilder template, int deepness) {
		long array[] = new long[26];
		deepness++;
		if (deepness<10) {
			char c = insertion_rules.get(template.toString());
			//subtract 65 from ASCII value of char
			//A matches to index 0, B to index 1, etc.
			int idx = (int) c-65;
			array[idx] = array[idx]+1;
			template = template.insert(1, c);
			StringBuilder copy = new StringBuilder(template);
			array = addArray(array, polymerAdder(insertion_rules, copy.replace(2, 3, ""), deepness));
			copy = new StringBuilder(template);
			array = addArray(array, polymerAdder(insertion_rules, copy.replace(0, 1, ""), deepness));
			return array;
		} else {
			//subtract 65 from ASCII value of char
			//A matches to index 0, B to index 1, etc.
			int idx = (int) insertion_rules.get(template.toString())-65;
			array[idx] = array[idx]+1;
			return array;
		}
	}

	//add the elements of 2 arrays of the same size
	public static long[] addArray(long[] arr1, long[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = arr1[i] + arr2[i];
		}
		return arr1;
	}

	public static long solvePart1(HashMap<String, Character> insertion_rules, String template) {
		long array[] = new long[26];
		//iterate over pairs in template string and solve recursively
		for (int i = 0, n = template.length()-1; i < n; i++) {
			array = addArray(array, polymerAdder(insertion_rules, new StringBuilder(template.subSequence(i, i+2)), 0));
		}

		//add the characters of the template to the occurence of chars array
		for (int i = 0; i < template.length(); i++) {
			int idx = (int) template.charAt(i)-65;
			array[idx] = array[idx]+1;
		}

		long max = 0;
		long min = Long.MAX_VALUE;

		for (int i = 0; i < array.length; i++) {
			if (array[i]<min && array[i]>0)
				min = array[i];
			if (array[i]>max)
				max = array[i];
		}
		return max-min;
	}
}
