package com.realitypd.cardrogue;

import java.util.Dictionary;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public interface Card {
	public int getImageId();

	/**
	 * Invariant, must be unique
	 * @return
	 */
	public String getName();

	/**
	 * For Gson, since it doesn't like interfaces
	 * @return
	 */
	//public String getType();

	/**
	 *
	 * @return
	 */
	public Effect[] getEffects();
}
