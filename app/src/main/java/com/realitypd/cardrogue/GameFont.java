package com.realitypd.cardrogue;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Singleton to set up Cocogoose font to use for the in game font.
 * Call GameFont.getInstance().getTypeface() to retrieve.
 * Created by Ryan Saunders on 11/25/2014.
 */
public class GameFont
{
	private static Context _context;
	private static Typeface _font;
	// region singleton
	static GameFont _instance = null;
	static GameFont getInstance(Context context)
	{
		// TODO: thread safe?
		if (_instance == null)
			_instance = new GameFont(context);
		return _instance;
	}

	private GameFont(Context context)
	{
		_font = Typeface.createFromAsset(context.getAssets(), "Cocogoose_trial.otf");
	}
	// endregion

	public Typeface getTypeface()
	{
		return _font;
	}
}
