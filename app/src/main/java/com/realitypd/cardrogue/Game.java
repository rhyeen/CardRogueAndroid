package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/4/2014.
 */
public class Game
{
	private Player[] _players;
	private Board _board;
	private Decks _decks;
	private int _turn;
	private boolean _gameOver = false;
	private boolean _beatBoss = false;
	private String _currentPlayerText = "";


	/**
	 * No-args constructor
	 */
	public Game()
	{
		Player onePlayer = new Player(10, null);
		Player[] players = new Player[] {onePlayer};

		// we want to have players be a parameter since players can start with special abilities
		_players = players;
		_decks = new Decks();
		_board = new Board(10, 10, _decks);

		// randomize starting positions on board
		for(Player player : _players)
			_board.placePlayerRandom(player);

		// TODO: randomize the turn?
		_turn = 0;
	}

	/**
	 *
	 * @param players
	 * @param boardWidth
	 * @param boardHeight
	 */
	public Game(Player[] players, int boardWidth, int boardHeight)
	{
		// we want to have players be a parameter since players can start with special abilities
		_players = players;
		_decks = new Decks();
		_board = new Board(boardWidth, boardHeight, _decks);

		// randomize starting positions on board
		for(Player player : _players)
			_board.placePlayerRandom(player);

		// TODO: randomize the turn?
		_turn = 0;
	}

	/**
	 * Moves turn to the next player
	 * @return
	 */
	public int advanceTurn()
	{
		if(_turn < _players.length - 1)
			_turn++;
		else
			_turn = 0;
		return _turn;
	}

	/**
	 * Gets the player number whose turn it currently is.
	 * @return
	 */
	public int getTurn() { return _turn; }

	public Player[] getPlayers()
	{
		return _players;
	}

	public Board getBoard()
	{
		return _board;
	}

	public Decks getDecks()
	{
		return _decks;
	}

	public void gameOver() { _gameOver = true; }
	public boolean isGameOver() { return _gameOver; }

	public String getCurrentPlayerText()
	{
		return _currentPlayerText;
	}

	public void setCurrentPlayerText(String currentPlayerText)
	{
		_currentPlayerText = currentPlayerText;
	}

	public void beatBoss() { _beatBoss = true; }
	public boolean isBossBeaten() { return _beatBoss; }

	/**
	 * When onResume is called, all Cards are set to CardObject, set to their original type
	 */
//	public void typeSetCards()
//	{
//		for(Player player : _players)
//		{
//			Inventory inventory = new Inventory(player.getInventory().getCapacity());
//
//			for(Card card : player.getInventory().getInventoryCards())
//			{
//				if(card.getType().equals("Enemy"))
//					inventory.addItem(new Enemy((CardObject) card));
//				else if(card.getType().equals("Equipment"))
//					inventory.addItem(new Equipment((CardObject) card));
//				else if(card.getType().equals("Item"))
//					inventory.addItem(new Item((CardObject) card));
//			}
//
//			player.setInventory(inventory);
//		}
//
//		Card[] badEDeck = _decks.getExplorationDeck();
//		Card[] badPDeck = _decks.getPathingDeck();
//
//		Card[] eDeck = new Card[badEDeck.length];
//		Card[] pDeck = new Card[badPDeck.length];
//
//		String cardType;
//
//		for(int i=0; i < badEDeck.length; i++)
//		{
//			if(badEDeck[i] != null)
//			{
//				cardType = badEDeck[i].getType();
//				if(cardType.equals("Enemy"))
//					eDeck[i] = new Enemy((CardObject) badEDeck[i]);
//				else if(cardType.equals("Item"))
//					eDeck[i] = new Item((CardObject) badEDeck[i]);
//				else if(cardType.equals("Tile"))
//					eDeck[i] = new Tile((CardObject) badEDeck[i]);
//				else if(cardType.equals("Shop"))
//					eDeck[i] = new Shop((CardObject) badEDeck[i]);
//			}
//		}
//
//		for(int i=0; i < badPDeck.length; i++)
//		{
//			if(badPDeck[i] != null)
//			{
//				cardType = badPDeck[i].getType();
//				if(cardType.equals("Enemy"))
//					pDeck[i] = new Enemy((CardObject) badPDeck[i]);
//				else if(cardType.equals("Item"))
//					pDeck[i] = new Item((CardObject) badPDeck[i]);
//				else if(cardType.equals("Tile"))
//					pDeck[i] = new Tile((CardObject) badPDeck[i]);
//				else if(cardType.equals("Shop"))
//					pDeck[i] = new Shop((CardObject) badPDeck[i]);
//			}
//		}
//
//		_decks.setExplorationDeck(eDeck);
//		_decks.setPathingDeck(pDeck);
//
//		int boardX = _board.getBoardWidth();
//		int boardY = _board.getBoardHeight();
//
//		Card card;
//
//		for(int x=0; x < boardX; x++)
//		{
//			for(int y=0; y < boardY; y++)
//			{
//				card = _board.getCard(x, y);
//				if(card != null)
//				{
//					cardType = card.getType();
//					if(cardType.equals("Enemy"))
//						_board.setBoardIndex(x, y, new Enemy((CardObject) card));
//					else if(cardType.equals("Item"))
//						_board.setBoardIndex(x, y, new Item((CardObject) card));
//					else if(cardType.equals("Tile"))
//						_board.setBoardIndex(x, y, new Tile((CardObject) card));
//					else if(cardType.equals("Shop"))
//						_board.setBoardIndex(x, y, new Shop((CardObject) card));
//				}
//			}
//		}
//	}
}
