package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Hangman {

	Set<String> usedWordSet = new HashSet<>();

	public int countAlphabet(String word, char alphabet) {
		int result = 0;
		for (char c : word.toCharArray()) {
			if (c == alphabet)
				result++;
		}
		return result;
	}

	public String fetchWord() {
		return "Pizza";
	}

	public String fetchWord(int requestedLength) {
		String result = null;

		try (BufferedReader br = new BufferedReader(new FileReader("resources/WordSource.txt"))) {
			while ((result = br.readLine()) != null) {
				if (result.length() != requestedLength)
					continue;
				else if (usedWordSet.add(result))
					break;
			}
		} catch (FileNotFoundException ex) {
			// System.out.println(new File("WordSource.txt").getAbsolutePath());
			ex.printStackTrace();
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		return result;

		/*
		 * switch(requestedLength){ case 5: return "pizza"; case 6: return "cheese";
		 * case 7: return "chicken"; case 8: return "tomatoes"; case 9: return
		 * "pineapple"; case 10: return "mozzerella"; default: return null;}
		 */

	}

}
