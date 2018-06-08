package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Utilities;
import model.Player;

public class Controller {

	GameData data;
	private int sleeptime = 50;

	public Controller() {
		boolean newGame = true;
		data = new Initializer(this, newGame).getData();
		data.gd.display("Player " + data.playQueue.peek().getId() + " plays first! \n");
		new Round().start();
	}

	/**
	 * Finishes the game.
	 */
	private void endGame() {
		data.gd.displayClear();
		data.gd.display("END OF GAME \n");
		int score1 = data.players.get(0).getMoney() - data.players.get(0).getLoan();
		int score2 = data.players.get(1).getMoney() - data.players.get(1).getLoan();
		data.gd.display("Player 1 score: " + score1 + " ! \nPlayer 2 score: " + score2 + " ! \n");
		String s = "Game finishes with ";
		if (score1 > score2) {
			s += "PLAYER 1 VICTORY";
		} else if (score1 < score2) {
			s += "PLAYER 2 VICTORY";
		} else {
			s += "DRAW";
		}
		s += ". \n\n\n\nThank you for playing! \n Manos Chalkiadakis - Computer Science Department. ";
		data.gd.display(s);
	}

	/**
	 * Handles the board action after Player rolls the dice and lands on board
	 * position.
	 */
	private class BoardAction extends Thread {

		Player p;

		BoardAction() {
			p = data.playsNow;
			this.start();
		}

		public void run() {
			RequestDice d, dd;
			Player opp = Utilities.opponent(data, p);
			data.gd.display("Landed on ");
			switch (data.board.pos[p.getDay()].name) {
			case "pay":
				data.gd.display("PayDay! End of month! \n");
				p.payday();
				break;

			case "mc1":
				data.gd.display("MailCard. \nYou get one MailCard! \n");
				p.performActionMailCard(1);
				break;

			case "mc2":
				data.gd.display("MailCard. \nYou get two MailCards! \n");
				p.performActionMailCard(2);
				break;

			case "deal":
				data.gd.display("DealCard. \nYou get a Deal! \n");
				p.performActionDealCard(false);
				break;

			case "sweep":
				data.gd.display("SweepStakes. \nRoll Dice to get that many thousands! \n");
				d = new RequestDice(p);
				while (d.isAlive()) {
					try {
						sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int money = p.getDice() * 1000;
				data.gd.display("You get " + money + "! \n");
				p.addMoney(money);
				break;

			case "lottery":
				data.gd.display("Lottery. \nEach player chooses a number to get 1000! \n");
				Integer no1, no2, dice;
				ArrayList<Integer> choices = new ArrayList<Integer>();
				for (int i = 0; i < 6; i++)
					choices.add(i + 1);

				do {
					no1 = (Integer) JOptionPane.showInputDialog(data.gd.view,
							"Choose number(Player " + data.playsNow.getId() + "): ", "Lottery",
							JOptionPane.QUESTION_MESSAGE, null, choices.stream().toArray(Integer[]::new), null);
				} while (no1 == null || no1 < 1 || no1 > 6);
				data.gd.display("Player " + data.playsNow.getId() + " chose " + no1 + ". \n");
				choices.remove((Integer) no1);
				do {
					no2 = (Integer) JOptionPane.showInputDialog(data.gd.view,
							"Choose number(Player " + opp.getId() + "): ", "Lottery", JOptionPane.QUESTION_MESSAGE,
							null, choices.stream().toArray(Integer[]::new), null);
				} while (no2 == null || no2 < 1 || no2 > 6);
				data.gd.display("Player " + opp.getId() + " chose " + no2 + ". \n");
				do {
					d = new RequestDice(p);
					while (d.isAlive()) {
						try {
							sleep(sleeptime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					dice = p.getDice();
				} while (dice != no1 && dice != no2);
				data.gd.display("Player ");
				if (dice == no1) {
					data.gd.display(String.valueOf(data.playsNow.getId()));
					p.addMoney(1000);
				} else {
					data.gd.display(String.valueOf(opp.getId()));
					opp.addMoney(1000);
				}
				data.gd.display(" won. \n");
				break;

			case "radio":
				data.gd.display("Radio Contest. \nHighest roll gets 1000! \n");
				int d1, d2;
				do {
					d = new RequestDice(p);
					dd = new RequestDice(opp);
					while (d.isAlive() || dd.isAlive()) {
						try {
							sleep(sleeptime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					d1 = p.getDice();
					d2 = opp.getDice();
				} while (d1 == d2);
				if (d1 > d2) {
					p.addMoney(1000);
				} else {
					opp.addMoney(1000);
				}
				break;
			case "buyer":
				data.gd.display("Buyer. \n");
				p.sellDeal();
				break;

			case "casino":
				data.gd.display("Family Casino Night \n");
				d = new RequestDice(p);
				while (d.isAlive()) {
					try {
						sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (p.getDice() % 2 == 1) {
					data.gd.display("Lost 500. Added to Jackpot. \n");
					p.pay(500);
					data.jackpot += 500;
				} else {
					data.gd.display("Won 500. \n");
					p.addMoney(500);
				}
				break;

			case "yard":
				data.gd.display("Yard Sale. \n");
				Integer choice = JOptionPane.showConfirmDialog(data.gd.view, "Do you want to buy from yard sale? ",
						"Yard Sale", JOptionPane.OK_OPTION);
				if (choice != 0) {
					break;
				}
				data.gd.display("Roll Dice to see how much you will pay. \n");
				d = new RequestDice(p);
				while (d.isAlive()) {
					try {
						sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int sum = p.getDice() * 100;
				data.gd.display("Got Deal for " + sum + "! \n");
				p.pay(sum);
				p.performActionDealCard(true);
				break;

			default:
				data.gd.display("Error. Sorry! \n");
			}
		}

	}

	/**
	 * Starts next round by dequeueing the first Player of the PlayQueue. If
	 * Queue is empty the game has finished.
	 */
	private class Round extends Thread {

		public void run() {
			Player p = data.playQueue.poll();
			if (p == null) {
				endGame();
				return;
			}
			data.gd.display("Player " + p.getId() + " plays now. \n");
			data.playsNow = p;

			RequestDice d = new RequestDice(p);
			while (d.isAlive()) {
				try {
					sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			p.move();
			BoardAction b = new BoardAction();
			while (b.isAlive()) {
				try {
					sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			data.gd.enableEndTurn(data.playsNow);

		}

	}

	/**
	 * Requests dice from the Player passed to the constructor. Enables him to
	 * roll dice until so is done.
	 * 
	 * @author Manos
	 *
	 */
	private class RequestDice extends Thread {

		Player p;

		RequestDice(Player p) {
			this.p = p;
			this.start();

		}

		public void run() {
			p.rolled = false;
			data.gd.enableDice(p);
			while (!p.rolled) {
				try {
					sleep(sleeptime);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * Listener for Rolling Dice. Instantly removes the listener from the dice
	 * rolled to prevent rerolling.
	 * 
	 * @author Manos
	 *
	 */
	public class RollDice_Listener implements ActionListener {

		private Player p;

		public RollDice_Listener(Player p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			data.gd.disableDice(p);
			p.rolled = true;
			int dice = Utilities.random(1, 6);
			System.out.println("Player " + p.getId() + " rolled " + dice + "!");
			p.setDice(dice);
		}

	}

	/**
	 * Listener for Get Loan button. Getting a loan should be available at any
	 * time, even not in your turn.
	 * 
	 * @author Manos
	 *
	 */
	public class Loan_Listener implements ActionListener {

		private Player p;

		public Loan_Listener(Player p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			p.showGetLoan();
		}
	}

	/**
	 * Listener for End Turn. A Player should be able to end his turn after he
	 * has rolled the dice and completed all necessary actions upon landing at
	 * new position.
	 * 
	 * @author Manos
	 *
	 */
	public class EndTurn_Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			data.gd.disableEndTurn(data.playsNow);
			System.out.println("Player " + data.playsNow.getId() + " ended turn! ");
			if (data.playsNow.stillPlays)
				data.playQueue.add(data.playsNow);
			data.gd.displayClear();
			new Round().start();
		}

	}

	/**
	 * Listener for View Deal Cards. Viewing your Deal Cards should be available
	 * at anay time, even not in your turn.
	 * 
	 * @author Manos
	 *
	 */
	public class ViewDealCards_Listener implements ActionListener {

		private Player p;

		public ViewDealCards_Listener(Player p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println(p.getId());
			p.showDealCards();
		}

	}

}
