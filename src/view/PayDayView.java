package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.GameData;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class PayDayView {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public PayDayView(GameData data) {
		initialize(data);
		this.frame.setVisible(true);
		this.frame.setResizable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(GameData data) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1028, 782);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayout(null);

		JLabel background = new JLabel("");
		background.setBounds(0, 0, 1024, 754);
		background.setIcon(new ImageIcon(PayDayView.class.getResource("/board/bg_green.png")));
		layeredPane.add(background);

		JLabel logo = new JLabel("");
		logo.setBounds(64, 0, 500, 242);
		layeredPane.setLayer(logo, 1);
		logo.setIcon(new ImageIcon(PayDayView.class.getResource("/board/logo.png")));
		layeredPane.add(logo);

		JPanel days = new JPanel();
		days.setBounds(10, 245, 628, 26);
		layeredPane.setLayer(days, 1);
		layeredPane.add(days);
		days.setLayout(new GridLayout(0, 7, 0, 0));

		JLabel lblSunday = new JLabel("Sunday");
		days.add(lblSunday);

		JLabel lblMonday = new JLabel("Monday");
		days.add(lblMonday);

		JLabel lblTuesday = new JLabel("Tuesday");
		days.add(lblTuesday);

		JLabel lblWednesday = new JLabel("Wednesday");
		days.add(lblWednesday);

		JLabel lblThursday = new JLabel("Thursday");
		days.add(lblThursday);

		JLabel lblFriday = new JLabel("Friday");
		days.add(lblFriday);

		JLabel lblSaturday = new JLabel("Saturday");
		days.add(lblSaturday);

		JPanel board = new JPanel();
		board.setBounds(10, 275, 628, 460);
		board.setOpaque(false);
		layeredPane.setLayer(board, 1);
		layeredPane.add(board);
		board.setLayout(new GridLayout(5, 7));

		JPanel players = new JPanel();
		players.setBounds(10, 275, 628, 460);
		layeredPane.setLayer(players, 2);
		layeredPane.add(players);
		players.setOpaque(false);
		players.setLayout(new GridLayout(5, 7));

		JPanel p1 = new JPanel();
		p1.setBounds(648, 11, 358, 253);
		layeredPane.setLayer(p1, 1);
		layeredPane.add(p1);
		p1.setLayout(null);

		JLabel player1 = new JLabel("Player 1");
		player1.setFont(new Font("Tahoma", Font.BOLD, 20));
		player1.setBounds(199, 11, 149, 21);
		p1.add(player1);

		JLabel dice1 = new JLabel("");
		dice1.setIcon(new ImageIcon(PayDayView.class.getResource("/board/dice-1.jpg")));
		dice1.setBounds(199, 93, 149, 149);
		p1.add(dice1);

		JLabel money1label = new JLabel("Money: ");
		money1label.setBounds(10, 11, 46, 14);
		p1.add(money1label);

		JLabel loan1label = new JLabel("Loan: ");
		loan1label.setBounds(10, 36, 46, 14);
		p1.add(loan1label);

		JLabel bills1label = new JLabel("Bills: ");
		bills1label.setBounds(10, 61, 46, 14);
		p1.add(bills1label);

		JLabel money1 = new JLabel("0");
		money1.setBounds(58, 11, 144, 14);
		p1.add(money1);

		JLabel loan1 = new JLabel("0");
		loan1.setBounds(58, 36, 144, 14);
		p1.add(loan1);

		JLabel bills1 = new JLabel("0");
		bills1.setBounds(58, 61, 61, 14);
		p1.add(bills1);

		JLabel month1label = new JLabel("Month: ");
		month1label.setBounds(199, 43, 46, 14);
		p1.add(month1label);

		JLabel day1label = new JLabel("Day: ");
		day1label.setBounds(199, 68, 46, 14);
		p1.add(day1label);

		JLabel month1 = new JLabel("0");
		month1.setBounds(255, 43, 46, 14);
		p1.add(month1);

		JLabel day1 = new JLabel("0");
		day1.setBounds(255, 68, 46, 14);
		p1.add(day1);

		JPanel buttons1 = new JPanel();
		buttons1.setBounds(10, 93, 179, 149);
		p1.add(buttons1);
		buttons1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton rolldice1 = new JButton("Roll Dice");
		buttons1.add(rolldice1);

		JButton endturn1 = new JButton("End Turn");
		buttons1.add(endturn1);

		JButton getloan1 = new JButton("Get Loan");
		buttons1.add(getloan1);

		JButton viewcards1 = new JButton("View Cards");
		buttons1.add(viewcards1);

		JPanel p2 = new JPanel();
		p2.setBounds(648, 489, 358, 253);
		layeredPane.setLayer(p2, 1);
		p2.setLayout(null);
		layeredPane.add(p2);

		JLabel player2 = new JLabel("Player 2");
		player2.setFont(new Font("Tahoma", Font.BOLD, 20));
		player2.setBounds(199, 11, 149, 21);
		p2.add(player2);

		JLabel dice2 = new JLabel("");
		dice2.setIcon(new ImageIcon(PayDayView.class.getResource("/board/dice-1.jpg")));
		dice2.setBounds(199, 93, 149, 149);
		p2.add(dice2);

		JLabel money2label = new JLabel("Money: ");
		money2label.setBounds(10, 11, 46, 14);
		p2.add(money2label);

		JLabel loan2label = new JLabel("Loan: ");
		loan2label.setBounds(10, 36, 46, 14);
		p2.add(loan2label);

		JLabel bills2label = new JLabel("Bills: ");
		bills2label.setBounds(10, 61, 46, 14);
		p2.add(bills2label);

		JLabel money2 = new JLabel("0");
		money2.setBounds(58, 11, 144, 14);
		p2.add(money2);

		JLabel loan2 = new JLabel("0");
		loan2.setBounds(58, 36, 144, 14);
		p2.add(loan2);

		JLabel bills2 = new JLabel("0");
		bills2.setBounds(58, 61, 61, 14);
		p2.add(bills2);

		JLabel month2label = new JLabel("Month: ");
		month2label.setBounds(199, 43, 46, 14);
		p2.add(month2label);

		JLabel day2label = new JLabel("Day: ");
		day2label.setBounds(199, 68, 46, 14);
		p2.add(day2label);

		JLabel month2 = new JLabel("0");
		month2.setBounds(255, 43, 46, 14);
		p2.add(month2);

		JLabel day2 = new JLabel("0");
		day2.setBounds(255, 68, 46, 14);
		p2.add(day2);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 93, 179, 149);
		p2.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton rolldice2 = new JButton("Roll Dice");
		panel_1.add(rolldice2);

		JButton endturn2 = new JButton("End Turn");
		panel_1.add(endturn2);

		JButton getloan2 = new JButton("Get Loan");
		panel_1.add(getloan2);

		JButton viewcards2 = new JButton("View Cards");
		panel_1.add(viewcards2);

		JPanel box = new JPanel();
		box.setBounds(648, 275, 358, 203);
		layeredPane.setLayer(box, 1);
		layeredPane.add(box);
		box.setLayout(null);

		JTextPane textArea = new JTextPane();
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		textArea.setBounds(10, 41, 338, 151);
		box.add(textArea);

		JLabel lblNewLabel = new JLabel("Information Box");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 338, 22);
		box.add(lblNewLabel);

		JPanel jackpot = new JPanel();
		jackpot.setBounds(399, 648, 211, 106);
		layeredPane.add(jackpot);
		jackpot.setOpaque(false);
		layeredPane.setLayer(jackpot, 2);
		jackpot.setLayout(new BorderLayout(0, 0));

		JLabel jplogo = new JLabel("");
		jackpot.add(jplogo, BorderLayout.CENTER);
		layeredPane.setLayer(jplogo, 3);
		jplogo.setIcon(new ImageIcon(PayDayView.class.getResource("/board/jackpot.png")));

		JPanel jppanel = new JPanel();
		jppanel.setOpaque(false);
		jackpot.add(jppanel, BorderLayout.SOUTH);

		JLabel jplabel = new JLabel("JackPot: ");
		jppanel.add(jplabel);
		jplabel.setFont(new Font("Tahoma", Font.BOLD, 22));

		JLabel jp = new JLabel("0");
		jppanel.add(jp);
		jp.setFont(new Font("Tahoma", Font.BOLD, 22));

		JLabel[] bpos = new JLabel[32];
		JPanel[] ppos = new JPanel[32];
		for (int i = 0; i < 32; i++) {
			bpos[i] = new JLabel(new ImageIcon(
					new ImageIcon(PayDayView.class.getResource("/board/" + data.board.pos[i].name + ".png")).getImage()
							.getScaledInstance(91, 94, Image.SCALE_SMOOTH)));
			ppos[i] = new JPanel();
			ppos[i].setLayout(new GridLayout(1, 2));
			ppos[i].setOpaque(false);
		}

		for (int i = 0; i < 32; i++) {
			board.add(bpos[i]);
			players.add(ppos[i]);
		}

		// Player 1
		data.gd.buttons[0] = rolldice1;
		data.gd.buttons[1] = endturn1;
		data.gd.buttons[2] = getloan1;
		data.gd.buttons[3] = viewcards1;
		data.gd.labels[0] = month1;
		data.gd.labels[1] = day1;
		data.gd.labels[2] = money1;
		data.gd.labels[3] = loan1;
		data.gd.labels[4] = bills1;
		data.gd.labels[5] = dice1;
		// Player2
		data.gd.buttons[4] = rolldice2;
		data.gd.buttons[5] = endturn2;
		data.gd.buttons[6] = getloan2;
		data.gd.buttons[7] = viewcards2;
		data.gd.labels[6] = month2;
		data.gd.labels[7] = day2;
		data.gd.labels[8] = money2;
		data.gd.labels[9] = loan2;
		data.gd.labels[10] = bills2;
		data.gd.labels[11] = dice2;
		// Board
		data.gd.labels[12] = jp;
		data.gd.positions = ppos;
		data.gd.messageBox = textArea;

	}

	public JFrame getFrame() {
		return this.frame;
	}
}
