package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Hangman;
import main.MockScoreDB;
import main.WordScore;

class TestHangman {
	static Random random;
	static Hangman hangman;
	int requestedLength;

	@BeforeAll
	public static void setupClass() {
		random = new Random();
		hangman = new Hangman();
		hangman.loadWords();
	}

	@BeforeEach
	public void setupTests() {
		requestedLength = random.nextInt(6) + 5;
		hangman.score = 0;
	}

	@Test
	void test_alphabetCountInAWord() {
		String word = "pizza";
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
	void test_uniquenessofFetchedWord() {
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
	void test_fetchClueBeforeAnyGuess() {
		String clue = hangman.fetchClue("pizza");
		assertEquals("-----", clue);
	}

	@Test
	void test_fetchClueAfterACorrectGuess() {
		String clue = hangman.fetchClue("pizza");
		String newclue = hangman.fetchClue("pizza", clue, 'a');
		assertEquals("----a", newclue);
	}

	@Test
	void test_fetchClueAfterAnIncorrectGuess() {
		String clue = hangman.fetchClue("pizza");
		String newclue = hangman.fetchClue("pizza", clue, 'x');
		assertEquals("-----", newclue);
	}

	@Test
	void test_whenInvalidGuessThenFetchClueThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> hangman.fetchClue("pizza", "-----", '*'));
	}

	/* Trails test cases */
	@Test
	void test_remainingTrailBeforeAnyGuess() {
		hangman.fetchWord(requestedLength);
		assertEquals(Hangman.MAX_TRIALS, hangman.remainingTrails);
	}

	@Test
	void test_remainingTrailAfterOneGuess() {
		hangman.fetchWord(requestedLength);
		hangman.fetchClue("pizza", "-----", 'x');
		assertEquals(Hangman.MAX_TRIALS - 1, hangman.remainingTrails);
	}

	/* scores test cases */
	@Test
	void test_scoreBeforeAnyGuess() {
		hangman.fetchWord(requestedLength);
		assertEquals(0, hangman.score);
	}

	@Test
	void test_scoreAfterACorrectGuess() {
		String word = "pizza";
		String clue = "-----";
		char guess = 'a';
		hangman.fetchClue(word, clue, guess);
		assertEquals((double) hangman.MAX_TRIALS / word.length(), hangman.score);
	}

	@Test
	void test_scoreAfterAnIncorrectGuess() {
		String word = "pizza";
		String clue = "-----";
		char guess = 'x';
		hangman.fetchClue(word, clue, guess);
		assertEquals(0, hangman.score);
	}

	@Test
	void test_saveScore() {
		assertTrue(hangman.saveScore(new WordScore("apple", 1)));
	}

	@Test
	void test_writeScoreDB() {
		WordScore ws = new WordScore("apple", 8);
		hangman.saveScore(ws);
		assertTrue(hangman.scoreDB.readScore("apple") == 8);
	}

	@Test
	void test_saveScoreUsingMockDB() {
		MockScoreDB msd = mock(MockScoreDB.class);
		Hangman hangman = new Hangman(msd);
		when(msd.writeScoreDB("apple", 10)).thenReturn(true);
		assertTrue(hangman.saveWordScore("apple", 10));
	}

	@AfterEach
	public void tearDownTest() {
		// to close common functonality if used
		requestedLength = 0;
	}

	@AfterAll
	public static void tearDownClass() {
		// to close common functonality if used
	}
}
