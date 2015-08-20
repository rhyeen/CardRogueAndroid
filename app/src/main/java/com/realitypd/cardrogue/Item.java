package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/3/2014.
 */
public class Item implements Card
{
	private String _type;
	private int _imageId;
	private String _name;
	private Effect[] _effects;
	private boolean _isThrowable;
	private boolean _isSellable;
	private boolean _isTradeable;
	private int _goldAmount;
	private boolean _isCursed;
	private String _description;


	/**
	 * For Gson
	 * @param cardObject
	 */
	public Item(CardObject cardObject)
	{
		_type = cardObject._type;
		_imageId = cardObject._imageId;
		_name = cardObject._name;
		_effects = cardObject._effects;
		_isThrowable = cardObject._isThrowable;
		_isSellable = cardObject._isSellable;
		_isTradeable = cardObject._isTradeable;
		_goldAmount = cardObject._goldAmount;
		_isCursed = cardObject._isCursed;
		_description = cardObject._description;
	}

	/**
	 * No-args constructor
	 */
	public Item()
	{
		_type = "Item";
		_imageId = 0;
		_name = "";
		_goldAmount = 0;
		_isThrowable = false;
		_isSellable = false;
		_isTradeable = false;
		_effects = null;
		_isCursed = false;
		_description = "";
	}

	public Item(int imageId, String name, String description, String[] effectKeys, int goldAmount, boolean isThrowable, boolean isSellable, boolean isTradeable, boolean isCursed)
	{
		_type = "Item";
		_imageId = imageId;
		_name = name;
		_goldAmount = goldAmount;
		_isThrowable = isThrowable;
		_isSellable = isSellable;
		_isTradeable = isTradeable;
		_effects = EffectLibrary.getInstance().getEffects(effectKeys);
		_isCursed = isCursed;
		_description = description;
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

	public void setType(String type) { _type = type; }

	public String getDescription() { return _description; }

	@Override
	public Effect[] getEffects()
	{
		return _effects;
	}

	public boolean isThrowable()
	{
		return _isThrowable;
	}

	public boolean isSellable()
	{
		return _isSellable;
	}

	public boolean isTradeable()
	{
		return _isTradeable;
	}

	public int getGoldAmount()
	{
		return _goldAmount;
	}

	public int getGoldAmountAtShop() { return 2 * _goldAmount; }

	public boolean isCursed()
	{
		return _isCursed;
	}
}
