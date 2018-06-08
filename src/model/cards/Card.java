package model.cards;

import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Card {

	public String type, message;
	public String typeEn;
	public Image image;

	Card(String[] card) {
		this.type = card[0];
		this.typeEn = card[1];
		this.message = card[2];
		image = new ImageIcon("input/images/" + card[5]).getImage();
	}

}
