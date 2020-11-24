package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Hangman {

	public static final int MAX_TRIALS = 10;
	Set<String> usedWordSet = new HashSet<>();
	ArrayList<String> wordsList = new ArrayList<>();
	public int remainingTrails;
	public int score = 0;
	public ScoreDB scoreDB = new ScoreDB();

	MockScoreDB msd;

	public Hangman() {
	}

	public Hangman(MockScoreDB msd) {
		this.msd = msd;
	}

	public int countAlphabet(String word, char alphabet) {
		int result = 0;
		for (char c : word.toCharArray()) {
			if (c == alphabet)
				result++;
		}
		return result;
	}

	public String fetchWord(int requestedLength) {
		remainingTrails = MAX_TRIALS;
		for (String result : wordsList) {
			if (result.length() != requestedLength)
				continue;
			else if (usedWordSet.add(result))
				return result;
		}
		return null;

		/*
		 * switch(requestedLength){ case 5: return "pizza"; case 6: return "cheese";
		 * case 7: return "chicken"; case 8: return "tomatoes"; case 9: return
		 * "pineapple"; case 10: return "mozzerella"; default: return null;}
		 */
	}

	public void loadWords() {
		String word = null;
		try (BufferedReader br = new BufferedReader(new FileReader("resources/WordSource.txt"))) {
			while ((word = br.readLine()) != null) {
				wordsList.add(word);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
	}

	public String fetchClue(String word) {
		StringBuilder clue = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			clue.append('-');
		}
		return clue.toString();
	}

	public String fetchClue(String word, String clue, char guess) {
		remainingTrails--;
		if (guess >= 'A' && guess <= 'Z') {
			guess += 32;
		}
		if (guess < 'a' || guess > 'z') {
			throw new IllegalArgumentException("Invalid Character");
		}

		StringBuilder newclue = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			if (guess == word.charAt(i) && guess != clue.charAt(i)) {
				newclue.append(guess);
				score += (double) MAX_TRIALS / word.length();
			} else {
				newclue.append(clue.charAt(i));
			}
		}
		return newclue.toString();
	}

	public boolean saveScore(WordScore wordscore) {
		return scoreDB.writeScoreDB(wordscore);
	}

	public boolean saveWordScore(String word, double score) {
		return msd.writeScoreDB(word, score);

		// return false;
	}

}
