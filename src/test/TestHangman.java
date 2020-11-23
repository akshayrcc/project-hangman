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
	void test_lengthOfFetchedWord() {
		Hangman hangman = new Hangman();
		String word = hangman.fetchWord();
		assertTrue(word.length() == 5);
	}

	@Test
	void test_lengthOfFetchedWordRandom() {
		Random ran = new Random();
		int requestedLength = ran.nextInt(6) + 5;
		System.out.println("requestedLength" + requestedLength);
		Hangman hangman = new Hangman();
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
		while (round < 100) {
			requestedLength = random.nextInt(6) + 5;
			word = hangman.fetchWord(requestedLength);
			round++;
			assertTrue(usedWordSet.add(word));
		}
	}
}
