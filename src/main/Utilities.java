package main;

import java.util.ArrayList;
import java.util.Random;

import controller.GameData;
import model.Player;


public class Utilities {

	static Random r;
	static {
		r = new Random();
	}

	public static class RandomNumbers {

		ArrayList<Integer> ints = new ArrayList<>();
		Random r = new Random();

		public RandomNumbers(int start, int end) {
			for (int i = start; i < end + 1; i++) {
				ints.add(i);
			}
		}

		/**
		 * Generates random numbers between a,b inclusive, one time each. 
		 * @return One time each integer [a, b]. 
		 */
		public int getNumber() {
			int n = r.nextInt(ints.size());
			return ints.remove(n);
		}

	}

	/**
	 * Returns a random Integer between a,b inclusive. 
	 * @param a Lower bound, inclusive. 
	 * @param b Upper bound, inclusive. 
	 * @return Random integer [a, b]. 
	 */
	public static int random(int a, int b) {
		return r.nextInt(b - a + 1) + a;
	}
	
	public static Player opponent(GameData data, Player p) {
		Player opp = data.players.get(0);
		if (opp == p)
			opp = data.players.get(1);
		return opp;
	}

}
