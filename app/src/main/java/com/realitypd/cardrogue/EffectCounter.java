package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/4/2014.
 */
public class EffectCounter
{
	public Effect effect;
	public int counter;

	public EffectCounter(Effect e)
	{
		counter = effect.turnsEffected;
	}

	public void resetCounter()
	{
		counter = effect.turnsEffected;
	}
}
