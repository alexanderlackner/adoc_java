import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Part1 {

	public static void main(String[] args) {

		HashMap<Character,String> dictionary = buildDictionary();
		String line = null;

		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			line = br.readLine();
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		String binary_string = hexToBinary(line, dictionary);
		System.out.println(solvePart1(binary_string));
	}

	public static int solvePart1(String binary_string) {
		int total = 0;
		int idx = 0;

		while (binary_string.length()-idx >= 11) {
			Tuple t = resolveSubPackage(binary_string, idx);
			total += t.total;
			idx = t.idx;
		}

		return total;
	}

	public static Tuple resolveSubPackage(String binary_string, int idx) {
		int total = binaryToDecimal(binary_string.substring(idx, idx+3));
		//found literal value
		if (binary_string.substring(idx+3, idx+6).equals("100")) {
			idx += 6;
			while(binary_string.charAt(idx) == '1') {
				idx+=5;
			}
			idx += 5;
			return new Tuple(total, idx);
		} else {
			//found length based package
			if (binary_string.charAt(idx+6) == '0') {
				int goal_idx = idx + binaryToDecimal(binary_string.substring(idx+7, idx+22));
				idx += 22;
				while(idx < goal_idx) {
					Tuple t = resolveSubPackage(binary_string, idx);
					idx = t.idx;
					total += t.total;
				}
			//found package number based package
			} else {
				int subpackets = binaryToDecimal(binary_string.substring(idx+7, idx+18));
				idx +=18;
				while(subpackets>0) {
					Tuple t = resolveSubPackage(binary_string, idx);
					idx = t.idx;
					total += t.total;
					subpackets--;
				}
			}
		}
		return new Tuple(total, idx);
	}

	//this works for short binary numbers as given in Part1
	//was rewritten for Part2
	public static int binaryToDecimal(String binary_string) {
		int total = 0, value = 1 << (binary_string.length()-1);
		for (int i = 0; i < binary_string.length(); i++) {
			if (binary_string.charAt(i) == '1')
				total += value;
			value = value/2;
		}
		return total;
	}

	//map each char in the hex string to his 4 bit binary representation and append it to a string
	public static String hexToBinary(String to_translate, HashMap<Character,String> dictionary) {
		StringBuilder output = new StringBuilder();

		for (char c : to_translate.toCharArray()) {
			output.append(dictionary.get(c));
		}

		return output.toString();
	}

	//bulding a HashMap to map each hex character to its 4 bit representation in binary
	public static HashMap<Character,String> buildDictionary() {
		HashMap<Character,String> dictionary = new HashMap<Character,String>();
		dictionary.put('0', "0000");
		dictionary.put('1', "0001");
		dictionary.put('2', "0010");
		dictionary.put('3', "0011");
		dictionary.put('4', "0100");
		dictionary.put('5', "0101");
		dictionary.put('6', "0110");
		dictionary.put('7', "0111");
		dictionary.put('8', "1000");
		dictionary.put('9', "1001");
		dictionary.put('A', "1010");
		dictionary.put('B', "1011");
		dictionary.put('C', "1100");
		dictionary.put('D', "1101");
		dictionary.put('E', "1110");
		dictionary.put('F', "1111");
	   return dictionary;
	}
}
