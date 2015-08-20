package com.realitypd.cardrogue;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by Ryan Saunders on 12/12/2014.
 */
public class CardInstanceCreator implements InstanceCreator<Card>
{
	@Override
	public Card createInstance(Type type)
	{
		//return new CardObject();
		if(type instanceof Enemy)
			return new Enemy();
		else if(type instanceof Item)
			return new Item();
		else if(type instanceof Shop)
			return new Shop();
		else if(type instanceof Tile)
			return new Tile();
		else
			return new Equipment();
	}
}
