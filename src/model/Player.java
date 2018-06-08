package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.GameData;
import main.Utilities;
import model.cards.DealCard;
import model.cards.MailCard;
import view.PayDayCards_Graphics;

public class Player {

	GameData data;
	private int id, money, bills = 0, loan = 0, day = 0, month = 1, dice = 1;
	private ArrayList<DealCard> dealCards = new ArrayList<>();
	private ArrayList<MailCard> billCards = new ArrayList<>();
	public boolean rolled, stillPlays = true;

	public Player(GameData data, int money, int id) {
		this.data = data;
		this.money = money;
		this.id = id;
	}

	/**
	 * Moves Player by "dice" steps.
	 *
	 */
	public void move() {
		if (dice == 6) {
			data.gd.display("Jackpot! Got " + data.jackpot + "! \n");
			jackpot();
		}
		if (day + dice > 31) {
			day = 31;
		} else {
			day += dice;
		}
		if (day % 7 == 0)
			match();
	}

	/**
	 * Adds money to Player's money.
	 * 
	 * @param amount
	 *            Money to be added to existing money.
	 */
	public void addMoney(int amount) {
		money += amount;
	}

	/**
	 * Adds bills to Player. Adds the billcard to the ArrayList of MailCards,
	 * unique to each player and adds the value of the card to bills.
	 * 
	 * @param card
	 *            Maillcard to add to bills.
	 */
	public void addBill(MailCard card) {
		bills += card.amount;
		billCards.add(card);
	}

	/**
	 * Gives loan to Player. Adds amount to money and loan
	 * 
	 * @param loan
	 *            Money to get loan.
	 */
	public void addLoan(int loan) {
		if (loan == 0)
			return;
		data.gd.display("Player " + this.id + " gets a loan of " + loan + ". \nBeware of interest! \n");
		addMoney(loan);
		this.loan += loan;
	}

	/**
	 * Pays the amount. If Player doens have enough money, gets loan
	 * automatically.
	 * 
	 * @param amount
	 *            Money to pay.
	 */
	public void pay(int amount) {
		int remainingMoney = money - amount;
		if (remainingMoney < 0) {
			data.gd.display("Player " + this.id + " automatically gets loan. \n");
			int toLoan = (((-remainingMoney) / 1000) + 1) * 1000;
			addLoan(toLoan);
			money -= amount;
		} else
			money = remainingMoney;
	}

	/**
	 * Pays the 10% loan interest. If Player doesnt have enough money, gets
	 * another loan automatically. Player can also pay existing loan.
	 */
	public void payLoan() {
		if (loan == 0)
			return;
		pay(loan / 10);
		Integer loanToPay = null;
		int p = JOptionPane.showConfirmDialog(data.gd.view, "Do you wish to pay the loan now?");
		if (p == 0)
			do {
				try {
					loanToPay = Integer.parseInt(JOptionPane.showInputDialog(data.gd.view,
							"How much do you want to pay?", "Pay Loan", JOptionPane.QUESTION_MESSAGE));
				} catch (Exception e) {
					return;
				}
			} while (loanToPay < 0 || loanToPay > loan);
		data.gd.display("Paid loan of " + loanToPay + ". \n");
		pay(loanToPay);
		loan -= loanToPay;
	}

	/**
	 * Pays the bills. If Player doens have enough money, gets loan
	 * automatically.
	 */
	public void payBills() {
		pay(bills);
		bills = 0;
		data.mailDeck.discard(billCards);
	}

	/**
	 * Sells DealCard after opening Dialog and choosing.
	 */
	public void sellDeal() {
		if (dealCards.isEmpty()) {
			data.gd.display("No Deals to sell. \n");
			return;
		}
		List<String> deals = new ArrayList<String>();
		DealCard d;
		for (int i = 0; i < dealCards.size(); i++) {
			d = dealCards.get(i);
			deals.add((i + 1) + ". " + d.message + " Sells for: " + d.value);
		}
		String[] options = deals.stream().toArray(String[]::new);
		String p = (String) JOptionPane.showInputDialog(data.gd.view, "Choose DealCard to sell", "Buyer!",
				JOptionPane.QUESTION_MESSAGE, null, options, null);
		if (p == null) {
			return;
		}
		int cardNo = Character.getNumericValue(p.charAt(0)) - 1;
		d = dealCards.remove(cardNo);
		data.dealDeck.discard(d);
		data.gd.display("Sold Deal for " + d.value + ". \n");
		addMoney(d.value);
	}

	/**
	 * Sunday Match handling.
	 */
	public void match() {
		Object[] options0 = { "Bet for Home", "Bet for Draw", "Bet for Guest", "Don't bet" };
		Icon image = new ImageIcon("/board/input/Barcelona_real.jpg");

		int outcome = Utilities.random(0, 2);
		int n = JOptionPane.showOptionDialog(data.gd.view, "Bet 500 for the outcome of Barcelona-Real", "Sunday FootBall Match!",
				JOptionPane.OK_OPTION, 0, image, options0, options0[3]);

		Object[] option = { "OK" };
		if (n == -1 || n == 3) {
			return;
		} else if (n == outcome) {
			JOptionPane.showOptionDialog(data.gd.view, "Won Bet!", "Sunday FootBall Match", JOptionPane.OK_OPTION, 0, image,
					option, option[0]);
			addMoney(1000);
		} else {
			JOptionPane.showOptionDialog(data.gd.view, "Lost Bet!", "Sunday FootBall Match", JOptionPane.OK_OPTION, 0, image,
					option, option[0]);
			pay(500);
		}
	}

	/**
	 * Adds jackpot money to Player, and sets it to zero.
	 */
	public void jackpot() {
		addMoney(data.jackpot);
		data.jackpot = 0;
	}

	/**
	 * Handles the completion of Board. Adds money, pays loan and bills for
	 * Player.
	 */
	public void payday() {
		addMoney(2500);
		payBills();
		payLoan();
		if (month != data.monthsToPlay) {
			month++;
			day = 0;
		} else
			stillPlays = false;
	}

	public void moveToBuyer() {
		for (int i = day; i < 31; i++) {
			if (data.board.pos[i].name.equals("buyer")) {
				day = i;
				sellDeal();
				return;
			}
		}
	}

	/**
	 * Draws MailCard(s), pops graphic showCard dialog and handles the card.
	 * 
	 * @param number
	 *            Amount of MailCards (1-2)
	 */
	public void performActionMailCard(int number) {
		MailCard[] card = new MailCard[number];
		for (int i = 0; i < number; i++) {
			card[i] = data.mailDeck.draw();
			PayDayCards_Graphics.showMailCard(card[i], data.gd.view);
		}
		boolean moveToBuyer = false;
		for (int i = 0; i < number; i++) {
			switch (card[i].typeEn) {
			case "Charity":
				pay(card[i].amount);
				data.jackpot += card[i].amount;
				break;
			case "Bill":
				addBill(card[i]);
				break;
			case "MadMoney":
				Utilities.opponent(data, this).pay(card[i].amount);
				addMoney(card[i].amount);
				break;
			case "PayTheNeighbor":
				pay(card[i].amount);
				Utilities.opponent(data, this).addMoney(card[i].amount);
				break;
			case "MoveToDealBuyer":
				moveToBuyer = true;
				break;
			case "Advertisement":
				break;
			}
		}
		if (moveToBuyer) {
			moveToBuyer();
		}
	}

	/**
	 * Draws DealCard, pops graphic showCard dialog and handles the card.
	 * 
	 * @param freeCard
	 *            True, if the card is from Yard Sale so you dont pay extra.
	 */
	public void performActionDealCard(boolean freeCard) {
		DealCard card = data.dealDeck.draw();

		int choise = PayDayCards_Graphics.showDealCard(card, data.gd.view, freeCard);
		if (choise == 0) {
			if (!freeCard)
				pay(card.cost);
			dealCards.add(card);
		} else {
			data.dealDeck.discard(card);
		}
	}

	/**
	 * Handles the Get Loan menu. 
	 */
	public void showGetLoan() {
		Integer loanToGet = null;
		do {
			try {
				loanToGet = Integer.parseInt(JOptionPane.showInputDialog(data.gd.view,
						"How much money do you want to get?", "Get Loan", JOptionPane.QUESTION_MESSAGE));
			} catch (Exception e) {
				return;
			}
		} while (loanToGet % 1000 != 0);
		addLoan(loanToGet);
	}

	public void showDealCards() {
		String s = "Deal Cards: ";
		String cards = "";
		DealCard d;
		for (int i = 0; i < dealCards.size(); i++) {
			d = dealCards.get(i);
			cards += "\n   " + d.message + " Cost: " + d.cost + " Sell Value: " + d.value;
		}
		if (cards.equals("")) {
			s += "\n   None ";
		} else {
			s += cards;
		}
		JOptionPane.showMessageDialog(data.gd.view, s, "View DealCards", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @return Player's ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return Player's Money
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * @return Player's Loan
	 */
	public int getLoan() {
		return this.loan;
	}

	/**
	 * @return Player's Bills
	 */
	public int getBills() {
		return this.bills;
	}

	/**
	 * Sets the dice.
	 * 
	 * @param dice
	 *            Dice to set to.
	 */
	public void setDice(int dice) {
		this.dice = dice;
	}

	/**
	 * @return Player's last rolled dice.
	 */
	public int getDice() {
		return dice;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
