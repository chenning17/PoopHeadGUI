package poopheadgui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// constructor test too? how do you make one?

	@Test
	public void testGetCardNumber() {
		Card card4 = new Card(4);

		assertEquals(4, card4.getCardNumber());

		Card card42 = new Card(42);

		assertEquals(42, card42.getCardNumber());
	}

	@Test
	public void testSetCardNumber() {
		Card card = new Card(2);

		card.setCardNumber(5);

		assertEquals(5, card.getCardNumber());
		assertNotEquals(2, card.getCardNumber());
	}

	@Test
	public void testStringCard() {
		Card card_4h = new Card(3);
		Card card_4s = new Card(42);

		
		assertEquals("4h", card_4h.stringCard());
		assertEquals("4s", card_4s.stringCard());
		
		assertNotEquals("5h", card_4s.stringCard());
	}

	@Test
	public void testGetCardSuit() {
		Card card1 = new Card(3);
		Card card2 = new Card(16);
		Card card3 = new Card(29);
		Card card4 = new Card(42);

		int suit1 = card1.getCardSuit();
		int suit2 = card2.getCardSuit();
		int suit3 = card3.getCardSuit();
		int suit4 = card4.getCardSuit();

		// card 4 = 4 of hearts, hearts = suit 1
		assertEquals(1, suit1);

		// card 17 = 4 of clubs, clubs = suit 2
		assertEquals(2, suit2);

		// card 30 = 4 of diamonds, diamonds = suit 3
		assertEquals(3, suit3);

		// card 43 = 4 of spades, spades = suit 4
		assertEquals(4, suit4);

	}

	@Test
	public void testGetCardValue() {
		Card card4 = new Card(3);

		assertEquals(4, card4.getCardValue());

		Card card42 = new Card(42);

		assertEquals(4, card42.getCardValue());
	}

	@Test
	public void testIsTwo() {
		Card cardTwo = new Card(1);
		Card cardNotTwo = new Card(4);

		assertTrue(cardTwo.isTwo());
		assertFalse(cardNotTwo.isTwo());
	}

	@Test
	public void testIsSeven() {
		Card cardSeven = new Card(6);
		Card cardNotSeven = new Card(4);

		assertTrue(cardSeven.isSeven());
		assertFalse(cardNotSeven.isSeven());
	}

	@Test
	public void testIsSevenOrLower() {
		Card cardSeven = new Card(6);
		Card cardLowerThanSeven = new Card(4);
		Card cardHigherThanSeven = new Card(9);

		assertTrue(cardSeven.isSevenOrLower());
		assertTrue(cardLowerThanSeven.isSevenOrLower());

		assertFalse(cardHigherThanSeven.isSevenOrLower());
	}

	@Test
	public void testIsEight() {
		Card cardEight = new Card(7);
		Card cardNotEight = new Card(4);

		assertTrue(cardEight.isEight());
		assertFalse(cardNotEight.isEight());
	}

	@Test
	public void testIsTen() {
		Card cardTen = new Card(9);
		Card cardNotTen = new Card(4);

		assertTrue(cardTen.isTen());
		assertFalse(cardNotTen.isTen());
	}

	@Test
	public void testIsAlwaysPlayable() {
		// always playable cards
		Card cardTwo = new Card(1);
		Card cardEight = new Card(7);
		Card cardTen = new Card(9);

		assertTrue(cardTwo.isAlwaysPlayable());
		assertTrue(cardEight.isAlwaysPlayable());
		assertTrue(cardTen.isAlwaysPlayable());
		
		// not always playable cards
		Card cardThree = new Card(2);
		Card cardSeven = new Card(6);
		Card cardNine = new Card(8);

		assertFalse(cardThree.isAlwaysPlayable());
		assertFalse(cardSeven.isAlwaysPlayable());
		assertFalse(cardNine.isAlwaysPlayable());
	}

}
