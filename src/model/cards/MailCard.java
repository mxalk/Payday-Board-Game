package model.cards;

public class MailCard extends Card {

	public int amount;
	public String option0;

	MailCard(String[] card) {
		super(card);
		option0 = card[3];
		this.amount = Integer.parseInt(card[4]);
	}

}
