package model.cards;

public class DealCard extends Card {

	public int cost, value;
	public String option0, option1;

	DealCard(String[] card) {
		super(card);
		cost = Integer.parseInt(card[3]);
		value = Integer.parseInt(card[4]);
		option0 = card[6];
		option1 = card[7];
	}

}
