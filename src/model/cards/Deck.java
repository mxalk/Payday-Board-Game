package model.cards;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Deck<T> {

	private Stack<T> deck = new Stack<>();
	private ArrayList<T> discarded = new ArrayList<>(), outOfDeck = new ArrayList<>();
	private int cards = 0, totalCards = 0;

	Deck() {

	}

	Deck(T[] totalCards) {
		this.totalCards = totalCards.length;
		for (int i = 0; i < this.totalCards; i++) {
			deck.add(totalCards[i]);
		}
		shuffle();
	}

	/**
	 * Draws card from deck and adds it to secret Out-of-deck card pile.
	 * 
	 * @return Card drawn.
	 */
	public T draw() {
		cards--;
		T card = deck.pop();
		outOfDeck.add(card);
		return card;
	}

	/**
	 * Adds card to discard pile. If card was originally in deck removes it from
	 * secret Out-of-deck card pile.
	 * 
	 * @param card
	 */
	public void discard(T card) {
		discarded.add(card);
		outOfDeck.remove(card);
	}

	/**
	 * Adds cards to discard pile. If cards were originally in deck removes them
	 * from secret Out-of-deck card pile.
	 * 
	 * @param cards
	 */
	public void discard(ArrayList<T> cards) {
		T card;
		while (!cards.isEmpty()) {
			card = cards.remove(0);
			discarded.add(card);
			outOfDeck.remove(card);
		}

	}

	/**
	 * Adds card to deck. Deck has to be reshuffled. Card is added to discard
	 * pile.
	 * 
	 * @param card
	 */
	void add(T card) {
		discarded.add(card);
		this.totalCards++;
	}

	/**
	 * Randomly shuffles the deck.
	 */
	void shuffle() {
		Random r = new Random();
		while (!deck.empty())
			discarded.add(deck.pop());

		ArrayList<Integer> ints = new ArrayList<>();
		for (int i = 0; i < this.totalCards; i++) {
			ints.add(i);
		}

		while (discarded.size() != 0) {
			int index = r.nextInt(ints.size());
			ints.remove(index);
			deck.push(discarded.remove(index));
		}
		this.cards = this.totalCards;
	}

	/**
	 * @return True if deck is empty.
	 */
	boolean isEmpty() {
		return deck.empty();
	}

	/**
	 * Reconstructs the deck. All cards out of deck are added back to it and
	 * shuffled. ATTENTION: previous position of cards out of deck doesn't
	 * change.
	 */
	void reconstruct() {
		while (outOfDeck.size() != 0) {
			deck.add(outOfDeck.remove(0));
		}
		shuffle();
	}

	/**
	 * Removes any cards out of Deck, so when you reshuffle they are not added
	 * back. Option for removing the discard pile available. No shuffle.
	 * 
	 * @param keepDiscarded
	 *            False to remove discard pile also.
	 */
	void removeMissingCards(boolean keepDiscarded) {
		if (!keepDiscarded)
			this.discarded.clear();
		this.outOfDeck.clear();
	}

}
