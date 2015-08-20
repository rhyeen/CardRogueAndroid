package com.realitypd.cardrogue;

import android.app.ActionBar;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ryan Saunders on 12/6/2014.
 */
public class InventoryFragment extends Fragment
{
	InventoryLayout _inventoryList = null;
	ShopLayout _shopList = null;
	TextView _title = null;
	LinearLayout.LayoutParams _titleParams = null;
	LinearLayout _screenLayout = null;
	LinearLayout.LayoutParams _inventoryListParams = null;
	Player _player = null;
	Shop _shop = null;

	public interface OnItemUsedListener {
		public void onItemUsed(InventoryFragment inventoryFragment, Item item, boolean refreshScreen);
	}

	OnItemUsedListener _onItemUsedListener = null;

	public void setItemUsedListener(OnItemUsedListener onItemUsedListener) {
		_onItemUsedListener = onItemUsedListener;
	}

	public interface OnRequesterListener {
		public void onRequester(InventoryFragment inventoryFragment);
	}

	OnRequesterListener _onRequesterListener = null;

	public void setRequesterListener(OnRequesterListener onRequesterListener) {
		_onRequesterListener = onRequesterListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		_screenLayout = new LinearLayout(getActivity());
		_screenLayout.setOrientation(LinearLayout.VERTICAL);

		_title = new TextView(getActivity());
		_titleParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		_inventoryListParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);

		_title.setText("INVENTORY");
		_title.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		_title.setTextSize(24.0f);
		_title.setGravity(Gravity.CENTER);
		_title.setTextColor(0xffd6d6d6);
		_title.setPadding(0, 40, 0, 0);

		_screenLayout.addView(_title, _titleParams);

		_inventoryList = new InventoryLayout(getActivity());
		_screenLayout.addView(_inventoryList, _inventoryListParams);

		// request update
		if(_onRequesterListener != null)
			_onRequesterListener.onRequester(InventoryFragment.this);

		_inventoryList.setToTheParentListener(new InventoryLayout.OnToTheParentListener()
		{
			@Override
			public void onToTheParent(InventoryLayout inventoryLayout, boolean isInShop, Item item)
			{
				if(_player == null)
					return;

				if(isInShop)
				{
					_player.getInventory().discardItem(item, false);
					_player.changeGold(item.getGoldAmount());

					// TODO: ask how to fix in class
					// notifyDataSetChanged()
					//_inventoryList.refreshScreen();
					// the real solution should be above, fix this later...
					if(_onItemUsedListener != null)
						_onItemUsedListener.onItemUsed(InventoryFragment.this, item, true);
				}
				else
				{
					// TODO: add more effects later
					_player.getInventory().discardItem(item, false);
					provideEffect(item);
					if(_onItemUsedListener != null)
						_onItemUsedListener.onItemUsed(InventoryFragment.this, item, false);
				}
			}
		});

		return _screenLayout;
	}

	/**
	 * Affects the player somehow based on potion used
	 */
	private void provideEffect(Item item)
	{
		String name = item.getName();

		if(name.contains("blue"))
			_player.changeCurrentHealth(5);
		else if(name.contains("green"))
			_player.changeCurrentHealth(_player.getMaxHealth());
		else if(name.contains("pink"))
			_player.changeMaxHealth(2);
		else if(name.contains("red"))
			_player.changeMaxHealth(5);
		else if(name.contains("yellow"))
			_player.changeAttackRoll(1);
		else if(name.contains("orange"))
			_player.changeAttackRoll(2);
	}

	public void setPlayer(Player player)
	{
		_player = player;
		if(_inventoryList != null)
			_inventoryList.setPlayer(player);
	}

	public void setInShop(boolean isInShop, Shop shop)
	{
		if (isInShop)
		{
			_shop = shop;
			// rebuild the fragment layout to have the shop
			LinearLayout.LayoutParams vParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					0,
					1
			);
			_screenLayout.removeAllViews();
			_screenLayout = new LinearLayout(getActivity());
			_screenLayout.setOrientation(LinearLayout.VERTICAL);

			LinearLayout inventoryLayout = new LinearLayout(getActivity());
			inventoryLayout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout shopLayout = new LinearLayout(getActivity());
			shopLayout.setOrientation(LinearLayout.VERTICAL);

			_screenLayout.addView(inventoryLayout, vParams);
			_screenLayout.addView(shopLayout, vParams);

			_shopList = new ShopLayout(getActivity());
			_shopList.setShop(shop);
			_inventoryList.setInShop(true);
			inventoryLayout.addView(_title, _titleParams);
			inventoryLayout.addView(_inventoryList, _inventoryListParams);

			TextView shopTitle = new TextView(getActivity());
			shopTitle.setText("SHOP");
			shopTitle.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
			shopTitle.setTextSize(24.0f);
			shopTitle.setGravity(Gravity.CENTER);
			shopTitle.setTextColor(0xffd6d6d6);
			shopTitle.setPadding(0, 40, 0, 0);

			shopLayout.addView(shopTitle, _titleParams);
			shopLayout.addView(_shopList, _inventoryListParams);

			_shopList.setToTheParentListener(new ShopLayout.OnToTheParentListener()
			{
				@Override
				public void onToTheParent(ShopLayout inventoryLayout, Item item)
				{
					if(_player == null)
						return;

					if(_shop == null)
						return;

					// don't allow purchase if player doesn't have enough gold
					if (_player.getTotalGold() < item.getGoldAmountAtShop())
						return;

					_player.getInventory().addItem(item);
					_player.changeGold((-1)*item.getGoldAmountAtShop());

					// refresh the screen, CHANGE LATER, see other use above for detail
					if(_onItemUsedListener != null)
						_onItemUsedListener.onItemUsed(InventoryFragment.this, item, true);
				}
			});
		}
	}
}
