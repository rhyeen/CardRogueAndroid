package com.realitypd.cardrogue;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public class CardLibrary
{
	Hashtable<String, Card> _cards;
	// region singleton
	static CardLibrary _instance = null;
	static CardLibrary getInstance() {
		// TODO: thread safe?
		if (_instance == null)
			_instance = new CardLibrary();
		return _instance;
	}
	private CardLibrary()
	{
		_cards = new Hashtable<String, Card>();
		loadLibrary();
	}
	// endregion

	public Card getCard(String cardKey)
	{
		return _cards.get(cardKey);
	}

	/**
	 * Returns false if key already exists, and does not add the key/value to the card library.
	 * @param cardKey
	 * @param card
	 * @return
	 */
	public boolean addEffect(String cardKey, Card card)
	{
		if(_cards.containsKey(cardKey))
			return false;
		_cards.put(cardKey, card);
		return true;
	}

	private void loadLibrary()
	{
		// TODO: load all the cards here to hashtable

		_cards.put("path 1", new Tile(R.drawable.path_1_bottom, "path 1", new boolean[]{false, false, false, true}, null, true));
		_cards.put("path 1 left", new Tile(R.drawable.path_1_left, "path 1", new boolean[]{true, false, false, false}, null, true));
		_cards.put("path 1 top", new Tile(R.drawable.path_1_top, "path 1", new boolean[]{false, true, false, false}, null, true));
		_cards.put("path 1 right", new Tile(R.drawable.path_1_right, "path 1", new boolean[]{false, false, true, false}, null, true));
		_cards.put("path 1 bottom", new Tile(R.drawable.path_1_bottom, "path 1", new boolean[]{false, false, false, true}, null, true));

		_cards.put("path 2", new Tile(R.drawable.path_2_bottom, "path 2", new boolean[]{false, false, true, true}, null, true));
		_cards.put("path 2 left", new Tile(R.drawable.path_2_left, "path 2", new boolean[]{true, false, false, true}, null, true));
		_cards.put("path 2 top", new Tile(R.drawable.path_2_top, "path 2", new boolean[]{true, true, false, false}, null, true));
		_cards.put("path 2 right", new Tile(R.drawable.path_2_right, "path 2", new boolean[]{false, true, true, false}, null, true));
		_cards.put("path 2 bottom", new Tile(R.drawable.path_2_bottom, "path 2", new boolean[]{false, false, true, true}, null, true));

		_cards.put("path 2v2", new Tile(R.drawable.path_2v3_top, "path 2", new boolean[]{false, true, false, true}, null, true));
		_cards.put("path 2v2 left", new Tile(R.drawable.path_2v3_left, "path 2", new boolean[]{true, false, true, false}, null, true));
		_cards.put("path 2v2 top", new Tile(R.drawable.path_2v3_top, "path 2", new boolean[]{false, true, false, true}, null, true));
		_cards.put("path 2v2 right", new Tile(R.drawable.path_2v3_left, "path 2", new boolean[]{true, false, true, false}, null, true));
		_cards.put("path 2v2 bottom", new Tile(R.drawable.path_2v3_top, "path 2", new boolean[]{false, true, false, true}, null, true));

		_cards.put("path 3", new Tile(R.drawable.path_3_bottom, "path 3", new boolean[]{true, false, true, true}, null, true));
		_cards.put("path 3 left", new Tile(R.drawable.path_3_left, "path 3", new boolean[]{true, true, false, true}, null, true));
		_cards.put("path 3 top", new Tile(R.drawable.path_3_top, "path 3", new boolean[]{true, true, true, false}, null, true));
		_cards.put("path 3 right", new Tile(R.drawable.path_3_right, "path 3", new boolean[]{false, true, true, true}, null, true));
		_cards.put("path 3 bottom", new Tile(R.drawable.path_3_bottom, "path 3", new boolean[]{true, false, true, true}, null, true));

		_cards.put("path 4", new Tile(R.drawable.path_4, "path 4", new boolean[]{true, true, true, true}, null, false));

		_cards.put("exploration card back", new Tile(R.drawable.exploration_card_back, "exploration card back", new boolean[]{false, false, false, false}, null, false));
		_cards.put("starting tile", new Tile(R.drawable.starting_tile, "starting tile", new boolean[]{true, true, true, true}, null, false));

		_cards.put("potion blue", new Item(R.drawable.potion_b, "blue potion", "Restore 5 health", null, 2, true, true, true, false));
		_cards.put("potion green", new Item(R.drawable.potion_g, "green potion", "Restore full health", null, 4, true, true, true, false));
		_cards.put("potion pink", new Item(R.drawable.potion_p, "pink potion", "+2 to max health", null, 2, true, true, true, false));
		_cards.put("potion red", new Item(R.drawable.potion_r, "red potion", "+5 to max health", null, 4, true, true, true, false));
		_cards.put("potion yellow", new Item(R.drawable.potion_y, "yellow potion", "+1 to attack", null, 2, true, true, true, false));
		_cards.put("potion orange", new Item(R.drawable.potion_o, "orange potion", "+2 to attack", null, 3, true, true, true, false));


		_cards.put("trap spike", new Tile(R.drawable.spike_trap, "trap spike", new boolean[]{true, true, true, true}, null, false));
		_cards.put("trap poison", new Tile(R.drawable.spike_trap, "trap poison", new boolean[]{true, true, true, true}, null, false));
		_cards.put("trap paralysis", new Tile(R.drawable.spike_trap, "trap paralysis", new boolean[]{true, true, true, true}, null, false));

		_cards.put("cave bat1", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat2", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat3", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat4", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat5", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat6", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat7", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat8", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat9", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));
		_cards.put("cave bat10", new Enemy(R.drawable.cave_bat, "cave bat", null, 1, 1, 4));

		_cards.put("kobold1", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));
		_cards.put("kobold2", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));
		_cards.put("kobold3", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));
		_cards.put("kobold4", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));
		_cards.put("kobold5", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));
		_cards.put("kobold6", new Enemy(R.drawable.kobold, "kobold", null, 4, 4, 6));

		_cards.put("goblin1", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));
		_cards.put("goblin2", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));
		_cards.put("goblin3", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));
		_cards.put("goblin4", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));
		_cards.put("goblin5", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));
		_cards.put("goblin6", new Enemy(R.drawable.goblin, "goblin", null, 2, 3, 8));

		_cards.put("vampire bat1", new Enemy(R.drawable.vampire_bat, "vampire bat", null, 2, 2, 4));
		_cards.put("vampire bat2", new Enemy(R.drawable.vampire_bat, "vampire bat", null, 2, 2, 4));
		_cards.put("vampire bat3", new Enemy(R.drawable.vampire_bat, "vampire bat", null, 2, 2, 4));
		_cards.put("vampire bat4", new Enemy(R.drawable.vampire_bat, "vampire bat", null, 2, 2, 4));

		_cards.put("the basilisk", new Enemy(1, R.drawable.boss, "the basilisk", null, 5, 1));

		ArrayList<Item> shopItems = new ArrayList<Item>();
		shopItems.add((Item) _cards.get("potion blue"));
		shopItems.add((Item) _cards.get("potion green"));
		shopItems.add((Item) _cards.get("potion pink"));
		shopItems.add((Item) _cards.get("potion red"));
		shopItems.add((Item) _cards.get("potion yellow"));
		shopItems.add((Item) _cards.get("potion orange"));


		_cards.put("shop1", new Shop(R.drawable.shop, "shop", shopItems));



		// get card effects from EffectLibrary
	}
}
