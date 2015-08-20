package com.realitypd.cardrogue;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class RootActivity extends Activity
{
	TitleScreenFragment _titleScreenFragment = null;
	GameFragment _gameFragment = null;
	InventoryFragment _inventoryFragment = null;
	Shop _currentShop = null;
	Player _currentPlayer = null;
	int _currentScreen = -1;
	Game _currentGame = null;
	boolean _isNewGameSignal = false;
	boolean _previouslyPlayed = false;

	int INVENTORY_BUTTON = 1;
	int SHOP_BUTTON = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		FrameLayout screenLayout = new FrameLayout(this);
		screenLayout.setId(10);

		LinearLayout.LayoutParams screenParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);

		_titleScreenFragment = new TitleScreenFragment();
		final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(10, _titleScreenFragment);

		_gameFragment = new GameFragment();
		_inventoryFragment = new InventoryFragment();
//		fragmentTransaction.replace(10, _gameFragment);

		fragmentTransaction.commit();

		setContentView(screenLayout);

		_titleScreenFragment.setOnGameModeSelectedListener(new TitleScreenFragment.OnGameModeSelectedListener()
		{
			@Override
			public void onGameModeSelected(TitleScreenFragment titleScreenFragment, boolean newGameSelected)
			{
				// if someone attempts to continue a game that they have never started, don't let them
				if (!newGameSelected && !_previouslyPlayed && _currentGame == null)
					return;
				if(newGameSelected)
					_isNewGameSignal = true;

				FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
				fragmentTransaction1.replace(10, _gameFragment);
				fragmentTransaction1.addToBackStack(null);
				fragmentTransaction1.commit();
			}
		});

		_gameFragment.setOnButtonHitSelectedListener(new GameFragment.OnButtonHitSelectedListener()
		{
			@Override
			public void onButtonHitSelected(GameFragment gameFragment, int buttonNumber, Shop shop)
			{
				_currentScreen = buttonNumber;
				if (buttonNumber == INVENTORY_BUTTON || buttonNumber == SHOP_BUTTON)
				{
					if(buttonNumber == SHOP_BUTTON)
						_currentShop = shop;
					_currentPlayer = gameFragment.getCurrentPlayer();

					FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
					fragmentTransaction1.replace(10, _inventoryFragment);
					fragmentTransaction1.addToBackStack(null);
					fragmentTransaction1.commit();
				}
			}
		});

		_inventoryFragment.setRequesterListener(new InventoryFragment.OnRequesterListener()
		{
			@Override
			public void onRequester(InventoryFragment inventoryFragment)
			{
				if(_currentScreen == SHOP_BUTTON || _currentScreen == INVENTORY_BUTTON)
				{
					if(_currentScreen == SHOP_BUTTON)
						_inventoryFragment.setInShop(true, _currentShop);
					_inventoryFragment.setPlayer(_currentPlayer);
				}
			}
		});

		_inventoryFragment.setItemUsedListener(new InventoryFragment.OnItemUsedListener()
		{
			@Override
			public void onItemUsed(InventoryFragment inventoryFragment, Item item, boolean refreshScreen)
			{
				FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
				fragmentTransaction1.replace(10, _gameFragment);
				fragmentTransaction1.commit();

				// this is pure evil.  look at _inventoryFragment.setToParentListener for info
				// on how to possibly resolve someday...
				if(refreshScreen)
				{
					fragmentTransaction1 = getFragmentManager().beginTransaction();
					fragmentTransaction1.replace(10, _inventoryFragment);
					fragmentTransaction1.commit();
				}
			}
		});

		_gameFragment.setOnStartListener(new GameFragment.OnStartListener()
		{
			@Override
			public void onStart(GameFragment gameFragment, Game game)
			{
				// allow players to push the continue button now
				_previouslyPlayed = true;
				// only save the game if we started a new game, otherwise, the gamefragment has
				// simply been replaced and has been recreated
				if(_isNewGameSignal)
				{
					_isNewGameSignal = false;
					_currentGame = game;
				}
				// if it isn't a new game, we need to reload the game to the gamefragment
				else
				{
					_gameFragment.setGame(_currentGame);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

//		if(_currentGame == null) return;
		// if current game has state, there is no need to reload that state
		if(_currentGame == null)
		{
			File file = new File(getFilesDir(), "CardRogue.txt");

			// if we don't have a file, just exit
			if(file == null)
				return;

			FileReader fileReader = null;

			RuntimeTypeAdapterFactory<Card> typeAdapterFactory = RuntimeTypeAdapterFactory.of(Card.class, "type")
					.registerSubtype(Enemy.class)
					.registerSubtype(Equipment.class)
					.registerSubtype(Item.class)
					.registerSubtype(Shop.class)
					.registerSubtype(Tile.class);

			Gson gson = new GsonBuilder().registerTypeAdapter(Card.class, typeAdapterFactory).create();

			//Gson gson = new GsonBuilder().registerTypeAdapterFactory(Card.class, new CardInstanceCreator()).create();
			String jsonObjects = null;

			try
			{
				fileReader = new FileReader(file);
				//Type game = new TypeToken<Game>().getType();

				BufferedReader bufferedReader = new BufferedReader(fileReader);

				jsonObjects = bufferedReader.readLine();
				_currentGame = gson.fromJson(jsonObjects, Game.class);

				// at this point, all cards will be of CardObject type, set to their real types
				//_currentGame.typeSetCards();

				bufferedReader.close();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if(_gameFragment == null)
			return;

		_currentGame = _gameFragment.getGame();

		if(_currentGame == null)
			return;

		StringBuilder stringBuilder = new StringBuilder();

		//Gson gson = new Gson();
		Gson gson = new Gson();

		stringBuilder.append(gson.toJson(_currentGame)+"\n");
		String jsonObjects = stringBuilder.toString();

		File file = new File(getFilesDir(), "CardRogue.txt");

		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(jsonObjects);
			bufferedWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


}