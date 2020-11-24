package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import main.Hangman;

class TestHangman {
	@Test
	void test_alphabetCountInAWord() {
		String word = "Pizza";
		char alphabet = 'a';
		Hangman hangman = new Hangman();
		int count = hangman.countAlphabet(word, alphabet);
		assertEquals(1, count);
	}

	@Test
	void test_lengthOfFetchedWordRandom() {
		Random ran = new Random();
		int requestedLength = ran.nextInt(6) + 5;
		System.out.println(" Random requestedLength is" + requestedLength);
		Hangman hangman = new Hangman();
		hangman.loadWords();
		String word = hangman.fetchWord(requestedLength);
		assertTrue(word.length() == requestedLength);
	}

	@Test
	void uniquenessofFetchedWord() {
		Random random = new Random();
		int requestedLength = 0;
		Set<String> usedWordSet = new HashSet<>();
		int round = 0;
		String word = null;
		Hangman hangman = new Hangman();
		hangman.loadWords();
		while (round < 100) {
			requestedLength = random.nextInt(6) + 5;
			word = hangman.fetchWord(requestedLength);
			round++;
			assertTrue(usedWordSet.add(word));
		}
	}

	@Test
	void fetchClueBeforeAnyGuess() {
		Hangman hangman = new Hangman();
		String clue = hangman.fetchClue("Pizza");
		assertEquals("-----", clue);
	}

	@Test
	void fetchClueAfterACorrectGuess() {
		Hangman hangman = new Hangman();
		String clue = hangman.fetchClue("Pizza");
		String newclue = hangman.fetchClue("Pizza", clue, 'a');
		assertEquals("----a", newclue);
	}

	@Test
	void fetchClueAfterAnIncorrectGuess() {
		Hangman hangman = new Hangman();
		String clue = hangman.fetchClue("Pizza");
		String newclue = hangman.fetchClue("Pizza", clue, 'x');
		assertEquals("-----", newclue);
	}
}
