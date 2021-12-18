import java.io.BufferedReader;
import java.io.FileReader;

public class Part2 {

	public static void main(String[] args) {
		String line_array[] = new String[100];

		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				line_array[i] = line;
				i++;
			}
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}
		System.out.println(maxMagnitudeFor2Nums(line_array));
	}

	//returns the maximum magnitude we can get by adding 2 numbers
	public static int maxMagnitudeFor2Nums(String line_array[]) {
		int max = 0;
		for (int i = 0; i < line_array.length; i++) {
			for (int j = 0; j < line_array.length; j++) {
				//skip if were about to add a number to itself
				if (i == j)
					continue;
				StringBuilder sb = new StringBuilder(line_array[i]);
				sb.append("," + line_array[j] + "]");
				sb.insert(0, "[");

				int magnitude = calculateMagnitude(reduceSnailfishNumber(sb));
				if (magnitude>max)
					max = magnitude;
			}
		}
		return max;
	}

	//calculate the magnitude for a snailbuilder as StringBuilder
	public static int calculateMagnitude(StringBuilder snailfishnumber) {
		while (snailfishnumber.indexOf("[") != -1) {
			snailfishnumber = replaceNextEasyPair(snailfishnumber);
		}
		return Integer.parseInt(snailfishnumber.toString());
	}

	//replaces the next easy pair by its magnitude value an easy pair is [x,y] for any given x and y element of N
	public static StringBuilder replaceNextEasyPair(StringBuilder snailfishnumber) {
		int a = 0, b = 0, start_idx = 0;
		for (int i = 0, n = snailfishnumber.length(); i < n; i++) {
			if (Character.isDigit(snailfishnumber.charAt(i))) {
				start_idx = i;
				while(Character.isDigit(snailfishnumber.charAt(i))) {
					i++;
				}
				a = Integer.parseInt(snailfishnumber.substring(start_idx, i));
				i++;
				if (Character.isDigit(snailfishnumber.charAt(i))) {
					int start_idx2 = i;
					while(Character.isDigit(snailfishnumber.charAt(i))) {
						i++;
					}
					b = Integer.parseInt(snailfishnumber.substring(start_idx2, i));
					return snailfishnumber.replace(start_idx-1, i+1, Integer.toString(3*a+2*b));
				}
			}
		}
		return snailfishnumber;
	}

	//reduce a snailfishnumber
	public static StringBuilder reduceSnailfishNumber(StringBuilder snailfishnumber) {
		int last_length = 0;
		//explode all possible explode pairs then split once and check if there are more pairs to explode
		//once we don't have any number to split after our explosion loop were done
		while (last_length != snailfishnumber.length()) {
			snailfishnumber = explodeAll(snailfishnumber);
			last_length = snailfishnumber.length();
			snailfishnumber = splitOnce(snailfishnumber);
		}
		return snailfishnumber;
	}

	//splits the first number>10 and return
	public static StringBuilder splitOnce(StringBuilder snailfishnumber) {
		for (int i = 0; i < snailfishnumber.length()-1; i++) {
			if (Character.isDigit(snailfishnumber.charAt(i)) && Character.isDigit(snailfishnumber.charAt(i+1))) {
				int start_idx = i;
				i++;
				while (Character.isDigit(snailfishnumber.charAt(i))) {
					i++;
				}
				int to_replace = Integer.parseInt(snailfishnumber.substring(start_idx, i));
				String replaced_by = "[" + Math.floorDiv(to_replace, 2) + "," + -Math.floorDiv(-to_replace, 2) + "]";
				snailfishnumber.replace(start_idx, i, replaced_by);
				return snailfishnumber;
			}
		}
		return snailfishnumber;
	}

	//explode until there are no more pairs to explode
	public static StringBuilder explodeAll(StringBuilder snailfishnumber) {
		int opening_brackets = 0;
		for (int i = 0; i < snailfishnumber.length()-1; i++) {
			char c = snailfishnumber.charAt(i);
			if (c == '[')
				opening_brackets++;
			else if (c == ']')
				opening_brackets--;
			if (opening_brackets>=5 && Character.isDigit(c)) {
				int end_idx_explodable_number = peekExplodableNumber(snailfishnumber, i);
				if (!(end_idx_explodable_number == 0)) {
					snailfishnumber = explode(snailfishnumber, i, end_idx_explodable_number);
					snailfishnumber = addMissingZeroes(snailfishnumber);
					i = -1;
					opening_brackets = 0;
				}
			}
		}
		return snailfishnumber;
	}

	//add missing zeros after exploding
	public static StringBuilder addMissingZeroes(StringBuilder snailfishnumber) {
		for (int i = 0; i < snailfishnumber.length()-1; i++) {
			if (snailfishnumber.charAt(i) == ',' && snailfishnumber.charAt(i+1) == ']')
				snailfishnumber.insert(i+1, "0");
			if (snailfishnumber.charAt(i) == '[' && snailfishnumber.charAt(i+1) == ',')
				snailfishnumber.insert(i+1, "0");
		}
		return snailfishnumber;
	}

	//explode a single pair
	public static StringBuilder explode(StringBuilder snailfishnumber, int start_idx, int end_idx) {
		String substring = snailfishnumber.substring(start_idx, end_idx);
		snailfishnumber.replace(start_idx-1, end_idx+1, "");
		String splitstring[] = substring.split(",");
		int num1 = 0, num2 = 0;
		try { 
			num1 = Integer.parseInt(splitstring[0]);
			num2 = Integer.parseInt(splitstring[1]);
		} catch (Exception e) {
			System.out.println("crashed converting numbers: " + e.getMessage());
		}
		snailfishnumber = addToLeft(snailfishnumber, num1, num2, start_idx-1);

		return snailfishnumber;
	}

	//add the left number of the pair to the closest number to the left or insert 0
	public static StringBuilder addToLeft(StringBuilder snailfishnumber, int num1, int num2, int idx) {
		int og_length = snailfishnumber.length();
		for (int i = idx; i > 0; i--) {
			if (Character.isDigit(snailfishnumber.charAt(i))){
				int end_idx = i;
				i--;
				while(Character.isDigit(snailfishnumber.charAt(i))) {
					i--;
				}
				snailfishnumber.replace(i+1, end_idx+1, Integer.toString(Integer.parseInt(snailfishnumber.substring(i+1, end_idx+1))+num1));

				return addToRight(snailfishnumber, num2, idx+snailfishnumber.length()-og_length);
			}
		}
		snailfishnumber.insert(idx, "0");
		return addToRight(snailfishnumber, num2, idx+snailfishnumber.length()-og_length);
	}

	//add the right number of the pair to the closest number to the right or insert 0
	public static StringBuilder addToRight(StringBuilder snailfishnumber, int num, int idx) {
		for (int i = idx; i < snailfishnumber.length(); i++) {
			if (Character.isDigit(snailfishnumber.charAt(i))){
				int start_idx = i;
				while(Character.isDigit(snailfishnumber.charAt(i))) {
					i++;
				}
				snailfishnumber.replace(start_idx, i, Integer.toString(Integer.parseInt(snailfishnumber.substring(start_idx, i))+num));
				return snailfishnumber;
			}
		}
		snailfishnumber.insert(idx, "0");
		return snailfishnumber;
	}

	//peek if the next pair is an explodable pair
	public static int peekExplodableNumber(StringBuilder snailfishnumber, int idx) {
		while(Character.isDigit(snailfishnumber.charAt(idx))) {
			idx++;
		}
		//skip the ,
		idx++;
		if(!Character.isDigit(snailfishnumber.charAt(idx)))
			return 0;
		while(Character.isDigit(snailfishnumber.charAt(idx))) {
			idx++;
		}
		return idx;
	}
}