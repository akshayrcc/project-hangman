package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Hangman;

class TestHangman {
	Random random;
	Hangman hangman;
	int requestedLength;

	@BeforeAll
	public void setupClass() {
		random = new Random();
		hangman = new Hangman();
		hangman.loadWords();
	}

	@BeforeEach
	public void setupTests() {
		requestedLength = random.nextInt(6) + 5;
	}

	@Test
	void test_alphabetCountInAWord() {
		String word = "Pizza";
		char alphabet = 'a';
		int count = hangman.countAlphabet(word, alphabet);
		assertEquals(1, count);
	}

	@Test
	void test_lengthOfFetchedWordRandom() {
		System.out.println(" Random requestedLength is" + requestedLength);
		String word = hangman.fetchWord(requestedLength);
		assertTrue(word.length() == requestedLength);
	}

	@Test
	void uniquenessofFetchedWord() {
		Set<String> usedWordSet = new HashSet<>();
		int round = 0;
		String word = null;
		while (round < 100) {
			word = hangman.fetchWord(requestedLength);
			round++;
			assertTrue(usedWordSet.add(word));
		}
	}

	@Test
	void fetchClueBeforeAnyGuess() {
		String clue = hangman.fetchClue("Pizza");
		assertEquals("-----", clue);
	}

	@Test
	void fetchClueAfterACorrectGuess() {
		String clue = hangman.fetchClue("Pizza");
		String newclue = hangman.fetchClue("Pizza", clue, 'a');
		assertEquals("----a", newclue);
	}

	@Test
	void fetchClueAfterAnIncorrectGuess() {
		String clue = hangman.fetchClue("Pizza");
		String newclue = hangman.fetchClue("Pizza", clue, 'x');
		assertEquals("-----", newclue);
	}

	@AfterEach
	public void tearDownTest() {
		// to close common functonality if used
		requestedLength = 0;
	}

	@AfterAll
	public void tearDownClass() {
		// to close common functonality if used
	}
}
