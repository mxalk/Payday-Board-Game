package controller;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import model.Player;
import view.PayDayView;

public class GraphicsData {

	GameData data;
	public JButton[] buttons = new JButton[8];
	public JLabel[] labels = new JLabel[13];
	public JPanel[] positions = new JPanel[32];
	public JFrame view;
	private ImageIcon[] dices = new ImageIcon[6];
	public JTextPane messageBox = new JTextPane();
	volatile String text = "";

	/*
	 * buttons[0] = rolldice1 buttons[1] = endturn1 buttons[2] = getloan1
	 * buttons[3] = viewcards1 labels[0] = month1 labels[1] = day1 labels[2] =
	 * money1 labels[3] = loan1 labels[4] = bills1 labels[5] = dice1 buttons[4]
	 * = rolldice2 buttons[5] = endturn2 buttons[6] = getloan2 buttons[7] =
	 * viewcards2 labels[6] = month2 labels[7] = day2 labels[8] = money2
	 * labels[9] = loan2 labels[10] = bills2 labels[11] = dice2 // Board
	 * labels[12] = jp
	 * 
	 */

	public GraphicsData(GameData data) {
		data.gd = this;
		this.view = new PayDayView(data).getFrame();
		this.data = data;

		buttons[0].setEnabled(false);
		buttons[1].setEnabled(false);

		buttons[0].addActionListener(data.c.new RollDice_Listener(data.players.get(0)));
		buttons[1].addActionListener(data.c.new EndTurn_Listener());
		buttons[2].addActionListener(data.c.new Loan_Listener(data.players.get(0)));
		buttons[3].addActionListener(data.c.new ViewDealCards_Listener(data.players.get(0)));

		buttons[4].setEnabled(false);
		buttons[5].setEnabled(false);

		buttons[4].addActionListener(data.c.new RollDice_Listener(data.players.get(1)));
		buttons[5].addActionListener(data.c.new EndTurn_Listener());
		buttons[6].addActionListener(data.c.new Loan_Listener(data.players.get(1)));
		buttons[7].addActionListener(data.c.new ViewDealCards_Listener(data.players.get(1)));

		dices[0] = new ImageIcon("input/board/dice-1.jpg");
		dices[1] = new ImageIcon("input/board/dice-2.jpg");
		dices[2] = new ImageIcon("input/board/dice-3.jpg");
		dices[3] = new ImageIcon("input/board/dice-4.jpg");
		dices[4] = new ImageIcon("input/board/dice-5.jpg");
		dices[5] = new ImageIcon("input/board/dice-6.jpg");

		new GraphicsUpdate(data.players.get(0), data.players.get(1));
	}

	private class GraphicsUpdate extends Thread {

		Player p1, p2;

		GraphicsUpdate(Player p1, Player p2) {
			this.p1 = p1;
			this.p2 = p2;
			this.start();
		}

		public void run() {
			Pawns pawns = new Pawns();
			while (true) {
				labels[0].setText(((Integer) p1.getMonth()).toString());
				labels[6].setText(((Integer) p2.getMonth()).toString());

				labels[1].setText(((Integer) p1.getDay()).toString());
				labels[7].setText(((Integer) p2.getDay()).toString());

				labels[2].setText(((Integer) p1.getMoney()).toString());
				labels[8].setText(((Integer) p2.getMoney()).toString());

				labels[3].setText(((Integer) p1.getLoan()).toString());
				labels[9].setText(((Integer) p2.getLoan()).toString());

				labels[4].setText(((Integer) p1.getBills()).toString());
				labels[10].setText(((Integer) p2.getBills()).toString());

				labels[5].setIcon(dices[p1.getDice() - 1]);
				labels[11].setIcon(dices[p2.getDice() - 1]);

				labels[12].setText(((Integer) data.jackpot).toString());
				pawns.check();
				
				try {
					sleep(50);
				} catch (InterruptedException e) {
				}
			}
		}

	}

	private class Pawns {

		int pawn1, pawn2;
		JLabel p1 = new JLabel(new ImageIcon(
				new ImageIcon("input/board/pawn_blue.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		JLabel p2 = new JLabel(new ImageIcon(
				new ImageIcon("input/board/pawn_yellow.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		
		Pawns() {
			positions[0].add(p1);
			positions[0].add(p2);
		}

		void check() {
			if (pawn1 != data.players.get(0).getDay()) {
				positions[pawn1].remove(p1);
				positions[pawn1].repaint();
				pawn1 = data.players.get(0).getDay();
				positions[pawn1].add(p1);
				positions[pawn1].repaint();

			}
			if (pawn2 != data.players.get(1).getDay()) {
				positions[pawn2].remove(p2);
				positions[pawn2].repaint();
				pawn2 = data.players.get(1).getDay();
				positions[pawn2].add(p2);
				positions[pawn2].repaint();
			}
		}

	}

	public void displayClear() {
		this.text = "";
		print();
	}

	public void display(String text) {
		this.text += text;
		print();
	}

	private void print() {
		messageBox.setText(text);
	}

	public void enableDice(Player p) {
		int id = p.getId() - 1;
		buttons[0 + id * 4].setEnabled(true);
	}

	public void disableDice(Player p) {
		int id = p.getId() - 1;
		buttons[0 + id * 4].setEnabled(false);
	}

	public void enableEndTurn(Player p) {
		int id = p.getId() - 1;
		buttons[1 + id * 4].setEnabled(true);
	}

	public void disableEndTurn(Player p) {
		int id = p.getId() - 1;
		buttons[1 + id * 4].setEnabled(false);
	}

}
