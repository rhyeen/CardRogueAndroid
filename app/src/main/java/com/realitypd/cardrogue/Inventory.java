package com.realitypd.cardrogue;

import java.util.ArrayList;

/**
 * Created by Ryan Saunders on 11/3/2014.
 */
public class Inventory
{
	private ArrayList<Card> _cardsInHand;
	private int _capacity;

	public Inventory(int capacity)
	{
		_cardsInHand = new ArrayList<Card>();
		_capacity = capacity;
	}

	/**
	 *
	 * @param item
	 * @return returns true if card was discarded
	 */
	public boolean discardItem(Item item, boolean canRemoveCursed)
	{
		// TODO: can remove cursed card?
		return _cardsInHand.remove(item);
	}

	/**
	 *
	 * @param item
	 * @return returns null if card was not in inventory to use
	 */
	public Item useItem(Item item)
	{
		if(discardItem(item, false))
			return item;
		return null;
	}

	/**
	 * Adds the card to the inventory as long as it is an Item and Inventory isn't at capacity
	 * @param item
	 * @return returns true if added, false otherwise
	 */
	public boolean addItem(Item item)
	{
		// if capacity < 0, then capacity is unlimited
		if(_cardsInHand.size() >= _capacity && _capacity >= 0)
			return false;
		_cardsInHand.add(item);
		return true;
	}

	public Card[] getInventoryCards()
	{
		return _cardsInHand.toArray(new Card[_cardsInHand.size()]);
	}

	public int getCapacity()
	{
		return _capacity;
	}

	public Item getItem(int index)
	{
		if(index > _cardsInHand.size())
			return null;
		return (Item) _cardsInHand.get(index);
	}

	public int getNumberOfItems()
	{
		return _cardsInHand.size();
	}
}
