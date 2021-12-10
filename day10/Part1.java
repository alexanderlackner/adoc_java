import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Part1 {

	public static void main(String args[]) {

		try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
			var wrapper = new Object(){ int result = 0;};
			stream.forEach((line) -> {
				//check error score for each line
				wrapper.result += countOpenBrackets(line);;
			});
			System.out.println(wrapper.result);
		} catch (Exception e) {
			System.out.println("caught Exception :" + e.getMessage());
		}
	}

	//find error bracket
	//if line is only incomplete return 0
	static int countOpenBrackets(String line) {
		line = checkForCompletion(line);
		for (int i = 0; i < line.length(); i++) {
			if(charIsClosingBracket(line.charAt(i)))
				return getScore(line.charAt(i));
		}
		return 0;
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
			case ')':
				return 3;
			case ']':
				return 57;
			case '}':
				return 1197;
			case '>':
				return 25137;
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