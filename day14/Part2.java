import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Part2 {
	public static void main(String[] args) {
		String line = null, template = null;
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

		System.out.print(solvePart2(insertion_rules, template));
	}

	//instead of solving recursively we just keep track of how many pairs of each type there are
	//for example NN -> NCN, therefore: <NN, 3> -> <NC, 3> <CN, 3>
	public static long solvePart2(HashMap<String, Character> insertion_rules, String template) {
		HashMap<String, Long> number_of_pairs = new HashMap<String, Long>();

		//add the pairs in the template to the Map
		for (int i = 0, n = template.length()-1; i < n; i++) {
			String key = template.substring(i, i+2);
			if (number_of_pairs.containsKey(key))
				number_of_pairs.replace(key, number_of_pairs.get(key)+1);
			else
				number_of_pairs.put(key, (long) 1);
		}

		//generate new empty HashMap
		HashMap<String, Long> new_pairs = new HashMap<String, Long>();

		for (int i = 0; i < 40; i++) {
			new_pairs.clear(); //clear after every iteration

			//add newly generated pairs to cleared HashMap
			for (HashMap.Entry<String,Long> pair : number_of_pairs.entrySet()){
				//generate the new keys
				//for example NN -> C becomes key1: NC and key2: CN
				String key1 = "" + pair.getKey().charAt(0) + insertion_rules.get(pair.getKey());
				String key2 = "" + insertion_rules.get(pair.getKey()) + pair.getKey().charAt(1);
				if(new_pairs.containsKey(key1))
					new_pairs.replace(key1, new_pairs.get(key1)+pair.getValue());
				else
					new_pairs.put(key1, pair.getValue());
				if(new_pairs.containsKey(key2))
					new_pairs.replace(key2, new_pairs.get(key2)+pair.getValue());
				else
					new_pairs.put(key2, pair.getValue());
			}

			//clear count of previous pairs
			number_of_pairs.clear();

			//update our Map with the new values
			for (HashMap.Entry<String,Long> pair : new_pairs.entrySet()){
				number_of_pairs.put(pair.getKey(), pair.getValue());
			}
		}

		long array[] = new long[26];

		//count occurences of single char
		for (HashMap.Entry<String,Long> pair : number_of_pairs.entrySet()){
			char c1 = pair.getKey().charAt(0);
			int idx = c1 - 65;
			array[idx] = array[idx] + pair.getValue();
		}
		//since the last char has not been accounted for in this method we add it manually
		int idx = template.charAt(template.length()-1) - 65;
		array[idx] = array[idx] + 1;

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
