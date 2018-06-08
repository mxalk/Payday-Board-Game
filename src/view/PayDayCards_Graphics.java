package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import model.cards.DealCard;
import model.cards.MailCard;
import model.cards.PayDayCards;

@SuppressWarnings("serial")
public class PayDayCards_Graphics extends JFrame {
	/*
	 * static methods show are used along with paydaycards, the rest are just
	 * for testing by creating new PayDayCards_Graphics object or by running
	 * main().
	 */

	ClassLoader cldr;
	PayDayCards pdc = new PayDayCards(null);

	PayDayCards_Graphics() {
		javax.swing.UIManager.put("OptionPane.messageFont", new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		javax.swing.UIManager.put("OptionPane.buttonFont", new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		cldr = this.getClass().getClassLoader();
		this.setResizable(false);
		this.setTitle("Cards");
		this.setPreferredSize(new Dimension(405, 200));

		JLayeredPane basic_panel = new JLayeredPane();
		basic_panel.setSize(405, 200);
		this.add(basic_panel);
		
		// set Mail Button
		JButton mailButton = new JButton("Get Mail Card");
		mailButton.setName("Mail");
		mailButton.setSize(200, 200);
		mailButton.addActionListener(new CardListener());

		// set Deal Button
		JButton dealButton = new JButton("Get Deal Card");
		dealButton.setName("Deal");
		dealButton.setSize(200, 200);
		dealButton.setBounds(200, 0, 200, 200);
		dealButton.addActionListener(new CardListener());
		
		basic_panel.add(mailButton);
		basic_panel.add(dealButton);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basic_panel,
				GroupLayout.PREFERRED_SIZE, 910, GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basic_panel,
				GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE));
		pack();
		basic_panel.repaint();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main() {
		// run for card graphics test
		new PayDayCards_Graphics();
	}

	void drawCard(String s) {
		if (s.equals("Mail")) {
			// draw card
			showMailCard(pdc.getMailDeck().draw(), this);
		} else if (s.equals("Deal")) {
			// draw card
			showDealCard(pdc.getDealDeck().draw(), this, false);
		}
	}

	/**
	 * Shows the MailCard.
	 * @param card MailCard to show
	 * @param o Parent Component
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static void showMailCard(MailCard card, Component o) {
		Object[] options = { card.option0 };

		Image image = card.image;
		image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);

		System.out.println("Type: " + card.type + "\nTypeEn: " + card.typeEn + "\nMessage: " + card.message
				+ "\nChoice: " + card.option0 + "\nEuro: " + card.amount);

		JOptionPane p = new JOptionPane();
		p.showOptionDialog(o, card.message, card.type, JOptionPane.OK_OPTION, 0, new ImageIcon(image), options,
				options[0]);
	}

	/**
	 * Shows the DealCard and returns choise.
	 * @param card DealCard to show
	 * @param o Parent Component
	 * @param noBuyChoice False for choise option, True to always get it
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int showDealCard(DealCard card, Component o, boolean noBuyChoice) {
		Image image = card.image;
		image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);

		System.out.println("Type: " + card.type + "\nTypeEn: " + card.typeEn + "\nMessage: " + card.message + "\nCost:"
				+ card.cost + "\nValue:" + card.value + "\nChoice1: " + card.option0 + "\nChoice2: " + card.option1);

		JOptionPane p = new JOptionPane();
		if (noBuyChoice) {
			Object[] options = { "OK" };
			p.showOptionDialog(o, card.message + "\nTimh agoras: " + card.cost + "\nTimh pwlhshs: " + card.value + "\n",
					card.type, JOptionPane.OK_OPTION, 0, new ImageIcon(image), options, options[0]);
			return 0;

		} else {
			Object[] options = { card.option0, card.option1 };
			int n = p.showOptionDialog(o,
					card.message + "\nTimh agoras: " + card.cost + "\nTimh pwlhshs: " + card.value + "\n", card.type,
					JOptionPane.OK_OPTION, 0, new ImageIcon(image), options, options[0]);
			return n;
		}

	}

	private class CardListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			drawCard(button.getName());
		}

	}

}
