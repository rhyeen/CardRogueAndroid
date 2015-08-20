package com.realitypd.cardrogue;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public class EffectLibrary
{
	private Hashtable<String, Effect> _effects;

	// region singleton
	static EffectLibrary _instance = null;
	static EffectLibrary getInstance() {
		// TODO: thread safe?
		if (_instance == null)
			_instance = new EffectLibrary();
		return _instance;
	}
	private EffectLibrary()
	{
		_effects = new Hashtable<String, Effect>();
		loadLibrary();
	}
	// endregion

	public Effect getEffect(String effectKey)
	{
		return _effects.get(effectKey);
	}

	/**
	 * Returns false if key already exists, and does not add the key/value to the effect library.
	 * @param effectKey
	 * @param effect
	 * @return
	 */
	public boolean addEffect(String effectKey, Effect effect)
	{
		if(_effects.containsKey(effectKey))
			return false;
		_effects.put(effectKey, effect);
		return true;
	}

	public Effect[] getEffects(String[] _effectKeys)
	{
		if(_effectKeys == null)
			return null;
		ArrayList<Effect> effects = new ArrayList<Effect>();
		for(String effectKey : _effectKeys)
		{
			if(_effects.containsKey(effectKey))
				effects.add(_effects.get(effectKey));
		}
		return (Effect[]) effects.toArray();
	}

	private void loadLibrary()
	{
		// TODO: load all the effects here to hash table
	}
}
