import java.util.stream.Stream;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Part2 {

	public static void main(String args[]) {

		try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
			var wrapper = new Object(){ List<Long> resultList = new ArrayList<>(); };
			stream.forEach((line) -> {
				//check completion for each line
				long score = countOpenBrackets(line);
				//if the line is incomplete the total is greater 0
				if (score>0) {
					wrapper.resultList.add(score);
				}
			});
			//sort array of results
			Collections.sort(wrapper.resultList);
			//and print middle element
			System.out.println(wrapper.resultList.get(wrapper.resultList.size()/2));
		} catch (Exception e) {
			System.out.println("caught Exception :" + e.getLocalizedMessage());
		}
	}

	//count how many brackets have not been closed
	//return 0 if there are still closing brackets left meaning its an incorrect line which we disregard
	static long countOpenBrackets(String line) {
		long total = 0;
		line = checkForCompletion(line);
		for (int i = line.length()-1; i >= 0; i--) {
			if(charIsClosingBracket(line.charAt(i)))
				return 0;
			total = total*5 + getScore(line.charAt(i));
		}
		return total;
	}
	
	//remove all the closing brackets from string
	static String checkForCompletion(String line) {
		while (true) {
			line = line.replace("()", "");
			line = line.replace("[]", "");
			line = line.replace("{}", "");
			line = line.replace("<>", "");
			if (!line.contains("()") && !line.contains("[]") && !line.contains("<>") && !line.contains("{}"))
				return line;
		}
	}

	//get score for bracket
	static int getScore(char c) {
		switch (c) {
			case '(':
				return 1;
			case '[':
				return 2;
			case '{':
				return 3;
			case '<':
				return 4;
			default:
				return 0;
		}
	}

	//check if char is a closing bracket
	static boolean charIsClosingBracket(char current_char) {
		if (current_char == ')' || current_char == '>' || current_char == ']' || current_char == '}') {
			return true;
		}
		return false;
	}
}