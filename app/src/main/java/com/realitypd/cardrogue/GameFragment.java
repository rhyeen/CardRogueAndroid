package com.realitypd.cardrogue;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ryan Saunders on 11/5/2014.
 */
public class GameFragment extends Fragment
{
	int INVENTORY_BUTTON = 1;
	int SHOP_BUTTON = 2;

	String HEALTH_TEXT = "HEALTH: ";
	String GOLD_TEXT = "GOLD: ";
	String STARTING_TEXT = "You enter a catacomb, seeking the treasure of the basilisk.  Move around by touching the tiles above, but be warned: this catacomb may not contain the treasure you are seeking.";
	String TRAP_FOUND = "Ouch, you lost 2 health! You must have triggered a trap! If you move onto it, it will trigger again.";
	String TRAP_HIT = "You triggered a trap! It dealt 2 damage to you.";
	String ENEMY_FOUND_1 = "You encountered ";
	String ENEMY_FOUND_2 = ". Tap on the enemy's tile to attack it, or tap on an adjacent path to attempt to flee.";
	String INVALID_COMBAT = "Cannot do that. You are in combat.";
	String FLEE_FAIL = "You failed to flee.";
	String FLEE_SUCCESS = "You got away.  Move adjacent to the enemy to enter combat again.";
	String YOU_WIN = "You win!  Thanks for playing Card Rogue!";

	BoardLayout _boardLayout = null;

	Game _game = null;
	Player[] _players = null;
	Board _board = null;

	TextView _healthText;
	TextView _goldText;
	TextView _statusText;
	//Button _waitButton, _inspectButton, _interactButton, _mapButton, _inventoryButton, _equipmentButton;
	ImageView _shopButton, _inventoryButton;

	public interface OnStartListener {
		public void onStart(GameFragment gameFragment, Game game);
	}

	OnStartListener _onStartListener = null;

	public void setOnStartListener(OnStartListener onStartListener) {
		_onStartListener = onStartListener;
	}

	public interface OnButtonHitSelectedListener {
		public void onButtonHitSelected(GameFragment gameFragment, int buttonNumber, Shop shop);
	}

	OnButtonHitSelectedListener _onButtonHitSelectedListener = null;

	public void setOnButtonHitSelectedListener(OnButtonHitSelectedListener onButtonHitSelectedListener) {
		_onButtonHitSelectedListener = onButtonHitSelectedListener;
	}

	public Game getGame() { return _game; }

	public Player getCurrentPlayer()
	{
		return _players[_game.getTurn()];
	}

	/**
	 * Called when this screen is returned to.  This will reset the current state of the screen.
	 */
	public void setGame(Game game)
	{
		_game = game;
		_players = _game.getPlayers();
		_board = _game.getBoard();
		_shopButton.invalidate();
		Player player = _players[_game.getTurn()];
		updateTopBar(player);
		// if we continue a played game, there should be a shop button if on the shop
		if(_board.getCard(player.getxPosition(), player.getyPosition()).getName().equals("shop"))
			_shopButton.setImageResource(R.drawable.shop_button);
		if(_game.isGameOver())
		{
			if(_game.getCurrentPlayerText().contains(YOU_WIN))
			{
				setStatusText(YOU_WIN + " If you want to play again, start a new game.");
				giveBoardLayoutTiles();
			}
			else
				showDeathScreen(_game.getTurn(), "You cannot continue this game.");
		}
		else
		{
			_statusText.setText(_game.getCurrentPlayerText());
			giveBoardLayoutTiles();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: get game/player from activity or a gameListFragment instead
		_players = new Player[1];

		_players[0] = new Player(10, null);

		for(Player player : _players)
			player.setOnStatChangedListener(new Player.OnStatChangedListener()
			{
				@Override
				public void onStatChanged(Player player)
				{
					updateTopBar(player);
				}
			});

		_game = new Game(_players, 10, 10);
		_board = _game.getBoard();

		LinearLayout gameLayout = new LinearLayout(getActivity());
		gameLayout.setBackgroundColor(0xff1e0d17);
		gameLayout.setOrientation(LinearLayout.VERTICAL);

		//// Top bar
		//
		LinearLayout topBar = new LinearLayout(getActivity());
		topBar.setPadding(20, 10, 20, 40);
		LinearLayout.LayoutParams topBarParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		LinearLayout.LayoutParams topBarTextParams = new LinearLayout.LayoutParams(
				0,
				LinearLayout.LayoutParams.MATCH_PARENT,
				1
		);
		topBar.setOrientation(LinearLayout.HORIZONTAL);
		_healthText = new TextView(getActivity());
		_goldText = new TextView(getActivity());
		_healthText.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		_goldText.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		_healthText.setTextSize(24.0f);
		_goldText.setTextSize(24.0f);
		_healthText.setTextColor(0xffcb3d2d);
		_goldText.setTextColor(0xfff4ce43);
		// show stats for first player
		updateTopBar(_players[_game.getTurn()]);

		topBar.addView(_healthText, topBarTextParams);
		topBar.addView(_goldText, topBarTextParams);
		gameLayout.addView(topBar, topBarParams);

		//// Board
		//
		LinearLayout.LayoutParams boardLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
		);

		//
		LinearLayout.LayoutParams boardWrapperLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		// Wrap board layout in another layout so that there can be padding, otherwise
		// padding on board layout affects the tile distribution
		LinearLayout boardWrapper = new LinearLayout(getActivity());
		boardWrapper.setPadding(40, 0, 40, 0);

		_boardLayout = new BoardLayout(getActivity());
		_boardLayout.setPadding(0, 0, 0, 0);

		_boardLayout.setOnTileSelectedListener(new BoardLayout.OnTileSelectedListener()
		{
			@Override
			public void onTileSelected(BoardLayout boardLayout, int tileAdditionalX, int tileAdditionalY, int tileIndex)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;
				Player player = _players[_game.getTurn()];
				int tileX = player.getxPosition() + tileAdditionalX;
				int tileY = player.getyPosition() + tileAdditionalY;
				// if the player is in combat, he can only move (flee) or tap on the enemy (attack)
				if(player.isInCombat())
				{
					inCombatMove(player, tileX, tileY);
				}
				else
				{
					if(!flipCard(player, tileX, tileY, tileIndex))
						movePlayer(player, tileX, tileY);
				}
			}
		});

		boardWrapper.addView(_boardLayout, boardLayoutParams);
		gameLayout.addView(boardWrapper, boardWrapperLayoutParams);

		LinearLayout midBar = new LinearLayout(getActivity());
		LinearLayout.LayoutParams midBarParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				0,
				1
		);
		midBar.setOrientation(LinearLayout.HORIZONTAL);
		midBar.setPadding(80, 40, 80, 10);

		gameLayout.addView(midBar, midBarParams);

		_statusText = new TextView(getActivity());
		setStatusText(STARTING_TEXT);
		LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
		);

		midBar.addView(_statusText, statusParams);

		//// Bot bar
		//
		LinearLayout botBar = new LinearLayout(getActivity());
		LinearLayout.LayoutParams botBarParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				0,
				1
		);
		botBar.setOrientation(LinearLayout.HORIZONTAL);
		botBar.setPadding(20, 40, 20, 10);

		gameLayout.addView(botBar, botBarParams);

		LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
				0,
				LinearLayout.LayoutParams.MATCH_PARENT,
				1
		);

		/*
		_waitButton = new Button(getActivity());
		_waitButton.setBackgroundResource(R.drawable.wait_button);
		_waitButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;
			}
		});

		_inspectButton = new Button(getActivity());
		_inspectButton.setBackgroundResource(R.drawable.inspect_button);
		_inspectButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;
			}
		});

		_interactButton = new Button(getActivity());
		_interactButton.setBackgroundResource(R.drawable.pickup_button);
		_interactButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;
			}
		});

		botBar.addView(_waitButton, buttonLayoutParams);
		botBar.addView(_inspectButton, buttonLayoutParams);
		botBar.addView(_interactButton, buttonLayoutParams);

		_mapButton = new Button(getActivity());
		_mapButton.setBackgroundResource(R.drawable.map_button);
		_mapButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;
			}
		});*/

		_inventoryButton = new ImageView(getActivity());
		_inventoryButton.setImageResource(R.drawable.inventory_button);
		_inventoryButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;

				if (_onButtonHitSelectedListener != null)
					_onButtonHitSelectedListener.onButtonHitSelected(GameFragment.this, INVENTORY_BUTTON, null);
			}
		});

		_shopButton = new ImageView(getActivity());
		// only show the shop button if you are on the shop, which you aren't on the first tile
		// setting it to 0 made the inventoryButton centered
		_shopButton.setImageResource(R.drawable.blank);
		_shopButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// don't allow the player to touch anything if the game is over.
				if(_game.isGameOver())
					return;

				if (_onButtonHitSelectedListener != null)
				{
					Card card = _board.getCard(getCurrentPlayer().getxPosition(), getCurrentPlayer().getyPosition());
					if(card instanceof Shop)
						_onButtonHitSelectedListener.onButtonHitSelected(GameFragment.this, SHOP_BUTTON, (Shop) card);
				}
			}
		});

		//botBar.addView(_mapButton, buttonLayoutParams);
		botBar.addView(_inventoryButton, buttonLayoutParams);
		botBar.addView(_shopButton, buttonLayoutParams);

		giveBoardLayoutTiles();

		// send game to activity for saving
		if (_onStartListener != null)
			_onStartListener.onStart(GameFragment.this, _game);

		return gameLayout;
	}

	private void inCombatMove(Player player, int tileX, int tileY)
	{
		// get enemy cards that are in combat with the player
		Enemy[] enemies = _board.getEnemyCards(player);

		Card selectedCard = _board.getCard(tileX, tileY);
		// if player has selected enemy, attack it
		if(selectedCard instanceof Enemy)
		{
			int attackRoll = player.rollAttack();
			int damageDealt = ((Enemy) selectedCard).changeHealth((-1)*attackRoll);
			int enemyHealthLeft = ((Enemy) selectedCard).getCurrentHealth();

			// if you killed the enemy
			if(enemyHealthLeft <= 0)
			{
				// if this was the only enemy, leave combat
				if(enemies.length <= 1)
					player.setInCombat(false);

				// replace the enemy with a pathing tile
				_board.placePathingTile(player, tileX, tileY, 0);

				// only the boss will have no gold
				if(((Enemy) selectedCard).getGoldAmount() > 0)
					setStatusText("You dealt " + attackRoll + " and killed the enemy.  You picked up the loot.");
				else
				{
					_game.beatBoss();
					setStatusText("You dealt " + attackRoll + " and killed " + selectedCard.getName().toUpperCase() + ". It drops the treasure you've been seeking.  Quickly! Make toward the ladder to leave this place!");
				}
				player.getInventory().addItem((Enemy) selectedCard);
			}
			else
			{
				int damageDealtToPlayer = 0;
				for(Enemy enemy : enemies)
				{
					damageDealtToPlayer += enemy.getAttackAmount();
					if(enemy.getName().equals("vampire bat"))
						enemy.changeHealth(2);
				}

				player.changeCurrentHealth((-1)*damageDealtToPlayer);
				updateTopBar(player);

				// if the player died
				if(player.getCurrentHealth() <= 0)
				{
					player.setInCombat(false);
					showDeathScreen(_game.getTurn(), "You were killed in battle.");
					return;
				}
				else
				{
					setStatusText("You dealt " + attackRoll + " to the " + selectedCard.getName() + ", which has " + enemyHealthLeft + " health left.  You were dealt " + damageDealtToPlayer + " damage.");
				}
			}
		}
		// player is attempting to flee
		else
		{
			if (_board.validMove(player, tileX, tileY))
			{
				if(player.attemptFlee())
				{
					setStatusText(FLEE_SUCCESS);
					movePlayer(player, tileX, tileY);
				}
				else
				{
					int damageDealtToPlayer = 0;
					for(Enemy enemy : enemies)
					{
						damageDealtToPlayer += enemy.getAttackAmount();
						if(enemy.getName().equals("vampire bat"))
							enemy.changeHealth(2);
					}

					player.changeCurrentHealth((-1)*damageDealtToPlayer);
					updateTopBar(player);

					// if the player died
					if(player.getCurrentHealth() <= 0)
					{
						player.setInCombat(false);
						showDeathScreen(_game.getTurn(), "You failed to flee and were killed in battle.");
						return;
					}
					else
					{
						setStatusText(FLEE_FAIL + " You were dealt " + damageDealtToPlayer + " damage.");
					}
				}
			}
			else
				setStatusText(INVALID_COMBAT);
		}

		giveBoardLayoutTiles();
		return;
	}

	private void setStatusText(String text)
	{
		_statusText.setText(text);
		_game.setCurrentPlayerText(text);
	}

	private void showDeathScreen(int turn, String deathText)
	{
		// TODO:
		_statusText.setTextColor(0xffcb3d2d);
		_statusText.setText("" + deathText + " Touch the back button to return to the title screen to start a new game.");
		_game.gameOver();
		giveBoardLayoutTiles();
	}


	private boolean flipCard(Player player, int tileX, int tileY, int tileIndex)
	{
		// if it is an unexplored tile, flip it over
		Card cardFlippedOver = _board.flipUnexploredCard(player, tileX, tileY);
		if(cardFlippedOver != null)
		{
			String cardName = cardFlippedOver.getName();
			if(cardName.contains("potion"))
			{
				setStatusText("You found a potion! You can move onto a potion tile to pick it up.");
			}

			else if(cardName.contains("trap"))
			{
				setStatusText(TRAP_FOUND);

				if(cardName.equals("trap spike"))
				{
					player.changeCurrentHealth(-2);
					updateTopBar(player);

					// if the player died
					if(player.getCurrentHealth() <= 0)
					{
						showDeathScreen(_game.getTurn(), "You were killed by a trap.");
						return true;
					}
				}
			}
			else if(cardFlippedOver instanceof Enemy)
			{
				String name = cardName.toLowerCase();
				if(name.contains("the"))
					name = "" + name.toUpperCase();
				// starting letter a vowel
				else if(name.charAt(0) == 'a' || name.charAt(0) == 'e' || name.charAt(0) == 'i' ||
						name.charAt(0) == 'o' || name.charAt(0) == 'u')
					name = "an " + name;
				else
					name = "a " + name;

				setStatusText(ENEMY_FOUND_1 + name + ENEMY_FOUND_2);
				player.setInCombat(true);
			}
			else if(cardFlippedOver instanceof Shop)
			{
				setStatusText("You found a shop!  Move onto it to buy and sell items.");
			}

			giveBoardSingleTile(tileIndex, tileX, tileY);
			return true;
		}
		return false;
	}

	private boolean movePlayer(Player player, int tileX, int tileY)
	{
		// otherwise, if it is a valid move, update the board
		Card cardMovedOnto = _board.movePlayer(player, tileX, tileY);
		if(cardMovedOnto != null)
		{
			String cardName = cardMovedOnto.getName();
			if(cardName.contains("potion"))
			{
				_board.placePathingTile(player, tileX, tileY, 0);
				setStatusText("You put the potion in your bag.");
				player.getInventory().addItem((Item)cardMovedOnto);
			}
			else if(cardName.contains("trap"))
			{
				setStatusText(TRAP_HIT);

				if(cardName.equals("trap spike"))
				{
					player.changeCurrentHealth(-2);
					updateTopBar(player);

					// if the player died
					if(player.getCurrentHealth() <= 0)
					{
						showDeathScreen(_game.getTurn(), "You were killed in by a trap.");
						return true;
					}
				}
			}
			else if(cardName.equals("starting tile") && _game.isBossBeaten())
			{
				setStatusText(YOU_WIN);
				_game.gameOver();
			}

			// only show the shop button if you are on the shop
			if(cardName.equals("shop"))
				_shopButton.setImageResource(R.drawable.shop_button);
			else
				_shopButton.setImageResource(0);

			// check adjacent tiles to see if in combat
			Enemy[] enemyCards = _board.getEnemyCards(player);
			if(enemyCards != null)
			{
				String enemyText = "";
				for(Enemy enemy : enemyCards)
				{
					String name = enemy.getName();
					name = name.toLowerCase();
					// starting letter a vowel
					if(name.contains("the"))
						enemyText += "" + name.toUpperCase() + " and ";
					else if(name.charAt(0) == 'a' || name.charAt(0) == 'e' || name.charAt(0) == 'i' ||
							name.charAt(0) == 'o' || name.charAt(0) == 'u')
						enemyText += "an " + enemy.getName() + " and ";
					else
						enemyText += "a " + enemy.getName() + " and ";
				}
				// remove the last and
				enemyText = enemyText.substring(0, enemyText.length() - " and ".length());
				setStatusText(ENEMY_FOUND_1 + enemyText + ENEMY_FOUND_2);
				player.setInCombat(true);
			}

			giveBoardLayoutTiles();
			return true;
		}
		return false;
	}

	private void updateTopBar(Player player)
	{
		_healthText.setText(HEALTH_TEXT + player.getCurrentHealth());
		_goldText.setText(GOLD_TEXT + player.getTotalGold());
	}

	private void giveBoardLayoutTiles()
	{
		if(_game == null || _boardLayout == null)
			return;

		int xPos = _players[_game.getTurn()].getxPosition();
		int yPos = _players[_game.getTurn()].getyPosition();

		giveBoardSingleTile(0, xPos - 1, yPos - 1);
		giveBoardSingleTile(1, xPos, yPos - 1);
		giveBoardSingleTile(2, xPos + 1, yPos - 1);

		giveBoardSingleTile(3, xPos - 1, yPos);
		giveBoardSingleTile(4, xPos, yPos);
		giveBoardSingleTile(5, xPos + 1, yPos);

		giveBoardSingleTile(6, xPos - 1, yPos + 1);
		giveBoardSingleTile(7, xPos, yPos + 1);
		giveBoardSingleTile(8, xPos + 1, yPos + 1);
	}

	/**
	 * Invariant: _boardLayout is initialized before calling this method
	 */
	private void giveBoardSingleTile(int tileIndex, int xPos, int yPos)
	{
		if(_board.getCard(xPos, yPos) != null)
			_boardLayout.updateTile(tileIndex, _board.getCard(xPos, yPos).getImageId());
		else
			_boardLayout.updateTile(tileIndex, -1);
	}
}
