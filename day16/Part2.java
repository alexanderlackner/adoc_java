import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

interface Operations {
	long run(boolean b, long x, long y);
}

public class Part2 {

	//operations for the different package types, boolean is explained down below
	public static Operations add = (b, x, y) -> x+y;
	public static Operations product = (b, x, y) -> {if(b){return y;} return x*y;};
	public static Operations minimum = (b, x, y) -> {if(b){return y;}if(x<y){return x;} return y;};
	public static Operations maximum = (b, x, y) -> {if(x>y){return x;} return y;};
	public static Operations greater = (b, x, y) -> {if(b){return y;}if(x>y){return 1;} return 0;};
	public static Operations smaller = (b, x, y) -> {if(b){return y;}if(x<y){return 1;} return 0;};
	public static Operations equal = (b, x, y) -> {if(b){return y;}if(x==y){return 1;} return 0;};

	//creating array of operations, each index corresponds to the package type that triggers it
	//4 is null since this is a literal and we don't need to compute it
	public static final Operations[] op_array = {add, product, minimum, maximum, null, greater, smaller, equal};

	public static void main(String[] args) {

		HashMap<Character,String> dictionary = buildDictionary();
		String line = null;

		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			line = br.readLine();
		} catch (Exception e) {
			System.out.println("crashed reading file: "+e.getMessage());
		}

		String binary_string = hexToBinary(line, dictionary);
		System.out.println(resolveSubPackage(binary_string, 0).total);
	}

	//resolve the subpackage and call the right methods to handle packages
	public static Tuple resolveSubPackage(String binary_string, int idx) {
		int op = (int) binaryToDecimal(binary_string.substring(idx+3, idx+6));
		Tuple t;
		if (op == 4)
			t = handleLiteral(binary_string, idx);
		else if (binary_string.charAt(idx+6) == '0')
			t = handleLengthBased(binary_string, idx, op_array[op]);
		else
			t = handlePackageBased(binary_string, idx, op_array[op]);
		return t;
	}

	//handle literals
	public static Tuple handleLiteral(String binary_string, int idx) {
		idx += 6;
		StringBuilder sb = new StringBuilder();
		while(binary_string.charAt(idx) == '1') {
			sb.append(binary_string.substring(idx+1, idx+5));
			idx+=5;
		}
		sb.append(binary_string.substring(idx+1, idx+5));
		idx += 5;
		long result = binaryToDecimal(sb.toString());
		return new Tuple(result, idx);
	}

	//handle the length based subpackages
	public static Tuple handleLengthBased(String binary_string, int idx, Operations op) {
		long result = 0L;
		boolean b = true;
		int goal_idx = idx + (int) binaryToDecimal(binary_string.substring(idx+7, idx+22)) + 22;
		idx += 22;
		while(idx < goal_idx) {
			Tuple t = resolveSubPackage(binary_string, idx);
			idx = t.idx;
			//the way I implemented it a multiplication of 5*6 leads to:
			//a multiplication of result*5 and a multiplication of result*6
			//the first time result is 0  but 5*0 needs to return 5 as specified
			//therefore we keep track if we have called the procedure before
			//just checking in the procedure if the result is 0 and returning 5 does not work since this might fallsify the result if we actually pass 0
			result = op.run(b, result, t.total);
			b = false;
		}
		return new Tuple(result, idx);
	}

	//handle the package based subpackages
	public static Tuple handlePackageBased(String binary_string, int idx, Operations op) {
		long result = 0L;
		boolean b = true;
		int subpackets = (int) binaryToDecimal(binary_string.substring(idx+7, idx+18));
		idx +=18;
		while(subpackets>0) {
			Tuple t = resolveSubPackage(binary_string, idx);
			idx = t.idx;
			//same reasoning as above
			result = op.run(b, result, t.total);
			subpackets--;
			b = false;
		}
		return new Tuple(result, idx);
	}

	//calculating decimal number from its binary representation
	public static long binaryToDecimal(String binary_string) {
		long total = 0L;
		for (int i = 0; i < binary_string.length(); i++) {
			if (binary_string.charAt(i) == '1')
				//TIL that java ALWAYS computes in integers even if the variable the result is assigned to is long
				//the way to circumvent this is by casting the first number or variable in its computation to long
				//this cost me 2.5hrs of my life...
				total += (long) 1 << ((binary_string.length()-(i+1)));
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
