package com.realitypd.cardrogue;

import java.util.ArrayList;

/**
 * This is for Gson to create a no-args of the interface, Card
 * Created by Ryan Saunders on 12/12/2014.
 */
public class CardObject implements Card
{
	String _type;
	int _attackAmount, _maxHealth;
	int _currentHealth;
	int _victoryPoints;
	String _equipmentSlot;
	int _imageId;
	String _name;
	Effect[] _effects;
	boolean _isThrowable;
	boolean _isSellable;
	boolean _isTradeable;
	int _goldAmount;
	boolean _isCursed;
	String _description;
	ArrayList<Item> _items;
	int _rotation = 0;
	boolean _isRotatable;
	boolean[] _paths;

	public CardObject()
	{
		_type = _equipmentSlot = _name = _description = "";
		_attackAmount = _maxHealth = _currentHealth = _victoryPoints = _imageId = _goldAmount = _rotation = 0;
		_effects = null;
		_items = null;
		_isCursed = _isRotatable = _isSellable = _isThrowable = _isTradeable = false;
		_paths = null;
	}
	@Override
	public int getImageId()
	{
		return _imageId;
	}

	@Override
	public String getName()
	{
		return _name;
	}

//	@Override
//	public String getType()
//	{
//		return _type;
//	}

	@Override
	public Effect[] getEffects()
	{
		return _effects;
	}
}
