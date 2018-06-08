package controller;

import java.util.ArrayList;
import java.util.Queue;

import model.Player;
import model.cards.DealCard;
import model.cards.Deck;
import model.cards.MailCard;
import model.positions.Board;

public class GameData {

	public Controller c;
	public GraphicsData gd;

	public ArrayList<Player> players;
	public Queue<Player> playQueue;

	public Deck<MailCard> mailDeck;
	public Deck<DealCard> dealDeck;

	public Board board;

	public int jackpot, monthsToPlay;
	public Player playsNow;
	
}
