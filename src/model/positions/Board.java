package model.positions;

import controller.GameData;
import main.Utilities;
import main.Utilities.RandomNumbers;

public class Board {

	public Position[] pos = new Position[32];

	public Board(GameData data) {

		Utilities.RandomNumbers rn = new RandomNumbers(1, 30);
		int j;

		pos[0] = new Position("start");
		pos[31] = new Position("pay");

		for (int i = 0; i < 4; i++) {
			j = rn.getNumber();
			pos[j] = new Position("mc1");
			j = rn.getNumber();
			pos[j] = new Position("mc2");
		}

		for (int i = 0; i < 5; i++) {
			j = rn.getNumber();
			pos[j] = new Position("deal");
		}

		for (int i = 0; i < 2; i++) {
			j = rn.getNumber();
			pos[j] = new Position("sweep");
		}

		for (int i = 0; i < 3; i++) {
			j = rn.getNumber();
			pos[j] = new Position("lottery");
		}

		for (int i = 0; i < 2; i++) {
			j = rn.getNumber();
			pos[j] = new Position("radio");
		}

		for (int i = 0; i < 6; i++) {
			j = rn.getNumber();
			pos[j] = new Position("buyer");
		}

		for (int i = 0; i < 2; i++) {
			j = rn.getNumber();
			pos[j] = new Position("casino");
		}

		for (int i = 0; i < 2; i++) {
			j = rn.getNumber();
			pos[j] = new Position("yard");
		}
	}

}
