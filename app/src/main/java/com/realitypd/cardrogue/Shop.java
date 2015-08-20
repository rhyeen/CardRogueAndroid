package com.realitypd.cardrogue;

import java.util.ArrayList;

/**
 * Created by Ryan Saunders on 12/8/2014.
 */
public class Shop extends Tile
{
	private ArrayList<Item> _items;


	/**
	 * For Gson
	 * @param cardObject
	 */
	public Shop(CardObject cardObject)
	{
		super(cardObject._imageId, cardObject._name, cardObject._paths, null, cardObject._isRotatable);
		_items = cardObject._items;
	}

	/**
	 * No-args constructor
	 */
	public Shop()
	{
		super(R.drawable.shop, "shop", new boolean[]{true, true, true, true}, null, false);
		setType("Shop");
		_items = null;
	}

	public Shop(int imageId, String name, ArrayList<Item> items)
	{
		super(imageId, name, new boolean[]{true, true, true, true}, null, false);
		setType("Shop");
		_items = items;
	}

	public ArrayList<Item> getItems() { return _items; }

	public Item getItem(int index)
	{
		if(_items == null)
			return null;
		if(index < 0 || index >= _items.size())
			return null;
		return _items.get(index);
	}

	public int getItemCount()
	{
		if(_items == null)
			return 0;
		return _items.size();
	}
}
