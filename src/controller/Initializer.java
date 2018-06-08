package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

import main.Utilities;
import model.Player;
import model.cards.PayDayCards;
import model.positions.*;

public class Initializer {

	GameData data;

	public Initializer(Controller c, boolean newGame) {
		this.data = new GameData();
		data.c = c;
		if (newGame) {
			player();
			months();
			cards();
			board();
		} else {
			// load
		}
		data.gd = new GraphicsData(data);
	}

	/**
	 * Initializes Players, chooses who to play first.
	 */
	private void player() {
		ArrayList<Player> p = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Player player = new Player(data, 2500, i + 1);
			p.add(player);
		}
		data.players = p;

		int playerFirst = (Utilities.random(0, 1));
		Queue<Player> playQueue = new LinkedList<>();
		if (playerFirst == 1) {
			playQueue.add(p.get(1));
			playQueue.add(p.get(0));
		} else {
			playQueue.add(p.get(0));
			playQueue.add(p.get(1));
		}
		data.playQueue = playQueue;
	}

	/**
	 * Initializes months, prompts window to choose months to play.
	 */
	private void months() {
		Integer[] months = {1,2,3};
		Integer x = null;
		x = (Integer) JOptionPane.showInputDialog(null, "Choose months to play: ", "Months",
				JOptionPane.QUESTION_MESSAGE, null,
				months, null);
		if (x == null) System.exit(0);
		 data.monthsToPlay = x;
	}

	/**
	 * Initializes cards.
	 */
	private void cards() {
		PayDayCards pdc = new PayDayCards(null);
		data.mailDeck = pdc.getMailDeck();
		data.dealDeck = pdc.getDealDeck();
	}

	/**
	 * Initializes the board with random positions.
	 */
	private void board() {
		data.board = new Board(data);
	}

	/**
	 * Returns the gamedata generated from this instance of Initializer
	 * 
	 * @return GameData generated.
	 */
	public GameData getData() {
		return data;
	}

}
