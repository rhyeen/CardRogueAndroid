package com.realitypd.cardrogue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public class Decks
{
	final static int EXPLORATION_DECK_SIZE = 150;
	final static int PATHING_DECK_SIZE = 200;
	final static int SHOP_DECK_SIZE = 22;
	private Card[] _explorationDeck;
	private Card[] _pathingDeck;
	private Card[] _shopDeck;
	private int _explorationDeckCount = EXPLORATION_DECK_SIZE;
	private int _pathingDeckCount = PATHING_DECK_SIZE;
	private int _shopDeckCount = SHOP_DECK_SIZE;


	/* Each game should have unique decks
	// region singleton
	static Decks _instance = null;
	static Decks getInstance() {
		// TODO: thread safe?
		if (_instance == null)
			_instance = new Decks();
		return _instance;
	}
	private Decks()
	{
		_explorationDeck = new Card[EXPLORATION_DECK_SIZE];
		_pathingDeck = new Card[PATHING_DECK_SIZE];
		_shopDeck = new Card[SHOP_DECK_SIZE];

		// build the decks
		buildExplorationDeck();
		buildPathingDeck();
		buildShopDeck();

		// shuffle the decks
		shuffleExplorationDeck();
		shufflePathingDeck();
	}
	// endregion */
	public Decks()
	{
		_explorationDeck = new Card[EXPLORATION_DECK_SIZE];
		_pathingDeck = new Card[PATHING_DECK_SIZE];
		_shopDeck = new Card[SHOP_DECK_SIZE];

		// build the decks
		buildExplorationDeck();
		buildPathingDeck();
		buildShopDeck();

		// shuffle the decks
		shuffleExplorationDeck();
		shufflePathingDeck();
	}

	private void buildExplorationDeck()
	{
		int index = 0;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("path 4");

		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("trap spike");

		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion green");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion yellow");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion blue");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion red");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion orange");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion pink");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion green");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion yellow");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion blue");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion red");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion orange");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion pink");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion green");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion yellow");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion blue");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion red");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion orange");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("potion pink");

		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat5");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat6");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat7");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat8");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat9");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("cave bat10");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold5");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("kobold6");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin4");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin5");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("goblin6");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("vampire bat1");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("vampire bat2");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("vampire bat3");
		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("vampire bat4");


		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("the basilisk");

		index++;
		_explorationDeck[index] = CardLibrary.getInstance().getCard("shop1");


		_explorationDeckCount = index + 1;
	}

	private void buildPathingDeck()
	{
		int index = 0;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 1");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 2v2");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 3");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");
		index++;
		_pathingDeck[index] = CardLibrary.getInstance().getCard("path 4");

		_pathingDeckCount = index + 1;

		// make sure to update the count variable
		// TODO:
	}

	private void buildShopDeck()
	{
		// make sure to update the count variable
		// TODO:
	}

	public void shuffleExplorationDeck()
	{
		_explorationDeck = shuffleDeck(_explorationDeck, _explorationDeckCount);
	}

	public void shufflePathingDeck()
	{
		_pathingDeck = shuffleDeck(_pathingDeck, _pathingDeckCount);
	}

	private Card[] shuffleDeck(Card[] deck, int deckCount)
	{
		if(deck == null)
			return deck;
		if(deck.length <= 1)
			return deck;

		Random rand = new Random();
		int temp;
		Card cardSwap;
		for(int i = deckCount - 1; i > 0; i--)
		{
			temp = rand.nextInt(i + 1);
			cardSwap = deck[temp];
			deck[temp] = deck[i];
			deck[i] = cardSwap;
		}
		return deck;
	}

	public Card removeCardFromExplorationDeck()
	{
		if(_explorationDeckCount <= 0)
			return null;

		Card card = _explorationDeck[_explorationDeckCount - 1];
		_explorationDeck[_explorationDeckCount - 1] = null;
		_explorationDeckCount--;
		return card;
	}

	public Card removeCardFromPathingDeck()
	{
		if(_pathingDeckCount <= 0)
			return null;

		Card card = _pathingDeck[_pathingDeckCount - 1];
		_pathingDeck[_pathingDeckCount - 1] = null;
		_pathingDeckCount--;
		return card;
	}

	/**
	 * Remove specified card from the shop deck
	 * returns false if not in the deck
	 * @param card
	 * @return
	 */
	public boolean removeCardFromShopDeck(Card card)
	{
		for(int i = 0; i < _shopDeckCount; i++)
		{
			if(_shopDeck[i].getName() == card.getName())
			{
				// move the shop cards down the array
				if(i < _shopDeckCount - 1)
				{
					for (; i < _shopDeckCount - 1; i++)
						_shopDeck[i] = _shopDeck[i+1];
				}
				_shopDeckCount--;
				return true;
			}
		}
		return false;
	}

	public Card[] getExplorationDeck()
	{
		return _explorationDeck;
	}

	public void setExplorationDeck(Card[] explorationDeck)
	{
		_explorationDeck = explorationDeck;
	}

	public Card[] getPathingDeck()
	{
		return _pathingDeck;
	}

	public void setPathingDeck(Card[] pathingDeck)
	{
		_pathingDeck = pathingDeck;
	}

	public Card[] getShopDeck()
	{
		// need to make a copy of the shopDeck, as to preserve our copy in case it is modified
		Card[] temp = new Card[_shopDeck.length];
		for(int i=0; i < _shopDeck.length; i++)
			temp[i] = _shopDeck[i];

		return temp;
	}
}
