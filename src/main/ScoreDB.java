package main;

import java.util.HashMap;
import java.util.Map;

public class ScoreDB {

	private Map<String, Double> wordScoreMap = new HashMap<>();

	public boolean writeScoreDB(WordScore wordscore) {
		wordScoreMap.put(wordscore.word, wordscore.score);
		return true;
	}

	public double readScore(String word) {
		return wordScoreMap.get(word);
	}
}
