package com.realitypd.cardrogue;

import android.util.Log;

import java.util.Random;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public class Board
{
	private Card[][] _board;
	private Decks _decks;

	/**
	 * No-args constructor
	 */
	public Board()
	{
		_decks = new Decks();
		_board = new Card[10][10];
	}

	/**
	 *
	 * @param width must be a non-negative number
	 * @param height must be a non-negative number
	 */
	public Board(int width, int height, Decks decks)
	{
		_decks = decks;
		if(width < 0)
			width = 0;
		if(height < 0)
			height = 0;
		_board = new Card[width][height];
	}

	public int getBoardWidth() { return _board.length;}
	public int getBoardHeight()
	{
		if(_board.length <= 0)
			return 0;
		return _board[0].length;
	}

	public boolean placePlayer(Player player, int startingX, int startingY)
	{
		if(checkNotOnBoard(startingX, startingY))
			return false;
		player.setStartingPosition(startingX, startingY);
		placeStartingTile(startingX, startingY);
		return true;
	}

	/**
	 * Invariant: assumes board dimension is at least the size of the number of players in a game
	 * @param player
	 */
	public synchronized void placePlayerRandom(Player player)
	{
		Random random = new Random();
		int guessX, guessY;
		int guessSizeX = _board.length;
		int guessSizeY = 0;
		if(_board.length > 0)
			guessSizeY = _board[0].length;

		// simple check, just to make sure board is set up
		if(guessSizeX <= 0 || guessSizeY <= 0)
			return;

		while(true)
		{
			guessX = random.nextInt(guessSizeX);
			guessY = random.nextInt(guessSizeY);
			if(_board[guessX][guessY] == null)
			{
				placePlayer(player, guessX, guessY);
				return;
			}
		}
	}

	public void setBoardIndex(int posX, int posY, Card card)
	{
		_board[posX][posY] = card;
	}


	/**
	 *
	 * @param player player that the pathing tile is going to be next to after pick up: to determine rotation
	 * @param positionX
	 * @param positionY
	 * @return
	 */
	public Card pickUpCard(Player player, int positionX, int positionY)
	{
		Card card = _board[positionX][positionY];
		placePathingTile(player, positionX, positionY, 0);
		return card;
	}

	/**
	 *
	 * @param player player that the pathing tile is going to be next to: to determine rotation
	 * @param positionX
	 * @param positionY
	 * @param paths 0 if random pathing tile replaces it, 1-4 if 1-4 pathing tile replaces it
	 */
	public void replaceCard(Player player, int positionX, int positionY, int paths)
	{
		placePathingTile(player, positionX, positionY, paths);
	}


	/**
	 * 0 if random pathing tile replaces it, 1-4 if 1-4 pathing tile replaces it
	 * @param player player that the pathing tile is going to be next to: to determine rotation
	 * @param positionX
	 * @param positionY
	 * @param paths
	 * @return
	 */
	public Card placePathingTile(Player player, int positionX, int positionY, int paths)
	{
		if(checkNotOnBoard(positionX, positionY))
			return null;
		String pathEntrance = tilePlacementAccordingToPlayer(player, positionX, positionY);
		if(paths <= 4 && paths >= 1)
		{
			_board[positionX][positionY] = CardLibrary.getInstance().getCard("path " + paths + pathEntrance);
		}
		else
		{
			// make it a random pathing tile
			Card card = _decks.removeCardFromPathingDeck();
			if(card instanceof Tile && ((Tile) card).isRotatable())
				card = CardLibrary.getInstance().getCard(card.getName() + pathEntrance);
			_board[positionX][positionY] = card;
		}
		addUnexploredTiles(positionX, positionY);
		return _board[positionX][positionY];
	}

	/**
	 * Do not need to check valid move beforehand.
	 * @param newX
	 * @param newY
	 * @return returns card that player stepped on, or null if invalid move.
	 */
	public Card movePlayer(Player player, int newX, int newY)
	{
		// perform actual board manipulation
		if(!validMove(player, newX, newY))
			return null;

		player.movePlayer(newX, newY);

		// is player now effected by card that he stepped on?
		// TODO:

		// place unexplored cards on pathing areas
		addUnexploredTiles(newX, newY);

		return _board[newX][newY];
	}

	/**
	 * Replaces an exploration card back with a random exploration card if
	 * it is valid.
	 * @param player
	 * @param cardX
	 * @param cardY
	 * @return Returns null if not a valid flip, otherwise returns newly flipped over exploration card
	 */
	public Card flipUnexploredCard(Player player, int cardX, int cardY)
	{
		if(checkNotOnBoard(cardX, cardY))
			return null;


		if(!validFlip(player, cardX, cardY))
			return null;

		// can only flip over exploration card backs
		if(_board[cardX][cardY].getName() != "exploration card back")
			return null;

		return placeExplorationCard(player, cardX, cardY);
	}

	public boolean validFlip(Player player, int flipX, int flipY)
	{
		int playerX = player.getxPosition();
		int playerY = player.getyPosition();

		// is it even adjacent to the player?
		if(!((flipX == playerX + 1 && flipY == playerY) ||
				(flipX == playerX - 1 && flipY == playerY) ||
				(flipY == playerY + 1 && flipX == playerX) ||
				(flipY == playerY - 1 && flipX == playerX)))
			return false;

		// check valid paths on current player tile
		Tile playerTile = (Tile) _board[playerX][playerY];
		boolean[] playerTilePaths = playerTile.getPaths();

		// if there is a path between them, return true
		// left of player
		if(flipX < playerX)
			return playerTilePaths[0];
		// above player
		if(flipY < playerY)
			return playerTilePaths[1];
		// right of player
		if(flipX > playerX)
			return playerTilePaths[2];
		// below player
		if(flipY > playerY)
			return playerTilePaths[3];

		// this should never be reached
		Log.d("Board.validFlip", "Should have never reached here.");
		return false;
	}

	/**
	 * Invariant: player must be on a tile
	 * @param newX
	 * @param newY
	 * @return
	 */
	public boolean validMove(Player player, int newX, int newY)
	{
		if(checkNotOnBoard(newX, newY))
			return false;

		int playerX = player.getxPosition();
		int playerY = player.getyPosition();
		// if it is not one tile above, below, left or right of current player position
		// then it is not a valid move.
		if(!((newX == playerX + 1 && newY == playerY) ||
				(newX == playerX - 1 && newY == playerY) ||
				(newY == playerY + 1 && newX == playerX) ||
				(newY == playerY - 1 && newX == playerX)))
			return false;

		// check valid paths on current player tile
		Tile playerTile = (Tile) _board[playerX][playerY];
		boolean[] playerTilePaths = playerTile.getPaths();
		//// check valid paths on new tile location
		// if it is an item, it is valid to step on if player has a path leading to it
		if(_board[newX][newY] instanceof Item)
		{
			// if there is a path between them, return true
			// left of player
			if(newX < playerX)
				return playerTilePaths[0];
			// above player
			if(newY < playerY)
				return playerTilePaths[1];
			// right of player
			if(newX > playerX)
				return playerTilePaths[2];
			// below player
			if(newY > playerY)
				return playerTilePaths[3];
		}

		if(!(_board[newX][newY] instanceof Tile))
			return false;

		Tile newTile = (Tile) _board[newX][newY];
		boolean[] newTilePaths = newTile.getPaths();

		// if there is a path between them, return true
		// left of player
		if(newX < playerX)
			return playerTilePaths[0] && newTilePaths[2];
		// above player
		if(newY < playerY)
			return playerTilePaths[1] && newTilePaths[3];
		// right of player
		if(newX > playerX)
			return playerTilePaths[2] && newTilePaths[0];
		// below player
		if(newY > playerY)
			return playerTilePaths[3] && newTilePaths[1];

		// this should never be reached
		Log.d("Board.validMove", "Should have never reached here.");
		return false;
	}

	public Card getCard(int positionX, int positionY)
	{
		if(checkNotOnBoard(positionX, positionY))
			return new Tile(R.drawable.off_board, "off of board", new boolean[] {false, false, false, false}, null, false);
		return _board[positionX][positionY];
	}

	/**
	 *
	 * @param player player that the card is going to be next to: to determine rotation
	 * @param positionX
	 * @param positionY
	 * @return
	 */
	public Card placeExplorationCard(Player player, int positionX, int positionY)
	{
		if(checkNotOnBoard(positionX, positionY))
			return null;
		String pathEntrance = tilePlacementAccordingToPlayer(player, positionX, positionY);
		Card card = _decks.removeCardFromExplorationDeck();
		if(card instanceof Tile && ((Tile) card).isRotatable())
			card = CardLibrary.getInstance().getCard(card.getName() + pathEntrance);
		return _board[positionX][positionY] = card;
	}

	private Card placeStartingTile(int positionX, int positionY)
	{
		if(checkNotOnBoard(positionX, positionY))
			return null;

		Card card = CardLibrary.getInstance().getCard("starting tile");
		_board[positionX][positionY] = card;

		addUnexploredTiles(positionX, positionY);

		return card;
	}

	/**
	 * Add unexplored tiles around the given x and y tile position if there is paths to such tiles
	 * @param positionX
	 * @param positionY
	 */
	private void addUnexploredTiles(int positionX, int positionY)
	{
		Card aroundCard = _board[positionX][positionY];

		// TODO: actually, can step on potions, which are items
		// if the card isn't a tile, we cannot place paths around it...
		// in fact, it shouldn't be stepped on to begin with!
		if(!(aroundCard instanceof Tile))
		{
			Log.d("Board.addUnexploredTiles", "Should have never reached here.");
			return;
		}

		Tile aroundTile = (Tile) aroundCard;
		boolean[] paths = aroundTile.getPaths();
		if(paths[0])
			placeUnexploredTile(positionX - 1, positionY);
		if(paths[1])
			placeUnexploredTile(positionX, positionY - 1);
		if(paths[2])
			placeUnexploredTile(positionX + 1, positionY);
		if(paths[3])
			placeUnexploredTile(positionX, positionY + 1);
	}

	public Card placeUnexploredTile(int positionX, int positionY)
	{
		if(checkNotOnBoard(positionX, positionY))
			return null;

		// do not place a tile on another tile
		if(_board[positionX][positionY] != null)
			return null;
		return _board[positionX][positionY] = CardLibrary.getInstance().getCard("exploration card back");
	}

	/**
	 * make sure the desired card placement is actually on the board
	 * @param positionX
	 * @param positionY
	 * @return
	 */
	private boolean checkNotOnBoard(int positionX, int positionY)
	{
		if(_board.length < 1)
			return true;
		if(positionX < 0 || positionY < 0)
			return true;
		if((positionX >= _board.length) || (positionY >= _board[0].length))
			return true;
		return false;
	}

	/**
	 * Invariant: player must be adjacent or on tile in order for this to work correctly
	 * @return returns with the necessary space included before the ordering string name
	 */
	private String tilePlacementAccordingToPlayer(Player player, int tileX, int tileY)
	{
		int playerX = player.getxPosition();
		int playerY = player.getyPosition();
		int playerPreviousX = player.getxPrevious();
		int playerPreviousY = player.getyPrevious();

		if(tileX < playerX)
			return " right";
		if(tileX > playerX)
			return " left";
		if(tileY < playerY)
			return " bottom";
		if(tileY > playerY)
			return " top";

		// otherwise, player is standing on the tile, check previous position
		if(tileX < playerPreviousX)
			return " right";
		if(tileX > playerPreviousX)
			return " left";
		if(tileY < playerPreviousY)
			return " bottom";
		if(tileY > playerPreviousY)
			return " top";

		// this should never be reached
		Log.d("Board.tilePlacement...", "Should have never reached here.");
		// just keep the card the same...
		return "";
	}

	public Enemy[] getEnemyCards(Player player)
	{
		Enemy[] enemies = new Enemy[4];
		int enemyCount = 0;

		int xPos = player.getxPosition();
		int yPos = player.getyPosition();

		if(!(_board[xPos][yPos] instanceof Tile))
			return null;

		Tile currentTile = (Tile) _board[xPos][yPos];
		boolean[] newTilePaths = currentTile.getPaths();

		if(!checkNotOnBoard(xPos-1, yPos) &&
				_board[xPos-1][yPos] != null &&
				_board[xPos-1][yPos] instanceof Enemy &&
				newTilePaths[0])
		{
			enemies[enemyCount] = (Enemy) _board[xPos-1][yPos];
			enemyCount++;
		}

		if(!checkNotOnBoard(xPos+1, yPos) &&
				_board[xPos+1][yPos] != null &&
				_board[xPos+1][yPos] instanceof Enemy &&
				newTilePaths[2])
		{
			enemies[enemyCount] = (Enemy) _board[xPos+1][yPos];
			enemyCount++;
		}

		if(!checkNotOnBoard(xPos, yPos-1) &&
				_board[xPos][yPos-1] != null &&
				_board[xPos][yPos-1] instanceof Enemy &&
				newTilePaths[1])
		{
			enemies[enemyCount] = (Enemy) _board[xPos][yPos-1];
			enemyCount++;
		}

		if(!checkNotOnBoard(xPos, yPos+1) &&
				_board[xPos][yPos+1] != null &&
				_board[xPos][yPos+1] instanceof Enemy &&
				newTilePaths[3])
		{
			enemies[enemyCount] = (Enemy) _board[xPos][yPos+1];
			enemyCount++;
		}

		if(enemyCount == 0)
			return null;

		Enemy[] enemiesTemp = new Enemy[enemyCount];
		for(int i=0; i<enemyCount; i++)
			enemiesTemp[i] = enemies[i];

		return enemiesTemp;
	}
}
