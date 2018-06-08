package model.cards;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.PayDayCards_Graphics;

public class PayDayCards {

	Deck<MailCard> mailDeck = new Deck<>();
	Deck<DealCard> dealDeck = new Deck<>();
	{
		// readFile("input/dealCards.csv", "Deal");
		// readFile("input/mailCards.csv", "Mail");
		readFile("input/dealCards_greeklish.csv", "Deal");
		readFile("input/mailCards_greeklish.csv", "Mail");
	}
	Component comp;

	public PayDayCards(Component comp) {
		shuffle();
		this.comp = comp;
	}

	/**
	 * Shuffles both Decks.
	 */
	private void shuffle() {
		mailDeck.shuffle();
		dealDeck.shuffle();
	}

	/**
	 * @return MailDeck
	 */
	public Deck<MailCard> getMailDeck() {
		return mailDeck;
	}

	/**
	 * @return DealDeck
	 */
	public Deck<DealCard> getDealDeck() {
		return dealDeck;
	}

	/**
	 * Reads the csv file to create each deck type.
	 * 
	 * @param path
	 *            Path to csv file.
	 * @param type
	 *            Mail/Deal to scan file for this type of card.
	 */
	private void readFile(String path, String type) {
		BufferedReader br = null;
		String sCurrentLine;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException ex) {
			Logger.getLogger(PayDayCards_Graphics.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				if (type.equals("Mail")) {
					mailDeck.add(new MailCard(sCurrentLine.split(",")));
				} else if (type.equals("Deal")) {
					dealDeck.add(new DealCard(sCurrentLine.split(",")));
				}
			}
			br.close();
		} catch (IOException ex) {
			Logger.getLogger(PayDayCards_Graphics.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
