package poopheadgui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RulesTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayable() {

		// maybe try to break these down into smaller easier to follow method chunks

		CardGroup pile = new CardGroup();

		Card two = new Card(1);
		Card three = new Card(2);
		Card six = new Card(5);
		Card seven = new Card(6);
		Card eight = new Card(7);
		Card nine = new Card(8);
		Card ace = new Card(13);

		pile.addCard(ace);
		assertTrue(Rules.playable(two, pile));
		assertFalse(Rules.playable(nine, pile));

		pile.addCard(three);
		assertTrue(Rules.playable(ace, pile));

		pile.addCard(seven);
		assertTrue(Rules.playable(six, pile));
		assertTrue(Rules.playable(eight, pile));
		assertFalse(Rules.playable(nine, pile));
		assertFalse(Rules.playable(ace, pile));
	}

	@Test
	public void testBurnable() {
		CardGroup pile = new CardGroup();

		Card ten = new Card(9);

		pile.addCard(ten);
		assertTrue(Rules.burnable(pile));

		Card three = new Card(2);

		pile.addCard(three);
		pile.addCard(three);
		pile.addCard(three);
		pile.addCard(three);

		assertTrue(Rules.burnable(pile));

	}

	@Test
	public void testEnoughCards() {
		CardGroup hand = new CardGroup();

		Card card = new Card(7);

		hand.addCard(card);
		hand.addCard(card);

		CardGroup deck = new CardGroup();

		// should be true since no deck cards
		assertTrue(Rules.enoughCards(hand, deck));

		deck.addCard(card);
		// false as now deck card available
		assertFalse(Rules.enoughCards(hand, deck));

		hand.addCard(card);
		// true, have enough cards now
		assertTrue(Rules.enoughCards(hand, deck));

	}

}
