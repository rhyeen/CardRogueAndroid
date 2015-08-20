package com.realitypd.cardrogue;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by Ryan Saunders on 11/2/2014.
 *
 * A tile is a type of card that a player can move onto.
 * Tiles are also rotatable, depending on the layout of the board.
 */
public class Tile implements Card
{
	private String _type;
	private int _imageId;
	private String _name;
	private Effect[] _effects;
	private int _rotation = 0;
	private boolean _isRotatable;
	/**
	 * [0] left [1] top [2] right [3] bottom
	 */
	private boolean[] _paths;


	/**
	 * For Gson
	 * @param cardObject
	 */
	public Tile(CardObject cardObject)
	{
		_type = cardObject._type;
		_imageId = cardObject._imageId;
		_name = cardObject._name;
		_effects = cardObject._effects;
		_rotation = cardObject._rotation;
		_isRotatable = cardObject._isRotatable;
		_paths = cardObject._paths;
	}

	public Tile()
	{
		_type = "Tile";
		_imageId = 0;
		_name = "";
		_paths = new boolean[] {false, false, false, false};
		_isRotatable = false;
		_effects = null;
	}

	public Tile(int imageId, String name, boolean[] paths, String[] effectKeys, boolean isRotatable)
	{
		_type = "Tile";
		_imageId = imageId;
		_name = name;
		_paths = paths;
		_isRotatable = isRotatable;
		_effects = EffectLibrary.getInstance().getEffects(effectKeys);
	}

	/**
	 * @return [0] left [1] top [2] right [3] bottom
	 */
	public boolean[] getPaths()
	{
		return _paths;
	}

	public int getRotation() { return _rotation;}

	/**
	 * Outdated, rotation is done by different tiles with "left, top, bottom, right" after name
	 * remove later
	 * @param rotation must be between 0 and 360, and divisible by 90
	 */
	public void setRotation(int rotation)
	{
		if(rotation >= 0 && rotation <= 360 && rotation % 90 == 0)
			_rotation = rotation;
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

	@Override
	public Effect[] getEffects()
	{
		return _effects;
	}

	public boolean isRotatable()
	{
		return _isRotatable;
	}

}
